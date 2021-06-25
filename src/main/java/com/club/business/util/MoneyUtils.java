package com.club.business.util;

import java.math.BigDecimal;

/**
 * 通用数值类型金额大写转换
 *
 * @author Tom
 * @date 2019/07/09
 */
public class MoneyUtils {

    /**
     * 汉语中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟" };
    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";
    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "-";
    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 2;

    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param numberOfMoney 输入的金额
     * @return 对应的汉语大写
     */
    public static String number2CNMontray(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        int signum = numberOfMoney.signum();
        /**
         * 零元整的情况
         */
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        /**
         * 这里会进行金额的四舍五入
         */
        long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
        /**
         * 得到小数点后两位值
         */
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        /**
         * 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
         */
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            /**
             * 每次获取到最后一个数
             */
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            /**
             * 让number每次都去掉最后一个数
             */
            number = number / 10;
            ++numIndex;
        }
        /**
         * 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
         */
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        /**
         * 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
         */
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }


    /**
     * 中文大写金额转换成数字金额
     * @param chineseNumber 大写汉字金额参数
     * @return
     */
    private static double chineseNumber2Int(String chineseNumber){
        double result = 0;
        /**
         * 存放一个单位的数字如：十万
         */
        double temp = 1;
        /**
         * 判断是否有chArr
         */
        int count = 0;
        char[] cnArr = new char[]{'壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
        char[] chArr = new char[]{'拾', '佰', '仟', '万', '亿', '角','分','厘'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            /**
             * 判断是否是chArr
             */
            boolean b = true;
            char c = chineseNumber.charAt(i);
            /**
             * 非单位，即数字
             */
            for (int j = 0; j < cnArr.length; j++) {
                if (c == cnArr[j]) {
                    /**
                     * 添加下一个单位之前，先把上一个单位值添加到结果中
                     */
                    if(0 != count){
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            /**
             * 单位{'十','百','千','万','亿'}
             */
            if(b){
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                            case 0:
                                temp *= 10;
                                break;
                            case 1:
                                temp *= 100;
                                break;
                            case 2:
                                temp *= 1000;
                                break;
                            case 3:
                                temp *= 10000;
                                break;
                            case 4:
                                temp *= 100000000;
                                break;
                            case 5:
                                temp *= 0.1;
                                break;
                            case 6:
                                temp *= 0.01;
                                break;
                            case 7:
                                temp *= 0.001;
                                break;
                            default:
                                break;
                        }
                        count++;
                    }
                }
            }
            /**
             * 遍历到最后一个字符
             */
            if (i == chineseNumber.length() - 1) {
                result += temp;
            }
        }
        return result;
    }
    public static void main(String args[]){
        String s1 = "壹万伍仟肆佰壹拾圆叁角伍分肆厘";
        String s2 = "捌万陆仟肆佰壹拾圆整";
        String s3 =  "壹万伍仟肆佰壹拾元贰角捌分肆厘";
        String s4 =  "拾壹亿壹仟万伍仟肆佰壹拾元贰角捌分肆厘";
        System.out.printf("%s = %6.3f\n",s1,chineseNumber2Int(s1));
        System.out.printf("%s = %6.3f\n",s2,chineseNumber2Int(s2));
        System.out.printf("%s = %6.3f\n",s3,chineseNumber2Int(s3));
        System.out.printf("%s = %6.3f\n",s4,chineseNumber2Int(s4));

        double money = -2020004.01;
        BigDecimal numberOfMoney = new BigDecimal(money);
        String s = MoneyUtils.number2CNMontray(numberOfMoney);
        System.out.println("你输入的金额为：【"+ money +"】   #--# [" +s.toString()+"]");
    }
}

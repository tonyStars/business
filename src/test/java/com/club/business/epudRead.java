package com.club.business;

import java.io.*;

public class epudRead {

    public static void main(String[] args) {

            File file = new File("D:\\desk\\epud\\神秘复苏.txt");//获得文件
            try {
                FileReader fr = new FileReader(file);//读取文件
                BufferedReader br = new BufferedReader(fr);//字符输入流化
                try {
                    String s, s2 = new String();
                    while ((s = br.readLine()) != null) {//循环的读取一行一行读取文件，知道最后一行为空停止
                        s2 += s + "\n ";
                    }
                    br.close();//关闭输入流
//                    System.out.println(spileStr(s2));//打印输出TXT文件中的数据
                    System.out.println(s2);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static String spileStr(String str){
        String txt = new String();
        if(str.length() > 100){
            txt = str.substring(0,100) + "\n ";
            txt = txt + spileStr(str.substring(100,str.length()-1));
        }else{
            txt = str + "\n ";
        }
        return txt;
    }
}

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        @page{
            size:297mm 210mm;
            margin: 10pt 20pt 40pt 20pt;
            @bottom-center{
                content:"${printFooter!}";
                font-family: SimSun;
                font-size: 9pt;
            };
        }
        .table{border:solid grey; border-width:1px 0 0 1px;}
        .table td{border:solid grey; border-width:0 1px 1px 0;}
    </style>
</head>
<body>
<div style="font-family:SimSun; font-size:10pt;">
    <div style="margin:0 0 10pt 0;text-align:center;">
        <span style="font-size:22pt; font-weight:bold">${companyName!}</span>
    </div>
    <table cellspacing="0" cellpadding="0" style="width:700pt">
        <tr>
            <td style="padding:0 5.4pt; vertical-align:top; width:230pt">
                <div style="margin-bottom:10pt;vertical-align:baseline;">公司名称：${companyName!}</div>
            </td>
            <td style="padding:0 5.4pt;vertical-align:top;width:230pt">
                <div style="margin-botom:10pt;vertical-align:baseline;">公司明细单</div>
            </td>
            <td style="padding:0 5.4pt;vertical-align:top;width:240pt">
                <div style="margin-botom:10pt;vertical-align:baseline;">父级公司：${parentName!}</div>
            </td>
        </tr>
        <tr>
            <td style="padding:0 5.4pt; vertical-align:top; width:230pt">
                <div style="margin-bottom:10pt;vertical-align:baseline;">公司编码：${companyCode!}</div>
            </td>
            <td style="padding:0 5.4pt;vertical-align:top;width:230pt">
                <div style="margin-botom:10pt;vertical-align:baseline;">${.now?string("yyyy-MM-dd")}</div>
            </td>
            <td style="padding:0 5.4pt;vertical-align:top;width:230pt">
                <div style="margin-bottom:10pt;vertical-align:baseline;">公司地址：${addr!}</div>
            </td>
        </tr>
        <tr>
            <td style="padding:0 5.4pt; vertical-align:top; width:230pt">
                <div style="margin-bottom:10pt;vertical-align:baseline;">联系人：${contactPerson!}</div>
            </td>
            <td style="padding:0 5.4pt;vertical-align:top;width:230pt">
                <div style="margin-botom:10pt;vertical-align:baseline;"></div>
            </td>
            <td style="padding:0 5.4pt;vertical-align:top;width:230pt">
                <div style="margin-bottom:10pt;vertical-align:baseline;">联系电话：${contactPhone!}</div>
            </td>
        </tr>
<#--        <tr>-->
<#--            <td style="padding:0 5.4pt; vertical-align:top; width:230pt">-->
<#--                <div style="margin-bottom:10pt;vertical-align:baseline;">收款人全称：${relationName!}</div>-->
<#--            </td>-->
<#--        </tr>-->
        <tr>
            <td style="padding:0 5.4pt; vertical-align:top; width:230pt">
                <div style="margin-bottom:10pt;vertical-align:baseline;">所在省份：${stateName!}</div>
            </td>
            <td style="padding:0 5.4pt;vertical-align:top;width:230pt">
                <div style="margin-botom:10pt;vertical-align:baseline;">所在城市：${cityName!}</div>
            </td>
            <td style="padding:0 5.4pt;vertical-align:top;width:230pt">
                <div style="margin-bottom:10pt;vertical-align:baseline;">所在乡镇：${countyName!}</div>
            </td>
        </tr>
    </table>
    <div style="margin-bottom:10pt;"></div>
    <table cellspacing="0" cellpadding="0" class="table" style="width:100%;text-align:center;">
        <tr style="height:29.1pt;">
            <td style="width:61pt;">编号</td>
            <td style="width:74pt;">银行名称</td>
            <td style="width:63pt;">开户行</td>
            <td style="width:50pt;">账号</td>
            <td style="width:24pt;">账户名</td>
            <td style="width:24pt;">纳税人识别号</td>
            <td style="width:34pt;">地址</td>
        </tr>
     <#list detailList as com>
     <tr style="height:29.1pt;">
         <td style="width:61pt;">${com_index + 1}</td>
         <td style="width:74pt;">${com.bankName!}</td>
         <td style="width:63pt;">${com.bankInfo!}</td>
         <td style="width:50pt;">${com.account!}</td>
         <td style="width:24pt;">${com.accountName!}</td>
         <td style="width:24pt;">${com.taxno!}</td>
         <td style="width:24pt;">${com.address!}</td>
     </tr>
     </#list>
    </table>
    <div style="margin-bottom:10pt;"></div>
    <table cellspacing="0" cellpadding="0" style="width:705.95pt">
        <tr>
            <td style="padding:0 5.4pt;vertical-align:top; width:165.65pt">
                <div style="margin-bottom:10pt;">财务经理：</div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;"></div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;"></div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;">核算：</div>
            </td>
        </tr>
        <tr>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.65pt">
                <div style="margin-bottom:10pt;">签章：</div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;"></div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;"></div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;">签名：</div>
            </td>
        </tr>
        <tr>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.65pt">
                <div style="margin-bottom:10pt;">总经理：</div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;">分管领导：</div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;">部门经理：</div>
            </td>
            <td style="padding:0 5.4pt; vertical-align:top; width:165.7pt">
                <div style="margin-bottom:10pt;">创建人：</div>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
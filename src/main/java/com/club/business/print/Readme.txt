打印使用：
前端统一请求入口：/print/printBill.do?billIds=' + billIds + '&printType=' + printType + '&companyId=' + companyId;
首先在PrintTypeEnum枚举类中定义打印类型
在枚举类PrintTypeEnum中定义好实现类之后,则重写实现类方法。如：CompanyPrintService实现类




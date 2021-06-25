<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>大乐透下期幸运号码</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
    <link rel="stylesheet" href="${ctx}/./plugin/roll-number/css/odometer-theme-car.css">
</head>
<style>
    .odometer {
        font-size: 40px;
    }
</style>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <div class="odometer">0000000</div>
    </div>
</div>
<script src="${ctx}/./plugin/roll-number/js/jquery.min.js"></script>
<script src="${ctx}/./plugin/roll-number/js/odometer.js"></script>
<script>
    window.odometerOptions = {
        format: '(ddd).dd'
    };
    setTimeout(function(){
        $('.odometer').html(4232342);
    }, 1000);
</script>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: wk
  Date: 2019/3/31
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--引入jquery--%>
    <script type="text/javascript" src="https://cdn.staticfile.org/jquery/1.12.4/jquery.min.js"></script>
    <%--引入bootstrap--%>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

</head>
<body>
    <h1>hello world!</h1>
    <button class="btn btn-default">按钮</button>
    <jsp:forward page="/emps"></jsp:forward>
</body>
</html>

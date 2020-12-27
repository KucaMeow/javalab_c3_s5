<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<form method="${form.method}" action="${form.action}">
    <#list fields as field>
        <input type="${field.type}" name="${field.name}" placeholder="${field.placeholder}">
    </#list>
    <input type="submit">
</form>
</body>
</html>
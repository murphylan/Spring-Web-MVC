<#ftl encoding="UTF-8">
<html>
<head>
	<title>@Controller Example</title>
</head>	
<body>
<div>
<#assign keys = h?keys>   
<#list keys as key>${key}</#list>  
<#assign values = h?values>   
<#list values as key>${key}</#list>  
</div>
        <h1>Please upload a file</h1>
        <form method="post" action="/account/form" enctype="multipart/form-data">
            <input type="text" name="name"/>
            <input type="file" name="file"/>
            <input type="submit"/>
        </form>
------------
<#import "/spring.ftl" as spring />
...
<form action="" method="POST">
  Name: 
  <@spring.bind "h"/> 
  <input type="text" 
    name="${spring.status.expression?default("")}" 
    value="${spring.status.value?default("")}" /><br>
  <#list spring.status.errorMessages as error> <b>${error}</b> <br> </#list>
  <br>
  ... 
  <input type="submit" value="submit"/>
</form>
...
读属性文件的值
<@spring.message "welcome.title"/>
<@spring.url "account/list"/>
${request.getRequestUri()}

<#import "../baseftl/page.ftl" as comm />
000000
${RequestParameters?default({})?keys?size}

<#assign yy = RequestParameters?default({})?values>  
<#list yy as key>${key}</#list>
   
<@comm.pages/>
${request.getContextPath()}
</body>
</html>

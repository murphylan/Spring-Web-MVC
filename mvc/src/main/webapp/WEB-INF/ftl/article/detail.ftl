<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/json.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.datePicker.min-2.1.2.js"></script>
<script type="text/javascript" src="/scripts/date.js"></script>
<title>Article details</title>
<script language=javascript>

function doSubmit(){
   if($.trim($("textarea[name='content']").val())==""){
       alert("Please input your comment!");
     $("textarea[name='content']").val("");
     $("textarea[name='content']").focus();
     return ;
   }else{
      //$('#myform').submit();
      var id=$('#articleId').val();
      var str=$('#content').val();
      $.ajax({
   		type: "POST",
   		url: "${request.getContextPath()}/comment/ajaxadd",
   		data: "articleId="+id+"&content="+str,
   		success: function(msg){
     		alert( "Comment added successfully,waiting approval!" );
     		$("textarea[name='content']").val("");
     	}
     });
   }
}

</script>
		
</head>

<body>

<div>
<br />

<div id="man_zone">
  
  <table width="99%" border="0" align="center"  cellpadding="3" cellspacing="1" class="table_style">
   <tr>
    <td><a href="/article/get/${article.id?default('')}">Edit</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href="/article/del/${article.id?default('')}">Delete</a></td>
   </tr>
    <tr>
      <td width="100%" align="center" ><font size="4"><b>${article.title?default("")}</font></b></td>
    </tr>
   
    
    <tr>
      <td width="100%" align="center"><font size="2">Time:${article.insertDate?string("yyyy-MM-dd HH:mm")?default("")}  Author:${article.author?default("")}</font></td>
    </tr>
    
     <tr>
     <td width="100%" align="left">
       ${article.content?default("")} 
     </td>
    </tr>
	<#list article.attachmentList as list>
     <tr>
      <td width="100%">
            Attachments: ${list_index+1}:&nbsp;<a href="${request.getContextPath()}/article/download/${list.id?default("")}">${list.name?default("")}(${list.fileSize?default("")})</a>  
      </td>
    </tr>
    </#list>
     
  </table>
 
</div>
</div>
</body>
</html>

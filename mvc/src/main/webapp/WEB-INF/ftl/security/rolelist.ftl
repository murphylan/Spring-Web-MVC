<#import "../baseftl/page.ftl" as comm />
<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="save" content="history"> 
<title>Role list</title> 
<link rel="stylesheet" type="text/css" href="/styles/default.css" />
<style type="text/css"> 
.talk {display:block;font:normal 12px Verdana;text-decoration:none;clear:both;border-left:none;border-right:none;padding:8px 0 3px 9px;border-bottom-width:2px;border-top:none;}
.talk b {font-size:14px}
.toarea .F,.toarea .M {border-bottom-width:1px;}
.hover{background-color:#cddaed;}  /*这里是鼠标经过时的颜色*/ 
</style> 
<script type="text/javascript" src="/scripts/jquery.tablesorter.js"></script>
<script type="text/javascript" src="/scripts/jquery.checkboxes.pack.js"></script>
<script>
$(document).ready(function(){
	$("#all").click(function(){
	   if($("#all").attr("checked")==true){
		$("#myform").checkCheckboxes();
		}else
		$("#myform").unCheckCheckboxes();
	});
	
	$("#styling tr").live("mouseover",function(){ 
    	$(this).addClass("hover");    //鼠标经过添加hover样式 
 	});
 	
 	$("#styling tr").live("mouseout",function(){ 
    	$(this).removeClass("hover");   //鼠标离开移除hover样式
    });
 	
 $("#styling").tableSorter({
			sortColumn: '',					  
			sortClassAsc: 'headerSortUp', 		
			sortClassDesc: 'headerSortDown',	
			headerClass: 'header' 				
		});
});

function p_del() {
	var msg = "Are you sure to delete it?\n\nPlease confirm!";
	if (confirm(msg)==true){
	return true;
	}else{
	return false;
	}
}

function delAll(){
   var ids=$("input[name=ids]:checked");
   if(ids.length==0){
     alert("No chosen article, please choose!");
     return ;
   }else{
    if(confirm("Are you sure to delete chosen article?\n\nPlease confirm!")){
      $('#myform').submit();
      return false;
    }
  }
}
</script>
</head>
<body>
<div id="sub_info" align="left">
<img src="/images/system/hi.gif"/>
<span id="show_text">System Management <img broder="0" src="/images/system/slide.gif"/> Role List</span>
</div>
<form id="myform" action="${request.getContextPath()}/security/delallrole" method="post">
<table id="styling" border="0" cellpadding="0" cellspacing="0">
<thead> 
 <tr>
   <td width="1%"><input id="all"  type="checkbox"/></td>
   <th width="5%">&nbsp;</th>
   <th>Role Name</th>
   <th width="35%">Authority</th>
   <th width="30%">Operation</th>
 </tr>
</thead> 
 <tbody>
 <#if page.result?size gt 0  >
<#list page.result as list>
<tr>
 <td align="right"><input type="checkbox" name="ids"  value="${list.id?default('')}"/></td>
 <td align="center">${page.getFirst()+list_index}&nbsp;</td>
<td>${list.name?default("")}&nbsp;</td>
<#assign authName = list.getAuthNames()>
<td><#if authName?length lt 32>${authName?default("")}&nbsp;<#else>${authName[0..32]}...&nbsp;</#if></td>
<td>
<@sec.authorize access="hasRole('ROLE_USER_GET')">
<a href="${request.getContextPath()}/security/getrole/${list.id?default('')}">Edit</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="${request.getContextPath()}/security/delrole/${list.id?default('')}" onclick="javascript:return p_del()">Delete</a>
</@sec.authorize>
</td>
</tr>
</#list>
 <#else>
 <tr><td colspan="5">No data!</td></tr>
 </#if>				
 </tbody>
</table>
</form>
<@comm.pages/>

<div>&nbsp;</div>
<div style="height:10px">
 <a  href="javascript:delAll();">Delete All</a>&nbsp;&nbsp;&nbsp;&nbsp;
</div>

</div>
</body>
</html>
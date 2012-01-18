<#import "../baseftl/page.ftl" as comm />
<#import "/spring.ftl" as spring />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="save" content="history"> 
<title>Article List</title> 
<link rel="stylesheet" type="text/css" href="/styles/default.css" />
<script type="text/javascript" src="/scripts/jquery.tablesorter.js"></script>
<script type="text/javascript" src="/scripts/jquery.checkboxes.pack.js"></script>

<style type="text/css"> 
.hover{background-color:#cddaed;}  /*这里是鼠标经过时的颜色*/ 
</style>
<script language=javascript>
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
<span id="show_text">
My Space 
<img broder="0" src="/images/system/slide.gif"/>
 My Article
</span>
</div>

<form id="searchForm" name="searchForm" method="post" action="${request.getContextPath()}/article/list/${folderId?default('')}">
Title：<@spring.formInput "article.title" />&nbsp; &nbsp; 
Author：<@spring.formInput "article.author" "size=10"/>&nbsp; &nbsp; 
<input type="submit" value="Find"/>&nbsp; 

</form>

<form id="myform" name="myform" method="post" action="${request.getContextPath()}/article/delall/">
<table id="styling" border="0" cellpadding="0" cellspacing="0">
<thead> 
 <tr>
   <td width="1%"><input id="all"  type="checkbox"/></td>
   <th width="5%">&nbsp;</th>
   <th>Title</th>
   <th>Author</th>
   <th>Publish Date</th>
   <th>Operation</th>
 </tr>
</thead> 
 
 <tbody>
 <#if page.result?size gt 0  >
 <#list page.result as list>
 <tr>
 <td align="right"><input type="checkbox" name="ids"  value="${list.id?default('')}"/></td>
 <td align="center">${page.getFirst()+list_index}</td>
 <td>
 <a href="${request.getContextPath()}/article/detail/${list.id?default("")}" title="${list.title?default("")}">
 <#if list.title?length lt 20 >${list.title?default("")}<#else>${list.title[0..19]?default("")}...</#if>
 </a>
 </td>
 <td>${list.author?default("")}</td>
 <td>${list.insertDate?string("yyyy-MM-dd HH:mm")}</td>
 <td >
	<a href="${request.getContextPath()}/article/get/${list.id?default("")}">Edit</a>
		&nbsp;&nbsp;
	<a href="${request.getContextPath()}/article/del/${list.id?default("")}" onclick="return p_del();">Delete</a>
		&nbsp;&nbsp;
 </td>
 </tr>
 </#list>
 <#else>
 <tr><td colspan="6">No data!</td></tr>
 </#if>				
 </tbody>
</table>
<input type="hidden" name="treeId" value="${folderId?default('')}" >
</form>     
<div><@comm.pages url="${request.getContextPath()}/article/list/${folderId?default('')}"/></div>
<div>&nbsp;</div>
<div style="height:10px">
 <a onClick="delAll();" href="#">Delete All</a>&nbsp;&nbsp;&nbsp;&nbsp;
 <a href="${request.getContextPath()}/article/input/${folderId?default('')}" >Add Article</a>
</div>
    </body>
</html>

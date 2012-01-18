<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title><decorator:title></decorator:title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css" href="/styles/frame.css" />
<link rel="stylesheet" type="text/css" href="/scripts/tree/!style.css" />

<script type="text/javascript" src="/scripts/tree/jquery.js"></script>
<script type="text/javascript" src="/scripts/tree/jquery.cookie.js"></script>
<script type="text/javascript" src="/scripts/tree/jquery.hotkeys.js"></script>
<script type="text/javascript" src="/scripts/tree/jquery.jstree.js"></script>
<script type="text/javascript" src="/scripts/tree/mytree.js"></script>
<script src="/scripts/jquery.layout.js" type=text/javascript></script>

<script type=text/javascript>
$(function () {
	var date = new Date(); 
	date.setTime(date.getTime() + (20 * 60 * 1000)); //20 minutes
	var options = { path: '/', expires: date };// share on the whole server
	var COOKIE_NAME = 'myNav';
	
	$('body').layout({ applyDefaultStyles: true });
	$("ul[name='"+$.cookie(COOKIE_NAME)+"']").css("display","block");
	$('h2').click(function(){
			$(this).siblings("ul[name='"+$(this).attr('name')+"']").toggle(100);
			$(this).siblings("ul").css("display","none");
	});
	$("#leftPan ul li a").click(function(){
		var nameStr = $(this).parent().parent().attr("name");
		$.cookie(COOKIE_NAME, null);
		$.cookie(COOKIE_NAME,nameStr,options);
		return true;
	});


	$("#mmenu img").click(function () {
		switch(this.id) {
			case "add_default":
			case "add_folder":
				$("#demo").jstree("create", null, "last", { "attr" : { "rel" : this.id.toString().replace("add_", "") } });
				break;
			case "search":
				$("#demo").jstree("search", document.getElementById("text").value);
				break;
			case "text": break;
			default:
				$("#demo").jstree(this.id);
				break;
		}
	});

    $("#search_button").click(function(){
	       var search_str=$("#search_str").val();
	       window.location.href="/article/lucene/"+search_str;
	    });
	
});


</script>
<style>
#tree_style {
	background-image: url(/images/system/manage_r3_c11.gif);
	background-repeat: repeat-x;
	padding-top: 5px;
	padding-left: 25px;
	padding-right: 5px;
}
</style>
<decorator:head></decorator:head>
</head>
<body>
<div class="ui-layout-center">
<div id="centerPan"><decorator:body></decorator:body></div>
</div>
<div class="ui-layout-north">
<div id="topPan">
<span style="vertical-align:text-top;float:right"><input type="text" id="search_str" name="search_str"/> <input type="button" value="search" id="search_button"/></span>
<div id="systemName" >
<a href="index.html">Knowledge Base System</a>
</div>


<sec:authorize access="isAuthenticated()">
<div align='right'>Login Time:<sec:authentication
	property="principal.loginTime" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Welcome, <strong><sec:authentication
	property="principal.username" /></strong> [<a href="#"><sec:authentication
	property="principal.roleList[0].name" /></a>]&nbsp;&nbsp;&nbsp;&nbsp; <a
	target="_parent" href="/j_spring_security_logout">Logout</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</sec:authorize>
</div>
</div>
<div class="ui-layout-south">
<div id="footermainPan" align="center">
<div id="footerPan">
<ul>
	<li><a href="/">Home</a>|</li>
	<li><a href="#">xxxx</a>|</li>
	<li><a href="#">xxxx</a>|</li>
	<li><a href="/attached/oracle.jsp">DBBackup</a>|</li>
	<li><a href="#">xxxx</a>|</li>
	<li><a href="#">xxxx</a>|</li>
	<li><a href="#">xxxx</a>|</li>
	<li><a href="#">xxxx</a>|</li>
	<li><a href="#">xxxx</a></li>

</ul>
<div class="copyright">&copy;2010 JavaTraining. All right
reserved.</div>
</div>
</div>
</div>

<div class="ui-layout-west">
<div id="tree_style">
<div id="mmenu" style="overflow:auto;">
		<img id="add_folder" title="add folder" style="display:block; float:left;cursor:pointer" src="/images/icons/add.gif"/>
		<!--  
		<input type="button" id="add_default" value="add file" style="display:block; float:left;"/>
		-->
		<img id="rename" title="rename folder" style="display:block; float:left;cursor:pointer" src="/images/icons/rename.gif"/>
		<img id="remove" title="remove" style="display:block; float:left;cursor:pointer" src="/images/icons/remove.gif"/>
		<img id="cut" title="cut folder" style="display:block; float:left;cursor:pointer" src="/images/icons/cut.gif"/>
		<img id="copy" title="copy folder" style="display:block; float:left;cursor:pointer" src="/images/icons/copy.gif"/>
		<img id="paste" title="paste folder" style="display:block; float:left;cursor:pointer" src="/images/icons/paste.gif"/>
		<img id="clear_search" title="clear" style="display:block; float:right;cursor:pointer" src="/images/icons/clear.gif"/>
		<img id="search" title="search" style="display:block; float:right;cursor:pointer" src="/images/icons/find.gif"/>
		<input type="text" id="text" value="" style="display:block; float:right;" size=8/>
		<span style="display:block; float:right;">Search&nbsp;&nbsp;</span>
</div>
</div>
<div id="demo" class="demo" style="padding-left: 25px;"></div>

<div id="leftPan">
<h2 name="s1"><b>System Management</b></h2>
<ul name="s1">
	<li><a href="/security/userlist">User List</a></li>
	<li><a href="/security/userinput">Add User</a></li>
	<li><a href="/security/rolelist">Role List</a></li>
	<li><a href="/security/roleinput">Add Role</a></li>
	<li><a href="/security/authoritylist">Authority List</a></li>
	<li><a href="/security/authorityinput">Add Authority</a></li>
	<li><a href="/dictionary/list">Dictionary List</a></li>
	<li><a href="/dictionary/input">Add Dictionary</a></li>
	<li><a href="/student/list">Demo</a></li>
</ul>
</div>
</div>

</body>
</html>

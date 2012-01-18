<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录页</title>
<style type="text/css">

.nbox  {
margin:0 0 10px;
overflow:hidden;
text-align:center;
width:250px;
}


.side_rbox {
background:url("images/side_rbox_gray.gif") repeat-y scroll -230px 0 transparent;
}
.nbox_s {
float:left;
width:230px;
}
* {
word-wrap:break-word;
}
body, th, td, input, select, textarea, button {
font:12px/1.5em Verdana,"Lucida Grande",Arial,Helvetica,sans-serif;
}

.side_rbox .ntitle {
background:url("images/side_rbox_gray.gif") no-repeat scroll 0 0 transparent;
height:34px;
line-height:34px;
}
.ntitle {
color:#333333;
font-size:12px;
height:32px;
line-height:32px;
padding:0 10px;
}
h1, h2, h3, h4, h5, h6 {
font-size:1em;
}
body, h1, h2, h3, h4, h5, h6, p, ul, dl, dt, dd, form, fieldset {
margin:0;
padding:0;
}
.side_rbox_c  {
background:url("images/side_rbox_gray.gif") no-repeat scroll -460px 100% transparent;
}
#nlogin_box p {
padding:6px 0;
}

#nlogin_box .t_input {
width:150px;
}
#nlogin_box p.checkrow, .nlogin_box p.submitrow {
padding:3px 0 3px 3.5em;
}
#nlogin_box p.submitrow {
line-height:26px;
padding:10px 0 5px 3.5em;
}

#nlogin_box #loginsubmit {
background:url("images/button_n.gif") no-repeat scroll 0 0 transparent;
color:#222222;
float:left;
height:26px;
line-height:26px;
margin-right:10px;
width:61px;
}
.submit {
background:none repeat scroll 0 0 #3B5998;
border:medium none;
color:#FFFFFF;
cursor:pointer;
height:24px;
letter-spacing:1px;
line-height:20px;
padding:0 5px;
}
body{
 text-align:center;
}
</style>

<script type="text/javascript" src="/scripts/jquery-1.4.min.js"></script>
<script src="/scripts/validate/jquery.validate.js"
	type="text/javascript"></script>
<script src="${base}/scripts/validate/messages_cn.js"
	type="text/javascript"></script>
<script>
  			$(document).ready(function(){
    			$("#verifypic").click(function(){
					var dt = Math.random();
  					$('#verifypic').html("<img border=\"0\" width=\"90\" src=\"/code.jpg?"+dt+"\" />"); 
				});
 			 });
  		</script>
</head>
<body>
<center>
<br /><br /><br /><br /><br /><br />
<%
	if (request.getParameter("error") != null
			&& request.getParameter("error").equals("1")) {
%>
<span style="color: red"> Login failed, please try again! </span>
<%
	} else if (request.getParameter("error") != null
			&& request.getParameter("error").equals("3")) {
%>
<span style="color: red"> System found that you log in somewhere else! </span>
<%
	} else if (request.getParameter("error") != null
			&& request.getParameter("error").equals("5")) {
%>
<span style="color: red"> Verification code you entered is incorrect! </span>
<%
	} else if (request.getParameter("error") != null
			&& request.getParameter("error").equals("7")) {
%>
<span style="color: red"> Session timeout, please re-visit! </span>
<%
	}
%>
<form id="loginForm" action="${base}/j_spring_security_check"
	method="post">



<div class="nbox" id="guestbar">



<div class="nbox_s side_rbox" id="nlogin_box">
<h3 class="ntitle">Login</h3>
<div class="side_rbox_c">
<p><label for="username">Username</label> <input type="text" name="j_username" id="username" style="width:150px;border:1px solid #DDDDDD;line-height:16px;padding:3px 2px;" class="required" value="" /></p>
<p><label for="password">Password</label> <input type="password" name="j_password" id="password" style="width:150px;border:1px solid #DDDDDD;line-height:16px;padding:3px 2px;" class="required" value="" /></p>
<p><label for="password">Verification</label> <input type="text" name="j_code" id="j_code" style="width:50px;border:1px solid #DDDDDD;line-height:16px;padding:3px 2px;" class="required" value="" />&nbsp;&nbsp;<span id="verifypic" style="cursor:pointer" title="Click the verification code to refresh it."><img border="0" width="90" src="${base}/code.jpg" /></span></p>
<input type="checkbox" id="cookietime" name="_spring_security_remember_me"/>
<label for="cookietime">Remember me(in 3 days)</label>
<p class="submitrow">

<input type="submit" id="loginsubmit" name="loginsubmit" value="Login" class="submit" />

&nbsp;

</p>
</div>
</div>
</div>
	
	
</form>
</center>
</body>
</html>


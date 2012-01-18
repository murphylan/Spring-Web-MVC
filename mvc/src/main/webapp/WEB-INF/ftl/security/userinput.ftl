<#import "/spring.ftl" as spring />
<#import "../baseftl/page.ftl" as comm />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="/styles/common.css" type="text/css" />
<title>User Management</title>


<script type="text/javascript">

$(document).ready(function(){

$('#loginName').blur(function() {
              var loginName=$('#loginName').val()
				if (loginName) {	
					checkAvailability(loginName);
				}
			});
function checkAvailability(loginName) {
			$.getJSON("${request.getContextPath()}/security/availability", { name: loginName}, function(availability) {
				if (availability.available) {
					fieldValidated("loginName", { valid : true });
				} else {
					fieldValidated("loginName", { valid : false, message : loginName + " is not available, try " + availability.suggestions });
				}
			});
		}

function fieldValidated(field, result) {
			if (result.valid) {
				$("#" + field + "\\.errors").remove();
			} else {
				if ($("#" + field + "\\.errors").length == 0) {
					$("#" + field).after("<span id='" + field + ".errors' style='color:red'>" + result.message + "</span>");		
				} else {
					$("#" + field + "\\.errors").html("<span id='" + field + ".errors' style='color:red'>" + result.message + "</span>");		
				}
			}			
		}
});
</script>


</head>
<body>
<div id="sub_info" align="left">
<img src="/images/system/hi.gif"/>
<span id="show_text">System Management <img broder="0" src="/images/system/slide.gif"/> Add User</span>
</div>
<form action="${request.getContextPath()}/security/userinput" method="POST">
<@spring.formHiddenInput "user.id"/>
<@spring.formHiddenInput "user.version"/>
<div id="man_zone">
  <table width="99%" border="0" align="center"  cellpadding="3" cellspacing="1" class="table_style">
    <tr>
	    <th align="center" colspan=2><b>Add/Edit User</b></th>
	   </tr>
    <tr>
      <td width="18%" class="left_title_1"><span class="left-title">User Account</span></td>
      <td width="82%"><@spring.formInput "user.loginName" />&nbsp;<span style="color:red"><@spring.showErrors "<br>"/>${loginNameError?default("")}</span>&nbsp;</td>
    </tr>
     <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Nickname</span></td>
      <td width="82%"><@spring.formInput "user.name"/>&nbsp;<span style="color:red"><@spring.showErrors "<br>"/></span>&nbsp;</td>
    </tr>
    <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Password</span></td>
      <td width="82%">
      <input type="password" name="password" value="${user.password?default('')}"/>&nbsp;
      &nbsp;&nbsp;<span style="color:red"><b>${pwdError?default('')}</b></span>&nbsp;
      </td>
    </tr>
     <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Email</span></td>
      <td width="82%"><@spring.formInput "user.email"/>&nbsp;<span style="color:red"><@spring.showErrors "<br>"/></span>&nbsp;</td>
    </tr>
    <tr>
      <td class="left_title_1">Roles</td>
      <td>
      <div style="word-break:break-all;width:80%; overflow:auto; ">
	    <#list roleMap?keys as value>
	    <#assign id="${value_index}">
	    <#assign isSelected = spring.contains(ids?default([""]), value)>
	    <input type="checkbox" id="roleList${id}" name="ids" value="${value?html}"
	    <#if isSelected>checked="checked"</#if> />
	    <label for="${id}">${roleMap[value]?html}</label>
	    </#list>
	    &nbsp;&nbsp;<span style="color:red"><b>${roleError?default('')}</b></span>
      </div>
      </td>
    </tr>
    <tr>
      <td class="left_title_1">Save</td>
      <td><input type="submit" value="Save"/>&nbsp;&nbsp;
      <input type="reset" value="Reset" id="reset"/>
      </td>
    </tr>
  </table>
</div>
</form>
</body>
</html>
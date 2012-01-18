<#import "/spring.ftl" as spring />
<#import "../baseftl/page.ftl" as comm />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="/styles/common.css" type="text/css" />
<script language="javascript">
$(document).ready(function(){

    $('#name').blur(function() {
              var name=$('#name').val()
				if (name) {	
					checkAvailability(name);
				}
			});
function checkAvailability(name) {
			$.getJSON("${request.getContextPath()}/security/role/availability", { name: name}, function(availability) {
				if (availability.available) {
					fieldValidated("name", { valid : true });
				} else {
					fieldValidated("name", { valid : false, message : name + " is not available, try " + availability.suggestions });
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



    $("#all").click(function(){
		$(":checkbox").attr("checked","true");
		return false;
	});
	$("#invert").click(function(){
		$(":checkbox").each(function(){
		    $(this).attr("checked", !$(this).attr("checked"));
		});
		return false;
	});
});
</script>
<title>Role management</title>
</head>

<body>
<div id="sub_info" align="left">
<img src="/images/system/hi.gif"/>
<span id="show_text">System Management <img broder="0" src="/images/system/slide.gif"/> Add Role</span>
</div>
<form action="${request.getContextPath()}/security/roleinput" method="POST" id="myform">
<@spring.formHiddenInput "role.id"/>
<@spring.formHiddenInput "role.version"/>
<div id="man_zone">
  <table width="99%" border="0" align="center"  cellpadding="3" cellspacing="1" class="table_style">
    <tr>
	    <th align="center" colspan=2><b>Add/Edit Role</b></th>
	   </tr>
    <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Role Name</span></td>
      <td width="82%"><@spring.formInput "role.name"/>&nbsp;<span style="color:red"><b><@spring.showErrors "<br>"/></b></span>&nbsp;</td>
    </tr>
    <tr>
      <td class="left_title_1">Authority</td>
      <td>
      <div style="word-break:break-all;width:80%; overflow:auto; ">
      <#list authorityMap?keys as value>
	    <#assign id="${value_index}">
	    <#assign isSelected = spring.contains(ids?default([""]), value)>
	    <div style="float:left; width:230px""> 
	    <input type="checkbox" id="authorityList${id}" name="ids" value="${value?html}"
	    <#if isSelected>checked="checked"</#if> />
	    <label for="${id}">${authorityMap[value]?html}</label>
	    </div>
	  </#list>
      </div>
      <span style="color:red"><b>${idsError?default("")}</b></span>
      </td>
    </tr>
    <tr>
      <td colspan=2>
      <div>
        <span>Choose：</span>&nbsp;
        <a id="all" href="">All</a>&nbsp;-&nbsp;
	    <a href="" id="invert">Invert</a>
      </div>
      </td>
    </tr>
    <tr>
      <td colspan=2 align="center"><input type="submit" value="Save"/>&nbsp;&nbsp;
      <input type="reset" value="Reset" id="reset"/>
      </td>
    </tr>
  </table>
</div>
</form>
</body>
</html>
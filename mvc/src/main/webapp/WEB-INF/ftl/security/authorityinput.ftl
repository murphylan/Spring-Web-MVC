<#import "/spring.ftl" as spring />
<#import "../baseftl/page.ftl" as comm />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="/styles/common.css" type="text/css" />
<title>Authority management</title>
</head>

<body>
<div id="sub_info" align="left">
<img src="/images/system/hi.gif"/>
<span id="show_text">System Management <img broder="0" src="/images/system/slide.gif"/> Add Authority</span>
</div>
<form action="${request.getContextPath()}/security/authorityinput" method="POST">
<@spring.formHiddenInput "authority.id"/>
<@spring.formHiddenInput "authority.version"/>
<div id="man_zone">
  <table width="99%" border="0" align="center"  cellpadding="3" cellspacing="1" class="table_style">
    <tr>
	    <th align="center" colspan=2><b>Add/Edit Authority</b></th>
	   </tr>
    <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Name</span></td>
      <td width="82%"><@spring.formInput "authority.name"/>&nbsp;<@spring.showErrors "<br>"/>&nbsp;</td>
    </tr>
    <tr>
      <td class="left_title_1">Description</td>
      <td><@spring.formInput path="authority.remark" attributes="size=100px"/>&nbsp;</td>
    </tr>
    <tr>
      <td colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="submit" value="Save"/>&nbsp;&nbsp;
      <input type="reset" value="Reset"/>
      </td>
    </tr>
  </table>
</div>
</form>
</body>
</html>
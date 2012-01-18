
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<#import "/spring.ftl" as spring />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="/styles/common.css" type="text/css" />
<script type="text/javascript" src="/scripts/json.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.datePicker.min-2.1.2.js"></script>
<script type="text/javascript" src="/scripts/date.js"></script>
<script type="text/javascript" src="/scripts/kindeditor/kindeditor.js"></script>


<link rel="stylesheet" type="text/css" href="/scripts/tree/!style.css" />
<script type="text/javascript" src="/scripts/tree/jquery.cookie.js"></script>
<script type="text/javascript" src="/scripts/tree/jquery.hotkeys.js"></script>
<script type="text/javascript" src="/scripts/tree/jquery.jstree.js"></script>
<title>Publish Article</title>

<script type="text/javascript">
			KE.show({
			id : 'content',
			imageUploadJson : '/article/upload',
			allowFileManager : true,
			afterCreate : function(id) {
				KE.event.ctrl(document, 13, function() {
					KE.util.setData(id);
					document.forms['example'].submit();
				});
				KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
					KE.util.setData(id);
					document.forms['example'].submit();
				});
			}
		});

		function addAttachment(){
		  
         	$("#mytable").append("<tr ><td class='left_title_1'>&nbsp;</td><td><input type='file' name='file' /><a href='' name='delTr'>删除</a></td></tr>");	
		}
		
		$(function(){
			$("a[name='delTr']").live("click", function(){
			 $(this).parent().parent().remove();
			 return false;
			});
	
	
	
	$.ajaxSetup({cache:false});
	$("#tree")
		.jstree({ 
			"plugins" : [ "themes", "json_data", "ui", "crrm", "cookies", "dnd", "search", "types", "hotkeys" ],
			"json_data" : { 
				"ajax" : {
					"url" : "/tree/get",
					"data" : function (n) { 
						return { 
							"operation" : "get_folder", 
							"id" : n.attr ? n.attr("id").replace("node_","") : "-1"
						}; 
					}
				}
			},
			"types" : {
				"max_depth" : -2,
				"max_children" : -2,
				"valid_children" : [ "drive" ],
				"types" : {
					"default" : {
						"valid_children" : "none",
						"icon" : {
						
							"image" : "/images/tree/file.png"
						}
					},
					"folder" : {
						"valid_children" : [ "default", "folder" ],
						"icon" : {
							"image" : "/images/tree/folder.png"
						}
					},
					"drive" : {
						"valid_children" : [ "default", "folder" ],
						"icon" : {
							"image" : "/images/tree/root.png"
						},
						"start_drag" : false,
						"move_node" : false,
						"delete_node" : false,
						"remove" : false
						
					}
				}
			},
				"ui" : {
				"initially_select" : [ "node_1" ]
			},
			"core" : { 
				"initially_open" : [ "node_-1"] 
			}
		})
		
		 .bind("select_node.jstree", function(e, data) { 
          var id=data.rslt.obj.attr("id").replace("node_","");
           var type=data.rslt.obj.attr("rel");
           if(type=="drive"){
             alert("Can't select the root node!");
             $("#node").val("");
             $("#treeId").val("");
            return;
           }
          $("#node").val($.trim(data.rslt.obj.children("a").text()));
          $("#treeId").val(id);
       })  
		
		});
		
		
	</script>
		
</head>

<body>
<div id="sub_info" align="left">
<img src="/images/system/hi.gif"/>
<span id="show_text">My Space <img broder="0" src="/images/system/slide.gif"/> Add/Edit Article</span>
</div>
<div>
<form id="myform" name="myform" action="${request.getContextPath()}/article/create" method="POST" enctype="multipart/form-data">
<@spring.formHiddenInput "article.id"/>
<@spring.formHiddenInput "article.version"/>

<div id="man_zone">

	<table  id="mytable" width="99%" border="0" align="center"  cellpadding="3" cellspacing="1" class="table_style">

	   <tr>
	    <th align="center" colspan='2'><b>Add/Edit Article</b></th>
	   </tr>
    <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Title</span></td>
      <td width="82%"><@spring.formInput "article.title" "size=60"/>&nbsp;<span style="color:red"><@spring.showErrors "<br>"/></span></td>
    </tr>
   
    
      <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Author</span></td>
      <td width="82%"><@spring.formInput "article.author"/>&nbsp;<span style="color:red"><@spring.showErrors "<br>"/></span></td>
    </tr>

   
  <tr>
    <td width="18%" class="left_title_1"><span class="left-title">Node</span></td>
    <td width="82%">
	 <@spring.formHiddenInput path="article.treeId" /> 
	 <input type="text" id="node" value="${nodeName?default('')}"  readonly/>
	 &nbsp;<span style="color:red"><@spring.showErrors "<br>"/></span>
	 </td>
	</tr>

	<tr>
	  <td></td>
	  <td>
         <div id="tree" class="demo" style="width:100%;height:100%"></div>
	  </td>
	</tr>
    
     <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Content</span></td>
      <td width="82%" >
      <input type="textarea" name="content" value="${article.content?default('')?html}" style='width:700px;height:300px;visibility:hidden;'>
      &nbsp;<span style="color:red"><@spring.showErrors "<br>"/></span>
      </td>
    </tr>
    
    <#list article.attachmentList as list>
     <tr>
      <td width="18%" class="left_title_1"><span class="left-title"></span></td>
      <td width="82%">
        <a href="${request.getContextPath()}/article/download/${list.id?default("")}">${list.name?default("")}(${list.fileSize?default("")})</a> &nbsp;&nbsp;<a href="${request.getContextPath()}/article/delattachement/${list.id?default("")}">删除</a> 
      </td>
    </tr>
    </#list>   
    
     <tr>
      <td width="18%" class="left_title_1"><span class="left-title">Attachment</span></td>
      <td width="82%" >
       <input type="file" name="file" />  <a href="javascript:addAttachment();" >Add</a>
      </td>      
    </tr>
  </table>
  
  <table width="99%" border="0" align="center"  cellpadding="3" cellspacing="1" class="table_style">
   <tr>
      <td width="100%" align="center">
        <input type="submit" value="Add" id="create"/>&nbsp;&nbsp;&nbsp;
        <input type="reset" value="Reset" id="reset"/>&nbsp;
      </td>
    </tr>
 </table>
</div>
</form>
</div>
</body>
</html>

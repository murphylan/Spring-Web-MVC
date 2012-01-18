   $(function () {
	$.ajaxSetup({cache:false});
	
	$("#styling tr").live("mouseover",function(){ 
    	$(this).addClass("hover");    //鼠标经过添加hover样式 
 	});
 	
 	$("#styling tr").live("mouseout",function(){ 
    	$(this).removeClass("hover");   //鼠标离开移除hover样式
    });
 	
	$("#demo")
		.jstree({ 
			"plugins" : [ "themes", "json_data", "ui", "crrm", "cookies", "dnd", "search", "types", "hotkeys", "contextmenu" ],
			"json_data" : { 
				"ajax" : {
					"url" : "/tree/get",
					"data" : function (n) { 
						return { 
							"operation" : "get_children", 
							"id" : n.attr ? n.attr("id").replace("node_","") : "-1"
						}; 
					}
				}
			},
			"search" : {
				"ajax" : {
					"url" : "/tree/search",
					"data" : function (str) {
						return { 
							"operation" : "search", 
							"search_str" : str 
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
			}

		})
		.bind("create.jstree", function (e, data) {
			$.post(
				"/tree/add", 
				{ 
					"operation" : "create_node", 
					"parentId" : data.rslt.parent.attr("id").replace("node_",""), 
					"position" : data.rslt.position,
					"title" : data.rslt.name,
					"type" : data.rslt.obj.attr("rel")
				}, 
				function (r) {
					if(r) {
						$(data.rslt.obj).attr("id", "node_" + r);
					}
					else {
						$.jstree.rollback(data.rlbk);
					}
				}
			);
		})
		.bind("remove.jstree", function (e, data) {
			if(!window.confirm("Are you sure to delete chosen article?\n\nPlease confirm!")){
				data.inst.refresh();
				return;
			}
			data.rslt.obj.each(function () {
				$.ajax({
					async : false,
					type: 'POST',
					url: "/tree/del",
					data : { 
						"operation" : "remove_node", 
						"id" : this.id.replace("node_","")
						
					}, 
					success : function (r) {
						if(!r) {
							data.inst.refresh();
						}
					}
				});
			});
		})
		.bind("rename.jstree", function (e, data) {
			var rel=data.rslt.obj.attr("rel");
			if(rel=="file"){
				alert("Can't change the name of file!");
				data.inst.refresh();
				return;
			}
			$.post(
				"/tree/add", 
				{ 
					"operation" : "rename_node", 
					"id" : data.rslt.obj.attr("id").replace("node_",""),
					"title" : data.rslt.new_name
				}, 
				function (r) {
					if(!r) {
						$.jstree.rollback(data.rlbk);
					}
				}
			);
		})

		.delegate("a", "click", function (event, data) { 
			var id=$(this).parent().attr("id");
			var type=$(this).parent().attr("rel");
	        if(type=="drive"){
	          return;
	        }
	      
	        var url="/tree/see/"+id+"/"+type;
	        $.ajaxSetup ({ 
	        	cache: false 
	        	});
	        $("#centerPan").load(url);
		})
		
		.bind("move_node.jstree", function (e, data) {
			data.rslt.o.each(function (i) {
				$.ajax({
					async : false,
					type: 'POST',
					url: "/tree/move",
					data : { 
						"operation" : "move_node", 
						"id" : $(this).attr("id").replace("node_",""), 
						"ref" : data.rslt.np.attr("id").replace("node_",""), 
						"position" : data.rslt.cp + i,
						"title" : data.rslt.name,
						"copy" : data.rslt.cy ? 1 : 0
					},
					success : function (r) {
						if(!r) {
							$.jstree.rollback(data.rlbk);
						}
						else {
						  
							$(data.rslt.oc).attr("id", "node_" + r);
							if(data.rslt.cy && $(data.rslt.oc).children("UL").length) {
								data.inst.refresh(data.inst._get_parent(data.rslt.oc));
							}
						}
						$("#analyze").click();
					}
				});
			});
		});
		 

	
});
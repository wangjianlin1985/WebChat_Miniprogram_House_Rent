var minsu_manage_tool = null; 
$(function () { 
	initMinsuManageTool(); //建立Minsu管理对象
	minsu_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#minsu_manage").datagrid({
		url : 'Minsu/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "minsuId",
		sortOrder : "desc",
		toolbar : "#minsu_manage_tool",
		columns : [[
			{
				field : "minsuId",
				title : "民宿id",
				width : 70,
			},
			{
				field : "areaObj",
				title : "所在地区",
				width : 140,
			},
			{
				field : "minsuName",
				title : "民宿名称",
				width : 140,
			},
			{
				field : "minsuPhoto",
				title : "民宿照片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "price",
				title : "每日价格",
				width : 70,
			},
			{
				field : "ownerObj",
				title : "民宿主人",
				width : 140,
			},
			{
				field : "addTime",
				title : "发布时间",
				width : 140,
			},
		]],
	});

	$("#minsuEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#minsuEditForm").form("validate")) {
					//验证表单 
					if(!$("#minsuEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#minsuEditForm").form({
						    url:"Minsu/" + $("#minsu_minsuId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#minsuEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#minsuEditDiv").dialog("close");
			                        minsu_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#minsuEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#minsuEditDiv").dialog("close");
				$("#minsuEditForm").form("reset"); 
			},
		}],
	});
});

function initMinsuManageTool() {
	minsu_manage_tool = {
		init: function() {
			$.ajax({
				url : "Area/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#areaObj_areaId_query").combobox({ 
					    valueField:"areaId",
					    textField:"areanName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{areaId:0,areanName:"不限制"});
					$("#areaObj_areaId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "Owner/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#ownerObj_ownerUserName_query").combobox({ 
					    valueField:"ownerUserName",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{ownerUserName:"",name:"不限制"});
					$("#ownerObj_ownerUserName_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#minsu_manage").datagrid("reload");
		},
		redo : function () {
			$("#minsu_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#minsu_manage").datagrid("options").queryParams;
			queryParams["ownerObj.ownerUserName"] = $("#ownerObj_ownerUserName_query").combobox("getValue");
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			queryParams["areaObj.areaId"] = $("#areaObj_areaId_query").combobox("getValue");
			queryParams["minsuName"] = $("#minsuName").val();
			$("#minsu_manage").datagrid("options").queryParams=queryParams; 
			$("#minsu_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#minsuQueryForm").form({
			    url:"Minsu/OutToExcel",
			});
			//提交表单
			$("#minsuQueryForm").submit();
		},
		remove : function () {
			var rows = $("#minsu_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var minsuIds = [];
						for (var i = 0; i < rows.length; i ++) {
							minsuIds.push(rows[i].minsuId);
						}
						$.ajax({
							type : "POST",
							url : "Minsu/deletes",
							data : {
								minsuIds : minsuIds.join(","),
							},
							beforeSend : function () {
								$("#minsu_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#minsu_manage").datagrid("loaded");
									$("#minsu_manage").datagrid("load");
									$("#minsu_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#minsu_manage").datagrid("loaded");
									$("#minsu_manage").datagrid("load");
									$("#minsu_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#minsu_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Minsu/" + rows[0].minsuId +  "/update",
					type : "get",
					data : {
						//minsuId : rows[0].minsuId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (minsu, response, status) {
						$.messager.progress("close");
						if (minsu) { 
							$("#minsuEditDiv").dialog("open");
							$("#minsu_minsuId_edit").val(minsu.minsuId);
							$("#minsu_minsuId_edit").validatebox({
								required : true,
								missingMessage : "请输入民宿id",
								editable: false
							});
							$("#minsu_areaObj_areaId_edit").combobox({
								url:"Area/listAll",
							    valueField:"areaId",
							    textField:"areanName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#minsu_areaObj_areaId_edit").combobox("select", minsu.areaObjPri);
									//var data = $("#minsu_areaObj_areaId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#minsu_areaObj_areaId_edit").combobox("select", data[0].areaId);
						            //}
								}
							});
							$("#minsu_minsuName_edit").val(minsu.minsuName);
							$("#minsu_minsuName_edit").validatebox({
								required : true,
								missingMessage : "请输入民宿名称",
							});
							$("#minsu_minsuPhoto").val(minsu.minsuPhoto);
							$("#minsu_minsuPhotoImg").attr("src", minsu.minsuPhoto);
							$("#minsu_price_edit").val(minsu.price);
							$("#minsu_price_edit").validatebox({
								required : true,
								validType : "number",
								missingMessage : "请输入每日价格",
								invalidMessage : "每日价格输入不对",
							});
							$("#minsu_minsuDesc_edit").val(minsu.minsuDesc);
							$("#minsu_minsuDesc_edit").validatebox({
								required : true,
								missingMessage : "请输入民宿介绍",
							});
							$("#minsu_minsuMemo_edit").val(minsu.minsuMemo);
							$("#minsu_ownerObj_ownerUserName_edit").combobox({
								url:"Owner/listAll",
							    valueField:"ownerUserName",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#minsu_ownerObj_ownerUserName_edit").combobox("select", minsu.ownerObjPri);
									//var data = $("#minsu_ownerObj_ownerUserName_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#minsu_ownerObj_ownerUserName_edit").combobox("select", data[0].ownerUserName);
						            //}
								}
							});
							$("#minsu_addTime_edit").datetimebox({
								value: minsu.addTime,
							    required: true,
							    showSeconds: true,
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}

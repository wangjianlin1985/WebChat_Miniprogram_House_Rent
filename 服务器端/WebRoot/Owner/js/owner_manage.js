var owner_manage_tool = null; 
$(function () { 
	initOwnerManageTool(); //建立Owner管理对象
	owner_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#owner_manage").datagrid({
		url : 'Owner/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "ownerUserName",
		sortOrder : "desc",
		toolbar : "#owner_manage_tool",
		columns : [[
			{
				field : "ownerUserName",
				title : "宿主用户名",
				width : 140,
			},
			{
				field : "name",
				title : "宿主姓名",
				width : 140,
			},
			{
				field : "gender",
				title : "性别",
				width : 140,
			},
			{
				field : "birthDate",
				title : "出生日期",
				width : 140,
			},
			{
				field : "ownerPhoto",
				title : "宿主照片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "telephone",
				title : "联系电话",
				width : 140,
			},
			{
				field : "regTime",
				title : "注册时间",
				width : 140,
			},
			{
				field : "shzt",
				title : "审核状态",
				width : 140,
			},
		]],
	});

	$("#ownerEditDiv").dialog({
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
				if ($("#ownerEditForm").form("validate")) {
					//验证表单 
					if(!$("#ownerEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#ownerEditForm").form({
						    url:"Owner/" + $("#owner_ownerUserName_edit").val() + "/update",
						    onSubmit: function(){
								if($("#ownerEditForm").form("validate"))  {
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
			                        $("#ownerEditDiv").dialog("close");
			                        owner_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#ownerEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#ownerEditDiv").dialog("close");
				$("#ownerEditForm").form("reset"); 
			},
		}],
	});
});

function initOwnerManageTool() {
	owner_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#owner_manage").datagrid("reload");
		},
		redo : function () {
			$("#owner_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#owner_manage").datagrid("options").queryParams;
			queryParams["ownerUserName"] = $("#ownerUserName").val();
			queryParams["name"] = $("#name").val();
			queryParams["birthDate"] = $("#birthDate").datebox("getValue"); 
			queryParams["telephone"] = $("#telephone").val();
			queryParams["shzt"] = $("#shzt").val();
			$("#owner_manage").datagrid("options").queryParams=queryParams; 
			$("#owner_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#ownerQueryForm").form({
			    url:"Owner/OutToExcel",
			});
			//提交表单
			$("#ownerQueryForm").submit();
		},
		remove : function () {
			var rows = $("#owner_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var ownerUserNames = [];
						for (var i = 0; i < rows.length; i ++) {
							ownerUserNames.push(rows[i].ownerUserName);
						}
						$.ajax({
							type : "POST",
							url : "Owner/deletes",
							data : {
								ownerUserNames : ownerUserNames.join(","),
							},
							beforeSend : function () {
								$("#owner_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#owner_manage").datagrid("loaded");
									$("#owner_manage").datagrid("load");
									$("#owner_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#owner_manage").datagrid("loaded");
									$("#owner_manage").datagrid("load");
									$("#owner_manage").datagrid("unselectAll");
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
			var rows = $("#owner_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Owner/" + rows[0].ownerUserName +  "/update",
					type : "get",
					data : {
						//ownerUserName : rows[0].ownerUserName,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (owner, response, status) {
						$.messager.progress("close");
						if (owner) { 
							$("#ownerEditDiv").dialog("open");
							$("#owner_ownerUserName_edit").val(owner.ownerUserName);
							$("#owner_ownerUserName_edit").validatebox({
								required : true,
								missingMessage : "请输入宿主用户名",
								editable: false
							});
							$("#owner_password_edit").val(owner.password);
							$("#owner_password_edit").validatebox({
								required : true,
								missingMessage : "请输入登录密码",
							});
							$("#owner_name_edit").val(owner.name);
							$("#owner_name_edit").validatebox({
								required : true,
								missingMessage : "请输入宿主姓名",
							});
							$("#owner_gender_edit").val(owner.gender);
							$("#owner_gender_edit").validatebox({
								required : true,
								missingMessage : "请输入性别",
							});
							$("#owner_birthDate_edit").datebox({
								value: owner.birthDate,
							    required: true,
							    showSeconds: true,
							});
							$("#owner_ownerPhoto").val(owner.ownerPhoto);
							$("#owner_ownerPhotoImg").attr("src", owner.ownerPhoto);
							$("#owner_telephone_edit").val(owner.telephone);
							$("#owner_telephone_edit").validatebox({
								required : true,
								missingMessage : "请输入联系电话",
							});
							$("#owner_address_edit").val(owner.address);
							$("#owner_regTime_edit").datetimebox({
								value: owner.regTime,
							    required: true,
							    showSeconds: true,
							});
							$("#owner_shzt_edit").val(owner.shzt);
							$("#owner_shzt_edit").validatebox({
								required : true,
								missingMessage : "请输入审核状态",
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

$(function () {
	$("#owner_ownerUserName").validatebox({
		required : true, 
		missingMessage : '请输入宿主用户名',
	});

	$("#owner_password").validatebox({
		required : true, 
		missingMessage : '请输入登录密码',
	});

	$("#owner_name").validatebox({
		required : true, 
		missingMessage : '请输入宿主姓名',
	});

	$("#owner_gender").validatebox({
		required : true, 
		missingMessage : '请输入性别',
	});

	$("#owner_birthDate").datebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#owner_telephone").validatebox({
		required : true, 
		missingMessage : '请输入联系电话',
	});

	$("#owner_regTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#owner_shzt").validatebox({
		required : true, 
		missingMessage : '请输入审核状态',
	});

	//单击添加按钮
	$("#ownerAddButton").click(function () {
		//验证表单 
		if(!$("#ownerAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#ownerAddForm").form({
			    url:"Owner/add",
			    onSubmit: function(){
					if($("#ownerAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#ownerAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#ownerAddForm").submit();
		}
	});

	//单击清空按钮
	$("#ownerClearButton").click(function () { 
		$("#ownerAddForm").form("clear"); 
	});
});

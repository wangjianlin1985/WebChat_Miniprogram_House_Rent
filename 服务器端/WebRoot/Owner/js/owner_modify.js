$(function () {
	$.ajax({
		url : "Owner/" + $("#owner_ownerUserName_edit").val() + "/update",
		type : "get",
		data : {
			//ownerUserName : $("#owner_ownerUserName_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (owner, response, status) {
			$.messager.progress("close");
			if (owner) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#ownerModifyButton").click(function(){ 
		if ($("#ownerEditForm").form("validate")) {
			$("#ownerEditForm").form({
			    url:"Owner/" +  $("#owner_ownerUserName_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#ownerEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});

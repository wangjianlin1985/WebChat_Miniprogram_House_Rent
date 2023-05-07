$(function () {
	$.ajax({
		url : "Minsu/" + $("#minsu_minsuId_edit").val() + "/update",
		type : "get",
		data : {
			//minsuId : $("#minsu_minsuId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (minsu, response, status) {
			$.messager.progress("close");
			if (minsu) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#minsuModifyButton").click(function(){ 
		if ($("#minsuEditForm").form("validate")) {
			$("#minsuEditForm").form({
			    url:"Minsu/" +  $("#minsu_minsuId_edit").val() + "/update",
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
			$("#minsuEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});

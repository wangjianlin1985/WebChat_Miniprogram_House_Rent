$(function () {
	$("#minsu_areaObj_areaId").combobox({
	    url:'Area/listAll',
	    valueField: "areaId",
	    textField: "areanName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#minsu_areaObj_areaId").combobox("getData"); 
            if (data.length > 0) {
                $("#minsu_areaObj_areaId").combobox("select", data[0].areaId);
            }
        }
	});
	$("#minsu_minsuName").validatebox({
		required : true, 
		missingMessage : '请输入民宿名称',
	});

	$("#minsu_price").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入每日价格',
		invalidMessage : '每日价格输入不对',
	});

	$("#minsu_minsuDesc").validatebox({
		required : true, 
		missingMessage : '请输入民宿介绍',
	});

	$("#minsu_ownerObj_ownerUserName").combobox({
	    url:'Owner/listAll',
	    valueField: "ownerUserName",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#minsu_ownerObj_ownerUserName").combobox("getData"); 
            if (data.length > 0) {
                $("#minsu_ownerObj_ownerUserName").combobox("select", data[0].ownerUserName);
            }
        }
	});
	$("#minsu_addTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#minsuAddButton").click(function () {
		//验证表单 
		if(!$("#minsuAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#minsuAddForm").form({
			    url:"Minsu/add",
			    onSubmit: function(){
					if($("#minsuAddForm").form("validate"))  { 
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
                        $("#minsuAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#minsuAddForm").submit();
		}
	});

	//单击清空按钮
	$("#minsuClearButton").click(function () { 
		$("#minsuAddForm").form("clear"); 
	});
});

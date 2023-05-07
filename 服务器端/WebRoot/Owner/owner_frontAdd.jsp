<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>民宿主人添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-12 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Owner/frontlist">民宿主人管理</a></li>
  			<li class="active">注册民宿主人</li>
		</ul>
		<div class="row">
			<div class="col-md-10">
		      	<form class="form-horizontal" name="ownerAddForm" id="ownerAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
				  <div class="form-group">
					 <label for="owner_ownerUserName" class="col-md-2 text-right">宿主用户名:</label>
					 <div class="col-md-8"> 
					 	<input type="text" id="owner_ownerUserName" name="owner.ownerUserName" class="form-control" placeholder="请输入宿主用户名">
					 </div>
				  </div> 
				  <div class="form-group">
				  	 <label for="owner_password" class="col-md-2 text-right">登录密码:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="owner_password" name="owner.password" class="form-control" placeholder="请输入登录密码">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="owner_name" class="col-md-2 text-right">宿主姓名:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="owner_name" name="owner.name" class="form-control" placeholder="请输入宿主姓名">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="owner_gender" class="col-md-2 text-right">性别:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="owner_gender" name="owner.gender" class="form-control" placeholder="请输入性别">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="owner_birthDateDiv" class="col-md-2 text-right">出生日期:</label>
				  	 <div class="col-md-8">
		                <div id="owner_birthDateDiv" class="input-group date owner_birthDate col-md-12" data-link-field="owner_birthDate" data-link-format="yyyy-mm-dd">
		                    <input class="form-control" id="owner_birthDate" name="owner.birthDate" size="16" type="text" value="" placeholder="请选择出生日期" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                </div>
				  	 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="owner_ownerPhoto" class="col-md-2 text-right">宿主照片:</label>
				  	 <div class="col-md-8">
					    <img  class="img-responsive" id="owner_ownerPhotoImg" border="0px"/><br/>
					    <input type="hidden" id="owner_ownerPhoto" name="owner.ownerPhoto"/>
					    <input id="ownerPhotoFile" name="ownerPhotoFile" type="file" size="50" />
				  	 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="owner_telephone" class="col-md-2 text-right">联系电话:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="owner_telephone" name="owner.telephone" class="form-control" placeholder="请输入联系电话">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="owner_address" class="col-md-2 text-right">家庭地址:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="owner_address" name="owner.address" class="form-control" placeholder="请输入家庭地址">
					 </div>
				  </div>
				  <div class="form-group" style="display:none;">
				  	 <label for="owner_regTimeDiv" class="col-md-2 text-right">注册时间:</label>
				  	 <div class="col-md-8">
		                <div id="owner_regTimeDiv" class="input-group date owner_regTime col-md-12" data-link-field="owner_regTime">
		                    <input class="form-control" id="owner_regTime" name="owner.regTime" size="16" type="text" value="" placeholder="请选择注册时间" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                </div>
				  	 </div>
				  </div>
				  <div class="form-group" style="display:none;">
				  	 <label for="owner_shzt" class="col-md-2 text-right">审核状态:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="owner_shzt" name="owner.shzt" class="form-control" placeholder="请输入审核状态">
					 </div>
				  </div>
		          <div class="form-group">
		             <span class="col-md-2""></span>
		             <span onclick="ajaxOwnerAdd();" class="btn btn-primary bottom5 top5">注册</span>
		          </div> 
		          <style>#ownerAddForm .form-group {margin:5px;}  </style>  
				</form> 
			</div>
			<div class="col-md-2"></div> 
	    </div>
	</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加民宿主人信息
	function ajaxOwnerAdd() { 
		//提交之前先验证表单
		$("#ownerAddForm").data('bootstrapValidator').validate();
		if(!$("#ownerAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Owner/add",
			dataType : "json" , 
			data: new FormData($("#ownerAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#ownerAddForm").find("input").val("");
					$("#ownerAddForm").find("textarea").val("");
				} else {
					alert(obj.message);
				}
			},
			processData: false, 
			contentType: false, 
		});
	} 
$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse > a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();
	//验证民宿主人添加表单字段
	$('#ownerAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"owner.ownerUserName": {
				validators: {
					notEmpty: {
						message: "宿主用户名不能为空",
					}
				}
			},
			"owner.password": {
				validators: {
					notEmpty: {
						message: "登录密码不能为空",
					}
				}
			},
			"owner.name": {
				validators: {
					notEmpty: {
						message: "宿主姓名不能为空",
					}
				}
			},
			"owner.gender": {
				validators: {
					notEmpty: {
						message: "性别不能为空",
					}
				}
			},
			"owner.birthDate": {
				validators: {
					notEmpty: {
						message: "出生日期不能为空",
					}
				}
			},
			"owner.telephone": {
				validators: {
					notEmpty: {
						message: "联系电话不能为空",
					}
				}
			},
			/*
			"owner.regTime": {
				validators: {
					notEmpty: {
						message: "注册时间不能为空",
					}
				}
			},
			"owner.shzt": {
				validators: {
					notEmpty: {
						message: "审核状态不能为空",
					}
				}
			},*/
		}
	}); 
	//出生日期组件
	$('#owner_birthDateDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd',
		minView: 2,
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#ownerAddForm').data('bootstrapValidator').updateStatus('owner.birthDate', 'NOT_VALIDATED',null).validateField('owner.birthDate');
	});
	//注册时间组件
	$('#owner_regTimeDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#ownerAddForm').data('bootstrapValidator').updateStatus('owner.regTime', 'NOT_VALIDATED',null).validateField('owner.regTime');
	});
})
</script>
</body>
</html>

<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Owner" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Owner owner = (Owner)request.getAttribute("owner");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改民宿主人信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">民宿主人信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="ownerEditForm" id="ownerEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="owner_ownerUserName_edit" class="col-md-3 text-right">宿主用户名:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="owner_ownerUserName_edit" name="owner.ownerUserName" class="form-control" placeholder="请输入宿主用户名" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="owner_password_edit" class="col-md-3 text-right">登录密码:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="owner_password_edit" name="owner.password" class="form-control" placeholder="请输入登录密码">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="owner_name_edit" class="col-md-3 text-right">宿主姓名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="owner_name_edit" name="owner.name" class="form-control" placeholder="请输入宿主姓名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="owner_gender_edit" class="col-md-3 text-right">性别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="owner_gender_edit" name="owner.gender" class="form-control" placeholder="请输入性别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="owner_birthDate_edit" class="col-md-3 text-right">出生日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date owner_birthDate_edit col-md-12" data-link-field="owner_birthDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="owner_birthDate_edit" name="owner.birthDate" size="16" type="text" value="" placeholder="请选择出生日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="owner_ownerPhoto_edit" class="col-md-3 text-right">宿主照片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="owner_ownerPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="owner_ownerPhoto" name="owner.ownerPhoto"/>
			    <input id="ownerPhotoFile" name="ownerPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="owner_telephone_edit" class="col-md-3 text-right">联系电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="owner_telephone_edit" name="owner.telephone" class="form-control" placeholder="请输入联系电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="owner_address_edit" class="col-md-3 text-right">家庭地址:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="owner_address_edit" name="owner.address" class="form-control" placeholder="请输入家庭地址">
			 </div>
		  </div>
		  <div class="form-group" style="display:none;">
		  	 <label for="owner_regTime_edit" class="col-md-3 text-right">注册时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date owner_regTime_edit col-md-12" data-link-field="owner_regTime_edit">
                    <input class="form-control" id="owner_regTime_edit" name="owner.regTime" size="16" type="text" value="" placeholder="请选择注册时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group" style="display:none;">
		  	 <label for="owner_shzt_edit" class="col-md-3 text-right">审核状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="owner_shzt_edit" name="owner.shzt" class="form-control" placeholder="请输入审核状态">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxOwnerModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#ownerEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改民宿主人界面并初始化数据*/
function ownerEdit(ownerUserName) {
	$.ajax({
		url :  basePath + "Owner/" + ownerUserName + "/update",
		type : "get",
		dataType: "json",
		success : function (owner, response, status) {
			if (owner) {
				$("#owner_ownerUserName_edit").val(owner.ownerUserName);
				$("#owner_password_edit").val(owner.password);
				$("#owner_name_edit").val(owner.name);
				$("#owner_gender_edit").val(owner.gender);
				$("#owner_birthDate_edit").val(owner.birthDate);
				$("#owner_ownerPhoto").val(owner.ownerPhoto);
				$("#owner_ownerPhotoImg").attr("src", basePath +　owner.ownerPhoto);
				$("#owner_telephone_edit").val(owner.telephone);
				$("#owner_address_edit").val(owner.address);
				$("#owner_regTime_edit").val(owner.regTime);
				$("#owner_shzt_edit").val(owner.shzt);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交民宿主人信息表单给服务器端修改*/
function ajaxOwnerModify() {
	$.ajax({
		url :  basePath + "Owner/" + $("#owner_ownerUserName_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#ownerEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#ownerQueryForm").submit();
            }else{
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
    /*出生日期组件*/
    $('.owner_birthDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    /*注册时间组件*/
    $('.owner_regTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    ownerEdit("<%=session.getAttribute("user_name")%>");
 })
 </script> 
</body>
</html>


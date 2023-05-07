<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Owner" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Owner> ownerList = (List<Owner>)request.getAttribute("ownerList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String ownerUserName = (String)request.getAttribute("ownerUserName"); //宿主用户名查询关键字
    String name = (String)request.getAttribute("name"); //宿主姓名查询关键字
    String birthDate = (String)request.getAttribute("birthDate"); //出生日期查询关键字
    String telephone = (String)request.getAttribute("telephone"); //联系电话查询关键字
    String shzt = (String)request.getAttribute("shzt"); //审核状态查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>民宿主人查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Owner/frontlist">民宿主人信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Owner/owner_frontAdd.jsp" style="display:none;">添加民宿主人</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<ownerList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Owner owner = ownerList.get(i); //获取到民宿主人对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Owner/<%=owner.getOwnerUserName() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=owner.getOwnerPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		宿主用户名:<%=owner.getOwnerUserName() %>
			     	</div>
			     	<div class="field">
	            		宿主姓名:<%=owner.getName() %>
			     	</div>
			     	<div class="field">
	            		性别:<%=owner.getGender() %>
			     	</div>
			     	<div class="field">
	            		出生日期:<%=owner.getBirthDate() %>
			     	</div>
			     	<div class="field">
	            		联系电话:<%=owner.getTelephone() %>
			     	</div>
			     	<div class="field">
	            		注册时间:<%=owner.getRegTime() %>
			     	</div>
			     	<div class="field">
	            		审核状态:<%=owner.getShzt() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Owner/<%=owner.getOwnerUserName() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="ownerEdit('<%=owner.getOwnerUserName() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="ownerDelete('<%=owner.getOwnerUserName() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

			<div class="row">
				<div class="col-md-12">
					<nav class="pull-left">
						<ul class="pagination">
							<li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								int startPage = currentPage - 5;
								int endPage = currentPage + 5;
								if(startPage < 1) startPage=1;
								if(endPage > totalPage) endPage = totalPage;
								for(int i=startPage;i<=endPage;i++) {
							%>
							<li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
							<%  } %> 
							<li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>民宿主人查询</h1>
		</div>
		<form name="ownerQueryForm" id="ownerQueryForm" action="<%=basePath %>Owner/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="ownerUserName">宿主用户名:</label>
				<input type="text" id="ownerUserName" name="ownerUserName" value="<%=ownerUserName %>" class="form-control" placeholder="请输入宿主用户名">
			</div>
			<div class="form-group">
				<label for="name">宿主姓名:</label>
				<input type="text" id="name" name="name" value="<%=name %>" class="form-control" placeholder="请输入宿主姓名">
			</div>
			<div class="form-group">
				<label for="birthDate">出生日期:</label>
				<input type="text" id="birthDate" name="birthDate" class="form-control"  placeholder="请选择出生日期" value="<%=birthDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="telephone">联系电话:</label>
				<input type="text" id="telephone" name="telephone" value="<%=telephone %>" class="form-control" placeholder="请输入联系电话">
			</div>
			<div class="form-group">
				<label for="shzt">审核状态:</label>
				<input type="text" id="shzt" name="shzt" value="<%=shzt %>" class="form-control" placeholder="请输入审核状态">
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="ownerEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;民宿主人信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		  <div class="form-group">
		  	 <label for="owner_regTime_edit" class="col-md-3 text-right">注册时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date owner_regTime_edit col-md-12" data-link-field="owner_regTime_edit">
                    <input class="form-control" id="owner_regTime_edit" name="owner.regTime" size="16" type="text" value="" placeholder="请选择注册时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="owner_shzt_edit" class="col-md-3 text-right">审核状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="owner_shzt_edit" name="owner.shzt" class="form-control" placeholder="请输入审核状态">
			 </div>
		  </div>
		</form> 
	    <style>#ownerEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxOwnerModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.ownerQueryForm.currentPage.value = currentPage;
    document.ownerQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.ownerQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.ownerQueryForm.currentPage.value = pageValue;
    documentownerQueryForm.submit();
}

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
				$('#ownerEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除民宿主人信息*/
function ownerDelete(ownerUserName) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Owner/deletes",
			data : {
				ownerUserNames : ownerUserName,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#ownerQueryForm").submit();
					//location.href= basePath + "Owner/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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
})
</script>
</body>
</html>


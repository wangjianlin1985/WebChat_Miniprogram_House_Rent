<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Minsu" %>
<%@ page import="com.chengxusheji.po.Area" %>
<%@ page import="com.chengxusheji.po.Owner" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Minsu> minsuList = (List<Minsu>)request.getAttribute("minsuList");
    //获取所有的areaObj信息
    List<Area> areaList = (List<Area>)request.getAttribute("areaList");
    //获取所有的ownerObj信息
    List<Owner> ownerList = (List<Owner>)request.getAttribute("ownerList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Owner ownerObj = (Owner)request.getAttribute("ownerObj");
    String addTime = (String)request.getAttribute("addTime"); //发布时间查询关键字
    Area areaObj = (Area)request.getAttribute("areaObj");
    String minsuName = (String)request.getAttribute("minsuName"); //民宿名称查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>民宿查询</title>
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
  			<li><a href="<%=basePath %>Minsu/frontlist">民宿信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Minsu/minsu_frontAdd.jsp" style="display:none;">添加民宿</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<minsuList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Minsu minsu = minsuList.get(i); //获取到民宿对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Minsu/<%=minsu.getMinsuId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=minsu.getMinsuPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		民宿id:<%=minsu.getMinsuId() %>
			     	</div>
			     	<div class="field">
	            		所在地区:<%=minsu.getAreaObj().getAreanName() %>
			     	</div>
			     	<div class="field">
	            		民宿名称:<%=minsu.getMinsuName() %>
			     	</div>
			     	<div class="field">
	            		每日价格:<%=minsu.getPrice() %>
			     	</div>
			     	<div class="field">
	            		民宿主人:<%=minsu.getOwnerObj().getName() %>
			     	</div>
			     	<div class="field">
	            		发布时间:<%=minsu.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Minsu/<%=minsu.getMinsuId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="minsuEdit('<%=minsu.getMinsuId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="minsuDelete('<%=minsu.getMinsuId() %>');" style="display:none;">删除</a>
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
    		<h1>民宿查询</h1>
		</div>
		<form name="minsuQueryForm" id="minsuQueryForm" action="<%=basePath %>Minsu/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="ownerObj_ownerUserName">民宿主人：</label>
                <select id="ownerObj_ownerUserName" name="ownerObj.ownerUserName" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Owner ownerTemp:ownerList) {
	 					String selected = "";
 					if(ownerObj!=null && ownerObj.getOwnerUserName()!=null && ownerObj.getOwnerUserName().equals(ownerTemp.getOwnerUserName()))
 						selected = "selected";
	 				%>
 				 <option value="<%=ownerTemp.getOwnerUserName() %>" <%=selected %>><%=ownerTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="addTime">发布时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择发布时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <div class="form-group">
            	<label for="areaObj_areaId">所在地区：</label>
                <select id="areaObj_areaId" name="areaObj.areaId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(Area areaTemp:areaList) {
	 					String selected = "";
 					if(areaObj!=null && areaObj.getAreaId()!=null && areaObj.getAreaId().intValue()==areaTemp.getAreaId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=areaTemp.getAreaId() %>" <%=selected %>><%=areaTemp.getAreanName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="minsuName">民宿名称:</label>
				<input type="text" id="minsuName" name="minsuName" value="<%=minsuName %>" class="form-control" placeholder="请输入民宿名称">
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="minsuEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;民宿信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="minsuEditForm" id="minsuEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="minsu_minsuId_edit" class="col-md-3 text-right">民宿id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="minsu_minsuId_edit" name="minsu.minsuId" class="form-control" placeholder="请输入民宿id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="minsu_areaObj_areaId_edit" class="col-md-3 text-right">所在地区:</label>
		  	 <div class="col-md-9">
			    <select id="minsu_areaObj_areaId_edit" name="minsu.areaObj.areaId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="minsu_minsuName_edit" class="col-md-3 text-right">民宿名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="minsu_minsuName_edit" name="minsu.minsuName" class="form-control" placeholder="请输入民宿名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="minsu_minsuPhoto_edit" class="col-md-3 text-right">民宿照片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="minsu_minsuPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="minsu_minsuPhoto" name="minsu.minsuPhoto"/>
			    <input id="minsuPhotoFile" name="minsuPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="minsu_price_edit" class="col-md-3 text-right">每日价格:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="minsu_price_edit" name="minsu.price" class="form-control" placeholder="请输入每日价格">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="minsu_minsuDesc_edit" class="col-md-3 text-right">民宿介绍:</label>
		  	 <div class="col-md-9">
			    <textarea id="minsu_minsuDesc_edit" name="minsu.minsuDesc" rows="8" class="form-control" placeholder="请输入民宿介绍"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="minsu_minsuMemo_edit" class="col-md-3 text-right">备注信息:</label>
		  	 <div class="col-md-9">
			    <textarea id="minsu_minsuMemo_edit" name="minsu.minsuMemo" rows="8" class="form-control" placeholder="请输入备注信息"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="minsu_ownerObj_ownerUserName_edit" class="col-md-3 text-right">民宿主人:</label>
		  	 <div class="col-md-9">
			    <select id="minsu_ownerObj_ownerUserName_edit" name="minsu.ownerObj.ownerUserName" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="minsu_addTime_edit" class="col-md-3 text-right">发布时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date minsu_addTime_edit col-md-12" data-link-field="minsu_addTime_edit">
                    <input class="form-control" id="minsu_addTime_edit" name="minsu.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#minsuEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxMinsuModify();">提交</button>
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
    document.minsuQueryForm.currentPage.value = currentPage;
    document.minsuQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.minsuQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.minsuQueryForm.currentPage.value = pageValue;
    documentminsuQueryForm.submit();
}

/*弹出修改民宿界面并初始化数据*/
function minsuEdit(minsuId) {
	$.ajax({
		url :  basePath + "Minsu/" + minsuId + "/update",
		type : "get",
		dataType: "json",
		success : function (minsu, response, status) {
			if (minsu) {
				$("#minsu_minsuId_edit").val(minsu.minsuId);
				$.ajax({
					url: basePath + "Area/listAll",
					type: "get",
					success: function(areas,response,status) { 
						$("#minsu_areaObj_areaId_edit").empty();
						var html="";
		        		$(areas).each(function(i,area){
		        			html += "<option value='" + area.areaId + "'>" + area.areanName + "</option>";
		        		});
		        		$("#minsu_areaObj_areaId_edit").html(html);
		        		$("#minsu_areaObj_areaId_edit").val(minsu.areaObjPri);
					}
				});
				$("#minsu_minsuName_edit").val(minsu.minsuName);
				$("#minsu_minsuPhoto").val(minsu.minsuPhoto);
				$("#minsu_minsuPhotoImg").attr("src", basePath +　minsu.minsuPhoto);
				$("#minsu_price_edit").val(minsu.price);
				$("#minsu_minsuDesc_edit").val(minsu.minsuDesc);
				$("#minsu_minsuMemo_edit").val(minsu.minsuMemo);
				$.ajax({
					url: basePath + "Owner/listAll",
					type: "get",
					success: function(owners,response,status) { 
						$("#minsu_ownerObj_ownerUserName_edit").empty();
						var html="";
		        		$(owners).each(function(i,owner){
		        			html += "<option value='" + owner.ownerUserName + "'>" + owner.name + "</option>";
		        		});
		        		$("#minsu_ownerObj_ownerUserName_edit").html(html);
		        		$("#minsu_ownerObj_ownerUserName_edit").val(minsu.ownerObjPri);
					}
				});
				$("#minsu_addTime_edit").val(minsu.addTime);
				$('#minsuEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除民宿信息*/
function minsuDelete(minsuId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Minsu/deletes",
			data : {
				minsuIds : minsuId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#minsuQueryForm").submit();
					//location.href= basePath + "Minsu/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交民宿信息表单给服务器端修改*/
function ajaxMinsuModify() {
	$.ajax({
		url :  basePath + "Minsu/" + $("#minsu_minsuId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#minsuEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#minsuQueryForm").submit();
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

    /*发布时间组件*/
    $('.minsu_addTime_edit').datetimepicker({
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


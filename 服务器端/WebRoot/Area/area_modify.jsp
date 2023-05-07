<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/area.css" />
<div id="area_editDiv">
	<form id="areaEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">地区id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="area_areaId_edit" name="area.areaId" value="<%=request.getParameter("areaId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">地区名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="area_areanName_edit" name="area.areanName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="areaModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Area/js/area_modify.js"></script> 

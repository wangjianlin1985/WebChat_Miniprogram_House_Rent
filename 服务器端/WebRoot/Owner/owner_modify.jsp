<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/owner.css" />
<div id="owner_editDiv">
	<form id="ownerEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">宿主用户名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_ownerUserName_edit" name="owner.ownerUserName" value="<%=request.getParameter("ownerUserName") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">登录密码:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_password_edit" name="owner.password" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">宿主姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_name_edit" name="owner.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_gender_edit" name="owner.gender" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_birthDate_edit" name="owner.birthDate" />

			</span>

		</div>
		<div>
			<span class="label">宿主照片:</span>
			<span class="inputControl">
				<img id="owner_ownerPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="owner_ownerPhoto" name="owner.ownerPhoto"/>
				<input id="ownerPhotoFile" name="ownerPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_telephone_edit" name="owner.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">家庭地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_address_edit" name="owner.address" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">注册时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_regTime_edit" name="owner.regTime" />

			</span>

		</div>
		<div>
			<span class="label">审核状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_shzt_edit" name="owner.shzt" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="ownerModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Owner/js/owner_modify.js"></script> 

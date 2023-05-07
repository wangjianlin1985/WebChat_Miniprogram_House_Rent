<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/owner.css" />
<div id="ownerAddDiv">
	<form id="ownerAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">宿主用户名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_ownerUserName" name="owner.ownerUserName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">登录密码:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_password" name="owner.password" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">宿主姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_name" name="owner.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_gender" name="owner.gender" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_birthDate" name="owner.birthDate" />

			</span>

		</div>
		<div>
			<span class="label">宿主照片:</span>
			<span class="inputControl">
				<input id="ownerPhotoFile" name="ownerPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_telephone" name="owner.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">家庭地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_address" name="owner.address" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">注册时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_regTime" name="owner.regTime" />

			</span>

		</div>
		<div>
			<span class="label">审核状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="owner_shzt" name="owner.shzt" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="ownerAddButton" class="easyui-linkbutton">添加</a>
			<a id="ownerClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Owner/js/owner_add.js"></script> 

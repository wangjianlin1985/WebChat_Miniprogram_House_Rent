<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/minsu.css" /> 

<div id="minsu_manage"></div>
<div id="minsu_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="minsu_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="minsu_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="minsu_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="minsu_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="minsu_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="minsuQueryForm" method="post">
			民宿主人：<input class="textbox" type="text" id="ownerObj_ownerUserName_query" name="ownerObj.ownerUserName" style="width: auto"/>
			发布时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			所在地区：<input class="textbox" type="text" id="areaObj_areaId_query" name="areaObj.areaId" style="width: auto"/>
			民宿名称：<input type="text" class="textbox" id="minsuName" name="minsuName" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="minsu_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="minsuEditDiv">
	<form id="minsuEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">民宿id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="minsu_minsuId_edit" name="minsu.minsuId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">所在地区:</span>
			<span class="inputControl">
				<input class="textbox"  id="minsu_areaObj_areaId_edit" name="minsu.areaObj.areaId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">民宿名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="minsu_minsuName_edit" name="minsu.minsuName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">民宿照片:</span>
			<span class="inputControl">
				<img id="minsu_minsuPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="minsu_minsuPhoto" name="minsu.minsuPhoto"/>
				<input id="minsuPhotoFile" name="minsuPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">每日价格:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="minsu_price_edit" name="minsu.price" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">民宿介绍:</span>
			<span class="inputControl">
				<textarea id="minsu_minsuDesc_edit" name="minsu.minsuDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">备注信息:</span>
			<span class="inputControl">
				<textarea id="minsu_minsuMemo_edit" name="minsu.minsuMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">民宿主人:</span>
			<span class="inputControl">
				<input class="textbox"  id="minsu_ownerObj_ownerUserName_edit" name="minsu.ownerObj.ownerUserName" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">发布时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="minsu_addTime_edit" name="minsu.addTime" />

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Minsu/js/minsu_manage.js"></script> 

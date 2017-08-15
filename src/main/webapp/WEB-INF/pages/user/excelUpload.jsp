<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding: 5px;overflow: hidden;">
	<form method="post" enctype="multipart/form-data" >
		<table class="tableForm">
			<tr>
				<th style="width:100px;">文件路径</th>
				<td><input name="excelFile" style="width:300px;" type=file class="easyui-validatebox"  data-options="required:'true',missingMessage:'请选择路径'"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
</DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/basejs.jsp" %>
		<script type="text/javascript" charset="utf-8">
var datagrid;
$(function() {
	datagrid = $('#datagrid')
			.datagrid(
					{
						url : 'getUserList',
						iconCls : 'icon-save',
                        pagination : true,
			            pagePosition : 'bottom',
			            pageSize : 10,
			            pageList : [ 10, 20, 30, 40 ],
						fit : true,
						fitColumns : false,
						rownumbers : true,
						singleSelect : false,
						border : false,
						idField : 'id',
						frozenColumns : [ [ {
							title : '编号',
							field : 'id',
							width : 50,
							sortable : true,
							checkbox : true
						},{
							title : '用户名',
							field : 'userName',
							width : 100,
							sortable : true
						},  {
							title : '昵称',
							field : 'nickName',
							width : 150,
							sortable : true
						},  {
							title : '用户类型',
							field : 'type',
							width : 150,
							sortable : true,
							 formatter : function(value, row, index) {
				                    if(value == 0) {
				                        return "系统管理员";
				                    }else if(value == 1) {
				                        return "用户";
				                    }
				                    return "未知类型";
				                }
						},  {
							title : '用户状态',
							field : 'status',
							width : 150,
							sortable : true,
							formatter : function(value, row, index) {
			                    switch (value) {
			                    case 0:
			                        return '正常';
			                    case 1:
			                        return '停用';
			                    }
			                }
						}] ],
						toolbar : [ {
							text : '增加',
							iconCls : 'icon-add',
							handler : function() {
								append();
							}
						}, '-', {
							text : '删除',
							iconCls : 'icon-remove',
							handler : function() {
								remove();
							}
						}, '-', {
							text : '修改',
							iconCls : 'icon-edit',
							handler : function() {
								edit();
							}
						}, '-' , {
							text : '下载',
							iconCls : 'icon-export',
							handler : function() {
								downLoad();
							}
						}, '-' , {
							text : 'csv下载',
							iconCls : 'icon-export',
							handler : function() {
								csvDownLoad();
							}
						}, '-' , {
							text : '上传',
							iconCls : 'icon-import',
							handler : function() {
								excelUpload();
							}
						},'-', {
							text : '上传CSV',
							iconCls : 'icon-import',
							handler : function() {
								csvUpload();
							}
						}, '-'],
						columns : [ [ {
							title : '操作',
							field : 'll',
							width : 100,
							formatter : function(value, rowData, rowIndex) {
								return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="changeStatus(' + rowIndex + ');">改变状态</a>';
							}
						},{
							title : '操作',
							field : 'l2',
							width : 100,
							formatter : function(value, rowData, rowIndex) {
								return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="changeStatus(' + rowIndex + ');">更多信息</a>';
							}
						} ] ],
						onRowContextMenu : function(e, rowIndex, rowData) {
							e.preventDefault();
							$(this).datagrid('unselectAll');
							$(this).datagrid('selectRow', rowIndex);
							$('#menu').menu('show', {
								left : e.pageX,
								top : e.pageY
							});
						} 
					});
});
function edit() {
	var rows = datagrid.datagrid('getSelections');
	if (rows.length == 1) {
		var p = parent.dj
				.dialog( {
					title : '修改用户',
					href : '${pageContext.request.contextPath}/userAction/userEdit?id=' + rows[0].id,
					width : 600,
					height : 450,
					buttons : [ {
						text : '修改',
						handler : function() {
							var f = p.find('form');
							f
									.form(
											'submit',
											{
												url : '${pageContext.request.contextPath}/userAction/edit',
												success : function(d) {
													var json = $.parseJSON(d);
													if (json.success) {
														datagrid
																.datagrid('reload');
														p.dialog('close');
													}
													parent.dj.messagerShow( {
														msg : json.msg,
														title : '提示'
													});
												}
											});
						}
					} ],
					onLoad : function() {
						var f = p.find('form');
						f.form('load', {
							id : rows[0].id,
							userName : rows[0].userName,
							nickName : rows[0].nickName
							
						});

					}
				});
	} else if (rows.length > 1) {
		parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
	} else {
		parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
	}
}
function excelUpload() {
		var p = parent.dj
				.dialog( {
					title : '导入用户',
					href : '${pageContext.request.contextPath}/userAction/excelUpload' ,
					width : 600,
					height : 450,
					buttons : [ {
						text : '导入',
						handler : function() {
							var f = p.find('form');
							f
									.form(
											'submit',
											{
												url : '${pageContext.request.contextPath}/userAction/importExcel',
												success : function(d) {
													var json = $.parseJSON(d);
													if (json.success) {
														datagrid
																.datagrid('reload');
														p.dialog('close');
													}
													parent.dj.messagerShow( {
														msg : json.msg,
														title : '提示'
													});
												}
											});
						}
					} ]
					
				});
	
}
function csvUpload() {
		var p = parent.dj
				.dialog( {
					title : '导入用户',
					href : '${pageContext.request.contextPath}/userAction/csvUpload' ,
					width : 600,
					height : 450,
					buttons : [ {
						text : '导入',
						handler : function() {
							var f = p.find('form');
							f
									.form(
											'submit',
											{
												url : '${pageContext.request.contextPath}/userAction/importCSV',
												success : function(d) {
													var json = $.parseJSON(d);
													if (json.success) {
														datagrid
																.datagrid('reload');
														p.dialog('close');
													}
													parent.dj.messagerShow( {
														msg : json.msg,
														title : '提示'
													});
												}
											});
						}
					} ]
					
				});
	
}
function append() {
	var p = parent.$.modalDialog( {
				title : '新增用户',
				href : '${pageContext.request.contextPath}/userAction/userAdd',
				width : 600,
				height : 450,
				buttons : [ {
					text : '添加',
	                handler : function() {
	                    parent.$.modalDialog.openner_dataGrid = datagrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
	                    var f = parent.$.modalDialog.handler.find('#userAddForm');
	                    f.submit();
	                }
				} ]
			});
}
function remove() {
	var rows = datagrid.datagrid('getChecked');
	var ids = [];
	if (rows.length > 0) {
		parent.dj
				.messagerConfirm(
						'请确认',
						'您要删除当前所选项目？',
						function(r) {
							if (r) {
								for ( var i = 0; i < rows.length; i++) {
									ids.push(rows[i].id);
								}
								$
										.ajax( {
											url : '${pageContext.request.contextPath}/userAction/delete',
											data : {
												ids : ids.join(',')
											},
											dataType : 'json',
											success : function(d) {
												datagrid.datagrid('load');
												datagrid
														.datagrid('unselectAll');
												parent.dj.messagerShow( {
													title : '提示',
													msg : d.msg
												});
											}
										});
							}
						});
	} else {
		parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
	}
}

function changeStatus(rowIndex) {
	var rows = datagrid.datagrid('getRows');
	var row = rows[rowIndex];
	var status =row.status;
	parent.dj
	.messagerConfirm(
			'请确认',
			status==0?'您要停用所选用户？':'您要启用所选用户？',
			function(r) {
				if (r) {
					id=row.id;
					status=
					$.ajax( {
								url : '${pageContext.request.contextPath}/userAction/changeStatus',
								data : {
									id : id,
									status:status
								},
								dataType : 'json',
								success : function(d) {
									datagrid.datagrid('load');
									datagrid
											.datagrid('unselectAll');
									parent.dj.messagerShow( {
										title : '提示',
										msg : d.msg
									});
								}
							});
				}
			});
}
function downLoad(){
	window.open("userAction/exportUser","_blank");
}
function csvDownLoad(){
	window.open("userAction/exportUserByCSV","_blank");
}

</script>
	</head>
	<body class="easyui-layout">

		<div data-options="region:'center',border:false"
			style="overflow: hidden;">
			<table id="datagrid"></table>
		</div>

		<div id="menu" class="easyui-menu"
			style="width: 120px; display: none;">
			<div onclick="append();" data-options="iconCls:'icon-add'">
				增加
			</div>
			<div onclick="remove();" data-options="iconCls:'icon-remove'">
				删除
			</div>
			<div onclick="edit();" data-options="iconCls:'icon-edit'">
				编辑
			</div>
			<div onclick="downLoad();" data-options="iconCls:'icon-edit'">
				编辑
			</div>
		</div>
	</body>
</html>
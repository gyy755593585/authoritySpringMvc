<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var permitTree;
    $(function() {
    	permitTree = $('#permitTree').tree({
            url : '${path }/permitAction/getPermitTree',
            parentField : 'pid',
            lines : true,
            checkbox : true,
            onClick : function(node) {},
            onLoadSuccess : function(node, data) {
                progressLoad();
                $.post( '${path }/roleAction/findPermitIdListByRoleId', {
                    id : '${id}'
                }, function(result) {
                    var ids;
                    if (result.success == true && result.obj /= undefined) {
                        ids = $.stringToList(result.obj + '');
                    }
                    if (ids.length > 0) {
                        for ( var i = 0; i < ids.length; i++) {
                            if (permitTree.tree('find', ids[i])) {
                            	permitTree.tree('check', permitTree.tree('find', ids[i]).target);
                            }
                        }
                    }
                }, 'json');
                progressClose();
            },
            cascadeCheck : false
        });

        $('#roleGrantForm').form({
            url : '${path }/roleAction/grant',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                var checknodes = permitTree.tree('getChecked');
                var ids = [];
                if (checknodes && checknodes.length > 0) {
                    for ( var i = 0; i < checknodes.length; i++) {
                        ids.push(checknodes[i].id);
                    }
                }
                $('#permitIds').val(ids);
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });

    function checkAll() {
        var nodes = permitTree.tree('getChecked', 'unchecked');
        if (nodes && nodes.length > 0) {
            for ( var i = 0; i < nodes.length; i++) {
            	permitTree.tree('check', nodes[i].target);
            }
        }
    }
    function uncheckAll() {
        var nodes = permitTree.tree('getChecked');
        if (nodes && nodes.length > 0) {
            for ( var i = 0; i < nodes.length; i++) {
            	permitTree.tree('uncheck', nodes[i].target);
            }
        }
    }
    function checkInverse() {
        var unchecknodes = permitTree.tree('getChecked', 'unchecked');
        var checknodes = permitTree.tree('getChecked');
        if (unchecknodes && unchecknodes.length > 0) {
            for ( var i = 0; i < unchecknodes.length; i++) {
            	permitTree.tree('check', unchecknodes[i].target);
            }
        }
        if (checknodes && checknodes.length > 0) {
            for ( var i = 0; i < checknodes.length; i++) {
            	permitTree.tree('uncheck', checknodes[i].target);
            }
        }
    }
</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" title="系统资源" style="width: 300px; padding: 1px;">
        <div class="well well-small">
            <form id="roleGrantForm" method="post">
                <input name="id" type="hidden"  value="${id}" readonly="readonly">
                <ul id="permitTree"></ul>
                <input id="permitIds" name="permitIds" type="hidden" />
            </form>
        </div>
    </div>
    <div data-options="region:'center'" title="" style="overflow: hidden; padding: 10px;">
        <div>
            <button class="btn btn-success" onclick="checkAll();">全选</button>
            <br /> <br />
            <button class="btn btn-warning" onclick="checkInverse();">反选</button>
            <br /> <br />
            <button class="btn btn-inverse" onclick="uncheckAll();">取消</button>
        </div>
    </div>
</div>
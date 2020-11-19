/**
 * zTree配置初始化
 * @type {{treeObj: null, treeId: string, view: {TXTSelectedEnable: boolean, addDIYDOM: null, selectedMulti: boolean, removeHoverDOM: null, expandSpeed: string, autoCancelSelected: boolean, dblClickExpand: boolean, showTitle: boolean, nameIsHTML: boolean, showLine: boolean, addHoverDOM: null, fontCSS: {}, showIcon: boolean}, data: {keep: {parent: boolean, leaf: boolean}, simpleData: {idKey: string, enable: string, pIdKey: string, rootpId: null}, key: {isParent: string, children: string, name: string, checked: string, title: string, url: string, isHidden: string}}, edit: {removeTitle: string, enable: boolean, renameTitle: string, showRenameBtn: boolean, drag: {autoExpandTrigger: boolean, next: boolean, borderMin: number, minMoveSize: number, prev: boolean, autoOpenTime: number, borderMax: number, isMove: boolean, inner: boolean, maxShowNodeNum: number, isCopy: boolean}, editnameSelectAll: boolean, showRemoveBtn: boolean}, callback: {onCheck: null, beforeMouseDown: null, onCollapse: null, onClick: null, onExpand: null, beforeEditName: null, onAsyncSuccess: null, onDragMove: null, beforeRightClick: null, onRightClick: null, beforeRemove: null, beforeDragOpen: null, onAsyncError: null, onMouseDown: null, onNodeCreated: null, beforeCheck: null, beforeClick: null, beforeCollapse: null, beforeAsync: null, beforeRename: null, onDrag: null, beforeExpand: null, beforeDrop: null, beforeDrag: null, onDrop: null, beforeMouseUp: null, beforeDBClick: null, onMouseUp: null, onRemove: null, onRename: null}, anync: {dataFilter: null, contentTye: string, enable: boolean, autoParam: [], dataType: string, otherParam: [], type: string, url: {}}, check: {chkStyle: string, autoCheckTrigger: boolean, enable: boolean, chkDisabledInherit: boolean, chkboxType: {Y: string, N: string}, radioType: string, nocheckInherit: boolean}}}
 */
var setting = {

    treeId : "",//zTree的唯一标识,初始化后,等于用户定义的zTree容器的id属性值,勿进行初始化或修改,属于内部参数

    treeObj : null, //zTree容器的jQuery对象,主要功能:便于操作

    anync:{    //是否异步加载 相当于ajax

        autoParam : [],   //可以设置提交时的参数名称,例如server只接受zId:["id=zId"]；默认值空

        contentTye : "application...", //   ajax提交参数的数据类型

        dataFilter : null ,//用于对ajax返回数据进行预处理的函数

        dataType : "text" ,//ajax获取的数据类型

        enable : false , //设置zTree是否开启异步加载模式,默认值是false

        otherParam : [] , //其他参数,直接用json格式制作键值对,例如:{key1:value1,key2:value2}

        type : "post" , //请求方式

        url : {} //路径
    },

    callback : {  //返回函数,根据需求选择合适的监听事件,

        beforeAsync : null,   //异步加载之前的事件回调函数,zTree根据返回值确定是否允许异步加载

        beforeCheck : null ,//勾选 取消勾选 之前的时间回调函数,并且根据返回值确定是否允许勾选 或 取消勾选

        beforeClick : null ,//单击节点之前的时间回调函数,并且根据返回值确定是否允许单击操作

        beforeCollapse : null,//父节点折叠之前的事件毁掉函数,并且根据返回值确定是否允许折叠操作

        beforeDBClick : null, //zTree上鼠标双击之前的事件回调函数,并且根据返回值确定触发onDBClick事件回调函数

        beforeDrag : null , //节点被拖拽之前的时间回调函数,并且根据返回值确定是否允许开启拖拽操作

        beforeDragOpen : null , //拖拽节点移动到折叠状态的父节点后,即将自动展开该父节点之前的事件回调函数,并且根据返回值确定是否允许自动展开操作

        beforeDrop : null ,//节点拖拽操作结束之前的时间回调函数,并且根据返回值确定是否允许此拖拽操作

        beforeEditName:null,//节点编辑按钮的click事件,并且根据返回值确定是否允许进入名称编辑 状态

        beforeExpand:null , //父节点展开之前的事件回调函数,并且根据返回值确定是否允许展开操作

        beforeMouseDown:null,  //zTree上鼠标按键按下之前的事件回调函数,并且根据返回值确定触发onMouseDown事件回调函数

        beforeMouseUp:null ,//zTree上鼠标按键松开之前的事件回调函数,并且根据返回值确定触发onMouseUp事件回调函数

        beforeRemove:null,//节点被删除之前的事件回调函数,并且根据返回值确定是否允许删除操作

        beforeRename:null,//节点编辑名称结束（input失去焦点或按下Enter键）之后,更新节点名称数据之前的事件回调函数,并且根据返回值确定是否允许更改名称的操作

        beforeRightClick:null,//zTree上鼠标右键点击之前的事件回调函数,并且根据返回值确定触发onRightClick事件回调函数

        onAsyncError:null,//异步加载出现异常错误的事件回调函数

        onAsyncSuccess : null,//异步加载正常结束的事件回调函数

        onCheck : null , //CheckBox/radio 被勾选或取消勾选的事件回调函数

        onClick : null,//节点被点击的事件回调函数

        onCollapse:onCollapse,//节点被折叠的事件回调函数

        onDrag:null,//节点被拖拽的事件回调函数

        onDragMove : null,//节点被拖拽过程中移动的事件回调函数

        onDrop:null,//节点拖拽操作结束的事件回调函数

        onExpand : null ,//节点被展开的事件回调函数

        onMouseDown:null,//zTree上鼠标按键按下后的时间回调事件

        onMouseUp:null,//zTree上鼠标按键松开后的事件回调函数

        onNodeCreated :null,//节点生成DOM后的事件回调函数

        onRemove:null,//删除节点之后的事件回调函数

        onRename : null ,//节点编辑名称结束之后的事件回调函数

        onRightClick : null,//zTree撒

    },

    check:{

        autoCheckTrigger : false,   //设置自动关联勾选时是否触发beforeCheck / onCheck时间回调函数 默认值false

        chkboxType:{"Y":"ps","N":"ps"},  //勾选checkbox对于父子节点的关联关系（Y表示checkbox被勾选后的情况；N表示取消勾选后的情况；p表示影响父节点；s表示影响子节点）

        chkStyle:"checkbox",  //勾选框类型（checkbox和radio）,和setting.check.enable=true时生效

        enable:true,  //设置ztree节点上是否显示checkbox/radio

        nocheckInherit:false ,//当父节点设置nocheck=true时,设置子节点是否自动继承nocheck=true,setting.check.enable =true 生效,默认值false

        chkDisabledInherit :false,//当父节点设置查看Disabled = true时,设置子节点是否自动继承查看Disabled = true

        radioType:"level",  //radio的分组范围,"level"在每一级节点范围内做一个分组；"all"在整颗树范围内做一个分组；setting.check.enable = true 且 setting.check.chkStyle="radio"时生效

    },

    data:{

        keep:{

            leaf:false, //ztree的节点叶子节点属性锁,是否始终保持isParent = false

            parent:false,//ztree的节点父节点属性锁,是否始终保持isParent = false

        },

        key:{

            checked:"checked",//ztree节点数据中保存check状态的属性名称

            children:"children",//ztree节点数据中保存子节点数据的属性名称

            isParent:"isParent",//ztree节点数据保存节点是否为父节点的属性名称

            isHidden:"isHidden",//ztree节点数据保存节点是否隐藏的属性名称

            name:"name", //ztree数据保存节点名称的属性名称

            title:"",//ztree节点数据保存节点提示信息的属性名称。setting.view.showTitletrue生效

            url:"",  //ztree节点数据保存节点链接的目标URL的属性名称

            status:"",//状态

            series:""

        },

        simpleData:{

            enable:"false",//确定ztree初始化时的节点数据、异步加载时的节点数据、或addNodes方法中输入的newNodes数据是否采用简单数据模式（array）,不需要用户再把数据库中取出的list强行转换为复杂的json嵌套格式

            idKey:"id",//节点数据中保存唯一标识的属性名称

            pIdKey:"pId",//节点数据中保存其父节点唯一标识的属性名称

            rootpId:null,//用于修正根节点父节点数据,即pIdKey指定的属性值

        }

    },

    edit:{

        drag:{

            autoExpandTrigger:true,//拖拽时父节点自动展开是否触发onExpand事件回调函数

            isCopy:false, //拖拽是否允许复制节点

            isMove:true, //拖拽是否允许移动节点

            prev:true,//拖拽到目标节点时,设置是否允许移动到目标节点前面的操作

            next:true,//拖拽到目标节点时,设置是否允许移动到目标节点后面的操作

            inner:true,//拖拽到目标节点时,设置是否允许成为目标节点的子节点

            borderMax:10,//拖拽节点成为根节点时的tree内边界范围

            borderMin:-5,//拖拽节点成为根节点时的tree外边界范围

            minMoveSize:5,//判定是否拖拽操作的最小位移值

            maxShowNodeNum:5,//拖拽多个兄弟节点时,浮动图层中显示的最大节点数,多余的节点用...代替

            autoOpenTime:500, //拖拽时父节点自动展开的延时间隔

        },

        editnameSelectAll :false,//节点编辑名称input初次显示时,设置TXT内容是否为全选状态

        enable:true, //设置ztree是否处于编辑状态

        removeTitle:"remove",// 删除按钮的title辅助信息

        renameTitle:"rename" , //编辑名称按钮的title辅助信息

        showRemoveBtn:false,//设置是否显示删除按钮

        showRenameBtn:false,//设置是否显示编辑名称按钮

    },

    view:{

        addDIYDOM:null,//用于节点上固定显示用户自定义控件

        addHoverDOM:null,//用于当鼠标移动到节点上时,显示用户自定义控件,显示隐藏状态通ztree内部的编辑、删除按钮

        autoCancelSelected:true,//点击节点时,按下Ctrl或Cmd键是否允许取消选择操作

        dblClickExpand: true, //双击节点时,是否自动展开父节点的标识

        expandSpeed:"fast",//ztree节点展开、折叠的动画速度,设置方法同jQuery动画效果中speed参数

        fontCss:setFontCss, //个性化文字样式,只针对ztree在节点上显示的a对象

        nameIsHTML:false,//设置name属性是否支持HTML版本

        removeHoverDOM:null,//用于当鼠标移除节点时,隐藏用户自定义控件,显示隐藏状态同ztree内部的编辑、删除按钮

        selectedMulti:true,//设置是否允许同时选中多个节点

        showIcon:true,//设置ztree是否显示节点的图标

        showLine:true,//设置ztree是否显示节点之间的连线

        showTitle:true,//设置ztree是否显示节点的title提示信息

        TXTSelectedEnable:false //设置ztree是否允许可以选择ztree  DOM内的文本

    }

}
var className = "dark";
var newCount = 1;
var cookie_tree = "role_tree";
var cookie_node = "select_role_node";
//添加
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, orgName:"new node" + (newCount++)});
        return false;
    });
};
/**
 * cookie缓存
 * @param event
 * @param treeId
 * @param treeNode
 */
function onExpand(event, treeId, treeNode) {
    var cookie = $.cookie(cookie_tree + window.location);
    var z_tree = new Array();
    if (cookie) {
        z_tree = JSON.parse(cookie);
    }
    if ($.inArray(treeNode.id, z_tree) < 0) {
        z_tree.push(treeNode.id);
    }
    $.cookie(cookie_tree + window.location, JSON.stringify(z_tree))
}
function onCollapse(event, treeId, treeNode) {
    var cookie = $.cookie(cookie_tree + window.location);
    var z_tree = new Array();
    if (cookie) {
        z_tree = JSON.parse(cookie);
    }
    var index = $.inArray(treeNode.id, z_tree);
    z_tree.splice(index, 1);
    for (var i = 0; i < treeNode.children.length; i++) {
        index = $.inArray(treeNode.children[i].id, z_tree);
        if (index > -1) z_tree.splice(index, 1);
    }
    $.cookie(cookie_tree + window.location, JSON.stringify(z_tree));
}
/**
 * 初始化
 */
$(document).ready(function() {
    //var array = JSON.parse($("#categoryList").val());
    //zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, array); //初始化树
    var id = $("#id").val();
    $.ajax({
        type: "POST",
        url: "/admin/role/getTreeData",
        data:{
            "id":id,
        },
        success: function (data) {
            if (data && data.success) {
                data = data.payload;//JSON.parse(data.payload);
                $.fn.zTree.init($("#treeDemo"), setting, data);
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var cookie = $.cookie(cookie_tree + window.location);
                if (cookie) {
                    z_tree = JSON.parse(cookie);
                    for (var i = 0; i < z_tree.length; i++) {
                        var node = treeObj.getNodeByParam('id', z_tree[i]);
                        treeObj.expandNode(node, true, false);
                    }
                }
                treeObj.expandAll(true);
                var cookieNode = $.cookie(cookie_node+window.location);
                if(cookieNode){
                    var node = treeObj.getNodeByParam("id", cookieNode);
                    treeObj.selectNode(node,true);
                }else {
                }
            }else {
                $("#reflush").html(data);
            }
        }
    });
});

/**
 * 设置节点颜色
 **/
function setFontCss(treeId, treeNode) {
    var status = treeNode.status;

    return status != "0" ? {color:"red"} : {};
}
layui.use('form',function () {
    var form = layui.form;
    form.render();
});
//iframe自适应
$(parent.window).on('resize', function() {
    // 设置Iframe窗口大小
    configIframe();
}).resize();
function configIframe(){
    var index = parent.layer.getFrameIndex(window.name);
    refreshArea(index);
}
function refreshArea(_formWin){
    layer.style(_formWin,{
        top: '10px'
    });
}
function submitData(){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var jsondata = JSON.stringify($("#form-role-add").serializeJSON());
    var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");//ztree的Id  zTreeId
    var checkedNodes = zTreeObj.getCheckedNodes();
    checkedNodes = JSON.stringify(checkedNodes);
    $.ajax({
        type:"POST",
        data:{
            "roleVo":jsondata,
            "checkedNodes":checkedNodes
        },
        url:"/admin/role/save",
        success: function(data){
            if(data && data.success){
                layer.msg(data.msg);
                setTimeout(function (){
                    parent.layer.close(index);
                },500);
            }else {
                layer.alert(data.msg || "保存失败，请刷新后重试");
            }
        }

    });
}
function quxiao(){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
}

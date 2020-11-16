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

        beforeDrop : beforeDrop ,//节点拖拽操作结束之前的时间回调函数,并且根据返回值确定是否允许此拖拽操作

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

        onClick : onClick,//节点被点击的事件回调函数

        onCollapse:onCollapse,//节点被折叠的事件回调函数

        onDrag:null,//节点被拖拽的事件回调函数

        onDragMove : null,//节点被拖拽过程中移动的事件回调函数

        onDrop:onDrop1,//节点拖拽操作结束的事件回调函数

        onExpand : onExpand ,//节点被展开的事件回调函数

        onMouseDown:null,//zTree上鼠标按键按下后的时间回调事件

        onMouseUp:null,//zTree上鼠标按键松开后的事件回调函数

        onNodeCreated :null,//节点生成DOM后的事件回调函数

        onRemove:null,//删除节点之后的事件回调函数

        onRename : null ,//节点编辑名称结束之后的事件回调函数

        onRightClick : OnRightClick,//zTree撒

    },

    check:{

        autoCheckTrigger : false,   //设置自动关联勾选时是否触发beforeCheck / onCheck时间回调函数 默认值false

        chkboxType:{"Y":"ps","N":"ps"},  //勾选checkbox对于父子节点的关联关系（Y表示checkbox被勾选后的情况；N表示取消勾选后的情况；p表示影响父节点；s表示影响子节点）

        chkStyle:"",  //勾选框类型（checkbox和radio）,和setting.check.enable=true时生效

        enable:false,  //设置ztree节点上是否显示checkbox/radio

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
var cookie_tree = "menu_tree";
var cookie_node = "select_menu_node";
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

    $.ajax({
        type: "POST",
        url: "/admin/menu/getData",
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
                    getData(cookieNode)
                }else {
                    getData("0");
                }
            }else {
                $("#reflush").html(data);
            }
        }
    });
    /*    //更新iframe的内容
        setTimeout(function () {
            var cookieNode = $.cookie(cookie_node+window.location);
            if(cookieNode){

            }else {

            }
        },200);*/

    //zTreeObj.expandAll(true);    //true 节点全部展开、false节点收缩
});

/**
 * 添加类别
 */
function add(id,title,url) {
    layer.open({
        type: 2,
        shadeClose: false,
        fixed: true, //不固定
        maxmin: false, //开启最大化最小化按钮
        area: ['500px', '420px'],
        title: title,
        content: url+"?id="+id,
        end: function () {
            window.location.href = window.location.href;
        }
    });
}

/**
 * 设置节点颜色
 **/
function setFontCss(treeId, treeNode) {
    var status = treeNode.status;

    return status != "0" ? {color:"red"} : {};
}

var rMenu = $("#rMenu");
var menuId = $("#menuId");

/**
 * 左键点击事件
 */
function onClick(event,treeId,treeNode) {
    if(treeNode){
        var cookie = $.cookie(cookie_node+window.location);
        $.cookie(cookie_node + window.location, treeNode.id);
        var id = treeNode.id;
        getData(id);
    }
}
function getData(id) {
    var url = "/admin/menu/getSubData";
    $.ajax({
        url: url,
        type: 'POST',
        data:{
            id:id
        },
        success: function (data) {
            if(data && data.success){
                var html = "";
                var data = data.payload;
                for(var i = 0;i<data.length;i++){
                    var menuVo = data[i];
                    var id = menuVo.id;
                    var name = menuVo.name;
                    var url = menuVo.url;
                    var icon = menuVo.icon;
                    var status = menuVo.status;
                    var sort = menuVo.sort;
                    html+="<tr ondblclick=\"trDbClick('"+id+"')\">";
                    html+="<td>"+name+"</td>";
                    html+="<td>"+url+"</td>";
                    html+="<td><i class=\"Hui-iconfont\">"+icon+"</i></td>";
                    if("0" == status){
                        html+="<td>"+"<input type=\"checkbox\" id=\"status\" lay-skin=\"primary\" onclick='return false;' checked='true' />"+"</td>";
                    }else {
                        html+="<td>"+"<input type=\"checkbox\" id=\"status\" lay-skin=\"primary\" onclick='return false;' />"+"</td>";
                    }
/*                    html+="<td>"+sort+"</td>";*/
                    html+="</tr>"
                }
                var topWin = window.document.getElementById("detail").contentWindow;
                topWin.document.getElementById("list").innerHTML=html;
            }else {
                $("#reflush").html(data);
            }
        }
    });
}
//用于捕获节点拖拽操作结束之前的事件回调函数，并且根据返回值确定是否允许此拖拽操作
//默认值 null
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
    var dragSeries = treeNodes[0].series;
    var dropSeries = targetNode.series;
    if('1'==dragSeries && '2'== dropSeries){
        return false;
    }
    var dragPid = treeNodes[0].pId;
    var dropId = targetNode.id;
    console.log(dragPid);
    console.log(dropId);
    if(dragPid == dropId){
        return false;
    }
    var dragId = treeNodes[0].id;
    var dropId = targetNode.id;
    if(StringUtils.isEmpty(dropId) || StringUtils.isEmpty(dragId)){
        layer.msg("移动失败！");
    }else {
        $.ajax({
            type:'POST',
            url: '/admin/menu/drag',
            data:{
                dragId:dragId,
                dropId:dropId
            },
            success: function (data) {
                if(!data.success){
                    layer.msg(data.msg || '更新失败');
                    setTimeout(function() {
                        window.location.href=window.location.href;
                    }, 1000);
                }
            },
        });
    }
    return true;
}
//用于捕获节点拖拽操作结束的事件回调函数  默认值： null
function onDrop1(event, treeId, treeNodes, targetNode,moveType) {
    window.location.href=window.location.href;
    //拖拽成功时，修改被拖拽节点的pid
/*    var dragId = treeNodes[0].id;
    var dropId = targetNode.id;
    if(StringUtils.isEmpty(dropId) || StringUtils.isEmpty(dragId)){
        layer.msg("移动失败！");
    }else {
        $.ajax({
            type:'POST',
            url: '/admin/menu/drag',
            data:{
                dragId:dragId,
                dropId:dropId
            },
            success: function (data) {
                if(!data.success){
                    layer.msg(data.msg || '更新失败');
                    setTimeout(function() {
                        window.location.href=window.location.href;
                    }, 1000);
                }
            },
        });
    }*/

}
function chooseData(id) {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.cancelSelectedNode();
    var node = treeObj.getNodeByParam("id", id);
    treeObj.selectNode(node, true);
    var cookie = $.cookie(cookie_node+window.location);
    $.cookie(cookie_node + window.location, id);
    getData(id);
}

/**
 * 加载菜单
 **/
function OnRightClick(event,treeId,treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        //showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        var id = treeNode.id;
        var pid = treeNode.pId;
        menuId.val(id);
        if(treeNode.series == '0'){
            var list=["#add"];
        }else if(treeNode.series == '1'){
            var list=["#view","#edit","#add","#update","#delete"];
        }else if(treeNode.series == '2'){
            var list=["#view","#edit","#update","#delete"];
        }

        showRMenu(list, event.clientX, event.clientY);
    }
}
//显示菜单
function showRMenu(list,x,y) {
    rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"}); //设置右键菜单的位置、可见
    for(var i=0;i<list.length;i++){
        $(list[i]).show();
    }
    $("body").bind("mousedown", onBodyMouseDown);
}
//隐藏右键菜单
function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"}); //设置右键菜单不可见
    $("body").unbind("mousedown", onBodyMouseDown);
    menuId.val("");
    var list=["#add","#view","#edit","#delete"];
    for(var i=0;i<list.length;i++){
        $(list[i]).hide();
    }
}
//鼠标按下事件
function onBodyMouseDown(event){
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
        rMenu.css({"visibility" : "hidden"});
        menuId.val("");
        var list=["#add","#view","#edit","#update","#delete"];
        for(var i=0;i<list.length;i++){
            $(list[i]).hide();
        }
    }
}
/**
 *
 * 右键菜单新增
 **/
function menu_add(){
    var id = menuId.val();
    add(id,"新增菜单","/admin/menu/add");
    hideRMenu();
}

/**
 * 右键编辑
 */
function menu_edit() {
    var id = menuId.val();
    add(id,"菜单编辑","/admin/menu/edit");
    hideRMenu();
}

/**
 * 菜单 查看
 */
function menu_view() {
    var id = menuId.val();
    add(id,"菜单查看","/admin/menu/view");
    hideRMenu();
}

function menu_delete() {
    var id = menuId.val();
    if(!StringUtils.isEmpty(id)){
        layer.confirm("是否将所选择的菜单删除",{
            btn:['确定','取消'],
            btn1:function (index, layero) {
                layer.close(index);
                updateStatus(id,2);
            },
            btn2:function (index, layero) {
                layer.close(index);
            }
        });
    }else {
        layer.msg("请选择需要删除的内容");
    }
    hideRMenu();
}

/**
 * 启用/停用/删除
 * type 1:启用/停用
 *      2：删除
 * */
function updateStatus(ids,type) {
    $.ajax({
        type:"POST",
        url:"/admin/menu/updateMenuStatusById",
        data:{
            "id":ids,
            "type":type
        },
        success:function (data) {
            layer.msg(data.msg || '更新失败，请刷新页面后重试');
            if(data && data.success){
                setTimeout(function () {
                    window.location.href = window.location.href;
                },100);
            }
        }
    });
}

/**
 * 菜单 启用/停用
 */
function menu_update(){
    var id = menuId.val();
    if(!StringUtils.isEmpty(id)){
        layer.confirm("是否将所选择的菜单启用/停用",{
            btn:['确定','取消'],
            btn1:function (index, layero) {
                layer.close(index);
                updateStatus(id,1);
            },
            btn2:function (index, layero) {
                layer.close(index);
            }
        });
    }else {
        layer.msg("请选择需要修改的内容");
    }
    hideRMenu();
}
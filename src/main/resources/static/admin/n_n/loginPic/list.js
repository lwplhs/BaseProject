$(function () {
    getPage();
});
var page = 1;
var limit = 10;
var total;



/**
 * 加载右键菜单
 * */
function getMenu() {
    $(".thumbnail").each(function () {
        $(this).contextMenu('menu',{
            /* menuStyle: {
                 border: '2px solid #000'
             }, //菜单项样式
             itemStyle: {
                 fontFamily: 'verdana',
                 backgroundColor: 'green',
                 color: 'white',
                 border: 'none',
                 padding: '1px'
             }, //菜单项鼠标放在上面样式
             itemHoverStyle: {
                 color: 'blue',
                 backgroundColor: 'red',
                 border: 'none'
             },*/ //事件
            bindings: {
                'updateStatus': function(t) {
                    /*alert('Trigger was ' + t.id + '\nAction was item_1');*/
                    var id = $(t).find("input[name='loginPic-id']").val();
                    updateStatus(id);
                },
                'updateDelete': function(t) {
                    var id = $(t).find("input[name='loginPic-id']").val();
                    updateDelete(id);
                },
                'viewLoginPic': function(t) {
                    var id = $(t).find("input[name='loginPic-id']").val();
                    viewLoginPic(id);
                },
                'editLoginPic': function(t) {
                    var id = $(t).find("input[name='loginPic-id']").val();
                    editLoginPic(id);

                }
            }
        });
    });
}
/**
 * 查看首页登录背景图信息
 * */
function viewLoginPic(id) {
    layer.open({
        type:2,
        title:"查看登录背景图",
        /*shadeClose: true,*/
        maxmin: true, //开启最大化最小化按钮
        area: ['600px', '500px'],
        content:"/admin/loginPic/view?id="+id
    });
}
/**
 * 编辑
 * */
function editLoginPic(id) {
    layer.open({
        type:2,
        title:"编辑登录背景图",
        /*shadeClose: true,*/
        maxmin: true, //开启最大化最小化按钮
        area: ['600px', '500px'],
        content:"/admin/loginPic/edit?id="+id,
        end: function () {
            loadData();
        }
    });
}
/**
 * 启用/停用
 * */
function updateStatus(ids) {
    $.ajax({
        type:"POST",
        url:"/admin/loginPic/updateLoginPicStatus",
        data:{
            "ids":ids,
            "type":"1"
        },
        success:function (data) {
            $("#reflush").html(data);
            layer.msg(data.msg);
            if(data && data.success){
                setTimeout(function () {
                    loadData();
                },100);
            }
        }
    });
}

/**
 * 删除
 * */
function updateDelete(ids) {
    $.ajax({
        type:"POST",
        url:"/admin/login/updateLoginPicStatus",
        data:{
            "ids":ids,
            "type":"2"
        },
        success:function (data) {
            $("#reflush").html(data);
            layer.msg(data.msg);
            if(data && data.success){
                setTimeout(function () {
                    //重新定义 页数
                    //获取当前页数
                    total = data.payload;
                    //判断当 前页面 是否存在
                    if(Math.ceil(total/limit) < page){
                        page = Math.ceil(total/limit);
                    }
                    getPage();
                },100);
            }
        }
    });
}

/**
 * 加载数据
 * */
function loadData() {
    NProgress.start();
    $.ajax({
        type: "POST",
        url: "/admin/loginPic/getLoginPic",
        data:{
            "pageNum":page,
            "limit":limit,
        },
        success: function (data) {
            $("#list").html(data);
            NProgress.done();
            var imgM = imgManger;
            imgM.init();
            setTimeout(function () {
                getMenu();
                var total = $("#total").val();
                if(total == 0){
                    $("#laypage").attr("hidden","hidden");
                    $("#dataNull").removeAttr("hidden");
                }else {
                    $("#dataNull").attr("hidden","hidden");
                    $("#laypage").removeAttr("hidden");
                }
            },200);
        }
    });
}
/**
 * 加载分页
 * */
function getPage() {
    loadData();  //加载数据
    setTimeout(function () {
        total=$("#total").val();
        /*    var imgM = imgManger;
            imgM.init();*/
        layui.use('laypage', function(){
            var laypage = layui.laypage;


            //执行一个laypage实例
            laypage.render({
                elem: 'laypage' //注意，这里的 test1 是 ID，不用加 # 号
                ,count: total, //数据总数，从服务端得到
                limit:limit,   //每页条数设置
                curr:page,
                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                jump: function(obj, first){
                    //obj包含了当前分页的所有参数，比如：
                    //console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                    //console.log(obj.limit); //得到每页显示的条数
                    //首次不执行
                    if(!first){
                        page=obj.curr;  //改变当前页码
                        limit=obj.limit;
                        total = $("#total").val();
                        loadData();  //加载数据
                        //初始化数据
                    }
                    /*                imgM.init();*/
                }
            });
        });
    },300);


}

/**
 * 批量设置是否启用
 * */
function btn_update(){

    var ids = '';
    $(".img-checked").each(function (t) {
        var id = $(this).find("input[name='loginPic-id']").val();
        ids = ids + id+",";
        console.log(id);
    });
    if(StringUtils.isEmpty(ids)){
        layer.msg("请选择至少一个登录背景图操作",function () {
        });
    }else{
        //判断最后是否有逗号 有的话去掉
        if(ids.endsWith(",")){
            ids = ids.substring(0,ids.length-1);
        }
        layer.confirm("是否将所选择的登录背景图启用/停用",{
            btn:['确定','取消'],
            btn1:function (index, layero) {
                layer.close(index);
                updateStatus(ids);
            },
            btn2:function (index, layero) {
                layer.close(index);
            }
        });
    }

    //layer.alert("123");
}

function btn_delete(){
    var ids = '';
    $(".img-checked").each(function (t) {
        var id = $(this).find("input[name='loginPic-id']").val();
        ids = ids + id+",";
        console.log(id);
    });
    if(StringUtils.isEmpty(ids)){
        layer.msg("请选择至少一个登录背景图操作",function () {
        });
    }else{
        //判断最后是否有逗号 有的话去掉
        if(ids.endsWith(",")){
            ids = ids.substring(0,ids.length-1);
        }
        layer.confirm("是否将所选择的登录背景图删除",{
            btn:['确定','取消'],
            btn1:function (index, layero) {
                layer.close(index);
                updateDelete(ids);
            },
            btn2:function (index, layero) {
                layer.close(index);
            }
        });
    }
}

$('.table-sort').dataTable({
    "aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [
        //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
        {"orderable":false,"aTargets":[0,8]}// 制定列不参与排序
    ]
});

/*图片-添加*/
function picture_add(title,url){
    layer.open({
        type: 2,
        /*shadeClose: true,*/
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        area: ['600px', '500px'],
        title: title,
        content: url,
        end: function () {
            //重新定义 页数 到首页
            page = 1;
            getPage();
        }
    });
}

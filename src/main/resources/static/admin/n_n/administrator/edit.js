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
function submitUser(){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var jsondata = $("#form-user-add").serialize();
    var pwd = $("[name='password']").val();
    var enPwd = $("[name='ensurePassword']").val();
    if(pwd != enPwd){
        layer.alert("密码需要一致");
        return;
    }
    $.ajax({
        type:"POST",
        data:jsondata,
        url:"/admin/administrator/saveAdministrator",
        success: function(data){
            $("#refresh").html(data);
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
/*
取消功能放到base.js中全局通用
function quxiao(){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
}*/

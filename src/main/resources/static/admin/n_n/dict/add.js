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
function submitDict(){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var jsondata = $("#form-dict-add").serialize();
    console.log(jsondata);
    $.ajax({
        type:"POST",
        data:jsondata,
        url:"/admin/dict/saveDict",
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

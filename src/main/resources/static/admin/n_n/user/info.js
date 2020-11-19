function pwd_save_submit(){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var jsondata = $("#form-pwd-add").serialize();
    //console.log(jsondata);
    $.ajax({
        type:"POST",
        data:jsondata,
        url:"/admin/savePwd",
        success: function(data){
            console.log(data.code)
            if(data && data.success){
                layer.msg(data.msg);
                setTimeout(function (){
                    parent.layer.close(index);
                    setTimeout(function () {
                        window.location.href=window.location.href;
                    },100)
                },500);
            }else {
                layer.alert(data.msg || "请求失败，请刷新后重试");
            }
        }

    });
}

/**
 * 取消
 */
function quxiao(){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
}
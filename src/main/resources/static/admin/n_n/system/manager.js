
layui.use('upload', function(){
    var $ = layui.jquery
        ,upload = layui.upload;
    var uploadInst = upload.render({
        elem: '#test1'
        ,url: '/admin/webUpload/upload' //改成您自己的上传接口
        , accept:'images'
        , acceptMime:'image/*'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code != '100000'){
                return layer.msg('上传失败');
            }else {
                layer.msg(res.msg);
                var a = res.aid;
                setAid(a);
            }
            //上传成功
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
});
function setAid(logoPath) {
    $("#logo").val(logoPath);
}
function btn_save() {
    var jsondata = $("#form-system-add").serialize();
    $.ajax({
        type:"POST",
        data:jsondata,
        url:"/admin/system/saveSystem",
        success: function(data){
            $("#refresh").html(data);
            if(data && data.success){
                layer.msg(data.msg);
            }else {
                layer.alert(data.msg || "保存失败，请刷新后重试");
            }
        }

    });}


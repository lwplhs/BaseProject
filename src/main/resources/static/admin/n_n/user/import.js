//选完文件后不自动上传
layui.use('upload', function() {

    var $ = layui.jquery
        , upload = layui.upload;
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    upload.render({
        elem: '#test8'
        , url: '/admin/user/importUser' //改成您自己的上传接口
        , auto: false
        , accept:'file'
        , acceptMime:'application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,.csv'
        , exts:'xls|xlsx|csv'
        //,multiple: true
        , bindAction: '#test9'
        , done: function (data) {
            if(data && data.success){
                layer.msg('上传成功');
                setTimeout(function (){
                    parent.layer.close(index);
                },500);
            }else if(data && !data.success){
                //查询
                var code = data.code;
                var msg = data.msg;
                if("-1"==code){
                    $("#errMsg").html(msg);
                }else if("-2"==code){
                    $("#errMsg").html(msg);
                    var errFile = data.payload;
                    $('#errFileA').attr('href',errFile);
                    $("#errMsg").show();
                    $("#errFile").show();//表示display:none;
                    //$("#errMsg").style.display="";
                    //$("#errFile").style.display="";
                }
            }

        }
    });
});

function downloadMould() {
    window.open("/admin/webUpload/downloadMould?name=人员信息导入.xlsx");
}
$("#test8").click(function () {
    $("#errMsg").hide();
    $("#errFile").hide();
})
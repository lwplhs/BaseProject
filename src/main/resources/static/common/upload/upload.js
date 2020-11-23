var $btn = $('#ctlBtn');
var $thelist = $('#thelist');
var attachmentId=new Array();
var attachmentName=new Array();
// HOOK 这个必须要再uploader实例化前面
WebUploader.Uploader.register({
    'before-send-file': 'beforeSendFile',//整个文件上传前
    'before-send': 'beforeSend', //每个分片上传前
    "after-send-file":"afterSendFile"  //分片上传完毕
}, {
    beforeSendFile: function (file) {
        // Deferred对象在钩子回掉函数中经常要用到，用来处理需要等待的异步操作。
        var deferred = WebUploader.Deferred();
        // 根据文件内容来查询MD5
        uploader.md5File(file,0,3*1024*1024).progress(function (percentage) {   // 及时显示进度
            getProgressBar(file, percentage, "MD5", "MD5");
        }).then(function (val) { // 完成
            file.md5 = val;
            file.uid = WebUploader.Base.guid();
            $.ajax({
                type:"POST",
                url:"/admin/webUpload/checkshard",  //验证文件是否上传过程中中断掉，断点续传，不需要重新开始上传
                async:false,
                data:{
                    fileMd5:val
                },
                success:function(data){
                    var datacode = data.resultCode;
                    file.indexcode = datacode;
                },error:function () {

                }
            });
            //获取文件信息后进入下一步
            deferred.resolve();
        });
        return deferred.promise();
    },
    beforeSend: function (block) {
        var indexchunk = block.file.indexcode;//获取已经上传过的下标
        var deferred = WebUploader.Deferred();
        if(indexchunk>0){
            if(block.chunk>indexchunk){
                //分块不存在，重新发送该分块内容
                deferred.resolve();
            }else{
                //分块存在，跳过
                deferred.reject();
            }
        }else{
            //分块不存在，重新发送该分块内容
            deferred.resolve();
        }
        //返回Deferred的Promise对象。
        return deferred.promise();
    }
    ,afterSendFile:function(file){
        //如果所有分块上传成功，则通知后台合并分块
        $.ajax({
            type:"POST",
            url:"/admin/webUpload/filewebMerge",  //ajax将所有片段合并成整体
            data:{
                fileName : file.name,
                fileMd5:file.md5
            },
            success:function(data){
                if(data && data.success){
                    var t = data.payload;
                    attachmentId.push(t.aId);
                    attachmentName.push(t.aName);
                }
            },error:function () {

            }
        });
    }
});

// 实例化
var uploader = WebUploader.create({
    pick: {
        id: '#picker',
        label: '选择文件'
    },
    fileNumLimit:1,
    multiple:false,
    duplicate:true,//去重， 根据文件名字、文件大小和最后修改时间来生成hash Key
    swf: '/static/admin/n_n/webuploader/0.1.5/Uploader.swf',
    chunked: true,
    chunkSize : 10 * 1024 * 1024, //每片10M
    threads: 3,
    server: '/admin/webUpload/filewebUpload',
    auto: true,
    // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
    disableGlobalDnd: true,
    fileNumLimit: 1024,
    fileSizeLimit: 50*1024 * 1024 * 1024,//50G 验证文件总大小是否超出限制, 超出则不允许加入队列
    fileSingleSizeLimit:10*1024 * 1024 * 1024 //10G 验证单个文件大小是否超出限制, 超出则不允许加入队列
});

// 当有文件被添加进队列的时候
uploader.on('fileQueued', function (file) {
    $thelist.append('<div id="' + file.id + '" class="item">' +
        '<h4 class="info">' + file.name + '</h4>' +
        '<p class="state">等待上传...</p>' +
        '</div>');
    $("#stopBtn").click(function () {
        uploader.stop(true);
    });
    $("#restart").click(function () {
        uploader.upload(file);
    });
});

//当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次。
uploader.onUploadBeforeSend = function (obj, data) {
    //console.log("onUploadBeforeSend");
    var file = obj.file;
    data.md5 = file.md5 || '';
    data.uid = file.uid;
};
// 上传中
uploader.on('uploadProgress', function (file, percentage) {
    getProgressBar(file, percentage, "FILE", "上传进度");
});
// 上传返回结果
uploader.on('uploadSuccess', function (file) {
    var text = '已上传';
    if (file.pass) {
        text = "文件妙传功能，文件已上传。"
    }
    $('#' + file.id).find('p.state').text(text);
});
uploader.on('uploadError', function (file) {
    $('#' + file.id).find('p.state').text('上传出错');
});
uploader.on('uploadComplete', function (file) {
    // 隐藏进度条
    fadeOutProgress(file, 'MD5');
    fadeOutProgress(file, 'FILE');
});
// 文件上传
$btn.on('click', function () {
    uploader.upload();
});

/**
 *  生成进度条封装方法
 * @param file 文件
 * @param percentage 进度值
 * @param id_Prefix id前缀
 * @param titleName 标题名
 */
function getProgressBar(file, percentage, id_Prefix, titleName) {
    var $li = $('#' + file.id), $percent = $li.find('#' + id_Prefix + '-progress-bar');
    // 避免重复创建
    if (!$percent.length) {
        $percent = $('<div id="' + id_Prefix + '-progress" class="progress progress-striped active">' +
            '<div id="' + id_Prefix + '-progress-bar" class="progress-bar" role="progressbar" style="width: 0%">' +
            '</div>' +
            '</div>'
        ).appendTo($li).find('#' + id_Prefix + '-progress-bar');
    }
    var jinducount = percentage * 100;
    var jisuanjindu  =  money_format_notstr(jinducount+"", null, 1);
    var progressPercentage = jisuanjindu + '%';
    $percent.css('width', progressPercentage);
    $percent.html(titleName + ':' + progressPercentage);
}

/**
 * 隐藏进度条
 * @param file 文件对象
 * @param id_Prefix id前缀
 */
function fadeOutProgress(file, id_Prefix) {
    $('#' + file.id).find('#' + id_Prefix + '-progress').fadeOut();
}
function money_format_notstr(money, template, decimals, dec_point, thousands_sep) {
    template = template || "{money}{unit}";
    var unit = "";
    // 大于等于亿，则转换单位亿元
    if (money >= 100000000) {
        money /= 100000000;
        // unit = "亿";
    }
    // 大于等于万，则转换单位万元
    else if (money >= 10000) {
        money /= 10000;
        //unit = "万";
    }
    //money = number_format(money, decimals);
    return money;
}
function btn_save() {
    var id = "";
    var name = "";
    if(attachmentId != null && attachmentId.length > 0){
        id = attachmentId.pop();
        name = attachmentName.pop();
    }
    parent.attId = id;
    parent.attName = name;
    quxiao();

}
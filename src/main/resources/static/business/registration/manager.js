function addSubTable(tableId,ckeckName) {

    var html = "";
    html = "<tr>"
                +"<td>"
                    +"<input type='checkbox' name='"+ckeckName+"' value=''/>"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='' class='layui-input' />"
                +"</td>"
                +"<td> "
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='' class='layui-input' />"
                +"</td>"
                +"<td>"
                    + "<input type='checkbox' name='step' value='0' />技术设计&nbsp;"
                    + "<input type='checkbox' name='step' value='1' />原理机&nbsp;"
                    +" <input type='checkbox' name='step' value='2' />原型机&nbsp;"
                    +" <input type='checkbox' name='step' value='3' />项目转化&nbsp;"
                +"</td>"
                +"<td>"
                    + "<input type='checkbox' name='means' value='0' />ppt&nbsp;"
                    + "<input type='checkbox' name='means' value='1' />视频&nbsp;"
                    +" <input type='checkbox' name='means' value='2' />动效&nbsp;"
                    +" <input type='checkbox' name='means' value='3' />其它&nbsp;"
                +"</td>"
           +"</tr>";
    $("#"+tableId).find("tbody").append(html);

}
function addSubTable2(tableId,ckeckName) {
    var html = "";
    html = "<tr>"
                +"<td>"
                    +"<input type='checkbox' name='"+ckeckName+"' value=''/>"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='zsNo' lay-verify='title'  autocomplete='off' placeholder='编号' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='zsName' lay-verify='title'  autocomplete='off' placeholder='证书名称' class='layui-input' />"
                +"</td>"
                +"<td> "
                    +"<input type='text' name='zsAid' hidden='hidden' lay-verify='title'  class='layui-input' />"
                    +"<label type='text' style='width: 60%;float:left;' name='zsAid-' ></label>"
                    +"<button style='width: 30%' onclick='upload("+"&quot;/admin/registration/upload&quot;"+",this,"+"&quot;zsAid&quot;"+")'>上传附件</button>"
                +"</td>"
            +"</tr>";
    $("#"+tableId).find("tbody").append(html);
}

function addSubTable3(tableId,ckeckName) {
    var html = "";
    html = "<tr>"
                +"<td>"
                    +"<input type='checkbox' name='"+ckeckName+"' value=''/>"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='zlNo' lay-verify='title'  autocomplete='off' placeholder='编号' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='zlName' lay-verify='title'  autocomplete='off' placeholder='介绍资料名称' class='layui-input' />"
                +"</td>"
                +"<td> "
                    +"<input type='text' name='zlAid' hidden='hidden' lay-verify='title' value='1'  class='layui-input' />"
                    +"<label type='text' style='width: 60%;float:left;' name='zlAid-' ></label>"
                    +"<button style='width: 30%' onclick='upload("+"&quot;/admin/registration/upload&quot;"+",this,"+"&quot;zlAid&quot;"+")'>上传附件</button>"
                +"</td>"
            +"</tr>";
    $("#"+tableId).find("tbody").append(html);

}

function chooseAll(checkId,checkName){
    var temp = $("#"+checkId).is(':checked');
    $('input[name="'+checkName+'"]').each(function () {
        if(temp){
            $(this).prop("checked","checked");
        }else {
            $(this).removeAttr("checked");
        }

    });
}
function deleteSubTable(checkName,checkId) {
    $('input[name="'+checkName+'"]:checked').each(function () {
        var s = $(this).closest("tr");
        s.remove();
    });
    $("#"+checkId).removeAttr("checked");
}

var attId="";
var attName="";

function upload(url,t,returnname) {
    var title = "上传文件";
    layer.open({
        type: 2,
        /*shadeClose: true,*/
        fixed: false, //不固定
        maxmin: false, //开启最大化最小化按钮
        area: ['600px', '500px'],
        title: title,
        content: url,
        end: function () {
            //重新定义 页数 到首页
            $(t).parent().find("[name='"+returnname+"']").val(attId);
            var e = $(t).parent().find("[name='"+returnname+"-']");
            var s = "<a href='#' onclick='downLoadFile(this,&quot;"+returnname+"&quot;)'>"+attName+"</a>";
            e.html(s);
        }
    });
}

function downLoadFile(t,returnName) {
    var aId = $(t).parent().parent().find("[name='"+returnName+"']").val();
    window.open("/admin/webUpload/downloadFile?aId="+aId);
}
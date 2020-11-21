function addSubTable(tableId,ckeckName) {

    var html = "";
    html = "<tr>"
                +"<td>"
                    +"<input type='checkbox' name='"+ckeckName+"' value=''/>"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='编号' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='项目名称' class='layui-input' />"
                +"</td>"
                +"<td> "
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='专利（或原创认证）号' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='专利(原创)发明人' class='layui-input' />"
                +"</td>"
                +"<td>"
                    + "<input type='checkbox' name='step' value='0' />技术设计&nbsp;&nbsp&nbsp&nbsp"
                    + "<input type='checkbox' name='step' value='1' />原理机&nbsp;&nbsp&nbsp&nbsp"
                    +" <input type='checkbox' name='step' value='2' />原型机&nbsp;&nbsp&nbsp&nbsp"
                    +" <input type='checkbox' name='step' value='3' />项目转化&nbsp;&nbsp&nbsp&nbsp"
                +"</td>"
                +"<td>"
                    + "<input type='checkbox' name='means' value='0' />ppt&nbsp;&nbsp&nbsp&nbsp"
                    + "<input type='checkbox' name='means' value='1' />视频&nbsp;&nbsp&nbsp&nbsp"
                    +" <input type='checkbox' name='means' value='2' />动效&nbsp;&nbsp&nbsp&nbsp"
                    +" <input type='checkbox' name='means' value='3' />其它&nbsp;&nbsp&nbsp&nbsp"
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
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='编号' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='证书名称' class='layui-input' />"
                +"</td>"
                +"<td> "
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='附件上传' class='layui-input' />"
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
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='编号' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='介绍资料名称' class='layui-input' />"
                +"</td>"
                +"<td> "
                    +"<input type='text' name='username' lay-verify='title'  autocomplete='off' placeholder='附件上传' class='layui-input' />"
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
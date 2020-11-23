function addSubTable(tableId,ckeckName) {

    var html = "";
    html = "<tr>"

                +"<td>"
                    +"<input type='text' name='id' hidden='hidden' value=''/>"
                    +"<input type='checkbox' name='"+ckeckName+"' value=''/>"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='no' lay-verify='title'  autocomplete='off' placeholder='' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='name' lay-verify='title'  autocomplete='off' placeholder='' class='layui-input' />"
                +"</td>"
                +"<td> "
                    +"<input type='text' name='patentNo' lay-verify='title'  autocomplete='off' placeholder='' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='inventor' lay-verify='title'  autocomplete='off' placeholder='' class='layui-input' />"
                +"</td>"
                +"<td>"
                    + "<input type='checkbox' name='stage1' value='0' />技术设计&nbsp;"
                    + "<input type='checkbox' name='stage2' value='1' />原理机&nbsp;"
                    +" <input type='checkbox' name='stage3' value='2' />原型机&nbsp;"
                    +" <input type='checkbox' name='stage4' value='3' />项目转化&nbsp;"
                +"</td>"
                +"<td>"
                    + "<input type='checkbox' name='introduction1' value='0' />ppt&nbsp;"
                    + "<input type='checkbox' name='introduction2' value='1' />视频&nbsp;"
                    +" <input type='checkbox' name='introduction3' value='2' />动效&nbsp;"
                    +" <input type='checkbox' name='introduction4' value='3' />其它&nbsp;"
                +"</td>"
           +"</tr>";
    $("#"+tableId).find("tbody").append(html);

}
function addSubTable2(tableId,ckeckName) {
    var html = "";
    html = "<tr>"

                +"<td>"
                    +"<input type='text' name='id' hidden='hidden' value=''/>"
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
                    +"<input type='text' name='id' hidden='hidden' value=''/>"
                    +"<input type='checkbox' name='"+ckeckName+"' value=''/>"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='zlNo' lay-verify='title'  autocomplete='off' placeholder='编号' class='layui-input' />"
                +"</td>"
                +"<td>"
                    +"<input type='text' name='zlName' lay-verify='title'  autocomplete='off' placeholder='介绍资料名称' class='layui-input' />"
                +"</td>"
                +"<td> "
                    +"<input type='text' name='zlAid' hidden='hidden' lay-verify='title' value=''  class='layui-input' />"
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
    attId =  $(t).parent().find("[name='"+returnname+"']").val();
    attName = $(t).parent().find("[name='"+returnname+"-']").find("a").html();
    layer.open({
        type: 2,
        /*shadeClose: true,*/
        fixed: false, //不固定
        maxmin: false, //开启最大化最小化按钮
        area: ['600px', '500px'],
        title: title,
        content: url,
        end: function () {
            //附件回调
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

function btn_save() {
    //获取主表数据
    var id = $("#mainTable").find("[name='id']").val();
    var name = $("#mainTable").find("[name='name']").val();
    var sex = $("#mainTable").find("[name='sex']").val();
    var age = $("#mainTable").find("[name='age']").val();
    var identity = $("#mainTable").find("[name='identity']").val();
    var unit = $("#mainTable").find("[name='unit']").val();
    var department = $("#mainTable").find("[name='department']").val();
    var office = $("#mainTable").find("[name='office']").val();
    var telephone = $("#mainTable").find("[name='telephone']").val();
    var area = $("#mainTable").find("[name='area']").val();
    var jsondata={
        "id":id,
        "name":name,
        "sex":sex,
        "age":age,
        "identity":identity,
        "unit":unit,
        "department":department,
        "office":office,
        "telephone":telephone,
        "area":area
    };
    //获取子表数据
    //项目基本信息
    var baseList = new Array();
    $("#baseInfo").find("tr").each(function (index,element) {
        //第一个是头部 从第二个开始
        if(index!=0){
            var temp = new Object();
            //id
            var id = $(element).find("[name='id']").val();
            var no = $(element).find("[name='no']").val();
            var name = $(element).find("[name='name']").val();
            var patentNo = $(element).find("[name='patentNo']").val();
            var inventor = $(element).find("[name='inventor']").val();
            var stage1 = $(element).find("[name='stage1']").is(':checked');
            var stage2 = $(element).find("[name='stage2']").is(':checked');
            var stage3 = $(element).find("[name='stage3']").is(':checked');
            var stage4 = $(element).find("[name='stage4']").is(':checked');
            var introduction1 = $(element).find("[name='introduction1']").is(':checked');
            var introduction2 = $(element).find("[name='introduction2']").is(':checked');
            var introduction3 = $(element).find("[name='introduction3']").is(':checked');
            var introduction4 = $(element).find("[name='introduction4']").is(':checked');
            temp={
                "id":id,
                "no":no,
                "name":name,
                "patentNo":patentNo,
                "inventor":inventor,
                "stage1":stage1,
                "stage2":stage2,
                "stage3":stage3,
                "stage4":stage4,
                "introduction1":introduction1,
                "introduction2":introduction2,
                "introduction3":introduction3,
                "introduction4":introduction4,
            }
            baseList.push(temp);
        }
    });
    //获取获奖信息
    var certList = new Array();
    $("#certificate").find("tr").each(function (index,element) {

        if(index != 0){
            var id = $(element).find("[name='id']").val();
            var no = $(element).find("[name='zsNo']").val();
            var name = $(element).find("[name='zsName']").val();
            var aId = $(element).find("[name='zsAid']").val();
            var temp={
              "id":id,
              "no":no,
              "name":name,
              "aId":aId,
            };
            certList.push(temp);
        }
    });
    //介绍信息
    var intrList = new Array();
    $("#attachment").find("tr").each(function (index,element) {
        if(index != 0){
            var id = $(element).find("[name='id']").val();
            var no = $(element).find("[name='zlNo']").val();
            var name = $(element).find("[name='zlName']").val();
            var aId = $(element).find("[name='zlAid']").val();
            var temp={
                "id":id,
                "no":no,
                "name":name,
                "aId":aId,
            };
            intrList.push(temp);
        }
    });

    $.ajax({
        type:"POST",
        data:{
            "jsondata":JSON.stringify(jsondata),
            "baseList":JSON.stringify(baseList),
            "certList":JSON.stringify(certList),
            "intrList":JSON.stringify(intrList)
        },
        url:"/admin/registration/save",
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
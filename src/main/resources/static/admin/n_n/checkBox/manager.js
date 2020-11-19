layui.use(['transfer', 'layer', 'util'], function() {
    var $ = layui.$
        , transfer = layui.transfer
        , layer = layui.layer
        , util = layui.util;
    var temp;
    var leftTitle = $("#leftTitle").val();
    var rightTitle = $("#rightTitle").val();
    var src = $("#src").val();
    var checkedValue = $("#checkedValue").val();
    var returnId = $("#returnId").val();
    $.ajax({
        type:"POST",
        async: false,
        url:src,
        success: function(data){
            $("#refresh").html(data);
            if(data && data.success){
                temp = data.payload;
            }else {
                layer.alert(data.msg);
            }
        }

    });
    var strs= new Array();
    strs = checkedValue.split(",");
    //基础效果
    transfer.render({
        elem: '#test1',
        id:"rList",
        title: [leftTitle, rightTitle],  //自定义标题
        width: 180, //定义宽度
        height: 280, //定义高度
        data: temp,
        value: strs,
/*        parseData: function(res){
            return {
                "value": res.id //数据值
                ,"title": res.name //数据标题
                ,"disabled": res.disabled  //是否禁用
                ,"checked": res.checked //是否选中
            }
        },*/
    })
    $("#ensure").click(function () {
        var getDate = transfer.getData("rList");
        var id = "";
        var name="";
        if(getDate != null && getDate.length > 0){
            for (var i = 0; i < getDate.length; i++) {
                var temp = getDate[i];
                id = id + temp.value + ",";
                name = name + temp.title + ",";
            }
        }
        if(id.length > 0){
            id = id.substring(0,id.length-1)
        }if(name.length > 0){
            name = name.substring(0,name.length-1);
        }
        parent.checkedIds = id;
        parent.checkedNames = name;
        setTimeout(function () {
            quxiao();
        },100);
    })
});
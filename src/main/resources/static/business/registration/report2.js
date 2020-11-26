
$(function () {
    loadData();
});
var searchKey="";
/**
 * 加载数据
 * */
function loadData() {
    NProgress.start();
    $.ajax({
        type: "POST",
        url: "/admin/registration/getReport2",
        data:{
            "searchKey":searchKey
        },
        success: function (data) {
            $("#list").html(data);
            NProgress.done();
            /*            var imgM = imgManger;
                        imgM.init();*/
            setTimeout(function () {
                /*
                                getMenu();
                */
                var total = $("#total").val();
                if(total == 0){
                    //$("#laypage").attr("hidden","hidden");
                    $("#dataNull").removeAttr("hidden");
                }else {
                    $("#dataNull").attr("hidden","hidden");
                    //$("#laypage").removeAttr("hidden");
                }
                footerPosition();
            },100);
        }
    });
}
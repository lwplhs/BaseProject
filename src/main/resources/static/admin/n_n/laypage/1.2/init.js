/**
 * 根据浏览器页面大小修改 laypage 的位置 暂时没实现
 */
$(function(){
/*    function layPagePosition(){
        //$("#laypage").removeClass("layPageStyle");
        var contentHeight = document.body.scrollHeight,//网页正文全文高度
            winHeight = window.innerHeight;//可视窗口高度，不包括浏览器顶部工具栏
        var s = $("#laypage");
        s = s.offsetTop;
        console.log("contentHeight"+contentHeight);
        console.log("winHeight"+winHeight);
        console.log("s"+s);
/!*        if(!(contentHeight > winHeight*0.8)){
            //当网页正文高度小于可视窗口高度时，为footer添加类fixed-bottom
            $("#laypage").addClass("layPageStyle");
        } else {
            $("#laypage").removeClass("layPageStyle");
        }*!/
    }
    setTimeout(function () {
        layPagePosition();
    },2000);
    $(window).resize(layPagePosition);*/
});
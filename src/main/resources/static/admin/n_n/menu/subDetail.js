/**
 * 双击显示
 */
function trDbClick(id) {

    /**
     * 判断是否是首级数据字典
     */
    /*$.ajax({
        type:"POST",
        url:"/admin/menu/getSeriesById",
        data:{
            "id":id
        },
        success:function (data) {
            if(data && data.success){
                var payload = data.payload;
                if(payload == '1'){
                    window.parent.chooseData(id);
                }else if('2' == payload){
                    //编辑
                    window.parent.dict_add(id,"编辑数据字典","/admin/menu/edit");
                }
            }else {
                $("#reflush").html(data);
            }
        }
    });*/
    window.parent.add(id,"编辑功能菜单","/admin/menu/edit");
}
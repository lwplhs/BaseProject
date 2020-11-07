if(document.querySelector(".float-menu")){
    var clickstatus = false;
    var x = 0;
    var y = 0;
    var l = 0;
    var t = 0;

    var plus = document.getElementById("plus");
    var floatMenu = document.getElementById("float-menu");
    floatMenu.addEventListener("mouseover",function(e){
        floatMenu.className = "float-menu open";
        //floatMenu.className.indexOf("open") > -1?floatMenu.className="float-menu" :
    },false);
    floatMenu.addEventListener("mouseout",function(){
        //floatMenu.className.indexOf("open") > -1? :floatMenu.className = "float-menu open"
        floatMenu.className="float-menu";
        clickstatus = false;
    },false);

    floatMenu.addEventListener("mousedown",function (e) {
        e.preventDefault();
        //获取x坐标和y坐标
        x = e.clientX;
        y = e.clientY;

        //获取左部和顶部的偏移量
        l = floatMenu.offsetLeft;
        t = floatMenu.offsetTop;
        clickstatus = true;
        floatMenu.style.cursor='move';
    },false);
    floatMenu.addEventListener("mousemove",function (e) {
        if(clickstatus){
            e.preventDefault();
            //获取x和y
            var nx = e.clientX;
            var ny = e.clientY;
            //计算移动后的左偏移量和顶部的偏移量
            var nl = nx - (x - l);
            var nt = ny - (y - t);

            floatMenu.style.left = nl + 'px';
            floatMenu.style.top = nt + 'px';
        }
    });
    floatMenu.addEventListener("mouseup",function (e) {
        e.preventDefault();
        clickstatus = false;
        floatMenu.style.cursor='default';
    });

}
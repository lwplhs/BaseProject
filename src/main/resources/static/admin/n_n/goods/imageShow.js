/*carousel-list 页面图片展示js*/
var cn = 'img-block';
var ic = 'img-checked';
var imgManger = {

    getImgs: function(){
        var ids = [];
        var ckd = document.getElementsByClassName(ic);
        for (var i=0; i<ckd.length; i++){
            ids.push(ckd[i].attributes.dataid.value);
        };
        return ids;
    },

    init: function(){
        var self = this;
        var imgObjs = document.getElementsByClassName(cn);
        for (var i = 0; i < imgObjs.length; i++) {
            imgObjs[i].onclick = self.imgClick;
            imgObjs[i].onmouseenter = self.imgHover;
            imgObjs[i].onmouseleave = self.imgOut;
        };
    },

    imgClick: function(){
        var tb = this.getElementsByClassName('thumbnail')[0];

        if (tb.className.indexOf(ic) == -1){
            tb.className += ' '+ic;
        }else{
            var classes = tb.className.split(' ');
            for (var i=0; i<classes.length; i++){
                if (classes[i]==ic){
                    delete classes[i];
                };
            };
            tb.className = classes.join(' ');
        };
    },

    imgHover: function(){
        var ih = 'img-hover';
        if (this.className.indexOf(ih) == -1){
            this.className += ' '+ih;
        };
    },

    imgOut: function(){
        var ih = 'img-hover';
        if (this.className.indexOf(ih) > -1){
            this.className = cn;
        };
    }

};

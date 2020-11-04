var core = (function ($) {
    var getRootPath = function() {
        var curWwwPath=window.document.location.href;
        var pathName=window.document.location.pathname;
        var pos=curWwwPath.indexOf(pathName);
        var localhostPaht=curWwwPath.substring(0,pos);
        var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
        return(localhostPaht+projectName);
    };

    /**
     *  分页组件
     */
    var getPage = function (url, config, content) {
        $.getJSON(url, config, function (res) {
            laypage({
                cont: 'page1',
                pages: Math.ceil(res.total / config.pageSize),
                curr: config.page || 1,
                group: 5,
                skip: true,
                jump: function (obj, first) {
                    if (!first) {
                        config.page = obj.curr;
                        getPage(url,config,content);
                    }
                }
            });
            $('#tbody').html(content(res,config.page));
        });
    };

    //返回content数据到页面，currPage便于删除数据时好定位到该类，此处未用
    function parseUserList(res,currPage){
        var content = "";
        $.each(res.rows, function (i, o) {
            content += "<tr>";
            content += "<td>"+o.id+"</td>";
            content += "<td>"+o.username+"</td>";
            content += "<td>"+o.password+"</td>";
            content += "<td>"+o.phone+"</td>";
            content += "<td>"+o.address+"</td>";
            content += "<td>"+o.score+"</td>";
            content += "<td><a>删除</a></td>";
            content += "</tr>";
        });
        return content;
    }

    //从后台获取json数据
    var getUserListByPage = function(curr){
        core.getPage(core.getRootPath() + '/user/list',{
            page: curr || 1,
            pageSize: 5
        },parseUserList);
    };

    return {
        getRootPath: getRootPath,
        getPage: getPage,
        getUserListByPage:getUserListByPage
    };


})(jQuery);
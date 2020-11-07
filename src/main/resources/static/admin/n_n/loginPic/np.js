/**
 * 进度条调用js 在需要的页面进行引用
 */
$(document).ready(function(){
    NProgress.start();
});
$(window).load(function(){
    NProgress.done();
});
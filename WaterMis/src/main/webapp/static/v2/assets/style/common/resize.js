var $ = function (selector) {
    return document.querySelector(selector);
};
var responsive = function() {
    var pageHeight = document.body.clientHeight;
    var resizeDoms = document.querySelectorAll('.auto-resize');

    if (resizeDoms && document.querySelectorAll('.auto-resize').length > 0) {
        for (var i = 0; i < resizeDoms.length; i++) {
            var dom = resizeDoms[i];
            var subHeight = dom.getAttribute('data-height');
            if (!subHeight) {
                subHeight = 0;
            }
            dom.style.height = pageHeight - subHeight + 'px';
            document.body.style.overflow = 'hidden';
        }
    }

};
responsive();
window.onresize = responsive;

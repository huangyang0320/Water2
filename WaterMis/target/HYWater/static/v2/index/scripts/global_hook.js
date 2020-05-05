var Loading = {
    layerLoading: null,
    show: function () {
        if (!this.layerLoading) {
            // this.layerLoading = layer.load();
            this.layerLoading = layer.load(3, {shade: [0.3, '#000'], offset: ['50%', '50%']});
        }
    },
    hide: function () {
        if (this.layerLoading) {
            layer.close(this.layerLoading);
            this.layerLoading = null;
        }
    }
};

var requestCount = 0;

//automatelly filter ajax request
hookAjax({
    onload:function(xhr){

        setTimeout(function () {
            requestCount--;
            if (requestCount === 0) {
                Loading.hide();

                var result = xhr.responseText;

                if (result) {
                    try {
                        result = JSON.parse(result);
                    } catch (e) {
                        layer.msg('返回数据异常', {icon: 4});
                    }

                    if (Array.isArray(result) && !result.length) {
                        layer.msg('没有查询到数据', {icon: 4});
                        return;
                    }
                    layer.msg('查询完毕', {icon: 1});
                } else {
                    layer.msg('没有查询到数据', {icon: 4});
                }
            }
        }, 500);

        var userStatus = xhr ? xhr.getAllResponseHeaders() : null;

        // check user session exist
        if (userStatus && userStatus!== "" && userStatus.indexOf("x-user-status: logout") > -1) {
            top.window.location = parent.GLOBAL.rootPath + "/cloud/login/login.html";
        }

    },
    open:function(arg){
    },
    send: function (arg) {
        requestCount++;
        Loading.show();
    }
});

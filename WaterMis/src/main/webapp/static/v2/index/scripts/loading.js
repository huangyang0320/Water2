var Loading = {
    maxTimeout: 20 * 1000, //max loading timeout
    loadingTimeout: null,
    layerLoading: null,
    show: function () {
        if (!this.layerLoading) {
            this.layerLoading = layer.load(3, {shade: [0.3, '#000'], offset: ['50%', '50%']});

            // if exceed max loading timeout close the loading layer
            this.loadingTimeout = setTimeout(function () {
                this.hide();
            }, this.maxTimeout);

        }
    },
    hide: function (result) {
        if (this.layerLoading) {
            layer.close(this.layerLoading);
            this.layerLoading = null;

            // clear loading timeout
            if (this.loadingTimeout) {
                clearTimeout(this.loadingTimeout);
            }

            if (result && Array.isArray(result) && result.length > 0) {
                layer.msg('查询完毕', {icon: 1, offset: ['30%']});
            } else {
                layer.msg('没有查询到数据', {icon: 2, offset: ['30%']});
            }
        }
    }
};

Loading.show();
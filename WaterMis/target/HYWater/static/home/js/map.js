function Map() {
    this.arrmaps = new Array();
    // 获取map集合的个数
    this.count = function () {
        return this.arrmaps.length;
    }
    // 判断map是否为空
    this.isEmpty = function () {
        return (this.arrmaps.length < 1);
    }
    // 删除map中所有的元素
    this.clear = function () {
        this.arrmaps = new Array();
    }
    // 向map中增加元素（key, value)
    this.put = function (_key, _value) {
        this.arrmaps.push({
            key: _key,
            value: _value
        });
    }
    // 获取指定key的元素值value
    this.get = function (_key) {
        try {
            for (i = 0; i < this.arrmaps.length; i++) {
                if (this.arrmaps[i].key == _key) {
                    return this.arrmaps[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    }
    // 删除指定key的元素
    this.remove = function (_key) {
        var flag = false;
        try {
            for (i = 0; i < this.arrmaps.length; i++) {
                if (this.arrmaps[i].key == _key) {
                    this.arrmaps.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            flag = false;
        }
        return flag;
    }

    // 获取指定value值的元素key，失败返回空
    this.getKey = function (_value) {
        try {
            for (i = 0; i < this.arrmaps.length; i++) {
                if (this.arrmaps[i].value == _value) {
                    return this.arrmaps[i].key;
                }
            }
        } catch (e) {
            return null;
        }
    }
    // 获取指定索引的元素（使用ele.key，ele.value获取key和value）
    this.value = function (_index) {
        if (_index < 0 || _index >= this.arrmaps.length) {
            return null;
        }
        return this.arrmaps[_index];
    }
    // 判断map中是否含有指定key的元素
    this.containsKey = function (_key) {
        varflag = false;
        try {
            for (i = 0; i < this.arrmaps.length; i++) {
                if (this.arrmaps[i].key == _key) {
                    flag = true;
                }
            }
        } catch (e) {
            flag = false;
        }
        return flag;
    }
    // 判断map中是否含有指定value的元素
    this.containsValue = function (_value) {
        var flag = false;
        try {
            for (i = 0; i < this.arrmaps.length; i++) {
                if (this.arrmaps[i].value == _value) {
                    flag = true;
                }
            }
        } catch (e) {
            flag = false;
        }
        return flag;
    }
    // 获取map中所有value的数组（ARRAY）
    this.values = function () {
        var arr = new Array();
        for (i = 0; i < this.arrmaps.length; i++) {
            arr.push(this.arrmaps[i].value);
        }
        return arr;
    }
    // 获取map中所有key的数组（ARRAY）
    this.keys = function () {
        var arr = new Array();
        for (i = 0; i < this.arrmaps.length; i++) {
            arr.push(this.arrmaps[i].key);
        }
        return arr;
    }
}

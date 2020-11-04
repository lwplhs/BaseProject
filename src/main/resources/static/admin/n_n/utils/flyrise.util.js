//"use strict";
/**
 * @name StringUtils
 * @class 常用字符处理函数 依赖：JsConstants.js
 */
var StringUtils = {

    /**
     * 去首尾空白字符
     */
    trim: function (str) {
        /*
         * 把str加一个空字符串 防止参数str为非字符类型引起的异常
         */
        str = str + PublicDataType.STRING_EMPTY;
        return str.replace(Constants.REGEXP_EMPTY, PublicDataType.STRING_EMPTY);
    },

    /**
     * 非空验证 如果是非空(去空白字符，如tab符、空白符等)则返回true
     */
    isNotEmpty: function (str) {
        return !StringUtils.isEmpty(str);
    },

    /**
     * 非空验证 如果是空(包括有空白字符，如tab符、空白符等)则返回true
     */
    isEmpty: function (str) {
        if (str == null) {
            return true;
        }
        // 先去掉str的空白字符再判断是否为空
        str = StringUtils.trim(str);
        if (str == PublicDataType.UNDEFINED[1] || str == PublicDataType.STRING_NULL || str == PublicDataType.STRING_EMPTY) {
            return true;
        } else {
            return false;
        }
    },

    /**
     * 对象转换成字符后相等比较
     */
    equals: function (arg1, arg2) {
        if (this.isEmpty(arg1) || this.isEmpty(arg2)) {
            return false;
        }
        arg1 = this.trim(arg1);
        arg2 = this.trim(arg2);
        if (/d+/.test(arg1) == false && HtmlFormat.checkDate(arg2) == false) {
            var date = stringToDateObject(arg2);
            arg2 = date.getTime() / 1000;
        }
        return arg1 == arg2;
    },
    /**
     * 对象转换成字符后不相等比较
     */
    notEquals: function (arg1, arg2) {
        return !this.equals(arg1, arg2);
    },

    /**
     * 参数一是否包涵参数二
     */
    contains: function (arg1, arg2) {
        var index = -1;
        try {
            if (this.isEmpty(arg1) || this.isEmpty(arg2)) {
                return false;
            }
            arg1 = this.trim(arg1);
            arg2 = this.trim(arg2);
            index = arg1.indexOf(arg2);
            return index >= 0;
        } catch (e) {
            return false;
        }
    },

    /**
     * 参数一是否不包涵参数二
     */
    notContains: function (arg1, arg2) {
        return !this.contains(arg1, arg2);
    },
    /**
     * 参数一是否以参数二开头
     */
    beginContains: function (arg1, arg2) {
        var index = -1;
        try {
            arg1 = this.trim(arg1);
            arg2 = this.trim(arg2);
            if (this.isEmpty(arg1) || this.isEmpty(arg2)) {
                return false;
            }
            index = arg1.indexOf(arg2);
            return index == 0;
        } catch (e) {
            return false;
        }
    },

    /**
     * 参数一是否以参数二结尾
     */
    endContains: function (arg1, arg2) {
        var index = -1;
        var lastIndex = 0;
        try {
            arg1 = this.trim(arg1);
            arg2 = this.trim(arg2);
            if (this.isEmpty(arg1) || this.isEmpty(arg2)) {
                return false;
            }
            index = arg1.lastIndexOf(arg2);
            lastIndex = arg2.length - 1;
            return index == lastIndex;
        } catch (e) {
            return false;
        }

    },

    /**
     * 判断两个对象转换成字符后比较 忽略大小写 相等返回 : true 否则返回 : false
     */
    equalsIgnoreCase: function (arg1, arg2) {
        arg1 = arg1 + PublicDataType.STRING_EMPTY;
        arg2 = arg2 + PublicDataType.STRING_EMPTY;
        return arg1.toUpperCase() == arg2.toUpperCase();
    },

    /**
     * 验证是否合法的邮箱地址 返回 是：true, 否： false
     */
    validEmail: function (email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        return Constants.REGEXP_EMAIL.test(email);
    },

    /**
     * 判断是否是有效的身份证号码 返回 是：true, 否： false
     */
    validIDCard: function (IDCard) {
        var len;
        var reg;
        if (StringUtils.isEmpty(IDCard)) {
            return false;
        }

        len = IDCard.length;
        if (len == 15) {
            return Constants.REGEXP_IDCARD_15.test(IDCard);
        } else if (len == 18) {
            return Constants.REGEXP_IDCARD_18.test(IDCard);
        } else {
            return false;
        }
    },

    /**
     * 验证是否为正整数(包含零)
     */
    validInteger: function (num) {
        if (StringUtils.isEmpty(num)) {
            return false;
        }
        return Constants.REGEXP_INTEGER.test(num);
    },

    /**
     * 验证是否为正整数(不包含零)
     */
    validIntegerNoZero: function (num) {
        if (StringUtils.isEmpty(num)) {
            return false;
        }
        return Constants.REGEXP_INTEGERNOZERO.test(num);
    },

    /**
     * 验证邮政编码
     */
    isZipCode: function (posCode) {
        if (StringUtils.isEmpty(posCode)) {
            return false;
        }
        return Constants.REGEXP_ZIP_CODE.test(posCode);
    },

    /**
     * 验证是否合法的电话号码
     */
    validTel: function (telNum) {
        if (StringUtils.isEmpty(telNum)) {
            return false;
        }
        return Constants.REGEXP_TEL.test(telNum);
    },

    /**
     * 验证是否合法的手机号 可以“+”开头
     */
    validMobile: function (mobileNum) {
        if (StringUtils.isEmpty(mobileNum)) {
            return false;
        }
        return Constants.REGEXP_MOBILE.test(mobileNum);
    },
    /**
     * 判断语句是否SQL语句或SQL条件
     */
    isSql: function (arg1) {
        arg1 = arg1.toUpperCase();
        return /.*\s(WHERE|FROM|SELECT|IN|UNION|NOT|BETWEEN|AND|OR)\s.*$/.test(arg1);
    },

    validNumberStr: function (text) {
        var reg = /^[A-Za-z0-9]+$/;
        return reg.test(text);

    }
};

function FeMap() {
    this.elements = [];

    /**
     * Map大小
     */
    this.size = function () {
        return this.elements.length;
    },

        /**
         * Map是否为空
         */
        this.isEmpty = function () {
            return (this.elements.length < 1);
        },

        /**
         * 清空Map
         */
        this.clear = function () {
            this.elements = [];
        },

        /**
         * 往Map添加元素
         */
        this.put = function (_key, _value) {
            try {
                if (this.containsKey(_key) == true) {
                    if (this.remove(_key) == true) {
                        this.elements.push({
                            key: _key,
                            value: _value
                        });
                    }
                } else {
                    this.elements.push({
                        key: _key,
                        value: _value
                    });
                }
            } catch (e) {
                Dialog.warn(e);
            }
        },

        /**
         * 移除Map中的元素
         */
        this.remove = function (_key) {
            var bln = false;
            try {
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].key == _key) {
                        this.elements.splice(i, 1);
                        return true;
                    }
                }
            } catch (e) {
                bln = false;
            }
            return bln;
        },

        /**
         * 根据Key获取元素
         */
        this.get = function (_key) {
            try {
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].key == _key) {
                        return this.elements[i].value;
                    }
                }
            } catch (e) {
                return null;
            }
        },

        /**
         * 获取指定下标的元素
         */
        this.element = function (_index) {
            if (_index < 0 || _index >= this.elements.length) {
                return null;
            }
            return this.elements[_index];
        },

        /**
         * 判断Map是否包含某Key
         */
        this.containsKey = function (_key) {
            var bln = false;
            try {
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].key == _key) {
                        bln = true;
                        break;
                    }
                }
            } catch (e) {
                bln = false;
            }
            return bln;
        },

        /**
         * 判断Map是否包含某值
         */
        this.containsValue = function (_value) {
            var bln = false;
            try {
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].value == _value) {
                        bln = true;
                        break;
                    }
                }
            } catch (e) {
                bln = false;
            }
            return bln;

        },

        /**
         * 获取Map所有的key
         */
        this.keys = function () {
            var arr = [];
            for (var i = 0; i < this.elements.length; i++) {
                arr.push(this.elements[i].key);
            }
            return arr;
        },

        /**
         * 获取Map所以的值
         */
        this.values = function () {
            var arr = [];
            for (var i = 0; i < this.elements.length; i++) {
                arr.push(this.elements[i].value);
            }
            return arr;
        };
}

// 把部分函数转成jquery的全局函数

jQuery.eq = function (arg1, arg2) {
    return StringUtils.equals(arg1, arg2);
};
jQuery.ne = function (arg1, arg2) {
    return StringUtils.notEquals(arg1, arg2);
};
jQuery.ct = function (arg1, arg2) {
    return StringUtils.contains(arg1, arg2);
};
jQuery.nc = function (arg1, arg2) {
    return StringUtils.notContains(arg1, arg2);
};
jQuery.bc = function (arg1, arg2) {
    return StringUtils.beginContains(arg1, arg2);
};
jQuery.ec = function (arg1, arg2) {
    return StringUtils.endContains(arg1, arg2);
};
/**
 * 增加正则表达式验证
 * author:高强
 * date:2017-07-04
 * */
jQuery.match = function (arg1, arg2) {
    return new RegExp(arg2, "gi").test(arg1);
}
jQuery.nomatch = function (arg1, arg2) {
    return !jQuery.match(arg1, arg2);
}
/**
 * 根据ID获取对象的值 id： 元素ID [obj]: 调用些方法的对象，可选 备注： 些函数为计算公式专用，对于非数字格式的不能使用。
 * 增加了对字符串类型的支持
 * author:高强
 * date:2017-07-04
 */
jQuery.getVal = function (id, obj) {
    // try {
    // ID格式为tableName.ID，即为子表的表达式
    var value = 0;
    var e1 = null;
    var e2 = null;
    var arr = id.split('\.');
    if (arr.length > 1) {
        if (obj) {
            obj = $(obj);
        } else {
            obj = $(InputUI.currentFormulaObj);
        }
        if (obj.closest('tr')[0]) {
            e1 = obj.closest('tr').find('#' + arr[1]);//这个地方将parents换成了closest才会取到值
            e2 = obj.closest('tr').find('#' + arr[1] + '-');
        } else {    //Zero.lu 2017/02/12 TODO 移动表单子表计划公式获取控件，暂时如此处理
            e1 = obj.closest('#tab-subtable').find('#' + arr[1]);
            e2 = obj.closest('#tab-subtable').find('#' + arr[1] + '-');
        }
    } else {
        e1 = $('#' + id);
        e2 = $('#' + id + '-');
    }
    if (e1 && e2) {
        if (StringUtils.beginContains(e2.attr("format"), "C")) {
            return e1.val();
        }
        value = jQuery.getNumberVal(e1);
        if (StringUtils.isEmpty(value) || isNaN(value)) {
            throw new Error("");
        }
        return Number(value);
    }
    throw new Error("对象未找到!");
    // } catch (e) {
    // return false;
    // }
};

/**
 * 大于
 *
 * @param {} compare1
 * @param {} compare2
 * @return {}
 */
jQuery.gt = function (compare1, compare2) {
    var flag = false;
    if (compare1 > compare2) {
        flag = true;
    }
    return flag;
};

/**
 * 小于
 *
 * @param {} compare1
 * @param {} compare2
 * @return {}
 */
jQuery.lt = function (compare1, compare2) {
    return this.ge(compare1, compare2) == false;
};

/**
 * 大于等于
 *
 * @param {} compare1
 * @param {} compare2
 * @return {}
 */
jQuery.ge = function (compare1, compare2) {
    var flag = false;
    if (!HtmlFormat.checkDate(compare2)) {
        var data = stringToDateObject(compare2);
        var value = data.getTime() / 1000;// 时间计算以秒为单位。
        if (compare1 >= value) {
            flag = true;
        }
    } else {
        if (compare1 >= compare2) {
            flag = true;
        }
    }
    return flag;
};

/**
 * 小于等于
 *
 * @param {} compare1
 * @param {} compare2
 * @return {}
 */
jQuery.le = function (compare1, compare2) {
    var flag = false;
    if (!HtmlFormat.checkDate(compare2)) {
        var data = stringToDateObject(compare2);
        var value = data.getTime() / 1000;// 时间计算以秒为单位。
        if (compare1 <= value) {
            flag = true;
        }
    } else {
        if (compare1 <= compare2) {
            flag = true;
        }
    }
    return flag;
};

jQuery.getNumberVal = function (obj) {
    try {
        var value = 0;
        value = obj.val();
        var showObj = $(obj).parent().find('#' + obj.attr('id') + '-');
        if (StringUtils.equals("date", (showObj.attr("tagType"))) && !HtmlFormat.checkDate(value)) {
            var data = stringToDateObject(value);
            value = data.getTime() / 1000;// 时间计算以秒为单位。
        }
        if (StringUtils.isEmpty(value) || isNaN(value)) {
            value = 0;
        }
        return value;
    } catch (e) {
        return 0;
    }
};
var PC_MOBILE_FLAG = false;
function getPcOrMobileFormObjWrapper(tableName, colId) {
    var $wrapper;
    if ($('#' + tableName + ' #' + colId)[0]) {
        PC_MOBILE_FLAG = false;
        $wrapper = $('#' + tableName + ' #' + colId);
    } else {
        $wrapper = $('table[name=table_' + tableName + '] #' + colId + '');
        PC_MOBILE_FLAG = true;
    }
    return $wrapper;
}

// 子表求和
jQuery.sum = function (tableName, colId) {
    var s = 0;
    if ($('#' + tableName)[0]) {
        var $wrapper = getPcOrMobileFormObjWrapper(tableName, colId);
        $wrapper.not('#td_title_' + tableName + ' :input[type=hidden]').each(function () {
            try {
                s += parseFloat(StringUtils.isEmpty($(this).val()) ? 0 : $(this).val());
            } catch (e) {
            }
        });
    }
    return s;
};

// 子表取最大
jQuery.max = function (tableName, colId) {
    var m = 0;
    var val = 0;
    if ($('#' + tableName)[0]) {
        var $wrapper = getPcOrMobileFormObjWrapper(tableName, colId);
        $wrapper.not('#td_title_' + tableName + ' :input[type=hidden]').each(function () {
            val = $(this).val();
            val = StringUtils.isEmpty(val) ? 0 : val;
            m = Math.max(m, val);
        });

    }
    return m;
};

// 最小
jQuery.min = function (tableName, colId) {
    var m2 = 0;
    var m3 = 0;
    if ($('#' + tableName)[0]) {
        var $wrapper = getPcOrMobileFormObjWrapper(tableName, colId);
        if (PC_MOBILE_FLAG) {
            $wrapper.not('#td_title_' + tableName + ' :input[type=hidden]').each(function (i) {
                if (i != 0) {
                    var val = parseFloat(StringUtils.isEmpty($(this).val()) ? 0 : $(this).val());
                    if (val != 0) {
                        if (i == 1) {
                            m2 = val;
                        }
                        m3 = Math.min(m2, val);
                        m2 = m3;
                    }
                }
            });
        } else {
            $wrapper.not('#td_title_' + tableName + ' :input[type=hidden]').each(function (i) {
                var val = parseFloat(StringUtils.isEmpty($(this).val()) ? 0 : $(this).val());
                if (val != 0) {
                    if (i == 0) {
                        m2 = val;
                    }
                    m3 = Math.min(m2, val);
                    m2 = m3;
                }
            });
        }
    }
    return m3;
};

// 平均
jQuery.avg = function (tableName, colId) {
    var a = 0.0;
    var s = 0.0;
    var len;
    if ($('#' + tableName)[0]) {
        s = $.sum(tableName, colId);
        len = $.count(tableName, colId);
        if (len <= 0) {
            return 0;
        }
        a = HtmlFormat.n45to1(s / len, 2);
    }
    return a;
};

// 记录数
jQuery.count = function (tableName, colId) {
    var c = 0;
    if ($('#' + tableName)[0]) {
        var $wrapper = getPcOrMobileFormObjWrapper(tableName, colId);
        c = $wrapper.length - 1;
    }
    return c;
};

function sysMasterKeyEncypt(id) {
    return base_encode64(id);
}

var base_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
function base_encode64(input) {
    input = unicodetoBytes(input);
    var output = PublicDataType.STRING_EMPTY;
    var chr1 = PublicDataType.STRING_EMPTY, chr2 = PublicDataType.STRING_EMPTY, chr3 = PublicDataType.STRING_EMPTY;
    var enc1 = PublicDataType.STRING_EMPTY, enc2 = PublicDataType.STRING_EMPTY, enc3 = PublicDataType.STRING_EMPTY, enc4 = PublicDataType.STRING_EMPTY;
    var i = 0;
    do {
        chr1 = input[i++];
        chr2 = input[i++];
        chr3 = input[i++];
        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;
        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }
        output = output + base_keyStr.charAt(enc1) + base_keyStr.charAt(enc2) + base_keyStr.charAt(enc3) + base_keyStr.charAt(enc4);
        chr1 = PublicDataType.STRING_EMPTY, chr2 = PublicDataType.STRING_EMPTY, chr3 = PublicDataType.STRING_EMPTY;
        enc1 = PublicDataType.STRING_EMPTY, enc2 = PublicDataType.STRING_EMPTY, enc3 = PublicDataType.STRING_EMPTY, enc4 = PublicDataType.STRING_EMPTY;
    } while (i < input.length);
    return output;
}

function unicodetoBytes(s) {
    var result = [];
    if (s == null || s == "") {
        return result;
    }
    var i = 0, len = s.length;
    result.push(255);
    result.push(254);
    for (var i = 0; i < len; i++) {
        var c = s.charCodeAt(i).toString(16);
        if (c.length == 1) {
            c = "000" + c;
        } else if (c.length == 2) {
            c = "00" + c;
        } else if (c.length == 3) {
            c = "0" + c;
        }
        var var1 = parseInt(c.substring(2), 16);
        var var2 = parseInt(c.substring(0, 2), 16);
        result.push(var1);
        result.push(var2);
    }
    return result;
}

function securitySubmit(form, issub) {
    var chk = document.getElementsByName('master_key');
    if (chk) {
        var master_key_encode = "";
        var _MASTERSTAMP = $('#_MASTERSTAMP');
        var i = 0, len = chk.length;
        for (var i = 0; i < len; i++) {
            if (chk[i].type == "hidden" && chk[i].value != "") {
                master_key_encode += chk[i].value + ",";
            } else {
                if (chk[i].checked) {
                    var mvalue = chk[i].value;
                    master_key_encode += mvalue + ",";
                }
            }
        }
        if (_MASTERSTAMP.length < 1) {
            _MASTERSTAMP = $("<INPUT TYPE='hidden' NAME='_MASTERSTAMP' id='_MASTERSTAMP'>");
            $(form).append(_MASTERSTAMP);
        }
        _MASTERSTAMP.val(sysMasterKeyEncypt(master_key_encode));
    }
    if (arguments.length == 2) {
        if (issub == true) {
            form.submit();
        }
    } else {
        try {
            saveMediaFile();
            form.submit();
        } catch (e) {
        }
    }
}

/**
 * 保存金格文件
 */
function saveMediaFile() {
    //保存DOC金格控件编辑的值
    if (mediaEdits) {
        for (var i = 0; i < mediaEdits.length; i++) {
            if (mediaEdits[i].isNativeUpdate()) {
                mediaEdits[i].WebSave();
            }
        }
    }
    //保存PDF金格控件编辑的值
    if (mediaPdfEdits) {
        for (var i = 0; i < mediaPdfEdits.length; i++) {
            mediaPdfEdits[i].SaveFile();
        }
    }
}

function countStringCode(str) {
    var intvalue = 0, i = 0, len = str.length;
    for (var i = 0; i < len; i++) {
        intvalue += str.charCodeAt(i);
    }
    return intvalue;
}

function formatFileSize(fsize) {
    var filesize = PublicDataType.STRING_EMPTY;
    if (StringUtils.isEmpty(fsize)) {
        return PublicDataType.STRING_EMPTY;
    }
    if (typeof(fsize) == "number") {
        if (fsize >= 1024 * 1024 * 1024) {
            filesize = (fsize / 1024 / 1024 / 1024).toFixed(2) + "G";
        } else if (fsize >= 1024 * 1024) {
            filesize = (fsize / 1024 / 1024).toFixed(2) + "M";
        } else if (fsize >= 1024) {
            filesize = (fsize / 1024).toFixed(2) + "K";
        } else {
            filesize = fsize + "B";
        }
        return filesize
    } else {
        return fsize;
    }
}

/**
 * 日期比较函数，DateOne大于DateTwo返回TURE，否则返回FALSE
 */
function stringToDateObject(strVal) {
    for (var i = strVal.length; i < Constants.DATE_FORMAT_VALUE.length; i++) {
        strVal += Constants.DATE_FORMAT_VALUE[i];
    }
    var D = new Date(0);
    D.setFullYear(strVal.substring(0, 4));
    D.setMonth(strVal.substring(5, 7) - 1);
    D.setDate(strVal.substring(8, 10));
    D.setHours(strVal.substring(11, 13));
    D.setMinutes(strVal.substring(14, 16));
    D.setSeconds(strVal.substring(17, 19));
    return D;
}

function numberToDateString(num) {
    var newTime = new Date(num);
    format = "DYYYY-MM-DD HH:NN:SS";
    return HtmlFormat.dateToFormatString(newTime, format);
}

function compareInputDate(strOne, strTwo) {
    if (typeof(strOne) == "string") {
        strOne = stringToDateObject(strOne)
    }
    if (typeof(strTwo) == "string") {
        strTwo = stringToDateObject(strTwo);
    }
    if (strOne >= strTwo) {
        return true;
    } else {
        return false;
    }
}

function compareTodayDate(strOne) {
    var D = new Date();
    D.setHours(0);
    D.setMinutes(0);
    return compareInputDate(strOne, D);
}

function compareDate(DateOne, DateTwo) {
    return compareInputDate(DateOne, DateTwo);
}

function convertURL(url) {
    if (url == '' || url == 'about:blank') {
        return url;
    }
    if (url == '') {
        return url;
    }
    var url1 = url + '&';
    var r = /[\?\&|]master_key=(\d+)[\&]/ig;
    var master_key_encode = '';
    var a;
    while ((a = r.exec(url1)) != null) {
        master_key_encode += a[1] + ',';
    }
    if (master_key_encode != '') {
        url += '&_MASTERSTAMP=' + sysMasterKeyEncypt(master_key_encode);
    }
    if (url.indexOf('?') < 0) {
        url += '?A' + new Date().getTime() + '=' + new Date().getTime();
    } else {
        url += '&A' + new Date().getTime() + '=' + new Date().getTime();
    }
    return url;
}

/* add by lw. 2014-07-23 
 *  判断字符输入个数（数字英文长度记为1，中文记为2，超过长度自动截取）
 */
function getByteLen(val) { //返回val的字节长度 
    var len = 0;
    for (var i = 0; i < val.length; i++) {
        if (val[i].match(/[^\x00-\xff]/ig) != null) //全角
        // [^\x00-\xff] 匹配双字节字符（一般就像汉字）,ig :i  是指正则忽略大小写 ，g 是指全局正则
        {
            len += 2;
        } else {
            len += 1;
        }
    }
    return len;
}

function getByteVal(val, max) {//返回val在规定字节长度max内的值
    var returnValue = '';
    var byteValLen = 0;
    for (var i = 0; i < val.length; i++) {
        if (val[i].match(/[^\x00-\xff]/ig) != null) {
            byteValLen += 2;
        } else {
            byteValLen += 1;
        }

        if (byteValLen > max) {
            break;
        }
        returnValue += val[i];
    }
    return returnValue;
}

var DateUtils = { //Zero.lu 2016/01/07 添加js时间函数,返回值均为Date类型
    getWeekStartDate: function () {  //获取当前周的第一天
        var now = new Date();
        now.setDate(now.getDate() - now.getDay());
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        return now;
    },
    getWeekEndDate: function () {  //获取当前周的最后一天
        var now = new Date();
        now.setDate(now.getDate() + (6 - now.getDay()));
        now.setHours(23);
        now.setMinutes(59);
        now.setSeconds(59);
        return now;
    },
    getMonthStartDate: function () {  //获取当前月的第一天
        var now = new Date();
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        return now;
    },
    getMonthEndDate: function () {  //获取当前月的最后一天
        var now = new Date();
        now.setDate(this.getMonthDays(now.getMonth()));
        now.setHours(23);
        now.setMinutes(59);
        now.setSeconds(59);
        return now;
    },//获得某月的天数
    getMonthDays: function (month) {
        var now = new Date();
        var monthStartDate = new Date(now.getYear(), month, 1);
        var monthEndDate = new Date(now.getYear(), month + 1, 1);
        return (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
    },
    getQuarterStartDate: function () {
        var now = new Date();
        now.setMonth(this.getQuarterStartMonth());
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        return now;
    },
    getQuarterEndDate: function () {//本季度的停止日期
        var now = new Date();
        var quarterEndMonth = this.getQuarterStartMonth() + 2;
        now.setMonth(quarterEndMonth);
        now.setDate(this.getMonthDays(quarterEndMonth));
        now.setHours(23);
        now.setMinutes(59);
        now.setSeconds(59);
        return now;
    },
    getQuarterStartMonth: function () {
        var month = new Date().getMonth() + 1;
        var quarterStartMonth = 0;
        if (month <= 3) {
            quarterStartMonth = 0;
        } else if (month <= 6) {
            quarterStartMonth = 3;
        } else if (month <= 9) {
            quarterStartMonth = 6;
        } else {
            quarterStartMonth = 9;
        }
        return quarterStartMonth;
    }
};
//

function encodeJsParam(sUrl) {//用来中文字符及特殊字符js转编码
    if (!sUrl) return sUrl;
    var key, val, ret = [];
    var index = sUrl.indexOf("?");
    var urlhead = "";
    var param = "";
    if (index > -1) {
        urlhead = sUrl.substring(0, index + 1)
        param = sUrl.substring(index + 1, sUrl.length);
    } else {
        param = sUrl;
    }
    var params = param.split("&");
    for (var i = 0; i < params.length; i++) {
        var pos = params[i].indexOf('=');
        if (pos == -1) {
            continue;
        } else {
            key = params[i].substring(0, pos);
            val = params[i].substring(pos + 1);
            ret[ret.length] = encodeURIComponent(key) + "=" + encodeURIComponent(val);
        }
    }
    return ret.length > 0 ? urlhead + ret.join('&') : sUrl;
}

/**
 * Url构建功能类，避免字符串拼接混乱
 * 为了保证输入的顺序，用了两个Array
 * var url = new UrlBuilder("xxxxx.jsp");
 * url.add("param1", "loadData");
 * url.add("param2", 21);
 * url.addEncode("param3", "中文");
 *
 * $('#treeFrame').attr('src',url.toString());
 * 得到的结果为: xxxxx.jsp?param1=loadData&param2=21&param3=%E4%B8%AD%E6%96%87
 *
 * @param uri
 * @class This is the basic UrlBuilder class.
 */
function UrlBuilder(uri) {
  var sUri = uri;
  var items = new Array();
  var mapping = new Array();

  /**
   * 设置请求的URL
   * @param uri
   */
  this.setUri = function(uri){
    sUri = uri;
  }
  /**
   * 添加参数（值不转码）
   * @param name
   * @param value
   */
  this.add = function (name, value) {
    items[items.length] = name;
    mapping[name] = value;
  }
  /**
   * 添加参数（值做转码）
   * @param name
   * @param value
   */
  this.addEncode = function (name, value) {
    this.add(name, encodeURIComponent(value));
  }

  /**
   * 清空参数
   */
  this.clear = function () {
    sUri = null;
    items.length = 0;
    mapping.length = 0;
  }

  /**
   * 获取组装后的URL()
   * @returns {string}
   */
  this.toString = function () {
    var i, name, value, url = [];
    for (i = 0; i < items.length; i++) {
      name = items[i];
      value = mapping[name];
      url[url.length] = name + "=" + value;
    }
    return (!!sUri ? sUri + "?" : "") + url.join("&");
  }
}

var numberUtils = {
    isNumberStr : function f(theObj) {
        var reg = /^[0-9]+\.?[0-9]*$/;
        if (reg.test(theObj)) {
            return 1;
        }
        return 0;
    }
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) { //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };

    if(/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }

    return fmt;
}
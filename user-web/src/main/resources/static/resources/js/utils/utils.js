window['getScrollerTop'] = function () {
    return document.body.scrollTop || document.documentElement.scrollTop;
};

window['getClientHeight'] = function () {
    return document.documentElement.clientHeight;
};

window['getClientWidth'] = function () {
    return document.documentElement.clientWidth;
};

window['getTopOf'] = function (elment) {
    var top = 0;
    while (elment) {
        top += elment.offsetTop;
        elment = elment.offsetParent;
    }
    return top;
};

window['getLeftOf'] = function (elment) {
    var left = 0;
    while (elment) {
        left += elment.offsetLeft;

        elment = elment.offsetParent;
    }
    return left;
};

/** String 类扩展 **/
// 兼容IE11（以测试）
if (!String.prototype.startsWith) {
    String.prototype.startsWith = function (str) {
        var reg = new RegExp("^" + str);
        return reg.test(this);
    }
}
/**
 * @description 利用占位符格式化字符串 例如："你好，{0}, {1}".format('aa', 'Nice to meet you!') = "你好，aa Nice to meet you!";
 */
String.prototype.format = function () {
    var s = this, i = arguments.length;

    while (i--) {
        s = s.replace(new RegExp(
            '\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return s;
};

/** array 类的扩展 */
Array.prototype.remove = function (obj) {
    var index = -1;
    for (var i = 0; i < this.length; i++) {
        if (this[i] == obj) {
            index = i;
            break;
        }
    }
    if (index != -1) {
        this.splice(index, 1);
    }
}

// 数组元素去重
Array.prototype.unique = function () {
    var n = {}, r = []; // n为hash表，r为临时数组
    for (var i = 0; i < this.length; i++) // 遍历当前数组
    {
        if (!n[this[i]]) // 如果hash表中没有当前项
        {
            n[this[i]] = true; // 存入hash表
            r.push(this[i]); // 把当前数组的当前项push到临时数组里面
        }
    }
    return r;
}

//数组比较
if (Array.prototype.equals)
    console.warn("Overriding existing Array.prototype.equals. Possible causes: New API defines the method, there's a framework conflict or you've got double inclusions in your code.");
// attach the .equals method to Array's prototype to call it on any array
Array.prototype.equals = function (array) {
    // if the other array is a falsy value, return
    if (!array)
        return false;
    // compare lengths - can save a lot of time
    if (this.length != array.length)
        return false;
    for (var i = 0, l = this.length; i < l; i++) {
        // Check if we have nested arrays
        if (this[i] instanceof Array && array[i] instanceof Array) {
            // recurse into the nested arrays
            if (!this[i].equals(array[i]))
                return false;
        }
        else if (this[i] != array[i]) {
            // Warning - two different object instances will never be equal: {x:20} != {x:20}
            return false;
        }
    }
    return true;
}
// Hide method from for-in loops
Object.defineProperty(Array.prototype, "equals", {enumerable: false});

//对象比较
// Object.prototype.equals = function (object2) {
//     //For the first loop, we only check for types
//     for (propName in this) {
//         //Check for inherited methods and properties - like .equals itself
//         //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/hasOwnProperty
//         //Return false if the return value is different
//         if (this.hasOwnProperty(propName) != object2.hasOwnProperty(propName)) {
//             return false;
//         }
//         //Check instance type
//         else if (typeof this[propName] != typeof object2[propName]) {
//             //Different types => not equal
//             return false;
//         }
//     }
//     //Now a deeper check using other objects property names
//     for (propName in object2) {
//         //We must check instances anyway, there may be a property that only exists in object2
//         //I wonder, if remembering the checked values from the first loop would be faster or not
//         if (this.hasOwnProperty(propName) != object2.hasOwnProperty(propName)) {
//             return false;
//         }
//         else if (typeof this[propName] != typeof object2[propName]) {
//             return false;
//         }
//         //If the property is inherited, do not check any more (it must be equa if both objects inherit it)
//         if (!this.hasOwnProperty(propName))
//             continue;
//         //Now the detail check and recursion
//         //This returns the script back to the array comparing
//         /**REQUIRES Array.equals**/
//         if (this[propName] instanceof Array && object2[propName] instanceof Array) {
//             // recurse into the nested arrays
//             if (!this[propName].equals(object2[propName]))
//                 return false;
//         }
//         else if (this[propName] instanceof Object && object2[propName] instanceof Object) {
//             // recurse into another objects
//             //console.log("Recursing to compare ", this[propName],"with",object2[propName], " both named \""+propName+"\"");
//             if (!this[propName].equals(object2[propName]))
//                 return false;
//         }
//         //Normal value comparison for strings and numbers
//         else if (this[propName] != object2[propName]) {
//             return false;
//         }
//     }
//     //If everything passed, let's say YES
//     return true;
// }

/**时间类的扩展**/

// 时间格式花
Date.prototype.format = function (fmt) {
    if (fmt === undefined) {
        fmt = "yyyy-MM-dd";
    }

    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}
// 时间格式花
Date.prototype.addDay = function (day) {
    var a = this.valueOf();
    a = a + day * 24 * 60 * 60 * 1000;
    a = new Date(a);
    return a;
}
/** number类的扩展 */

/**
 * 四舍五入时，无小数时，不要0。 例如：Number(156).toFixed(1); 输出156.0 ，Number(156).toFixed2(1);输出156
 *
 * @param n
 * @returns {number}
 */
Number.prototype.toFixed2 = function (n) {
    var p = Math.pow(10, n);
    return this.toFixed(n) * p / p;
}


window.Req = {
    // 获取URL参数
    queryString: function (item) {
        var svalue = location.search.match(new RegExp(
            "[\?\&]" + item + "=([^\&]*)(\&?)", "i"));
        return svalue ? svalue[1] : svalue;
    },

    /**
     * @param url
     *          目标url
     * @param arg
     *          需要替换的参数名称
     * @param arg_val
     *          替换后的参数的值
     * @return url 参数替换后的url
     */
    changeURLArg: function (url, arg, arg_val) {
        var pattern = arg + '=([^&]*)';
        var replaceText = arg + '=' + arg_val;
        if (url.match(pattern)) {// 如果没有此参数，添加
            var tmp = '/(' + arg + '=)([^&]*)/gi';
            tmp = url.replace(eval(tmp), replaceText);
            return tmp;
        } else {// 如果有此参数，修改
            if (url.match('[\?]')) {
                return url + '&' + replaceText;
            } else {
                return url + '?' + replaceText;
            }
        }
        return url + '\n' + arg + '\n' + arg_val;
    },

    /**
     * @param url
     *          目标url
     * @param params
     *          参数对象，可是是多个 格式如：[{arg:01,arg_val:11},{arg:00,arg_val:12}]
     */
    changeURLArgs: function (url, params) {
        var lastPattern = "";
        for (var item in params) {
            var pattern = params[item].arg + '=([^&]*)';
            var replaceText = params[item].arg + '=' + params[item].arg_val;
            if (url.match(pattern)) {// 如果没有此参数，添加
                var tmp = '/(' + params[item].arg + '=)([^&]*)/gi';
                tmp = url.replace(eval(tmp), replaceText);
                return tmp;
            } else {// 如果有此参数，修改
                if (url.match('[\?]')) {
                    return url + '&' + replaceText;
                } else {
                    return url + '?' + replaceText;
                }
            }
        }
    },

    /**
     * 去除指定的url参数
     *
     * @param url
     * @param param
     * @returns {*}
     */
    cutURLParam: function (url, param) {
        var url1 = url;
        if (url.indexOf(param) > 0) {
            if (url.indexOf("&", url.indexOf(param) + param.length) > 0) {
                url1 = url.substring(0, url.indexOf(param)) + url.substring(url.indexOf("&", url.indexOf(param) + param.length) + 1);
            } else {
                url1 = url.substring(0, url.indexOf(param) - 1);
            }
            return url1;
        } else {
            return url1;
        }
    },
    redirect: function (url) {
        window.location.href = url;
    }
};

window.CHNUM = {
    SectionToChinese: function (section) {
        var chnNumChar = ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"];
        var chnUnitSection = ["", "万", "亿", "万亿", "亿亿"];
        var chnUnitChar = ["", "十", "百", "千"];
        var strIns = '', chnStr = '';
        var unitPos = 0;
        var zero = true;
        while (section > 0) {
            var v = section % 10;
            if (v === 0) {
                if (!zero) {
                    zero = true;
                    chnStr = chnNumChar[v] + chnStr;
                }
            } else {
                zero = false;
                strIns = chnNumChar[v];
                strIns += chnUnitChar[unitPos];
                chnStr = strIns + chnStr;
            }
            unitPos++;
            section = Math.floor(section / 10);
        }
        return chnStr;
    },
    ChineseToNumber: function (chnStr) {
        var chnNumChar = {
            零: 0,
            一: 1,
            二: 2,
            三: 3,
            四: 4,
            五: 5,
            六: 6,
            七: 7,
            八: 8,
            九: 9
        };

        var chnNameValue = {
            十: {value: 10, secUnit: false},
            百: {value: 100, secUnit: false},
            千: {value: 1000, secUnit: false},
            万: {value: 10000, secUnit: true},
            亿: {value: 100000000, secUnit: true}
        }
        var rtn = 0;
        var section = 0;
        var number = 1;
        var secUnit = false;
        var str = chnStr.split('');

        for (var i = 0; i < str.length; i++) {
            var num = chnNumChar[str[i]];
            if (typeof num !== 'undefined') {
                number = num;
                if (i === str.length - 1) {
                    section += number;
                }
            } else {
                var unit = chnNameValue[str[i]].value;
                secUnit = chnNameValue[str[i]].secUnit;
                if (secUnit) {
                    section = (section + number) * unit;
                    rtn += section;
                    section = 0;
                } else {
                    section += (number * unit);
                }
                number = 1;
            }
        }
        return rtn + section;
    }
};

window.getClazzNameNum = function (clazzName, defaulValue) {
    var re = /[0123456789]+/
    var data = undefined;
    if (re.test(clazzName)) {
        data = re.exec(clazzName)[0];
    }
    if (!data) {
        re = /[一二三四五六七八九十]+/
        if (re.test(clazzName)) {
            data = re.exec(clazzName)[0];
            data = CHNUM.ChineseToNumber(data);
        }
    }
    if (data) {
        return parseInt(data);
    } else {
        return defaulValue ? defaulValue : 99999;
    }
}
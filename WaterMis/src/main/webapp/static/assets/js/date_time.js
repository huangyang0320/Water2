// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd HH:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
 * 针对Ext的工具类
 */
/***
 * 获得当前时间
 */
Date.prototype.getCurrentDate = function () {
    return new Date();
};

/***
 * 获得当前一小时的起止时间
 */
Date.prototype.getCurrentHour = function (date, format) {
    date = date.replace(/-/g, "/");
    var startStop = [];
    var currentDate = date ? new Date(date + ":00:00") : this.getCurrentDate();
    var currentStartDate = new Date(
        currentDate.getFullYear(),
        currentDate.getMonth(),
        currentDate.getDate(),
        currentDate.getHours(), 0, 0);
    var currentEndDate = new Date(
        currentDate.getFullYear(),
        currentDate.getMonth(),
        currentDate.getDate(),
        currentDate.getHours(), 59, 59);
    startStop.push(currentStartDate.Format(format));
    startStop.push(currentEndDate.Format(format));
    return startStop;
};

/***
 * 获得当天的起止时间
 */
Date.prototype.getCurrentDay = function (date, format) {
    var startStop = [];
    date = date.replace(/-/g, "/");
    var currentDate = date ? new Date(date) : this.getCurrentDate();
    var currentStartDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate(), 0, 0, 0);
    var currentEndDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate(), 23, 59, 59);
    startStop.push(currentStartDate.Format(format));
    startStop.push(currentEndDate.Format(format));
    return startStop;
};

/***
 * 获得本周起止时间
 */
Date.prototype.getCurrentWeek = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //返回date是一周中的某一天
    var week = currentDate.getDay();
    //返回date是一个月中的某一天
    var month = currentDate.getDate();

    //一天的毫秒数
    var millisecond = 1000 * 60 * 60 * 24;
    //减去的天数
    var minusDay = week != 0 ? week - 1 : 6;
    //alert(minusDay);
    //本周 周一
    var monday = new Date(currentDate.getTime() - (minusDay * millisecond));
    monday.setHours(0);
    monday.setMinutes(0);
    monday.setSeconds(0);
    //本周 周日
    var sunday = new Date(monday.getTime() + (6 * millisecond));
    sunday.setHours(23);
    sunday.setMinutes(59);
    sunday.setSeconds(59);
    //添加本周时间
    startStop.push(monday.Format("yyyy-MM-dd HH:mm:ss"));//本周起始时间
    //添加本周最后一天时间
    startStop.push(sunday.Format("yyyy-MM-dd HH:mm:ss"));//本周终止时间
    //返回
    return startStop;
};

/***
 * 获得本月的起止时间
 */
Date.prototype.getCurrentMonth = function (date, format) {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = date ? new Date(date) : this.getCurrentDate();
    //获得当前月份0-11
    var currentMonth = currentDate.getMonth();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    //求出本月第一天
    var firstDay = new Date(currentYear, currentMonth, 1);


    //当为12月的时候年份需要加1
    //月份需要更新为0 也就是下一年的第一个月
    if (currentMonth == 11) {
        currentYear++;
        currentMonth = 0;//就为
    } else {
        //否则只是月份增加,以便求的下一月的第一天
        currentMonth++;
    }


    //一天的毫秒数
    var millisecond = 1000 * 60 * 60 * 24;
    //下月的第一天
    var nextMonthDayOne = new Date(currentYear, currentMonth, 1);
    //求出上月的最后一天
    var lastDay = new Date(nextMonthDayOne.getTime() - millisecond);
    lastDay.setHours(23);
    lastDay.setMinutes(59);
    lastDay.setSeconds(59);

    //添加至数组中返回
    startStop.push(firstDay.Format(format));
    startStop.push(lastDay.Format(format));
    //返回
    return startStop;
};

/**
 * 得到本季度开始的月份
 * @param month 需要计算的月份
 ***/
Date.prototype.getQuarterSeasonStartMonth = function (month) {
    var quarterMonthStart = 0;
    var spring = 0; //春
    var summer = 3; //夏
    var fall = 6;   //秋
    var winter = 9;//冬
    //月份从0-11
    if (month < 3) {
        return spring;
    }

    if (month < 6) {
        return summer;
    }

    if (month < 9) {
        return fall;
    }

    return winter;
};

/**
 * 获得该月的天数
 * @param year年份
 * @param month月份
 * */
Date.prototype.getMonthDays = function (year, month) {
    //本月第一天 1-31
    var relativeDate = new Date(year, month, 1);
    //获得当前月份0-11
    var relativeMonth = relativeDate.getMonth();
    //获得当前年份4位年
    var relativeYear = relativeDate.getFullYear();

    //当为12月的时候年份需要加1
    //月份需要更新为0 也就是下一年的第一个月
    if (relativeMonth == 11) {
        relativeYear++;
        relativeMonth = 0;
    } else {
        //否则只是月份增加,以便求的下一月的第一天
        relativeMonth++;
    }
    //一天的毫秒数
    var millisecond = 1000 * 60 * 60 * 24;
    //下月的第一天
    var nextMonthDayOne = new Date(relativeYear, relativeMonth, 1);
    //返回得到上月的最后一天,也就是本月总天数
    return new Date(nextMonthDayOne.getTime() - millisecond).getDate();
};

/**
 * 获得本季度的起止日期
 */
Date.prototype.getCurrentSeason = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前月份0-11
    var currentMonth = currentDate.getMonth();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    //获得本季度开始月份
    var quarterSeasonStartMonth = this.getQuarterSeasonStartMonth(currentMonth);
    //获得本季度结束月份
    var quarterSeasonEndMonth = quarterSeasonStartMonth + 2;

    //获得本季度开始的日期
    var quarterSeasonStartDate = new Date(currentYear, quarterSeasonStartMonth, 1);
    //获得本季度结束的日期
    var quarterSeasonEndDate = new Date(currentYear, quarterSeasonEndMonth, this.getMonthDays(currentYear, quarterSeasonEndMonth));
    //加入数组返回
    startStop.push(quarterSeasonStartDate.Format("yyyy-MM-dd HH:mm:ss"));
    startStop.push(quarterSeasonEndDate.Format("yyyy-MM-dd HH:mm:ss"));
    //返回
    return startStop;
};

/***
 * 得到本年的起止日期
 *
 */
Date.prototype.getCurrentYear = function (date, format) {
    date = date.replace(/-/g, "/");
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = date ? new Date(date) : this.getCurrentDate();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();

    //本年第一天
    var currentYearFirstDate = new Date(currentYear, 0, 1);
    //本年最后一天
    var currentYearLastDate = new Date(currentYear, 11, 31, 23, 59, 59);
    //添加至数组
    startStop.push(currentYearFirstDate.Format(format));
    startStop.push(currentYearLastDate.Format(format));
    //返回
    return startStop;
};

/**
 * 返回上一个月的第一天Date类型
 * @param year 年
 * @param month 月
 **/
Date.prototype.getPriorMonthFirstDay = function (year, month) {
    //年份为0代表,是本年的第一月,所以不能减
    if (month == 0) {
        month = 11;//月份为上年的最后月份
        year--;//年份减1
        return new Date(year, month, 1);
    }
    //否则,只减去月份
    month--;
    return new Date(year, month, 1);
    ;
};

/**
 * 获得上一月的起止日期
 * ***/
Date.prototype.getPreviousMonth = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前月份0-11
    var currentMonth = currentDate.getMonth();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    //获得上一个月的第一天
    var priorMonthFirstDay = this.getPriorMonthFirstDay(currentYear, currentMonth);
    //获得上一月的最后一天
    var priorMonthLastDay = new Date(priorMonthFirstDay.getFullYear(), priorMonthFirstDay.getMonth(), this.getMonthDays(priorMonthFirstDay.getFullYear(), priorMonthFirstDay.getMonth()));
    //添加至数组
    startStop.push(priorMonthFirstDay.Format("yyyy-MM-dd HH:mm:ss"));
    startStop.push(priorMonthLastDay.Format("yyyy-MM-dd HH:mm:ss"));
    //返回
    return startStop;
};


/**
 * 获得上一周的起止日期
 * **/
Date.prototype.getPreviousWeek = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //返回date是一周中的某一天
    var week = currentDate.getDay();
    //返回date是一个月中的某一天
    var month = currentDate.getDate();
    //一天的毫秒数
    var millisecond = 1000 * 60 * 60 * 24;
    //减去的天数
    var minusDay = week != 0 ? week - 1 : 6;
    //获得当前周的第一天
    var currentWeekDayOne = new Date(currentDate.getTime() - (millisecond * minusDay));
    //上周最后一天即本周开始的前一天
    var priorWeekLastDay = new Date(currentWeekDayOne.getTime() - millisecond);
    //上周的第一天
    var priorWeekFirstDay = new Date(priorWeekLastDay.getTime() - (millisecond * 6));

    //添加至数组
    startStop.push(priorWeekFirstDay.Format("yyyy-MM-dd HH:mm:ss"));
    startStop.push(priorWeekLastDay.Format("yyyy-MM-dd HH:mm:ss"));

    return startStop;
};

/**
 * 得到上季度的起始日期
 * year 这个年应该是运算后得到的当前本季度的年份
 * month 这个应该是运算后得到的当前季度的开始月份
 * */
Date.prototype.getPriorSeasonFirstDay = function (year, month) {
    var quarterMonthStart = 0;
    var spring = 0; //春
    var summer = 3; //夏
    var fall = 6;   //秋
    var winter = 9;//冬
    //月份从0-11
    switch (month) {//季度的其实月份
        case spring:
            //如果是第一季度则应该到去年的冬季
            year--;
            month = winter;
            break;
        case summer:
            month = spring;
            break;
        case fall:
            month = summer;
            break;
        case winter:
            month = fall;
            break;

    }
    ;

    return new Date(year, month, 1);
};

/**
 * 得到上季度的起止日期
 * **/
Date.prototype.getPreviousSeason = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前月份0-11
    var currentMonth = currentDate.getMonth();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    //上季度的第一天
    var priorSeasonFirstDay = this.getPriorSeasonFirstDay(currentYear, currentMonth);
    //上季度的最后一天
    var priorSeasonLastDay = new Date(priorSeasonFirstDay.getFullYear(), priorSeasonFirstDay.getMonth() + 2, this.getMonthDays(priorSeasonFirstDay.getFullYear(), priorSeasonFirstDay.getMonth() + 2));
    //添加至数组
    startStop.push(priorSeasonFirstDay.Format("yyyy-MM-dd HH:mm:ss"));
    startStop.push(priorSeasonLastDay.Format("yyyy-MM-dd HH:mm:ss"));
    return startStop;
};

/**
 * 得到去年的起止日期
 * **/
Date.prototype.getPreviousYear = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    currentYear--;
    var priorYearFirstDay = new Date(currentYear, 0, 1);
    var priorYearLastDay = new Date(currentYear, 11, 1, 23, 59, 59);
    //添加至数组
    startStop.push(priorYearFirstDay.Format("yyyy-MM-dd HH:mm:ss"));
    startStop.push(priorYearLastDay.Format("yyyy-MM-dd HH:mm:ss"));
    return startStop;
};

/**
 * Get specified date less than the interval
 * @param date
 * @param interval
 * @param dimen
 */
Date.prototype.lessThanDate = function (date, interval, dimen) {
    date = date.replace(/-/g, "/");
    var format;
    if (dimen == "hour") {
        date = new Date(date + ":00:00");
    } else if (dimen == "month") {
        date = new Date(date + "/01");
    } else {
        date = new Date(date);
    }
    if (dimen == "hour") {
        format = "yyyy-MM-dd HH:00:00";
        date.setHours(date.getHours() - interval);
    } else if (dimen == "day") {
        format = "yyyy-MM-dd 00:00:00";
        date.setDate(date.getDate() - interval);
    } else if (dimen == "month") {
        format = "yyyy-MM-01 00:00:00";
        date.setMonth(date.getMonth() - interval);
    } else {
        format = "yyyy-01-01 00:00:00";
        date.setFullYear(date.getFullYear() - interval);
    }
    return date.Format(format);
};

/**
 * Filter specified date less than the interval
 * @param date
 * @param interval
 * @param dimen
 */
Date.prototype.filterDate = function (date, interval, dimen) {
    date = date.replace(/-/g, "/");
    var format;
    date = new Date(date);
    if (dimen == "hour") {
        format = "yyyy-MM-dd HH";
        date.setHours(date.getHours() - interval);
    } else if (dimen == "day") {
        format = "yyyy-MM-dd";
        date.setDate(date.getDate() - interval);
    } else if (dimen == "month") {
        format = "yyyy-MM";
        date.setMonth(date.getMonth() - interval);
    } else {
        format = "yyyy";
        date.setFullYear(date.getFullYear() - interval);
    }
    return date.Format(format);
};

Date.prototype.getDateKey = function (date, dimen) {
    date = date.replace(/-/g, "/");
    var format;
    date = new Date(date);
    if (dimen == "hour") {
        format = "mm";
    } else if (dimen == "day") {
        format = "HH";
    } else if (dimen == "month") {
        format = "dd";
    } else {
        format = "MM";
    }
    return date.Format(format);
};

Date.prototype.allDate = function (startDate, endDate, dimen) {
    startDate = startDate.replace(/-/g, "/");
    endDate = endDate.replace(/-/g, "/");
    var result = [];
    startDate = new Date(startDate);
    endDate = new Date(endDate);
    if (dimen == "hour") {
        format = "yyyy-MM-dd HH:mm";
    } else if (dimen == "day") {
        format = "yyyy-MM-dd HH";
    } else if (dimen == "month") {
        format = "yyyy-MM-dd";
    } else {
        format = "yyyy-MM";
    }
    result.push(startDate.Format(format));
    while (startDate.getTime() < endDate.getTime()) {
        if (dimen == "hour") {
            startDate.setMinutes(startDate.getMinutes() + 1);
            format = "yyyy-MM-dd HH:mm";
        } else if (dimen == "day") {
            startDate.setHours(startDate.getHours() + 1);
            format = "yyyy-MM-dd HH";
        } else if (dimen == "month") {
            startDate.setDate(startDate.getDate() + 1);
            format = "yyyy-MM-dd";
        } else {
            startDate.setMonth(startDate.getMonth() + 1);
            format = "yyyy-MM";
        }
        result.push(startDate.Format(format));
    }
    return result;
};

Date.prototype.formatByDimen = function (formDate, dimen) {
    var date, format;

    date = new Date().lessThanDate(formDate,  0, dimen);
    date = new Date(date.replace(/-/g, "/"));
    if (dimen == "hour") {
        format = "yyyy年MM月dd日 HH时";
    } else if (dimen == "day") {
        format = "yyyy年MM月dd日";
    } else if (dimen == "month") {
        format = "yyyy年MM月";
    } else {
        format = "yyyy年";
    }
    return date.Format(format);
};

/* 转化城时间戳 */
Date.prototype.getTimeStamp = function (date) {
    date = new Date(Date.parse(date.replace(/-/g, "/")));
    return  date.getTime();
}
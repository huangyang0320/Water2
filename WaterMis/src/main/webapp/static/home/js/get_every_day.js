/*

	/!**
	 * 首先了解一下JavaScript中Date类的一些方法
	 * @type {Date}
	 *!/
	var myDate = new Date();
	myDate.getYear();        //获取当前年份(2位)
	myDate.getFullYear();    //获取完整的年份(4位,1970-????)
	myDate.getMonth();       //获取当前月份(0-11,0代表1月)
	myDate.getDate();        //获取当前日(1-31)
	myDate.getDay();         //获取当前星期X(0-6,0代表星期天)
	myDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
	myDate.getHours();       //获取当前小时数(0-23)
	myDate.getMinutes();     //获取当前分钟数(0-59)
	myDate.getSeconds();     //获取当前秒数(0-59)
	myDate.getMilliseconds();    //获取当前毫秒数(0-999)
	myDate.toLocaleDateString();     //获取当前日期
	var mytime=myDate.toLocaleTimeString();     //获取当前时间
	myDate.toLocaleString( );        //获取日期与时间
*/

	/**
	 * 通过这些方法来实现获取当月天数的功能
	 */

	/*获取一个月的天数 */
	function getCountDays() {
		var curDate = new Date();
		/* 获取当前月份 */
		var curMonth = curDate.getMonth();
		/*  生成实际的月份: 由于curMonth会比实际月份小1, 故需加1 */
		curDate.setMonth(curMonth + 1);
		/* 将日期设置为0, 这里为什么要这样设置, 我不知道原因, 这是从网上学来的 */
		curDate.setDate(0);
		/* 返回当月的天数 */
		return curDate.getDate();
	}

	/**
	 * 在通过数组来添加天数
	 * @returns {Array}
	 */
	function getEveryDay(){
		var myDate = new Date();
		var day=getCountDays();
		var dayArry=[];
		for (var k = 1; k <= day; k++) {
			if(k<=myDate.getDate()){
				dayArry.push(k);
			}

		}
		return dayArry;
}

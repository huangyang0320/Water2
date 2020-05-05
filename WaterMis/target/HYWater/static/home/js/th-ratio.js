
$(function () {
	traSuppPumpStat();
})

function traSuppPumpStat() {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/homeV2/getUseWaterDayData' + "?"
			+ Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length < 1) {
				return;
			} else {
				$("#lastYearAvg").html(data.lastYearMonthAvgDay);
				$("#lastMonthAvg").html(data.lastMonthAvgDay)
				$("#cureentDayAvg").html(data.currentMonthAvgDay)


				$("#lastYearPower").html(data.lastYearPower);
				$("#lastPower").html(data.lastPower)
				$("#currentPower").html(data.currentPower)
				$("#hRate").html(data.hRate);
				// 电 同比/环比
				// 同比增长率=（本期数-同期数）/|同期数|×100%
				let tRated = (data.currentPower.split(' ')[0] - data.lastYearPower.split(' ')[0]) / data.lastYearPower.split(' ')[0]
                // 环比增长率=（本期数-上期数）/上期数×100%
                let hRated = (data.currentPower.split(' ')[0] - data.lastPower.split(' ')[0]) / data.lastPower.split(' ')[0]

				if(tRated >= 0) {
                    $("#tbd").attr("src","static/home/images/iconup.png");
                    $("#tRated").html((tRated*100).toFixed(0));
				} else {
                    $("#tRated").html((tRated*100).toFixed(0));
				}
                if(hRated>=0){
                    $("#hbd").attr("src","static/home/images/iconup.png");
                    $("#hRated").html((hRated*100).toFixed(0));
                }else{
                    $("#hRated").html((hRated*100).toFixed(0));
                }
                // 水 同比/环比
				if(data.tRate>=0){
					$("#tb").attr("src","static/home/images/iconup.png");
					$("#tRate").html((data.tRate*100).toFixed(0));
				}else{
					$("#tRate").html((((data.tRate).replace("-",""))*100).toFixed(0));
				}
				if(data.hRate>=0){
					$("#hb").attr("src","static/home/images/iconup.png");
					$("#hRate").html((data.hRate*100).toFixed(0));
				}else{
					$("#hRate").html((((data.hRate).replace("-",""))*100).toFixed(0));
				}
			}
		},
		error : function(data) {

		}
	});
}



		
		
		


		










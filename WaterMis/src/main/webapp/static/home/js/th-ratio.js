
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

				if(data.tRate>=0){
					$("#tb").attr("src","static/home/images/iconup.png");
					$("#tRate").html((data.tRate*100).toFixed(0)+"%");
				}else{
					$("#tRate").html((((data.tRate).replace("-",""))*100).toFixed(0)+"%");
				}
				if(data.hRate>=0){
					$("#hb").attr("src","static/home/images/iconup.png");
					$("#hRate").html((data.hRate*100).toFixed(0)+"%");
				}else{
					$("#hRate").html((((data.hRate).replace("-",""))*100).toFixed(0)+"%");
				}
			}
		},
		error : function(data) {

		}
	});
}



		
		
		


		










function resizeFullBak() {
	var ratioX = $(window).width()/$('body').width();
	var ratioY = $(window).height()/$('body').height();
	$('body').css({
		transform: "scale("+ratioX+", "+ratioY+")",
		transformOrigin: "left top 0px"
	});
}

resizeFullBak();

$(window).resize(function() {
	resizeFullBak();
});
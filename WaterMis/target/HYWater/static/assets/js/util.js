function dateFormatBars(strTime) {
	if(isEmp(strTime)){
		return "-";
	}
    var date = new Date(strTime);
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate() + " " +date.getHours() + ":" + date.getMinutes() +":" + date.getSeconds();
}

function dateFormatEmp(strTime) {
	if(isEmp(strTime)){
		return "";
	}
    var date = new Date(strTime);
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate() + " " +date.getHours() + ":" + date.getMinutes() +":" + date.getSeconds();
}
function isEmp(str){

   	if(undefined == str || null == str || "" == str){
   		return true;
   	}else{
   		return false;
   	}
 }
function toTrim(str){
 	if(undefined == str || null == str){
 		return "-";
 	}
 	return str;
 }
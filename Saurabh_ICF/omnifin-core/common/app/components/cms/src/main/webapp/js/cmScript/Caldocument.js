//---------------------------------------------------CALENDAR-------------------------------------
var z = document.getElementById('documentVal').value ;
$(function() {
	var contextPath =document.getElementById('contextPath').value ;

//	alert("recievedDate"+i);
	for(i=0;i<=z;i++){
	$("#recivDate"+i).datepicker({
	format: "%Y-%m-%d %H:%i:%s %E %#",
	formatUtcOffset: "%: (%@)",
	placement: "inline",

		changeMonth: true,
		changeYear: true,
		yearRange: '1900:+10',
		showOn: 'both',
		buttonImage: contextPath+'/images/theme1/calendar.gif',
	buttonImageOnly: true,
    dateFormat: document.getElementById("formatD").value,
	defaultDate: document.getElementById("bDate").value

	});
	}
});

$(function() {
	var contextPath =document.getElementById('contextPath').value ;

//	alert("deferedDate"+i);
	for(i=0;i<=z;i++){
	$("#deferedDate"+i).datepicker({
	format: "%Y-%m-%d %H:%i:%s %E %#",
	formatUtcOffset: "%: (%@)",
	placement: "inline",

		changeMonth: true,
		changeYear: true,
		yearRange: '1900:+10',
		showOn: 'both',
		buttonImage: contextPath+'/images/theme1/calendar.gif',
	buttonImageOnly: true,
    dateFormat: document.getElementById("formatD").value,
	defaultDate: document.getElementById("bDate").value

	});
	}
});

$(function() {
	var contextPath =document.getElementById('contextPath').value ;

	for(i=0;i<=z;i++){
		$("#expiryDate"+i).datepicker({
		format: "%Y-%m-%d %H:%i:%s %E %#",
		formatUtcOffset: "%: (%@)",
		placement: "inline",

			changeMonth: true,
			changeYear: true,
			yearRange: '1900:+10',
			showOn: 'both',
			buttonImage: contextPath+'/images/theme1/calendar.gif',
			buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("bDate").value

	});
	}
});


function makeChoice(str){

	var status = 'status'+str;
	var deferedDate = 'deferedDate'+str;
	var recievedDate = 'recivDate'+str;
	var def = 'def'+str;
	var rec = 'rec'+str;
	//alert(str);
	if(document.getElementById(status).value == 'R'){
		document.getElementById(rec).innerHTML = "<input name='recievedDate' type='text' class='text6' id='recievedDate"+str+"' value=''  maxlength='10'>";
		document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' value='' type='text' class='text6'>";
		$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			$("#recivDate"+str).datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
 			changeMonth: true,
 			changeYear: true,
 			yearRange: '1900:+10',
 			showOn: 'both',
 			buttonImage: contextPath+'/images/theme1/calendar.gif',
			buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("bDate").value

			});
		});
	}else if(document.getElementById(status).value == 'D'){	
	//alert("In  d");
		document.getElementById(rec).innerHTML = "<input name='recievedDate' readonly='readonly' value='' type='text' class='text6'>";
		document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' type='text' class='text6' id='deferedDate"+str+"' value=''   maxlength='10'>";
			$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			$("#deferedDate"+str).datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
 			changeMonth: true,
 			changeYear: true,
 			yearRange: '1900:+10',
 			showOn: 'both',
 			buttonImage: contextPath+'/images/theme1/calendar.gif',
			buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("bDate").value
			});
		});
	
	
	}else {
	       document.getElementById(rec).innerHTML = "<input name='recievedDate' readonly='readonly' type='text' class='text6' id='recievedDate"+str+"' value=''   maxlength='10'>";
	       document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' type='text' class='text6' id='deferedDate"+str+"' value=''   maxlength='10'>";
			$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			$("#deferedDate"+str).datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
 			changeMonth: true,
 			changeYear: true,
 			yearRange: '1900:+10',
 			showOn: 'both',
 			buttonImage: contextPath+'/images/theme1/calendar.gif',
			buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("bDate").value

			});
		});
	
	    $(function() {
			var contextPath =document.getElementById('contextPath').value ;
			$("#recievedDate"+str).datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
 			changeMonth: true,
 			changeYear: true,
 			yearRange: '1900:+10',
 			showOn: 'both',
 			buttonImage: contextPath+'/images/theme1/calendar.gif',
			buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("bDate").value

			});
		});
		//document.getElementById(deferedDate).style.display = 'block';
		//document.getElementById(recievedDate).style.display = 'block';
	}
	
}




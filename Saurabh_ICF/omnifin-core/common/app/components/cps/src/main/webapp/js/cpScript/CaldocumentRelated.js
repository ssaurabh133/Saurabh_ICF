//---------------------------------------------------CALENDAR-------------------------------------
var z = document.getElementById('documentVal').value ;
$(function() {
	var contextPath =document.getElementById('contextPath').value ;

//	alert("recievedDate"+i);
	for(i=0;i<=z;i++){
	//$("#recievedDate"+i).datepicker({
	$("#recievedDateBussiness"+i).datepicker({
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

	//alert(str);
	var status = 'status'+str;
	//alert("status:------"+status);
	var deferedDate = 'deferedDate'+str;
	//alert("deferedDate:------"+deferedDate);
	var recievedDate = 'recievedDate'+str;
	//alert("recievedDate:------"+recievedDate);
	var def = 'def'+str;
	//alert("def:------"+def);
	var rec = 'rec'+str;
	//alert("rec:------"+rec);
	var exp='exp'+str;
	//alert("exp:------"+rec);
	var flagE='flagExp'+str;
	
	var businessDate=document.getElementById("businessdate").value;
	//alert("flagE:------"+rec);
	//alert(document.getElementById(flagE).value);
	if(document.getElementById(status).value == 'R'){
		//document.getElementById(rec).innerHTML = "<input name='recievedDate' type='text' class='text6' id='recievedDate"+str+"' value=''  maxlength='10' onchange=\"return checkDate('recievedDate"+str+"');\" >";
		document.getElementById(rec).innerHTML = "<input name='recievedDate' type='text' class='text6' id='recievedDate"+str+"' value='"+businessDate+"'  readonly='readonly' >"; 
		document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' value='' type='text' class='text6'>";
		if(document.getElementById(flagE).value =='Y')
		{
			document.getElementById(exp).innerHTML = "<input name='expiryDate' id='expiryDate"+str+"' value='' type='text' class='text6' onchange=\"return checkDate('expiryDate"+str+"');\" >";
		}
		else
		{
			document.getElementById(exp).innerHTML = "<input name='expiryDate' readonly='readonly' id='expirDate' value='' type='text' class='text6' >";
		}
		$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			//$("#recievedDate"+i).datepicker({
			$("#recievedDateBussiness"+i).datepicker({
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
		if(document.getElementById(flagE).value=='Y')
		{
			$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			$("#expiryDate"+str).datepicker({
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
		}
		
	}else if(document.getElementById(status).value == 'D'){	
	   //alert("In  d");
		document.getElementById(rec).innerHTML = "<input name='recievedDate' readonly='readonly' value='' type='text' class='text6'>";
		document.getElementById(def).innerHTML = "<input name='deferedDate'  type='text' class='text6' id='deferedDate"+str+"' value=''   maxlength='10' onchange=\"return checkDate('deferedDate"+str+"');\" >";
		document.getElementById(exp).innerHTML = "<input name='expiryDate' readonly='readonly' value='' type='text' class='text6'>";
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
	
	
	}else if(document.getElementById(status).value == 'L'){
//		alert("IN L");
//	       document.getElementById(rec).innerHTML = "<input name='recievedDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
//	       document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
//	       document.getElementById(exp).innerHTML = "<input name='expiryDate' readonly='readonly' value='' type='text' class='text6'>";
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
	}else {
	       document.getElementById(rec).innerHTML = "<input name='recievedDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
	       document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
	       document.getElementById(exp).innerHTML = "<input name='expiryDate' readonly='readonly' value='' type='text' class='text6'>";
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

// Changes By Amit Starts
function makeChoiceAdditional(str){

	var psize = str-1;
	var additionalDocStatus = 'additionalDocStatus'+psize;
	//alert("additionalDocStatus:------"+additionalDocStatus);
	var additionalDeferredDate = 'additionalDeferredDate'+psize;
	//alert("additionalDeferredDate:------"+additionalDeferredDate);
	var additionalReceivedDate = 'additionalReceivedDate'+psize;
	//alert("additionalReceivedDate:------"+additionalReceivedDate);
	var additionalExpiryDate = 'additionalExpiryDate'+psize;
	//alert("additionalExpiryDate:------"+additionalExpiryDate);
	var addDef = 'addDef'+psize;
	//alert("addDef:------"+addDef);
	var addRec = 'addRec'+psize;
	//alert("addRec:------"+addRec);
	var addExp='addExp'+psize;
	//alert("addExp:------"+addExp);
	// var flagE='flagExp'+str;
	
	var businessDate=document.getElementById("businessdate").value;

	if(document.getElementById(additionalDocStatus).value == 'R'){
		//document.getElementById(rec).innerHTML = "<input name='recievedDate' type='text' class='text6' id='recievedDate"+str+"' value=''  maxlength='10' onchange=\"return checkDate('recievedDate"+str+"');\" >";
		document.getElementById(addRec).innerHTML = "<input name='additionalReceivedDate' type='text' class='text6' id='additionalReceivedDate"+psize+"' value='"+businessDate+"'  readonly='readonly' >"; 
		document.getElementById(addDef).innerHTML = "<input name='additionalDeferredDate' readonly='readonly' value='' type='text' class='text6'>";
		document.getElementById(addExp).innerHTML = "<input name='additionalExpiryDate'  type='text' class='text6' id='additionalExpiryDate"+psize+"' value=''   maxlength='10' onchange=\"return checkDate('additionalExpiryDate"+psize+"');\" >";
		
		$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			//$("#recievedDate"+i).datepicker({
			$("#recievedDateBussiness"+i).datepicker({
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
			$("#additionalExpiryDate"+psize).datepicker({
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
		
	}else if(document.getElementById(additionalDocStatus).value == 'D'){	
	   //alert("In  d");
		document.getElementById(addRec).innerHTML = "<input name='additionalReceivedDate' readonly='readonly' value='' type='text' class='text6'>";
		document.getElementById(addDef).innerHTML = "<input name='additionalDeferredDate'  type='text' class='text6' id='additionalDeferredDate"+psize+"' value=''   maxlength='10' onchange=\"return checkDate('additionalDeferredDate"+psize+"');\" >";
		document.getElementById(addExp).innerHTML = "<input name='additionalExpiryDate' readonly='readonly' value='' type='text' class='text6'>";
			$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			$("#additionalDeferredDate"+psize).datepicker({
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
	
	
	}else if(document.getElementById(additionalDocStatus).value == 'L'){
//		alert("IN L");
//	       document.getElementById(rec).innerHTML = "<input name='recievedDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
//	       document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
//	       document.getElementById(exp).innerHTML = "<input name='expiryDate' readonly='readonly' value='' type='text' class='text6'>";
			$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			$("#additionalDeferredDate"+psize).datepicker({
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
			$("#additionalReceivedDate"+psize).datepicker({
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
	}else if(document.getElementById(additionalDocStatus).value == 'W'){ 
	       document.getElementById(addRec).innerHTML = "<input name='additionalReceivedDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
	       document.getElementById(addDef).innerHTML = "<input name='additionalDeferredDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
	       document.getElementById(addExp).innerHTML = "<input name='additionalExpiryDate' readonly='readonly' type='text' class='text6' value=''   maxlength='10' >";
			$(function() {
			var contextPath =document.getElementById('contextPath').value ;
			$("#additionalExpiryDate"+i).datepicker({
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
			$("#additionalReceivedDate"+i).datepicker({
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
	else
	{
	document.getElementById(addRec).innerHTML = "<input name='additionalReceivedDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
    document.getElementById(addDef).innerHTML = "<input name='additionalDeferredDate' readonly='readonly' type='text' class='text6'  value=''   maxlength='10'>";
    //document.getElementById(addExp).innerHTML = "<input name='additionalExpiryDate'  type='text' class='text6' id='additionalExpiryDate"+psize+"' value=''   maxlength='10' onchange=\"return checkDate('additionalExpiryDate"+psize+"');\" >";
    document.getElementById(addExp).innerHTML = "<input name='additionalExpiryDate'  type='text' class='text6' id='additionalExpiryDate"+psize+"' value='' readonly='readonly' >";
//		$(function() {
//		var contextPath =document.getElementById('contextPath').value ;
//		$("#additionalExpiryDate"+psize).datepicker({
//		format: "%Y-%m-%d %H:%i:%s %E %#",
//		formatUtcOffset: "%: (%@)",
//		placement: "inline",
//		changeMonth: true,
//		changeYear: true,
//		yearRange: '1900:+10',
//		showOn: 'both',
//		buttonImage: contextPath+'/images/theme1/calendar.gif',
//		buttonImageOnly: true,
//     dateFormat: document.getElementById("formatD").value,
//		defaultDate: document.getElementById("bDate").value
//		});
//	});

 $(function() {
		var contextPath =document.getElementById('contextPath').value ;
		$("#additionalReceivedDate"+i).datepicker({
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
	}
}

// Changes by Amit Ends
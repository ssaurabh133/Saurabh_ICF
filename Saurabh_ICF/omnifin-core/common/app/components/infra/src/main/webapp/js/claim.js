		$(function() {
		$("#tabs").tabs();
	});
    
    
    	$(function() {
		$("#datepicker").datepicker({
			changeMonth: true,
			changeYear: true,
            yearRange: '1950:2010',
            showOn: 'both',
			buttonImage: 'images/calendar.gif',
			buttonImageOnly: true
	

		});

	});
	$(function() {
		$("#d_datepicker").datepicker({
			changeMonth: true,
			changeYear: true,
            yearRange: '1950:2010',
            showOn: 'both',
			buttonImage: 'images/calendar.gif',
			buttonImageOnly: true
	

		});

	});
	<!--
function popitup(url) {
	newwindow=window.open(url,'name','height=370,width=850,top=200,left=100');
	if (window.focus) {newwindow.focus()}
	return false;
}
function isNumberKey(evt) {
         //alert(event.keyCode);
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		alert("Only Numeric allowed here");
		return false;
	}
	return true;
}
function displaybasic(obj,tr,challan,dd) {
	
txt = obj.options[obj.selectedIndex].value;
//alert(txt);
if (txt) {
document.getElementById('val').innerHTML =txt;
document.getElementById('val2').innerHTML =txt;
}
if (txt=='TR5') {
document.getElementById('val2').innerHTML ='Treasury';
}
if (txt=='DD') {
document.getElementById('val2').innerHTML ='Bank';
}
if (txt=='CHALLAN') {
document.getElementById('val2').innerHTML ='Office';
}

}

function display(obj,id1,id2) {
//alert(obj);	
txt = obj.options[obj.selectedIndex].value;
//alert(txt);

document.getElementById(id1).style.display = 'none';
document.getElementById(id2).style.display = 'none';
document.getElementById(id2).style.display = 'none';
document.getElementById("nominee").style.display = 'none';
document.getElementById("death_date").style.display = 'none';
 document.getElementById("t2").style.display = 'none';
if(txt=='2')
{
	document.getElementById(id1).innerHTML = 'Date of Death';
	document.getElementById(id1).style.display = '';
	document.getElementById("death_date").style.display = '';
	document.getElementById("nominee").style.display = '';
    document.getElementById("t2").style.display = '';
}
if(txt=='3')
{
	document.getElementById(id1).innerHTML = 'Paid Amount';
	document.getElementById(id1).style.display = '';
	document.getElementById(id2).style.display = '';
}
if(txt=='4')
{
	document.getElementById(id1).innerHTML = 'Surrender Amount';
	document.getElementById(id1).style.display = '';
	document.getElementById(id2).style.display = '';
}
if(txt=='5')
{
	document.getElementById(id1).innerHTML = 'Discount Amount';
	document.getElementById(id1).style.display = '';
	document.getElementById(id2).style.display = '';
}
}
function calculate(risk_date)
{
var myVar = document.getElementById(risk_date).value ;
//alert(myVar);

var user_date = Date.parse(myVar);
var today_date = new Date();
var diff_date =  today_date - user_date;

var num_years = diff_date/31536000000;
var num_months = (diff_date % 31536000000)/2628000000;
var num_days = ((diff_date % 31536000000) % 2628000000)/86400000;

//document.write("Number of years: " + Math.floor(num_years) + "<br>");
//document.write("Number of months: " + Math.floor(num_months) + "<br>");
//document.write("Number of days: " + Math.floor(num_days) + "<br>");
if(Math.floor(num_months)>'11')
{
//alert('no discount');	
}else {
//alert('discount');
	document.getElementById('discount').style.display = '';	
}
}
// -->



			// Sample 2
			$(document).ready(function()
			{
				$('.text2').blur(function()
				{
					$('.text2').formatCurrency();
				});
			}); 
    	$(function() {
		$("#tabs").tabs();
        
        	});
   
    
	$(function() {
		$("#risk_datepicker").datepicker({
			changeMonth: true,
			changeYear: true,
            yearRange: '1950:2010',
            showOn: 'both',
			buttonImage: 'images/calendar.gif',
			buttonImageOnly: true
		
		});

	});
	$(function() {
		$("#datepicker").datepicker({
                  showOn: 'both',
			buttonImage: 'images/calendar.gif',
			buttonImageOnly: true
			

			

		});

	});
		$(function() {
		$("#maturity_datepicker").datepicker({
			changeMonth: true,
			changeYear: true,
            yearRange: '1950:2010',
            showOn: 'both',
			buttonImage: 'images/calendar.gif',
			buttonImageOnly: true
	

		});

	});
		$(function() {
		$("#dob_datepicker").datepicker({
		 	changeMonth: true,
			changeYear: true,
            yearRange: '1950:2010',
            showOn: 'both',
			buttonImage: 'images/calendar.gif',
			buttonImageOnly: true
	

		});
		

	});
	$(function() {
		$("#join_datepicker").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1950:2070',
            showOn: 'both',
			buttonImage: 'images/calendar.gif',
			buttonImageOnly: true


		});
			});
		$(function() {
		$("#retire_datepicker").datepicker({
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
	newwindow=window.open(url,'name','height=250,width=850,top=200,left=100');
	if (window.focus) {newwindow.focus()}
	return false;
}
function displaypolicyselect(id) {
  var value = document.getElementById(id).value ;
  //alert(value);
  if (value=='') {
    alert("Please Enter the value for search. ");
    }else {
        
    
document.getElementById("select_policyno").style.display = '';
document.getElementById("select_policy").style.display = '';
  }  
}
function displaypolicy(obj) {
	
txt = obj.options[obj.selectedIndex].value;
//alert(txt);
document.getElementById("approve").style.display = '';
//document.getElementById("edit").style.display = '';
if (txt !='') {
if (txt=='3') {
document.getElementById("approve").style.display = 'none';
//document.getElementById("edit").style.display = 'none';
}else
{
    document.getElementById("approve").style.display = 'none';
}
} }
function display(obj,tr,challan,dd) {
	
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
function check(sum_assured) {
value = document.getElementById('sum_assured').value;
if(value=='')
{
document.xx.processingoffice.disabled=false ;
}else {
	

if( value >= 10000)
{
	
	var xx = document.xx.processingoffice;
document.xx.processingoffice.selectedIndex = 0;
	document.xx.processingoffice.disabled=true ;
}else{
	document.xx.processingoffice.selectedIndex = 1;
	document.xx.processingoffice.disabled=true ;
}	
}	

}

function able() {
	document.xx.fname.disabled=true ;
document.xx.lname.disabled=true ;
document.xx.gender.disabled=true ;
document.xx.dob_datepicker.disabled=true ;
document.xx.phone.disabled=true ;
document.xx.mobile.disabled=true ;
document.xx.pan.disabled=true ;
document.xx.marital.disabled=true ;
document.xx.email.disabled=true ;
document.xx.resid_address.disabled=true ;
document.xx.district.disabled=true ;
document.xx.department.disabled=true ;
document.xx.designation.disabled=true ;
document.xx.join_datepicker.disabled=true ;
document.xx.retirementage.disabled=true ;
document.xx.retire_datepicker.disabled=true ;
document.xx.officeaddress.disabled=true ;
document.xx.basicpay.disabled=true ;
document.xx.risk_datepicker.disabled=true ;
document.xx.modeofpayment.disabled=true ;
document.xx.receipt.disabled=true ;
document.xx.receiptfrom.disabled=true ;
document.xx.maturity_datepicker.disabled=true ;
document.xx.premium.disabled=true ;
document.xx.sum_assured.disabled=true ;
document.xx.processingoffice.disabled=true ;
document.xx.paymentfreq.disabled=true ;

}
function disable(id) {
	//alert(id);
	value = document.getElementById(id).value;
	if(value == '')
	{
		document.xx.fname.disabled=false ;
document.xx.lname.disabled=false ;
document.xx.gender.disabled=false ;
document.xx.dob_datepicker.disabled=false ;
document.xx.phone.disabled=false ;
document.xx.mobile.disabled=false ;
document.xx.pan.disabled=false ;
document.xx.marital.disabled=false ;
document.xx.email.disabled=false ;
document.xx.resid_address.disabled=false ;
document.xx.resid_address2.disabled=false ;
document.xx.district.disabled=false ;
document.xx.department.disabled=false ;
document.xx.designation.disabled=false ;
document.xx.join_datepicker.disabled=false ;
document.xx.retirementage.disabled=false ;
document.xx.retire_datepicker.disabled=false ;
document.xx.officeaddress.disabled=false ;
document.xx.basicpay.disabled=false ;
document.xx.risk_datepicker.disabled=false ;
document.xx.modeofpayment.disabled=false ;
document.xx.receipt.disabled=false ;
document.xx.receiptfrom.disabled=false ;
document.xx.maturity_datepicker.disabled=false ;
document.xx.premium.disabled=false ;
document.xx.sum_assured.disabled=false ;
document.xx.processingoffice.disabled=false ;
document.xx.paymentfreq.disabled=false ;


}
}
function turnRed() {
	var color ="#FCBDB6";
var myfname = document.getElementById("fname");
fnamevalue = document.getElementById("fname").value;
var mylname = document.getElementById("lname");
lnamevalue = document.getElementById("lname").value;
var mydob = document.getElementById("dob_datepicker");
dobvalue = document.getElementById("dob_datepicker").value;
var myresid_address = document.getElementById("resid_address");
resid_address_value = document.getElementById("resid_address").value;
var myresid_address = document.getElementById("resid_address2");
resid_address_value = document.getElementById("resid_address2").value;
var myretiredate = document.getElementById("retire_datepicker");
retiredate_value = document.getElementById("retire_datepicker").value;
var mybasicpay = document.getElementById("basicpay");
basicpay_value = document.getElementById("basicpay").value;
var mymodeofpayment = document.getElementById("modeofpayment");
modeofpayment_value = document.getElementById("modeofpayment").value;
var myreceipt = document.getElementById("receipt");
receipt_value = document.getElementById("receipt").value;
var mymaturity_date = document.getElementById("maturity_datepicker");
maturity_date_value = document.getElementById("maturity_datepicker").value;
var gender = document.getElementById("gender");
gender_value = document.getElementById("gender").value;
var pan = document.getElementById("pan");
pan_value = document.getElementById("pan").value; 
var officeaddress1 = document.getElementById("officeaddress1");
officeaddress1_value = document.getElementById("officeaddress1").value;
var officeaddress2 = document.getElementById("officeaddress2");
officeaddress2_value = document.getElementById("officeaddress2").value;
var join_datepicker = document.getElementById("join_datepicker");
join_datepicker_value = document.getElementById("join_datepicker").value;
var gender = document.getElementById("gender");
gender_value = document.getElementById("gender").value;
var retirementage = document.getElementById("retirementage");
retirementage_value = document.getElementById("retirementage").value;
var district = document.getElementById("district");
district_value = document.getElementById("district").value;
var designation = document.getElementById("designation");
designation_value = document.getElementById("designation").value; 
var department = document.getElementById("department");
department_value = document.getElementById("department").value;  
var marital = document.getElementById("marital");
marital_value = document.getElementById("marital").value; 
var risk_datepicker = document.getElementById("risk_datepicker");
risk_datepicker_value = document.getElementById("risk_datepicker").value; 
var fileno = document.getElementById("fileno");
fileno_value = document.getElementById("fileno").value; 
var modeofpayment = document.getElementById("modeofpayment");
modeofpayment_value = document.getElementById("modeofpayment").value; 
var receipt = document.getElementById("receipt");
receipt_value = document.getElementById("receipt").value; 
var premium = document.getElementById("premium");
premium_value = document.getElementById("premium").value; 
var receiptfrom = document.getElementById("receiptfrom");
receiptfrom_value = document.getElementById("receiptfrom").value; 
var sum_assured = document.getElementById("sum_assured");
sum_assured_value = document.getElementById("sum_assured").value; 
//var processingoffice = document.getElementById("processingoffice");
//processingoffice_value = document.getElementById("processingoffice").value; 
var paymentfreq = document.getElementById("paymentfreq");
paymentfreq_value = document.getElementById("paymentfreq").value; 
var payrange = document.getElementById("payrange");
payrange_value = document.getElementById("payrange").value; 
var dep_district = document.getElementById("dep_district");
dep_district_value = document.getElementById("dep_district").value; 
var ermsg= 'Please enter the mandatory fields which are Red in all tabs.  ';

  document.getElementById('errormsg').innerHTML =ermsg;
  document.getElementById('errormsg').style.color  = '#FF0000';
  document.getElementById('errormsg2').innerHTML =ermsg;
  document.getElementById('errormsg2').style.color  = '#FF0000';
  document.getElementById('errormsg3').innerHTML =ermsg;
  document.getElementById('errormsg3').style.color  = '#FF0000';


if(fnamevalue=='')
{
document.getElementById("errormsg").style.display = '';
myfname.style.backgroundColor  = color;
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
}

if(lnamevalue=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
mylname.style.backgroundColor  = color;
}
if(dobvalue=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
mydob.style.backgroundColor  = color;
}
if(resid_address_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
myresid_address.style.backgroundColor  = color;
}
if(retiredate_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
myretiredate.style.backgroundColor  = color;
}
if(basicpay_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
mybasicpay.style.backgroundColor  = color;
}
if(modeofpayment_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
mymodeofpayment.style.backgroundColor  = color;
}
if(receipt_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
myreceipt.style.backgroundColor  = color;
}
if(maturity_date_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
mymaturity_date.style.backgroundColor  = color;
}

if(officeaddress1_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
officeaddress1.style.backgroundColor  = color;
}
if(officeaddress2_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
officeaddress2.style.backgroundColor  = color;
}
if(join_datepicker_value=='')
{
document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
join_datepicker.style.backgroundColor  = color; 
}
if(gender_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
gender.style.backgroundColor  = color; 

}

if(retirementage_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
retirementage.style.backgroundColor  = color; 

}
if(district_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
district.style.backgroundColor  = color; 

}
if(designation_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
designation.style.backgroundColor  = color; 

}  
if(department_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
department.style.backgroundColor  = color; 

}
if(marital_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
marital.style.backgroundColor  = color; 

}
if(payrange_value=='')
{
document.getElementById("errormsg").style.display = '';
payrange.style.backgroundColor  = color;
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
}
if(dep_district_value=='')
{
document.getElementById("errormsg").style.display = '';
dep_district.style.backgroundColor  = color;
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
}

if(risk_datepicker_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
risk_datepicker.style.backgroundColor  = color; 

}
if(fileno_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
fileno.style.backgroundColor  = color; 

}
if(modeofpayment_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
modeofpayment.style.backgroundColor  = color; 

}
if(receipt_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
receipt.style.backgroundColor  = color; 

}
if(receiptfrom_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
receiptfrom.style.backgroundColor  = color; 

}
if(premium_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
premium.style.backgroundColor  = color; 

}
if(sum_assured_value=='')
{

document.getElementById("errormsg").style.display = '';
document.getElementById("errormsg2").style.display = '';
document.getElementById("errormsg3").style.display = '';
sum_assured.style.backgroundColor  = color; 

}




}
 function turnoff() {
        
        
        document.getElementById("thinboxModalBG").style.display = 'none';
             }
     function turnon(id) {
        
        if(id=''){
        document.getElementById("thinboxModalBG").style.display = 'none';
        }else{
            document.getElementById("thinboxModalBG").style.display = '';
        }
     }
  function getRow(t){



var oRows = document.getElementById('table1').getElementsByTagName('tr');
var iRowCount = oRows.length;
var color ='#FFFFFF';
var newcolor ='#E2EFF4'
//alert('Your table has ' + iRowCount + ' rows.');
 for (var j = 1; j < iRowCount; j++) {
 document.getElementById('table1').getElementsByTagName('tr')[j].getElementsByTagName('td')[0].style.backgroundColor  = color;
  document.getElementById('table1').getElementsByTagName('tr')[j].getElementsByTagName('td')[1].style.backgroundColor  = color;
   document.getElementById('table1').getElementsByTagName('tr')[j].getElementsByTagName('td')[2].style.backgroundColor  = color;
    document.getElementById('table1').getElementsByTagName('tr')[j].getElementsByTagName('td')[3].style.backgroundColor  = color;
     document.getElementById('table1').getElementsByTagName('tr')[j].getElementsByTagName('td')[4].style.backgroundColor  = color;
      document.getElementById('table1').getElementsByTagName('tr')[j].getElementsByTagName('td')[5].style.backgroundColor  = color;
       document.getElementById('table1').getElementsByTagName('tr')[j].getElementsByTagName('td')[6].style.backgroundColor  = color;
        document.getElementById('table1').getElementsByTagName('tr')[j].getElementsByTagName('td')[7].style.backgroundColor  = color;
        
 
 }
// alert("hello"+rowid);
var sid = t.parentNode.id ;

var rowid=  document.getElementById(sid).parentNode.id;
//alert("hello"+rowid);

//alert(nrcols);
var  child0 =document.getElementById(rowid).childNodes[0].id  ;
var  child1 =document.getElementById(rowid).childNodes[1].id  ;
var  child2 =document.getElementById(rowid).childNodes[2].id  ;
var  child3 =document.getElementById(rowid).childNodes[3].id  ;
var  child4 =document.getElementById(rowid).childNodes[4].id  ;
var  child5 =document.getElementById(rowid).childNodes[5].id  ;
var  child6 =document.getElementById(rowid).childNodes[6].id  ;
var  child7 =document.getElementById(rowid).childNodes[7].id  ;
//alert(child);
document.getElementById(child0).style.backgroundColor  = newcolor;
document.getElementById(child1).style.backgroundColor  = newcolor;
document.getElementById(child2).style.backgroundColor  = newcolor;
document.getElementById(child3).style.backgroundColor  = newcolor;
document.getElementById(child4).style.backgroundColor  = newcolor;
document.getElementById(child5).style.backgroundColor  = newcolor;
document.getElementById(child6).style.backgroundColor  = newcolor;
document.getElementById(child7).style.backgroundColor  = newcolor;


}
function getCheckedRadios() {
	var checkedRadios = [];
	var radios = document.getElementsByTagName('input');
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].type == 'radio' && radios[i].checked) 
{
//alert("Yes");
var cellid=  radios[i].parentNode.id;
var rowid=  document.getElementById(cellid).parentNode.id;
//alert(rowid);
document.getElementById(rowid).style.display = 'none';
}
	}
	return checkedRadios;
} 
function popupblack()
{
//alert("yes");

 javascript:parent.topNav.Popup.showModal('modal',null,null,{'screenColor':'#000000','screenOpacity':.6});return false;
 javascript:parent.menu.Popup.showModal('modal',null,null,{'screenColor':'#000000','screenOpacity':.6});return false;
Popup.showModal('modal',null,null,{'screenColor':'#000000','screenOpacity':.6});return false;
	}

// -->

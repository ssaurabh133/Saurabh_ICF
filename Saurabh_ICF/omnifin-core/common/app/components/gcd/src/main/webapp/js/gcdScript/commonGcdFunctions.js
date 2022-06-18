function getRequestObject() {
	  if (window.ActiveXObject) { 
		return(new ActiveXObject("Microsoft.XMLHTTP"));
	  } else if (window.XMLHttpRequest) {
		return(new XMLHttpRequest());
	  } else {
		return(null);
	  }
	}

function trim(str) {
	return ltrim(rtrim(str));
}
function isWhitespace(charToCheck) {
	var whitespaceChars = " \t\n\r\f";
	return (whitespaceChars.indexOf(charToCheck) != -1);
}

function ltrim(str) { 
	for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
	return str.substring(k, str.length);
}
function rtrim(str) {
	for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
	return str.substring(0,j+1);
}


function isNumberKey(evt) 
{
     //alert(event.keyCode);
var charCode = (evt.which) ? evt.which : event.keyCode;
if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	alert("Only Numeric allowed here");
	return false;
}
return true;
}

function isAlphNumericKey(evt) 
{
    
var charCode = (evt.which) ? evt.which : event.keyCode;
//alert(charCode);
if (charCode > 31 && (charCode < 48 || charCode > 57)&& (charCode < 65 || charCode > 90)&& (charCode < 97 || charCode > 122)) {
	alert("Only Numeric and Alphanumeric are allowed here");
	return false;
}
return true;
}



function allChecked()
{
	//alert("ok");
	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
	//alert(c);
	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
	
}


function check()
{
    //alert("ok");
	var ch=document.getElementsByName('chk');
	    var zx=0;
	    var flag=0;
	    for(i=0;i<ch.length;i++)
	{
		if(ch[zx].checked==false)
		{
			flag=0;
		}
		else
		{
			
			flag=1;
			break;
		}
		zx++;
	}
	return flag;
}


function checkBoxes()
{
    //alert("ok");
	var ch=document.getElementsByName('chk');
	    var zx=0;
	    var flag=0;
	    for(i=0;i<ch.length;i++)
	{
		if(ch[i].checked==true)
		{
			zx++;
			
		}
		
	}
	if(zx>1)
	{
		alert("Please select one record to modify !!!");
		return false;
	}
	else if(zx==1)
	{
		return true;
	}
	
}

function appendZero()
{
	if(document.getElementById("constitution").value=='PARTNER' || document.getElementById("constitution").value=='PROPRIETOR')
	{
		document.getElementById("registrationNo").value="00000";
	}
	else
	{
		document.getElementById("registrationNo").value='';
	}
}
function openAddressCustomer()
{
	var functionId=document.getElementById("functionId").value;
	if(functionId=='3000206')
		openLOVCommon(338,'customerForm','customerId','','','','','copyAddress','customerName','adType','cmAdd');
	else
		openLOVCommon(2007,'customerForm','customerId','','','','','copyAddress','customerName','adType','cmAdd');
}

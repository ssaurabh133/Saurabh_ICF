 function fnCaseChange()
     {
	 
         document.getElementById("userName").value=(document.getElementById("userName").value).toUpperCase();
         return ( document.getElementById("userName").value).toUpperCase();

     }
  
     function trimAll(str)
{
	/*************************************************************
	Input Parameter :str
	Purpose         : remove all white spaces in front and back of string
	Return          : str without white spaces    
	***************************************************************/

	//check for all spaces
	var objRegExp =/^(\s*)$/;
	if (objRegExp.test(str))
	{
		str = str.replace(objRegExp,''); 
		if (str.length == 0)
		return str; 
	} 

	// check for leading and trailling spaces
	objRegExp = /^(\s*)([\W\w]*)(\b\s*$)/;
	if(objRegExp.test(str))
	{
		str = str.replace(objRegExp, '$2');
	}
	return str;
}

    
     
     function turnoff() {
        
        
				document.getElementById("thinboxModalBG").style.display = 'none';
				document.getElementById("empcode_label").style.display = 'none';
				document.getElementById("empcode").style.display = 'none';
				document.getElementById("select_ques").style.display = 'none';
				document.getElementById("quest").style.display = 'none';
				document.getElementById("ans_label").style.display = 'none';
				document.getElementById("ans_des").style.display = 'none';
   
  
     }
     function turnon(id) {
        
        if(id=''){
        document.getElementById("thinboxModalBG").style.display = 'none';
        }else{
            document.getElementById("thinboxModalBG").style.display = '';
        
        }
     }
function validateUser()
{
    var j_username = document.getElementById("userName");
    var j_password = document.getElementById("userPassword");
    if(trimAll(j_username.value)== '')
    {
    	alert("Please enter User Name.");
    	j_username.focus();
    	return false;
    }
    else if(trimAll(j_password.value)== '')
    {
    	alert("Please enter Password.");
		j_password.focus();
		return false;

    }


//return true;
}

    
function display(obj) {
//alert(obj);	
txt = obj.options[obj.selectedIndex].value;
//alert(txt);
document.getElementById("empcode_label").style.display = 'none';
   document.getElementById("empcode").style.display = 'none';
   document.getElementById("select_ques").style.display = 'none';
   document.getElementById("quest").style.display = 'none';
    document.getElementById("ans_label").style.display = 'none';
   document.getElementById("ans_des").style.display = 'none';
if(txt=='1')
{
    
   
   document.getElementById("empcode_label").style.display = '';
   document.getElementById("empcode").style.display = '';
   
}
if(txt=='2')
{
    
   
   document.getElementById("select_ques").style.display = '';
   document.getElementById("quest").style.display = '';
   document.getElementById("ans_label").style.display = '';
   document.getElementById("ans_des").style.display = '';
}
}

//Ravindra Business Date Change start

function checkAdminPwd()
{
	//alert("adminPwd1 ");
	var contextPath = document.getElementById('contextPath').value;
	var adminPwd=document.getElementById("adminPwd").value;
	//alert("adminPwd2 ");
	if(adminPwd.trim() != '')
	{
		//alert("adminPwd "+adminPwd);
	  		document.getElementById("askAdminPassword").action=contextPath+"/checkAdminPwd.do";
	 		document.getElementById("askAdminPassword").submit();
		//document.location.href = contextPath+"/checkAdminPwd.do?adminPwd="+adminPwd;
		
		return true;
	}
	else
	{
		alert("Please enter admin password");
		document.getElementById("adminPwd").focus();
		return false;
	}
}


function checkDateNew()
{
	if(checkDate('newBusinessDate'))
	{
		var formatD=document.getElementById("formatD").value;
		var origionalBusinessDate = document.getElementById("origionalBusinessDate").value;
		var newBusinessDate = document.getElementById("newBusinessDate").value;
		if(origionalBusinessDate!='' && newBusinessDate!='')
		{
			date1=getDateObject(origionalBusinessDate,formatD.substring(2, 3));
			date2=getDateObject(newBusinessDate,formatD.substring(2, 3));
			if(date1<date2)
			{
				alert("New business date can't be greater than Origional business date.");
				document.getElementById("newBusinessDate").value='';
				return false;
			}
		}	
		return true;
	}
}

function getDateObject(dateString,dateSeperator)
{
	//This function return a date object after accepting 
	//a date string ans dateseparator as arguments
	var curValue=dateString;
	var sepChar=dateSeperator;
	var curPos=0;
	var cDate,cMonth,cYear;

	//extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	
	//extract month portion				
	endPos=dateString.indexOf(sepChar,curPos+1);			
	cMonth=dateString.substring(curPos+1,endPos);

	//extract year portion				
	curPos=endPos;
	endPos=curPos+5;			
	cYear=curValue.substring(curPos+1,endPos);
	
	//Create Date Object
	dtObject=new Date(cYear,cMonth,cDate);	
	return dtObject;
}

function saveNewBusinessDate()
{ 
	//alert("ok");
	var contextPath = document.getElementById("contextPath").value;
	var currentBusinessDate= document.getElementById("currentBusinessDate").value;
	var newBusinessDate= document.getElementById("newBusinessDate").value;
	if(currentBusinessDate!='' && newBusinessDate!='')
	{
		document.location.href=contextPath+"/saveNewBusinessDate.do?method=saveNewBusinessDate&newBusinessDate="+newBusinessDate;
		return true;
	}
	else
	{
		alert("Please enter newBusinessDate");
		document.getElementById("newBusinessDate").focus();
	}
	return false;
}

//Ravindra Business Date Change end
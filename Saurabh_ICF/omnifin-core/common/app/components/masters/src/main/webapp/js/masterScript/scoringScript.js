function caseChange(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}


function scoreAdd()
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("scoreMasterSearch").action=sourcepath+"/scoringSearchMasters.do?method=addScoreMasterDetails";
	document.getElementById("scoreMasterSearch").submit();
	
}

//function saveScore(){
//	var scoreAlert = "";
//	var sourcepath=document.getElementById("path").value;
//	if(validateScoreMasterDynaValidatorForm(document.getElementById("scoreMasterAdd")))
//	{
//		if(document.getElementById("sourceTable").value == "" || document.getElementById("sourceColumn").value == ""){
//			if(document.getElementById("sourceTable").value == ""){
//				scoreAlert += "* Source Table Is Required \n";
//			}
//			if(document.getElementById("sourceColumn").value == ""){
//				scoreAlert += "* Source Column Is Required ";
//			}
//			alert(scoreAlert);
//			document.getElementById("save").removeAttribute("disabled","true");
//			return false;
//			
//		}
//	document.getElementById("scoreMasterAdd").action=sourcepath+"/scoringMastersAdd.do?method=insertScoreMasterDetails";
//	document.getElementById("scoreMasterAdd").submit();
//	}
//	else
//	{
//		document.getElementById("save").removeAttribute("disabled","true");
//		return false;
//	}
//
//}

function saveScore(){
	var scoreAlert = "";
	var sourcepath=document.getElementById("path").value;
	if(validateScoreMasterDynaValidatorForm(document.getElementById("scoreMasterAdd")))
	{
	document.getElementById("scoreMasterAdd").action=sourcepath+"/scoringMastersAdd.do?method=insertScoreMasterDetails";
	document.getElementById("scoreMasterAdd").submit();
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}

}

function searchScore()
{
	if((document.getElementById("scoreCode").value=='') && (document.getElementById("scoreDesc").value==''))
	{
		alert("*  Please select at least one field.");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	var sourcepath=document.getElementById("path").value;
	document.getElementById("scoreMasterSearch").action=sourcepath+"/scoringSearchMasters.do?method=scoringMasterList";
	document.getElementById("scoreMasterSearch").submit();
	
}

function editScore()
{
	var sourcepath=document.getElementById("path").value;
	var scoreAlert = "";
	
	if(validateScoreMasterDynaValidatorForm(document.getElementById("scoreMasterAdd")))
	{
	
	document.getElementById("scoreMasterAdd").action=sourcepath+"/scoringMastersUpdate.do?method=updateScoreDetails";
	document.getElementById("scoreMasterAdd").submit();
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
}




// Score Card Details Functions



function scoreCardAdd()
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("scoreCardMasterSearch").action=sourcepath+"/scoreCardMasters.do?method=addCardScoreDetails";
	document.getElementById("scoreCardMasterSearch").submit();
	
}

function searchCardScore()
{
	if(document.getElementById("scoreCardId").value=='' && document.getElementById("scoreCardDesc").value=='')
	{
		alert("*  Please select at least one field.");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	else
	{
		if(validateScoreCardMasterSearchDynaValidatorForm(document.getElementById("scoreCardMasterSearch")))
		{
			var sourcepath=document.getElementById("path").value;
			document.getElementById("scoreCardMasterSearch").action=sourcepath+"/scoreCardMasters.do?method=scoringCardList";
			document.getElementById("scoreCardMasterSearch").submit();
		}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	}
}



function saveCardScore(){
	
	var sourcepath=document.getElementById("path").value;
	if(validateScoreCardMasterDynaValidatorForm(document.getElementById("scoreCardMasterAdd")))
	{
	document.getElementById("scoreCardMasterAdd").action=sourcepath+"/scoreCardAdd.do?method=insertCardScoreMasterDetails";
	document.getElementById("scoreCardMasterAdd").submit();
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}

}

function editCardScore()
{
	var sourcepath=document.getElementById("path").value;
	if(validateScoreCardMasterDynaValidatorForm(document.getElementById("scoreCardMasterAdd")))
	{
	document.getElementById("scoreCardMasterAdd").action=sourcepath+"/scoringCardMastersUpdate.do?method=updateCardScore";
	document.getElementById("scoreCardMasterAdd").submit();
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
}

function radioCheck(san){
	if(san == 'dataType1'){
		document.getElementById("dataType1").checked = true;
		document.getElementById("dataType2").checked = false;
		document.getElementById("dataType1").value = 'N';
			
	}else if(san == 'dataType2'){
		document.getElementById("dataType2").checked = true;
		document.getElementById("dataType1").checked = false;
		document.getElementById("dataType2").value = 'A';
		
	}
}

//Abhimanyu


function addParameter()
{
	//alert("ok");
	var parameterCode=document.getElementById('parameterCode').value;
	var type=document.getElementById('type').value;
	var numericFrom=document.getElementById('numericFrom').value;
	var numericTo=document.getElementById('numericTo').value;
	var charecter=document.getElementById('charecter').value;
	var score=document.getElementById('score').value;
	var a=0;	
	if(parameterCode=='' || type=='' || score=='')
	{	
		a=1;
	}
  if(type=='N')
	{
	  if(numericFrom=='' || numericTo=='')
	  {
		a=1;
	  }
	}	
 if(type=='A')
	{	
	 if(charecter=='')
	 {
		a=1;
	 }
	
	}

 if(a==1)
 {
	alert("Please Fill All Parameter Score Information!");
	 return false;
 }
	else
	{
				
		 var table1="table1";
         var row1="row1";
         
	            var oRows =document.getElementById('table1').getElementsByTagName('tr');
	            var iRowCount = oRows.length;	     
	            var  orgcount=iRowCount;
	            iRowCount=iRowCount-1;
	            if(iRowCount>=1)
	            {	          
	            for(var a=1;a<orgcount;a++)
	            {
	            var parameterCode1=parameterCode+a;
	            var paramrow=document.getElementById("parameterCode"+a).value;	
	            // alert(parameterCode+"\n"+paramrow);
	            if(parameterCode==paramrow)
	            {
	            	alert("Duplicate Parameter Code Can Not Be Inserted!");
	            	return false;
	            
	            }
	            }
	            }
	            var textbox1 = '<input type="checkbox" name="chk" id="chk"/>';
	            var textbox2 = document .getElementById('parameterCode').value;	           
	            var textbox3 = document .getElementById('type').value;
	            var textbox4 = document .getElementById('numericFrom').value;	         
	            var textbox5 = document .getElementById('numericTo').value;
	            var textbox6 = document .getElementById('charecter').value;
	            var textbox7 = document .getElementById('score').value;	              

	            var tbl =document.getElementById(table1);
	            //var rowIndex =document.getElementById(row1).value;

	             var newRow = tbl.insertRow(orgcount);	           
	             newRow.setAttribute('className','white1' );
	             newRow.setAttribute('class','white1' );
	             newRow.setAttribute('align','center' );
	             //row.setAttribute('className','white2' );
	     	   
	     	    
	             var newCell = newRow.insertCell(0);	             
	             newCell.innerHTML = textbox1;

	             var newCell = newRow.insertCell(1);	        
				 newCell.innerHTML =textbox2+'<input type="hidden" id=parameterCode'+orgcount+' name="parametergGridCode"  value='+textbox2+'>'; 
	             
	             var newCell = newRow.insertCell(2);	          
	             newCell.innerHTML = textbox3+'<input type="hidden" id="gridtype" name="gridtype"  value='+textbox3+'>';
	             
	             var newCell = newRow.insertCell(3);	            
	             newCell.innerHTML = textbox4+'<input type="hidden" id="gridnumericFrom" name="gridnumericFrom" value='+textbox4+'>';
					
				var newCell = newRow.insertCell(4);
				newCell.innerHTML = textbox5+'<input type="hidden" id="gridnumericTo" name="gridnumericTo" value='+textbox5+'>';
			
				var newCell = newRow.insertCell(5);					
			    newCell.innerHTML = textbox6+'<input type="hidden" id="gridcharecter" name="gridcharecter" value='+textbox6+'>';
				
				
				var newCell = newRow.insertCell(6);				
				newCell.innerHTML = textbox7+'<input type="hidden" id="gridscore" name="gridscore"  value='+textbox7+'>';
	             				
				document.getElementById('parameterCode').value='';
				document.getElementById('type').value='';
				document.getElementById('numericFrom').value='';
				document.getElementById('numericTo').value='';
				document.getElementById('charecter').value='';
				document.getElementById('score').value='';
		
	}
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

function allChecked()
{
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
function editRow()
{
	var parameterCode=document.getElementById('parameterCode').value;
	var type=document.getElementById('type').value;
	var numericFrom=document.getElementById('numericFrom').value;
	var numericTo=document.getElementById('numericTo').value;
	var charecter=document.getElementById('charecter').value;
	var score=document.getElementById('score').value;
	var a=0;	
	if(parameterCode=='' || type=='' || score=='')
	{	
		a=1;
	}
  if(type=='N')
	{
	  if(numericFrom=='' || numericTo=='')
	  {
		a=1;
	  }
	}	
 if(type=='A')
	{	
	 if(charecter=='')
	 {
		a=1;
	 }
	
	}

 if(a==1)
 {
	alert("Please Fill All Parameter Score Information!");
	 return false;
 }
	else
	{
		 var contextPath=document.getElementById("contextPath").value;		     
		 document.getElementById("ParameterScoreDef").action=contextPath+"/parameterScoreBusiness.do?method=editParameterList";
		 document.getElementById("ParameterScoreDef").submit();
	}
	}

function removeRow() {	
		if(check()==1)
		{  		  
		 if(confirm("Are you sure to remove row from table!"))
		    {
		var table = document.getElementById("table1");
        var rowCount = table.rows.length;
        for(var i=1; i<rowCount; i++) {
         var row = table.rows[i];
         var chkbox = row.cells[0].childNodes[0];
         if(null != chkbox && true == chkbox.checked) 
         {
             table.deleteRow(i);
             rowCount--;
             i--;
         }
        
      }
       return true;
		 }
		 else
		 {
		 	return false;
		 }
     
		}
		else
		{
		    alert("Select atleast one row!!!");
			return false;
		}
		
		
 }

function isCharKey(evt)
{
	var charCode = (evt.which) ? evt.which : event.keyCode;   
	if (charCode>47 && charCode<58)
	 {
	 alert("Only Characters are allowed here!");
	 return false;		
	 }	
	 else
	 {
	 return true;	
	 	
	 }
}
function check()
{	
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
function saveParameterDesc()
{
	 var oRows =document.getElementById('table1').getElementsByTagName('tr');
     var iRowCount = oRows.length;
     iRowCount=iRowCount-1;     
     if(iRowCount==0)
     {    	 
    	 alert("Add Data To Save!");
    	 return false;
     }
     if(document.getElementById("scoreCardId").value=='')
     {
    	 alert("Select Score Card Id!");
    	 return false;
     }
     else
     {    
    	 var contextPath=document.getElementById("contextPath").value;
    	 document.getElementById("ParameterScoreDef").action=contextPath+"/parameterScoreBusiness.do?method=saveParameter";
    	 document.getElementById("ParameterScoreDef").submit();
     }
}
function newParameterAdd()
{
	     var contextPath=document.getElementById("contextPath").value;	
	     document.getElementById("flag").value="Add";
		 document.getElementById("ParameterScoreDef").action=contextPath+"/parameterScoreBusiness.do?method=openAddParameter";
		 document.getElementById("ParameterScoreDef").submit();
	
	
}
function searchParameterDesc()
{
	 
     if(document.getElementById("scoreCardId").value=='')
     {
    	 alert("Select Score Card Id!");
    	 return false;
     }
     else
     {    	
    	 var contextPath=document.getElementById("contextPath").value;
    	 document.getElementById("ParameterScoreDef").action=contextPath+"/parameterScoreDef.do";
    	 document.getElementById("ParameterScoreDef").submit();
     }
}

function GetXmlHttpObject()
{
	if (window.XMLHttpRequest)
    {
		// code for IE7+, Firefox, Chrome, Opera, Safari
  		return new XMLHttpRequest();
  	}
	if (window.ActiveXObject)
	{
		// code for IE6, IE5
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
	return null;
}
function chk_radio()
{ 
var policyIds=document.getElementsByName('chk');	
var istrue=false;
var data;  
if(policyIds.length==1 && document.ParameterScoreDef.chk.checked==true)
{
data=document.getElementById("chk").value;
istrue=true;
}
	else if(policyIds.length>=2)
	{	 
	for(i=0;i<policyIds.length;i++)
	{
	if(policyIds[i].checked)
	{
	istrue=true;
	data=document.ParameterScoreDef.chk[i].value;
	}
	}	
	
	}
	if(istrue)
	{	
		getParameterDef(data);	
	return true;
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
function getParameterDef(parametercode)
{	
	var parametercode=document.getElementById(parametercode).value;
	 var scorecardid=document.getElementById("scoreCardId").value;	
	 //alert("parametercode="+parametercode+"\nscorecardid="+scorecardid);	
	 var contextPath=document.getElementById("contextPath").value;
	 var url=contextPath+"/parameterScoreBusiness.do?method=getParameterDef&scorecardid="+scorecardid+"&parametercode="+parametercode;
	 xmlhttp1=GetXmlHttpObject();
	 if (xmlhttp1==null)
		{
 		alert("Your browser does not support AJAX!");
 	 	return;
		}
	
	xmlhttp1.onreadystatechange=ledgerList;
	xmlhttp1.open("GET",url,true);
	xmlhttp1.send(null);
}
function ledgerList()
{
	
	if (xmlhttp1.readyState == 4)
	{
        if (xmlhttp1.status == 200)
        {       
        var str=xmlhttp1.responseText;
      
        var str1=str.split("$:");
        document.getElementById("parameterCode").value=str1[0];
        document.getElementById("type").value=str1[5];
        if(trim(str1[5])=="A")
        {
        	 document.getElementById("charecter").style.display="";	
        	 document.getElementById("numericFrom").style.display="none";
             document.getElementById("numericTo").style.display="none";	
             document.getElementById("nfrom").style.display="none";	
             document.getElementById("paramchar").style.display="";
             document.getElementById("nto").style.display="none";
        }
        if(trim(str1[5])=="N")
        {
        	 document.getElementById("numericFrom").style.display="";
             document.getElementById("numericTo").style.display="";	
             document.getElementById("charecter").style.display="none";	
             document.getElementById("paramchar").style.display="none";
             document.getElementById("nfrom").style.display="";	
             document.getElementById("nto").style.display="";
        }
        document.getElementById("score").value=str1[4];
        document.getElementById("charecter").value=str1[3];
        document.getElementById("numericFrom").value=str1[1];
        document.getElementById("numericTo").value=str1[2];
     

        }
    } else
    {
       
    }
} 
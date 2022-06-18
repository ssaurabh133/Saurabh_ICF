 var insuranceDeletedID="";

function newAsset(){ 
		
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("assetMakerSearch").action=basePath+"/assetMakerForNewAction.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("assetMakerSearch").submit();	   
	    return true;
	}	

 function removeComma(id)
 {
     var str = id;
     var arr = str.split(',');
     var stri = '';
     for(i=0; i<arr.length; i++){
         stri = stri+arr[i];
     }   
     var amount = parseFloat(stri);
 	return amount;
 }

 
   function getDateObject(dateString,dateSeperator)
    {
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
    function fnSearchAuthor(alert1)   
    {	
    	
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("assetMakerSearch").action=basePath+"/assetAuthorProcessAction.do?method=authorSearchDetail";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("assetMakerSearch").submit();
	 
	    return true;
  	}

    function fnSearch(alert1)
	{	
		
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("assetMakerSearch").action=basePath+"/assetMakerSearch.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("assetMakerSearch").submit();
	    return true;
  	}
	
function onSaveUpdateAssetMaker(alert1,alert2){
	
			var basePath=document.getElementById("contextPath").value;
			var formatD=document.getElementById("formatD").value;
			var startDate = document.getElementById("startDate").value;
			var dtstart=getDateObject(startDate,formatD.substring(2, 3));		
			var businessDate= document.getElementById("businessDate").value;
			var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
			var endDate = document.getElementById("endDate").value;
			var dtend = getDateObject(endDate,formatD.substring(2, 3));		
			var sumAssured = removeComma((document.getElementById('sumAssured').value));
			var premiumAmnt = removeComma((document.getElementById('premiumAmnt').value));
			var policyNo = document.getElementById('policyNo').value;
			var coverNoteNo = document.getElementById('coverNoteNo').value;
			var yearDesc = parseInt(document.getElementById('yearDesc').value);
			var yearNo = document.getElementById('yearNo').value;
			var yearCheck =(yearDesc+1).toString() ;
			
			if (yearNo != yearCheck)
			{
				if (yearDesc==0&&yearNo=="")
				{
					alert("Please select Year No ");
					return false;
				}
				alert("Please select Year in sequence.");
				return false;
			}
			if (policyNo=="" && coverNoteNo=="")
			{
				alert("Please enter Policy No or Cover Note No");
				return false;
			}
			
			if(validateAssetMakerDynaValidatorForm(document.getElementById("assetMakerForm"))){
		
		     document.getElementById("assetMakerForm").action = basePath+"/assetMakerForSaveAction.do?method=updateonSaveAssetMaker&recStatus=P&afterWarning=";
			 document.getElementById("processingImage").style.display = '';
		     document.getElementById("assetMakerForm").submit();
		     DisButClass.prototype.DisButMethod(); 
			 return true;							
		 }	
		
}
function onSaveofAssetMaker(alert1,alert2){
	
				var basePath=document.getElementById("contextPath").value;			
				var sumAssured = removeComma((document.getElementById('sumAssured').value));
				var premiumAmnt = removeComma((document.getElementById('premiumAmnt').value));
				var policyNo = document.getElementById('policyNo').value;
				var coverNoteNo = document.getElementById('coverNoteNo').value;
				var yearDesc = parseInt(document.getElementById('yearDesc').value);
				var yearNo = document.getElementById('yearNo').value;
				var yearCheck =(yearDesc+1).toString() ;
				
				
				
				if(validateAssetMakerDynaValidatorForm(document.getElementById("assetMakerForm"))){
		
					if (policyNo=="" && coverNoteNo=="")
					{
						alert("Please enter Policy No or Cover Note No");
						return false;
					}
				
					if (yearNo != yearCheck)
					{
						if (yearDesc==0&&yearNo=="")
						{
							alert("Please select Year No ");
							return false;
						}
						alert("Please select Year in sequence.");
						return false;
					}		
				document.getElementById("assetMakerForm").action=basePath+"/assetMakerForSaveAction.do?method=onSaveAssetMaker&afterWarning=";
		 		document.getElementById("processingImage").style.display = '';
		 		document.getElementById("assetMakerForm").submit();
		 		DisButClass.prototype.DisButMethod();
		 		return true;
			}		
          }

         function saveBeforeForward(){
        	 
			 DisButClass.prototype.DisButMethod();
        	 alert("Please save the data First");
        	 DisButClass.prototype.EnbButMethod();
        	 return false;
         }         
		
         function onSaveForwardUpdateAssetMaker(alert1,alert2)
         {        	
        		var basePath=document.getElementById("contextPath").value;
        		var formatD=document.getElementById("formatD").value;
        		var startDate = document.getElementById("startDate").value;
        		var dtstart=getDateObject(startDate,formatD.substring(2, 3));        		
        		var businessDate= document.getElementById("businessDate").value;
        		var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
        		var sumAssured = removeComma((document.getElementById('sumAssured').value));
				var premiumAmnt = removeComma((document.getElementById('premiumAmnt').value));
        		var endDate = document.getElementById("endDate").value;
        		var dtend = getDateObject(endDate,formatD.substring(2, 3));
        		var policyNo = document.getElementById('policyNo').value;
				var coverNoteNo = document.getElementById('coverNoteNo').value;
				
				if (policyNo=="" && coverNoteNo=="")
				{
					alert("Please enter Policy No or Cover Note No");
					return false;
				};
				
				if(validateAssetMakerDynaValidatorForm(document.getElementById("assetMakerForm"))){
        			        			             		

				document.getElementById("assetMakerForm").action=basePath+"/assetMakerForSaveAction.do?method=onSaveForwardofAssetMaker&recStatus=F&afterWarning=";
    			document.getElementById("processingImage").style.display = '';
    			document.getElementById("assetMakerForm").submit();
    		    return true; 
        	    }        		
        	  }


	function onCancelAssetMaker()
	{ 
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("assetMakerForm").action=basePath+"/cancelAssets.do?method=cancelAsset";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("assetMakerForm").submit();
	   return true;
	   }

function getAuthorScreen(){ 
	var basePath=document.getElementById("contextPath").value;
    document.getElementById("assetMakerSearch").action=basePath+"/assetAuthorProcessAction.do?method=authorScreen";
    document.getElementById("assetMakerSearch").submit();
   return true;
   }

	function onSaveOfAuthor(alert1){
		
		if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
		   {
			alert(alert1);	
			return false;
		   }
		else{	
				var basePath=document.getElementById("contextPath").value;
				document.getElementById("assetAuthorForm").action = basePath+"/assetAuthorProcessAction.do?method=onSaveAuthor";
			    document.getElementById("processingImage").style.display = '';
				document.getElementById("assetAuthorForm").submit();
				return true;		 
		
			}
		   }   

	function numbersonly(e, san, Max){
		var dynaVal = san;
		document.getElementById(dynaVal).maxLength = Max+3;
		var unicode=e.charCode? e.charCode : e.keyCode
		if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9)){ //if the key isn't the backspace key (which we should allow)
		if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
		return false //disable key press
		}
		}

	function formatNumber(val, san)
	{
		if(val != ''){

			var expAllVal = val.split('.');
			var firstVal = expAllVal[0];
			var strToArray = new Array();
			var secVal = '';
			var dynaVal = san;
			
			if(expAllVal.length == 2){
				var secVal = expAllVal[1];
			}
			
			var lengthOffirstVal = firstVal.length;
			var roundVal = lengthOffirstVal/3;
			var remind = lengthOffirstVal % 3;
			
			if(remind == 0){
				var findRoundOrNot = roundVal;
			}else{
				var findRoundOrNot = Math.ceil(roundVal);
			}
			if(findRoundOrNot > 0){
				var forLoopFindRoundOrNot = findRoundOrNot-1; 
				var jIndex = lengthOffirstVal;	              
				for(i=forLoopFindRoundOrNot; i >= 0 ; i--){
					if(jIndex > 2){
						var jIndex = jIndex-3;	
						var lastId = 3;
					}
					else{
						var lastId = jIndex;
						var jIndex = 0;							
					}				
					
					strToArray[i] = firstVal.substr(jIndex, lastId);
					
				}
				if(firstVal.indexOf(',') < 0){
					var finalValueForDisp = strToArray.join(',');
				}
				if(secVal != ''){
					finalValueForDisp = finalValueForDisp+'.'+secVal;
				}else{
					finalValueForDisp = finalValueForDisp+'.00';
				}
				document.getElementById(dynaVal).value = '';
				document.getElementById(dynaVal).value = finalValueForDisp;
			}
			if(val.indexOf(',') > 0){
				  	var origString = val;
						var inChar = ',';
						var newString = origString.split(inChar);
						newString = newString.join('');
					
				document.getElementById(dynaVal).value = '';
				document.getElementById(dynaVal).value = newString;
				//alert(newString);
				}
		} 
	}

	
	
	function checkPolicyNo(evt){
		var charCode = (evt.which) ? evt.which : event.keyCode;
		if (charCode==92 )
		{
			alert("Back Slash (\\) are not Allowed Here");
			return false;
		}
		else{
			return true;
		    }
		}
	

	function checkNumber(val, e, Max, san){
		var dynaVal = san;
		var keyVal = e.keyCode;
		var textValue = val;
		var textValueLen = document.getElementById(dynaVal).maxLength = Max+3;	
		var removeLast = 'NO';
		var finlDone = 'NO';
		var lastLength = '0';
		var firstVal = Max;
		
		if (e.shiftKey==1){
			finlDone = 'YES';
			newValue = val;
		}else{
			if(keyVal != '13' && keyVal != '16'){ 					
				if((keyVal > '95' && keyVal < '106') || (keyVal > '47' && keyVal < '58')){
					if(textValue.indexOf('.') > -1){
						var splt = textValue.split('.');
						if(splt[1].length > 2){						
							var f1V = parseInt(splt[0].length);
							var f2V = f1V+3;
							lastLength = f2V;
							removeLast = 'YES';
						}
					}else{
						if(textValueLen > firstVal){
							removeLast = 'YES';
							lastLength = firstVal;
						}
					}
				}else if(keyVal == '110' || keyVal == '190'){
					if(textValue.indexOf('.') != textValue.lastIndexOf('.')){
						var splt = textValue.split('.');
						var f1V = parseInt(splt[0].length);
						var f2V = f1V+1;
						lastLength = f2V;					
						removeLast = 'YES';
					}
				}else{
					if(keyVal > '105'){
							var ascToStr = textValue;
					}else{
						var ascToStr = String.fromCharCode(keyVal);		
						if(ascToStr.toUpperCase() === ascToStr && ascToStr.toLowerCase() !== ascToStr){
							
							var textValue = ascToStr.toUpperCase();
						}
					}
					
					
					var getCharLen = textValue.indexOf(ascToStr);
					
					if(lastLength > Max){
						lastLength = getCharLen;
					}else if(getCharLen == 0){
						lastLength = getCharLen;		
					}
				}
			}
		}	
		
		
		
		if(removeLast === 'YES'){
			var newtextValueLen = lastLength;
			var newValue = textValue.substr(0, newtextValueLen);
			finlDone = 'YES';
		}
		
		if(finlDone === 'YES'){
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newValue;
		}
	}
	function keyUpNumber(val, e, Max, san){
	if(val.indexOf(',') > 0){
			var dynaVal = san;
			document.getElementById(dynaVal).maxLength = Max+3;
			var origString = val;
			var inChar = ',';
			var outChar = '.';
			var newString = origString.split(outChar);
			var newString = origString.split(inChar);
			newString = newString.join('');
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newString;
	}
	}
	
	
	var message="Right Click is disabled";
	function clickIE4(){
	if (event.button==2){
	alert(message);
	return false;
	}
	}

	function clickNS4(e){
	if (document.layers||document.getElementById&&!document.all){
	if (e.which==2||e.which==3){
	alert(message);
	return false;
	}
	}
	}

	if (document.layers){
	document.captureEvents(Event.MOUSEDOWN);
	document.onmousedown=clickNS4;
	}
	else if (document.all&&!document.getElementById){
	document.onmousedown=clickIE4;
	}

	function getEntityType()
	{
		document.getElementById('entityTypeDesc').value=document.getElementById('entityType').value;
	}
	
	function assetInsuranceViewer()
	{		
		var loanid=document.getElementById('lbxLoanNoHID').value;
		var contextPath=document.getElementById("contextPath").value;
		
		if(loanid=="")
		{
		alert("Please Select Loan No");
		}else{
		
		var url= contextPath+"/assetProcessAction.do?method=onAssetInsuranceViewer&loanId="+loanid;
		mywindow =window.open(url,'assetInsurance','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
			}
			return true;
	}}
	function assetPolicyViewer(assetInsuranceId,entityType)
	{			
		var contextPath=document.getElementById("contextPath").value;						
		var url= contextPath+"/assetProcessAction.do?method=onAssetInsurancePolicyViewer&entityType="+entityType+"&assetInsuranceId="+assetInsuranceId;
		mywindow =window.open(url,'assetPolicy','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
			}
			return true;
	}
	function changeEntity()
	{
		document.getElementById('entity').value="";
		document.getElementById("assetDesc").value='';
		document.getElementById("assetNature").value='';
		document.getElementById("assetMake").value='';
		document.getElementById("assetModel").value='';
		document.getElementById("dealerName").value='';
		document.getElementById("engineNumber").value='';
		document.getElementById("chasisNumber").value='';
		document.getElementById("registrationNumber").value='';			
	}
	
	function openAssetLOV()
	{ 
		var productCat=document.getElementById('productCategory').value;			
		var entityType=document.getElementById('entityType').value;
		
		if(entityType=='PRAPPL'||entityType=='COAPPL'||entityType=='GUARANTOR')
		{
				openLOVCommon(2034,'assetMakerForm','lbxEntity','entityTypeDesc|loanAccountNumber','','entityType|lbxLoanNoHID','Select Entity Type|Select Loan No','','entity','lbxAssetHID','endDate');
		}
		 else
		 {
			 if(productCat=='CV')
				 openLOVCommon(2022,'assetMakerForm','entity','entityTypeDesc|loanAccountNumber','','entityType|lbxLoanNoHID','Select Entity Type|Select Loan No','getVehicleDetailsForAsset','asstCostLov','lbxAssetHID','endDateLov');
			 else
				 openLOVCommon(2036,'assetMakerForm','entity','entityTypeDesc|loanAccountNumber','','entityType|lbxLoanNoHID','Select Entity Type|Select Loan No','getVehicleDetailsForAsset','asstCostLov','lbxAssetHID','endDateLov');
		 }
   }		
	
	function assetListAuthorFlag()
	{
		var assetAuthorFlag=document.getElementById('assetMakerListAuthorFlag').value;	
		if(assetAuthorFlag== 'Y')
		{
			document.getElementById("entityType").disabled = true;;
			document.getElementById("entity").readOnly = true;
			document.getElementById("yearNo").readOnly = true;	
			document.getElementById("cancel").button.disabled = true;
			document.getElementById("insuranceButton").button.disabled = true;
			
					
		}
	}
	function clickAssetInsuranceMaker(loanId,entityType,assetInsuranceId)
	{
		var sourcepath=document.getElementById("contextPath").value;
		var url=sourcepath+"/assetProcessAction.do?method=onAssetInsurancePolicyViewer&loanId="+loanId+"&entityType="+entityType+"&assetInsuranceId="+assetInsuranceId;
		popupWin=window.open(url,'Asset_Insurance_Maker','height=500,width=800,top=400,left=400, scrollbars=yes ').focus();
		if (window.focus) 
		   popupWin.focus();	
		return true;	
		
		
	}
	
	
	
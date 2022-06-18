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

function SearchApprovalLevel(val){ 
	   DisButClass.prototype.DisButMethod();
	    if(document.getElementById("product").value=='' && document.getElementById("scheme").value=='' && document.getElementById("findApprovalLevel").value=='' )
		{
			alert(val);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    document.getElementById("approvalLevelDefSearchForm").action="approvalLevelDefSearchBehind.do?method=OpenApprovalLevelMainJsp"; 
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("approvalLevelDefSearchForm").submit();
		
	}	

function AddApprovalLevel(flag)
{
	//alert("in newApprovalLevel"+flag);
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("approvalLevelDefSearchForm").action=sourcepath+"/approvalLevelDefSearchBehind.do?method=OpenApprovalLevelDef&makerFlag="+flag;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("approvalLevelDefSearchForm").submit();

}
//saurabh changes starts
function forwardApprovalLevel()
{
	//alert("here we forwarding:::::::::");	
	var productModify=document.getElementById("productModify").value;
	//alert("here we forwarding:::::::::"+productModify);
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("approvalLevelDefAddForm").action=sourcepath+"/approvalLevelDefAdd.do?method=forwardApprovalLevel&productModify="+productModify;
	document.getElementById("processingImage").style.display='';
	document.getElementById("approvalLevelDefAddForm").submit();
}
function forwardBeforeSave(){
	alert("Please Save the data first");
}
function SearchApprovalLevelAuthor(val){ 
	   DisButClass.prototype.DisButMethod();
	    if(document.getElementById("product").value=='' && document.getElementById("scheme").value=='' && document.getElementById("findApprovalLevel").value=='' )
		{
			alert(val);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    document.getElementById("approvalLevelDefSearchFormAuthor").action="UWApprovalLevelDefAuthor.do"; 
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("approvalLevelDefSearchFormAuthor").submit();		
	}	
function saveUWApprovalAuthor(){
	//alert("going to approve::::::::::::");
	if(document.getElementById("comments").value=='')
	{
		alert("Comments is Required");
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("comments").focus();
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("uwApprovalLevelDefAuthorApproveForm").action = contextPath+"/UWApprovalLevelDefAuthorApprove.do?method=saveUWAuthor";
	    document.getElementById("uwApprovalLevelDefAuthorApproveForm").submit();
	}
}
//saurabh changes ends
function SaveApprovalLevel(flag){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var productId =document.getElementById("lbxProductID").value;
	var product =document.getElementById("product").value;
	var schemeId =document.getElementById("lbxSchemeId").value;
	var scheme =document.getElementById("scheme").value;
	var amountFrom =document.getElementById("amountFrom").value;
	var amountTo =document.getElementById("amountTo").value;
	var level1=document.getElementById("level1").value;
	var level2=document.getElementById("level2").value;
	var level3=document.getElementById("level3").value;
	//Nishant starts
	var psmtLevel =document.getElementById("psmtLevel").value;
	psmtLevel=parseInt(psmtLevel);
	if(psmtLevel==4)
	{
		var level4=document.getElementById("level4").value;
	}
	if(psmtLevel==5)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
	}
	if(psmtLevel==6)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
		var level6=document.getElementById("level6").value;
	}
	if(psmtLevel==7)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
		var level6=document.getElementById("level6").value;
		var level7=document.getElementById("level7").value;
	}
	if(psmtLevel==8)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
		var level6=document.getElementById("level6").value;
		var level7=document.getElementById("level7").value;
		var level8=document.getElementById("level8").value;
	}
	if(psmtLevel==9)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
		var level6=document.getElementById("level6").value;
		var level7=document.getElementById("level7").value;
		var level8=document.getElementById("level8").value;
		var level9=document.getElementById("level9").value;
	}
	//Nishant End
	
	
	var levelCheck=document.getElementById("findApprovalLevel").value;
	var amountTo=document.getElementById("amountTo").value;
	var amountFrom=document.getElementById("amountFrom").value;
	var msg1='',msg2='',msg3='',msg4='';
	
	//alert("flag:::::::"+flag);
	if(productId=="" || product=="" )
	{
	    	msg1="*Product is required.\n";
		
		
	}
	if(schemeId=="" || scheme=="")
	{
		msg2="*Scheme is required.\n";
		
		
	}
	if(amountFrom=="")
	{
		msg3="*Amount From is required.\n";
		
		
	}
	if(amountTo=="")
	{
		msg4="*Amount To is required.\n";
	
		
	}
	if(msg1!=''||msg2!=''||msg3!=''||msg4!='')
	{
		alert(msg1+msg2+msg3+msg4);
		if(msg1!='')
		{
			document.getElementById('productButton').focus();
		}
		else if(msg2!='')
		{
			document.getElementById('schemeButton').focus();
		}
		else if(msg3!='')
		{
			document.getElementById('amountFrom').focus();
		}
		else if(msg4!='')
		{
			document.getElementById('amountTo').focus();
		}
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("add").removeAttribute("disabled","false");
		return false;
	}
	
	
	if(levelCheck==1)
	{
		if(level1<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==2)
	{
		if(level1<=0 || level2<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==3)
	{
		if(level1<=0 || level2<=0 || level3<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	
	if(levelCheck==4)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==5)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==6)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0 || level6<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==7)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0 || level6<=0 || level7<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==8)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0 || level6<=0 || level7<=0 || level8<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==9)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0 || level6<=0 || level7<=0 || level8<=0 || level9<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	
	if(removeComma(amountFrom) > removeComma(amountTo))
	{
		alert("'Amount From' should be less than or equal to 'Amount To'");
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("add").removeAttribute("disabled","false");
		return false;
	}


	document.getElementById("approvalLevelDefAddForm").action=sourcepath+"/approvalLevelDefAdd.do?method=SaveApprovalLevelDef&makerFlag="+flag;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("approvalLevelDefAddForm").submit();
	
	
	
}

function UpdateApprovalLevel(flag){
	DisButClass.prototype.DisButMethod();
	var productModify=document.getElementById("productModify").value;
	var level1=document.getElementById("level1").value;
	var level2=document.getElementById("level2").value;
	var level3=document.getElementById("level3").value;
	//Nishant starts
	var psmtLevel =document.getElementById("psmtLevel").value;
	psmtLevel=parseInt(psmtLevel);
	if(psmtLevel==4)
	{
		var level4=document.getElementById("level4").value;
	}
	if(psmtLevel==5)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
	}
	if(psmtLevel==6)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
		var level6=document.getElementById("level6").value;
	}
	if(psmtLevel==7)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
		var level6=document.getElementById("level6").value;
		var level7=document.getElementById("level7").value;
	}
	if(psmtLevel==8)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
		var level6=document.getElementById("level6").value;
		var level7=document.getElementById("level7").value;
		var level8=document.getElementById("level8").value;
	}
	if(psmtLevel==9)
	{
		var level4=document.getElementById("level4").value;
		var level5=document.getElementById("level5").value;
		var level6=document.getElementById("level6").value;
		var level7=document.getElementById("level7").value;
		var level8=document.getElementById("level8").value;
		var level9=document.getElementById("level9").value;
	}
	//Nishant End
	var levelCheck=document.getElementById("findApprovalLevel").value;
	var amountTo=document.getElementById("amountTo").value;
	var amountFrom=document.getElementById("amountFrom").value;
	
	var productId =document.getElementById("lbxProductID").value;
	var product =document.getElementById("product").value;
	var schemeId =document.getElementById("lbxSchemeId").value;
	var scheme =document.getElementById("scheme").value;
	var amountFrom =document.getElementById("amountFrom").value;
	var amountTo =document.getElementById("amountTo").value;
	var decideUpdate='N';
	//alert("value of flag::::::::"+flag);
	
	var msg1='',msg2='',msg3='',msg4='';
	if(flag=='Y')
		 decideUpdate=document.getElementById('decideUpdate').value;
	
		
	
	//alert("hahhahahahhahahhah:::::::::::::::::::::::::"+decideUpdate);
	
	
	if(productId=="" || product=="" )
	{
	    	msg1="*Product is required.\n";
		
		
	}
	if(schemeId=="" || scheme=="")
	{
		msg2="*Scheme is required.\n";
		
		
	}
	if(amountFrom=="")
	{
		msg3="*Amount From is required.\n";
		
		
	}
	if(amountTo=="")
	{
		msg4="*Amount To is required.\n";
	
		
	}
	if(msg1!=''||msg2!=''||msg3!=''||msg4!='')
	{
		alert(msg1+msg2+msg3+msg4);
		
		if(msg3!='')
		{
			document.getElementById('amountFrom').focus();
		}
		else if(msg4!='')
		{
			document.getElementById('amountTo').focus();
		}
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("add").removeAttribute("disabled","false");
		return false;
	}
	

	if(levelCheck==1)
	{
		if(level1<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			// document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==2)
	{
		if(level1<=0 || level2<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==3)
	{
		if(level1<=0 || level2<=0 || level3<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	
	if(levelCheck==4)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==5)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==6)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0 || level6<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==7)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0 || level6<=0 || level7<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if(levelCheck==8)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0 || level6<=0 || level7<=0 || level8<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	if( levelCheck==9)
	{
		if(level1<=0 || level2<=0 || level3<=0 || level4<=0 || level5<=0 || level6<=0 || level7<=0 || level8<=0 || level9<=0)
		{
			 alert("Level must be 1 or greater");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("add").removeAttribute("disabled","false");
			return false;
		}
	}
	
	if(removeComma(amountFrom) > removeComma(amountTo))
	{
		alert("'Amount From' should be less than or equal to 'Amount To'");
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("add").removeAttribute("disabled","false");
		return false;
	}
	
	
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("approvalLevelDefAddForm").action=sourcepath+"/approvalLevelDefAdd.do?method=UpdateApprovalLevelDef&productModify="+productModify+"&makerFlag="+flag+"&decideUpdate="+decideUpdate;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("approvalLevelDefAddForm").submit();
	
}

function checklevel(){
//	alert("in checheck");
//	alert(document.getElementById("findApprovalLevel").value);
	if(document.getElementById("findApprovalLevel").value=='1'  )
	{	
		//alert(document.getElementById("findApprovalLevel").value);
		document.getElementById("level2").disabled=true;
		document.getElementById("level2").value='';
		document.getElementById("userButton21").disabled=true;
		document.getElementById("userButton22").disabled=true;
		document.getElementById("userButton23").disabled=true;
		document.getElementById("user21").value='';
		document.getElementById("user22").value='';
		document.getElementById("user23").value='';
		document.getElementById("lbxUserSearchId21").value='';
		document.getElementById("lbxUserSearchId22").value='';
		document.getElementById("lbxUserSearchId23").value='';
		document.getElementById("level3").disabled=true;
		document.getElementById("level3").value='';
		document.getElementById("userButton31").disabled=true;
		document.getElementById("userButton32").disabled=true;
		document.getElementById("userButton33").disabled=true;
		document.getElementById("user31").value='';
		document.getElementById("user32").value='';
		document.getElementById("user33").value='';
		document.getElementById("lbxUserSearchId31").value='';
		document.getElementById("lbxUserSearchId32").value='';
		document.getElementById("lbxUserSearchId33").value='';
		
		//New added by sanjog
		document.getElementById("level4").disabled=true;
		document.getElementById("level4").value='';
		document.getElementById("userButton41").disabled=true;
		document.getElementById("userButton42").disabled=true;
		document.getElementById("userButton43").disabled=true;
		document.getElementById("user41").value='';
		document.getElementById("user42").value='';
		document.getElementById("user43").value='';
		document.getElementById("lbxUserSearchId41").value='';
		document.getElementById("lbxUserSearchId42").value='';
		document.getElementById("lbxUserSearchId43").value='';
		document.getElementById("level5").disabled=true;
		document.getElementById("level5").value='';
		document.getElementById("userButton51").disabled=true;
		document.getElementById("userButton52").disabled=true;
		document.getElementById("userButton53").disabled=true;
		document.getElementById("user51").value='';
		document.getElementById("user52").value='';
		document.getElementById("user53").value='';
		document.getElementById("lbxUserSearchId51").value='';
		document.getElementById("lbxUserSearchId52").value='';
		document.getElementById("lbxUserSearchId53").value='';
		document.getElementById("level6").disabled=true;
		document.getElementById("level6").value='';
		document.getElementById("userButton61").disabled=true;
		document.getElementById("userButton62").disabled=true;
		document.getElementById("userButton63").disabled=true;
		document.getElementById("user61").value='';
		document.getElementById("user62").value='';
		document.getElementById("user63").value='';
		document.getElementById("lbxUserSearchId61").value='';
		document.getElementById("lbxUserSearchId62").value='';
		document.getElementById("lbxUserSearchId63").value='';
		document.getElementById("level7").disabled=true;
		document.getElementById("level7").value='';
		document.getElementById("userButton71").disabled=true;
		document.getElementById("userButton72").disabled=true;
		document.getElementById("userButton73").disabled=true;
		document.getElementById("user71").value='';
		document.getElementById("user72").value='';
		document.getElementById("user73").value='';
		document.getElementById("lbxUserSearchId71").value='';
		document.getElementById("lbxUserSearchId72").value='';
		document.getElementById("lbxUserSearchId73").value='';
		document.getElementById("level8").disabled=true;
		document.getElementById("level8").value='';
		document.getElementById("userButton81").disabled=true;
		document.getElementById("userButton82").disabled=true;
		document.getElementById("userButton83").disabled=true;
		document.getElementById("user81").value='';
		document.getElementById("user82").value='';
		document.getElementById("user83").value='';
		document.getElementById("lbxUserSearchId81").value='';
		document.getElementById("lbxUserSearchId82").value='';
		document.getElementById("lbxUserSearchId83").value='';
		document.getElementById("level9").disabled=true;
		document.getElementById("level9").value='';
		document.getElementById("userButton91").disabled=true;
		document.getElementById("userButton92").disabled=true;
		document.getElementById("userButton93").disabled=true;
		document.getElementById("user91").value='';
		document.getElementById("user92").value='';
		document.getElementById("user93").value='';
		document.getElementById("lbxUserSearchId91").value='';
		document.getElementById("lbxUserSearchId92").value='';
		document.getElementById("lbxUserSearchId93").value='';
	}
	else if(document.getElementById("findApprovalLevel").value=='2')
	{	
		//alert(document.getElementById("findApprovalLevel").value);
		
		document.getElementById("level2").disabled=false;
		document.getElementById("userButton21").disabled=false;
		document.getElementById("userButton22").disabled=false;
		document.getElementById("userButton23").disabled=false;
		document.getElementById("level3").disabled=true;
		document.getElementById("level3").value='';
		document.getElementById("userButton31").disabled=true;
		document.getElementById("userButton32").disabled=true;
		document.getElementById("userButton33").disabled=true;
		document.getElementById("user31").value='';
		document.getElementById("user32").value='';
		document.getElementById("user33").value='';
		document.getElementById("lbxUserSearchId31").value='';
		document.getElementById("lbxUserSearchId32").value='';
		document.getElementById("lbxUserSearchId33").value='';
		
		//New added by sanjog
		document.getElementById("level4").disabled=true;
		document.getElementById("level4").value='';
		document.getElementById("userButton41").disabled=true;
		document.getElementById("userButton42").disabled=true;
		document.getElementById("userButton43").disabled=true;
		document.getElementById("user41").value='';
		document.getElementById("user42").value='';
		document.getElementById("user43").value='';
		document.getElementById("lbxUserSearchId41").value='';
		document.getElementById("lbxUserSearchId42").value='';
		document.getElementById("lbxUserSearchId43").value='';
		document.getElementById("level5").disabled=true;
		document.getElementById("level5").value='';
		document.getElementById("userButton51").disabled=true;
		document.getElementById("userButton52").disabled=true;
		document.getElementById("userButton53").disabled=true;
		document.getElementById("user51").value='';
		document.getElementById("user52").value='';
		document.getElementById("user53").value='';
		document.getElementById("lbxUserSearchId51").value='';
		document.getElementById("lbxUserSearchId52").value='';
		document.getElementById("lbxUserSearchId53").value='';
		document.getElementById("level6").disabled=true;
		document.getElementById("level6").value='';
		document.getElementById("userButton61").disabled=true;
		document.getElementById("userButton62").disabled=true;
		document.getElementById("userButton63").disabled=true;
		document.getElementById("user61").value='';
		document.getElementById("user62").value='';
		document.getElementById("user63").value='';
		document.getElementById("lbxUserSearchId61").value='';
		document.getElementById("lbxUserSearchId62").value='';
		document.getElementById("lbxUserSearchId63").value='';
		document.getElementById("level7").disabled=true;
		document.getElementById("level7").value='';
		document.getElementById("userButton71").disabled=true;
		document.getElementById("userButton72").disabled=true;
		document.getElementById("userButton73").disabled=true;
		document.getElementById("user71").value='';
		document.getElementById("user72").value='';
		document.getElementById("user73").value='';
		document.getElementById("lbxUserSearchId71").value='';
		document.getElementById("lbxUserSearchId72").value='';
		document.getElementById("lbxUserSearchId73").value='';
		document.getElementById("level8").disabled=true;
		document.getElementById("level8").value='';
		document.getElementById("userButton81").disabled=true;
		document.getElementById("userButton82").disabled=true;
		document.getElementById("userButton83").disabled=true;
		document.getElementById("user81").value='';
		document.getElementById("user82").value='';
		document.getElementById("user83").value='';
		document.getElementById("lbxUserSearchId81").value='';
		document.getElementById("lbxUserSearchId82").value='';
		document.getElementById("lbxUserSearchId83").value='';
		document.getElementById("level9").disabled=true;
		document.getElementById("level9").value='';
		document.getElementById("userButton91").disabled=true;
		document.getElementById("userButton92").disabled=true;
		document.getElementById("userButton93").disabled=true;
		document.getElementById("user91").value='';
		document.getElementById("user92").value='';
		document.getElementById("user93").value='';
		document.getElementById("lbxUserSearchId91").value='';
		document.getElementById("lbxUserSearchId92").value='';
		document.getElementById("lbxUserSearchId93").value='';
	}
	else if(document.getElementById("findApprovalLevel").value=='3')
	{	
		document.getElementById("level2").disabled=false;
		document.getElementById("userButton21").disabled=false;
		document.getElementById("userButton22").disabled=false;
		document.getElementById("userButton23").disabled=false;
		document.getElementById("level3").disabled=false;
		document.getElementById("userButton31").disabled=false;
		document.getElementById("userButton32").disabled=false;
		document.getElementById("userButton33").disabled=false;
		
		//New added by sanjog
		document.getElementById("level4").disabled=true;
		document.getElementById("level4").value='';
		document.getElementById("userButton41").disabled=true;
		document.getElementById("userButton42").disabled=true;
		document.getElementById("userButton43").disabled=true;
		document.getElementById("user41").value='';
		document.getElementById("user42").value='';
		document.getElementById("user43").value='';
		document.getElementById("lbxUserSearchId41").value='';
		document.getElementById("lbxUserSearchId42").value='';
		document.getElementById("lbxUserSearchId43").value='';
		document.getElementById("level5").disabled=true;
		document.getElementById("level5").value='';
		document.getElementById("userButton51").disabled=true;
		document.getElementById("userButton52").disabled=true;
		document.getElementById("userButton53").disabled=true;
		document.getElementById("user51").value='';
		document.getElementById("user52").value='';
		document.getElementById("user53").value='';
		document.getElementById("lbxUserSearchId51").value='';
		document.getElementById("lbxUserSearchId52").value='';
		document.getElementById("lbxUserSearchId53").value='';
		document.getElementById("level6").disabled=true;
		document.getElementById("level6").value='';
		document.getElementById("userButton61").disabled=true;
		document.getElementById("userButton62").disabled=true;
		document.getElementById("userButton63").disabled=true;
		document.getElementById("user61").value='';
		document.getElementById("user62").value='';
		document.getElementById("user63").value='';
		document.getElementById("lbxUserSearchId61").value='';
		document.getElementById("lbxUserSearchId62").value='';
		document.getElementById("lbxUserSearchId63").value='';
		document.getElementById("level7").disabled=true;
		document.getElementById("level7").value='';
		document.getElementById("userButton71").disabled=true;
		document.getElementById("userButton72").disabled=true;
		document.getElementById("userButton73").disabled=true;
		document.getElementById("user71").value='';
		document.getElementById("user72").value='';
		document.getElementById("user73").value='';
		document.getElementById("lbxUserSearchId71").value='';
		document.getElementById("lbxUserSearchId72").value='';
		document.getElementById("lbxUserSearchId73").value='';
		document.getElementById("level8").disabled=true;
		document.getElementById("level8").value='';
		document.getElementById("userButton81").disabled=true;
		document.getElementById("userButton82").disabled=true;
		document.getElementById("userButton83").disabled=true;
		document.getElementById("user81").value='';
		document.getElementById("user82").value='';
		document.getElementById("user83").value='';
		document.getElementById("lbxUserSearchId81").value='';
		document.getElementById("lbxUserSearchId82").value='';
		document.getElementById("lbxUserSearchId83").value='';
		document.getElementById("level9").disabled=true;
		document.getElementById("level9").value='';
		document.getElementById("userButton91").disabled=true;
		document.getElementById("userButton92").disabled=true;
		document.getElementById("userButton93").disabled=true;
		document.getElementById("user91").value='';
		document.getElementById("user92").value='';
		document.getElementById("user93").value='';
		document.getElementById("lbxUserSearchId91").value='';
		document.getElementById("lbxUserSearchId92").value='';
		document.getElementById("lbxUserSearchId93").value='';
	}
	else if(document.getElementById("findApprovalLevel").value=='4')
	{	
		document.getElementById("level2").disabled=false;
		document.getElementById("userButton21").disabled=false;
		document.getElementById("userButton22").disabled=false;
		document.getElementById("userButton23").disabled=false;
		document.getElementById("level3").disabled=false;
		document.getElementById("userButton31").disabled=false;
		document.getElementById("userButton32").disabled=false;
		document.getElementById("userButton33").disabled=false;
		
		//New added by sanjog
		document.getElementById("level4").disabled=false;
		document.getElementById("userButton41").disabled=false;
		document.getElementById("userButton42").disabled=false;
		document.getElementById("userButton43").disabled=false;
		document.getElementById("level5").disabled=true;
		document.getElementById("level5").value='';
		document.getElementById("userButton51").disabled=true;
		document.getElementById("userButton52").disabled=true;
		document.getElementById("userButton53").disabled=true;
		document.getElementById("user51").value='';
		document.getElementById("user52").value='';
		document.getElementById("user53").value='';
		document.getElementById("lbxUserSearchId51").value='';
		document.getElementById("lbxUserSearchId52").value='';
		document.getElementById("lbxUserSearchId53").value='';
		document.getElementById("level6").disabled=true;
		document.getElementById("level6").value='';
		document.getElementById("userButton61").disabled=true;
		document.getElementById("userButton62").disabled=true;
		document.getElementById("userButton63").disabled=true;
		document.getElementById("user61").value='';
		document.getElementById("user62").value='';
		document.getElementById("user63").value='';
		document.getElementById("lbxUserSearchId61").value='';
		document.getElementById("lbxUserSearchId62").value='';
		document.getElementById("lbxUserSearchId63").value='';
		document.getElementById("level7").disabled=true;
		document.getElementById("level7").value='';
		document.getElementById("userButton71").disabled=true;
		document.getElementById("userButton72").disabled=true;
		document.getElementById("userButton73").disabled=true;
		document.getElementById("user71").value='';
		document.getElementById("user72").value='';
		document.getElementById("user73").value='';
		document.getElementById("lbxUserSearchId71").value='';
		document.getElementById("lbxUserSearchId72").value='';
		document.getElementById("lbxUserSearchId73").value='';
		document.getElementById("level8").disabled=true;
		document.getElementById("level8").value='';
		document.getElementById("userButton81").disabled=true;
		document.getElementById("userButton82").disabled=true;
		document.getElementById("userButton83").disabled=true;
		document.getElementById("user81").value='';
		document.getElementById("user82").value='';
		document.getElementById("user83").value='';
		document.getElementById("lbxUserSearchId81").value='';
		document.getElementById("lbxUserSearchId82").value='';
		document.getElementById("lbxUserSearchId83").value='';
		document.getElementById("level9").disabled=true;
		document.getElementById("level9").value='';
		document.getElementById("userButton91").disabled=true;
		document.getElementById("userButton92").disabled=true;
		document.getElementById("userButton93").disabled=true;
		document.getElementById("user91").value='';
		document.getElementById("user92").value='';
		document.getElementById("user93").value='';
		document.getElementById("lbxUserSearchId91").value='';
		document.getElementById("lbxUserSearchId92").value='';
		document.getElementById("lbxUserSearchId93").value='';
	}
	else if(document.getElementById("findApprovalLevel").value=='5')
	{	
		document.getElementById("level2").disabled=false;
		document.getElementById("userButton21").disabled=false;
		document.getElementById("userButton22").disabled=false;
		document.getElementById("userButton23").disabled=false;
		document.getElementById("level3").disabled=false;
		document.getElementById("userButton31").disabled=false;
		document.getElementById("userButton32").disabled=false;
		document.getElementById("userButton33").disabled=false;
		document.getElementById("level4").disabled=false;
		document.getElementById("userButton41").disabled=false;
		document.getElementById("userButton42").disabled=false;
		document.getElementById("userButton43").disabled=false;
		document.getElementById("level5").disabled=false;
		document.getElementById("userButton51").disabled=false;
		document.getElementById("userButton52").disabled=false;
		document.getElementById("userButton53").disabled=false;
		
		//New added by sanjog
		
		document.getElementById("level6").disabled=true;
		document.getElementById("level6").value='';
		document.getElementById("userButton61").disabled=true;
		document.getElementById("userButton62").disabled=true;
		document.getElementById("userButton63").disabled=true;
		document.getElementById("user61").value='';
		document.getElementById("user62").value='';
		document.getElementById("user63").value='';
		document.getElementById("lbxUserSearchId61").value='';
		document.getElementById("lbxUserSearchId62").value='';
		document.getElementById("lbxUserSearchId63").value='';
		document.getElementById("level7").disabled=true;
		document.getElementById("level7").value='';
		document.getElementById("userButton71").disabled=true;
		document.getElementById("userButton72").disabled=true;
		document.getElementById("userButton73").disabled=true;
		document.getElementById("user71").value='';
		document.getElementById("user72").value='';
		document.getElementById("user73").value='';
		document.getElementById("lbxUserSearchId71").value='';
		document.getElementById("lbxUserSearchId72").value='';
		document.getElementById("lbxUserSearchId73").value='';
		document.getElementById("level8").disabled=true;
		document.getElementById("level8").value='';
		document.getElementById("userButton81").disabled=true;
		document.getElementById("userButton82").disabled=true;
		document.getElementById("userButton83").disabled=true;
		document.getElementById("user81").value='';
		document.getElementById("user82").value='';
		document.getElementById("user83").value='';
		document.getElementById("lbxUserSearchId81").value='';
		document.getElementById("lbxUserSearchId82").value='';
		document.getElementById("lbxUserSearchId83").value='';
		document.getElementById("level9").disabled=true;
		document.getElementById("level9").value='';
		document.getElementById("userButton91").disabled=true;
		document.getElementById("userButton92").disabled=true;
		document.getElementById("userButton93").disabled=true;
		document.getElementById("user91").value='';
		document.getElementById("user92").value='';
		document.getElementById("user93").value='';
		document.getElementById("lbxUserSearchId91").value='';
		document.getElementById("lbxUserSearchId92").value='';
		document.getElementById("lbxUserSearchId93").value='';
	}
	else if(document.getElementById("findApprovalLevel").value=='6')
	{	
		document.getElementById("level2").disabled=false;
		document.getElementById("userButton21").disabled=false;
		document.getElementById("userButton22").disabled=false;
		document.getElementById("userButton23").disabled=false;
		document.getElementById("level3").disabled=false;
		document.getElementById("userButton31").disabled=false;
		document.getElementById("userButton32").disabled=false;
		document.getElementById("userButton33").disabled=false;
		document.getElementById("level4").disabled=false;
		document.getElementById("userButton41").disabled=false;
		document.getElementById("userButton42").disabled=false;
		document.getElementById("userButton43").disabled=false;
		document.getElementById("level5").disabled=false;
		document.getElementById("userButton51").disabled=false;
		document.getElementById("userButton52").disabled=false;
		document.getElementById("userButton53").disabled=false;
		document.getElementById("level6").disabled=false;
		document.getElementById("userButton61").disabled=false;
		document.getElementById("userButton62").disabled=false;
		document.getElementById("userButton63").disabled=false;
		
		//New added by sanjog
		
		document.getElementById("level7").disabled=true;
		document.getElementById("level7").value='';
		document.getElementById("userButton71").disabled=true;
		document.getElementById("userButton72").disabled=true;
		document.getElementById("userButton73").disabled=true;
		document.getElementById("user71").value='';
		document.getElementById("user72").value='';
		document.getElementById("user73").value='';
		document.getElementById("lbxUserSearchId71").value='';
		document.getElementById("lbxUserSearchId72").value='';
		document.getElementById("lbxUserSearchId73").value='';
		document.getElementById("level8").disabled=true;
		document.getElementById("level8").value='';
		document.getElementById("userButton81").disabled=true;
		document.getElementById("userButton82").disabled=true;
		document.getElementById("userButton83").disabled=true;
		document.getElementById("user81").value='';
		document.getElementById("user82").value='';
		document.getElementById("user83").value='';
		document.getElementById("lbxUserSearchId81").value='';
		document.getElementById("lbxUserSearchId82").value='';
		document.getElementById("lbxUserSearchId83").value='';
		document.getElementById("level9").disabled=true;
		document.getElementById("level9").value='';
		document.getElementById("userButton91").disabled=true;
		document.getElementById("userButton92").disabled=true;
		document.getElementById("userButton93").disabled=true;
		document.getElementById("user91").value='';
		document.getElementById("user92").value='';
		document.getElementById("user93").value='';
		document.getElementById("lbxUserSearchId91").value='';
		document.getElementById("lbxUserSearchId92").value='';
		document.getElementById("lbxUserSearchId93").value='';
	}
	else if(document.getElementById("findApprovalLevel").value=='7')
	{	
		document.getElementById("level2").disabled=false;
		document.getElementById("userButton21").disabled=false;
		document.getElementById("userButton22").disabled=false;
		document.getElementById("userButton23").disabled=false;
		document.getElementById("level3").disabled=false;
		document.getElementById("userButton31").disabled=false;
		document.getElementById("userButton32").disabled=false;
		document.getElementById("userButton33").disabled=false;
		document.getElementById("level4").disabled=false;
		document.getElementById("userButton41").disabled=false;
		document.getElementById("userButton42").disabled=false;
		document.getElementById("userButton43").disabled=false;
		document.getElementById("level5").disabled=false;
		document.getElementById("userButton51").disabled=false;
		document.getElementById("userButton52").disabled=false;
		document.getElementById("userButton53").disabled=false;
		document.getElementById("level6").disabled=false;
		document.getElementById("userButton61").disabled=false;
		document.getElementById("userButton62").disabled=false;
		document.getElementById("userButton63").disabled=false;
		document.getElementById("level7").disabled=false;
		document.getElementById("userButton71").disabled=false;
		document.getElementById("userButton72").disabled=false;
		document.getElementById("userButton73").disabled=false;
		
		//New added by sanjog
		
		document.getElementById("level8").disabled=true;
		document.getElementById("level8").value='';
		document.getElementById("userButton81").disabled=true;
		document.getElementById("userButton82").disabled=true;
		document.getElementById("userButton83").disabled=true;
		document.getElementById("user81").value='';
		document.getElementById("user82").value='';
		document.getElementById("user83").value='';
		document.getElementById("lbxUserSearchId81").value='';
		document.getElementById("lbxUserSearchId82").value='';
		document.getElementById("lbxUserSearchId83").value='';
		document.getElementById("level9").disabled=true;
		document.getElementById("level9").value='';
		document.getElementById("userButton91").disabled=true;
		document.getElementById("userButton92").disabled=true;
		document.getElementById("userButton93").disabled=true;
		document.getElementById("user91").value='';
		document.getElementById("user92").value='';
		document.getElementById("user93").value='';
		document.getElementById("lbxUserSearchId91").value='';
		document.getElementById("lbxUserSearchId92").value='';
		document.getElementById("lbxUserSearchId93").value='';
	}
	else if(document.getElementById("findApprovalLevel").value=='8')
	{	
		document.getElementById("level2").disabled=false;
		document.getElementById("userButton21").disabled=false;
		document.getElementById("userButton22").disabled=false;
		document.getElementById("userButton23").disabled=false;
		document.getElementById("level3").disabled=false;
		document.getElementById("userButton31").disabled=false;
		document.getElementById("userButton32").disabled=false;
		document.getElementById("userButton33").disabled=false;
		document.getElementById("level4").disabled=false;
		document.getElementById("userButton41").disabled=false;
		document.getElementById("userButton42").disabled=false;
		document.getElementById("userButton43").disabled=false;
		document.getElementById("level5").disabled=false;
		document.getElementById("userButton51").disabled=false;
		document.getElementById("userButton52").disabled=false;
		document.getElementById("userButton53").disabled=false;
		document.getElementById("level6").disabled=false;
		document.getElementById("userButton61").disabled=false;
		document.getElementById("userButton62").disabled=false;
		document.getElementById("userButton63").disabled=false;
		document.getElementById("level7").disabled=false;
		document.getElementById("userButton71").disabled=false;
		document.getElementById("userButton72").disabled=false;
		document.getElementById("userButton73").disabled=false;
		document.getElementById("level8").disabled=false;
		document.getElementById("userButton81").disabled=false;
		document.getElementById("userButton82").disabled=false;
		document.getElementById("userButton83").disabled=false;
		
		//New added by sanjog
		
		document.getElementById("level9").disabled=true;
		document.getElementById("level9").value='';
		document.getElementById("userButton91").disabled=true;
		document.getElementById("userButton92").disabled=true;
		document.getElementById("userButton93").disabled=true;
		document.getElementById("user91").value='';
		document.getElementById("user92").value='';
		document.getElementById("user93").value='';
		document.getElementById("lbxUserSearchId91").value='';
		document.getElementById("lbxUserSearchId92").value='';
		document.getElementById("lbxUserSearchId93").value='';
	} else
	{
		document.getElementById("level2").disabled=false;
		document.getElementById("userButton21").disabled=false;
		document.getElementById("userButton22").disabled=false;
		document.getElementById("userButton23").disabled=false;
		document.getElementById("level3").disabled=false;
		document.getElementById("userButton31").disabled=false;
		document.getElementById("userButton32").disabled=false;
		document.getElementById("userButton33").disabled=false;
		document.getElementById("level4").disabled=false;
		document.getElementById("userButton41").disabled=false;
		document.getElementById("userButton42").disabled=false;
		document.getElementById("userButton43").disabled=false;
		document.getElementById("level5").disabled=false;
		document.getElementById("userButton51").disabled=false;
		document.getElementById("userButton52").disabled=false;
		document.getElementById("userButton53").disabled=false;
		document.getElementById("level6").disabled=false;
		document.getElementById("userButton61").disabled=false;
		document.getElementById("userButton62").disabled=false;
		document.getElementById("userButton63").disabled=false;
		document.getElementById("level7").disabled=false;
		document.getElementById("userButton71").disabled=false;
		document.getElementById("userButton72").disabled=false;
		document.getElementById("userButton73").disabled=false;
		document.getElementById("level8").disabled=false;
		document.getElementById("userButton81").disabled=false;
		document.getElementById("userButton82").disabled=false;
		document.getElementById("userButton83").disabled=false;
		document.getElementById("level9").disabled=false;
		document.getElementById("userButton91").disabled=false;
		document.getElementById("userButton92").disabled=false;
		document.getElementById("userButton93").disabled=false;
		
		//New added by sanjog
		
		
	}
}

function isNumberKey(evt) 
{
var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode > 31 && (charCode < 48 || charCode > 57))
{
	alert("Only Numeric Value Allowed Here");
	return false;
}
	return true;
}
function removeCommaAmountTo(val)
{ 	
	var amount=removeComma(val);
    
    if(amount!=null)
    {
		document.getElementById("amountToDesc").value=amount;
		document.getElementById("amountToId").value=amount;
    }
    else
    {
		document.getElementById("amountToDesc").value="0";
		document.getElementById("amountToId").value="0";
    }
    return true;
}
function removeCommaAmountFrom(val)
{	
	var amount=removeComma(val);

    if(amount!=null)
    {
	    document.getElementById("amountFromDesc").value=amount;
	    document.getElementById("amountFromId").value=amount;
    }
    else
    {
	    document.getElementById("amountFromDesc").value="0";
	    document.getElementById("amountFromId").value="0";
    }
    return true;
}
function setAmount()
{
	var amountFrom=document.getElementById("amountFrom").value;
	var amountTo=document.getElementById("amountTo").value;
	
	amountFrom=removeComma(amountFrom);
	amountTo=removeComma(amountTo);
	
    document.getElementById("amountFromDesc").value=amountFrom;
    document.getElementById("amountFromId").value=amountFrom;
	document.getElementById("amountToDesc").value=amountTo;
	document.getElementById("amountToId").value=amountTo;
	
	return true;
}
		
	
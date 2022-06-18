function trim(str) {
	return ltrim(rtrim(str));
}
function calculateMacValue(){
	var machineCost= document.getElementById("assetsCost").value;
	var machineDiscount=document.getElementById("assetsDiscount").value;
	var mchinevalue=document.getElementById("assetsCollateralValue").value;
	if(machineCost=="" || machineCost==null){
		machineCost=0;
		
	}else{
		machineCost=removeComma(machineCost);
	}	
	
	if(machineDiscount=="" || machineDiscount==null){
		machineDiscount=0;
	}else{
		machineDiscount=removeComma(machineDiscount);
	}
	
	if(mchinevalue=="" || mchinevalue==null){
		mchinevalue=0;
	}else{
		mchinevalue=removeComma(mchinevalue);
	}
	var machineNetValue=machineCost-machineDiscount;
	
	document.getElementById("assetsCollateralValue").value=machineNetValue.toFixed(2);
	if(machineDiscount>machineCost){
		alert("Machine Discount should be equal or less than Machine Cost");
		document.getElementById("assetsCost").value="";
		document.getElementById("assetsDiscount").value="";
		document.getElementById("assetsCollateralValue").value="";
		return false;
	}
	return true;
	
}
function discount(){
	var machineNetValue=document.getElementById("assetsCollateralValue").value;
	var machineDiscount=document.getElementById("assetsDiscount").value;
	var machineCost= document.getElementById("assetsCost").value;
	if(machineCost=="" || machineCost==null){
		
		machineCost=0;
		
	}else{
		machineCost=removeComma(machineCost);
	}
	if(machineNetValue=="" || machineNetValue==null){
		
		machineNetValue=0;
		
	}else{
		machineNetValue=removeComma(machineNetValue);
	}
	//if(machineDiscount=="" || machineDiscount==null) {
		machineDiscount=removeComma(machineDiscount);
		machineDiscount=machineCost-machineNetValue;
		document.getElementById("assetsDiscount").value=machineDiscount.toFixed(2);
	//}
	if(machineNetValue>machineCost){
		alert("Machine Value should be equal or less than Machine Cost");
		document.getElementById("assetsCost").value="";
		document.getElementById("assetsDiscount").value="";
		document.getElementById("assetsCollateralValue").value="";
		return false;
		
	}
}
//neeraj
function calculateVehicleValue(){
	var vehicleCost= document.getElementById("vehicleCost").value;
	if(vehicleCost=="" || vehicleCost==null)
	{
		document.getElementById("vehicleCost").value="0.00";
		vehicleCost=0;
	}else{
		vehicleCost=removeComma(vehicleCost);
	}
	//alert("machineCost...."+machineCost);
	
	var vehicleDiscount=document.getElementById("vehicleDiscount").value;
	if(vehicleDiscount=="" || vehicleDiscount==null)
	{
		document.getElementById("vehicleDiscount").value="0.00";
		vehicleDiscount=0;
	}else{
		vehicleDiscount=removeComma(vehicleDiscount);
	}
	
	//alert("machineDiscount........"+machineDiscount);
	var vehicleNetValue=vehicleCost-vehicleDiscount;
	//alert("machineNet......"+machineValue);
	//var vehicleNetValue=vehicleCost-vehicleValue;
	//alert("machineNetValue......"+machineNetValue);
	calculateLTV(vehicleNetValue);
	vehicleNetValue=vehicleNetValue.toFixed(2)+"";
	formatNumber(vehicleNetValue,'assetsCollateralValue');
	
	if(vehicleDiscount>vehicleCost){
		alert("Vehicle Discount should be equal or less than Vehicle Cost");
		document.getElementById("vehicleCost").value="";
		document.getElementById("vehicleDiscount").value="";
		document.getElementById("assetsCollateralValue").value="";
	}
	//Nishant space starts
	var loanAmount=document.getElementById("loanAmount").value;
	var vehicleCost=document.getElementById("vehicleCost").value;
	var loan=parseFloat(removeComma(loanAmount));
	var value=parseFloat(removeComma(vehicleCost));

	/*if(value<loan)
	{
		alert("Vehicle Cost can not be less than Loan Amount.");
		document.getElementById("vehicleCost").value="";
		document.getElementById("assetsCollateralValue").value="";
		return false;
	}*/
	//Nishant space end
	return true;
	
}
function discountVehicle(){
	
	var vehicleNetValue=document.getElementById("assetsCollateralValue").value;
	var vehicleDiscount=document.getElementById("vehicleDiscount").value;
	var vehicleCost= document.getElementById("vehicleCost").value;
	if(vehicleCost=="")
	{
		alert("Vehicle Cost is required.");
		document.getElementById("assetsCollateralValue").value="";
		return false;
	}
	if(vehicleNetValue=="" || vehicleNetValue==null){
		
		vehicleNetValue=0;
		
	}else{
		vehicleNetValue=removeComma(vehicleNetValue);
	}
	if(vehicleCost=="" || vehicleCost==null){
		
		vehicleCost=0;
		
	}else{
		vehicleCost=removeComma(vehicleCost);
	}
	//if(vehicleDiscount=="" || vehicleDiscount==null) {
		vehicleDiscount=removeComma(vehicleDiscount);
		vehicleDiscount=vehicleCost-vehicleNetValue;
		document.getElementById("vehicleDiscount").value=vehicleDiscount.toFixed(2);
	//}
	if(vehicleNetValue>vehicleCost){
		alert("Vehicle Value should be equal or less than Vehicle Cost");
		//document.getElementById("vehicleCost").value="";
		document.getElementById("vehicleDiscount").value="";
		document.getElementById("assetsCollateralValue").value="";
	}
}
function saveMachineDetails()
{   
	DisButClass.prototype.DisButMethod();	
	var machineSupplier= document.getElementById("lbxmachineSupplier");
	var assetManufact= document.getElementById("lbxmachineManufact");
	var contextPath= document.getElementById("contextPath").value;	
	var assetsCollateralDesc = document.getElementById("assetsCollateralDesc");
	var assetsCollateralValue = document.getElementById("assetsCollateralValue");
	var machineMake = document.getElementById("machineMake");
	var machineModel = document.getElementById("machineModel");
	var assetCost = document.getElementById("assetsCost");
	var msg= '';
	
	if(assetsCollateralDesc.value == '')
		msg += '* Machine Description is required.\n';
	if(assetCost.value == '')
		msg += '* Machine Cost is required.\n';
	if(assetsCollateralValue.value == '')
		msg += '* Machine Value is required.\n';
	if(machineMake.value == '')
		msg += '* Machine Make is required.\n';
	if(machineModel.value == '')
		msg += '* Machine Model is required.\n';
	if(assetManufact.value == '')
		msg += '* Machine Manufacturer is required.\n';
	if(machineSupplier.value == '')
		msg += '* Machine Dealer/Supplier is required.\n';	
	
	var machinAddress=document.getElementById("machinAddress").value;
	if(machinAddress=='Y')
	{
		var propertyAddress=document.getElementById("propertyAddress");
		var country=document.getElementById("country");
		var state=document.getElementById("state");
		var dist=document.getElementById("dist");		
		 if(propertyAddress.value == '')
	 			msg += '* Machine Address is required.\n';
		 if(country.value == '')
	 			msg += '* Machine Country is required.\n';
		 if(state.value == '')
	 			msg += '* Machine State/Providence  is required.\n';
		 if(dist.value == '')
	 			msg += '* Machine District  is required.\n';		
	}		 		
	if(msg.match("Description")){
		assetsCollateralDesc.focus();
	}else if(msg.match("Cost")){
		machineModel.focus();
	}else if(msg.match("Value")){
		assetsCollateralValue.focus();
	}else if(msg.match("Make")){
		machineMake.focus();
	}else if(msg.match("Model")){
		machineModel.focus();
	}else if(msg.match("Manufacturer")){
		assetManufact.focus();
	}else if(msg.match("Dealer/Supplier")){
		machineSupplier.focus();
	} else if(msg.match("Address")){
		propertyAddress.focus();
	} else if(msg.match("Country")){
		country.focus();
	} else if(msg.match("State")){
		state.focus();
	} else if(msg.match("District")){
		dist.focus();
	} 		
 	
	if(msg !='')
	{
		alert(msg);
 		document.getElementById("save").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;	 
	 }
	 else 
	 {
		if(document.getElementById("assetsIdMachine").value!='')
		{
			var primaryId = document.getElementById("assetsIdMachine").value;	
		    var assetsType = document.getElementById("ass1").value;		
		    document.getElementById("MachineForm").action = contextPath+"/collateralMachineryProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("MachineForm").submit();
		 	return true;
		}
		else
		{	
			document.getElementById("MachineForm").action = contextPath+"/collateralMachineryProcessAction.do?method=saveCollateralDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("MachineForm").submit();
		    return true;
		}
	}
}


function saveOthersDetails()
{
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	
	var assetsCollateralDesc = document.getElementById("assetsCollateralDesc");
	var assetsCollateralValue = document.getElementById("assetsCollateralValue");
	
	 if(assetsCollateralDesc.value == ''||assetsCollateralValue.value == ''){
		 var msg= '';
 		if(assetsCollateralDesc.value == '')
 			msg += '* Assets/Collateral Description is required.\n';
 		if(assetsCollateralValue.value == '')
 			msg += '* Assets/Collateral Value is required.\n';
 		 		
 		if(msg.match("Description")){
 			assetsCollateralDesc.focus();
 		}else if(msg.match("Value")){
 			assetsCollateralValue.focus();
 		}
 		
 		alert(msg);
 		document.getElementById("assetButton").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }else {
	if(!(document.getElementById("assetsIdOther").value)==''){
		var primaryId = document.getElementById("assetsIdOther").value;
	    var assetsType = document.getElementById("oth1").value;	
		document.getElementById("OthersForm").action=contextPath+"/collateralAssetProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("OthersForm").submit();
	 	return true;
	}
	else
	{		
	document.getElementById("OthersForm").action=contextPath+"/collateralAssetProcessAction.do?method=saveCollateralDetails";
	document.getElementById("processingImage").style.display = '';
 	document.getElementById("OthersForm").submit();	
}
}
}


function savePropertyDetails()
{  
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	
	var assetsCollateralDesc = document.getElementById("assetsCollateralDesc");
	var assetsCollateralValue = document.getElementById("assetsCollateralValue");
	var collateralSecurityMargin = document.getElementById("collateralSecurityMargin");
	var propertyAddress = document.getElementById("propertyAddress");
	var propertyOwner = document.getElementById("propertyOwner");
	var propertyConstruct = document.getElementById("propertyConstruct");
	var propertyArea = document.getElementById("propertyArea");
	var val1 =document.getElementById("techValuation").value;
	var val2 = document.getElementById("technicalValuation1").value;
	var val3 = document.getElementById("technicalValuation2").value;
	var method = document.getElementById("valuationMethodId").value;
	var valuationAmonunt=document.getElementById("valuationAmount").value;
	
	//start by sachin
	var propertyType = document.getElementById("propertyType");
	var docValue = document.getElementById("docValue");
	var addConstruction = document.getElementById("addConstruction");
	var country = document.getElementById("country");
	var state = document.getElementById("state");
	var dist = document.getElementById("dist");
	var pincode = document.getElementById("pincode");
	
	//added by harsh
	var CustomerProfile = document.getElementById("assetLevel");
	var propertyStatus = document.getElementById("propertyStatus");
	var propertyTitle  = document.getElementById("propertyTitle");
	var Customersegment = document.getElementById("mortgage");
	
	
	
	//end by sachin
	var testFlag=0;
	if(valuationAmonunt==''){
		valuationAmonunt=0;
	}
	else
	{
		valuationAmonunt=removeComma(valuationAmonunt);	
	}
	
	 
	if(assetsCollateralDesc.value == ''|| assetsCollateralValue.value == '' || propertyAddress.value == ''||propertyOwner.value == ''||propertyConstruct.value == ''
		 ||propertyArea.value == '' || propertyType.value=='' || docValue.value=='' || addConstruction.value=='' || country.value=='' || state.value=='' || dist.value=='' 		|| pincode.value=='' || assetLevel.value=='' || propertyStatus.value=='' || propertyTitle.value=='' || mortgage.value==''){
		 var msg= '';
 		if(assetsCollateralDesc.value == '')
 			msg += '* Property Description is required.\n';
 		if(propertyType.value=='')
 			msg += '* Property Type is required.\n';
 		if(propertyOwner.value == '')
 			msg += '* Property Owner is required.\n';
 		if(propertyConstruct.value == '')
 			msg += '* % of Construction is required.\n';
 		if(propertyArea.value == '')
 			msg += '* Constructed Area(SQFT) is required.\n';
 		if(assetsCollateralValue.value == '')
 			msg += '* Property Value is required.\n';
 		if(docValue.value=='')
 			msg += '* Document Value is required.\n';
 		if(addConstruction.value=='')
 			msg += '* Additional Construction is required.\n';
// 		if(collateralSecurityMargin.value == '')
// 			msg += '* Property Security Margin is required.\n';
 		if(propertyAddress.value == '')
 			msg += '* Address Line1 is required.\n';
 		if(country.value=='')
 			msg += '* Country is required.\n';
 		if(state.value=='')
 			msg += '* State/Providence is required.\n';
 		if(dist.value=='')
 			msg += '* District is required.\n';
 		if(pincode.value=='')
 			msg += '* Pincode is required.\n';
 		
 		//added by harsh
 		if(propertyStatus.value=='')
 			msg += '* propertyStatus is required.\n';
 		if(propertyTitle.value=='')
 			msg += '* propertyTitle is required.\n';
 		if(Customersegment.value=='')
 			msg += '* Customersegment is required.\n';
 		if(CustomerProfile.value=='')
 			msg += '* CustomerProfile is required.\n';
 		
 			 		
 			 		
 		if(msg.match("Description")){
 			assetsCollateralDesc.focus();
 		}else if(msg.match("Type")){
 			propertyType.focus();
 		}else if(msg.match("Owner")){
 			propertyOwner.focus();
 		}else if(msg.match("% of Construction")){
 			propertyConstruct.focus();
 		}else if(msg.match("Area(SQFT)")){
 			propertyArea.focus();
 		}else if(msg.match("Property Value")){
 			assetsCollateralValue.focus();
 		}else if(msg.match("Document Value")){
 			docValue.focus();
 		}else if(msg.match("Additional")){
 			addConstruction.focus();
 		}else if(msg.match("Address")){
 			propertyAddress.focus();
 		}else if(msg.match("Country")){
 			country.focus();
 		}else if(msg.match("State/Providence")){
 			state.focus();
 		}else if(msg.match("District")){
 			dist.focus();
 		}else if(msg.match("Pincode")){
 			pincode.focus();
 		}else if(msg.match("Security")){
 			collateralSecurityMargin.focus();
 		}else if(msg.match("propertyTitle")){
 			propertyTitle.focus();
 		}
// 		else if(msg.match("Area")){
// 			propertyArea.focus();
// 		}
 		
 		alert(msg);
 		document.getElementById("save").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }
	 else if(assetsCollateralValue.value=="0.00")
	 {
		 alert("Property value can not be 0");
		 document.getElementById("save").removeAttribute("disabled","true");
	 	 DisButClass.prototype.EnbButMethod();
		 return false;
	 }
	 else {
		 		//sachin
		 		if(val1=='' && (method=='LOT' || method=='AOT' || method=='AOFS' || method=='AOTF' || method=='TV1')){
				val1=0;
				alert("Technical Valuation1 is required. ");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
				else{
				val1=removeComma(val1);
				}
				if(val2=='' && (method=='LOT' || method=='AOT' || method=='AOST' || method=='AOFS' || method=='TV2')){
					val2=0;
					alert("Technical Valuation2 is required. ");
					DisButClass.prototype.EnbButMethod();
					return false;
					}
				else{
					val2=removeComma(val2);
					}
				if(val3=='' && (method=='LOT' || method=='AOT' || method=='AOST' || method=='AOTF' || method=='TV3')){
					val3=0;
					alert("Technical Valuation3 is required. ");
					DisButClass.prototype.EnbButMethod();
					return false;
					}
				else{
					val3=removeComma(val3);
					}
				
				if(method=='LOT'){
				var val=Math.min(val1,val2,val3).toFixed(2);
				if(val!=valuationAmonunt)
				{
					testFlag=1;
					}
				}
				if(method=='AOT'){
					var val=((val1+val2+val3)/3).toFixed(2);
					if(val!=valuationAmonunt){
					testFlag=1;
					}
					}
				if(method=='AOFS'){
					
					var val=((val1+val2)/2).toFixed(2);
					if(val!=valuationAmonunt)
					{
					testFlag=1;
					}
					}
				if(method=='AOST'){
					
					var val=((val2+val3)/2).toFixed(2);
					if(val!=valuationAmonunt)
					{
					testFlag=1;
					}
					}
				if(method=='AOTF'){
					
					var val=((val1+val3)/2).toFixed(2);
					if(val!=valuationAmonunt)
					{
					testFlag=1;
					}
					}
				if(method=='TV1'){
					
					var val=val1;
					if(val!=valuationAmonunt)
					{
					testFlag=1;
					}
					}
				if(method=='TV2'){
					
					var val=val2;
					if(val!=valuationAmonunt)
					{
					testFlag=1;
					}
					}
				if(method=='TV3'){
					
					var val=val3;;
					if(val!=valuationAmonunt)
					{
					testFlag=1;
					}
					}
				if(testFlag==1)
				{
				alert("Valuation Amount to be considered as per Valuation method is not matching with Technical Valuation/s");
				document.getElementById("save").removeAttribute("disabled","true");
			 	 DisButClass.prototype.EnbButMethod();
				 return false;
				}
		 
	//sachin	 
		 
	if(!(document.getElementById("assetsIdProperty").value)=='')
	{
		var primaryId = document.getElementById("assetsIdProperty").value;	    
	    var assetsType = document.getElementById("p2").value;	  
		document.getElementById("PropertyForm").action=contextPath+"/collateralPropertyProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("PropertyForm").submit();
	 	return true;
	}
	else
	{
	
	document.getElementById("PropertyForm").action=contextPath+"/collateralPropertyProcessAction.do?method=saveCollateralDetails";
	document.getElementById("processingImage").style.display = '';
 	document.getElementById("PropertyForm").submit();
 	return true;
	}
	}
}
function removeComma(id)
{
    var str = id;
    var arr = str.split(',');
    var stri = '';
    for(i=0; i<arr.length; i++)
        stri = stri+arr[i];
    var amount = parseFloat(stri);
    return amount;
}
//added by neeraj
function loanAmountValidation(value,id)
{
	/*//alert("neeraj");
	var val="";
	var assetsCollateralValue=document.getElementById("assetsCollateralValue").value;
	var collateralSecurityMargin=document.getElementById("collateralSecurityMargin").value;
	if(assetsCollateralValue!=""&&collateralSecurityMargin!="")
	{
		var loanAmount=document.getElementById("loanAmount").value;
		var loan=parseFloat(loanAmount);
		//alert("Loan Amount : "+loan);
		var value=parseFloat(removeComma(assetsCollateralValue));
		//alert("value : "+value);
		var ltv=parseFloat(removeComma(collateralSecurityMargin));
		//alert("ltv : "+ltv);
		var result=(value*ltv)/100;
		//alert("result : "+result);
		if(loan>result)
		{
			alert("Loan Amount can't be more than "+result);
			document.getElementById("loanAmount").value="";
			return false;
		}
		else
		{
			val=loanAmount+"";
			formatNumber(val,'loanAmount');	
		}
	}
	else
	{
		var msg= '';
		if(assetsCollateralValue=="")
			msg += '* Vehicle Value is required.\n';
		if(collateralSecurityMargin=="")
			msg += '* Vehicle LTV % is required.';
		alert(msg);
		document.getElementById("loanAmount").value="";
		return false;		
	}*/
	var assetsCollateralValue=document.getElementById("assetsCollateralValue").value;
	if(assetsCollateralValue=="")
	{
		alert("Vehicle Value is required.");
		document.getElementById("loanAmount").value="";
		return false;		
	}
	else
	{
		var loanAmount=document.getElementById("loanAmount").value;
		if(loanAmount=="")
			document.getElementById("loanAmount").value="0.00";
		var loan=parseFloat(removeComma(loanAmount));
		//alert("Loan Amount : "+loan);
		var value=parseFloat(removeComma(assetsCollateralValue));
		//alert("value : "+value);
		/*if(value<loan)
		{
			alert("Loan Amount can't be greater than Vehicle Value.");
			document.getElementById("loanAmount").value="";
			return false;
		}
		else
		{*/
			var ltv=(loan*100)/value;
			ltv=ltv.toFixed(2)+"";
			//Nishant space starts
			if(loanAmount == "")
			{
				ltv=0.00;
				ltv=ltv.toFixed(2)+"";
			}
			//Nishant space end
			//alert("LTV   :  "+ltv);
			formatNumber(ltv,'collateralSecurityMargin');			
		//}
	}	
}
//method added by Neeraj Tripathi
function calculateLTV(value)
{
	//alert("calculateLTV  value  :  "+value);
	var loanAmount=document.getElementById("loanAmount").value;
	//alert("Loan Amount : "+loanAmount);
	if(loanAmount!="")
	{
		var loan=parseFloat(removeComma(loanAmount));
		//alert("Loan Amount : "+loan);
		if(value>=loan)
		{
			var ltv=(loan*100)/value;
			ltv=ltv.toFixed(2)+"";
			//alert("LTV : " + ltv);
			//Nishant space starts
			if(loanAmount == 0.00)
			{
				ltv=0.00;
				ltv=ltv.toFixed(2)+"";
			}
			//Nishant space end
			formatNumber(ltv,'collateralSecurityMargin');
		}
//		else
//		{
//			document.getElementById("loanAmount").value="";
//			document.getElementById("collateralSecurityMargin").value="";
//		}
	}
	
}
function saveVehicleDetails()
{ 
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;

	var assetsCollateralDesc = document.getElementById("assetsCollateralDesc");
	var assetsCollateralValue = document.getElementById("assetsCollateralValue");
	var vehicleMake = document.getElementById("vehicleMake");
	var vehicleYearOfManufact = document.getElementById("vehicleYearOfManufact");
	var vehicleRegDate = document.getElementById("vehicleRegDate");
	var assetcollateral = document.getElementById("assetcollateral");
	var vehicleModel = document.getElementById("vehicleModel");
	var collateralSecurityMargin = document.getElementById("collateralSecurityMargin");
	var vehicleCost = document.getElementById("vehicleCost");
	var supplier = document.getElementById("machineSupplier"); 
	var loanAmount1 = document.getElementById("loanAmount").value; 
	var checkRefinaceReqInfo = document.getElementById("checkRefinaceReqInfo"); 
	var assetNature = document.getElementById("assetNature");  
	var vehicleChesisNo = document.getElementById("vehicleChesisNo");
	var engineNumber = document.getElementById("engineNumber");
	var vehicleRegNo = document.getElementById("vehicleRegNo");
	var idvValue= document.getElementById("idv").value;
	if( loanAmount1=='0.00')
		{
		alert('Loan Amount should be grather than 0');
		DisButClass.prototype.EnbButMethod();
		return false;
		}

	if(assetNature.value=="O"){
		var vehicleInsureDate=document.getElementById("vehicleInsureDate");
		if(vehicleInsureDate.value==''){
 			alert("* Insurance Expiry Date is required.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	 var msg= '';
	 //if(assetsCollateralDesc.value == ''||assetsCollateralValue.value == ''||vehicleMake.value == ''||vehicleYearOfManufact.value == ''||vehicleRegDate.value == ''||vehicleCost.value=='')
	 if(assetsCollateralDesc.value == ''||assetsCollateralValue.value == ''||vehicleMake.value == ''||vehicleYearOfManufact.value == ''||vehicleCost.value=='' || supplier.value=='')
	 {
		
 		if(assetsCollateralDesc.value == '')
 			msg += '* Vehicle  Description is required.\n';
 		if(vehicleCost == '')
 			msg += '* Vehicle Cost is required.\n';
 		if(assetsCollateralValue.value == '')
 			msg += '* Vehicle  Value is required.\n';
 		if(vehicleMake.value == '')
 			msg += '* Vehicle Make is required.\n';
 		if(supplier.value == '')
 			msg += '* Supplier is required.\n';
 		if(vehicleYearOfManufact.value == '')
 			msg += '* Year Of Manufacturing is required.\n';
 		/*if(vehicleRegDate.value == '')
 			msg += '* Registration Date is required.\n';*/
 		 		
 		if(msg.match("Description")){
 			assetsCollateralDesc.focus();
 		}else if(msg.match("Cost")){
 			vehicleCost.focus();
 		}else if(msg.match("Value")){
 			assetsCollateralValue.focus();
 		}else if(msg.match("Make")){
 			if(assetcollateral.value == "A"){
 				document.getElementById("menufacturerButton").focus();
 			}else if(assetcollateral.value == "C"){
 				vehicleMake.focus();
 			}
 		}
 		/*else if(msg.match("Manufacturing")){
 			vehicleYearOfManufact.focus();}*/
 		else if(msg.match("Date")){
 			vehicleRegDate.focus();
 		}else if(msg.match("Loan Amount")){
 			document.getElementById("loanAmount").focus();
 		}
 		
 		alert(msg);
 		document.getElementById("save").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }
	 else 
	 {
		 if(checkRefinaceReqInfo.value=="Y" && assetNature.value=="O" && (vehicleChesisNo.value == ''||engineNumber.value == ''||vehicleRegNo.value == ''))
			 {
			 if(vehicleChesisNo.value == '')
		 			msg += '* Chasis Number is required.\n';
			 if(engineNumber.value == '')
		 			msg += '* Engine Number is required.\n';
			 if(vehicleRegNo.value == '')
		 			msg += '* Registration Number is required.\n';
			 
			 if(msg.match("vehicleChesisNo")){
				 vehicleChesisNo.focus();
		 		}else if(msg.match("engineNumber")){
		 			engineNumber.focus();
		 			
		 		}else if(msg.match("vehicleRegNo")){
			 			engineNumber.focus();
		 		}	
			 		alert(msg);
			 	 	document.getElementById("save").removeAttribute("disabled","true");
			 	 	DisButClass.prototype.EnbButMethod();
			 	 	return false;
			 
			 
			 }
		 
		 if(assetcollateral.value == "C")
		 {
			 if(trim(vehicleModel.value) == '' || trim(collateralSecurityMargin.value) == ''){
				 if(collateralSecurityMargin.value == ''){
		 				msg += '* Vehicle LTV % is required.\n';
		 			}
				 if(vehicleModel.value == ''){
	 				msg += '* Vehicle Model is required.\n';
	 				
	 				if(collateralSecurityMargin.value == ''){
		 				collateralSecurityMargin.focus();
	 				}else if(vehicleModel.value == ''){
		 				vehicleModel.focus();
	 				
	 				}
	 			}
				alert(msg);
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
		 		return false;
			 }
	 	}
		else
		{
			var loanAmount=document.getElementById("loanAmount").value;
			var collateralSecurityMargin=document.getElementById("collateralSecurityMargin").value;
			var msg="";
	 		if(loanAmount=="")
	 			msg="*Loan Amount is required.\n";
	 		if(collateralSecurityMargin=="")
	 			msg=msg+"*Vehicle LTV % is required.";
	 		if(msg!="")
	 		{
	 			alert(msg);
	 			document.getElementById("loanAmount").focus();
	 			DisButClass.prototype.EnbButMethod();
		 		return false;	 			
	 		}
	 		else
	 		{
	 			var value=parseFloat(removeComma(assetsCollateralValue.value));
	 			var loan=parseFloat(removeComma(loanAmount));
	 			var ltv=parseFloat(removeComma(collateralSecurityMargin));
	 			var ltvR=(loan*100)/value;
	 			ltv=ltv.toFixed(2);
	 			ltvR=ltvR.toFixed(2);
	 			if(ltvR!=ltv)
	 			{
	 				alert("Value of Vehicle LTV is wrong it should be "+ltvR);
	 				DisButClass.prototype.EnbButMethod();
			 		return false;
	 			}
	 		}
	 	}	 		
	}

	
	 if(idvValue< loan)
		 {
		 alert("Idv amount less then Loan amount.Do you want to Continue....");
		 }
	if(document.getElementById("assetsIdVehicle").value!='')
    {
		var primaryId = document.getElementById("assetsIdVehicle").value;
	    var assetsType = document.getElementById("v2").value;	
		document.getElementById("VehicleForm").action=contextPath+"/collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("VehicleForm").submit();
	 	return true;
	 }
	 else
	 {		
		document.getElementById("VehicleForm").action=contextPath+"/collateralVehicleProcessAction.do?method=saveCollateralDetails";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("VehicleForm").submit();	
     }
}

function clearMakeLov()
{
	document.getElementById("vehicleMake").value='';
	document.getElementById("make_model_id").value='';
	document.getElementById("vehicleModel").value='';
	document.getElementById("assetState").value='';
	document.getElementById("txtStateCode").value='';
	document.getElementById("collateralSecurityMargin").value='';
	document.getElementById("usageType").value='';
    document.getElementById("assetManufactDesc").value='';	
	
}

function calBuiltUpArea()
{
	var constArea=document.getElementById("propertyConstruct").value;
	var pArea=document.getElementById("propertyArea").value;
	var msg="* % of Construction is required.";
	var msg1="* Area is required.";

		if(constArea=='' && pArea=='')
		{
			alert(msg+"\n"+msg1);
			document.getElementById("buildUpArea").value="";
			document.getElementById("buildUpAreaSQMTR").value="";
		}
		else if(constArea=='')
		{
			alert(msg);
			document.getElementById("buildUpArea").value="";
			document.getElementById("buildUpAreaSQMTR").value="";
		}
		else if(pArea=='')
		{
			alert(msg1);
			document.getElementById("buildUpArea").value="";
			document.getElementById("buildUpAreaSQMTR").value="";
		}
		else
		{
			var area = ((pArea*constArea)/100).toFixed(2);
			document.getElementById("buildUpArea").value=area;
			document.getElementById("buildUpAreaSQMTR").value=(area*0.09).toFixed(2);
		}	
}
function setArea()
{
	if(document.getElementById("propertyArea").value!="")
	{
		calBuiltUpArea();
	}
	else
	{
		document.getElementById("buildUpArea").value="";
		document.getElementById("buildUpAreaSQMTR").value="";
	}
}
function calBuiltUpAreaSQMT()
{
	var area=document.getElementById("buildUpArea").value;
	if(area!="")
		document.getElementById("buildUpAreaSQMTR").value=(area*0.09).toFixed(2);
}
function txnLtvPerc()
{
	var txnValue=removeComma(document.getElementById("assetsCollateralValue").value);
	var loanAmount=document.getElementById("loanAmt").value;
	if(!txnValue=='' && txnValue>0)
	{
		var calLtv=(loanAmount/txnValue)*100;
		document.getElementById("collateralSecurityMargin").value=calLtv.toFixed(2);
	}
	else
	{
		document.getElementById("collateralSecurityMargin").value="0.00";
	}
}


function tvLtvPerc()
{
	var tvValue=removeComma(document.getElementById("techValuation").value);
	var loanAmount=document.getElementById("loanAmt").value;
	if(!tvValue=='' && tvValue>0 )
	{
		var calLtv=(loanAmount/tvValue)*100;
		document.getElementById("tvLTV").value=calLtv.toFixed(2);
	}
	else
	{
		document.getElementById("tvLTV").value="0.00";
	}
}
function docLtvPerc()
{
	var docValue=removeComma(document.getElementById("docValue").value);
	var loanAmount=document.getElementById("loanAmt").value;
	if(!docValue=='' && docValue>0)
	{
		var calLtv=((loanAmount/docValue)*100);
		document.getElementById("docLTV").value=calLtv.toFixed(2);
	}
	else
	{
		document.getElementById("docLTV").value="0.00";
	}
}
function acLtvPerc()
{
	var addConstruction=removeComma(document.getElementById("addConstruction").value);
	var loanAmount=document.getElementById("loanAmt").value;
	if(!addConstruction=='' && addConstruction>0)
	{
		var calLtv=(loanAmount/addConstruction)*100;
		document.getElementById("acLTV").value=calLtv.toFixed(2);
	}
	else
	{
		document.getElementById("acLTV").value="0.00";
	}
}

function clearSupplierLovChild(){
	document.getElementById("machineSupplier").value="";
 	document.getElementById("lbxmachineSupplier").value="";	
}
///////////////////////////////////////////////////////////////////////////////////start by sachin
function tvLtvPerc1()
{
	var tvValue=removeComma(document.getElementById("technicalValuation1").value);
	var loanAmount=document.getElementById("loanAmt").value;
	if(!tvValue=='' && tvValue>0)
	{
		var calLtv=(loanAmount/tvValue)*100;
		document.getElementById("LTV1").value=calLtv.toFixed(2);
	}
	else
	{
		document.getElementById("LTV1").value="0.00";
	}
}

function tvLtvPerc2()
{
	var tvValue=removeComma(document.getElementById("technicalValuation2").value);
	var loanAmount=document.getElementById("loanAmt").value;
	if(!tvValue=='' && tvValue>0)
	{
		var calLtv=(loanAmount/tvValue)*100;
		document.getElementById("LTV2").value=calLtv.toFixed(2);
	}
	else
	{
		document.getElementById("LTV2").value="0.00";
	}
}
function calAreaSqf()
{
	var constArea=document.getElementById("propertyConstruct").value;
	var buildUpArea=document.getElementById("buildUpArea").value;
	if(buildUpArea=='')
	{
		buildUpArea=document.getElementById("buildUpArea").value="0.00";	
	}
	
	var pArea =((buildUpArea*100)/constArea).toFixed(2);
	if(pArea=='Infinity' || pArea=='NaN')
		pArea = '0';
//	alert("constArea"+constArea);
//	alert("buildUpArea"+buildUpArea);
//	alert("pArea"+pArea);
	
		
		//	var area = ((pArea*constArea)/100).toFixed(2);
			document.getElementById("propertyArea").value=pArea;
			document.getElementById("buildUpAreaSQMTR").value=(buildUpArea*0.09).toFixed(2);
			
}

function calVehicleLTV()
{
		var loanAmount=document.getElementById("loanAmount").value;
		var vehicleCost= document.getElementById("assetsCollateralValue").value;
		//alert("Loan Amount : "+loanAmount);
		if(loanAmount!="" && vehicleCost!="")
		{
			var loan=parseFloat(removeComma(loanAmount));
			var value=parseFloat(removeComma(vehicleCost));
			if(value>=loan)
			{
				var ltv=(loan*100)/value;
				ltv=ltv.toFixed(2)+"";
				//alert("LTV : " + ltv);
				if(loanAmount == 0.00)
				{
					ltv=0.00;
					ltv=ltv.toFixed(2)+"";
				}
				document.getElementById("collateralSecurityMargin").value=ltv;
			}
		}
}

function getEditField(){
	var assetNature=document.getElementById("assetNature").value;
	if(assetNature!='' && assetNature=='N'){
		document.getElementById("idv").value='';
		document.getElementById("valuationCost").value='';
		document.getElementById("gridValue").value='';
		// document.getElementById("idv").setAttribute("disabled",true);
		document.getElementById("valuationCost").setAttribute("disabled",true);
		//document.getElementById("gridValue").setAttribute("disabled",true);
		document.getElementById("gridValue").removeAttribute('readonly',false);
		document.getElementById("vehicleYearOfManufact").removeAttribute('readonly',false);
		document.getElementById("valuationCost").removeAttribute("disabled",true);
	}
	if(assetNature!='' && assetNature=='O'){
		 document.getElementById("idv").removeAttribute("disabled",true);
		document.getElementById("valuationCost").removeAttribute("disabled",false);
		//document.getElementById("gridValue").removeAttribute("disabled",true);
		document.getElementById("gridValue").setAttribute('readonly',true);
		document.getElementById("vehicleYearOfManufact").setAttribute('readonly',true);
	//	document.getElementById("valuationCost").setAttribute("disabled",true);
	
	}
	
}

function calValuationAmount()
{
	var val1 =document.getElementById("techValuation").value;
	var val2 = document.getElementById("technicalValuation1").value;
	var val3 = document.getElementById("technicalValuation2").value;
	var method = document.getElementById("valuationMethodId").value;
	
	if(val1=='' && (method=='LOT' || method=='AOT' || method=='AOFS' || method=='AOTF' || method=='TV1'))
	{
		val1=0;
		alert("Technical Valuation1 is required. ");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		val1=removeComma(val1);
	}
	if(val2=='' && (method=='LOT' || method=='AOT' || method=='AOST' || method=='AOFS' || method=='TV2'))
	{
		val2=0;
		alert("Technical Valuation2 is required. ");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		val2=removeComma(val2);
	}
	if(val3=='' && (method=='LOT' || method=='AOT' || method=='AOST' || method=='AOTF' || method=='TV3'))
	{
		val3=0;
		alert("Technical Valuation3 is required. ");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		val3=removeComma(val3);
	}
	
	if(method=='LOT'){
		var val=Math.min(val1,val2,val3);
		document.getElementById("valuationAmount").value=val.toFixed(2);
		document.getElementById("assetsCollateralValue").value=val.toFixed(2);
		}
		if(method=='AOT'){
			
			var val=((val1+val2+val3)/3);
			document.getElementById("valuationAmount").value=val.toFixed(2);
			document.getElementById("assetsCollateralValue").value=val.toFixed(2);
			}
		if(method=='AOFS'){
			
			var val=((val1+val2)/2);
			document.getElementById("valuationAmount").value=val.toFixed(2);
			document.getElementById("assetsCollateralValue").value=val.toFixed(2);
			}
		if(method=='AOST'){
			
			var val=((val2+val3)/2);
			document.getElementById("valuationAmount").value=val.toFixed(2);
			document.getElementById("assetsCollateralValue").value=val.toFixed(2);
			}
		if(method=='AOTF'){
			
			var val=((val1+val3)/2);
			document.getElementById("valuationAmount").value=val.toFixed(2);
			document.getElementById("assetsCollateralValue").value=val.toFixed(2);
			}
		if(method=='TV1'){
			
			var val=val1;
			document.getElementById("valuationAmount").value=val.toFixed(2);
			document.getElementById("assetsCollateralValue").value=val.toFixed(2);
			}
		if(method=='TV2'){
			
			var val=val2;
			document.getElementById("valuationAmount").value=val.toFixed(2);
			document.getElementById("assetsCollateralValue").value=val.toFixed(2);
			}
		if(method=='TV3'){
			
			var val=val3;
			document.getElementById("valuationAmount").value=val.toFixed(2);
			document.getElementById("assetsCollateralValue").value=val.toFixed(2);
			}
		if(method=='OTHER'){
			document.getElementById("valuationAmount").value='';
			document.getElementById("valuationAmount").removeAttribute("readonly",true);
			}
}
function calAssetsCollateralValue()
{
	var val =document.getElementById("valuationAmount").value;
	document.getElementById("assetsCollateralValue").value=val;
			
}

//Ashish change for invoice in asset colletral

function updateAssetInvoiceDtl(recordId)
{

	 var contextPath =document.getElementById("contextPath").value ;
	 var assetsId =document.getElementById('assetsId').value ;
	 var assetCollateralType =document.getElementById('assetCollateralType').value ;
	 var vAssetManufact =document.getElementById('assetManufact');
	 var vMachineSupplier =document.getElementById('machineSupplier');
	 var vSiRdName =document.getElementById('siRdName');
	 var vProductDesc =document.getElementById('productDesc');
	 var vInvoiceQuantity =document.getElementById('invoiceQuantity');
	 var vInvoiceAmount =document.getElementById('invoiceAmount');
	 var vInvoicePrice =document.getElementById('invoicePrice');
	 var vInvoiceNo =document.getElementById('invoiceNo');
	 var vInvoiceDate =document.getElementById('assetInvoiceDate');
	 var vInvoicelocation =document.getElementById('invoicelocation');
	 var msg= '';
		
		if(vAssetManufact.value == '')
			msg += '* OEM is required.\n';
		if(vMachineSupplier.value == '')
			msg += '* Supplier / ND is required.\n';
		if(vSiRdName.value == '')
			msg += '* Si / Rd Name is required.\n';
		if(vProductDesc.value == '')
			msg += '* Product Type is required.\n';
		if(vInvoiceQuantity.value == '')
			msg += '* Quantity is required.\n';
		if(vInvoiceAmount.value == '')
			msg += '* Invoice Amount is required.\n';
		if(vInvoicePrice.value == '')
			msg += '* Price is required.\n';
		if(vInvoiceNo.value == '')
			msg += '* Invoice No is required.\n';
		if(vInvoiceDate.value == '')
			msg += '* Invoice Date is required.\n';
		if(vInvoicelocation.value == '')
			msg += '* Invoice location is required.\n';
		
		if(msg.match("Asset Manufact")){
			vAssetManufact.focus();
		}else if(msg.match("Machine Supplier")){
			vMachineSupplier.focus();
		}else if(msg.match("Rd")){
			vSiRdName.focus();
		}else if(msg.match("Product")){
			vProductDesc.focus();
		}else if(msg.match("Quantity")){
			vInvoiceQuantity.focus();
		}else if(msg.match("Rd")){
			vInvoiceAmount.focus();
		}else if(msg.match("Price")){
			vInvoicePrice.focus();
		}else if(msg.match("Invoice No")){
			vInvoiceNo.focus();
		}else if(msg.match("Date")){
			vInvoiceDate.focus();
		}else if(msg.match("location")){
			vInvoicelocation.focus();
		} 
	 	
		if(msg !='')
		{
			alert(msg);
	 		document.getElementById("addInvoice").removeAttribute("disabled","true");
	 		DisButClass.prototype.EnbButMethod();
	 		return false;	 
		 }
		else 
		{
			 document.getElementById("invoiceAsset").action = contextPath+"/collateralAssetInvoiceAction.do?method=saveAssetInvoiceDtl&recordId="+recordId+"&assetsId="+assetsId+"&assetCollateralType="+assetCollateralType;
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("invoiceAsset").submit();
			 return true;
			
		} 
	
}
function updateAssetDispatchDtl(recordId)
{
	 var contextPath =document.getElementById("contextPath").value ;
	 var assetsId =document.getElementById('assetsId').value ;
	 var assetCollateralType =document.getElementById('assetCollateralType').value ;
	 var invoiceProductTypeDesc =document.getElementById('invoiceProductTypeDesc');
	 var msg= '';
	 if(invoiceProductTypeDesc.value == '')
		 msg += 'Please Select Product Type in Dispatch Details.\n';
	 if(msg !='')
		{
			alert(msg);
	 		document.getElementById("addInvoice").removeAttribute("disabled","true");
	 		DisButClass.prototype.EnbButMethod();
	 		return false;	 
		 }
	 else
		{
	 document.getElementById("invoiceAsset").action = contextPath+"/collateralAssetInvoiceAction.do?method=saveAssetDispatchDtl&recordId="+recordId+"&assetsId="+assetsId+"&assetCollateralType="+assetCollateralType;
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("invoiceAsset").submit();
	 return true;
		}
}
	
function deleteAssetInvoiceDetail(confirmStatus)
{	
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById("contextPath").value ;
	var assetsId =document.getElementById('assetsId').value ;
	var assetCollateralType =document.getElementById('assetCollateralType').value ;
		
	if(checkboxInvoice(document.getElementsByName('chk1'),confirmStatus))
	    { 
		   if(confirm("Do you want to Delete ?"))
		   {
			var recordIds = "";
			var itable=document.getElementById('invoicetable');
			var len=itable.rows.length-2;
			for (var i=1; i<len; i++)
	        {
		        if (document.getElementById('chk1'+i).checked==true)
	               {	
		        	 recordIds = recordIds + document.getElementById('chk1'+i).value+",";
		           // alert("c_value"+c_value);				   
		            }
	              }		    	 
	       	document.getElementById("invoiceAsset").action = contextPath+"/collateralAssetInvoiceAction.do?method=deleteAssetInvoiceDtl&recordIds="+recordIds+"&type=invoice&assetsId="+assetsId+"&assetCollateralType="+assetCollateralType;
	  	 	document.getElementById("processingImage").style.display = '';
	  	 	document.getElementById("invoiceAsset").submit();
		   }
		   else
		   {
			   document.getElementById("addInvoice").removeAttribute("disabled","true");
			   DisButClass.prototype.EnbButMethod();
			   return false;
			   
		   }
	    }
	      else
		   {
		    	alert("You are Requested please select atleast one!!!");
		    	DisButClass.prototype.EnbButMethod();
		    return false;
	        }
}

function deleteAssetDispatchDetail(confirmStatus)
{	
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById("contextPath").value ;
	var assetsId =document.getElementById('assetsId').value ;
	var assetCollateralType =document.getElementById('assetCollateralType').value ;
	
	if(checkboxInvoice(document.getElementsByName('chk'),confirmStatus))
	    { 
		   if(confirm("Do you want to Delete ?"))
		   {
	       var recordIds = "";
    	   	itable=document.getElementById('dispatchtable');
			var len=itable.rows.length-1;
    	   	for (var i=1; i<len; i++)
            {
				if (document.getElementById('chk'+i).checked==true)
				{	
					recordIds = recordIds + document.getElementById('chk'+i).value+",";
				// alert("c_value"+c_value);				   
				}
	        }	
	    	document.getElementById("invoiceAsset").action = contextPath+"/collateralAssetInvoiceAction.do?method=deleteAssetInvoiceDtl&recordIds="+recordIds+"&type=dispatch&assetsId="+assetsId+"&assetCollateralType="+assetCollateralType;
	  	 	document.getElementById("processingImage").style.display = '';
	  	 	document.getElementById("invoiceAsset").submit();
		   }
		   else
		   {
			   document.getElementById("addInvoice").removeAttribute("disabled","true");
			   DisButClass.prototype.EnbButMethod();
			   return false;
			   
		   }
	    }
	      else
		   {
		    	alert("You are Requested please select atleast one!!!");
		    	DisButClass.prototype.EnbButMethod();
		    return false;
	        }
}

function saveAssetDetail()
{
	var contextPath =document.getElementById("contextPath").value ;
	var invoiceFlag=document.getElementById('invoiceFlag').value;
	var dispatchFlag=document.getElementById('dispatchFlag').value;
	var assetCollateralType =document.getElementById('assetCollateralType').value ;
	var assetsId =document.getElementById('assetsId').value ;
	var vAssetManufact =document.getElementById('assetManufact');
	var vMachineSupplier =document.getElementById('machineSupplier');
	var vSiRdName =document.getElementById('siRdName');
	var invoiceTotalAmount =document.getElementById('invoiceTotalAmount');
	var invoiceNumber =document.getElementById('invoiceNumber').value;
	var productDesc =document.getElementById('productDesc').value;
	var msg= '';
	if(vAssetManufact.value == '')
		msg += '* OEM is required.\n';
	if(vMachineSupplier.value == '')
		msg += '* Supplier / ND is required.\n';
	if(vSiRdName.value == '')
		msg += '* Si / Rd Name is required.\n';
	if(msg.match("Asset Manufact")){
		vAssetManufact.focus();
	}else if(msg.match("Machine Supplier")){
		vMachineSupplier.focus();
	}else if(msg.match("Rd")){
		vSiRdName.focus();
	}
	
	if(msg !='')
	{
		alert(msg);
 		document.getElementById("addInvoice").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;	 
	 }
	
	if(invoiceFlag=='Y'|| invoiceNumber=='FALSE'|| productDesc!='')
	{
		alert("Please add Invoice Details first");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	}
	if(dispatchFlag=='Y')
	{
		alert("Please add Dispatch Details first");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	}
	document.getElementById("invoiceAsset").action = contextPath+"/collateralAssetInvoiceAction.do?method=saveInvoiceDtl&assetsId="+assetsId+"&assetCollateralType="+assetCollateralType+"&invoiceTotalAmount="+invoiceTotalAmount;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("invoiceAsset").submit();
		
	
}
function modifyInvoice(recordId)
{	
	var contextPath = document.getElementById("contextPath").value;
	var assetsId =document.getElementById('assetsId').value ;
	var assetCollateralType =document.getElementById('assetCollateralType').value ;
	document.getElementById("invoiceAsset").action=contextPath+"/collateralAssetInvoiceAction.do?method=fetchIndividualInvoice&recordId="+recordId+"&assetsId="+assetsId+"&assetCollateralType="+assetCollateralType ;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("invoiceAsset").submit();
  
}
function modifyDispatch(id)
{	
	var contextPath = document.getElementById("contextPath").value;
	var assetsId =document.getElementById('assetsId').value ;
	var assetCollateralType =document.getElementById('assetCollateralType').value ;
	document.getElementById("invoiceAsset").action=contextPath+"/collateralAssetInvoiceAction.do?method=fetchIndividualDispatch&recordId="+id+"&assetsId="+assetsId+"&assetCollateralType="+assetCollateralType ;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("invoiceAsset").submit();
  
}
function multiply()
{
   var qnty = document.getElementById('invoiceQuantity').value;
   var price = document.getElementById('invoicePrice').value;
   var discount = document.getElementById('invoiceDiscount').value;
   var tax= document.getElementById('taxAmount').value;
   if(discount=="")
	   discount=0;
   if(tax=="")
	   tax=0;
   document.getElementById('invoiceAmount').value = +(qnty * price-discount) + +tax;
   return true;
 
}
function updateInvoiceFlag()
{
   document.getElementById('invoiceFlag').value='Y';
   return true;
}
function updateDispatchFlag()
{
   document.getElementById('dispatchFlag').value='Y';
   return true;
}
function checkboxInvoice(chk,confirm)
{

	if(confirm=='N'||confirm=='')
	{
	   var zx=0;
	   var flag=0;
	   for(var i=0;i<chk.length;i++)
		{
			
			if(chk[zx].checked==false)
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
	}
	else
	{
		flag=1;
	}
	return flag;
}


//Ashish change ended    

//Parvez for Gold Ornament 

/*function calGoldGms(){
	var grossWeight =parseInt(document.getElementById("grossWeight").value);
	var deduction = parseInt(document.getElementById("deduction").value);
	var netWeight = parseInt(document.getElementById("netWeight").value);
	
	
	
	 if(grossWeight == '')
	 {
		 grossWeight=0; 
		
	 }
		
		 
	 if(deduction == '')
	 {
		deduction=0;
	}
	 if(grossWeight==0 && deduction>0)
	 {
		alert("Gross Weight is required");
		return false;
	 }
	 else{
	 netWeight=grossWeight-deduction;
	 document.getElementById("netWeight").value=netWeight.toFixed(3);
	return true;
	 }

	}
	
function calLoanAmt(){
	//alert("In calLoanAmt");
		var rateGoldOrnament =parseInt(document.getElementById("rateGoldOrnament").value);
		var goldOrnamentLTV = parseInt(document.getElementById("goldOrnamentLTV").value);
		var netWeight = parseInt(document.getElementById("netWeight").value);
        var loanAmountEligible= parseInt(document.getElementById("loanAmountEligible").value);
		 if(rateGoldOrnament == '')
		 {
			 rateGoldOrnament=0;
		}
		 if(goldOrnamentLTV == '')
		 {
			 goldOrnamentLTV=0;
		}
		 
		 if(netWeight == '')
		 {
			 netWeight=0;
		}

		//var loanAmountEligible=0;
		
		
		 loanAmountEligible=rateGoldOrnament*goldOrnamentLTV*netWeight;
		  
		  
		
		 document.getElementById("loanAmountEligible").value=loanAmountEligible.toFixed(3);
		 return true;
		
		 }

		



	function calNetOrnamentVal(){
	//alert("In calNetOrnamentVal");
		var rateGoldOrnament =parseInt(document.getElementById("rateGoldOrnament").value);
		
		var netWeight = parseInt(document.getElementById("netWeight").value);
		var netOrnamentValue = parseInt(document.getElementById("netOrnamentValue").value);
		
		 if(rateGoldOrnament == '')
		 {
			 rateGoldOrnament=0; 
			
		 }
			
		if(netWeight == '')
		 {
			 rateGoldOrnament=0; 
			
		 }
		//var netOrnamentValue =0;
			
		 netOrnamentValue=rateGoldOrnament*netWeight;
		 
		
		 document.getElementById("netOrnamentValue").value=netOrnamentValue.toFixed(3);
		return true;
		 }
	
	*/
	
function calgoldOrnamnet()
	 {
	var grossWeight =document.getElementById("grossWeight");
  var deduction = document.getElementById("deduction");
  var netWeight = document.getElementById("netWeight");
  var loanAmountEligible= document.getElementById("loanAmountEligible");
	var goldOrnamentLTV= document.getElementById("goldOrnamentLTV");
	var rateGoldOrnament= document.getElementById("rateGoldOrnament");
	var netOrnamentValue= document.getElementById("netOrnamentValue");
	var quantity= document.getElementById("quantity");
	
if(rateGoldOrnament==undefined ||rateGoldOrnament==null ||rateGoldOrnament.value=='' )
	 {
		 rateGoldOrnament=0; 
		
	 }else{
	  rateGoldOrnament= removeComma(document.getElementById("rateGoldOrnament").value);
	 }
		
	if( netWeight==undefined || netWeight== null  ||netWeight.value=='')
	 {
		 netWeight=0; 
		
	 }else{
	  netWeight=removeComma(document.getElementById("netWeight").value);
	 }
	if( goldOrnamentLTV==undefined || goldOrnamentLTV== null  ||goldOrnamentLTV.value=='')

	{
	   goldOrnamentLTV=0;
	 }else{
	  goldOrnamentLTV= removeComma(document.getElementById("goldOrnamentLTV").value);
	 }
	 
	 if( grossWeight==undefined || grossWeight== null  ||grossWeight.value=='')
	 {
	   grossWeight=0;
	 }
	 else{
	  grossWeight= removeComma(document.getElementById("grossWeight").value);
	 }
	 if( deduction==undefined || deduction== null  ||deduction.value=='')
	 {
	   deduction=0;
	 } else{
	  deduction= removeComma(document.getElementById("deduction").value);
	 }
	  if( netOrnamentValue==undefined || netOrnamentValue== null  ||netOrnamentValue.value=='')
	  {
	   netOrnamentValue=0;
	 }else{
	  netOrnamentValue= removeComma(document.getElementById("netOrnamentValue").value);
	 }
	 
	   
if(quantity<1)
    {
	       alert("Quantity Can not be less than or equal to Zero. ");		
			document.getElementById("Save").removeAttribute("disabled","true");
			
			    DisButClass.prototype.EnbButMethod();
		    	return false;;
       }		   
		if(deduction>grossWeight)
       {
	       alert("Deduction Weight should be less than Gross Weight. ");
		   document.getElementById("grossWeight").value='';
		   document.getElementById("deduction").value='';
		   DisButClass.prototype.EnbButMethod();
	     return false;
          } 
		  
     else{
	 
	 document.getElementById("grossWeight").value=grossWeight.toFixed(3);
	 document.getElementById("deduction").value=deduction.toFixed(3);
	 
// Changes started by Kumar Aman	 
   // netWeight=grossWeight-deduction;
	 netWeight = ((grossWeight-deduction)*goldOrnamentLTV)/100;
// Changes Ended by Kumar Aman    
    document.getElementById("netWeight").value=netWeight.toFixed(3);
    
		loanAmountEligible=rateGoldOrnament*goldOrnamentLTV*netWeight/100;
		document.getElementById("loanAmountEligible").value=loanAmountEligible.toFixed(3);
		netOrnamentValue=rateGoldOrnament*netWeight;
		document.getElementById("netOrnamentValue").value=netOrnamentValue.toFixed(3);
		
		
		return true;

	 }
	}	

function checkNumber2(val, e, Max, san){
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
					if(splt[1].length > 3){	 // FOR DECIMAL					
						var f1V = parseInt(splt[0].length);
						var f2V = f1V+4; // INCREMENT 1
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



function numbersonly2(e, san, Max){
	var dynaVal = san;
	//alert(document.getElementById(dynaVal).maxLength = Max+3);
	document.getElementById(dynaVal).maxLength = Max+4; // FOR DECIMAL-1
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false; //disable key press
	}
}

function defaultValueLtv()
{
//alert("I m in default Ltv value");
var goldOrnamentLTV= document.getElementById("goldOrnamentLTV");

if(goldOrnamentLTV == null ||goldOrnamentLTV.value=='')
{
 document.getElementById("goldOrnamentLTV").value=100;
		    DisButClass.prototype.EnbButMethod();	    
	    	return false;;
  }
}

function searchMfrSupplMappingRecords()
{
	DisButClass.prototype.DisButMethod();
	var manufacturerId=document.getElementById("manufacturerId").value;
	var manufacturerDesc=document.getElementById("manufacturerDesc").value;
	if(manufacturerId=="" && manufacturerDesc =="" )
	{
		alert(" Please select at least one field.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("ManufacturerSupplierMappingForm").action=sourcepath+"/mfrSupplMappingBehindAction.do?manufacturerId="+manufacturerId+"&manufacturerDesc="+manufacturerDesc;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ManufacturerSupplierMappingForm").submit();
}


function openMfrSupplMappingScreen()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("ManufacturerSupplierMappingForm").action=sourcepath+"/mfrSupplMappingDispatchAction.do?method=openMfrSupplMappingScreen";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ManufacturerSupplierMappingForm").submit();
}


function saveMfrSupplMappingScreen()
{
	DisButClass.prototype.DisButMethod();
	var msg1='',msg2='',msg3='',i;
	var manufacturerId=document.getElementById("manufacturerId").value;
	var menufacturerDesc=document.getElementById("manufacturerDesc").value;
	var supplierDesc=document.getElementById("lbxSupplierId").value;

	
	if(manufacturerId=="")
	{
	   msg1="*Manufacturer is required.\n";		
		
	}
	if(menufacturerDesc=="")
	{
	    msg2="*Manufacturer Description is required.\n";		
		
	}
	
	if(supplierDesc=="")
	{
		msg3="*Dealer/Supplier is required.\n";
		
		
	}
			
	if(msg1!=''||msg2!=''||msg3!='')
	{
		alert(msg1+msg2+msg3);
		if(msg1!='')
		{
			document.getElementById('manufacturerId').focus();
		}
		else if(msg2!='')
		{
			document.getElementById('menufacturerDesc').focus();
		}
		
		else if(msg3!='')
		{
			document.getElementById('supplierDesc').focus();
		}
				
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	if(supplierDesc==null||supplierDesc==''){
		supplierDesc="";

		
	}else{
		supplierDesc=supplierDesc+"|";
		//alert("supplierDesc::"+supplierDesc);
		
	}
	
	var supplierArr=new Array();
	supplierArr=supplierDesc.split("|");
	//alert("supplierArr::::"+supplierArr);
	for( i=1;i<supplierDesc.length;i++){
	
	}	
		
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("ManufacturerSupplierMappingAddForm").action=sourcepath+"/mfrSupplMappingDispatchAction.do?method=saveMfrSupplMappingRecord&supplier="+supplierArr;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ManufacturerSupplierMappingAddForm").submit();
}


function updateMfrSupplMappingScreen()
	{
	DisButClass.prototype.DisButMethod();
	var msg1='',msg2='',msg3='';
	var manufacturerId=document.getElementById("manufacturerId").value;
	var menufacturerDesc=document.getElementById("manufacturerDesc").value;
	var supplierDesc=document.getElementById("lbxSupplierId").value;
	var DListValues ="";
	
	if(manufacturerId=="")
	{
	   msg1="*Manufacturer is required.\n";		
		
	}
	if(menufacturerDesc=="")
	{
	    msg2="*Manufacturer Description is required.\n";		
		
	}
	
	if(supplierDesc=="")
	{
		msg3="*Dealer/Supplier is required.\n";
		
		
	}
			
	if(msg1!=''||msg2!=''||msg3!='')
	{
		alert(msg1+msg2+msg3);
		if(msg1!='')
		{
			document.getElementById('manufacturerId').focus();
		}
		else if(msg2!='')
		{
			document.getElementById('menufacturerDesc').focus();
		}
		
		else if(msg3!='')
		{
			document.getElementById('supplierDesc').focus();
		}
				
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(supplierDesc==null||supplierDesc==''){
		supplierDesc="";
	}else{
		supplierDesc=supplierDesc+"|";
		//alert("supplierDesc::"+supplierDesc);
		
	}
	
	var supplierArr=new Array();
	supplierArr=supplierDesc.split("|");
	//alert("supplierArr::::"+supplierArr);
	for( i=1;i<supplierDesc.length;i++){
	
	}
		
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("ManufacturerSupplierMappingAddForm").action=sourcepath+"/mfrSupplMappingDispatchAction.do?method=updateMfrSupplMappingRecords&supplier="+supplierArr;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ManufacturerSupplierMappingAddForm").submit();
	

}
function allProduct()
{
	document.getElementById("productbutton").style.display='none';
	document.getElementById("showProductDesc").options.length = 0;
	document.getElementById("lbxProductId").value='';
	
}
function selectiveProduct()
{
	document.getElementById("productbutton").style.display="";
	document.getElementById("showProductDesc").options.length = 0;
	document.getElementById("lbxProductId").value='';
}

function saveUserProductAccess()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var userId=document.getElementById("lbxUserId").value;
	var userName=document.getElementById("showUserDesc").value;
	var productId=document.getElementById("lbxProductId").value;
	var productName=document.getElementById("showProductDesc").value;
	var all=document.getElementById("allselection").checked;
	var selective=document.getElementById("singleselection").checked;

	if((!userName) && ((!all) && (!selective)))
	{
		alert("* User Name is required. \n * Select either All or Selective for Product Access.");
		document.getElementById("userButton").focus();
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled");
		return false;
	}
	
	if((!userId) || (!userName))
	{
		alert("* User Name is required.");
		document.getElementById("userButton").focus();
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled");
		return false;
	}
	
	if((!all) && (!selective))
	{
		alert("* Select either All or Selective for Product Access.");
		document.getElementById("allselection").focus();
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled");
		return false;
	}
	if((selective) && ((!productId)&&(!productName)))
	{
		alert("* Product is required.");
		document.getElementById("productbutton").focus();
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled");
		return false;
	}
	
	document.getElementById("userProductAccessAdd").action=sourcepath+"/openAddUserProductAccess.do?method=saveUserProduct";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userProductAccessAdd").submit();
}

function clearAll()
{
	if(document.getElementById("refreshInSave").value=="refresh")
	{
		document.getElementById("showUserDesc").value="";
		document.getElementById("lbxUserId").value="";
		document.getElementById("showProductDesc").value="";
		document.getElementById("lbxProductId").value="";
	}
	if(document.getElementById("allselection").checked)
	{
		document.getElementById("productbutton").style.display='none';	
	}
	if(document.getElementById("singleselection").checked)
	{
		document.getElementById("productbutton").style.display="";	
	}

}

function openAddUserProductAcess()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("userProductAccess").action=sourcepath+"/openAddUserProductAccess.do?method=openAddUserProduct";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userProductAccess").submit();
}
function searchUserproduct(val)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	if((!document.getElementById("showUserDescSearch").value) && !(document.getElementById("showProductDescSearch").value))
	{
		//alert("Please select at least one criteria");
		alert(val);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	document.getElementById("userProductAccess").action=sourcepath+"/openAddUserProductAccess.do?method=searchUserProduct";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userProductAccess").submit();
}
function clearAll()
{
	document.getElementById("showUserDescSearch").value="";
	document.getElementById("lbxUserId").value="";
	document.getElementById("showProductDescSearch").value="";
	document.getElementById("lbxProductId").value="";
}
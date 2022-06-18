function trim(str) {
	return ltrim(rtrim(str));
	}


function getVehicleApprovalSearchGrid()
{
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;

	document.getElementById("vehicleApprovalGrid").action=sourcepath+"/vehicleApprovalGridBehindAction.do?method=getVehicleApprovalSearchGrid";
	document.getElementById("processingImage").style.display = '';
	
	document.getElementById("vehicleApprovalGrid").submit();
	DisButClass.prototype.EnbButMethod();
	
	//document.getElementById("save").removeAttribute("disabled","true");
	return false;
}


function searchVehicleApprovalGrid()
{
	DisButClass.prototype.DisButMethod();
	
	var lbxProductID= document.getElementById("lbxProductID").value;
	var lbxscheme= document.getElementById("lbxScheme").value;
	var sourcepath=document.getElementById("contextPath").value;
	
	if(trim(lbxProductID) == '' || trim(lbxscheme) =='')
	{
		var msg= '';
		if(trim(lbxProductID) == ''){
			msg += '* Product is required.\n';
		}
		if(trim(lbxscheme) == ''){
			msg += '* Scheme is required.\n';
		}
		if(msg.match("Product")){
			document.getElementById("productButton").focus();
			
		}else if(msg.match("Scheme")){
			document.getElementById("schemeButton").focus();
	}
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}
	else
	{

	document.getElementById("vehicleApprovalGrid").action=sourcepath+"/vehicleApprovalGridBehindAction.do?method=getVehicleApprovalSearchGrid";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("vehicleApprovalGrid").submit();
	DisButClass.prototype.EnbButMethod();
	}

	return true;
}


function newAddVehicalApprovalGrid()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("vehicleApprovalGrid").action=sourcepath+"/vehicleApprovalGridDispatchAction.do?method=openAddVehicleGrid";
	document.getElementById("processingImage").style.display='';
	document.getElementById("vehicleApprovalGrid").submit();
	DisButClass.prototype.EnbButMethod();
	return false;
}

function saveVehicleApprovalGrid()
{

	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var lbxProductID= document.getElementById("lbxProductID").value;
	var lbxscheme= document.getElementById("lbxScheme").value;
	var vehicleType= document.getElementById("vehicleType").value;
	var manufacturer= document.getElementById("manufactId").value;
	var model= document.getElementById("modelDescId").value;
	var branchAmt= document.getElementById("branchAmt").value;
	var nationalAmt= document.getElementById("nationalAmt").value;
	var hoAmt= document.getElementById("hoAmt").value;
	var gridBranchAmt= document.getElementById("gridBranchAmt1").value;
	var gridNationalAmt= document.getElementById("gridNationalAmt1").value;
	var gridHoAmt= document.getElementById("gridHoAmt1").value;
	
	
	if(trim(lbxProductID) == '' || trim(lbxscheme) =='' || trim(manufacturer) == '' || trim(model) == '' )
		{
		var msg= '';
		if(trim(lbxProductID) == ''){
			msg += '* Product is required.\n';
		}
		if(trim(lbxscheme) == ''){
			msg += '* Scheme is required.\n';
		}
		if(trim(manufacturer) == ''){
			msg += '* Manufacturer is required.\n';
		}
		if(trim(model) == ''){
			msg += '* Model is required.\n';
		}
		
		if(msg.match("Product")){
			document.getElementById("productButton").focus();
			
		}
		else if(msg.match("Scheme")){
			document.getElementById("schemeButton").focus();
		}
		else if(msg.match("Manufacturer")){
			document.getElementById("manufactButton").focus();
		}
		else if(msg.match("Model")){
			document.getElementById("modelButton").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		}	
	
	 else if(vehicleType == 'N') 
		{
		
		if(trim(branchAmt) == ''|| trim(nationalAmt) == ''|| trim(hoAmt) == '')
		{
		var msg= '';
		
		if(trim(branchAmt) == ''){
			msg += '* Branch LTV is required.\n';
		}
		if(trim(nationalAmt) == ''){
			msg += '* National Head LTV  is required.\n';
		}
		if(trim(hoAmt) == ''){
			msg += '* Head Office LTV  is required.\n';
		}
		
		if(msg.match("Branch")){
			document.getElementById("branchAmt").focus();
		}
		else if(msg.match("National")){
			document.getElementById("nationalAmt").focus();
		}
		else if(msg.match("Head"))
		{
			document.getElementById("hoAmt").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		
		}
		else if(trim(branchAmt) > 100|| trim(nationalAmt) > 100|| trim(hoAmt) > 100)
			{
			var msg= '';
			
			if(trim(branchAmt) > 100){
				msg += '* Branch LTV should not be greater than 100.\n';
			}
			if(trim(nationalAmt) > 100){
				msg += '* National Head LTV should not be greater than 100.\n';
			}
			if(trim(hoAmt) > 100){
				msg += '* Head Office LTV  should not be greater than 100.\n';
			}
			
			if(msg.match("Branch")){
				document.getElementById("branchAmt").focus();
			}
			else if(msg.match("National")){
				document.getElementById("nationalAmt").focus();
			}
			else if(msg.match("Head"))
			{
				document.getElementById("hoAmt").focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;	
			}
		else
		{
			document.getElementById("vehicleApprovalForm").action=sourcepath+"/vehicleApprovalGridDispatchAction.do?method=saveVehicleApprovalGrid";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("vehicleApprovalForm").submit();
			return true;
		}
		}
		
		
	 else if(vehicleType == 'O') 
		{
		
		if(trim(gridBranchAmt) == '0.00'|| trim(gridNationalAmt) == '0.00'|| trim(gridHoAmt) == '0.00')
		{
		var msg= '';
		
		if(trim(gridBranchAmt) == '0.00'){
			msg += '* Branch Amount is required.\n';
		}
		if(trim(gridNationalAmt) == '0.00'){
			msg += '* National Head Amount  is required.\n';
		}
		if(trim(gridHoAmt) == '0.00'){
			msg += '* Head Office Amount  is required.\n';
		}
		
		if(msg.match("Branch")){
			document.getElementById("gridBranchAmt1").focus();
		}
		else if(msg.match("National")){
			document.getElementById("gridNationalAmt1").focus();
		}
		else if(msg.match("Head")){
			document.getElementById("gridHoAmt1").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		
		}
		else
		{
			document.getElementById("vehicleApprovalForm").action=sourcepath+"/vehicleApprovalGridDispatchAction.do?method=saveVehicleApprovalGrid";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("vehicleApprovalForm").submit();
			return true;
		}
		}   
	
	
}

function updateVehicleApprovalGrid()
{

	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var lbxProductID= document.getElementById("lbxProductID").value;
	var lbxscheme= document.getElementById("lbxScheme").value;
	var vehicleType= document.getElementById("vehicleType").value;
	var manufacturer= document.getElementById("manufactId").value;
	var model= document.getElementById("modelDescId").value;
	var branchAmt= document.getElementById("branchAmt").value;
	var nationalAmt= document.getElementById("nationalAmt").value;
	var hoAmt= document.getElementById("hoAmt").value;
	var gridBranchAmt= document.getElementById("gridBranchAmt1").value;
	var gridNationalAmt= document.getElementById("gridNationalAmt1").value;
	var gridHoAmt= document.getElementById("gridHoAmt1").value;
	
	
	if(trim(lbxProductID) == '' || trim(lbxscheme) =='' || trim(manufacturer) == '' || trim(model) == '' )
		{
		var msg= '';
		if(trim(lbxProductID) == ''){
			msg += '* Product is required.\n';
		}
		if(trim(lbxscheme) == ''){
			msg += '* Scheme is required.\n';
		}
		if(trim(manufacturer) == ''){
			msg += '* Manufacturer is required.\n';
		}
		if(trim(model) == ''){
			msg += '* Model is required.\n';
		}
		
		if(msg.match("Product")){
			document.getElementById("productButton").focus();
			
		}
		else if(msg.match("Scheme")){
			document.getElementById("schemeButton").focus();
		}
		else if(msg.match("Manufacturer")){
			document.getElementById("manufactButton").focus();
		}
		else if(msg.match("Model")){
			document.getElementById("modelButton").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		}	
	
	 else if(vehicleType == 'N') 
		{
		
		if(trim(branchAmt) == ''|| trim(nationalAmt) == ''|| trim(hoAmt) == '')
		{
		var msg= '';
		
		if(trim(branchAmt) == ''){
			msg += '* Branch LTV is required.\n';
		}
		if(trim(nationalAmt) == ''){
			msg += '* National Head LTV  is required.\n';
		}
		if(trim(hoAmt) == ''){
			msg += '* Head Office LTV  is required.\n';
		}
		
		if(msg.match("Branch")){
			document.getElementById("branchAmt").focus();
		}
		else if(msg.match("National")){
			document.getElementById("nationalAmt").focus();
		}
		else if(msg.match("Head"))
		{
			document.getElementById("hoAmt").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		
		}
		else if(trim(branchAmt) > 100|| trim(nationalAmt) > 100|| trim(hoAmt) > 100)
		{
		var msg= '';
		
		if(trim(branchAmt) > 100){
			msg += '* Branch LTV should not be greater than 100.\n';
		}
		if(trim(nationalAmt) > 100){
			msg += '* National Head LTV should not be greater than 100.\n';
		}
		if(trim(hoAmt) > 100){
			msg += '* Head Office LTV  should not be greater than 100.\n';
		}
		
		if(msg.match("Branch")){
			document.getElementById("branchAmt").focus();
		}
		else if(msg.match("National")){
			document.getElementById("nationalAmt").focus();
		}
		else if(msg.match("Head"))
		{
			document.getElementById("hoAmt").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		}
		else
		{
			document.getElementById("vehicleApprovalForm").action=sourcepath+"/vehicleApprovalGridDispatchAction.do?method=updateVehicleApprovalGrid";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("vehicleApprovalForm").submit();
			return true;
		}
		}
		
		
	 else if(vehicleType == 'O') 
		{
		
		if(trim(gridBranchAmt) == '0.00'|| trim(gridNationalAmt) == '0.00'|| trim(gridHoAmt) == '0.00')
		{
		var msg= '';
		
		if(trim(gridBranchAmt) == '0.00'){
			msg += '* Branch Amount is required.\n';
		}
		if(trim(gridNationalAmt) == '0.00'){
			msg += '* National Head Amount  is required.\n';
		}
		if(trim(gridHoAmt) == '0.00'){
			msg += '* Head Office Amount  is required.\n';
		}
		
		if(msg.match("Branch")){
			document.getElementById("gridBranchAmt1").focus();
		}
		else if(msg.match("National")){
			document.getElementById("gridNationalAmt1").focus();
		}
		else if(msg.match("Head")){
			document.getElementById("gridHoAmt1").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		
		}
		else
		{
			document.getElementById("vehicleApprovalForm").action=sourcepath+"/vehicleApprovalGridDispatchAction.do?method=updateVehicleApprovalGrid";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("vehicleApprovalForm").submit();
			return true;
		}
		}   
	
	
}

function checkVehicleType()
{
	var amountTable = document.getElementById('gridtable');
	
	if(document.getElementById("vehicleType").value=='N')
	{
		for(i=1;i<=amountTable.rows.length-1;i++){
			
		document.getElementById("gridBranchAmt"+i).value='0.00';
		document.getElementById("gridBranchAmt"+i).disabled='true';
		document.getElementById("gridNationalAmt"+i).value='0.00';
		document.getElementById("gridNationalAmt"+i).disabled='true';
		document.getElementById("gridHoAmt"+i).value='0.00';
		document.getElementById("gridHoAmt"+i).disabled='true';
		}
		
		document.getElementById("branchAmt").disabled='false';
		document.getElementById("branchAmt").removeAttribute("disabled","true");
		document.getElementById("nationalAmt").disabled='false';
		document.getElementById("nationalAmt").removeAttribute("disabled","true");
		document.getElementById("hoAmt").disabled='false';
		document.getElementById("hoAmt").removeAttribute("disabled","true");
		return true;
	}
	else
{
	document.getElementById("branchAmt").value='';	
	document.getElementById("branchAmt").disabled='true';
	document.getElementById("nationalAmt").value='';
	document.getElementById("nationalAmt").disabled='true';
	document.getElementById("hoAmt").value='';
	document.getElementById("hoAmt").disabled='true';
	
	for(i=1;i<=amountTable.rows.length-1;i++)
		{
	document.getElementById("gridBranchAmt"+i).disabled='false';
	document.getElementById("gridBranchAmt"+i).removeAttribute("disabled","true");
	document.getElementById("gridNationalAmt"+i).disabled='false';
	document.getElementById("gridNationalAmt"+i).removeAttribute("disabled","true");
	document.getElementById("gridHoAmt"+i).style.display='false';
	document.getElementById("gridHoAmt"+i).removeAttribute("disabled","true");
		}
	return true;
}	
}


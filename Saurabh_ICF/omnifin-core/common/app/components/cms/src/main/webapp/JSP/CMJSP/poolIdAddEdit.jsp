<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 


<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/cmLoanInitjs.js"></script>
   
		<script type="text/javascript">
	        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
			
			function defaultFocus()
			{
				document.getElementById('sourcingForm').loanNoButton.focus();
			}
		</script>

		<script type="text/javascript">
function validateDocUpload()
{
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		document.getElementById("docFile").focus(); 
			document.getElementById("save").removeAttribute("disabled");
	    return false; 
	}
var fup = document.getElementById('docFile');
var file_Name = fup.value;
var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
 
if(ext == "xls" || ext == "XLS")
{
	return true;
} 
else
{
	alert("Upload xls file only.");
	fup.focus();
		document.getElementById("save").removeAttribute("disabled");
	return false;
}
  
 }
function submitPoolIdUpload()
{
 

	var sourcepath=document.getElementById("contextPath").value;

	//if((document.getElementById("poolID").value)==""){
	//alert("Please select PoolID ");
	//document.getElementById("save").removeAttribute("disabled");
	//return false;
	//}
	 
	 if((document.getElementById("poolName").value)==""){
	alert("Please select Pool Name ");
	document.getElementById("save").removeAttribute("disabled");
	return false;
	}
	   if((document.getElementById("poolCreationDate").value)==""){
	alert("Please select Pool CreationDate ");
	document.getElementById("save").removeAttribute("disabled");
	return false;
	}
	  if((document.getElementById("cutOffDate").value)==""){
	alert("Please select Cutoff Date ");
	document.getElementById("save").removeAttribute("disabled");
	return false;
	}
	 if((document.getElementById("poolType").value)==""){
	alert("Please select Pool Type ");
	document.getElementById("save").removeAttribute("disabled");
	return false;
	}
	 if((document.getElementById("instituteID").value)==""){
	alert("Please select Institute ID ");
	document.getElementById("save").removeAttribute("disabled");
	return false;
	}
	 
	 
	 
	if(validateDocUpload())
	{
	 
	 document.getElementById("sourcingForm").action=sourcepath+"/poolIdMakerProcessAction.do?method=submitPoolIdUpload";
	 document.getElementById("sourcingForm").submit();
	 return true;
	
	
                  //document.getElementById("poolCreationForm").action=sourcepath+"/poolCreationProcessAction.do?method=submitPoolUpload";
	              //document.getElementById("poolCreationForm").submit();
	              //return true;
	
	}
	
}

</script>
	<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->	
	</head>
	<body onload="enableAnchor();">
	<div id="centercolumn">
	<div id="revisedcontainer">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	
	<html:form action="/poolIdMakerProcessAction" method="post" styleId="sourcingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
    <fieldset>
    
    <logic:present name="poolIdMaker"> 
	<legend><bean:message key="lbl.poolIdMaker"/></legend>   
	</logic:present>	    
	
	<logic:present name="poolIdAuthor"> 
	<legend><bean:message key="lbl.poolIdAuthor"/></legend>   
	</logic:present>   
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		
        	 <!-- ---------      For Pool ID Add Edit Saved Data          -------------------------------------------- -->
	
		 <logic:present name="poolIdEditSavedList"> 	
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 <tr>			 	
		   <td><bean:message key="lbl.poolID"/></td>
		   <td>
	        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${requestScope.poolIdEditSavedList[0].poolID}" readonly="true" tabindex="-1"/>   
           </td>
           <td><bean:message key="lbl.PoolName"/><font color="red">*</font></td>
	       <td>
	        <html:text styleClass="text" property="poolName" styleId="poolName" value="${requestScope.poolIdEditSavedList[0].poolName}" maxlength="50"  readonly="true"/>
	       </td>
	 
        </tr>
        
	    <tr>   
            <td><bean:message key="lbl.PoolCreationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="poolCreationDate" styleId="poolCreationDate" value="${requestScope.poolIdEditSavedList[0].poolCreationDate}" maxlength="20" disabled="true" readonly="true"/>
	       </label></td>	
         
     
     
	       <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="cutOffDate" styleId="cutOffDate" value="${requestScope.poolIdEditSavedList[0].cutOffDate}" maxlength="20" disabled="true" readonly="true"/>
	       </label></td>	
		
		</tr>
		<tr>
		 <td><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      <td><label>
	      <html:select property="poolType" styleId="poolType" styleClass="text" value="${requestScope.poolIdEditSavedList[0].poolType}" disabled="true">
	           <html:option value="">--Select--</html:option>
		        <html:option value="S">Securitized</html:option>
		        <html:option value="R">Re-finance</html:option>
		   </html:select> 
	        </label>
	      </td>
     
	      <td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	    
		   <td>
			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="${requestScope.poolIdEditSavedList[0].instituteID}" readonly="true" tabindex="-1"/>   
		     <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
		     <input type="hidden" name="hcommon" id="hcommon" />
			<!-- <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button> -->  
           </td>
        <td></td>
        <td></td>
	       </tr>
	     
		  
	</table>
	<table>
	<tr>
			
	        <td align="left">  			
           <!--   <html:button property="button" value="Error" styleId="err" styleClass="topformbutton2" title="Alt+E" accesskey="S" onclick="this.disabled='true';return searchInstrument();"> </html:button> -->
	      
	      </td>
	      <td></td>
	      <td></td>
	      <td></td>
		</tr>
 
	</table>
        	</logic:present>	
        	

	</td>
    </tr>
    </table>	 
	</fieldset>	
	
	
	<fieldset>	


	<legend><bean:message key="lbl.poolIdMakerRecords"/></legend>   
	
   
 
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
        
        
    <tr class="white2">
    
       <td><input type="checkbox" name="chkd" id="allchkd"  onclick="allChecked();" /></td>         
        <td><b><bean:message key="lbl.poolID"/></b></td>
        <td><strong><bean:message key="lbl.LoanID"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.customerName"/></strong></td>
        
        <td><b><bean:message key="lbl.loanCustType"/></b></td>
        <td><strong><bean:message key="lbl.loanCustCate"/></strong></td>
        <td><strong><bean:message key="lbl.loanCustConstitution"/></strong></td>
        <td><strong><bean:message key="lbl.loanCustBusSeg"/></strong></td>
        <td><strong><bean:message key="lbl.loanIndustry"/></strong></td>
        
        <td><b><bean:message key="lbl.loanSubIndustry"/></b></td>
        <td><strong><bean:message key="lbl.loanDisbursalDate"/></strong></td>
        <td><strong><bean:message key="lbl.loanMaturityDate"/></strong></td>
        <td><strong><bean:message key="lbl.loanTenure"/></strong></td>
        <td><strong><bean:message key="lbl.loanBalanceTenure"/></strong></td>
        
        <td><b><bean:message key="lbl.loanInstNum"/></b></td>
        <td><strong><bean:message key="lbl.LoanAdvEMINum"/></strong></td>
        <td><strong><bean:message key="lbl.loanInitRate"/></strong></td>
        <td><strong><bean:message key="lbl.loanDisbursalStatus"/></strong></td>
        <td><strong><bean:message key="lbl.loanNPAFlag"/></strong></td>
        
        <td><b><bean:message key="lbl.loanDPD"/></b></td>
        <td><strong><bean:message key="lbl.LoanDPDString"/></strong></td>
         <td><strong><bean:message key="lbl.LoanAssetCost"/></strong></td>
        <td><strong><bean:message key="lbl.loanAmount"/></strong></td>
        <td><strong><bean:message key="lbl.loanEmi"/></strong></td>
        <td><strong><bean:message key="lbl.loanAdvEMIAmount"/></strong></td>
        
        <td><b><bean:message key="lbl.loanBalPrinc"/></b></td>
        <td><strong><bean:message key="lbl.loanOverduePrin"/></strong></td>
        <td><strong><bean:message key="lbl.loanRecvdPrinc"/></strong></td>
        <td><strong><bean:message key="lbl.loanOverdueInsNum"/></strong></td>
        <td><strong><bean:message key="lbl.loanOverdueAmount"/></strong></td>
        
        <td><b><bean:message key="lbl.loanBalInsNum"/></b></td>
        <td><strong><bean:message key="lbl.loanBalInstAmount"/></strong></td>
        
        
     </tr>
 

         
         	    <logic:present name="editList">
         <logic:iterate id="mainListobj" name="editList">
         <tr class="white1">
         
          <input type="hidden" name="poolIDList" id="poolIDList" value="${mainListobj.poolID}"/>
            <input type="hidden" name="loanIDList" id="loanIDList" value="${mainListobj.loanID}"/>
            
          <td><input type="checkbox" name="chk" id="chk" value="" /></td>
          <td>${mainListobj.poolID}
          <input type="hidden" name="poolID" id="poolId" value="${mainListobj.poolID}"/></td>
       
           <td>${mainListobj.loanID}</td>
          <td>${mainListobj.loanProduct}</td>
          
           <td>${mainListobj.loanScheme}</td>
          <td>${mainListobj.loanCustomerID}</td>
              <td>${mainListobj.loanCustomerType}</td>
          <td>${mainListobj.loanCustomerCtegory}</td>
          <td>${mainListobj.loanCustomerConstituion}</td>
          
           <td>${mainListobj.loanCustomerBusinessSeg}</td>
          <td>${mainListobj.loanIndustry}</td>
            <td>${mainListobj.loanSubIndustry}</td>
          <td>${mainListobj.loanDisbursalDate}</td>
          <td>${mainListobj.loanMaturityDate}</td>
          
           <td>${mainListobj.loanTenure}</td>
          <td>${mainListobj.loanBalanceTenure}</td>
           <td>${mainListobj.loanInstallmentNum}</td>
          <td>${mainListobj.loanAdvEMINUm}</td>
          <td>${mainListobj.loanInitRate}</td>
          
           <td>${mainListobj.loanDisbursalStatus}</td>
          <td>${mainListobj.loanNPAFlag}</td>
          <td>${mainListobj.loanDPD}</td>
          <td>${mainListobj.loanDPDString}</td>
           <td>${mainListobj.loanAssetCost}</td>
          
         <td>${mainListobj.loanAmount}</td>
          <td>${mainListobj.loanEMI}</td>
          <td>${mainListobj.loanAdvEMIAmount}</td>
          <td>${mainListobj.loanBalPrincipal}</td>
           <td>${mainListobj.loanOverduePrincipal}</td>
	 
          
          <td>${mainListobj.loanReceivedPrincipal}</td>
          <td>${mainListobj.loanOverdueInstNo}</td>
          <td>${mainListobj.loanOverdueAmount}</td>
           <td>${mainListobj.loanBalnceInstNo}</td>
          <td>${mainListobj.loanBalInstlAmount}</td>
	 
          
          
          </tr>
         <tr>
          </tr>
          
         </logic:iterate>
         </logic:present>
         
 </table>    
   
 
 </td>
</tr>
</table>

<logic:present name="editList">
<logic:notEmpty name="editList">
 <table>
	<tr>	
	       <td align="left">  
			 <button type="button" name="button" id="Delete" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return deletePoolIDEdit();"><bean:message key="button.delete" /></button> 
            <button type="button" name="button"  id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick=" onAddPoolIDEdit();"><bean:message key="button.add" /></button> 
	      </td>
	      <td></td>
	      <td></td>
	      <td></td>
		</tr>
 
	</table>
	</logic:notEmpty>
 </logic:present>

	</fieldset>
	
	
</html:form>
	</div>
	
	
 <logic:present name="delete">
 <script type="text/javascript">
 if(${delete})
 	alert("Selected record is deleted successfully.");
 else
 	alert("Selected record is not deleted.");
 
 </script>
 </logic:present>

    
 <logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("Data not Found ");
	
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdAddEditBehindAction.do";
	    document.getElementById("sourcingForm").submit();
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked1')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdAddEditBehindAction.do";
	    document.getElementById("sourcingForm").submit();
	}
	</script>
	</logic:present>
	
	    


</div>
</body>
</html:html>
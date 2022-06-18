<%--
      Author Name-  Kaustuv Ranjan    
      Date of creation -08/04/2011
      Purpose-   Providing User Interface To A Customer/Applicant       
      Documentation-      
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/applicant.js"></script>
	
<script type="text/javascript">		
		function linkCustomer()
		{
			curWin = 0;
			otherWindows = new Array();
			
			var applType = document.getElementById("applicantType").value;
	 	    var url="<%=request.getContextPath() %>/link.do?applType="+applType;
	        window.child=window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
	        otherWindows[curWin++] =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
		    window.child.moveTo(800,300);
            if (window.focus) 
            {
                window.child.focus();
                return false;
            }
	        return true;
	    }
	    function linkGCD(id,cType1)
        {
        	curWin = 0;
			otherWindows = new Array();
            if(cType1!=''&& cType1=='CORPORATE')
		    	cType='C';
       		else
       			cType='I';
            if(cType=='I')
	    	{
				var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag=notEdit&gcdReq=yes";
				window.child = window.open(url,'name1','height=400,width=1000,top=200,left=250');
				otherWindows[curWin++] =window.open(url,'name1','height=400,width=1000,top=200,left=250');
	    		return true;
	    	}
	   		else if(cType=='C')
	    	{
				var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag=notEdit&gcdReq=yes";
				window.child = window.open(url,'name1','height=420,width=1130,top=200,left=250');
				otherWindows[curWin++] =window.open(url,'name1','height=400,width=1000,top=200,left=250');
				return true;
        	}    
       }
	//Rohit Chnzges starts for sarva Surksha
	    function linkGCDnew(id,cType1,applType)
        {
        	curWin = 0;
			otherWindows = new Array();
            if(cType1!=''&& cType1=='CORPORATE')
		    	cType='C';
       		else
       			cType='I';
            if(cType=='I')
	    	{
				var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&applType="+applType+"&updateFlag=notEdit&gcdReq=yes";
				window.child = window.open(url,'name1','height=400,width=1000,top=200,left=250');
				otherWindows[curWin++] =window.open(url,'name1','height=400,width=1000,top=200,left=250');
	    		return true;
	    	}
	   		else if(cType=='C')
	    	{
				var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&applType="+applType+"&updateFlag=notEdit&gcdReq=yes";
				window.child = window.open(url,'name1','height=420,width=1130,top=200,left=250');
				otherWindows[curWin++] =window.open(url,'name1','height=400,width=1000,top=200,left=250');
				return true;
        	}    
       }	
//Rohit Changes End for Sarva Surksha...
    </script>
   

    </head>
  
<body onunload="closeAllWindowCallUnloadBodyAn();" onclick="parent_disable();"  onload="enableAnchor();checkChanges('applicantForm');document.getElementById('applicantForm').applicantType.focus();init_fields();" >   

<html:errors />



<div id=centercolumn>
<div id=revisedcontainer>
 <fieldset>
 
   <logic:present name="loanId">
<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 </logic:present>

  </fieldset>


<html:form action="/creditProcessing" styleId="applicantForm" method="post">
<html:hidden property="optionIndv" styleId="optionIndv" value="${optionIndv}"/>

<html:javascript formName="ApplicantDynavalidatorForm" />
<fieldset>	
	<legend><bean:message key="lbl.deals"/></legend>  
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
    			<tr class="white2">	    
        			<td width="10%"><b><bean:message key="lbl.customerID"/></b></td>
					<td ><strong><bean:message key="lbl.customerName"/></strong></td>
        			<td ><b><bean:message key="lbl.applicantType"/></b></td>
        			<td><b><bean:message key="lbl.applicantCategory"/></b></td>
        			<td><b><bean:message key="lbl.guaranteeAmount"/></b></td>
        			<td><b><bean:message key="lbl.photoUpload"/></b></td>
				</tr>
	           <logic:present name="custDetail">
	           <logic:iterate id="subCustDetail" name="custDetail">	
				<tr class="white1">
						<td><a href="#" id="anchor0" onclick="return linkGCDnew('${subCustDetail.customerId }','${subCustDetail.applicantCategory}','${subCustDetail.applicantType}');">${subCustDetail.customerId}</a></td>
	      			<td>${subCustDetail.customerName }</td>
	      			<td>${subCustDetail.applicantDesc }</td>
					<td>${subCustDetail.applicantCategory }</td>
					<td ><logic:equal name="subCustDetail" property="applicantType"	value="GUARANTOR">${subCustDetail.guaranteeAmount }</logic:equal></td>
					<td ><logic:equal name="subCustDetail" property="applicantCategory"
																	value="INDIVIDUAL">
						<a href="#" id="anchor0" onclick="return linkPhotoUpload('${subCustDetail.customerId }','ViewMode');">Photo Upload</a>
																	
																</logic:equal>
							</td>		
				
				
				</tr>				
				</logic:iterate>
				</logic:present> 
			</table>
			</td>
		</tr>
		</table>
</fieldset>

</html:form>

</div>
</div>
<script>
	parent.menu.document.test.getFormName.value = document.getElementById("applicantForm").id;
</script>
  </body>
 
  
</html:html>
 
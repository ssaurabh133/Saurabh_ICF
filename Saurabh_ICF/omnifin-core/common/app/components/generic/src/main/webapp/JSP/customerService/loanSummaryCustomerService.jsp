<%--
      Author Name-  Prashant kumar    
      Date of creation -12/05/2011
      Purpose-   InitLoan Info       
      Documentation-      
      
 --%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		   <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/customerService/customerServiceScript.js"></script>

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
	
	<body oncontextmenu="return false" >
	
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">

<form action="summaryService" method="post" name="SummaryServiceForm">

 <fieldset>	
	     <legend><bean:message key="lbl.loanSummary"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2" >        
        			 <td><strong><bean:message key="lbl.loanAccountNo" /></strong></td>
		             <td><strong><bean:message key="lbl.customername" /></strong></td>
                     <td><strong><bean:message key="lbl.loanStatus" /></strong></td>
                     <td ><strong><bean:message key="lbl.DisbursalStatus" /></strong></td>
		             <td><strong><bean:message key="lbl.fileTrackStatus" /></strong></td>
                     <td ><strong><bean:message key="lbl.loanApprovalStatus" /></strong></td>
                     <td><strong><bean:message key="lbl.repaymentMode" /></strong></td>
		             <td><strong><bean:message key="lbl.repaySflatRate" /></strong></td>
                     <td><strong><bean:message key="lbl.repaySeffectRate" /></strong></td>
                     <td ><strong><bean:message key="lbl.installmentType" /></strong></td>
		             <td><strong><bean:message key="lbl.insMode" /></strong></td>
                     <td ><strong><bean:message key="lbl.sectorType" /></strong></td>
                     <td><strong><bean:message key="lbl.loanNPAFlag" /></strong></td>
                     <td><strong><bean:message key="lbl.loanDPD" /></strong></td>
                     <td><strong><bean:message key="lbl.LoanDPDString" /></strong></td>
                     <td ><strong><bean:message key="lbl.tenure" /></strong></td>
		             <td><strong><bean:message key="lbl.maturityDate" /></strong></td>
                  
	               </tr>
	               <logic:present name="loanSummaryList">
	                 <logic:iterate id="subLoanSummaryList" name="loanSummaryList">	 
		               <tr class="white1"> 
                      
                          <td >${subLoanSummaryList.loanNo }</td>
		                  <td >${subLoanSummaryList.customerName }</td>
		                  <td >${subLoanSummaryList.loanStatus }</td>
		                  <td >${subLoanSummaryList.disbursalStatus }</td> 	
		                  <td>${subLoanSummaryList.fileStatus }</td> 
		                  
		                  <td >${subLoanSummaryList.loanApprovalDate }</td>
		                  <td >${subLoanSummaryList.loanRepaymentMode }</td>
		                  <td >${subLoanSummaryList.loanFlatRate }</td>
		                  <td >${subLoanSummaryList.loanEffRate }</td> 	
		                  <td>${subLoanSummaryList.loanInstallmentType }</td> 
		                  
		                  <td >${subLoanSummaryList.loanInstallmentMode }</td>
		                  <td>${subLoanSummaryList.sectorType }</td>
		                  <td >${subLoanSummaryList.npaFlag }</td>
		                  <td >${subLoanSummaryList.loanDpd }</td>
		                  <td >${subLoanSummaryList.loanDpdString }</td> 	
		                  <td>${subLoanSummaryList.loanTenure }</td>
		                  <td>${subLoanSummaryList.loanMaturityDate }</td>	
		                 		

		               </tr>	 
                    </logic:iterate>
                    </logic:present>      
	        
                </table>
                </td>
                </tr>
                </table>
        </fieldset>
        <!-- Nishant space starts -->
         <fieldset>	
	     <legend><bean:message key="lbl.vehicleDetails"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2" >        
        			 <td><strong><bean:message key="lbl.assetNature" /></strong></td>
		             <td><strong><bean:message key="lbl.vehicleMake" /></strong></td>
                     <td><strong><bean:message key="lbl.vehicleModel" /></strong></td>
                     <td ><strong><bean:message key="lbl.manufact" /></strong></td>
		             <td><strong><bean:message key="lbl.supplier" /></strong></td>
                     <td ><strong><bean:message key="lbl.registrationNumber" /></strong></td>
                     <td><strong><bean:message key="lbl.chasisNumber" /></strong></td>
		             <td><strong><bean:message key="lbl.engineNumber" /></strong></td>
                     <td><strong><bean:message key="lbl.yearofManufacture" /></strong></td>

	               </tr>
	               <logic:present name="vehicleDetailsList">
	                 <logic:iterate id="subVehicleDetailsList" name="vehicleDetailsList">	 
		               <tr class="white1"> 
                          <td >${subVehicleDetailsList.assetNature }</td>
		                  <td >${subVehicleDetailsList.vehicleMake }</td>
		                  <td >${subVehicleDetailsList.vehicleModel}</td>
		                  <td >${subVehicleDetailsList.manufact }</td> 	
		                  <td> ${subVehicleDetailsList.supplier }</td> 
		                  <td >${subVehicleDetailsList.registrationNumber }</td>
		                  <td >${subVehicleDetailsList.chasisNumber }</td>
		                  <td >${subVehicleDetailsList.engineNumber }</td>
		                  <td >${subVehicleDetailsList.yearofManufacture }</td> 	
		               </tr>	 
                    </logic:iterate>
                    </logic:present>      
	        
                </table>
                </td>
                </tr>
                </table>
        </fieldset>
        <!-- Nishant space ends -->
        <fieldset>	
	     <legend><bean:message key="lbl.caseMarkingDetails"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2" >        
        			 <td><strong><bean:message key="lbl.caseStatus" /></strong></td>
		             <td><strong><bean:message key="lbl.CaseFlag" /></strong></td>
                     <td><strong><bean:message key="lbl.markingDate" /></strong></td>
                     <td><strong><bean:message key="lbl.unmarkingDate" /></strong></td>
                     <td ><strong><bean:message key="lbl.agencyName" /></strong></td>
		             <td><strong><bean:message key="lbl.otherDetails" /></strong></td>
                     <td ><strong><bean:message key="lbl.remarks" /></strong></td>
                                       
	               </tr>
	               <logic:present name="caseMarkingList">
	                 <logic:iterate id="subCaseMarkingList" name="caseMarkingList">	 
		               <tr class="white1"> 
                      
                          <td >${subCaseMarkingList.caseMarkingStatus }</td>
		                  <td >${subCaseMarkingList.caseMarkingFlag }</td>
		                  <td >${subCaseMarkingList.caseMarkingDate }</td>
		                    <td >${subCaseMarkingList.caseUnMarkingDate }</td>
		                  <td >${subCaseMarkingList.caseMarkingAgencyName }</td> 	
		                  <td>${subCaseMarkingList.caseMarkingOtherDetails }</td> 
		                  <td >${subCaseMarkingList.caseMarkingRemarks }</td>
	
		               </tr>	 
                    </logic:iterate>
                    </logic:present>      
	        
                </table>
                </td>
                </tr>
                </table>
        </fieldset>
   
        <fieldset>	
	     <legend><bean:message key="lbl.securitizationRefinanceDetails"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2" >   
					<td><strong><bean:message key="lbl.poolNo" /></strong></td>
		             <td><strong><bean:message key="lbl.PoolName" /></strong></td>      				   
        			 <td><strong><bean:message key="lbl.poolType" /></strong></td>
		             <td><strong><bean:message key="lbl.PoolCreationDate" /></strong></td>
                     <td><strong><bean:message key="lbl.agencyName" /></strong></td>
                   
                                       
	               </tr>
	               <logic:present name="secuitizationList">
	                 <logic:iterate id="subSecuitizationList" name="secuitizationList">	 
		               <tr class="white1"> 
                      
						  <td >${subSecuitizationList.secPoolNo }</td>
		                  <td >${subSecuitizationList.secPoolName }</td>
                          <td >${subSecuitizationList.secPoolType }</td>
		                  <td >${subSecuitizationList.secPoolMarkingDate }</td>
		                  <td >${subSecuitizationList.secAgencyName }</td>
		
	
		               </tr>	 
                    </logic:iterate>
                    </logic:present>      
	        
                </table>
                </td>
                </tr>
                </table>
        </fieldset>
        
        <fieldset>	
	     <legend><bean:message key="lbl.resheduleDetail"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2" >        
        			 <td><strong><bean:message key="lbl.resheduleType" /></strong></td>
		             <td><strong><bean:message key="lbl.resheduleDate" /></strong></td>
                     <td><strong><bean:message key="lbl.resheduleMaker" /></strong></td>
                     <td><strong><bean:message key="lbl.resheduleMakerRemarks" /></strong></td>
		             <td><strong><bean:message key="lbl.resheduleAuthorRemarks" /></strong></td>
                     <td><strong><bean:message key="lbl.resheduleAuthor" /></strong></td>
                   
                                       
	               </tr>
	               <logic:present name="rescheduleList">
	                 <logic:iterate id="subRescheduleList" name="rescheduleList">	 
		               <tr class="white1"> 
                      
                          <td >${subRescheduleList.reschType }</td>
		                  <td >${subRescheduleList.reschDate }</td>
		                  <td >${subRescheduleList.reschMakerName }</td>
		                  
		                   <td >${subRescheduleList.reschMakerRemarks }</td>
		                  <td >${subRescheduleList.reschAuthoremarks }</td>
		                  <td >${subRescheduleList.reschAuthorName }</td>
		
	
		               </tr>	 
                    </logic:iterate>
                    </logic:present>      
	
                </table>
                </td>
                </tr>
                </table>
        </fieldset>
        
        
          <fieldset>	
	     <legend><bean:message key="lbl.closureDetail"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2" >        
        			 <td><strong><bean:message key="lbl.closureType" /></strong></td>
		             <td><strong><bean:message key="lbl.closureDate" /></strong></td>
                     <td><strong><bean:message key="lbl.closureMaker" /></strong></td>
                     <td><strong><bean:message key="lbl.closureMakerRemarks" /></strong></td>
		             <td><strong><bean:message key="lbl.closureAuthorRemarks" /></strong></td>
                     <td><strong><bean:message key="lbl.closureAuthor" /></strong></td>
                   
                                       
	               </tr>
	               <logic:present name="closureList">
	                 <logic:iterate id="subClosureList" name="closureList">	 
		               <tr class="white1"> 
                      
                          <td >${subClosureList.closureType }</td>
		                  <td >${subClosureList.closureDate }</td>
		                  <td >${subClosureList.closureMakerName }</td>
		                  
		                   <td >${subClosureList.closureMakerRemarks }</td>
		                  <td >${subClosureList.closureAuthoremarks }</td>
		                  <td >${subClosureList.closureAuthorName }</td>
		
	
		               </tr>	 
                    </logic:iterate>
                    </logic:present>      
	
                </table>
                </td>
                </tr>
                </table>
        </fieldset>
   
      <fieldset>
<legend><bean:message key="lbl.notepadDetails" /></legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td><strong><bean:message key="lbl.noteCode"/></strong></td>
        				<td><strong><bean:message key="lbl.meetingDateTime"/></strong></td>
						<td><strong><bean:message key="lbl.personMet"/></strong></td>
						<td><strong><bean:message key="lbl.meetingLocation"/></strong></td>
						<td><strong><bean:message key="lbl.meetingRemarks"/></strong></td>
        				<td><strong><bean:message key="lbl.followUp"/></strong></td>
        				<td><strong><bean:message key="lbl.followupDateTime"/></strong></td>
        				<td><strong><bean:message key="lbl.followUpPerson"/></strong></td>
        				<td><strong><bean:message key="lbl.followUpLocation"/></strong></td>        				
        				<td><strong><bean:message key="lbl.followupRemarks"/></strong></td>
        				<td><strong><bean:message key="lbl.userName"/></strong></td>
        				<td><strong><bean:message key="lbl.creationDateTime"/></strong></td>
        				<td><strong><bean:message key="lbl.stage"/></strong></td>
    				</tr>

	  				<logic:present name="notePadList">
		 			<logic:iterate name="notePadList" id="sublist">
					<tr class="white1">
		     			<td>${sublist.noteCodeDescription}</td>
			 			<td>${sublist.meetingDate}</td>
			 			<td>${sublist.personMet}</td>
			 			<td>${sublist.meetingLocation}</td>
			 			<td>${sublist.meetingRemarks}</td>
			 			<td>${sublist.followUp}</td>
			 			<td>${sublist.followupDate}</td>
			 			<td>${sublist.followUpPerson}</td>
			 			<td>${sublist.followUpLocation}</td>
			 			<td>${sublist.followupRemarks}</td>
			 			<td>${sublist.userName}</td>
			 			<td>${sublist.creationDate}</td>
			 			<td>${sublist.txnType}</td>
	      			</tr>
	    			</logic:iterate>
	   				</logic:present>
	   				
 				</table>
			</td>
		</tr>
	</table>

</fieldset>
      
   </form>

</div>
</div>
</body>
</html:html>
<%--
      Author Name-  Ankit agarwal    
      Date of creation -15/09/2011
      Purpose-          
      Documentation-     
      
 --%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       Date currentDate = new Date();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
	   	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/tradeCheck.js"></script>
	   	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	   
  
     
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>

	
	</head>

	<body onclick="parent_disable();" onload="enableAnchor();init_fields();">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<div id="centercolumn">
	
	<div id="revisedcontainer">
 
<html:form action="/tradeBuyerDetails" styleId="tradeSuplierForm" method="POST">

	<fieldset>
	<legend><bean:message key="lbl.buyersCheck"/> </legend>
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1"> 
                   
          <html:hidden property="tradeCheckId" styleId="tradeCheckId" value="${list[0].tradeCheckId}"/>
          <tr>
                       <td ><bean:message key="lbl.refrenceNo" /><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="refrenceNO" styleId="refrenceNO" readonly="true" tabindex="-1" value="${list[0].refrenceNO}" maxlength="20"/></td>
                       <td><bean:message key="lbl.tradeAppraiserName"/><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="appraiser" styleId="appraiser" readonly="true" tabindex="-1" value="${list[0].appraiser}" maxlength="50"/></td>
                    </tr>
                 <tr>
                   <td><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
                   <td><html:text property="viewAppraisalDate" styleClass="text" styleId="viewAppraisalDate" tabindex="-1" maxlength="10" tabindex="-1" value="${list[0].appraisalDate}" readonly="true" onchange="return checkDate('appraisalDate');"/></td>
					<td><bean:message key="lbl.reportDate" /></td>
					<td><html:text property="reportDate" styleId="reportDate" maxlength="10" styleClass="text" value="${userobject.businessdate }" disabled="true" tabindex="-1"/>
        	  			 <html:hidden property="reportDate" styleClass="text" styleId="reportDate" value="${userobject.businessdate }" />
        	   </td>
                  </tr>
           <tr>
                 	<td><bean:message key="lbl.verificationMode" /></td>
						 <td>
							 <html:select property="verificationMode" styleId="verificationMode" styleClass="text" tabindex="-1" value="${list[0].verificationMode}" disabled="true">
								    <html:option value="F">Field Visit</html:option>
									<html:option value="P">Phone</html:option>
									<html:option value="W">Website</html:option>
							</html:select> 
						</td>
			
                    	 <td><bean:message key="lbl.contactPerson"/><font color="red">*</font></td>
                    	 <td><html:text styleClass="text" property="contactPerson" styleId="contactPerson" readonly="true" tabindex="-1" value="${list[0].contactPerson}" maxlength="50"/></td>
                    	</tr>                    
                     <tr> 
                    	 <td><bean:message key="lbl.relation"/><font color="red">*</font></td>
                    	 <td><html:text styleClass="text" property="relation" maxlength="50" styleId="relation" readonly="true" tabindex="-1" value="${list[0].relation}" /> </td>
		               	<td><bean:message key="lbl.mobile"/></td>
                      	<td><html:text styleClass="text" property="phone1" styleId="phone1" style="text-align: right" readonly="true" tabindex="-1" value="${list[0].phone1}" onkeypress="return isNumberKey(event);" maxlength="10" ></html:text> </td>
                      	 </tr> 
                      <tr>
                      	<td><bean:message key="lbl.tradePhone"/></td>
                      	<td><html:text styleClass="text" property="phone2" styleId="phone2" style="text-align: right" readonly="true" tabindex="-1" value="${list[0].phone2}" onkeypress="return isNumberKey(event);" maxlength="20" ></html:text> </td>
                      	<td ><bean:message key="lbl.email" /></td>
                    	<td><html:text styleClass="text" property="email" styleId="email" tabindex="-1" value="${list[0].email}" readonly="true" maxlength="100"/></td>
                      		</tr>
                      	<tr>
                      	<td ><bean:message key="lbl.TradeaverageMontSale" /><font color="red">*</font></td>
                      	<td><html:text styleClass="text"  property="averagePurchase" maxlength="18" tabindex="-1" value="${list[0].averagePurchase}" readonly="true" styleId="averagePurchaseSales"  style="text-align: right" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
                      	</td>
                      	<td><bean:message key="lbl.paymentTerms" /><font color="red">*</font></td>
                      	<td><html:text styleClass="text" property="paymentTerms"  styleId="paymentTerms" tabindex="-1" value="${list[0].paymentTerms}" readonly="true" maxlength="25" />
                      	</td>
                      	</tr>
                      	<tr>
               			<td><bean:message key="lbl.productType" /><font color="red">*</font></td>
                       <td><html:text styleClass="text" property="productType" styleId="productType" tabindex="-1" value="${list[0].productType}" readonly="true" maxlength="50"/></td>      
                       <td><bean:message key="lbl.vintageOfRelationship" /><font color="red">*</font></td>
                       <td><html:text styleClass="text" property="vintageOfRelationship" tabindex="-1" value="${list[0].vintageOfRelationship}" readonly="true" styleId="vintageOfRelationship" style="text-align: right" maxlength="3" onkeypress="return isNumberKey(event);"/></td>						    
               </tr>
                      	<tr>
 				<td><bean:message key="lbl.address" /></td>
                <td><html:text styleClass="text" property="address" tabindex="-1" value="${list[0].address}" styleId="address" readonly="true" maxlength="500" /></td>
				<td ><bean:message key="lbl.Tradecity" /></td>
          	    <td><html:text property="city" styleId="city" styleClass="text" tabindex="-1" value="${list[0].city} " readonly="true" maxlength="50"/></td>	
					</tr>
          	 <tr>
			   <td><bean:message key="lbl.stateOrProvidence" /></td>
                <td><html:text property="txtStateCode" styleClass="text" styleId="txtStateCode" tabindex="-1" value="${list[0].txtStateCode}" readonly="true" maxlength="50"/></td>
                 <td ><bean:message key="lbl.country" /></td>
                 <td><html:text property="txtCountryCode" styleClass="text" styleId="txtCountryCode" tabindex="-1" value="${list[0].txtCountryCode}" readonly="true" maxlength="50" /></td>
                      </tr> 
           <tr>
                 <td ><bean:message key="lbl.pincode" /></td>           
          	  <td ><html:text styleClass="text" property="pincode" styleId="pincode" tabindex="-1" value="${list[0].pincode}"  style="text-align: right" readonly="true" maxlength="20" onkeypress="return isNumberKey(event);"/></td>        
         		<td><bean:message key="lbl.appRemark"/><font color="red">*</font></td>
      		  <td><html:textarea property="textarea" styleId="textarea" cols="70" rows="1" styleClass="text" tabindex="-1" value="${list[0].textarea}" readonly="true" ></html:textarea></td>
      	 	 </tr>

</table>
</td>
</tr>
</table>
</fieldset>

	</html:form>
</div>
</div>
</body>
</html:html>
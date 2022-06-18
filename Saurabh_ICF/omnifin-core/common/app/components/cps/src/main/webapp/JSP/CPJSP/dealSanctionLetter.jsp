<%--
      Author Name-  Ranjeet Singh
      Date of creation -11/06/2019
      Purpose-   Deal Sanction Letter      
      
 --%>

<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
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
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/dealSanctionLetter.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>


	</head>
	<body  onload="enableAnchor();">	
	
<script type="text/javascript">
 function imposeMaxLength(Object, MaxLen)
	   {

	     if(Object.value.length > MaxLen)
	     {
	   	  alert("Max Length can not be greater than "+MaxLen);
	   	  return false;
	     }
	     return true;
	   }
 
 
 function textCounters( field,maxlimit ) {
	  if ( field.value.length > maxlimit )
		  
	  {
	    field.value = field.value.replace(/\s+/g,' ').substring( 0, maxlimit );
	    alert( 'Textarea value can only be '+maxlimit+' characters in length.' );
	 
	    return false;
	  }
	
	}
	   
</script>





<style type="text/css">
textarea {

color:#000;
font-family:arial,serif;
font-size:13px;
padding-left:2px;
width:200%;
resize:none;
height:70px;
}

</style>
				<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:form action="/dealSanctionLetterCP" method="post" styleId="loanSanctionLetterForm">
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<fieldset>	  

	 <legend><bean:message key="lbl.loanSanctionLetter"/></legend>	

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 	<tr>
		 		<td ><bean:message key="lbl.dealNo"/><font color="red">*</font></td>
		 		<td>
		 			<html:text styleClass="text" property="dealNo" styleId="dealNo" value="${loanSanctionInfo[0].dealNo}" readonly="true" maxlength="20" tabindex="-1" />
					<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(192047,'loanSanctionLetterForm','dealNo','','', '','','','customerName','','');" value=" " styleClass="lovbutton"></html:button>
					<html:hidden property="lbxDealNoHID" styleId="lbxDealNoHID" 	value="${loanSanctionInfo[0].loanId}" />
		 		</td>
		 			<td nowrap="nowrap">	<bean:message key="lbl.customerName" />	</td>
					<td nowrap="nowrap">	<html:text styleClass="text" property="customerName"	readonly="true" value="${loanSanctionInfo[0].customerName}" styleId="customerName" maxlength="50" />	</td>	
		     </tr>
		  		  <tr>	
          		<td><bean:message key="lbl.insurancePremium" /></td>
          		<td><html:text property="insurancePremium" styleId="insurancePremium" styleClass="text" onkeypress="return numbersonly(event, id, 7)"  style="text-align: right" maxlength="10" value="${loanSanctionInfo[0].insurancePremium}" /> </td>
          	<td><bean:message key="lbl.rois" /></td>
          		<td><html:text property="rois" styleId="rois" styleClass="text" onkeypress="return numbersonly(event, id, 2);" onchange="checkRate('rois')"   style="text-align: right" maxlength="5" value="${loanSanctionInfo[0].rois}" /> </td>
          	</tr>		
          		  <tr>	
          		<td><bean:message key="lbl.processingFee" /></td>
          		<td><html:text property="processingFee" styleId="processingFee" styleClass="text" onchange="checkRate('processingFee')"  onkeypress="return numbersonly(event, id, 7)"  style="text-align: right" maxlength="10" value="${loanSanctionInfo[0].processingFee}" /> </td>
          	<td><bean:message key="lbl.marginMoneyAmount" /></td>
          		<td><html:text property="marginMoneyAmount" styleId="marginMoneyAmount" styleClass="text" onkeypress="return numbersonly(event, id, 7)"   style="text-align: right" maxlength="10" value="${loanSanctionInfo[0].marginMoneyAmount}" /> </td>
          	</tr>	
          		  <tr>	
          		<td><bean:message key="lbl.marginMoneyRemarks" /></td>
          		
          		 
          		
          		 <td ><textarea name="marginMoneyRemarks" id="marginMoneyRemarks" onblur="textCounters(this,500);"  >${loanSanctionInfo[0].marginMoneyRemarks}</textarea></td>
          		
          	 
          	</tr>	
		   <tr>
		  	<td align="left">
		 	    <button type="button" name="save" id="searchButton" class="topformbutton4" title="Alt+S" accesskey="S" onclick="return saveAndGenerateReport();"><bean:message key="button.save&generate" /></button>
			    
		    </td>		     
		 	</tr>
			</table>
		
	      </td>
	</tr>	
	</table>	 
	</fieldset>
	</html:form>
</body>
</html:html>


  <logic:present name="msg">
		<script type="text/javascript">
		 if('<%=request.getAttribute("msg").toString()%>'=='saved')
			{
				alert("<bean:message key="lbl.dataSaveSuccess" />");
			}
		 else if('<%=request.getAttribute("msg").toString()%>'=='notSaved')
			{
				alert("<bean:message key="msg.NonEmiError" />");
			}
			
	</script>
	</logic:present>
  
  
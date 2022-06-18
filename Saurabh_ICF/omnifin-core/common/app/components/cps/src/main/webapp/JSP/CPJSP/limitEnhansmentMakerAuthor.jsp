
<!-- 
Author Name :- Ankit Agarwal
Date of Creation :27-02-2012
Purpose :-  Limit Enhancement Maker
-->

<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/limitEnhancement.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
	
	<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>	

		
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>
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
	<body onload="enableAnchor();checkChanges('LimitMakerAuthorForm');document.getElementById('LimitMakerAuthorForm').dealButton.focus();">
	
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
	<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
	</logic:notPresent>
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
 	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<html:form action="/newLimitEnhancement" method="post" styleId="LimitMakerAuthorForm">

	<fieldset><legend><bean:message key="lbl.limEnhMake"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">

		 <tr>	   
            <td width="13%"><bean:message key="lbl.dealNo"></bean:message></td>
		     <td width="13%">
		     <html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="20"  value="${limitAuthorList[0].dealNo}" readonly="true" tabindex="-1"/>
<!--             <html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(288,'LimitMakerForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>-->
             <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${limitAuthorList[0].lbxDealNo}" />
<!--             <html:hidden property="customerName" styleId="customerName"/>-->
		  </td>
		  
	     <td width="13%"><bean:message key="lbl.LoanNo"></bean:message> </td>
		     <td width="13%">
		     <html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${limitAuthorList[0].loanNo}" readonly="true" tabindex="-1"/>
	     		<html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="${list[0].lbxLoanNo}" />
	     	    

		  </td>
		</tr>
		<tr>
			<td><bean:message key="lbl.customerName"/></td>
	  		<td><html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" maxlength="50" value="${limitAuthorList[0].customerName}" />  
			</td> 
		</tr>
		
		 <tr>
		 	 <td><bean:message key="lbl.oldSenAmount" /></td>
			 <td><html:text styleClass="text" property="oldSenAmount" styleId="oldSenAmount" readonly="true" tabindex="-1" maxlength="18" style="text-align: right" value="${limitAuthorList[0].oldSenAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
			 
			 <td><bean:message key="lbl.oldSenDate" /></td>
			 <td><html:text styleClass="text" property="oldSenDate" styleId="oldSenDate" readonly="true" value="${limitAuthorList[0].oldSenDate}" tabindex="-1" ></html:text></td>
		 </tr> 
		 <tr>
			 <td><bean:message key="lbl.utilAmount"/></td>
			 <td><html:text styleClass="text" property="utilAmount" styleId="utilAmount" readonly="true" maxlength="18" tabindex="-1" style="text-align: right" value="${limitAuthorList[0].utilAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 	 <td><bean:message key="lbl.oldSenValidTill" /></td>
			 <td><html:text styleClass="text" property="oldSenValidTill" styleId="oldSenValidTill" readonly="true" value="${limitAuthorList[0].oldSenValidTill}" tabindex="-1" ></html:text></td>
		 </tr>
		
		 <tr>
		  	<td><bean:message key="lbl.newSenDate" /></td>
		 	<td ><html:text property="newSenDate" styleClass="text" styleId="newSenDate" value="${limitAuthorList[0].newSenDate}" readonly="true" tabindex="-1"></html:text></td>
		  	
		  	<td><bean:message key="lbl.addSenAmount"/></td>
			 <td><html:text styleClass="text" property="addSenAmount" styleId="addSenAmount" readonly="true" maxlength="18" style="text-align: right" onkeypress="return numbersonly(event, id, 18)" value="${limitAuthorList[0].addSenAmount}"  onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 </tr>
		 <tr>
		 	<td><bean:message key="lbl.newSenValidTill"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
		 	<td><html:text styleClass="text" property="applicationdate" readonly="true" maxlength="10" value="${limitAuthorList[0].applicationdate}" onchange="return checkDate('applicationdate');" ></html:text></td>
		 	<td><bean:message key="lbl.oldLoanAmount" /></td>
		 	 <td><html:text styleClass="text" property="oldLoanAmount" styleId="oldLoanAmount"  readonly="true" maxlength="18" style="text-align: right" value="${sessionDisList[0].oldLoanAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 </tr>
		 <tr>
			<td><bean:message key="lbl.loanAmountType" /><span><font color="red">*</font></span></td>
			<td>
				<html:select property="loanAmountType" styleId="loanAmountType" styleClass="text" disabled="true" value="${limitAuthorList[0].loanAmountType}" >
					<option selected="selected" value="">---Select---</option>
					<html:option value="I">INCREMENT </html:option>
					<html:option value="D">DECREMENT</html:option>
				</html:select> 
			</td>
			<td><bean:message key="lbl.totalDisbursedAmount" /></td>
		 	<td><html:text styleClass="text" property="totalDisbursedAmount" styleId="totalDisbursedAmount" readonly="true" tabindex="-1" maxlength="18" style="text-align: right" value="${sessionDisList[0].totalDisbursedAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		</tr>
		 <tr>
		 	<td><bean:message key="lbl.newLoanAmount" /></td>
		 	<td><html:text styleClass="text" property="newLoanAmount" styleId="newLoanAmount"  readonly="true" tabindex="-1" maxlength="18" style="text-align: right" value="${limitAuthorList[0].newLoanAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 	
		</tr>
					
		 <tr>
		 	<td><bean:message key="lbl.makerRemarks"/></td>
			<td><html:textarea property="makeRemarks" styleId="makeRemarks" styleClass="text" readonly="true" value="${limitAuthorList[0].makeRemarks}"></html:textarea></td>
			<td><bean:message key="lbl.authorRemarks"/></td>
			<td><html:textarea property="authorRemarks" styleId="authorRemarks" disabled="true"  value="${limitAuthorList[0].authorRemarks}" tabindex="-1" styleClass="text"></html:textarea></td>
		 </tr>
	    <tr>
		  <td>
	    	   <button type="button" class="topformbutton2" id="dealButton" onclick="return searchDealInLimit();" title="Alt+D" accesskey="D"><bean:message key="button.dealViewer" /></button>
	    	 
	      </td>
		  </tr>
		</table>	
  	 </td>	
   </tr>
	</table>
	</fieldset>	
	
</html:form>
<script>
	setFramevalues("LimitMakerAuthorForm");
</script>
</body>
</html>
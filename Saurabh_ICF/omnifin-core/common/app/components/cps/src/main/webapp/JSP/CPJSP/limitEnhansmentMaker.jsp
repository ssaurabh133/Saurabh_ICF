
<!-- 
Author Name :- Ankit Agarwal
Date of Creation :27-02-2012
Purpose :-  Limit Enhancement Maker
-->

<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		

		
<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
<!-- css and jquery for Datepicker -->
        
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/limitEnhancement.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		
	
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
	<body onload="enableAnchor();document.getElementById('LimitMakerForm').dealNoButton.focus();">

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
 	<input type="hidden" id="loanInsType" name="loanInsType" value="${disList[0].loanInsType}"/>
 	<input type="hidden" id="facilityLimitButton" name="facilityLimitButton" value="${requestScope.facilityLimitButton}"/> 	
<html:form action="/newLimitEnhancement" method="post" styleId="LimitMakerForm">

	<fieldset><legend><bean:message key="lbl.limEnhMake"/></legend>
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<logic:notPresent name="list">
		 <tr>	   
            <td width="13%"><bean:message key="lbl.dealNo"></bean:message><font color="red">*</font> </td>
		     <td width="13%">
		<html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
             <html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(288,'LimitMakerForm','dealNo','','', '','','genrateValuesForLimitEnhance','customerName')" value=" " styleClass="lovbutton"></html:button>
             <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
           
		  </td>
		  
	     <td width="13%"><bean:message key="lbl.loanNumber"></bean:message></td>
		     <td width="13%">
		     <html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
           	<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(289,'LimitMakerForm','loanNo','dealNo','lbxLoanNo','lbxDealNo', 'Select Deal Number','genrateValuesForLimitEnhance','customerName')" value=" " styleClass="lovbutton"></html:button>
             <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="" />
		  </td>
		</tr>
		<tr>
			<td><bean:message key="lbl.custName" /></td>
	  		<td><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="50" onchange="upperMe('customerName');"/>  
			</td> 
		</tr>
		 <tr>
		 	 <td><bean:message key="lbl.oldSenAmount" /></td>
			 <td><html:text styleClass="text" property="oldSenAmount" styleId="oldSenAmount" readonly="true" tabindex="-1" maxlength="18" style="text-align: right" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
			 
			 <td><bean:message key="lbl.oldSenDate" /></td>
			 <td><html:text styleClass="text" property="oldSenDate" styleId="oldSenDate" readonly="true" value=" " tabindex="-1" ></html:text></td>
		 </tr> 
		 <tr>
			 <td><bean:message key="lbl.utilAmount"/></td>
			 <td><html:text styleClass="text" property="utilAmount" styleId="utilAmount" readonly="true" maxlength="18" tabindex="-1" style="text-align: right" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 	 <td><bean:message key="lbl.oldSenValidTill" /></td>
			 <td><html:text styleClass="text" property="oldSenValidTill" styleId="oldSenValidTill" readonly="true" value=" " tabindex="-1" ></html:text></td>
		 </tr>
		
		 <tr>
		  	<td><bean:message key="lbl.newSenDate" /></td>
		 	<td ><html:text property="newSenDate" styleClass="text" styleId="newSenDate" value="${userobject.businessdate}" readonly="true" tabindex="-1"></html:text></td>
		  	
		  	<td><bean:message key="lbl.addSenAmount"/></td>
			 <td><html:text styleClass="text" property="addSenAmount" styleId="addSenAmount"  maxlength="18" style="text-align: right" onkeypress="return numbersonly(event, id, 18)" value="" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 </tr>
		 
		 <tr>
		 	<td><bean:message key="lbl.newSenValidTill"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
		 	<td><html:text styleClass="text" property="applicationdate" styleId="applicationdate" maxlength="10" value="" onchange="return checkDate('applicationdate');"></html:text></td>
		 	<td><bean:message key="lbl.oldLoanAmount"/></td>
		 	<td><html:text styleClass="text" property="oldLoanAmount" styleId="oldLoanAmount" readonly="true"  maxlength="18" style="text-align: right" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 </tr>
		 <tr>
			<td><bean:message key="lbl.loanAmountType" /><span><font color="red">*</font></span></td>
			<td>
				<html:select property="loanAmountType" styleId="loanAmountType" styleClass="text" onclick="checkLoanType();" value="${list[0].loanAmountType}" >
					<html:option value="">---Select---</html:option>
					<html:option value="I">INCREMENT </html:option>
					<html:option value="D">DECREMENT</html:option>
				</html:select> 
			</td>
			<td><bean:message key="lbl.totalDisbursedAmount"/></td>
		 	<td><html:text styleClass="text" property="totalDisbursedAmount" styleId="totalDisbursedAmount"  readonly="true" tabindex="-1" maxlength="18" style="text-align: right" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
			  
		</tr>
		 <tr>
		 	<td><bean:message key="lbl.newLoanAmount"/></td>
		 	<td><html:text styleClass="text" property="newLoanAmount" styleId="newLoanAmount"  onclick="checkLoanType();" tabindex="-1" maxlength="18" style="text-align: right" value="0.0" readonly="true" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 	<td><bean:message key="lbl.makerRemarks"/><font color="red">*</font></td>
			<td><html:textarea property="makeRemarks" styleId="makeRemarks" styleClass="text" value=""></html:textarea></td>
			
		</tr>
	    <tr>
		  <td colspan="3">
			  <button type="button" class="topformbutton2" id="saveButton" onclick="return saveNewLimit();" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	    	  <button type="button" class="topformbutton2" id="frowardButton" onclick="return saveForward();" title="Alt+F" accesskey="F"> <bean:message key="button.forward" /></button>
	    	  <button type="button" class="topformbutton2" id="dealButton" onclick="return searchDealInLimit();" title="Alt+D" accesskey="D"> <bean:message key="button.dealViewer" /></button>
	    	 <button type="button" name="facilityDetails" id="facilityDetails" title="Alt+F" accesskey="S" class="topformbutton4" onclick="openFacilityDetailsLimit();"><bean:message key="button.facilityDetails"/></button>	    	 
	    	
	      </td>
		  </tr>
		  </logic:notPresent>

	<logic:present name="list">
		
		 <tr>	   
            <td width="13%"><bean:message key="lbl.dealNo"></bean:message><font color="red">*</font> </td>
		     <td width="13%">
		     <html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="20"  value="${list[0].dealNo}" readonly="true" tabindex="-1"/>
<!--             <html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(288,'LimitMakerForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>-->
             <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${list[0].lbxDealNo}" />
<!--             <html:hidden property="customerName" styleId="customerName"/>-->
		  </td>
		  <td width="13%"><bean:message key="lbl.loanNumber"></bean:message> </td>
		     <td width="13%">
		    <html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${list[0].loanNo}" readonly="true" tabindex="-1"/>
	     	<html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="${list[0].lbxLoanNo}" />
	     	   

		  </td>
		</tr>
	<tr>
		<td><bean:message key="lbl.custName"/></td>
	  	<td><html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" maxlength="50" value="${list[0].customerName}" />  
		</td> 
	</tr>
		
		 <tr>
		 	 <td><bean:message key="lbl.oldSenAmount" /></td>
			 <td><html:text styleClass="text" property="oldSenAmount" styleId="oldSenAmount" readonly="true" tabindex="-1" maxlength="18" style="text-align: right" value="${list[0].oldSenAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
			 
			 <td><bean:message key="lbl.oldSenDate" /></td>
			 <td><html:text styleClass="text" property="oldSenDate" styleId="oldSenDate" readonly="true" value="${list[0].oldSenDate}" tabindex="-1" ></html:text></td>
		 </tr> 
		 <tr>
	<td><bean:message key="lbl.utilAmount"/></td>
			 <td><html:text styleClass="text" property="utilAmount" styleId="utilAmount" readonly="true" maxlength="18" tabindex="-1" style="text-align: right" value="${list[0].utilAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
	<td><bean:message key="lbl.oldSenValidTill" /></td>
			 <td><html:text styleClass="text" property="oldSenValidTill" styleId="oldSenValidTill" readonly="true" value="${list[0].oldSenValidTill}" tabindex="-1" ></html:text></td>
		
		 </tr>
		
		 <tr>
		  	<td><bean:message key="lbl.newSenDate" /></td>
		 	<td ><html:text property="newSenDate" styleClass="text" styleId="newSenDate" value="${list[0].newSenDate}" readonly="true" tabindex="-1"></html:text></td>
		  	
		  	<td><bean:message key="lbl.addSenAmount"/><font color="red">*</font></td>
			 <td><html:text styleClass="text" property="addSenAmount" styleId="addSenAmount"  maxlength="18" style="text-align: right" onkeypress="return numbersonly(event, id, 18)" value="${list[0].addSenAmount}"  onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 </tr>
		 <tr>
		 	<td><bean:message key="lbl.newSenValidTill"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		 	<td><html:text styleClass="text" property="applicationdate" styleId="applicationdate" maxlength="10" value="${list[0].applicationdate}"  onchange="return checkDate('applicationdate');"></html:text></td>
		 <td><bean:message key="lbl.oldLoanAmount"/></td>
		 	<td><html:text styleClass="text" property="oldLoanAmount" styleId="oldLoanAmount"  readonly="true" maxlength="18" style="text-align: right" value="${disList[0].oldLoanAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
		 </tr>
		 <tr>
			<td><bean:message key="lbl.loanAmountType" /><span><font color="red">*</font></span></td>
			<td>
				<html:select property="loanAmountType" styleId="loanAmountType"  styleClass="text" onclick="checkLoanType();" value="${list[0].loanAmountType}" >
					<html:option value="">---Select---</html:option>
					<html:option value="I">INCREMENT </html:option>
					<html:option value="D">DECREMENT</html:option>
				</html:select> 
			</td>
			<td><bean:message key="lbl.totalDisbursedAmount"/></td>
		 	<td><html:text styleClass="text" property="totalDisbursedAmount" styleId="totalDisbursedAmount"  readonly="true" tabindex="-1" maxlength="18" style="text-align: right" value="${disList[0].totalDisbursedAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text></td>
			  
		</tr>
		 <tr>
		 <td><bean:message key="lbl.newLoanAmount"/></td>
		 <td>
		    <logic:present name="disList">
		      <logic:iterate name="disList" id="obDisList" length="1">
		        <logic:equal name="obDisList" property="loanInsType" value="I">
		          <html:text styleClass="text" property="newLoanAmount" styleId="newLoanAmount"  tabindex="-1" maxlength="18" style="text-align: right" value="${list[0].newLoanAmount}" readonly="true" onclick="checkLoanType();" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text>
		         </logic:equal>
		          <logic:equal name="obDisList" property="loanInsType" value="N">
		          <html:text styleClass="text" property="newLoanAmount" styleId="newLoanAmount"  onclick="checkLoanType();" maxlength="18" style="text-align: right" value="${list[0].newLoanAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"></html:text>
		         </logic:equal>
		      </logic:iterate>
		    </logic:present>
		 </td>
		 
	</tr>
		<tr>
		 	<td><bean:message key="lbl.makerRemarks"/><font color="red">*</font></td>
			<td><html:textarea property="makeRemarks" styleId="makeRemarks" styleClass="text" value="${list[0].makeRemarks}"></html:textarea></td>
			<td><bean:message key="lbl.authorRemarks"/></td>
			<td><html:textarea property="authorRemarks" styleId="authorRemarks" disabled="true" value="${list[0].authorRemarks}" tabindex="-1" styleClass="text"></html:textarea></td>
		 </tr>
	    <tr>
		  <td colspan="3">
	    	  <button type="button" class="topformbutton2" id="saveButton" onclick="return savemodfyLimit();" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	    	  <button type="button" class="topformbutton2" id="frowardButton" onclick="return modfySaveLimit();" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	    	  <button type="button" class="topformbutton2" id="dealButton" onclick="return searchDealInLimit();" title="Alt+D" accesskey="D"><bean:message key="button.dealViewer" /></button>
	    	 <button type="button" name="facilityDetails" id="facilityDetails" title="Alt+F" accesskey="S" class="topformbutton4" onclick="openFacilityDetailsLimit();"><bean:message key="button.facilityDetails"/></button>	    	 
	      </td>
		  </tr>
		  </logic:present>
		</table>	
  	 </td>	
   </tr>
	</table>
	</fieldset>	
	
</html:form>
	<logic:present name="sms">

		<script type="text/javascript">
			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" />");
				document.getElementById("LimitMakerForm").action="<%=request.getContextPath()%>/newLimitEnhancement.do?method=openModifyLimit&loanId="+'${requestScope.loanID}'+"&deaId="+'${requestScope.dealID}';
	    		document.getElementById("LimitMakerForm").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.canForward" />");
				location.href="<%=request.getContextPath()%>/limitEnhansmentBehind.do?method=limitMakerSearch";
	    	
			}
				
				else if('<%=request.getAttribute("sms").toString()%>'=="E")
					{
						alert("<bean:message key="msg.notepadError" />");
					}
				else if('<%=request.getAttribute("sms").toString()%>'=="F")
				{
					alert("New Sanction Amount should be equal to Facility detail's total Amount.");
					document.getElementById("LimitMakerForm").action="<%=request.getContextPath()%>/newLimitEnhancement.do?method=openModifyLimit&loanId="+'${requestScope.loanID}'+"&deaId="+'${requestScope.dealID}';
		    		document.getElementById("LimitMakerForm").submit();
				}
				else if('<%=request.getAttribute("sms").toString()%>'=='Z')
				{
					alert("First save all details of Facility Details Tab!!!");
					document.getElementById("LimitMakerForm").action="<%=request.getContextPath()%>/newLimitEnhancement.do?method=openModifyLimit&loanId="+'${requestScope.loanID}'+"&deaId="+'${requestScope.dealID}';
		    		document.getElementById("LimitMakerForm").submit();
					
				}
				else
					{
						alert("<bean:message key="lbl.dataExist" />");
					}
	</script>
	</logic:present>

</body>
</html>
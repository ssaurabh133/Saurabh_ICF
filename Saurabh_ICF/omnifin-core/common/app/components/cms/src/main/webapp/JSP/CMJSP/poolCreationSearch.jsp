<!-- 
Author Name :- Manas SInha
Date of Creation :18-07-2011
Purpose :-  screen for the Pool Creation -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

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
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/poolCreation.js"></script>
	 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
     <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>


</head>
<body onload="enableAnchor();document.getElementById('poolCreationSearchForm').loanAccountNumber.focus();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
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

 <html:form action="/poolCreationProcessAction" method="post" styleId="poolCreationSearchForm">
  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath" />
			  <input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
			  <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
			
    <fieldset>	 
	<legend><bean:message key="lbl.poolDownload"></bean:message></legend>  
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
		<tr>
			<td>


			<table width="100%" border="0" cellspacing="1" cellpadding="4">
			 <tr>
			<td><bean:message key="lbl.loanAccountNumber"></bean:message></td>
			<td>
		 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20" />
            </td>
			 <td><bean:message key="lbl.customerName"></bean:message></td>
	          <td><html:text property="customerName" styleClass="text" styleId="customerName" value="" maxlength="100" ></html:text>
	         </td>				
		  </tr>
		    <tr>
		    
		    <td><bean:message key="customerCategory" /></td>
			<td><html:select property="customerCategory" styleId="customerCategory" value="" styleClass="text">
				<option value="">-- Select --</option>
				<logic:present name="customerCategory">
				<logic:notEmpty name="customerCategory">
					<html:optionsCollection name="customerCategory" label="customerCategoryDesc" value="customerCategoryCode" />
				</logic:notEmpty>
				</logic:present>
				</html:select>
			</td>
			<td><bean:message key="businessSegment" /></td>
		  	<td><html:select property="customerSegment" styleId="customerSegment"  value="${detailList[0].businessSegment}"	styleClass="text">
		  		<html:option value="">--Select--</html:option>
				<logic:present name="businessSegmentList">
				<logic:notEmpty name="businessSegmentList">
				<html:optionsCollection name="businessSegmentList" label="businessSegmentDesc" value="businessSegmentCode" />
				</logic:notEmpty>
				</logic:present>
				</html:select>
			</td>
			</tr>
		  
		  
		      <tr>
		      <td><bean:message key="lbl.constitution"></bean:message></td>
		      <td> 	 <% session.setAttribute("poolRecStatus","A"); %>     
		      <select id="constitution" name="constitution" size="5" multiple="multiple"  style="width: 150px">
              </select>
              <html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(196,'poolCreationSearchForm','constitution','','', '','','','lbxValue');" value=" " styleClass="lovbutton"></html:button>
              <!-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(120,'poolCreationSearchForm','constitution','','', '','','');" > -->
		      <html:hidden property="lbxValue" styleId="lbxValue"/>
			  </td>
			  
			 <td><bean:message key="lbl.assetCategory"></bean:message></td>
			 <td> 
			 <select id="assetCategory" name="assetCategory" size="5" multiple="multiple"  style="width: 150px">
             </select>
             <html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(107,'poolCreationSearchForm','assetCategory','','', '','','','lbxAssetCollId');" value=" " styleClass="lovbutton"></html:button>
		     <html:hidden property="lbxAssetCollId" styleId="lbxAssetCollId"/>
			 </td>
			 </tr>
				
				<tr>
			<td><bean:message key="lbl.industry"></bean:message></td>
			<td>
			<select id="industry" name="industry" size="5" multiple="multiple" style="width: 150px">
             </select>
             <html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(10,'poolCreationSearchForm','industry','','subIndustry', 'lbxSubIndustry','','','lbxIndustry');" value=" " styleClass="lovbutton"></html:button>
			 <html:hidden property="lbxIndustry" styleId="lbxIndustry"/>
			 </td>
		
			 <td><bean:message key="lbl.subIndustry"></bean:message></td>
	          <td>
	          <select id="subIndustry" name="subIndustry" size="5" multiple="multiple" style="width: 150px">
             </select>
             <html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(121,'poolCreationSearchForm','subIndustry','lbxIndustry','lbxSubIndustry', 'lbxIndustry','Select Industry LOV','','lbxSubIndustry');" value=" " styleClass="lovbutton"></html:button>
             <!-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(121,'poolCreationSearchForm','subIndustry','','', '','','');" > -->
			<html:hidden property="lbxSubIndustry" styleId="lbxSubIndustry"/>
			 </td>
				
		     </tr>
		     
		      <tr>
			  <td><bean:message key="lbl.product"></bean:message></td>
			 <td>
			 <select id="product" name="product" size="5" multiple="multiple"  style="width: 150px">
             </select>
             	<html:hidden property="lbxProductID" styleId="lbxProductID"/>
             <html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(17,'poolCreationSearchForm','product','','scheme','lbxScheme','','','lbxProductID');" value=" " styleClass="lovbutton"></html:button>
             <!-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(17,'poolCreationSearchForm','product','','', '','','');" > -->
			 </td>
			 
			 
			 <td><bean:message key="lbl.scheme"></bean:message></td>
			<td>
			<select id="scheme" name="scheme" size="5" multiple="multiple"  style="width: 150px">
             </select>                                                                                             
             <html:button property="schmButton" styleId="schmButton" onclick="return openMultiSelectLOVCommon(25,'poolCreationSearchForm','scheme','lbxProductID','lbxScheme', 'lbxProductID','Select Product LOV','','lbxScheme');" value=" " styleClass="lovbutton"></html:button>
             <!-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(25,'poolCreationSearchForm','scheme','','', '','','');" > -->
			<html:hidden property="lbxScheme" styleId="lbxScheme"/>
			 </td>
			
				</tr>	
				<tr>			
			 <td><bean:message key="lbl.disbursalDate1"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	          <td><html:text property="disbursalDate1" styleClass="text" styleId="disbursalDate1" value=""  onchange="checkDate('disbursalDate1')" ></html:text>
	         </td>
	         
	         <td><bean:message key="lbl.disbursalDate2"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	          <td><html:text property="disbursalDate2" styleClass="text" styleId="disbursalDate2" value="" onchange="checkDate('disbursalDate2')" ></html:text>
	         </td>
	         
	         </tr>
		    <tr>
		      
	          <td><bean:message key="lbl.maturityDate1"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
			 <td><div style="float:left;">
			 <html:text property="maturityDate1" styleClass="text" styleId="maturityDate1" value="" onchange="checkDate('maturityDate1')"></html:text>
			 </div></td>
	         
	         
	         <td><bean:message key="lbl.maturityDate2"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
			 <td><div style="float:left;">
			 <html:text property="maturityDate2" styleClass="text" styleId="maturityDate2" value=""  onchange="checkDate('maturityDate2')" ></html:text>
			 </div></td>
			  				
		  </tr>
		  
		      <tr>
			 <td><bean:message key="lbl.loanAmount1"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="loanAmount1" styleClass="text" styleId="loanAmount1" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right"></html:text>
			 </div></td>
			  <td><bean:message key="lbl.loanAmount2"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="loanAmount2" styleClass="text" styleId="loanAmount2" value=""  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right"></html:text>
			 </div></td>
			 
			 </tr>
			 <tr>
			 
			   <td><bean:message key="lbl.installmentAmount1"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="installmentAmount1" styleClass="text" styleId="installmentAmount1" value=""  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right"></html:text>
			 </div></td>

			  <td><bean:message key="lbl.installmentAmount2"></bean:message></td>
			 <td width="35%"><div style="float:left;">
			 <html:text property="installmentAmount2" styleClass="text" styleId="installmentAmount2" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right" ></html:text>
			 </div></td>
				</tr>
				
				<tr>
			<td><bean:message key="lbl.interestRate1"></bean:message></td>
			<td>
		   <html:text styleClass="text" property="interestRate1" styleId="interestRate1" onkeypress="return fourDecimalnumbersonly(event, id, 3);" onblur="fourDecimalNumber(this.value, id);checkRate('interestRate1');"  onchange="fourDecimalcheckNumber(this.value, event, 3, id);checkRate('interestRate1');" onfocus="fourDecimalkeyUpNumber(this.value, event, 3, id);" style="text-align: right"  value="" />
   		   </td>
   		   
   		   <td><bean:message key="lbl.interestRate2"></bean:message></td>
			<td>
		   <html:text styleClass="text" property="interestRate2" styleId="interestRate2" onkeypress="return fourDecimalnumbersonly(event, id, 3);" onblur="fourDecimalNumber(this.value, id);checkRate('interestRate2');"  onchange="fourDecimalcheckNumber(this.value, event, 3, id);checkRate('interestRate2');" onfocus="fourDecimalkeyUpNumber(this.value, event, 3, id);" style="text-align: right" value="" />
   		   </td>
   		   </tr>
   		    
		      <tr>	
		      
			 <td><bean:message key="lbl.amountOutstanding1"></bean:message></td>
			<td>
		   <html:text styleClass="text" property="amountOutstanding1" styleId="amountOutstanding1" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"  value="" style="text-align: right"/>
   		   </td>
   		    <td><bean:message key="lbl.amountOutstanding2"></bean:message></td>
			<td>
		   <html:text styleClass="text" property="amountOutstanding2" styleId="amountOutstanding2" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" value="" style="text-align: right" />
   		   </td>
   		   
				</tr>
				<tr>			
			 <td><bean:message key="lbl.totalInstallments1"></bean:message></td>
	          <td><html:text property="totalInstallments1" styleClass="text" styleId="totalInstallments1" value="" maxlength="3" onkeypress="return isNumberKey(event);" style="text-align: right"></html:text>
	         </td>
	         <td><bean:message key="lbl.totalInstallments2"></bean:message></td>
	          <td><html:text property="totalInstallments2" styleClass="text" styleId="totalInstallments2" value="" maxlength="3" onkeypress="return isNumberKey(event);" style="text-align: right"></html:text>
	         </td>
	         
	         </tr>
	         <tr>
	         
	          <td><bean:message key="lbl.installmentReceived1"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="installmentReceived1" styleClass="text" styleId="installmentReceived1" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right"></html:text>
			 </div></td>	
			 
			 <td><bean:message key="lbl.installmentReceived2"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="installmentReceived2" styleClass="text" styleId="installmentReceived2" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right"></html:text>
			 </div></td>
			 		
		  </tr>
		  
		      <tr>			   
			 <td><bean:message key="lbl.overDueAmount1"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="overDueAmount1" styleClass="text" styleId="overDueAmount1" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right"></html:text>
			 </div></td>
			 
			  <td><bean:message key="lbl.overDueAmount2"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="overDueAmount2" styleClass="text" styleId="overDueAmount2" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);" style="text-align: right"></html:text>
			 </div></td>
			 
			 </tr>
			 <tr>
			 
			  <td><bean:message key="lbl.DPD1"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="DPD1" styleClass="text" styleId="DPD1" value="" maxlength="3" onkeypress="return isNumberKey(event);" ></html:text>
			 </div></td>
			 
			 <td><bean:message key="lbl.DPD2"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="DPD2" styleClass="text" styleId="DPD2" value="" maxlength="3" onkeypress="return isNumberKey(event);"></html:text>
			 </div></td>
				</tr>	
				
				<tr>
			 <td><bean:message key="lbl.rateType"></bean:message></td>
			 <td>
			 	<html:select property="rateType" styleId="rateType" styleClass="text">
			 	<html:option value="">---Select---</html:option>	
			  	<html:option value="F">FLAT</html:option>		
			  	<html:option value="E">EFFECTIVE</html:option>
			  	</html:select>	
			  </td>	
			  <td><bean:message key="lbl.legacy"></bean:message></td>
			 <td><html:text property="legacyLoan" styleClass="text" styleId="legacyLoan" value="" maxlength="15" /></td>										
			</tr>
			 <tr>
			 	<td><bean:message key="lbl.branchname"/></td>
				<td>
					<html:text property="branchid" styleClass="text" styleId="branchid" maxlength="100" readonly="true"/>
					<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="" />
					<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(12,'reportid','branchid','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"  >
					</html:button>
				</td>
				<td><bean:message key="lbl.cutOffDate"/></td>
				<td><html:text property="cutOffDate" styleClass="text" styleId="cutOffDate" maxlength="10" value="" onchange="checkDate('cutOffDate')" /></td>		
			 </tr>	
				 <tr>
		   <td align="left" >&nbsp;</td>
		   <td align="left" >&nbsp;</td>
            <td align="left" >&nbsp;</td>
            <td><input type="hidden" id="cutOffDateList" value="${cutOffDate}"/>
	     <button type="button" class="topformbutton3" id="save"  title="Alt+G" accesskey="G" onclick="return generate();" ><bean:message key="button.generate" /></button>
		</td>
		  </tr>	
	</table>

      </td>
			</tr>

	 
     </table>
	</fieldset>
     
</html:form>
 <logic:present name="datalist">
		<script type="text/javascript">
			alert("<bean:message key="lbl.noDataFound" />");	
		
		</script>
		</logic:present>	
  
</div>

</div>
</body>
</html:html>
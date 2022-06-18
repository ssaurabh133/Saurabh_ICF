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
        <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/fundFlowAnalysis.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  		
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
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
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('salesAnalysisForm');document.getElementById('salesAnalysisForm').salesMonthAndYear.focus();init_fields();" onclick="parent_disable();">
	
		<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		</div>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
		<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Fund Flow Analysis</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>

<logic:notPresent name="underWriterViewData">
	<html:form action="/salesAnalysisBehindAction" styleId="salesAnalysisForm">
	<input type="hidden" id="contextPath" value="<%=path %>" />
	
	   <fieldset>
	   
	   <legend><bean:message key="lbl.salesAnalysis"/></legend>      
	 <logic:notPresent name="fundFlowAuthor">   

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<logic:notPresent name="saveYear">
		
		 <tr>
		   <td width="20%"><bean:message key="lbl.SalesYear"/><font color="red">*</font> </td>
		   <td nowrap="nowrap" align="left">
		   <html:select property="year" styleId="year" styleClass="text"  style="width:90px !important; min-width:85px !important;" value="${requestScope.yearVal}" onchange="searchSalesAnalysis(this.value);">
		         <html:option value="">--Select--</html:option>
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
		   </td>
		     
		     </tr><div> 
		 
		     </div></logic:notPresent>
		     <logic:present name="saveYear"><div> 
		
		     </div><tr>
		   <td width="20%" align="left"><bean:message key="lbl.SalesYear"/><font color="red">*</font> </td>
		   <td nowrap="nowrap" align="left"><html:select property="year" styleId="year" styleClass="text"  style="width:90px !important; min-width:85px !important;" onchange="searchSalesAnalysis(this.value);">
	         <html:option value="">--Select--</html:option>
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
		   </td>
		      
		   
		     </tr>
		     </logic:present>
		  
		 
		</table>
		
	      </td>
	</tr>
	
	</table>

	
	
	

	
	</logic:notPresent>
	
	
	
	<logic:present name="fundFlowAuthor">

			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		 <tr>
		   <td width="20%"><bean:message key="lbl.SalesYear"/></td>
		   <td nowrap="nowrap" ><html:select property="year" styleId="year" styleClass="text"  style="width:90px !important; min-width:85px !important;"  onchange="searchSalesAnalysis(this.value);">
		         <html:option value="">--Select--</html:option>
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
		   </td>
		  
		     </tr>
		  
		</table>
		
	      </td>
	</tr>
	
	</table>
	</logic:present>
	</fieldset>	
	
	 <br/>
	<logic:present name="fundFlowDealId">
	
	<fieldset>	
  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
	
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	
    	<logic:present name="fundFlowAuthor">
    
    		<%--<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="contact.select" /></b>
    	--%></logic:present>
    	<logic:notPresent name="fundFlowAuthor"><%--
    
    	<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
    	--%></logic:notPresent>
	    <td ><b><bean:message key="lbl.SalesMonth"/> </b></td>
        <td ><strong><bean:message key="lbl.netsales"/></strong></td>
		<td><strong><bean:message key="lbl.averagemonthsales"/></strong></td>
		<td ><strong><bean:message key="lbl.perincreaseinsales"/></strong></td>
		</tr>
		<logic:notPresent name="salesAnalysisList">
		<tr class="white1">
				<input type="hidden" name="chk1" id="chk1" value="${subSalesAnalysisList.salesId }"/>
				<td >January</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales1"  value="0" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" onchange="calculateInterest();"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales1"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest1"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk2" id="chk2" value="${subSalesAnalysisList.salesId }"/>
				<td >February</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales2" value="0" maxlength="22" style="text-align: right;" onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales2"  maxlength="22" style="text-align: right;" readonly="readonly"  />
				</td>
				<td><input type="text" class="text" name="interest" id="interest2"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk3" id="chk3" value="${subSalesAnalysisList.salesId }"/>
				<td >March</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales3" value="0" maxlength="22" style="text-align: right;" onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales3"  maxlength="22" style="text-align: right;" readonly="readonly"  />
				</td>
				<td><input type="text" class="text" name="interest" id="interest3"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk4" id="chk4" value="${subSalesAnalysisList.salesId }"/>
				<td >April</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales4" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales4"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest4"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		
		<tr class="white1">
				<input type="hidden" name="chk5" id="chk5" value="${subSalesAnalysisList.salesId }"/>
				<td >May</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales5" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales5"  maxlength="22" style="text-align: right;" readonly="readonly" /></td>
				<td><input type="text" class="text" name="interest" id="interest5"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk6" id="chk6" value="${subSalesAnalysisList.salesId }"/>
				<td >June</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales6" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales6"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest6"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk7" id="chk7" value="${subSalesAnalysisList.salesId }"/>
				<td >July</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales7" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales7"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest7"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr><tr class="white1">
				<input type="hidden" name="chk8" id="chk8" value="${subSalesAnalysisList.salesId }"/>
				<td >August</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales8" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales8"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
			
				<td><input type="text" class="text" name="interest" id="interest8"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk9" id="chk9" value="${subSalesAnalysisList.salesId }"/>
				<td >September</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales9" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales9"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest9"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk10" id="chk10" value="${subSalesAnalysisList.salesId }"/>
				<td >October</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales10" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales10"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest10"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		
		<tr class="white1">
				<input type="hidden" name="chk11" id="chk11" value="${subSalesAnalysisList.salesId }"/>
				<td >November</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales11" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales11"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest11"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>	
		
		<tr class="white1">
				<input type="hidden" name="chk12" id="chk12" value="${subSalesAnalysisList.salesId }"/>
				<td >December</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales12" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales12"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest12"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		 
		</logic:notPresent>
		<logic:present name="salesAnalysisList">
			
			<logic:iterate id="subSalesAnalysisList" name="salesAnalysisList" indexId="count">
				<tr class="white1">
					
					
					<logic:present name="fundFlowAuthor">
    					<input type="hidden" name="chk" id="chk<%=count.intValue()+1 %>" value="${subSalesAnalysisList.salesId }" disabled="disabled"/>
    				</logic:present>
    	 			<logic:notPresent name="fundFlowAuthor">
    					<input type="hidden" name="chk" id="chk<%=count.intValue()+1 %>" value="${subSalesAnalysisList.salesId }"/>
    	 			</logic:notPresent>
					
					<td >${subSalesAnalysisList.month }</td>
					<td ><input type="text"  class="text" name="netsales" id="netsales<%=count.intValue()+1 %>" value="${subSalesAnalysisList.netSales}" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" onchange="calculateInterest();"/></td>
					<td><input type="text" class="text" name="avgsales" id="avgsales<%=count.intValue()+1 %>" value="${subSalesAnalysisList.averageSale}" maxlength="22" style="text-align: right;" readonly="readonly" /></td>
					<td><input type="text" class="text" name="interest" id="interest<%=count.intValue()+1 %>" value="${subSalesAnalysisList.increaseInSale}" maxlength="22" style="text-align: right;"  readonly="readonly" /></td>
			
				</tr>	
			</logic:iterate>
			<input type="hidden" id="salesId" value="${subSalesAnalysisList.salesId }"/>
		</logic:present>	
 </table>
    
    </td>
</tr>
</table>
	<logic:notPresent name="fundFlowAuthor">
		<!--<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteSalesAnalysis();"><bean:message key="button.delete" /></button>
 	 
		--><button type="button" name="Save" class="topformbutton2" id="Save" onclick="return saveSalesAnalysis();" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
		 <button type="button"  class="topformbutton2" onclick="salesAnalysisClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
		
 	</logic:notPresent>
	</fieldset>
	</logic:present>
</html:form>
</logic:notPresent>

<%--   -------------- -------------------- ---------View mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="underWriterViewData">

		<html:form action="/salesAnalysisBehindAction" styleId="salesAnalysisForm">
	<input type="hidden" id="contextPath" value="<%=path %>" />
	
	   <fieldset>
	   
	   <legend><bean:message key="lbl.salesAnalysis"/></legend>      
	 <logic:notPresent name="fundFlowAuthor">   

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<logic:notPresent name="saveYear">
		
		 <tr>
		   <td width="20%"><bean:message key="lbl.SalesYear"/><font color="red">*</font> </td>
		   <td nowrap="nowrap" align="left">
		   <html:select property="year" styleId="year" styleClass="text"  style="width:90px !important; min-width:85px !important;" value="${requestScope.yearVal}" onchange="searchSalesAnalysis(this.value);" >
		         <html:option value="">--Select--</html:option>
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
		   </td>
		     
		     </tr><div> 
		 
		     </div></logic:notPresent>
		     <logic:present name="saveYear"><div> 
		
		     </div><tr>
		   <td width="20%" align="left"><bean:message key="lbl.SalesYear"/><font color="red">*</font> </td>
		   <td nowrap="nowrap" align="left"><html:select property="year" styleId="year" styleClass="text"  style="width:90px !important; min-width:85px !important;" onchange="searchSalesAnalysis(this.value);" >
	         <html:option value="">--Select--</html:option>
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
		   </td>
		      
		   
		     </tr>
		     </logic:present>
		  
		 
		</table>
		
	      </td>
	</tr>
	
	</table>

	
	
	

	
	</logic:notPresent>
	
	
	
	<logic:present name="fundFlowAuthor">

			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		 <tr>
		   <td width="20%"><bean:message key="lbl.SalesYear"/></td>
		   <td nowrap="nowrap" ><html:select property="year" styleId="year" styleClass="text"  style="width:90px !important; min-width:85px !important;"  onchange="searchSalesAnalysis(this.value);">
		         <html:option value="">--Select--</html:option>
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
		   </td>
		  
		     </tr>
		  
		</table>
		
	      </td>
	</tr>
	
	</table>
	</logic:present>
	</fieldset>	
	
	 <br/>
	<logic:present name="fundFlowDealId">
	
	<fieldset>	
  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
	
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	
    	<logic:present name="fundFlowAuthor">
    
    		<%--<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="contact.select" /></b>
    	--%></logic:present>
    	<logic:notPresent name="fundFlowAuthor"><%--
    
    	<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
    	--%></logic:notPresent>
	    <td ><b><bean:message key="lbl.SalesMonth"/> </b></td>
        <td ><strong><bean:message key="lbl.netsales"/></strong></td>
		<td><strong><bean:message key="lbl.averagemonthsales"/></strong></td>
		<td ><strong><bean:message key="lbl.perincreaseinsales"/></strong></td>
		</tr>
		<logic:notPresent name="salesAnalysisList">
		<tr class="white1">
				<input type="hidden" name="chk1" id="chk1" value="${subSalesAnalysisList.salesId }"/>
				<td >January</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales1" readonly="true"  value="0" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" onchange="calculateInterest();"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales1" readonly="readonly"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest1" readonly="readonly"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk2" id="chk2" value="${subSalesAnalysisList.salesId }"/>
				<td >February</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales2" readonly="true" value="0" maxlength="22" style="text-align: right;" onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales2" readonly="readonly"  maxlength="22" style="text-align: right;" readonly="readonly"  />
				</td>
				<td><input type="text" class="text" name="interest" id="interest2" readonly="readonly" maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk3" id="chk3" value="${subSalesAnalysisList.salesId }"/>
				<td >March</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales3" readonly="true" value="0" maxlength="22" style="text-align: right;" onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales3" readonly="readonly"  maxlength="22" style="text-align: right;" readonly="readonly"  />
				</td>
				<td><input type="text" class="text" name="interest" id="interest3" readonly="readonly"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk4" id="chk4" value="${subSalesAnalysisList.salesId }"/>
				<td >April</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales4" readonly="true" value="0" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales4" readonly="readonly"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest4" readonly="readonly"  maxlength="22" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		
		<tr class="white1">
				<input type="hidden" name="chk5" id="chk5" value="${subSalesAnalysisList.salesId }"/>
				<td >May</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales5" value="0" readonly="true" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales5"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly" /></td>
				<td><input type="text" class="text" name="interest" id="interest5"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk6" id="chk6" value="${subSalesAnalysisList.salesId }"/>
				<td >June</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales6" value="0" readonly="true" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales6"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest6"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk7" id="chk7" value="${subSalesAnalysisList.salesId }"/>
				<td >July</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales7" value="0" readonly="true" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales7"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest7"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
		</tr><tr class="white1">
				<input type="hidden" name="chk8" id="chk8" value="${subSalesAnalysisList.salesId }"/>
				<td >August</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales8" value="0" readonly="true" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales8"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
			
				<td><input type="text" class="text" name="interest" id="interest8"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk9" id="chk9" value="${subSalesAnalysisList.salesId }"/>
				<td >September</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales9" value="0" readonly="true" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales9"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest9"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		<tr class="white1">
				<input type="hidden" name="chk10" id="chk10" value="${subSalesAnalysisList.salesId }"/>
				<td >October</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales10" value="0" readonly="true" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales10"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest10"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		
		<tr class="white1">
				<input type="hidden" name="chk11" id="chk11" value="${subSalesAnalysisList.salesId }"/>
				<td >November</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales11" value="0" readonly="true" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales11"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest11"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
		</tr>	
		
		<tr class="white1">
				<input type="hidden" name="chk12" id="chk12" value="${subSalesAnalysisList.salesId }"/>
				<td >December</td>
				<td ><html:text  styleClass="text" property="netsales" styleId="netsales12" value="0" readonly="true" maxlength="22" style="text-align: right;"  onchange="calculateInterest();" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<td><input type="text" class="text" name="avgsales" id="avgsales12"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
				<td><input type="text" class="text" name="interest" id="interest12"  maxlength="22" readonly="readonly" style="text-align: right;" readonly="readonly"  /></td>
		</tr>
		 
		</logic:notPresent>
		<logic:present name="salesAnalysisList">
			
			<logic:iterate id="subSalesAnalysisList" name="salesAnalysisList" indexId="count">
				<tr class="white1">
					
					
					<logic:present name="fundFlowAuthor">
    					<input type="hidden" name="chk" id="chk<%=count.intValue()+1 %>" value="${subSalesAnalysisList.salesId }" disabled="disabled"/>
    				</logic:present>
    	 			<logic:notPresent name="fundFlowAuthor">
    					<input type="hidden" name="chk" id="chk<%=count.intValue()+1 %>" value="${subSalesAnalysisList.salesId }" disabled="disabled"/>
    	 			</logic:notPresent>
					
					<td >${subSalesAnalysisList.month }</td>
					<td ><input type="text"  class="text" name="netsales" id="netsales<%=count.intValue()+1 %>" value="${subSalesAnalysisList.netSales}" maxlength="22" readonly="readonly" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" onchange="calculateInterest();"/></td>
					<td><input type="text" class="text" name="avgsales" id="avgsales<%=count.intValue()+1 %>" value="${subSalesAnalysisList.averageSale}" maxlength="22"  style="text-align: right;" readonly="readonly" /></td>
					<td><input type="text" class="text" name="interest" id="interest<%=count.intValue()+1 %>" value="${subSalesAnalysisList.increaseInSale}" maxlength="22" style="text-align: right;"  readonly="readonly" /></td>
			
				</tr>	
			</logic:iterate>
			<input type="hidden" id="salesId" value="${subSalesAnalysisList.salesId }"/>
		</logic:present>	
 </table>
    
    </td>
</tr>
</table>
	
	</fieldset>
	</logic:present>
</html:form>

</logic:present>
</div>

</div>
<logic:present name="procval">
<script type="text/javascript">
  		alert('<%=request.getAttribute("procval").toString()%>');
		
</script>


</logic:present>
<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		document.getElementById("netsales").value="";
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='D')
	{
			document.getElementById("month").focus();
			alert("<bean:message key="lbl.salesMonthAndYearDuplicate" />");
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='C')
	{
		document.getElementById("bankButton").focus();
			alert("<bean:message key="lbl.combinationRepeated" />");
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Del')
	{
		alert("<bean:message key="lbl.dataDeleted" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.dataNtDeleted" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
	}
	
	</script>
</logic:present>
<script>
	setFramevalues("salesAnalysisForm");
</script>

</body>
</html:html>

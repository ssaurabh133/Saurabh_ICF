<!--Author Name : Manish Shukla-->
<!--Date of Creation : Jun-2013-->
<!--Purpose  : Rate Aproval Matrix Master-->
<!--Documentation : -->

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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<logic:present name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
</logic:present>
<logic:notPresent name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>

<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/rateApprovalMaker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
	

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

<body onload="enableAnchor();fntab();">
<html:form styleId="rateApprovalMakerForm"  method="post"  action="/rateMatrixMakerDispatchAction" >
 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" id="businessdate" value="${userobject.businessdate }" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="saveForwardFlag" id="saveForwardFlag"  />
<fieldset>
<legend><bean:message key="lbl.rateApprovalMatrix" /></legend>
  <table width="100%">
  
   <logic:present name="editVal">
  <tr>
		
	<td>
		<bean:message key="lbl.rateApprovalProduct"/><span><font color="red">*</font></span>  </td>
	<td > 
		 <html:text  property="product" styleId="product" styleClass="text"  value="${requestScope.list[0].product}" readonly="true" tabindex="-1"/>
         <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${requestScope.list[0].lbxProductID}" />
      <!--    <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(2017,'rateApprovalMakerForm','product','','','','','deleteScheme','lbxProductID')" value=" " styleClass="lovbutton"> </html:button> -->
	</td>
	<td> <bean:message key="lbl.rateApprovalScheme"/><span><font color="red">*</font></span>  </td>
	<td > 
	      <html:text property="scheme" styleId="scheme" styleClass="text"  value="${requestScope.list[0].scheme}" readonly="true" tabindex="-1"/>
          <html:hidden  property="lbxScheme" styleId="lbxScheme" value="${requestScope.list[0].lbxScheme}" />
    	<!--  <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(2018,'rateApprovalMakerForm','scheme','product','', 'lbxProductID','Please Select Product','','lbxScheme')" value=" " styleClass="lovbutton"></html:button> -->
   </td>
	     
	</tr>
  </logic:present>
  
  <logic:present name="save">
  <tr>
	
	<td>
		<bean:message key="lbl.rateApprovalProduct"/><span><font color="red">*</font></span>  </td>
	<td > 
		 <html:text  property="product" styleId="product" styleClass="text" value="${requestScope.list[0].product}" readonly="true" tabindex="-1"/>
         <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${requestScope.list[0].lbxProductID}" />
         <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(2017,'rateApprovalMakerForm','product','','','','','deleteScheme','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>
	</td>
	<td> <bean:message key="lbl.rateApprovalScheme"/><span><font color="red">*</font></span> </td>
	<td > 
	      <html:text property="scheme" styleId="scheme" styleClass="text" value="${requestScope.list[0].scheme}" readonly="true" tabindex="-1"/>
          <html:hidden  property="lbxScheme" styleId="lbxScheme" value="${requestScope.list[0].lbxScheme}" />
    	  <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(2018,'rateApprovalMakerForm','scheme','product','', 'lbxProductID','Please Select Product','','lbxScheme')" value=" " styleClass="lovbutton"></html:button>
   </td>
	     
	</tr>
  </logic:present>
   <!--  
  	<tr>
  	 	<td><bean:message key="lbl.creditInsuranceCover" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:select property="creditInsuranceCover" styleId="creditInsuranceCover" styleClass="text" value="${requestScope.list[0].creditInsuranceCover}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		</td>  
																								
		<td><bean:message key="lbl.typeOfCoverage" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:select property="typeOfCoverage" styleId="typeOfCoverage" styleClass="text" value="${requestScope.list[0].typeOfCoverage}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="SCF">Single Cover Full</html:option>
			<html:option value="SCP">Single Cover Partial</html:option>
			<html:option value="JCF">Joint Cover Full</html:option>
			<html:option value="JCP">Joint Cover Partial</html:option>
			<html:option value="NA">Not Applicable</html:option>
		</html:select> 
		</td>  
    
    </tr>
    
    <tr>
		<td><bean:message key="lbl.dob" /><span><font color="red">*</font></span></td>
		<td ><html:text property="dob" styleId="dob" value="${requestScope.list[0].dob}" readonly="true" styleClass="text" onchange="checkDate('dob');" /></td>
		
		<td><bean:message key="lbl.sumAssuredToLoan" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="sumAssuredToLoan"  style="text-align: right" styleId="sumAssuredToLoan" maxlength="18" onchange="checkRate();" onkeypress="return numbersonly(event,id,18)" value="${requestScope.list[0].sumAssuredToLoan}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
	   
    </tr>
    
    <tr>
		<td><bean:message key="lbl.csliPremiumAmt" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="csliPremiumAmount"  style="text-align: right" styleId="csliPremiumAmount" maxlength="18" value="${requestScope.list[0].csliPremiumAmount}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>  
		
		<td><bean:message key="lbl.creditInsuranceTenor" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="creditInsuranceTenor" styleClass="text" styleId="creditInsuranceTenor" maxlength="5" onkeypress="return isNumberKey(event);"  value="${requestScope.list[0].creditInsuranceTenor}" />
		</td>  
		   
    </tr>
    
    <tr>
    
		<td><bean:message key="lbl.generalInsurance" /><span><font color="red">*</font></span></td>
		
		 <td>
		<html:select property="generalInsurance" styleId="generalInsurance" styleClass="text" value="${requestScope.list[0].generalInsurance}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		</td>  
           
         <td><bean:message key="lbl.csgiPremiumAmount" /><span><font color="red">*</font></span></td>
		 <td><html:text property="csgiPremiumAmount"  style="text-align: right" styleId="csgiPremiumAmount" maxlength="18" value="${requestScope.list[0].csliPremiumAmount}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
		 
		     
    </tr>
    
    <tr>
		
		<td><bean:message key="lbl.anyOtherInsurance" /><span><font color="red">*</font></span></td>
		<td>
		<html:select property="anyOtherInsurance" styleId="anyOtherInsurance" styleClass="text" onchange="productReadOnly();" value="${requestScope.list[0].anyOtherInsurance}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		
		</td> 
		
		<td><bean:message key="lbl.ifyesOtherAmount" /></td>
		<td><html:text property="ifyesOtherAmount" styleClass="text" styleId="ifyesOtherAmount" maxlength="50" value="${requestScope.list[0].ifyesOtherAmount}" /></td>  
		     
    </tr>
    <tr>
		
		<td><bean:message key="lbl.csoiPremiumAmount" /><span><font color="red">*</font></span></td>
		<td><html:text property="csoiPremiumAmount"  style="text-align: right" styleId="csoiPremiumAmount" maxlength="18" value="${requestScope.list[0].csoiPremiumAmount}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td> 
		
		<td><bean:message key="lbl.transactionFee" /><span><font color="red">*</font></span></td>
		<td><html:text property="transactionFee"  style="text-align: right" styleId="transactionFee" maxlength="18" value="${requestScope.list[0].transactionFee}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>  
		     
    </tr>  
    -->	
      <tr>
		
		<td><bean:message key="lbl.rackRate" /><span><font color="red">*</font></span></td>
		<td><html:text property="rackRate"  style="text-align: right" styleId="rackRate" maxlength="18" onchange="checkRackRate();" value="${requestScope.list[0].rackRate}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td> 
		
		<td><bean:message key="lbl.rackProcessingFee" /><span><font color="red">*</font></span></td>
		<td><html:text property="rackProcessingFee"  style="text-align: right" styleId="rackProcessingFee" maxlength="18" onchange="checkRackProcessRate();" value="${requestScope.list[0].rackProcessingFee}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>  
		     
    </tr>
    
     
       
    <tr>
	
		<td>
    
	    <logic:present name="editVal">
	      <button type="button" name="update" id="update" title="Alt+V" accesskey="V" onclick="return updateRateApproval('P');" class="topformbutton2" ><bean:message key="button.save" /></button>
	      <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return updateRateApproval('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
	   </logic:present>
   
	   <logic:present name="save">
	    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRateApproval('P');" class="topformbutton2" ><bean:message key="button.save" /></button>
	    <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return saveRateApproval('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
	   </logic:present>
	   
	   
	   
    	<br>
   		 </td> 
   	</tr>
    
    </table>
	
</fieldset>
 
</html:form>

		
<logic:present name="sms">
<script type="text/javascript">


if('<%=request.getAttribute("sms").toString()%>'=='S')
{
	alert("<bean:message key="lbl.dataSave" />");
	document.getElementById("rateApprovalMakerForm").action="rateMatrixMakerDispatchAction.do?method=rateApprovalMakerOpenEdit&product="+'${requestScope.product}'+"&scheme="+'${requestScope.scheme}';
    document.getElementById("rateApprovalMakerForm").submit();
	
}
else if('<%=request.getAttribute("sms").toString()%>'=='M')
{
	alert("<bean:message key="lbl.dataModify" />");
	document.getElementById("rateApprovalMakerForm").action="rateMatrixMakerDispatchAction.do?method=rateApprovalMakerOpenEdit&product="+'${requestScope.product}'+"&scheme="+'${requestScope.scheme}';
    document.getElementById("rateApprovalMakerForm").submit();
}
else if('<%=request.getAttribute("sms").toString()%>'=='F')
{
	alert("<bean:message key="msg.ForwardSuccessfully" />");
	document.getElementById("rateApprovalMakerForm").action="rateMatrixMakerDispatchAction.do?method=rateApprovalMakerSearch";
    document.getElementById("rateApprovalMakerForm").submit();
}

else if('<%=request.getAttribute("sms").toString()%>'=='E')
{
	alert("<bean:message key="lbl.dataExist" />");	
}

	
</script>
</logic:present>
 </body>
		</html:html>
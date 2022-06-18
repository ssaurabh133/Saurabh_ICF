<%--
      Author Name-      Prashant Kumar
      Date of creation -20/04/2011
      Purpose-          Entry of Collateral Detail
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

	<head>
 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>

	
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>	

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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('INSForm').policyNo.focus();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<%
	String assetClass=request.getParameter("assetClass");
	String assetCollateralType=request.getParameter("assetCollateralType");
	if(assetClass!=null)
	{
		request.setAttribute("assetClass",assetClass);
	}
	
	if(assetCollateralType!=null)
	{
		request.setAttribute("assetCollateralType",assetCollateralType);
	    request.setAttribute("new","N");
	}
	
 %>

<div id=centercolumn>
<div id=revisedcontainer>	
<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">

<html:form action="/collateralInsuranceProcessAction" styleId="INSForm" method="post">
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdIns" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdIns"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="fd1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="fd2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="fd3"/>
<fieldset>
<legend><bean:message key="lbl.insuranceDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>   
 		<tr>
 		<td><bean:message key="lbl.insuranceAgency"></bean:message> <font color="red">* </font></td>

		   
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="${fetchCollateralDetails[0].insuranceAgency}" readonly="true"/>
           <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency" value="${fetchCollateralDetails[0].lbxInsuranceAgency}"/>
           <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerForm','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>	   
		 </td>
		  <td style="width:23%"><bean:message key="lbl.insuranceDesc"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 		 
 		</tr>

 		<tr>
 		<td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	      <td>
	      <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="${fetchCollateralDetails[0].coverNoteNo}" maxlength="20"  ></html:text>
 		  </td>
 		  <td style="width:23%"><bean:message key="lbl.policyNo"/></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="policyNo" property="policyNo" maxlength="100" value="${fetchCollateralDetails[0].policyNo}" /></td>
 		  
 		  </tr>
 		  <tr>
 		  
		   <td><bean:message key="lbl.policyStartDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="policyStartDate" property="policyStartDate" value="${fetchCollateralDetails[0].policyStartDate}" onchange="checkDate('policyStartDate'); policyFirstDateValid(); "/></td>
		   <td><bean:message key="lbl.maturityDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="insMatureDate" property="insMatureDate" value="${fetchCollateralDetails[0].insMatureDate}" onchange="checkDate('insMatureDate'); insValidMaturity(); "/></td>
		 </tr>
 		 <tr>
		 <td><bean:message key="lbl.sumAssured"/><font color="red">*</font></td>
		 <td nowrap="nowrap"><html:text styleClass="text" styleId="sumAssured" property="sumAssured" style="text-align: right" value="${fetchCollateralDetails[0].sumAssured}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		 <td><bean:message key="lbl.surrenderValue"/></td>
		 <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		 </tr>
 		
 		<tr>
 		<td><bean:message key="lbl.premiumAmount"/></td>
 		  <td noWrap><html:text styleClass="text" styleId="premiumAmount" property="premiumAmount" style="text-align: right" value="${fetchCollateralDetails[0].premiumAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 		  <td style="width:23%"><bean:message key="lbl.premiumFrequency"/></td>
           <td style="width:26%">
           <html:select property="premiumFrequency" styleId="premiumFrequency" styleClass="text" value="${fetchCollateralDetails[0].premiumFrequency}">
             <html:option value=""></html:option>
             <html:option value="MONTHLY">MONTHLY</html:option>
             <html:option value="QUARTERLY">QUARTERLY</html:option>
             <html:option value="HALFYEARLY">HALF YEARLY</html:option>      
             <html:option value="ANNUALY">ANNUALY</html:option>        
           </html:select></td>
 		  
 		  </tr>
 		 	
 		  <tr>
 		  <td style="width:23%"><bean:message key="lbl.nominee"/></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value="${fetchCollateralDetails[0].nominee}"  /></td>
		   <td><bean:message key="lbl.relWithNominee"/></td>
		   <td nowrap="nowrap"><html:select styleClass="text"  styleId="relWithNominee" value="${fetchCollateralDetails[0].relWithNominee}" property="relWithNominee">
                  <html:option value="">Select</html:option>  
       			  <logic:present name="showInsuranceRelWithNominee">
				  <logic:notEmpty name="showInsuranceRelWithNominee">
				  <html:optionsCollection name="showInsuranceRelWithNominee" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
                  </html:select>
                  </td>
		 </tr>
 		<tr>
 		
 		  <td><bean:message key="lbl.tenureYear"/></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="tenureYear" property="tenureYear"  value="${fetchCollateralDetails[0].tenureYear}" style="text-align: right" onkeypress="return isNumberKey(event);"/></td>
 		  
		 </tr>
		 	 		 
		 <tr>
 		  <td><button id="save" type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick=" saveInsuranceDetails();"><bean:message key="button.save" /></button>  </td>
		  </tr>  
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>   
	</table>
	</td>
	</tr>
	</table>
	</fieldset>	
	
</html:form>
</logic:present>
<%--          			view Deal 																 --%>
<logic:present name="viewDeal">
<html:form action="/collateralInsuranceProcessAction" styleId="INSForm" method="post">
<html:hidden property="assetsIdIns" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdIns"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="fd1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="fd2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="fd3"/>
<fieldset>
<legend><bean:message key="lbl.insuranceDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>   
  		<tr>
 		<td><bean:message key="lbl.insuranceAgency"></bean:message> <font color="red">* </font></td>

		   
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="${fetchCollateralDetails[0].insuranceAgency}" readonly="true"/>
           <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency" value="${fetchCollateralDetails[0].lbxInsuranceAgency}"/>	   
		 </td>
		  <td style="width:23%"><bean:message key="lbl.insuranceDesc"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" disabled="true" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 		 
 		</tr>

 		<tr>
 		<td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	      <td>
	      <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo"  disabled="true" value="${fetchCollateralDetails[0].coverNoteNo}" maxlength="20"  ></html:text>
 		  </td>
 		  <td style="width:23%"><bean:message key="lbl.policyNo"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="policyNo" property="policyNo" maxlength="100"  disabled="true" value="${fetchCollateralDetails[0].policyNo}" /></td>
 		  
 		  </tr>
 		  <tr>
 		  
		   <td><bean:message key="lbl.policyStartDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text"  property="policyStartDate" value="${fetchCollateralDetails[0].policyStartDate}" disabled="true" /></td>
		   <td><bean:message key="lbl.maturityDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text"  property="insMatureDate" value="${fetchCollateralDetails[0].insMatureDate}" disabled="true"/></td>
		 </tr>
 		 <tr>
		 <td><bean:message key="lbl.sumAssured"/><font color="red">*</font></td>
		 <td nowrap="nowrap"><html:text styleClass="text" styleId="sumAssured" property="sumAssured" style="text-align: right" disabled="true" value="${fetchCollateralDetails[0].sumAssured}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		 <td><bean:message key="lbl.surrenderValue"/><font color="red">*</font></td>
		 <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" disabled="true" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		 </tr>
 		
 		<tr>
 		<td><bean:message key="lbl.premiumAmount"/><font color="red">*</font></td>
 		  <td noWrap><html:text styleClass="text" styleId="premiumAmount" property="premiumAmount" style="text-align: right" value="${fetchCollateralDetails[0].premiumAmount}" disabled="true" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 		  <td style="width:23%"><bean:message key="lbl.premiumFrequency"/></td>
           <td style="width:26%">
           <html:select property="premiumFrequency" styleId="premiumFrequency" styleClass="text" value="${fetchCollateralDetails[0].premiumFrequency}" disabled="true">
             <html:option value=""></html:option>
             <html:option value="MONTHLY">MONTHLY</html:option>
             <html:option value="QUARTERLY">QUARTERLY</html:option>
             <html:option value="HALFYEARLY">HALF YEARLY</html:option>      
             <html:option value="ANNUALY">ANNUALY</html:option>        
           </html:select></td>
 		  
 		  </tr>
 		 	
 		  <tr>
 		  <td style="width:23%"><bean:message key="lbl.nominee"/></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value="${fetchCollateralDetails[0].nominee}" disabled="true" /></td>
		   <td><bean:message key="lbl.relWithNominee"/></td>
		   <td nowrap="nowrap"><html:select styleClass="text"  styleId="relWithNominee" value="${fetchCollateralDetails[0].relWithNominee}" property="relWithNominee" disabled="true" >
                  <html:option value="">Select</html:option>  
       			  <logic:present name="showInsuranceRelWithNominee">
				  <logic:notEmpty name="showInsuranceRelWithNominee">
				  <html:optionsCollection name="showInsuranceRelWithNominee" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
                  </html:select>
                  </td>
		 </tr>
 		<tr>
 		
 		  <td><bean:message key="lbl.tenureYear"/></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="tenureYear" property="tenureYear"  value="${fetchCollateralDetails[0].tenureYear}"  disabled="true" style="text-align: right" onkeypress="return isNumberKey(event);"/></td>
 		  
		 </tr>
		 	 		 
<!--		 <tr>-->
<!-- 		  <td><button id="save" type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick=" saveInsuranceDetails();"><bean:message key="button.save" /></button>  </td>-->
<!--		  </tr>  -->
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>   
	</table>
	</td>
	</tr>
	</table>
	</fieldset>	
	
</html:form>
</logic:present>
</div>

<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewInsuranceInCM">
<html:form action="/collateralInsuranceProcessAction" styleId="INSForm" method="post">

<fieldset>
<legend><bean:message key="lbl.insuranceDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>   
 
 		<tr>
 		<td><bean:message key="lbl.insuranceAgency"></bean:message> <font color="red">* </font></td>

		   
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="${viewInsuranceInCM[0].insuranceAgency}" readonly="true"/>
           <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency" value="${viewInsuranceInCM[0].lbxInsuranceAgency}"/>	   
		 </td>
		  <td style="width:23%"><bean:message key="lbl.insuranceDesc"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" disabled="true" value="${viewInsuranceInCM[0].assetsCollateralDesc}" /></td>
 		 
 		</tr>

 		<tr>
 		<td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	      <td>
	      <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo"  disabled="true" value="${viewInsuranceInCM[0].coverNoteNo}" maxlength="20"  ></html:text>
 		  </td>
 		  <td style="width:23%"><bean:message key="lbl.policyNo"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="policyNo" property="policyNo" maxlength="100"  disabled="true" value="${viewInsuranceInCM[0].policyNo}" /></td>
 		  
 		  </tr>
 		  <tr>
 		  
		   <td><bean:message key="lbl.policyStartDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text"  property="policyStartDate" value="${viewInsuranceInCM[0].policyStartDate}" disabled="true" /></td>
		   <td><bean:message key="lbl.maturityDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text"  property="insMatureDate" value="${viewInsuranceInCM[0].insMatureDate}" disabled="true"/></td>
		 </tr>
 		 <tr>
		 <td><bean:message key="lbl.sumAssured"/><font color="red">*</font></td>
		 <td nowrap="nowrap"><html:text styleClass="text" styleId="sumAssured" property="sumAssured" style="text-align: right" disabled="true" value="${viewInsuranceInCM[0].sumAssured}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		 <td><bean:message key="lbl.surrenderValue"/><font color="red">*</font></td>
		 <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" disabled="true" value="${viewInsuranceInCM[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		 </tr>
 		
 		<tr>
 		<td><bean:message key="lbl.premiumAmount"/><font color="red">*</font></td>
 		  <td noWrap><html:text styleClass="text" styleId="premiumAmount" property="premiumAmount" style="text-align: right" value="${viewInsuranceInCM[0].premiumAmount}" disabled="true" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 		  <td style="width:23%"><bean:message key="lbl.premiumFrequency"/></td>
           <td style="width:26%">
           <html:select property="premiumFrequency" styleId="premiumFrequency" styleClass="text" value="${viewInsuranceInCM[0].premiumFrequency}" disabled="true">
             <html:option value=""></html:option>
             <html:option value="MONTHLY">MONTHLY</html:option>
             <html:option value="QUARTERLY">QUARTERLY</html:option>
             <html:option value="HALFYEARLY">HALF YEARLY</html:option>      
             <html:option value="ANNUALY">ANNUALY</html:option>        
           </html:select></td>
 		  
 		  </tr>
 		 	
 		  <tr>
 		  <td style="width:23%"><bean:message key="lbl.nominee"/></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value="${viewInsuranceInCM[0].nominee}" disabled="true" /></td>
		   <td><bean:message key="lbl.relWithNominee"/></td>
		   <td nowrap="nowrap"><html:select styleClass="text"  styleId="relWithNominee" value="${viewInsuranceInCM[0].relWithNominee}" property="relWithNominee" disabled="true" >
                  <html:option value="">Select</html:option>  
       			  <logic:present name="showInsuranceRelWithNominee">
				  <logic:notEmpty name="showInsuranceRelWithNominee">
				  <html:optionsCollection name="showInsuranceRelWithNominee" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
                  </html:select>
                  </td>
		 </tr>
 		<tr>
 		
 		  <td><bean:message key="lbl.tenureYear"/></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="tenureYear" property="tenureYear"  value="${viewInsuranceInCM[0].tenureYear}"  disabled="true" style="text-align: right" onkeypress="return isNumberKey(event);"/></td>
 		  
		 </tr>
		 	 		 
<!--		 <tr>-->
<!-- 		  <td><button id="save" type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick=" saveInsuranceDetails();"><bean:message key="button.save" /></button>  </td>-->
<!--		  </tr>  -->
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>   
	</table>
	</td>
	</tr>
	</table>
	</fieldset>	
	
</html:form>
</logic:present>


<%--          			Insertion in LOAN iNITIATION													 --%>
<logic:present name="loanInit">
	

<html:form action="/collateralInsuranceProcessAction" styleId="INSForm" method="post">
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdIns" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdIns"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="fd1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="fd2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="fd3"/>
<fieldset>
<legend><bean:message key="lbl.insuranceDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>   
 		<tr>
 		<td><bean:message key="lbl.insuranceAgency"></bean:message> <font color="red">* </font></td>

		   
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="${fetchCollateralDetails[0].insuranceAgency}" readonly="true"/>
           <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency" value="${fetchCollateralDetails[0].lbxInsuranceAgency}"/>
           <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerForm','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>	   
		 </td>
		  <td style="width:23%"><bean:message key="lbl.insuranceDesc"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 		 
 		</tr>

 		<tr>
 		<td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	      <td>
	      <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="${fetchCollateralDetails[0].coverNoteNo}" maxlength="20"  ></html:text>
 		  </td>
 		  <td style="width:23%"><bean:message key="lbl.policyNo"/><font color="red">*</font></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="policyNo" property="policyNo" maxlength="100" value="${fetchCollateralDetails[0].policyNo}" /></td>
 		  
 		  </tr>
 		  <tr>
 		  
		   <td><bean:message key="lbl.policyStartDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="policyStartDate" property="policyStartDate" value="${fetchCollateralDetails[0].policyStartDate}" onchange="checkDate('policyStartDate'); policyFirstDateValid(); "/></td>
		   <td><bean:message key="lbl.maturityDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		   <td nowrap="nowrap"><html:text styleClass="text" styleId="insMatureDate" property="insMatureDate" value="${fetchCollateralDetails[0].insMatureDate}" onchange="checkDate('insMatureDate'); insValidMaturity(); "/></td>
		 </tr>
 		 <tr>
		 <td><bean:message key="lbl.sumAssured"/><font color="red">*</font></td>
		 <td nowrap="nowrap"><html:text styleClass="text" styleId="sumAssured" property="sumAssured" style="text-align: right" value="${fetchCollateralDetails[0].sumAssured}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		 <td><bean:message key="lbl.surrenderValue"/><font color="red">*</font></td>
		 <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		 </tr>
 		
 		<tr>
 		<td><bean:message key="lbl.premiumAmount"/><font color="red">*</font></td>
 		  <td noWrap><html:text styleClass="text" styleId="premiumAmount" property="premiumAmount" style="text-align: right" value="${fetchCollateralDetails[0].premiumAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 		  <td style="width:23%"><bean:message key="lbl.premiumFrequency"/></td>
           <td style="width:26%">
           <html:select property="premiumFrequency" styleId="premiumFrequency" styleClass="text" value="${fetchCollateralDetails[0].premiumFrequency}">
             <html:option value=""></html:option>
             <html:option value="MONTHLY">MONTHLY</html:option>
             <html:option value="QUARTERLY">QUARTERLY</html:option>
             <html:option value="HALFYEARLY">HALF YEARLY</html:option>      
             <html:option value="ANNUALY">ANNUALY</html:option>        
           </html:select></td>
 		  
 		  </tr>
 		 	
 		  <tr>
 		  <td style="width:23%"><bean:message key="lbl.nominee"/></td>
 		  <td style="width:26%"><html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value="${fetchCollateralDetails[0].nominee}"  /></td>
		   <td><bean:message key="lbl.relWithNominee"/></td>
		   <td nowrap="nowrap"><html:select styleClass="text"  styleId="relWithNominee" value="${fetchCollateralDetails[0].relWithNominee}" property="relWithNominee">
                  <html:option value="">Select</html:option>  
       			  <logic:present name="showInsuranceRelWithNominee">
				  <logic:notEmpty name="showInsuranceRelWithNominee">
				  <html:optionsCollection name="showInsuranceRelWithNominee" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
                  </html:select>
                  </td>
		 </tr>
 		<tr>
 		
 		  <td><bean:message key="lbl.tenureYear"/></td>
 		  <td nowrap="nowrap"><html:text styleClass="text" styleId="tenureYear" property="tenureYear"  value="${fetchCollateralDetails[0].tenureYear}" style="text-align: right" onkeypress="return isNumberKey(event);"/></td>
 		  
		 </tr>
		 	 		 
		 <tr>
 		  <td><button id="save" type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick=" saveInsuranceDetails();"><bean:message key="button.save" /></button>  </td>
		  </tr>  
		   <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>   
	</table>
	</td>
	</tr>
	</table>
	</fieldset>	
	
</html:form>
</logic:present>
</div>

</body>
<%--
      Author Name-      Pawan Kumar Singh
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
<%@page import="com.login.roleManager.UserObject"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

	<head>

  <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		
		
		
		
		
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>

 	<!--jquery and js for number formatting -->
  	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
    <!-- jquery and js for number formatting -->

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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('SBLCForm').assetsCollateralDesc.focus();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
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
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">
<html:form action="/collateralSBLCProcessAction" styleId="SBLCForm" method="post">	
<html:javascript formName="CollateralSBLCDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdSBLC" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdSBLC"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="sblc1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="sblc2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="sblc3"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.sblcDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
       <TR>
         <td style="width:23%"><bean:message key="lbl.sblcDescription"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" maxlength="100" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
         <TD><bean:message key="lbl.sblcValue"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="assetsCollateralValue" style="text-align: right" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></td>
         </TR>
       <TR>
		 <td width="23%" style="width:23%"><bean:message key="lbl.sblcSecutiryMargin"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="collateralSecurityMargin" maxlength="3" style="text-align: right" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></TD>
		 <TD><bean:message key="lbl.sblcAmount"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcAmount" property="sblcAmount" style="text-align: right" value="${fetchCollateralDetails[0].sblcAmount}" maxlength="15" onchange="numberFormatting(this.id,'2');" /> </TD>
		 </TR>
		 <TR>
		 <TD><bean:message key="lbl.sblcValidity"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcValidity" property="sblcValidity" value="${fetchCollateralDetails[0].sblcValidity}"  onchange="return checkDate('sblcValidity');"/> </TD>
		 <TD><bean:message key="lbl.sblcIssuingDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcIssuingDate" property="sblcIssuingDate" value="${fetchCollateralDetails[0].sblcIssuingDate}" onchange="return checkDate('sblcIssuingDate');"/> </TD>
		 </TR>		
		 <TR>
         <TD><bean:message key="lbl.parentCompany"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcParentCompany" property="sblcParentCompany" maxlength="50" value="${fetchCollateralDetails[0].sblcParentCompany}" /></TD>
		 </TR>
		 <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveSBLCDetails();"><bean:message key="button.save" /></button> </td>
		  </tr>  
		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>     
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
</html:form>
</logic:present>
<%--          			view Deal 																 --%>



<logic:present name="viewDeal">
<html:form action="/collateralSBLCProcessAction" styleId="SBLCForm" method="post">	
<html:javascript formName="CollateralSBLCDynaValidatorForm"/>
<html:hidden property="assetsIdSBLC" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdSBLC"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="sblc1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="sblc2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="sblc3"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.sblcDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
       <TR>
         <td style="width:23%"><bean:message key="lbl.sblcDescription"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
         <TD><bean:message key="lbl.sblcValue"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" style="text-align: right" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></td>
         </TR>
       <TR>
		 <td width="23%" style="width:23%"><bean:message key="lbl.sblcSecutiryMargin"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="collateralSecurityMargin" style="text-align: right" disabled="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></TD>
		 <TD><bean:message key="lbl.sblcAmount"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcAmount" disabled="true" property="sblcAmount" maxlength="15" style="text-align: right" value="${fetchCollateralDetails[0].sblcAmount}" onchange="numberFormatting(this.id,'2');"/> </TD>
		 </TR>
		 <TR>
		 <TD><bean:message key="lbl.sblcValidity"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text"  property="sblcValidity" disabled="true" value="${fetchCollateralDetails[0].sblcValidity}"  /> </TD>
		 <TD><bean:message key="lbl.sblcIssuingDate"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text"  property="sblcIssuingDate" disabled="true" value="${fetchCollateralDetails[0].sblcIssuingDate}" /> </TD>
		 </TR>		
		 <TR>
         <TD><bean:message key="lbl.parentCompany"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcParentCompany" disabled="true" property="sblcParentCompany" value="${fetchCollateralDetails[0].sblcParentCompany}" /></TD>
		 </TR>
<!--		 <tr>-->
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveSBLCDetails();"/> </td>-->
<!--		  </tr>  -->
		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>     
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
</html:form>
</logic:present>
</div>
	<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewSBLCInCM">

	<html:form action="/collateralSBLCProcessAction" styleId="SBLCForm" method="post">	

<FIELDSET>
<LEGEND><bean:message key="lbl.sblcDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
       <TR>
         <td style="width:23%"><bean:message key="lbl.sblcDescription"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${viewSBLCInCM[0].assetsCollateralDesc}" /></td>
         <TD><bean:message key="lbl.sblcValue"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" style="text-align: right" property="assetsCollateralValue" value="${viewSBLCInCM[0].assetsCollateralValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></td>
         </TR>
       <TR>
		 <td width="23%" style="width:23%"><bean:message key="lbl.sblcSecutiryMargin"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" style="text-align: right" property="collateralSecurityMargin" value="${viewSBLCInCM[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></TD>
		 <TD><bean:message key="lbl.sblcAmount"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcAmount" disabled="true" property="sblcAmount" maxlength="15" style="text-align: right" value="${viewSBLCInCM[0].sblcAmount}" onchange="numberFormatting(this.id,'2');"/> </TD>
		 </TR>
		 <TR>
		 <TD><bean:message key="lbl.sblcValidity"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text"  property="sblcValidity" disabled="true" value="${viewSBLCInCM[0].sblcValidity}"  /> </TD>
		 <TD><bean:message key="lbl.sblcIssuingDate"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text"  property="sblcIssuingDate" disabled="true" value="${viewSBLCInCM[0].sblcIssuingDate}" /> </TD>
		 </TR>		
		 <TR>
         <TD><bean:message key="lbl.parentCompany"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcParentCompany" disabled="true" property="sblcParentCompany" value="${viewSBLCInCM[0].sblcParentCompany}" /></TD>
		 </TR>
<!--		 <tr>-->
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveSBLCDetails();"/> </td>-->
<!--		  </tr>  -->
		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>     
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
</html:form>
</logic:present>

<%--          			Insertion in LOAN iNITIATION													 --%>
<logic:present name="loanInit">
	<html:form action="/collateralSBLCProcessAction" styleId="SBLCForm" method="post">	
<html:javascript formName="CollateralSBLCDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdSBLC" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdSBLC"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="sblc1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="sblc2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="sblc3"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.sblcDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
       <TR>
         <td style="width:23%"><bean:message key="lbl.sblcDescription"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" maxlength="100" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
         <TD><bean:message key="lbl.sblcValue"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="assetsCollateralValue" maxlength="18" style="text-align: right" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></td>
         </TR>
       <TR>
		 <td width="23%" style="width:23%"><bean:message key="lbl.sblcSecutiryMargin"/><font color="red">*</font></TD>
         <td width="26%" style="width:26%"><html:text styleClass="text" styleId="collateralSecurityMargin" maxlength="3" style="text-align: right" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></TD>
		 <TD><bean:message key="lbl.sblcAmount"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcAmount" property="sblcAmount" maxlength="15" style="text-align: right" value="${fetchCollateralDetails[0].sblcAmount}" onchange="numberFormatting(this.id,'2');"/> </TD>
		 </TR>
		 <TR>
		 <TD><bean:message key="lbl.sblcValidity"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcValidity" property="sblcValidity" value="${fetchCollateralDetails[0].sblcValidity}"  onchange="return checkDate('sblcValidity');"/> </TD>
		 <TD><bean:message key="lbl.sblcIssuingDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcIssuingDate" property="sblcIssuingDate" value="${fetchCollateralDetails[0].sblcIssuingDate}" onchange="return checkDate('sblcIssuingDate');"/> </TD>
		 </TR>		
		 <TR>
         <TD><bean:message key="lbl.parentCompany"/><font color="red">*</font></TD>
         <TD noWrap><html:text styleClass="text" styleId="sblcParentCompany" property="sblcParentCompany" maxlength="50" value="${fetchCollateralDetails[0].sblcParentCompany}" /></TD>
		 </TR>
		 <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveSBLCDetails();"><bean:message key="button.save" /></button> </td>
		  </tr>  
		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>     
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
</html:form>
</logic:present>

	</div>
	</body>
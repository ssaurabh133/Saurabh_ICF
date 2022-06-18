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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

	<head>
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>

	<!--jquery and js for number formatting -->
  	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
    <!-- jquery and js for number formatting -->
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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('SecurityForm').assetsCollateralDesc.focus();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
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

<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">
 <html:form action="/collateralSecurityProcessAction" styleId="SecurityForm" method="post">
<html:javascript formName="CollateralSecurityDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdSecurity" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdSecurity"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="sec1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="sec2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="sec3"/>

 <FIELDSET>
<LEGEND><bean:message key="lbl.securitiesDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>        
        <TR>
          <td style="width:23%"><bean:message key="lbl.securitiesDescription"/><font color="red">*</font></TD>
          <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></TD>
          <td style="width:23%"><bean:message key="lbl.securitiesValue"/><font color="red">*</font></TD>
          <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></TD>
        </TR>
        <TR>
         <td width="23%" style="width:23%"><bean:message key="lbl.securityMargin"/><font color="red">*</font></TD>
         <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" maxlength="3" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
           <td style="width:23%"><bean:message key="lbl.securitiesType"/><font color="red">*</font> </td>
           <td style="width:26%"><html:select property="securityType" styleId="securityType" styleClass="text" value="${fetchCollateralDetails[0].securityType}">
             <html:option value="m">Mutual Funds</html:option>
             <html:option value="s"> Shares and Debentures</html:option>
             <html:option value="b">Bonds</html:option>
           </html:select></td>
		    </TR>
        <TR>
          <td style="width:23%"><bean:message key="lbl.securitiesCategories"/><font color="red">*</font> </td>
          <td style="width:26%"><html:select property="securityCategory" styleId="securityCategory" styleClass="text" value="${fetchCollateralDetails[0].securityCategory}">
            <html:option value="X">X</html:option>
            <html:option value="Y">Y</html:option>
            <html:option value="Z">Z</html:option>
          </html:select></td>
		 <td><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
           <td nowrap="nowrap"><html:text styleClass="text" styleId="securityMarketValue" style="text-align: right" property="securityMarketValue" value="${fetchCollateralDetails[0].securityMarketValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></td>			  	         
		  </TR>	
			  <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveSecurityDetails();"><bean:message key="button.save" /></button>  </td>
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
 <html:form action="/collateralSecurityProcessAction" styleId="SecurityForm" method="post">
<html:javascript formName="CollateralSecurityDynaValidatorForm"/>
<html:hidden property="assetsIdSecurity" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdSecurity"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="sec1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="sec2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="sec3"/>
 <FIELDSET>
<LEGEND><bean:message key="lbl.securitiesDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>        
        <TR>
          <td style="width:23%"><bean:message key="lbl.securitiesDescription"/><font color="red">*</font></TD>
          <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></TD>
          <td style="width:23%"><bean:message key="lbl.securitiesValue"/><font color="red">*</font></TD>
          <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" style="text-align: right" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></TD>
        </TR>
        <TR>
         <td width="23%" style="width:23%"><bean:message key="lbl.securityMargin"/><font color="red">*</font></TD>
         <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" style="text-align: right" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
           <td style="width:23%"><bean:message key="lbl.securitiesType"/><font color="red">*</font> </td>
           <td style="width:26%"><html:select property="securityType" styleId="securityType" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].securityType}">
             <option value="m"><bean:message key="lbl.mutualFunds"/></option>
             <option value="s"><bean:message key="lbl.sharesDebentures"/></option>
             <option value="b"><bean:message key="lbl.bonds"/></option>
           </html:select></td>
		    </TR>
        <TR>
          <td style="width:23%"><bean:message key="lbl.securitiesCategories"/><font color="red">*</font> </td>
          <td style="width:26%"><html:select property="securityCategory" styleId="securityCategory" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].securityCategory}">
            <option value="X">X</option>
            <option value="Y">Y</option>
            <option value="Z">Z</option>
          </html:select></td>
		 <td><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
           <td nowrap="nowrap"><html:text styleClass="text" styleId="securityMarketValue" style="text-align: right" disabled="true" property="securityMarketValue" value="${fetchCollateralDetails[0].securityMarketValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></td>			  	         
		  </TR>	
<!--			  <tr>-->
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveSecurityDetails();"/>  </td>-->
<!--		  </tr>	-->
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
<logic:present name="viewSecuritiesInCM">
	 <html:form action="/collateralSecurityProcessAction" styleId="SecurityForm" method="post">

 <FIELDSET>
<LEGEND><bean:message key="lbl.securitiesDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>        
        <TR>
          <td style="width:23%"><bean:message key="lbl.securitiesDescription"/><font color="red">*</font></TD>
          <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${viewSecuritiesInCM[0].assetsCollateralDesc}" /></TD>
          <td style="width:23%"><bean:message key="lbl.securitiesValue"/><font color="red">*</font></TD>
          <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" style="text-align: right" property="assetsCollateralValue" value="${viewSecuritiesInCM[0].assetsCollateralValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></TD>
        </TR>
        <TR>
         <td width="23%" style="width:23%"><bean:message key="lbl.securityMargin"/><font color="red">*</font></TD>
         <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" style="text-align: right" property="collateralSecurityMargin" value="${viewSecuritiesInCM[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
           <td style="width:23%"><bean:message key="lbl.securitiesType"/><font color="red">*</font> </td>
           <td style="width:26%"><html:select property="securityType" styleId="securityType" disabled="true" styleClass="text" value="${viewSecuritiesInCM[0].securityType}">
             <option value="m"><bean:message key="lbl.mutualFunds"/></option>
             <option value="s"><bean:message key="lbl.sharesDebentures"/></option>
             <option value="b"><bean:message key="lbl.bonds"/></option>
           </html:select></td>
		    </TR>
        <TR>
          <td style="width:23%"><bean:message key="lbl.securitiesCategories"/><font color="red">*</font> </td>
          <td style="width:26%"><html:select property="securityCategory" styleId="securityCategory" disabled="true" styleClass="text" value="${viewSecuritiesInCM[0].securityCategory}">
            <option value="X">X</option>
            <option value="Y">Y</option>
            <option value="Z">Z</option>
          </html:select></td>
		 <td><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
           <td nowrap="nowrap"><html:text styleClass="text" styleId="securityMarketValue" disabled="true" style="text-align: right" property="securityMarketValue" value="${viewSecuritiesInCM[0].securityMarketValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></td>			  	         
		  </TR>	
<!--			  <tr>-->
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveSecurityDetails();"/>  </td>-->
<!--		  </tr>	-->
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
	<html:form action="/collateralSecurityProcessAction" styleId="SecurityForm" method="post">
<html:javascript formName="CollateralSecurityDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdSecurity" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdSecurity"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="sec1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="sec2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="sec3"/>
 <FIELDSET>
<LEGEND><bean:message key="lbl.securitiesDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>        
        <TR>
          <td style="width:23%"><bean:message key="lbl.securitiesDescription"/><font color="red">*</font></TD>
          <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></TD>
          <td style="width:23%"><bean:message key="lbl.securitiesValue"/><font color="red">*</font></TD>
          <td style="width:26%"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" maxlength="15" onchange="numberFormatting(this.id,'2');"/></TD>
        </TR>
        <TR>
         <td width="23%" style="width:23%"><bean:message key="lbl.securityMargin"/><font color="red">*</font></TD>
         <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" style="text-align: right" maxlength="3" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');"/></td>
           <td style="width:23%"><bean:message key="lbl.securitiesType"/><font color="red">*</font> </td>
           <td style="width:26%"><html:select property="securityType" styleId="securityType" styleClass="text" value="${fetchCollateralDetails[0].securityType}">
             <html:option value="m">Mutual Funds</html:option>
             <html:option value="s"> Shares and Debentures</html:option>
             <html:option value="b">Bonds</html:option>
           </html:select></td>
		    </TR>
        <TR>
          <td style="width:23%"><bean:message key="lbl.securitiesCategories"/><font color="red">*</font> </td>
          <td style="width:26%"><html:select property="securityCategory" styleId="securityCategory" styleClass="text" value="${fetchCollateralDetails[0].securityCategory}">
            <html:option value="X">X</html:option>
            <html:option value="Y">Y</html:option>
            <html:option value="Z">Z</html:option>
          </html:select></td>
		 <td><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
           <td nowrap="nowrap"><html:text styleClass="text" styleId="securityMarketValue"  style="text-align: right" property="securityMarketValue" value="${fetchCollateralDetails[0].securityMarketValue}" maxlength="15"  onchange="numberFormatting(this.id,'2');"/></td>			  	         
		  </TR>	
			  <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveSecurityDetails();"><bean:message key="button.save" /></button>  </td>
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
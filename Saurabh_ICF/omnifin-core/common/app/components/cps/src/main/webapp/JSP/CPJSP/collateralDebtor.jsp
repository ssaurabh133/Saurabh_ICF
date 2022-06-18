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
 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>	
 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
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
<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('DebtorForm').assetsCollateralDesc.focus();init_fields();">
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

 <html:form action="/collateralDebtorProcessAction" styleId="DebtorForm" method="post">
<html:javascript formName="CollateralDebtorDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdDebtor" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdDebtor"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="debtor1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="debtor2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="debtor3"/> 

<FIELDSET>
<LEGEND><bean:message key="lbl.debtorDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>  
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.debtorDescription"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc"  maxlength="100"  property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 	   <td style="width:23%"><bean:message key="lbl.debtorValue"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue"  maxlength="18" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 	   </TR>
 	 <TR>
 	   <td><bean:message key="lbl.debtorSecurityMargin"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin"  maxlength="3" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');" onkeypress="return isNumberKey(event);"/></td>
 	   <td><bean:message key="lbl.debtorType"/></td>
 	   <td style="width:26%"><html:select property="debtorType" styleId="debtorType" styleClass="text" value="${fetchCollateralDetails[0].debtorType}" >
 	     <option value="30"><30 Days</option>
 	     <option value="31">31-60 Days</option>
 	     <option value="61">61-90 Days</option>
 	     <option value="91">91> Days</option>
 	     </html:select></td>
 	   </TR>
 	 <TR>
 	   <td><bean:message key="lbl.totalOutStanding"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="debtorTotal"  maxlength="50" property="debtorTotal"  style="text-align: right" value="${fetchCollateralDetails[0].debtorTotal}" onkeypress="return isNumberKey(event);"/></td>
 	   </TR>
        <TR>
		
		  </TR>
		  <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveDebtorDetails();"><bean:message key="button.save" /></button> </td>
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
 <html:form action="/collateralDebtorProcessAction" styleId="DebtorForm" method="post">
<html:javascript formName="CollateralDebtorDynaValidatorForm"/>
<html:hidden property="assetsIdDebtor" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdDebtor"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="debtor1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="debtor2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="debtor3"/> 

<FIELDSET>
<LEGEND><bean:message key="lbl.debtorDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>  
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.debtorDescription"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 	   <td style="width:23%"><bean:message key="lbl.debtorValue"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return isNumberKey(event);"/></td>
 	   </TR>
 	 <TR>
 	   <td><bean:message key="lbl.debtorSecurityMargin"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');" onkeypress="return isNumberKey(event);"/></td>
 	   <td><bean:message key="lbl.debtorType"/></td>
 	   <td style="width:26%"><html:select property="debtorType" styleId="debtorType" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].debtorType}" >
 	     <option value="30"><30 Days</option>
 	     <option value="31">31-60 Days</option>
 	     <option value="61">61-90 Days</option>
 	     <option value="91">91> Days</option>
 	     </html:select></td>
 	   </TR>
 	 <TR>
 	   <td><bean:message key="lbl.totalOutStanding"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="debtorTotal" disabled="true"  style="text-align: right" property="debtorTotal" value="${fetchCollateralDetails[0].debtorTotal}" onkeypress="return isNumberKey(event);"/></td>
 	   </TR>
        <TR>
		
		  </TR>
		  <tr>
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveDebtorDetails();"/> </td>-->
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
<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewDebtorInCM">
	 <html:form action="/collateralDebtorProcessAction" styleId="DebtorForm" method="post">

<FIELDSET>
<LEGEND><bean:message key="lbl.debtorDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>  
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.debtorDescription"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${viewDebtorInCM[0].assetsCollateralDesc}" /></td>
 	   <td style="width:23%"><bean:message key="lbl.debtorValue"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" style="text-align: right" value="${viewDebtorInCM[0].assetsCollateralValue}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 	   </TR>
 	 <TR>
 	   <td><bean:message key="lbl.debtorSecurityMargin"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" property="collateralSecurityMargin" style="text-align: right" value="${viewDebtorInCM[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');" onkeypress="return isNumberKey(event);"/></td>
 	   <td><bean:message key="lbl.debtorType"/></td>
 	   <td style="width:26%"><html:select property="debtorType" styleId="debtorType" disabled="true" styleClass="text" value="${viewDebtorInCM[0].debtorType}" >
 	     <option value="30"><30 Days</option>
 	     <option value="31">31-60 Days</option>
 	     <option value="61">61-90 Days</option>
 	     <option value="91">91> Days</option>
 	     </html:select></td>
 	   </TR>
 	 <TR>
 	   <td><bean:message key="lbl.totalOutStanding"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="debtorTotal"  style="text-align: right" disabled="true" property="debtorTotal" value="${viewDebtorInCM[0].debtorTotal}" onkeypress="return isNumberKey(event);"/></td>
 	   </TR>
        <TR>
		
		  </TR>
		  <tr>
<!-- 		  <td><input type="button" name="Submit20" value="Save" class="topformbutton2" onclick="return saveDebtorDetails();"/> </td>-->
		  </tr>
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
	
<html:form action="/collateralDebtorProcessAction" styleId="DebtorForm" method="post">
<html:javascript formName="CollateralDebtorDynaValidatorForm"/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdDebtor" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdDebtor"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="debtor1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="debtor2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="debtor3"/> 

<FIELDSET>
<LEGEND><bean:message key="lbl.debtorDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>  
 	 <TR>
 	   <td style="width:23%"><bean:message key="lbl.debtorDescription"/><font color="red">*</font></TD>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc"  maxlength="100"  property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
 	   <td style="width:23%"><bean:message key="lbl.debtorValue"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue"  maxlength="18" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
 	   </TR>
 	 <TR>
 	   <td><bean:message key="lbl.debtorSecurityMargin"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin"  maxlength="3" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeyup="return checkRate('collateralSecurityMargin');" onkeypress="return isNumberKey(event);"/></td>
 	   <td><bean:message key="lbl.debtorType"/></td>
 	   <td style="width:26%"><html:select property="debtorType" styleId="debtorType" styleClass="text" value="${fetchCollateralDetails[0].debtorType}" >
 	     <option value="30"><30 Days</option>
 	     <option value="31">31-60 Days</option>
 	     <option value="61">61-90 Days</option>
 	     <option value="91">91> Days</option>
 	     </html:select></td>
 	   </TR>
 	 <TR>
 	   <td><bean:message key="lbl.totalOutStanding"/><font color="red">*</font></td>
 	   <td nowrap="nowrap"><html:text styleClass="text" styleId="debtorTotal" style="text-align: right" maxlength="50" property="debtorTotal"  value="${fetchCollateralDetails[0].debtorTotal}" onkeypress="return isNumberKey(event);"/></td>
 	   </TR>
        <TR>
		
		  </TR>
		  <tr>
 		  <td><button type="button" name="Submit20" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveDebtorDetails();"><bean:message key="button.save" /></button> </td>
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
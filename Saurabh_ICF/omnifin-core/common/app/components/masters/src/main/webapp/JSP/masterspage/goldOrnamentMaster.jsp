<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>



<title><bean:message key="a3s.noida" /></title>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/contentstyle.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/subpage.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/branchScript/branch.js"></script>

</head>
<body onload="enableAnchor();init_fields();">
<html:form styleId="form1" method="post" action="/product">
<div align="center"><font color="red">${requestScope.sms}</font></div>

<fieldset>
<legend><bean:message key="product.master" /></legend>

  <table width="100%">
 
    <tr>
      <td><bean:message key="product.code" /><span><font color="red">*</font></span></td>
      
      <td><html:text property="productCode"  styleClass="text" styleId="productCode" maxlength="10" /></td>
      <td><span><font color="red"><html:errors property="productCode"  /></font></span></td>
      <td><bean:message key="product.desc" /><span><font color="red">*</font></span></td>
      
      <td><html:text property="productDesc" styleClass="text" styleId="productDesc"  /></td>
      <td ><span><font color="red"><html:errors property="productDesc" /></font></span></td>
      <td><bean:message key="product.active" /></td>
      <td><input type="checkbox" name="activebox"  value="YES" checked="checked" /></td>
      
  </tr>
  <tr><td>&nbsp; </td></tr>
  
			<tr align="left">
			
			<td> <button type="button"  name="method" id="save" class="topformbutton2" onclick="return savebySubmit();"><bean:message key="button.save" /></button>
			 <button type="button"  name="method" id="update" style="display: none;" class="topformbutton2" onclick="return UpdatebySubmit();"><bean:message key="button.update"/></button>&nbsp;
			</td>
			
			</tr>
	</table>		


</fieldset>
        
		<fieldset>
<legend><bean:message key="product.detail" /></legend>

<table width="100%" border="0" cellpadding="4" cellspacing="1" class="gridtd">
  <tr class="gridheader">
    <td><div align="center">
  	  <bean:message key="branch.select" />
	  </div></td>
    <td ><div align="center">
      <bean:message key="product.code" />
    </div></td>
    <td ><div align="center">
      <bean:message key="product.desc" />
    </div></td>
    <td ><div align="center">
      <bean:message key="product.active" />
    </div></td>
  </tr>
   <logic:present name="productdetail">
  <logic:iterate id="subproductdetail" name="productdetail">
  
  <tr class="white1">
    <td ><input type="radio" name="check" value="on" id="check" onClick="checkRadioForProduct();"/></td>
    <td ><div align="right">
      ${subproductdetail.code}<input type="hidden" name="hiddencode" id="hiddencode" value=" ${subproductdetail.code}" />
    </div></td>
    <td ><div align="right">
       ${subproductdetail.desc}<input type="hidden" name="hiddendesc" id="hiddendesc" value=" ${subproductdetail.desc}" />
    </div></td>
    <td><div align="right">
       ${subproductdetail.status}
    </div></td>
  </tr>
   </logic:iterate>
  </logic:present>
</table>
</fieldset>
<br/>

           
			
		</html:form>
  </body>
	</html:html>
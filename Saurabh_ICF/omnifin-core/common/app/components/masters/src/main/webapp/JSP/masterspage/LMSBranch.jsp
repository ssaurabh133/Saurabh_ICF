<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

<html:javascript formName="BranchMasterDynaValidatorForm" />

<title><bean:message key="a3s.noida" /></title>


<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/contentstyle.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/subpage.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/branchScript/branch.js"></script>





 

</head>
<body onload="enableAnchor();init_fields();">
<html:form styleId="form1" method="post" action="/branch" onsubmit="validateBranchMasterDynaValidatorForm(this)">
<div align="center"><font color="red">${requestScope.sms}</font></div>

<fieldset>

<legend><bean:message key="branch.master" /></legend>

  <table width="100%">
 
    <tr>
      <td><bean:message key="branch.code" /><span><font color="red">*</font></span></td>
      
      <td><html:text property="branchCode" styleClass="text" styleId="branchCode" maxlength="10"/></td>
<!--      <td width="13%"><span><font color="red"><html:errors property="branchCode"  /></font></span></td>-->
      <td><bean:message key="branch.desc" /><span><font color="red">*</font></span></td>
      
      <td><html:text property="branchDesc" styleClass="text" styleId="branchDesc"  /></td>
<!--      <td width="15%"><span><font color="red"><html:errors property="branchDesc" /></font></span></td>-->
      <td><bean:message key="branch.active" /></td>
      <td><input type="checkbox" name="activebox" id="activebox" value="YES" checked="checked" /></td>
      
  </tr>
  <tr><td>&nbsp; </td></tr>
  
			<tr align="left">
			
			<td><button type="button"  name="method" id="save" class="topformbutton2" onclick="savebySubmit();"><bean:message key="button.save"/></button>
			<button type="button"  name="method" id="update" class="topformbutton2" style="display: none;" onclick="UpdatebySubmit();"><bean:message key="button.update"/></button>&nbsp;
			</td>
		
			</tr>
	</table>		


</fieldset>
    <fieldset>
<legend><bean:message key="branch.detail" /></legend>

<table width="100%" border="0" cellpadding="4" cellspacing="1" class="gridtd">
  <tr class="gridheader">
  	<td><div align="center">
  	  <bean:message key="branch.select" />
	  </div></td>
    <td><div align="center">
      <bean:message key="branch.code" />
    </div></td>
    <td>  <div align="center">
      <bean:message key="branch.desc" />    
    </div></td>
    <td><div align="center">
      <bean:message key="branch.active" />
    </div></td>
  </tr>
  
  <logic:present name="branchdetail">
  <logic:iterate id="subbranchdetail" name="branchdetail">
  <tr class="white1">
    <td><input type="radio" name="check" value="on" id="check" onClick="checkRadio();"/></td>
    <td><div align="right">
      ${subbranchdetail.code}<input type="hidden" name="hiddencode" id="hiddencode" value="${subbranchdetail.code}" />
    </div></td>
    <td><div align="right">
      ${subbranchdetail.desc}<input type="hidden" name="hiddendesc" id="hiddendesc" value="${subbranchdetail.desc}" />
    </div></td>
    <td><div align="right">
      ${subbranchdetail.status}
    </div></td>
  </tr>
  </logic:iterate>
  </logic:present>
</table>
</fieldset>    
           
	</html:form>		
		
  </body>
		</html:html>
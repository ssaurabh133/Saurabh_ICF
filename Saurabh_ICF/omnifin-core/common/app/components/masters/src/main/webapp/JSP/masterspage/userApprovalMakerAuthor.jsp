<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userApprovalMatrix.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
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
	<body onload="enableAnchor();checkChanges('userApprovalMatrix');">
	
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
 	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<html:form action="/userApprovalMatrixAction" styleId="userApprovalMatrix" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>
<fieldset>
<legend><bean:message key="lbl.userApprovalMatrix"/></legend>
	<table width="100%">
	<tr>
      	<td width="13%"><bean:message key="lbl.userNam" /><span><font color="red">*</font></span></td>
      	<td width="13%">
      		<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" readonly="true" value="${userList[0].userId}"/>				
      	</td>
      	<td width="13%"><bean:message key="lbl.UserRole" /><span><font color="red">*</font></span></td>
      	<td width="13%">
      		<html:select property="role" styleId="role" styleClass="text"  value="${userList[0].role}" disabled="true" onchange="userRole();">
				<html:option value="" >---Select---</html:option>
				<html:option value="U">Under Writer</html:option>
				<html:option value="P">Policy Approval</html:option>
			</html:select>
	  	</td>
	</tr>
	
	<logic:notPresent name="policy">
	<tr id=amount>
	 	<td width="13%"><bean:message key="lbl.amountFrom" /><span><font color="red">*</font></span></td>	
	 	<td width="13%"><html:text property="amountFrom" styleClass="text" style="text-align: right" styleId="amountFrom" value="${userList[0].amountFrom}" readonly="true"  onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	  	<td width="13%"><bean:message key="lbl.amountTo" /><span><font color="red">*</font></span></td>	
	 	<td width="13%"><html:text property="amountTo" styleClass="text" style="text-align: right" styleId="amountTo" value="${userList[0].amountTo}" readonly="true"  onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
    </tr>
    </logic:notPresent>
    <tr>
    	<td width="13%"><bean:message key="lbl.level" /></td>
      	<td width="13%">
      		<html:select property="level" styleId="level" styleClass="text" value="${userList[0].level}" disabled="true" style="text-align: center">
				 <html:option value="">---Select---</html:option>
				 <html:option value="1">1</html:option>
				 <html:option value="2">2</html:option>
				 <html:option value="3">3</html:option>
				 <html:option value="4">4</html:option>
				 <html:option value="5">5</html:option>
				 <html:option value="6">6</html:option>
				 <html:option value="7">7</html:option>
				 <html:option value="8">8</html:option>
				 <html:option value="9">9</html:option>
			</html:select>	
	  	</td>
	  	<td>Status</td>
	  	<td>
  			<logic:present name="A">
  				<input type=checkbox id="status" name="status" checked="checked" disabled="disabled"/>
  			</logic:present>
  			<logic:present name="X">
  				<input type=checkbox id="status" name="status" disabled="disabled"/>
  			</logic:present>
	  	</td>
	</tr>
	<tr id=forP style="display: none;">
	      <td width="13%"><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="subRuleType" styleId="subRuleType" styleClass="text" value="${userList[0].subRuleType}" disabled="true" >
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td> 
	</tr>
	<logic:present name="policy">
	<tr id=forP>
	      <td width="13%"><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="subRuleTypeP" styleId="subRuleTypeP" styleClass="text" value="${userList[0].subRuleTypeP}" disabled="true">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td> 
	</tr>
	</logic:present>
	</table>
	
</fieldset>
</html:form>
<script>
	setFramevalues("userApprovalMatrix");
</script>
</body>
</html:html>
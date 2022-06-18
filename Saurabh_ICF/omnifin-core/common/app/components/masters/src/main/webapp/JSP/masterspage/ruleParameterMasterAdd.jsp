<%@ page session="true"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.lowagie.text.Document"%>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
   		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
	<title><bean:message key="a3s.noida" />
	</title>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/ruleScript.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
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

<body onload="enableAnchor();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<html:form action="/ruleAddMasters" styleId="ruleParameterAddEdit" method="post" >
		<html:javascript formName="RuleParameterDynaValidator" />
		<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<fieldset>
			<legend>
				<bean:message key="lbl.RPMaster" />
			</legend>
			<table width="100%">
			<html:hidden  property="hiddenparameterCode" styleId="hiddenparameterCode"  value="${requestScope.list[0].hiddenparameterCode}" />
				<tr>
					<td>
						<bean:message key="lbl.parameterCode" /><span><font color="red">*</font></span>
					</td>

						<logic:present name="editVal">
						      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
						      <td><html:text property="parameterCode" styleClass="text" styleId="parameterCode" readonly="true" maxlength="50" value="${requestScope.list[0].hiddenparameterCode}" />
				     </td>
					     </logic:present>
					     <logic:notPresent name="editVal">
						     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
						     <td><html:text property="parameterCode" styleClass="text" styleId="parameterCode" maxlength="50"  onblur="caseChange('ruleParameterAddEdit','parameterCode')" />
				     </td>
				     </logic:notPresent>

					<td>
						<bean:message key="lbl.paramName" /><span><font color="red">*</font></span>
					</td>
					 	<logic:present name="editVal">
	     					<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
	      					<td><html:text property="paramName" styleClass="text" styleId="paramName" maxlength="50" value="${requestScope.list[0].paramName}"   onblur="caseChange('ruleParameterAddEdit','paramName')"/></td>
     
     					</logic:present>
     
				     <logic:notPresent name="editVal">
						     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
						      <td><html:text property="paramName" styleClass="text" styleId="paramName" maxlength="50"   onblur="caseChange('ruleParameterAddEdit','paramName')"/></td>
     
    				 </logic:notPresent>
				</tr>
				<tr>
					<td>
						<bean:message key="lbl.sourceTable" /><span><font color="red">*</font></span>
					</td>

					<logic:present name="editVal">
				      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
				      <td>
				      <html:text property="sourceTable" styleClass="text" styleId="sourceTable" readonly="true" maxlength="50" readonly="true" value="${requestScope.list[0].sourceTable}" />
				      <input type="button" class="lovbutton" id="lbxTablesSel" onclick="openLOVCommon(269,'ruleParameterAddEdit','lbxTables','','sourceColumn', 'lbxColumn','','','sourceTable')" value=" " tabindex="3" name="dealButton">
				      <input type="hidden" name="lbxTables" id="lbxTables" value="${requestScope.list[0].sourceTable}"  />
     			     </td>
				     </logic:present>
				     <logic:notPresent name="editVal">
				     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
				     <td><html:text property="sourceTable" styleClass="text" styleId="sourceTable" maxlength="50" readonly="true"/>
				     <input type="button" class="lovbutton" id="lbxTablesSel" value=" " onclick="openLOVCommon(269,'ruleParameterAddEdit','lbxTables','','sourceColumn', 'lbxColumn','','','sourceTable')" tabindex="3" name="dealButton">
				     <input type="hidden" name="lbxTables" id="lbxTables" value=""  />
				     </td>
				     </logic:notPresent>

					<td>
						<bean:message key="lbl.sourceColumn" /><span><font color="red">*</font></span>
					</td>
					 <logic:present name="editVal">
						     <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
						      <td><html:text property="sourceColumn" styleClass="text" styleId="sourceColumn" maxlength="3" readonly="true" value="${requestScope.list[0].sourceColumn}"/>
      				          <input type="button" class="lovbutton" id="lbxColumnSel" onclick="openLOVCommon(270,'ruleParameterAddEdit','lbxColumn','sourceTable','lbxColumnSel','lbxTables','Please Select Table First !!!','','sourceColumn')" tabindex="3" name="dealButton">
				     			<input type="hidden" name="lbxColumn" id="lbxColumn" value="${requestScope.list[0].sourceColumn}"  />
					</td>
     
     				</logic:present>
     
			     <logic:notPresent name="editVal">
							     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
							      <td><html:text property="sourceColumn" styleClass="text" styleId="sourceColumn" readonly="true" maxlength="3"/>
							      <input type="button" class="lovbutton" id="lbxColumnSel" onclick="openLOVCommon(270,'ruleParameterAddEdit','lbxColumn','sourceTable','lbxColumnSel','lbxTables','Please Select Table First !!!','','sourceColumn')" tabindex="3" name="dealButton">
				     			<input type="hidden" name="lbxColumn" id="lbxColumn"/>
			     </logic:notPresent>
				</tr>
				<tr>


      
      				<td>
						<bean:message key="lbl.parameterStatus" />
					</td>
					<td>
						<logic:notPresent name="editVal">
						<input type="checkbox" name="parameterStatus" id="parameterStatus" checked="checked"/>
						</logic:notPresent>
					
					    <logic:present name="editVal">
						<logic:equal value="Active" name="status">
			            <input type="checkbox" name="parameterStatus" id="parameterStatus" checked="checked"/>
       				 	</logic:equal>
      					<logic:notEqual value="Active" name="status">
         			 	<input type="checkbox" name="parameterStatus" id="parameterStatus"/>
      					</logic:notEqual>
      					</logic:present>
      				</td>

				</tr>
				<tr>
					<td>

					    <logic:present name="editVal">
					      	<button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return editRule();" class="topformbutton2" ><bean:message key="button.save" /></button>
					   </logic:present>
					   
					   <logic:present name="save">
					     	 <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
					    	<button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRule();" class="topformbutton2" ><bean:message key="button.save" /></button>
					   </logic:present>
					</td>
				</tr>

			</table>


		</fieldset>


	</html:form>
	
<logic:present name="msg">
<script type="text/javascript">

    
		if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
			document.getElementById("parameterCode").value="";
			document.getElementById("ruleParameterAddEdit").action="ruleSearchMasters.do?method=initialruleMaster";
			document.getElementById("ruleParameterAddEdit").submit();
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
			document.getElementById("ruleParameterAddEdit").action="ruleSearchMasters.do?method=initialruleMaster";
			document.getElementById("ruleParameterAddEdit").submit();
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}

</script>
</logic:present>
</body>
</html:html>
<%@ page session="true"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
	<title><bean:message key="a3s.noida" />
	</title>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/scoringScript.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
</head>

<body onload="enableAnchor();">
	<html:form action="/scoreCardAdd" styleId="scoreCardMasterAdd" method="post">
			<html:javascript formName="ScoreCardMasterDynaValidatorForm" />
			
			<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
			<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
		<fieldset>
			<legend>
				<bean:message key="lbl.scoringCard" />
			</legend>
			<table width="100%">
			<html:hidden  property="hiddenScoreCode" styleId="hiddenScoreCode"  value="${requestScope.list[0].hiddenScoreCode}" />
				<tr>
					<td>
						<bean:message key="lbl.cardDesc" /><span><font color="red">*</font></span>
					</td>

					<logic:present name="editVal">
				      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
				      <td><html:text property="scoreCardDesc" styleClass="text" styleId="scoreCardDesc" maxlength="50" value="${requestScope.list[0].scoreCardDesc}"  onblur="caseChange('scoreCardMasterAdd','scoreCardDesc')"/>
				     </td>
				     </logic:present>
				     <logic:notPresent name="editVal">
				     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
				     <td><html:text property="scoreCardDesc" styleClass="text" styleId="scoreCardDesc" maxlength="50"  onblur="caseChange('scoreCardMasterAdd','scoreCardDesc')"/>
				     </td>
				     </logic:notPresent>

					<td>
						<bean:message key="lbl.productDesc" /><span><font color="red">*</font></span>
					</td>
					 <logic:present name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
      <td><html:text property="productId" styleClass="text" styleId="productId" readonly="true" maxlength="3" value="${requestScope.list[0].productId}"/>
      <input type="button" class="lovbutton" id="lbxProduct" onclick="openLOVCommon(89,'scoreCardMasterAdd','productId','','','','','','lbxProductID')" value=" " tabindex="3" name="dealButton">
      <input type="hidden" name="lbxProductID" id="lbxProductID" value="${requestScope.list[0].lbxProductID}" /></td>
     
     </logic:present>
     
     <logic:notPresent name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
      <td><html:text property="productId" styleClass="text" styleId="productId" readonly="true" maxlength="3"/>
      <input type="button" class="lovbutton" id="lbxProduct" onclick="openLOVCommon(89,'scoreCardMasterAdd','productId','','','','','','lbxProductID')" value=" " tabindex="3" name="dealButton">
      <input type="hidden" name="lbxProductID" id="lbxProductID" value="" /></td>
     
     </logic:notPresent>
				</tr>
				<tr>
					<td>
						<bean:message key="lbl.cardStatus" />
					</td>
					      <td><logic:equal value="Active" name="status">
         <input type="checkbox" name="cardStatus" id="cardStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="cardStatus" id="cardStatus"  />
      </logic:notEqual></td>

				</tr>
				<tr>
					<td>

						    <logic:present name="editVal">
						      <button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return editCardScore();" class="topformbutton2" ><bean:message key="button.save" /></button>
						   </logic:present>
						   
						   <logic:present name="save">
						      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
						    <button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return saveCardScore();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
			document.getElementById("scoreCardDesc").value="";
			document.getElementById("productId").value="";
			document.getElementById("scoreCardMasterAdd").action="scoreCardMasters.do?method=scoringCardList";
			document.getElementById("scoreCardMasterAdd").submit();
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
			document.getElementById("scoreCardMasterAdd").action="scoreCardMasters.do?method=scoringCardList";
			document.getElementById("scoreCardMasterAdd").submit();
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
			document.getElementById("scoreCardMasterAdd").action="scoreCardMasters.do?method=scoringCardList";
			document.getElementById("scoreCardMasterAdd").submit();
	}
	

</script>
</logic:present>
</body>
</html:html>
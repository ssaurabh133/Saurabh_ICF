<%@ page language="java"%>
<%@ page session="true"%>
<%@ page import="java.util.Date"%>
<%@page import="com.lowagie.text.Document"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/scoringScript.js"></script>	
			
</head>

<body onload="enableAnchor();">
	<html:form action="/scoringMastersAdd" styleId="scoreMasterAdd" method="post">
		<html:javascript formName="ScoreMasterDynaValidatorForm" />
		<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<fieldset>
			<legend>
				<bean:message key="lbl.scoringCode" />
			</legend>
			<table width="100%">
			<html:hidden  property="hiddenScoreCode" styleId="hiddenScoreCode"  value="${list[0].hiddenScoreCode}" />
				<tr>
					<td>
						<bean:message key="lbl.scoringCode" /><span><font color="red">*</font></span>
					</td>

						<logic:present name="editVal">
						      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
						      <td><html:text property="scoreCode" styleClass="text" styleId="scoreCode" readonly="true" maxlength="20" value="${list[0].hiddenScoreCode}" />
				     </td>
					     </logic:present>
					     <logic:notPresent name="editVal">
						     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
						     <td><html:text property="scoreCode" styleClass="text" styleId="scoreCode" maxlength="20"  onblur="caseChange('scoreMasterAdd','scoreCode')" />
				     </td>
				     </logic:notPresent>

					<td>
						<bean:message key="lbl.scoringDesc" /><span><font color="red">*</font></span>
					</td>
					 	<logic:present name="editVal">
	     					<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
	      					<td><html:text property="scoreDesc" styleClass="text" styleId="scoreDesc" maxlength="50" value="${list[0].scoreDesc}"   onblur="caseChange('scoreMasterAdd','scoreDesc')"/></td>
     
     					</logic:present>
     
				     <logic:notPresent name="editVal">
						     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
						      <td><html:text property="scoreDesc" styleClass="text" styleId="scoreDesc" maxlength="50" onblur="caseChange('scoreMasterAdd','scoreDesc')"/></td>
     
    				 </logic:notPresent>
				</tr>
				<tr>
					<td>
						<bean:message key="lbl.sourceTable" /><span><font color="red">*</font></span>
					</td>

					<logic:present name="editVal">
				      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
				      <td>
				      <html:text property="sourceTable" styleClass="text" styleId="sourceTable" readonly="true" maxlength="50" tabindex="-1" readonly="true" value="${list[0].sourceTable}" />
				      <input type="button" class="lovbutton" id="lbxTablesSel" onclick="openLOVCommon(234,'scoreMasterAdd','sourceTable','','', '','','ScoreColumn','lbxTables')" value=" " name="dealButton">
				      <input type="hidden" name="lbxTables" id="lbxTables" value="${list[0].sourceTable}"  />
     			     </td>
				     </logic:present>
				     <logic:notPresent name="editVal">
				     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
				     <td><html:text property="sourceTable" styleClass="text" tabindex="-1" styleId="sourceTable" maxlength="50" readonly="true"/>
				     <input type="button" class="lovbutton" id="lbxTablesSel" value=" " onclick="openLOVCommon(234,'scoreMasterAdd','sourceTable','','', '','','ScoreColumn','lbxTables')" name="dealButton">
				     <input type="hidden" name="lbxTables" id="lbxTables" value=""  />
				     </td>
				     </logic:notPresent>

					<td>
						<bean:message key="lbl.sourceColumn" /><span><font color="red">*</font></span>
					</td>
					 <logic:present name="editVal">
						     <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
						      <td><html:text property="sourceColumn" styleClass="text" tabindex="-1" styleId="sourceColumn" maxlength="50" readonly="true" value="${list[0].sourceColumn}"/>
      				          <input type="button" class="lovbutton" id="lbxColumnSel" onclick="openLOVCommon(235,'scoreMasterAdd','sourceColumn','lbxTables','lbxColumn', 'sourceTable','Please Select Table First !!!','ScoreChecked','lbxColumn')" name="dealButton">
				     			<input type="hidden" name="lbxColumn" id="lbxColumn" value="${list[0].sourceColumn}"  />
					</td>
     
     				</logic:present>
     
			     <logic:notPresent name="editVal">
							     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
							      <td><html:text property="sourceColumn" styleClass="text" tabindex="-1" styleId="sourceColumn" maxlength="50" readonly="true"/>
							      <input type="button" class="lovbutton" id="lbxColumnSel" onclick="openLOVCommon(235,'scoreMasterAdd','sourceColumn','lbxTables','lbxColumn', 'sourceTable','Please Select Table First !!!','ScoreChecked','lbxColumn')" name="dealButton">
				     			<input type="hidden" name="lbxColumn" id="lbxColumn"/>
			     </logic:notPresent>
				</tr>
				<tr>
					<td>
						<bean:message key="lbl.dataType" />
					</td>
					<td>
					<logic:present name="list">
						<logic:iterate name="list" id="subList">
							<logic:equal name="subList" property="dataType" value="N">
								<input type="radio" name="dataType" id="dataType1" checked="checked" onclick="return false;" value="N"/><bean:message key="lbl.numeric" />
				        		<input type="radio" name="dataType" id="dataType2" onclick="return false;" value="A"/><bean:message key="lbl.alphabetic" />
							</logic:equal>
							
							<logic:equal name="subList" property="dataType" value="A">
								<input type="radio" name="dataType" id="dataType1"  onclick="return false;" value="N"/><bean:message key="lbl.numeric" />
				        		<input type="radio" name="dataType" id="dataType2" checked="checked" onclick="return false;" value="A"/><bean:message key="lbl.alphabetic" />
							</logic:equal>
						</logic:iterate>
					</logic:present>
					
					<logic:notPresent name="list">
					
						<input type="radio" name="dataType" id="dataType1" onclick="return false;" value="N"/><bean:message key="lbl.numeric" />
				        <input type="radio" name="dataType" id="dataType2" onclick="return false;" value="A"/><bean:message key="lbl.alphabetic" />
					
					</logic:notPresent>
					</td>					

      
      				<td>
						<bean:message key="lbl.scoringStatus" />
					</td>
					<td>
						<logic:equal value="Active" name="status">
			         		<input type="checkbox" name="scoreStatus" id="scoreStatus" checked="checked"/>
       				 	</logic:equal>
      					<logic:notEqual value="Active" name="status">
         			 		<input type="checkbox" name="scoreStatus" id="scoreStatus"/>
      					</logic:notEqual>
      				</td>

				</tr>
				<tr>
					<td>

					    <logic:present name="editVal">
					      	<button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return editScore();" class="topformbutton2" ><bean:message key="button.save" /></button>
					   </logic:present>
					   
					   <logic:present name="save">
					     	 <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
					    	<button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveScore();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
			document.getElementById("scoreCode").value="";
			document.getElementById("scoreDesc").value="";
			document.getElementById("scoreMasterAdd").action="scoringSearchMasters.do?method=scoringMasterList";
			document.getElementById("scoreMasterAdd").submit();
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
			document.getElementById("scoreMasterAdd").action="scoringSearchMasters.do?method=scoringMasterList";
			document.getElementById("scoreMasterAdd").submit();
	}
		else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}

</script>
</logic:present>
</body>
</html:html>
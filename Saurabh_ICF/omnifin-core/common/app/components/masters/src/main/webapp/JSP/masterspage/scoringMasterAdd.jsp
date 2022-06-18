<!--Author Name : Arun Kumar MIshra-->
<!--Date of Creation : 01-Nov-2012-->
<!--Purpose  : Scoring Master Search-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/scoringMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

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

<body onload="enableAnchor();document.getElementById('scoringMasterAdd').scoreingDesc.focus();init_fields();">
<html:form  styleId="scoringMasterAdd" method="post" action="/scoringMasterDispatch">
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<input type="hidden" name="psize" id="psize"/>
<fieldset>
<legend><bean:message key="lbl.scoringMaster" /></legend> 
<logic:notPresent name="edit">
 <table width="100%">
      <tr>
     <td><bean:message key="lbl.scoringDesc" /><font color="red">*</font></td>
     <td><html:text property="scoreingDesc" styleId="scoreingDesc" style="text" maxlength="50"/></td>
     <td ><bean:message key="lbl.product2" /><font color="red">*</font></td>
      <td >
      <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
      <input type="hidden" id="modifyRecord" name="modifyRecord"	value="I" />
	  <html:text property="product" styleId="product" styleClass="text" readonly="true" tabindex="-1"/>
	  <input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(89,'scoringMasterAdd','product','','','','','','lbxProductID')" value=" "  name="dealButton">
	 <html:hidden property="lbxProductID" styleId="lbxProductID" styleClass="text"></html:hidden>
	 </td>
	 <td ><bean:message key="lbl.schemeLOV"/><font color="red">*</font></td>
       <td>
       <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/> 
        <html:hidden property="lbxSchemeId" styleId="lbxSchemeId"  />
        <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(287,'scoringMasterAdd','scheme','product','lbxSchemeId', 'lbxProductID','Please firstly select Product then Scheme','','lbxSchemeId');" value=" " styleClass="lovbutton"> </html:button>
       </td>
       <td><bean:message key="lbl.status"/></td>
       <td><input type="checkbox" name="recStatus" id="recStatus" checked="checked"/></td>  
  </tr>
</table>
<br/><br/>
<fieldset>
<table width="100%" border="0" cellpadding="0" cellspacing="1">
<tr>
	<td class="gridtd">
		<table width="100%" border="0" cellpadding="4" cellspacing="1" id="gridTable">
			<tr class="white2" >
			   <td>
					<input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked()"/>
				</td>
				<td >
					<strong><bean:message key="lbl.scoreParem" /> </strong>
				</td>
				
				<td >
					<b><bean:message key="lbl.weightage" /> <br> </b>
				</td>
				<td >
					<b><bean:message key="lbl.default" /> <br> </b>
				</td>
												
		</tr>
		<tr class="white1" >
			<td>
			<input type="checkbox" name="chk" id="chk1"/>
			</td>
			<td>
			<input type="text" class="text" name="scoringParam" id="scoringParam1" readonly="readonly" tabindex="-1"/>
			<input type="button" class="lovbutton" id="scoringParamLOV" onclick="openLOVCommon(475,'scoringMasterAdd','scoringParam1','','','','','','lbxScoringParam1')" value=" " tabindex="26" name="dealButton">
			<input type="hidden" class="text" name="lbxScoringParam" id="lbxScoringParam1"/>
			<input type="hidden" class="text" name="lbxScoringParamHID1" id="lbxScoringParamHID"/>
			</td>
			<td><input type="text" class="text" name="weightage" id="weightage1"  onkeypress="return numbersonlyForScoring(event);" maxlength="3"/></td>
			<td><input type="text" class="text" name="defaultValue" id="defaultValue1"  onkeypress="return numbersonlyForScoring(event);" maxlength="1"/></td>
			 </tr>
          </table>
	</td>
</tr>
<tr><td>
<button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="addROW();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRow();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
 </td>

</tr>
</table>
</fieldset>
 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveScoringMaster();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>


</logic:notPresent>
<logic:present name="edit">
<table width="100%">
      <tr>
     <td><bean:message key="lbl.scoringDesc" /><font color="red">*</font></td>
     <td>
     <html:hidden property="scoringId" styleId="scoringId"  value="${scoringHeaderList[0].scoringId}"/>
     <html:text property="scoreingDesc" styleId="scoreingDesc" style="text" value="${scoringHeaderList[0].scoreingDesc}" maxlength="50"/></td>
     <td ><bean:message key="lbl.product2" /><font color="red">*</font></td>
      <td >
      <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
      <input type="hidden" id="modifyRecord" name="modifyRecord"	value="M" />
	  <html:text property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1" value="${scoringHeaderList[0].product}"/>
	  <input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(89,'scoringMasterAdd','product','','','','','','lbxProductID')" value=" "  name="dealButton">
	 <html:hidden property="lbxProductID" styleId="lbxProductID" styleClass="text" value="${scoringHeaderList[0].lbxProductID}"></html:hidden>
	 </td>
	 <td ><bean:message key="lbl.schemeLOV"/><font color="red">*</font></td>
       <td>
       <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1" value="${scoringHeaderList[0].scheme}"/> 
        <html:hidden property="lbxSchemeId" styleId="lbxSchemeId" value="${scoringHeaderList[0].lbxSchemeId}" />
        <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(287,'scoringMasterAdd','scheme','product','lbxSchemeId', 'lbxProductID','Please firstly select Product then Scheme','','lbxSchemeId');" value=" " styleClass="lovbutton"> </html:button>
       </td>
        <td><bean:message key="lbl.status"/></td>
       <td>
       <logic:equal value="Active" name="status">
        <input type="checkbox" name="recStatus" id="recStatus" checked="checked"/>
       </logic:equal>
      <logic:notEqual value="Active" name="status">
      <input type="checkbox" name="recStatus" id="recStatus" />
      </logic:notEqual>
       </td>
  </tr>
</table>
<br/><br/>
<fieldset>
<table width="100%" border="0" cellpadding="0" cellspacing="1">
<tr>
	<td class="gridtd">
		<table width="100%" border="0" cellpadding="4" cellspacing="1" id="gridTable">
			<tr class="white2" >
			 <td>
					<input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked()"/>
				</td>
				<td >
					<strong><bean:message key="lbl.scoreParem" /> </strong>
				</td>
				
				<td >
					<b><bean:message key="lbl.weightage" /> <br> </b>
				</td>
				<td >
					<b><bean:message key="lbl.default" /> <br> </b>
				</td>
												
		</tr>
		<logic:present name="scoringDtlList">
		<logic:iterate id="objScoringDtlList" name="scoringDtlList" indexId="count">
		<tr class="white1" >
		<td>
		<input type="checkbox" name="chk" id="chk<%=count.intValue()+1 %>"/>
		</td>
			<td>
			<input type="text" class="text" name="scoringParam" id="scoringParam<%=count.intValue()+1 %>" value="${objScoringDtlList.scoringParamStr}" readonly="readonly" tabindex="-1"/>
			<input type="button" class="lovbutton" id="scoringParamLOV" onclick="openLOVCommon(475,'scoringMasterAdd','scoringParam<%=count.intValue()+1 %>','','','','','','lbxScoringParam<%=count.intValue()+1 %>')" value=" " tabindex="26" name="dealButton">
			<input type="hidden" class="text" name="lbxScoringParam" id="lbxScoringParam<%=count.intValue()+1 %>" value="${objScoringDtlList.scoringParamCode}"/>
			<input type="hidden" class="text" name="lbxScoringParamHID<%=count.intValue()+1 %>" id="lbxScoringParamHID" value="${objScoringDtlList.scoringParamCode}"/>
			</td>
			<td><input type="text" class="text" name="weightage" id="weightage<%=count.intValue()+1 %>" value="${objScoringDtlList.weightageStr}" onkeypress="return numbersonlyForScoring(event);" maxlength="3" /></td>
			<td><input type="text" class="text" name="defaultValue" id="defaultValue<%=count.intValue()+1 %>" value="${objScoringDtlList.defaultValueStr}"onkeypress="return numbersonlyForScoring(event);" maxlength="1"/></td>
			 </tr>
		</logic:iterate>
		</logic:present>
		
          </table>
	</td>
</tr>
<tr><td>
<button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="addROW();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRow();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
 </td>

</tr>
</table>
</fieldset>
 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveScoringMaster();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
 <button type="button" class="topformbutton4" name="openpop"  id="openpop"  onclick=" openParameterValue();" title="Alt+P" accesskey="P" >Add <span class='underLine'>P</span>arameter Values</button>
</logic:present>
</fieldset>

</html:form>
	<logic:present name="sms">
		<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("scoringMasterAdd").action="scoringMasterBehind.do";
	    document.getElementById("scoringMasterAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("scoringMasterAdd").action="scoringMasterBehind.do";
	    document.getElementById("scoringMasterAdd").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
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
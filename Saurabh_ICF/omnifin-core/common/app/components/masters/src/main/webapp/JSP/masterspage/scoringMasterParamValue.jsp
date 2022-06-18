<!--Author Name : Arun Kumar MIshra-->
<!--Date of Creation : 02-Nov-2012-->
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

<body onload="enableAnchor();init_fields();">
<html:form  styleId="scoringMasterAddParamVal" method="post" action="/scoringMasterDispatch">
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<input type="hidden" name="psize" id="psize"/>

<input type="hidden" name="checkIdVal" id="checkIdVal"/>
 <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />

<html:hidden property="scoringId" styleId="scoringId"  value="${requestScope.scoringId}"/>
<fieldset>
<legend><bean:message key="lbl.scoringMasterParamValue" /></legend> 
<logic:notPresent name="paramValueList">
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
					<b><bean:message key="lbl.from" /> <br> </b>
				</td>
				<td >
					<b><bean:message key="lbl.to" /> <br> </b>
				</td>
				<td >
					<b><bean:message key="lbl.charValue" /> <br> </b>
				</td>
				<td >
					<b><bean:message key="lbl.score" /> <br> </b>
				</td>								
		</tr>
		<tr class="white1" >
			<td>
			<input type="checkbox" name="chk" id="chk1"/>
			</td>
			<td>
			<input type="text" class="text" name="scoringParam" id="scoringParam1" readonly="readonly" tabindex="-1"/>
			<input type="button" class="lovbutton" id="scoringParamLOV" onclick="checkId('1');openLOVCommon(474,'scoringMasterAddParamVal','scoringParam1','scoringId','lbxScoringParam1','scoringId','Scoring Id Can not be null','enaDisIntStringField','lbxScoringParam1','hidDataType1','');" value=" "  name="dealButton">
			<input type="hidden" class="text" name="lbxScoringParam" id="lbxScoringParam1"/>
			<input type="hidden" class="text" name="lbxScoringParamHID1" id="lbxScoringParamHID"/>
			 <input type="hidden" name="hidDataType" id="hidDataType1"/>
			</td>
			<td><input type="text" class="text" name="from" id="from1" onkeypress="return numbersonlyForScoring(event);" maxlength="3" readonly="readonly"/></td>
			<td><input type="text" class="text" name="to" id="to1" onkeypress="return numbersonlyForScoring(event);" maxlength="1" readonly="readonly"/></td>
			<td><input type="text" class="text" name="charValue" id="charValue1" maxlength="50" readonly="readonly"/></td>
			<td><input type="text" class="text" name="score" id="score1"  onkeypress="return numbersonlyForScoring(event);" maxlength="5"/></td>
			 </tr>
          </table>
	</td>
</tr>
<tr><td>
<button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="addRowParamValue()();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRow();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
 </td>

</tr>
</table>
</fieldset>
</logic:notPresent>
<logic:present name="paramValueList">

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
					<b><bean:message key="lbl.from" /> <br> </b>
				</td>
				<td >
					<b><bean:message key="lbl.to" /> <br> </b>
				</td>
				<td >
					<b><bean:message key="lbl.charValue" /> <br> </b>
				</td>
				<td >
					<b><bean:message key="lbl.score" /> <br> </b>
				</td>								
		</tr>
		<logic:present name="paramValueList">
		<logic:iterate id="objScoringDtlList" name="paramValueList" indexId="count">
		<tr class="white1" >
		<td>
		<input type="checkbox" name="chk" id="chk<%=count.intValue()+1 %>"/>
		</td>
			<td>
			<input type="text" class="text" name="scoringParam" id="scoringParam<%=count.intValue()+1 %>" value="${objScoringDtlList.scoringParamStr}" readonly="readonly" tabindex="-1"/>
			<input type="button" class="lovbutton" id="scoringParamLOV" onclick="checkId('<%=count.intValue()+1 %>');openLOVCommon(474,'scoringMasterAddParamVal','scoringParam<%=count.intValue()+1 %>','scoringId','lbxScoringParam<%=count.intValue()+1 %>','scoringId','Scoring Id Can not be null','enaDisIntStringField','lbxScoringParam<%=count.intValue()+1 %>','hidDataType<%=count.intValue()+1 %>','');"  value=" "  name="dealButton">
			<input type="hidden" class="text" name="lbxScoringParam" id="lbxScoringParam<%=count.intValue()+1 %>" value="${objScoringDtlList.scoringParamCode}"/>
			<input type="hidden" class="text" name="lbxScoringParamHID<%=count.intValue()+1 %>" id="lbxScoringParamHID" value="${objScoringDtlList.scoringParamCode}"/>
			<input type="hidden" name="hidDataType" id="hidDataType<%=count.intValue()+1 %>" />
			</td>
			<td>
			<logic:equal value="INT" name="objScoringDtlList" property="dataType">
			<input type="text" class="text" name="from" id="from<%=count.intValue()+1 %>" onkeypress="return numbersonlyForScoring(event);" value="${objScoringDtlList.fromStr}" maxlength="21"/>
			</logic:equal>
			<logic:notEqual value="INT" name="objScoringDtlList" property="dataType">
			<input type="text" class="text" name="from" id="from<%=count.intValue()+1 %>" onkeypress="return numbersonlyForScoring(event);" value="" readonly="readonly" maxlength="21"/>
			</logic:notEqual>
			</td>
			<td>
			<logic:equal value="INT" name="objScoringDtlList" property="dataType">
			<input type="text" class="text" name="to" id="to<%=count.intValue()+1 %>" onkeypress="return numbersonlyForScoring(event);" value="${objScoringDtlList.toStr}" maxlength="21"/>
			</logic:equal>
			<logic:notEqual value="INT" name="objScoringDtlList" property="dataType">
			<input type="text" class="text" name="to" id="to<%=count.intValue()+1 %>" onkeypress="return numbersonlyForScoring(event);" value="" readonly="readonly" maxlength="21"/>
			</logic:notEqual>
			</td>
			<td>
			<logic:equal value="INT" name="objScoringDtlList" property="dataType">
			<input type="text" class="text" name="charValue" id="charValue<%=count.intValue()+1 %>" value="" readonly="readonly" maxlength="50"/>
			</logic:equal>
			<logic:notEqual value="INT" name="objScoringDtlList" property="dataType">
			<input type="text" class="text" name="charValue" id="charValue<%=count.intValue()+1 %>" value="${objScoringDtlList.charValueStr}" maxlength="50"/>
			</logic:notEqual>
			</td>
			<td><input type="text" class="text" name="score" id="score<%=count.intValue()+1 %>"  onkeypress="return numbersonlyForScoring(event);" value="${objScoringDtlList.scoreStr}" maxlength="5"/></td>
			 </tr>
		</logic:iterate>
		</logic:present>
		
          </table>
	</td>
</tr>
<tr><td>
<button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="addRowParamValue();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRow();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
 </td>

</tr>
</table>
</fieldset>
 

</logic:present>
<button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveParamValue();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
</fieldset>

</html:form>
	<logic:present name="sms">
		<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		//document.getElementById("scoringMasterAdd").action="scoringMasterBehind.do";
	   //  document.getElementById("scoringMasterAdd").submit();
	   window.close();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		//document.getElementById("scoringMasterAdd").action="scoringMasterBehind.do";
	    //document.getElementById("scoringMasterAdd").submit();
	    window.close();
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
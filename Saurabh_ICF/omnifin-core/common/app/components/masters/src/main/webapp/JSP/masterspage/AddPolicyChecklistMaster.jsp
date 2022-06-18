<%--	Created By Sajog     --%>


<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>    
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/pcdMaster.js"></script> 	
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
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
  
  <body onload="enableAnchor();levelDis();">
  <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <html:form styleId="policyChecklistAdd" action="/policyChecklistMasterAdd" >
 <html:javascript formName="PolicyChecklistDefDynaValidatorForm"/>
 <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
 <input type="hidden" name="approvalpmstSize" value="<%=request.getAttribute("pmstSize")%>" id="approvalpmstSize" />
  <fieldset>
  <legend><bean:message key="lbl.PCDMaster"/></legend>
  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="4" cellspacing="2">
 
   <logic:notPresent name="editVal">
    
  <tr>

    <td><bean:message key="lbl.product"/><span><font color="red">*</font></span></td>
    <td>
      <html:text property="product" styleId="product"  styleClass="text" readonly="true" tabindex="-1" value="${list[0].product}"/>
       <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${list[0].lbxProductID}" />
  	  <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(89,'policyChecklistAdd','product','','scheme', 'lbxSchemeID','','','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>
  	</td>

    <td><bean:message key="lbl.scheme"/><span><font color="red">*</font></span></td>
    <td><html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"  value="${list[0].scheme}"/>
    <html:hidden  property="lbxSchemeID" styleId="lbxSchemeID" value="${list[0].lbxSchemeID}"/>
  	 <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(75,'policyChecklistAdd','scheme','product','lbxSchemeID', 'lbxProductID','Select Product Code','','lbxSchemeID');" value=" " styleClass="lovbutton"> </html:button>
  </tr>
  </logic:notPresent>
  
  <logic:present name="editVal">
  
   <tr>

    <td><bean:message key="lbl.product"/><span><font color="red">*</font></span></td>
      <td>
      <html:text property="product" styleId="product"  styleClass="text" readonly="true" tabindex="-1" value="${list[0].product}"/>
       <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${list[0].lbxProductID}" />
  	</td>

    <td><bean:message key="lbl.scheme"/><span><font color="red">*</font></span></td>
    <td><html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"  value="${list[0].scheme}"/>
    <html:hidden  property="lbxSchemeID" styleId="lbxSchemeID" value="${list[0].lbxSchemeID}"/>
 
  </tr>
  
  </logic:present>
  
  <tr>
    <logic:notPresent name="editVal">
    <td><bean:message key="lbl.PCDStage"/><span><font color="red">*</font></span></td>
    <td><html:text property="stage" styleId="stage"  styleClass="text" readonly="true" value="${list[0].stage}" tabindex="-1"/>
  	<html:button property="schemeButton" styleId="schemeButton" value=" " onclick="openLOVCommon(251,'policyChecklistAdd','lbxPCDStage','','', '','','','stage');" styleClass="lovbutton"></html:button>
  	<html:hidden  property="lbxPCDStage" styleId="lbxPCDStage"  value="${list[0].lbxPCDStage}"/>
  	</td>
  	</logic:notPresent>
  	<logic:present name="editVal">
  	<td><bean:message key="lbl.PCDStage"/><span><font color="red">*</font></span></td>
    <td><html:text property="stage" styleId="stage"  styleClass="text" readonly="true" value="${list[0].stage}" tabindex="-1"/>
  	<html:hidden  property="lbxPCDStage" styleId="lbxPCDStage"  value="${list[0].lbxPCDStage}"/>
  	</td>
  	</logic:present>
  	 
  	 <td><bean:message key="lbl.PCDStatus"/><input type="hidden" id="statusCheck" value="${list[0].status}"/></td>
  	 
    <td><input type="checkbox" name="status" id="status" class="text" checked="checked"/>
    <script type="text/javascript">
  	 	if(document.getElementById("statusCheck").value == "on"){
  	 		document.getElementById("status").checked = true;
  	 	}else if(document.getElementById("statusCheck").value == "off"){
  	 		document.getElementById("status").checked = false;
  	 	}
  	 </script>
 	 </td>
  </tr>
 </table></td>
  </tr></table>
  </fieldset>
  
  <fieldset>
<legend><bean:message key="lbl.PCDRule"/></legend>
	<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
		<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			<input type="hidden" name="countVal" id="countVal"/>
			<input type="hidden" name="psize" id="psize" value="1"/>
			<input type="hidden" name="pcheck" id="pcheck" />
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			 <tr class="white2">   
			 	 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" /></td>
				 <td ><b><bean:message key="lbl.PCDRuleCode" /></b></td>
    			 <td ><b><bean:message key="lbl.PCDRuleDes" /></b></td>
        		 <td ><b><bean:message key="lbl.PCDAction" /></b></td>
        		 <td ><b><bean:message key="lbl.PCDAppLevel" /></b></td>
      		</tr>
      		

<logic:present name="list">
		<input type="hidden" name="rowCounter" id="rowCounter" value="${list[0].totalRecordSize}"/>	
	
	<logic:iterate name="list" id="sublist" indexId="counter">
	
	<tr class="white1">
		<td  ><input type="checkbox" name="chk" id="chk<%=counter.intValue()+1%>" checked="checked"/>
		
			<input type="hidden" name="pcdCheckId" id="pcdCheckId<%=counter.intValue()+1%>" value="${sublist.pcdCheckId}"/></td>
<!--		<td><input type="text" name="docId" id="docId<%=counter.intValue()+1%>"  value="${sublist.docId}" readonly="readonly" class="text"/></td>-->
		<td><input type="text" name="ruleCode" id="ruleCode<%=counter.intValue()+1%>"  value="${sublist.ruleCode}" readonly="readonly" class="text"/>
		<html:hidden  property="lbxDocId" styleId="lbxDocId"  value="${sublist.lbxDocId}" />

		<html:button property="docButton" styleId="docButton" onclick="openLOVCommon(250,'policyChecklistDef','ruleCode${counter+1 }','','', '','','','ruleDesc${counter+1 }');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button></td>
		
		<td ><input type="text" name="ruleDesc" id="ruleDesc<%=counter.intValue()+1%>" value="${sublist.ruleDesc}" readonly="readonly" class="text" />
		<html:hidden  property="lbxPCD" styleId="lbxPCD"  value="" /></td>

		
		<td ><html:select property="action" styleClass="action${counter+1}" styleId="action${counter+1}" value="${sublist.action}" onchange="levelDis();">
							<html:option value="D">DEVIATION</html:option>
							<html:option value="S">STOP</html:option>
						
		
		
		</html:select></td>	
		<td ><html:select styleClass="appLevel${counter+1}" property="appLevel" styleId="appLevel${counter+1}" value="${sublist.appLevel}" onchange="levelDis();">
							<logic:present name="pmstSize">   				
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=0;i<=j;i++) { %>
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>
		<%} %>
		</logic:present>
		
		
		</html:select></td>
	
	</tr>
	</logic:iterate>
	
	<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="6">
			<button title="Alt+A" type="button" accesskey="A" onclick="addRow('Edit');" class="topformbutton2"><bean:message key="button.addrow" /></button>
			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.deleterow" /></button>
		</td>
	</tr>
	
</table>
</logic:present>
<logic:notPresent  name="list">

<input type="hidden" name="rowCounter" id="rowCounter" value="5"/>	

<%for(int i=1;i<=5;i++){ 

request.setAttribute("i",i);

%>
<tr class="white1" style="width: 25px;">


<td ><input type="checkbox" name="chk" id="chk<%=i %>" value="<%=i %>"/></td>
		<td >
		<input type="text" name="ruleCode" id="ruleCode<%=i%>"  value="${requestScope.list[0].ruleCode}" readonly="readonly" class="text"/>
		 <html:button property="docButton" styleId="docButton" onclick="openLOVCommon(250,'policyChecklistDef','ruleCode${i }','','', '','','','ruleDesc${i }');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		
	
		</td>
		<td ><input type="text" name="ruleDesc" id="ruleDesc<%=i %>" value="${requestScope.list[0].ruleDesc}" readonly="readonly" class="text" tabindex="-1"/>
			<html:hidden  property="lbxPCD" styleId="lbxPCD"  value="" />
			</td>

		
		<td >
				<select class="action" id="action<%=i %>" value="${requestScope.list[0].action}" onchange="levelDis();">
							<option value="D">DEVIATION</option>
							<option value="S">STOP</option>
							

						</select></td>

		<td>
		<select class="appLevel" id="appLevel<%=i %>" value="${requestScope.list[0].appLevel}" onchange="levelDis();">
		 <logic:present name="pmstSize">   				
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int k=0;k<=j;k++) { %>
			<option value="<%=CommonFunction.checkNull(k).toString()%>"><%=CommonFunction.checkNull(k).toString()%></option>
		<%} %>
		</logic:present>
			</select></td>  			

</tr>
<%} %>
<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="6">
			<button type="button" title="Alt+A" accesskey="A" onclick="addRow('Save');" class="topformbutton2"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.deleterow" /></button></td>
	</tr>
	
</table>
</logic:notPresent>
</table>


</td></tr></table>
<logic:present name="editVal">
<tr>
     <td> <button type="button" id="Update" title="Alt+V" accesskey="V" class="topformbutton2" onclick="pcdSave('<bean:message key="lbl.selectOne" />',id);"><bean:message key="button.save" /></button></td>
</tr>
</logic:present>

<logic:notPresent name="editVal">
<tr>
     <td> <button type="button" id="Save" title="Alt+V" accesskey="V" class="topformbutton2" onclick="pcdSave('<bean:message key="lbl.selectOne" />',id);"><bean:message key="button.save" /></button></td>
</tr>
</logic:notPresent>


  
  </fieldset>

</html:form>
		<logic:present name="sms">
<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("policyChecklistAdd").action="<%=request.getContextPath()%>/policyChecklistMasterSearchScreen.do";
	    document.getElementById("policyChecklistAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("policyChecklistAdd").action="<%=request.getContextPath()%>/policyChecklistMasterSearchScreen.do";
	    document.getElementById("policyChecklistAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");	
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");
		document.getElementById("policyChecklistAdd").action="<%=request.getContextPath()%>/policyChecklistMasterSearchScreen.do";
	    document.getElementById("policyChecklistAdd").submit();
	}
	

	
	
</script>
</logic:present>
   
  </body>
</html:html>


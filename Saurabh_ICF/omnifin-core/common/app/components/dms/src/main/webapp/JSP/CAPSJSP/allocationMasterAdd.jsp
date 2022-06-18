<!--Author Name :Kanika Maheshwari-->
<!--Date of Creation :13 October 2011  -->
<!--Purpose  : Allocation Master Add Information-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/allocation.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript">
function allChecked()
{

	var c = document.getElementById("allchkd").checked;

	var ch=document.getElementsByName('chkCases');

 	var zx=0;

	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
	
}
</script>
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

<body onload="enableAnchor();fntab();init_fields();">





<html:form styleId="AllocationMasterAddForm"  method="post"  action="/allocationmasteradd" >

<fieldset>
<legend><bean:message key="lbl.allocationmaster" /></legend>
  <table width="100%">


<logic:notPresent name="list">

    <tr>
    <td width="13%"><bean:message key="lbl.userId" /></td>
    <td width="13%"> <html:text property="userIdSearch" styleClass="text" styleId="userIdSearch" maxlength="10" readonly="true" tabindex="-1"   /></td><td>
    <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(91,'AllocationMasterSearchForm','userIdSearch','','', '','','','lbxUserSearchId')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" value="" />
     </td>
   
       <td><bean:message key="lbl.active" /></td>
  <td><input type="checkbox" name="queueStatus" id="queueStatus" checked="checked" /></td>
    </tr>
    </table>
      <table width="100%">
    <tr>
    <td class="gridtd">
  	 <tr class="white2">
    	<td ><input type=checkbox id="allchkd" name=allchkd onClick="allChecked()" /></td> 

    	<td ><b><bean:message key="lbl.queueId" /></b></td>
      	<td ><b><bean:message key="lbl.queueDesc" /></b></td>
      		<td ><b><bean:message key="lbl.allocpercentage" /></b></td>
      		<td ><b><bean:message key="lbl.totalpercentage" /></b></td>
 	 </tr>
 	 
	<logic:present name="queuelist">
	<logic:notEmpty  name="queuelist">
	<logic:iterate name="queuelist" id="list"  indexId="count">

  	<tr class="white1">
  	<td>
  		<input type="checkbox" name="chkCases"  id="chkCases" value="<bean:write name='list' property='queue' />"/>
	</td>
	<td><bean:write name='list' property='queue' /></td>
	<td><bean:write name='list' property='queuedesc' /></td>
	<td><input type="text" name="allocpercentage" id="allocpercentage<%=count.intValue() %>"
	 onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" 
	 onkeyup="checkNumber(this.value, event, 3,id);"  onfocus="keyUpNumber(this.value, event, 3,id);" /></td>
	 	<td><input type="text" name="percent" id="percent" value="<bean:write name='list' property='showtotalpercent' />" tabindex="-1"  readonly="true" /></td>
	</tr>
	</logic:iterate>
	</logic:notEmpty>
	</logic:present>

     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" title="Alt+V" accesskey="V" id="save" onclick="this.disabled='true';saveAllocation();" class="topformbutton2" ><bean:message key="button.save" /></button>
    </td></tr></table>
</logic:notPresent>
<logic:present name="editMode">
<logic:present name="list">
<logic:notEmpty name="list">
<tr>
    <td width="13%"><bean:message key="lbl.iduser" /></td>
    <td width="13%"> <html:text property="userIdSearch" styleClass="text" styleId="userIdSearch" value="${list[0].userSearchId}" maxlength="10" readonly="true" tabindex="-1"  /></td><td>
    <input type="hidden" name="user" value="${list[0].userSearchId}" id="user" />
    <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(91,'AllocationMasterSearchForm','userIdSearch','','', '','','','lbxUserSearchId')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" value="${list[0].lbxUserSearchId}" />
     </td>
           <td><bean:message key="lbl.active" /></td>

    <td>
<logic:equal value="Active" name="status">
         <input type="checkbox" name="queueStatus" id="queueStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="queueStatus" id="queueStatus"  />
      </logic:notEqual></td>


        
   </tr>
    </table>
      <table width="100%">
    <tr>
    <td>
  	 <tr class="white2">
    	<td ><input type=checkbox id="allchkd" name=allchkd onClick="allChecked()" /></td> 

    	<td ><b><bean:message key="lbl.queueId" /></b></td>
      	<td ><b><bean:message key="lbl.queueDesc" /></b></td>
      	<td ><b><bean:message key="lbl.allocpercentage" /></b></td>
      	   	<td ><b><bean:message key="lbl.totalpercentage" /></b></td>
      		
 	 </tr>
 	 
<logic:present name="queueList">
<logic:notEmpty name="queueList">
<logic:iterate name="queueList" id="list" indexId="count" >

  	<tr class="white1">
  	<td>
  	  	<logic:equal property="queue" name="list" value="${list.queueId}">
  
  		<input type="checkbox" name="chkCases"  id="chkCases"  checked="checked" value="<bean:write name='list' property='queueId' />"/>
  		
  		</logic:equal>
  			<logic:notEqual property="queue" name="list" value="${list.queueId}">
  		<input type="checkbox" name="chkCases"  id="chkCases"   value="<bean:write name='list' property='queueId' />"/>
  		</logic:notEqual>
	</td>
	<td><bean:write name='list' property='queueId' /></td>
	<td><bean:write name='list' property='queuedesc' /></td>
	<td><input type="text" name="allocpercentage" id="allocpercentage<%=count.intValue() %>" value="<bean:write name='list' property='allocationpercentage' />" 
	           onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" 
	           onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" /></td>
		<td><input type="text" name="percent" id="percent" value="<bean:write name='list' property='showtotalpercent' />" tabindex="-1"  readonly="true" /></td>
	</tr>
</logic:iterate>
</logic:notEmpty>
</logic:present>

   
 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="this.disabled='true';updateAllocation();"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

    </td></tr></table>
    </logic:notEmpty>
</logic:present>
</logic:present>
	


</fieldset>
  
           
	</html:form>		
		<logic:present name="PERCENT">
<script type="text/javascript">

 if('<%=request.getAttribute("PERCENT").toString()%>'=='P')
	{
		alert("<bean:message key="msg.percent" />");
	
	} 
	</script>
	</logic:present>
	<logic:present name="sms">
<script type="text/javascript">
    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		document.getElementById("AllocationMasterAddForm").action="allocationMasterSearch.do?method=openSearchJsp";
		document.getElementById("AllocationMasterAddForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("AllocationMasterAddForm").action="allocationMasterSearch.do?method=openSearchJsp";
		document.getElementById("AllocationMasterAddForm").submit();
	
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
	
	}     
	    
	
	
</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
  </body>
		</html:html>
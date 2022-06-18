<!--Author Name : Richa Bansal-->
<!--Date of Creation : 11-March-2013-->
<!--Purpose  : Information of court Name Master-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
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
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/courtNameMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('lbxCourtTypeCode').focus();
   return true;
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

<body onload="document.getElementById('lbxCourtTypeCode').focus();">
<html:form styleId="courtNameMasterForm"  method="post"  action="/courtNameMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.courtNameMaster" /></legend>

<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
  <table width="100%">
  
   
  
  <tr>
    
     <td><bean:message key="lbl.courtType" /><span><font color="red">*</font></span></td>
      <td><html:text property="courtType" styleClass="text" styleId="courtType" maxlength="10"  onblur="fnChangeCase('courtNameMasterForm','courtType')" value="${requestScope.list[0].courtType}" readonly="true"/>
      <input type="hidden" name = "lbxCourtTypeCode" id="lbxCourtTypeCode" value="${requestScope.list[0].lbxCourtTypeCode}" />
      <html:button property="courtTypeButton" styleId="courtTypeButton" onclick="openLOVCommon(1501,'courtNameMasterForm','courtType','','', '','','','lbxCourtTypeCode')" value=" " styleClass="lovbutton"></html:button></td>
   
	

      <td><bean:message key="lbl.branch" /><span><font color="red">*</font></span></td>
      <td><html:text property="branch" styleClass="text" styleId="branch" maxlength="10"  onblur="fnChangeCase('courtNameMasterForm','branch')" value="${requestScope.list[0].branch}" readonly="true" />
      <input type="hidden" name = "lbxBranchId" id="lbxBranchId" value="${requestScope.list[0].lbxBranchId}" />
      <html:button property="branchButton" styleId="branchButton" onclick="openLOVCommon(1507,'courtNameMasterForm','branch','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"></html:button></td>
   
    </tr>
    <tr>
    
     <logic:present name="editVal">
      <td> <bean:message key="lbl.courtNameCode" /><span><font color="red">*</font></span></td>
        <td><html:text property="courtNameCode" styleClass="text" styleId="courtNameCode" maxlength="10" onblur="fnChangeCase('courtNameMasterForm','courtNameCode')" value="${requestScope.list[0].courtNameCode}" readonly="true"/></td>
      </logic:present>
   
   <logic:present name="save">
   <td> <bean:message key="lbl.courtNameCode" /><span><font color="red">*</font></span></td>
        <td><html:text property="courtNameCode" styleClass="text" styleId="courtNameCode" maxlength="10"  onblur="fnChangeCase('courtNameMasterForm','courtNameCode')" value="${requestScope.list[0].courtNameCode}"/></td>
   </logic:present>
	

      <td><bean:message key="lbl.courtNameDesc" /><span><font color="red">*</font></span></td>
     
      
      <td><html:text property="courtNameDesc" styleClass="text" styleId="courtNameDesc" maxlength="50"  onblur="fnChangeCase('courtNameMasterForm','courtNameDesc')" value="${requestScope.list[0].courtNameDesc}" />
     </td>
    </tr>
    
    
      <tr>
      
    <td><bean:message key="lbl.status" /></td>
    
    <td>
      
         <logic:present name="editVal">
      
 		<logic:equal value="A" name="status">
         <input type="checkbox" name=recStatus id="countryStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="status">
         <input type="checkbox" name="recStatus" id="countryStatus"  />
      </logic:notEqual>

   </logic:present>
   
   <logic:present name="save">
   
    <input type="checkbox" name=recStatus id="countryStatus" checked="checked" />
     
   </logic:present>
      
      
    
      
   </td> </tr><tr><td>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditCourtName('${requestScope.list[0].courtNameCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveCourtName();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("courtNameMasterForm").action="courtNameMasterBehind.do";
	    document.getElementById("courtNameMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("courtNameMasterForm").action="courtNameMasterBehind.do";
	    document.getElementById("courtNameMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
//	{
//		alert("<bean:message key="msg.notepadError" />");
//	}
//	else
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>
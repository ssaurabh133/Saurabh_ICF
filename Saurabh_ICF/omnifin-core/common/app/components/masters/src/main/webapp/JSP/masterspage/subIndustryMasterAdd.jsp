<!--Author Name : Ritu Jindal-->
<!--Date of Creation : -->
<!--Purpose  : Information of SubIndustry Master Add-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/subIndustryScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('subIndustryMasterForm').insusButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('subIndustryMasterForm').subIndustryDesc.focus();
     }
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

<body onload="enableAnchor();fntab();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:errors/>
<html:form styleId="subIndustryMasterForm"  method="post"  action="/subIndustryMasterAdd" >

<fieldset>
<legend><bean:message key="lbl.subIndustryMaster" /></legend>
  <table width="100%">
    <tr>
    <html:hidden property="subIndustryId" styleId="subIndustryId" value="${requestScope.list[0].subIndustryId}"/>
   <logic:present name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
      <td><bean:message key="lbl.subIndustryDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="subIndustryDesc" styleClass="text" styleId="subIndustryDesc" readonly="true" maxlength="50" value="${requestScope.list[0].subIndustryDesc}" />
    </logic:present>
    
    <logic:present name="editVal">
      <td><bean:message key="lbl.industry" /><span><font color="red">*</font></span></td>
      <td><html:text property="industryId" styleClass="text" styleId="industryId" maxlength="10" tabindex="-1" readonly="true" value="${requestScope.list[0].lbxIndustry}" />
      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
     <html:hidden  property="lbxIndustry" styleId="lbxIndustry"  value="${requestScope.list[0].industryId}"/>
      <html:button property="insusButton" styleId="insusButton" onclick="openLOVCommon(10,'subIndustryMasterForm','industryId','','', '','','','lbxIndustry');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>  
  <%--<img onClick="openLOVCommon(10,'subIndustryMasterForm','industryId','','', '','','','lbxIndustry')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>    
        </td></logic:present>
     </tr><tr>
 	 <logic:present name="editVal">       
      <td><bean:message key="lbl.active" /></td>
      <logic:equal value="Active" name="status">
       <td>  <input type="checkbox" name="subIndustryStatus" id="subIndustryStatus" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="Active" name="status">
       <td><input type="checkbox" name="subIndustryStatus" id="subIndustryStatus"  /></td>
      </logic:notEqual>
</logic:present></tr>
   <tr>
   	<logic:notPresent name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
      <td><bean:message key="lbl.subIndustryDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="subIndustryDesc" styleClass="text" styleId="subIndustryDesc" maxlength="50" onblur="fnChangeCase('subIndustryMasterForm','subIndustryDesc')"/>
     </logic:notPresent>
      <logic:notPresent name="editVal">
      <td><bean:message key="lbl.industry" /><span><font color="red">*</font></span></td>
      <td><html:text property="industryId" styleClass="text" styleId="industryId" tabindex="-1" maxlength="10" readonly="true" />
      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
     <html:hidden  property="lbxIndustry" styleId="lbxIndustry" />
        <html:button property="insusButton" styleId="insusButton" onclick="openLOVCommon(10,'subIndustryMasterForm','industryId','','', '','','','lbxIndustry');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>  
  <%--<img onClick="openLOVCommon(10,'subIndustryMasterForm','industryId','','', '','','','lbxIndustry')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>    
        </td>   </logic:notPresent>
       </tr><tr> 
        <logic:notPresent name="editVal">
      <td><bean:message key="lbl.active" /></td>

      <logic:equal value="Active" name="status">
       <td>  <input type="checkbox" name="subIndustryStatus" id="subIndustryStatus" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="Active" name="status">
       <td><input type="checkbox" name="subIndustryStatus" id="subIndustryStatus"  /></td>
      </logic:notEqual>
      </logic:notPresent>
    </tr>
    
  <tr>
   <td>

    <logic:present name="editVal">
      <strong><button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return fnEditSubIndustry('${requestScope.list[0].subIndustryId}');" class="topformbutton2"><bean:message key="button.save" /></button></strong>
   </logic:present>
   
   <logic:notPresent name="editVal">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return saveSubIndustry();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
</td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>
	<logic:present name="sms">
<script type="text/javascript">

    
	
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("subIndustryMasterForm").action="subIndustryMasterBehind.do";
	    document.getElementById("subIndustryMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");		document.getElementById("subIndustryMasterForm").action="subIndustryMasterBehind.do";
	    document.getElementById("subIndustryMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='EX')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	
	
</script>
</logic:present>		
		
  </body>
		</html:html>
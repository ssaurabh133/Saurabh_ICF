<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 23-May-2011-->
<!--Purpose  : Information of District Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript">
function fntab()
{
     document.getElementById('districtMaster').districtDes.focus();
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

<body onload="enableAnchor();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
<html:form styleId="districtMaster"  method="post"  action="/districtMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<fieldset>
<legend><bean:message key="lbl.districtMaster" /></legend>
  <table width="100%">
    <tr>
   
	<html:hidden  property="districtId" styleId="districtId"  value="${requestScope.list[0].districtId}" />

     <logic:present name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
      <td><bean:message key="lbl.districtDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="districtDes" styleClass="text"  styleId="districtDes" maxlength="50" onblur="fnChangeCase('districtMaster','districtDes')" value="${requestScope.list[0].districtDes}" /> </td>
     
      <td><bean:message key="lbl.state" /><span><font color="red">*</font></span></td>
      <td><html:text property="stateId" styleClass="text" styleId="stateId" readonly="true" value="${requestScope.list[0].stateId}" tabindex="-1" />
	  <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(32,'districtMaster','stateId','','', '','','','lbxStateId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	  <%-- <img onClick="openLOVCommon(32,'districtMaster','stateId','','', '','','','lbxStateId')" src="<%= request.getContextPath()%>/images/lov.gif"></td>--%>
	  <html:hidden  property="lbxStateId" styleId="lbxStateId"  value="${requestScope.list[0].lbxStateId}"/>
   
     </logic:present>
     
     <logic:notPresent name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
      <td><bean:message key="lbl.districtDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="districtDes" styleClass="text" styleId="districtDes" maxlength="50" onblur="fnChangeCase('districtMaster','districtDes')"/> </td>
     
      <td><bean:message key="lbl.state" /><span><font color="red">*</font></span></td>
      <td><html:text property="stateId" styleClass="text" styleId="stateId" readonly="true" tabindex="-1" />
	  <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(32,'districtMaster','stateId','','', '','','','lbxStateId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	 <%-- <img onClick="openLOVCommon(32,'districtMaster','stateId','','', '','','','lbxStateId')" src="<%= request.getContextPath()%>/images/lov.gif"></td>  --%>
	  <html:hidden  property="lbxStateId" styleId="lbxStateId" />
  
     </logic:notPresent>
     
        </tr>
    
      <tr>
      
    <td><bean:message key="lbl.status" /></td>
      <td>
      <logic:notPresent name="editVal">
      <input type="checkbox" name="districtStatus" id="districtStatus" checked="checked" />
      </logic:notPresent>
      
      <logic:present name="editVal">
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="districtStatus" id="districtStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="districtStatus" id="districtStatus"  />
      </logic:notEqual>
      </logic:present>
      </td>
      
    </tr><tr><td>
    
    <logic:present name="editVal">
       <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditDistrict('${requestScope.list[0].districtId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
     <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveDistrict();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("districtMaster").action="districtMasterBehind.do";
	    document.getElementById("districtMaster").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("districtMaster").action="districtMasterBehind.do";
	    document.getElementById("districtMaster").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}  
	else
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}     
	    
	
	
</script>
</logic:present>
  </body>
		</html:html>
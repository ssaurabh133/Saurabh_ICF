<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 23-Jun-2011-->
<!--Purpose  : Information of State Master-->
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
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('docChildMasterForm').docButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('docChildMasterForm').docButton.focus();
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

<body onload="enableAnchor();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:javascript formName="DocChildMasterAddDyanavalidatiorForm"/>
<html:errors/>
<html:form styleId="docChildMasterForm"  method="post"  action="/docChildMasterAdd" >

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<fieldset>
<legend><bean:message key="lbl.docChildMaster" /></legend>
  <table width="100%">
    <tr>
   
	<html:hidden  property="docChildID" styleId="docChildID"  value="${requestScope.list[0].docChildID}" />
  <logic:present name="editVal">
  
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
  	<td><bean:message key="lbl.documentCode" /><span><font color="red">*</font></span></td>
 	<td><html:text property="docId" styleClass="text" styleId="docId" tabindex="-1" readonly="true" value="${requestScope.list[0].docId}" />
      <input type="hidden" value="${requestScope.list[0].lbxDocId}" name="lbxDocId" id="lbxDocId"/>
     <html:button property="docButton" styleId="docButton" onclick="openLOVCommon(72,'docChildMasterForm','docId','','', '','','','docDes');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
     <%-- <img onClick="openLOVCommon(72,'docChildMasterForm','docId','','', '','','','docDes')" src="<%= request.getContextPath()%>/images/lov.gif"></td>--%>

  	 <td><bean:message key="lbl.documentDesc" /> </td>
     <td> <html:text property="docDes" styleClass="text" styleId="docDes" readonly="true" tabindex="-1" maxlength="50" value="${requestScope.list[0].docDes}"/></td>
   </logic:present>
   
   <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
  	<td><bean:message key="lbl.documentCode" /><span><font color="red">*</font></span></td>
 	<td><html:text property="docId" styleClass="text" styleId="docId" readonly="true" tabindex="-1" />
      <input type="hidden"  name="lbxDocId" id="lbxDocId"/>
     <html:button property="docButton" styleId="docButton" onclick="openLOVCommon(72,'docChildMasterForm','docId','','', '','','','docDes');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img onClick="openLOVCommon(72,'docChildMasterForm','docId','','', '','','','docDes')" src="<%= request.getContextPath()%>/images/lov.gif"></td> --%>

  	 <td><bean:message key="lbl.documentDesc" /> </td>
     <td> <html:text property="docDes" styleClass="text" styleId="docDes" readonly="true" maxlength="50"/></td>
   </logic:notPresent>
    </tr>
    
      <tr>
	 
	 <logic:present name="editVal">
	 <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
	     <td><bean:message key="lbl.docChildDesc" /><span><font color="red">*</font></span></td>
    	 <td> <html:text property="docChildDes" styleClass="text" styleId="docChildDes" tabindex="-1" readonly="true" maxlength="50" onblur="fnChangeCase('docChildMasterForm','docChildDes')" value="${listObj.agencyDesc}" value="${requestScope.list[0].docChildDes}"/></td>
	</logic:present>
      
    <logic:notPresent name="editVal">
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
        <td><bean:message key="lbl.docChildDesc" /><span><font color="red">*</font></span></td>
        <td> <html:text property="docChildDes" styleClass="text" styleId="docChildDes" onblur="fnChangeCase('docChildMasterForm','docChildDes')" maxlength="50" /></td>
    </logic:notPresent> 
     
    <td><bean:message key="lbl.status" /></td>
      <td>
       <logic:notPresent name="editVal">
       <input type="checkbox" name="status" id="status" checked="checked" />
       </logic:notPresent>
       
       <logic:present name="editVal">
       <logic:equal value="Active" name="status">
       <input type="checkbox" name="status" id="status" checked="checked" />
       </logic:equal>
       <logic:notEqual value="Active" name="status">
       <input type="checkbox" name="status" id="status"  />
       </logic:notEqual>
       </logic:present>
      </td>
      
    </tr><tr><td>
    
    <logic:present name="editVal">
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditDoc('${requestScope.list[0].docChildID}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
    <logic:present name="editValUpdate">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditDoc('${requestScope.list[0].docChildID}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveDoc();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("docChildMasterForm").action="documentChildMasterBehind.do";
	    document.getElementById("docChildMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("docChildMasterForm").action="documentChildMasterBehind.do";
	    document.getElementById("docChildMasterForm").submit();
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
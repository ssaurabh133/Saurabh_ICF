<!--Author Name : Ritu Jindal-->
<!--Date of Creation :9 May 2011 -->
<!--Purpose  : Information of Document Master Add-->
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

		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/documentScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('documentMasterForm').documentStatus.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('documentMasterForm').documentDesc.focus();
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

<body onload="enableAnchor();fntab();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <logic:present name="editVal">
 	
 	
<html:form styleId="documentMasterForm"  method="post"  action="/documentMasterAdd" onsubmit="return fnEditDocument('${list[0].documentId }');">
<fieldset>
<legend><bean:message key="lbl.documentMaster" /></legend>
  <table cellpadding="0" cellspacing="1" width="100%">
   <logic:present name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
   <logic:iterate id="listObj" name="list">
   <tr>
   <td width="13%"><bean:message key="lbl.documentDesc" /><span><font color="red">*</font></span></td>
   <td width="13%"><html:text property="documentDesc" styleClass="text" styleId="documentDesc" maxlength="50"  readonly="true" value="${listObj.documentDesc}" /></td>
   
   <td width="13%"><bean:message key="lbl.active" /></td>
      <td width="13%">
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="documentStatus" id="documentStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="documentStatus" id="documentStatus"  />
      </logic:notEqual></td>
      
    </tr>
   
   <!-- Virender Code Start -->
   <tr>
   <td width="13%"><bean:message key="lbl.documentcategory" /><span><font color="red">*</font></span></td>
	<td >
      <html:select property="documentCategory" styleClass=""  styleId="documentCategory" value="${listObj.documentCategory}">  
        <html:option value="CD">CREDIT DOCUMENT</html:option>
        <html:option value="PD">PROPERTY DOCUMENT</html:option>
        <html:option value="GD">GOVT DOC</html:option>
      </html:select>
    </td>
    <td width="13%"></td>
	<td width="13%"></td>
	</tr>
   <!-- Virender Code End --> 
    
   </logic:iterate>
   </logic:present>
   
   <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
   <tr>
   <td width="13%"><bean:message key="lbl.documentDesc" /><span><font color="red">*</font></span></td>
   <td width="13%"><html:text property="documentDesc" styleClass="text" styleId="documentDesc" maxlength="50"  /></td>
   
   <td width="13%"><bean:message key="lbl.active" /></td>
      <td width="13%">
         <input type="checkbox" name="documentStatus" id="documentStatus" checked="checked" />
      </td>
      
    </tr>
    
       <!-- Virender Code Start -->
   <tr>
   <td width="13%"><bean:message key="lbl.documentcategory" /><span><font color="red">*</font></span></td>
	<td >
      <html:select property="documentCategory" styleClass=""  styleId="documentCategory" value="${listObj.documentCategory}">  
        <html:option value="CD">CREDIT DOCUMENT</html:option>
        <html:option value="PD">PROPERTY DOCUMENT</html:option>
        <html:option value="GD">GOVT DOC</html:option>
      </html:select>
    </td>
    <td width="13%"></td>
	<td width="13%"></td>
	</tr>
   <!-- Virender Code End --> 
 
   </logic:notPresent>
    <tr><td>

   
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditDocument('${list[0].documentId }');" class="topformbutton2" ><bean:message key="button.save" /></button>
  
    </td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>
 </logic:present>

 <logic:notPresent name="editVal">

<html:form styleId="documentMasterForm"  method="post"  action="/documentMasterAdd" onsubmit="return saveDocument();">
<fieldset>
<legend><bean:message key="lbl.documentMaster" /></legend>
  <table cellpadding="0" cellspacing="1" width="100%">
   <logic:present name="editVal">
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
   <logic:iterate id="listObj" name="list">
   <tr>
   <td width="13%"><bean:message key="lbl.documentDesc" /><span><font color="red">*</font></span></td>
   <td width="13%"><html:text property="documentDesc" styleClass="text" styleId="documentDesc" maxlength="50"  readonly="true" value="${listObj.documentDesc}" /></td>
   
   <td width="13%"><bean:message key="lbl.active" /></td>
      <td width="13%">
         <input type="checkbox" name="documentStatus" id="documentStatus" checked="checked" />
      </td>
      
    </tr>
    
       <!-- Virender Code Start -->
   <tr>
   <td width="13%"><bean:message key="lbl.documentcategory" /><span><font color="red">*</font></span></td>
	<td >
      <html:select property="documentCategory" styleClass=""  styleId="documentCategory" value="${listObj.documentCategory}">  
        <html:option value="CD">CREDIT DOCUMENT</html:option>
        <html:option value="PD">PROPERTY DOCUMENT</html:option>
        <html:option value="GD">GOVT DOC</html:option>
      </html:select>
    </td>
    <td width="13%"></td>
	<td width="13%"></td>
	</tr>
   <!-- Virender Code End --> 
    
   </logic:iterate>
   </logic:present>
   
   <logic:notPresent name="editVal">
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
   <tr>
   <td width="13%"><bean:message key="lbl.documentDesc" /><span><font color="red">*</font></span></td>
   <td width="13%"><html:text property="documentDesc" styleClass="text" styleId="documentDesc" maxlength="50"  /></td>
   
   <td width="13%"><bean:message key="lbl.active" /></td>
      <td width="13%">
         <input type="checkbox" name="documentStatus" id="documentStatus" checked="checked" />
      </td>
      
    </tr>
 
 	   <!-- Virender Code Start -->
   <tr>
   <td width="13%"><bean:message key="lbl.documentcategory" /><span><font color="red">*</font></span></td>
	<td >
      <html:select property="documentCategory" styleClass=""  styleId="documentCategory" value="${listObj.documentCategory}">  
        <html:option value="CD">CREDIT DOCUMENT</html:option>
        <html:option value="PD">PROPERTY DOCUMENT</html:option>
        <html:option value="GD">GOVT DOC</html:option>
      </html:select>
    </td>
    <td width="13%"></td>
	<td width="13%"></td>
	</tr>
   <!-- Virender Code End --> 
 
   </logic:notPresent>
    <tr><td>

  
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveDocument();" class="topformbutton2"><bean:message key="button.save" /></button>
   
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>
	</logic:notPresent>		
		<logic:present name="sms">
<script type="text/javascript">

    
			if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("documentMasterForm").action="documentMasterBehind.do";
	    document.getElementById("documentMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("documentMasterForm").action="documentMasterBehind.do";
	    document.getElementById("documentMasterForm").submit();
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
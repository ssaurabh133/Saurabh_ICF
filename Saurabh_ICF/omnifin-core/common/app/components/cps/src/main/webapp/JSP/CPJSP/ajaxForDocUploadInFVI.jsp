<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td ><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList">
    <tr  class="white1">
    	<td><input type="checkbox" name="chk" id="chk" value="${uploadedDocSubList.fileName}"/></td>
     	<td><a href="#" onclick="downloadFVCDoc('${uploadedDocSubList.fileName}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
        <td>${uploadedDocSubList.userName}</td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>
    </table>
    </td>
</tr>
</table>
<button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" onclick="deleteUploadDoc();"><bean:message key="button.delete" /></button>
	</fieldset>
	
	<logic:present name="message">

 <script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='E')
	{
		alert('<bean:message key="msg.fileExist" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='U')
	{
		alert('<bean:message key="msg.upperLimit" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='S')
	{
		alert('<bean:message key="msg.selectFile" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='FiveDocOnly')
	{
		alert('<bean:message key="msg.fiveDocOnly" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='UploadSuccessful')
	{
		alert('<bean:message key="msg.uploadSuccessful" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='deletedoc')
	{
		alert('<bean:message key="lbl.dataDeleted" />');
	}
</script>
</logic:present>
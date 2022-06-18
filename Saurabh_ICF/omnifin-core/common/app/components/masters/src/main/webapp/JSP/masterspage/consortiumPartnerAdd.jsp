<!--Author Name : Parvez Khan-->
<!--Date of Creation : 08-Aug-2014-->
<!--Purpose  : Information of Region Master-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab1()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('ConsortiumPartner').consortiumStatus.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	 document.getElementById('ConsortiumPartner').consortiumPartnerDes.focus();
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

<body onload="enableAnchor();fntab1();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <logic:present name="editVal">
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
 <html:hidden property="contextPath" styleId="contextPath"
			value="<%=request.getContextPath()%>" />
<html:form styleId="ConsortiumPartner"  method="post"  action="/consortiumPartnerAdd" onsubmit="fnEditConsortium('${requestScope.list[0].consortiumPartnerId}');" >
<fieldset>
<legend><bean:message key="lbl.consortiumPartner" /></legend>
  <table width="100%">
   
   
	<html:hidden  property="consortiumPartnerId" styleId="consortiumPartnerId"  value="${requestScope.list[0].consortiumPartnerId}" />
	<logic:present name="editVal">
	<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
    <logic:iterate id="listObj" name="list">
 <tr>
        <td width="13%"><bean:message key="lbl.consortiumPartnerName" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="consortiumPartnerName" styleClass="text" styleId="consortiumPartnerName" maxlength="50"  onblur="fnChangeCase('ConsortiumPartner','consortiumPartnerName')" value="${requestScope.list[0].consortiumPartnerName}"/>
     </td>
      <td width="13%"><bean:message key="lbl.defaultPercentageLoan" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="defaultPercentageLoan" styleClass="text" styleId="defaultPercentageLoan" maxlength="50"  onblur="fnChangeCase('ConsortiumPartner','defaultPercentageLoan')" value="${requestScope.list[0].defaultPercentageLoan}"/>
     </td>
     
      <td width="13%"><bean:message key="lbl.defaultAgreedRate" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="defaultAgreedLoan" styleClass="text" styleId="defaultAgreedLoan" maxlength="50"  onblur="fnChangeCase('ConsortiumPartner','defaultAgreedLoan')" value="${requestScope.list[0].defaultAgreedLoan}"/>
     </td>
     

    </tr>
    <tr>
    <td><bean:message key="lbl.active" /></td>
      <td>
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="consortiumPartnerStatus" id="consortiumPartnerStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="consortiumPartnerStatus" id="consortiumPartnerStatus"  />
      </logic:notEqual></td>
      </tr>
    
     </logic:iterate ></logic:present >
    
    <logic:notPresent name="editVal">
    

 <tr>
      <td width="13%"><bean:message key="lbl.defaultPercentageLoan" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="defaultPercentageLoan" styleClass="text" styleId="defaultPercentageLoan" maxlength="50" onblur="fnChangeCase('ConsortiumPartner','defaultPercentageLoan')" />
     </td >
     
      

    </tr></logic:notPresent >
   
    <tr>
   <td>
  
  <logic:present name="editVal">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditConsortium();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    
     <%-- <logic:present name="editValUpdate">
      <button type="button"  name="save"  id="save" title="Alt+V" accesskey="V" onclick="return fnEditRegion('${requestScope.list[0].consortiumPartnerIdModify}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present></td> --%></tr>
    
	</table>		


</fieldset>

</html:form>	

 		
 </logic:present>
 
   <logic:notPresent name="editVal">
 
   		<html:form styleId="ConsortiumPartner"  method="post"  action="/consortiumPartnerAdd" onsubmit="return saveConsortiumPartner();" >
<fieldset>
<legend><bean:message key="lbl.consortiumPartner" /></legend>
  <table width="100%">
   
   
	<html:hidden  property="consortiumPartnerId" styleId="consortiumPartnerId"  value="${requestScope.list[0].consortiumPartnerId}" />
	<logic:present name="editVal">
    <logic:iterate id="listObj" name="list">
 <tr>
      <td width="13%"><bean:message key="lbl.defaultPercentageLoan" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="defaultPercentageLoan" styleClass="text" styleId="defaultPercentageLoan" maxlength="50" readonly="true" onblur="fnChangeCase('ConsortiumPartner','defaultPercentageLoan')" value="${requestScope.list[0].defaultPercentageLoan}"/>
     </td>
     
      
 <td width="13%"><bean:message key="lbl.defaultAgreedRate" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="defaultAgreedLoan" styleClass="text" styleId="defaultAgreedLoan" maxlength="50" readonly="true" onblur="fnChangeCase('ConsortiumPartner','defaultAgreedLoan')" value="${requestScope.list[0].defaultAgreedLoan}"/>
     </td>
    </tr> </logic:iterate ></logic:present >
    
    <logic:notPresent name="editVal">
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
 <tr>
      <td width="13%"><bean:message key="lbl.consortiumPartnerName" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="consortiumPartnerName" styleClass="text" styleId="consortiumPartnerName" maxlength="50" onblur="fnChangeCase('ConsortiumPartner','consortiumPartnerName')" value=""/>
     </td >
     
     <td width="13%"><bean:message key="lbl.defaultPercentageLoan" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="defaultPercentageLoan" styleClass="text" styleId="defaultPercentageLoan" maxlength="50" onblur="fnChangeCase('ConsortiumPartner','defaultPercentageLoan')" value=""/>
     </td >
     
     <td width="13%"><bean:message key="lbl.defaultAgreedRate" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="defaultAgreedLoan" styleClass="text" styleId="defaultAgreedLoan" maxlength="50" onblur="fnChangeCase('ConsortiumPartner','defaultAgreedLoan')" value=""/>
     </td >
     
     
     

    </tr>
    <tr>
    <td ><bean:message key="lbl.active" /></td>
      <td>
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="consortiumPartnerStatus" id="consortiumPartnerStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
           <input type="checkbox" name="consortiumPartnerStatus" id="consortiumPartnerStatus"  />
      </logic:notEqual></td></tr>
    
    </logic:notPresent >
   
    <tr>
    <td>
  <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
   <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveConsortiumPartner();" class="topformbutton2" ><bean:message key="button.save" /></button>
</logic:present >

<logic:present name="editValUpdate">
     <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditConsortium('${requestScope.list[0].consortiumPartnerIdModify}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    
 </td></tr>
    
	</table>		
</fieldset>
  	</html:form>	
  	 		
</logic:notPresent>


  
  
          
	
		<logic:present name="sms">
<script type="text/javascript">

if('<%=request.getAttribute("sms").toString()%>'=='SC')
{
	alert("<bean:message key="lbl.dataSave" />");
	document.getElementById("ConsortiumPartner").action="consortiumPartnerMasterBehindAction.do";
    document.getElementById("ConsortiumPartner").submit();
}

else if('<%=request.getAttribute("sms").toString()%>'=='M')
{
	alert("<bean:message key="lbl.dataModify" />");
	document.getElementById("ConsortiumPartner").action="consortiumPartnerMasterBehindAction.do";
    document.getElementById("ConsortiumPartner").submit();
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
<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 28-April-2011-->
<!--Purpose  : Information of Bank Master1-->
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
<script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/bankScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
</script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('bankmaster').bankName.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('bankmaster').bankCode.focus();
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
<html:form action="/bankMasterAdd" styleId="bankmaster" method="POST" >
<html:errors/>

<fieldset>

<legend><bean:message key="lbl.bankMaster"/></legend>

  <table width="100%" height="86";">
    

    <tr>
    
      <logic:present name="editVal">
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
    		 <td><bean:message key="lbl.bankcode" /><span><font color="red">*</font></span></td>
     		 <td><html:text property="bankCode" styleClass="text" styleId="bankCode" maxlength="10"  readonly="true" value="${requestScope.list[0].bankCode}" /></td>
   	 
   	         <td><bean:message key="lbl.name"/><span><font color="red">*</font></span></td>
             <td><label><html:text property="bankName" styleClass="text" styleId="bankName" maxlength="50" onblur="fnChangeCase('bankmaster','bankName')" value="${requestScope.list[0].bankName}" /></label></td>
      
   	 </logic:present>
    
    <logic:notPresent name="editVal">
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
    		<td><bean:message key="lbl.bankcode" /><span><font color="red">*</font></span></td>
   		    <td><html:text property="bankCode" styleClass="text" styleId="bankCode" maxlength="10"  onblur="fnChangeCase('bankmaster','bankCode')"  /></td>
   
            <td><bean:message key="lbl.name"/><span><font color="red">*</font></span></td>
            <td><label><html:text property="bankName" styleClass="text" styleId="bankName" maxlength="50" onblur="fnChangeCase('bankmaster','bankName')"  /></label></td>
      
    </logic:notPresent> 
     </tr>
       <tr>
     
      <logic:present name="editVal"> 
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
	 <td> <bean:message key="lbl.status"/> </td>
     
       <td>
      <logic:equal value="Active" name="status">
              <input type="checkbox" name="bankStatus" id="bankStatus" checked="checked" />
      </logic:equal>
      
         <logic:notEqual value="Active" name="status">
      	 		  <input type="checkbox" name="bankStatus" id="bankStatus"  />
         </logic:notEqual></td>
      </logic:present>    
  </tr>
 
  <tr>
  <logic:notPresent name="editVal">
	<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>  
   <td>
     <bean:message key="lbl.status"/> </td>
     
       <td>
       <input type="checkbox" name="bankStatus" id="bankStatus" checked="checked" />
       </td>
         
  
  </logic:notPresent></tr>
  <tr><td>
    <br>
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEdit();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    
   <logic:notPresent name="editVal">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick=" fnSave();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
    <br></td> </tr>
  
		
	</table>		

</fieldset></html:form>

<logic:present name="sms">
		<script type="text/javascript">

			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("Data Saved Successfully");
				document.getElementById("bankmaster").action="bankMasterBehind.do";
	    		document.getElementById("bankmaster").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=='M')
	        {
		        alert("<bean:message key="lbl.dataModify" />");
		        document.getElementById("bankmaster").action="bankMasterBehind.do";
	            document.getElementById("bankmaster").submit();
	        }
	        else if('<%=request.getAttribute("sms").toString()%>'=='E')
			{
				alert("<bean:message key="msg.notepadError" />");
			}
			else if('<%=request.getAttribute("sms").toString()%>'=='EX')
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

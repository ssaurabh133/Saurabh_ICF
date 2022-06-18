<!--Author Name : -->
<!--Date of Creation : 21-May-2011-->
<!--Purpose  : Information of country Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/caseTypeMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('caseTypeMasterForm').caseTypeCode.focus();
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

<body onload="fntab();init_fields();">
<html:form styleId="caseTypeMasterForm"  method="post"  action="/caseTypeMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.caseTypeMaster" /></legend>
  <table width="100%">
    <tr>
    
     <td><bean:message key="lbl.caseTypeCode" /><span><font color="red">*</font></span></td>
     
      <logic:present name="editVal">
      <td><html:text property="caseTypeCode" readonly="true" styleClass="text" styleId="caseTypeCode" maxlength="10" onblur="fnChangeCase('caseTypeMasterForm','caseTypeCode')" value="${requestScope.list[0].caseTypeCode}" /></td>
   	  </logic:present>
   	  
   	   <logic:notPresent name="editVal">
      <td><html:text property="caseTypeCode" styleClass="text" styleId="caseTypeCode" maxlength="10" onblur="fnChangeCase('caseTypeMasterForm','caseTypeCode')" value="${requestScope.list[0].caseTypeCode}" /></td>
   	  </logic:notPresent>
	

      <td><bean:message key="lbl.caseTypeDesc" /><span><font color="red">*</font></span></td>
     
      
      <td><html:text property="caseTypeDesc" styleClass="text" styleId="caseTypeDesc" maxlength="50" onblur="fnChangeCase('caseTypeMasterForm','caseTypeDesc')" value="${requestScope.list[0].caseTypeDesc}" />
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
      
     </td>
     
    
      
    </tr><tr><td>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnUpdateCaseType('${requestScope.list[0].caseTypeCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveCaseType();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("caseTypeMasterForm").action="caseTypeMasterBehind.do";
	    document.getElementById("caseTypeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("caseTypeMasterForm").action="caseTypeMasterBehind.do";
	    document.getElementById("caseTypeMasterForm").submit();
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
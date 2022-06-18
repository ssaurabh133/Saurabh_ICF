<!--Author Name : Ankit Agarwal-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('countryMasterForm').countryDes.focus();
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
<html:form styleId="countryMasterForm"  method="post"  action="/countryMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.countryMaster" /></legend>
  <table width="100%">
    <tr>
   
	<html:hidden  property="countryId" styleId="countryId"  value="${requestScope.list[0].countryId}" />

      <td><bean:message key="lbl.countyDesc" /><span><font color="red">*</font></span></td>
      <logic:present name="editVal">
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
      <td><html:text property="countryDes" styleClass="text" styleId="countryDes" maxlength="50" onblur="fnChangeCase('countryMasterForm','countryDes')" value="${requestScope.list[0].countryDes}" />
     </td>
     </logic:present>
     <logic:notPresent name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
     <td><html:text property="countryDes" styleClass="text" styleId="countryDes" maxlength="50" onblur="fnChangeCase('countryMasterForm','countryDes')"  />
     </td>
     </logic:notPresent>
     
     <logic:present name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
     <td><bean:message key="lbl.currenId" /><span><font color="red">*</font></span></td>
      <td><html:text property="currencyId" styleClass="text" styleId="currencyId" maxlength="3" onblur="fnChangeCase('countryMasterForm','currencyId')" value="${requestScope.list[0].currencyId}" /></td>
     
     </logic:present>
     
     <logic:notPresent name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
     <td><bean:message key="lbl.currenId" /><span><font color="red">*</font></span></td>
      <td><html:text property="currencyId" styleClass="text" styleId="currencyId" maxlength="3" onblur="fnChangeCase('countryMasterForm','currencyId')"  /></td>
     
     </logic:notPresent>
      
    </tr>
    
      <tr>
      
    <td><bean:message key="lbl.status" /></td>
      <td>
       <logic:notPresent name="editVal">
          <input type="checkbox" name="countryStatus" id="countryStatus" checked="checked" />
       </logic:notPresent>
      
       <logic:present name="editVal">
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="countryStatus" id="countryStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="countryStatus" id="countryStatus"  />
      </logic:notEqual>
      </logic:present></td>
      
    </tr><tr><td>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditCountry('${requestScope.list[0].countryId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveCountry();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("countryMasterForm").action="countryMasterBehind.do";
	    document.getElementById("countryMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("countryMasterForm").action="countryMasterBehind.do";
	    document.getElementById("countryMasterForm").submit();
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
<!--Author Name : -->
<!--Date of Creation : 10-Dec-2013-->
<!--Purpose  : Sector Type Detail-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/LoanDetails.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/loanInitiation.js"></script>
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

<body onload="enableAnchor();fntab();init_fields();">
<html:form styleId="SectorTypeDetailsForm"  method="post"  action="/caseTypeMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.agriDetails" /></legend>
<logic:present name="deal">

  <table width="100%">
    <tr>
    <html:hidden property="dealId" styleClass="text" styleId="dealId"  value="${sessionScope.dealId}" />
    <html:hidden property="loanId" styleClass="text" styleId="loanId"  value="${sessionScope.loanId}" />
     <td><bean:message key="lbl.agriDocs" /><font color="red">*</font></td>
     
  	   <td>
			 <html:select property="agriDocs" value="${list[0].agriDocs}" styleClass="text" styleId="agriDocs">
				<html:option value="">-- Select --</html:option>
				<logic:present name="agriDocsList">
					<logic:notEmpty name="agriDocsList">
					<html:optionsCollection name="agriDocsList" label="name" value="id" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
	</td>

      <td><bean:message key="lbl.agriLand" /></td>
     
      
      <td><html:text property="agriLand" styleClass="text" styleId="agriLand" maxlength="50" onblur="fnChangeCase('caseTypeMasterForm','agriLand')" value="${requestScope.list[0].agriLand}" />
     </td>
   
    </tr>
    <tr>
    <td><bean:message key="lbl.nameAgriDoc" /></td>
    <td><html:text property="nameAgriDoc" styleClass="text" styleId="nameAgriDoc" maxlength="50" onblur="fnChangeCase('caseTypeMasterForm','nameAgriDoc')" value="${requestScope.list[0].nameAgriDoc}" />
     </td>
    
      <td><bean:message key="lbl.relationWithHirer" /></td>
    <td><html:text property="relationWithHirer" styleClass="text" styleId="relationWithHirer" maxlength="50" onblur="fnChangeCase('caseTypeMasterForm','relationWithHirer')" value="${requestScope.list[0].relationWithHirer}" />
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
	 <logic:equal value="A" name="status">
         <input type="checkbox" name=recStatus id="countryStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="status">
         <input type="checkbox" name="recStatus" id="countryStatus"  />
      </logic:notEqual>
	</logic:present>
      
     </td>
    
      
    </tr><tr><td>
    
   <logic:present name="inDeal">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveSectorTypeDetails();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   
    <logic:present name="inLoan">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveSectorTypeDetail();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> 
    

    </tr>
    </table>
</logic:present>
    <logic:present name="edit">
    <table width="100%">
    <tr>
    <html:hidden property="dealId"  styleClass="text" styleId="dealId"  value="${sessionScope.dealId}" />
    <html:hidden property="loanId"  styleClass="text" styleId="loanId"  value="${sessionScope.loanId}" />
     <td><bean:message key="lbl.agriDocs" /><font color="red">*</font></td>
     
  	   <td>
			 <html:select property="agriDocs" value="${list[0].agriDocs}" styleClass="text" styleId="agriDocs" disabled="true">
				<html:option value="">-- Select --</html:option>
				<logic:present name="agriDocsList">
					<logic:notEmpty name="agriDocsList">
					<html:optionsCollection name="agriDocsList" label="name" value="id" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
	</td>

      <td><bean:message key="lbl.agriLand" /></td>
     
      
      <td><html:text property="agriLand" styleClass="text" styleId="agriLand" maxlength="50" onblur="fnChangeCase('caseTypeMasterForm','agriLand')" value="${requestScope.list[0].agriLand}" readonly="true" />
     </td>
   
    </tr>
    <tr>
    <td><bean:message key="lbl.nameAgriDoc" /></td>
    <td><html:text property="nameAgriDoc" styleClass="text" styleId="nameAgriDoc" maxlength="50" onblur="fnChangeCase('caseTypeMasterForm','nameAgriDoc')" value="${requestScope.list[0].nameAgriDoc}"readonly="true" />
     </td>
    
      <td><bean:message key="lbl.relationWithHirer" /></td>
    <td><html:text property="relationWithHirer" styleClass="text" styleId="relationWithHirer" maxlength="50" onblur="fnChangeCase('caseTypeMasterForm','relationWithHirer')" value="${requestScope.list[0].relationWithHirer}"readonly="true" />
     </td>
     </tr>
      <tr>
      
    <td><bean:message key="lbl.status" /></td>
      <td>
      
      
	<logic:present name="editVal">
	
	 <logic:equal value="A" name="status">
         <input type="checkbox" name=recStatus id="countryStatus" checked="checked" disabled="disabled"/>
       </logic:equal>
      <logic:notEqual value="A" name="status">
         <input type="checkbox" name="recStatus" id="countryStatus" disabled="disabled" />
      </logic:notEqual>
	
	</logic:present>
	
	<logic:present name="save">
	 <logic:equal value="A" name="status">
         <input type="checkbox" name=recStatus id="countryStatus" checked="checked" disabled="disabled" />
       </logic:equal>
      <logic:notEqual value="A" name="status">
         <input type="checkbox" name="recStatus" id="countryStatus" disabled="disabled" />
      </logic:notEqual>
	</logic:present>
      
     </td>
      
    </tr>
	</table>		
</logic:present>

</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		window.close();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		window.close();
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
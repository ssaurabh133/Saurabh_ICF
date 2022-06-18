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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/stockyardMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('stockyardMasterAddForm').stockyardButton.focus();
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
<html:form styleId="stockyardMasterAddForm"  method="post"  action="/repoStockyardMasterDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.stockyardMaster" /></legend>
  <table width="100%">
    <tr>
    
     <td><bean:message key="lbl.stockyard" /><span><font color="red">*</font></span></td>
     <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
    <td>
    <logic:present name="save">
			
			<html:text  property="lbxUserId" styleId="lbxUserId" readonly="true" value="${requestScope.list[0].lbxUserId}"  />
			<html:button property="stockyardButton" styleId="stockyardButton" onclick="closeLovCallonLov1();openLOVCommon(1609,'stockyardMasterAddForm','stockyardDesc','','','','','','hmon');" value=" " styleClass="lovbutton"> </html:button>
			<input type="hidden" id="hmon" value="hmon"/> 
			
</logic:present>
<logic:present name="editVal">
<html:text  property="lbxUserId" styleId="lbxUserId" value="${requestScope.list[0].lbxUserId}" styleClass="text" readonly="true" tabindex="-1"/>
			
			<input type="hidden" id="hmon" value="hmon"/> 
			
</logic:present>
</td>
      <td><bean:message key="lbl.stockyardName" /><span><font color="red">*</font></span></td>
      <td>
      <html:text property="stockyardDesc" styleId="stockyardDesc" maxlength="10" value="${requestScope.list[0].stockyardDesc}" styleClass="text" readonly="true" tabindex="2"/>
     </td>    </tr>
    
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
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnUpdateStockyard('${requestScope.list[0].stockyard}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveStockyard();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("stockyardMasterAddForm").action="repoStockyardMasterDispatch.do?method=searchStockYard";
	    document.getElementById("stockyardMasterAddForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("stockyardMasterAddForm").action="repoStockyardMasterDispatch.do?method=searchStockYard";
	    document.getElementById("stockyardMasterAddForm").submit();
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
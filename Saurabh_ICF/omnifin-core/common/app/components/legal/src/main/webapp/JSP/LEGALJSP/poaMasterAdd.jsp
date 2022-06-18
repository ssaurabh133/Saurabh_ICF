<!--Author Name : Richa Bansal-->
<!--Date of Creation : 11-March-2013-->
<!--Purpose  : Information of court Name Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/poaMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('userButton').focus();
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

<body onload="fntab();">
<html:form styleId="poaMasterForm"  method="post"  action="/poaMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.poaMaster" /></legend>

<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
  <table width="100%">
  <tr>
    <td><bean:message key="lbl.branch" /><span><font color="red">*</font></span></td>
    
	<td><html:text property="branch" styleClass="text" styleId="branch" maxlength="10" onblur="fnChangeCase('courtNameMasterForm','branch')" value="${list[0].branch}" readonly="true" />
	<input type="hidden" name = "lbxBranchId" id="lbxBranchId" value="${list[0].lbxBranchId}" />
	<html:button property="userButton" styleId="userButton" tabindex="1" onclick="openLOVCommon(12,'poaMasterForm','branch','','', '','','','lbxBranchId'); clearCourtName();" value=" " styleClass="lovbutton"></html:button>
	</td>

   <td><input type="hidden" name="hcommon" id="hcommon" /></td>
	
    <td><bean:message key="lbl.courtName" /><span><font color="red">*</font></span></td>

   	<td>
	
	<html:select property="courtName" styleId="courtName" size="5" multiple="multiple" style="width: 120" >
	<logic:present name="courtNameList">
	<html:optionsCollection name="courtNameList" value="courtName" label="courtName" />
	</logic:present>
	</html:select>
	
	<input type="hidden" name = "lbxCourtNameCode" id = "lbxCourtNameCode" value="${requestScope.courtNameID}"/>
	<html:button property="userButton" styleId="userButton1" tabindex="2" 
	onclick="return openMultiSelectLOVCommon(1503,'poaMasterForm',
	'courtName','branch','lbxCourtNameCode', 'lbxBranchId','Please select branch first!!!','','hcommon'); 
	closeLovCallonLov('branch');" value=" " styleClass="lovbutton"></html:button>	   
	</td>
     
    </tr>
    <tr>
        <td><bean:message key="lbl.poa" /><span><font color="red">*</font></span></td>
        <logic:present name="editVal">
      <td><html:text property="userId" styleClass="text" styleId="userId" value="${list[0].userId}" tabindex="-1" readonly="true" />
        
		<html:hidden  property="lbxUserId" styleId="lbxUserId" value="${list[0].lbxUserId}"  />
      </td>  </logic:present>
     
    <logic:present name="save">
      <td><html:text property="userId" styleClass="text" styleId="userId" value="${list[0].userId}"  readonly="true" />
        <html:button property="poabutton" styleId="poabutton" tabindex="3"  onclick="openLOVCommon(33,'poaMasterForm','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
		<html:hidden  property="lbxUserId" styleId="lbxUserId" value="${list[0].lbxUserId}"  />
      </td>  </logic:present>
     

    </tr>
    
    
      <tr>
      
    <td><bean:message key="lbl.status" /></td>
      <td>
      
      
        <logic:present name="editVal">
        
       <logic:equal value="A" name="status">
         <input type="checkbox" name=recStatus id="countryStatus" checked="checked" tabindex="4"/>
       </logic:equal>
      <logic:notEqual value="A" name="status">
         <input type="checkbox" name="recStatus" id="countryStatus" tabindex="4" />
      </logic:notEqual>
      
   </logic:present>
   
   <logic:present name="save">
   
     <input type="checkbox" name=recStatus id="countryStatus" checked="checked" tabindex="4"/>
     
   </logic:present>
      
      
    
      
   </td> </tr><tr><td>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" tabindex="5"onclick="return openEditPOA();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" tabindex="5" onclick="return savePOA();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("poaMasterForm").action="poaMasterBehind.do";
	    document.getElementById("poaMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("poaMasterForm").action="poaMasterBehind.do";
	    document.getElementById("poaMasterForm").submit();
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
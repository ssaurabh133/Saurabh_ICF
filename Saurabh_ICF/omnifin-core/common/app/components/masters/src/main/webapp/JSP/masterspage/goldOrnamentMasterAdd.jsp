<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 17-May-2011-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>	 
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('goldOrnamentMaster').ornamentType.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('goldOrnamentMaster').ornamentStandard.focus();
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
  <html:form styleId="goldOrnamentMaster" action="/goldOrnamentMasterAdd">
 	<html:errors/>
   <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 
 <fieldset>
<legend><bean:message key="lbl.goldOrnamentMaster" /></legend>
  <table width="100%">
  <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
   <logic:iterate id="listObj" name="list">
   <tr>
   <td><bean:message key ="lbl.ornamentType"/></td>
	<td><html:text property="ornamentType" styleClass="text" styleId="ornamentType" readonly="true" maxlength="10" value="${listObj.ornamentType}"/></td>
        <html:hidden  property="ornamentId" styleId="ornamentId" value="${listObj.ornamentId}"/>
	<td><bean:message key ="lbl.ornamentStandard"/></td>
	<td><html:text property="ornamentStandard" styleClass="text" styleId="ornamentStandard" readonly="true" maxlength="10" value="${listObj.ornamentStandard}"/></td>

  </tr>
   <tr>
   <td><bean:message key="lbl.active" /></td>
      <td>
		 
		  <logic:equal value="Active" name="status">
              <input type="checkbox" name="goldORnamentStatus" id="goldORnamentStatus" checked="checked" />
      </logic:equal>
      
         <logic:notEqual value="Active" name="status">
      	 		  <input type="checkbox" name="goldORnamentStatus" id="goldORnamentStatus"  />
         </logic:notEqual></td>	 
		 
      </td>
   
			<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="goldOrnamentLTV" property="goldOrnamentLTV"   maxlength="3" style="text-align: right"  onkeyup="return checkRate('goldOrnamentLTV');" value="${listObj.goldOrnamentLTV}" onkeypress="return isNumberKey(event);" /></td>
		 
   </tr>
    
  
    </logic:iterate>
   </logic:present>
   
    <logic:notPresent name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>

		
		  <TR>
     <td><bean:message key="lbl.ornamentType" /></td>
          <td >
           
           <html:text  property="ornamentType" styleId="ornamentType" styleClass="text" readonly="true" value="${listObj.ornamentType}" tabindex="-1"/>
          <html:hidden  property="lbxOrnamentType" styleId="lbxOrnamentType" value="${listObj[0].lbxOrnamentType}"/>
          <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(6079,'goldOrnamentMaster','ornamentType','','ornamentStandard','','','','ornamentId','lbxOrnamentType')" value=" " styleClass="lovbutton"> </html:button>
            <input type="hidden" id="ornamentId" name="ornamentId"/>
	   </td>
          <td><bean:message key="lbl.ornamentStandard" /></td>
          <td>
          <html:text property="ornamentStandard" styleId="ornamentStandard" styleClass="text" readonly="true" value="${listObj.ornamentStandard}" tabindex="-1"/>
          		<html:hidden  property="lbxOrnamentStandard" styleId="lbxOrnamentStandard" value="${listObj[0].lbxOrnamentStandard}" />
                <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(6078,'goldOrnamentMaster','ornamentStandard','','','','','','ornamentId','lbxOrnamentType')" value=" " styleClass="lovbutton"> </html:button>
                  <input type="hidden" id="ornamentId" name="ornamentId"/>
            
            </td> 
         <TR> 
		  <tr>
   <td><bean:message key="lbl.active" /></td>
      <td>
         <input type="checkbox" name="goldORnamentStatus" id="goldORnamentStatus" checked="checked" />
      </td>
   
			<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="goldOrnamentLTV" property="goldOrnamentLTV"   maxlength="3" style="text-align: right"  onkeyup="return checkRate('goldOrnamentLTV');" value="${listObj.goldOrnamentLTV}" onkeypress="return isNumberKey(event);" /></td>
   
   </tr>
    
    
   </logic:notPresent>
   
    <tr><td>
    
    <br>
    <logic:present name="editVal">
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditGoldOrnament();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:notPresent name="editVal">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fngoldOrnamentSave();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent> 
   
 
   
    <br></td> </tr>

	</table>		


</fieldset>
</html:form>

<logic:present name="sms">
		<script type="text/javascript">

			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" />");
				document.getElementById("goldOrnamentMaster").action="goldOrnamentMasterBehind.do";
	    		document.getElementById("goldOrnamentMaster").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.dataModify" />");
				document.getElementById("goldOrnamentMaster").action="goldOrnamentMasterBehind.do";
	    		document.getElementById("goldOrnamentMaster").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="E")
					{
						alert("<bean:message key="msg.notepadError" />");
					}
			else if('<%=request.getAttribute("sms").toString()%>'=="EX")
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

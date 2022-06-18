<!--Author Name : Surendra-->
<!--Date of Creation : 17-Oct-2012-->
<!--Purpose  : Information of Sub Dealer Master Ad-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	// document.getElementById('subDealerMaster').subDealerCode.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	//document.getElementById('subDealerMaster').subDealerCode.focus();
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


 <body onload="enableAnchor();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/subDealerMasterAdd" styleId="subDealerMaster" method="POST" >
 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 
 <html:errors/>


<fieldset>

<legend><bean:message key="lbl.subDealerMaster"/></legend>

  <table width="100%" height="86">
    
<logic:present name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
    <tr>
          <td><bean:message key="lbl.subDealerCode"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="subDealerCode" styleClass="text" readonly="true" styleId="subDealerCode" maxlength="20"  onblur="fnChangeCase('subDealerMaster','subDealerCode')" value="${list[0].subDealerCode}" /></label></td>
    	  <html:hidden property="subDealerID" styleId="subDealerCodeModify" value="${list[0].subDealerID}" />
		   <td><bean:message key="lbl.sDealer" /><span><font color="red">*</font></span></td>	<td>
			<html:text property="dealerDes" styleId="dealerDes" styleClass="text" readonly="true" value="${list[0].dealerDes}" />
             <html:button property="dealerButton" styleId="dealerButton" onclick="closeLovCallonLov1();openLOVCommon(466,'subDealerMaster','dealerDes','','', '','','','dealerID');" value=" " styleClass="lovbutton"> </html:button>
			 <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
			 <html:hidden property="lbxdealerID" styleId="lbxdealerID" />
			<html:hidden property="dealerID" styleId="dealerID" />
	
	 </tr>
      
	<tr>
	 <td><bean:message key="lbl.subDealerDes"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="subDealerDes" styleClass="text" styleId="subDealerDes" maxlength="50"  onblur="fnChangeCase('subDealerMaster','subDealerDes')"  value="${list[0].subDealerDes}" /></label></td>	
	 <td> <bean:message key="lbl.subDealerBankAC"/> </td>   

     <td><label><html:text property="subDealerBankAC" styleClass="text" styleId="subDealerBankAC" maxlength="20"  onblur="fnChangeCase('subDealerMaster','subDealerBankAC')" value="${list[0].subDealerBankAC}" /></label></td>
	 
  </tr>      
	<tr><td> <bean:message key="lbl.status"/> </td>
	     <td> <logic:equal value="A" name="status">
              <input type="checkbox" name="subDealerStatus" id="subDealerStatus" checked="checked" />
      </logic:equal>
      
      <logic:notEqual value="A" name="status">
     		  <input type="checkbox" name="subDealerStatus" id="subDealerStatus"  />
      </logic:notEqual></td>   
  </tr>
  </logic:present>
  
  <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/> 
    <tr>
     
          <td><bean:message key="lbl.subDealerCode"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="subDealerCode" styleClass="text" styleId="subDealerCode" maxlength="20"  onblur="fnChangeCase('subDealerMaster','subDealerCode');this.value=removeSpaces1(this.value);" /></label></td>
      
      	    <td><bean:message key="lbl.sDealer" /><span><font color="red">*</font></span></td>	<td>
			<html:text property="dealerDes" styleId="dealerDes" styleClass="text" readonly="true" />
             <html:button property="dealerButton" styleId="dealerButton" onclick="closeLovCallonLov1();openLOVCommon(466,'subDealerMaster','dealerDes','','', '','','','dealerID');" value=" " styleClass="lovbutton"> </html:button>
			 <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
			 <html:hidden property="lbxdealerID" styleId="lbxdealerID" />
			  <html:hidden property="dealerID" styleId="dealerID" />
		</td>	
	
    	     	
	 </tr>
      
	<tr>
	 <td><bean:message key="lbl.subDealerDes"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="subDealerDes" styleClass="text" styleId="subDealerDes" maxlength="50"  onblur="fnChangeCase('subDealerMaster','subDealerDes')"  /></label></td>	
	 <td> <bean:message key="lbl.subDealerBankAC"/> </td>     

     <td><label><html:text property="subDealerBankAC" styleClass="text" styleId="subDealerBankAC" maxlength="20"  onblur="fnChangeCase('subDealerMaster','subDealerBankAC')"  /></label></td>
	 
   
     
     
  </tr><tr>
   <td> <bean:message key="lbl.status"/> </td>
     <td>
      
              <input type="checkbox" name="subDealerStatus" id="subDealerStatus" checked="checked" />
     </td>
  </tr> </logic:notPresent>
   
  
  <tr><td>
     <logic:present name="editVal">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditSubDealer();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    
     <logic:present name="editValUpdate">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditSubDealer();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnSaveSubDealer();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>
  
		
	</table>		

</fieldset></html:form>

<logic:present name="sms">
		<script type="text/javascript">


    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("subDealerMaster").action="subDealerBehind.do";
	    document.getElementById("subDealerMaster").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("subDealerMaster").action="subDealerBehind.do";
	    document.getElementById("subDealerMaster").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	} 
	
	</script>
</logic:present>
</body>
</html:html>

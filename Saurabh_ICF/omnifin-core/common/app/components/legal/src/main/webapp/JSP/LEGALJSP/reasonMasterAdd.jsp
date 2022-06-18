<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 19-May-2011-->
<!--Purpose  : Information of Reason Master1-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalReason.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('reasonMaster').chargeFlag.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('reasonMaster').reasonTypeButton.focus();
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
  <html:form styleId="reasonMaster" action="/legalReasonMasterAdd">
 	<html:errors/>
   <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 
  <fieldset>

	<legend><bean:message key="lbl.reasonMaster"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="0" cellspacing="0">
 
        
      <html:hidden  property="reasonId" styleId="reasonId"  value="${requestScope.list[0].reasonId}" />
 <logic:present name="editVal">
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
 <tr>
    <td><bean:message key="lbl.reasonTypeDesc"/><span><font color="red">*</font></span></td>
    <td><html:text property="reasonType" styleId="reasonType" styleClass="text" readonly="true" value="${requestScope.list[0].lbxReason}"/> </td>
	<td><bean:message key="lbl.reasonShortcode"/><span><font color="red">*</font></span></td>
    <td><html:text property="reasonShortcode" styleId="reasonShortcode" styleClass="text" maxlength="5" onblur="fnChangeCase('reasonMaster','reasonShortcode')" value="${requestScope.list[0].reasonShortcode}"/></td>
	</tr>
	
	<logic:notPresent name="editVal"><img  src="<%= request.getContextPath()%>/images/lov.gif"></logic:notPresent>	 
     <html:hidden  property="lbxReason" styleId="lbxReason"  value="${requestScope.list[0].reasonType}" />
          
   
   <tr>
   <td><bean:message key="lbl.reasonDesc"/><span><font color="red">*</font></span></td>
    <td><html:text property="reasonDes" styleId="reasonDes" styleClass="text" maxlength="200" readonly="true" onblur="fnChangeCase('reasonMaster','reasonDes')" value="${requestScope.list[0].reasonDes}"/></td> 
    
    </tr>
    <tr>
    
    	<td><bean:message key="lbl.notice" /><span><font color="red">*</font></span></td>
		<td>
		<html:text property="notice" styleId="notice" value="${requestScope.list[0].notice}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxNoticeCode" styleId="lbxNoticeCode" value="${requestScope.list[0].lbxNoticeCode}"  />
		<html:button property="noticeButton" styleId="noticeButton" onclick="closeLovCallonLov1();openLOVCommon(1521,'reasonMaster','notice','','','','','','lbxNoticeCode');" value=" " styleClass="lovbutton"> </html:button>
		</td> 
		
		<td><bean:message key="lbl.legalCaseType" /><span><font color="red">*</font></td>
		<td>
		<html:text property="caseType" styleId="caseType" value="${requestScope.list[0].caseType}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCaseTypeCode" styleId="lbxCaseTypeCode" value="${requestScope.list[0].lbxCaseTypeCode}"  />
		<html:button property="caseTypeButton" styleId="caseTypeButton" onclick="closeLovCallonLov1();openLOVCommon(1524,'reasonMaster','caseType','','','','','','lbxCaseTypeCode');" value=" " styleClass="lovbutton"> </html:button>
		</td>
 
    </tr>
    



<tr>
	<%--  <td><bean:message key="lbl.chargeFlag"/></td>
	 <td>
	 		<logic:present name="checkbox">
	 			<logic:equal value="Yes" name="chargeFlag">
            		<input type="checkbox" name="chargeFlag" id="chargeFlag"  checked="checked" />
      			</logic:equal>     
         		<logic:notEqual value="Yes" name="chargeFlag">
      	 		  	<input type="checkbox" name="chargeFlag"  id="chargeFlag"  />
      	 	 	</logic:notEqual>
      	 	 </logic:present>
      	 	 <logic:notPresent name="checkbox">
      	 	 	<input type="checkbox" name="chargeFlag"  id="chargeFlag"  disabled="disabled"/>
      	 	 </logic:notPresent>      	 	 
     </td> --%>
     <td><bean:message key="lbl.recStatus"/> </td>
        <td >
     			 <logic:equal value="Active" name="status">
              			<input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Active" name="status">
      	 		  <input type="checkbox" name="recStatus" id="recStatus"  />
         </logic:notEqual></td>
  
    </tr>
    
   
   
     </logic:present>
     
     <logic:notPresent name="editVal">
     
<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
 <tr>
    <td><bean:message key="lbl.reasonTypeDesc"/><span><font color="red">*</font></span></td>
    <td><html:text property="reasonType" styleId="reasonType" styleClass="text" readonly="true" tabindex="-1"/>
        <html:button property="reasonTypeButton" styleId="reasonTypeButton" onclick="openLOVCommon(27,'reasonMaster','reasonType','','', '','','','lbxReason');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button> </td>
       <td> <bean:message key="lbl.reasonShortcode"/><span><font color="red">*</font></span></td>
    <td> <html:text property="reasonShortcode" styleId="reasonShortcode" styleClass="text" maxlength="5" onblur="fnChangeCase('reasonMaster','reasonShortcode')" value=""/> 
	</tr>
	
	
	<%--<img onClick="openLOVCommon(27,'reasonMaster','reasonType','','', '','','fndisable','lbxReason')" src="<%= request.getContextPath()%>/images/lov.gif"></td> --%>
     <html:hidden  property="lbxReason" styleId="lbxReason"/>
        <tr>     
    <td><bean:message key="lbl.reasonDesc"/><span><font color="red">*</font></span></td>
    <td><html:text property="reasonDes" styleId="reasonDes" styleClass="text" maxlength="200" onblur="fnChangeCase('reasonMaster','reasonDes')" value=""/>
     </tr>
  
    
   <tr> 
    <td><bean:message key="lbl.notice" /><span><font color="red">*</font></span></td>
		<td>
		<html:text property="notice" styleId="notice" value="${requestScope.list[0].notice}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxNoticeCode" styleId="lbxNoticeCode" value="${requestScope.list[0].lbxNoticeCode}"  />
		<html:button property="noticeButton" styleId="noticeButton" onclick="closeLovCallonLov1();openLOVCommon(1521,'reasonMaster','notice','','','','','','lbxNoticeCode');" value=" " styleClass="lovbutton"> </html:button>
		</td> 
		
		<td><bean:message key="lbl.legalCaseType" /><span><font color="red">*</font></td>
		<td>
		<html:text property="caseType" styleId="caseType" value="${requestScope.list[0].caseType}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCaseTypeCode" styleId="lbxCaseTypeCode" value="${requestScope.list[0].lbxCaseTypeCode}"  />
		<html:button property="caseTypeButton" styleId="caseTypeButton" onclick="closeLovCallonLov1();openLOVCommon(1524,'reasonMaster','caseType','','','','','','lbxCaseTypeCode');" value=" " styleClass="lovbutton"> </html:button>
		</td>
 
    </tr>
 






<tr>
  <%--   <td><bean:message key="lbl.chargeFlag"/></td>
    <td><input type="checkbox" name="chargeFlag" id="chargeFlag"  onclick="fndisable();"/></td>   --%>  
        
    <td><bean:message key="lbl.recStatus"/> </td>
    <td><input type="checkbox" name="recStatus" id="recStatus" checked="checked" /></td>
  
    </tr>
   
    
   
     </logic:notPresent>
     
  <tr>
	 <logic:present name="editVal">
	 	 <td><button type="button"  name="save" title="Alt+V" accesskey="V" id="save" onclick="return fnEditReason();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
	 </logic:present>
	 
	 <logic:notPresent name="editVal">
	  <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
		<td><button type="button"  name="save" title="Alt+V" accesskey="V" id="save" onclick="return fnreasonSave();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
	 </logic:notPresent>	
	 
 </tr>
 </table></td>
  </tr></table></fieldset>
</html:form>

<logic:present name="sms">
		<script type="text/javascript">

			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" />");
				document.getElementById("reasonMaster").action="legalReasonMasterBehind.do";
	    		document.getElementById("reasonMaster").submit();
			}
			 else if('<%=request.getAttribute("sms").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.dataModify" />");
				document.getElementById("reasonMaster").action="legalReasonMasterBehind.do";
	    		document.getElementById("reasonMaster").submit();
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

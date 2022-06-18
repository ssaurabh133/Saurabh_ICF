<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 23-May-2011-->
<!--Purpose  : Information of State Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/asset.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('stateMasterForm').stateDes.focus();
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
<body onload="enableAnchor();">
<html:form styleId="stateMasterForm"  method="post"  action="/stateMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<fieldset>
<legend><bean:message key="lbl.stateMaster" /></legend>
  <table width="100%">
    <tr>
   
	<html:hidden  property="stateId" styleId="stateId"  value="${requestScope.list[0].stateId}" />

     
 <logic:present name="editVal">
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
      <td><bean:message key="lbl.stateDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="stateDes" styleClass="text" styleId="stateDes"  maxlength="50" onblur="fnChangeCase('stateMasterForm','stateDes')" value="${requestScope.list[0].stateDes}" /></td>
      <td> </td>      
      <td><bean:message key="lbl.country" /><span><font color="red">*</font></span></td>
      <td><html:text property="countryId" readonly="true" tabindex="-1" styleClass="text" styleId="countryId" value="${requestScope.list[0].countryId}" />
	 <html:button property="countryButton" styleId="countryButton" onclick="openLOVCommon(4,'stateMasterForm','countryId','','', '','','','txtCountryCode');closeLovCallonLov1();" value=" " styleClass="lovbutton"  ></html:button>
	 <%--<img onClick="openLOVCommon(4,'stateMasterForm','countryId','','', '','','','txtCountryCode')" src="<%= request.getContextPath()%>/images/lov.gif"></td> --%> 
	  <html:hidden  property="txtCountryCode" styleId="txtCountryCode"  value="${requestScope.list[0].txtCountryCode}"/>
      
 </logic:present>
  <tr>
 <logic:notPresent name="editVal">
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
     <td><bean:message key="lbl.stateDesc" /><span><font color="red">*</font></span></td>
     <td><html:text property="stateDes" styleClass="text" styleId="stateDes" maxlength="50" onblur="fnChangeCase('stateMasterForm','stateDes')"  /></td>
       <td> </td>
      <td><bean:message key="lbl.country" /><span><font color="red">*</font></span></td>
      <td>
      <logic:notPresent name="defaultcountry">
      <html:text property="countryId" readonly="true" styleClass="text" styleId="countryId" tabindex="-1"/>
      </logic:notPresent>
      <logic:present name="defaultcountry">
      	<html:text property="countryId" readonly="true" tabindex="-1" styleClass="text" styleId="countryId" value="${defaultcountry[0].defaultcountryname}" />
      </logic:present>
	 <html:button property="countryButton" styleId="countryButton" onclick="openLOVCommon(4,'stateMasterForm','countryId','','', '','','','txtCountryCode');closeLovCallonLov1();" value=" " styleClass="lovbutton"  ></html:button>
	 <%--<img onClick="openLOVCommon(4,'stateMasterForm','countryId','','', '','','','txtCountryCode')" src="<%= request.getContextPath()%>/images/lov.gif"></td> --%> 
	   <logic:notPresent name="defaultcountry">
	  <html:hidden  property="txtCountryCode" styleId="txtCountryCode" />
	  </logic:notPresent>
 	<logic:present name="defaultcountry">
	  <html:hidden  property="txtCountryCode" styleId="txtCountryCode"  value="${defaultcountry[0].defaultcountryid}"/>
      </logic:present>
 </logic:notPresent>
    	 
    </tr>
    
      <tr>
       <!-- Changes Start by Richa -->     
       <td><bean:message key="lbl.VatPercent" /><span><font color="red">*</font></span></td>
         <logic:notPresent name="editVal">
      			<td>
	      			<html:text property="vatPercent" styleClass="text" styleId="vatPercent"  onfocus="keyUpNumber(this.value, event, 2,id);"
	      			 onblur="formatNumber(this.value,id);" onkeypress="return numbersonly1(event,id,2);" 
	      			 onkeyup="checkNumber1(this.value, event, 2,id);"  value="${requestScope.list[0].vatPercent}"  />
      			</td>
         </logic:notPresent>
         <logic:present name="editVal">
              <td><html:text property="vatPercent" styleClass="text" styleId="vatPercent"   value="${requestScope.list[0].vatPercent}"  onfocus="keyUpNumber(this.value, event, 2,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly1(event,id,2);" onkeyup="checkNumber1(this.value, event, 2,id);"/></td>
         </logic:present>
           <td> </td>             
             <td><bean:message key="lbl.serviceTax" /><span><font color="red">*</font></td>
        <logic:notPresent name="editVal">
      			<td><html:text property="serviceTax" styleClass="text" styleId="serviceTax"  onfocus="keyUpNumber(this.value, event, 2,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly1(event,id,2);" onkeyup="checkNumber1(this.value, event, 2,id);"  value="${requestScope.list[0].serviceTax}"  /></td>
         </logic:notPresent>
         <logic:present name="editVal">
              <td><html:text property="serviceTax" styleClass="text" styleId="serviceTax"   value="${requestScope.list[0].serviceTax}"  onfocus="keyUpNumber(this.value, event, 2,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly1(event,id,2);" onkeyup="checkNumber1(this.value, event, 2,id);"/></td>
             </logic:present>
      <!-- Changes end by Richa -->
    <tr>
      <td width="13%"><bean:message key="lbl.gstno" /> </td>
      <td width="13%"><html:text property="gstId" style="text-align: right" styleClass="text" styleId="gstId" value="${requestScope.list[0].gstId}" maxlength="50" />
     </td>
	 
	 
	                          <td> </td>

             <td><bean:message key="lbl.unionterritory"/></td>
			
            <td> <html:select property="unionterritory" styleId="unionterritory" value="${requestScope.list[0].unionterritory}" >
                <html:option value="N">NO</html:option>
                 <html:option value="Y">YES</html:option>
			</html:select>
            </td>
			 <tr>
     <td><bean:message key="lbl.gstcode" /> </td>
     <td><html:text property="gstCode" style="text-align: right" styleClass="text" styleId="gstCode" value="${requestScope.list[0].gstCode}" maxlength="50" />
     </td>
	   <td> </td>	  
    <td><bean:message key="lbl.status" /></td>
      <td> 
      <logic:notPresent name="editVal">
         <input type="checkbox" name="stateStatus" id="stateStatus"  checked="checked"/>
      </logic:notPresent>
      
      <logic:present name="editVal">
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="stateStatus" id="stateStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="stateStatus" id="stateStatus"  />
      </logic:notEqual>
      </logic:present>
      </td>
      
	 </tr>

       
    <table>
    
    <logic:present name="editVal">
      <button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return fnEditState('${requestScope.list[0].stateId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return saveState();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("stateMasterForm").action="stateMasterBehind.do";
	    document.getElementById("stateMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("stateMasterForm").action="stateMasterBehind.do";
	    document.getElementById("stateMasterForm").submit();
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
<!--Author Name : Yogesh
Date of Creation : 27-April-2011
-->

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
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/bankBranchMaster.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('bankBranchMasterForm').bankBranchName.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('bankBranchMasterForm').bankBranchCode.focus();
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
<html:form styleId="bankBranchMasterForm" action="/bankbranchAction" >
  <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 <input type="hidden" name="hcommon" id="hcommon" value="" /> 
 <input type="hidden" name="bankbranchSearchId" id="bankbranchSearchId" value="${bankbranchSearchId}" /> 
 
<fieldset>
<legend><bean:message key="lbl.bankBranchMaster" /></legend>
  <table width="100%">
    <tr>
      <logic:present name="modify">
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
 <!--  
	  <td><bean:message key="lbl.bankBranchCode" /><span><font color="red">*</font></span></td>
       <td><html:text property="bankBranchCode" styleClass="text" styleId="bankBranchCode" readonly="true" maxlength="10" onblur="fnChangeCase('bankBranchMasterForm','bankBranchCode')" value="${requestScope.list[0].bankBranchCode}" />
     </td>
  -->   
      <td><bean:message key="lbl.bankBranchName" /><span><font color="red">*</font></span></td>
      <td><html:text property="bankBranchName" styleClass="text" styleId="bankBranchName" maxlength="50" onblur="fnChangeCase('bankBranchMasterForm','bankBranchName')" value="${requestScope.list[0].bankBranchName}" /></td>
	  
     <td><bean:message key="lbl.branchMICRCode"/></td>
     <td><html:text property="branchMICRCode" styleId="branchMICRCode" maxlength="9" onkeypress="return isNumberKey(event);" styleClass="text"  value="${requestScope.list[0].branchMICRCode}"/></td>
 

     </logic:present>
     <logic:notPresent name="modify">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
    <!-- 
      <td><bean:message key="lbl.bankBranchCode" /><span><font color="red">*</font></span></td>
     <td><html:text property="bankBranchCode" styleClass="text" styleId="bankBranchCode" maxlength="10" onblur="fnChangeCase('bankBranchMasterForm','bankBranchCode')" />
     </td>
     -->
     
     <td><bean:message key="lbl.bankBranchName" /><span><font color="red">*</font></span></td>
      <td><html:text property="bankBranchName" styleClass="text" styleId="bankBranchName" maxlength="50" onblur="fnChangeCase('bankBranchMasterForm','bankBranchName')" /></td>
	  
     <td><bean:message key="lbl.branchMICRCode"/></td>
     <td><html:text property="branchMICRCode" styleId="branchMICRCode" maxlength="9" onkeypress="return isNumberKey(event);" styleClass="text" /></td>
 
     </logic:notPresent>
      </tr>
     <logic:present name="modify">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
  
    <tr>
    <td><bean:message key="lbl.branchIFCSCode"/></td>
   <td><html:text property="branchIFCSCode"  styleId="branchIFCSCode" maxlength="11" styleClass="text" value="${requestScope.list[0].branchIFCSCode}"/></td>
    
   <td><bean:message key="lbl.bank"/><font color="red">*</font></td>
   <td><html:text property="bank" styleId="bank" styleClass="text" maxlength="10" value="${requestScope.list[0].bank}" readonly="true" tabindex="-1"/>
   <html:hidden  property="lbxBankID" styleId="lbxBankID"  value="${requestScope.list[0].lbxBankID}"/>
   <html:button property="bankButton" styleId="bankButton" onclick="openLOVCommon(7,'bankBranchMasterForm','bank','','', '','','','lbxBankID');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
   <%-- <img onClick="openLOVCommon(7,'bankBranchMasterForm','bank','','', '','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
     </td>
    
    <td><bean:message key="lbl.contactPerson"/></td>
   <td><html:text property="contactPerson" styleId="contactPerson" maxlength="50" styleClass="text" value="${requestScope.list[0].contactPerson}"/></td>
  </tr>
  
  <tr>
    <td><bean:message key="lbl.personDesignation"/> </td>
    <td><html:text property="personDesignation" styleId="personDesignation" maxlength="50"  styleClass="text" value="${requestScope.list[0].personDesignation}"/></td>
    
    <td><bean:message key="lbl.address1"/></td>
    <td><html:text property="address1" styleId="address1" styleClass="text" maxlength="50" value="${requestScope.list[0].address1}"/></td>
    
    <td><bean:message key="lbl.address2"/></td>
    <td><html:text property="address2" styleId="address2" styleClass="text" maxlength="50" value="${requestScope.list[0].address2}"/></td>
  </tr>
  <tr>
    <td><bean:message key="lbl.address3"/></td>
    <td><html:text property="address3" styleId="address3" styleClass="text" maxlength="50" value="${requestScope.list[0].address3}"/></td>
    
    <td><bean:message key="lbl.country"/></td>
    <td><html:text property="country" styleId="country" maxlength="10" styleClass="text" value="${requestScope.list[0].country}" readonly="true" tabindex="-1"/>
    <html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChildMaster','txtCountryCode');" value=" " styleClass="lovbutton"> </html:button>
      <html:hidden  property="tahsil" styleId="tahsil" value="" />
      <html:hidden  property="tahsilDesc" styleId="tahsilDesc" value="" />
    <html:hidden  property="txtCountryCode" styleId="txtCountryCode" value="${requestScope.list[0].txtCountryCode}" /></td>
       
    <td><bean:message key="lbl.state"/></td>
    <td><html:text property="state" styleId="state" styleClass="text" maxlength="10" value="${requestScope.list[0].state}" readonly="true" tabindex="-1"/>
    <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'bankBranchMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChildMaster','txtStateCode');closeLovCallonLov('country');" value=" " styleClass="lovbutton"> </html:button>
   <%--  <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(5,'bankBranchMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChild','hcommon');"> --%>
    <html:hidden  property="tahsil" styleId="tahsil" value="" />
     <html:hidden  property="tahsilDesc" styleId="tahsilDesc" value="" />
    <html:hidden  property="txtStateCode" styleId="txtStateCode" value="${requestScope.list[0].txtStateCode}" /></td>
     
  </tr>
  <tr>
    <td><bean:message key="lbl.city"/></td>
    <td><html:text property="dist" styleId="dist" styleClass="text" maxlength="10" value="${requestScope.list[0].dist}" readonly="true" tabindex="-1"/>
    <html:button property="cityButton" styleId="cityButton" onclick="openLOVCommon(20,'bankBranchMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','txtDistCode');closeLovCallonLov('state');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(20,'bankBranchMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','hcommon');"> --%>
    <html:hidden  property="txtDistCode" styleId="txtDistCode" value="${requestScope.list[0].txtDistCode}" /></td>
    
    <td><bean:message key="lbl.zipCode"/> </td>
    <td><html:text property="zipCode" styleId="zipCode" style="text-align: right" maxlength="6" styleClass="text" value="${requestScope.list[0].zipCode}"/></td>
   
    <td><bean:message key="lbl.phone1"/></td>
    <td><html:text property="phone1" style="text-align: right" styleId="phone1" maxlength="10" styleClass="text" value="${requestScope.list[0].phone1}"/></td>
  </tr>
  <tr>
    <td><bean:message key="lbl.phone2"/></td>
    <td><html:text property="phone2" styleId="phone2" maxlength="10" style="text-align: right" styleClass="text" value="${requestScope.list[0].phone2}"/></td>
    
    <td><bean:message key="lbl.fax"/></td>
    <td><html:text property="fax" styleId="fax" style="text-align: right" styleClass="text" maxlength="20" value="${requestScope.list[0].fax}"/></td>
    
    <td><bean:message key="lbl.email"/></td>
    <td><html:text property="email" styleId="email" styleClass="text" maxlength="100" value="${requestScope.list[0].email}"/></td>

  </tr>
  <tr>
      
    <td><bean:message key="lbl.active" /></td>
      <td>
      <logic:equal value="Active" name="status">
      
         <input type="checkbox" name="bankBranchStatus" id="bankBranchStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">

         <input type="checkbox" name="bankBranchStatus" id="bankBranchStatus"  />
      </logic:notEqual></td>
        <td><bean:message key="lbl.ECS" /></td>
      <td>
      <logic:equal value="A" name="ECS">

         <input type="checkbox" name="ecsStatus" id="ecsStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="ECS">
     
         <input type="checkbox" name="ecsStatus" id="ecsStatus"  />
      </logic:notEqual></td>
        <%--Rohit Changes for ACH Starts --%>
      <td><bean:message key="lbl.achStatus" /></td>
      <td>
      <logic:equal value="A" name="ACH">

         <input type="checkbox" name="achStatus" id="achStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="ACH">
     
         <input type="checkbox" name="achStatus" id="achStatus"  />
      </logic:notEqual></td>
      <%--	Rohit End --%>
    </tr>
 </logic:present>


<logic:notPresent name="modify">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>   
  
    <tr>
   <td><bean:message key="lbl.branchIFCSCode"/></td>
   <td><html:text property="branchIFCSCode"  styleId="branchIFCSCode" maxlength="11" styleClass="text" /></td>
    
   <td><bean:message key="lbl.bank"/><font color="red">*</font></td>
   <td><html:text property="bank" styleId="bank" styleClass="text" maxlength="10" readonly="true" tabindex="-1"/>
   <html:hidden  property="lbxBankID" styleId="lbxBankID"  />
   <html:button property="bankButton" styleId="bankButton" onclick="openLOVCommon(7,'bankBranchMasterForm','bank','','', '','','','lbxBankID');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
   <%-- <img onClick="openLOVCommon(7,'bankBranchMasterForm','bank','','', '','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>
   </td>
   <td><bean:message key="lbl.contactPerson"/></td>
   <td><html:text property="contactPerson" styleId="contactPerson" maxlength="50" styleClass="text"/></td>
  </tr>
  
  <tr>
    <td><bean:message key="lbl.personDesignation"/> </td>
    <td><html:text property="personDesignation" styleId="personDesignation" maxlength="50"  styleClass="text" /></td>
    
    <td><bean:message key="lbl.address1"/></td>
    <td><html:text property="address1" styleId="address1" styleClass="text" maxlength="50" /></td>
    
    <td><bean:message key="lbl.address2"/></td>
    <td><html:text property="address2" styleId="address2" styleClass="text" maxlength="50" /></td>
  </tr>
  <tr>
    <td><bean:message key="lbl.address3"/></td>
    <td><html:text property="address3" styleId="address3" styleClass="text" maxlength="50" /></td>
    
    <td><bean:message key="lbl.country"/></td>
    <td>
    <logic:present name="defaultcountry">
    <html:text property="country" styleId="country" maxlength="10" styleClass="text" value="${defaultcountry[0].defaultcountryname}" readonly="true" tabindex="-1"/>
    </logic:present>
      <logic:notPresent name="defaultcountry">
      <html:text property="country" styleId="country" maxlength="10" styleClass="text" readonly="true" tabindex="-1"/>
      </logic:notPresent>
    
   
  
    <html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChildMaster','txtCountryCode');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(4,'bankBranchMasterForm','country','','','','','clearCountryLovChild','hcommon');"> --%>
     <html:hidden  property="tahsil" styleId="tahsil" value="" />
      <html:hidden  property="tahsilDesc" styleId="tahsilDesc" value="" />
     <logic:present name="defaultcountry">
    <html:hidden  property="txtCountryCode" styleId="txtCountryCode" value="${defaultcountry[0].defaultcountryid}" />
      </logic:present>
      <logic:notPresent name="defaultcountry">
        <html:hidden  property="txtCountryCode" styleId="txtCountryCode"  />
      </logic:notPresent>
</td>
    
     
    <td><bean:message key="lbl.state"/></td>
    <td><html:text property="state" styleId="state" styleClass="text" maxlength="10" readonly="true" tabindex="-1"/>
    <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'bankBranchMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChildMaster','hcommon');closeLovCallonLov('country');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(5,'bankBranchMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first','clearStateLovChild','hcommon');"> --%>
    <html:hidden  property="tahsil" styleId="tahsil" value="" />
     <html:hidden  property="tahsilDesc" styleId="tahsilDesc" value="" />
    <html:hidden  property="txtStateCode" styleId="txtStateCode"  /></td>  </tr>
  <tr>
    <td><bean:message key="lbl.city"/></td>
    <td><html:text property="dist" styleId="dist" styleClass="text" maxlength="10" readonly="true" tabindex="-1"/>
    <html:button property="cityButton" styleId="cityButton" onclick="openLOVCommon(20,'bankBranchMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','hcommon');closeLovCallonLov('state');" value=" " styleClass="lovbutton"> </html:button>
    <%-- <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(20,'bankBranchMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first','','hcommon');">--%>
    <html:hidden  property="txtDistCode" styleId="txtDistCode" /></td>
    
    <td><bean:message key="lbl.zipCode"/> </td>
    <td><html:text property="zipCode" styleId="zipCode" maxlength="6" style="text-align: right" styleClass="text" /></td>
   
    <td><bean:message key="lbl.phone1"/></td>
    <td><html:text property="phone1" styleId="phone1" maxlength="10" style="text-align: right" styleClass="text" /></td>
  </tr>
  <tr>
    <td><bean:message key="lbl.phone2"/></td>
    <td><html:text property="phone2" styleId="phone2" maxlength="10" style="text-align: right" styleClass="text" /></td>
    <td><bean:message key="lbl.fax"/></td>
    <td><html:text property="fax" styleId="fax" styleClass="text" style="text-align: right" maxlength="20" /></td>
    <td><bean:message key="lbl.email"/></td>
    <td><html:text property="email" styleId="email" styleClass="text" maxlength="100" /></td>

  </tr>
  <tr>
      
    <td><bean:message key="lbl.active" /></td>
      <td>
         <input type="checkbox" name="bankBranchStatus" id="bankBranchStatus" checked="checked" />
      </td>
      
      <td><bean:message key="lbl.ECS" /></td>
      <td>
      <logic:equal value="A" name="ECS">
    
         <input type="checkbox" name="ecsStatus" id="ecsStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="ECS">
    
         <input type="checkbox" name="ecsStatus" id="ecsStatus"  />
      </logic:notEqual></td>
        <%--Rohit Changes for ACH starts --%>
      <td><bean:message key="lbl.achStatus" /></td>
      <td>
      <logic:equal value="A" name="ACH">

         <input type="checkbox" name="achStatus" id="achStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="ACH">
     
         <input type="checkbox" name="achStatus" id="achStatus"  />
      </logic:notEqual></td>
      <%--	Rohit End --%>
    </tr>
 </logic:notPresent>
  
  <tr><td>
    
    <logic:present name="modify">
             <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return bankBranchModify('${requestScope.list[0].bankBranchId}');" class="topformbutton2" ><bean:message key="button.save" /></button> 
   </logic:present>
   
   <logic:notPresent name="modify">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
              <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return bankBranchSubmit();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
    <br></td> 
         </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="msg">
		<script type="text/javascript">
		
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("bankBranchMasterForm").action="bankBranchSearchBehind.do";
	    document.getElementById("bankBranchMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("bankBranchMasterForm").action="bankBranchSearchBehind.do";
	    document.getElementById("bankBranchMasterForm").submit();
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='EXIT')
	{
		alert("<bean:message key="lbl.ifscCodeExist" />");
		
	}
	
	else
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>
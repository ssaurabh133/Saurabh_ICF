<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="com.login.roleManager.UserObject;"  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/salesExecutiveMaster.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>



	</head>
	<body >
 	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<html:form action="/salesExecutiveMasterAdd" styleId="salesExecutiveMasterForm" method="post" >
 	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
     <html:hidden  property="bpEmpUniqueId" styleId="bpEmpUniqueId" value="${requestScope.list[0].bpEmpUniqueId}"/>
	<fieldset>
	<legend><bean:message key="lbl.salesExecutiveMaster" /></legend>

 	 <table width="100%" height="86">
   		 <tr>
	 	 <td><bean:message key="lbl.name"/><span><font color="red">*</font></span></td>
   		 <td>
   		 
   		  <html:text property="businessPartnerName" styleId="businessPartnerName" styleClass="text" readonly="true" value="${requestScope.list[0].businessPartnerName}"/>
   		  <html:hidden  property="lbxBusinessPartnerId" styleId="lbxBusinessPartnerId" value="${requestScope.list[0].lbxBusinessPartnerId}"/>
          <html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(2052,'salesExecutiveMasterForm','businessPartnerName','','', '','','','businessPartnerName','businessPartnerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>									
    	   		  
   		  </td>

         <td>
			<bean:message key="lbl.type" /><span><font color="red">*</font></span>
		</td>
	       <td>
			 <html:select property="employeeType" value="${requestScope.list[0].employeeType}" styleClass="text" styleId="employeeType">
				<html:option value="">--Select--</html:option>
				<logic:present name="typeList">
					<logic:notEmpty name="typeList">
					<html:optionsCollection name="typeList" label="name" value="id" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
            </td>

          </tr>

	 	<tr>
	
         <td><bean:message key="lbl.employeeName"/><span><font color="red">*</font></span></td>
         <td><html:text property="employeeName" styleId="employeeName" styleClass="text" maxlength="50"  value="${requestScope.list[0].employeeName}"/></td>
	   
	     <td ><bean:message key="lbl.bankName"/><span><font color="red">*</font></span></td>
      	<td >		
		  <html:text styleClass="text" property="bank" styleId="bank"  value="${requestScope.list[0].bank}" readonly="true" />
          <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${requestScope.list[0].lbxBankID}"/>
          <html:button property="bankButton" styleId="bankButton" onclick="openLOVCommon(2053,'salesExecutiveMasterForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>	
    	</td>
	   
	   </tr>
	    <tr>
	
	   <td><bean:message key="lbl.bankBranchName"/><span><font color="red">*</font></span></td>
   		 <td>
   		     <html:text styleClass="text" property="branch" styleId="branch" maxlength="100" value="${requestScope.list[0].branch}" readonly="true"/>
     	     <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${requestScope.list[0].lbxBranchID}"/>
     	  	 <html:button property="branchButton" styleId="branchButton" onclick="openLOVCommon(2054,'salesExecutiveMasterForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micrCode','ifscCode')" value=" " styleClass="lovbutton"></html:button>
		  </td>   
		    <td><bean:message key="lbl.bankAccountNo"/><span><font color="red">*</font></span></td>
    	    <td><html:text property="bankAccountNo" styleId="bankAccountNo" styleClass="text"  value="${requestScope.list[0].bankAccountNo}"/></td>
	    </tr>
	     <tr>
	     <td><bean:message key="lbl.ifsCode"/><span><font color="red">*</font></span></td>
         <td><html:text property="ifscCode" styleId="ifscCode" styleClass="text" readonly="true"  value="${requestScope.list[0].ifscCode}"/></td>
	   
	 
	     <td><bean:message key="lbl.micrCode"/><span><font color="red">*</font></span></td>
         <td><html:text property="micrCode" styleId="micrCode" styleClass="text" readonly="true" value="${requestScope.list[0].micrCode}"/></td>
	   
	    </tr>
	 
  
	    <tr>
	    <td><bean:message key="lbl.bmStatus"/> </td>   
          <td>
     
               <logic:equal value="A" name="status">
              			<input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
      			</logic:equal>
      
               <logic:notEqual value="A" name="status">
      	 		  <input type="checkbox" name="recStatus" id="recStatus"  />
               </logic:notEqual>
          </td>
        </tr>
        <tr>
          <td>
        <logic:notPresent name="editVal">
          <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnSaveSalesExecutiveMaster();" class="topformbutton2" ><bean:message key="button.save" /></button>
        </logic:notPresent>
         <logic:present name="editVal">
             <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnUpdateSalesExecutiveMaster();" class="topformbutton2" ><bean:message key="button.save" /></button>
  		</logic:present>
  		</td>
        </tr>
 		
	</table>		

	</fieldset>
	
</html:form>


		<logic:present name="sms">
		<script type="text/javascript">

   			 if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" />");
				location.href="<%=request.getContextPath()%>/salesExecutiveMaster.do";
			}
	
			else if('<%=request.getAttribute("sms").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.dataModify" />");
				location.href="<%=request.getContextPath()%>/salesExecutiveMaster.do";

			}
			else if('<%=request.getAttribute("sms").toString()%>'=='E')
			{
				alert("<bean:message key="msg.notepadError" />");
		        location.href="<%=request.getContextPath()%>/salesExecutiveMaster.do";
			} 
		
	
	
		</script>
	</logic:present>


	</body>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
	</div>
	</html:html>

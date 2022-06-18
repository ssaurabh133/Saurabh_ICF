<!--Author Name : Ritu Jindal-->
<!--Date of Creation :23 May 2011 -->
<!--Purpose  : Information of User Master Add-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>

<script type="text/javascript">
  $(function() {
				    	$("#validityDate").datepicker({
				    			changeMonth: true,
				    		changeYear: true,
				            yearRange: '1900:+10',
				            showOn: 'both',
				            buttonImage: document.getElementById("CalImage").value,
				            buttonImageOnly: true,
				            dateFormat: document.getElementById("formatD").value,
				    		defaultDate: document.getElementById("businessdate").value
				         });
				    	});	
</script>		
			
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('userMasterForm').userName.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('userMasterForm').userId.focus();
     }
     return true;
}
</script>
</head>

<body onload="enableAnchor();fntab();init_fields();">
<div align="center" class="opacity" style="display: none"
		id="processingImage"></div>
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<html:javascript formName="UserMasterAddDynaValidatorForm"/>
<html:errors/>

            <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
            <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>


<html:form styleId="userMasterForm"  method="post"  action="/userMasterAdd" >

<fieldset>
<legend><bean:message key="lbl.userMaster" /></legend>
  <table width="100%">
    
   <logic:present name="editVal">
   
<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
     <logic:iterate id="listObj" name="list"> 
   <tr>
   
      <td width="13%"><bean:message key="lbl.iduser" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="userId" tabindex="-1" styleClass="text" styleId="userId" maxlength="10" readonly="true" value="${requestScope.list[0].userId}" onblur="fnChangeCase('userMasterForm','userId')" /></td>
   
      <td width="13%"><bean:message key="lbl.userNam" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="userName" styleClass="text" styleId="userName" maxlength="50" value="${requestScope.list[0].userName}"/> </td>
      </tr>
    <tr>  
      <td ><bean:message key="lbl.empId" /><span><font color="red">*</font></span></td>
      <td  ><html:text property="empId" styleClass="text" styleId="empId" maxlength="10" value="${requestScope.list[0].empId}"/> </td>
      
     <td ><bean:message key="lbl.userDept" /><span><font color="red">*</font></span></td>
     <td  ><html:text property="userDept" styleClass="text" styleId="userDept" readonly="true" tabindex="-1" value="${requestScope.list[0].userDept}"/>
     <html:hidden  property="lbxDepartmentId" styleId="lbxDepartmentId"  value="${requestScope.list[0].lbxDepartmentId}"/>
     <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(84,'userMasterForm','userDept','','', '','','','lbxDepartmentId')" value=" " styleClass="lovbutton"></html:button>
  <%--<img onClick="openLOVCommon(84,'userMasterForm','userDept','','', '','','','lbxDepartmentId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   
      </td>
      </tr>
    <tr>  
      <td ><bean:message key="lbl.defaultBranchId" /><span><font color="red">*</font></span></td>
      <td>
     
      <html:text property="branchId" styleClass="text" styleId="branchId" maxlength="10" readonly="true" tabindex="-1" value="${requestScope.list[0].branchId}" />
    <html:hidden  property="lbxBranchId" styleId="lbxBranchId"  value="${requestScope.list[0].lbxBranchId}"/>
      
      
      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
     <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(12,'userMasterForm','branchId','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"></html:button>
  <%--<img onClick="openLOVCommon(12,'userMasterForm','branchId','','', '','','','lbxBranchId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   

     </td>
     
     <td ><bean:message key="lbl.userDesg" /><span><font color="red">*</font></span></td>
     <td  ><html:text property="userDesg" styleClass="text" styleId="userDesg" readonly="true" tabindex="-1" value="${requestScope.list[0].userDesg}"/>
     <html:hidden  property="lbxDesignationId" styleId="lbxDesignationId"  value="${requestScope.list[0].lbxDesignationId}"/>
       <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(31,'userMasterForm','userDesg','','', '','','','lbxDesignationId')" value=" " styleClass="lovbutton"></html:button>
  <%--<img onClick="openLOVCommon(31,'userMasterForm','userDesg','','', '','','','lbxDesignationId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   
     </td>
     </tr>
    <tr>  
     <td ><bean:message key="lbl.phone1" /><span><font color="red">*</font></span></td>
     <td  ><html:text property="phone1" styleClass="text" styleId="phone1" maxlength="10" value="${requestScope.list[0].phone1}"/></td>
     
     <td ><bean:message key="lbl.phone2" /></td>
     <td  ><html:text property="phone2" styleClass="text" styleId="phone2" maxlength="10" value="${requestScope.list[0].phone2}"/></td>
     </tr>
    <tr>  
     <td ><bean:message key="lbl.email" /><span><font color="red">*</font></span></td>
     <td  ><html:text property="email" styleClass="text" styleId="email" maxlength="100" value="${requestScope.list[0].email}"/></td>
      
 
      
          <td><bean:message key="lbl.reportingto" /><span><font color="red">*</font></span></td>  
           <td><html:text property="reportingto" styleClass="text" styleId="reportingto" readonly="true" tabindex="-1" value="${requestScope.list[0].reportingto}" /> 
          
    <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(217,'userMasterForm','reportingto','userId','lbxReportingUser', 'userId','','','lbxReportingUser')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxReportingUser" styleId="lbxReportingUser"  value="${requestScope.list[0].lbxReportingUser}" /></td> 
    </tr>
    
     <tr>
      	<td><bean:message key="lbl.branchselection" /><span><font color="red">*</font></span></td>
      	<td><bean:message key="lbl.chkAll" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<logic:equal value="A" name="SelectionAccecc">
       			<input type="radio" onclick="chkAllEdit();" name="allselection" value="A" checked="checked" id="allselection" />
       		</logic:equal>
        	<logic:notEqual value="A" name="SelectionAccecc">
       			<input type="radio" onclick="chkAllEdit();" name="allselection" value="A"  id="allselection" />
       		</logic:notEqual>
       		</br>
      		<bean:message key="lbl.chkSelective" /> 
       		<logic:equal value="S" name="SelectionAccecc">
      			<input type="radio" onclick="chkSelectiveEdit();" checked="checked"  name="allselection" value="S" id="singleselection"/>
     		 </logic:equal>
      		 <logic:notEqual value="S" name="SelectionAccecc">
      			<input type="radio" onclick="chkSelectiveEdit();" name="allselection" value="S" id="singleselection"/>
      		</logic:notEqual>
      	</td>
      
   		 <td><bean:message key="lbl.branchId" /></td> 
  		 <td> <html:hidden  property="lbxBranchIds" styleId="lbxBranchIds" value="${requestScope.branchDescIds}" />
   			<html:select property="branchDesc" styleId="branchDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="branchNameList">
       			 <html:optionsCollection name="branchNameList" value="branchId" label="branchDesc"/>
      		</logic:present>
     		</html:select>
      		<html:button property="userButton" styleId="userButtonEdit" onclick="return openMultiSelectLOVCommon(223,'userBranchMasterForm','branchDesc','','', '','','','lbxBranchIds');" value=" " styleClass="lovbutton"></html:button>
     <!--  <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"> -->
       </td>
    </tr>
   <tr>
      <td><bean:message key="lbl.levelaccess" />
<!--      <span><font color="red">*</font></span>-->
      </td>
      <td><bean:message key="lbl.chkAll" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
      
       		<logic:equal value="A" name="levelSelectionAccecc">
       			<input type="radio" onclick="chkAlllevelsEdit();" checked="checked" name="selection" value="A" id="allselectlevel" />
       		</logic:equal>
        	<logic:notEqual value="A" name="levelSelectionAccecc">
       			<input type="radio" onclick="chkAlllevelsEdit();" name="selection" value="A"  id="allselectlevel" />
       		</logic:notEqual>
       		</br>
      		<bean:message key="lbl.chkSelective" /> 
       		<logic:equal value="S" name="levelSelectionAccecc">
      			<input type="radio" onclick="chkSelectivelevelsEdit();" checked="checked"  name="selection" value="S" id="singleselectlevel"/>
     		 </logic:equal>
      		 <logic:notEqual value="S" name="levelSelectionAccecc">
      			<input type="radio" onclick="chkSelectivelevelsEdit();" name="selection" value="S" id="singleselectlevel"/>
      		</logic:notEqual>
       		
      </td>
     
           <td><bean:message key="lbl.level" /></td>
 
        <td>

<html:hidden  property="levelIDs" styleId="levelIDs" value="${requestScope.levelDescIds}" />
   			<html:select property="levelDesc" styleId="levelDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="levelNameList">
       			 <html:optionsCollection name="levelNameList" value="levelID" label="levelDesc"/>
      		</logic:present>
     		</html:select>
      		<html:button property="levelButton" styleId="levelButtonEdit" onclick="return openMultiSelectLOVCommon(339,'userBranchMasterForm','levelDesc','','', '','','','levelIDs');" value=" " styleClass="lovbutton"></html:button>
   
</td>
</tr>
<tr>
        <td ><bean:message key="lbl.validityDate"/><span><font color="red">*</font></span></td>
		<td>
				<html:text property="validityDate"  styleId="validityDate" styleClass="text" value="${requestScope.list[0].validityDate}" maxlength="10" onchange="checkDate('validityDate');validateValidityDate();"/>
	    </td>

             <td><bean:message key="lbl.active" /></td>
      <td>
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="userStatus" id="userStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="userStatus" id="userStatus"  />
      </logic:notEqual>   
      </td> 
   </tr>
     <tr>
   <td width="13%"><bean:message key="lbl.Remarks" /></td>
		     <td width="13%">
			 	<html:text property="remarks" styleClass="text" styleId="remarks" value="${requestScope.list[0].remarks}" maxlength="10"  tabindex="-1"/>
			</td>
    </tr> 
    </logic:iterate></logic:present>
    
    
     <logic:notPresent name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
    
   <tr>
   
      <td ><bean:message key="lbl.iduser" /><span><font color="red">*</font></span></td>
      <td ><html:text property="userId" styleClass="text" styleId="userId" maxlength="10"  onblur="fnChangeCase('userMasterForm','userId')" /></td>
   
      <td ><bean:message key="lbl.userNam" /><span><font color="red">*</font></span></td>
      <td  ><html:text property="userName" styleClass="text" styleId="userName" maxlength="50" /> </td>
      </tr>
    <tr>  
      <td ><bean:message key="lbl.empId" /><span><font color="red">*</font></span></td>
      <td  ><html:text property="empId" styleClass="text" styleId="empId" maxlength="10" /> </td>
      
     <td ><bean:message key="lbl.userDept" /><span><font color="red">*</font></span></td>
     <td  ><html:text property="userDept" styleClass="text" styleId="userDept" readonly="true" tabindex="-1" />
     <html:hidden  property="lbxDepartmentId" styleId="lbxDepartmentId"  />
      <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(84,'userMasterForm','userDept','','', '','','','lbxDepartmentId')" value=" " styleClass="lovbutton"></html:button>
  <%--<img onClick="openLOVCommon(84,'userMasterForm','userDept','','', '','','','lbxDepartmentId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   

     </td>
      </tr>
    <tr>  
      <td ><bean:message key="lbl.defaultBranchId" /><span><font color="red">*</font></span></td>
      <td  ><html:text property="branchId" styleClass="text" styleId="branchId" maxlength="10" readonly="true" tabindex="-1" value="${sessionScope.userobject.branchName}"  />
      <html:hidden  property="lbxBranchId" styleId="lbxBranchId" value="${sessionScope.userobject.branchId}"  />
      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
      <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(12,'userMasterForm','branchId','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"></html:button>
       
  <%--<img onClick="openLOVCommon(12,'userMasterForm','branchId','','', '','','','lbxBranchId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   

     </td>
     
     <td ><bean:message key="lbl.userDesg" /><span><font color="red">*</font></span></td>
     <td  ><html:text property="userDesg" styleClass="text" styleId="userDesg" readonly="true" tabindex="-1" />
     <html:hidden  property="lbxDesignationId" styleId="lbxDesignationId"  />
     <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(31,'userMasterForm','userDesg','','', '','','','lbxDesignationId')" value=" " styleClass="lovbutton"></html:button>
  <%--<img onClick="openLOVCommon(31,'userMasterForm','userDesg','','', '','','','lbxDesignationId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   
     </td>
     </tr>
    <tr>  
     <td ><bean:message key="lbl.phone1" /><span><font color="red">*</font></span></td>
     <td  ><html:text property="phone1" styleClass="text" styleId="phone1" maxlength="10" /></td>
     
     <td ><bean:message key="lbl.phone2" /></td>
     <td  ><html:text property="phone2" styleClass="text" styleId="phone2" maxlength="10" /></td>
     </tr>
    <tr>  
     <td ><bean:message key="lbl.email" /><span><font color="red">*</font></span></td>
     <td  ><html:text property="email" styleClass="text" styleId="email" maxlength="100" /></td>
      <td><bean:message key="lbl.reportingto" /><span><font color="red">*</font></span></td>  
           <td><html:text property="reportingto" styleClass="text" styleId="reportingto" readonly="true" tabindex="-1" /> 
      
       <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(217,'userMasterForm','reportingto','','', '','','','lbxReportingUser')" value=" " styleClass="lovbutton"></html:button>
      <%--<img onClick="openLOVCommon(91,'userAccessMasterSearchForm','userSearchId','','', '','','','lbxUserSearchId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>      
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxReportingUser" styleId="lbxReportingUser" />
     </td>
     </tr> 
      <tr>
      <td><bean:message key="lbl.branchselection" /><span><font color="red">*</font></span>
      </td>
      <td><bean:message key="lbl.chkAll" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:radio onclick="chkAll();" property="allselection" value="A" styleId="allselection"/></br>
      <bean:message key="lbl.chkSelective" /> 
      <html:radio onclick="chkSelective();"  property="allselection" value="S" styleId="singleselection"/>
      </td>
     
           <td><bean:message key="lbl.branchId" /></td>
 
        <td>
           <select id="branchDesc" name="branchDesc" size="5" multiple="multiple" style="width: 120" tabindex="-1">
		   </select>
		     <html:hidden  property="lbxBranchIds" styleId="lbxBranchIds" value="" />
		       <html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(223,'userBranchMasterForm','branchDesc','','', '','','','lbxBranchIds');" value=" " styleClass="lovbutton"></html:button>	   
           <%--<img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"></td> --%>
</td>
</tr>
   <tr>
      <td><bean:message key="lbl.levelaccess" />
<!--      <span><font color="red">*</font></span>-->
      </td>
      <td><bean:message key="lbl.chkAll" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
      <html:radio onclick="chkAlllevels();" property="selection" value="A" styleId="allselectlevel"/></br>
      <bean:message key="lbl.chkSelective" />
       <html:radio onclick="chkSelectivelevels();"  property="selection" value="S" styleId="singleselectlevel"/>
      </td>
     
           <td><bean:message key="lbl.level" /></td>
 
        <td>
           <select id="levelDesc" name="levelDesc" size="5" multiple="multiple" style="width: 120" tabindex="-1">
		   </select>
		     <html:hidden  property="levelIDs" styleId="levelIDs" value="" />
		       <html:button property="levelButton" styleId="levelButton" onclick="return openMultiSelectLOVCommon(339,'userBranchMasterForm','levelDesc','','', '','','','levelIDs');" value=" " styleClass="lovbutton"></html:button>	   
           <%--<img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"></td> --%>
</td>
</tr>
<tr>
        <td ><bean:message key="lbl.validityDate"/><span><font color="red">*</font></span></td>
		<td>
				<html:text property="validityDate"  styleId="validityDate" styleClass="text" value="" maxlength="10" onchange="checkDate('validityDate');validateValidityDate();"/>
	    </td>
	    
			
           <td><bean:message key="lbl.active" /></td>
      <logic:equal value="Active" name="status">
         <td><input type="checkbox" name="userStatus" id="userStatus" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <td><input type="checkbox" name="userStatus" id="userStatus"  /></td>
      </logic:notEqual>
      
         </tr>
       <tr>
   <td width="13%"><bean:message key="lbl.Remarks" /></td>
		     <td width="13%">
			 	<html:text property="remarks" styleClass="text" styleId="remarks" value="" maxlength="10"  tabindex="-1"/>
			</td>
    </tr>   
         
    </logic:notPresent>
    
    <tr><td>
    
    <br>
    <logic:present name="editVal">
      <button type="button"  id="save" title="Alt+V" accesskey="V" onclick="return fnEditUser('${requestScope.list[0].userId}','<bean:message key="lbl.branchContain" />');" class="topformbutton2" ><bean:message key="button.save" /></button>
      <button type="button" name="reInitialize"  title="Alt+R" accesskey="R" id="reInitialize" onclick="return fnEditUserPass('${requestScope.list[0].userId}');" class="topformbutton4" ><bean:message key="button.reinitializepassword" /></button>
   </logic:present>
   
   <logic:present name="editValUpdate">
      <button type="button"  id="save" title="Alt+V" accesskey="V" onclick="return fnEditUser('${requestScope.list[0].userId}','<bean:message key="lbl.branchContain" />');" class="topformbutton2" ><bean:message key="button.save" /></button>
      <button type="button" name="reInitialize"  title="Alt+R" accesskey="R" id="reInitialize" onclick="return fnEditUserPass('${requestScope.list[0].userId}');" class="topformbutton4" ><bean:message key="button.reinitializepassword" /></button>
   </logic:present>
   
   <logic:present name="save">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button"  id="save" title="Alt+V" accesskey="V" onclick="return saveUser('<bean:message key="lbl.branchContain" />');" class="topformbutton2" ><bean:message key="button.save" /></button>
    
   </logic:present>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">
    if(('<%=request.getAttribute("sms")%>')== "S")
	{
		alert("<bean:message key="lbl.dataSave" />");
		alert("Your new password is:: <%=request.getAttribute("randompassword")%>");
		document.getElementById("userMasterForm").action="userMasterBehind.do";
	    document.getElementById("userMasterForm").submit();
	}
	
	else if(('<%=request.getAttribute("sms")%>')== "M")
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("userMasterForm").action="userMasterBehind.do";
	    document.getElementById("userMasterForm").submit();
	}
	else if(('<%=request.getAttribute("sms")%>')== "EMPID")
	{
	    var userId=document.getElementById("userId").value;
		alert("<bean:message key="lbl.empIdExist" />");
		document.getElementById("userMasterForm").action="userMasterSearch.do?method=openEditUser&userSearchId="+userId;
	    document.getElementById("userMasterForm").submit();
		
	}
	else if(('<%=request.getAttribute("sms")%>')== "DUPLIEMPID")
	{
		alert("<bean:message key="lbl.empIdExist" />");
	
	}
	
	else if(('<%=request.getAttribute("sms")%>')== "UserInactive")
	{
		
		var userId=document.getElementById("userId").value;
		alert("<bean:message key="lbl.ReportingToUserInactive" />");
		document.getElementById("userMasterForm").action="userMasterSearch.do?method=openEditUser&userSearchId="+userId;
	    document.getElementById("userMasterForm").submit();
		
		
	}
	
	else if(('<%=request.getAttribute("sms")%>') == "R")
	{
		alert("<bean:message key="lbl.passReinitialize" />");
		alert("Your new password is:: '<%=request.getAttribute("randompassword")%>'");
		document.getElementById("userMasterForm").action="userMasterBehind.do";
	    document.getElementById("userMasterForm").submit();
	}
	else if(('<%=request.getAttribute("sms")%>') == "E")
	{
		alert("<bean:message key="msg.notepadError" />");
		
	} 
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	} 

</script>
</logic:present>
<logic:present name="heirarchyuser">
<script type="text/javascript">
if(('<%=request.getAttribute("heirarchyuser")%>')== "exist")
	{
	    var userId=document.getElementById("userId").value;
		alert("<bean:message key="lbl.lowerheirarchyuser" />");
		document.getElementById("userMasterForm").action="userMasterSearch.do?method=openEditUser&userSearchId="+userId;
	    document.getElementById("userMasterForm").submit();
	
	}
	else if(('<%=request.getAttribute("sms")%>') == "notexist")
	{

		document.getElementById("userMasterForm").action="userMasterBehind.do";
	    document.getElementById("userMasterForm").submit();
	}
	</script>
</logic:present>
  </body>
		</html:html>
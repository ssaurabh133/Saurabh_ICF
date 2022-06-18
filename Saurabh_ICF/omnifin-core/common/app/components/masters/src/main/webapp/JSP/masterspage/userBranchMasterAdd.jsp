<!--Author Name : Ritu Jindal-->
<!--Date of Creation : 25 May 2011-->
<!--Purpose  : Information of USER BRANCH Master Add-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userBranchScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
</head>

<body onload="enableAnchor();init_fields();">

<html:form styleId="userBranchMasterForm"  method="post"  action="/userBranchMasterAdd" >
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<fieldset>
<legend><bean:message key="lbl.userBranchMaster" /></legend>
  <table width="100%">
    
    <logic:present name="editVal">
    
    <tr><td><bean:message key="lbl.userId" /><span><font color="red">*</font></span></td>
        <td><html:text property="userId" styleClass="text" styleId="userId" readonly="true" value="${list[0].userId}" /></td>
    </tr>
    <tr>
    <td>
   <bean:message key="lbl.branchId" /><span><font color="red">*</font></span></td> 
   
   <td> <html:select property="branchDesc" styleId="branchDesc" size="5" multiple="multiple" style="width: 120" >
      <logic:present name="branchNameList">
        <html:optionsCollection name="branchNameList" value="branchId" label="branchDesc"/>
      </logic:present>
     
          </html:select><img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');">
        
</td>
    </tr>
     </logic:present>
    
    <logic:notPresent name="editVal">
    <tr>
     <td ><bean:message key="lbl.userId" /><span><font color="red">*</font></span></td>
       <td ><html:text property="userId" styleClass="text" styleId="userId" maxlength="10" readonly="true"  />
        <img onClick="openLOVCommon(33,'userBranchMasterForm','userId','','', '','','','lbxUserId')" src="<%= request.getContextPath()%>/images/lov.gif">
       
       <html:hidden  property="lbxUserId" styleId="lbxUserId" value="" />
      
     </td>
    </tr>
    <TR>
           <td><bean:message key="lbl.branchId" /><span><font color="red">*</font></span></td>
 
        <td>
           <select id="branchDesc" name="branchDesc" size="5" multiple="multiple" style="width: 120">
		   </select>
           <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"></td>
           <html:hidden  property="lbxBranchId" styleId="lbxBranchId"/>
                    
         </TR>
     </logic:notPresent>
     
           
      <tr>
      <td ><bean:message key="lbl.active" /></td>
      <logic:equal value="Active" name="userBranchStatus">
         <td><input type="checkbox" name="userBranchStatus" id="userBranchStatus" checked="checked" /></td>
       </logic:equal>
    
      <logic:notEqual value="Active" name="userBranchStatus">
       <td><input type="checkbox" name="userBranchStatus" id="userBranchStatus"  /></td> 
      </logic:notEqual>
      
    </tr><tr><td>
    
    <br>
    <logic:present name="editVal">
      <button type="button"  id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return fnEditUserBranch('${requestScope.list[0].lbxUserId}','<bean:message key="lbl.selectBranch" />');" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:notPresent name="editVal">
      
     
      <button type="button" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveUserBranch('<bean:message key="lbl.selectUserId" />','<bean:message key="lbl.selectBranch" />','<bean:message key="lbl.selectBranch&UserId" />');" ><bean:message key="button.save" /></button> 
   </logic:notPresent>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>
	<logic:present name="sms">
<script type="text/javascript">

	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("userBranchMasterForm").action="userBranchMasterBehind.do";
	    document.getElementById("userBranchMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("userBranchMasterForm").action="userBranchMasterBehind.do";
	    document.getElementById("userBranchMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	
</script>
</logic:present>		
		
  </body>
		</html:html>
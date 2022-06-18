
<%--Author Name : Saurabh
Date of Creation : 28 November 2011 
Purpose  : U/W Approval Level Def Master Search
Documentation : --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="com.masters.vo.*" %>
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

ArrayList appList=new ArrayList();
appList=(ArrayList)session.getAttribute("appList");

ArrayList UserList=new ArrayList();
UserList=(ArrayList)session.getAttribute("UserList");

%>

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
<body onload="enableAnchor();setLevel();checklevel();fntab();" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="approvalLevelDefAddForm"  method="post"  action="/approvalLevelDefAdd" >

<fieldset>
<legend><bean:message key="lbl.approvalLevelMaster" /></legend>
<logic:iterate id="listObj" name="mylist">
<table width="100%" cellspacing="1" cellpadding="0" border="0">
<tr>  
  <td width="13%"><bean:message key="lbl.product2" />edit mode<font color="red">*</font></td>
  <td width="13%" >
  	<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />

	<html:text property="product" value="${listObj.product}" styleId="product" styleClass="text" readonly="true" tabindex="-1"/>
<!--	<input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(89,'approvalLevelDefSearchForm','product','','','','','','lbxProductID')" value=" " tabindex="26" name="dealButton">-->
	<input type="hidden" name="lbxProductID" id="lbxProductID" 	value="${listObj.lbxProductID}" /></td>
	
	 <td width="8%"><bean:message key="lbl.schemeLOV"/><font color="red">*</font></td>
            <td width="20%">
             <html:text property="scheme" styleId="scheme" styleClass="text" value="${listObj.scheme}" readonly="true" tabindex="-1"/> 
          	 <html:hidden property="lbxSchemeId" styleId="lbxSchemeId" value="${listObj.lbxSchemeId}"  />
            </td>
  
  </tr>
  <tr>   
    <td width="13%"><bean:message key="lbl.amountFrom" /><span><font color="red">*</font></span></td>
    <td width="13%" ><html:text property="amountFrom" value="${listObj.amountFrom}" style="text-align: right"   styleClass="text" styleId="amountFrom" maxlength="18" readonly="true" /></td>
    <td width="13%"><bean:message key="lbl.amountTo" /><span><font color="red">*</font></span></td>
    <td width="13%" ><html:text property="amountTo" value="${listObj.amountTo}" style="text-align: right"   styleClass="text" styleId="amountTo" maxlength="18" readonly="true" /></td>
  </tr>
  <tr>
  <td width="13%">
  	<bean:message key="lbl.approvalLevel" /></td>
  <td width="13%" >
  		<html:select property="findApprovalLevel" value="${listObj.findApprovalLevel}"  onchange="checklevel();" styleClass="text" styleId="findApprovalLevel" disabled="true">
   			<logic:present name="pmstSize">
   		<% 	int j = Integer.parseInt(session.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { %>
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>
		<%} %>
		</logic:present>
   		</html:select>   
   </td>
  	<td><bean:message key="lbl.active" /></td>
      	  
		<logic:equal value="Active" name="status">
			<td><input type="checkbox" name="status" id="status" checked="checked" disabled="true" /></td>	
		</logic:equal>
		<logic:notEqual value="Active" name="status">
		<td><input type="checkbox" name="status" id="status"  disabled="true"/></td>
		</logic:notEqual>
  
  </tr>
  </table>
  <br/>
  <table width="100%" cellspacing="2" cellpadding="0" border="0">
  <tr>
  	<td>
  		<fieldset><legend><bean:message key="lbl.minApprovalReq" /></legend>
		<table width="100%" cellpadding="0" cellspacing="1" border="0">
     		<% 	int j = Integer.parseInt(session.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { 
   			String k = CommonFunction.checkNull(i).toString();
   			session.setAttribute("level",k);
   	%>
				
	    <tr>
	     <td width="13%"><bean:message key="lbl.level" /><%=k %></td>
	     <td><html:text property="level${level}" value="<%=(String)appList.get(i-1) %>" onkeypress="return isNumberKey(event);" 
	     onblur="hideUserLevel${level}(id);" styleClass="text" styleId="level${level}"  maxlength="10"  readonly="true"/></td>
		</tr>
		<%} 
	%>
     </table>
     </fieldset>
   </td>
   <td></td>
   <td>
  		
  		<fieldset><legend><bean:message key="lbl.masterApprove" /></legend>
     	<table width="100%" cellspacing="1" cellpadding="0" border="0">
       <% 	int list = 0;
       		for(int l=1;l<=j;l++) {
   			String k = CommonFunction.checkNull(l).toString();
   			String user1 = "user"+k+"1";
   			String button1 = "userButton"+k+"1";
   			String userHiddenId1 = "lbxUserSearchId"+k+"1";
   			String user2 = "user"+k+"2";
   			String button2 = "userButton"+k+"2";
   			String userHiddenId2 = "lbxUserSearchId"+k+"2";
   			String user3 = "user"+k+"3";
   			String button3 = "userButton"+k+"3";
   			String userHiddenId3 = "lbxUserSearchId"+k+"3";
   			request.setAttribute("id",k);
   	%>
    <tr>
         <td><bean:message key="lbl.user1" /></td>
    <td> <html:text property="user${id }1" styleClass="text" value="<%=(String)UserList.get(list) %>" styleId="user${id }1"  readonly="true" />     
         </td>
   
         <td><bean:message key="lbl.user2" /></td>
    <td> <html:text property="user${id }2" styleClass="text" value="<%=(String)UserList.get(list+1) %>" styleId="user${id }2"  readonly="true"  />      
     </td>
    
      <td><bean:message key="lbl.user3" /></td>
    <td> <html:text property="user${id }3" styleClass="text" value="<%=(String)UserList.get(list+2) %>" styleId="user${id }3"  readonly="true"  />     
     </td>
     </tr>
     <% list = list +3; } %>
   
     </table>
   
       </fieldset>
     </td>
     </tr>

</table>
</logic:iterate>
 </fieldset>
        
	</html:form>	
  	</body>
  	</html:html>
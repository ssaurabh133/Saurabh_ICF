
<%--Author Name : Anil Yadav
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/approvalLevelDefScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);

ArrayList appList=new ArrayList();
appList=(ArrayList)request.getAttribute("appList");

ArrayList UserList=new ArrayList();
UserList=(ArrayList)request.getAttribute("UserList");

ArrayList LbxUserSearchList=new ArrayList();
LbxUserSearchList=(ArrayList)request.getAttribute("LbxUserSearchList");
%>
<script type="text/javascript">
function hideUserLevel1(san){
		document.getElementById("userButton11").style.display = "";
		document.getElementById("userButton12").style.display = "";
		document.getElementById("userButton13").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton11").style.display = "";
		document.getElementById("userButton12").style.display = "";
		document.getElementById("userButton13").style.display = "";
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton12").style.display = "none";
		document.getElementById("userButton13").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton13").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton11").style.display = "";
		document.getElementById("userButton12").style.display = "";
		document.getElementById("userButton13").style.display = "";
	}
	
}
function hideUserLevel2(san){
		document.getElementById("userButton21").style.display = "";
		document.getElementById("userButton22").style.display = "";
		document.getElementById("userButton23").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton21").style.display = "";
		document.getElementById("userButton22").style.display = "";
		document.getElementById("userButton23").style.display = "";
	
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton22").style.display = "none";
		document.getElementById("userButton23").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton23").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton21").style.display = "";
		document.getElementById("userButton22").style.display = "";
		document.getElementById("userButton23").style.display = "";
	}
	
}
function hideUserLevel3(san){
		document.getElementById("userButton31").style.display = "";
		document.getElementById("userButton32").style.display = "";
		document.getElementById("userButton33").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton31").style.display = "";
		document.getElementById("userButton32").style.display = "";
		document.getElementById("userButton33").style.display = "";
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton32").style.display = "none";
		document.getElementById("userButton33").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton33").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton31").style.display = "";
		document.getElementById("userButton32").style.display = "";
		document.getElementById("userButton33").style.display = "";
	}
	
}
function hideUserLevel4(san){
		document.getElementById("userButton41").style.display = "";
		document.getElementById("userButton42").style.display = "";
		document.getElementById("userButton43").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton41").style.display = "";
		document.getElementById("userButton42").style.display = "";
		document.getElementById("userButton43").style.display = "";
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton42").style.display = "none";
		document.getElementById("userButton43").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton43").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton41").style.display = "";
		document.getElementById("userButton42").style.display = "";
		document.getElementById("userButton43").style.display = "";
	}
	
}
function hideUserLevel5(san){
		document.getElementById("userButton51").style.display = "";
		document.getElementById("userButton52").style.display = "";
		document.getElementById("userButton53").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton51").style.display = "";
		document.getElementById("userButton52").style.display = "";
		document.getElementById("userButton53").style.display = "";
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton52").style.display = "none";
		document.getElementById("userButton53").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton53").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton51").style.display = "";
		document.getElementById("userButton52").style.display = "";
		document.getElementById("userButton53").style.display = "";
	}
	
}
function hideUserLevel6(san){
		document.getElementById("userButton61").style.display = "";
		document.getElementById("userButton62").style.display = "";
		document.getElementById("userButton63").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton61").style.display = "";
		document.getElementById("userButton62").style.display = "";
		document.getElementById("userButton63").style.display = "";
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton62").style.display = "none";
		document.getElementById("userButton63").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton63").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton61").style.display = "";
		document.getElementById("userButton62").style.display = "";
		document.getElementById("userButton63").style.display = "";
	}
	
}
function hideUserLevel7(san){
		document.getElementById("userButton71").style.display = "";
		document.getElementById("userButton72").style.display = "";
		document.getElementById("userButton73").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton71").style.display = "";
		document.getElementById("userButton72").style.display = "";
		document.getElementById("userButton73").style.display = "";
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton72").style.display = "none";
		document.getElementById("userButton73").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton73").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton71").style.display = "";
		document.getElementById("userButton72").style.display = "";
		document.getElementById("userButton73").style.display = "";
	}
	
}
function hideUserLevel8(san){
		document.getElementById("userButton81").style.display = "";
		document.getElementById("userButton82").style.display = "";
		document.getElementById("userButton83").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton81").style.display = "";
		document.getElementById("userButton82").style.display = "";
		document.getElementById("userButton83").style.display = "";
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton82").style.display = "none";
		document.getElementById("userButton83").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton83").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton81").style.display = "";
		document.getElementById("userButton82").style.display = "";
		document.getElementById("userButton83").style.display = "";
	}
	
}
function hideUserLevel9(san){
		document.getElementById("userButton91").style.display = "";
		document.getElementById("userButton92").style.display = "";
		document.getElementById("userButton93").style.display = "";
	if(document.getElementById(san).value > 3){
		document.getElementById("userButton91").style.display = "";
		document.getElementById("userButton92").style.display = "";
		document.getElementById("userButton93").style.display = "";
	}else if(document.getElementById(san).value == 1){
		document.getElementById("userButton92").style.display = "none";
		document.getElementById("userButton93").style.display = "none";
	}else if(document.getElementById(san).value == 2){
		document.getElementById("userButton93").style.display = "none";
	}else if(document.getElementById(san).value == 3){
		document.getElementById("userButton91").style.display = "";
		document.getElementById("userButton92").style.display = "";
		document.getElementById("userButton93").style.display = "";
	}
	
}
function setLevel(){
		hideUserLevel1("level1");
		hideUserLevel2("level2");
		hideUserLevel3("level3");
		hideUserLevel4("level4");
		hideUserLevel5("level5");
		hideUserLevel6("level6");
		hideUserLevel7("level7");
		hideUserLevel8("level8");
		hideUserLevel9("level9");
}
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('approvalLevelDefAddForm').findApprovalLevel.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('approvalLevelDefAddForm').productButton.focus();
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
<body onload="enableAnchor();setAmount();setLevel();checklevel();fntab();" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="approvalLevelDefAddForm"  method="post"  action="/approvalLevelDefAdd" >

<fieldset>
<legend><bean:message key="lbl.approvalLevelMaster" /></legend>
<input type="hidden" name="psmtLevel" id="psmtLevel" value="<%=Integer.parseInt(request.getAttribute("pmstSize").toString())%>" />
<logic:present name="makerCheckerFlag">


<logic:present name="editVal">
<logic:iterate id="listObj" name="list">
<table width="100%" cellspacing="1" cellpadding="0" border="0">
<tr>  
  <td width="13%"><bean:message key="lbl.product2" /><font color="red">*</font></td>
  <td width="13%" >
  	<input type="hidden" name="decideUpdate" value="<%=request.getParameter("decideUpdate")%>" id="decideUpdate" />
  	<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
    <input type="hidden" id="modifyRecord" name="modifyRecord"	value="M" />
    <input type="hidden" id="productModify" name="productModify"	value="<%=request.getAttribute("productModify")%>" />
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
    <td width="13%" ><html:text property="amountFrom" value="${listObj.amountFrom}" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);" onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);removeCommaAmountFrom(this.value);" 	onkeypress="return numbersonly(event,id,18)"  styleClass="text" styleId="amountFrom" maxlength="18" />
         	<html:hidden  styleId="amountFromDesc" property="amountFromDesc"	value="" />
     		<html:hidden  styleId="amountFromId" property="amountFromId"	value="" /></td>
    <td width="13%"><bean:message key="lbl.amountTo" /><span><font color="red">*</font></span></td>
    <td width="13%" ><html:text property="amountTo" value="${listObj.amountTo}" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);" onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);removeCommaAmountTo(this.value);" 	onkeypress="return numbersonly(event,id,18)"  styleClass="text" styleId="amountTo" maxlength="18" />
         	<html:hidden  styleId="amountToDesc" property="amountToDesc"	value="" />
     		<html:hidden  styleId="amountToId" property="amountToId"	value="" /></td>
  </tr>
  <tr>
  <td width="13%">
  	<bean:message key="lbl.approvalLevel" /></td>
  <td width="13%" >
  		<html:select property="findApprovalLevel" value="${listObj.findApprovalLevel}"  onchange="checklevel();" styleClass="text" styleId="findApprovalLevel" >
   			<logic:present name="pmstSize">   				
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { %>
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>
		<%} %>
		</logic:present>
   		</html:select>   
   </td>
  	<td><bean:message key="lbl.active" /></td>
      	  
		<logic:equal value="Active" name="status">
			<td><input type="checkbox" name="status" id="status" checked="checked" /></td>	
		</logic:equal>
		<logic:notEqual value="Active" name="status">
		<td><input type="checkbox" name="status" id="status" /></td>
		</logic:notEqual>
  
  </tr>
  </table>
  <br/>
  <table width="100%" cellspacing="2" cellpadding="0" border="0">
  <tr>
  	<td>
  		<fieldset><legend><bean:message key="lbl.minApprovalReq" /></legend>
		<table width="100%" cellpadding="0" cellspacing="1" border="0">
     		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { 
   			String k = CommonFunction.checkNull(i).toString();
   			request.setAttribute("level",k);
   	%>
				
	    <tr>
	     <td width="13%"><bean:message key="lbl.level" /><%=k %></td>
	     <td><html:text property="level${level}" value="<%=(String)appList.get(i-1) %>" onkeypress="return isNumberKey(event);" onblur="hideUserLevel${level}(id);" styleClass="text" styleId="level${level}"  maxlength="10" /></td>
		</tr>
		<%} %>
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
     <html:hidden  styleId="levelId${id}" property="levelId${id}"	value="${id}" />
     <html:hidden  styleId="levelDesc${id}" property="levelDesc${id}"	value="${id}" />
         <td><bean:message key="lbl.user1" /></td>
    <td> <html:text property="user${id }1" styleClass="text" value="<%=(String)UserList.get(list) %>" styleId="user${id }1"  readonly="true" />
    <html:button property="<%=button1%>" styleId="<%=button1%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}1','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}1')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
        <html:hidden  property="lbxUserSearchId1" styleId="lbxUserSearchId1" value="<%=(String)LbxUserSearchList.get(list) %>"/>
       <html:hidden  property="<%=userHiddenId1%>" styleId="<%=userHiddenId1%>" value="<%=(String)LbxUserSearchList.get(list) %>"/>
     </td>
   
         <td><bean:message key="lbl.user2" /></td>
    <td> <html:text property="user${id }2" styleClass="text" value="<%=(String)UserList.get(list+1) %>" styleId="user${id }2"  readonly="true"  />
    <html:button property="<%=button2%>" styleId="<%=button2%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}2','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}2')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId2" styleId="lbxUserSearchId2" value="<%=(String)LbxUserSearchList.get(list+1) %>" />
       <html:hidden  property="<%=userHiddenId2%>" styleId="<%=userHiddenId2%>" value="<%=(String)LbxUserSearchList.get(list+1) %>"/>
     </td>
    
      <td><bean:message key="lbl.user3" /></td>
    <td> <html:text property="user${id }3" styleClass="text" value="<%=(String)UserList.get(list+2) %>" styleId="user${id }3"  readonly="true"  />
    <html:button property="<%=button3%>" styleId="<%=button3%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}3','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}3')"></html:button>  
       <html:hidden  property="lbxUserSearchId3" styleId="lbxUserSearchId3" value="<%=(String)LbxUserSearchList.get(list+2) %>" />
        <html:hidden  property="<%=userHiddenId3%>" styleId="<%=userHiddenId3%>" value="<%=(String)LbxUserSearchList.get(list+2) %>"/>
     </td>
     </tr>
     <% list = list +3; } %>
   
     </table>
     </fieldset>
     </td>
     </tr>
	<tr> 
    <td>
    <button type="button" name="add" id="add" class="topformbutton2" title="Alt+V" accesskey="V" onclick=" UpdateApprovalLevel('Y');"><bean:message key="button.save" /></button>
    <button type="button" name="forward" id="forward" class="topformbutton2" title="Alt+F" accesskey="F" onclick="forwardApprovalLevel();"><bean:message key="button.forward" /></button></td>
  </tr>
</table>
</logic:iterate>
</logic:present>

<logic:notPresent name="editVal">

<table>


  <tr>
  
  <td width="13%"><bean:message key="lbl.product2" /><font color="red">*</font></td>
      <td width="13%" >
      <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
      <input type="hidden" id="modifyRecord" name="modifyRecord"	value="I" />
	  <html:text property="product" styleId="product" styleClass="text" readonly="true" tabindex="-1"/>
	  <input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(89,'approvalLevelDefSearchForm','product','','','','','','lbxProductID')" value=" " tabindex="26" name="dealButton">
	 <html:hidden property="lbxProductID" styleId="lbxProductID" styleClass="text"></html:hidden>
	 </td>
	 <td width="8%"><bean:message key="lbl.schemeLOV"/><font color="red">*</font></td>
            <td width="20%">
            
             <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/> 
          		<html:hidden property="lbxSchemeId" styleId="lbxSchemeId"  />
          		
          		 <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(287,'approvalLevelDefAddForm','scheme','product','lbxSchemeId', 'lbxProductID','Please firstly select Product then Scheme','','lbxSchemeId');" value=" " styleClass="lovbutton"> </html:button>
               
           						    
                </td>
         
  </tr>



    <tr> 
  
    <td width="13%"><bean:message key="lbl.amountFrom" /><font color="red">*</font></td>
    <td width="13%" ><html:text property="amountFrom" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);"
									onkeyup="checkNumber(this.value, event,18,id);"
									onblur="formatNumber(this.value,id);removeCommaAmountFrom(this.value);"
									onkeypress="return numbersonly(event,id,18)"  styleClass="text" styleId="amountFrom" maxlength="18" />
			<html:hidden  styleId="amountFromDesc" property="amountFromDesc"	value="" />
     		<html:hidden  styleId="amountFromId" property="amountFromId"	value="" /></td>
    
    <td width="13%"><bean:message key="lbl.amountTo" /><font color="red">*</font></td>
    <td width="13%" ><html:text property="amountTo" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);"
									onkeyup="checkNumber(this.value, event,18,id);"
									onblur="formatNumber(this.value,id);removeCommaAmountTo(this.value);"
									onkeypress="return numbersonly(event,id,18)"  styleClass="text" styleId="amountTo" maxlength="18" />
		    <html:hidden  styleId="amountToDesc" property="amountToDesc"	value="" />
     		<html:hidden  styleId="amountToId" property="amountToId"	value="" /></td>
        
     </tr>
          <tr>
          <td width="13%"><bean:message key="lbl.approvalLevel" /></td>
   
   <td width="13%" ><html:select property="findApprovalLevel" styleClass="text" onchange="checklevel();" styleId="findApprovalLevel" >
   		<logic:present name="pmstSize">
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { %>
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>
		<%} %>
		</logic:present>
   </html:select>   
   </td>
         <td>
							<bean:message key="lbl.active" />
		</td>
						<td>
							<input type="checkbox" name="status" id="status"
								checked="checked" />
						</td>
		</tr>	
</table>

<br/>
<br/>

<td>
<table width="100%" cellspacing="2" cellpadding="0" border="0">
<tr>
 <td>
    <fieldset>
     <legend>
     <bean:message key="lbl.minApprovalReq" />
     </legend>
     <table width="30%" cellpadding="0" cellspacing="1" border="0">
     <% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { 
   			String k = CommonFunction.checkNull(i).toString();
   			request.setAttribute("level",k);
   	%>
				
	    <tr>
	     <td width="13%"><bean:message key="lbl.level" /><%=k %></td>
	     <td><html:text property="level${level}" onkeypress="return isNumberKey(event);" onblur="hideUserLevel${level}(id);" styleClass="text" styleId="level${level}"  maxlength="10" /></td>
		</tr>
		<%} %>
			
     </table>
     </fieldset>
   </td>

 <td>
  <fieldset>
    <legend>
    <bean:message key="lbl.masterApprove" />
    </legend>
     <table width="100%" cellspacing="1" cellpadding="0" border="0">
     <% 	j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { 
   			String k = CommonFunction.checkNull(i).toString();
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
        <html:hidden  styleId="levelId${id}" property="levelId${id}"	value="${id}" />
     	<html:hidden  styleId="levelDesc${id}" property="levelDesc${id}"	value="${id}" />
         <td><bean:message key="lbl.user1" /></td>
    <td> <html:text property="user${id }1" styleClass="text"  styleId="user${id }1"  readonly="true" />
    <html:button property="<%=button1%>" styleId="<%=button1%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}1','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}1')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
        <html:hidden  property="lbxUserSearchId1" styleId="lbxUserSearchId1" value=""/>
       <html:hidden  property="<%=userHiddenId1%>" styleId="<%=userHiddenId1%>" />
     </td>
   
         <td><bean:message key="lbl.user2" /></td>
    <td> <html:text property="user${id }2" styleClass="text" styleId="user${id }2"  readonly="true"  />
    <html:button property="<%=button2%>" styleId="<%=button2%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}2','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}2')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId2" styleId="lbxUserSearchId2" value="" />
       <html:hidden  property="<%=userHiddenId2%>" styleId="<%=userHiddenId2%>" />
     </td>
    
      <td><bean:message key="lbl.user3" /></td>
    <td> <html:text property="user${id }3" styleClass="text" styleId="user${id }3"  readonly="true"  />
    <html:button property="<%=button3%>" styleId="<%=button3%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}3','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}3')"></html:button>  
       <html:hidden  property="lbxUserSearchId3" styleId="lbxUserSearchId3" value="" />
        <html:hidden  property="<%=userHiddenId3%>" styleId="<%=userHiddenId3%>" />
     </td>
     </tr>
     <%} %>
         
     </table>
     </fieldset>
     </td>
</tr>
  <tr> 
   
    <td> <button type="button" name="add" id="add" class="topformbutton2" title="Alt+V" accesskey="V" onclick="SaveApprovalLevel('Y');"><bean:message key="button.save" /></button>
     <button type="button" name="forward" id="forward" class="topformbutton2" title="Alt+F" accesskey="F" onclick="forwardBeforeSave();"><bean:message key="button.forward" /></button></td>
  </tr>
</table>

</logic:notPresent>





</logic:present>
<logic:notPresent name="makerCheckerFlag" >

<logic:present name="editVal">
<logic:iterate id="listObj" name="list">
<table width="100%" cellspacing="1" cellpadding="0" border="0">
<tr>  
  <td width="13%"><bean:message key="lbl.product2" /><font color="red">*</font></td>
  <td width="13%" >
  	<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
    <input type="hidden" id="modifyRecord" name="modifyRecord"	value="M" />
    <input type="hidden" id="productModify" name="productModify"	value="<%=request.getAttribute("productModify")%>" />
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
    <td width="13%" ><html:text property="amountFrom" value="${listObj.amountFrom}" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);" onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);removeCommaAmountFrom(this.value);" 	onkeypress="return numbersonly(event,id,18)"  styleClass="text" styleId="amountFrom" maxlength="18" />
            <html:hidden  styleId="amountFromDesc" property="amountFromDesc"	value="" />
     		<html:hidden  styleId="amountFromId" property="amountFromId"	value="" /></td>
    <td width="13%"><bean:message key="lbl.amountTo" /><span><font color="red">*</font></span></td>
    <td width="13%" ><html:text property="amountTo" value="${listObj.amountTo}" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);" onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);removeCommaAmountTo(this.value);" 	onkeypress="return numbersonly(event,id,18)"  styleClass="text" styleId="amountTo" maxlength="18" />
            <html:hidden  styleId="amountToDesc" property="amountToDesc"	value="" />
     		<html:hidden  styleId="amountToId" property="amountToId"	value="" /></td>
  </tr>
  <tr>
  <td width="13%">
  	<bean:message key="lbl.approvalLevel" /></td>
  <td width="13%" >
  		<html:select property="findApprovalLevel" value="${listObj.findApprovalLevel}"  onchange="checklevel();" styleClass="text" styleId="findApprovalLevel" >
   			<logic:present name="pmstSize">
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { %>
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>
		<%} %>
		</logic:present>
   		</html:select>   
   </td>
  	<td><bean:message key="lbl.active" /></td>
      	  
		<logic:equal value="Active" name="status">
			<td><input type="checkbox" name="status" id="status" checked="checked" /></td>	
		</logic:equal>
		<logic:notEqual value="Active" name="status">
		<td><input type="checkbox" name="status" id="status" /></td>
		</logic:notEqual>
  
  </tr>
  </table>
  <br/>
  <table width="100%" cellspacing="2" cellpadding="0" border="0">
  <tr>
  	<td>
  		<fieldset><legend><bean:message key="lbl.minApprovalReq" /></legend>
  		
		<table width="100%" cellpadding="0" cellspacing="1" border="0">
     		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { 
   			String k = CommonFunction.checkNull(i).toString();
   			request.setAttribute("level",k);
   	%>
				
	    <tr>
	     <td width="13%"><bean:message key="lbl.level" /><%=k %></td>
	     <td><html:text property="level${level}" value="<%=(String)appList.get(i-1) %>" onkeypress="return isNumberKey(event);" onblur="hideUserLevel${level}(id);" styleClass="text" styleId="level${level}"  maxlength="10" /></td>
		</tr>
		<%} %>
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
         <html:hidden  styleId="levelId${id}" property="levelId${id}"	value="${id}" />
     	 <html:hidden  styleId="levelDesc${id}" property="levelDesc${id}"	value="${id}" />
         <td><bean:message key="lbl.user1" /></td>
    <td> <html:text property="user${id }1" styleClass="text" value="<%=(String)UserList.get(list) %>" styleId="user${id }1"  readonly="true" />
    <html:button property="<%=button1%>" styleId="<%=button1%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}1','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}1')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
        <html:hidden  property="lbxUserSearchId1" styleId="lbxUserSearchId1" value="<%=(String)LbxUserSearchList.get(list) %>"/>
       <html:hidden  property="<%=userHiddenId1%>" styleId="<%=userHiddenId1%>" value="<%=(String)LbxUserSearchList.get(list) %>"/>
     </td>
   
         <td><bean:message key="lbl.user2" /></td>
    <td> <html:text property="user${id }2" styleClass="text" value="<%=(String)UserList.get(list+1) %>" styleId="user${id }2"  readonly="true"  />
    <html:button property="<%=button2%>" styleId="<%=button2%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}2','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}2')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId2" styleId="lbxUserSearchId2" value="<%=(String)LbxUserSearchList.get(list+1) %>" />
       <html:hidden  property="<%=userHiddenId2%>" styleId="<%=userHiddenId2%>" value="<%=(String)LbxUserSearchList.get(list+1) %>"/>
     </td>
    
      <td><bean:message key="lbl.user3" /></td>
    <td> <html:text property="user${id }3" styleClass="text" value="<%=(String)UserList.get(list+2) %>" styleId="user${id }3"  readonly="true"  />
    <html:button property="<%=button3%>" styleId="<%=button3%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}3','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}3')"></html:button>  
       <html:hidden  property="lbxUserSearchId3" styleId="lbxUserSearchId3" value="<%=(String)LbxUserSearchList.get(list+2) %>" />
        <html:hidden  property="<%=userHiddenId3%>" styleId="<%=userHiddenId3%>" value="<%=(String)LbxUserSearchList.get(list+2) %>"/>
     </td>
     </tr>
     <% list = list +3; } %>
   
     </table>
     </fieldset>
     </td>
<tr> 
    <td>
    <button type="button" name="add" id="add" class="topformbutton2" title="Alt+V" accesskey="V" onclick=" UpdateApprovalLevel('N');"><bean:message key="button.save" /></button></td>
  </tr>
</table>
</logic:iterate>
</logic:present>

<logic:notPresent name="editVal">

<table>


  <tr>
  
  <td width="13%"><bean:message key="lbl.product2" /><font color="red">*</font></td>
      <td width="13%" >
      <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
      <input type="hidden" id="modifyRecord" name="modifyRecord"	value="I" />
	  <html:text property="product" styleId="product" styleClass="text" readonly="true" tabindex="-1"/>
	  <input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(89,'approvalLevelDefSearchForm','product','','','','','','lbxProductID')" value=" " tabindex="26" name="dealButton">
	 <html:hidden property="lbxProductID" styleId="lbxProductID" styleClass="text"></html:hidden>
	 </td>
	 <td width="8%"><bean:message key="lbl.schemeLOV"/><font color="red">*</font></td>
            <td width="20%">
            
             <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/> 
          		<html:hidden property="lbxSchemeId" styleId="lbxSchemeId"  />
          		
          		 <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(287,'approvalLevelDefAddForm','scheme','product','lbxSchemeId', 'lbxProductID','Please firstly select Product then Scheme','','lbxSchemeId');" value=" " styleClass="lovbutton"> </html:button>
               
           						    
                </td>
         
  </tr>



    <tr> 
  
    <td width="13%"><bean:message key="lbl.amountFrom" /><font color="red">*</font></td>
    <td width="13%" ><html:text property="amountFrom" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);"
									onkeyup="checkNumber(this.value, event,18,id);"
									onblur="formatNumber(this.value,id);removeCommaAmountFrom(this.value);"
									onkeypress="return numbersonly(event,id,18)"  styleClass="text" styleId="amountFrom" maxlength="18" />
			<html:hidden  styleId="amountFromDesc" property="amountFromDesc"	value="" />
     		<html:hidden  styleId="amountFromId" property="amountFromId"	value="" /></td>
    
    <td width="13%"><bean:message key="lbl.amountTo" /><font color="red">*</font></td>
    <td width="13%" ><html:text property="amountTo" style="text-align: right" onfocus="keyUpNumber(this.value, event,18,id);"
									onkeyup="checkNumber(this.value, event,18,id);"
									onblur="formatNumber(this.value,id);removeCommaAmountTo(this.value);"
									onkeypress="return numbersonly(event,id,18)"  styleClass="text" styleId="amountTo" maxlength="18" />
			<html:hidden  styleId="amountToDesc" property="amountToDesc"	value="" />
     		<html:hidden  styleId="amountToId" property="amountToId"	value="" /></td>
        
     </tr>
          <tr>
          <td width="13%"><bean:message key="lbl.approvalLevel" /></td>
   
   <td width="13%" ><html:select property="findApprovalLevel" styleClass="text" onchange="checklevel();" styleId="findApprovalLevel" >
   		<logic:present name="pmstSize">
   		<% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { %>
				   <html:option value="<%=CommonFunction.checkNull(i).toString()%>"><%=CommonFunction.checkNull(i).toString()%></html:option>
		<%} %>
		</logic:present>
   </html:select>   
   </td>
         <td>
							<bean:message key="lbl.active" />
		</td>
						<td>
							<input type="checkbox" name="status" id="status"
								checked="checked" />
						</td>
		</tr>	
</table>

<br/>
<br/>

<td>
<table width="100%" cellspacing="2" cellpadding="0" border="0">
<tr>
 <td>
    <fieldset>
     <legend>
     <bean:message key="lbl.minApprovalReq" />
     </legend>
     <table width="30%" cellpadding="0" cellspacing="1" border="0">
     <% 	int j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { 
   			String k = CommonFunction.checkNull(i).toString();
   			request.setAttribute("level",k);
   	%>
				
	    <tr>
	     <td width="13%"><bean:message key="lbl.level" /><%=k %></td>
	     <td><html:text property="level${level}" onkeypress="return isNumberKey(event);" onblur="hideUserLevel${level}(id);" styleClass="text" styleId="level${level}"  maxlength="10" /></td>
		</tr>
		<%} %>
			
     </table>
     </fieldset>
   </td>

 <td>
  <fieldset>
    <legend>
    <bean:message key="lbl.masterApprove" />
    </legend>
     <table width="100%" cellspacing="1" cellpadding="0" border="0">
     <% 	j = Integer.parseInt(request.getAttribute("pmstSize").toString());
   			for(int i=1;i<=j;i++) { 
   			String k = CommonFunction.checkNull(i).toString();
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
         <html:hidden  styleId="levelId${id}" property="levelId${id}"	value="${id}" />
     	<html:hidden  styleId="levelDesc${id}" property="levelDesc${id}"	value="${id}" />
         <td><bean:message key="lbl.user1" /></td>
    <td> <html:text property="user${id }1" styleClass="text"  styleId="user${id }1"  readonly="true" />
    <html:button property="<%=button1%>" styleId="<%=button1%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}1','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}1')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
        <html:hidden  property="lbxUserSearchId1" styleId="lbxUserSearchId1" value=""/>
       <html:hidden  property="<%=userHiddenId1%>" styleId="<%=userHiddenId1%>" />
     </td>
   
         <td><bean:message key="lbl.user2" /></td>
    <td> <html:text property="user${id }2" styleClass="text" styleId="user${id }2"  readonly="true"  />
    <html:button property="<%=button2%>" styleId="<%=button2%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}2','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}2')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId2" styleId="lbxUserSearchId2" value="" />
       <html:hidden  property="<%=userHiddenId2%>" styleId="<%=userHiddenId2%>" />
     </td>
    
      <td><bean:message key="lbl.user3" /></td>
    <td> <html:text property="user${id }3" styleClass="text" styleId="user${id }3"  readonly="true"  />
    <html:button property="<%=button3%>" styleId="<%=button3%>" value=" " styleClass="lovbutton" onclick="openLOVCommon(247,'approvalLevelDefAddForm','user${id}3','amountToDesc|amountFromDesc|levelDesc${id}','', 'amountToId|amountFromId|levelId${id}','','','lbxUserSearchId${id}3')"></html:button>  
       <html:hidden  property="lbxUserSearchId3" styleId="lbxUserSearchId3" value="" />
        <html:hidden  property="<%=userHiddenId3%>" styleId="<%=userHiddenId3%>" />
     </td>
     </tr>
     <%} %>
         
     </table>
     </fieldset>
     </td>
</tr>
  <tr> 
    <td>
    <button type="button" name="add" id="add" class="topformbutton2" title="Alt+V" accesskey="V" onclick="SaveApprovalLevel('N');"><bean:message key="button.save" /></button></td>
  </tr>
</table>

</logic:notPresent>
</logic:notPresent>

 </fieldset>  
   


<br/>

           
	</html:form>	
		
<logic:present name="exist">
<script type="text/javascript">
	alert("<bean:message key="lbl.dataExist" />");
	document.getElementById("product").value="";
    document.getElementById("lbxProductID").value="";
    document.getElementById("scheme").value="";
    document.getElementById("lbxSchemeId").value="";
	document.getElementById("approvalLevelDefAddForm").action="approvalLevelDefSearchBehind.do?method=OpenApprovalLevelMainJsp";		
	document.getElementById("approvalLevelDefAddForm").submit();
</script>
</logic:present>
	<logic:present name="sms">
		<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		if('<%=request.getAttribute("decideSearchJsp").toString()%>'=='N')
		{
		document.getElementById("product").value="";
    	document.getElementById("lbxProductID").value="";
    	document.getElementById("scheme").value="";
    	document.getElementById("lbxSchemeId").value="";
		document.getElementById("approvalLevelDefAddForm").action="approvalLevelDefSearchBehind.do?method=OpenApprovalLevelMainJsp";		
	    document.getElementById("approvalLevelDefAddForm").submit();
	    }

	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		if('<%=request.getAttribute("decideSearchJsp").toString()%>'=='N')
		{
		 location.href="<%=request.getContextPath()%>/approvalLevelDefSearchBehind.do?method=OpenApprovalLevelMainJsp";
		 }

	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='N')
	{
		alert("<bean:message key="lbl.errorSuccess" />");	
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='check')
	{
		alert("Amount must be different ");	
	}

</script>
</logic:present>	
<logic:present name="smsForward">
		<script type="text/javascript">
 if('<%=request.getAttribute("smsForward").toString()%>'=='F')
	{
		alert("<bean:message key="msg.ForwardSuccessfully" />");
		document.getElementById("product").value="";
    	document.getElementById("lbxProductID").value="";
    	document.getElementById("scheme").value="";
    	document.getElementById("lbxSchemeId").value="";
		document.getElementById("approvalLevelDefAddForm").action="approvalLevelDefSearchBehind.do?method=OpenApprovalLevelMainJsp";		
	    document.getElementById("approvalLevelDefAddForm").submit();
	}
</script>
</logic:present>
	
  </body>
		</html:html>
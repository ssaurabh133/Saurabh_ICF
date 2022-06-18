<!--Author Name : Prashant Kumar-->
<!--Date of Creation : 17-sep-2012-->
<!--Purpose  : Information of Verification Mapping Master-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>

<%@ page language="java" import="java.util.ResourceBundle.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>

<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>
		

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/verificationQuestionScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);

%>
function fntab()
{
   document.getElementById('countryMasterForm').countryDes.focus();
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
<style type="text/css">
textarea {

color:#000;
font-family:arial,serif;
font-size:13px;
padding-left:2px;
width:750px;
resize:none;
height:50px;
}

</style>
</head>

<body onload="enableAnchor();" onunload="closeAllLovCallUnloadBody();">
<html:form styleId="verificationQuestMasterForm"  method="post"  action="/verificationQuestProcessingMaster" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<fieldset>
<legend><bean:message key="lbl.ver.question" /></legend>
  <table width="100%">
    <tr>
   
	<html:hidden property="verificationQuestId" styleId="verificationQuestId"  value="${requestScope.list[0].verificationQuestId}" />

      <td><bean:message key="lbl.verification.type" /><span><font color="red">*</font></span></td>
           
     <td>
     
     
     <html:text property="verificationType" styleClass="text" styleId="verificationType" maxlength="100" readonly="true" tabindex="-1" value="${requestScope.list[0].verificationType}"/>
			<html:hidden  property="lbxverificationType" styleId="lbxverificationType" value=""/>
		<logic:notPresent name="list">
		
		     
		  		<html:button property="verificationTypeButton" styleId="verificationTypeButton" onclick="closeLovCallonLov1();openLOVCommon(454,'verificationQuestMasterForm','verificationType','','verificationType', '','','','verificationSubType','entityType','entitySubType');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	
			
		</logic:notPresent>
		
     		
     </td>
         
      <td><bean:message key="lbl.verification.subtype" /><span><font color="red">*</font></span></td>
           
     <td>
     
     
     <html:text property="verificationSubType" styleId="verificationSubType" readonly="true" styleClass="text" value="${requestScope.list[0].verificationSubType}"  />
    	  
           
     </td>

    </tr>
    
       <tr >
      
        <td><bean:message key="lbl.veri.entityType" /><span><font color="red">*</font></span></td>
	      <td >
	    
	          <html:text property="entityType" styleId="entityType" readonly="true" styleClass="text" value="${requestScope.list[0].entityType}"  />
	       
	       </td>
	       
	       <td ><bean:message key="lbl.veri.sub.entityType" /><span><font color="red">*</font></span></td>
	      <td >
	    
	          <html:text property="entitySubType" styleId="entitySubType" readonly="true" styleClass="text" value="${requestScope.list[0].entitySubType}"  />
	       
	       </td>
     
	       
	                    
	      
	      </tr>
      
      <tr >
      
       <td ><bean:message key="lbl.ver.question" /><span><font color="red">*</font></span></td>
	      <td>
	    	<html:textarea property="verificationQuest" styleClass="text" styleId="verificationQuest" value="${requestScope.list[0].verificationQuest}"></html:textarea>
	            
	       </td>
	         <!-- code added by manish  -->
	      <td><bean:message key="address.type" /></td>
	      <td>
				<html:select property="addrType" styleId="addrType" value="${requestScope.list[0].addrType}" styleClass="text" >
					<option value=""><bean:message key="select" /></option>
					<logic:present name="addrList">
					<logic:notEmpty name="addrList">
					<html:optionsCollection name="addrList" label="addressDesc" value="addressId" />
					</logic:notEmpty>
					</logic:present>
				</html:select>
		 </td>
	       
	       
	       
	      </tr>
	      <tr>
      
      <td ><bean:message key="lbl.ver.required" /><span><font color="red">*</font></span></td>
	       <td>
	       
	       	 <html:select property="verificationRequired" styleId="verificationRequired" styleClass="text" value="${requestScope.list[0].verificationRequired}">
     		
     			
     			<html:option value="Y">YES</html:option>
     			<html:option value="N">NO</html:option>
     			
     		
     		  </html:select>
	       
	       </td>
	       
	     <td ><bean:message key="lbl.questionSeqNo" /><span><font color="red">*</font></span></td>
	     <td><html:text property="qSequenceNo" styleId="qSequenceNo" maxlength="3" styleClass="text" onkeypress="return isNumberKey(event);"  value="${requestScope.list[0].qSequenceNo}" ></html:text></td>
	       
	  </tr>
	       <tr>
	     <td><bean:message key="lbl.productType" /></td>
	      <td>
				<html:select property="productType" styleId="productType" value="${requestScope.list[0].productType}" styleClass="text" onchange="return chkProductType()">
				<html:option value="">---Select---</html:option>
				<html:option value="A">All</html:option>
				<html:option value="S">Selective</html:option>
					
				</html:select>
		 </td>
			  		 <td><bean:message key="lbl.product" /></td>
			<td>
			
			 <html:hidden  property="lbxproduct" styleId="lbxproduct" value="${requestScope.list[0].lbxproduct}" />
			<html:select property="product" styleId="product" size="5"  multiple="multiple" style="width: 120" >
   			<logic:present name="productList">
   			<logic:notEmpty name="productList">
       			 <html:optionsCollection name="productList" value="productId" label="productDesc"/>
       		</logic:notEmpty>
       		</logic:present>
     		</html:select>
      		<html:button property="productButton1" styleId="productButton1" onclick="return openMultiSelectLOVCommon(2073,'verificationQuestMasterForm','product','','', '','','','lbxproduct');" value=" " styleClass="lovbutton"></html:button>
     			   
		   
	</td>    
			  </tr>    
	 <tr>
	       
    <td><bean:message key="lbl.status" /></td>
  
        
       
       <logic:present name="status"> 
      
	
     
       <td>
      <logic:equal value="Active" name="status">
              <input type="checkbox" name="questStatus" id="questStatus" checked="checked" />
      </logic:equal>
      
         <logic:notEqual value="Active" name="status">
      	 		  <input type="checkbox" name="questStatus" id="questStatus"  />
         </logic:notEqual></td>
      </logic:present>
      <logic:notPresent name="status">
   		<td><input type="checkbox" name="questStatus" id="questStatus"  checked="checked"/></td>
    </logic:notPresent>    
 
      
    </tr>
    <tr>
    <td>
    
  
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="saveVerificationQuestion();" class="topformbutton2" ><bean:message key="button.save" /></button>
  <logic:present name="list">
		  		<button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="refreshVerificationQuestion();" class="topformbutton2" ><bean:message key="button.refresh" /></button>	
	</logic:present>
   </td>
    </tr>

	</table>		


</fieldset>
</html:form>		

	<fieldset>
	<legend><bean:message key="lbl.verification.quest.master"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
 		<td ><b><bean:message key="lbl.verification.quest.id"/></b></td>
 		<td ><b><bean:message key="lbl.questionSeqNo"/></b></td>
 		<td ><b><bean:message key="lbl.verification.type"/></b></td>
 		<td ><b><bean:message key="lbl.verification.subtype"/></b></td>
 		<td ><b><bean:message key="lbl.ver.question"/></b></td>
        <td ><b><bean:message key="lbl.ver.required"/></b></td>
        <td ><b><bean:message key="address.type"/></b></td>

        <td ><b><bean:message key="lbl.active"/></b></td>
              
   
	</tr>

<logic:present name="grideList">
	<logic:iterate name="grideList" id="sublist" indexId="counter">
	<tr class="white1">


       	<td >
		
			${sublist.verificationModifyLink}
       </td>
       <td >
         ${sublist.qSequenceNo}
      </td>
      
      <td >
		
		${sublist.verificationType}
       </td>
      
	
     <td >
         ${sublist.verificationSubType}
      </td>
	<td >
		
		${sublist.verificationQuest}
       </td>
      
	
     <td >
         ${sublist.verificationRequired}
      </td>
      <td >
         ${sublist.addrType}
      </td>
      
   
      
        <td >
        
         ${sublist.questStatus}
     
       </td>
 
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
	
           
		</fieldset>
     
	
<logic:present name="sms">
<script type="text/javascript">
	
	if('<%=request.getAttribute("sms")%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		DisButClass.prototype.EnbButMethod();
		//location.href="verificationQuestMasterBehindAction.do";
	 
	}
	else if('<%=request.getAttribute("sms")%>'=='DUBLICATE')
	{
		alert("<bean:message key="lbl.ver.dublicate" />");	
			DisButClass.prototype.EnbButMethod();
	}

	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert("<bean:message key="lbl.dataError" />");	
		DisButClass.prototype.EnbButMethod();
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>
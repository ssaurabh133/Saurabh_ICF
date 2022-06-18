<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 17-May-2011-->
<!--Purpose  : Information of Bank Master1-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>	 
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript">

</script>
</head>

<body onload="disableFieldForDealMandatory();enableAnchor();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
  <html:form styleId="sblMaster" action="/sblMasterAdd">
 	<html:errors/>
   <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 
  <fieldset>
	<legend><bean:message key="lbl.sblMaster"/></legend>
	<table width="100%"  >
  <tr>
  <td>
   
  
 	 <logic:present name="editVal">
  <%-- <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>--%>
    <logic:iterate id="listObj" name="list"> 
    <tr>
    <td><bean:message key="lbl.productName"/><span><font color="red">*</font></span></td>
    <td>
	<html:text property="product" styleId="product" styleClass="text"  value="${requestScope.list[0].productDes}" readonly="true" tabindex="-1"></html:text>
	<!-- <input type="button" class="lovbutton" id="lbxProduct" onclick="openLOVCommon(230,'productMaster','lbxProductID','','','','','','product')"
	value=" " tabindex="3" name="dealButton">-->
	<html:hidden property="lbxProductID" styleId="lbxProductID" value="" styleClass="text"></html:hidden>
	</td>
	</tr>
	<tr>
	<td><bean:message key="lbl.singleBorrowerLimit"/><span><font color="red">*</font></span></td>
	<td><html:text property="singleBorrowerLimit" styleId="singleBorrowerLimit" maxlength="10" styleClass="text" value="${requestScope.list[0].singleBorrowerLimit}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onchange="numberFormatting(this.id,'2');"/></td>
	</tr>
	<tr>
	<td><bean:message key="lbl.groupBorrowerLimit"/><span><font color="red">*</font></span></td>
	<td><html:text property="groupBorrowerLimit" styleId="groupBorrowerLimit" maxlength="10" styleClass="text" value="${requestScope.list[0].groupBorrowerLimit}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onchange="numberFormatting(this.id,'2');"/></td>
	</tr>
	<tr>
	   <td>
     	<bean:message key="lbl.recStatus"/> </td>
        <td>
     			 <logic:equal value="A" name="recStatus">
              			<input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="A" name="recStatus">
      	 		  <input type="checkbox" name="recStatus" id="recStatus" />
         </logic:notEqual></td>
         </tr>
   </logic:iterate>
   </logic:present>
    
    
    <logic:notPresent name="editVal">
   <!-- <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/> -->
     <tr>
    <td><bean:message key="lbl.productName"/><span><font color="red">*</font></span></td>
    <td>
	<html:text property="product" styleId="product" styleClass="text"  value="${requestScope.list[0].productDes}" readonly="true" tabindex="-1"></html:text>
	<input type="button" class="lovbutton" id="lbxProduct" onclick="openLOVCommon(17,'productMaster','lbxProductID','','','','','','product')"
	value=" " tabindex="3" name="dealButton">
	<html:hidden property="lbxProductID" styleId="lbxProductID" value="" styleClass="text"></html:hidden>
	</td>
	</tr>
	<tr>
	<td><bean:message key="lbl.singleBorrowerLimit"/><span><font color="red">*</font></span></td>
	<td><html:text property="singleBorrowerLimit" styleId="singleBorrowerLimit" maxlength="10" styleClass="text" value="${requestScope.list[0].singleBorrowerLimit}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onchange="numberFormatting(this.id,'2');"/></td>
	</tr>
	<tr>
	<td><bean:message key="lbl.groupBorrowerLimit"/><span><font color="red">*</font></span></td>
	<td><html:text property="groupBorrowerLimit" styleId="groupBorrowerLimit" maxlength="10" styleClass="text" value="${requestScope.list[0].groupBorrowerLimit}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onchange="numberFormatting(this.id,'2');"/></td>
	</tr>
	<tr>
	  <td> <bean:message key="lbl.recStatus"/></font></span></td>
        <td>
              			<input type="checkbox" name="recStatus" id="recStatus" checked="" />
        </td>
	</tr>
   </logic:notPresent>

   
	 <logic:present name="editVal">
	 	 <td>
	 	 <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditSBL();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
	 </logic:present>

     <logic:present name="editValUpdate">
	 	 <td>
	 	 <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditSBL();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
	 </logic:present>
	 
	 <logic:present name="save">
	  <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
		<td>
		<button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnsblSave();" class="topformbutton2" ><bean:message key="button.save" /></button></td>
	 </logic:present>	
	 
	 
 </tr>

  </table></fieldset>
</html:form>

<logic:present name="sms">
		<script type="text/javascript">

			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" />");
				document.getElementById("sblMaster").action="SBLGBLMasterBehind.do";
	    		document.getElementById("sblMaster").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.dataModify" />");
				document.getElementById("sblMaster").action="SBLGBLMasterBehind.do";
	    		document.getElementById("sblMaster").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="E")
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

<!--Author Name : Yogesh
Date of Creation : 29-April-2011
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
    	     
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/chargeCodeMaster.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		
   <script type="text/javascript"> 
  
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('chargeCodeMasterForm').manualAdviceFlag.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('chargeCodeMasterForm').chargeDescription.focus();
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
  
  <body onload="enableAnchor();disabletaxapp();fntab();init_fields();">
  <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
  <logic:present name="modify">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
  		 <html:form styleId="chargeCodeMasterForm" action="/chargeCodeAction" onsubmit="return chargeCodeModify('${requestScope.list[0].chargeCode}');">

  <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
  <html:hidden property="systemDefinedFlag"
				styleId="systemDefinedFlag"
							value="${requestScope.list[0].systemDefinedFlag}" />
  <fieldset>
<legend><bean:message key="lbl.chargeCodeMaster" /></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="4" cellspacing="2">
  <tr>
  <html:hidden  property="chargeCode" styleId="chargeCode"  value="${requestScope.list[0].chargeCode}" />
        
    <td><bean:message key="lbl.chargeDescription"/><span><font color="red">*</font></span></td>
   <logic:present name="modify">

    <td><html:text property="chargeDescription" styleId="chargeDescription" styleClass="text" maxlength="50" readonly="true" value="${requestScope.list[0].chargeDescription}"/></td>
</logic:present>
<logic:notPresent name="modify">

    <td><html:text property="chargeDescription" styleId="chargeDescription" styleClass="text" maxlength="50" /></td>
</logic:notPresent>

<td >
		   
	<bean:message key="lbl.serviceTax1"/></td>
	<td>
	<input type="hidden" name="hCommon" id="hCommon"/> 
	     <html:text property="taxdescription1" styleClass="text" styleId="taxdescription1" value="${requestScope.list[0].taxdescription1}" maxlength="100" readonly="true" tabindex="-1"/>
		 <html:hidden  property="lbxTaxdescription1" styleId="lbxTaxdescription1" value="${requestScope.list[0].lbxTaxdescription1}" />

		 <html:button property="taxdescription1Button" styleId="taxdescription1Button" onclick="closeLovCallonLov1();openLOVCommon(6081,'chargeCodeMasterForm','taxdescription1','','', '','','','lbxTaxdescription1');" value=" " styleClass="lovbutton"></html:button>

        		  
	
	</td>
</tr>
<tr>

<td>
		   
	<bean:message key="lbl.serviceTax2"/></td>
	<td>
	<input type="hidden" name="hCommon" id="hCommon"/> 
	     <html:text property="taxdescription2" styleClass="text" styleId="taxdescription2" value="${requestScope.list[0].taxdescription2}" maxlength="100" readonly="true" tabindex="-1"/>
		 <html:hidden  property="lbxTaxdescription2" styleId="lbxTaxdescription2"  value="${requestScope.list[0].lbxTaxdescription2}" />

		 <html:button property="taxdescription1Button" styleId="taxdescription1Button" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','taxdescription2','','', '','','','lbxTaxdescription2');" value=" " styleClass="lovbutton"></html:button>

        		  
	
	</td>
	<td>
		   
	<bean:message key="lbl.serviceTax3"/></td>
	<td>
	<input type="hidden" name="hCommon" id="hCommon"/> 
	     <html:text property="taxdescription3" styleClass="text" styleId="taxdescription3" value="${requestScope.list[0].taxdescription3}" maxlength="100" readonly="true" tabindex="-1"/>
		 <html:hidden  property="lbxTaxdescription3" styleId="lbxTaxdescription3"  value="${requestScope.list[0].lbxTaxdescription3}" />

		 <html:button property="taxdescription1Button" styleId="taxdescription1Button" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','taxdescription3','','', '','','','lbxTaxdescription3');" value=" " styleClass="lovbutton"></html:button>

        		  
	
	</td>
	</tr>
	<tr>
	
      <td><bean:message key="lbl.cgstcode" /> </td>
      <td>
	   <input type="hidden" name="hCommon" id="hCommon"/> 
	   <html:text property="cgstId" styleClass="text" styleId="cgstId" value="${requestScope.list[0].cgstId}" maxlength="50" readonly="true" tabindex="-1"/>
	  <html:hidden  property="lbxCgstId" styleId="lbxCgstId"  value="${requestScope.list[0].lbxCgstId}" />
	  <html:button property="cgstId" styleId="cgstIdButton" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','cgstId','','', '','','','lbxCgstId');" value=" " styleClass="lovbutton"></html:button></td>
     </td>
  
      <td><bean:message key="lbl.sgstcode" /> </td>
      <td>
	  <input type="hidden" name="hCommon" id="hCommon"/> 
	  <html:text property="sgstId" styleClass="text" styleId="sgstId" value="${requestScope.list[0].sgstId}" maxlength="50" readonly="true" tabindex="-1" />
	   <html:hidden  property="lbxSgstId" styleId="lbxSgstId"  value="${requestScope.list[0].lbxSgstId}" />
   <html:button property="sgstId" styleId="sgstIdButton" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','sgstId','','', '','','','lbxSgstId');" value=" " styleClass="lovbutton"></html:button></td>
     </td>
      </tr>
      <tr>
	  
      <td><bean:message key="lbl.igstcode" /> </td>
      <td>
	  <input type="hidden" name="hCommon" id="hCommon"/> 
	  <html:text property="igstId" styleClass="text" styleId="igstId" value="${requestScope.list[0].igstId}" maxlength="50" readonly="true" tabindex="-1" />
	   <html:hidden  property="lbxIgstId" styleId="lbxIgstId"  value="${requestScope.list[0].lbxIgstId}" />
   <html:button property="igstId" styleId="igstIdButton" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','igstId','','', '','','','lbxIgstId');" value=" " styleClass="lovbutton"></html:button></td>
     
	 
	  <td><bean:message key="lbl.ugstcode" /> </td>
      <td>
	  <input type="hidden" name="hCommon" id="hCommon"/> 
	   <html:text property="ugstId" styleClass="text" styleId="ugstId" value="${requestScope.list[0].ugstId}" maxlength="50" readonly="true" tabindex="-1" />
	   <html:hidden  property="lbxUgstId" styleId="lbxUgstId"  value="${requestScope.list[0].lbxUgstId}" />
   <html:button property="ugstId" styleId="ugstIdButton" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','ugstId','','', '','','','lbxUgstId');" value=" " styleClass="lovbutton"></html:button></td>
     
	 </tr>	
	 <tr>

<td><bean:message key="lbl.taxapplicable" /></td>
			
			<td>
			
				 <html:select property="taxapp" styleId="taxapp" value="${requestScope.list[0].taxapp}" onchange="disabletaxapp();" >
           
                <html:option value="Y">YES</html:option>
                 <html:option value="N">NO</html:option>
			</html:select>
			</td>
			
			 <td><bean:message key="lbl.duereceiptbasis"/><span><font color="red">*</font></span></td>
         <td>
		
            <html:select property="dueReceiptBasis" styleId="dueReceiptBasis" value="${requestScope.list[0].dueReceiptBasis}" >
            <html:option value=" ">--SELECT--</html:option>
                <html:option value="D">DUE</html:option>
                 <html:option value="R">RECEIPT</html:option>
			</html:select>
            </td>
			</tr>
			<tr>
			
				<td><bean:message key="lbl.taxrate1" /></td>
			
			<td>
			
				<html:text property="taxrate1" styleId="taxrate1"  value="${requestScope.list[0].taxrate1}" styleClass="text" tabindex="-1" />
				
  </td>
      
            <td><bean:message key="lbl.taxrate2" /></td>
			
			<td>
			
				<html:text property="taxrate2" styleId="taxrate2"  value="${requestScope.list[0].taxrate2}" styleClass="text" tabindex="-1" />
				
  </td>
			
         </tr>
		<tr>
		<td><bean:message key="lbl.hsc/scndes" /></td>
			
			<td>
			
				<html:text property="hscscn" styleId="hscscn" maxlength="100" value="${requestScope.list[0].hscscn}" styleClass="text" tabindex="-1" />
				
				
			</td>
			 <td><bean:message key="lbl.hsc/scncode" /></td>
			
			<td>
			
				<html:text property="hscscnc" styleId="hscscnc" maxlength="50" value="${requestScope.list[0].hscscnc}" styleClass="text" tabindex="-1" />
				
				
			</td>


		</tr>
 
		 
        
  <tr>
    			
   <td>
       <bean:message key="lbl.manualAdviceFlag"/> </td>
       <td>
   			  <logic:equal value="Y" name="systemDefinedFlag">
   			   <logic:equal value="Y" name="adviceFlag">
            	  <input type="checkbox" name="manualAdviceFlag" id="manualAdviceFlag" checked="checked" disabled="disabled"/>
      			</logic:equal>
      
         <logic:notEqual value="Y" name="adviceFlag">
      	 		  <input type="checkbox" name="manualAdviceFlag" id="manualAdviceFlag"  disabled="disabled"/>
         </logic:notEqual>
         </logic:equal>
         
        <logic:notEqual value="Y" name="systemDefinedFlag">
   			   <logic:equal value="Y" name="adviceFlag">
            	  <input type="checkbox" name="manualAdviceFlag" id="manualAdviceFlag" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Y" name="adviceFlag">
      	 		  <input type="checkbox" name="manualAdviceFlag" id="manualAdviceFlag"  />
         </logic:notEqual>
         </logic:notEqual>
         </td>
   </tr>
  
    <tr>
       <td>
       <bean:message key="lbl.waveOffFlag"/> </td>
       <td>
       			<logic:equal value="Y" name="systemDefinedFlag">
   			   <logic:equal value="Y" name="waveOffFlag">
            		  <input type="checkbox" name="waveOffFlag" id="waveOffFlag" checked="checked" disabled="disabled" />
      			</logic:equal>
      
         <logic:notEqual value="Y" name="waveOffFlag">
      	 		  <input type="checkbox" name="waveOffFlag" id="waveOffFlag"  disabled="disabled"/>
         </logic:notEqual>
         </logic:equal>
         
         <logic:notEqual value="Y" name="systemDefinedFlag">
   			   <logic:equal value="Y" name="waveOffFlag">
            		  <input type="checkbox" name="waveOffFlag" id="waveOffFlag" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Y" name="waveOffFlag">
      	 		  <input type="checkbox" name="waveOffFlag" id="waveOffFlag"  />
         </logic:notEqual>
         </logic:notEqual>
         </td>
 		 
			        
		</tr>
   <tr>  
    <td>
     	<bean:message key="lbl.recStatus"/> </td>
        <td>
        		<logic:equal value="Y" name="systemDefinedFlag">
     			 <logic:equal value="A" name="status">
              			<input type="checkbox" name="recStatus" id="recStatus" checked="checked" disabled="disabled"/>
      			</logic:equal>
      
         <logic:notEqual value="A" name="status">
      	 		  <input type="checkbox" name="recStatus" id="recStatus"  disabled="disabled"/>
         </logic:notEqual>
         </logic:equal>
         
         <logic:notEqual value="Y" name="systemDefinedFlag">
     			 <logic:equal value="A" name="status">
              			<input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="A" name="status">
      	 		  <input type="checkbox" name="recStatus" id="recStatus"  />
         </logic:notEqual>
         </logic:notEqual>
        
         </td>
 		 
	  </tr>        
     

      
  <tr>
  
  <logic:notEqual value="N" name="systemDefinedFlag">
	 
	 	 <td align="left" > 
	 	 <button type="button" name="button" id="button" class="topformbutton2" title="Alt+V" accesskey="V" disabled="disabled"><bean:message key="button.save" /></button></td>
	</logic:notEqual> 
  <logic:equal value="N" name="systemDefinedFlag">
	 
	 	 <td align="left" > 
	 	 <button type="button" name="button" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return chargeCodeModify('${requestScope.list[0].chargeCode}');" ><bean:message key="button.save" /></button></td>
	</logic:equal> 
 </tr>
 </table></td>
  </tr></table></fieldset>
</html:form>
  </logic:present>
  
  
   <logic:notPresent name="modify">
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
  <html:form styleId="chargeCodeMasterForm" action="/chargeCodeAction" onsubmit="return chargeCodeSave();">

  <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
  <fieldset>
<legend><bean:message key="lbl.chargeCodeMaster" /></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="4" cellspacing="2">
  <tr>
  <html:hidden  property="chargeCode" styleId="chargeCode"  value="${requestScope.list[0].chargeCode}" />
        
    <td><bean:message key="lbl.chargeDescription"/><span><font color="red">*</font></span></td>
   <logic:present name="modify">
    <td><html:text property="chargeDescription" styleId="chargeDescription" styleClass="text" maxlength="50" readonly="true" value="${requestScope.list[0].chargeDescription}"/></td>
</logic:present>
<logic:notPresent name="modify">
    <td><html:text property="chargeDescription" styleId="chargeDescription" styleClass="text" maxlength="50" /></td>
</logic:notPresent>

<td >
		   
	<bean:message key="lbl.serviceTax1"/></td>
	<td>
	<input type="hidden" name="hCommon" id="hCommon"/> 
	     <html:text property="taxdescription1" styleClass="text" styleId="taxdescription1" value="${requestScope.list[0].taxdescription1}" maxlength="100" readonly="true" tabindex="-1"/>
		 <html:hidden  property="lbxTaxdescription1" styleId="lbxTaxdescription1" value="${requestScope.list[0].lbxTaxdescription1}" />

		 <html:button property="taxdescription1Button" styleId="taxdescription1Button" onclick="closeLovCallonLov1();openLOVCommon(6081,'chargeCodeMasterForm','taxdescription1','','', '','','','lbxTaxdescription1');" value=" " styleClass="lovbutton"></html:button>

        		  
	
	</td>
</tr>
<tr>

<td>
		   
	<bean:message key="lbl.serviceTax2"/></td>
	<td>
	<input type="hidden" name="hCommon" id="hCommon"/> 
	     <html:text property="taxdescription2" styleClass="text" styleId="taxdescription2" value="${requestScope.list[0].taxdescription2}" maxlength="100" readonly="true" tabindex="-1"/>
		 <html:hidden  property="lbxTaxdescription2" styleId="lbxTaxdescription2"  value="${requestScope.list[0].lbxTaxdescription2}" />

		 <html:button property="taxdescription1Button" styleId="taxdescription1Button" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','taxdescription2','','', '','','','lbxTaxdescription2');" value=" " styleClass="lovbutton"></html:button>

        		  
	
	</td>
	<td>
		   
	<bean:message key="lbl.serviceTax3"/></td>
	<td>
	<input type="hidden" name="hCommon" id="hCommon"/> 
	     <html:text property="taxdescription3" styleClass="text" styleId="taxdescription3" value="${requestScope.list[0].taxdescription3}" maxlength="100" readonly="true" tabindex="-1"/>
		 <html:hidden  property="lbxTaxdescription3" styleId="lbxTaxdescription3"  value="${requestScope.list[0].lbxTaxdescription3}" />

		 <html:button property="taxdescription1Button" styleId="taxdescription1Button" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','taxdescription3','','', '','','','lbxTaxdescription3');" value=" " styleClass="lovbutton"></html:button>

        		  
	
	</td>
	</tr>
	<!-- raj space start for GST -->
	<tr>
	<td><bean:message key="lbl.cgstcode" /> </td>
	<td>
		<input type="hidden" name="hCommon" id="hCommon"/> 
		<html:text property="cgstId" styleClass="text" styleId="cgstId" value="${requestScope.list[0].cgstId}" maxlength="50" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCgstId" styleId="lbxCgstId"  value="${requestScope.list[0].lbxCgstId}" />

		<html:button property="cgstId" styleId="cgstIdButton" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','cgstId','','', '','','','lbxCgstId');" value=" " styleClass="lovbutton"></html:button>
	</td>
	
	<td><bean:message key="lbl.sgstcode" /> </td>
	<td> 
		<input type="hidden" name="hCommon" id="hCommon"/> 
		<html:text property="sgstId" styleClass="text" styleId="sgstId" value="${requestScope.list[0].sgstId}" maxlength="50" readonly="true" tabindex="-1" />
		<html:hidden  property="lbxSgstId" styleId="lbxSgstId"  value="${requestScope.list[0].lbxSgstId}" />
		<html:button property="sgstId" styleId="sgstIdButton" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','sgstId','','', '','','','lbxSgstId');" value=" " styleClass="lovbutton"></html:button>
	</td>
	
	</tr>
	<tr>
	<td><bean:message key="lbl.igstcode" /> </td>
	<td>
	<input type="hidden" name="hCommon" id="hCommon"/> 
	<html:text property="igstId" styleClass="text" styleId="igstId" value="${requestScope.list[0].igstId}" maxlength="50" readonly="true" tabindex="-1" />
	<html:hidden  property="lbxIgstId" styleId="lbxIgstId"  value="${requestScope.list[0].lbxIgstId}" />
	<html:button property="igstId" styleId="igstIdButton" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','igstId','','', '','','','lbxIgstId');" value=" " styleClass="lovbutton"></html:button></td>
	
	
	<td><bean:message key="lbl.ugstcode" /> </td>
	<td>
	<input type="hidden" name="hCommon" id="hCommon"/> 
	<html:text property="ugstId" styleClass="text" styleId="ugstId" value="${requestScope.list[0].ugstId}" maxlength="50" readonly="true" tabindex="-1" />
	<html:hidden  property="lbxUgstId" styleId="lbxUgstId"  value="${requestScope.list[0].lbxUgstId}" />
	<html:button property="ugstId" styleId="ugstIdButton" onclick="closeLovCallonLov1();openLOVCommon(6082,'chargeCodeMasterForm','ugstId','','', '','','','lbxUgstId');" value=" " styleClass="lovbutton"></html:button></td>
	
	</tr>
	<tr>

	<td><bean:message key="lbl.taxapplicable" /></td>

	<td>

	<html:select property="taxapp" styleId="taxapp"  value="${requestScope.list[0].taxapp}" onchange="disabletaxapp();" >

	<html:option value="Y">YES</html:option>
	<html:option value="N">NO</html:option>
	</html:select>
	</td>
	

	<td><bean:message key="lbl.duereceiptbasis"/><span><font color="red">*</font></span></td>
	<td>

	<html:select property="dueReceiptBasis" styleId="dueReceiptBasis" value="${requestScope.list[0].dueReceiptBasis}" >
	<html:option value=" ">--SELECT--</html:option>
	<html:option value="D">DUE</html:option>
	<html:option value="R">RECEIPT</html:option>
	</html:select>
	</td>

	</tr>
	<tr>
	<td><bean:message key="lbl.taxrate1" /></td>

	<td>

	<html:text property="taxrate1" styleId="taxrate1" value="${requestScope.list[0].taxrate1}" styleClass="text" tabindex="-1" />

	</td>



	<td><bean:message key="lbl.taxrate2" /></td>

	<td>

	<html:text property="taxrate2" styleId="taxrate2"  value="${requestScope.list[0].taxrate2}" styleClass="text" tabindex="-1" />

	</td>

	</tr> 
	<tr>
			 <td><bean:message key="lbl.hsc/scndes" />	</td>
		
			<td>
			
				<html:text property="hscscn" styleId="hscscn" maxlength="100" value="${requestScope.list[0].hscscn}" styleClass="text" tabindex="-1" />
				
				
			</td>
		          <td><bean:message key="lbl.hsc/scncode" /></td>
			
			<td>
			
				<html:text property="hscscnc" styleId="hscscnc" maxlength="50" value="${requestScope.list[0].hscscnc}" styleClass="text" tabindex="-1" />
				
				
			</td>

	</tr>

	<!-- space end by raj -->
	<tr>


   <td>
       <bean:message key="lbl.manualAdviceFlag"/> </td>
       <td>
   			   <logic:equal value="Y" name="adviceFlag">
            	  <input type="checkbox" name="manualAdviceFlag" id="manualAdviceFlag" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Y" name="adviceFlag">
      	 		  <input type="checkbox" name="manualAdviceFlag" id="manualAdviceFlag"  />
         </logic:notEqual></td>
   </tr>
  
    <tr>
       <td>
       <bean:message key="lbl.waveOffFlag"/> </td>
       <td>
   			   <logic:equal value="Y" name="waveOffFlag">
            		  <input type="checkbox" name="waveOffFlag" id="waveOffFlag" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="Y" name="waveOffFlag">
      	 		  <input type="checkbox" name="waveOffFlag" id="waveOffFlag"  />
         </logic:notEqual></td>
   
    <td>
     	<bean:message key="lbl.recStatus"/> </td>
        <td>
        <input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
        </td>
         
     
         
                 
         
    </tr>
      
  <tr>
	 
	 		
	 <td align="left" >
	 <button type="button" name="button" id="button" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return chargeCodeSave();" ><bean:message key="button.save" /></button></td>
	   	
 </tr>
 </table></td>
  </tr></table></fieldset>
</html:form>
 </logic:notPresent>		
<logic:present name="msg">
		<script type="text/javascript">

			if('<%=request.getAttribute("msg").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" />");
				document.getElementById("chargeCodeMasterForm").action="chargeCodeSearchBehind.do";
	    		document.getElementById("chargeCodeMasterForm").submit();
			}
			else if('<%=request.getAttribute("msg").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.dataModify" />");
				document.getElementById("chargeCodeMasterForm").action="chargeCodeSearchBehind.do";
	    		document.getElementById("chargeCodeMasterForm").submit();
			}
				else if('<%=request.getAttribute("msg").toString()%>'=="E")
					{
						alert("<bean:message key="msg.notepadError" />");
					}
				else if('<%=request.getAttribute("msg").toString()%>'=="D")
					{
						alert("<bean:message key="lbl.dataExist" />");
					}
	</script>
</logic:present>
   
  </body>
</html:html>

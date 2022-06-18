<%@ page language="java"%>
<%@ page session="true"%>
<%@ page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/manufaturerSupplierMapping.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
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

<body onload="enableAnchor();" ><div align="center"> 
</div><div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/mfrSupplMappingDispatchAction"  styleId="ManufacturerSupplierMappingAddForm"  method="post" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.manufacturerSupplierMapping"/></legend>

	<table width="100%">
	<logic:notPresent name="edit">
	<tr>
  
     <td width="13%"><bean:message key="lbl.manufacturers" /><span><font color="red">*</font></span></td>
     <td width="13%"><html:text property="manufacturerId" styleClass="text" styleId="manufacturerId" readonly="true" tabindex="-1" value="${requestScope.list[0].manufacturerId}"/>
       <html:button property="manufacturerIdButton" styleId="manufacturerIdButton" onclick="openLOVCommon(66,'ManufacturerSupplierMappingAddForm','manufacturerId','','', '','','','manufacturerDesc');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxmachineManufact" styleId="lbxmachineManufact" value="${requestScope.list[0].lbxmachineManufact}" />
     </td>
    
      <td width="13%"><bean:message key="lbl.manufacturerDesc"/></td>
       <td width="13%"><html:text property="manufacturerDesc" styleClass="text" value="${requestScope.list[0].manufacturerDesc}" styleId="manufacturerDesc" maxlength="50" readonly="true"/>
    
     </tr>	    			


    <tr>
   <td width="13%"><bean:message key="lbl.supplier" /><span><font color="red">*</font></span></td>
   		<td>			
			<html:hidden  property="lbxSupplierId" styleId="lbxSupplierId" value="${requestScope.list[0].lbxSupplierId}" />
   			<html:select property="supplierDesc" styleId="supplierDesc" multiple="multiple" style="width: 150" > 
   			  		
     		</html:select>
      		<html:button property="supplierButton" styleId="supplierButton" onclick="return openMultiSelectLOVCommon(410,'ManufacturerSupplierMappingAddForm','supplierDesc','','', '','','','lbxSupplierId');" value=" " styleClass="lovbutton"></html:button>	
   			</td>
   			
   		
   		<td width="13%"><bean:message key="lbl.recStatus" /></td>
   		<td>   		
	  			<input type=checkbox id="recStatus" name="recStatus" checked="checked"/>	  
	  </td>
   		
   </tr>
   </logic:notPresent>
   
   <logic:present name="edit">
   
   	<tr>
  
     <td width="13%"><bean:message key="lbl.manufacturers" /><span><font color="red">*</font></span></td>
     <td width="13%"><html:text property="manufacturerId" styleClass="text" styleId="manufacturerId" readonly="true" tabindex="-1" value="${requestScope.list[0].manufacturerId}"/>
<!--       <html:button property="manufacturerIdButton" styleId="manufacturerIdButton" onclick="openLOVCommon(66,'ManufacturerSupplierMappingAddForm','manufacturerId','','', '','','','manufacturerDesc');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>-->
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxmachineManufact" styleId="lbxmachineManufact" value="${requestScope.list[0].lbxmachineManufact}" />
     </td>
    
      <td width="13%"><bean:message key="lbl.manufacturerDesc"/></td>
       <td width="13%"><html:text property="manufacturerDesc" styleClass="text" value="${requestScope.list[0].manufacturerDesc}" styleId="manufacturerDesc" maxlength="50" readonly="true"/>
    
     </tr>	    			


    <tr>
   <td width="13%"><bean:message key="lbl.supplier" /><span><font color="red">*</font></span></td>
   		<td>			
			<html:hidden  property="lbxSupplierId" styleId="lbxSupplierId" value="${requestScope.supplierDescIds}" />
   			<html:select property="supplierDesc" styleId="supplierDesc" multiple="multiple" style="width: 150" > 
   			<logic:present name="supplierDescList"> 			
       		<html:optionsCollection name="supplierDescList" value="supplierId" label="supplierDesc"/>  
       		</logic:present>   		
     		</html:select>
      		<html:button property="supplierButton" styleId="supplierButton" onclick="return openMultiSelectLOVCommon(410,'ManufacturerSupplierMappingAddForm','supplierDesc','','', '','','','lbxSupplierId');" value=" " styleClass="lovbutton"></html:button>	
   			</td>
   			
   		
   		<td width="13%"><bean:message key="lbl.recStatus" /></td>
   		<td>
   		
   		<logic:present name="Active">	
	  	<input type=checkbox id="recStatus" name="recStatus" checked="checked"/>	  
	    </logic:present>	    	   
   		<logic:present name="InActive" >		
	  	<input type=checkbox id="recStatus" name="recStatus" />	  
	    </logic:present>
   		
   		
	    </td>
   		
   </tr>
   
   </logic:present>
   </table> 
   <table width="100%">
   		<tr>
   			<logic:present name="edit" >   			
   				 <button type="button" name="update" id="update" class="topformbutton2" onclick="return updateMfrSupplMappingScreen();"><bean:message key="button.save" /></button>
   			</logic:present>
   			<logic:notPresent name="edit">
   				 <button type="button" name="save" id="save" class="topformbutton2"  onclick="return saveMfrSupplMappingScreen();"><bean:message key="button.save" /></button>
   			</logic:notPresent>
   		</tr>
   </table>
</fieldset>
</html:form>

<logic:present name="save">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataSave" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("ManufacturerSupplierMappingAddForm").action=contextPath+"/mfrSupplMappingBehindAction.do?flag=S";
		document.getElementById("ManufacturerSupplierMappingAddForm").submit();
	</script>
</logic:present>
<logic:present name="notSave">
	<script type="text/javascript">	
		alert("<bean:message key="msg.DataNotSaved" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("ManufacturerSupplierMappingAddForm").action=contextPath+"/mfrSupplMappingDispatchAction.do?method=openMfrSupplMappingScreen";
		document.getElementById("ManufacturerSupplierMappingAddForm").submit();
	</script>
</logic:present>
<logic:present name="update">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataUpdate" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("ManufacturerSupplierMappingAddForm").action=contextPath+"/mfrSupplMappingBehindAction.do?flag=S";
		document.getElementById("ManufacturerSupplierMappingAddForm").submit();
	</script>
</logic:present>
<logic:present name="dataExist">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataExist" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("ManufacturerSupplierMappingAddForm").action=contextPath+"/mfrSupplMappingDispatchAction.do?method=openMfrSupplMappingScreen&flag=S";
		document.getElementById("ManufacturerSupplierMappingAddForm").submit();
	</script>
</logic:present>

</body>	
</html:html>
	
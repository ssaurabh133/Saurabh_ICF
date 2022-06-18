<%--Author Name : Neeraj Kumar Tripathi
Date of Creation : 05-Jan-2012
Purpose  : Make Model Master Add
Documentation : --%>


<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/makeModelMaster.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
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

<body onload="enableAnchor();" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/MakeModelMasterSave" styleId="MakeModelMasterAdd"  method="post" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<logic:present name="makeModelId">
<html:hidden property="makemodelIData" styleId="makemodelIData" value="${makeModelId }" />
</logic:present>

<logic:notPresent name="makeModelId">
<html:hidden  property="makemodelIData" styleId="makemodelIData" value="${makeModelId }"  />
</logic:notPresent>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.makeModelMaster"/></legend>

	<table width="100%">
	<tr>
     <td width="5%"><bean:message key="lbl.productCategory" /><span><font color="red">*</font></span></td>
        <td width="5%">
                  <html:hidden  property="productCategoryHid" styleId="productCategoryHid"  value="${requestScope.list[0].productCategory}" />
                   <html:select styleClass="text"  styleId="productCategory" value="${requestScope.list[0].productCategory}" property="productCategory"  onchange="return clearUsageType();">
                   <html:option value="">--SELECT--</html:option>  
        				<logic:present name="ProductCategory">
        				<html:optionsCollection name="ProductCategory" label="categoryDesc" value="category"/>        				
        				</logic:present>
                   </html:select> 
     </td>
     
<!--     <td>-->
<!--     <html:text property="state" styleClass="text" styleId="state" value="${requestScope.list[0].state}" tabindex="-1" maxlength="20" readonly="true"/>-->
<!--     <html:hidden property="stateId" styleId="stateId" value="${requestScope.list[0].stateId}"/>-->
<!--     <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(256,'MakeModelMasterAdd','stateId','','', '','','','state')" value=" " styleClass="lovbutton"  >-->
<!--     </html:button>       -->
<!--       <html:hidden property="txtStateCode" styleId="txtStateCode" value=""/>     -->
<!--      -->
<!--     </td>    -->
<!--     		<td>-->
<!--			<select id="stateId" name="stateId" size="5" multiple="multiple" style="width: 150px">-->
<!--            </select>-->
<!--            <html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(399,'MakeModelMasterAdd','stateId','','', '','','','state');" value=" " styleClass="lovbutton"></html:button>-->
<!--			<html:hidden property="state" styleId="state" value="${requestScope.stateIds}" />-->
<!--			</td>  -->
		<td width="5%"><bean:message key="lbl.usesType" /><span><font color="red">*</font></span></td>
      	<td width="5%">
      	<html:text property="usesType" tabindex="-1" styleClass="text" readonly="true" styleId="usesType" maxlength="25" value="${requestScope.list[0].usesType}" /> 
      	<html:hidden property="usesTypeId" styleId="usesTypeId" value="${requestScope.list[0].usesTypeId}"/>
        <html:button property="usesTypeButton" styleId="usesTypeButton" tabindex="" onclick="openLOVCommon(373,'MakeModelMasterAdd','usesTypeId','productCategoryHid','', 'productCategory','Pleaes select Product Category First!!','','usesType','productType')" value=" " styleClass="lovbutton"  ></html:button>
        <html:hidden property="txtUsesType" styleId="txtUsesType" value=""/>
        <html:hidden property="productType" styleId="productType" value=""/>        
   		</td>   	
   </tr>
   <!-- Nishant Space starts -->
   <tr>
   		<td width="13%"><bean:message key="lbl.product2" /><font color="red">*</font></td>
      	<td>
	  		<html:text property="product" styleId="product" styleClass="text" value="${requestScope.list[0].product}" readonly="true" tabindex="-1"/>
	  		<input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(569,'MakeModelMasterAdd','product','productCategoryHid','','productCategory','Pleaes select Product Category First!!','','lbxProductID','productType')" value=" " tabindex="26" name="dealButton">
	 		<html:hidden property="lbxProductID" styleId="lbxProductID" value="${requestScope.list[0].lbxProductID}" styleClass="text"></html:hidden>
	 	</td>
	 	
	 	<td width="13%"><bean:message key="lbl.schemeLOV"/></td>
        <td>
             <html:text property="scheme" styleId="scheme" styleClass="text" value="${requestScope.list[0].scheme}" readonly="true" tabindex="-1"/> 
          	 <html:hidden property="lbxSchemeId" styleId="lbxSchemeId"  value="${requestScope.list[0].lbxSchemeId}"/>
       		 <html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(287,'MakeModelMasterAdd','scheme','product','lbxSchemeId', 'lbxProductID','Please firstly select Product then Scheme','','lbxSchemeId');" value=" " styleClass="lovbutton"> </html:button>
       </td> 
  </tr>
   <!-- Nishant Space end -->
   <!-- Nishant space starts -->
   <tr>
   		<td width="13%"><bean:message key="lbl.manufact" /><span><font color="red">*</font></span></td>
   		<td>
   			<html:text property="manufact" tabindex="-1" styleClass="text" readonly="true" styleId="manufact" maxlength="25" value="${requestScope.list[0].manufact}" /> 
      		<html:hidden property="manufactId" styleId="manufactId" value="${requestScope.list[0].manufactId}"/>
        	<html:button property="manufactButton" styleId="manufactButton" tabindex="" onclick="openLOVCommon(272,'MakeModelMasterAdd','manufactId','','', '','','','manufact')" value=" " styleClass="lovbutton"  ></html:button>
        	<html:hidden property="manufacturerId" styleId="manufacturerId" value=""/> 
        </td>
        <td width="13%"><bean:message key="lbl.make" /><span><font color="red">*</font></span></td>     		
      	<td width="13%">      	
      		
      			<html:text property="make" styleClass="text" styleId="make" maxlength="20" value="${requestScope.list[0].make}"/>
      	
      	</td>
   </tr>
   <!-- Nishant space end -->
   <tr>
   		<td width="13%"><bean:message key="lbl.model" /><span><font color="red">*</font></span></td>     		
      	<td width="13%">      		
      			<html:text property="model" styleClass="text" styleId="model" maxlength="20" value="${requestScope.list[0].model}"/>
      	</td>	
      	<td width="5%"><bean:message key="lbl.underType" /><span><font color="red">*</font></span></td>
    	<td width="5%"><html:select styleClass="text" styleId="type" value="${requestScope.list[0].type}" property="type"> 
    		<html:option value="">---SELECT---</html:option>  
    			<html:option value="N">NEW</html:option>
    			<html:option value="O">OLD</html:option> 
    		</html:select> 
    	</td>      	
   </tr>
   <tr>
     
   <td width="13%"><bean:message key="lbl.LTV" /><span><font color="red">*</font></span></td>     		
      	<td width="13%">
      		<html:text property="ltv" styleClass="text" styleId="ltv" maxlength="10" value="${requestScope.list[0].ltv}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/>
      	</td> 
      	<td width="13%"><bean:message key="lbl.state" /><span><font color="red">*</font></span></td>
   		<td>
		    <logic:present name="search">
			<html:hidden  property="state" styleId="state" value="${requestScope.stateIds}" />
   			<html:select property="stateId" styleId="stateId" size="5" multiple="multiple" style="width: 150" >  
   			<logic:present name="mapp"> 			
       		<html:optionsCollection name="mapp" value="state" label="stateId"/>   
       		</logic:present>   		
     		</html:select>
      		<html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(399,'MakeModelMasterAdd','stateId','','', '','','','state');" value=" " styleClass="lovbutton"></html:button>	
   			</logic:present>
   			
   			
   			<logic:notPresent name="search">
			<html:hidden  property="state" styleId="state" value="${requestScope.stateIds}" />
   			<html:select property="stateId" styleId="stateId" size="5" multiple="multiple" style="width: 150" > 
   			<logic:present name="search"> 			
       		<html:optionsCollection name="mapp" value="state" label="stateId"/>  
       		</logic:present>   		
     		</html:select>
      		<html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(399,'MakeModelMasterAdd','stateId','','', '','','','state');" value=" " styleClass="lovbutton"></html:button>	
   			</logic:notPresent>
   			</td>  
   </tr>
   <tr>
   
   		<td width="13%">Vehicle Segment<span></span></td>     		
      	<td width="13%">
      		       <html:select styleClass="text"  styleId="vct" value="${requestScope.list[0].vct}" property="vct">
                   <html:option value="">--SELECT--</html:option>  
        				<logic:present name="VehicleCategory">
        				<html:optionsCollection name="VehicleCategory" label="vehicleDesc" value="vehicleValue"/>        				
        				</logic:present>
                   </html:select> 
      	</td> 
   		<td>Status</td>
   		<td>   		
   			<logic:notPresent name="list">
	  			<input type=checkbox id="status" name="status" checked="checked"/>
	  		</logic:notPresent>
	  		<logic:present name="list">
	  			<logic:present name="A">
	  				<input type=checkbox id="status" name="status" checked="checked"/>
	  			</logic:present>
	  			<logic:present name="X">
	  				<input type=checkbox id="status" name="status"/>
	  			</logic:present>
	  		</logic:present>
	  
	  </td>
   		
   </tr>
   </table> 
   <table width="100%">
   		<tr>
   			<logic:present name="search" >   			
   				 <button type="button" name="update" id="update" class="topformbutton2" onclick="return updateMakeModelRecord();"><bean:message key="button.save" /></button>
   			</logic:present>
   			<logic:notPresent name="search">
   				 <button type="button" name="save" id="save" class="topformbutton2"  onclick="return saveMakeModelRecord();"><bean:message key="button.save" /></button>
   			</logic:notPresent>
   		</tr>
   </table>
</fieldset>
</html:form>

<logic:present name="save">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataSave" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("MakeModelMasterAdd").action=contextPath+"/MakeModelMasterBehindAction.do";
		document.getElementById("MakeModelMasterAdd").submit();
	</script>
</logic:present>
<logic:present name="notSave">
	<script type="text/javascript">	
		alert("<bean:message key="msg.NonEmiError" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("MakeModelMasterAdd").action=contextPath+"/addMakeModelMaster.do?method=addNewMakeModel";
		document.getElementById("MakeModelMasterAdd").submit();
	</script>
</logic:present>
<logic:present name="update">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataUpdate" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("MakeModelMasterAdd").action=contextPath+"/MakeModelMasterBehindAction.do";
		document.getElementById("MakeModelMasterAdd").submit();
	</script>
</logic:present>
<logic:present name="exist">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataExist" />");
<!--		var contextPath =document.getElementById('contextPath').value ;-->
<!--		document.getElementById("MakeModelMasterAdd").action=contextPath+"/MakeModelMasterBehindAction.do";-->
<!--		document.getElementById("MakeModelMasterAdd").submit();-->
	</script>
</logic:present>
<logic:present name="existsameforupdate">
	<script type="text/javascript">	
	alert("<bean:message key="lbl.dataExist" />");	

	
	</script>
</logic:present>


</body>	
</html:html>
	
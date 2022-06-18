<%--
      Author Name-      Pawan Kumar Singh
      Date of creation -20/04/2011
      Purpose-          Entry of Collateral Detail
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.connect.CommonFunction"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

	<head>
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->	
		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/assetCollateral.js"></script>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
com.login.roleManager.UserObject userobj=(com.login.roleManager.UserObject)session.getAttribute("userobject");
String branchId = "";
if (userobj != null) {
branchId = userobj.getBranchId();
session.setAttribute("branchId",branchId);
		              }
 
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
<body onclick="parent_disable();"  onload="getEditField();enableAnchor();document.getElementById('VehicleForm').assetsCollateralDesc.focus();calVehicleLTV();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />


<div id=centercolumn>
<div id=revisedcontainer>	
<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">
<html:form action="/collateralVehicleProcessAction" styleId="VehicleForm" method="post">
<html:errors/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<input type="hidden" name="suStateID" id="suStateID" />  
<html:hidden property="assetsIdVehicle" value="${primaryId}" styleClass="text" styleId ="assetsIdVehicle"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="v1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="v2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="v3"/>
 <html:hidden styleClass="text" styleId="checkRefinaceReqInfo" property="checkRefinaceReqInfo" value="${checkRefinaceReqInfo}"/> 
<fieldset>
<legend><bean:message key="lbl.vehicleDetails"/></legend>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>  

   <%-- Start By Prashant --%>
   <tr>
   			  <html:hidden property="assetManufactDesc" styleId="assetManufactDesc"  value="" />
              <html:hidden styleClass="text" styleId="productCat" property="productCat" value="${productCat}"/>
	          <html:hidden styleClass="text" styleId="txtProductCat" property="txtProductCat" value="${productCat}"/> 
          		
          <logic:present name="action" >                
               <logic:present name="ASSET" >              	         
	        <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="getEditField(); clearMakeLov();calVehicleLTV();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" styleClass="text" onchange="getEditField(); clearMakeLov();calVehicleLTV();" value="" >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>  
         
         </logic:present>         
           		<logic:present name="COLLATERAL" >             
	        <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="COLLATERAL">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="getEditField();clearMakeLov();calVehicleLTV();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" styleClass="text" onchange="getEditField();clearMakeLov();calVehicleLTV();" value="" >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>  
         
         </logic:present>
          

	      </logic:present>
	      
	      <logic:notPresent name="action">
	        <%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
	      	  <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="getEditField();clearMakeLov();calVehicleLTV();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="getEditField();clearMakeLov();" styleClass="text" >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>   	
         
         	<%	}else{ %>
         	
	         <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="COLLATERAL">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="getEditField();clearMakeLov();calVehicleLTV();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="getEditField();clearMakeLov();calVehicleLTV();" styleClass="text"  >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>
         
         	<%} %>         	          
	      	 
	      </logic:notPresent>
	        <%-- Start By Prashant --%>
	        	        
	       <td><bean:message key="lbl.vechicleDescription"/><font color="red">*</font></td>
          <td><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /> </td>
    </tr>
          	
          	<tr>         
          <logic:present name="action" >                   					           
         <td><bean:message key="lbl.vehicleMake"/><font color="red">*</font></td>          	
          	<logic:present name="action" >
          
          		<logic:present name="ASSET" >          		          		        		
          				<td nowrap="nowrap">
          					<input type="hidden" value="A" id="assetcollateral"/>
          					<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="clearSupplierLovChild();openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          				</td>
          		</logic:present>          		
          		<logic:present name="COLLATERAL" >
          		<td nowrap="nowrap">
          		<input type="hidden" value="C" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="clearSupplierLovChild();openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          					</td>
          				</logic:present>
          	</logic:present> 		
          	<logic:notPresent name="action">
          	
         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
          		<td nowrap="nowrap">
          			<input type="hidden" value="A" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          		</td>
             	<%	}else{ %>
             	<input type="hidden" value="C" id="assetcollateral"/>
          		<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          		<%	}%>
          	</logic:notPresent> 		       
	      </logic:present>	      
	      <logic:notPresent name="action">
	      <%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>	   
   
<!--	       	 <html:hidden styleClass="text" styleId="productCat" property="productCat" value="${productCat}"/>-->
<!--	         <html:hidden styleClass="text" styleId="txtProductCat" property="txtProductCat" value="${productCat}"/>      -->
         	
         	<%	} %>
         
         <td><bean:message key="lbl.vehicleMake"/><font color="red">*</font></td>          	
          	<logic:present name="action" >
          
          		<logic:present name="ASSET" >          		          		        		
          				<td nowrap="nowrap">
          					<input type="hidden" value="A" id="assetcollateral"/>
          					<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          				</td>
          		</logic:present>          		
          		<logic:present name="COLLATERAL" >
          		<td nowrap="nowrap">
          		<input type="hidden" value="C" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          					</td>
          				</logic:present>
          	</logic:present> 		
          	<logic:notPresent name="action">
          	
         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
          		<td nowrap="nowrap">
          			<input type="hidden" value="A" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="clearSupplierLovChild();openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          		</td>
             	<%	}else{ %>
             	<td nowrap="nowrap">
             	<input type="hidden" value="C" id="assetcollateral"/>
          		<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="clearSupplierLovChild();openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          		</td>
          		<%	}%>
          	</logic:notPresent>
	       	 
	      </logic:notPresent>
     
     
        <td><bean:message key="lbl.vehicleModel"/></td>
			<logic:present name="action" >
          		<logic:present name="ASSET" >
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}" readonly="true"/></td>
          		</logic:present>
          		<logic:present name="COLLATERAL" >
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}"/></td>
          		</logic:present>
        	</logic:present> 
        	<logic:notPresent name="action">
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
					<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}" readonly="true"/></td>
			<%	}else{ %>
				<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}"/></td>
			<%	}%>
			</logic:notPresent>	
		</tr>	
		
		<tr>
		            	
             <td><bean:message key="lbl.manufact"/></td>
          <td nowrap="nowrap">
               
        	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${fetchCollateralDetails[0].assetManufact}" />
        	<html:hidden property="lbxmachineManufact" styleId="lbxmachineManufact" value="${fetchCollateralDetails[0].lbxmachineManufact}"/>
          </td>

        	<td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap">
        	<html:text property="machineSupplier" styleClass="text" styleId="machineSupplier"  readonly="true" value="${fetchCollateralDetails[0].machineSupplier}" tabindex="-1"/>
<!--			<input type="hidden" name="lbxmachineSupplier" id="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}" />-->
			<html:hidden property="lbxmachineSupplier" styleId="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}"/>
			<html:hidden property="empanelledStatus" styleClass="text" styleId="empanelledStatus" value=""/>
			<html:button property="machineButton" styleId="machineButton" onclick="openLOVCommon(412,'VehicleForm','machineSupplier','assetManufact','', 'lbxmachineManufact','Select Vehicle Make LOV','clearDealer','machineSupplier','empanelledStatus','suStateID')" value=" " styleClass="lovbutton"></html:button>
		 <%--<img onClick="openLOVCommon(65,'MachineForm','machineSupplier','','', '','','','supplierDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>    	  
        </td>
        	
		    </tr>
				
       <tr>
         
			<logic:present name="action" >
          		<td><bean:message key="lbl.usesType"/></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
          	</logic:present>
          	<logic:notPresent name="action">
          	<td><bean:message key="lbl.usesType"/></td>
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>				
					<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
             <%}else{ %>	
             <td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
             <%} %>		
			</logic:notPresent>	        	           	
         		
			   <logic:present name="action" >          		
          		<td><bean:message key="lbl.state"/></td>
	          	 <td>	          	 	
	          	 	<html:hidden styleClass="text" styleId="txtStateCode" property="txtStateCode"  value="${fetchCollateralDetails[0].txtStateCode}" />
	          	 	<html:text styleClass="text" styleId="assetState" property="assetState" readonly="true"  value="${fetchCollateralDetails[0].assetState}" />
	          	 </td>          		          		
        	</logic:present> 
        	<logic:notPresent name="action">        				
			<td><bean:message key="lbl.state"/></td>			
	          	 <td>
	          	    <html:hidden styleClass="text" styleId="txtStateCode" property="txtStateCode"  value="${fetchCollateralDetails[0].txtStateCode}" />
	          	 	<html:text styleClass="text" styleId="assetState" property="assetState" readonly="true"  value="${fetchCollateralDetails[0].assetState}" />
	          	 </td>			         				
			</logic:notPresent>	
			</tr>
		
         	<tr>
			
			<logic:present name="action" >
	                   <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
	                   <td><html:text styleClass="text" styleId="collateralSecurityMarginDF" property="collateralSecurityMarginDF" value="${fetchCollateralDetails[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	         </logic:present>
	         <logic:notPresent name="action">
	         <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>		     	         	        
		         			<td><html:text styleClass="text" styleId="collateralSecurityMarginDF"property="collateralSecurityMarginDF" value="${fetchCollateralDetails[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>                
         	</logic:notPresent>
         	
			<td><bean:message key="lbl.vehicleSecurityMargin"/></td>
			<logic:present name="action" >          	      	
	                     <td><html:text styleClass="text" styleId="collateralSecurityMargin"property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	        </logic:present>
	        <logic:notPresent name="action">	          	
		         	<td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>		         			         
         	</logic:notPresent>
         	
          	          
         </tr>
         
         <tr>
         
         <logic:present name="action" >
       			<td><bean:message key="lbl.loanAmount"/></td>
       			<td><html:text styleClass="text" styleId="loanAmount" property="loanAmount"  value="${fetchCollateralDetails[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
       		</logic:present>
       		<logic:notPresent name="action">
       		<td><bean:message key="lbl.loanAmount"/></td>       			    				
					<td nowrap="nowrap"><html:text styleClass="text" styleId="loanAmount" property="loanAmount" value="${fetchCollateralDetails[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>				
			</logic:notPresent>		
         
          <td><bean:message key="lbl.vehicleCost"/></td>
          <td><html:text styleClass="text" styleId="vehicleCost" property="vehicleCost"  value="${fetchCollateralDetails[0].vehicleCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         
         </tr>
         
         <tr>
          <td><bean:message key="lbl.vehiclediscount"/></td>
          <td><html:text styleClass="text" styleId="vehicleDiscount" property="vehicleDiscount" value="${fetchCollateralDetails[0].vehicleDiscount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          <td><bean:message key="lbl.vehicleValue"/></TD>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue"  value="${fetchCollateralDetails[0].assetsCollateralValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);discountVehicle();" onchange="calculateLTV(this.value);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </tr>                 
<!--sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss	-->
		  <tr>
          <td><bean:message key="lbl.gridValue"/></td>
          <td><html:text styleClass="text" styleId="gridValue" property="gridValue" value="${fetchCollateralDetails[0].gridValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          <td><bean:message key="lbl.valuationCost"/></td>
          <td ><html:text styleClass="text" styleId="valuationCost" property="valuationCost"  value="${fetchCollateralDetails[0].valuationCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </tr> 
<!--sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss					-->
<tr>

      			<logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="${fetchCollateralDetails[0].securityTypes}">
			<!-- asesh space start-->
				<logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			<!-- asesh space end-->
			 </html:select>
		      </td>
		      </logic:equal>
		       <logic:equal name="subList" property="colltype2" value="COLLATERAL">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="${fetchCollateralDetails[0].securityTypes}">
			     <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>
		      <logic:notPresent name="fetchCollateralDetails">
		  	 <logic:present name="assetCollateralTypeAsset" >
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="" >
			     <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:present>
		      </logic:notPresent>		    
		  	
		  	<logic:present name="ASSET">		  	
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" /></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:present>	  	
		  	
		  	
      			
		    </tr>
		    
		    <tr>
		    
		    <td><bean:message key="lbl.chasisNumber"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleChesisNo" property="vehicleChesisNo" maxlength="25" value="${fetchCollateralDetails[0].vehicleChesisNo}" /></td>
		   
		     <td><bean:message key="lbl.engineNumber"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="engineNumber" property="engineNumber" maxlength="50" value="${fetchCollateralDetails[0].engineNumber}" /></td>
		   
		     </tr>       
   <%-- End By Prashant --%>
		    
		  <tr>
		  <td><bean:message key="lbl.invoiceNumber"/></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="invoiceNumber" property="invoiceNumber" maxlength="20"  value="${fetchCollateralDetails[0].invoiceNumber}" /></td>
		  <td><bean:message key="lbl.invoiceDate"/></td>
		  <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleInvoiceDate" property="vehicleInvoiceDate" value="${fetchCollateralDetails[0].vehicleInvoiceDate}" onchange="return checkDate('vehicleInvoiceDate');" /></td>
		  </tr>
		  <tr>
		  <td><bean:message key="lbl.rcReceived"/></td>
		  <td>
		  <html:select property="rcReceived" styleId="rcReceived" styleClass="text" value="${fetchCollateralDetails[0].rcReceived}">
	              <html:option  value="">---Select---</html:option>
	              <html:option  value="Y">YES</html:option>  
		          <html:option  value="N">NO</html:option>
	      </html:select>
	      </td>
		  <td><bean:message key="lbl.rcReceivedDate"/></td>
		  <td nowrap="nowrap"><html:text styleClass="text" styleId="rcReceivedDate" property="rcReceivedDate" value="${fetchCollateralDetails[0].rcReceivedDate}" onchange="return checkDate('rcReceivedDate');" /></td>
		  </tr>
		  <tr>
		    <td><bean:message key="lbl.registrationNumber" /></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleRegNo" property="vehicleRegNo" maxlength="25" value="${fetchCollateralDetails[0].vehicleRegNo}"  /></td>
		    <td><bean:message key="lbl.registrationDate"/></td>
            <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleRegDate" property="vehicleRegDate" value="${fetchCollateralDetails[0].vehicleRegDate}" onchange="return checkDate('vehicleRegDate');" /></td>
		  </tr> 
		  <tr>
		    <td><bean:message key="lbl.yearofManufacture"/>(YYYY)<font color="red">*</font></td>
            <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleYearOfManufact" maxlength="4" onblur="checkFormateYear(this.value,this.id)" property="vehicleYearOfManufact" value="${fetchCollateralDetails[0].vehicleYearOfManufact}" /></td>
		    <td><bean:message key="lbl.vehicleOwner"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleOwner" property="vehicleOwner" maxlength="50" value="${fetchCollateralDetails[0].vehicleOwner}" /></td>
		  </tr>
		
		  <tr>
		    <td><bean:message key="lbl.Insurer"/></td>
            <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleInsurer" property="vehicleInsurer" maxlength="50"  value="${fetchCollateralDetails[0].vehicleInsurer}" /></td>
		    <td><bean:message key="lbl.insuredExpDate"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleInsureDate" property="vehicleInsureDate" value="${fetchCollateralDetails[0].vehicleInsureDate}" onchange="return checkDate('vehicleInsureDate');" /></td>
		  </tr> 
		  <tr>
          	<td><bean:message key="lbl.idv"/></td>
          	<td><html:text styleClass="text" styleId="idv" property="idv" value="${fetchCollateralDetails[0].idv}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          </tr> 
           <tr>
		    <td><bean:message key="lbl.dealerExecutiveName" /></td>
		    <td>
		        <html:text styleId="dealerExecutive" property="dealerExecutive" readonly="true" styleClass="text" value="${fetchCollateralDetails[0].dealerExecutive}" />
		    	<html:button property="executiveButton" styleId="executiveButton" onclick="closeLovCallonLov('dealerExecutive');openLOVCommon(2055,'VehicleForm','dealerExecutive','machineSupplier','lbxDealerExecutive', 'lbxmachineSupplier','Select Supplier LOV','','lbxDealerExecutive')" value=" " styleClass="lovbutton"></html:button>  								    
                <html:hidden  property="lbxDealerExecutive" styleId="lbxDealerExecutive" value="${fetchCollateralDetails[0].lbxDealerExecutive}" />
		    </td>
		    
		    <td><bean:message key="lbl.dealerManagerName" /></td>
		    <td> 
		        <html:text styleId="dealerManager" property="dealerManager" readonly="true" styleClass="text" value="${fetchCollateralDetails[0].dealerManager}" />
		    	<html:button property="managerButton" styleId="managerButton" onclick="closeLovCallonLov('dealerManager');openLOVCommon(2056,'VehicleForm','dealerManager','machineSupplier','lbxDealerManager', 'lbxmachineSupplier','Select Supplier LOV','','lbxDealerManager')" value=" " styleClass="lovbutton"></html:button>  								    
                <html:hidden  property="lbxDealerManager" styleId="lbxDealerManager" value="${fetchCollateralDetails[0].lbxDealerManager}" />
		       
		       </td> 
		    </tr>
		  <tr>
 		  <td><button type="button" name="Submit20" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveVehicleDetails();"><bean:message key="button.save" /></button>  </td>
		  </tr>	  
		  <html:hidden property="dealId" value="${dealId}" styleClass="text" styleId ="dealId"/>  
          
	</table>
	</td>
	</tr>
</table>
	</fieldset>		
</html:form>

</logic:present>
<%--          			view Deal 																 --%>


 <logic:present name="viewDeal">
 
<html:form action="/collateralVehicleProcessAction" styleId="VehicleForm" method="post">
<html:hidden property="assetsIdVehicle" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdVehicle"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="v1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="v2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="v3"/>
<fieldset>
<legend><bean:message key="lbl.vehicleDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>        
    
   <%-- Start By Prashant --%>
   <tr>

          <logic:present name="action" >
                   
          		<logic:present name="ASSET" >
               
	           <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" onchange="clearMakeLov();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}" >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" styleClass="text" onchange="clearMakeLov();"  >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>  
         
         </logic:present>
         
           		<logic:present name="COLLATERAL" >
               
	          <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="COLLATERAL">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" onchange="clearManufDealerLov();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" styleClass="text" onchange="clearManufDealerLov();" >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>  
         
         </logic:present>
          

	      </logic:present>
	      
	      <logic:notPresent name="action">
	         <%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
	      	  <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" onchange="clearMakeLov();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" onchange="clearMakeLov();" styleClass="text"  >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>   	
         
         	<%	}else{ %>
         	
	         <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" onchange="clearManufDealerLov();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" onchange="clearManufDealerLov();" styleClass="text"  >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>
         
         	<%} %>         	          
	      	 
	      </logic:notPresent>
	        <%-- Start By Prashant --%>
	        	        
	       <td><bean:message key="lbl.vechicleDescription"/><font color="red">*</font></td>
          <td><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" readonly="true" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /> </td>
          </tr>
          	
          	<tr>         
          <logic:present name="action" >
                   					 
          
         <td><bean:message key="lbl.vehicleMake"/><font color="red">*</font></td>          	
          	<logic:present name="action" >
          
          		<logic:present name="ASSET" >
          		
          		        		
          				<td nowrap="nowrap">
          					<input type="hidden" value="A" id="assetcollateral"/>
          					<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
<!--          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="clearSupplierLovChild();openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>-->
          				</td>
          		</logic:present>          		
          		<logic:present name="COLLATERAL" >
          		<input type="hidden" value="C" id="assetcollateral"/>
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" readonly="true" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" /></td>
          		</logic:present>
          	</logic:present> 		
          	<logic:notPresent name="action">
          	
         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
          		<td nowrap="nowrap">
          			<input type="hidden" value="A" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
<!--          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>-->
          		</td>
             	<%	}else{ %>
             	<input type="hidden" value="C" id="assetcollateral"/>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" /></td>	
          		<%	}%>
          	</logic:notPresent> 	
	       
	      </logic:present>
	      
	      <logic:notPresent name="action">
	      <%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>	   
   
<!--	       	 <html:hidden styleClass="text" styleId="productCat" property="productCat" value="${productCat}"/>-->
<!--	         <html:hidden styleClass="text" styleId="txtProductCat" property="txtProductCat" value="${productCat}"/>      -->
         
         	<%	} %>
         
         <td><bean:message key="lbl.vehicleMake"/><font color="red">*</font></td>          	
          	<logic:present name="action" >
          
          		<logic:present name="ASSET" >
          		
          		        		
          				<td nowrap="nowrap">
          					<input type="hidden" value="A" id="assetcollateral"/>
          					<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
<!--          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>-->
          				</td>
          		</logic:present>          		
          		<logic:present name="COLLATERAL" >
          		<input type="hidden" value="C" id="assetcollateral"/>
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" readonly="true" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" /></td>
          		</logic:present>
          	</logic:present> 		
          	<logic:notPresent name="action">
          	
         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
          		<td nowrap="nowrap">
          			<input type="hidden" value="A" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
<!--          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="clearSupplierLovChild();openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>-->
          		</td>
             	<%	}else{ %>
             	<input type="hidden" value="C" id="assetcollateral"/>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" readonly="true" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" /></td>	
          		<%	}%>
          	</logic:notPresent>
	       	 
	      </logic:notPresent>
     
     
        <td><bean:message key="lbl.vehicleModel"/></td>
			<logic:present name="action" >
          		<logic:present name="ASSET" >
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}" readonly="true"/></td>
          		</logic:present>
          		<logic:present name="COLLATERAL" >
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" readonly="true"  value="${fetchCollateralDetails[0].vehicleModel}"/></td>
          		</logic:present>
        	</logic:present> 
        	<logic:notPresent name="action">
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
					<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}" readonly="true"/></td>
			<%	}else{ %>
				<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}" readonly="true"/></td>
			<%	}%>
			</logic:notPresent>	
		</tr>	
		
		<tr>
		    <logic:present name="COLLATERAL">
		    <td><bean:message key="lbl.manufact"/></td>
            <td nowrap="nowrap">
           	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${fetchCollateralDetails[0].assetManufact}" tabindex="-1"/>
			<input type="hidden" name="lbxmachineManufact" id="lbxmachineManufact" value="${fetchCollateralDetails[0].lbxmachineManufact}" />
			<html:hidden property="lbxmachineManufact" styleId="lbxmachineManufact" value="${fetchCollateralDetails[0].lbxmachineManufact}"/>
<!--			<html:button property="assetbutton" style="assetbutton" onclick="clearSupplierLovChild();openLOVCommon(66,'MachineForm','assetManufact','','', '','','','assetManufact')" value=" " styleClass="lovbutton"></html:button>-->
		   <%--<img onClick="openLOVCommon(66,'MachineForm','assetManufact','','', '','','','assetManufactDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>  	  
        	
            </td>
                
          <td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap">
          	<html:text property="machineSupplier" styleClass="text" styleId="machineSupplier"  readonly="true" value="${fetchCollateralDetails[0].machineSupplier}" tabindex="-1"/>
<!--			<input type="hidden" name="lbxmachineSupplier" id="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}" />-->
			<html:hidden property="lbxmachineSupplier" styleId="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}"/>
<!--			<html:button property="machineButton" styleId="machineButton" onclick="openLOVCommon(412,'VehicleForm','machineSupplier','assetManufact','', 'lbxmachineManufact','Select Manufacturer LOV','','machineSupplier')" value=" " styleClass="lovbutton"></html:button>-->
		 <%--<img onClick="openLOVCommon(65,'MachineForm','machineSupplier','','', '','','','supplierDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>    	  
        	</td>
        	</logic:present>
        	
          <logic:present name="ASSET">
             <td><bean:message key="lbl.manufact"/></td>
          <td nowrap="nowrap">
               
        	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${fetchCollateralDetails[0].assetManufact}" />
        	<html:hidden property="lbxmachineManufact" styleId="lbxmachineManufact" value="${fetchCollateralDetails[0].lbxmachineManufact}"/>
          </td>

        	<td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap">
        	<html:text property="machineSupplier" styleClass="text" styleId="machineSupplier"  readonly="true" value="${fetchCollateralDetails[0].machineSupplier}" tabindex="-1"/>
<!--			<input type="hidden" name="lbxmachineSupplier" id="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}" />-->
			<html:hidden property="lbxmachineSupplier" styleId="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}"/>
<!--			<html:button property="machineButton" styleId="machineButton" onclick="openLOVCommon(412,'VehicleForm','machineSupplier','assetManufact','', 'lbxmachineManufact','Select Vehicle Make LOV','','machineSupplier')" value=" " styleClass="lovbutton"></html:button>-->		     	  
        </td>
        	</logic:present>
        	
		    </tr>
				
       <tr>
         
			<logic:present name="action" >
          	<logic:present name="ASSET" >
          		<td><bean:message key="lbl.usesType"/></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
          	</logic:present>
          	<logic:present name="COLLATERAL" >
          		<td><bean:message key="lbl.usesType"/></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
          	</logic:present>
          	</logic:present>
          	<logic:notPresent name="action">
          	<td><bean:message key="lbl.usesType"/></td>
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>				
					<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
             <%	} else{%>	           
					<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
             
             <%} %>			
			</logic:notPresent>	        	           	
         					        	
          		<td><bean:message key="lbl.state"/></td>
	          	 <td>	          	 	
	          	 	<html:hidden styleClass="text" styleId="txtStateCode" property="txtStateCode"  value="${fetchCollateralDetails[0].txtStateCode}" />
	          	 	<html:text styleClass="text" styleId="assetState" property="assetState" readonly="true"  value="${fetchCollateralDetails[0].assetState}" />
	          	 </td>          		          		          		

			</tr>
		
         	<tr>
			
			<logic:present name="action" >
	        <logic:present name="ASSET" >
	                   <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
	                   <td><html:text styleClass="text" styleId="collateralSecurityMarginDF"  property="collateralSecurityMarginDF" value="${fetchCollateralDetails[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	         </logic:present>
	         <logic:present name="COLLATERAL" >
	                   <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
	                   <td><html:text styleClass="text" styleId="collateralSecurityMarginDF" property="collateralSecurityMarginDF" value="${fetchCollateralDetails[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	         </logic:present>
	         </logic:present>
	         <logic:notPresent name="action">
	         <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
		         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>		         	        
		         			<td><html:text styleClass="text" styleId="collateralSecurityMarginDF"  property="collateralSecurityMarginDF" value="${fetchCollateralDetails[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	<%	}else{%>		         
		         			<td><html:text styleClass="text" styleId="collateralSecurityMarginDF"  property="collateralSecurityMarginDF" value="${fetchCollateralDetails[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	<%} %>
         	</logic:notPresent>
         	
			<td><bean:message key="lbl.vehicleSecurityMargin"/></td>
			<logic:present name="action" >
          	      	<logic:present name="ASSET" >
	                     <td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	          	   </logic:present>
	          	   <logic:present name="COLLATERAL" >
	          	   			<td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	          	   </logic:present>
	        </logic:present>
	        <logic:notPresent name="action">
	          	
		         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
		         	<td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	
		         	<%	}else{ %>
		         			<td><html:text styleClass="text" styleId="collateralSecurityMargin"  readonly="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	
		         	<%	}%>
         	</logic:notPresent>
         	
          	          
         </tr>
         
         <tr>
         
         <logic:present name="action" >
          	<logic:present name="ASSET" >	
       			<td><bean:message key="lbl.loanAmount"/></td>
       			<td><html:text styleClass="text" styleId="loanAmount" property="loanAmount" readonly="true" value="${fetchCollateralDetails[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
       		</logic:present>
       			<logic:present name="COLLATERAL" >	
       			<td><bean:message key="lbl.loanAmount"/></td>
       			<td><html:text styleClass="text" styleId="loanAmount" property="loanAmount" readonly="true" value="${fetchCollateralDetails[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
       		</logic:present>
       		</logic:present>
       		<logic:notPresent name="action">
       		<td><bean:message key="lbl.loanAmount"/></td>
       			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>       				
					<td nowrap="nowrap"><html:text styleClass="text" styleId="loanAmount" readonly="true" property="loanAmount" value="${fetchCollateralDetails[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<%	} else{%>				
					<td nowrap="nowrap"><html:text styleClass="text" styleId="loanAmount" readonly="true" property="loanAmount" value="${fetchCollateralDetails[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<%} %>
			</logic:notPresent>		
         
          <td><bean:message key="lbl.vehicleCost"/></td>
          <td><html:text styleClass="text" styleId="vehicleCost" property="vehicleCost" readonly="true" value="${fetchCollateralDetails[0].vehicleCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         
         </tr>
         
         <tr>
          <td><bean:message key="lbl.vehiclediscount"/></td>
          <td><html:text styleClass="text" styleId="vehicleDiscount" property="vehicleDiscount" readonly="true" value="${fetchCollateralDetails[0].vehicleDiscount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          <td><bean:message key="lbl.vehicleValue"/></TD>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" readonly="true" value="${fetchCollateralDetails[0].assetsCollateralValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);discountVehicle();" onchange="calculateLTV(this.value);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </tr>                 
		<!--sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss	-->
		  <tr>
          <td><bean:message key="lbl.gridValue"/></td>
          <td><html:text styleClass="text" styleId="gridValue" property="gridValue" readonly="true"  value="${fetchCollateralDetails[0].gridValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          <td><bean:message key="lbl.valuationCost"/></td>
          <td ><html:text styleClass="text" styleId="valuationCost" property="valuationCost" readonly="true"  value="${fetchCollateralDetails[0].valuationCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </tr> 
<!--sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss					-->	
			<tr>

      			<logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].securityTypes}">
			     <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		       <logic:equal name="subList" property="colltype2" value="COLLATERAL">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].securityTypes}">
			     <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>
		      <logic:notPresent name="fetchCollateralDetails">
		  	 <logic:present name="assetCollateralTypeAsset" >
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" disabled="true" value="" >
			     <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:present>
		      </logic:notPresent>
		      
		  	<logic:equal name="actype" value="ASSET">
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" disabled="disabled" checked="checked"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" disabled="disabled" /></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	
		  	
      			
		</tr>
		    
		    <tr>
		    
		    <td><bean:message key="lbl.chasisNumber"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleChesisNo" readonly="true" property="vehicleChesisNo" maxlength="25" value="${fetchCollateralDetails[0].vehicleChesisNo}" /></td>
		   
		     <td><bean:message key="lbl.engineNumber"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="engineNumber" readonly="true" property="engineNumber" maxlength="50" value="${fetchCollateralDetails[0].engineNumber}" /></td>
		   
		     </tr>       
   <%-- End By Prashant --%>
		    
		 <tr>
		  <td><bean:message key="lbl.invoiceNumber"/></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="invoiceNumber" property="invoiceNumber" maxlength="20" readonly="true" value="${fetchCollateralDetails[0].invoiceNumber}" /></td>
		  <td><bean:message key="lbl.invoiceDate"/></td>
		  <td nowrap="nowrap"><html:text styleClass="text" property="vehicleInvoiceDate" readonly="true" value="${fetchCollateralDetails[0].vehicleInvoiceDate}" onchange="return checkDate('vehicleInvoiceDate');" /></td>
		  </tr>
		  <tr>
		  <td><bean:message key="lbl.rcReceived"/></td>
		  <td>
		  <html:select property="rcReceived"  styleClass="text" disabled="true" value="${fetchCollateralDetails[0].rcReceived}">
	              <html:option  value="">---Select---</html:option>
	              <html:option  value="Y">YES</html:option>  
		          <html:option  value="N">NO</html:option>
	      </html:select>
	      </td>
		  <td><bean:message key="lbl.rcReceivedDate"/></td>
		  <td nowrap="nowrap"><html:text styleClass="text" property="rcReceivedDate" readonly="true" value="${fetchCollateralDetails[0].rcReceivedDate}" onchange="return checkDate('rcReceivedDate');" /></td>
		 </tr>
		 <tr>
		    <td><bean:message key="lbl.registrationNumber" /></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleRegNo" readonly="true" property="vehicleRegNo" maxlength="25" value="${fetchCollateralDetails[0].vehicleRegNo}"  /></td>
		    <td><bean:message key="lbl.registrationDate"/></td>
            <td nowrap="nowrap"><html:text styleClass="text" readonly="true" property="vehicleRegDate" value="${fetchCollateralDetails[0].vehicleRegDate}" onchange="return checkDate('vehicleRegDate');" /></td>
		  </tr> 
		  	<tr>
		   		<td><bean:message key="lbl.yearofManufacture"/>(YYYY)<font color="red">*</font></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleYearOfManufact" readonly="true" maxlength="4" onblur="checkFormateYear(this.value,this.id)" property="vehicleYearOfManufact" value="${fetchCollateralDetails[0].vehicleYearOfManufact}" /></td>
		    	<td><bean:message key="lbl.vehicleOwner"/></td>
		    	<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleOwner" readonly="true" property="vehicleOwner" maxlength="50" value="${fetchCollateralDetails[0].vehicleOwner}" /></td>
		   </tr>		
		   <tr>
		  		<td><bean:message key="lbl.Insurer"/></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleInsurer" readonly="true" property="vehicleInsurer" maxlength="50"  value="${fetchCollateralDetails[0].vehicleInsurer}" /></td>
		        <td><bean:message key="lbl.insuredExpDate"/></td>
		    	<td nowrap="nowrap"><html:text styleClass="text" readonly="true" property="vehicleInsureDate" value="${fetchCollateralDetails[0].vehicleInsureDate}" onchange="return checkDate('vehicleInsureDate');" /></td>
		   </tr> 
		   <tr>
		  		<td><bean:message key="lbl.idv"/></td>
          		<td><html:text styleClass="text" styleId="idv" property="idv"  readonly="true" value="${fetchCollateralDetails[0].idv}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
           </tr> 
            <tr>
		    <td><bean:message key="lbl.dealerExecutiveName" /></td>
		    <td>
		        <html:text styleId="dealerExecutive" property="dealerExecutive" readonly="true" styleClass="text" value="${fetchCollateralDetails[0].dealerExecutive}" />
		    	
		    </td>
		    
		    <td><bean:message key="lbl.dealerManagerName" /></td>
		    <td> 
		        <html:text styleId="dealerManager" property="dealerManager" readonly="true" styleClass="text" value="${fetchCollateralDetails[0].dealerManager}" />
		    	
		       </td> 
		    </tr>	
	     <html:hidden property="dealId" value="${dealId}" styleClass="text" styleId ="dealId"/>    
          
	</table>
	</TD>
	</TR>
</table>
	</fieldset>		
</html:form>
</logic:present>
</div>
	<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewVehicleInCM">
<html:form action="/collateralVehicleProcessAction" styleId="VehicleForm" method="post">
<fieldset>
<legend><bean:message key="lbl.vehicleDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>        
   <tr>

          <logic:notPresent name="action" >
               <logic:present name="printLov">
   
          		<logic:present name="ASSET" >               
	          <logic:present name="viewVehicleInCM">
			 <logic:iterate name="viewVehicleInCM" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" onchange="clearMakeLov();" styleClass="text" value="${viewVehicleInCM[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>		          
         </logic:present>
         
           		<logic:present name="COLLATERAL" >               
	          <logic:present name="viewVehicleInCM">
			 <logic:iterate name="viewVehicleInCM" id="subList">
			 <logic:equal name="subList" property="colltype2" value="COLLATERAL">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" disabled="true" onchange="clearManufDealerLov();" styleClass="text" value="${viewVehicleInCM[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present> 
         </logic:present>
         
         </logic:present>          
	      </logic:notPresent>	      
	        	        
	       <td><bean:message key="lbl.vechicleDescription"/><font color="red">*</font></td>
          <td><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" readonly="true" maxlength="100" value="${viewVehicleInCM[0].assetsCollateralDesc}" /> </td>
          </tr>
          	
          	<tr>         
          <logic:present name="action" >
                   					 
          
         <td><bean:message key="lbl.vehicleMake"/><font color="red">*</font></td>          	
          	<logic:present name="action" >
          
          		<logic:present name="ASSET" >
          		
          		        		
          				<td nowrap="nowrap">
          					<input type="hidden" value="A" id="assetcollateral"/>
          					<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${viewVehicleInCM[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${viewVehicleInCM[0].make_model_id}"/>
<!--          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="clearSupplierLovChild();openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>-->
          				</td>
          		</logic:present>          		
          		<logic:present name="COLLATERAL" >
          		<input type="hidden" value="C" id="assetcollateral"/>
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" readonly="true" property="vehicleMake" maxlength="50" value="${viewVehicleInCM[0].vehicleMake}" /></td>
          		</logic:present>
          	</logic:present> 		
          	<logic:notPresent name="action">
          	
         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
          		<td nowrap="nowrap">
          			<input type="hidden" value="A" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${viewVehicleInCM[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${viewVehicleInCM[0].make_model_id}"/>
<!--          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>-->
          		</td>
             	<%	}else{ %>
             	<input type="hidden" value="C" id="assetcollateral"/>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" readonly="true" property="vehicleMake" maxlength="50" value="${viewVehicleInCM[0].vehicleMake}" /></td>	
          		<%	}%>
          	</logic:notPresent> 	
	       
	      </logic:present>
	      
	      <logic:notPresent name="action">
	      <%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>	   
   
<!--	       	 <html:hidden styleClass="text" styleId="productCat" property="productCat" value="${productCat}"/>-->
<!--	         <html:hidden styleClass="text" styleId="txtProductCat" property="txtProductCat" value="${productCat}"/>      -->
         
         	<%	} %>
         
         <td><bean:message key="lbl.vehicleMake"/><font color="red">*</font></td>          	
          	<logic:present name="action" >
          
          		<logic:present name="ASSET" >
          		
          		        		
          				<td nowrap="nowrap">
          					<input type="hidden" value="A" id="assetcollateral"/>
          					<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${viewVehicleInCM[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${viewVehicleInCM[0].make_model_id}"/>
<!--          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>-->
          				</td>
          		</logic:present>          		
          		<logic:present name="COLLATERAL" >
          		<input type="hidden" value="C" id="assetcollateral"/>
          			<td nowrap="nowrap"><html:text styleClass="text" readonly="true" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${viewVehicleInCM[0].vehicleMake}" /></td>
          		</logic:present>
          	</logic:present> 		
          	<logic:notPresent name="action">
          	
         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
          		<td nowrap="nowrap">
          			<input type="hidden" value="A" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${viewVehicleInCM[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${viewVehicleInCM[0].make_model_id}"/>
<!--          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="clearSupplierLovChild();openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|productCat','make_model_id', 'txtProductCat|assetNature','Please select Asset Nature first|Please capture loan details first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>-->
          		</td>
             	<%	}else{ %>
             	<input type="hidden" value="C" id="assetcollateral"/>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" readonly="true" property="vehicleMake" maxlength="50" value="${viewVehicleInCM[0].vehicleMake}" /></td>	
          		<%	}%>
          	</logic:notPresent>
	       	 
	      </logic:notPresent>
     
     
        <td><bean:message key="lbl.vehicleModel"/></td>
			<logic:present name="action" >
          		<logic:present name="ASSET" >
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${viewVehicleInCM[0].vehicleModel}" readonly="true"/></td>
          		</logic:present>
          		<logic:present name="COLLATERAL" >
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${viewVehicleInCM[0].vehicleModel}" readonly="true"/></td>
          		</logic:present>
        	</logic:present> 
        	<logic:notPresent name="action">
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
					<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${viewVehicleInCM[0].vehicleModel}" readonly="true"/></td>
			<%	}else{ %>
				<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${viewVehicleInCM[0].vehicleModel}" readonly="true"/></td>
			<%	}%>
			</logic:notPresent>	
		</tr>	
		
		<tr>
		    <logic:present name="COLLATERAL">
		    <td><bean:message key="lbl.manufact"/></td>
            <td nowrap="nowrap">
           	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${viewVehicleInCM[0].assetManufact}" tabindex="-1"/>
			<input type="hidden" name="lbxmachineManufact" id="lbxmachineManufact" value="${viewVehicleInCM[0].lbxmachineManufact}" />
			<html:hidden property="lbxmachineManufact" styleId="lbxmachineManufact" value="${viewVehicleInCM[0].lbxmachineManufact}"/>
            </td>
                
          <td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap">
          	<html:text property="machineSupplier" styleClass="text" styleId="machineSupplier"  readonly="true" value="${viewVehicleInCM[0].supplierDesc}" tabindex="-1"/>
			<html:hidden property="lbxmachineSupplier" styleId="lbxmachineSupplier" value="${viewVehicleInCM[0].lbxmachineSupplier}"/>   	  
        	</td>
        	</logic:present>
        	
          <logic:present name="ASSET">
             <td><bean:message key="lbl.manufact"/></td>
          <td nowrap="nowrap">
               
        	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${viewVehicleInCM[0].assetManufact}" />
        	<html:hidden property="lbxmachineManufact" styleId="lbxmachineManufact" value="${viewVehicleInCM[0].lbxmachineManufact}"/>
          </td>

        	<td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap">
        	<html:text property="machineSupplier" styleClass="text" styleId="machineSupplier"  readonly="true" value="${viewVehicleInCM[0].supplierDesc}" tabindex="-1"/>
			<html:hidden property="lbxmachineSupplier" styleId="lbxmachineSupplier" value="${viewVehicleInCM[0].lbxmachineSupplier}"/>  	  
        </td>
        	</logic:present>
        	
		    </tr>
				
       <tr>
         
			<logic:present name="action" >
          	<logic:present name="ASSET" >
          		<td><bean:message key="lbl.usesType"/></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${viewVehicleInCM[0].usageType}" readonly="true"/></td>
          	</logic:present>
          	<logic:present name="COLLATERAL" >
          		<td><bean:message key="lbl.usesType"/></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${viewVehicleInCM[0].usageType}" readonly="true"/></td>
          	</logic:present>
          	</logic:present>
          	<logic:notPresent name="action">
          	<td><bean:message key="lbl.usesType"/></td>
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>				
					<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${viewVehicleInCM[0].usageType}" readonly="true"/></td>
             <%	} else{%>	           
					<td nowrap="nowrap"><html:text styleClass="text" readonly="true" styleId="usageType" property="usageType" maxlength="50" value="${viewVehicleInCM[0].usageType}" /></td>
             
             <%} %>			
			</logic:notPresent>	        	           	
         					             		
          		<td><bean:message key="lbl.state"/></td>
	          	 <td>	          	 	
	          	 	<html:hidden styleClass="text" styleId="txtStateCode" property="txtStateCode"  value="${viewVehicleInCM[0].txtStateCode}" />
	          	 	<html:text styleClass="text" styleId="assetState" property="assetState" readonly="true"  value="${viewVehicleInCM[0].assetState}" />
	          	 </td>          		          	
          		        	
			</tr>
		
         	<tr>
			
			<logic:present name="action" >
	        <logic:present name="ASSET" >
	                   <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
	                   <td><html:text styleClass="text" styleId="collateralSecurityMarginDF" property="collateralSecurityMarginDF" value="${viewVehicleInCM[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	         </logic:present>
	         <logic:present name="COLLATERAL" >
	                   <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
	                   <td><html:text styleClass="text" styleId="collateralSecurityMarginDF"  property="collateralSecurityMarginDF" value="${viewVehicleInCM[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	         </logic:present>
	         </logic:present>
	         <logic:notPresent name="action">
	         <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
		         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>		         	        
		         			<td><html:text styleClass="text" styleId="collateralSecurityMarginDF" property="collateralSecurityMarginDF" value="${viewVehicleInCM[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	<%	}else{%>		         
		         			<td><html:text styleClass="text" styleId="collateralSecurityMarginDF"  property="collateralSecurityMarginDF" value="${viewVehicleInCM[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	<%} %>
         	</logic:notPresent>
         	
			<td><bean:message key="lbl.vehicleSecurityMargin"/></td>
			<logic:present name="action" >
          	      	<logic:present name="ASSET" >
	                     <td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${viewVehicleInCM[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	          	   </logic:present>
	          	   <logic:present name="COLLATERAL" >
	          	   			<td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${viewVehicleInCM[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	          	   </logic:present>
	        </logic:present>
	        <logic:notPresent name="action">
	          	
		         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
		         	<td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${viewVehicleInCM[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	
		         	<%	}else{ %>
		         			<td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${viewVehicleInCM[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	
		         	<%	}%>
         	</logic:notPresent>
         	
          	          
         </tr>
         
         <tr>
         
         <logic:present name="action" >
          	<logic:present name="ASSET" >	
       			<td><bean:message key="lbl.loanAmount"/></td>
       			<td><html:text styleClass="text" styleId="loanAmount" property="loanAmount" readonly="true" value="${viewVehicleInCM[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
       		</logic:present>
       			<logic:present name="COLLATERAL" >	
       			<td><bean:message key="lbl.loanAmount"/></td>
       			<td><html:text styleClass="text" styleId="loanAmount" property="loanAmount" readonly="true" value="${viewVehicleInCM[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
       		</logic:present>
       		</logic:present>
       		<logic:notPresent name="action">
       		<td><bean:message key="lbl.loanAmount"/></td>
       			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>       				
					<td nowrap="nowrap"><html:text styleClass="text" styleId="loanAmount" readonly="true" property="loanAmount" value="${viewVehicleInCM[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<%	} else{%>				
					<td nowrap="nowrap"><html:text styleClass="text" styleId="loanAmount" readonly="true" property="loanAmount" value="${viewVehicleInCM[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<%} %>
			</logic:notPresent>		
         
          <td><bean:message key="lbl.vehicleCost"/></td>
          <td><html:text styleClass="text" styleId="vehicleCost" property="vehicleCost" readonly="true"  value="${viewVehicleInCM[0].vehicleCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         
         </tr>
         
         <tr>
          <td><bean:message key="lbl.vehiclediscount"/></td>
          <td><html:text styleClass="text" styleId="vehicleDiscount" property="vehicleDiscount" readonly="true" value="${viewVehicleInCM[0].vehicleDiscount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          <td><bean:message key="lbl.vehicleValue"/></TD>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" readonly="true" property="assetsCollateralValue"  value="${viewVehicleInCM[0].assetsCollateralValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);discountVehicle();" onchange="calculateLTV(this.value);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </tr>                 
		<!--sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss	-->
		  <tr>
          <td><bean:message key="lbl.gridValue"/></td>
          <td><html:text styleClass="text" styleId="gridValue" property="gridValue"  readonly="true"  value="${viewVehicleInCM[0].gridValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          <td><bean:message key="lbl.valuationCost"/></td>
          <td ><html:text styleClass="text" styleId="valuationCost" property="valuationCost"  readonly="true"  value="${viewVehicleInCM[0].valuationCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </tr> 
<!--sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss					-->
		<tr>

      			<logic:present name="viewVehicleInCM">
			 <logic:iterate name="viewVehicleInCM" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" disabled="true" styleClass="text" value="${viewVehicleInCM[0].securityTypes}">
			     <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		       <logic:equal name="subList" property="colltype2" value="COLLATERAL">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" disabled="true" styleClass="text" value="${viewVehicleInCM[0].securityTypes}">
			    <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>	      				  	
			
			 <logic:present name="viewVehicleInCM">
		     <logic:equal value="ASSET" name="acType">
		  	<logic:iterate name="viewVehicleInCM" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked" disabled="disabled"/></td>
		  	</logic:equal>
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" disabled="disabled"/></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	</logic:present>
		  	
		  	
      			
		</tr>
		    
		    <tr>
		    
		    <td><bean:message key="lbl.chasisNumber"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleChesisNo" readonly="true" property="vehicleChesisNo" maxlength="25" value="${viewVehicleInCM[0].vehicleChesisNo}" /></td>
		   
		     <td><bean:message key="lbl.engineNumber"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="engineNumber" readonly="true" property="engineNumber" maxlength="50" value="${viewVehicleInCM[0].engineNumber}" /></td>
		   
		     </tr>       
   <%-- End By Prashant --%>
		  		
		<tr>
		  <td><bean:message key="lbl.invoiceNumber"/></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="invoiceNumber" property="invoiceNumber" maxlength="20" readonly="true" value="${viewVehicleInCM[0].invoiceNumber}" /></td>
		  <td><bean:message key="lbl.invoiceDate"/></td>
		  <td nowrap="nowrap"><html:text styleClass="text" property="vehicleInvoiceDate" readonly="true" value="${viewVehicleInCM[0].vehicleInvoiceDate}" onchange="return checkDate('vehicleInvoiceDate');" /></td>
		  </tr>
		  <tr>
		  <td><bean:message key="lbl.rcReceived"/></td>
		  <td>
		  <html:select property="rcReceived" styleClass="text" disabled="true" value="${viewVehicleInCM[0].rcReceived}">
	              <html:option  value="">---Select---</html:option>
	              <html:option  value="Y">YES</html:option>  
		          <html:option  value="N">NO</html:option>
	      </html:select>
	      </td>
		  <td><bean:message key="lbl.rcReceivedDate"/></td>
		  <td nowrap="nowrap"><html:text styleClass="text" property="rcReceivedDate" readonly="true" value="${viewVehicleInCM[0].rcReceivedDate}" onchange="return checkDate('rcReceivedDate');" /></td>
		 </tr>
		 <tr>
		    <td><bean:message key="lbl.registrationNumber" /></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleRegNo" readonly="true" property="vehicleRegNo" maxlength="25" value="${viewVehicleInCM[0].vehicleRegNo}"  /></td>
		   	<td><bean:message key="lbl.registrationDate"/></td>
          	<td nowrap="nowrap"><html:text styleClass="text" readonly="true" property="vehicleRegDate" value="${viewVehicleInCM[0].vehicleRegDate}" onchange="return checkDate('vehicleRegDate');" /></td>
		 </tr>
		 	<tr>
		   		<td><bean:message key="lbl.yearofManufacture"/>(YYYY)<font color="red">*</font></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleYearOfManufact" maxlength="4" readonly="true" onblur="checkFormateYear(this.value,this.id)" property="vehicleYearOfManufact" value="${viewVehicleInCM[0].vehicleYearOfManufact}" /></td>
		    	<td><bean:message key="lbl.vehicleOwner"/></td>
		    	<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleOwner" property="vehicleOwner" readonly="true" maxlength="50" value="${viewVehicleInCM[0].vehicleOwner}" /></td>
		    </tr>
		    <tr>
		  		<td><bean:message key="lbl.Insurer"/></td>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleInsurer" readonly="true" property="vehicleInsurer" maxlength="50"  value="${viewVehicleInCM[0].vehicleInsurer}" /></td>
		    	<td><bean:message key="lbl.insuredExpDate"/></td>
		    	<td nowrap="nowrap"><html:text styleClass="text"  property="vehicleInsureDate" value="${viewVehicleInCM[0].vehicleInsureDate}" readonly="true" onchange="return checkDate('vehicleInsureDate');" /></td>	    
		    </tr>  
		    <tr>
          		<td><bean:message key="lbl.idv"/></td>
          		<td><html:text styleClass="text" styleId="idv" property="idv"  readonly="true" value="${viewVehicleInCM[0].idv}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
            </tr> 
             <tr>
		    <td><bean:message key="lbl.dealerExecutiveName" /></td>
		    <td>
		        <html:text styleId="dealerExecutive" property="dealerExecutive" readonly="true" styleClass="text" value="${viewVehicleInCM[0].dealerExecutive}" />
		    </td>
		    
		    <td><bean:message key="lbl.dealerManagerName" /></td>
		    <td> 
		        <html:text styleId="dealerManager" property="dealerManager" readonly="true" styleClass="text" value="${viewVehicleInCM[0].dealerManager}" />
		    	
		       </td> 
		    </tr>	
	      <html:hidden property="dealId" value="${dealId}" styleClass="text" styleId ="dealId"/> 
		  	</table>
	</td>
	</tr>
</table>
	</fieldset>		
</html:form>
</logic:present>

<%--          			Insertion in LOAN iNITIATION													 --%>
<logic:present name="loanInit">
	<html:form action="/collateralVehicleProcessAction" styleId="VehicleForm" method="post">
<html:errors/>
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdVehicle" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdVehicle"/>
<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="v1"/>
<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="v2"/>
<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="v3"/>
<fieldset>
<legend><bean:message key="lbl.vehicleDetails"/></legend>
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr>
<td>
 <table cellSpacing=1 cellPadding=1 width="100%" border=0>        
        <tr>
          <td><bean:message key="lbl.vechicleDescription"/><font color="red">*</font></TD>
          <td><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /> </td>
          
          <TD><bean:message key="lbl.vehicleCost"/></TD>
          <td><html:text styleClass="text" styleId="vehicleCost" disabled="true" property="vehicleCost"  value="${fetchCollateralDetails[0].vehicleCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         </tr>
         <tr>
          <TD><bean:message key="lbl.vehiclediscount"/></TD>
          <td><html:text styleClass="text" styleId="vehicleDiscount" disabled="true" property="vehicleDiscount" value="${fetchCollateralDetails[0].vehicleDiscount}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('vehicleDiscount');calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/></td>
          
          <td><bean:message key="lbl.vehicleValue"/></TD>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </tr>
          <!--sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss	-->
		  <tr>
          <td><bean:message key="lbl.idv"/></td>
          <td><html:text styleClass="text" styleId="idv" property="idv" value="${fetchCollateralDetails[0].idv}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          <td><bean:message key="lbl.valuationCost"/></td>
          <td ><html:text styleClass="text" styleId="valuationCost" property="valuationCost"  value="${fetchCollateralDetails[0].valuationCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </tr> 
          
          <tr>
          <td><bean:message key="lbl.gridValue"/></td>
          <td><html:text styleClass="text" styleId="gridValue" property="gridValue" value="${fetchCollateralDetails[0].gridValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          </tr> 
		<!--sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss					-->
            <%-- Start By Prashant --%>
          <logic:present name="action" >
         
          
          		<logic:present name="ASSET" >
               <tr>
	          
	          <html:hidden styleClass="text" styleId="txtProductCat" property="txtProductCat" value="${productCat}"/>
	        <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="clearMakeLov();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="clearMakeLov();" styleClass="text"  >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>  
         
         </logic:present>
          
         <td><bean:message key="lbl.vehicleMake"/><font color="red">*</font></td>          	
          	<logic:present name="action" >
          
          		<logic:present name="ASSET" >
          		
          		        		
          				<td nowrap="nowrap">
          					<input type="hidden" value="A" id="assetcollateral"/>
          					<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|txtProductCat','make_model_id', 'txtProductCat|assetNature','Please select Nature first|Please select product category first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          				</td>
          		</logic:present>          		
          		<logic:present name="COLLATERAL" >
          		<input type="hidden" value="C" id="assetcollateral"/>
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" /></td>
          		</logic:present>
          	</logic:present> 		
          	<logic:notPresent name="action">
          	
         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
          		<td nowrap="nowrap">
          			<input type="hidden" value="A" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|txtProductCat','make_model_id', 'txtProductCat|assetNature','Please select Nature first|Please select product category first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          		</td>
             	<%	}else{ %>
             	<input type="hidden" value="C" id="assetcollateral"/>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" /></td>	
          		<%	}%>
          	</logic:notPresent> 	
	          
	          </tr>
	          
	         
	          
	      </logic:present>
	      
	      <logic:notPresent name="action">
	      <%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
	      	<tr>
	       
	         <html:hidden styleClass="text" styleId="txtProductCat" property="txtProductCat" value="${productCat}"/>
	         <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
			 	<td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="clearMakeLov();" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			 </logic:equal>
			  </logic:iterate>
			 </logic:present>
			 <logic:notPresent name="fetchCollateralDetails">
			<logic:present name="assetCollateralTypeAsset" >
			 <td><bean:message key="lbl.assetNature"/></td>
			          <td>
			          <html:select property="assetNature" styleId="assetNature" onchange="clearMakeLov();" styleClass="text"  >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
         </logic:notPresent>   	
         
         	<%	} %>
         
         <td><bean:message key="lbl.vehicleMake"/><font color="red">*</font></td>          	
          	<logic:present name="action" >
          
          		<logic:present name="ASSET" >
          		
          		        		
          				<td nowrap="nowrap">
          					<input type="hidden" value="A" id="assetcollateral"/>
          					<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          					<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          					<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|txtProductCat','make_model_id', 'txtProductCat|assetNature','Please select Nature first|Please select product category first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          				</td>
          		</logic:present>          		
          		<logic:present name="COLLATERAL" >
          		<input type="hidden" value="C" id="assetcollateral"/>
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" /></td>
          		</logic:present>
          	</logic:present> 		
          	<logic:notPresent name="action">
          	
         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
          		<td nowrap="nowrap">
          			<input type="hidden" value="A" id="assetcollateral"/>
          			<html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" readonly="true"/>
          			<html:hidden styleClass="text" styleId="make_model_id" property="make_model_id" value="${fetchCollateralDetails[0].make_model_id}"/>
          			<html:button property="menufacturerButton" styleId="menufacturerButton" onclick="openLOVCommon(383,'VehicleForm','vehicleMake','assetNature|txtProductCat','make_model_id', 'txtProductCat|assetNature','Please select Nature first|Please select product category first','getLtv','vehicleModel','vehicleYearOfManufact','usageType')" value=" " styleClass="lovbutton"  ></html:button>
          		</td>
             	<%	}else{ %>
             	<input type="hidden" value="C" id="assetcollateral"/>
          		<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleMake" property="vehicleMake" maxlength="50" value="${fetchCollateralDetails[0].vehicleMake}" /></td>	
          		<%	}%>
          	</logic:notPresent>
	          
	          </tr>
	          
	      	 
	      </logic:notPresent>
      
      
       <tr>
         <td><bean:message key="lbl.vehicleModel"/></td>
			<logic:present name="action" >
          		<logic:present name="ASSET" >
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}" readonly="true"/></td>
          		</logic:present>
          		<logic:present name="COLLATERAL" >
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}"/></td>
          		</logic:present>
        	</logic:present> 
        	<logic:notPresent name="action">
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
					<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}" readonly="true"/></td>
			<%	}else{ %>
				<td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleModel" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].vehicleModel}"/></td>
			<%	}%>
			</logic:notPresent>	
			<logic:present name="action" >
          		<logic:present name="ASSET" >
          			<td><bean:message key="lbl.usesType"/></td>
          			<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="usageType" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
          		</logic:present>
          		
        	</logic:present> 
        	<logic:notPresent name="action">
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
			        <td><bean:message key="lbl.usesType"/></td>
					<td nowrap="nowrap"><html:text styleClass="text" styleId="usageType" property="vehicleModel" maxlength="50" value="${fetchCollateralDetails[0].usageType}" readonly="true"/></td>
			<%	} %>
				
			</logic:notPresent>	
			
         
         	</tr>
      
        <tr>
        	 
          	
         		
			   <logic:present name="action" >
          		<logic:present name="ASSET" >
          		<td><bean:message key="lbl.state"/></td>
	          	 <td>
	          	 	<html:hidden styleClass="text" styleId="txtStateCode" property="txtStateCode"  value="${fetchCollateralDetails[0].txtStateCode}" />
	          	 	<html:text styleClass="text" styleId="assetState" property="assetState" readonly="true"  value="${fetchCollateralDetails[0].assetState}" />
	          	 </td>
          		</logic:present>
          		
        	</logic:present> 
        	<logic:notPresent name="action">
			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
			<td><bean:message key="lbl.state"/></td>
	          	 <td>
	          	    <html:hidden styleClass="text" styleId="txtStateCode" property="txtStateCode"  value="${fetchCollateralDetails[0].txtStateCode}" />
	          	 	<html:text styleClass="text" styleId="assetState" property="assetState" readonly="true"  value="${fetchCollateralDetails[0].assetState}" />
	          	 </td>
			         
			<%	} %>
				
			</logic:notPresent>	
			<td><bean:message key="lbl.vehicleSecurityMargin"/></td>
			<td><html:text styleClass="text" styleId="collateralSecurityMargin"  readonly="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
<!--          		<logic:present name="action" >-->
<!--	          		<logic:present name="ASSET" >-->
<!--	                     	<td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>-->
<!--	          	   </logic:present>-->
<!--	          	   <logic:present name="COLLATERAL" >-->
<!--	          	   			<td><html:text styleClass="text" styleId="collateralSecurityMargin"  property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>-->
<!--	          	   </logic:present>-->
<!--	          	</logic:present>-->
<!--	          	<logic:notPresent name="action">-->
<!--		         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>-->
<!--		         	-->
<!--		         		<td><html:text styleClass="text" styleId="collateralSecurityMargin" readonly="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>-->
<!--		         	-->
<!--		         	<%	}else{ %>-->
<!--		         			<td><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>-->
<!--		         	-->
<!--		         	<%	}%>-->
<!--         	</logic:notPresent>-->
          	          
         </tr>
        
		<tr>
		
			<logic:present name="action" >
          	<logic:present name="ASSET" >	
       			<td><bean:message key="lbl.loanAmount"/></td>
       			<td><html:text styleClass="text" styleId="loanAmount" property="loanAmount"  value="${fetchCollateralDetails[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
       		</logic:present>
       		</logic:present>
       		<logic:notPresent name="action">
       			<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
       				<td><bean:message key="lbl.loanAmount"/></td>
					<td nowrap="nowrap"><html:text styleClass="text" styleId="loanAmount" property="loanAmount" value="${fetchCollateralDetails[0].loanAmount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="loanAmountValidation(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				<%	}%>
			</logic:notPresent>
			<logic:present name="action" >
	        <logic:present name="ASSET" >
	                   <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
	                   <td><html:text styleClass="text" styleId="collateralSecurityMarginDF" readonly="true" property="collateralSecurityMarginDF" value="${fetchCollateralDetails[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
	         </logic:present>
	         </logic:present>
	         <logic:notPresent name="action">
		         	<%if(CommonFunction.checkNull(request.getParameter("printLov")).equalsIgnoreCase("Y")){%>
		         	        <td><bean:message key="lbl.vehicleSecurityMarginDF"/></td>
		         			<td><html:text styleClass="text" styleId="collateralSecurityMarginDF" readonly="true" property="collateralSecurityMarginDF" value="${fetchCollateralDetails[0].collateralSecurityMarginDF}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);calculateVehicleValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </td>
		         	<%}%>
         	</logic:notPresent>
		 </tr>         
   <%-- End By Prashant --%>         
   
		  <tr>
		    <td><bean:message key="lbl.vehicleOwner"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleOwner" property="vehicleOwner" maxlength="50" value="${fetchCollateralDetails[0].vehicleOwner}" /></td>
		   <td><bean:message key="lbl.yearofManufacture"/>(YYYY)<font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" maxlength="4" styleId="vehicleYearOfManufact" onblur="checkFormateYear(this.value)" property="vehicleYearOfManufact" value="${fetchCollateralDetails[0].vehicleYearOfManufact}"/></td>
		    </tr>
		  <tr>
		    <td><bean:message key="lbl.registrationNumber" /></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleRegNo" property="vehicleRegNo" maxlength="25" value="${fetchCollateralDetails[0].vehicleRegNo}"  /></td>
		   <td><bean:message key="lbl.registrationDate"/></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleRegDate" property="vehicleRegDate" value="${fetchCollateralDetails[0].vehicleRegDate}" onchange="return checkDate('vehicleRegDate');" /></td>
		    </tr>
		  <tr>
		    <td><bean:message key="lbl.chasisNumber"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleChesisNo" property="vehicleChesisNo" maxlength="25" value="${fetchCollateralDetails[0].vehicleChesisNo}" /></td>
		   <td><bean:message key="lbl.Insurer"/></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleInsurer" property="vehicleInsurer" maxlength="50"  value="${fetchCollateralDetails[0].vehicleInsurer}" /></td>
		    </tr>
		  <tr>
		    <td><bean:message key="lbl.insuredExpDate"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="vehicleInsureDate" property="vehicleInsureDate" value="${fetchCollateralDetails[0].vehicleInsureDate}" onchange="return checkDate('vehicleInsureDate');" /></td>
		   
		  	   
		   </tr>
		  <tr>
		  <td><bean:message key="lbl.invoiceNumber"/></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="invoiceNumber" property="invoiceNumber" maxlength="20" readonly="true" value="${fetchCollateralDetails[0].invoiceNumber}" /></td>
		  <td><bean:message key="lbl.invoiceDate"/></td>
		  <td nowrap="nowrap"><html:text styleClass="text"  property="vehicleInvoiceDate" readonly="true" value="${fetchCollateralDetails[0].vehicleInvoiceDate}" onchange="return checkDate('vehicleInvoiceDate');" /></td>
		  </tr>
		  <tr>
		  <td><bean:message key="lbl.rcReceived"/></td>
		  <td>
		  <html:select property="rcReceived" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].rcReceived}">
	              <html:option  value="">---Select---</html:option>
	              <html:option  value="Y">YES</html:option>  
		          <html:option  value="N">NO</html:option>
	      </html:select>
	      </td>
		  <td><bean:message key="lbl.rcReceivedDate"/></td>
		  <td nowrap="nowrap"><html:text styleClass="text"  property="rcReceivedDate" readonly="true" value="${fetchCollateralDetails[0].rcReceivedDate}" onchange="return checkDate('rcReceivedDate');" /></td>
		 </tr>
		  	 <TR>
		  
          <td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap">
       
          
            <html:text property="machineSupplier" styleClass="text" styleId="machineSupplier"  readonly="true" value="${fetchCollateralDetails[0].lbxmachineSupplier}" tabindex="-1"/>
<!--			<input type="hidden" name="lbxmachineSupplier" id="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}" />-->
			<html:hidden property="lbxmachineSupplier" styleId="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}"/>
			<html:button property="machineButton" style="machineButton" onclick="openLOVCommon(65,'MachineForm','machineSupplier','','', '','','','supplierDesc')" value=" " styleClass="lovbutton"></html:button>
		    <%--<img onClick="openLOVCommon(65,'MachineForm','machineSupplier','','', '','','','supplierDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%> 	  
        	
          </td>
          
           <td><bean:message key="lbl.supplierDesc"/></td>
          <td nowrap="nowrap">
               
        	<html:text property="supplierDesc" styleClass="text" styleId="supplierDesc"  readonly="true" value="${fetchCollateralDetails[0].machineSupplier}" onfocus="statusSDesc();"/>
        	
          </td>
          
		    </TR>
		    <tr>
		    <td><bean:message key="lbl.manufact"/></td>
            <td nowrap="nowrap">
           	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${fetchCollateralDetails[0].assetManufact}" tabindex="-1"/>
<!--			<input type="hidden" name="lbxmachineManufact" id="lbxmachineManufact" value="${fetchCollateralDetails[0].lbxmachineManufact}" />-->
			<html:hidden property="lbxmachineManufact" styleId="lbxmachineManufact" value="${fetchCollateralDetails[0].lbxmachineManufact}"/>
			<html:button property="button" style="button" onclick="openLOVCommon(66,'MachineForm','assetManufact','','', '','','','assetManufactDesc')" value=" " styleClass="lovbutton"></html:button>
		   <%--<img onClick="openLOVCommon(66,'MachineForm','assetManufact','','', '','','','assetManufactDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>  	  
        	
            </td>
              <td><bean:message key="lbl.manufactDesc"/></td>
          <td nowrap="nowrap">
               
        	<html:text property="assetManufactDesc" styleClass="text" styleId="assetManufactDesc"  readonly="true" value="${fetchCollateralDetails[0].assetManufactDesc}" onfocus="statusMDesc();"/>
        	
          </td>
		    </tr>
		  <tr>
 		  <td><button type="button" name="Submit20" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveVehicleDetails();"><bean:message key="button.save" /></button>  </td>
		  </tr>	  
		  <html:hidden property="dealId" value="${dealId}" styleClass="text" styleId ="dealId"/>  
          
	</table>
	</td>
	</tr>
</table>
	</fieldset>		
</html:form>
</logic:present>
<logic:present name="vehicalUpdt">

<script type="text/javascript">
	if('<%=request.getAttribute("vehicalUpdt")%>'=='CHASIS')
	{
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisAlreadyExist" />"))

		{var propertyType = document.getElementById("v1").value;
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=saveCollateralDetails&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalUpdt")%>'=='ENGINE')
{		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.engineAlreadyExist" />"))

		{var propertyType = document.getElementById("v1").value;
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=saveCollateralDetails&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
		}
		else if('<%=request.getAttribute("vehicalUpdt")%>'=='REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.RegistrationAlreadyExist" />"))

		{var propertyType = document.getElementById("v1").value;
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=saveCollateralDetails&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}

else if('<%=request.getAttribute("vehicalUpdt")%>'=='CHASIS_ENGINE')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisEngineAlreadyExist" />"))

		{var propertyType = document.getElementById("v1").value;
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=saveCollateralDetails&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalUpdt")%>'=='CHASIS_REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisRegnoAlreadyExist" />"))

		{var propertyType = document.getElementById("v1").value;
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=saveCollateralDetails&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalUpdt")%>'=='ENGINE_REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.EngineRegnoAlreadyExist" />"))

		{var propertyType = document.getElementById("v1").value;
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=saveCollateralDetails&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}




	</script>
</logic:present>

<logic:present name="vehicalSave">

<script type="text/javascript">
		var primaryId = document.getElementById("assetsIdVehicle").value;
	    var assetsType = document.getElementById("v2").value;
		
	if('<%=request.getAttribute("vehicalSave")%>'=='CHASIS')
	{
	
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisAlreadyExist" />"))

		{	
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+${primaryId}+"&assetsType="+'${assetsType}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalSave")%>'=='ENGINE')
{		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.engineAlreadyExist" />"))

		{	
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+${primaryId}+"&assetsType="+'${assetsType}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
		}
		else if('<%=request.getAttribute("vehicalSave")%>'=='REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.RegistrationAlreadyExist" />"))

		{
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+${primaryId}+"&assetsType="+'${assetsType}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}

else if('<%=request.getAttribute("vehicalSave")%>'=='CHASIS_ENGINE')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisEngineAlreadyExist" />"))

		{
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+${primaryId}+"&assetsType="+'${assetsType}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		}
		
		
}
else if('<%=request.getAttribute("vehicalSave")%>'=='CHASIS_REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisRegnoAlreadyExist" />"))

		{
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+${primaryId}+"&assetsType="+'${assetsType}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalSave")%>'=='ENGINE_REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.EngineRegnoAlreadyExist" />"))

		{
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+${primaryId}+"&assetsType="+'${assetsType}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalSave")%>'=='CHASIS_ENGINE_REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisEngineRegnoAlreadyExist" />"))

		{
			document.getElementById("VehicleForm").action="collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+${primaryId}+"&assetsType="+'${assetsType}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("VehicleForm").submit();
		
		}
		
		
}

</script>
</logic:present>
	</div>
	</body>
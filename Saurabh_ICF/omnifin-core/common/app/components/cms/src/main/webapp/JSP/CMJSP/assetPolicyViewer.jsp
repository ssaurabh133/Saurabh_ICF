<!-- 
Author Name :- MRADUL AGARWAL
Date of Creation :06-08-2013
Purpose :-  screen for the Asset Maker
-->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>	
	<head>
	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	request.setAttribute("no",no); %>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 
	<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
			
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>	   
		  <!-- css and jquery for Datepicker -->	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/asset.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>	
		  <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
	   <link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />	
	</head>
	<body onload="enableAnchor();">
	<div align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">	
	<div id="revisedcontainer">
	<logic:present name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
	</logic:notPresent>
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
	<html:form action="/assetProcessAction" method="post" styleId="assetMakerSearch">
		          
    <fieldset>	
	<legend><bean:message key="lbl.assetInsuranceViewer"/></legend>  
  

   <table width="100%" border="0" cellspacing="1" cellpadding="1">
     <logic:present name="onAssetInsurancePolicyViewer">
		<logic:notEmpty  name="onAssetInsurancePolicyViewer">
		<html:hidden property="assetInsuranceID" styleId="assetInsuranceID" value="${requestScope.assetInsuranceID}"/>
            <tr>
		     	<td><bean:message key="lbl.loanAccount"></bean:message> </td>
		  		 <td>
				 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="${onAssetInsurancePolicyViewer[0].loanAccountNumber}" readonly="true" tabindex="-1"/>
		        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${onAssetInsurancePolicyViewer[0].lbxLoanNoHID}"  />
		        <input type="hidden" value="${onAssetInsurancePolicyViewer[0].lbxLoanNoHID}" id="loanId" name="loanId"/>	
		 		</td>
				<td><bean:message key="lbl.customerName"></bean:message></td>
				<td>
				<html:text property="customerName"  styleClass="text" styleId="customerName" value="${onAssetInsurancePolicyViewer[0].customerName}"  maxlength="50" readonly="true" disabled="true" tabindex="-1"></html:text>
				<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
				</td>
		</tr>
	       <tr>
	         <td><bean:message key="lbl.veri.entityType"></bean:message></td>
	        <td>
	       <html:text property="entityType"  styleClass="text" styleId="entityType" value="${onAssetInsurancePolicyViewer[0].entityType}" maxlength="20" style="text-align: right" disabled="true" ></html:text>
	       </td>
	       <td><bean:message key="lbl.entity"></bean:message></td>
	        <td>
	        <a rel="tooltip3" id="tool">
		    <html:text styleClass="text" property="entity" styleId="entity" maxlength="50"  value="${onAssetInsurancePolicyViewer[0].entity}" onmouseover="applyToolTip(id);" readonly="true" tabindex="-1"/>
           </a></td> 	      
	      </tr>
	      <tr>
	       <td><bean:message key="lbl.assetDesc"></bean:message></td>
	       <td>
	       <html:text property="assetDesc"  styleClass="text" styleId="assetDesc" value="${onAssetInsurancePolicyViewer[0].assetDesc}"  disabled="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetNature"></bean:message></td>
	       <td>
	       <html:text property="assetNature"  styleClass="text" styleId="assetNature" value="${onAssetInsurancePolicyViewer[0].assetNature}"  disabled="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.assetMake"></bean:message></td>
	       <td>
	       <html:text property="assetMake"  styleClass="text" styleId="assetMake" value="${onAssetInsurancePolicyViewer[0].assetMake}"  disabled="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetModel"></bean:message></td>
	       <td>
	       <html:text property="assetModel"  styleClass="text" styleId="assetModel" value="${onAssetInsurancePolicyViewer[0].assetModel}"  disabled="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.vendorname"></bean:message></td>
	       <td>
	       <html:text property="dealerName"  styleClass="text" styleId="dealerName" value="${onAssetInsurancePolicyViewer[0].dealerName}" disabled="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.engineNumber"></bean:message></td>
	       <td>
	       <html:text property="engineNumber"  styleClass="text" styleId="engineNumber" value="${onAssetInsurancePolicyViewer[0].engineNumber}"  disabled="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.chasisNumber"></bean:message></td>
	       <td>
	       <html:text property="chasisNumber"  styleClass="text" styleId="chasisNumber" value="${onAssetInsurancePolicyViewer[0].chasisNumber}" disabled="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.registrationNumber"></bean:message></td>
	       <td>
	       <html:text property="registrationNumber"  styleClass="text" styleId="registrationNumber" value="${onAssetInsurancePolicyViewer[0].registrationNumber}"  disabled="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
	      <tr>
	      <td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	       <td>
	       <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="${onAssetInsurancePolicyViewer[0].coverNoteNo}" disabled="true"></html:text>
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	      </td>
	       <td><bean:message key="lbl.policyNo"></bean:message></td>
	       <td >
	       <html:text property="policyNo"  styleClass="text" styleId="policyNo" value="${onAssetInsurancePolicyViewer[0].policyNo}" disabled="true"></html:text>
	       </td>
		   </tr>		   
		   <tr>    		   
	        <td><bean:message key="lbl.insuranceAgency"></bean:message></td>
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="${onAssetInsurancePolicyViewer[0].insuranceAgency}" disabled="true"/>
          <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency" value="${onAssetInsurancePolicyViewer[0].lbxInsuranceAgency}" />
		   </td>	  		   
		   <td><bean:message key ="lbl.insrDoneBy"></bean:message></td>
		   <td>
	       <html:text property="insuranceDoneBy"  styleClass="text" styleId="insuranceDoneBy" value="${onAssetInsurancePolicyViewer[0].insuranceDoneBy}" disabled="true"></html:text>
	       </td> 		  	
		   </tr>		   
		  <tr>	
		   <td><bean:message key="lbl.sumAssured"></bean:message></td>
	       <td >
	       <html:text property="sumAssured"  styleClass="text" styleId="sumAssured" value="${onAssetInsurancePolicyViewer[0].sumAssured}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  disabled="true"></html:text>
	       </td>       
	       <td><bean:message key="lbl.premiumAmnt"></bean:message></td>
	       <td>
	       <html:text property="premiumAmnt"  styleClass="text" styleId="premiumAmnt" value="${onAssetInsurancePolicyViewer[0].premiumAmnt}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)"  disabled="true"></html:text>
	       </td>	       
		   </tr>		   
	       <tr>
	       <td><bean:message key ="lbl.startDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td >
	       <html:text property="startDate"  styleClass="text" styleId="remarks" value="${onAssetInsurancePolicyViewer[0].startDate}" maxlength="50" disabled="true"></html:text>
	       </td>	   
	        <td><bean:message key ="lbl.endDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td>
	       <html:text property="endDate"  styleClass="text" styleId="remarks" value="${onAssetInsurancePolicyViewer[0].endDate}" maxlength="50" disabled="true"></html:text>
	       </td>	       
	      </tr>
	    <tr>	
	    <td><bean:message key ="lbl.yearNo"></bean:message></td>
	       <td >
	       <html:text property="yearNo"  styleClass="text" styleId="yearNo" value="${onAssetInsurancePolicyViewer[0].yearNo}" maxlength="04" disabled="true" ></html:text>
	       </td>     
	       <td><bean:message key="lbl.surrenderValue"/></td>
			 <td>
	       <html:text property="surrenderValue"  styleClass="text" styleId="surrenderValue" value="${onAssetInsurancePolicyViewer[0].surrenderValue}" maxlength="20" style="text-align: right" disabled="true" ></html:text>
	       </td>		 	 
		 	</tr>
	        <tr>
	        <td style="width:23%"><bean:message key="lbl.premiumFrequency"/></td>
           	<td>
	       <html:text property="premiumFrequency"  styleClass="text" styleId="premiumFrequency" value="${onAssetInsurancePolicyViewer[0].premiumFrequency}" maxlength="20" style="text-align: right" disabled="true" ></html:text>
	       </td>	         
	       <td style="width:23%"><bean:message key="lbl.nominee"/></td>
 		   <td style="width:26%"><html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value="${onAssetInsurancePolicyViewer[0].nominee}" disabled="true" /></td>
		    </tr>
	        <tr>   
	         <td><bean:message key="lbl.relWithNominee"/></td>
		   	<td>
	       <html:text property="relWithNominee"  styleClass="text" styleId="relWithNominee" value="${onAssetInsurancePolicyViewer[0].relWithNominee}" maxlength="20" style="text-align: right"  disabled="true" ></html:text>
	       </td>
	       <td><bean:message key="lbl.adviseFlag" /></td>
	      	<td>
	       <html:text property="adviseFlag"  styleClass="text" styleId="adviseFlag" value="${onAssetInsurancePolicyViewer[0].adviseFlag}" maxlength="20" style="text-align: right" disabled="true" ></html:text>
	       </td>		   
	        </tr>	      
	       <tr>	
	       <td><bean:message key="lbl.remark" /></td>
	       <td><textarea name="remarks" class="text" id="remarks" maxlength="500" property="relWithNominee" disabled="true" >${onAssetInsurancePolicyViewer[0].remarks}</textarea>	         
	       </td>	  
	       <td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true" disabled="true"
			value="${onAssetInsurancePolicyViewer[0].authorRemarks}" /></td>
	       </tr>	       
	     </logic:notEmpty>
		 </logic:present>
 </table>    


 </fieldset>
 </html:form>
  
</div>
</div>
</body>
</html>
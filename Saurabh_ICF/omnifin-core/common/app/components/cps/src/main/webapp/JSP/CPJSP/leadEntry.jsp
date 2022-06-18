<%--
      Author Name-      Prashant Kumar
      Date of creation -15/04/2011
      Purpose-          Entry of Leads
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       Date currentDate = new Date();
	   String receiptSysDate=dateFormat.format(currentDate);
	   	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/relationship_manager.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customer_address.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/leadEntry.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	 
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
	   <link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
	
	   
       
  <base target="body" />
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
	
<body oncontextmenu="return false;" onload="enableAnchor();showHideDescLovOnDeal();checkChanges('sourcingForm');checkVendor();genrateByDealOrLead();" onunload="closeAllWindowCallUnloadBody();">
<input type="hidden" name="<%= org.apache.struts.taglib.html.Constants.TOKEN_KEY %>" value="<%= session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>" >
<%-- Div for screen Saver --%>
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>

<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	

<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

<div id=centercolumn>
<div id=revisedcontainer>
<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
<logic:notPresent name="viewDeal">
<html:form styleId="sourcingForm" action="/leadProcessAction" method="post">

<FIELDSET>
<LEGEND><bean:message key="lbl.lead.detail"/></LEGEND>
<TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>
  <TBODY>
  <TR>
    <TD>
<!--    <font color="red" ><html:errors /></font>-->
      <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>
        
        <TR>
        
        <html:hidden  property="leadMValue" styleId="leadMValue" value="${leadMValue}"/>
         <html:hidden  property="hCommon" styleId="hCommon" value=""/>
           <TD>
           <bean:message key="lbl.leadNo"/>
           <logic:present name="leadMValue">
           		<logic:equal name="leadMValue" value="Y" >
           			<font color="red">*</font>
           		</logic:equal>
           </logic:present>
           </TD>
           	
             <html:hidden property="dealId" styleClass="text" styleId="dealId"  value="${leadInfo[0].dealId}" />
           <TD ><input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
              <html:text property="leadNo" styleClass="text" styleId="leadNo" readonly="true" value="${leadInfo[0].leadNo}" tabindex="-1"/>
                <html:hidden  property="lbxLeadNo" styleId="lbxLeadNo" value="${leadInfo[0].leadNo}"/>
                <logic:equal name="leadMValue" value="Y" >
                <logic:notPresent name="leadInfo">
                	 <html:button property="leadButton" styleId="leadButton" onclick="openLOVCommon(226,'sourcingForm','leadNo','userId','leadNo', 'userId','','getLeadInfo','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
                </logic:notPresent>
                </logic:equal>
                
                       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>       
             
           </TD>
           <TD><bean:message key="lbl.customerName"/></TD>
           <TD ><html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" value="${leadInfo[0].customerName}" tabindex="-1"/></TD>
		</TR>
		
		 <tr>
          	<TD><bean:message key="lbl.dealNo"/></TD>
           <TD><html:text property="dealNo" styleClass="text" styleId="dealNo" readonly="true" value="${leadInfo[0].dealNo}" tabindex="-1"/></TD>
         
		</tr>
		
		 <tr>
          
           <TD><bean:message key="lbl.applicationFormNo"  /><font color="red">*</font> </TD>
           <TD ><html:text property="applicationFormNo" styleClass="text" maxlength="20" styleId="applicationFormNo" value="${leadInfo[0].applicationFormNo}" onkeyup="return upperMe('applicationFormNo');" /></TD>
           <TD><bean:message key="lbl.dateOfReceipt" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></TD>

           <TD >
           		<logic:present name="leadInfo">
           		<html:text property="leadDate" styleClass="text3" styleId="leadDate"  value="${leadInfo[0].leadDate}" onchange="checkDate('leadDate');validateLeadDate();"/>
           	    </logic:present> 
           	   	<logic:notPresent name="leadInfo">
           		<html:text property="leadDate" styleClass="text3" styleId="leadDate"  value="${userobject.businessdate }" onchange="checkDate('leadDate');validateLeadDate();"/>
           	    </logic:notPresent> 
           	    <html:text property="leadTime"  styleId="leadTime" onchange="validateLeadTime();" maxlength="5"  styleClass="text5" value="${leadInfo[0].leadTime}" />&nbsp;(24hrs Format)
                   
           </TD>
		</tr>
        <tr>
        	<td><bean:message key="lbl.dateEncoded" /><font color="red">*</font></td>
        	<td>
        	
        	  <html:text property="dateEncoded" styleClass="text" styleId="dateEncoded" value="<%=receiptSysDate%>" disabled="true"/>
        	  <html:hidden property="dateEncoded" styleClass="text" styleId="dateEncoded" value="<%=receiptSysDate%>" />
        	        	
        	</td>
        	
        	<td><bean:message key="lbl.dealCat" /></td>
        	<td>
        	
        	  <html:select property="dealCat" styleId="dealCat" styleClass="text" value="${leadInfo[0].dealCat}"  >
          	  	<logic:present name="dealCatList">
          	  		<html:optionsCollection name="dealCatList" label="name" value="id" />
          	  	</logic:present>
          	  
          	  	</html:select>
        	        	
        	</td>
        
        </tr>

        <tr>
        <td><bean:message key="lbl.branch" /><font color="red">*</font></td>
        <td >
         
         <html:text property="branch" readonly="true" styleId="branch"  value="${userobject.branchName}" styleClass="text" tabindex="-1"/>
          
         <html:hidden  property="lbxBranchId" styleId="lbxBranchId" value="${userobject.branchId}" />
        <%--  <img onClick="openLOVCommon(12,'leadEntryForm','branch','','', '','','lovBranchLead','hIndustry')" src="<%= request.getContextPath()%>/images/lov.gif"> 	--%>  
          	  	
          	  	
         
       
        </td>
        
        <td><bean:message key="lbl.relationshipManager"/><font color="red">*</font>
        </td>
          <td>
              <logic:notPresent name="leadInfo">
      		    <html:text property="relationshipManager" readonly="true" styleId="relationshipManager" styleClass="text" value="${checkLoginUserLevel[0].relationshipManager}" tabindex="-1" />
          		<html:hidden  property="lbxRelationship" styleId="lbxRelationship" value="${checkLoginUserLevel[0].lbxRelationship}" />
                <html:button property="lbxSubIndustryButton" styleId="lbxSubIndustryButton" onclick="closeLovCallonLov1();openLOVCommon(13,'sourcingForm','relationshipManager','branch','lbxRelationship', 'lbxBranchId','Please Select Branch','','hCommon')" value=" " styleClass="lovbutton"> </html:button>
              </logic:notPresent >
              
              <logic:present name="leadInfo">
                <html:text property="relationshipManager" readonly="true" styleId="relationshipManager" styleClass="text" value="${leadInfo[0].relationshipManager}" tabindex="-1" />
          		<html:hidden  property="lbxRelationship" styleId="lbxRelationship" value="${leadInfo[0].lbxRelationship}" />
                <html:button property="lbxSubIndustryButton" styleId="lbxSubIndustryButton" onclick="closeLovCallonLov1();openLOVCommon(13,'sourcingForm','relationshipManager','branch','lbxRelationship', 'lbxBranchId','Please Select Branch','','hCommon')" value=" " styleClass="lovbutton"> </html:button>
              </logic:present>
          </td>
        
          </tr>
           <tr>
		     
<!--		    	 <TD><bean:message key="lbl.vendor.code"/><font color="red">*</font></TD>-->
<!--		    	 <TD>-->
<!--		         -->
<!--		             <html:text property="vendorCode" readonly="true" styleClass="text" styleId="vendorCode" value="${leadInfo[0].lbxvendorCode}" onkeypress="return isNumberKey(event);" tabindex="-1"/>-->
<!--		             <input type="hidden" name="lbxvendorCode" id="lbxvendorCode" value="${leadInfo[0].lbxvendorCode}" />-->
<!--		         <html:button property="vendorCodeButton"  styleId="vendorCodeButton" onclick="closeLovCallonLov1();openLOVCommon(34,'leadEntryForm','vendorCode','','', '','','leadVendorDesc','sourcedesc')" value=" " styleClass="lovbutton" style="display:none;"> </html:button>   -->
<!--		        </TD>-->
                    <td><bean:message key="lbl.leadgeneratorBy" /><span><font style="color: red;"></font></span></td>
					<td>
					<html:select styleId="leadGeneratorByDrop" property="leadGeneratorBy" styleClass="text" value="${leadInfo[0].leadGeneratorBy}">
							<html:option value="">---Select---</html:option>
							<html:option value="RM">RM / SALES EXEC</html:option>
							<html:option value="VENDOR">VENDOR / DEALER</html:option>
							<html:option value="BRANCH">TELECALLER / BRANCH</html:option>
					</html:select>
					<html:hidden style="display:none" property="leadGeneratorBy"  disabled="true" styleId="leadGeneratorByBox" styleClass="text" value="${leadInfo[0].leadGeneratorBy}"  />
					<html:text style="display:none" property="leadGeneratorByDesc" disabled="true" readonly="true" styleId="leadGeneratorByDesc" styleClass="text" value="${leadInfo[0].leadGeneratorByDesc}" tabindex="-1" />
					</td>			 
		        <td><bean:message key="lbl.relationshipofficer"/></td>
          <td>
  			<logic:notPresent name="leadInfo">
  				<html:text property="generatedUser" readonly="true" styleId="generatedUser" styleClass="text" value="${checkLoginUserLevel[0].generatedUser}" tabindex="-1" />
          		<html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" value="${checkLoginUserLevel[0].lbxUserSearchId}" />
          		<html:button property="lbxSubIndustryButton" styleId="lbxSubIndustryButton" onclick="closeLovCallonLov1();openLOVCommon(502,'sourcingForm','generatedUser','branch','lbxUserSearchId', 'lbxBranchId','Please Select Branch','checkRelationshipManage','hCommon','relationshipManager')" value=" " styleClass="lovbutton"> </html:button>
  			</logic:notPresent>
  			<logic:present name="leadInfo">
      		    <html:text property="generatedUser" readonly="true" styleId="generatedUser" styleClass="text" value="${leadInfo[0].generatedUser}" tabindex="-1" />
          		<html:hidden property="lbxUserSearchId" styleId="lbxUserSearchId" value="${leadInfo[0].leadRo}" />
                <html:button property="lbxSubIndustryButton" styleId="lbxSubIndustryButton" onclick="closeLovCallonLov1();openLOVCommon(502,'sourcingForm','generatedUser','branch','lbxUserSearchId', 'lbxBranchId','Please Select Branch','checkRelationshipManage','hCommon','relationshipManager')" value=" " styleClass="lovbutton"> </html:button>
			</logic:present>
          </td>
			      
			      
			       
			       </tr>
			       
		  <tr>
		    <td><bean:message key="lbl.sourceTypeDesc"/><font color="red">*</font></td>
          <td>
<!--               <html:select property="source" styleId="source" styleClass="text" value="${leadInfo[0].source}" onchange="return getVendor();" >-->
<!--          	  	<html:option value="" >--Select--</html:option>-->
<!--          	  	<logic:present name="sourceTypeList">-->
<!--          	  		<html:optionsCollection name="sourceTypeList" label="name" value="id" />-->
<!--          	  	</logic:present>-->
<!--          	  -->
<!--          	  	</html:select>-->
          	  	<html:text property="lovSourceDes" styleId="lovSourceDes" styleClass="text" readonly="true" tabindex="-1" value="${leadInfo[0].lovSourceDes}"></html:text>
				
				
				<input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(396,'leadCapturingDetails','source','','','','','showHideSourceDescLov','lovSourceDes');" value=" " tabindex="1" name="dealButton" />
				
				<html:hidden property="source" styleId="source" value="${leadInfo[0].source}"/>
          	  	
          </td>
          
		    <td><bean:message key="lbl.source.desc"/><font color="red">* </font></td>
		    <td >
		    	<input type="hidden" id="lbxDescription" name="lbxDescription"/>
		    	<logic:present name="leadInfo">
			    <logic:iterate name="leadInfo" id="obleadInfo">
			    <logic:equal name="obleadInfo" property="source" value="VENDOR">
		          	<html:text property="sourcedesc" styleClass="text" readonly="true" styleId="sourcedesc" value="${leadInfo[0].sourcedesc}" maxlength="50" onchange="return upperMe('sourcedesc');"/>
		        </logic:equal>
		         <logic:notEqual name="obleadInfo" property="source" value="VENDOR">
						<html:text property="sourcedesc" styleClass="text"  styleId="sourcedesc" value="${leadInfo[0].sourcedesc}" maxlength="50" onchange="return upperMe('sourcedesc');" />
						<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLovOnDeal();" value=" " styleClass="lovbutton"> </html:button>
		         </logic:notEqual>
		        </logic:iterate>
		        </logic:present>
		        <logic:notPresent name="leadInfo">
		        	<html:text property="sourcedesc" styleClass="text" styleId="sourcedesc" value="${leadInfo[0].sourcedesc}" maxlength="50" onchange="return upperMe('sourcedesc');"/>
		        	<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLovOnDeal();" value=" " styleClass="lovbutton"> </html:button>
		        </logic:notPresent>
		      </td>
		    
		     </tr>
		   
		    
		    	 <tr>
		    <td><bean:message key="lbl.dealerExecutiveName" /></td>
		    <td><html:text styleId="dealerExecutive" property="dealerExecutive" styleClass="text" value="${leadInfo[0].dealerExecutive}" /></td>
		    
		    <td><bean:message key="lbl.dealerManagerName" /></td>
		    <td> <html:text property="dealerManager" styleId="dealerManager" styleClass="text" value="${leadInfo[0].dealerManager}" /></td> 
		    </tr>
		    
		   
		   
		   
		    <!-- Nishant start -->
		    <tr>
		    	<td><bean:message key="lbl.referredBy" /></td>
		    	<td><html:text styleId="referredBy" property="referredBy" styleClass="text" maxlength="50" value="${leadInfo[0].referredBy}" /></td>
		    </tr>
		    <!-- Nishant ends -->
		    <tr>
		          <td ><bean:message key="lbl.authorRemarks" /></td>
		          <td >
		          <a rel="tooltip3" id="tool">
		          
		                <html:textarea property="remarks" styleId="remarks" styleClass="text" readonly="true" onmouseover="applyToolTip(id);" value="${creditApprovalList[0].remarks }"/>
						
				  </a>
					</td>
		     <td><bean:message key="lbl.areaCode" /></td> 
			  		 <td>
			  		      
			   			<html:text property="areaCodename" styleId="areaCodename" styleClass="text" readonly="true" value="${leadInfo[0].areaCodename}"/>
			   			
			     			
			      		<html:button property="areaCodeButton" styleId="areaCodeButton" onclick="openLOVCommon(370,'leadCapturingDetails','areaCodename','','', '','','','lbxareaCodeVal');" value=" " styleClass="lovbutton"></html:button>
			     		<html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal" value="${leadInfo[0].lbxareaCodeVal}"  />
			
			  
			       </td>
		     </tr>
		      <tr>
		       <td><bean:message key="lbl.rateApprovalRemark" /></td> 
		        <td>
		          <html:text property="rateApprovalRemark" styleId="rateApprovalRemark" styleClass="text" readonly="true" value="${leadInfo[0].rateApprovalRemark}"/>
		        </td>
		         <td ><bean:message key="lbl.fiAppraiserName" /></td>
		            <td >
		       			<html:text property="fiAppraiserName" styleId="fiAppraiserName" styleClass="text" maxlength="50" value="${leadInfo[0].fiAppraiserName}"/>                
		
				   </td>
		      </tr>  
		      
		       <tr>
		          
		          <td><bean:message key="lbl.fidecisionDeal" /></td> 
			  		 <td>
			  		      
			   			<html:select styleId="fidecisionDeal" property="fidecisionDeal" styleClass="text" value="${leadInfo[0].fidecisionDeal}">
							<html:option value="">---Select---</html:option>
							<html:option value="P">POSITIVE</html:option>
							<html:option value="N">NEGATIVE</html:option>
							<html:option value="R">REFER</html:option>
					    </html:select>
			  
			       </td>
			        <td ><bean:message key="lbl.fiRemarksDeal" /></td>
		            <td >
		       			<textarea name="fiRemarksDeal" id="fiRemarksDeal" class="text" maxlength="450" >${leadInfo[0].fiRemarksDeal }</textarea>                
		
					</td>
		       </tr>
		       
		       
		       
		        <tr>
		          <td ><bean:message key="lbl.makerRemark" /></td>
		            <td >
		       			<textarea name="makerRemark" id="makerRemark" class="text" maxlength="450" >${leadInfo[0].makerRemark }</textarea>                
		
					</td>
					
					<td ><bean:message key="lbl.caseVisitedBy" /></td>
		            <td >
		       			<html:text property="caseVisitedBy" styleId="caseVisitedBy" styleClass="text" maxlength="50" value="${leadInfo[0].caseVisitedBy}"/>                
		
				   </td>
		     
		     </tr>
		       
		       
		       
		       	     
		     
		  </TABLE></TD></TR>
 
   
    </TBODY></TABLE>
     <button type="button" name="button" id="searchButton" title="Alt+V" accesskey="V" class="topformbutton2" onclick="saveLeadEntryDetails('sourcingForm');"><bean:message key="button.save" /></button>
     <logic:present name="functionId">
       <logic:equal name="functionId" value="3000206">
          <button type="button" name="button" id="newButton" title="Alt+F" accesskey="F" class="topformbutton2" onclick="return saveForwardLeadEntryDetails('<bean:message key="msg.confirmationForwardMsg" />');"><bean:message key="button.forward" /></button>
       </logic:equal>
        <logic:equal name="functionId" value="3000217">
          <button type="button" name="button" id="newButton" title="Alt+F" accesskey="F" class="topformbutton2" onclick="confirmDealDetails();"><bean:message key="button.confirm" /></button>
        </logic:equal>
     </logic:present>
     <button type="button" name="button" id="leadButton" title="Alt+L" accesskey="L" class="topformbutton2" onclick="return viewLeadDetails();"><bean:message key="button.leadview" /></button>

</FIELDSET> 

</html:form>

<logic:present name="astLonError">
	<script type="text/javascript">	
		if('<%=request.getAttribute("val").toString()%>'=='1')
			alert("Asset cost at Loan Detail should be equal to sum of Value of all Asset,which is   : ${asstcstAD}");
		else if ('<%=request.getAttribute("val").toString()%>'=='2')
			alert("Loan Amount at Loan Detail should be equal to sum of Loan Amounts of all Asset(vehicle),which is  : ${loanamtAD}");
		else if ('<%=request.getAttribute("val").toString()%>'=='3')
		{
			var msg="Asset cost at Loan Detail should be equal to sum of Value of all Asset,which is   : ${asstcstAD} \nLoan Amount at Loan Detail should be equal to sum of Loan Amounts of all Asset(vehicle),which is  : ${loanamtAD}";
			alert(msg);
		}
	</script>
</logic:present>
<logic:present name="back">

	<script type="text/javascript">
	
		alert("<bean:message key="lbl.dealMove" />")
		
</script>
</logic:present>
<logic:present name="appl">

	<script type="text/javascript">
	
		alert("<bean:message key="lbl.dealDublicateApplNo" />")
		
  </script>

</logic:present>
<logic:present name="NotSaved">

	<script type="text/javascript">
	
		alert("<bean:message key="lbl.errorSuccess" />")
		
  </script>

</logic:present>
<logic:present name="moveTosecond">

	<script type="text/javascript">
			parent.menu.document.test.recStatus.value = "P";
	       	alert("<bean:message key="lbl.saveDeal" />");
		   //location.href="custEntryAction.do";
	  </script>
  
</logic:present>

<logic:present name="status">

 <script type="text/javascript">
 
	if('<%=request.getAttribute("status").toString()%>'=='CS')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.custMove" />");
		DisButClass.prototype.EnbButMethod();
	//	location.href="custEntryAction.do";
	//	document.forms[0].action="custEntryAction.do";
	    //document.forms[0].submit();
	}
	
	else if('<%=request.getAttribute("status").toString()%>'=='CA')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.cummAddr" />");
		//location.href="custEntryAction.do";
	//	document.forms[0].action="custEntryAction.do";
	    //document.forms[0].submit();
	    DisButClass.prototype.EnbButMethod();
	}
	
	else if('<%=request.getAttribute("status").toString()%>'=='LD')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.loanMove" />");
		//location.href="openLoanDetailAction.do";
		//document.forms[0].action="loanDetailBehindAction.do";
	   // document.forms[0].submit();
	   DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='AC')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.assetMove" />");
		//location.href="collateralBehindAction.do?method=collateralBehindDetail";
		//document.forms[0].action="collateralDetailBehindAction.do";
		//alert(document.forms[0].action);
	   // document.forms[0].submit();
	   DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='CH')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.chargeMove" />");
		//location.href="chargeBehindAction.do?method=chargeInDeal";
		//location.href="openLoanDetailAction.do";
		//document.forms[0].action="chargeBehindAction.do";
	    //document.forms[0].submit();
	    DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='DC')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.docsMove" />");
		//location.href="docsCollectionBehindAction.do";
		//document.forms[0].action="docsCollectionBehindAction.do";
	    //document.forms[0].submit();
	    DisButClass.prototype.EnbButMethod();
	}	
	else if('<%=request.getAttribute("status").toString()%>'=='ASSETCT')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.noOfAssetError" />");
		//location.href="docsCollectionBehindAction.do";
		//document.forms[0].action="docsCollectionBehindAction.do";
	    //document.forms[0].submit();
	    DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='PLZASSETCAP')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.plzAssetCap" />");
		//location.href="docsCollectionBehindAction.do";
		//document.forms[0].action="docsCollectionBehindAction.do";
	    //document.forms[0].submit();
	    DisButClass.prototype.EnbButMethod();
	}		
	else if('<%=request.getAttribute("status").toString()%>'=='CP2')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.saveCustEntryThenForward" />");
		//location.href="custEntryAction.do";
		DisButClass.prototype.EnbButMethod();
	}	
	else if('<%=request.getAttribute("status").toString()%>'=='CP5')
	{
		alert("<bean:message key="lbl.saveLoanDetailThenForward" />");
		//location.href="loanDetailBehindAction.do";
		///location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
		
	}
	else if('<%=request.getAttribute("status").toString()%>'=='CP6')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.saveChargeFeeThenForward" />");
		//location.href="chargeBehindAction.do?method=chargeInDeal";
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	
	}		
	else if('<%=request.getAttribute("status").toString()%>'=='CP11')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.saveDocCollectionThenForward" />");
		//location.href="docsCollectionBehindAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='CP8')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.saveInstallmentPlanThenForward"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}	
	else if('<%=request.getAttribute("status").toString()%>'=='CP9')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.selectPaymentScheduleThenForward"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='GA')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.custGA" />");
		//location.href="custEntryAction.do";
	//	document.forms[0].action="custEntryAction.do";
	    //document.forms[0].submit();
	    DisButClass.prototype.EnbButMethod();
	}	
	else if('<%=request.getAttribute("status").toString()%>'=='TE')
	{
	DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.tenureEqual"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='SD')
	{
	DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.sdEqual"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='CSM')
	{
	DisButClass.prototype.DisButMethod();
		alert('${checkStageM}');
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='charge101')
	{
	DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.charge101"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='charge102')
	{
	DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.charge102"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='bothcharge')
	{
	DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.bothcharge"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='bothNotExist')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.bothchargeREQ"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status")%>'=='CUSTREF')
	{
		DisButClass.prototype.DisButMethod();
		alert("AtLeast "+'${referenceCountPara}'+" <bean:message key="lbl.insufficient"/>");
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status")%>'=='VS')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="msg.saveBeforeForward"/>");
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='1')
	{
	        DisButClass.prototype.DisButMethod();
			alert("Asset cost at Loan Details Screen should be equal to Sum of Value of all Assets displayed in Asset/Collaterals Screen. .");
			DisButClass.prototype.EnbButMethod();
	}
	else if ('<%=request.getAttribute("status").toString()%>'=='2')
	{
	        DisButClass.prototype.DisButMethod();
			alert("Loan Amount at Loan Detail should be equal to sum of Loan Amounts of all Asset(vehicle).");
			DisButClass.prototype.EnbButMethod();
	}
	else if ('<%=request.getAttribute("status").toString()%>'=='3')
	{
			DisButClass.prototype.DisButMethod();
			var msg="Asset cost at Loan Details Screen should be equal to Sum of Value of all Assets displayed in Asset/Collaterals Screen. \nLoan Amount at Loan Detail should be equal to sum of Loan Amounts of all Asset(vehicle).";
			alert(msg);
			DisButClass.prototype.EnbButMethod();
	}
	else if ('<%=request.getAttribute("status").toString()%>'=='LNASTNTMATMAR')
	{
			DisButClass.prototype.DisButMethod();
			var msg="Sum of Margin Amount and Loan Amount should be equal to Asset Cost at Loan Details Screen.";
			alert(msg);
			DisButClass.prototype.EnbButMethod();
	}
	else if ('<%=request.getAttribute("status").toString()%>'=='VEHICLECT')
	{
			DisButClass.prototype.DisButMethod();
			var msg="Captured Make Model For Vehicle Class In Asset/Collaterals Is Not Mapped With Selected Product And Scheme.";
			alert(msg);
			DisButClass.prototype.EnbButMethod();
	}
	

	else if('<%=request.getAttribute("status").toString()%>'=='CP16')
	{
		alert("<bean:message key="lbl.saveInsuranceDetailsThenForward" />");
		
		DisButClass.prototype.EnbButMethod();
		
	}
	else if('<%=request.getAttribute("status").toString()%>'=='sumPerct')
	{
		alert("<bean:message key="lbl.sumPerct" />");
		
		DisButClass.prototype.EnbButMethod();
		
	}
	
	else if('<%=request.getAttribute("status").toString()%>'=='sumPerctForParamN')
	{
		var insuranceflag="Y";
		if (confirm("<bean:message key="lbl.sumPerctForParamN" />"))
			{
			location.href='<%=request.getContextPath()%>/stageMoveBehindAction.do?insuranceflag='+insuranceflag; 
			}
	
	}
	else if('<%=request.getAttribute("status").toString()%>'=='custStatus')
	{
		alert("First Fill All Mandatory Fields on Custmer Details.");
	
		DisButClass.prototype.EnbButMethod();
		
	}
	else if('<%=request.getAttribute("status").toString()%>'=='custAddStatus')
	{
		alert("First Fill All Mandatory Fields on Custmer Address Details.");
		
		DisButClass.prototype.EnbButMethod();
		
	}
	else if('<%=request.getAttribute("status").toString()%>'=='relationStatus')
	{
		alert("Please capture Relationship Details in Customer Address Details Tab");
		
		DisButClass.prototype.EnbButMethod();
		
	}
</script>
</logic:present>
<logic:present name="custStatus">
	<script type="text/javascript">	
		alert("First Fill All Mandatory Fields on Custmer Details.");
	</script>
	</logic:present>
	<logic:present name="custAddStatus">
	<script type="text/javascript">	
		alert("First Fill All Mandatory Fields on Custmer Address Details.");
	</script>
	</logic:present>
<logic:present name="functionId">
<logic:equal name="functionId" value="3000206">
			<logic:present name="sms">
			 <script type="text/javascript">
				if('<%=request.getAttribute("sms").toString()%>'=='S')
				{
					alert("<bean:message key="lbl.stageSuccess" />");
				    parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=P';
			 
					
				}
				else if('<%=request.getAttribute("sms").toString()%>'=='E')
				{
					alert("<bean:message key="lbl.stageError" />");
					parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=P';
					
				}
				</script>
			</logic:present>
</logic:equal>
<logic:notEqual name="functionId" value="3000206">

		<logic:present name="sms">
			 <script type="text/javascript">
				if('<%=request.getAttribute("sms").toString()%>'=='S')
				{
					alert("<bean:message key="lbl.stageConfirm" />");
				    parent.location='<%=request.getContextPath()%>/qualityCheckAction.do?method=searchDealForQualityCheck';
			 
					
				}
				else if('<%=request.getAttribute("sms").toString()%>'=='E')
				{
					alert("<bean:message key="lbl.stageConfirmError" />");
					parent.location='<%=request.getContextPath()%>/qualityCheckAction.do?method=searchDealForQualityCheck';
					
				}
				</script>
			</logic:present>

</logic:notEqual>
</logic:present>
<!-- add by saorabh -->
<logic:present name="vatPercent">
 <script type="text/javascript">
 alert("<bean:message key="msg.checkVatPercent" />");

</script>
</logic:present>
<!-- end by saorabh -->
<%--                                    Start By Prashant                          --%>
<logic:present name="checkViabilityTab">
 <script type="text/javascript">
	 
	alert("<bean:message key="lbl.checkProductCatViability" />");
	
</script>
</logic:present>
<logic:present name="checkFleetTab">
 <script type="text/javascript">
	 
	alert("<bean:message key="lbl.checkProductCatFleet" />");
	
</script>
</logic:present>
<logic:present name="VEC">

	<script type="text/javascript">
	
	if("<%=request.getAttribute("VEC")%>"=="V")
	{
		alert("<bean:message key="msg.registrationNumber" />")
		
	}
	else if("<%=request.getAttribute("VEC")%>"=="E")
	{
		alert("<bean:message key="lbl.engineAlreadyExist1" />")
		
	}
	else if("<%=request.getAttribute("VEC")%>"=="C")
	{
		alert("<bean:message key="lbl.ChasisAlreadyExist1" />")
		
	}
	
		
</script>
</logic:present>

<%--                                    End By Prashant                          --%>

<!-- Start By Vishal -->

<logic:present name="approvedBy">

	<script type="text/javascript">
	
	if("<%=request.getAttribute("approvedBy")%>"=="BALTV")
	{
		var check = confirm("<bean:message key="msg.branchAmount" />")
		if(check)
			{
			var contextPath = document.getElementById('contextPath').value;
			document.getElementById("sourcingForm").action = contextPath+"/stageMoveBehindAction.do?checkDeal=true ";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			}
		else
			{
			DisButClass.prototype.EnbButMethod();
			}
		
	}
	else if("<%=request.getAttribute("approvedBy")%>"=="NALTV")
	{
		var check = confirm("<bean:message key="msg.nationalAmount" />")
		if(check)
			{
			var contextPath = document.getElementById('contextPath').value;
			document.getElementById("sourcingForm").action = contextPath+"/stageMoveBehindAction.do?checkDeal=true ";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			}
		else
			{
 			DisButClass.prototype.EnbButMethod();
			}
		
	}
	else if("<%=request.getAttribute("approvedBy")%>"=="HOALTV")
	{
		var check = confirm("<bean:message key="msg.headOfficeAmount" />")
		if(check)
			{
			var contextPath = document.getElementById('contextPath').value;
			document.getElementById("sourcingForm").action = contextPath+"/stageMoveBehindAction.do?checkDeal=true ";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			}
		else
			{
			DisButClass.prototype.EnbButMethod();
			}	
	}
	
	else if("<%=request.getAttribute("approvedBy")%>"=="BA")
	{
		var check = confirm("<bean:message key="msg.branchAmount" />")
		if(check)
			{
			var contextPath = document.getElementById('contextPath').value;
			document.getElementById("sourcingForm").action = contextPath+"/stageMoveBehindAction.do?checkDeal=true ";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			}
		else
			{
			DisButClass.prototype.EnbButMethod();
			}	
	}
	else if("<%=request.getAttribute("approvedBy")%>"=="NA")
	{
		var check = confirm("<bean:message key="msg.nationalAmount" />")
		if(check)
			{
			var contextPath = document.getElementById('contextPath').value;
			document.getElementById("sourcingForm").action = contextPath+"/stageMoveBehindAction.do?checkDeal=true ";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			}
		else
			{
			DisButClass.prototype.EnbButMethod();
			}	
	}
	else if("<%=request.getAttribute("approvedBy")%>"=="HOA")
	{
		var check = confirm("<bean:message key="msg.headOfficeAmount" />")
		if(check)
			{
			var contextPath = document.getElementById('contextPath').value;
			document.getElementById("sourcingForm").action = contextPath+"/stageMoveBehindAction.do?checkDeal=true ";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();

			}
		else
			{
			DisButClass.prototype.EnbButMethod();
			}
		
	}
       else if("<%=request.getAttribute("approvedBy")%>"=="NOLTV")
		{
		alert("<bean:message key="lbl.noAmount" />")
		}
		
</script>
</logic:present>

<!-- End By Vishal -->



</logic:notPresent>

<%--                                     View Deal                          --%>
 

<logic:present name="viewDeal">

	<html:form styleId="sourcingForm" action="/leadProcessAction" method="post" >

<FIELDSET>
<LEGEND><bean:message key="lbl.lead.detail"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
        
        <TR>
           <TD><bean:message key="lbl.leadNo"/><font color="red">*</font> </TD>

           <TD ><html:text property="leadNo" styleClass="text" value="${leadInfo[0].leadNo}" styleId="leadNo" disabled="true" tabindex="-1"/>
              <html:hidden  property="lbxLeadNo" styleId="lbxLeadNo" value="${leadInfo[0].leadNo}"/>
<!--           		<button type="button" name="lovlbxCaseType" class="topformbutton2"  onClick="return openLOV(1,'leadNo');"><img SRC="<%=request.getContextPath() %>/images/lov.gif"></button>-->
                       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>       

           </TD>
              <TD><bean:message key="lbl.customerName"/></TD>
           <TD ><html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" value="${leadInfo[0].customerName}" tabindex="-1"/></TD>
		</TR>
		
		 <tr>
          	<TD><bean:message key="lbl.dealNo"/></TD>
           <TD><html:text property="dealNo" styleClass="text" styleId="dealNo" readonly="true" value="${leadInfo[0].dealNo}" tabindex="-1"/></TD>
         
		</tr>
		 <TR>
          
           <TD><bean:message key="lbl.applicationFormNo"/><font color="red">*</font> </TD>
           <TD ><html:text property="applicationFormNo" styleClass="text" styleId="applicationFormNo" value="${sessionScope.leadInfo[0].applicationFormNo}" disabled="true" tabindex="-1"/></TD>
           <TD><bean:message key="lbl.dateOfReceipt" /><font color="red">*</font></TD>

           <TD ><html:text property="leadDate" styleClass="text3"  disabled="true"  value="${sessionScope.leadInfo[0].leadDate}"/>
           	    <html:text property="leadTime"  styleId="leadTime" onchange="validLeadTime();" maxlength="5"  styleClass="text5" value="${sessionScope.leadInfo[0].leadTime}" disabled="true" tabindex="-1"/>(24hrs Format)
           	       
           </TD>
		</TR>
        <tr>
        	<td><bean:message key="lbl.dateEncoded" /><font color="red">*</font></td>
        	<td>
        	<logic:notPresent name="leadInfo">
        	  <html:text property="dateEncoded" styleClass="text" disabled="true" styleId="dateEncoded" value="${userobject.businessdate }" disabled="true" tabindex="-1"/>
        	</logic:notPresent>
        	<logic:present name="leadInfo">
        	    <html:text property="dateEncoded" styleClass="text" disabled="true" styleId="dateEncoded" value="${sessionScope.leadInfo[0].dateEncoded}" disabled="true" tabindex="-1"/>
        	</logic:present>
        	
        	</td>
        	
        	<td><bean:message key="lbl.dealCat" /></td>
        	<td>
        	
        	  <html:select property="dealCat" styleId="dealCat" disabled="true" styleClass="text" value="${leadInfo[0].dealCat}"  >
          	  	<html:option value="" >--Select--</html:option>
          	  	<logic:present name="dealCatList">
          	  		<html:optionsCollection name="dealCatList" label="name" value="id" />
          	  	</logic:present>
          	  
          	  	</html:select>
        	        	
        	</td>
        	
        
        </tr>

        <TR>
        <TD><bean:message key="lbl.branch" /><font color="red">*</font></TD>
          <TD >
         
         <html:text property="branch" disabled="true" styleId="branch" tabindex="-1" value="${sessionScope.leadInfo[0].branch}" styleClass="text"/>
          
         <html:hidden  property="lbxBranchId" styleId="lbxBranchId" value="${sessionScope.leadInfo[0].lbxBranchId}" />

       
        </TD>
        
        <td><bean:message key="lbl.relationshipManager"/><font color="red">*</font></td>
          <TD>
  
      		    <html:text property="relationshipManager" disabled="true" styleId="relationshipManager" styleClass="text" tabindex="-1" value="${sessionScope.leadInfo[0].relationshipManager}" />
          		<html:hidden  property="lbxRelationship" styleId="lbxRelationship" value="${sessionScope.leadInfo[0].lbxRelationship}" />

         
          </TD>
        
          </TR>
          <tr>
		     
<!--		     <logic:present name="leadInfo">-->
<!--		    <logic:iterate name="leadInfo" id="obleadInfo">-->
<!--		    <logic:equal name="obleadInfo" property="source" value="VENDOR">-->
<!--		    	 <TD><bean:message key="lbl.vendor.code"/><font color="red">*</font></TD>-->
<!--		    	 <TD>-->
<!--		         -->
<!--		             <html:text property="vendorCode" readonly="true" styleClass="text" tabindex="-1" styleId="vendorCode" value="${leadInfo[0].vendorCode}" onkeypress="return isNumberKey(event);" disabled="true"/>-->
<!--		             -->
<!--		        </TD>-->
<!--		    </logic:equal>-->
<!--		    </logic:iterate>-->
<!--		    </logic:present>-->
<!--		    <logic:notPresent name="leadInfo">-->
<!--		     <TD><bean:message key="lbl.vendor.code"/><font color="red">*</font></TD>-->
<!--		     	<TD>-->
<!--		         -->
<!--		             <html:text property="vendorCode" readonly="true" styleClass="text" styleId="vendorCode" value="${leadInfo[0].vendorCode}" onkeypress="return isNumberKey(event);" tabindex="-1" disabled="true"/>-->
<!--		          </TD>-->
<!--		          </logic:notPresent>-->

					<td><bean:message key="lbl.leadgeneratorBy" /><span><font style="color: red;"></font></span></td>
					<html:hidden styleId="leadGeneratorBy" property="leadGeneratorBy" styleClass="text" value="${leadInfo[0].leadGeneratorBy}" />
					<td><html:text property="leadGeneratorByDesc" disabled="true" styleId="leadGeneratorByDesc" styleClass="text" value="${leadInfo[0].leadGeneratorByDesc}" tabindex="-1" /></td>		 
		      	  	<td><bean:message key="lbl.relationshipofficer"/></td>
          			<td><html:text property="generatedUser" readonly="true" styleId="generatedUser" styleClass="text" value="${leadInfo[0].generatedUser}" tabindex="-1" /></td>
		     </tr>
          
		    <TR>
		    <TD><bean:message key="lbl.sourceTypeDesc"/><font color="red">*</font></TD>
          <TD>
<!--               <html:select property="source" styleId="source" styleClass="text" value="${leadInfo[0].source}" onchange="return getVendor();" tabindex="-1" disabled="true">-->
<!--          	  	<html:option value="" >--Select--</html:option>-->
<!--          	  	<logic:present name="sourceTypeList">-->
<!--          	  		<html:optionsCollection name="sourceTypeList" label="name" value="id" />-->
<!--          	  	</logic:present>        	  	-->
<!--          	  -->
<!--          	  	</html:select>-->
          	  	
            	<html:text property="lovSourceDes" styleId="lovSourceDes" styleClass="text" readonly="true" tabindex="-1" value="${leadInfo[0].lovSourceDes}"></html:text>
				
          </TD>
		    <TD><bean:message key="lbl.source.desc"/><font color="red">*</font></TD>
		    <TD >
		       <html:text property="sourcedesc" styleClass="text" styleId="sourcedesc" value="${leadInfo[0].sourcedesc}" tabindex="-1" maxlength="50" tabindex="9" disabled="true"/>
		      </TD>
		    
		     </TR>
		    
		    

		            <tr>
		    <td><bean:message key="lbl.dealerExecutiveName" /></td>
		    <td><html:text styleId="dealerExecutive" property="dealerExecutive" styleClass="text" value="${leadInfo[0].dealerExecutive}" readonly="true" /></td>
		    
		    <td><bean:message key="lbl.dealerManagerName" /></td>
		    <td> <html:text property="dealerManager" styleId="dealerManager" styleClass="text" value="${leadInfo[0].dealerManager}" readonly="true" /></td> 
		    </tr>

		   
		
		    <!-- Nishant start -->
		    <tr>
		    	<td><bean:message key="lbl.referredBy" /></td>
		    	<td><html:text styleId="referredBy" property="referredBy" styleClass="text" value="${leadInfo[0].referredBy}" readonly="true"/></td>
		    </tr>
		    <!-- Nishant ends -->
		      <tr>	<td ><bean:message key="lbl.authorRemarks" /></td>
					<td >
					
					 <a rel="tooltip3" id="tool">
		          
		                <html:textarea property="remarks" styleId="remarks" styleClass="text" readonly="true" onmouseover="applyToolTip(id);" value="${creditApprovalList[0].remarks }"/>
						
				      </a>
						
					</td>
					<td><bean:message key="lbl.areaCode" /></td> 
			  		 <td><html:text property="areaCodename"  readonly="true" styleId="areaCodename" styleClass="text" readonly="true" value="${leadInfo[0].areaCodename}"/>
			   				
			  
			       </td>
			</tr>
			       <tr>
			          <td><bean:message key="lbl.rateApprovalRemark" /></td> 
		             <td>
		               <html:text property="rateApprovalRemark" styleId="rateApprovalRemark" styleClass="text" readonly="true" value="${leadInfo[0].rateApprovalRemark}"/>
		           </td>
		          <td ><bean:message key="lbl.fiAppraiserName" /></td>
		            <td >
		       			<html:text property="fiAppraiserName" styleId="fiAppraiserName" readonly="true" styleClass="text" maxlength="50" value="${leadInfo[0].fiAppraiserName}"/>                
		
					</td>
		          
		       </tr>	     
		      <tr>
		         <td><bean:message key="lbl.fidecisionDeal" /></td> 
			  		 <td>
			  		      
			   			<html:select styleId="fidecisionDeal" property="fidecisionDeal" disabled="true" styleClass="text" value="${leadInfo[0].fidecisionDeal}">
							<html:option value="">---Select---</html:option>
							<html:option value="P">POSITIVE</html:option>
							<html:option value="N">NEGATIVE</html:option>
							<html:option value="R">REFER</html:option>
					    </html:select>
			  
			       </td>
		          <td ><bean:message key="lbl.fiRemarksDeal" /></td>
		            <td >
		       			<textarea name="fiRemarksDeal" id="fiRemarksDeal" class="text" readonly="readonly" maxlength="450" >${leadInfo[0].fiRemarksDeal }</textarea>                
		
					</td>
			</tr>
			  <tr>
		          <td ><bean:message key="lbl.makerRemark" /></td>
		            <td >
		       			<textarea name="makerRemark" id="makerRemark" readonly="readonly" class="text" maxlength="450" >${leadInfo[0].makerRemark }</textarea>                
		
					</td>
					<td ><bean:message key="lbl.caseVisitedBy" /></td>
		            <td >
		       			<html:text property="caseVisitedBy" styleId="caseVisitedBy" readonly="readonly" styleClass="text" maxlength="50" value="${leadInfo[0].caseVisitedBy}"/>                
		
				   </td>
		     
		     </tr>
		       
		  </TABLE></TD></TR>
 
   
    </TBODY></TABLE>
 
             <button type="button" name="button" id="leadButton" title="Alt+L" accesskey="L" class="topformbutton2" onclick="return viewLeadDetails();"><bean:message key="button.leadview" /></button>
</FIELDSET> 
</html:form>

<%--                                    Start By Prashant                          --%>
<logic:present name="checkViabilityTab">
 <script type="text/javascript">
	 
	alert("<bean:message key="lbl.checkProductCatViability" />");
	
</script>
</logic:present>
<logic:present name="checkFleetTab">
 <script type="text/javascript">
	 
	alert("<bean:message key="lbl.checkProductCatFleet" />");
	
</script>
</logic:present>

<%--                                    End By Prashant                          --%>
</logic:present>

</div>
</div>
<script>
	setFramevalues("sourcingForm");
</script>

  </body>
  </html:html>

  
  
  
 
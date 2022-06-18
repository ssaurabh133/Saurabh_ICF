<!--  
Author Name:- Manisha Tomar
Date of Creation:- 14/04/2011
Purpose:-  The purpose of this page is to enter notes and follow up Remarks.
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
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 
		
		<link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>

	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/scoring.js"></script>
		   <script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
	
	
	
	<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>

	</head>
<body onload="enableAnchor();" oncontextmenu="return false">
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<div id="centercolumn">
	
	<div id="revisedcontainer">

	<html:form styleId="scoringForm" action="/scoringProcessAction" method="post">
    
<fieldset>	  
<legend><bean:message key="lbl.applicantDetails" /></legend>

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		    	<logic:present name="scoringList">
		        	<tr>
		        		
		            	<td><bean:message key="lbl.dealNo" /></td>
		               	<td>
		               	<input type="hidden" name="scoringID" id="scoringID" value="${requestScope.scoringList[0].scoringID}"/>
					<html:text styleClass="text" property="dealNo" styleId="dealNo" value="${requestScope.scoringList[0].dealNo}" maxlength="10" readonly="true" />
				     <html:hidden property="lbxDealNo" styleId="lbxDealNo" value="${requestScope.scoringList[0].lbxDealNo}"/>
					<!--   <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(184,'commonForm','dealNo','','', '','','','customername')"
						value=" " styleClass="lovbutton"></html:button>-->
											</td>
			
	                  <td><bean:message key="lbl.customerName"></bean:message></td>
	                  <td >
                   	<html:text property="customerName"  styleClass="text" styleId="customerName" value="${requestScope.scoringList[0].customerName}" maxlength="50" readonly="true"></html:text>
	                <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	                </td>
					</tr>
					<tr>
	  	        	<td>
					<bean:message key="lbl.product" /></td>
					<td>
					<html:text styleClass="text" property="product"
					styleId="product" value="${requestScope.scoringList[0].dealProduct}" maxlength="50" readonly="true" />
					<html:hidden property="lbxProductID" styleId="lbxProductID"
					value="" />
				<!-- 	<input type="hidden" id="productId" name="productId" value="" />
					<html:button property="productButton" style="productButton"
					onclick="closeLovCallonLov1();openLOVCommon(209,'commonForm','product','','scheme','lbxscheme','','','productId')"
					value=" " styleClass="lovbutton"></html:button> -->
				   </td>
				  
					<td>
					<bean:message key="lbl.scheme" /></td>
					<td>
					<html:text styleClass="text" property="scheme"
					styleId="scheme" value="${requestScope.scoringList[0].dealScheme}" maxlength="50" readonly="true" />
					<html:hidden property="lbxscheme" styleId="lbxscheme" value="" />
					<input type="hidden" id="schemeId" name="schemeId" value="" />
				<!-- 	<html:button property="schemeButton" style="schemeButton"
					onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');"
					value=" " styleClass="lovbutton"></html:button>-->
					</td>
					</tr> 
	                 <tr>
	                 <td><bean:message key="lbl.loanAmount"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="loanAmount" styleClass="text" styleId="loanAmount" value="${requestScope.scoringList[0].loanAmount}" readonly="true" 
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
			
			 </div></td>
			  <td><bean:message key="lbl.loanTenure"></bean:message> </td>
			         <td >			         
                   	<html:text property="loanTenure"  styleClass="text" styleId="loanTenure" value="${requestScope.scoringList[0].loanTenure}" maxlength="50" readonly="true"></html:text>
	                </td>	                
	                 </tr>
	                 <tr>
	                  <td><bean:message key="lbl.loanPurpose"></bean:message> </td>
			         <td >			         
                   	<html:text property="loanPurpose"  styleClass="text" styleId="loanPurpose" value="${requestScope.scoringList[0].loanPurpose}" maxlength="50" readonly="true"></html:text>
	                </td>	                
	         
	                 </tr>
	                 
	
		</logic:present>
	</table>
	</td>
	</tr>
	</table>
</fieldset>
	


<fieldset>
<legend><bean:message key="lbl.scoringDetails" /></legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td><strong><bean:message key="lbl.pramName"/></strong></td>
      					<td><strong><bean:message key="lbl.paramValue"/></strong></td>
        				<td><strong><bean:message key="lbl.paramScore"/></strong></td>
						<td><strong><bean:message key="lbl.paramWeightage"/></strong></td>
						<td><strong><bean:message key="lbl.weightageScore"/></strong></td>				
    				</tr>
    				
    				
	  				<logic:present name="scoringList">
	  				<logic:notEmpty name="scoringList">
			 			<logic:iterate name="scoringList" id="sublist">
						<tr class="white1">
			     			<td>${sublist.pramName}</td>
			     			<td>${sublist.paramValue}</td>
				 			<td>${sublist.paramScore}</td>
				 			<td>${sublist.paramWeightage}</td>
				 			<td>${sublist.weightageScore}</td>
				 		
		      			</tr>
		    			</logic:iterate>
	    			</logic:notEmpty>
	    			<logic:empty name="scoringList">
							<script type="text/javascript">
							
							        alert('No Data Found');
							        self.close(); 
						                 
						   	 </script>
                     </logic:empty>
	   				</logic:present>
	   		
 				</table>
			</td>
		</tr>
	</table>

</fieldset>

<logic:notPresent name="viewMode">
 <fieldset>

          <legend><bean:message key="lbl.decision"/></legend>
           <table width="100%"  border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td>
             <table cellspacing="1" cellpadding="1" border="0" width="100%">
	           <tr>
           
	<logic:present name="scoringList">
		        	<tr>
		            	<td><bean:message key="lbl.decision" /><font color="red">*</font></td>
		               <td><span style="float:left;">
		               <html:select property="decision" styleId="decision" styleClass="text" value="${requestScope.scoringList[0].decision}" >
				       <html:option value="P">POSITIVE</html:option> 
		               <html:option value="N">NEGETIVE</html:option> 
				       </html:select>
		               </span></td>
		               
				     <td><bean:message key="lbl.remark"></bean:message></td>
	                  <td >
	                <textarea name="remark" class="text" id="remark"  rows="5"  cols="50">${requestScope.scoringList[0].remark}</textarea>
                   
	                 </td>
                     </tr>
     </logic:present>  
     </tr>
     </table>
     </td>
     </tr>
     </table>
     <tr>
	<td>
	 <button type="button" class="topformbutton2" id="save" onclick="return onSaveofScoring();" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	</td>
	</tr>
     </fieldset>
     </logic:notPresent>
   
	
</html:form>
</div>
</body>
</html:html>

<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.updateSuccess" />");
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.errorSuccess" />");
	}
</script>
</logic:present>
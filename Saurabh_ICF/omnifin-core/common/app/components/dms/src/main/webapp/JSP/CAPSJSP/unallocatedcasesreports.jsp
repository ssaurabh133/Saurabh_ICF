<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<title>Reports</title>
		
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
			<script type="text/javascript"
			src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/unallocatedreport.js"></script>
		
		<style type="text/css">
		
			.white {
			background:repeat-x scroll left bottom #FFFFFF;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
			.white2 {
			background:repeat-x scroll left bottom #CDD6DD;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
		</style>
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
	
	
	
	<body onload="enableAnchor();">
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
				<html:form action="/unallocatedReports" styleId="unallocatedForm" >
					<fieldset>
						<legend><bean:message key="lbl.unAllocatedCase"/></legend>
							<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
							
							
    <tr>	<td><bean:message key="lbl.reportformat" /></td>
									<td>
										<html:select property="reportformat" styleClass="text">
											<html:option value="P">PDF</html:option>
											<html:option value="E">EXCEL</html:option>
											<html:option value="H">HTML</html:option>
						
										</html:select> 
									</td>
								</tr>
								<tr>	
						
  
     <td ><bean:message key="lbl.loanAccountNo" /></td>
     <td>  <html:text styleClass="text" property="loanno" styleId="loanno"   readonly="true" />
    <html:button property="loanSearchButton" styleId="loanSearchButton" onclick="openLOVCommon(195,'reallocationCollForm','loanno','','', '','','','lbxLoanno');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
<html:hidden property="lbxLoanno" styleId="lbxLoanno"  />
     </td>
    <td ><bean:message key="lbl.product" /></td>
     
     
      <td><html:select  property="product" styleClass="text"  styleId="product"  >
       <html:option value="" >--Select--</html:option>
           <logic:present name="productList">
     <logic:notEmpty  name="productList">
       <html:optionsCollection name="productList" label="productval" value="productid" />
     </logic:notEmpty>
       </logic:present>
       </html:select>
     </td>
      <td ><bean:message key="lbl.customername"/></td>

        <td>  <html:text property="customername" styleClass="text"  styleId="customername" maxlength="50"  />
      
     </td>
     
     
     </tr>
    
    <tr>
      <td><bean:message key="lbl.DPD1" /></td>
   
      <td><html:text property="dpd2" styleClass="text" styleId="dpd2" style="text-align: right"
										maxlength="5" onkeypress="return isNumberKey(event);" />
  
     </td>
    <td><bean:message key="lbl.DPD2" /></td>
      <td>
<html:text property="dpd1" styleClass="text" styleId="dpd1" style="text-align: right"
										maxlength="5" onkeypress="return isNumberKey(event);" />
  
     </td>
      
    <td ><bean:message key="lbl.queue" /></td>
     <td><html:text property="queue" styleClass="text"  styleId="queue" maxlength="30"  readonly="true"  />

      <html:button property="queueButton" styleId="queueButton" onclick="openLOVCommon(189,'reallocationCollForm','queue','','', '','','','lbxQueuesearchId')" value=" " styleClass="lovbutton"></html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxQueuesearchId" styleId="lbxQueuesearchId" />
     </td>
     </tr>
    
    <tr>
    <td><bean:message key="lbl.overDueAmount1" /></td>
      
      <td>
<html:text property="pos2" styleClass="text" styleId="pos2" style="text-align: right"
										maxlength="18" onkeyup="checkNumber(this.value, event, 3,id);"
										onblur="formatNumber(this.value,id);"
										onkeypress="return numbersonly(event,id,3)" />
  
     </td>
   <td><bean:message key="lbl.overDueAmount2" /></td>
      <td><html:text property="pos1" styleClass="text" styleId="pos1" style="text-align: right"
										maxlength="18" onkeyup="checkNumber(this.value, event, 3,id);"
										onblur="formatNumber(this.value,id);"
										onkeypress="return numbersonly(event,id,3)" />

      </td>
    
     
    
     
         <td ><bean:message key="lbl.Type" /></td>
       <td >   <html:select  property="custype" styleClass="text"  styleId="custype" >
       <html:option value="" >--Select--</html:option>
              <html:option value="I" >INDIVIDUAL</html:option>
              <html:option value="C" >CORPORATE</html:option>
       </html:select>
      </td>
       
      </tr>
    
    <tr>
         <td ><bean:message key="lbl.Category" /></td>
       <td> <html:select  property="custcategory" styleClass="text"  styleId="custcategory" >
       <html:option value="" >--Select--</html:option>
     
       <logic:present name="customercatList">
     <logic:notEmpty  name="customercatList">
       <html:optionsCollection name="customercatList" label="cstcat" value="cstcatval" />
     </logic:notEmpty>
       </logic:present>
           
       </html:select>
      </td>
         
<!--      <td ><bean:message key="lbl.datefrom" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td> 
       <td>
       <html:text property="fromdate" tabindex="8"  styleClass="text"  styleId="fromdate"  onchange="return checkDate('assignfrom');"/>
         </td>  
         
          <td ><bean:message key="lbl.dateto" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
       <td>
        <html:text property="todate" tabindex="8"  styleClass="text"  styleId="todate"  onchange="return checkDate('assignto');"/>
    
      </td> -->
       <td ><bean:message key="lbl.balancePrincipal" /></td>
       <td><html:text property="balanceprincipal" styleClass="text" style="text-align: right"
										styleId="balanceprincipal" maxlength="18"
										onkeyup="checkNumber(this.value, event, 3,id);"
										onblur="formatNumber(this.value,id);"
										onkeypress="return numbersonly(event,id,3)" />
      </td>
     </tr>
      <tr>
						<td>
									<bean:message key="lbl.productCategory" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="producTCategory" styleId="producTCategory" 
										onchange="disableCal(reportName.value)" styleClass="text" value="select">
										<option value="All">
											--
											All
											--
										</option>
										<logic:present name="productlist">
											<logic:notEmpty name="productlist">
												<html:optionsCollection name="productlist"
													label="producTCategoryID" value="producTCategory" />
											</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
								<td>
									<bean:message key="lbl.branch" />
									<html:hidden property="contextPath" styleId="contextPath"
										value="<%=request.getContextPath() %>" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="branch" styleClass="text"
										onchange="hideLovofBranch()" styleId="branch">
										<html:option value="Specific">Specific</html:option>
										<html:option value="All">All</html:option>
										</html:select>
								</td>
								</tr>
							
   
     <tr>
    <td >
									<bean:message key="lbl.branchname" />
									<font color="red">*</font>
								</td>
								<td>
								<select id="branchid"  name="branchid" size="5" multiple="multiple"  value="" style="width: 150px"></select>
									<html:hidden property="lbxBranchId" styleId="lbxBranchId"
										value="" />
									<html:button property="dealButton" styleId="dealButton"
										tabindex="1"
										onclick="return openMultiSelectLOVCommon(2075,'reportid','branchid','','', '','','','lbxBranchId');"
										value=" " styleClass="lovbutton" >
									</html:button>
								</td>
								
								<td>
									<bean:message key="lbl.loanClassification" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="loanClassification" styleId="loanClassification" 
										onchange="disableCal(reportName.value)" styleClass="text" value="select">
										<option value="All">
											--
											All
											--
										</option>
										<logic:present name="loanClasslist">
											<logic:notEmpty name="loanClasslist">
												<html:optionsCollection name="loanClasslist"
													label="producTCategoryID" value="producTCategory" />
											</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
					
     </tr>
     <tr>
     
									<td >
									<!--<html:button property=" mybutton" styleId="show" value="Show"  styleClass="topformbutton2" onclick="checkValidation();"  />-->
									<button type="button" class="topformbutton2" name="mybutton"  onclick="checkValidation();" 
 							id="show" title="Alt+W" accesskey="W"  tabindex="-1" ><bean:message key="button.show" /></button>
									</td>
								</tr>
								
								
								
				
		
							<!--  <tr>
							
						            <td align="left" colspan="2" class="form2"><html:button property=" mybutton"  value="pdfReport"  styleClass="topformbutton2" onclick="checkValidation();"   /></td>
						        
					
									
								</tr>
								<tr>
							
						            <td align="left" colspan="2" class="form2"><html:button property="mybutton"   value="excelReport"  styleClass="topformbutton2" onclick="checkValidation();"   /></td>
						        
					
									
								</tr>-->
							<!--  	<tr>
								<td><button type="button" name="mybutton" value="pdfReport"  class="topformbutton2" onclick="checkValidation();">Show PDF </button></td>
								</tr>
								<tr>
								<td><button type="button" name="mybutton" value="excelReport"  class="topformbutton2" onclick="checkValidation();">Show Excel</button></td>
								</tr>
								<tr>
								<td><button type="button" name="mybutton" value="htmlReport"  class="topformbutton2" onclick="checkValidation();">Show HTML</button></td>
								</tr>-->
				
				
						</table>
		
					</fieldset>
				</html:form>
			</div>
		</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
	</body>
</html>
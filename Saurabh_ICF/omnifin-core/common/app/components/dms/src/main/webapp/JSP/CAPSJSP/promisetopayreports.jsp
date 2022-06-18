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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

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
	
	<script type="text/javascript">
	function actionCodeValue()
	{
		document.getElementById('lastActionCode').value="PTP";
		
	}
	</script>
	</head>
	
	
	
	<body onload="enableAnchor();loadJSP()">
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
				<html:form action="/promisereport" styleId="promisetoPayForm" >
					<fieldset>
						<legend>Report Parameter Form</legend>
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
						
  
    <!--  <td ><bean:message key="lbl.loanAccountNo" /></td>
     <td>  <html:text styleClass="text" property="loanno" styleId="loanno"   readonly="true" />
    <html:button property="loanSearchButton" styleId="loanSearchButton" onclick="openLOVCommon(195,'reallocationCollForm','loanno','','','lastActionCode','','','lbxLoanno');closeLovCallonLov1();actionCodeValue();" value=" " styleClass="lovbutton"> </html:button>
	<html:hidden property="lbxLoanno" styleId="lbxLoanno"  />
	<html:hidden property="lastActionCode" styleId="lastActionCode" value="PTP" />

     </td> -->
     <td>
									<bean:message key="lbl.loanAccountNo" />
								</td>
								<td>
									<html:text styleClass="text" property="loanno" styleId="loanno"
										readonly="true" tabindex="-1"/>
									<html:button property="loanSearchButton"
										styleId="loanSearchButton"
										onclick="openLOVCommon(195,'reallocationCollForm','loanno','','', 'lastActionCode','','','lbxLoanno');closeLovCallonLov1();actionCodeValue();"
										value=" " styleClass="lovbutton">
									</html:button>
									<html:hidden property="lbxLoanno" styleId="lbxLoanno" />
									<html:hidden property="lastActionCode" styleId="lastActionCode" value="PTP" />
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
     <td><bean:message key="lbl.DPD2" /></td>
      <td><html:text property="dpd2" styleClass="text"  styleId="dpd2" maxlength="5"  />
  
     </td>
    <td><bean:message key="lbl.DPD1" /></td>
      <td><html:text property="dpd1" styleClass="text"  styleId="dpd1" maxlength="5"     />
  
     </td>
      
    <td ><bean:message key="lbl.queue" /></td>
     <td><html:text property="queue" styleClass="text"  styleId="queue" maxlength="30"  readonly="true"  />

      <html:button property="queueButton" styleId="queueButton" onclick="openLOVCommon(189,'reallocationCollForm','queue','','', '','','','lbxQueuesearchId')" value=" " styleClass="lovbutton"></html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxQueuesearchId" styleId="lbxQueuesearchId" />
     </td>
     </tr>
    
    <tr>
 
       <td><bean:message key="lbl.overDueAmount2" /></td>
      <td><html:text property="pos2" styleClass="text"  styleId="pos2"  maxlength="18"  />
  
     </td>
     <td><bean:message key="lbl.overDueAmount1" /></td>
      <td><html:text property="pos1" styleClass="text"  styleId="pos1"  maxlength="18"  />
      </td>
    
      <td><bean:message key="lbl.iduser" /></td>
    <td> <html:text property="user" styleClass="text"  styleId="user"  readonly="true"  />
    <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(286,'AllocationMasterSearchForm','user','','', '','','','lbxUserSearchId')"></html:button>  
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId"  />
     </td>
     </tr>
    
    <tr>
      <td><bean:message key="lbl.tos2" /></td>
      <td><html:text property="tos2" styleClass="text"  styleId="tos2"  maxlength="18"  />
  
     </td>
  <td><bean:message key="lbl.tos1" /></td>
      <td><html:text property="tos1" styleClass="text"  styleId="tos1"  maxlength="18"  />
  
     </td>
    
         
      <td ><bean:message key="lbl.npaStage" /></td>
       <td ><html:select  property="npastage"  styleClass="text"   styleId="npastage"  >
       <html:option value="" >--Select--</html:option>
           <logic:present name="npastageList">
     <logic:notEmpty  name="npastageList">
       <html:optionsCollection name="npastageList" label="npastageid" value="npastageval" />
     </logic:notEmpty> 
       </logic:present>
       </html:select>
      </td>
      </tr>
    
    <tr>
         <td ><bean:message key="lbl.Type" /></td>
       <td >   <html:select  property="custype" styleClass="text"  styleId="custype" >
       <html:option value="" >--Select--</html:option>
              <html:option value="I" >Individual</html:option>
              <html:option value="C" >Customer</html:option>
       </html:select>
      </td>
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
       <td ><bean:message key="lbl.balancePrincipal" /></td>
       <td><html:text property="balanceprincipal" styleClass="text"  styleId="balanceprincipal"   maxlength="18" />
      </td>
      
      </tr>
    
    <tr>
    
     <td ><bean:message key="lbl.datefrom" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
       <td>
       <html:text property="fromdate" tabindex="8"  styleClass="text"  styleId="fromdate"  onchange="return checkDate('assignfrom');"/>
 </td>  <td ><bean:message key="lbl.dateto" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
       <td>
        <html:text property="todate" tabindex="8"  styleClass="text"  styleId="todate"  onchange="return checkDate('assignto');"/>
    
      </td>
     </tr>
     
									<td >
									<!--<html:button property=" mybutton"  value="SHOW"  styleClass="topformbutton2" onclick="checkValidation();"  />
									-->
									
<button type="button" class="topformbutton2" name="mybutton"  onclick="checkValidation();" 
 							 title="Alt+W" accesskey="W"  ><bean:message key="button.show" /></button>
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
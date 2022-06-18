<%@page import="java.util.ResourceBundle"%>
<%@page import="com.cp.vo.UnderwritingDocUploadVo"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>


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
	    <meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval()%>;url= <%=request.getContextPath()%>/logoff.do" />
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
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/applicant.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/upload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script> 	
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
 
 </head>
	
<body onclick="parent_disable();" oncontextmenu="return false" onload="enableAnchor();checkChanges('documentUploadMaker');document.getElementById('documentUploadMaker').applicantType.focus();" onunload="closeAllWindowCallUnloadBody();closeAllWindowCallUnloadBodyAn();" >   
<input type="hidden" name="<%= org.apache.struts.taglib.html.Constants.TOKEN_KEY %>" value="<%= session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>" >

      <%
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
			request.setAttribute("no",no);

		%>


<%-- Div for screen Saver (Calender picker)--%>

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
		<input type="hidden" name="financialYear" id="financialYear" value="${financialYear}" />

		<div id=centercolumn>
		<div id=revisedcontainer>
		<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" >
	

	
	
<html:form action="/documentUploadSearch" method="post" styleId="documentUploadMaker" enctype="multipart/form-data">

		<fieldset>
			<legend><bean:message key="lbl.Docmaker"></bean:message></legend>
			<table width="100%"  border="0" cellspacing="5" cellpadding="2"  >
				
				<tr>
					<td width="22%"><bean:message key="lbl.dealNo"/><font color="red">*</font></td>
					<td >
						<logic:notPresent name="uploadDoc">
						<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
						<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
						<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
						<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(6514,'documentUploadMaker','dealNo','userId','dealNo', 'userId','','','customerName','financialYear');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
				        </logic:notPresent>
		   
	      				<logic:present name="uploadDoc">
	      				
						<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
						<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" value="${uploadDoc[0].custRef}" />
						
						<html:hidden  property="lbxDealNo" styleId="lbxDealNo"  value="${uploadDoc[0].dealId}" />
						<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(6514,'documentUploadMaker','dealNo','userId','dealNo', 'userId','','','customerName','financialYear');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
						
						</logic:present>
	      
	      				<%-- <logic:present name="List3">
	      				<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${List3}" />
	      				</logic:present> --%>
	  				</td>
	  				
		 			<td  width="18%" ><bean:message key="lbl.customerName"/><span><font color="red">*</font></span></td>
		  			<logic:notPresent name="uploadDoc">
					<td><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="50" readonly="true" onchange="upperMe('customerName');"/></td> 
			     	</logic:notPresent>
				
					<logic:present name="uploadDoc">
					<td><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="50" readonly="true" value="${uploadDoc[0].customerName}" onchange="upperMe('customerName');"/></td>
					</logic:present>
			
	    		</tr>
	    		
	    				
	
				
	  
	 
	  
  <%String value = ""; %>
	  <logic:present name="uploadDoc">
	  <logic:notEmpty name="uploadDoc">
  <%
	  ArrayList  list= (ArrayList)request.getAttribute("uploadDoc");
	  System.out.println(list+"===");
	  Iterator iterator = list.iterator();
	  while(iterator.hasNext())
		{
			UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)iterator.next();
			value = value+vo.getDocDescription()+"|";
			System.out.println(value);
		}
							
 %>
	</logic:notEmpty>
	</logic:present>
 	   				
		<tr>   
   			<td>Document Type<font color="red">*</font></td>
   			<logic:notPresent name="uploadDoc">
   			<td>
    		<html:select  property="docType" styleClass="text"  value = "" styleId="docType" onchange="hideAsterik(value)"  >
		    <html:option value="" >--Select--</html:option>
		    <logic:present name="docType">
		    <logic:notEmpty name="docType">
		    <html:optionsCollection name="docType" label="docTypeDesc" value="docTypeCode" />
		    </logic:notEmpty>
		    </logic:present>
		    </html:select>
    		</td>
    		</logic:notPresent>
    		
    		<logic:present name="uploadDoc">
    		<td>
    		<html:select  property="docType" styleClass="text"  styleId="docType" onchange="hideAsterik(value)" value="" >
		    <html:option value="" >--Select--</html:option>
		    <logic:present name="docType">
		    <logic:notEmpty name="docType">
		    <html:optionsCollection name="docType" label="docTypeDesc" value="docTypeCode" />
		    </logic:notEmpty>
		    </logic:present>
		    </html:select>
    		</td>
    		</logic:present>
       		
       		<td  width="18%" ><bean:message key="lbl.docFormat"/><span><font color="red">*</font></span></td>
    		<td>
    		<logic:notPresent name="uploadDoc">
    		<html:select  property="formatType" styleClass="text"  value = "" styleId="formatType"  >
       		<html:option value="" >--Select--</html:option>
            <logic:present name="formatType">
            <logic:notEmpty name="formatType">
            <html:optionsCollection name="formatType" label="formatTypeDesc" value="formatTypeCode" />
            </logic:notEmpty>
            </logic:present>
            </html:select>
            </logic:notPresent>
          
            <logic:present name="uploadDoc">
            <html:select  property="formatType" styleClass="text"  styleId="formatType" value="${uploadDoc[0].docFormat}" >
       		<html:option value="" >--Select--</html:option>
            <logic:present name="formatType">
            <logic:notEmpty name="formatType">
            <html:optionsCollection name="formatType" label="formatTypeDesc" value="formatTypeCode" />
            </logic:notEmpty>
            </logic:present>
            </html:select>
			</logic:present>
            </td>
        </tr>
    				
    	<tr>
    		 <td><bean:message key="lbl.docEntity"/><span><font color="red">*</font></span></td>
 			 <logic:notPresent name="uploadDoc">
 			<td>
 			<html:select property="docEntity" styleId="docEntity" styleClass="text"  onchange="getCustomerName();" value=""><!-- onchange="getlist();getChageClass();" > -->
	        <html:option value="" >--<bean:message key="lbl.select" />--</html:option>
	        <logic:present name="docEntity">
	        <html:optionsCollection name="docEntity" label="entityDescription" value="entityValue" />
	        </logic:present>	         	  	
	  	  	</html:select>
	  	  	</td>	    		
    		</logic:notPresent>
    			 
    		<logic:present name="uploadDoc">
     		<td>
			<html:select property="docEntity" styleId="docEntity" styleClass="text"  onchange="getCustomerName();"  value=""><!-- onchange="getlist();getChageClass();" > -->
	        <html:option value="" >--<bean:message key="lbl.select" />--</html:option>
	        <logic:present name="docEntity">
	        <html:optionsCollection name="docEntity" label="entityDescription" value="entityValue" />
	        </logic:present>	         	  	
	  	  	</html:select>
	        </td>	  
           	</logic:present> 	 		
        		
        	<td  width="18%" ><bean:message key="lbl.entityCustomer"/><span><font color="red">*</font></span></td>
        	<td>
        	<div id="customerDiv">
			<logic:notPresent name="uploadDoc">
			<html:select property="entityCustomerId" styleId="entityCustomerId" styleClass="text"  ><!-- onchange="getlist();getChageClass();" > -->
	        <html:option value="" >--<bean:message key="lbl.select" />--</html:option>
	        <logic:present name="entityCustomerList">
	        <html:optionsCollection name="entityCustomerList" label="customerDesc" value="customerCode" />
	        </logic:present>	         	  	
	  	  	</html:select>
	  	  	</logic:notPresent>
	  	  			
	  	  	<logic:present name="uploadDoc">
	  	  	<html:select property="entityCustomerId" styleId="entityCustomerId" styleClass="text"  value="${uploadDoc[0].customerName}" ><!-- onchange="getlist();getChageClass();" > -->
	        <html:option value="" >--<bean:message key="lbl.select" />--</html:option>
	        <logic:present name="entityCustomerList">
	        <html:optionsCollection name="entityCustomerList" label="customerDesc" value="customerCode" />
	        </logic:present>	         	  	
	  	  	</html:select>
	  	  	</logic:present>
	  	  	</div>
        	</td>
        </tr>	
        		
        <tr>
        <td>
			<bean:message key="lbl.accountNo" />
			<font color="red">*</font>
			</td>
			<td nowrap="nowrap">
			<logic:notPresent name="uploadDoc">
			<html:text styleClass="text" property="accountNo" styleId="accountNo" value=""	maxlength="20" style="text-align: right;" onkeypress="return isNumberKey(evt);" />
			</logic:notPresent>		
			<logic:present name="uploadDoc">
			<html:text property="accountNo" styleClass="text" styleId="accountNo" maxlength="100" readonly="false" tabindex="-1" value="${uploadDoc[0].accountNo}" />
			</logic:present>
			</td>
         	<%-- <td ><bean:message key="lbl.docDescription"/><font color="red">*</font></td>
		 	<logic:notPresent name="uploadDoc">
		 	<td  valign="top"> 
			<input type="text" name="docDes" id="docDes" value="<%=value %>"  readonly="readonly" class="text" tabindex="-1" />
			</td>  
	        </logic:notPresent>
	       	 
	       	<logic:present name="uploadDoc">
	       	<td  valign="top"> 
			<input type="text" name="docDes" id="docDes" value="${uploadDoc[0].docDes}"  readonly="readonly" class="text" tabindex="-1" />
			</td>  
	       	</logic:present> --%>
	       	 
	       	 
	       	 
    		
	       	 
	        <td  width="18%" >
			<bean:message key="lbl.aCType" />
			<font color="red">*</font>
			</td>
			<logic:notPresent name="uploadDoc">
			<td colspan="4" nowrap="nowrap">
			<html:select property="accountType" styleId="accountType" styleClass="text" value="" onchange="return odocChange();" onclick="return allowNegativeValue('F');">
			<html:option value="">--Select--</html:option>
			<logic:present name="accountType">
			<logic:notEmpty name="accountType">
			<html:optionsCollection name="accountType" label="description" value="value" />
			</logic:notEmpty>
			</logic:present>
			</html:select>
			</td>
			</logic:notPresent>
			
			<logic:present name="uploadDoc">
			<td colspan="4" nowrap="nowrap">
			<html:select property="accountType" styleId="accountType" styleClass="text" value="${uploadDoc[0].accountType}"  onchange="return odocChange();" onclick="return allowNegativeValue('F');">
			<html:option value="">--Select--</html:option>
			<logic:present name="accountType">
			<logic:notEmpty name="accountType">
			<html:optionsCollection name="accountType" label="description" value="value" />
			</logic:notEmpty>
			</logic:present>
			</html:select>
			</td>
			</logic:present>
		</tr>
			
		<tr>
			<td> 
			<bean:message key="lbl.name" />
			<font color="red">*</font>
			</td>
			<logic:notPresent name="uploadDoc">
			<td>
			<input type="hidden" id="hCommon" value="" />
			<html:text property="bankName" styleId="bankName" styleClass="text" maxlength="50" value="${bankAcAnalysisDetail[0].bankName}" readonly="true" tabindex="-1" />
			<html:hidden property="lbxBankID" styleId="lbxBankID" value="${bankAcAnalysisDetail[0].lbxBankID}" />
			<html:button property="bankButton" styleId="bankButton" value=" " onclick="closeLovCallonLov1();openLOVCommon(7,'documentUploadMaker','bankName','','bankBranch', 'lbxBranchID','','','hCommon')" styleClass="lovbutton">	</html:button>
			</td>
			</logic:notPresent>
			<logic:present name="uploadDoc">
			<td>
			<input type="hidden" id="hCommon" value="" />
			<html:text property="bankName" styleId="bankName" styleClass="text" maxlength="50" value="${uploadDoc[0].bankName}" readonly="true" tabindex="-1" />
			<html:hidden property="lbxBankID" styleId="lbxBankID" value="${uploadDoc[0].bankId}" />
			<html:button property="bankButton" styleId="bankButton" value=" " disabled = "true" onclick="closeLovCallonLov1();openLOVCommon(7,'documentUploadMaker','bankName','','bankBranch', 'lbxBranchID','','','hCommon')" styleClass="lovbutton">	</html:button>
			</td>
			</logic:present>
			<td  width="18%" >
			<bean:message key="lbl.bankBranchName" />
			
			</td>
			<logic:notPresent name="uploadDoc">
			<td colspan="4" nowrap="nowrap">
			<html:text styleClass="text" property="bankBranch" styleId="bankBranch" value="" maxlength="50" tabindex="-1" readonly="true" />
			<html:hidden property="lbxBranchID" styleId="lbxBranchID" value="${bankAcAnalysisDetail[0].lbxBranchID}" />
			<html:button property="bankBrnchButton" styleId="bankBrnchButton" value=" " onclick="closeLovCallonLov('bankName');openLOVCommon(8,'documentUploadMaker','bankBranch','bankName','lbxBranchID', 'lbxBankID','Please select Bank First!','','hCommon');" styleClass="lovbutton"></html:button>
			</td>
			</logic:notPresent>
			<logic:present name="uploadDoc">
			<td colspan="4" nowrap="nowrap">
			<html:text styleClass="text" property="bankBranch" styleId="bankBranch" value="${uploadDoc[0].bankBranch}" maxlength="50" tabindex="-1" readonly="true" />
			<html:hidden property="lbxBranchID" styleId="lbxBranchID" value="${uploadDoc[0].branchId}" />
			<html:button property="bankBrnchButton" styleId="bankBrnchButton" value=" " onclick="closeLovCallonLov('bankName');openLOVCommon(8,'documentUploadMaker','bankBranch','bankName','lbxBranchID', 'lbxBankID','Please select Bank First!','','hCommon');" styleClass="lovbutton"></html:button>
			</td>
			</logic:present>
		</tr>
		
		<tr>
        <td>
			<bean:message key="lbl.odccimitamount" />
			<font color="red">*</font>
			</td>
			<td nowrap="nowrap">
			<logic:notPresent name="uploadDoc">
			<html:text styleClass="text" property="odccLimitAmount" styleId="odccLimitAmount" value=""	maxlength="20" style="text-align: right;" onkeypress="return isNumberKey(evt);" />
			</logic:notPresent>		
			<logic:present name="uploadDoc">
			<html:text property="odccLimitAmount" styleClass="text" styleId="odccLimitAmount" maxlength="100" readonly="false" tabindex="-1" value="${uploadDoc[0].odccLimitAmount}"  onkeypress="return isNumberKey(evt);"/>
			</logic:present>
			</td>
         	
	       	 
	        <td  width="18%" >
			<bean:message key="lbl.dateIncrementalOrder" />
			<font color="red">*</font>
			</td>
			<logic:notPresent name="uploadDoc">
			<td colspan="4" nowrap="nowrap">
			<html:select property="dateIncrementalOrder" styleId="dateIncrementalOrder" styleClass="text" value=""  >
			<html:option value="">--Select--</html:option>
			<html:option value="A">Ascending</html:option>
			<html:option value="D">Descending</html:option>
			</html:select>
			</td>
			</logic:notPresent>
			
			<logic:present name="uploadDoc">
			<td colspan="4" nowrap="nowrap">
			<html:select property="dateIncrementalOrder" styleId="dateIncrementalOrder" styleClass="text" value="${uploadDoc[0].dateIncrementalOrder}"  >
			<html:option value="">--Select--</html:option>
				<html:option value="A">Ascending</html:option>
				<html:option value="D">Descending</html:option>
			
			</html:select>
			</td>
			</logic:present>
		</tr>
		
		
		<tr>
    		<td><bean:message key="lbl.FromDate"/><span><font color="red">*</font></span></td>
			<td>
			<logic:notPresent name="uploadDoc">
			<html:text property="fromDate"  styleId="fromdate" value = "" styleClass="text" readonly="true" maxlength="10" onchange="checkDate('fromdate');" />
			</logic:notPresent>
			<logic:present name="uploadDoc">
			<html:text property="fromDate"  styleId="fromdate" styleClass="text" maxlength="10" onchange="checkDate('fromdate');" readonly="true" value="${uploadDoc[0].fromDate}"/>
			</logic:present>
			</td>
			<td  width="18%" >
			<bean:message key="lbl.ToDate"/><span><font color="red">*</font></span>
			</td>
			<td>
			<logic:notPresent name="uploadDoc">
			<html:text property="toDate"  styleId="todate" value = "" styleClass="text" maxlength="10" readonly="true" onchange="checkDate('todate');"/>
			</logic:notPresent>
			<logic:present name="uploadDoc">
			<html:text property="toDate"  styleId="todate" styleClass="text" maxlength="10" value="${uploadDoc[0].toDate}" readonly="true" onchange="checkDate('todate');" />
			</logic:present>
			</td>
		</tr>
		<tr>
    		<td><bean:message key="lbl.bankStmtDateFormat"/><span><font color="red">*</font></span></td>
			<logic:notPresent name="uploadDoc">
			<td colspan="4" nowrap="nowrap">
			<html:select property="bankStmtDateFormat" styleId="bankStmtDateFormat" styleClass="text" value="" >
			<html:option value="">--Select--</html:option>
			<logic:present name="bankStmntDateFormatList">
	        <html:optionsCollection name="bankStmntDateFormatList" label="description" value="value" />
	        </logic:present>	         	  	
			
			</html:select>
			</td>
			</logic:notPresent>
			
			<logic:present name="uploadDoc">
			<td colspan="4" nowrap="nowrap">
			<html:select property="bankStmtDateFormat" styleId="bankStmtDateFormat" styleClass="text" value="${uploadDoc[0].bankStmtDateFormat}"  >
			<html:option value="">--Select--</html:option>
			<logic:present name="bankStmntDateFormatList">
	        <html:optionsCollection name="bankStmntDateFormatList" label="description" value="value" />
	        </logic:present>	         	  	
			</html:select>
			</td>
			</logic:present>
			
		</tr>
		
		
		<tr>
			<td>
        	<bean:message key="lbl.filepath"/><font color="red">*</font>
		 	<td><html:file size="100" property="docFile" styleId="docFile" styleClass="text" tabindex="3"  /></td> 
       		</td>
   	  					 	  				
		</tr>
		
		<tr>
			<td colspan="3"><button type="button" id="save" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return uploadDoc();" tabindex="4" ><bean:message key="button.upload" /></button>
			
		<button type="button" name="button" id="report" class="topformbutton4"
															title="Alt+F" accesskey="R" 
															onclick="generateAccountUploadFormat();">
															<bean:message key="lbl.generateUloadFormat" />
														</button>
			</td>
		</tr>
			
	</table>
	</fieldset>
	
<!-- ------------------------------------------------------------------------------------------------------------------ -->
<fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    
    <tr class="white2">
	<logic:notPresent name="uploadDoc">
	
        			<td><b><bean:message key="lbl.select" /></b></td> 
	 				<td><b><bean:message key="lbl.fileName"/></b></td> 
					<td ><b><bean:message key="lbl.docEntity"/></b></td>
					<td ><b><bean:message key="lbl.uploadState"/></b></td>
					<td ><b><bean:message key="lbl.uploadedBy"/></b></td>
   
</logic:notPresent>
	</tr>
 </table>    </td>
</tr>
</table>
 <logic:present name="uploadDoc">
 <logic:notEmpty name="uploadDoc">
  <display:table  id="uploadDoc"  name="uploadDoc" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${uploadDoc[0].totalRecordSize}" requestURI="/documentMaker.do?method=viewAllDocument" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    		<display:column property="chk" titleKey="lbl.select"  sortable="false" />
			<display:column property="fileName" titleKey="lbl.fileName"  sortable="false"  />
			<display:column property="docEntity" titleKey="lbl.docEntity"  sortable="false"  />
			<display:column property="uploadedState" titleKey="lbl.uploadState"  sortable="false"  />
			<display:column property="userName" titleKey="lbl.uploadedBy"  sortable="false"  />
	
</display:table>
			<button type="button" id="save" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return forwardDoc();" tabindex="4" ><bean:message key="button.Forward" /></button>
			<button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" onclick="deleteUploadDoc();"><bean:message key="button.delete" /></button>
</logic:notEmpty>
<logic:empty name="uploadDoc">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
	
					<td><b><bean:message key="lbl.select" /></b></td> 
	 				<td><b><bean:message key="lbl.fileName"/></b></td> 
					<td ><b><bean:message key="lbl.docEntity"/></b></td>
					<td ><b><bean:message key="lbl.uploadState"/></b></td>
					<td ><b><bean:message key="lbl.uploadedBy"/></b></td>
        
	</tr>
	<tr class="white2"><td colspan="7"><bean:message key="lbl.noDataFound"/></td></tr>
	
 </table>    </td>
</tr>
</table>

</logic:empty>
  </logic:present>


	</fieldset>
<!-- ------------------------------------------------------------------------------------------------------------------ -->	
	
	
	
		
</html:form>
</div>
</div>
</body>
</html:html>


<logic:present name="message">

 <script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='E')
	{
		alert('<bean:message key="msg.fileExist" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='U')
	{
		alert('<bean:message key="msg.upperLimit" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='S')
	{
		alert('<bean:message key="msg.selectFile" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='FiveDocOnly')
	{
		alert('<%=request.getAttribute("limitOfDocument").toString()%>');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='UploadSuccessful')
	{
		alert('<bean:message key="msg.uploadSuccessful" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='UploadProcess')
	{
		alert('<bean:message key="msg.uploadProcess" />');
		location.href ="<%=request.getContextPath()%>/documentUploadSearch.do?method=getDocumentForOCR&dealId="+'${caseId}';
		
	}
	else if('<%=request.getAttribute("message").toString()%>'=='deletedoc')
	{
		alert('<bean:message key="lbl.dataDeleted" />');
		location.href ="<%=request.getContextPath()%>/documentUploadSearch.do?method=getDocumentForOCR&dealId="+'${caseId}';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='Updatedoc')
	{
		alert('<bean:message key="msg.manualAdviceForward" />');
	}<%-- else if('<%=request.getAttribute("message").toString()%>'=='FR')
	{
		alert('<bean:message key="msg.fileReadingProblem" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='fileNameValidation')
	{
		alert('<bean:message key="msg.fileNameValidation" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='R')
	{
		alert('<bean:message key="msg.validatiionRule" />');
	} --%>
	
</script>
</logic:present>

<logic:present name="msg">

 <script type="text/javascript">
 if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.datasms" />");
		location.href ="<%=request.getContextPath()%>/documentUploadSearch.do?method=getDocumentForOCR&dealId="+'${caseId}';
	}
		
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("Data Not Uploaded.");
		location.href ="<%=request.getContextPath()%>/documentUploadSearch.do?method=getDocumentForOCR&dealId="+'${caseId}';
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='FR')
	{
		alert('<bean:message key="msg.fileReadingProblem" />');
	}
		
	else if('<%=request.getAttribute("msg").toString()%>'=='fileNameValidation')
	{
		alert('<bean:message key="msg.fileNameValidation" />');
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='R')
	{
		alert('<bean:message key="msg.validatiionRule" />');
	}

	else if('<%=request.getAttribute("msg").toString()%>'=='fileTypeVal')
	{
		alert('<bean:message key="msg.fileTypeVal" />');
	}

	else if('<%=request.getAttribute("msg").toString()%>'=='DS')
	{
		alert('<bean:message key="msg.docForward" />');
		
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='DE')
	{
		alert('<bean:message key="msg.DocNotForward" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='EF')
	{
		alert('<bean:message key="msg.checkErrorFile" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='ANU')
	{
		alert('<bean:message key="msg.AccountNumberUpload" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='FUE')
	{
		alert('<bean:message key="msg.FUE" />');
		location.href ="<%=request.getContextPath()%>/documentUploadSearch.do?method=getDocumentForOCR&dealId="+'${caseId}';
	} 
	else if('<%=request.getAttribute("msg").toString()%>'=='CDSF')
	{
		alert('<bean:message key="msg.checkFile" />');
		location.href ="<%=request.getContextPath()%>/documentUploadSearch.do?method=getDocumentForOCR&dealId="+'${caseId}';
	} 
 // Hina space here
	if('<%=request.getAttribute("msg").toString()%>'=='QueryInitiationPending')
	{
		alert("<bean:message key="msg.QueryInitiationPending" />");
		location.href ="<%=request.getContextPath()%>/documentUploadSearch.do?method=getDocumentForOCR&dealId="+'${caseId}';
	} 
	
	
	</script>

</logic:present>
<logic:present name="checkStageM">

 <script type="text/javascript">
	if('<%=request.getAttribute("checkStageM").toString()%>'=='checkStageM')
	{
		alert('${checkStageM}');
	}
</script>
</logic:present>






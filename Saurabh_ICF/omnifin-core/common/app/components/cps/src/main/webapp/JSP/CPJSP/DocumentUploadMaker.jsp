<%@page import="java.util.ResourceBundle"%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
	

<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

<div id=centercolumn>
<div id=revisedcontainer>
<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
<%-- End Div for screen Saver (Calender picker)--%>	
	
	
	<html:form action="/documentUploadMaker" method="post" styleId="documentUploadMaker" enctype="multipart/form-data">


	<fieldset>
	<legend><bean:message key="lbl.Documnet_maker"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="2" cellpadding="3" >
			
		<tr>
			<td width="20%"><bean:message key="lbl.dealNo"/><font ></font></td>
			<td width="35%" valign="top">
			<logic:notPresent name="List">
			
			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
			<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(6513,'documentUploadMaker','dealNo','userId','dealNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	       	  
      		</logic:notPresent>
      		<logic:present name="List">
			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" value="${List}" />
			
      		</logic:present>
	  		</td>
	  
	  		<td><bean:message key="lbl.customerName"/><font ></font></td>
	  		<logic:notPresent name="List1">
			<td width="13%" ><html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" maxlength="50" onchange="upperMe('customerName');"/></td> 
		    </logic:notPresent>
			
			<logic:present name="List1">
			<td width="13%" ><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="50" readonly="true" value="${List1}" onchange="upperMe('customerName');"/></td>
			</logic:present>
			<td width="15%" ></td>
	   </tr>
		
	   <tr>
    	 	<td width="13%"><bean:message key="lbl.docEntity"/><span><font></font></span></td>
 			<td width="13%">
	 		<html:select property="docEntity" styleId="docEntity" styleClass="text" ><!-- onchange="getlist();getChageClass();" > -->
	        <html:option value="" >--<bean:message key="lbl.select" />--</html:option>
	        <logic:present name="docEntity">
	        <html:optionsCollection name="docEntity" label="entityDescription" value="entityValue" />
	        </logic:present>	         	  	
	  	  	</html:select>
  			</td>	    		
     </tr>
     
     <tr>
		   <td>
		   <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return makerSearch();"><bean:message key="button.search" /></button>
		   <button type="button" name="new" id="button" class="topformbutton2" title="Alt+N" accesskey="N" onclick="newDeal();" ><bean:message key="button.new" /></button></td>
 	 </tr>
				
		
	</table>
	</fieldset>



	<fieldset>	
		 
		 <legend><bean:message key="lbl.docInformation"/></legend>  
       	 <table width="100%"  border="0" cellspacing="0" cellpadding="4">
		
		<tr>
		   <td>
    	   <table width="100%" border="0" cellspacing="1" cellpadding="1">
  			
  			<tr>
    	  		<logic:notPresent name="uploadedDocList">
    	  	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					
					<tr>
			    	<td class="gridtd">
			    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
			   		<tr class="white2">
				        <td ><b><bean:message key="lbl.dealNo"/></b></td>
				      	<td><b><bean:message key="lbl.customerName"/></b></td>
					 	<td><b><bean:message key="lbl.product"/></b></td>
							<td><b><bean:message key="lbl.scheme"/></b></td>
							<td><b><bean:message key="lbl.customerType"/></b></td>
			        </tr>
			    
			    	<tr class="white2">
			    	<td colspan="6"> <bean:message key="lbl.noDataFound"/></td>
			    	</tr>
				 	</table>
				 	</td>
					</tr>
				</table>
    	  		</logic:notPresent>

    	  		<logic:present name="uploadedDocList">
				<logic:notEmpty name="uploadedDocList">
				<display:table id="uploadedDocList" name="uploadedDocList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${uploadedDocList[0].totalRecordSize}" requestURI="/documentMaker.do?method=makerSearch"> 
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						
				<display:column property="custRef" titleKey="lbl.cust_ref" sortable="true" />
				<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
				<display:column property="productDesc" titleKey="lbl.product" sortable="true" />
				<display:column property="schemeDesc" titleKey="lbl.scheme" sortable="true" />
				<display:column property="customerRoleType" titleKey="lbl.customerType" sortable="true" />	
				</display:table>
				</logic:notEmpty>
				<logic:empty name="uploadedDocList">

 			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
    		<td class="gridtd">
    
    		<table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    		<tr class="white2">
	    		
	    		<td ><b><bean:message key="lbl.dealNo"/></b></td>
				<td><b><bean:message key="lbl.customerName"/></b></td>
				<td><b><bean:message key="lbl.product"/></b></td>
				<td><b><bean:message key="lbl.scheme"/></b></td>
				<td><b><bean:message key="lbl.customerType"/></b></td>
			
			</tr>
			<tr class="white2"><td colspan="7"><bean:message key="lbl.noDataFound"/></td></tr>
	
 			</table> 
 		   	</td>
			</tr>
			</table>
<!--	<script type="text/javascript">-->
<!---->
<!--		alert("<bean:message key="lbl.noDataFound" />");-->
<!--	-->
<!--	</script>-->
		</logic:empty>
		</logic:present>
    	
		</tr>
 		</table>
 	    </td>
	</tr>
	</table>
	</fieldset>
	
	
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
	else if('<%=request.getAttribute("message").toString()%>'=='deletedoc')
	{
		alert('<bean:message key="lbl.dataDeleted" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='Updatedoc')
	{
		alert('<bean:message key="msg.manualAdviceForward" />');
	}
	
</script>
</logic:present>


<logic:present name="msg">
 <script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='Z')
	{
		alert('<bean:message key="msg.nodatafound" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='DS')
	{
		
		alert('<bean:message key="msg.docForward" />');
		<%-- location="<%=request.getContextPath()%>/documentUploadMaker.do"; --%>
		
		
	}
	
</script>

</logic:present>


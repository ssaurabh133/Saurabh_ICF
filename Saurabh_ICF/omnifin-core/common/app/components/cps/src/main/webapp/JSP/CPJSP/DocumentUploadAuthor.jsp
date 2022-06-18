<%@page import="java.util.ResourceBundle"%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp"%> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%-- <%@include file="/JSP/OCRJSP/DocumentUploadAuthorBalanceSheet.jsp"%>
<%@include file="/JSP/OCRJSP/DocumentUploadAuthorProfitAndLoss.jsp"%> --%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/uploadAuthor.js"></script>
		<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/ocrSript/upload.js"></script>  --%>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script> 	
		    
	</head>
	
	
	  
<body onclick="parent_disable();" oncontextmenu="return false" onload="enableAnchor();checkChanges('DocumentActionUploadAuthorForm');document.getElementById('DocumentActionUploadAuthorForm').applicantType.focus();" onunload="closeAllWindowCallUnloadBody();closeAllWindowCallUnloadBodyAn();">   

<input type="hidden" name="<%= org.apache.struts.taglib.html.Constants.TOKEN_KEY %>" value="<%= session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>" >

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>	


<!-- Div for screen Saver (Calendar code Start) -->

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

<!-- End Div for screen Saver (Calendar code Start) -->

<html:form action="/DocumentActionUploadAuthorForm" method="post" styleId="DocumentActionUploadAuthorForm">
					
<!-- code for Upload document  -->
<fieldset>
<legend><bean:message key="lbl.Documnet_author"></bean:message></legend>
<table width="100%"  border="0" cellspacing="2" cellpadding="3" >
	
<!-- Customer Type code -->
<tr>
		<td width="20%"><bean:message key="lbl.Upload_doc_reference_no"/></td>
		<td width="35%" valign="top">
				
			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<html:text property="dealNo" value = "" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
			<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(6519,'DocumentActionUploadAuthorForm','dealNo','userId','dealNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	       <%--  <img onclick="openLOVCommon(21,'commonForm','dealNo','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"  />  --%>	  
      
	  </td>
	  <td><bean:message key="lbl.customerName"/></td>
		<td width="13%" ><html:text property="customerName" value = "" styleClass="text" readonly="true" styleId="customerName" maxlength="50" onchange="upperMe('customerName');"/></td> 
		<td width="15%" ></td>
	   
	    </tr>
          
          <tr>
		
	 	</tr>
	 	
	 		
	  	
    	 	<td width="13%"><bean:message key="lbl.docEntity"/><span></span></td>
 			<td width="13%">
	 		<html:select property="docEntity" styleId="docEntity" value = "" styleClass="text" ><!-- onchange="getlist();getChageClass();" > -->
	       <html:option value="" >--<bean:message key="lbl.select" />--</html:option> 
	        <logic:present name="docEntity">
	        <html:optionsCollection name="docEntity" label="entityDescription" value="entityValue" />
	        </logic:present>	         	  	
	   	    </html:select>

  			</td>	 
	 	
    <tr> 
     <%-- <td><button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return makerSearch();"><bean:message key="button.search" /></button></td> --%>
   	  <td><button type="button" id="save" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return makerSearch();" tabindex="4" ><bean:message key="button.search" /></button>
   	  </td>
	</tr>
</table>
</fieldset>


<!-- -------------------------------------------------------------------------------------------------------------------------- -->

<fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
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
				
				
					        <td ><b><bean:message key="lbl.cust_ref"/></b></td>
					      	<td><b><bean:message key="lbl.customerName"/></b></td>
						 	<%-- <td><b><bean:message key="lbl.docEntity"/></b></td>
					        <td><b><bean:message key="lbl.uploadedBy"/></b></td> --%>
					        <td><b><bean:message key="lbl.product"/></b></td>
							<td><b><bean:message key="lbl.scheme"/></b></td>
							<td><b><bean:message key="lbl.customerType"/></b></td>
			       	
			    </tr>
			    <tr class="white2">
			    <td colspan="6"> <bean:message key="lbl.noDataFound"/></td>
			    </tr>
				
			 </table>    </td>
			</tr>
			</table>
    	  </logic:notPresent>

    	  		 <logic:present name="uploadedDocList">
				 <logic:notEmpty name="uploadedDocList">
				 
				 <display:table id="uploadedDocList" name="uploadedDocList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${uploadedDocList[0].totalRecordSize}" requestURI="/documentUploadAuthor.do?method=makerSearch1"> 
				
				<display:setProperty name="paging.banner.placement"  value="bottom"/>
    			<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    			<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							
				 <display:column property="custRef" titleKey="lbl.cust_ref" sortable="true" />
				 <display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
				 <%-- <display:column property="docEntity" titleKey="lbl.docEntity" sortable="true" /> 
				 <display:column property="makerId" titleKey="lbl.uploadedBy" sortable="true" />--%>
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
	    		
	    		<td ><b><bean:message key="lbl.cust_ref"/></b></td>
				<td><b><bean:message key="lbl.customerName"/></b></td>
				<%-- <td><b><bean:message key="lbl.docEntity"/></b></td> 
				<td><b><bean:message key="lbl.uploadedBy"/></b></td>--%>
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
 </table>    </td>
</tr>
</table>

	</fieldset>
	
<!-- ............................................................................................................. -->	



<%-- 
<fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">



<tr>


    <td class="gridtd" >
    
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
	
    <tr class="white2">
    
        <td><b><bean:message key="lbl.cust_ref"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td> 
		<td><b><bean:message key="lbl.customerName"/></b></td>
		<td ><b><bean:message key="lbl.docEntity"/></b></td>
	<td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        </tr>
       
       <logic:notPresent name="uploadedDocList">
	 		 <td><b><bean:message key="lbl.select" /></b></td> 
	 		 <td><b><bean:message key="lbl.fileName"/></b></td> 
			 <td ><b><bean:message key="lbl.docEntity"/></b></td>
			 <td ><b><bean:message key="lbl.uploadState"/></b></td>
		</logic:notPresent>
       
       
       
       <logic:notPresent name="uploadedDocList">
      
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="i">
    	 	 <td><b><bean:message key="lbl.select" /></b></td> 
	 		 <td><b><bean:message key="lbl.fileName"/></b></td> 
			 <td ><b><bean:message key="lbl.docEntity"/></b></td>
			 <td ><b><bean:message key="lbl.uploadState"/></b></td>
        </logic:iterate>
	    </logic:notPresent>
       
       
       
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="i">
    	<tr  class="white1">
    	<input type="hidden" name="dealId" id="dealId" value="${uploadedDocSubList.dealId}"/>
    	<input type="hidden" name="docEntity" id="docEntity" value="${uploadedDocSubList.docEntity}"/>
     	<input type="hidden" name="fileName" id="fileName" value="${uploadedDocSubList.fileName}"/>
   	 	<td><a href="#" id="dupDealNO" onclick="return linkDeal('${uploadedDocSubList.dealId}','${uploadedDocSubList.docEntity}','${uploadedDocSubList.customerName}','${uploadedDocSubList.fileName}');">${uploadedDocSubList.custRef}</a></td> 
     	<td>${uploadedDocSubList.customerName}</td>
        <td>${uploadedDocSubList.docEntity}</td>
        <td>${uploadedDocSubList.userName}</td>
        </tr>
        </logic:iterate>
	  	</logic:notEmpty>

	  </logic:present>
    	
    	
    	
    	
    </table>
    </td>

</tr>
</table>

</fieldset> --%>

</html:form>
</body>
</html:html> 
<logic:present name="msg">

 <script type="text/javascript">
	
 if('<%=request.getAttribute("msg").toString()%>'=='G')
	{
		
	 	alert("<bean:message key="msg.dataforwarded" />");
	 	
	 	parent.location.href="documentUploadAuthor.do";
		
	}
 else if('<%=request.getAttribute("msg").toString()%>'=='L')
	{
	 	alert("<bean:message key="msg.datasendBack" />");
	 	parent.location.href ="documentUploadAuthor.do";
	}
 else if('<%=request.getAttribute("msg").toString()%>'=='X')
	{
	 	alert("<bean:message key="msg.datareject" />");
	 	parent.location.href="documentUploadAuthor.do";
	}
 else if('<%=request.getAttribute("msg").toString()%>'=='F')
	{
	 alert('${ansNotMatch}');
	 	parent.location.href ="documentUploadAuthor.do";
	}
 else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="msg.nodatafound" />");
		parent.location.href ="documentUploadAuthor.do";
	}
 else if('<%=request.getAttribute("msg").toString()%>'=='QAR')
	{
	 alert('${ansNotMatch}');
		parent.location.href ="documentUploadAuthor.do";
	} 
 //Hina start here
 else if('<%=request.getAttribute("msg").toString()%>'=='QueryInitiationPending')
	{
	 alert("<bean:message key="msg.QueryInitiationPending" />");
		parent.location.href ="documentUploadAuthor.do";
	}
 
</script>
</logic:present>


<%-- <logic:present name="msg">
 <script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert('<bean:message key="msg.nodatafound" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='G')
	{
		alert('<bean:message key="msg.dataforwarded" />');
		 parent.location.href ="<%=request.getContextPath()%>/documentUploadAuthor.do";
	}
	
</script>
</logic:present> --%>
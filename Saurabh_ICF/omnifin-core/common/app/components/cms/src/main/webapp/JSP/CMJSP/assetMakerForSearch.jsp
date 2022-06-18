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
		
		
		<!--[if gte IE 5]>
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
	<![endif]-->
	
	<script type="text/javascript">
  		
		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>

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
	<body onload="enableAnchor();document.getElementById('assetMakerSearch').coverNoteNo.focus();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	<html:form action="/assetAuthorProcessAction" method="post" styleId="assetMakerSearch">
	
	
		  
	          <logic:present name="fromAuthor">
	          
	           <fieldset>	  
	<legend><bean:message key="lbl.insuranceAssetAuthorSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">		
		<tr>					
		   <td width="20%">	            
	             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		         <input type="hidden" name="lbxAssetId" id="lbxAssetId" value="" /></td></tr>
		 <tr>	   
       		  <td><bean:message key="lbl.loanAccountNumber"></bean:message> </td>
	     	 <td>
				<html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="" readonly="true" tabindex="-1"/>
		        <html:hidden  property="loanNoHID" styleId="loanNoHID"  />
		       <html:button property="loanAcButton" styleId="loanAcButton" onclick="closeLovCallonLov1();openLOVCommon(95,'assetMakerSearch','loanAccountNumber','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
      		 </td>
	
	    	<td><bean:message key="lbl.customerName"></bean:message> </td>
			<td width="35%">
				 <html:text property="customerName" styleClass="text" styleId="customerName" value="" readonly="true" tabindex="-1"></html:text>
			 </td>
		</tr>
           <tr>
           <td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	       <td >
	       <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="" maxlength="20" ></html:text>
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	      </td>
	       <td><bean:message key="lbl.policyNo"></bean:message></td>
	       <td >
	       <html:text property="policyNo"  styleClass="text" styleId="policyNo" value=""  maxlength="20" ></html:text>
	       </td>
		   </tr>
		   <tr>		   	  
	       <td><bean:message key="lbl.insuranceAgency"></bean:message></td>
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="" readonly="true" tabindex="-1"/>
           <input type="hidden"  name="lbxInsuranceAgency" id="lbxInsuranceAgency"/>
            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerSearch','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>
		  </td>
		 
 <td>
 <bean:message key="lbl.userName" />
 </td>
 <td>
    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'assetMakerSearch','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
 </td>		
          </tr>
           <tr>
		   <td><bean:message key ="lbl.startDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td >
	       <html:text property="startDate"  styleClass="text" styleId="startDate" value="" maxlength="50" ></html:text>
	       </td>
	        <td><bean:message key ="lbl.endDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td >
	       <html:text property="endDate"  styleClass="text" styleId="endDate" value="" maxlength="50" ></html:text>
	       </td>
	      </tr>	  
	      
	       <td>
	        <button type="button" class="topformbutton2"  id="search" onclick="return fnSearchAuthor('<bean:message key="msg.plsSelOneCriteria" />');" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
	        </td> 
		   
		   	</td>
		  </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
		   
		    </logic:present>
		    
		     <logic:notPresent name="fromAuthor">	
		      <fieldset>	  
	<legend><bean:message key="lbl.insuranceAssetMakerSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%">
	            
	             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		          <input type="hidden" name="lbxAssetId" id="lbxAssetId" value="" />
            
		   <tr>	   
         <td><bean:message key="lbl.loanAccountNumber"></bean:message> </td>
	      <td>
		<html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="" readonly="true" tabindex="-1"/>
        <html:hidden  property="loanNoHID" styleId="loanNoHID"  />
       <html:button property="loanAcButton" styleId="loanAcButton" onclick="openLOVCommon(94,'assetMakerSearch','loanAccountNumber','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
        </td>
	
	    <td><bean:message key="lbl.customerName"></bean:message> </td>
			<td width="35%"><div style="float:left;">
			 <html:text property="customerName" styleClass="text" styleId="customerName" value="" readonly="true" tabindex="-1"></html:text>
			  </div></td>
		</tr>	
		     <tr>
	      <td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	       <td >
	       
	       <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="" maxlength="20" ></html:text>
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	      </td>
	       <td><bean:message key="lbl.policyNo"></bean:message></td>
	       <td >
	       <html:text property="policyNo"  styleClass="text" styleId="policyNo" value=""  maxlength="20" ></html:text>
	       </td>
		   </tr>
		   <tr>		   	  
	       <td><bean:message key="lbl.insuranceAgency"></bean:message> </td>
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="" readonly="true" tabindex="-1"/>
           <input type="hidden"  name="lbxInsuranceAgency" id="lbxInsuranceAgency"/>
            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerSearch','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>
		  </td>
			 <td>
			 <bean:message key="lbl.userName" />
			 </td>
			 <td>
			    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
			    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
			    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
			    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'assetMakerSearch','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			 </td></tr>
           <tr>
		   <td><bean:message key ="lbl.startDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td >
	       <html:text property="startDate"  styleClass="text" styleId="startDate" value="" maxlength="50" ></html:text>
	       </td>
	        <td><bean:message key ="lbl.endDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td >
	       <html:text property="endDate"  styleClass="text" styleId="endDate" value="" maxlength="50" ></html:text>
	       </td>
	        </tr>	
	      <td>
	      <button type="button" class="topformbutton2"  id="search" onclick="return fnSearch('<bean:message key="msg.plsSelOneCriteria"/>');" title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
	      <button type="button" name="button2" class="topformbutton2"  onclick="return newAsset();" title="Alt+N" accesskey="N"><bean:message key="button.new" /></button>
	      </td>
	   	  </td>
		  </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
		</logic:notPresent>    


		
     <fieldset>	
		 <legend><bean:message key="lbl.insuranceAssetSearchRecords"/></legend>  

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
 <logic:notPresent name="authordetailList">
   <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <logic:notPresent name="list">
    <tr class="white2">	     
	    <td><b><bean:message key="lbl.loanAccountNumber"/></b></td>
	    
	    <td><b><bean:message key="lbl.customerName"/></b></td> 
	    <td><b><bean:message key="lbl.policyNo"/></b></td>        
	    <td><b><bean:message key="lbl.coverNoteNo"/></b></td>        
        <td><b><bean:message key="lbl.insuranceAgency"/></b></td>
        <td><b><bean:message key="lbl.startDate"/></b></td>
		 <td><b><bean:message key="lbl.endDate"/></b></td>
		  <td><b><bean:message key="lbl.userName"/></b></td>
		
	</tr>
	<tr class="white2">
<td colspan="8">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>

	</logic:notPresent>
	</table>
	</logic:notPresent>
	</td>
	</tr>
	</table>
	 <logic:present name="list">
<logic:notEmpty name="list"> 
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/assetMakerSearch.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
      
    <display:column property="loanAccountNumber" titleKey="lbl.loanAccountNumber"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
    <display:column property="policyNo" titleKey="lbl.policyNo"  sortable="true"  />   
    <display:column property="coverNoteNo" titleKey="lbl.coverNoteNo"  sortable="true"  />
	<display:column property="insuranceAgency" titleKey="lbl.insuranceAgency"  sortable="true"  />
	<display:column property="startDate" titleKey="lbl.startDate"  sortable="true"  />
	<display:column property="endDate" titleKey="lbl.endDate"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>

<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    	
        <td><b><bean:message key="lbl.loanAccountNumber"/></b></td>
	    <td><b><bean:message key="lbl.customerName"/></b></td>       
         <td><b><bean:message key="lbl.policyNo"/></b></td>
	    <td><b><bean:message key="lbl.coverNoteNo"/></b></td>        
        <td><b><bean:message key="lbl.insuranceAgency"/></b></td>
        <td><b><bean:message key="lbl.startDate"/></b></td>
		 <td><b><bean:message key="lbl.endDate"/></b></td>
		 <td><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	<tr class="white2">
<td colspan="8">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
 </table>    </td>
</tr>

</table>
</logic:empty>
  </logic:present>
 
 
 	 <logic:present name="authordetailList">

<logic:notEmpty name="authordetailList"> 
  <display:table  id="authordetailList"  name="authordetailList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${authordetailList[0].totalRecordSize}" requestURI="/assetAuthorProcessAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    <display:column property="loanAccountNumber" titleKey="lbl.loanAccountNumber"  sortable="true"  />
     <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
    <display:column property="policyNo" titleKey="lbl.policyNo"  sortable="true"  />
    <display:column property="coverNoteNo" titleKey="lbl.coverNoteNo"  sortable="true"  />
	<display:column property="insuranceAgency" titleKey="lbl.insuranceAgency"  sortable="true"  />
	<display:column property="startDate" titleKey="lbl.startDate"  sortable="true"  />
	<display:column property="endDate" titleKey="lbl.endDate"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>

<logic:empty name="authordetailList">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
       
         <td><b><bean:message key="lbl.loanAccountNumber"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>   
           <td><b><bean:message key="lbl.policyNo"/></b></td>    
	     <td><b><bean:message key="lbl.coverNoteNo"/></b></td>        
         <td><b><bean:message key="lbl.insuranceAgency"/></b></td>
         <td><b><bean:message key="lbl.startDate"/></b></td>
		 <td><b><bean:message key="lbl.endDate"/></b></td>
		  <td><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	<tr class="white2">
<td colspan="8">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
 </table>    </td>
</tr>


</table>
</logic:empty>
  </logic:present>
 
	</fieldset>

 </html:form>
  
		<logic:present name="msg"><br />
		<script type="text/javascript">
		
		if('<%=request.getAttribute("msg").toString()%>'=='F')
		{
			alert("<bean:message key="lbl.forwardSuccess" />");
			
		}
	   else if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.updateSuccess" />");
			document.getElementById('assetMakerSearch').action="<%=request.getContextPath()%>/assetMakerSearch.do}';
		document.getElementById('assetMakerSearch').submit();
			
		}
		else if('<%=request.getAttribute("msg")%>'=='Locked')
	    {
		    alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	    }
		else
		{
			alert("<bean:message key="lbl.erroruSuccess" />");
			
		}
		
		</script>
		</logic:present>
   
</div>



</div>
</body>
</html>
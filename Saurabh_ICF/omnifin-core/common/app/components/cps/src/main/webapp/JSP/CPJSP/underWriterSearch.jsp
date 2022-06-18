<%--
      Author Name-  Prashant kumar    
      Date of creation -12/05/2011
      Purpose-   InitLoan Info       
      Documentation-      
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ page language="java" import="java.util.ResourceBundle" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>	
	<head>
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
	
	
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		
	
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
<%-- 
<script language="Javascript">
function enableAnchor(){


    var upperMaxSize=parent.menu.document.commonFormMenu.upperMaxSize;
    //alert("upperMaxSize: "+upperMaxSize.value);
    
    

var maxNum=parseInt(upperMaxSize.value);

 for (var j=0;j<maxNum;j++ ) {
     
    
     for ( var i=0;i<50;i++ ) {
          	
          
     	     var anchorId="hrefId"+j+i;
     	
     	 
     	     var ab=parent.menu.document.getElementById(anchorId);
     	    
     	     if(ab!=null && ab!='undefined')
     	     {
     	         if(ab.attributes['href_bak']!=null && ab.attributes['href_bak']!='undefined')
     	         {
		     		 //alert("atribute: "+ab.attributes['href_bak'].nodeValue);
		     		 ab.setAttribute('href', ab.attributes['href_bak'].nodeValue);
		             ab.style.color="white";
	             }
	           
             }
     	 }
   }


}
</script>
--%>
	</head>
	<body onunload="closeAllLovCallUnloadBody();" oncontextmenu="return false" onload="enableAnchor();document.getElementById('commonForm').dealButton.focus();init_fields();" onclick="parent_disable();">

	<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>	

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>

	<div id="centercolumn">

	<div id="centercolumn">

	<div id="revisedcontainer">

	

	<div id="revisedcontainer">
	

			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>

	<html:form action="/commondeal" method="post" styleId="commonForm">
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
<input type="hidden" name="functionId" id="functionId" value="${sessionScope.functionId}" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="customerExposureRequestedLoanAmount" id="customerExposureRequestedLoanAmount" value="${customerExposureRequestedLoanAmount}" />

	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>
	<fieldset>	  
	 	
	<logic:equal name="functionId" value="500000123">
	<legend>Co-Lending Under Writer Search</legend>   
	</logic:equal>
	<logic:notEqual name="functionId" value="500000123">
	<legend><bean:message key="lbl.underwriter.detail"/></legend>  
	</logic:notEqual>                
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		 <table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		<td width="20%"><bean:message key="lbl.dealNo"/></td>
		<td width="35%">
		
<!--			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />-->
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true"  tabindex="-1"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openDealLovUnderWriter();closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
	      <%--<img onclick="openLOVCommon(58,'commonForm','dealNo','','', '','','','customername')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>   	  
    
       
	  </td>
	  <td><bean:message key="lbl.customerName"/></td>
		<td width="13%" ><html:text property="customername" tabindex="2"  styleClass="text" styleId="customername" maxlength="50" onchange="upperMe('customerName');"/></td> 
		<td width="15%" ></td>
	   
	    </tr>
	  
	    <tr>
		<td><bean:message key="lbl.applicationDate"/></td>
		<td ><html:text property="applicationdate"  tabindex="3" styleClass="text" styleId="applicationdate" maxlength="10" onchange="return checkDate('applicationdate');"/></td> 
	    
		 <td width="17%"><bean:message key="lbl.applicationNo"/></td>
	    <td colspan="2"><html:text property="applicationno" styleClass="text"styleId="applicationno" tabindex="4" maxlength="20"  onkeyup="return upperMe('applicationno');"/></td>
	    </tr>
		
		<tr> 
		<td><bean:message key="lbl.product"/></td> 
       
        <td > 
      
	        <html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        <html:button property="productButton" style="productButton" onclick="closeLovCallonLov1();openLOVCommon(150,'commonForm','product','userId','scheme','lbxscheme|userId','','','productId')" tabindex="5" value=" " styleClass="lovbutton"></html:button>
	      <%--<img onclick="openLOVCommon(87,'commonForm','product','','scheme','lbxscheme','','','productId')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>   	         
    
      
        </td>
        <input type="hidden" id="productId" name="productId"/> 
        
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true"  tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          		<input type="hidden" id="schemeId" name="schemeId"/> 
          		<html:button property="schemeButton" style="schemeButton" onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');" tabindex="6" value=" " styleClass="lovbutton"></html:button>
              <%--<img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>  
           						    
                </td>
                

		</tr> 
	
			
		<tr>
		 <td width="13%">
						<bean:message key="lbl.userNam" />
				      </td>
				      <td width="13%">
						<html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId"  />
					  	<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
					  	<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(1385,'commonForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>
   					  
   					  </td>
   		<td  ><bean:message key="lbl.allBranch"/></td>
	    <td  ><html:checkbox property="allBranches" styleClass="text"styleId="allBranches" /></td>
		</tr>
				
		 <tr>
		 		<td>  <button type="button" name="search" id="searchButton" title="Alt+S" accesskey="S" tabindex="7" class="topformbutton2" onclick="return newSearchDeal('F');"><bean:message key="button.search" /></button></td>		          
		

		 </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>

	</html:form>

	</div>

<fieldset>	
		 <legend><bean:message key="lbl.applicationDetails"/></legend>  

   <logic:notPresent name="dealdetails">
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
	
	
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
	
        <td><b><bean:message key="lbl.applicationDate"/></b></td>
       	<td><b><bean:message key="grd.applicationNo"/></b></td>
      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
        <td><b><bean:message key="lbl.userNam"/> </b></td>
        <td><b><bean:message key="lbl.branch"/> </b></td>
        
        <td><b><bean:message key="lbl.currentStatus"/> </b></td>
	</tr>
	<tr class="white2">
	<td colspan="8"><bean:message key="lbl.noDataFound"/></td></tr>
	
 </table>    </td>
</tr>
</table>

</logic:notPresent>
 <logic:present name="dealdetails">
 <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/commondeal.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    <display:column property="lbxDealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customername" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="applicationdate" titleKey="lbl.applicationDate"  sortable="true"  />
	<display:column property="applicationno" titleKey="grd.applicationNo"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userNam"  sortable="true"  />
	<display:column property="allBranches" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="status" titleKey="lbl.currentStatus"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">

   <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
	
	
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
	
        <td><b><bean:message key="lbl.applicationDate"/></b></td>
       	<td><b><bean:message key="grd.applicationNo"/></b></td>
      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
        <td><b><bean:message key="lbl.userNam"/> </b></td>
        <td><b><bean:message key="lbl.branch"/> </b></td>
        <td><b><bean:message key="lbl.currentStatus"/> </b></td>
	</tr>
	<tr class="white2">
	<td colspan="9"><bean:message key="lbl.noDataFound"/></td>
	</tr>
	
 </table>    </td>
</tr>
</table>
<!--	<script type="text/javascript">-->
<!---->
<!--		alert("<bean:message key="lbl.noDataFound" />");-->
<!--	-->
<!--	</script>-->
</logic:empty>
  </logic:present>

	</fieldset>


</div>
<logic:present name="sms">
<script type="text/javascript">
 if('<%=request.getAttribute("sms")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}
	
	</script>
</logic:present>

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	
</div>

</body>
</html:html>
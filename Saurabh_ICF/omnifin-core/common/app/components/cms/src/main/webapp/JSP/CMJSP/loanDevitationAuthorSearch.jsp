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
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

com.login.roleManager.UserObject userobj=(com.login.roleManager.UserObject)session.getAttribute("userobject");
if(userobj!=null){
			
			session.setAttribute("userId", userobj.getUserId());
		}
%>
   
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
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
		
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

	<body onunload="closeAllLovCallUnloadBody();" oncontextmenu="return false" onload="enableAnchor();document.getElementById('CreditForm').dealnoButton.focus();init_fields();">
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/creditListAction" method="post" styleId="CreditForm">
	
	<input type="hidden" name="contextPath" id="contextPath" value="<%=path %>" />
		  
	   <fieldset>	  
	<legend><bean:message key="lbl.deviationAuthor"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%">
		   
	<bean:message key="lbl.dealNo"/></td>
	<td width="35%">
		 <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	     <html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1"/>
		 <html:hidden  property="lbxDealNo" styleId="lbxDealNo" />

		 <html:button property="dealnoButton" styleId="dealnoButton" onclick="closeLovCallonLov1();openLOVCommon(414,'CreditForm','dealNo','userId','dealNo', 'userId','','','hCommon');" value=" " styleClass="lovbutton"></html:button>

		<%--<img onclick="openLOVCommon(102,'CreditForm','dealNo','','', '','','','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>  
        		  
	
	</td>
	<td><bean:message key="lbl.loanNumber"/> </td>
	<td>
	         <input type="hidden" name="hCommon" id="hCommon"/>   
	         <html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
          	  <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />

          	   <html:button property="loanButton" styleId="loanButton" onclick="closeLovCallonLov('dealNo');openLOVCommon(416,'CreditForm','loanNo','dealNo','lbxLoanNoHID', 'lbxDealNo','Select Deal LOV','','customerName');" value=" " styleClass="lovbutton"></html:button>

		<%--<img onclick="openLOVCommon(77,'CreditForm','loanNo','dealNo','lbxLoanNoHID', 'lbxDealNo','Select Deal LOV','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>  
          	          
         </td>				
	        
		</tr>
	  <tr>
		<td>
		   
	<bean:message key="lbl.agrementDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
			<td  > 
	<html:text styleClass="text" property="agrementDate" styleId="agrementDate"  maxlength="10" onchange="checkDate('agrementDate');"/> </td>
		   <td>
		   
		   
	<bean:message key="lbl.customerName"/> </td>
			<td  > 
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="50" /> </td>     
	 </tr>
		 <tr>
				  
		   <td>
		   
	<bean:message key="lbl.product"/>  </td>
		<td > 
		 <html:text  property="product" styleId="product" styleClass="text" readonly="true" tabindex="-1"/>
         <html:hidden  property="lbxProductID" styleId="lbxProductID" />

          <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(149,'CreditForm','product','userId','scheme', 'lbxscheme|userId','','','hCommon');" value=" " styleClass="lovbutton"></html:button>

		<%--<img onclick="openLOVCommon(149,'','product','','scheme', '','','','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>    
		</td>
		 <td><bean:message key="lbl.scheme"/>  </td>
		 <td  > 
	      <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />

          		    <html:button property="schemeButton" styleId="dealButton" onclick="closeLovCallonLov('product');openLOVCommon(22,'CreditForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','hCommon');" value=" " styleClass="lovbutton"></html:button>

		<%--<img onclick="openLOVCommon(22,'CreditForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','hCommon')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>    
	
	     </td>
		</tr>
		<%--
		
		<tr>
            <td>
      		<bean:message key="lbl.userName" />
      	    </td>
			<td>
            <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
            <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
     		<html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
     		<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
      		<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'CreditForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
		</tr>
		
		 --%>
			
       
		
		
		<tr>

	      <td align="left" class="form2" colspan="1">
	   
		            <button type="button" name="search"  id="button" title="Alt+S" accesskey="S" class="topformbutton2" onclick="newSearchDeviationLoan('F');"><bean:message key="button.search" /></button>
		  	       
	      </td>
	 
		</tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
 	
			 
     <fieldset>	
		 <legend><bean:message key="lbl.deviationAuthor"/></legend>  

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <logic:notPresent name="list">
    <tr class="white2">
	 
        <td ><b><bean:message key="lbl.loan"/></b></td>
        <td><b><bean:message key="lbl.agrementDate"/></b></td>
        <td><b><bean:message key="lbl.customerName"/></b></td>
		<td><b><bean:message key="lbl.product"/> </b></td>
		<td><b><bean:message key="lbl.scheme"/> </b></td>
		<td><b><bean:message key="lbl.sancValidDate"/></b></td>
		<td><b><bean:message key="lbl.userName"/></b></td>
		
	</tr>
	<tr class="white2">
	<td colspan="7"> <bean:message key="lbl.noDataFound"/> </td>
	</tr>
 	</table>    </td>
	</tr>
	</logic:notPresent>
	</table>
	</td>
	</tr>
	</table>
	 <logic:present name="list">
<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/creditListAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="modifyNo" titleKey="lbl.loan"  sortable="true"  />
    <display:column property="agrementDate" titleKey="lbl.agrementDate"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="validSancTill" titleKey="lbl.sancValidDate"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>


<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td ><b><bean:message key="lbl.loan"/></b></td>
        <td><b><bean:message key="lbl.agrementDate"/></b></td>
        <td><b><bean:message key="lbl.customerName"/></b></td>
		<td><b><bean:message key="lbl.product"/> </b></td>
		<td><b><bean:message key="lbl.scheme"/> </b></td>
		<td><b><bean:message key="lbl.sancValidDate"/></b></td>
		<td><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	<tr class="white2">
	<td colspan="7"> <bean:message key="lbl.noDataFound"/> </td>
	</tr>
 </table>    </td>
</tr>
</table>
	
</logic:empty>

  </logic:present>



  </html:form>
  
</div>
<logic:present name="sms">
<script type="text/javascript">
//alert("hello");

	if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert('<bean:message key="msg.sanctionValid" />');
	}
	
	</script>
</logic:present>


</div>
</body>
</html>
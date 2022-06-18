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
		request.setAttribute("no",no); 
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		
		<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jquery.simpletip-1.3.1.js"></script>
		<script type="text/javascript" src="<%=path%>/js/cmScript/loanEdit.js"></script>
		
		
		
		
		<!--[if IE 8]>
			<style type="text/css">
			.opacity
			{
			 	position:fixed;
  			 	_position:absolute;
  			 	top:0;
 			 	_top:expression(eval(document.body.scrollTop));
			}
			</style>
		<! [endif] -->		
</head>
<body onload="enableAnchor();">
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="contextPath" id="contextPath" value="<%=path %>" />
	<input type="hidden" name="hCommon" id="hCommon"/>   
	<input type="hidden" name="new" id="new" value="${new}"/>   
	<html:form action="/editLoanDetailAction" method="post" styleId="editLoanDetailAction">
	<fieldset><legend><bean:message key="lbl.accDetail"/></legend>         
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>		
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td><bean:message key="lbl.loanNumber"/></td>
				<td>
				    <html:text property="loanNo" styleId="loanNo" styleClass="text" value="" readonly="true" tabindex="-1"/>
				 	<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
		 			<logic:notPresent name="editLoanAuthor">
		 				<html:button property="dealButton" styleId="loanButton" onclick="openLOVCommon(2003,'editLoanDetailAction','loanNo','','', '','','','customerName');" value=" " styleClass="lovbutton"></html:button>
<!--		 			<logic:present name="editLoanMaker">-->
<!--		 			<logic:notPresent name="newCase">-->
<!--		 				<html:button property="dealButton" styleId="loanButton" onclick="openLOVCommon(2001,'editLoanDetailAction','loanNo','','', '','','','customerName');" value=" " styleClass="lovbutton"></html:button>-->
<!--		 			</logic:notPresent>-->
<!--		 			<logic:present name="newCase">-->
<!--		 				<html:button property="dealButton" styleId="loanButton" onclick="openLOVCommon(2003,'editLoanDetailAction','loanNo','','', '','','','customerName');" value=" " styleClass="lovbutton"></html:button>-->
<!--		 			</logic:present>-->
<!--		 			</logic:present>-->
		 			</logic:notPresent>
		 			<logic:notPresent name="editLoanMaker">
		 			<logic:present name="editLoanAuthor">
		 				<html:button property="dealButton" styleId="loanButton" onclick="openLOVCommon(2002,'editLoanDetailAction','loanNo','','', '','','','customerName');" value=" " styleClass="lovbutton"></html:button>
		 			</logic:present>
		 			</logic:notPresent>
		 		</td>		 			                                                      
				<td><bean:message key="lbl.customerName"/></td>
		  		<td><html:text styleClass="text" property="customerName" styleId="customerName"  readonly="true" value="" maxlength="50" /></td>	
		  	</tr>
<!--			<tr>-->
<!--				<td><bean:message key="lbl.product"/></td>-->
<!--				<td> -->
<!--			 		<html:text  property="product" styleId="product" styleClass="text" readonly="true" value="" tabindex="-1"/>-->
<!--         			<html:hidden  property="lbxProductID" styleId="lbxProductID" value=""/>-->
<!--         			<logic:notPresent name="editLoanAuthor">-->
<!--		 			<logic:present name="editLoanMaker">-->
<!--		 			<logic:notPresent name="newCase">-->
<!--		 				<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(2004,'editLoanDetailAction','product','','', '','','','lbxProductID');" value=" " styleClass="lovbutton"></html:button>-->
<!--		 			</logic:notPresent>-->
<!--		 			<logic:present name="newCase">-->
<!--		 				<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(2005,'editLoanDetailAction','product','','', '','','','lbxProductID');" value=" " styleClass="lovbutton"></html:button>-->
<!--		 			</logic:present>-->
<!--		 			</logic:present>-->
<!--		 			</logic:notPresent>-->
<!--		 			<logic:notPresent name="editLoanMaker">-->
<!--		 			<logic:present name="editLoanAuthor">-->
<!--		 				<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(2006,'editLoanDetailAction','product','','', '','','','lbxProductID');" value=" " styleClass="lovbutton"></html:button>-->
<!--		 			</logic:present>-->
<!--		 			</logic:notPresent>-->
<!--		 		</td>-->
<!--		 		<td><bean:message key="lbl.scheme"/>  </td>-->
<!--		 		<td> -->
<!--		      		<html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" value="" tabindex="-1"/>-->
<!--          			<html:hidden  property="lbxscheme" styleId="lbxscheme" value="" />-->
<!--	   		 		<html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('product');openLOVCommon(22,'editLoanDetailAction','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','hCommon');" value=" " styleClass="lovbutton"></html:button>-->
<!--				</td>-->
<!--	 		</tr>-->
			<tr>
				<td align="left" class="form2" colspan="4">
	       			<button type="button" name="search"  title="Alt+S" accesskey="S" id="searchButton" class="topformbutton2" onclick="return editLoanSearch('${new}');"><bean:message key="button.search" /></button>
<!--	       			<logic:notPresent name="editLoanAuthor">-->
<!--	       			<logic:notPresent name="newCase">-->
<!--	       				<button type="button" name="new"  title="Alt+N" accesskey="N" id="newButton" class="topformbutton2" onclick="return newEditLoan();"><bean:message key="button.new" /></button>-->
<!--	       			</logic:notPresent>-->
<!--	       			</logic:notPresent>-->
	       		</td>
	 		</tr>
			</table>
		</td>
	</tr>
	</table>	 
	</fieldset>		
 	<fieldset><legend><bean:message key="lbl.loanDetail"/></legend>  
 	
 	<logic:present name="list">
		<logic:notEmpty name="list"> 
  			<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/editLoanDetailAuthor.do?method=defaultEditLoanAuthor" >
    			<display:setProperty name="paging.banner.placement"  value="bottom"/>
    			<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    			<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    			<display:column property="modifyNo" titleKey="lbl.loan"  sortable="true"  />
    			<display:column property="agrementDate" titleKey="lbl.agrementDate"  sortable="true"  />
				<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
				<display:column property="product" titleKey="lbl.product"  sortable="true"  />
				<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
				<display:column property="validSancTill" titleKey="lbl.sancValidDate"  sortable="true"  />
				<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true" />
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
					<td colspan="7"><bean:message key="lbl.noDataFound" /></td>
				</tr>
 				</table>    
 			</td>
 		</tr>
		</table>
	</logic:empty>
  </logic:present>
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
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
	<td colspan="7"><bean:message key="lbl.noDataFound" /></td>
	</tr>
	</logic:notPresent>
	</table>
	</td>
	</tr>
	</table>
	</fieldset>
	</html:form>
	
	<logic:present name="sms">
		<script type="text/javascript">
			 if('<%=request.getAttribute("sms")%>'=='Locked')
				alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
			 else if('<%=request.getAttribute("sms")%>'=='E')
				alert('<bean:message key="msg.sanctionValid" />');			
		</script>
	</logic:present>
	<logic:present name="editSuccess">
		<script type="text/javascript">
			alert('Record is Forwarded Successfully ');			
		</script>
	</logic:present>
	<logic:present name="errorMsg">
		<script type="text/javascript">
			alert('<%=request.getAttribute("errorMsg")%>');
		</script>
	</logic:present>
	
	
</body>
</html>
<%-- --%>
<!--Author Name :Richa-->
<!--Date of Creation : 06-Aug-2013-->
<!--Purpose  : Case Marking Search->
<!--Documentation : -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/RepoBillingApprovalMaker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
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

<body onload="enableAnchor();lovHideShow();document.getElementById('loanNoButton').focus();">
<html:form action="/repoBillingApprovalMakerDispatch" styleId="RepoBillingApprovalSearchForm" method="post" > 	
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>

<fieldset>	  
	
		<legend>
		<bean:message key="lbl.repoBillingApprovalMaker"/>
		</legend>
       
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		
		<tr>
		<td width="20%"><bean:message key="lbl.loanNo"/></td>
		<td width="35%"  >
			
			<html:text property="searchLoanNo" styleClass="text" styleId="searchLoanNo" maxlength="10" readonly="true"  tabindex="-1" value=""/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value=""/>
			<html:button property="dealButton" style = "" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(580,'RepoBillingApprovalSearchForm','searchLoanNo','','', '','','','searchCustomerName')" value=" " styleClass="lovbutton"></html:button>
	 </td>
	 

	  <td width="17%"><bean:message key="lbl.customerName"/></td>
		<td width="28%" ><html:text property="searchCustomerName" tabindex="2"  styleClass="text" styleId="searchCustomerName" maxlength="50" value=""/></td> 
	   
	    </tr>

 <tr>
		  <td align="left" class="form2" colspan="4">
		   <button type="button" name="search"  id="button" title="Alt+S" accesskey="S" class="topformbutton2" onclick="return searchRepoBillingApprovalMaker('<bean:message key="lbl.selectLoanNo" />');"><bean:message key="button.search" /></button>
		   <button type="button"  name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="return newAddRepoBillingApproval()" ><bean:message key="button.new" /></button></td>
		  
		  </tr>
		</table>
		
	      </td>
	</tr>
	
	
	</table>
	 
	</fieldset>

<br/>

<fieldset>
		 <legend><bean:message key="lbl.repoBillingApprovalMaker"/></legend> 

 <logic:present name="detailList">

   <logic:notEmpty name="detailList">

  <display:table  id="detailList"  name="detailList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${detailList[0].totalRecordSize}" requestURI="/caseMarkingMakerBehind.do" >

    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="loanNo" titleKey="lbl.loanNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	<display:column property="balancePrincipal" titleKey="lbl.balancePrincipal"  sortable="true"  />
	
	
	
</display:table>
</logic:notEmpty>
<logic:empty name="detailList">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.loanNo" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.customerName" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;" align="center">
									<b><bean:message key="lbl.loanAmount" /> <br> </b>
									</td>
																		
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.balancePrincipal" /> <br> </b>
									</td>
									
							</tr>
			                <tr class="white2" >
	                                  <td colspan="7"><bean:message key="lbl.noDataFound" /></td>
                            </tr>
			            </table>
						</td>
					</tr>
				</table>
</logic:empty>

  </logic:present>
</fieldset>    
           
	</html:form>		
	<logic:present name="sms">
<script type="text/javascript">

    
	
	
	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
	
	
	
</script>
</logic:present>		
  </body>
		</html:html>
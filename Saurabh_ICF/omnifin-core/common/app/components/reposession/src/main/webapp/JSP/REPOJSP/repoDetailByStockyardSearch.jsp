<!--Author Name :Richa-->
<!--Date of Creation : 15-Apr-2013-->
<!--Purpose  : Legal Notice Initiation Search->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoDetailByStockyard.js"></script>
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

<body onload="enableAnchor();document.getElementById('loanNoButton').focus();">
<html:form action="/repoDetailByStockyardDispatch" styleId="repoDetailByStockyardForm" method="post" > 	
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.repoDetailByStockyardSearch"/></legend>
	<table width="100%">
	
	<tr>
	
	
	<td><bean:message key="lbl.repoId" /></td>
			
	<td><html:text property="searchRepoId" styleClass="text" styleId="searchRepoId" maxlength="10" /></td> 
	
	<td><bean:message key="lbl.loanNo" /></td>
	
		
	<td>
		<html:text property="loanNo" styleClass="text" styleId="loanNo" />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(1611,'repoDetailByStockyardForm','loanNo','','lbxLoanIdSearch','','','','customerName');" value=" " styleClass="lovbutton"> </html:button>
		<input type="hidden" name = "lbxLoanIdSearch" id = "lbxLoanIdSearch"  />
		<input type="hidden" name="customerName" id="customerName"/>
	</td>
	
																								
    </tr>
    
    	
    
	 <tr>
	 <td>
	 	  <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return fnSearchRepoDetailByStockyard('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
	 	  

	</tr>
	 </table>
</fieldset>

<br/>

<fieldset>
		 <legend><bean:message key="lbl.repoDetailByStockyard"/></legend> 

  <logic:present name="list">
   <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/repoDetailByStockyardDispatch.do?method=searchRepoDetailByStockyard" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="repoId" titleKey="lbl.repoId"  sortable="true"  />
	<display:column property="loanNo" titleKey="lbl.repoLoanNo"  sortable="true"  />
	<display:column property="customer" titleKey="lbl.repoCustomerName"  sortable="true"  />
	<display:column property="product" titleKey="lbl.repoProduct"  sortable="true"  />
	<display:column property="assetDescription" titleKey="lbl.repoAssetDesc"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
										<td width="220" class="white2" style="width: 220px;" align="center">
											<strong><bean:message key="lbl.repoId" /> </strong>
										</td>
										
										<td width="220" class="white2" style="width: 250px;" align="center">
											<b><bean:message key="lbl.repoLoanNo" /> <br> </b>
										</td>
																			
										<td width="220" class="white2" style="width: 250px;" align="center">
											<b><bean:message key="lbl.repoCustomerName" /> <br> </b>
										</td>
										
										<td width="220" class="white2" style="width: 250px;" align="center">
											<b><bean:message key="lbl.repoProduct" /> <br> </b>
										</td>
										
										<td width="220" class="white2" style="width: 250px;" align="center">
											<b><bean:message key="lbl.repoAssetDesc" /> <br> </b>
										</td>
										<tr class="white2" >
		                                  <td colspan="7"><bean:message key="lbl.noDataFound" /></td>
	                                    </tr>
									</tr>
			            </table>
						</td>
					</tr>
				</table>
</logic:empty>

  </logic:present>
</fieldset>    
           
	</html:form>		
	
  </body>
		</html:html>
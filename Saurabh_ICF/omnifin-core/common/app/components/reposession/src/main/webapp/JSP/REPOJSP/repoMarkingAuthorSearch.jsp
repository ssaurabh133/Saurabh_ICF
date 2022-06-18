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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoMarkingAuthor.js"></script>
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
<html:form action="/repoMarkingMakerDispatch" styleId="repoMarkingMakerForm" method="post" > 	
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.repoMarkingMakerSearch"/></legend>
	<table width="100%">
	
	<tr>
		<td><bean:message key="lbl.loanNo" /></td>
	<td>
		<html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxLoanId" styleId="lbxLoanId"  />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(1616,'repoMarkingMakerForm','loanNo','','lbxLoanId','','','','customerName');" value=" " styleClass="lovbutton"> </html:button>
		<input type="hidden" name="customerName" id="customerName"/>
	</td> 
	
	<td><bean:message key="lbl.repoProduct" /></td>
	
	<td>
		<html:text property="repoProduct" styleClass="text" styleId="repoProduct" maxlength="10" onblur="fnChangeCase('repoAssetChecklistMasterForm','repoProduct')"  readonly="true" />
		<html:button property="productButton" styleId="productButton" tabindex="1"  onclick="openLOVCommon(1618,'repoMarkingMakerForm','repoProduct','','', '','','','lbxProductSearchID')" value=" " styleClass="lovbutton"></html:button>  
		<html:hidden  property="lbxProductSearchID" styleId="lbxProductSearchID" value=""  />
	</td>
																								
    </tr>
    
    	
    
	 <tr>
	 <td>
	 	  <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return fnSearchRepoMarkingAuthor('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
	 	  

	</tr>
	 </table>
</fieldset>

<br/>

<fieldset>
		 <legend><bean:message key="lbl.repoMarkingMakerDetail"/></legend> 

  <logic:present name="list">
   <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/repoMarkingAuthorDispatch.do?method=searchRepoMarkingAuthor" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	
	<display:column property="loanNo" titleKey="lbl.loanNo"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
								
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.loanNo" /> <br> </b>
									</td>
									
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.product" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 220px;" align="center">
										<strong><bean:message key="lbl.scheme" /> </strong>
									</td>		
									
									<td width="220" class="white2" style="width: 220px;" align="center">
										<strong><bean:message key="lbl.branch" /> </strong>
									</td>	
									<td width="220" class="white2" style="width: 220px;" align="center">
										<strong><bean:message key="lbl.loanAmount" /> </strong>
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
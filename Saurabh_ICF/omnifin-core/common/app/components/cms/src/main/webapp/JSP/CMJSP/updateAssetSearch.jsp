<%-- --%>
<!--Author Name :Richa-->
<!--Date of Creation : 15-Apr-2013-->
<!--Purpose  : Legal Notice Initiation Search->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/updateAsset.js"></script>
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
<html:form action="/updateAssetSearch.do" styleId="updateAssetForm" method="post" > 	
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.updateAssetDetails"/></legend>
	<table width="100%">
	
	<tr>
		<td><bean:message key="lbl.loanNo" /></td>
	<td>
		<html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxLoanId" styleId="lbxLoanId"  />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(550,'updateAssetForm','loanNo','','lbxLoanId','','','','customerName');" value=" " styleClass="lovbutton"> </html:button>
		<input type="hidden" name="customerName" id="customerName"/>
		</td> 
																								
	<td><bean:message key="lbl.asset" /></td>

		<td>
		<html:text property="asset" styleId="asset"  styleClass="text"  tabindex="-1"/>

		</td> 
    </tr>
    
    </table>
	<table>	
    
	 <tr>
	 <td>
	 	  <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return fnSearchUpdateAsset('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
	 	  <button type="button" id="new" class="topformbutton2" title="Alt+N" accesskey="N" onclick="return fnNewUpdateAsset();" ><bean:message key="button.new" /></button>
	
	</tr>
	 </table>
</fieldset>

<br/>

<fieldset>
		 <legend><bean:message key="lbl.assetDetails"/></legend> 

  <logic:present name="list">
   <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/updateAssetSearch.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="loanNo" titleKey="lbl.loanNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="assetId" titleKey="lbl.assetId"  sortable="true"  />
	<display:column property="asset" titleKey="lbl.asset"  sortable="true"  />
	<display:column property="assetValue" titleKey="lbl.assetValue"  sortable="true"  />
	
	
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr class="white2">
									
									<td  align="center">
										<b><bean:message key="lbl.loanNo" /> <br> </b>
									</td>
									<td  align="center">
										<b><bean:message key="lbl.customerName" /> <br> </b>
									</td>
									<td align="center">
										<b><bean:message key="lbl.assetId" /> <br> </b>
									</td>
									<td  align="center">
									<b><bean:message key="lbl.asset" /> <br> </b>
									</td>
																		
									<td align="center">
										<b><bean:message key="lbl.assetValue" /> <br> </b>
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
       <%
		   Object ob=request.getAttribute("list").toString();
		   ob=null;
		%>
		
  </body>
		</html:html>
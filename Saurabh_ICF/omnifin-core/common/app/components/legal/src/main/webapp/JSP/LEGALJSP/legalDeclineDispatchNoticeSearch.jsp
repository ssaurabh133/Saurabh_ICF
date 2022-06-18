<!--Author Name : Nazia Parvez ->
<!--Date of Creation : 2-apr-2013-->
<!--Purpose  : Information of Legal Decline Dispatch Notice  -->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalDeclineDispatchNotice.js"></script>
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

<body onload="enableAnchor();document.getElementById('legalDeclineDispatchNoticeForm').loanNo.focus();init_fields();"><br>
<html:form action="/legalDeclineDispatchNoticeBehind" styleId="legalDeclineDispatchNoticeForm" method="post" > 	
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:hidden property="path" styleId="path" value="<%=request.getContextPath()%>"/>
<html:hidden property="path" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.legalDeclineDispatchNotice"/></legend>
	<table width="100%">
	
	<tr>
		<td><bean:message key="lbl.loanNo" /></td>
		
		<td>
		<html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxLoanId" styleId="lbxLoanId"  />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(1514,'legalNoticeInitiationMakerForm','loanNo','','lbxLoanId','','','','customerName');" value=" " styleClass="lovbutton"> </html:button>
		<input type="hidden" name="customerName" id="customerName"/>
		</td> 
																								
		<td><bean:message key="lbl.notice" /></td>
		
		<td>
		<html:hidden  property="noticeId" styleId="noticeId"   />
		<html:text property="noticeDesc" styleId="noticeDesc"  styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxNoticeCode" styleId="lbxNoticeCode"   />
		<html:button property="noticeButton" styleId="noticeButton" onclick="closeLovCallonLov1();openLOVCommon(1521,'legalNoticeInitiationMakerForm','noticeDesc','','','','','','lbxNoticeCode');" value=" " styleClass="lovbutton"> </html:button>
		</td> 
    </tr>
    
    </table>
	<table>
    
    	 <tr>
	 <td>
	 	  <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchLegalDeclineDispatchNoticeData('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
	 	 
	</tr>
    </table>
</fieldset>
<br/>

<fieldset>
		 <legend><bean:message key="lbl.legalDeclineDispatchNotice"/></legend> 

  <logic:present name="list">
   <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/legalDeclineDispatchNoticeBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="noticeIdlink" titleKey="lbl.noticeId"  sortable="true"  />
	<display:column property="loanNo" titleKey="lbl.loanNo"  sortable="true"  />
	<display:column property="customerId" titleKey="lbl.customerName"  sortable="true"  /><%-- added by Rohit --%>
	<display:column property="custRole" titleKey="lbl.applicantType"  sortable="true"  /><%-- added by Rohit --%>
	<display:column property="makerDate" titleKey="lbl.noticeInitiationDate"  sortable="true"  />
	<display:column property="makerId" titleKey="lbl.noticeInitiatedBy"  sortable="true"  />
	<display:column property="autherDate" titleKey="lbl.noticeApprovedDate"  sortable="true"  />
	<display:column property="autherId" titleKey="lbl.noticeApprovedBy"  sortable="true"  />
	<display:column property="reasonDesc" titleKey="lbl.initiateReason"  sortable="true"  />
	<display:column property="makerRemarks" titleKey="lbl.initiateRemark"  sortable="true"  />
	<display:column property="modeOfNotice" titleKey="lbl.modeOfNotice"  sortable="true"  />
	<display:column property="authorRemarks" titleKey="lbl.authorRemarks"  sortable="true"  />

	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
								<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.noticeId" /> <br> </b>
									</td>
									
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.loanNo" /> </strong>
									</td>
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.customerName" /> <br> </b>
									</td>
									
									<td width="220" class="white2" style="width: 250px;" align="center">
										<b><bean:message key="lbl.applicantType" /> <br> </b>
									</td>
									
																		
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.noticeInitiationDate" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.noticeInitiatedBy" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.noticeApprovedDate" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.noticeApprovedBy" /> <br> </b>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.initiateReason" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.initiateRemark" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.modeOfNotice" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.authorRemarks" /> <br> </b>
									</td>
									</tr>
									
									<tr class="white2" >
	                                  <td colspan="12"><bean:message key="lbl.noDataFound" /></td>
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
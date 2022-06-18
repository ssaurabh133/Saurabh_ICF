<%--

	Created By Sajog      
 	
 --%>

<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

	<title><bean:message key="title.name"/></title>
	<%
		ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");

			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
	%>
	<logic:present name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
			 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/cpPreDeal.js"></script>

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

<body onload="enableAnchor();diffLead(<%=session.getAttribute("leadpageid")%>);document.getElementById('leadButton').focus();">

			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />


	<html:form action="/preDealCapturingSearchBehind" styleId="leadCapturing" method="post">
		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
   <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		<html:hidden property="status" styleId="status" />
		<fieldset>
			<logic:present name="capturing"> 
				<legend id="leadCapt">
					<bean:message key="lbl.preDealcapturing" />
				</legend>
				<html:hidden property="leadGenerator1" styleClass="text" styleId="leadGenerator1" value=""/>
				<html:hidden property="status" styleId="status" value="P" />
			</logic:present>

			<logic:present name="allocation">
				<legend id="leadAllo">
					<bean:message key="lbl.leadallocation" />
				</legend>
				<html:hidden property="leadGenerator1" styleClass="text" styleId="leadGenerator1" value=""/>
				<html:hidden property="status" styleId="status" value="F" />
			</logic:present>
			<table width="100%">

				<tr>
					<td width="13%">
					
						<bean:message key="lbl.preDealNoLeadNo" />
					</td>
					<td width="13%">
					<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
						<html:text property="leadno" styleClass="text" styleId="leadno" value=""
							maxlength="50" readonly="true" tabindex="-1" />
						<logic:present name="capturing">
							<input type="button" class="lovbutton" id="leadButton"
								onclick="openLOVCommon(155,'leadCapturing','leadno','userId','', 'userId','','','customername')"
								value=" " tabindex="1" name="dealButton">
						</logic:present>
						<logic:present name="allocation">
							<input type="button" class="lovbutton" id="leadButton"
								onclick="openLOVCommon(163,'leadCapturing','leadno','userId','', 'userId','','','customername')"
								value=" " tabindex="1" name="dealButton">
						</logic:present>
						<input type="hidden" name="lbxLeadSearchDetail" 
							id="lbxLeadSearchDetail" value="" />
					</td>
					<td width="13%">
						<bean:message key="lbl.customername" />
					</td>
					<td width="13%">
						<html:text property="customername" styleClass="text"  value=""
							styleId="customername" maxlength="50" tabindex="2" />
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.products" />
					</td>
					<td width="13%">
						<html:text property="product" styleId="product" styleClass="text"  value=""
							readonly="true" tabindex="-1"></html:text>
						<logic:present name="capturing">
							<input type="button" class="lovbutton" id="lbxProduct"
								onclick="openLOVCommon(227,'leadCapturing','lbxProductID','','scheme','lbxscheme','','','product')"
								value=" " tabindex="3" name="dealButton">
							<html:hidden property="lbxProductID" styleId="lbxProductID" value="" 
								styleClass="text"></html:hidden>
						</logic:present>
						<logic:present name="allocation">
							<input type="button" class="lovbutton" id="lbxProduct"
								onclick="openLOVCommon(228,'leadCapturing','product','','scheme','lbxscheme','','','lbxProductID')"
								value=" " tabindex="3" name="dealButton">
							<html:hidden property="lbxProductID" styleId="lbxProductID" value=""
								styleClass="text"></html:hidden>
						</logic:present>

					</td>
					<td width="13%">
						<bean:message key="lbl.scheme" />
						<input type="hidden" name="lbxscheme" id="lbxscheme" tabindex="-1" />
					</td>
					<td width="13%">
						<html:text property="scheme" styleClass="text" styleId="scheme"  value=""
							maxlength="10" readonly="true" tabindex="-1" />
						<input type="button" class="lovbutton" id="lbxscheme"
							onclick="openLOVCommon(252,'leadCapturing','schemeId','product','lbxscheme', 'lbxProductID','Please Select Product','','scheme')"
							value=" " tabindex="4" name="dealButton">
						<input type="hidden" id="schemeId" name="schemeId" />
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.preDealSource" />
					</td>
					<td width="13%">
						<html:select styleId="leadGenerator" property="leadGenerator"
							styleClass="text" value=""
							tabindex="5">

							<html:option value="">---Select---</html:option>
							<html:option value="RM">
								RM / SALES EXEC
							</html:option>
							<html:option value="VENDOR">
								DEALER
							</html:option>
							<html:option value="BRANCH">
								TELECALLER / BRANCH
							</html:option>
							<html:option value="OTHERS">
								OTHERS
							</html:option>
							<html:optionsCollection name="sourceList" label="name" value="id" />
						</html:select>
					</td>

					<td width="13%">
						<bean:message key="lbl.preGendate" />
					</td>
					<td>
						<html:text property="applicationdate" tabindex="6"
							styleClass="text" styleId="applicationdate" maxlength="10"
							onchange="return checkDate('applicationdate');" />
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
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'leadCapturing','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>
   					  </td>
								
						</tr>
				<tr>
					<td>
						<button type="button" name="Search"  id="Search" title="Alt+S" accesskey="S" 
							class="topformbutton2" onclick="return searchLead(id);" tabindex="7"><bean:message key="button.search" /></button>
						<logic:present name="capturing">
						<button type="button" name="New" 
							id="New" class="topformbutton2" title="Alt+N" accesskey="N"
							onclick="return newLead();" tabindex="8"><bean:message key="button.new" /></button>
						</logic:present>
					</td>

				</tr>
			</table>
		</fieldset>

<br/>

			<fieldset>
				<legend>
					<bean:message key="lbl.details" />
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
								<logic:notPresent name="list">
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.preDealNo" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.customername" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.products" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.scheme" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.preDealSource" /> </strong>
									</td>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.preGendate" /> </strong>
									</td>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.userNam" /> </strong>
									</td>
									<tr class="white2" >
	                                  <td colspan="7"><bean:message key="lbl.noDataFound" /></td>
                                    </tr>
									</logic:notPresent>
							</tr>
			            </table>
						</td>
					</tr>
				</table>
		<logic:present name="list">
			
				<display:table id="list" name="list" style="width: 100%" 
					class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
					partialList="true" size="${list[0].totalRecordSize}"
					requestURI="/preDealCapturingSearchBehind.do">
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:setProperty name="locale.resolver"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:setProperty name="locale.provider"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:column property="leadno" titleKey="lbl.preDealNo"
						sortable="true" />
					<display:column property="applicationdate" titleKey="lbl.preGendate"
						sortable="true" />
					<display:column property="customername" titleKey="lbl.customername"
						sortable="true" />
					<display:column property="product" titleKey="lbl.products"
						sortable="true" />
					<display:column property="scheme" titleKey="lbl.scheme"
						sortable="true" />
					<display:column property="leadGenerator" titleKey="lbl.preDealSource"
						sortable="true" />
					<display:column property="reportingToUserId" titleKey="lbl.userNam"
						sortable="true" />
				</display:table>


		</logic:present>
			</fieldset>
	</html:form>

	<logic:present name="sms">
		<script type="text/javascript">

	
	
	if('<%=request.getAttribute("sms").toString()%>'=='Locked')
	{
		alert("<bean:message key="lbl.recordIsInUse" />");
		
	}
	
	
	
	
</script>
	</logic:present>
<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>

</body>
</html:html>
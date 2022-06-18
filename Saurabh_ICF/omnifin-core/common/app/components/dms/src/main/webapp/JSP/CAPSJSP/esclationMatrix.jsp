
<!--Author Name : Anil Yadav-->
<!--Date of Creation :20 October 2011  -->
<!--Purpose  : Esclation Matrix-->
<!--Documentation : -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html:html>

<head>

	<title><bean:message key="a3s.noida" /></title>


	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery.formatCurrency-1.3.0.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery.formatCurrency.all.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/capsScript/escalation.js"></script>

	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>


	<%
		ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");

			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
	%>
	<script type="text/javascript">
	

			
function fntab()
{
     if(document.getElementById('searchRecord').value =='S')	
     {
    	 document.getElementById('escalationsMatrixForm').loanSearchButton.focus();
     }
 
     return true;
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

<body onload="enableAnchor();fntab();">
	<html:form styleId="escalationsMatrixForm" method="post"
		action="/escalationsMatrixBehind.do?method=searchEscalationsMatrix">
		<input type="hidden" name="formatD" id="formatD"
			value="<bean:message key="lbl.dateFormat"/>" />
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<html:errors />
		<fieldset>
			<legend>
				<bean:message key="lbl.escalationsMatrix" />
			</legend>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td>
						<table border="0" cellpadding="1" cellspacing="1" width="100%">
							<tr>
            
				
								<td>
									<bean:message key="lbl.loanAccountNo" />
								</td>
								<td>
								   <input type="hidden" id="searchRecord" name="searchRecord" value="S" />
									<html:text styleClass="text" property="loanno" styleId="loanno"
										readonly="true" tabindex="-1" />
									<html:button property="loanSearchButton"
										styleId="loanSearchButton"
										onclick="openLOVCommon(195,'escalationsMatrixForm','loanno','','', '','','','lbxLoanno');closeLovCallonLov1();"
										value=" " styleClass="lovbutton">
									</html:button>
									<html:hidden property="lbxLoanno" styleId="lbxLoanno" />
								</td>
								<td>
									<bean:message key="lbl.product" />
								</td>


								<td>
									<html:select property="product" styleClass="text"
										styleId="product">
										<html:option value="">--Select--</html:option>
										<logic:present name="productList">
										<logic:notEmpty name="productList">
											<html:optionsCollection name="productList" label="productval"
												value="productid" />
											</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
								<td>
									<bean:message key="lbl.customername" />
								</td>

								<td>
									<html:text property="customername" styleClass="text"
										styleId="customername" maxlength="50" />

								</td>


							</tr>

							<tr>

								<td>
									<bean:message key="lbl.DPD1" />
								</td>
								<td>
									<html:text property="dpd2" style="text-align: right" styleClass="text" styleId="dpd2"
										maxlength="8" onkeypress="return isNumberKey(event);" />

								</td>

								
								<td>
									<bean:message key="lbl.DPD2" />
								</td>
								<td>
									<html:text property="dpd1" style="text-align: right" styleClass="text" styleId="dpd1"
										maxlength="8" onkeypress="return isNumberKey(event);" />

								</td>

								<td>
									<bean:message key="lbl.queue" />
								</td>
								<td>
									<html:text property="queue" styleClass="text" styleId="queue"
										maxlength="30" readonly="true" tabindex="-1" />

									<html:button property="queueButton" styleId="queueButton"
										onclick="openLOVCommon(189,'escalationsMatrixForm','queue','','', '','','','lbxQueuesearchId')"
										value=" " styleClass="lovbutton"></html:button>
									<input type="hidden" name="contextPath"
										value="<%=request.getContextPath()%>" id="contextPath" />
									<html:hidden property="lbxQueuesearchId"
										styleId="lbxQueuesearchId" />
								</td>
							</tr>

							<tr>

								
								<td>
									<bean:message key="lbl.overDueAmount1" />
								</td>
								<td>
									<input type="text" style="text-align: right"
										onfocus="keyUpNumber(this.value, event, 8,id);"
										onkeyup="checkNumber(this.value, event, 8,id);"
										onblur="formatNumber(this.value,id);"
										onkeypress="return numbersonly(event,id,8)" name="pos2"
										class="text" id="pos2" maxlength="10" />
								</td>

								<td>
									<bean:message key="lbl.overDueAmount2" />
								</td>
								<td>
									<input type="text" style="text-align: right"
										onfocus="keyUpNumber(this.value, event, 8,id);"
										onkeyup="checkNumber(this.value, event, 8,id);"
										onblur="formatNumber(this.value,id);"
										onkeypress="return numbersonly(event,id,8)" name="pos1"
										class="text" id="pos1" maxlength="10" />

								</td>

								<!-- 	<td>
										<bean:message key="lbl.iduser" />
									</td>
									<td>
										<html:text property="user" styleClass="text" styleId="user"
											maxlength="10" readonly="true" />
										<html:button property="userButton" styleId="userButton"
											value=" " styleClass="lovbutton"
											onclick="openLOVCommon(91,'escalationsMatrixForm','user','','', '','','','lbxUserSearchId')"></html:button>
										<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
										<html:hidden property="lbxUserSearchId" styleId="lbxUserSearchId" />
									</td>
									
									 -->
								<td>
									<bean:message key="lbl.actionNotTaken" />
								</td>
								<td>
									<html:text property="actionNotTaken" tabindex="8"
										onkeypress="return isNumberKey(event);" styleClass="text"
										styleId="actionNotTaken" maxlength="8" />
								</td>
							</tr>

							<!--	<tr>
									
									<td>
										<bean:message key="lbl.tos2" />
									</td>
									<td>
										<html:text property="tos2" styleClass="text" styleId="tos2"
											maxlength="18"  />
	
									</td>
									
									<td>
										<bean:message key="lbl.tos1" />
									</td>
									<td>
										<html:text property="tos1" styleClass="text" styleId="tos1"
											maxlength="18"  />
	
									</td>
	
									<td>
										<bean:message key="lbl.npaStage" />
									</td>
									<td>
										<html:select property="npastage" styleClass="text"
											styleId="npastage">
											<html:option value="">--Select--</html:option>
											<logic:present name="npastageList">
	
												<html:optionsCollection name="npastageList"
													label="npastageid" value="npastageval" />
	
											</logic:present>
										</html:select>
									</td>
								</tr>
								-->

							<tr>
								<td>
									<bean:message key="lbl.Type" />
								</td>
								<td>
									<html:select property="custype" styleClass="text"
										styleId="custype">
										<html:option value="">--Select--</html:option>
										<html:option value="I">INDIVIDUAL</html:option>
										<html:option value="C">CORPORATE</html:option>
									</html:select>
								</td>
								<td>
									<bean:message key="lbl.Category" />
								</td>
								<td>
									<html:select property="custcategory" styleClass="text"
										styleId="custcategory">
										<html:option value="">--Select--</html:option>

										<logic:present name="customercatList">
										<logic:notEmpty  name="customercatList">
											<html:optionsCollection name="customercatList" label="cstcat"
												value="cstcatval" />
										</logic:notEmpty>
										</logic:present>

									</html:select>
								</td>
								<td>
									<bean:message key="lbl.balancePrincipal" />
								</td>
								<td>
									<input type="text" style="text-align: right"
										onfocus="keyUpNumber(this.value, event, 8,id);"
										onkeyup="checkNumber(this.value, event, 8,id);"
										onblur="formatNumber(this.value,id);"
										onkeypress="return numbersonly(event,id,8)"
										name="balanceprincipal" class="text" id="balanceprincipal"
										maxlength="18" />
								</td>

							</tr>

							<tr>

								<td>
									<bean:message key="lbl.queueDateFrom" />
									<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
								</td>
								<td>
									<html:text property="queueDateFrom" tabindex="8"
										styleClass="text" styleId="queueDateFrom" maxlength="10"
										onchange="return checkDate('queueDateFrom');" />
								</td>
								<td>
									<bean:message key="lbl.queueDateTo" />
									<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
								</td>
								<td>
									<html:text property="queueDateTo" tabindex="8"
										styleClass="text" styleId="queueDateTo" maxlength="10"
										onchange="return checkDate('queueDateTo');"/>

								</td>


							</tr>

							<tr>
								<td>
									<!--  <input type="button" value="<bean:message key="lbl.search"/>"
										id="search" class="topformbutton2" title="Alt+S" accesskey="S"
										onclick="this.disabled='true';escalationSearch('<bean:message key="lbl.selectAtLeast" />');" />-->
										
<button type="button" class="topformbutton2"  id="search"  onclick="this.disabled='true';escalationSearch('<bean:message key="lbl.selectAtLeast" />');"
 title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
										
								</td>



							</tr>
						</table>
					</td>
				</tr>
			</table>
		</fieldset>


		<fieldset>
			<legend>
				<bean:message key="lbl.escalationMatrixDetail" />
			</legend>

			<logic:present name="list">

				<display:table id="list" name="list" style="width: 100%"
					class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
					partialList="true" size="${list[0].totalRecordSize}"
					requestURI="/escalationsMatrixBehind.do?method=searchEscalationsMatrix">
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:setProperty name="locale.resolver"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:setProperty name="locale.provider"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:column property="checkBoxDis" title="<input type='checkbox' id='chkId' name='chkId'  onclick='selectAll();'/>" style="text-align: center"/>
					<display:column property="gloanno" titleKey="lbl.loanAccountNo"
						sortable="true" />
					<display:column property="gdpd" titleKey="lbl.DPD" style="text-align: right" sortable="true" />
					<display:column property="gcustomername"
						titleKey="lbl.customername" sortable="true" />
					<display:column property="gpos" style="text-align: right" titleKey="lbl.overduePrincipal"
						sortable="true" />
					<display:column property="gqueue" titleKey="lbl.queue"
						sortable="true" />
					<display:column property="gassignto" titleKey="lbl.assignedto"
						sortable="true" />
					<display:column property="gactionottaken"
						titleKey="lbl.actionNotTaken" style="text-align: center" sortable="true" />
					<display:column property="gQueuedate"
						titleKey="lbl.queueAssignDate" style="text-align: center" sortable="true" />
				</display:table>
	

			<table>
			<tr>

				<td>
					<bean:message key="lbl.remark" /><font color="red">*</font>
				</td>
				<td>
					<html:textarea rows="5" cols="25" property="remarks" styleClass="text" styleId="remarks" value="" ></html:textarea>
				</td>
			</tr>
			<tr>

				<td>
					<input type="button" name="save"
						value="<bean:message key="lbl.accept"/>" id="save"
						class="topformbutton2" title="Alt+A" accesskey="A"
						onclick="this.disabled='true';saveEscalationMatrix();" />
				</td>

			</tr>
		</table>

		</logic:present>

		<logic:notPresent name="list">

			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<td class="gridtd">
							<table width="100%" cellspacing="1" cellpadding="1" border="0">
								<tbody>
									<tr align="center" class="white2">
										<td>
											<b>Select</b>
										</td>
										<td>
											<b><bean:message key="lbl.loanAccountNo" />
											</b>
										</td>
										<td>
											<b><bean:message key="lbl.DPD" />
											</b>
										</td>
										<td>
											<b><bean:message key="lbl.customername" />
											</b>
										</td>
										<td>
											<b><bean:message key="lbl.overduePrincipal" />
											</b>
										</td>
										<!--		<td><b><bean:message key="lbl.totalOutstanding" /></b></td>-->
										<td>
											<b><bean:message key="lbl.queue" />
											</b>
										</td>
										<td>
											<b><bean:message key="lbl.assignedto" />
											</b>
										</td>
										<td>
											<b><bean:message key="lbl.actionNotTaken" />
											</b>
										</td>
										<td>
											<b><bean:message key="lbl.queueAssignDate" />
											</b>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>

		</logic:notPresent>
		</fieldset>
	</html:form>
	<logic:present name="sms">
		<script type="text/javascript">

	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.escalationMatrixUpdated" />");

	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.errorSuccess" />");
		
    }

</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>
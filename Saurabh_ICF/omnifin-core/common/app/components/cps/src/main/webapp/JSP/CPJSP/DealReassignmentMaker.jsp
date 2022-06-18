<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String initiationDate = userobj.getBusinessdate();
			String branchId = userobj.getBranchId();
	%>


	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
    <script type="text/javascript" 
    	src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('dealReassignmentMakerForm').dealLov.focus();
     }
    
     return true;
}

</script>
<style type="text/css">
	.readonly{
			width:150px !important;
	}
</style>

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
<body onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();fntab();">
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="instmntSaveFlag" id="instmntSaveFlag" value="" />
	<div id=centercolumn>
		<div id=revisedcontainer>

			<!-- --------------------------------------- Deal Reassignment New --------------------------------- -->
<logic:notPresent name="dealReassignmentAuthor">
				<html:form action="/dealReassignmentMaker" method="post"
					styleId="dealReassignmentMakerForm">
					<fieldset>
						<legend>
							<bean:message key="lbl.dealReassignmentDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="N"/>
									<input type="hidden"  name="lbxBranchId" id="lbxBranchId" value="<%=branchId%>" />
									<input type="hidden"  name="hCommon" id="hCommon" value=""/>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="25%">
												<bean:message key="lbl.dealNo" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="dealNo"
													styleId="dealNo" readonly="true" maxlength="20" value="${requestScope.dealNo}" />
<%-- 												<html:button property="dealLov" styleId="dealLov" --%>
<%-- 												onclick="openLOVCommon(172,'dealReassignmentMakerForm','dealNo','','', '','','','customerName');closeLovCallonLov1();" --%>
<%-- 												value=" " styleClass="lovButton" /> --%>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxDealNo" styleId="lbxDealNo" value="${requestScope.dealId}"
													 />
											</td>
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="customerName" readonly="true"
												styleId="customerName" value="${requestScope.customerName}"  />
											</td>
										</tr>
										
									</table>
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td> -->
<!-- 								<button type="button" name="search" id="search"  -->
<!-- 									class="topformbutton2" title="Alt+S" accesskey="S" -->
<%-- 									onclick="return searchDealReassignmentMaker();"><bean:message key="button.search" /></button> --%>
<!-- 								</td> -->
<!-- 								<td></td> -->
<!-- 							</tr> -->
						</table>
						<fieldset>
							<legend>
							<bean:message key="lbl.dealMovement" />
							</legend>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="gridtd">
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
						    				<tr class="white2">
						      					<td><strong><bean:message key="lbl.seqNo"/></strong></td>
						        				<td><strong><bean:message key="lbl.stage"/></strong></td>
												<td><strong><bean:message key="lbl.dealReceived"/></strong></td>
												<td><strong><bean:message key="lbl.dealForwarded"/></strong></td>
												<td><strong><bean:message key="lbl.action"/></strong></td>
						        				<td><strong><bean:message key="lbl.dealForwardedUser"/></strong></td>
						        				<td><strong><bean:message key="lbl.pendingWith"/></strong></td>
						    				</tr>
    										<logic:present name="dealMovementList">
		 										<logic:iterate name="dealMovementList" id="subDealMovementList">
		 										<logic:equal name="subDealMovementList" property="action" value="INITIATED">
													<tr bgcolor="#8CD1E6">
													
										     			<td>${subDealMovementList.seqNo}</td>
											 			<td>${subDealMovementList.stage}</td>
											 			<td>${subDealMovementList.dealReceived}</td>
											 			<td>${subDealMovementList.dealForwarded}</td>
											 			<td>${subDealMovementList.action}</td>
											 			<td>${subDealMovementList.dealForwardedUser}</td>
											 			<td>${subDealMovementList.pendingWith}</td>
											 			
											 		</tr>
			 									</logic:equal>
			 									<logic:equal name="subDealMovementList" property="action" value="COMPLETED">
													<tr bgcolor="#aaff91">
													
										     			<td>${subDealMovementList.seqNo}</td>
											 			<td>${subDealMovementList.stage}</td>
											 			<td>${subDealMovementList.dealReceived}</td>
											 			<td>${subDealMovementList.dealForwarded}</td>
											 			<td>${subDealMovementList.action}</td>
											 			<td>${subDealMovementList.dealForwardedUser}</td>
											 			<td>${subDealMovementList.pendingWith}</td>
											 			
											 		</tr>
			 									</logic:equal>
										 		<logic:equal name="subDealMovementList" property="action" value="PENDING">
													<tr bgcolor="#E0E04E">
													
										     			<td>${subDealMovementList.seqNo}</td>
											 			<td>${subDealMovementList.stage}</td>
											 			<td>${subDealMovementList.dealReceived}</td>
											 			<td>${subDealMovementList.dealForwarded}</td>
											 			<td>${subDealMovementList.action}</td>
											 			<td>${subDealMovementList.dealForwardedUser}</td>
											 			<td>${subDealMovementList.pendingWith}</td>
											 			
											 		</tr>
										 		</logic:equal>
	    										</logic:iterate>
	   										</logic:present>
 										</table>
									</td>
								</tr>
							</table>
												<fieldset>
						<legend>
							<bean:message key="lbl.otherDetails" />
						</legend>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td>
							</td>
							</tr>
							<tr>
							<td>
							</td>
							</tr>
							<tr>
							<td>
							<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
							<bean:message key="lbl.reversingStage" />
							<font color="red">*</font>
							</td>
							<td >
							<html:select property="initiateStage" styleId="initiateStage" styleClass="text">
							<html:option value="">---SELECT---</html:option>
							<logic:present name="workFlowStage">
							<logic:notEmpty name="workFlowStage">
							<html:optionsCollection name="workFlowStage" value="id" label="name"/>
							</logic:notEmpty>
							</logic:present>																	
							</html:select>
							</td>
							<td >
							<bean:message key="lbl.stageAction" />
							<font color="red">*</font>
							</td>
							<td>
							<html:select property="stageAction" styleId="stageAction" styleClass="text" onchange="editEnableAndDisableField();" >							 
							<html:option value="">---SELECT---</html:option>
							<html:option value="UR">User ReAssignment</html:option>
							<html:option value="SC">Stage Correction</html:option>
							</html:select>
							</td>
							</tr>
							<tr id="1" style="display:none">
							<td>
							<bean:message key="lbl.userNam" />
							<font color="red">*</font>
							</td>
				     	 	<td id="2" style="display:none">

					  		<html:text property="userName" styleClass="text" styleId="userName2" readonly="true" tabindex="-1"/>
					  		<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(726,'dealReassignmentMakerForm','userName2','lbxDealNo','', 'lbxDealNo','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
   					  		</td>
   					  		<td id="3" style="display:none">

					  		<html:text property="userName" styleClass="text" styleId="userName3" readonly="true" tabindex="-1"/>
<%-- 					  		<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/> --%>
					  		<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(727,'dealReassignmentMakerForm','userName3','lbxDealNo','', 'lbxDealNo','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
   					  		</td>

   					  		<td id="6" style="display:none">

					  		<html:text property="userName" styleClass="text" styleId="userName6" readonly="true" tabindex="-1"/>
<%-- 					  		<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/> --%>
					  		<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(729,'dealReassignmentMakerForm','userName6','lbxDealNo','', 'lbxDealNo','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
   					  		</td>
   					  		<td id="7" style="display:none">

					  		<html:text property="userName" styleClass="text" styleId="userName7" readonly="true" tabindex="-1"/>
<%-- 					  		<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/> --%>
					  		<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(728,'dealReassignmentMakerForm','userName7','lbxDealNo','', 'lbxDealNo','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
   					  		</td>
   					  		<td id="8" style="display:none">

					  		<html:text property="userName" styleClass="text" styleId="userName8" readonly="true" tabindex="-1"/>
<%-- 					  		<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/> --%>
					  		<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(731,'dealReassignmentMakerForm','userName8','lbxDealNo','', 'lbxDealNo','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
   					  		</td>
   					  		<td id="9" style="display:none">

					  		<html:text property="userName" styleClass="text" styleId="userName9" readonly="true" tabindex="-1"/>
					  		<html:hidden property="userId" styleId="userId"  value=""/>
					  		<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(732,'dealReassignmentMakerForm','userName9','lbxDealNo','', 'lbxDealNo','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
   					  		</td>
   					  		
   					  		
							</tr>
							<tr id="10" style="display:none">
							<td>
							<bean:message key="lbl.active" />
							<font color="red">*</font>		
							</td>
							<td id="11" style="display:none">
							<input type="checkbox" name="codeStatus" id="codeStatus" checked="checked" disabled="disabled" />
							</td>
							</tr>
							
							<tr>
									<td>
										<button type="button" name="save" id="save" 
											class="topformbutton3" title="Alt+F" accesskey="F"
											onclick="return saveDealReassignmentEdit();"><bean:message key="button.savefrwd" /></button>
									</td>
							</tr>
							</table>	
</fieldset>							
						</fieldset>
						
<%-- 						<logic:present name="dealMovementListNew"> --%>
<!-- 						<fieldset> -->
<!-- 							<legend> -->
<%-- 							<bean:message key="lbl.newDealMovement" /> --%>
<!-- 							</legend> -->
<!-- 							<table width="100%"  border="0" cellspacing="0" cellpadding="0"> -->
<!-- 								<tr> -->
<!-- 									<td class="gridtd"> -->
<!-- 										<table width="100%" border="0" cellspacing="1" cellpadding="1"> -->
<!-- 						    				<tr class="white2"> -->
<%-- 						      					<td><strong><bean:message key="lbl.select"/></strong></td> --%>
<%-- 						        				<td><strong><bean:message key="lbl.stage"/></strong></td> --%>
<%-- 												<td><strong><bean:message key="lbl.dealReceived"/></strong></td> --%>
<%-- 												<td><strong><bean:message key="lbl.dealForwarded"/></strong></td> --%>
<%-- 												<td><strong><bean:message key="lbl.action"/></strong></td> --%>
<%-- 						        				<td><strong><bean:message key="lbl.dealForwardedUser"/></strong></td> --%>
<!-- 						    				</tr> -->
    										
<%-- 		 										<logic:iterate name="dealMovementListNew" id="subDealMovementListNew"> --%>
<!-- 													<tr class="white2"> -->
<!-- 										     			<td> -->
<%-- 										     				<input id="checkId" type="radio" value="${subDealMovementListNew.dealMovementId}" name="checkId"> --%>
<!-- 										     			</td> -->
<%-- 											 			<td>${subDealMovementListNew.stage}</td> --%>
<%-- 											 			<td>${subDealMovementListNew.dealReceived}</td> --%>
<%-- 											 			<td>${subDealMovementListNew.dealForwarded}</td> --%>
<%-- 											 			<td>${subDealMovementListNew.action}</td> --%>
<%-- 											 			<td>${subDealMovementListNew.dealForwardedUser}</td> --%>
<!-- 											 		</tr> -->
<%-- 	    										</logic:iterate> --%>
	   										
<!--  										</table> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td> -->
<!-- 										<button type="button" name="edit" id="edit"  -->
<!-- 											class="topformbutton2" title="Alt+E" accesskey="E" -->
<%-- 											onclick="return editDealReassignmentMaker();"><bean:message key="button.edit" /></button> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 							</table>						 -->
<!-- 						</fieldset> -->
<%-- 						</logic:present> --%>
					</fieldset>
				</html:form>
</logic:notPresent>
<!--  Deal Reassignment Author -->
<logic:present name="dealReassignmentAuthor">
<html:form action="/dealReassignmentMaker" method="post"
					styleId="dealReassignmentMakerForm">
					<fieldset>
						<legend>
							<bean:message key="lbl.dealReassignmentDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
									<input type="hidden" id="modifyRecord" name="modifyRecord" value="N"/>
									<input type="hidden"  name="lbxBranchId" id="lbxBranchId" value="<%=branchId%>" />
									<input type="hidden"  name="hCommon" id="hCommon" value=""/>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="25%">
												<bean:message key="lbl.dealNo" />
												<font color="red">*</font>
											</td>
											<td width="25%">
												<html:text styleClass="text" property="dealNo"
													styleId="dealNo" readonly="true" maxlength="20" value="${sessionScope.dealNo}" />
<%-- 												<html:button property="dealLov" styleId="dealLov" --%>
<%-- 												onclick="openLOVCommon(172,'dealReassignmentMakerForm','dealNo','','', '','','','customerName');closeLovCallonLov1();" --%>
<%-- 												value=" " styleClass="lovButton" /> --%>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxDealNo" styleId="lbxDealNo" value="${sessionScope.dealId}"
													 />
											</td>
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											<td width="25%">
												<html:text styleClass="text" property="customerName" readonly="true"
												styleId="customerName" value="${sessionScope.customerName}"  />
											</td>
										</tr>
										
									</table>
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td> -->
<!-- 								<button type="button" name="search" id="search"  -->
<!-- 									class="topformbutton2" title="Alt+S" accesskey="S" -->
<%-- 									onclick="return searchDealReassignmentMaker();"><bean:message key="button.search" /></button> --%>
<!-- 								</td> -->
<!-- 								<td></td> -->
<!-- 							</tr> -->
						</table>
						<fieldset>
							<legend>
							<bean:message key="lbl.dealMovement" />
							</legend>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="gridtd">
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
						    				<tr class="white2">
						      					<td><strong><bean:message key="lbl.seqNo"/></strong></td>
						        				<td><strong><bean:message key="lbl.stage"/></strong></td>
												<td><strong><bean:message key="lbl.dealReceived"/></strong></td>
												<td><strong><bean:message key="lbl.dealForwarded"/></strong></td>
												<td><strong><bean:message key="lbl.action"/></strong></td>
						        				<td><strong><bean:message key="lbl.dealForwardedUser"/></strong></td>
						        				<td><strong><bean:message key="lbl.pendingWith"/></strong></td>
						    				</tr>
    										<logic:present name="dealMovementList">
		 										<logic:iterate name="dealMovementList" id="subDealMovementList">
		 										<logic:equal name="subDealMovementList" property="action" value="INITIATED">
													<tr bgcolor="#8CD1E6">
													
										     			<td>${subDealMovementList.seqNo}</td>
											 			<td>${subDealMovementList.stage}</td>
											 			<td>${subDealMovementList.dealReceived}</td>
											 			<td>${subDealMovementList.dealForwarded}</td>
											 			<td>${subDealMovementList.action}</td>
											 			<td>${subDealMovementList.dealForwardedUser}</td>
											 			<td>${subDealMovementList.pendingWith}</td>
											 			
											 		</tr>
			 									</logic:equal>
			 									<logic:equal name="subDealMovementList" property="action" value="COMPLETED">
													<tr bgcolor="#aaff91">
													
										     			<td>${subDealMovementList.seqNo}</td>
											 			<td>${subDealMovementList.stage}</td>
											 			<td>${subDealMovementList.dealReceived}</td>
											 			<td>${subDealMovementList.dealForwarded}</td>
											 			<td>${subDealMovementList.action}</td>
											 			<td>${subDealMovementList.dealForwardedUser}</td>
											 			<td>${subDealMovementList.pendingWith}</td>
											 			
											 		</tr>
			 									</logic:equal>
										 		<logic:equal name="subDealMovementList" property="action" value="PENDING">
													<tr bgcolor="#E0E04E">
													
										     			<td>${subDealMovementList.seqNo}</td>
											 			<td>${subDealMovementList.stage}</td>
											 			<td>${subDealMovementList.dealReceived}</td>
											 			<td>${subDealMovementList.dealForwarded}</td>
											 			<td>${subDealMovementList.action}</td>
											 			<td>${subDealMovementList.dealForwardedUser}</td>
											 			<td>${subDealMovementList.pendingWith}</td>
											 			
											 		</tr>
										 		</logic:equal>
	    										</logic:iterate>
	   										</logic:present>
 										</table>
									</td>
								</tr>
							</table>
						<fieldset>
						<legend>
							<bean:message key="lbl.otherDetails" />
						</legend>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
							<logic:present name="stageDetails">
							<tr></tr><tr></tr>
							<tr>
							<td>
							<bean:message key="lbl.reversingStage" />
							<font color="red">*</font>
							</td>
							<td >
							
							<html:text styleClass="text" property="initiateStageDesc" readonly="true"
							styleId="initiateStageDesc" value="${stageDetails[0].initiateStageDesc}"  />
							
							<html:hidden styleClass="text" property="initiateStage" 
							styleId="initiateStage" value="${stageDetails[0].initiateStage}"  />
							
							</td>
							<td >
							<bean:message key="lbl.stageAction" />
							<font color="red">*</font>
							</td>
							<td>
							
							<html:hidden styleClass="text" property="stageAction" 
							styleId="stageAction" value="${stageDetails[0].stageAction}"  />
							<html:text styleClass="text" property="stageActionDesc" readonly="true"
							styleId="stageActionDesc" value="${stageDetails[0].stageActionDesc}"  />
							
							</td>
							</tr>
							<%-- <logic:equal name="stageDetails" property="stageAction" value="A"> --%>
							<logic:present name="stageActionDesc1">
							<tr>
							<td>
							<bean:message key="lbl.userNam" />
							<font color="red">*</font>
							</td>
				     	 	<td>
							
							<html:text styleClass="text" property="userName" readonly="true"
							styleId="userName" value="${stageDetails[0].userName}"  />
							
							</td>
   					  		
							</tr>
							</logic:present>
							<logic:present name="stageActionDesc2">
							<%-- <logic:equal name="arrListobj" property="status" value="A"> --%>
							<tr >
							<td>
							<bean:message key="lbl.active" />
							<font color="red">*</font>		
							</td>
							<td >
							<input type="checkbox" name="codeStatus" id="codeStatus" checked="checked" disabled="disabled"  />
							</td>
							</tr>
							<%-- </logic:equal> --%>
							</logic:present>
					</logic:present>
							</table>	
						</fieldset>					
						</fieldset>
						
					</fieldset>
				</html:form>
</logic:present>
<!-- Deal Reassignment Author ends -->




			<!-- --------------------------------------- Edit Deal New Ends --------------------------------- -->


		</div>
	</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<script>
	setFramevalues("dealReassignmentMakerForm");
</script>
</body>
</html:html>
<logic:present name="delete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataDeleted" />");
		window.location = "<%=request.getContextPath()%>/editDealSearch.do?method=editDealMakerSearch";	
</script>
</logic:present>
<logic:present name="notDelete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataNtDeleted" />");
</script>
</logic:present>
<logic:present name="message">

<script type="text/javascript">
if("<%=request.getAttribute("message")%>"=="S")
{
	alert("<bean:message key="msg.ForwardSuccessfully" />");
	document.getElementById("dealReassignmentMakerForm").action="dealReassignmentSearch.do?method=dealReassignmentMakerSearch";
    document.getElementById("dealReassignmentMakerForm").submit();
}

else if("<%=request.getAttribute("message")%>"=="E")
{
	alert("<bean:message key="msg.DataNotSaved" />");
	document.getElementById("dealReassignmentMakerForm").action="dealReassignmentSearch.do?method=dealReassignmentMakerSearch";
    document.getElementById("dealReassignmentMakerForm").submit();
}
else if('<%=request.getAttribute("message")%>'=="PROCERR")
{
	alert('<%=request.getAttribute("status")%>');
	document.getElementById("dealReassignmentMakerForm").action="dealReassignmentSearch.do?method=dealReassignmentMakerSearch";
    document.getElementById("dealReassignmentMakerForm").submit();
}
</script>
</logic:present>
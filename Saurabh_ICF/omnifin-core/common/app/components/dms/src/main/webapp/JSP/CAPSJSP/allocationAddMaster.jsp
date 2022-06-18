<!--Author Name :Kanika Maheshwari-->
<!--Date of Creation :13 October 2011  -->
<!--Purpose  : Information of Allocation master search-->
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

	<title><bean:message key="a3s.noida" />
	</title>

	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/capsScript/allocation.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<%
		ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");

			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
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
<body onload="enableAnchor();allocationDetailsHideShow();setTotalAllocatedPercentage();init_fields();">


	<html:form styleId="AllocationForm" method="post"
		action="/allocationMasterSearch">
     <input type="hidden" name="psize" id="psize"/>
     <input type="hidden" name="alloctionGroupId" value="${requestScope.groupId}" id="alloctionGroupId" />

<fieldset>
			<legend>
				<bean:message key="lbl.queueAllocationMaster" />
			</legend>
				<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
				<input type="hidden" name="allocId" value="${requestScope.searchList[0].allocId}" id="contextPath" />
				<input type="hidden" name="alloctionGroupId" value="${requestScope.groupId}" id="alloctionGroupId" />
			<table>
				<tr>
					<td width="13%">
						<bean:message key="lbl.queue" />
					</td>
				
	<logic:present name="modify">
		<td width="13%">
		<logic:present name="searchList">
			<logic:notEmpty name="searchList">
				<html:text property="queueIdSearch" styleClass="text" styleId="queueIdSearch" value="${requestScope.searchList[0].queuedesc}"
						 		maxlength="30" readonly="true" />
			</logic:notEmpty>
		</logic:present>
		<logic:notPresent name="searchList">
			<html:text property="queueIdSearch" styleClass="text" styleId="queueIdSearch" value="${requestScope.queuedesc}"
			readonly="true" />
		</logic:notPresent>
	<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
		<logic:present name="searchList">	
			<logic:notEmpty name="searchList">
				<html:hidden property="lbxQueuesearchId" styleId="lbxQueuesearchId" value="${requestScope.searchList[0].lbxQueuesearchId}" />
			</logic:notEmpty>
		</logic:present>
		<logic:notPresent name="searchList">
			<html:hidden property="lbxQueuesearchId" styleId="lbxQueuesearchId" value="${requestScope.queuecode}" />
		</logic:notPresent>
		</td>
		<logic:present name="searchList">
		<logic:notEmpty name="searchList">
		<!-- toni -->
			<td width="13%" ><bean:message key="lbl.branch" /></td>
      		 <td> <html:hidden  property="lbxBranchIds" styleId="lbxBranchIds" value="${requestScope.searchList[0].lbxBranchIds}" />
   			<html:select property="branchDesc" styleId="branchDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="branchList">
       			 <html:optionsCollection name="branchList" value="lbxBranchIds" label="branchDesc"/>
      		</logic:present>
     		</html:select>
      		<html:button property="userButton" styleId="userButtonEdit" onclick="return openMultiSelectLOVCommon(223,'userBranchMasterForm','branchDesc','','', '','','','lbxBranchIds');" value=" " styleClass="lovbutton"></html:button>
      		</td>
      	
      </logic:notEmpty>
    </logic:present>
    <logic:notPresent name="searchList">
   <!-- toni -->
			<td width="13%"><bean:message key="lbl.branch" /></td>
      			 <td> <html:hidden  property="lbxBranchIds" styleId="lbxBranchIds" value="${requestScope.searchList[0].lbxBranchIds}" />
   			<html:select property="branchDesc" styleId="branchDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="branchList">
       			 <html:optionsCollection name="branchList" value="lbxBranchIds" label="branchDesc"/>
      		</logic:present>
     		</html:select>
      		<html:button property="userButton" styleId="userButtonEdit" onclick="return openMultiSelectLOVCommon(223,'userBranchMasterForm','branchDesc','','', '','','','lbxBranchIds');" value=" " styleClass="lovbutton"></html:button>
      		</td>
      	
    </logic:notPresent>
<!--Surendra Code Start Here -->
    <logic:present name="searchList">
   <tr>
		<logic:notEmpty name="searchList">
		
			<td width="13%" ><bean:message key="lbl.area" /></td>
			<!-- Rohit Changes start for collection bugList -->
      		<td width="13%">
      			<html:text property="areaCode" styleClass="text" styleId="areaCode" maxlength="10" readonly="true" tabindex="-1" value="" />
    			<html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal"  value=""/>
      		    <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(376,'AllocationForm','areaCode','','', '','','','lbxareaCodeVal')" value=" " styleClass="lovbutton"></html:button>
 
      		</td>
      		<%-- <td width="13%">
      			<html:text property="areaCode" styleClass="text" styleId="areaCode" maxlength="10" readonly="true" tabindex="-1" value="${requestScope.searchList[0].areaCode}" />
    			<html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal"  value="${requestScope.searchList[0].lbxareaCodeVal}"/>
      		</td>    --%>   
      				<!-- Rohit Changes end for collection bugList -->
      </logic:notEmpty>
      
        <td width="13%">
						<bean:message key="lbl.totalpercentage" />
					</td>
					<td width="13%">
						<input type="text" name="percent" id="percent" readonly="readonly" class="text"
							value="${requestScope.percent}"
							onkeypress="return numbersonly(event,id,3)"
							onblur="formatNumber(this.value,id);"
							onkeyup="checkNumber(this.value, event, 3,id);"
							onfocus="keyUpNumber(this.value, event, 3,id);" />
					</td>
					
					</tr>
					
    </logic:present>    
    <!--Surendra Code End Here -->
	</logic:present>

	
	
	<logic:notPresent name="modify">
	<td width="13%">
		<logic:present name="searchList">
			<logic:notEmpty name="searchList">
				<html:text property="queueIdSearch" styleClass="text" styleId="queueIdSearch" value="${requestScope.searchList[0].queuedesc}" maxlength="30"
							readonly="true" />
			</logic:notEmpty>
		</logic:present>
		<logic:notPresent name="searchList">
			<html:text property="queueIdSearch" styleClass="text" styleId="queueIdSearch" value="${requestScope.queuedesc}"
			readonly="true" />
		</logic:notPresent>
		<html:button property="queueButton" styleId="queueButton" onclick="openLOVCommon(358,'AllocationMasterSearchForm','queueIdSearch','','', '','','','lbxQueuesearchId')"
							value=" " styleClass="lovbutton"></html:button>
		<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
		<logic:present name="searchList">	
			<logic:notEmpty name="searchList">
				<html:hidden property="lbxQueuesearchId" styleId="lbxQueuesearchId" value="${requestScope.searchList[0].lbxQueuesearchId}" />
			</logic:notEmpty>
		</logic:present>
		<logic:notPresent name="searchList">
			<html:hidden property="lbxQueuesearchId" styleId="lbxQueuesearchId" value="${requestScope.queuecode}" />
		</logic:notPresent>
		</td>
		<logic:present name="searchList">
		<logic:notEmpty name="searchList">
		
			<td width="13%"><bean:message key="lbl.defaultBranchId" /></td>
      		<td width="13%">
      			<html:text property="branchId" styleClass="text" styleId="branchId" maxlength="10" readonly="true" tabindex="-1" value="${requestScope.searchList[0].branchId}" />
    			<html:hidden  property="lbxBranchId" styleId="lbxBranchId"  value="${requestScope.searchList[0].lbxBranchId}"/>
    			
      		</td>
      	
      </logic:notEmpty>
    </logic:present>
  	
   	 <logic:notPresent name="searchList">
  <!-- toni -->
			<td width="13%"><bean:message key="lbl.branch" /></td>
      		 <td> <html:hidden  property="lbxBranchIds" styleId="lbxBranchIds" value="${requestScope.searchList[0].lbxBranchIds}" />
   			<html:select property="branchDesc" styleId="branchDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="branchList">
       			 <html:optionsCollection name="branchList" value="lbxBranchIds" label="branchDesc"/>
      		</logic:present>
     		</html:select>
      		<html:button property="userButton" styleId="userButtonEdit" onclick="return openMultiSelectLOVCommon(223,'userBranchMasterForm','branchDesc','','', '','','','lbxBranchIds');" value=" " styleClass="lovbutton"></html:button>
      	
    	</logic:notPresent>
	</logic:notPresent>
	</tr>

<logic:notPresent name="searchList">
	<tr>
	      <!-- Surendra Added 21-07-2012-->
	         <td width="13%"><bean:message key="lbl.area" /></td>
      		<td width="13%">
      			<html:text property="areaCode" styleClass="text" styleId="areaCode" maxlength="10" readonly="true" tabindex="-1" value="" />
    			<html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal"  value=""/>
      		    <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(376,'AllocationForm','areaCode','','', '','','','lbxareaCodeVal')" value=" " styleClass="lovbutton"></html:button>
 
      		</td>
      		    <td width="13%">
						<bean:message key="lbl.totalpercentage" />
					</td>
					<td width="13%">
						<input type="text" name="percent" id="percent" readonly="readonly" class="text"
							value="${requestScope.searchList[0].percent}"
							onkeypress="return numbersonly(event,id,3)"
							onblur="formatNumber(this.value,id);"
							onkeyup="checkNumber(this.value, event, 3,id);"
							onfocus="keyUpNumber(this.value, event, 3,id);" />
					</td>
      		</tr>
      		<!-- Surendra Added -->
		</logic:notPresent>	

		         
					

<tr>
					<!-- Nishant space Starts -->
					<td><bean:message key="lbl.InsAllTo" /></td>
					<td>
				      	<html:select property="allocationType" styleId="allocationType" styleClass="text" value="${requestScope.searchList[0].allocationType}" onchange="return allocationDetailsHideShow();">
				      		<html:option value="OTHER">Other</html:option>
				      		<html:option value="RM">RM</html:option>
				      		<html:option value="RO">RO</html:option>
				      	</html:select>		
	     			</td>
					<!-- Nishant space end -->
					<td><bean:message key="lbl.active" /></td>
					<td>
					<logic:notPresent name="recStatus">
						<input type="checkbox" name="recStatus"  id="rec_status" checked="checked"/>
					</logic:notPresent>
						<logic:present  name="recStatus">
						<logic:equal value="A"  name="recStatus">
						<input type="checkbox" name="recStatus"  id="rec_status" checked="checked"/>
						</logic:equal>
						<logic:notEqual value="A" name="recStatus">
						<input type="checkbox" name="recStatus"  id="rec_status" />
						</logic:notEqual>
						</logic:present>
					</td>
								
				</tr>
<logic:notPresent name="modify">
	
</logic:notPresent>
      			<!-- Nishant space starts -->
      			<tr id="showHideSaveButton">
      				<logic:notPresent name="default">
						<logic:notPresent name="searchList">
									
							<td>
								<button type="button" name="save" value="Save" title="Alt+V"
									accesskey="V" id="save" onclick="saveAllocationRM();"
									class="topformbutton2"><bean:message key="button.save" /></button>
							</td>
						</logic:notPresent>
					</logic:notPresent>
					
					<logic:present name="searchList">
						<logic:notEmpty name="searchList">
							<td>
									<button type="button"  name="save" value="Save" title="Alt+V"
										accesskey="V" id="save" onclick="modifyAllocationRM();"
										class="topformbutton2"><bean:message key="button.save" /></button>
							</td>
											
						</logic:notEmpty>								
					</logic:present>
      			</tr>
      			<!-- Nishant space ends -->
			</table>
		</fieldset>

		<fieldset id="showHideAllocationDetail">
			<legend>
				<bean:message key="lbl.queueAllocationDetail" />
			</legend>
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<td class="gridtd">
							<table width="100%" cellspacing="1" cellpadding="1" border="0"
								id="gridTable">
								<tbody>
									<tr  class="white2">
										<td>
									<input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked();"/>
										</td>
										<td align="center">
											<b><bean:message key="lbl.userId" />
											</b>
										</td>
										<td align="center"><b>User Status</b></td>
										<td align="center">
											<b><bean:message key="lbl.allocpercentage" />
											</b>
										</td>
									</tr>
									<logic:notPresent name="default">
									<logic:notPresent name="searchList">
										<tr class="white1">
											<td >
												<input type="checkbox" name="chk" id="chk1" />
											</td>
											<td>
												<input type="text" name="userIdsearch" id="userIdsearch1"
													value="" readonly="readonly" class="text"  />
												<input type="hidden" name="lbxUserSearchId"
													id="lbxUserSearchId" value="" />
												<input type="hidden" name="lbxUserSearchIdd"
													id="lbxUserSearchIdd1" value="" />
												<input type="button" value="" name="userButton"
													id="userButton" class="lovbutton"
													onclick="openLOVCommon(20250,'AllocationMasterSearchForm','userIdsearch1','','','','','','lbxUserSearchIdd1','userStatus1')"
													class="lovbutton" />
											</td>
											<td>
												<input type="text" name="userStatus" readonly="readonly" class="text" id="userStatus1" value="" />
											</td>
											<td>
												<input type="text" name="allocpercentage" class="text"
													id="allocpercentage1" value="" onchange="totalpercent();"
													  onkeypress="return numbersonly(event,id,3);totalpercent();" onblur="formatNumber(this.value,id);" 
	           onkeyup="checkNumber(this.value, event, 3,id);totalpercent();" onfocus="keyUpNumber(this.value, event, 3,id);"
													onclick="numbersonly(event,id,3);"  />
											</td>
										</tr>
									
										
									</logic:notPresent>
									</logic:notPresent>
									<logic:present name="allocatedCollQueueDataList">
									<logic:notEmpty name="allocatedCollQueueDataList">
										<logic:iterate id="searchListObj" name="allocatedCollQueueDataList"
											indexId="count">
											<input type="hidden" name="allocId" id="allocId" value="${searchListObj.allocationId}" />
											<input type="hidden" name="allocIdList" id="allocIdList" value="${searchListObj.allocationId}" />
											<tr class="white1">
												<td>
													<input type="checkbox" name="chk"
														id="chk<%=count.intValue() + 1%>" value="${searchListObj.allocationId}"  />
														
												</td>
												<td>
													<input type="text" name="userIdsearch" class="text"
														id="userIdsearch<%=count.intValue() + 1%>"
														value="${searchListObj.lbxUserSearchId}" readonly="readonly" />
													<input type="hidden" name="lbxUserSearchId"
														id="lbxUserSearchId" value="" />
													<input type="hidden" name="lbxUserSearchIdd"
														id="lbxUserSearchIdd<%=count.intValue() + 1%>"
														value="${searchListObj.userId}" />
													<input type="button" value="" name="userButton"
														id="userButton" class="lovbutton"
														onclick="openLOVCommon(20250,'AllocationMasterSearchForm','userIdsearch<%=count.intValue() + 1%>','','','','','','lbxUserSearchIdd<%=count.intValue() + 1%>','userStatus<%=count.intValue() + 1%>')"
														class="lovbutton" />
												</td>
												<td>
													<input type="text" name="userStatus" readonly="readonly" class="text" id="userStatus<%=count.intValue() + 1%>" value="${searchListObj.userStatus}" />
												</td>
												<td>
													<input type="text" name="allocpercentage" class="text"
														id="allocpercentage<%=count.intValue() + 1%>"
														value="${searchListObj.allocationpercentage}"
														onchange="totalpercent();"
													  onkeypress="return numbersonly(event,id,3);totalpercent();" onblur="formatNumber(this.value,id);" 
	           onkeyup="checkNumber(this.value, event, 3,id);totalpercent();" onfocus="keyUpNumber(this.value, event, 3,id);"
													onclick="numbersonly(event,id,3);"  />
												</td>
											</tr>
										</logic:iterate>
										</logic:notEmpty>
									</logic:present>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
									<logic:notPresent name="default">
									<logic:notPresent name="searchList">
									<tr>
											<td>
												<button type="button" name="save" value="Save" title="Alt+V"
													accesskey="V" id="save" onclick="saveAllocation();"
													class="topformbutton2"><bean:message key="button.save" /></button>
											</td>
											
											<td>
												<button type="button" value="Add Row" tabindex="-1"
													class="topformbutton2" onclick="addallocationRow();" title="Alt+A"
													accesskey="A" ><bean:message key="button.addrow" /></button>
											</td>
											
											<td>
												<button type="button"  name="deletebutton" value="Delete Row"
													title="Alt+D" accesskey="D" tabindex="-1"
													class="topformbutton2" 
													onclick="removeallocationRow();totalpercent();" ><bean:message key="button.deleterow" /></button>
											</td>
											
										</tr>
										</logic:notPresent>
										</logic:notPresent>
										<logic:present name="searchList">
										<tr><logic:notEmpty name="searchList">
											<td>
												<button type="button"  name="save" value="Save" title="Alt+V"
													accesskey="V" id="save" onclick="modifyAllocation();"
													class="topformbutton2"><bean:message key="button.save" /></button>
											</td>
											<td>
												<button type="button" value="Add Row" tabindex="-1"
													class="topformbutton2" onclick="addallocationRow();" title="Alt+A"
													accesskey="A" ><bean:message key="button.addrow" /></button>
											</td>
											<td>
												<button type="button"  name="deletebutton" value="Delete Row"
													title="Alt+D" accesskey="D" tabindex="-1"
													class="topformbutton2" 
													onclick="removeallocationRow();totalpercent();" ><bean:message key="button.deleterow" /></button>
											</td>
												</logic:notEmpty>
										</tr>
									
										</logic:present>
		</fieldset>



	</html:form>

	<logic:present name="sms">
<script type="text/javascript">
if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
	
   else if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		document.getElementById("AllocationForm").action="allocationMasterSearch.do?method=openSearchJsp";
		document.getElementById("AllocationForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("AllocationForm").action="allocationMasterSearch.do?method=openSearchJsp";
		document.getElementById("AllocationForm").submit();
	
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		//alert("<bean:message key="msg.DataNotSaved" />");
		alert("<%=request.getAttribute("errorStr")%>");
	
	}     
	    
	
	
</script>
</logic:present>
		<logic:present name="PERCENT">
<script type="text/javascript">

 if('<%=request.getAttribute("PERCENT").toString()%>'=='P')
	{
		alert("<bean:message key="msg.percent" />");
	
	} 
	</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>
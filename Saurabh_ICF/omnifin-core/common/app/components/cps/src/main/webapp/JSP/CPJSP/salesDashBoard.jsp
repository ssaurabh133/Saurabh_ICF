<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<html>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>OmniFin Sales Dashboard</title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/contentstyle.css">
	<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dashboard/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dashboard/style5.css">
	<link href="<%=request.getContextPath()%>/dashboard/ui.dynatree.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/dashboard/prettify.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/dashboard/sample.css" rel="stylesheet" type="text/css">
	
	<script src="<%=request.getContextPath()%>/dashboard/jquery-1.12.0.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/dashboard/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/dashboard/jquery.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/dashboard/jquery-ui.custom.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/dashboard/jquery.cookie.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/dashboard/jquery.dynatree.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/dashboard/prettify.js" type="text/javascript"></script>		
	<script src="<%=request.getContextPath()%>/dashboard/sample.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/salesDashBoard.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<style>
		*{
			box-sizing:border-box;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function (){
			$('#sidebarCollapse').on('click', function () {
				$('#sidebar').toggleClass('active');
				$(this).toggleClass('active');
			});
			callUserHierarchyWebservice();
		});
	</script>
	</head>
	<body onload="enableAnchor();" style="font-family:Arial, Helvetica, sans-serif">
		<div  align="center" class="opacity" id="processingImage" style="z-index: 500 !important;"></div>
		<form  action="/salesDashBoardAction.do?method=showDefaultView" id="salesDashBoard" method="post">
			<input type="hidden" id="loginUserId" name="loginUserId" value="${loginUserId}"/>
			<input type="hidden" id="queue" name="queue" value="${queue}"/>
			<input type="hidden" id="userId" name="userId" value="${userId}"/>
			<input type="hidden" id="source" name="source" value="${source}"/>
			<input type="hidden" id="selectedUserIds" name="selectedUserIds" value=""/>
			<input type="hidden" id="selectedBranchIds" name="selectedBranchIds" value=""/>
			<input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
			
			<div class="wrapper">
				<nav id="sidebar" style="border:1px solid black">
					<div class="sidebar-header" align="center" style="background:#5892D2 !important;height:25px;border-bottom:1px solid black;">
						<table border="0" cellspacing="1" cellpadding="1" style="width:100%;height:100%">
							<tr valign="middle">
								<td width="30px">&nbsp;&nbsp;<input type="checkbox" name="branchAll" id="branchAll" value="" onclick="manageSelecteAllBranch(checked)">&nbsp;</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><font color="white" size="2">Branch Filter</font></strong></td>
							</tr>
						</table>
					</div>
					<div class="sidebar-header" align="center" style="background:#CCCCCC !important;height:150px;overflow-y: scroll">
						<table border="1" cellspacing="1" cellpadding="1" style="font-family:Arial, Helvetica, sans-serif;font-size: 12px;width:100%">
							<logic:present name="branchList">
								<logic:iterate id="list" name="branchList" indexId="count">
									<tr>
										<td>&nbsp;&nbsp;<input type="checkbox" name="branchCB" id="branchCB${count}" value="${list.branchId}" onclick="manageSelectedBranch(checked,'${list.branchId}')">&nbsp;</td>
										<td>&nbsp;${list.branchName}(${list.branchId})</td>
									</tr>
								</logic:iterate>
							</logic:present>
						</table>
					</div>
					<div class="sidebar-header" align="center" style="background:#5892D2 !important;height:25px;border-bottom:1px solid black;border-top:1px solid black;">
						<table border="0" cellspacing="1" cellpadding="1" style="width:100%;height:100%">
							<tr valign="middle">
								<td width="30px">&nbsp;&nbsp;&nbsp;</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;<strong><font color="white" size="2">User Filter</font></strong></td>
							</tr>
						</table>
					</div>
					<div id="tree" name="selNodes" style="height:275px;overflow-y: scroll"></div>
				</nav>
				<div id="content" style="height:100%;width:100%;background-color:#CCCCCC;border:1px solid black">
					<div style="width:100%;height:60px;background-color:#a4c8f8;border:1px solid black">
						<div style="width:50%;height:100%;float:left" align="left">
							<table style="height:100%;width:95%">
								<tr valign="middle">
									<td align="left" width="10px"></td>
									<td align="left" width="42px">
										<button type="button" id="sidebarCollapse" class="btn btn-info navbar-btn">
											<span></span>
											<span></span>
											<span></span>
										</button>
									</td>
									<td align="left">
										<b>Hide/Show Filter</b>
									</td>
								</tr>
							</table>
						</div>
						<div style="width:50%;height:100%;float:left" align="left">
							<table style="height:100%;width:95%">
								<tr valign="middle">
									<td align="right">
										<font size="5"><b>Dashboard</b></font>
									</td>
								</tr>
								<tr valign="middle">
									<td align="right">
									<logic:present name="switchAccess">
									<%
										String queue=(String)request.getAttribute("queue");
										if(StringUtils.equalsIgnoreCase(queue,"R")){
											%>
												<input type="radio" name="operationQueue" value="O" onclick="manageQueue(this.value);"><b>&nbsp;Own&nbsp;&nbsp;</b>
												<input type="radio" name="operationQueue" value="R" onclick="manageQueue(this.value);" checked><b>&nbsp;Root</b>
											<%
										}
										if(StringUtils.equalsIgnoreCase(queue,"O")){
											%>
												<input type="radio" name="operationQueue" value="O" onclick="manageQueue(this.value);" checked><b>&nbsp;Own&nbsp;&nbsp;</b>
												<input type="radio" name="operationQueue" value="R" onclick="manageQueue(this.value);" ><b>&nbsp;Root</b>
											<%
										}
									%>
									</logic:present>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('LD_PD_SB');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>1</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Draft Applications in tablet">
										<b>&nbsp;&nbsp;Leads (Applications) Pending Submission</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="LD_PD_SB">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('PRE_LGN_DL');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>2</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Total Deals Submitted but not logged in by CPC including the CPC queried Deals">
										<b>&nbsp;&nbsp;Total Pre-login Deals</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="PRE_LGN_DL">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('PRE_LGM_QR_DL');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>3</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Deals where queries raised by CPC are unresolved">
										<b>&nbsp;&nbsp;Pre-Login Queried Deals</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="PRE_LGM_QR_DL">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('OPEN_DEAL');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>4</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Deals pending login at CPC for over 3 days">
										<b>&nbsp;&nbsp;Open Deals > 3 days at CPC Desk</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="OPEN_DEAL">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('LD_LGM_MNTH');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>5</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Deals logged (IMD Author done) in by CPC">
										<b>&nbsp;&nbsp;Deals Logged in during the month</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="LD_LGM_MNTH">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('LGD_QRY_DL');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>6</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Deals where Query raised by Credit (Query module to have functionality of raising query to Sales/ CPC / Credit / Tech / Legal and FCU…Once the query is raised the case status is shown accordingly eg. Sales Query WIP, CPC Query WIP, Tech Query WIP etc). Deals showing in Sales WIP are available to Sales to reply">
										<b>&nbsp;&nbsp;Deals in query after Login</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="LGD_QRY_DL">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('LD_RJCT_CNCLD');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>7</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Deals which are cancelled or rejected">
										<b>&nbsp;&nbsp;Deals Rejected/ Cancelled in Current Month</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="LD_RJCT_CNCLD">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('DL_IN_CP');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>8</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Including the Deals in Query (For all previous months and current month)">
										<b>&nbsp;&nbsp;Deals in Credit Process</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="DL_IN_CP">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('LD_SNCD');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>9</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Approved Deals in current month">
										<b>&nbsp;&nbsp;Deals Sanctioned in Current Month</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="LD_SNCD">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('DL_SNCD_UN_DSBSD');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>10</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="Approved deals where CROP has not yet been done for all previous months and current month">
										<b>&nbsp;&nbsp;Deals Sanctioned Un-Disbursed</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="DL_SNCD_UN_DSBSD">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
					<div style="width:100%;height:08px;"></div>
					<div style="width:100%;height:42px;background-color:#ffffff">
						<a href="#" onclick="salesDashBoardItemDtl('LD_DSBSD');">
							<table style="height:100%;width:100%" border="1">
								<tr valign="middle">
									<td width="30px" align="center" style="color:black"><b>11</b></td>
									<td style="color:black" data-toggle="tooltip" data-placement="top" title="All Deals for which Disbursal Author has been done">
										<b>&nbsp;&nbsp;Deals Disbursed in Current Month</b>
									</td>
									<td width="70px" align="center" class="tooltiptext">
										<b id="LD_DSBSD">?</b>
									</td>
								</tr>
							</table>
						</a>
					</div>
				</div>
			</div>
		</form>
	</body>	
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/salesDashBoard.js"></script>
<title>Sales Dash Board Details</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/contentstyle.css">
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
</head>
	<body>
	<input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
				<fieldset>
				<logic:equal name="dashBoard" value="LD_PD_SB">
					<legend>Leads (Applications) Pending Submission</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
						<tr class="white2">
							<td><b>S. NO</b></td>
							<td><b>Application ID</b></td>
							<td><b>Case Name</b></td>
							<td><b>RO Name</b></td>
							<td><b>RM Name</b></td>
							<td><b>Branch</b></td>
							<td><b>Status</b></td>
							<td><b>Maker</b></td>
						</tr>
						<logic:present name="dashBoardDetail">
						<logic:iterate id="list" name="dashBoardDetail" indexId="count">
						<tr class="white1">
							<td>${count+1}</td>
							<td>${list.formNo}</td>
							<td>${list.customerName}</td>
							<td>${list.roName}</td>
							<td>${list.rmName}
							<td>${list.branchName}</td>
							<td>${list.status}</td>
							<td>${list.makerName}</td>
						</tr>
						</logic:iterate> 
						</logic:present>
						</table>
					</td></tr>
					</table>
				</logic:equal>
				<!-- 2 form -->				
				<logic:equal name="dashBoard" value="PRE_LGN_DL">				
				<legend>Total Pre-login Deals</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}
						<td>${list.branchName}</td>
						<td>
							<logic:equal name="list" property="status" value="QUERY WIP">
								<a href="#" onclick="openDashboardQueryDtl('${list.dealId}');"> ${list.status}</a>
							</logic:equal>
							<logic:notEqual name="list" property="status" value="QUERY WIP">
								${list.status}
							</logic:notEqual>

						</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>

		<!-- 3 form -->				
				<logic:equal name="dashBoard" value="PRE_LGM_QR_DL">				
				<legend>Pre-Login Queried Deals</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}
						<td>${list.branchName}</td>
						<td>
							<logic:equal name="list" property="status" value="QUERY WIP">
								<a href="#" onclick="openDashboardQueryDtl('${list.dealId}');"> ${list.status}</a>
							</logic:equal>
							<logic:notEqual name="list" property="status" value="QUERY WIP">
								${list.status}
							</logic:notEqual>

						</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				
				
				<!-- 4 form -->				
				<logic:equal name="dashBoard" value="OPEN_DEAL">				
				<legend>Open Deals > 3 days at CPC Desk</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Days Pending</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}
						<td>${list.branchName}</td>
						<td>${list.status}</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				
				
				<!-- 5 form -->				
				<logic:equal name="dashBoard" value="LD_LGM_MNTH">				
				<legend>Leads (Deals) Logged in during the month</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}
						<td>${list.branchName}</td>
						<td>
							<logic:equal name="list" property="status" value="QUERY WIP">
								<a href="#" onclick="openDashboardQueryDtl('${list.dealId}');"> ${list.status}</a>
							</logic:equal>
							<logic:notEqual name="list" property="status" value="QUERY WIP">
								${list.status}
							</logic:notEqual>

						</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				
				<!-- 6 form -->				
				<logic:equal name="dashBoard" value="LGD_QRY_DL">				
				<legend>Logged in Queried Deals</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}</td>
						<td>${list.branchName}</td>
						<td>
							<logic:equal name="list" property="status" value="QUERY WIP">
								<a href="#" onclick="openDashboardQueryDtl('${list.dealId}');"> ${list.status}</a>
							</logic:equal>
							<logic:notEqual name="list" property="status" value="QUERY WIP">
								${list.status}
							</logic:notEqual>

						</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				
				<!-- 7 form -->				
				<logic:equal name="dashBoard" value="LD_RJCT_CNCLD">				
				<legend>Leads (Deals) Rejected/ Cancelled in Current Month</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
						<td><b>Reason</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}</td>
						<td>${list.branchName}</td>
						<td>${list.status}</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
						<td>${list.reason}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				
				<!-- 8 form -->				
				<logic:equal name="dashBoard" value="DL_IN_CP">				
				<legend>Deals in Credit Process</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}
						<td>${list.branchName}</td>
						<td>
							<logic:equal name="list" property="status" value="QUERY WIP">
								<a href="#" onclick="openDashboardQueryDtl('${list.dealId}');"> ${list.status}</a>
							</logic:equal>
							<logic:notEqual name="list" property="status" value="QUERY WIP">
								${list.status}
							</logic:notEqual>

						</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				
				<!-- 9 form -->				
				<logic:equal name="dashBoard" value="LD_SNCD">				
				<legend>Leads (Deals) Sanctioned in Current Month</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}
						<td>${list.branchName}</td>
						<td>${list.status}</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				
				<!-- 10 form -->				
				<logic:equal name="dashBoard" value="DL_SNCD_UN_DSBSD">				
				<legend>Deals Sanctioned Un-Disbursed</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}
						<td>${list.branchName}</td>
						<td>${list.status}</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				
				<!-- 11 form -->				
				<logic:equal name="dashBoard" value="LD_DSBSD">				
				<legend>Deals in Disbursal Process in Current Month</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Deal ID</b></td>
						<td><b>Application ID</b></td>
						<td><b>Case Name</b></td>
						<td><b>RO Name</b></td>
						<td><b>RM Name</b></td>
						<td><b>Branch</b></td>
						<td><b>Status</b></td>
						<td><b>Month</b></td>
						<td><b>Maker</b></td>
					</tr>
					<logic:present name="dashBoardDetail">
					<logic:iterate id="list" name="dashBoardDetail" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.dealNo}</td>
						<td>${list.formNo}</td>
						<td>${list.customerName}</td>
						<td>${list.roName}</td>
						<td>${list.rmName}
						<td>${list.branchName}</td>
						<td>${list.status}</td>
						<td>${list.dealIntitionDate}</td>
						<td>${list.makerName}</td>
					</tr>
					</logic:iterate> 
					</logic:present>
					</table>
				</td></tr>
				</table>
				</logic:equal>
				</fieldset>
				
		<!-- WiP query Details -->
				<fieldset>
				<logic:present name="wipQueryDtl">						
				<legend>WIP QUERY DETAILS</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td class="gridtd">
					<table width="100%" border="0" cellspacing="1" cellpadding="1" id="DeviationTable">
					<tr class="white2">
						<td><b>S. NO</b></td>
						<td><b>Query Details</b></td>
						<td><b>Query Date</b></td>
						<td><b>Raised By</b></td>
						<td><b>Raised To</b></td>
						<td><b>Department</b></td>
					</tr>
					<logic:iterate id="list" name="wipQueryDtl" indexId="count">
					<tr class="white1">
						<td>${count+1}</td>
						<td>${list.queryDtl}</td>
						<td>${list.queryDate}</td>
						<td>${list.queryMakerId}</td>
						<td>${list.userId}</td>
						<td>${list.department}</td>
					</tr>
					</logic:iterate>
					</table>
				</td></tr>
				</table>
				</logic:present>
				</fieldset>
	</body>
</html>
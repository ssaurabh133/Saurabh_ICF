<!--Author Name : Arun Mishra-->
<!--Date of Creation :25 June 2012 -->
<!--Purpose  : Summary For User->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/capsScript/actionCodeScript.js"></script>


	<link type="text/css" 	href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('actionCodeMasterForm').codeDesc.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('actionCodeMasterForm').codeId.focus();
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

<body onload="enableAnchor();fntab();init_fields();">

	
		<fieldset>
			<legend>
				<bean:message key="lbl.collSummary" />
			</legend>
			<table cellpadding="0"  cellspacing="0" width="100%" >
            <tr><td class="gridtd">
			<table cellpadding="1" cellspacing="1" width="100%">
            <tr class="white1">
							<td><b><bean:message key="lbl.totalCaseAllocated" /></b>
                            </td>
							<td>
						     ${summaryVO.totalAllocatedCase}
						     </td>


						</tr>
						<tr class="white1">
							<td>
								<b><bean:message key="lbl.followupDueToday" /></b>
							</td>
							<td>
							   ${summaryVO.followupDueToday}
							</td>

						</tr>
				  <tr class="white1">
							<td>
							<b>	<bean:message key="lbl.escalatedCase" /></b>
							</td>
							<td>
							   ${summaryVO.escalatedCases}
							</td>

						</tr>
				  <tr class="white1">
							<td>
							<b>	<bean:message key="lbl.escalationRecive" /></b>
							</td>
							<td>
							   ${summaryVO.escalatedRecive}
							</td>

						</tr>
                      <tr class="white1">
							<td>
								<b><bean:message key="lbl.totalPTPDoneToday" /></b>
							</td>
							<td>
							   ${summaryVO.totalPTPDoneToday}
							</td>

						</tr>
							
			</table>
</td></tr></table>

		</fieldset>

<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>
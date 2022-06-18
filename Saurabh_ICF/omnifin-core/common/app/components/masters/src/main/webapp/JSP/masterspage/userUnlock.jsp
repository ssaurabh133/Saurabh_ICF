<!--Author Name : Kanika Maheshwari-->
<!--Date of Creation :26 December 2011 -->
<!--Purpose  : Information of Unlock User-->
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

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/masterScript/unlockUserScript.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript">
function allChecked()
{

	var c = document.getElementById("allchkd").checked;

	var ch=document.getElementsByName('chkCases');

 	var zx=0;

	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
	
}
</script>
</head>



<html:form styleId="unlockUserMasterForm" method="post"
	action="/unlockUser">
	<input type="hidden" id="hidCheck" name="hidCheck" />

	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<fieldset>
		<legend>
			<bean:message key="lbl.userUnlockMaster" />
		</legend>
		<table width="100%">
			<tr>
				<td width="13%">
					<bean:message key="lbl.userId" />
				</td>
				<td width="13%">
					<html:text property="userId" styleClass="text" styleId="userId"
						maxlength="10" />
				</td>
				<td width="13%">
					<bean:message key="lbl.userNam" />
				</td>
				<td width="13%">
					<html:text property="userName" styleClass="text" styleId="userName"
						maxlength="50" />
				</td>
			</tr>

			<tr>

				<td>
					<button type="button" id="search"
						class="topformbutton2" title="Alt+S" accesskey="S"
						onclick="fnSearch();" ><bean:message key="button.search" /></button>
					<input type="hidden" name="path" id="path"
						value="<%=request.getContextPath()%>" />




				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="gridtd">

					<table width="100%" border="0" cellspacing="1" cellpadding="4"
						id="gridTable">
						<logic:present name="list">
							<tr class="white2">
								<td>
									<input type=checkbox id="allchkd" name=allchkd
										onClick="allChecked()" />
								</td>
								<td>
									<b><bean:message key="lbl.userId" /> </b>
								</td>

								<td>
									<b><bean:message key="lbl.userNam" /> </b>
								</td>
							</tr>


							<logic:iterate name="list" id="sublist" indexId="count">

								<tr class="white1">
									<td>
										<input type="checkbox" name="chkCases"
											id="chkCases<%=count.intValue() + 1%>"
											value="${sublist.userId }" />


									</td>
									<td>
										${sublist.userId }
											

									</td>
									<td>
										${sublist.userName }

									</td>
								</tr>
							</logic:iterate>
							
							
						</logic:present>
						<logic:notPresent name="list">
							<tr class="white2">
							<td>
									<input type=checkbox id="allchkd" name=allchkd
										onClick="allChecked()" />
								</td>
								<td>
									<b><bean:message key="lbl.userId" /> </b>
								</td>

								<td>
									<b><bean:message key="lbl.userNam" /> </b>
								</td>
								

							</tr>
						</logic:notPresent>
				</table>
				</td>
			</tr>
				<tr class="white1"><td>
							<logic:notEmpty name="list">
						<button type="button"  id="save" class="topformbutton2"
							title="Alt+U" accesskey="U"
							onclick="return fnUpdateUser();" ><bean:message key="button.unlock" /></button>
							
					</logic:notEmpty>
					</td></tr>
		</table>
	</fieldset>

</html:form>

<logic:present name="sms">
	<script type="text/javascript">


	if('<%=request.getAttribute("sms")%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("unlockUserMasterForm").action="unlockUser.do";
	    document.getElementById("unlockUserMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert("<bean:message key="lbl.noDataSave" />");
		document.getElementById("unlockUserMasterForm").action="unlockUser.do";
	    document.getElementById("unlockUserMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms")%>'=='No')
	{	//alert('<%=request.getAttribute("sms")%>');
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
	
	else 
	{
		alert("<bean:message key="lbl.dataExist" />");
	}


	
	

</script>
</logic:present>

</body>
</html:html>
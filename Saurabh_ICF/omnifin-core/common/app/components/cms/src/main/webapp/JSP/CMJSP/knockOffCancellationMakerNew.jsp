<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		
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
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/knockOffCancellation.js"></script>  
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
		
		
		
	
		<style type="text/css">
		
			.white {
			background:repeat-x scroll left bottom #FFFFFF;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
			.white2 {
			background:repeat-x scroll left bottom #CDD6DD;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
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
	
	
	
<body onload="enableAnchor();checkChanges('knockOffCanNew');">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
	</div>
	<html:form action="/knockOffCancellationDispatchAction" styleId="knockOffCanNew" >
		<fieldset>
			<legend><bean:message key="lbl.KnockOffCancellationMaker"/></legend>  
				<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />		
					<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
						<td><bean:message key="lbl.loanNumber"/></td>
						<td><html:text property="loanNumber" styleClass="text" value="${headerList[0].loanNumber}" styleId="loanNumber" readonly="true"/>
							<html:hidden property="lbxLoanID" styleId="lbxLoanID" value="${headerList[0].lbxLoanNoHID}" />
							<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
							<html:hidden property="knockOffID" styleClass="text" value="${headerList[0].knockOffId}" styleId="knockOffID" />
								<input type="hidden" id="knockOffDate" value=""/>
							<input type="hidden" id="flag" value="N"/>
							<logic:notPresent name="hideLov">
								<html:button property="loanFromButton" styleId="loanFromButton" tabindex="1" onclick="openLOVCommon(344,'knockOffCanNew','loanNumber','flag','', 'flag','','getKnockOffCancellationData','customerName','knockOffID','knockOffDate')" value=" " styleClass="lovbutton"  />
							</logic:notPresent>					
						</td>
							<td><bean:message key="lbl.customer_name"/></td>
							<td><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="100" readonly="true" value="${headerList[0].customerName}"/>
						</td>
					</tr>
					<tr>
						<td><bean:message key="lbl.businessPartnerType" /><font color="red">*</font></td>
						<td>
							<html:text property="businessPartnerType"  styleId="businessPartnerType" value="${headerList[0].businessPartnerType}" styleClass="text" readonly="true"/>
							<html:hidden property="hBPType" styleId="hBPType"	value="${headerList[0].businessPartnerType}" />
						</td>
						<td><bean:message key="lbl.businessPartnerName" /></td>
						<td>
							<html:text property="businessPartnerName"	styleId="businessPartnerName" size="20" value="${headerList[0].businessPartnerName}"	styleClass="text" tabindex="-1" readonly="true"></html:text>
							<html:hidden property="lbxBusinessPartnearHID"	styleId="lbxBusinessPartnearHID" value="${headerList[0].lbxBusinessPartnearHID}" />
						</td>
					</tr>
					<tr>
						<td><bean:message key="lbl.makerRemark" /></td>
							<logic:notPresent name="forAuthor">
								<td><html:textarea property="makerRemarks" styleId="makerRemarks"	value="${headerList[0].remarks}" styleClass="text"></html:textarea></td>
							</logic:notPresent>
							<logic:present name="forAuthor">
								<td><html:textarea property="makerRemarks" styleId="makerRemarks"	disabled="true" value="${headerList[0].remarks}" styleClass="text"></html:textarea></td>
							</logic:present>
						<td><bean:message key="lbl.authorRemarks" /></td>
						<td><html:textarea styleClass="text" property="authorRemarks" styleId="authorRemarks"	disabled="true" value="${headerList[0].authorRemarks}" /></td>
					</tr>							
			</table>
		</fieldset>
		<br/><br/>
		<fieldset>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="knockoff">
				<tr>
					<th><div align="center"><strong><bean:message key="lbl.receivableAdvices" /></strong></div></th>
					<th class="thnone"><div align="center"><strong><bean:message key="lbl.payableAdvices" /></strong></div></th>
				</tr>
				<tr valign="top">
						<td valign="top" class="bnone">
						<div>
							<table width="100%" border="0" cellspacing="0" cellpadding="1"	id="dtable" class="knockoff none">
							<tr>
								<th><strong><bean:message key="lbl.loanAccountNo" /></strong></th>
								<th><b><bean:message key="lbl.advicedate" /></b></th>
								<th><b><bean:message key="lbl.charges" /></b></th>
								<th><b><bean:message key="lbl.adviceAmount" /></b></th>
							    <th><b><bean:message key="lbl.balanceAmount" /></b></th>
								<th><b><bean:message key="lbl.amountInProcess" /></b></th>		
								<th><b><bean:message key="lbl.knockOffAmount" /></b></th>
								<th class="bnone"><b><bean:message key="lbl.select" /></b></th></tr>
								<%
										int i = 1;
								%>
								<logic:present name="receivableList">
								<logic:iterate id="loanDataListR1" name="receivableList">
								<tr>
									<td>${loanDataListR1.loanNumber}</td>
									<td>${loanDataListR1.adviceDateR}</td>
									<td>${loanDataListR1.charges}</td>
									<td align="right">${loanDataListR1.originalAmountR}</td>
									<td align="right">${loanDataListR1.balanceAmountR}</td>
									<td align="right">${loanDataListR1.amountInProcessR}</td>
  								    <td>
  								    	<input type="hidden" name="originalAmountR"	id="originalAmountR<%=i%>"  value="${loanDataListR1.originalAmountR}" />
										<input type="hidden" name="balanceAmountR" id="balanceAmountR<%=i%>"	value="${loanDataListR1.balanceAmountR}" />
										<input type="hidden" name="txnAdviceIdR" id="txnAdviceIdR<%=i%>"	value="${loanDataListR1.txnAdviceIdR}" />
										<input type="hidden" name="amountInProcessR" id="amountInProcessR<%=i%>"	value="${loanDataListR1.amountInProcessR}" />
										<input type="hidden" name="knockOffIdR"	id="knockOffIdR<%=i%>" value="${loanDataListR1.knockOffIdR}" />
										<logic:equal name="loanDataListR1"	property="knockOffAmountR" value="0.0000">
											<input type="text" class="text3" id="knockOffAmountR<%=i%>" name="knockOffAmountR"	disabled="disabled" />
									    </logic:equal>
										<logic:notEqual name="loanDataListR1" property="knockOffAmountR" value="0.0000">
											<input type="text" class="text3" id="knockOffAmountR<%=i%>" name="knockOffAmountR" value="${loanDataListR1.knockOffAmountR}" disabled="disabled" style="text-align: right;" />
										</logic:notEqual>
									</td>
									<td class="bnone">
											<logic:equal name="loanDataListR1"	property="knockOffAmountR" value="0.0000">
												<input type="checkbox" name="chk" id="chkR<%=i%>"	disabled="disabled"	value="${loanDataListR1.txnAdviceIdR}" />
											</logic:equal>

											<logic:notEqual name="loanDataListR1" property="knockOffAmountR" value="0.0000">
												<input type="checkbox" name="chk" id="chkR<%=i%>" value="${loanDataListR1.txnAdviceIdR}" checked="checked" disabled="disabled" />
											</logic:notEqual>
									</td>
									<%
											i++;
									%>
							</tr>
							</logic:iterate>
							<tr class="white1">
								<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
								<td><b><bean:message key="lbl.Total" /></b></td>
								<td><html:text styleClass="text3" styleId="totalReveivable"	property="totalReveivable"	value="${totalReceivable[0].totalReveivable}" disabled="true"	style="text-align:right;" /></td>
								<td class="bnone">&nbsp;</td>
							</tr>
							</logic:present>
							</table>
						</div>
						</td>
						<td class="bnone">
							<div>
								<table width="100%" border="0" cellspacing="0" cellpadding="1"	id="dtable1" class="knockoff rtnone">
								<tr>
									<th><b><bean:message key="lbl.select" /></b></th>
									<th><strong><bean:message key="lbl.loanAccountNo" /></strong></th>
									<th><b><bean:message key="lbl.advicedate" /></b></th>
									<th><b><bean:message key="lbl.charges" /></b></th>
									<th><b><bean:message key="lbl.adviceAmount" /></b></th>
									<th><b><bean:message key="lbl.balanceAmount" /></b></th>
									<th><b><bean:message key="lbl.amountInProcess" /></b></th>
									<th class="bnone"><b><bean:message key="lbl.knockOffAmount" /></b></th>
								</tr>
								<%
									int j = 1;
								%>
								<logic:present name="payableList">
								<logic:iterate id="loanDataListP1" name="payableList">
									<tr class="white1">
										<td>
											<logic:equal name="loanDataListP1"	property="knockOffAmountP" value="0.0000">
												<input type="checkbox" name="chk" id="chkP<%=j%>" disabled="disabled" value="${loanDataListP1.txnAdviceIdP }" />
											</logic:equal>
											<logic:notEqual name="loanDataListP1" property="knockOffAmountP" value="0.0000">
												<input type="checkbox" name="chk" id="chkP<%=j%>" value="${loanDataListP1.txnAdviceIdP }" checked="checked" disabled="disabled" />
											</logic:notEqual>
										</td>
										<td>${loanDataListP1.loanNumber }</td>
										<td>${loanDataListP1.adviceDateP }</td>
										<td>${loanDataListP1.charges }</td>
										<td align="right">${loanDataListP1.originalAmountP }</td>
										<td align="right">${loanDataListP1.balanceAmountP }</td>
										<td align="right">${loanDataListP1.amountInProcessP }</td>

										<td class="bnone">
												<logic:equal name="loanDataListP1"	property="knockOffAmountP" value="0.0000">
													<input type="text" class="text3" id="knockOffAmountP<%=j%>" name="knockOffAmountP"	value="" disabled="disabled" />
												</logic:equal>
												<logic:notEqual name="loanDataListP1" property="knockOffAmountP" value="0.0000">
													<input type="text" class="text3" id="knockOffAmountP<%=j%>" name="knockOffAmountP"	value="${loanDataListP1.knockOffAmountP }"	disabled="disabled" style="text-align: right;" />
												</logic:notEqual>
												<input type="hidden" name="originalAmountP" id="originalAmountP<%=j%>"	value="${loanDataListP1.originalAmountP }" />
												<input type="hidden" name="balanceAmountP" id="balanceAmountP<%=j%>" value="${loanDataListP1.balanceAmountP }" />
												<input type="hidden" name="txnAdviceIdP" id="txnAdviceIdP<%=j%>" value="${loanDataListP1.txnAdviceIdP }" />
												<input type="hidden" name="amountInProcessP" id="amountInProcessP<%=j%>" value="${loanDataListP1.amountInProcessP }" />
												<input type="hidden" name="knockOffIdP"	id="knockOffIdP<%=j%>" value="${loanDataListP1.knockOffIdP }" />
										</td>
										<%
											j++;
										%>
									</tr>
								</logic:iterate>
								<tr class="white1">
								<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
								<td><b><bean:message key="lbl.Total" />	</b></td>
								<td class="bnone">
									<html:text styleClass="text3" styleId="totalPayable" property="totalPayable" value="${totalPayable[0].totalPayable}" disabled="true" style="text-align:right;" />
									<br>
								</td>
							    </tr>
							</logic:present>
							</table>
							</div>
					 </td>
			</tr>
			</table>
			<html:hidden property="adviceTypeR" styleId="adviceTypeR" value="R" />
			<html:hidden property="adviceTypeP" styleId="adviceTypeP" value="P" />
		</fieldset>	
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
		<td>
			<logic:present name="receivableList">
			<logic:present name="payableList">
			<logic:notPresent name="forAuthor">
				
					<button type="button" name=" mybutton" accesskey="V" id="save" title="Alt+V" class="topformbutton2" onclick="return cancelknockOff('P')" ><bean:message key="button.save" /></button>
					<button type="button"  name=" mybutton" accesskey="F" title="Alt+F" class="topformbutton2" onclick="return cancelknockOff('F')" ><bean:message key="button.forward" /></button>
				    <logic:present name="hideLov">

				<button type="button" id="delete" class="topformbutton2" onclick="return deleteKnockOffCancellation();" title="Alt+D" accesskey="D"><bean:message key="button.delete" /></button>
				</logic:present>
				</logic:notPresent>
				
			</logic:present>
			</logic:present>
		</td>
		</tr>
		</table>		
	</html:form>
	
	<logic:present name="save">   
	<script type="text/javascript">
		alert("<bean:message key="lbl.dataSave" />");
	</script>
	</logic:present>
<logic:present name="forward">   
	<script type="text/javascript">
		alert("<bean:message key="msg.manualAdviceForward" />");
		var contextPath=document.getElementById("contextPath").value;
    	document.getElementById("knockOffCanNew").action = contextPath+"/knockOffCancellationMakerBehindAction.do";
    	document.getElementById("knockOffCanNew").submit();
	</script>
</logic:present>

<logic:present name="notSave">   
	<script type="text/javascript">
		alert("<bean:message key="msg.notepadError" />");
	</script>
</logic:present>
<logic:present name="msg">   
	<script type="text/javascript">
	 if('<%=request.getAttribute("msg").toString()%>'=="DS")
		{
			alert("<bean:message key="lbl.dataDeleted" />");
			window.location="<%=request.getContextPath()%>/knockOffCancellationMakerBehindAction.do";
		}else if('<%=request.getAttribute("msg").toString()%>'=='DN')
		{
			alert("<bean:message key="lbl.dataNtDeleted" />");
			
		}
	</script>
</logic:present>

<script>
	setFramevalues("knockOffCanNew");
</script>
</body>
</html>
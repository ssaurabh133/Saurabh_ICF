<!--Author Name : Ritu-->
<!--Date of Creation : 08 july-2011-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js" /></script>
	<script type="text/javascript"	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"	src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"	src="<%=request.getContextPath()%>/js/masterScript/bankAccountMaster.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('bankAccountMaster').accountType.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('bankAccountMaster').bankButton.focus();
     }
     return true;
}

</script>
<script type="text/javascript">
function myFunction()
{
document.getElementById("percentage").innerHTML=Math.max(0,100);
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

<body onload="enableAnchor();fntab();init_fields();myFunction();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<html:form styleId="bankAccountMaster" action="/bankAccountMasterAdd">
		<html:errors />
		<input type="hidden" name="path" id="path"
			value="<%=request.getContextPath()%>" />

		<fieldset>
			<legend>
				<bean:message key="lbl.bankAccountMaster" />
			</legend>
			<table width="100%">
				<tr>

					<logic:present name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
						<logic:iterate id="listObj" name="list">
							<tr>
								<td>
									<bean:message key="lbl.bank" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text property="bankCode" styleId="bankCode"
										styleClass="text" readonly="true" value="${listObj.bankCode}" />
									<html:hidden property="lbxBankID" styleId="lbxBankID"
										value="${listObj.lbxBankID}" />

								</td>

								<td>
									<bean:message key="lbl.bankBranchName" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text property="bankBranchName" styleId="bankBranchName"
										styleClass="text" maxlength="50"
										value="${listObj.bankBranchName}" readonly="true" />

									<html:hidden property="lbxBranchID" styleId="lbxBranchID"
										value="${listObj.lbxBranchID}" />
								</td>
							</tr>

							<tr>

								<td>
									<bean:message key="lbl.ifscCode" />
									
								</td>
								<td>
									<html:text property="ifscCode" styleId="ifscCode"
										styleClass="text" readonly="true" maxlength="20"
										value="${listObj.ifscCode}" />
								</td>


								<td>
									<bean:message key="lbl.micrCode" />
									
								</td>
								<td>
									<html:text property="micrCode" styleId="micrCode"
										styleClass="text" maxlength="20" readonly="true"
										value="${listObj.micrCode}" />
								</td>

							</tr>
							<tr>

								<td>
									<bean:message key="lbl.accountNo" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text property="accountNo" styleId="accountNo"
										styleClass="text" maxlength="20" readonly="true" value="${listObj.accountNo}" />
								</td>

								<td>
						       <bean:message key="lbl.accountType"/></td>
						       <td>
						       <html:select property="accountType" styleId="accountType" styleClass="text" value="${listObj.accountType}">
						                 <html:option value="C">CASH</html:option>
						                <html:option value="B">BANK</html:option>
						                <html:option value="S">ADJUSTMENT</html:option>
						       </html:select>
						     </td>
						     
						     </tr>

							<tr>
     
								<td>
									<bean:message key="lbl.glCode" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
										<html:text property="glCode" styleId="glCode" styleClass="text" readonly="true" value="${listObj.glCode}"/>
										<html:button property="ledgerButton" styleId="ledgerButton" onclick="openLOVCommon(356,'bankAccountMaster','lbxLedgerId','','', '','','','glCode');closeLovCallonLov('glCode');"
										value=" " styleClass="lovbutton">
										</html:button>
										<html:hidden property="lbxLedgerId" styleId="lbxLedgerId" value=""/>
										<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
										
								</td>

							
						        <td><bean:message key="lbl.drawingPower" /></td>
								<td>
									<html:text property="drawingPower" styleId="drawingPower" onkeypress="return numbersonly(event, id, 3)"  onchange="drawingData();" 
										styleClass="text" maxlength="20"  value="${listObj.drawingPower}" />
								</td>
							</tr>
<!--							added by neeraj-->
							<tr>
							<td><bean:message key="lbl.clientCode"/></td>
							<td><html:text property="clientCode" styleId="clientCode" styleClass="text" maxlength="16" value="${listObj.clientCode}" /></td>
							
							<td><bean:message key="lbl.status" /></td>
								<td>

									<logic:equal value="Active" name="status">
										<input type="checkbox" name="bankAccountStatus"
											id="bankAccountStatus" checked="checked" />
									</logic:equal>

									<logic:notEqual value="Active" name="status">
										<input type="checkbox" name="bankAccountStatus"
											id="bankAccountStatus" />
									</logic:notEqual>
								</td>

							</tr>

							
						</logic:iterate>
					</logic:present>


					<logic:notPresent name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
						<tr>
							<td>
								<bean:message key="lbl.bank" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="bankCode" styleId="bankCode" styleClass="text" readonly="true" />
                                <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'bankAccountMaster','bankCode','','bankBranchName', 'lbxBranchID','','','lbxBankID');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(7,'bankAccountMaster','bankCode','','bankBranchName', 'lbxBranchID','','','lbxBankID')" src="<%=request.getContextPath()%>/images/lov.gif"> --%>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
								<html:hidden property="lbxBankID" styleId="lbxBankID" />
							</td>

							<td>
								<bean:message key="lbl.bankBranchName" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="bankBranchName" styleId="bankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1"/>
                                <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(109,'bankAccountMaster','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','micrCode','ifscCode','lbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(109,'bankAccountMaster','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','lbxBranchID','ifscCode','micrCode')" src="<%=request.getContextPath()%>/images/lov.gif"> --%> 
								<html:hidden property="lbxBranchID" styleId="lbxBranchID" value=""/>
							</td>
						</tr>

						<tr>

							<td>
								<bean:message key="lbl.ifscCode" />
							
							</td>
							<td>
								<html:text property="ifscCode" styleId="ifscCode"
									styleClass="text" readonly="true" maxlength="20" />
							</td>


							<td>
								<bean:message key="lbl.micrCode" />
								
							</td>
							<td>
								<html:text property="micrCode" styleId="micrCode"
									styleClass="text" maxlength="20" readonly="true" />
							</td>

						</tr>
						<tr>

							<td>
								<bean:message key="lbl.accountNo" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="accountNo" styleId="accountNo"
									styleClass="text" maxlength="20" />
							</td>


					<td>
					       <bean:message key="lbl.accountType"/></td>
					       <td>
					       <html:select property="accountType" styleId="accountType" styleClass="text" >
					       		<html:option value="C">CASH</html:option>
								<html:option value="B">BANK</html:option>
								<html:option value="S">ADJUSTMENT</html:option>
					       </html:select>
					     </td>
					     </tr>

						<tr>
							<td>
								<bean:message key="lbl.glCode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="glCode" styleId="glCode" styleClass="text" readonly="true" />
								<html:button property="ledgerButton" styleId="ledgerButton" onclick="openLOVCommon(356,'bankAccountMaster','lbxLedgerId','','', '','','','glCode');closeLovCallonLov('glCode');"
										value=" " styleClass="lovbutton">
										</html:button>
										<html:hidden property="lbxLedgerId" styleId="lbxLedgerId" value=""/>
										<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
							</td>

						
						      <td><bean:message key="lbl.drawingPower" /></td>
								<td>
									<html:text property="drawingPower" styleId="drawingPower" onkeypress="return numbersonly(event, id, 3)"    onchange="drawingData();"
										styleClass="text" maxlength="20" value="" />
								</td>	
							
						</tr>
						<!--							added by neeraj-->
							<tr>
							<td><bean:message key="lbl.clientCode"/></td>
							<td><html:text property="clientCode" styleId="clientCode" styleClass="text" maxlength="16"  /></td>
							<td>
								<bean:message key="lbl.status" />
							</td>
							<td>
							<input type="checkbox" name="bankAccountStatus" id="bankAccountStatus" checked="checked" />
							</td>

						</tr>
						<!--							added by neeraj-->
							<tr>
							<td><bean:message key="lbl.clientCode"/></td>
							<td><html:text property="clientCode" styleId="clientCode" styleClass="text" maxlength="16"  /></td>
							<td></td><td></td>
							</tr>

					</logic:notPresent>


					<logic:present name="editVal">
						<td>
							<button  type="button" name="save" value="Save" id="save" title="Alt+V" accesskey="V"
								onclick="return fnEditBankAccount();" class="topformbutton2"><bean:message key="button.save" /></button>
						</td>
					</logic:present>

					<logic:notPresent name="editVal">
						<input type="hidden" name="path" id="path"
							value="<%=request.getContextPath()%>" />
						<td>
							<button type="button" name="save" value="Save" id="save" title="Alt+V" accesskey="V"
								onclick="return saveBankAccount();" class="topformbutton2"><bean:message key="button.save" /></button>
						</td>
					</logic:notPresent>


				</tr>

			</table>
		</fieldset>
	</html:form>

	<logic:present name="sms">

		<script type="text/javascript">

			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" />");
				document.getElementById("bankAccountMaster").action="bankAccountMasterBehind.do";
	    		document.getElementById("bankAccountMaster").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.dataModify" />");
				document.getElementById("bankAccountMaster").action="bankAccountMasterBehind.do";
	    		document.getElementById("bankAccountMaster").submit();
			}
				
				else if('<%=request.getAttribute("sms").toString()%>'=="E")
					{
						alert("<bean:message key="lbl.dataExist" />");
					}
				else
					{
						alert("<bean:message key="msg.notepadError" />");
					}
	</script>
	</logic:present>

</body>
</html:html>

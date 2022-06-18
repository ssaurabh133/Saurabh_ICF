<!--Author Name : Anil Yadav-->
<!--Date of Creation :14 Oct 2011 -->
<!--Purpose  : Information of Collection Application Master Add-->
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
			src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/capsScript/queueCodeScript.js"></script>
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/popup.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('queueCodeMasterForm').queueDesc.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('queueCodeMasterForm').queueCode.focus();
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

<body onload="enableAnchor();CheckBranchProScheme();fntab();">

	<html:form styleId="queueCodeMasterForm" method="post"
		action="/queueCodeMasterAdd">
		<html:javascript formName="QueueCodeMasterAddDyanavalidatiorForm" />
		<html:hidden property="path" styleId="path"
			value="<%=request.getContextPath()%>" />
		<fieldset>
			<legend>
				<bean:message key="lbl.queueCodeMaster" />
			</legend>
			<table cellpadding="0" cellspacing="1" width="100%">

	
				<logic:present name="editVal">
				<logic:notEmpty  name="editVal">
				<input type="hidden" name="queueid" value="${sessionScope.queueid}" />
					<input type="hidden" id="modifyRecord" name="modifyRecord"	value="M" />
					<logic:iterate id="listObj" name="list">
						<tr>
							<td>
								<bean:message key="lbl.queueCode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="queueCode" styleClass="text"
									styleId="queueCode" maxlength="10" readonly="true"
									value="${listObj.queueCode}" tabindex="-1" />
							</td>

							<td>
								<bean:message key="lbl.queueDescription" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="queueDesc" styleClass="text"
									styleId="queueDesc" maxlength="50"
									onblur="fnChangeCase('queueCodeMasterForm','queueDesc')"
									value="${listObj.queueDesc}" />
							</td>

						</tr>
						<tr>

							<td>
								<bean:message key="lbl.DPD1" />
<!--								<span><font color="red">*</font>-->
								</span>
							</td>
							<td>
								<input type="text" style="text-align: right" onkeypress="return isNumberKey(event);"
									name="dpdGreater" class="text" id="dpdGreater" maxlength="5"
									value="${listObj.dpdGreater}" />
							</td>

							
							<td>
								<bean:message key="lbl.DPD2" />
<!--								<span><font color="red">*</font>-->
								</span>
							</td>
							<td>
								<input type="text" style="text-align: right" onkeypress="return isNumberKey(event);"
									name="dpdLess" class="text" id="dpdLess" maxlength="5"
									value="${listObj.dpdLess}" />
							</td>

						</tr>
						<tr>

							<td>
								<bean:message key="lbl.overDueAmount1" />
<!--								<span><font color="red">*</font>-->
								</span>
							</td>
							<td>
								<input type="text" style="text-align: right"
									onfocus="keyUpNumber(this.value, event,18,id);"
									onkeyup="checkNumber(this.value, event,18,id);"
									onblur="formatNumber(this.value,id);"
									onkeypress="return numbersonly(event,id,18)" name="posGreater"
									class="text" id="posGreater" maxlength="18"
									value="${listObj.posGreater}" />
							</td>

							
							<td>
								<bean:message key="lbl.overDueAmount2" />
<!--								<span><font color="red">*</font>-->
								</span>
							</td>
							<td>
								<input type="text" style="text-align: right"
									onfocus="keyUpNumber(this.value, event,18,id);"
									onkeyup="checkNumber(this.value, event,18,id);"
									onblur="formatNumber(this.value,id);"
									onkeypress="return numbersonly(event,id,18)" name="posLess"
									class="text" id="posLess" maxlength="18"
									value="${listObj.posLess}" />
							</td>

						</tr>

						<tr>

							<td>
								<bean:message key="lbl.customerCategory" />
<!--								<span><font color="red">*</font>-->
								</span>
							</td>
							<td>
								<html:select property="custCategory" styleClass="text"
									styleId="custCategory" value="${listObj.custCategory}">
									<html:option value="">-------------ALL-------------</html:option>
									<logic:present name="custCategoryList">
									  <logic:notEmpty name="custCategoryList">
										<html:optionsCollection name="custCategoryList"
											label="custCategoryValue" value="custCategoryId" />
											</logic:notEmpty>
									</logic:present>
								</html:select>
							</td>

							<td>
								<bean:message key="customer.type" />
<!--								<span><font color="red">*</font>-->
								</span>
							</td>
							<td>
								<html:select property="custType" styleClass="text"
									styleId="custType" value="${listObj.custType}">
									<html:option value="">-------------ALL-------------</html:option>
									<html:option value="I">INDIVIDUAL</html:option>
									<html:option value="C">CORPORATE</html:option>
								</html:select>
							</td>

						</tr>
						<tr>
						
						    <td>
								<bean:message key="lbl.allBranch" />
							</td>
							
							<td>
								<logic:equal value="0" name="listObj" property="lbxBranchId">
									<input type="checkbox" name="branch1" checked="checked"
										id="allBranch" value="ALL" onclick="AllBranch();" />

								</logic:equal>

								<logic:notEqual value="0" name="listObj" property="lbxBranchId">
									<input type="checkbox" name="branch1" id="allBranch"
										value="ALL" onclick="AllBranch();" />
								</logic:notEqual>

							</td>
						

							<td>
								<bean:message key="lbl.branch" />
							</td>
							<td>

								<input type="hidden" name="contextPath"
									value="<%=request.getContextPath()%>" id="contextPath" />
									<logic:present name="listObj">
									<html:text property="branch" styleId="branch" styleClass="text"
									value="${listObj.branch}" readonly="true" tabindex="-1" />
									</logic:present>
									<logic:notPresent name="listObj">
								<html:text property="branch" styleId="branch" styleClass="text"
									value="${sessionScope.userobject.branchName}" readonly="true" tabindex="-1" />
									
									</logic:notPresent>
								<input type="button" class="lovbutton" id="branchButton"
									onclick="openLOVCommon(12,'queueCodeMasterForm','branch','','','','','clearBranchLovChild','lbxBranchId')"
									value="" name="dealButton" tabindex="25">
									<logic:present name="listObj">
								<input type="hidden" name="lbxBranchId" id="lbxBranchId"
									value="${listObj.lbxBranchId}" />
									</logic:present>
									<logic:notPresent name="listObj">
								<input type="hidden" name="lbxBranchId" id="lbxBranchId"
									value="${sessionScope.userobject.branchId}" />
									</logic:notPresent>

							</td>
                  
                  <tr>
                  
                  <td>
								<bean:message key="lbl.allProduct" />
							</td>
							<td>
								<logic:equal value="0" name="listObj" property="lbxProductID">
									<input type="checkbox" checked="checked" name="product1"
										id="allProduct" value="ALL" onclick="AllProduct();" />

								</logic:equal>

								<logic:notEqual value="0" name="listObj" property="lbxProductID">
									<input type="checkbox" name="product1" id="allProduct"
										value="ALL" onclick="AllProduct();" />

								</logic:notEqual>
							</td>
                  
                  <td>
								<bean:message key="lbl.products" />
							</td>
							<td>

								<html:text property="product" styleId="product"
									styleClass="text" value="${listObj.product}" readonly="true" tabindex="-1" />
								<input type="button" class="lovbutton" id="productButton"
									onclick="openLOVCommon(89,'queueCodeMasterForm','product','','lbxProductID','','','clearProductLovChild','lbxProductID')"
									value=" " tabindex="26" name="dealButton">
								<input type="hidden" name="lbxProductID" id="lbxProductID"
									value="${listObj.lbxProductID}" />

							</td>
                  
                  </tr>							

						<tr>
						
						<td>
								<bean:message key="lbl.allScheme" />
							</td>
							<td>
								<logic:equal value="0" name="listObj" property="lbxscheme">
									<input type="checkbox" checked="checked" name="scheme1"
										id="allScheme" value="ALL" onclick="AllScheme();" />
								</logic:equal>
								<logic:notEqual value="0" name="listObj" property="lbxscheme">
									<input type="checkbox" name="scheme1" id="allScheme"
										value="ALL" onclick="AllScheme();" />
								</logic:notEqual>

							</td>

							<td>
								<bean:message key="lbl.scheme" />
							</td>
							<td>

								<html:text property="scheme" styleId="scheme" styleClass="text"
									value="${listObj.scheme}" readonly="true" tabindex="-1" />
								<input type="button" class="lovbutton" id="schemeButton"
									onclick="openLOVCommon(22,'queueCodeMasterForm','scheme','product','lbxscheme', 'lbxProductID','* Please select Product first !!!','','lbxscheme')"
									value=" " name="dealButton">
								<input type="hidden" name="lbxscheme" id="lbxscheme"
									value="${listObj.lbxscheme}" />

							</td>

							
						</tr>


						<tr>

							<td>
								<bean:message key="lbl.priority" />		<span><font color="red">*</font></span>
							</td>
							<td>
								<input type="text" name="priority" class="text" id="priority"
									maxlength="08" onkeypress="return isNumberKey(event);"
									value="${listObj.priority}" />
							</td>

							<td>
								<bean:message key="lbl.actionPeriod" />
							</td>
							<td>
								<input type="text" name="actionPeriod" class="text"
									id="actionPeriod" maxlength="8"
									onkeypress="return isNumberKey(event);"
									value="${listObj.actionPeriod}" />
							</td>

						</tr>

						<tr>
							<td>
								<bean:message key="lbl.active" />
							</td>
							<td>
								<logic:equal value="Active" name="status">
									<input type="checkbox" name="queueStatus" id="queueStatus"
										checked="checked" />
								</logic:equal>
								<logic:notEqual value="Active" name="status">
									<input type="checkbox" name="queueStatus" id="queueStatus" />
								</logic:notEqual>
							</td>
						</tr>
						<tr>
							<td>
								<button type="button" name="save"  id="save"
									title="Alt+V" accesskey="V"
									onclick="return fnEditQueueCode('${list[0].queueCode }');"
									class="topformbutton2"><bean:message key="button.save" /></button>
							</td>
						</tr>
					</logic:iterate>
					</logic:notEmpty>
				</logic:present>

				<logic:notPresent name="editVal">
				
			
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="I" />
					<tr>
						<td>
							<bean:message key="lbl.queueCode" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="queueCode" styleClass="text"
								styleId="queueCode" maxlength="10"
								value="${requestScope.defaultVal.queueCode}"
								onblur="fnChangeCase('queueCodeMasterForm','queueCode')" />
						</td>

						<td>
							<bean:message key="lbl.queueDescription" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="queueDesc" styleClass="text"
								styleId="queueDesc" maxlength="50"
								value="${requestScope.defaultVal.queueDesc}"
								onblur="fnChangeCase('queueCodeMasterForm','queueDesc')" />
						</td>

					</tr>
					<tr>

						<td>
							<bean:message key="lbl.DPD1" />
							
							
						</td>
						<td>
							<input type="text" style="text-align: right" onkeypress="return isNumberKey(event);"
								name="dpdGreater" class="text" id="dpdGreater" maxlength="5"
								value="${requestScope.defaultVal.dpdGreater}" />
						</td>

						
						<td>
							<bean:message key="lbl.DPD2" />
							
							
						</td>
						<td>
							<input type="text" style="text-align: right" onkeypress="return isNumberKey(event);"
								name="dpdLess" class="text" id="dpdLess" maxlength="5"
								value="${requestScope.defaultVal.dpdLess}" />
						</td>
					</tr>
					<tr>

						<td>
							<bean:message key="lbl.overDueAmount1" />
							
							
						</td>
						<td>
							<input type="text" style="text-align: right"
								onfocus="keyUpNumber(this.value, event,18,id);"
								onkeyup="checkNumber(this.value, event,18,id);"
								onblur="formatNumber(this.value,id);"
								onkeypress="return numbersonly(event,id,18)" name="posGreater"
								class="text" id="posGreater" maxlength="18"
								value="${requestScope.defaultVal.posGreater}" />
						</td>

						
						<td>
							<bean:message key="lbl.overDueAmount2" />
							
						</td>
						<td>
							<input type="text" style="text-align: right"
								onfocus="keyUpNumber(this.value, event,18,id);"
								onkeyup="checkNumber(this.value, event,18,id);"
								onblur="formatNumber(this.value,id);"
								onkeypress="return numbersonly(event,id,18)" name="posLess"
								class="text" id="posLess" maxlength="18"
								value="${requestScope.defaultVal.posLess}" />
						</td>
					</tr>
					<tr>

						<td>
							<bean:message key="lbl.customerCategory" />
							
						</td>
						<td>
							<html:select property="custCategory" styleClass="text"
								styleId="custCategory">
								
								<html:option value="">-------------ALL-------------</html:option>
								<logic:present name="custCategoryList">
								    <logic:notEmpty name="custCategoryList">
									   <html:optionsCollection name="custCategoryList"	label="custCategoryValue" value="custCategoryId" />
									</logic:notEmpty>
								</logic:present>
							
							</html:select>
						</td>

						<td>
							<bean:message key="customer.type" />
							
						</td>
						<td>
							<html:select property="custType" styleClass="text"
								styleId="custType">
								<html:option value="">-------------ALL-------------</html:option>
								<html:option value="I">INDIVIDUAL</html:option>
								<html:option value="C">CORPORATE</html:option>

							</html:select>
						</td>

					</tr>
					<tr>
                        
                        <td><bean:message key="lbl.allBranch" /></td>
							<td>
								<input type="checkbox" name="branch1" id="allBranch" value="ALL"
									onclick="AllBranch();" />
							</td>
												
						<td>
							<bean:message key="lbl.branch" />
						</td>
						<td>

							<input type="hidden" name="contextPath"
								value="<%=request.getContextPath()%>" id="contextPath" />
							<html:text property="branch" styleId="branch" styleClass="text"
								readonly="true" tabindex="-1" value="${sessionScope.userobject.branchName}" />
							<input type="button" class="lovbutton" id="branchButton"
								onclick="openLOVCommon(12,'queueCodeMasterForm','branch','','','','','clearBranchLovChild','lbxBranchId')"
								value="" name="dealButton" tabindex="25">
							<input type="hidden" name="lbxBranchId" id="lbxBranchId"
								value="${sessionScope.userobject.branchId}" />
						</td>
						

					</tr>

					<tr>
					
					   <td><bean:message key="lbl.allProduct" /></td>
							<td>
								<input type="checkbox" name="product1" id="allProduct"
									value="ALL" onclick="AllProduct();" />
							</td>
										
					    <td>
							<bean:message key="lbl.products" />
						</td>

						<td>

							<html:text property="product" styleId="product" styleClass="text"
								readonly="true" tabindex="-1"/>
							<input type="button" class="lovbutton" id="productButton"
								onclick="openLOVCommon(89,'queueCodeMasterForm','product','','lbxProductID','','','clearProductLovChild','lbxProductID')"
								value=" " tabindex="26" name="dealButton">
							<input type="hidden" name="lbxProductID" id="lbxProductID"
								value="${requestScope.defaultVal.lbxProductID}" />
						</td>											
					
					</tr>

					<tr>
						
						<td><bean:message key="lbl.allScheme" /></td>
							<td>
								<input type="checkbox" name="scheme1" id="allScheme" value="ALL"
									onclick="AllScheme();" />
							
						</td>
						
                        <td>
							<bean:message key="lbl.scheme" />
						</td>

						<td>

							<html:text property="scheme" styleId="scheme" styleClass="text"
								readonly="true"  tabindex="-1" />
							<input type="button" class="lovbutton" id="schemeButton"
								onclick="openLOVCommon(22,'queueCodeMasterForm','scheme','product','lbxscheme', 'lbxProductID','* Please Select Product first !!!','','lbxscheme')"
								value="" tabindex="34" name="dealButton">
							<input type="hidden" name="lbxscheme" id="lbxscheme"
								value="${requestScope.defaultVal.lbxscheme}" />
						</td>

						
					</tr>

					<tr>
						<td>
							<bean:message key="lbl.priority" />
							<span><font color="red">*</font></span>
						</td>
						<td>
							<input type="text" name="priority" class="text" id="priority"
								value="${requestScope.defaultVal.priority}"
								onkeypress="return isNumberKey(event);" maxlength="08" />
						</td>

						<td>
							<bean:message key="lbl.actionPeriod" />
						</td>
						<td>
							<input type="text" name="actionPeriod" class="text"
								id="actionPeriod" maxlength="8"
								value="${requestScope.defaultVal.actionPeriod}"
								onkeypress="return isNumberKey(event);" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.active" />
						</td>
						<td>
							<input type="checkbox" name="queueStatus" id="queueStatus"
								checked="checked" />
						</td>

					</tr>
					<tr>
						<td>
						<!--<html:button property="save" value="Save" styleId="save"
								title="Alt+V" accesskey="V" onclick="fnSaveQueueCode();"
								styleClass="topformbutton2"></html:button>-->
								 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="fnSaveQueueCode();"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
						</td>
					</tr>

				</logic:notPresent>


			</table>


		</fieldset>


	</html:form>

	<logic:present name="sms">
		<script type="text/javascript">

    
			if('<%=request.getAttribute("sms")%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("queueCodeMasterForm").action="queueCodeMasterBehind.do?method=searchQueue";
	    document.getElementById("queueCodeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms")%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("queueCodeMasterForm").action="queueCodeMasterBehind.do?method=searchQueue";
	    document.getElementById("queueCodeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms")%>'=='Q')
	{
		alert("<bean:message key="msg.queueexist" />");	

	
	}
	else if('<%=request.getAttribute("sms")%>'=='PE')
	{
		var queueid='<%=session.getAttribute("queueid")%>';
		alert("<bean:message key="msg.priorityexist" />");	
		document.getElementById("queueCodeMasterForm").action="queueCodeMasterBehind.do?method=openEditQueueCode&queueCode="+queueid;
	    document.getElementById("queueCodeMasterForm").submit();
	
	}
	else if('<%=request.getAttribute("sms")%>'=='P')
	{	
		alert("<bean:message key="msg.priorityexist" />");	
	
	}
	else if('<%=request.getAttribute("sms")%>'=='N')
	{
		alert("<bean:message key="lbl.errorSuccess" />");	
	}
	

</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>
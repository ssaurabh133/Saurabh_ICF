<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 13 JULY 2012-->
<!--Purpose  : Scheme BP Map Add->
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
		src="<%=request.getContextPath()%>/js/payoutScript/schemeBpMap.js"></script>
<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

	<link type="text/css" 	href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('schemeBpMapAdd').bpButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('schemeBpMapAdd').bpButton.focus();
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


	<html:form styleId="schemeBpMapAdd" method="post"		action="/schemeBpMapDispatch" >
	 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	  <input type="hidden" name="hcommon"  id="hcommon"/>
	  
		<fieldset>
			<legend>
				<bean:message key="lbl.schemBpMapMaster" />
			</legend>
			<table cellpadding="0" cellspacing="1" width="100%">


				<logic:present name="editVal">
				<logic:notEmpty name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="M" />
						
					<logic:iterate id="listObj" name="list">
						<tr>
						<td>
							<bean:message key="lbl.bpName" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="bpName" styleClass="text" styleId="bpName" readonly="true" tabindex="-1"	value="${listObj.bpName}"/>
						  <html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(1370,'schemeBpMapAdd','bpName','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxBpId" styleId="lbxBpId" value="${listObj.lbxBpId}"/>
						 <input type="hidden" name="mapId"  id="mapId" value="${listObj.mapId}"/>
						</td>

						<td>
							<bean:message key="lbl.activity" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						  <html:text property="activityCode" styleClass="text" styleId="activityCode" readonly="true"	tabindex="-1" value="${listObj.activityCode}"/>
						<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1364,'schemeBpMapAdd','activityCode','','', '','','','lbxActivityCode','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
						                                                                
						   <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
							<html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxActivityCode" styleId="lbxActivityCode" value="${listObj.lbxActivityCode}" />
						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.payScheme" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
						  <html:text property="schemeName" styleClass="text" styleId="schemeName"	readonly="true"	 tabindex="-1" value="${listObj.schemeName}"/>
						   <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1371,'schemeBpMapAdd','schemeName','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxSchemeName" styleId="lbxSchemeName" value="${listObj.schemeName}" />
							</td>
                             <td>
								<bean:message key="lbl.paySpecificTime" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<html:select property="specificTime" styleId="specificTime" styleClass="text" value="${listObj.specificTime}">
							<html:option value="MONTH">MONTHLY</html:option>
							<html:option value="QUAR">QUARTERLY</html:option>
							<html:option value="HALF">HALF YEARLY</html:option>
							<html:option value="YEAR">YEARLY</html:option>
							</html:select>
							</td>
						</tr>
						<tr>
					    	
							<td><bean:message key="lbl.paySpecificTar" />
							<span><font color="red"></font>
							</span></td>
							<td>
							
						<logic:equal value="Y" name="specific">
							<input type="checkbox" name="specificTar" id="specificTar" checked="checked" onclick="disableSpecific()"/>
								</logic:equal>
								<logic:notEqual value="Y" name="specific">
									<input type="checkbox" name="specificTar" id="specificTar" onclick="disableSpecific()"/>
								</logic:notEqual>
						</td>
							<td>
								<bean:message key="lbl.paySpecificTarOn" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<logic:equal value="Y" name="specific">
							<html:select property="specificTarOn" styleId="specificTarOn" styleClass="text" value="${listObj.specificTarOn}" >
							<html:option value="">Select</html:option>
							<html:option value="C">Case</html:option>
							<html:option value="A">Amount</html:option>
							</html:select>
							</logic:equal>
							<logic:notEqual value="Y" name="specific">
							<html:select property="specificTarOn" styleId="specificTarOn" styleClass="text" value=""  disabled="true">
							<html:option value="">Select</html:option>
							<html:option value="C">Case</html:option>
							<html:option value="A">Amount</html:option>
							</html:select>
							</logic:notEqual>
							</td>
						</tr>
						<tr>
					   <%-- 	<td><bean:message key="lbl.payPriority" />
								<span><font color="red">*</font>
								</span></td>
				        <td> 	
				     <html:text property="priority" styleClass="text" onkeypress="return isNumberKey(event);" value="${listObj.priority}"
								styleId="priority" maxlength="12" /></td>
						--%>  
						<td><bean:message key="lbl.targetForSt" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    	<logic:equal value="Y" name="specific">
					    	<html:text property="targetForSt" styleClass="text" styleId="targetForSt" value="${listObj.targetForSt}"
								 onkeypress="return numbersonly(event,this.id,18);" />
					    	</logic:equal>
					    	<logic:notEqual value="Y" name="specific">
					    	<html:text property="targetForSt" styleClass="text" styleId="targetForSt" disabled="true"
								 onkeypress="return numbersonly(event,this.id,18);"/>
					    	</logic:notEqual>
					    
					    </td>
					    <td><bean:message key="lbl.commissionForTarget" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    	<logic:equal value="Y" name="specific">
				 <html:text property="commissionForTarget" styleClass="text" styleId="commissionForTarget" value="${listObj.commissionForTarget}"
								 onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" />
					 </logic:equal>
					 	<logic:notEqual value="Y" name="specific">
					 	<html:text property="commissionForTarget" styleClass="text" styleId="commissionForTarget" disabled="true"
								 onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" />
					 
					 	</logic:notEqual>
					    </td>

						</tr>
						<tr>
				 
				   <td>
							<bean:message key="lbl.payRecStatus" />
						</td>
						<td>
								<logic:equal value="Active" name="status">
									<input type="checkbox" name="recStatus" id="recStatus"
										checked="checked" />
								</logic:equal>
								<logic:notEqual value="Active" name="status">
									<input type="checkbox" name="recStatus" id="recStatus" />
								</logic:notEqual>
							</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						
				 </tr>
					</logic:iterate>
					</logic:notEmpty>
				</logic:present>

				<logic:notPresent name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="I" />
					<tr>
						<td>
							<bean:message key="lbl.bpName" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="bpName" styleClass="text" styleId="bpName" readonly="true" tabindex="-1"	/>
						  <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1370,'schemeBpMapAdd','bpName','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxBpId" styleId="lbxBpId" />
						</td>

						<td>
							<bean:message key="lbl.activity" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						  <html:text property="activityCode" styleClass="text" styleId="activityCode" readonly="true"	tabindex="-1" />
						<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1364,'schemeBpMapAdd','activityCode','','', '','','','lbxActivityCode','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
						                                                                
						   <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
							<html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxActivityCode" styleId="lbxActivityCode" />
						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.payScheme" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
						  <html:text property="schemeName" styleClass="text" styleId="schemeName"	readonly="true"	 tabindex="-1"/>
						   <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1371,'schemeBpMapAdd','schemeName','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxSchemeName" styleId="lbxSchemeName" />
							</td>
                             <td>
								<bean:message key="lbl.paySpecificTime" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<html:select property="specificTime" styleId="specificTime" styleClass="text" >
							<html:option value="MONTH">MONTHLY</html:option>
							<html:option value="QUAR">QUARTERLY</html:option>
							<html:option value="HALF">HALF YEARLY</html:option>
							<html:option value="YEAR">YEARLY</html:option>
							
							</html:select>
							</td>
							
						
						

						</tr>
						<tr id="tr1" >
							<td><bean:message key="lbl.paySpecificTar" />
								<span><font color="red"></font>
								</span></td>
							<td>
							<input type="checkbox" name="specificTar" id="specificTar" checked="checked" onclick="disableSpecific()"/>
						</td>
						 <td>
								<bean:message key="lbl.paySpecificTarOn" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<html:select property="specificTarOn" styleId="specificTarOn" styleClass="text" >
							<html:option value="">Select</html:option>
							<html:option value="C">Case</html:option>
							<html:option value="A">Amount</html:option>
							</html:select>
							</td>
					    			    
						</tr>
				 
						 <tr id="tr2" >
				 <%-- <td><bean:message key="lbl.payPriority" />
								<span><font color="red">*</font>
								</span></td>
				  <td> 	<html:text property="priority" styleClass="text" onkeypress="return isNumberKey(event);"
								styleId="priority" maxlength="12" /></td>
				--%>
				
				 <td><bean:message key="lbl.targetForSt" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    <html:text property="targetForSt" styleClass="text" styleId="targetForSt"
								maxlength="50" />
					    </td>
					    
					<td><bean:message key="lbl.commissionForTarget" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
				 <html:text property="commissionForTarget" styleClass="text" styleId="commissionForTarget"
							 onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"/>
					    </td>	
				   
				 </tr>
				 <tr>
				 
				   <td>
							<bean:message key="lbl.payRecStatus" />
						</td>
						<td>
							<input type="checkbox" name="recStatus" id="recStatus"
								checked="checked" />
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						
				 </tr>
				 
				</logic:notPresent>

				<tr>
					<td>
						
						<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
						 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveSchemeBpMap();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

					</td>
				</tr>
				
 
 


			</table>


		</fieldset>


	</html:form>

	<logic:present name="sms">
		<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("schemeBpMapAdd").action="schemeBpMapBehind.do";
	    document.getElementById("schemeBpMapAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("schemeBpMapAdd").action="schemeBpMapBehind.do";
	    document.getElementById("schemeBpMapAdd").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='P')
	{
		alert("<bean:message key="lbl.payDataExistPriority" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	
</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>
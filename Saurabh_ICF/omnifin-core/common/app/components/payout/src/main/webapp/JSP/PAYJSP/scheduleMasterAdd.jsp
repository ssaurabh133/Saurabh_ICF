<!--Author Name : Ritesh Srivastava-->
<!--Date of Creation : 09 Aug 2012-->
<!--Purpose  : Schedule Master Search->
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
	<script type="text/javascript" 		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"		src="<%=request.getContextPath()%>/js/payoutScript/taxMaster.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/payoutScript/scheduleMaster.js"></script>
	<link type="text/css" 	href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript">
			
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('scheduleMasterAdd').activityId.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('scheduleMasterAdd').activityId.focus();
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

<body onload="enableAnchor();fntab();">


	<html:form styleId="scheduleMasterAdd" method="post"
		action="/scheduleMasterDispatch" >
		<fieldset>
			<legend>
				<bean:message key="lbl.payScheduleMaster" />
			</legend>
			<table cellpadding="0" cellspacing="1" width="100%">


				<logic:present name="editVal">
				<logic:notEmpty name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="M" />
					
					<logic:iterate id="listObj" name="list">
					<html:hidden property="batch_sch_id" styleClass="text"
							styleId="batch_sch_id"	value="${listObj.batch_sch_id}" />	
						<tr>
							<td>
								<bean:message key="lbl.payScheduleActivityCode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>

						<html:text property="activityId" styleClass="text" styleId="activityId" readonly="true"	tabindex="-1" value="${listObj.activityId}"/>
						<!--
						<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1364,'scheduleMasterAdd','activityId','','', '','','','lbxActivityCode','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
						-->
						<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
						<html:hidden  property="hidName" styleId="hidName" value="" />
						<html:hidden  property="lbxActivityCode" styleId="lbxActivityCode" value="${listObj.lbxActivityCode}" />
							
							</td>

							<td>
								<bean:message key="lbl.payScheduleStartMonth" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="startDate" styleClass="text7"
							styleId="startDate"		onkeyup="checkVariableDay(this.value,id)" onkeydown="checkVariableDay(this.value,id)" 
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);"	value="${listObj.startDate}" />
							/
							<html:text property="startMonth" styleClass="text7"
							styleId="startMonth"	onkeyup="checkVariableMonth(this.value,id)" onkeydown="checkVariableMonth(this.value,id)"
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);"	value="${listObj.startMonth}" />	
							</td>

						</tr>
						<tr>
						<td>
							<bean:message key="lbl.payScheduleEndMonth" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="endDate" styleClass="text7"
							styleId="endDate"		onkeyup="checkVariableDay(this.value,id)" onkeydown="checkVariableDay(this.value,id)" 
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);"	value="${listObj.endDate}" />
							/
							<html:text property="endMonth" styleClass="text7"
							styleId="endMonth"		onkeyup="checkVariableMonth(this.value,id)" onkeydown="checkVariableMonth(this.value,id)"
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);"	value="${listObj.endMonth}" />		
						</td>

						<td>
							<bean:message key="lbl.payScheduleRecStatus" />
							<span><font color="red">*</font>
							</span>
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
					</tr>
					
					</logic:iterate>
					</logic:notEmpty>
				</logic:present>

				<logic:notPresent name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="I" />
					<tr>
							<td>
								<bean:message key="lbl.payScheduleActivityCode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="activityId" styleClass="text" styleId="activityId" readonly="true"	tabindex="-1" />
						<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1364,'scheduleMasterAdd','activityId','','', '','','','lbxActivityCode','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
						<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
						<html:hidden  property="hidName" styleId="hidName" value="" />
						<html:hidden  property="lbxActivityCode" styleId="lbxActivityCode"  />
					
							</td>

							<td>
								<bean:message key="lbl.payScheduleStartMonth" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="startDate" styleClass="text7"
							styleId="startDate"		onkeyup="checkVariableDay(this.value,id)" onkeydown="checkVariableDay(this.value,id)" 
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);"	 />
							/
								<html:text property="startMonth" styleClass="text7"
							styleId="startMonth"		onkeyup="checkVariableMonth(this.value,id)" onkeydown="checkVariableMonth(this.value,id)"
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);"	 />		
							</td>

						</tr>
						<tr>
						<td>
							<bean:message key="lbl.payScheduleEndMonth" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="endDate" styleClass="text7"
							styleId="endDate"		onkeyup="checkVariableDay(this.value,id)" onkeydown="checkVariableDay(this.value,id)" 
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);"	 />
							/
							<html:text property="endMonth" styleClass="text7"
							styleId="endMonth"		onkeyup="checkVariableMonth(this.value,id)" onkeydown="checkVariableMonth(this.value,id)"
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);"	 />
						</td>

						<td>
							<bean:message key="lbl.payScheduleRecStatus" />
						</td>
						<td>
							<input type="checkbox" name="recStatus" id="recStatus"
								checked="checked" />
						</td>
					</tr>
					

				</logic:notPresent>

				<tr>
					<td>
						
						<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
						 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveSchedule();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

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
		document.getElementById("scheduleMasterAdd").action="scheduleMasterBehind.do";
	    document.getElementById("scheduleMasterAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("scheduleMasterAdd").action="scheduleMasterBehind.do";
	    document.getElementById("scheduleMasterAdd").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Invalid')
	{
		alert('<%=request.getAttribute("msg").toString()%>');
		
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
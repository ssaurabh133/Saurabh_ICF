<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 01 JULY 2012-->
<!--Purpose  : Activity Master Add->
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
		src="<%=request.getContextPath()%>/js/payoutScript/activityMaster.js"></script>


	<link type="text/css" 	href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('activityMasterAdd').activityDesc.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('activityMasterAdd').activityCode.focus();
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


	<html:form styleId="activityMasterAdd" method="post"
		action="/activityMasterDispatch" >
		<fieldset>
			<legend>
				<bean:message key="lbl.activityMaster" />
			</legend>
			<table cellpadding="0" cellspacing="1" width="100%">


				<logic:present name="editVal">
				<logic:notEmpty name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="M" />
					<logic:iterate id="listObj" name="list">
						<tr>
							<td>
								<bean:message key="lbl.activityCode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="activityCode" styleClass="text" styleId="activityCode"
									maxlength="10" readonly="true" value="${listObj.activityCode}" />
							</td>

							<td>
								<bean:message key="lbl.activityDesc" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="activityDesc" styleClass="text"
									styleId="activityDesc" maxlength="50"
									onblur="fnChangeCase('activityMasterAdd','activityDesc')"
									value="${listObj.activityDesc}" />
							</td>

						</tr>
						<tr>
						<td>
							<bean:message key="lbl.mappingSource" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:select property="sourceId" styleClass="text" styleId="sourceId"  value="${listObj.sourceId}">
							<html:option value="">Select</html:option>
							<html:optionsCollection name="sourceList" label="sourcedesc" value="sourceId"/>
							</html:select>
						</td>

						<td>
							<bean:message key="lbl.mappingCode" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="mappingCode" styleClass="text"  value="${listObj.mappingCode}"
								styleId="mappingCode" maxlength="10"
								onblur="fnChangeCase('activityMasterAdd','mappingCode')" />
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

						</tr>
					</logic:iterate>
					</logic:notEmpty>
				</logic:present>

				<logic:notPresent name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="I" />
					<tr>
						<td>
							<bean:message key="lbl.activityCode" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="activityCode" styleClass="text" styleId="activityCode"
								maxlength="10"
								onblur="fnChangeCase('activityMasterAdd','activityCode')" />
						</td>

						<td>
							<bean:message key="lbl.activityDesc" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="activityDesc" styleClass="text"
								styleId="activityDesc" maxlength="50"
								onblur="fnChangeCase('activityMasterAdd','activityDesc')" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.mappingSource" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:select property="sourceId" styleClass="text" styleId="sourceId" >
							<html:option value=""> Select</html:option>
							<html:optionsCollection name="sourceList" label="sourcedesc" value="sourceId"/>
							</html:select>
						</td>

						<td>
							<bean:message key="lbl.mappingCode" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="mappingCode" styleClass="text"
								styleId="mappingCode" maxlength="10"
								onblur="fnChangeCase('activityMasterAdd','mappingCode')" />
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

					</tr>

				</logic:notPresent>

				<tr>
					<td>
						
						<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
						 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveActivity();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

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
		document.getElementById("activityMasterAdd").action="activityMasterBehind.do";
	    document.getElementById("activityMasterAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("activityMasterAdd").action="activityMasterBehind.do";
	    document.getElementById("activityMasterAdd").submit();
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
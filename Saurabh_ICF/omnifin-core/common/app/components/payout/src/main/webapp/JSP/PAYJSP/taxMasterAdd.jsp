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
	<script type="text/javascript" 		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"		src="<%=request.getContextPath()%>/js/payoutScript/taxMaster.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<link type="text/css" 	href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('taxMasterAdd').AloanButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('taxMasterAdd').taxName.focus();
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


	<html:form styleId="taxMasterAdd" method="post"
		action="/taxMasterDispatch" >
		<fieldset>
			<legend>
				<bean:message key="lbl.taxMaster" />
			</legend>
			<table cellpadding="0" cellspacing="1" width="100%">


				<logic:present name="editVal">
				<logic:notEmpty name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="M" />
					
					<logic:iterate id="listObj" name="list">
						<tr>
							<td>
								<bean:message key="lbl.taxName" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="taxName" styleClass="text" styleId="taxName" onblur="fnChangeCase('taxMasterAdd','taxName')"
									maxlength="100"  value="${listObj.taxName}"  readonly="true"/>
									<input type="hidden" id="taxId" name="taxId" value="${listObj.taxId}" />
							</td>

                           <td>
							<bean:message key="lbl.taxPer" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="taxPer" styleClass="text"
							styleId="taxPer"		onkeypress="return numbersonly(event,id,3)" 
							onblur="formatNumber(this.value,id);"
							onkeyup="checkNumber(this.value, event, 3,id);" 
							onfocus="keyUpNumber(this.value, event, 3,id);" 	value="${listObj.taxPer}" />
						</td>
							

						</tr>
						<tr>
						<td>
								<bean:message key="lbl.activity" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="activityCode" styleClass="text" tabindex="-1" 
									styleId="activityCode" maxlength="50"	readonly="true" value="${listObj.activityDesc}" />
									<html:button property="AloanButton" styleId="loanButton" onclick="openLOVCommon(1364,'taxMasterAdd','activityCode','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
						             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
									<html:hidden  property="lbxActivityCode" styleId="lbxActivityCode" value="${listObj.activityCode}" />
									<html:hidden  property="hidName" styleId="hidName" value="" />
									
							</td>

						<td>
							<bean:message key="lbl.state" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						<div id="statedetail"> 
                 <input type="hidden" name="hcommon"  value="" id="hcommon" class="text">
                 
                <input type="text" name="state" id="state" size="20"  value="${listObj.stateName}" class="text" readonly="readonly" tabindex="-1"> 
    			 <input type="hidden" name="txtStateCode"  value="${listObj.stateId}" id="txtStateCode" class="text" > 
   				 <input type="hidden" name="txtCountryCode" id="txtCountryCode" value="1"/>
   				 <input type="hidden" name="country" id="country" value="INDIA"/> 
    	     	<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'taxMasterAdd','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	           
				</div> 
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
								<bean:message key="lbl.taxName" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="taxName" styleClass="text" styleId="taxName"
									onblur="fnChangeCase('taxMasterAdd','taxName')" maxlength="100"   />
							</td>
                       <td>
							<bean:message key="lbl.taxPer" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="taxPer" styleClass="text"
							styleId="taxPer"		onkeypress="return numbersonly(event,id,3)" 
							onblur="formatNumber(this.value,id);"
							onkeyup="checkNumber(this.value, event, 3,id);" 
							onfocus="keyUpNumber(this.value, event, 3,id);" 	 />
						</td>
							

						</tr>
						<tr>
						<td>
								<bean:message key="lbl.activity" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:text property="activityCode" styleClass="text" tabindex="-1" 
									styleId="activityCode" maxlength="50"	readonly="true" />
									<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1364,'taxMasterAdd','activityCode','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
						             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
									<html:hidden  property="lbxActivityCode" styleId="lbxActivityCode"  />
									<html:hidden  property="hidName" styleId="hidName" value="" />
										
							</td>

						<td>
							<bean:message key="lbl.state" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						<div id="statedetail"> 
                 <input type="hidden" name="hcommon"  value="" id="hcommon" class="text">
                 
                <input type="text" name="state" id="state" size="20"  value="" class="text" readonly="readonly" tabindex="-1"> 
    			 <input type="hidden" name="txtStateCode"  value="" id="txtStateCode" class="text"> 
   				 <input type="hidden" name="txtCountryCode" id="txtCountryCode" value="1"/>
   				 <input type="hidden" name="country" id="country" value="INDIA"/> 
    	     	<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'taxMasterAdd','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	           
				</div> 
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
						 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveTax();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

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
		document.getElementById("taxMasterAdd").action="taxMasterBehind.do";
	    document.getElementById("taxMasterAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("taxMasterAdd").action="taxMasterBehind.do";
	    document.getElementById("taxMasterAdd").submit();
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
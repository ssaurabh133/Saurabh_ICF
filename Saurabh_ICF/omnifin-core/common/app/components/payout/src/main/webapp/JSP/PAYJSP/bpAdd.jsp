<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 05 JULY 2012-->
<!--Purpose  : BP Master Add->
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
		src="<%=request.getContextPath()%>/js/payoutScript/bpMaster.js"></script>


	<link type="text/css" 	href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('bpMasterAdd').activityDesc.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('bpMasterAdd').activityCode.focus();
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


	<html:form styleId="bpMasterAdd" method="post"		action="/bpMasterDispatch" >
	 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	  <input type="hidden" name="hcommon"  id="hcommon"/>
	 
		<fieldset>
			<legend>
				<bean:message key="lbl.bpMaster" />
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
							<html:text property="bpName" styleClass="text" styleId="bpName"
							 value="${listObj.bpName}"	maxlength="50" />
							 <input type="hidden" name="bpId" id="bpId" value="${listObj.bpId}"/>
						</td>

						<td>
							<bean:message key="lbl.activity" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						  <html:select styleId="activityCode" property="activityCode" size="5" multiple="multiple" style="width: 120" tabindex="-1">
						  <html:optionsCollection value="lbxActivityCode" label="activityCode" name="activityList"/>
						  </html:select>
						  <html:button property="loanButton" styleId="loanButton" onclick="openMultiSelectLOVCommon(1365,'bpMasterAdd','activityCode','','', '','','','lbxActivityCode');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
						   <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
							<html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxActivityCode" styleId="lbxActivityCode" value="${activityStr}" />

						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.bpAdd" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:textarea property="bpAdd" styleClass="text" styleId="bpAdd" value="${listObj.bpAdd}"></html:textarea>
							</td>

							<td>
								<bean:message key="lbl.payCountry" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<input type="text" name="country" id="country" size="20" class="text"   value="${listObj.countryDesc}" readonly="readonly" tabindex="-1"> 
    			            <input type="hidden" name="txtCountryCode" id="txtCountryCode" class="text"   value="${listObj.txtCountryCode}" > 
    				        <html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'bpMasterAdd','country','','','','','clearCountryLovChildMaster','hcommon');" value=" " styleClass="lovbutton"> </html:button>	 
							</td>

						</tr>
						<tr>
					    <td><bean:message key="lbl.payState" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    <div id="statedetail"> 
                            <input type="text" name="state" id="state" size="20"   value="${listObj.txtStateCode}" class="text" readonly="readonly" tabindex="-1"> 
    			            <input type="hidden" name="txtStateCode"   id="txtStateCode" class="text"  value="${listObj.stateId}"/> 
   				             <html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('country');openLOVCommon(5,'bpMasterAdd','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChildMaster','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	           
				</div> 
					    </td>
					    <td><bean:message key="lbl.payCity" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
				<div id="cityID"> 
									 
				<input type="text" name="dist" id="dist" size="20"   value="${listObj.txtDistCode}" class="text" readonly="readonly"  tabindex="-1"> 
				<input type="hidden" name="txtDistCode" id="txtDistCode"   value="${listObj.districtId}" class="text"> 
				<html:button property="distButton" styleId="stateButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'bpMasterAdd','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
				
				</div>
					    </td>	
						</tr>
				 
						 <tr>
				 <td><bean:message key="lbl.bpMobile" />
								<span><font color="red">*</font>
								</span></td>
				  <td> 	<html:text property="bpMobile" styleClass="text"  value="${listObj.bpMobile}" onkeypress="return isNumberKey(event);"
								styleId="bpMobile" maxlength="12" /></td>
				   <td><bean:message key="lbl.bpPhone" />
								</td>
				    <td><html:text property="bpPhone" styleClass="text"  value="${listObj.bpPhone}" onkeypress="return isNumberKey(event);"
								styleId="bpPhone" maxlength="12" /></td>
				 </tr>
				 <tr>
				 <td><bean:message key="lbl.cpName" />
								<span><font color="red">*</font>
								</span></td>
				
				  <td><html:text property="cpName" styleClass="text"  value="${listObj.cpName}" 
								styleId="cpName" maxlength="100" /></td>
					 <td><bean:message key="lbl.cpAdd" />
								<span><font color="red">*</font>
								</span></td> 			 
				    <td><html:textarea property="cpAdd" styleClass="text" styleId="cpAdd" value="${listObj.cpAdd}"></html:textarea></td>
				 </tr>
				 <tr>
			
				 <td><bean:message key="lbl.cpCountry" />
								<span><font color="red">*</font></span></td>
					<td>
					<input type="text" name="cpCountry" id="cpCountry" size="20" class="text"   value="${listObj.cpCountryDesc}" readonly="readonly" tabindex="-1"> 
  			        <input type="hidden" name="cpCountryCode" id="cpCountryCode" class="text"   value="${listObj.cpCountryCode}"> 
  				    <html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(1367,'bpMasterAdd','cpCountry','','','','','','hcommon');" value=" " styleClass="lovbutton"> </html:button>	 
					</td>
					<td><bean:message key="lbl.cpState" />
								<span><font color="red">*</font></span></td>
				<td>
				
				<div id="statedetail"> 
				<input type="text" name="cpState" id="cpState" size="20"  value="${listObj.cpStateDesc}"class="text" readonly="readonly" tabindex="-1"> 
				<input type="hidden" name="cpStateCode"   value="${listObj.cpStateCode}"id="cpStateCode" class="text"> 
				<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('cpCountry');openLOVCommon(1368,'bpMasterAdd','cpState','cpCountry','cpStateCode', 'cpCountryCode','Please select country first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
				 
				</div> </td>
				 </tr>
				 <tr>
				 <td><bean:message key="lbl.cpCity" />
								<span><font color="red">*</font></span></td>
				  <td>
				 <div id="cityID"> 
				<input type="text" name="cpDist" id="cpDist" size="20"   value="${listObj.cpDistDesc}" class="text" readonly="readonly"  tabindex="-1"> 
				<input type="hidden" name="cpDistCode" id="cpDistCode"  value="${listObj.cpDistCode}"class="text"> 
				<html:button property="distButton" styleId="stateButton" onclick="closeLovCallonLov('cpState');openLOVCommon(1369,'bpMasterAdd','cpDist','cpState','cpDistCode', 'cpStateCode','Please select state first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
				
				</div>
				  </td>
				   <td><bean:message key="lbl.cpMobile" />
								<span><font color="red">*</font></span></td>
				    <td>	<html:text property="cpMobile" styleClass="text"  value="${listObj.cpMobile}" onkeypress="return isNumberKey(event);"
								styleId="cpMobile" maxlength="12" /></td>
				 </tr>
				 <tr>
				 
				 </tr>
					<tr>
						<td>
							<bean:message key="lbl.mappingSource" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:select property="sourceId" styleClass="text"  value="${listObj.sourceId}" styleId="sourceId" >
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
								styleId="mappingCode" maxlength="50"  value="${listObj.mappingCode}"
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
							<bean:message key="lbl.bpName" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="bpName" styleClass="text" styleId="bpName"
								maxlength="50" />
						</td>

						<td>
							<bean:message key="lbl.activity" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						  <html:select styleId="activityCode" property="activityCode" size="5" multiple="multiple" style="width: 120" tabindex="-1">
						  </html:select><html:button property="loanButton" styleId="loanButton" onclick="openMultiSelectLOVCommon(1365,'bpMasterAdd','activityCode','','', '','','','lbxActivityCode');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
						   <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
							<html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxActivityCode" styleId="lbxActivityCode" />

						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.bpAdd" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
								<html:textarea property="bpAdd" styleClass="text" styleId="bpAdd"></html:textarea>
							</td>

							<td>
								<bean:message key="lbl.payCountry" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<input type="text" name="country" id="country" size="20" class="text"  value="${defaultcountry[0].defaultcountryname}" readonly="readonly" tabindex="-1"> 
    			            <input type="hidden" name="txtCountryCode" id="txtCountryCode" class="text"  value="${defaultcountry[0].defaultcountryid}" > 
    				        <html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'bpMasterAdd','country','','','','','clearCountryLovChildMaster','hcommon');" value=" " styleClass="lovbutton"> </html:button>	 
							</td>

						</tr>
						<tr>
					    <td><bean:message key="lbl.payState" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    <div id="statedetail"> 
                            <input type="text" name="state" id="state" size="20"  value="" class="text" readonly="readonly" tabindex="-1"> 
    			            <input type="hidden" name="txtStateCode"   id="txtStateCode" class="text"> 
   				             <html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('country');openLOVCommon(5,'bpMasterAdd','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChildMaster','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	           
				</div> 
					    </td>
					    <td><bean:message key="lbl.payCity" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
				<div id="cityID"> 
									 
				<input type="text" name="dist" id="dist" size="20"  value="" class="text" readonly="readonly"  tabindex="-1"> 
				<input type="hidden" name="txtDistCode" id="txtDistCode"  value="" class="text"> 
				<html:button property="distButton" styleId="stateButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'bpMasterAdd','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
				
				</div>
					    </td>	
						</tr>
				 
						 <tr>
				 <td><bean:message key="lbl.bpMobile" />
								<span><font color="red">*</font>
								</span></td>
				  <td> 	<html:text property="bpMobile" styleClass="text" onkeypress="return isNumberKey(event);"
								styleId="bpMobile" maxlength="12" /></td>
				   <td><bean:message key="lbl.bpPhone" />
								</td>
				    <td><html:text property="bpPhone" styleClass="text" onkeypress="return isNumberKey(event);"
								styleId="bpPhone" maxlength="12" /></td>
				 </tr>
				 <tr>
				 <td><bean:message key="lbl.cpName" />
								<span><font color="red">*</font>
								</span></td>
				
				  <td><html:text property="cpName" styleClass="text"
								styleId="cpName" maxlength="100" /></td>
					 <td><bean:message key="lbl.cpAdd" />
								<span><font color="red">*</font>
								</span></td> 			 
				    <td><html:textarea property="cpAdd" styleClass="text" styleId="cpAdd"></html:textarea></td>
				 </tr>
				 <tr>
			
				 <td><bean:message key="lbl.cpCountry" />
								<span><font color="red">*</font></span></td>
					<td>
					<input type="text" name="cpCountry" id="cpCountry" size="20" class="text"  value="${defaultcountry[0].defaultcountryname}" readonly="readonly" tabindex="-1"> 
  			        <input type="hidden" name="cpCountryCode" id="cpCountryCode" class="text"  value="${defaultcountry[0].defaultcountryid}" > 
  				    <html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(1367,'bpMasterAdd','cpCountry','','','','','','hcommon');" value=" " styleClass="lovbutton"> </html:button>	 
					</td>
					<td><bean:message key="lbl.cpState" />
								<span><font color="red">*</font></span></td>
				<td>
				
				<div id="statedetail"> 
				<input type="text" name="cpState" id="cpState" size="20"  value="" class="text" readonly="readonly" tabindex="-1"> 
				<input type="hidden" name="cpStateCode"  value="" id="cpStateCode" class="text"> 
				<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('cpCountry');openLOVCommon(1368,'bpMasterAdd','cpState','cpCountry','cpStateCode', 'cpCountryCode','Please select country first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
				 
				</div> </td>
				 </tr>
				 <tr>
				 <td><bean:message key="lbl.cpCity" />
								<span><font color="red">*</font></span></td>
				  <td>
				 <div id="cityID"> 
				<input type="text" name="cpDist" id="cpDist" size="20"  value="" class="text" readonly="readonly"  tabindex="-1"> 
				<input type="hidden" name="cpDistCode" id="cpDistCode"  value="" class="text"> 
				<html:button property="distButton" styleId="stateButton" onclick="closeLovCallonLov('cpState');openLOVCommon(1369,'bpMasterAdd','cpDist','cpState','cpDistCode', 'cpStateCode','Please select state first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
				
				</div>
				  </td>
				   <td><bean:message key="lbl.cpMobile" />
								<span><font color="red">*</font></span></td>
				    <td>	<html:text property="cpMobile" styleClass="text" onkeypress="return isNumberKey(event);"
								styleId="cpMobile" maxlength="12" /></td>
				 </tr>
				 <tr>
				 
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
								styleId="mappingCode" maxlength="50"
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
						 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveBP();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

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
		document.getElementById("bpMasterAdd").action="bpMasterBehind.do";
	    document.getElementById("bpMasterAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("bpMasterAdd").action="bpMasterBehind.do";
	    document.getElementById("bpMasterAdd").submit();
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
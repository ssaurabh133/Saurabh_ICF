<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	UserObject userobj = (UserObject) session
			.getAttribute("userobject");
	String initiationDate = userobj.getBusinessdate();
%>

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
	
	<script type="text/javascript" 
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->

	<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImagetransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImagetransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->
		
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/capsScript/contactRecordingScript.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" 
	src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>


<body onload="enableAnchor();fntab();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">

	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<div id="centercolumn">
		<div id="revisedcontainer">


<html:form action="/collButtonAction" styleId="foreclosureForm" method="post" >
	<input type="hidden" id="foreclosureType" name="foreclosureType" value="T"/>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<fieldset>
		<legend><bean:message key="lbl.duesAndRefunds" /></legend>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr>
									<td width="25%">
										<bean:message key="lbl.loanAc" />
									</td>
									<td width="25%">
										<input type="hidden" class="text" id="loanNO" maxlength="20"
											class="loanNO" value="${requestScope.loanId}"/>
										<html:text styleClass="text" styleId="loanNumber" maxlength="20"
											property="loanNumber" value="${requestScope.loanno}" readonly="true" tabindex="-1" />
											<input type="hidden" class="text" id="loanN" maxlength="20"
											name="loanId" value="${requestScope.loanId}" 	/>
									</td>
									
									<td>
										<bean:message key="lbl.effectiveDate" /><span><font color="red">*</font></span>
									</td>
									<td>
										<html:text styleClass="text" styleId="effectiveDate"
											maxlength="10" property="effectiveDate"
											value="${requestScope.effectiveDate}"
											onchange="checkDate('effectiveDate');checkMaturityDateForMatClo();" />
									</td>
									
								</tr>
								<tr>
									<td colspan="2">
										<html:button property="getDetail" styleId="getDetail"
											value="Get Detail" styleClass="topformbutton2"
											onclick="showForeClosureData();"
											accesskey="D" title="Alt+D" />
									</td>
									
								</tr>
							</table>
						</fieldset>

						<fieldset>
							<legend>
								<bean:message key="lbl.duesAndRefunds" />
							</legend>
								<logic:present name="closureData">
								<logic:notEmpty name="closureData">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								
								<tr>
									<td width="25%"></td>
									<td width="25%" style="padding-left:40px">
										<b><bean:message key="lbl.dues" /></b>
									</td>
									<td width="25%"></td>
									<td width="25%" style="padding-left:40px">
									
										<b><bean:message key="lbl.refunds" /></b>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.balancePrincipal" />
									</td>
									<td>
										<html:text styleClass="text" styleId="balancePrincipal"
											readonly="true" value="${closureData[0].balancePrincipal}"
											maxlength="18" property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDeposit" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDeposit"
											readonly="true" value="${closureData[0].secureDeposit}"
											maxlength="18" property="secureDeposit" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overduePrincipal"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="overduePrincipal"
											readonly="true" value="${closureData[0].overduePrincipal}"
											maxlength="18" property="overduePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositInterest"
											value="${closureData[0].secureDepositInterest}"
											readonly="true" maxlength="18"
											property="secureDepositInterest" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overdueInstallments" />
									</td>
									<td>
										<html:text styleClass="text" styleId="overdueInstallments"
											readonly="true" value="${closureData[0].overdueInstallments}"
											maxlength="18" property="overdueInstallments" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositTDS"
											value="${closureData[0].secureDepositTDS}" readonly="true"
											maxlength="18" property="secureDepositTDS" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.interestTillDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="interestTillDate"
											value="${closureData[0].interestTillDate}" readonly="true"
											maxlength="18" property="interestTillDate" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDInterest"
											value="${closureData[0].gapSDInterest}" readonly="true"
											maxlength="18" property="gapSDInterest" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.unBilledInstallments" />
									</td>
									<td>
										<html:text property="unBilledInstallments"
											styleId="unBilledInstallments" styleClass="text"
											value="${closureData[0].unBilledInstallments}" maxlength="18"
											readonly="true" tabindex="-1" style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDTDS"
											value="${closureData[0].gapSDTDS}" maxlength="18"
											property="gapSDTDS" readonly="true" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.otherDues" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherDues"
											readonly="true" value="${closureData[0].otherDues}"
											maxlength="18" property="otherDues" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.otherRefunds" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherRefunds"
											readonly="true" value="${closureData[0].otherRefunds}"
											maxlength="18" property="otherRefunds" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.foreClosurePenalty" />
									</td>
									<td>
										<html:text styleClass="text" styleId="foreClosurePenalty"
											value="${closureData[0].foreClosurePenalty}"
											property="foreClosurePenalty" readonly="true" tabindex="-1"
											maxlength="18" style="text-align:right;"/>
									</td>
									<!--
								-->
								<!-- Change  advanceEmiRefunds Added By Arun  -->
									<td>
									<bean:message key="lbl.advanceEmiRefunds" />
									</td>
									<td><html:text styleClass="text" styleId="advanceEmiRefunds"
											value="${closureData[0].advanceEmiRefunds}" maxlength="18" property="advanceEmiRefunds"
											style="text-align:right;" readonly="true" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.lppAmount"/>
									</td>
									<td>
										<html:text property="lppAmount" styleId="lppAmount" 
											styleClass="text" maxlength="18" readonly="true"
											tabindex="-1" value="${closureData[0].lppAmount}"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.waiveOffAmount"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="waiveOffAmount"
											value="${closureData[0].waiveOffAmount}"
											maxlength="18" property="waiveOffAmount"
											onkeypress="return numbersonly(event,id,18)" 
											onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"
											style="text-align:right;"
											onfocus="keyUpNumber(this.value, event, 18,id);"  readonly="true"  tabindex="-1"/>
									</td>
									
								</tr>
								<tr>
								<td>
									<bean:message key="lbl.interstTillLP"/>
								</td>
								<td>
									<html:text property="interstTillLP" styleId="interstTillLP"
										styleClass="text" value="${closureData[0].interstTillLP}" 
										readonly="true" maxlength="18" tabindex="-1"
										style="text-align:right;"/>
								</td>
								<td>
										<bean:message key="lbl.netReceivablePayable" />
									</td>
									<td>
										<html:text styleClass="text" styleId="netReceivablePayable"
											value="${closureData[0].netReceivablePayable}"
											readonly="true" maxlength="18"
											property="netReceivablePayable" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
							</table>
						
			</logic:notEmpty>
			</logic:present>
	<logic:notPresent name="closureData">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								
								<tr>
									<td width="25%"></td>
									<td width="25%" style="padding-left:40px">
										<b><bean:message key="lbl.dues" /></b>
									</td>
									<td width="25%"></td>
									<td width="25%" style="padding-left:40px">
									
										<b><bean:message key="lbl.refunds" /></b>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.balancePrincipal" />
									</td>
									<td>
										<html:text styleClass="text" styleId="balancePrincipal"
											readonly="true" value=""
											maxlength="18" property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDeposit" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDeposit"
											readonly="true" value=""
											maxlength="18" property="secureDeposit" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overduePrincipal"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="overduePrincipal"
											readonly="true" value=""
											maxlength="18" property="overduePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositInterest"
											value=""
											readonly="true" maxlength="18"
											property="secureDepositInterest" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overdueInstallments" />
									</td>
									<td>
										<html:text styleClass="text" styleId="overdueInstallments"
											readonly="true" value=""
											maxlength="18" property="overdueInstallments" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositTDS"
											value="" readonly="true"
											maxlength="18" property="secureDepositTDS" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.interestTillDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="interestTillDate"
											value="" readonly="true"
											maxlength="18" property="interestTillDate" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDInterest"
											value="" readonly="true"
											maxlength="18" property="gapSDInterest" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.unBilledInstallments" />
									</td>
									<td>
										<html:text property="unBilledInstallments"
											styleId="unBilledInstallments" styleClass="text"
											value="" maxlength="18"
											readonly="true" tabindex="-1" style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDTDS"
											value="" maxlength="18"
											property="gapSDTDS" readonly="true" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.otherDues" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherDues"
											readonly="true" value=""
											maxlength="18" property="otherDues" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.otherRefunds" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherRefunds"
											readonly="true" value=""
											maxlength="18" property="otherRefunds" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.foreClosurePenalty" />
									</td>
									<td>
										<html:text styleClass="text" styleId="foreClosurePenalty"
											value=""
											property="foreClosurePenalty" readonly="true" tabindex="-1"
											maxlength="18" style="text-align:right;"/>
									</td><!--
									
								-->
								<!-- Change  advanceEmiRefunds Added By Arun  -->
									<td>
									<bean:message key="lbl.advanceEmiRefunds" />
									</td>
									<td><html:text styleClass="text" styleId="advanceEmiRefunds"
											value="${closureData[0].advanceEmiRefunds}" maxlength="18" property="advanceEmiRefunds"
											style="text-align:right;" readonly="true" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.lppAmount"/>
									</td>
									<td>
										<html:text property="lppAmount" styleId="lppAmount" 
											styleClass="text" maxlength="18" readonly="true"
											tabindex="-1" value=""
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.waiveOffAmount"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="waiveOffAmount"
										value=""
										     maxlength="18" property="waiveOffAmount"
											onkeypress="return numbersonly(event,id,18)"  readonly="true" tabindex="-1"
											onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"
											style="text-align:right;"
											onfocus="keyUpNumber(this.value, event, 18,id);"/>
									</td>
									
								</tr>
								<tr>
								<td>
									<bean:message key="lbl.interstTillLP"/>
								</td>
								<td>
									<html:text property="interstTillLP" styleId="interstTillLP"
										styleClass="text" value="" 
										readonly="true" maxlength="18" tabindex="-1"
										style="text-align:right;"/>
								</td>
								<td>
										<bean:message key="lbl.netReceivablePayable" />
									</td>
									<td>
										<html:text styleClass="text" styleId="netReceivablePayable"
											value=""
											readonly="true" maxlength="18"
											property="netReceivablePayable" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
							</table>
						
			
			</logic:notPresent>

	</html:form>
		</div>
		</div>
		<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
		<logic:present name="error">
	<script type="text/javascript">	
		alert('${error}');
		alert("Report can not be Generate.");
	</script>
	</logic:present>
		</body>
		</html:html>
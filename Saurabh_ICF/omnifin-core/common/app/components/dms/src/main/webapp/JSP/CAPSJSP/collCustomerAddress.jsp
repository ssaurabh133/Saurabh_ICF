<%--
 	Auther Name :- Kanika maheshwari
 	Date of Creation :- 
 	Purpose :- To capture address details
 	Documentation :-  
 --%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
	<html>
	
	<head>
	<title><bean:message key="a3s.noida" /></title>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
	   
		 
	
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/contactRecordingScript.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   
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

<DIV id=centercolumn >
<DIV id=revisedcontainer>

<br />
<FIELDSET ><LEGEND><bean:message key="address.contact" /></LEGEND>
<body oncontextmenu="return false" onload="clearInput();fntab();">

<center><font color="#FF0000"></font></center>

<html:form action="/collAddressAction" styleId="customerForm">
	   <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath">
<html:hidden  property="bpid" styleId="bpid" value="${requestScope.loanId}"/>
<html:javascript formName="CollAddressValidatorForm" />
		<html:errors />
		 
 <input type="hidden" name="hcommon" id="hcommon"> 
<table width="100%" cellspacing="0" cellpadding="3" border="0"> 
					 
				<tr> 
					
					<td><bean:message key="address.type" /><font color="red">*</font></td> 
					<td><input type="hidden" name="hiddenIndividualId" id="hiddenIndividualId" value="">
					<input type="hidden" name="flag" id="flag" value="corporate"> 
								 
				 
						<html:select property="addr_type" styleId="addr_type" styleClass="text"   value="" > 
						   <option value=""> 
						   <bean:message key="select" /></option> 
						 
									<html:optionsCollection name="addrList" label="add_desc" value="add_val" /> 
						 
						</html:select> 
					</td> 
								 
					<td> 
			 
						<html:hidden property="bp_type" styleClass="text" styleId="bp_type" value="CS" /> 
			 
					<bean:message key="address.addr.one" /><font color="red">*</font></td> 
					<td><html:text property="addr1" styleClass="text" styleId="addr1" maxlength="50"  value="" onkeyup="return upperMe('addr1');"></html:text> 
					  
					</td> 
					 
				</tr>	 
				<tr> 
					<td><bean:message key="address.addr.two" /></td> 
					<td><html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50"  value="" onkeyup="return upperMe('addr2');"></html:text></td>	 
                     <td><bean:message key="address.country" /><font color="red">*</font></td> 
           			 <td> 
           
          			<input type="text" name="country" id="country" size="20" class="text"  value="${defaultcountry[0].defaultcountryname}" readonly="readonly" tabindex="-1"> 
    			 
   						<input type="hidden" name="txtCountryCode" id="txtCountryCode" class="text"  value="${defaultcountry[0].defaultcountryid}" > 
    				 
    			<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'customerForm','country','','','','','clearCountryLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>	 
	             
					</td> 
  				</tr> 
 
				<tr> 
 
                <td><bean:message key="address.addr.three" /></td> 
				<td><html:text property="addr3" styleClass="text" styleId="addr3" maxlength="50"  value="" onkeyup="return upperMe('addr3');"></html:text></td>					 
          
              <td><bean:message key="address.state" /><font color="red">*</font> </td> 
              <td> 
                <div id="statedetail"> 
                 
                 
                <input type="text" name="state" id="state" size="20"  value="" class="text" readonly="readonly" tabindex="-1"> 
    			 
     					<input type="hidden" name="txtStateCode"  value="" id="txtStateCode" class="text"> 
   				 
   					 
    		<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('country');openLOVCommon(5,'customerForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	           
				</div> 
			 </td> 
		   </tr> 
		<tr> 
		<td><bean:message key="address.dist" /><font color="red">*</font></td> 
          <td> 
           
          						<div id="cityID"> 
          						 
          						<input type="text" name="dist" id="dist" size="20"  value="" class="text" readonly="readonly"  tabindex="-1"> 
   								<input type="hidden" name="txtDistCode" id="txtDistCode"  value="" class="text"> 
   								<html:button property="distButton" styleId="stateButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'customerForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
   								 
                                </div></td> 
		   
					<td><bean:message key="address.pincode" /><font color="red">*</font></td> 
					<td><html:text maxlength="6" property="pincode" styleClass="text"  value="" styleId="pincode" style="text-align: right;" onkeypress="return isNumberKey(event);"></html:text></td>				 
				</tr>	 
				<tr> 
					<td><bean:message key="address.primary" /><font color="red">*</font></td> 
					<td><html:text property="primaryPhoneNo"  value="" styleId="primaryPhoneNo" styleClass="text" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"></html:text></td>	 
                    <td><bean:message key="address.alter" /></td> 
					<td><html:text property="alternatePhoneNo"  value="" maxlength="10" styleId="alternatePhoneNo" styleClass="text" style="text-align: right;" onkeypress="return isNumberKey(event);"></html:text></td>	 
				</tr>	 
				<tr> 
					<td><bean:message key="address.toll" /></td> 
					<td><html:text property="tollfreeNo" maxlength="25"  value="" styleId="tollfreeNo" styleClass="text" style="text-align: right;" onkeypress="return isNumberKey(event);"></html:text></td>	 
                    <td><bean:message key="address.fax" /></td> 
					<td><html:text property="faxNo" maxlength="25"  value="" styleId="faxNo" styleClass="text" style="text-align: right;" onkeypress="return isNumberKey(event);"></html:text></td>	 
				</tr>	 
				<tr> 
					<td><bean:message key="address.land" /></td> 
					<td><html:text property="landMark" maxlength="25" styleId="landMark" styleClass="text"  value="" ></html:text></td>	 
                    <td><bean:message key="address.years" /><font color="red">*</font></td> 
					<td><html:text property="noYears" maxlength="3" styleId="noYears"  value="" styleClass="text" onkeypress="return numbersonly(event, id, 3)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 3, id);"></html:text></td>					 
				</tr>	 
				<tr> 
					<td><bean:message key="lbl.communication.address" /></td> 
					<td> 
					 
			 
         		<input type="checkbox" name="communicationAddress" id="communicationAddress"> 
          
					</td> 
					 
				</tr> 
<tr> 
           
          <td><span class="white"> 
         
<!--<html:button property="button1" styleClass="topformbutton2" styleId="save" value="Save" title="Alt+V" accesskey="V"
 onclick="this.disabled=true; return saveAddress();"></html:button> -->
        <button type="button" class="topformbutton2" name="button1"  id="save"  onclick="this.disabled='true';return saveAddress();"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>       
            </span></td> 
</tr></table>

		<logic:present name="sms">
<script type="text/javascript">

    
			if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		self.close();
	}
	else
	{
		alert("<bean:message key="msg.notepadError" />");
		self.close();
		
	}
	
</script>
</logic:present>
</html:form>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</FIELDSET>
</DIV>
</DIV></HTML>
	  


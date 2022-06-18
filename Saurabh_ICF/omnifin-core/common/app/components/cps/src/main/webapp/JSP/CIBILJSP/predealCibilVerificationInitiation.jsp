<!--Author Name : Manish Shukla-->
<!--Date of Creation : -->
<!--Purpose  :Cibil Initiation & Verification-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=path%>/js/cpScript/cpPreDeal.js"></script>
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

<script type="text/javascript">


function disableData(obj)
{
if(obj=='leadId')
	{
	//alert("hello");
	document.getElementById('dealID').value='';
	document.getElementById('dealNO').value='';
	document.getElementById('customerName').value='';
	}
else
	{	
	//alert("hello 3");
	//document.getElementById('lbxLeadId').value='';
	//alert("hello 3");
//	document.getElementById('leadno').value='';
	//alert("hello 3");
//	document.getElementById('custName').value='';
	}
	
}

</script>
</head>

<body onload="enableAnchor();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="cibilVerification"  method="post"  action="/preDealcibilVerificationDispatchAction" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:hidden property="leadId" styleId="leadId" value="${requestScope.lbxLeadId}" />	


<fieldset><legend><bean:message key="lbl.predeals"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td class="gridtd">
		   <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		<tr class="white2">
    	 		<td align="center"><b>Select</b></td>
	 	 		
	 	  	<td align="center"><b><bean:message key="lbl.leadNo"/></b></td>
         		<td align="center"><b><bean:message key="lbl.customerName"/></b></td>
         		<td align="center"><b><bean:message key="lbl.Type"/></b></td>
         		<td align="center"><b><bean:message key="lbl.gender"/></b></td>
         		<td align="center"><b><bean:message key="lbl.customerRole"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.memberName"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.memberId"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.No"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.panNo"/></b></td>
	     		<td align="center"><b><bean:message key="voterId"/></b></td>
	     		<td align="center"><b><bean:message key="passport"/></b></td>
	     		<td align="center"><b><bean:message key="drivingLicense"/></b></td>
	     		<td align="center"><b><bean:message key="aadhaar"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.primary"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.cibilStatus"/></b></td>
	     		
		    </tr>
			<logic:iterate name="gridList" id="sublist" indexId="id">
			<tr class="white1">
				<td><input type='checkbox' name='chkCases' value='${sublist.refranceNo}'/></td>
	     		<td>${sublist.dealNO}</td>
	     		<td>${sublist.customerName}
	     		<input type="hidden" id="<%="fname"+id %>" name="fname" value="${sublist.customerName}"></td>
	    		<td>${sublist.customerType}</td>
	    		<td>${sublist.customerGender}
	    		<input type="hidden" id="<%="gender"+id %>" name="gender" value="${sublist.customerGender}"></td>	
	    		<td>${sublist.customerRole}</td>
	    		<td>${sublist.memberName}</td>	
	    		<td>${sublist.memberId}</td>	
	    		<td>${sublist.inquiryPurpus}</td>		
	    		<input type="hidden" id="<%="dob"+id %>" name="dob" value="${sublist.customerDob}">
	    		<input type="hidden" id="<%="state"+id %>" name="state" value="${sublist.state}">	
	    		<input type="hidden" id="<%="pincode"+id %>" name="pincode" value="${sublist.pincode}">		
	    		<td>${sublist.panNo}
	    		<input type="hidden" id="<%="pan"+id %>" name="pan" value="${sublist.panNo}"></td>	
	    		<td>${sublist.voterId}
	    		<input type="hidden" id="<%="voterid"+id %>" name="voterid" value="${sublist.voterId}"></td>
	    		<td>${sublist.passport}
	    		<input type="hidden" id="<%="passport"+id %>" name="passport" value="${sublist.passport}"></td>	
	    		<td>${sublist.drivingLicense}
	    		<input type="hidden" id="<%="drivinglicence"+id %>" name="drivinglicence" value="${sublist.drivingLicense}"></td>	
	    		<td>${sublist.uid}
	    		<input type="hidden" id="<%="uid"+id %>" name="uid" value="${sublist.uid}"></td>	
	    		<td>${sublist.primaryPhone}</td>	  		    	
	    		<td>${sublist.cibilDone}</td>	    	
	     	</tr> 
   			</logic:iterate>
   		 	<td colspan="16" class="white1"><button type="button" name="save&forward" title="Alt+S" accesskey="S" id="button" class="topformbutton3" onclick="return forwordCibilIniPreDeal();"><bean:message key="button.cibilInit" /></button></td>
   		 </table>
   	</td>
   </tr>
   </table>
</fieldset>

<fieldset><legend><bean:message key="lbl.predeals"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td class="gridtd">
		   <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		<tr class="white2">
    	  	<td align="center"><b>Select</b></td> 
	 	 		<td align="center"><b><bean:message key="lbl.customerId"/></b></td>
	 	 		<td align="center"><b><bean:message key="lbl.customerName"/></b></td>
         		<td align="center"><b><bean:message key="lbl.Type"/></b></td>	
         		<td align="center"><b><bean:message key="lbl.customerRole"/></b></td>
         		<td align="center"><b><bean:message key="lbl.cibilScore"/></b></td>
         		<td align="center"><b><bean:message key="lbl.crifHighMarkScore"/></b></td>
         		<td align="center"><b><bean:message key="lbl.experianScore"/></b></td>
         		<td align="center"><b><bean:message key="lbl.cibilStatus"/></b></td>
        
			 <logic:iterate name="roleList" id="sublist" indexId="index">
			<input type="hidden"  name="cibilDone"  value="${sublist.cibilDone}" id="cibilDone<%=index.intValue()%>"/>
			<input type="hidden" name="customerId" id="customerId<%=index.intValue()%>" value="${sublist.customerId}" />	
			<input type="hidden" name="leadId" id="leadId" value="${sublist.dealId}" />
			<tr class="white1">
				<td align="center"><input type="radio" name="radioId" id="radioId<%=index.intValue()%>"  value="<%=index.intValue()%>" /></td>
	     		
	      	  <td>${sublist.customerId}</td>
	     		<td>${sublist.customerName}</td>
	     		<td>${sublist.applicantCategory}</td>
	    		   
	    		<td>${sublist.applicantType}</td>
	    		<td>${sublist.cibilScore}</td>
	    		<td>${sublist.crifHighMarkScore}</td>
	    		<td>${sublist.experianScore}</td>
				<td>${sublist.cibilResponse}</td>
	    	
	    		<input type="hidden" name="lbxCibilId" id="lbxCibilId" value="${sublist.lbxCibilId}" />		 
	    		   	
	     	</tr> 
   			</logic:iterate>
   			<tr>
   		 	<td colspan="32" class="white1"><button type="button" name="viewGenerate" title="Alt+S" accesskey="S" id="button" class="topformbutton3" onclick=" viewPreDealCibilDone();"><bean:message key="button.viewCibilReport" /></button></td>
   		 	</tr>
   		 </table>
   	</td>
   </tr>
   </table>
     <fieldset>
<legend><bean:message key="lbl.cibilVerificationHst" /></legend>
<table width="100%">
    <tr>
    	<td>Report Name<span><font color="red">*</font></span></td>
    	<td>
				<html:hidden styleClass="text" property="LinkId" styleId="LinkId" value=""/>
				<html:text styleClass="text" maxlength="10" property="reportName" styleId="reportName" readonly="true" value="" tabindex="-1"/>
				<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(192032,'cibilVerificationDynaValidatorForm','LinkId','','','','','','reportName');" value=" " styleClass="lovbutton"></html:button>
   		</td>  
   	 </tr>
   	 <tr>
   	 <td>
   		<button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return generateHistoricalData();">ViewGenerate</button>
   		</td>
   	 </tr>
      
 </table>
</fieldset>
</fieldset>
</html:form>		
	
	<logic:present name="success">
	<script type="text/javascript">	
		alert('Successfully Response Recived From Bureau.');
		top.opener.location.href="preDealCapturingBehind.do";
	</script>
	</logic:present>
	<logic:present name="error">
	<script type="text/javascript">	
		alert('Some Error Occured During Communication.');
		top.opener.location.href="preDealCapturingBehind.do";
	</script>
	</logic:present>
	<logic:present name="msg">
	<script type="text/javascript">	
		alert('Bureau can not be initiate. Data Successfully Forward.');
	</script>
	</logic:present>
	<logic:present name="dedupe">
	<script type="text/javascript">	
		alert('Please save Dedupe Referral first!!!');
		window.close();
	</script>
	</logic:present>
	<logic:present name="imd">
	<script type="text/javascript">	
		alert('Please Capture IMD Details first!!!');
		window.close();
	</script>
	</logic:present>
	<logic:present name="reportError">
	<script type="text/javascript">	
		alert('No Such File Exist.');
	</script>
	</logic:present>	
	</body>	
	</html:html>
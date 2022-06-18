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
<script type="text/javascript" src="<%=path%>/js/cpScript/cibilVerification.js"></script>
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
<html:form styleId="cibilVerification"  method="post"  action="/cibilVerificationDispatchAction" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.cibilVerification" /></legend>
  <table width="100%">
    <tr>
    	<td><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
    	<td>
				<html:hidden styleClass="text" property="dealID" styleId="dealID" value="${gridList[0].dealID}"/>
				<html:text styleClass="text" maxlength="10" property="dealNO" styleId="dealNO" readonly="true" value="${gridList[0].dealNO}" tabindex="-1"/>
				<html:button property="dealButton" styleId="dealButton" onclick="disableData('dealId');openLOVCommon(2009,'cibilVerificationDispatchAction','dealNO','','','','','','customerName');" value=" " styleClass="lovbutton"></html:button>
   		</td>  
   		<td><bean:message key="lbl.customerName"/></td>
		<td><html:text styleClass="text" maxlength="10" property="customerName" styleId="customerName" style="" readonly="true" value="${gridList[0].customerName}" maxlength="100"/></td>
   </tr>
      
   <!-- parvez starts -->
   
   
  <!-- <tr>
    	<td><bean:message key="lbl.leadNumber"/><span><font color="red">*</font></span></td>
    	<td>
				<input type="hidden" name="lbxLeadId" id="lbxLeadId" value="${lbxLeadId}" />
						<html:text property="leadno" styleClass="text" styleId="leadno" value="${leadno} "
							maxlength="50" readonly="true" tabindex="-1" />
				<html:button property="leadButton" styleId="leadButton" onclick="disableData('leadId');openLOVCommon(5056,'cibilVerification','leadno','','', '','','','custName');" value=" " styleClass="lovbutton"></html:button>
   		</td>  
   		<td><bean:message key="lbl.customerName"/></td>
		<td><html:text styleClass="text" maxlength="10" property="custName" styleId="custName" style="" readonly="true" value="${custName}" maxlength="100"/></td>
   </tr> -->
   
   <!-- end parvez -->
   
   <!--   <tr>
   			<td><bean:message key="lbl.cibilId"/></td>
    	<td>
				<html:hidden styleClass="text" property="lbxCibilId" styleId="lbxCibilId" value="${gridList[0].lbxCibilId}"/>
				<html:text styleClass="text" maxlength="10" property="cibilNo" styleId="cibilNo" readonly="true" value="${gridList[0].cibilNo}" tabindex="-1"/>
				<html:button property="cibilButton" styleId="cibilButton" onclick="openLOVCommon(528,'cibilVerificationDispatchAction','cibilNo','','','','','getReportStatus','customerId','custName','CibilStatus');" value=" " styleClass="lovbutton"></html:button>
				<input type="hidden" name="customerId" id="customerId"/>
				<input type="hidden" name="custName" id="custName"/>
				<input type="hidden" name="CibilStatus" id="CibilStatus"/>
   		</td> 
  	
   </tr>
    	-->
   <tr>
   		<td>
   		<button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return searchDeal();"><bean:message key="button.search" /></button>
   	<!-- 	 <button type="button" name=" view"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="viewCibilReport();"  ><bean:message key="button.generate" /></button>  -->
   		</td>
   		
   </tr> 
 </table>		
</fieldset>

<logic:present name="gridList">	
<logic:notEmpty name="gridList">
<fieldset><legend><bean:message key="lbl.deals"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td class="gridtd">
		   <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		<tr class="white2">
    	 		<td align="center"><b>Select</b></td>
	 	 	<td align="center"><b><bean:message key="lbl.dealNo"/></b></td>
	 	 	<%-- 	<td align="center"><b><bean:message key="lbl.leadNo"/></b></td> --%>
         		<td align="center"><b><bean:message key="lbl.customerName"/></b></td>
         		<td align="center"><b><bean:message key="lbl.Type"/></b></td>
			<td align="center"><b><bean:message key="lbl.cibilStatus"/></b></td>
			<td align="center"><b><bean:message key="lbl.cibilScore"/></b></td>
			<td align="center"><b><bean:message key="lbl.crifHighMarkScore"/></b></td>
			<td align="center"><b><bean:message key="lbl.cibilReport"/></b></td>
         		<td align="center"><b><bean:message key="lbl.gender"/></b></td>
         		<td align="center"><b><bean:message key="lbl.customerRole"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.memberName"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.memberId"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.inquiryPurpus"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.refranceNo"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.dob"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address1"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address2"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address3"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.addressType"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.addressDetail"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.dist"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.state"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.pincode"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.country"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.panNo"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.No"/></b></td>
	     		<td align="center"><b><bean:message key="voterId"/></b></td>
	     		<td align="center"><b><bean:message key="passport"/></b></td>
	     		<td align="center"><b><bean:message key="drivingLicense"/></b></td>
	     		<td align="center"><b><bean:message key="aadhaar"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.primary"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.alter"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.toll"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.fax"/></b></td>
	     		
	     		
		    </tr>
			<logic:iterate name="gridList" id="sublist" indexId="id">
			<tr class="white1">
				<td><input type='checkbox' name='chkCases' value='${sublist.refranceNo}'/></td>
	     		<td>${sublist.dealNO}</td>
	     		<td>${sublist.customerName}
	     		<input type="hidden" id="<%="fname"+id %>" name="fname" value="${sublist.customerName}"></td>
				<td>${sublist.customerType}</td>
				<td>${sublist.cibilDone}
					<input type="hidden" name="lbxCibilId" id="lbxCibilId<%=id%>" value="${sublist.lbxCibilId}" />
					<input type="hidden"  name="cibilDone" id="cibilDone<%=id%>" value="${sublist.cibilDone}"/>
				</td>
				
				<td>${sublist.cibilScore}</td>
				<td>${sublist.crifHighMarkScore}</td>
				<td align="center">
					<a href='#' onClick="cibilDownload('<%=id%>');"><img style=\"cursor:pointer\" src="<%=request.getContextPath()%>/images/download.png" width="18" height="18" ></a>		
				</td>
				
	    		<td>${sublist.customerGender}
	    		<input type="hidden" id="<%="gender"+id %>" name="gender" value="${sublist.customerGender}"></td>	
	    		<td>${sublist.customerRole}</td>
	    		<td>${sublist.memberName}</td>	
	    		<td>${sublist.memberId}</td>	
	    		<td>${sublist.inquiryPurpus}</td>	
	    		<td>${sublist.refranceNo}</td>	
	    		<td>${sublist.customerDob}
	    		<input type="hidden" id="<%="dob"+id %>" name="dob" value="${sublist.customerDob}"></td>
	    		<td>${sublist.address1}</td>	
	    		<td>${sublist.address2}</td>
	    		<td>${sublist.address3}</td>
	    		<td>${sublist.addressType}</td>
	    		<td>${sublist.addressDetail}</td>
	    		<td>${sublist.distict}</td>
	    		<td>${sublist.state}
	    		<input type="hidden" id="<%="state"+id %>" name="state" value="${sublist.state}"></td>	
	    		<td>${sublist.pincode}
	    		<input type="hidden" id="<%="pincode"+id %>" name="pincode" value="${sublist.pincode}"></td>	
	    		<td>${sublist.country}</td>	
	    		<td>${sublist.panNo}
	    		<input type="hidden" id="<%="pan"+id %>" name="pan" value="${sublist.panNo}"></td>	
	    		<td>${sublist.regNo}</td>	
	    		<td>${sublist.voterId}
	    		<input type="hidden" id="<%="voterid"+id %>" name="voterid" value="${sublist.voterId}"></td>
	    		<td>${sublist.passport}
	    		<input type="hidden" id="<%="passport"+id %>" name="passport" value="${sublist.passport}"></td>	
	    		<td>${sublist.drivingLicense}
	    		<input type="hidden" id="<%="drivinglicence"+id %>" name="drivinglicence" value="${sublist.drivingLicense}"></td>	
	    		<td>${sublist.uid}
	    		<input type="hidden" id="<%="uid"+id %>" name="uid" value="${sublist.uid}"></td>	
	    		<td>${sublist.primaryPhone}</td>	
	    		<td>${sublist.alterPhone}</td>	
	    		<td>${sublist.tollPhone}</td>	
	    		<td>${sublist.faxNo}</td>	    	
	     	</tr>		
   			</logic:iterate>
			
   		 	<td colspan="31" class="white1"><button type="button" name="save&forward" title="Alt+S" accesskey="S" id="button" class="topformbutton3" onclick="return forwordCibilIni();"><bean:message key="button.save&forward" /></button></td>
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
				<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(192031,'cibilVerificationDispatchAction','LinkId','','','','','','reportName');" value=" " styleClass="lovbutton"></html:button>
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
</logic:notEmpty>
</logic:present>

</html:form>		
	
	<logic:present name="success">
	<script type="text/javascript">	
		alert('Successfully Response Recived From Bureau.');
	</script>
	</logic:present>
	<logic:present name="error">
	<script type="text/javascript">	
		alert('Some Error Occured During Communication.');
	</script>
	</logic:present>
	<logic:present name="msg">
	<script type="text/javascript">	
		alert('Bureau can not be initiate. Data Successfully Forward.');
	</script>
	</logic:present>
	<logic:present name="reportError">
	<script type="text/javascript">	
		alert('No Such File Exist.');
	</script>
	</logic:present>	
	</body>	
	</html:html>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@page import="java.util.Date"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();

%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
	
		<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
        int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
        request.setAttribute("no",no); %>
        
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	   		 <logic:present name="css">
	     <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
         </logic:present>
			
         <logic:notPresent name="css">
	     <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
         </logic:notPresent>	
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
    	<script type="text/javascript" src="<%=path%>/js/sczScript/poolIDMakerAuthor.js"></script>
   
   
		<script type="text/javascript">
		
		 function btn_reset() {
		 	//parent.location.reload(); 
		 }
		 
	        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
			
			function defaultFocus()
			{
				document.getElementById('sourcingForm').loanNoButton.focus();
			}
		</script>
	
	</head>
	<body onclick="parent_disable();init_fields();" onload="enableAnchor();document.getElementById('sourcingForm').loanNoButton.focus();" onunload="closeAllLovCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/poolIdMakerProcessAction" method="post" styleId="sourcingForm" enctype="multipart/form-data" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	<input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate %>"/>
    <input type="hidden" id=investmentRatio value='${investmentRatio}'/>		<!-- seema -->
    
  
    <fieldset>
    <logic:present name="poolIdMaker"> 
	<legend><bean:message key="lbl.poolIdMaker"/></legend>   
	</logic:present>	    
	
	<logic:present name="poolIdAuthor"> 
	<legend><bean:message key="lbl.poolIdAuthor"/></legend>   	
	</logic:present>   
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
<!--	 ........      For Pool ID Maker Saved Data   .............. -->
		
		 <logic:present name="poolIdSavedDataList"> 
		
		<html:hidden styleClass="text" property="hid" styleId="hid" value="${poolIdSavedDataList[0].poolID}"/> 	
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 <tr>			 	
		   <td><bean:message key="lbl.poolID"/></td>
		   <td>
	        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${poolNo}" readonly="true" tabindex="-1"/>   
           </td>
           <td><bean:message key="lbl.PoolName"/><font color="red">*</font></td>
	       <td>
	        <html:text styleClass="text" property="poolName" styleId="poolName" value="${poolIdSavedDataList[0].poolName}" maxlength="50" />
	       </td>
	 
        </tr>
        
	    <tr>   
            <td><bean:message key="lbl.PoolCreationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="poolCreationDate" styleId="poolCreationDate1" readonly="true" value="${poolIdSavedDataList[0].poolCreationDate}" maxlength="20" />
	       </label></td>	
         
	       <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="cutOffDate" styleId="cutOffDate" value="${poolIdSavedDataList[0].cutOffDate}" maxlength="20" onchange="checkDate('cutOffDate')"/>
	       </label></td>	
		
		</tr>
		<tr>
		 <td><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      <td><label>
	      <html:select property="poolType" styleId="poolType" styleClass="text" value="${poolIdSavedDataList[0].poolType}" >
	           <html:option value="">--Select--</html:option>
		        <html:option value="S">Securitized</html:option>
		        <html:option value="R">Re-finance</html:option>
		   </html:select> 
	        </label>
	      </td>
     
	      <td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	    
	    <td>
			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="${poolIdSavedDataList[0].lbxinstituteID}" readonly="false"
			 onchange="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon','','','Y');closeLovCallonLov1();" tabindex="-1"/>   
		     <html:hidden property="lbxinstituteID" value="${poolIdSavedDataList[0].lbxinstituteID}" styleId="lbxinstituteID" />
		     <input type="hidden" name="hcommon" id="hcommon" />
			 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
        </td>
	    
<!--		   <td>-->
<!--			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="${poolIdSavedDataList[0].instituteID}" tabindex="-1"/>   -->
<!--		     <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" value="${poolIdSavedDataList[0].lbxinstituteID}"/>-->
<!--		     <input type="hidden" name="hcommon" id="hcommon" />-->
<!--			 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   -->
<!--           </td>-->
        <td></td>
        <td></td>
	       </tr>
	       
	       <tr>
			<td><bean:message key="lbl.assignType"/><font color="red">*</font></td>
			<td>
				<html:select property="assignType" value="${poolIdSavedDataList[0].assignType}" styleId="assignType" styleClass="text">
					<html:option value="">--Select--</html:option>
					<html:option value="Direct Assignment">Direct Assignment</html:option>
					<html:option value="PTC Assignment">PTC Assignment</html:option>
				</html:select>
			</td>
				<td><bean:message key="lbl.assignDate"/>&nbsp;<bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		    <td>
		    	<html:text property="assignDate" value="${poolIdSavedDataList[0].assignDate}" styleClass="text" styleId="assignDate" onchange="checkDate('assignDate')" />
		    </td>
		</tr>
	       <tr>
									<td><bean:message key="lbl.dealAmount"/><font color="red">*</font></td>
									<td>
										
										<html:text styleClass="text" value="${poolIdSavedDataList[0].dealAmount}" styleId="dealAmount" property="dealAmount" onkeypress="return numbersonly(event,id,15)"   maxlength="15" onkeyup="checkNumber(this.value, event, 15,id);" onfocus="keyUpNumber(this.value, event, 15,id);" ></html:text>
									</td>
									<td><bean:message key="lbl.interestRate"/></td>
									<td>
										<html:text styleClass="text" value="${poolIdSavedDataList[0].interestRate}" styleId="interestRate" property="interestRate" maxlength="3" onkeypress="return numbersonly(event,id,3)"   onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" onchange="checkInterestRate()" ></html:text>
									</td>
		</tr>
		
		<tr>
			<td><bean:message key="lbl.creditEnhancement"/></td>
			<logic:present name="credit">
			<logic:equal name="credit" value="Y">
				<td><input type="checkbox" name="creditEnhancement" checked="checked" /></td>
			</logic:equal>
			</logic:present>
			<logic:notPresent name="credit">
				<td><input type="checkbox" name="creditEnhancement" /></td>
			</logic:notPresent>
			<td><bean:message key="lbl.multipleInvestor"/></td>
			<logic:present name="multi">
			<logic:equal name="multi" value="Y">
				<td><input type="checkbox" name="multipleInvestor" checked="checked"  /></td>
			</logic:equal>
			</logic:present>
			<logic:notPresent name="multi">
				<td><input type="checkbox" name="multipleInvestor" /></td>
			</logic:notPresent>
		</tr>
		<!-- added by seema -->
		 <tr>
		 
			<td><bean:message key="lbl.assignmentPercentage"/></td>
			<td>
				<html:text styleClass="text" value="${poolIdSavedDataList[0].assignmentPercentage}" styleId="assignmentPercentage" property="assignmentPercentage"  maxlength="15"  tabindex="-1"  ></html:text>
			</td>
		</tr>
		
	<!-- end -->	
			
	    </table>
	<table>
	<tr>
			
	        <td align="left">
	         <button type="button" name="button" id="save"  class="topformbutton3" title="Alt+S" accesskey="S" onclick="updatePoolIdData();"><bean:message key="button.save" /></button> 
			 <logic:present name="poolBut">
			 	<button type="button" name="button" id="repaymentSchdUpload" disabled  title="Alt+P" accesskey="P" onclick="openNewWindow('poolUpload')" ><bean:message key="button.poolUpload" /></button>
			 </logic:present>
			 <logic:notPresent name="poolBut">
			 	<button type="button" name="button" id="repaymentSchdUpload" class="topformbutton3" title="Alt+P" accesskey="P" onclick="openNewWindow('poolUpload')" ><bean:message key="button.poolUpload" /></button>
			 </logic:notPresent>
			 
			 <logic:present name="rePayBut">
			 	<button type="button" name="button" id="repaymentSchdUpload" disabled title="Alt+R" accesskey="R" onclick="openNewWindow('repamentSchdUpload')"><bean:message key="button.repamentSchdUpload" /></button>
			 </logic:present>
			 <logic:notPresent name="rePayBut">
			 	<button type="button" name="button" id="repaymentSchdUpload" class="topformbutton3" title="Alt+R" accesskey="R" onclick="openNewWindow('repamentSchdUpload')"><bean:message key="button.repamentSchdUpload" /></button>
			 </logic:notPresent>
			 
			 <logic:present name="bankBut">
			 	<button type="button" name="button" id="bankUpload" title="Alt+B" disabled accesskey="B" onclick="openNewWindow('bankUpload')"><bean:message key="button.bankUpload" /></button>
			 </logic:present>
			 <logic:notPresent name="bankBut">
			 	<button type="button" name="button" id="bankUpload" class="topformbutton3" title="Alt+B" accesskey="B" onclick="openNewWindow('bankUpload')"><bean:message key="button.bankUpload" /></button>
			 </logic:notPresent>
           
           <button type="button" name="button" id="format" title="Alt+F" class="topformbutton3"  onclick="downloadBankUploadFormat()" accesskey="F" ><bean:message key="bank.format" /></button>
	       <button type="button" name="button" id="frwrd" class="topformbutton2" title="Alt+F" accesskey="F" onclick="return forwardPoolIdMaker();"><bean:message key="button.forward" /></button>
	       <button type="button" name="button" id="delt" title="Alt+F" class="topformbutton3" accesskey="F" onclick="deletePoolID()"><bean:message key="button.delete" /></button>
	      </td>
	      <td></td>
	      <td></td>
	      <td></td>
		</tr>
 
	</table>
	</logic:present>
        	
        	
    <!-- ---------For Pool ID Author Saved Data---------------------------------- -->    	
        	
        	 <logic:present name="authorPoolIdSavedList"> 	
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 <tr>			 	
		   <td><bean:message key="lbl.poolID"/></td>
		   <td>
	        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${sessionScope.authorPoolIdSavedList[0].poolID}" readonly="true" tabindex="-1"/>   
           </td>
           <td><bean:message key="lbl.PoolName"/><font color="red">*</font></td>
	       <td>
	        <html:text styleClass="text" property="poolName" styleId="poolName" value="${sessionScope.authorPoolIdSavedList[0].poolName}" maxlength="50"  readonly="true"/>
	       </td>
	 
        </tr>
        
	    <tr>   
           <td><bean:message key="lbl.PoolCreationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="poolCreationDate" styleId="poolCreationDate" value="${sessionScope.authorPoolIdSavedList[0].poolCreationDate}" maxlength="20" disabled="true" readonly="true"/>
	       </label></td>	
         
     
	       <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="cutOffDate" styleId="cutOffDate" value="${sessionScope.authorPoolIdSavedList[0].cutOffDate}" maxlength="20" disabled="true" readonly="true" onchange="checkDate('cutOffDate')"/>
	       </label></td>	
		
		</tr>
		<tr>
		  <td><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      <td><label>
	      <html:select property="poolType" styleId="poolType" styleClass="text" value="${sessionScope.authorPoolIdSavedList[0].poolType}" disabled="true">
	           <html:option value="">--Select--</html:option>
		        <html:option value="S">Securitized</html:option>
		        <html:option value="R">Re-finance</html:option>
		   </html:select> 
	        </label>
	      </td>
	      <td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	    
		   <td>
			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="${sessionScope.authorPoolIdSavedList[0].instituteID}" readonly="true" tabindex="-1"/>   
		     <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
		     <input type="hidden" name="hcommon" id="hcommon" />
			<!-- <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button> -->  
           </td>
        <td></td>
        <td></td>
	       </tr>
	       
	       	<tr>
				<td><bean:message key="lbl.assignType"/><font color="red">*</font></td>
				<td>
					<html:select property="assignType" disabled="true" value="${sessionScope.authorPoolIdSavedList[0].assignType}" styleId="assignType" styleClass="text">
						<html:option value="">--Select--</html:option>
						<html:option value="Direct Assignment">Direct Assignment</html:option>
						<html:option value="PTC Assignment">PTC Assignment</html:option>
					</html:select>
				</td>
				<td><bean:message key="lbl.assignDate"/>&nbsp;<bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
			    <td>
			    	<html:text property="assignDate" value="${sessionScope.authorPoolIdSavedList[0].assignDate}" styleClass="text" styleId="assignDate1" disabled="true" readonly="true"  onchange="checkDate('assignDate')" />
			    </td>
			</tr>
	       	<tr>
				<td><bean:message key="lbl.dealAmount"/><font color="red">*</font></td>
				<td>
					<html:text styleClass="text" value="${sessionScope.authorPoolIdSavedList[0].dealAmount}" readonly="true" styleId="dealAmount" property="dealAmount" maxlength="15" onkeypress="return numbersonly(event,id,15)"   onkeyup="checkNumber(this.value, event, 15,id);" onfocus="keyUpNumber(this.value, event, 15,id);"></html:text>
				</td>
				<td><bean:message key="lbl.interestRate"/><font color="red">*</font></td>
				<td>
					<html:text styleClass="text" value="${sessionScope.authorPoolIdSavedList[0].interestRate}" readonly="true" styleId="interestRate" property="interestRate" maxlength="3" onkeypress="return numbersonly(event,id,3)"   onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" onchange="checkInterestRate()" ></html:text>
				</td>
			</tr>
		
			
			<tr>
				<td><bean:message key="lbl.creditEnhancement"/></td>
				<logic:present name="credit">
				<logic:equal name="credit" value="Y">
					<td><input type="checkbox" name="creditEnhancement" checked="checked" value="Y" disabled="true" /></td>
				</logic:equal>
				</logic:present>
				<logic:notPresent name="credit">
					<td><input type="checkbox" name="creditEnhancement" disabled="true" value="N" /></td>
				</logic:notPresent>
				<td><bean:message key="lbl.multipleInvestor"/></td>
				<logic:present name="multi">
				<logic:equal name="multi" value="Y">
					<td><input type="checkbox" name="multipleInvestor" checked="checked" value="Y" disabled="true" /></td>
				</logic:equal>
				</logic:present>
				<logic:notPresent name="multi">
					<td><input type="checkbox" name="multipleInvestor" value="N" disabled="true" /></td>
				</logic:notPresent>
			</tr>
			<!-- added by seema -->
		 <tr>
			<td><bean:message key="lbl.assignmentPercentage"/></td>
			<td>
				<html:text styleClass="text" value="${sessionScope.authorPoolIdSavedList[0].assignmentPercentage}" styleId="assignmentPercentage" property="assignmentPercentage"  maxlength="15" readonly="true" tabindex="-1"  ></html:text>
			</td>
		</tr>
		
	<!-- end -->
	 </table>
	</logic:present>	
        	
        	   <!-- ---------For Pool ID Maker  For New Screen      -------------------------------------------- -->    
        	   
        	 <logic:notPresent name="poolIdSavedDataList"> 
        	 <logic:notPresent name="authorPoolIdSavedList"> 
        	 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        	 <tr>
		<td><bean:message key="lbl.poolID"/></td>
		<td> 
		 <input type="text" name="poolID" id="poolID" value="${sessionScope.poolNo}" readonly="readonly"/>
	       <!--  <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="50"  />  -->
	       <%//System.out.println(request.getAttribute("poolNo")); %>
	       </td>
		
           <td><bean:message key="lbl.PoolName"/><font color="red">*</font></td>
	       <td>
	        <html:text styleClass="text" property="poolName" styleId="poolName" maxlength="50" value="" />
	       </td>
	 
        </tr>
        
	    <tr>   
          <td><bean:message key="lbl.PoolCreationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label> 
	       <html:text styleClass="text" property="poolCreationDate" value="${sessionScope.creationDate}" readonly="true" styleId="poolCreationDate1" maxlength="20" />
	       </label></td>	
         
             
	       <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td>
		       <abbr title="Should be Last Date Of Month"><label>
		       		<html:text styleClass="text" property="cutOffDate" value="${sessionScope.cutoffDate}" styleId="cutOffDate"  maxlength="20" onchange="checkDate('cutOffDate')" />
		       </label></abbr>
	       </td>	
		
		</tr>
		<tr>
		   <td><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      <td><label>
	    
		   <html:select styleId="poolType" styleClass="text" property="poolType" >
			<option value="S">Securitized</option>
			<option value="R">Re-finance</option>
		   </html:select>
	
	        </label>
	      </td>
	      <td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	    
		   <td>
			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="" readonly="false"
			 onchange="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon','','','Y');closeLovCallonLov1();" tabindex="-1"/>   
		     <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
		     <input type="hidden" name="hcommon" id="hcommon" />
			 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
           </td>
        <td></td>
        <td></td>
			
    </tr>
	    <tr>
			<td><bean:message key="lbl.assignType"/><font color="red">*</font></td>
			<td>
				<html:select property="assignType" styleId="assignType" styleClass="text">
					<html:option value="">--Select--</html:option>
					<html:option value="Direct Assignment">Direct Assignment</html:option>
					<html:option value="PTC Assignment">PTC Assignment</html:option>
				</html:select>
			</td>
			<td><bean:message key="lbl.assignDate"/>&nbsp;<bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		    <td>
		    	<html:text property="assignDate" styleClass="text" styleId="assignDate" value=""  onchange="checkDate('assignDate')" />
		    </td>
		</tr>
		<tr>
			<td><bean:message key="lbl.dealAmount"/><font color="red">*</font></td>
			<td>
				<html:text styleClass="text" styleId="dealAmount" property="dealAmount" maxlength="15" onkeypress="return numbersonly(event,id,15)"   onkeyup="checkNumber(this.value, event, 15,id);" onfocus="keyUpNumber(this.value, event, 15,id);"></html:text>
			</td>
			<td><bean:message key="lbl.interestRate"/><font color="red">*</font></td>
			<td>
				<html:text styleClass="text" styleId="interestRate" property="interestRate" onkeypress="return numbersonly(event,id,18)"   maxlength="3" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" onchange="checkInterestRate()" ></html:text>
			</td>
		</tr>
	
		<tr>
			<td><bean:message key="lbl.creditEnhancement"/></td>
			<td><html:checkbox property="creditEnhancement" value="Y"  /></td>
			<td><bean:message key="lbl.multipleInvestor"/></td>
			<td><html:checkbox property="multipleInvestor" value="Y"  /></td>
		</tr>
		<!-- added by seema -->
		 <tr>
			<td><bean:message key="lbl.assignmentPercentage"/></td>
			<td>
				<html:text styleClass="text" value="${poolIdSavedDataList[0].investmentRatio}" styleId="assignmentPercentage" property="assignmentPercentage"  maxlength="15" tabindex="-1"  ></html:text>
			</td>
		</tr>
		
	<!-- end -->
	</table>
	<table>
	<!--			Rudra  		-->
	<tr>
		<td align="left">
			<button type="button" name="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="savePoolIdData();"><bean:message key="button.save" /></button> 
			<button type="button" name="button" id="repaymentSchdUpload" disabled title="Alt+P" accesskey="P" onclick="openNewWindow('poolUpload')" ><bean:message key="button.poolUpload" /></button>
            <button type="button" name="button" id="repaymentSchdUpload" disabled title="Alt+R" accesskey="R" onclick="openNewWindow('repamentSchdUpload')"><bean:message key="button.repamentSchdUpload" /></button>
           	<button type="button" name="button" id="bankUpload" disabled title="Alt+B" accesskey="B" onclick="openNewWindow('bankUpload')"><bean:message key="button.bankUpload" /></button>
	       	 
	       	 <button type="button" name="button" id="format" disabled title="Alt+F" accesskey="F" onclick="downloadBankUploadFormat()"><bean:message key="bank.format" /></button>
	       	<button type="button" name="button" id="frwrd" disabled title="Alt+F" accesskey="F" onclick="forwardOnNew()"><bean:message key="button.forward" /></button>
	       	<button type="button" name="button" id="delt" disabled title="Alt+F" accesskey="F" onclick="deletePoolID()"><bean:message key="button.delete" /></button>
	   	</td>
	</tr>
 
	</table>
		</logic:notPresent>
		</logic:notPresent>
		
	</td>
    </tr>
    </table>	 
	</fieldset>	
	
</html:form>
	</div>
	
	<logic:present name="sms">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.sms" />');
 </script>
  </logic:present> 
  <logic:present name="deleteOk">
 <script type="text/javascript">	
 if(('<%=request.getAttribute("deleteOk")%>') == 'Y'){
	alert('<bean:message key="lbl.deleteOk" />');
	parent.location="<%=request.getContextPath()%>/scz_poolIdMakerBehindAction.do";
}	
 </script>
  </logic:present> 
  
  	<logic:present name="smsk">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smsk" />');
 </script>
  </logic:present> 
    	<logic:present name="smks">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smks" />');
 </script>
  </logic:present> 
  
  <logic:present name="smsno">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smsno" />');
 </script>
  </logic:present>
  
   <logic:present name="maxCount">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.maxCount" />');
 </script>
  </logic:present>
   <logic:present name="inserted">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.processingCompleted" />');
 </script>
  </logic:present>

  <logic:present name="noinserted">
	 <script type="text/javascript">	
			alert('<bean:message key="lbl.rejectProcessingError" />');	
	 </script>
  </logic:present>
  
  <logic:present name="msg1">
	<script type="text/javascript">
		alert('${msg1}');
	</script>
  </logic:present>
    
   <logic:present name="msg">
   	<script type="text/javascript">
   		alert("Data Saved Successfully");
   		/* parent.frames['menu'].location.reload(); */
   		window.parent.location="<%=request.getContextPath()%>/poolIdMakerProcessAction.do?method=getPoolSearchedData&poolID=${poolNo}";
   		<%--document.getElementById("sourcingForm").submit();
   		 window.parent.location="<%=request.getContextPath()%>/poolIdBehindMaker.do"; --%>
   	</script>
   </logic:present>
   
      <logic:present name="APE">
   	<script type="text/javascript">
   		alert("Pool Name Already Exists");
   		window.parent.location="<%=request.getContextPath()%>/poolIdBehindMaker.do";
   	</script>
   </logic:present>
   
   
   
   
 <logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'Y')
		{
			alert("<bean:message key="lbl.forwardSuccess" />");
			window.parent.location="<%=request.getContextPath()%>/poolIdBehindMaker.do";
	      /*   document.getElementById("sourcingForm").submit(); */
		}
	else if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("Data not Found ");
	
	}
	
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdBehindMaker.do";
	    document.getElementById("sourcingForm").submit();
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked1')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdBehindAuthor.do";
	    document.getElementById("sourcingForm").submit();
	}
	</script>
	</logic:present>
	
	    


</div>
</body>
</html:html>
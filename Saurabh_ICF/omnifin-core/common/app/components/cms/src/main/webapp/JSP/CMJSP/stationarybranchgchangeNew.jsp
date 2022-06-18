<!--Author Name :- Mradul Agarwal-->
<!--Date of Creation : 19_Jan_2013-->
<!--Purpose :-  Stationary Addition Screen-->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="com.logger.LoggerMsg"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

<html>                                                                                     
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>
		<!-- css for Datepicker -->
<link type="text/css" href="development-bundle/demos/demos.css" rel="stylesheet" />
<link type="text/css"
	href="development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<!-- jQuery for Datepicker -->
<script type="text/javascript" src="development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/stationaryMasterScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
</head>

<body onload="enableAnchor();">
	<div align="center" class="opacity" style="display:none" id="processingImage"></div>
		<html:form action="/StationaryIssuanceDispatchActionAtCM" styleId="StationaryMasterForm1" >
		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"  />
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<logic:present name="image">
				<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
				<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
				
	<input type="hidden" id="additionStationaryDate" name="additionStationaryDate" value='${edit[0].issuedate}'/>
				
	<fieldset><legend><bean:message key="lbl.hoAllocationCreation"/></legend>
		<table width="100%" border="0" cellspacing="1" cellpadding="0">
		<%--<logic:notPresent name="edit"> --%>
			<tr>
				
				<td><bean:message key="lbl.BookNo"/><span><font color="red">*</font></span></td>
							
				<td>
					<html:text property="bookIssue" styleClass="text" styleId="bookIssue" maxlength="100" readonly="true" value="${edit[0].bookIssue}"/>
					<html:hidden property="lbxBookNo" styleId="lbxBookNo"  />
					<%--<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="openLOVCommon(2023,'StationaryMasterForm','bookIssue','stationaryType','', 'desc','','','lbxBookNo')" value=" " styleClass="lovbutton"  ></html:button>--%>
			</td>
			<td><bean:message key="lbl.AllocatedBranch" /><span><font color="red">*</font></span></td>
				<td>
				  <html:text property="allocatedBranch" styleClass="text" styleId="allocatedBranch" maxlength="100" readonly="true" value="${edit[0].allocatedBranch}"/>
				</td>
			</tr>
			
			
				<tr></tr>
		  	  <tr>
				 	<td><bean:message key="lbl.hoRemarks"/></td>
				 	<td><html:text property="hoRemarks" styleClass="text" styleId="hoRemarks" maxlength="100" value="${edit[0].hoRemarks}" /></td>
		      
		       
		
		        <td><bean:message key="lbl.NewBranchChange" /><span><font color="red">*</font></span></td>
					
				<td>
			


			        <html:text property="branchid" styleClass="text" styleId="branchid" maxlength="100" readonly="true" value=""/>
					<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="" />
					<html:button property="NewBranchChangeButton" styleId="NewBranchChangeButton" tabindex="1" onclick="openLOVCommon(2023,'StationaryMasterForm','branchid','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"  ></html:button>
				</td>
				
				
				
				
				
				
				
				
				
				
				
				
		       </tr>
		       <tr>
		       
		        	
			
		       
		                                                                                                                                                                                                                                                            	
			 		</tr>
				
				 
						
				 <tr><td>
				 <button type="button" name="Save" id="Save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveStationarBranchChange();">
				 <bean:message key="button.save" /></button>	
				
				 </td>
                </tr>
      
		</table>
				  
	    </fieldset>
	    </html:form>
	    <logic:present name="msg">
<script type="text/javascript">

   if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('Data Saved Succesfully');
		document.getElementById("StationaryMasterForm1").action="stationaryBranchgChangeBehindAtCM.do";
	    document.getElementById("StationaryMasterForm1").submit();
	}
</script>
</logic:present>
	    </body>
	    </html>	    
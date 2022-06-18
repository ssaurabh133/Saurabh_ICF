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
<script type="text/javascript">
			$(function() {
				$("#additionDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
						<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
    		</logic:notPresent>
					buttonImageOnly: true,
					dateFormat:"<bean:message key="lbl.dateFormat"/>",
					defaultDate:'${userobject.businessdate }'
			})
			});
			
</script>
<body onload="enableAnchor();">
	<div align="center" class="opacity" style="display:none" id="processingImage"></div>
		<html:form action="/StationaryIssuanceDispatchActionAtCM" styleId="StationaryMasterForm" >
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
				<td><bean:message key="lbl.InsType" /><span><font color="red">*</font></span></td>
				<td>
				  <html:text property="desc" styleClass="text" styleId="desc" maxlength="100" readonly="true" value="${edit[0].desc}"/>
				</td>
				<td><bean:message key="lbl.BookNo"/><span><font color="red">*</font></span></td>
							
				<td>
					<html:text property="bookIssue" styleClass="text" styleId="bookIssue" maxlength="100" readonly="true" value="${edit[0].bookIssue}"/>
					<html:hidden property="lbxBookNo" styleId="lbxBookNo"  />
					<%--<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="openLOVCommon(2023,'StationaryMasterForm','bookIssue','stationaryType','', 'desc','','','lbxBookNo')" value=" " styleClass="lovbutton"  ></html:button>--%>
			</td></tr>
				<tr>
				  <td><bean:message key="lbl.InsFrom"/><span><font color="red">*</font></span></td>
				  <td>
					<html:text property="instruFrom1" styleClass="text" styleId="instruFrom1" readonly="true" value="${edit[0].instruFrom1}"/>
				  </td>
				  <td><bean:message key="lbl.InsTo" /><span><font color="red">*</font></span></td>
				  <td>
				    <html:text property="instruTo1" styleClass="text" styleId="instruTo1"  value="${edit[0].instruTo1}" readonly="true" />
				  </td>
							
				</tr>
				<tr>
				<td><bean:message key="lbl.hoIssubranch"/><span><font color="red">*</font></span></td>
				<td>

					<html:text property="branchid" styleClass="text" styleId="branchid" maxlength="100" readonly="true" value="${edit[0].branchid}"/>
					<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="${edit[0].lbxBranchId}" />
					<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="openLOVCommon(2023,'StationaryMasterForm','branchid','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"  ></html:button>
				</td>
				  <td><bean:message key="lbl.hoIssudate" /><span><font color="red">*</font></span></td>
				  <logic:present name="edit">
				  		<logic:iterate name="edit" id="subEdit" length="1">
				  		   <logic:equal name="subEdit" property="additionDate" value="">
				  		   	  <td><html:text property="additionDate" styleClass="text" styleId="additionDate"  value="${userobject.businessdate}" maxlength="10" /></td>
				  		   </logic:equal>
				  		   <logic:notEqual name="subEdit" property="additionDate" value="">
				  		   	  <td><html:text property="additionDate" styleClass="text" styleId="additionDate"  value="${edit[0].additionDate}" maxlength="10" /></td>
				  		   </logic:notEqual>
				  		</logic:iterate>
				  </logic:present>
				  
									
				</tr>
				<tr></tr>
		  	  <tr>
				 	<td><bean:message key="lbl.hoRemarks"/></td>
				 	<td><html:text property="hoRemarks" styleClass="text" styleId="hoRemarks" maxlength="100" value="${edit[0].hoRemarks}" /></td>
		        	<td><bean:message key="lbl.Status"/></td>
		       
		        	<td><input type="checkbox" id="status1" name="status1" checked="checked"/></td>	
			 </tr>
			 		 <tr>
			 		 
			 		 
                     <td><bean:message key="lbl.stationaryBookType" /></td>
                     <td>
                          <html:select styleId="stationaryBookType" property="stationaryBookType" styleClass="text" >
                           <html:option value=""><bean:message key="lbl.select" /></html:option>
							<logic:present name="bookTypeList" >
							    <html:optionsCollection name="bookTypeList" label="name" value="id" />
							</logic:present>   
						  </html:select>
						
						</td>
			
			 	<td><bean:message key="lbl.AllBranch"/></td>
			 	
			 		<logic:present name="checkBranch">
			           	   <logic:equal name="checkBranch" value="Y">
						     <td><input type="checkbox" id="allBranch" name="allBranch" checked="checked"/></td>
						   </logic:equal>
						   <logic:equal name="checkBranch" value="N">
						     <td><input type="checkbox" id="allBranch" name="allBranch" /></td>
						   </logic:equal>
					</logic:present>
				
			 	
			 </tr>				
						
				 <tr><td>
				 <button type="button" name="Save" id="Save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveHOAllocation();">
				 <bean:message key="button.save" /></button>	
				
				 </td>
                </tr>
        <%--
        	</logic:notPresent>	
        	 
        	<logic:present name="edit">
        	
        	
			<tr>
				<td><bean:message key="lbl.InsType" /></td>
				<td><html:text property="desc" styleId="desc" maxlength="10" readonly="true" value="${edit[0].desc}">						
				   </html:text></td> 
				 
				<td><bean:message key="lbl.BookNo"/><span><font color="red">*</font></span></td>
						
				<td>
					<html:text property="bookIssue" style="width:220px;" styleId="bookIssue" maxlength="100" readonly="true" value="${edit[0].bookIssue}"/>
					
			</td></tr>
			<tr></tr>
				<tr>
				<td><bean:message key="lbl.hoIssubranch"/><span><font color="red">*</font></span></td>
				<td>
					<html:text property="branchid" styleClass="text" styleId="branchid" maxlength="100" readonly="true" value="${edit[0].branchid}"/>
					<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="${edit[0].lbxBranchId}"/>
					<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="openLOVCommon(12,'StationaryMasterForm','branchid','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"  ></html:button>
				</td>					
					
				  <td><bean:message key="lbl.hoIssudate" /><span><font color="red">*</font></span></td>
				  <td><html:text property="additionDate" styleClass="text" styleId="additionDate" maxlength="10" value="${edit[0].additionDate}"/></td>
					
					</tr>
				<tr></tr>
		  	  <tr>
				  	<td><bean:message key="lbl.hoRemarks"/></td>
				 	<td><html:text property="hoRemarks" styleClass="text" styleId="hoRemarks" maxlength="100" value="${edit[0].hoRemarks}"/></td>	
		        	<td><bean:message key="lbl.Status"/></td>
		
					<logic:present name="check">
					<td><input type="checkbox" id="status1" name="status1" checked="checked"/></td>
					</logic:present>
					<logic:notPresent name="check">
					<td><input type="checkbox" id="status1" name="status1" /></td>
					</logic:notPresent>						
			 </tr>
			  
			 <tr><td><input type="hidden" id="bookNoL" name="bookNoL" value="L"/></td> </tr>				
				 <tr><td><input type="button" value="Save" id="Save" class="topformbutton2" accesskey="S" title="Alt+S"  onclick="return saveHOAllocation();" /></td>
        </tr>
        	</logic:present>	
        	--%>
		</table>
				  
	    </fieldset>
	    </html:form>
	    <logic:present name="saveData">
<script type="text/javascript">

   if('<%=request.getAttribute("saveData").toString()%>'=='saveData')
	{
		alert('Data Saved Succesfully');
		document.getElementById("StationaryMasterForm").action="hoAllocationSearchAtCM.do";
	   	document.getElementById("StationaryMasterForm").submit();
	}
</script>
</logic:present>
	    </body>
	    </html>	    
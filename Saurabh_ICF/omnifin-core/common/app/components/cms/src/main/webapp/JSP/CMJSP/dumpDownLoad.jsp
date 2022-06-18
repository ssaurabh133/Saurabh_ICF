<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 04-Apr-2012-->
<!--Documentation : -->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.*"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/report/report.js"></script>  

<style type="text/css">		
			.white {
			background:repeat-x scroll left bottom #FFFFFF;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
			.white2 {
			background:repeat-x scroll left bottom #CDD6DD;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
</style>
<script type="text/javascript">
		
	$(function() 
	{
		$("#fromDate").datepicker({
			changeMonth: true,
			changeYear: true,
         		yearRange: '1900:+40',
           		showOn: 'both',
				<logic:present name="image">
    	  		buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',
           </logic:present>
    	   <logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',
    		</logic:notPresent>
					buttonImageOnly: true,
					dateFormat:"<bean:message key="lbl.dateFormat"/>",
					defaultDate:'${userobject.businessdate }'
					});
		});
		$(function() {
				$("#toDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+40',
		            showOn: 'both',
						<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',
    		</logic:notPresent>
					buttonImageOnly: true,
					dateFormat:"<bean:message key="lbl.dateFormat"/>",
					defaultDate:'${userobject.businessdate }'
		
					});
			});	
			$(function() {
				$("#asOnDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
						<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',
    		</logic:notPresent>
					buttonImageOnly: true,
					dateFormat:"<bean:message key="lbl.dateFormat"/>",
					defaultDate:'${userobject.businessdate }'
		
					});
			});	

</script> 
</head>	
	
<body onload="enableAnchor();" >
	<html:form action="/dumpDownLoadDispatchAction" styleId="dumpDownLoad" >
	<input type="hidden" name="dumpRestricted" id="dumpRestricted" value="${sessionScope.dumpRestrictedParm }">
		<fieldset><legend><bean:message key="lbl.dumpDownload"/></legend>  
				<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
				<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
				<input type="hidden" id="asOnDatePrs" value="${requestScope.AsOnDate}"/>
				<input type="hidden" id="dateRangeFlag" value="${requestScope.dateRange}"/>
				<input type="hidden" id="query1Flag" value="${requestScope.query1}"/>
				<input type="hidden" id="query1Label" value="${requestScope.query1Label}"/>
				<input type="hidden" id="query2Flag" value="${requestScope.query2}"/>
				<input type="hidden" id="query2Label" value="${requestScope.query2Label}"/>
				<input type="hidden" id="query3Flag" value="${requestScope.query3}"/>
				<input type="hidden" id="query3Label" value="${requestScope.query3Label}"/>
				<input type="hidden" id="query4Flag" value="${requestScope.query4}"/>
				<input type="hidden" id="query4Label" value="${requestScope.query4Label}"/>
				
			
			
				
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
						<td><bean:message key="lbl.dumpDesc"/><span><font color="red">*</font></span></td>
						<td>
							<html:hidden property="lbxRecordID" styleId="lbxRecordID" value="${list[0].lbxRecordID}"/>
							<html:text property="recordDesc" styleClass="text" value="${list[0].recordDesc}" styleId="recordDesc" readonly="false"
							onchange="openLOVCommon(316,'dumpDownLoad','lbxRecordID','','', '','','generatField','recordDesc','label1','label2','Y')"
							/>
    							<html:button property="recordButton" styleId="recordButton" tabindex="1" onclick="openLOVCommon(316,'dumpDownLoad','lbxRecordID','','', '','','generatField','recordDesc','label1','label2')" value=" " styleClass="lovbutton"  ></html:button>
    						<input type="hidden" id="label1" value="${requestScope.ParamKeyOne}"/>
    						<input type="hidden" id="label2" value="${requestScope.ParamKeyTwo}"/>
    						<input type="hidden" id="keyOnePr" value="${requestScope.keyOnePr}"/>
    						<input type="hidden" id="keyTwoPr" value="${requestScope.keyTwoPr}"/>
    						
    					</td>
					</tr>
					<tr><td></td></tr>
					<tr>
					<!--Added by Aditi--> 
					<!-- toni --> 
					<logic:equal name="AsOnDate"  value="N" >
					<logic:equal name="dateRange"  value="Y" >
					    <td><bean:message key="lbl.FromDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="fromDate" styleClass="text" value="" styleId="fromDate" onchange="return checkDate('fromDate');"/></td>
				  		<td><bean:message key="lbl.ToDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="toDate" styleClass="text" styleId="toDate" value="" onchange="return checkDate('toDate');"/></td>
                    </logic:equal>
                    </logic:equal>
                    <logic:equal name="AsOnDate"  value="Y">
                    <logic:equal name="dateRange"  value="N">
					    <td><bean:message key="lbl.AsOnDate"/><span><font color="red">*</font></span></td>
						<td><html:text property="asOnDate" styleClass="text" value="" styleId="asOnDate" onchange="return checkDate('asOnDate');"/></td>
				  		<td></td>
						<td></td>
                    </logic:equal>
                    </logic:equal>
                    <!--End code by Aditi-->  
						
					</tr>
					<!-- Virender code Start -->
					<tr>
					<logic:equal name="AsOnDate"  value="Y">
                    <logic:equal name="dateRange"  value="Y">
					    <td><bean:message key="lbl.dump.format"/><span><font color="red">*</font></span></td>
						<td>
						<html:select property="asOnDate" styleId="format" tabindex='1'>
						<html:option value="CSV">CSV</html:option>
						<html:option value="XML">XML</html:option>
						</html:select>
						</td>

						<td><bean:message key="lbl.dump.custType"/><span><font color="red">*</font></span></td>
						<td>
						<html:select property="toDate" styleId="custType" tabindex='1'>
						<html:option value="I">INDIVIDUAL</html:option>
						<html:option value="C">CORPORATE</html:option>
						</html:select>
						</td>
					 </logic:equal>
                    </logic:equal>
					</tr>
					<!-- Virender code End -->
					<tr>
						<logic:present name="query1Label">
							<td>${requestScope.query1Label}<span><font color="red">*</font></span></td>
							<td><html:text property="query1" styleClass="text" value="" styleId="query1"/></td>
						</logic:present>
						<logic:present name="query2Label">
							<td>${requestScope.query2Label}<span><font color="red">*</font></span></td>
							<td><html:text property="query2" styleClass="text" value="" styleId="query2"/></td>
						</logic:present>
					</tr>
					<tr>
						<logic:present name="query3Label">
							<td>${requestScope.query3Label}<span><font color="red">*</font></span></td>
							<td><html:text property="query3" styleClass="text" value="" styleId="query3"/></td>
						</logic:present>
						<logic:present name="query4Label">
							<td>${requestScope.query4Label}<span><font color="red">*</font></span></td>
							<td><html:text property="query4" styleClass="text" value="" styleId="query4"/></td>
						</logic:present>
					</tr>
					<tr>
						<logic:present name="ParamKeyOne">
							<td>${requestScope.ParamKeyOne}<span><font color="red">*</font></span></td>
							<td><html:text property="paramValueOne" styleClass="text" value="" styleId="paramValueOne"/></td>
						</logic:present>
						<logic:present name="ParamKeyTwo">
							<td>${requestScope.ParamKeyTwo}<span><font color="red">*</font></span></td>
							<td><html:text property="paramValueTwo" styleClass="text" value="" styleId="paramValueTwo"/></td>
						</logic:present>
					</tr>
					<tr>
						<logic:present name="list">
							<td><button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="return genrateDumpDownload();"  ><bean:message key="button.generate" /></button></td>
						</logic:present>
					</tr>
				</table>
		</fieldset>
	</html:form>
	<logic:present name="dowmload">
	<script type="text/javascript">	
		alert("Report is downloaded successfully.");
	</script>
	</logic:present>
	<logic:present name="path">
	<script type="text/javascript">	
		alert("Report is downloaded successfully.Contact Administrator.");
		alert("Path  "+"'${path}'");
	</script>
	</logic:present>
	<logic:present name="msg">
	<script type="text/javascript">	
		alert('${msg}');
	</script>
	</logic:present>
	<logic:present name="error">
	<script type="text/javascript">	
		alert('Some Error occure! Please contact Administrartor.');
	</script>
	</logic:present>
	</body>
</html>
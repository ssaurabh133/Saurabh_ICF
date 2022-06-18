<!--Author Name : Sidharth Trehan-->
<!--Date of Creation : 01-Aug-2012-->
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
</script> 
<style type="text/css">
		textarea {
/*border:1px solid #9BB168;*/
	color:#000;
	font-family:arial,serif;
	font-size:13px;
	padding-left:2px;
	width:600px;
	resize:none;
	height:100px;
}
</style>
</head>	
	
<body onload="enableAnchor();" >
	<html:form action="/adhocreportsDispatchAction" styleId="adhocForm" >
		<fieldset><legend>Ad Hoc Report</legend>  
				<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
				<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
						<td>Query<span><font color="red">*</font></span></td>
						<td colspan="3">
							<textarea rows="10" cols="200" name="query" id="query" styleClass="textarea"  ></textarea>
    					</td>
    					<td>
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					&nbsp;
    					</td>
   					</tr>
					<tr><td></td></tr>
									
					<tr>
						<td><button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="return genrateAdHocReport();"  ><bean:message key="button.generate" /></button></td>
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
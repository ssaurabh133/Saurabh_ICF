<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="com.GL.actionForm.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@page import="com.GL.actionForm.GlVoucherDetailForm;"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />

<link type="text/css" rel="stylesheet" href="./css/theme2/style.css" />
     <logic:present name="css">
	    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
     </logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<%
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	request.setAttribute("no",no);
%>
<!-- css for Datepicker -->

<link type="text/css" href="./development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<!-- jQuery for Datepicker -->
<script type="text/javascript" src="./development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="./development-bundle/ui/jquery.ui.core.js"></script>

<script type="text/javascript" src=" <%=request.getContextPath() %>/js/genericUploadJS/${jsfile}"></script>

<script type="text/javascript" src="./development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript">

  	$(function() {
		$("#makerdate").datepicker({
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
		
		}
  	
	);
  	
	
	$(function() {
		$("#uploaddate").datepicker({
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
	

	
<script type="text/javascript">


 function checkspecial(e)
 {
	 
	 var specialKeys = new Array();
     specialKeys.push(8); //Backspace
     specialKeys.push(9); //Tab
     specialKeys.push(46); //Delete
     specialKeys.push(36); //Home
     specialKeys.push(35); //End
     specialKeys.push(37); //Left
     specialKeys.push(39); //Right
     specialKeys.push(13); //Enter
     
   
    var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
	if( e.charCode == 32 || e.charCode == 13 || (keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) ||  (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != (e.keyCode) )  )   
	{
		return true;
	}
	else
	{	
		alert("Special characters not allowed");
		return false;
	}
	
 }

 function  searchAction()
 {
	
 	var batchid=document.getElementById("batchid").value;
 	var makerid=document.getElementById("makerid").value;
 	var makerdate=document.getElementById("makerdate").value;
 	var uploaddate=document.getElementById("uploaddate").value;
 	var filename=document.getElementById("filename").value;
 	
 	if( batchid == "" && makerid == "" && makerdate == "" && uploaddate == "" && filename == "" )
 	{
 		alert("Please Provide One Of The Information");
 		return false;
 		
 	}
 	else
 	{
 		document.getElementById('actionName').value="Search";
 	 	document.getElementById('uploadAuthor').submit();
 		return true;
 	}
  	
   	
}
 
 
 function authorAction()
 {
	 var batch_id = "";
	 var radios=document.getElementsByName("radio");
	 for(var i = 0; i < radios.length; i++)
	 {
		if(radios[i].checked == true)
		{
			batch_id = radios[i].value;
		}
	 }
	 
	if(batch_id.length > 0)
	{
		    var action = document.getElementById('actions').value;
			if(action == "A")
			{
		    	document.getElementById('actionName').value="approve";
				var contextPath= document.getElementById("contextPath").value;
				document.getElementById("uploadAuthor").action=contextPath+"/genericUploadAuthor.do?batch_id="+batch_id;
 	 			document.getElementById('uploadAuthor').submit();
			
			}
			
			if(action == "B")
			{
				
				var comment = document.getElementById('comment').value;
				if(comment.length > 1)
				{
		    		document.getElementById('actionName').value="sendBack";
					var contextPath= document.getElementById("contextPath").value;
					document.getElementById("uploadAuthor").action=contextPath+"/genericUploadAuthor.do?batch_id="+batch_id+"&comment="+comment;
 	 				document.getElementById('uploadAuthor').submit();
				}
				else
				{
					alert("Author comment required in case of send back");
				}
			}
			
			if(action == "R")
			{
				var comment = document.getElementById('comment').value;
				if(comment.length > 1)
				{
		    		document.getElementById('actionName').value="reject";
					var contextPath= document.getElementById("contextPath").value;
					document.getElementById("uploadAuthor").action=contextPath+"/genericUploadAuthor.do?batch_id="+batch_id+"&comment="+comment;
 	 				document.getElementById('uploadAuthor').submit();
				}
				else
				{
					alert("Author comment required in case of reject");
				}
			}
			
	}
	else
	{
		alert("Please select batch first");
	}
 }


 function downloaddump()
 {										
 	 var batch=document.getElementById("batch").value;
 	 if(batch.length > 0)
 	 {
 	 	var basePath=document.getElementById("contextPath").value;
 	  	document.getElementById("uploadAuthor").action = basePath+'/genericUploadAuthor.do?actionName=downloaddump&batch='+batch;
 	  	document.getElementById("uploadAuthor").submit();
 	 }
 	 else
 	 {
 		alert('Please select batch ID');	 
 	 }
 }
 

</script>

</head>
<body onload="enableAnchor();">
<div id="centercolumn">
<div id="revisedcontainer">
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<html:form action="/genericUploadAuthor" method="post" styleId="uploadAuthor"  >
<html:hidden property="actionName" styleId="actionName" />
<!--  <input type="hidden" name="actionName" id="actionName"/>  -->
<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>" />
<fieldset>
<legend>Upload Author Search</legend>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
     <tr>
      <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
        	<tr>
			
				<td>Batch ID</td>
				<td>
				<!--  <html:text property="batch_id" styleClass="text" style="width:153px;"  styleId="batchid" maxlength="50"  />  -->
				<input type="text" name="batch_id" id="batchid" class="text" style="width:153px;" />
				</td>
				
			
				<td>Maker ID</td>
				<td>
				<html:text property="maker_id" styleClass="text" style="width:153px;"  styleId="makerid" maxlength="50"  />
				</td>
				
				<td>Maker Date</td>
				<td nowrap="nowrap">
				<html:text property="maker_date" styleClass="text" onchange="return checkDate('makerdate');"	 styleId="makerdate" maxlength="50"/>
				</td>
				
			</tr>
			
			<tr>
				<td>Upload Date</td>
				<td nowrap="nowrap">
					<html:text property="upload_date" styleClass="text" onchange="return checkDate('uploaddate');"	 styleId="uploaddate" maxlength="50"/>
				</td>
				 
				<td>File Name</td>
				<td>
				<html:text property="file_name" styleClass="text" style="width:153px;"  styleId="filename" maxlength="50"  />
				</td>
			</tr>
			
				
			
			
        </table>
     </td>
    </tr>
    <tr>
    <td><input name="button" type="button" accesskey="S" title="Alt+S"	 class="topformbutton2" value="Search" onclick="searchAction();" /> </td>
    </tr>
    </table>
</fieldset>
<fieldset>
	<legend>Dump Download</legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
	<tr>
		<td>
		Select batch ID
		</td>
		<td>
			<input type="text" name="bid" id="bid" readonly="true" tabindex="-1"  class="text" />
			<input type="hidden" name="batch" id="batch" class="text" /> 
    		<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(${lovid},'uploadMaker','bid','','','','','','batch');" value=" " styleClass="lovbutton"> </html:button>
		</td>
	</tr>
	<tr>
		<td>
		<button type="button" id="download" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return downloaddump()" >Download</button>
		</td>
	</tr>
	</table>

</fieldset>






    	 <logic:present name="searchList">
         <fieldset>
         <legend>Upload Search List</legend>
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
           <logic:notEmpty name="searchList">
		 <tr>
			<td>
			     <display:table  id="lst"  name="searchList" style="width: 100%" class="dataTable" pagesize="10" size="${size}" partialList="true"  cellspacing="1" requestURI="/uploadAuthor.do?extra=grid" >
			     <display:setProperty name="paging.banner.placement"  value="bottom"/>
			    <!--  <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>  -->
			    <!--  <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>   -->
				 <display:column  title="Select"><input type="radio" name="radio" value="${lst.batch_id}" /> </display:column>
				 <display:column  title="Batch ID" ><a href="genericUploadAuthor.do?actionName=openExcel&batch_id=${lst.batch_id}">${lst.batch_id}</a></display:column>
				 <display:column property="upload_type" title="Upload Type"    />
				 <display:column property="upload_date" title="Upload Date"   />
				 <display:column property="maker_id" title="Maker ID"  />
				 <display:column property="maker_date"  title="Maker Date"     />
				 <display:column property="no_of_records"  title="No Of Records"    />
				 <display:column property="file_name"  title="File Name"    />
				 <display:column property="summary1"  title="Author Comment"    />
				 
				</display:table>
   			</td>
		</tr>
		
		</logic:notEmpty>
		<logic:empty name="searchList">
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr class="white2">
				<td><b>Batch ID</b></td>
				<td><b>Upload Type</b></td>
				<td><b>Upload Date</b></td>
				<td><b>Maker ID</b></td>
				<td><b>Maker Date</b></td>
				<td><b>No Of Records</b></td>
				<td><b>File Name</b></td>
				<td><b>Author Comment</b></td>
				
				
			</tr>
			<tr class="white2">
				<td colspan="10"><bean:message key="lbl.noDataFound"/></td>
			</tr>
		 </table>
		  </td>
		</logic:empty>
		</table>
        </fieldset>
        
        <fieldset>
			<legend>Upload Author Actions</legend>
  			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
     		<tr>
      			<td>
        			<table width="100%" border="0" cellspacing="1" cellpadding="1">
        				<tr>
						<td>Author Actions</td>
						<td>
							<select id="actions" name="actions" value = "">
								<option value="A" >Approve</option>
								<option value="B" >Send Back</option>
								<option value="R" >Reject</option>
							</select>
						</td>
						<td>Author Comment</td>
						<td>
							<textarea id="comment" name="comment" rows="4" cols="500" onkeypress="return checkspecial(event)"></textarea>
						</td>
						</tr>
	   				</table>
     			</td>
    		</tr>
    		<tr>
				<td><input name="button" type="button" accesskey="S" title="Alt+S"	 class="topformbutton2" value="Action" onclick="authorAction()" /> </td>
    		</tr>
    		</table>
		</fieldset>
        
        </logic:present>
<logic:notPresent name="searchList">
 <fieldset>
 <legend>Upload Search List</legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
 <tr>
 		<td class="gridtd">
 			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr class="white2" align="center">
				<td><b>Batch ID</b></td>
				<td><b>Upload Type</b></td>
				<td><b>Upload Date</b></td>
				<td><b>Maker ID</b></td>
				<td><b>Maker Date</b></td>
				<td><b>NO Of Records</b></td>
				<td><b>File Name</b></td>
				<td><b>Summary 1</b></td>
				<td><b>Summary 2</b></td>
				</tr>
			</table>
		</td>
</tr>
</table>
</fieldset>
</logic:notPresent>
	
 <logic:present name="msg" scope="request" >
       <script type="text/javascript">	
			alert("<%=request.getAttribute("msg")%>");
	   </script>
 </logic:present>
				
</html:form>
				
</div>

</div>

</body>
</html>
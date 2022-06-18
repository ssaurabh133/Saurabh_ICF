<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();
%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	 	

<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/cmLoanInitjs.js"></script>
   
		<script type="text/javascript">
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

		<script type="text/javascript"><!--
function validateDocUpload()
{
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		document.getElementById("docFile").focus(); 
			document.getElementById("save").removeAttribute("disabled");
	    return false; 
	}
var fup = document.getElementById('docFile');
var file_Name = fup.value;
var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
 
if(ext == "csv" || ext == "CSV")
{
	return true;
} 
else
{
	alert("Upload xls file only.");
	fup.focus();
		document.getElementById("save").removeAttribute("disabled");
	return false;
}
  
 }
function submitUpload()
{
 

	var sourcepath=document.getElementById("contextPath").value;
    var formatD=document.getElementById("formatD").value;	
	var basePath=document.getElementById("contextPath").value;
	//var poolCreationDate = document.getElementById("poolCreationDate").value;	
	//var cutOffDate = document.getElementById("cutOffDate").value;	
	//var currDate = document.getElementById("businessDate").value;
	//var dt1=getDateObject(poolCreationDate,formatD.substring(2, 3));
   // var dt2=getDateObject(cutOffDate,formatD.substring(2, 3));
   // var dt3=getDateObject(currDate,formatD.substring(2, 3));
       	 
	 
	if(validateDocUpload())
	{
	 document.getElementById("sourcingForm").action=sourcepath+"/collUploadData.do?method=submitUpload";
	 document.getElementById("sourcingForm").submit();
	 return true;
		
	}else{
	return false;
	}
	
}

--></script>
		
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
	<body onload="enableAnchor();" onclick="parent_disable();init_fields();" onunload="closeAllLovCallUnloadBody();">
	
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
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
	
	<html:form action="/collUploadData" method="post" styleId="sourcingForm" enctype="multipart/form-data" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	 <input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	  <input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate %>"/>
    <fieldset>
    
    
	<legend><bean:message key="lbl.UploadData"/></legend>   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
	   <tr>
	   <td>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	   <tr>
		 <td><bean:message key="lbl.fileDescription"/><font color="red">*</font></td>
	      <td>
		  <html:file size="40" property="uploadFileLoan" styleId="docFile" styleClass="text"/> 
		  </td>
		  <td></td>
		<td></td>
    </tr>
	  
		  
	</table>
	
	<tr>
			
	        <td align="left">  

			 <button type="button" name="button" id="save" class="topformbutton3" title="Alt+S" accesskey="S" onclick="this.disabled='true';return submitUpload();"><bean:message key="button.updsave" /></button> 
            

	      </td>
	      <td></td>
	      <td></td>
	      <td></td>
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
	

    
 <logic:present name="alertMsg">
	<script type="text/javascript">
	
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
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
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>
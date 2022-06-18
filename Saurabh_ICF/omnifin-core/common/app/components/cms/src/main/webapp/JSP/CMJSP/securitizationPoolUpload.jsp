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

<%--     <script type="text/javascript" src="<%=path%>/js/omniFinUploadScript/poolIDMakerAuthor.js"></script> --%>
   
   
		<script type="text/javascript">
		
				
			function validateDocUpload()
			{
				if(document.getElementById('docFile').value=="")
				{
					alert("Choose file to be uploaded.");
					document.getElementById("docFile").focus(); 
					DisButClass.prototype.EnbButMethod();
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
					alert("Upload csv file only.");
					fup.focus();
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			
	function PoolIdUploadKtr()
	{
		DisButClass.prototype.DisButMethod();
		var sourcepath=document.getElementById("contextPath").value;
		var formatD=document.getElementById("formatD").value;	
		var poolCreationDate = document.getElementById("poolCreationDate").value;	
		var cutOffDate = document.getElementById("cutOffDate").value;
		var businessdate = document.getElementById("businessdate").value;	
		var currDate = document.getElementById("businessDate").value;
		var poolName=document.getElementById("poolName").value;
		var poolType=document.getElementById("poolType").value;
		var instituteID=document.getElementById("instituteID").value;
		var msg="";
		if(poolName==''||poolCreationDate==''||cutOffDate==''||poolType==''||instituteID=='')
		{
			if(poolName=='')
				msg=msg+"*Pool Name is required.\n";
			if(poolCreationDate=='')
				msg=msg+"*Pool Creation Date is required.\n";
			if(cutOffDate=='')
				msg=msg+"*Cut Off Date is required.\n";
			if(poolType=='')
				msg=msg+"*Pool Type is required.\n";
			if(instituteID=='')
				msg=msg+"*InstituteID is required.\n";
			alert(msg); 
			if(msg.match("Pool Name"))
				document.getElementById("poolName").focus();
			else if(msg.match("Pool Creation Date"))
				document.getElementById("poolCreationDate").focus();
			else if(msg.match("Cut Off Date"))
				document.getElementById("cutOffDate").focus();
			else if(msg.match("Pool Type"))
				document.getElementById("poolType").focus();
			else if(msg.match("InstituteID"))
				document.getElementById("loanNoButton").focus();		
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			var dt1=getDateObject(poolCreationDate,formatD.substring(2,3));
			var dt2=getDateObject(cutOffDate,formatD.substring(2,3));
			var dt3=getDateObject(currDate,formatD.substring(2,3));
			var dt4=getDateObject(businessdate,formatD.substring(2,3));
			if(dt1>dt2)
			{
		 		alert("Cut Off Date cannot be less than Pool Creation Date.");
			 	DisButClass.prototype.EnbButMethod();
			 	return false;
			}
			if(poolType=='S' && dt2<dt4)
		 	{
		 		alert("In the case of Securitized Cut Off Date should be greater than or equal to Business date.");
		 		DisButClass.prototype.EnbButMethod();
		 		return false;
		 	}
		 	if(poolType=='R' && dt2>dt4)
		 	{
		 		alert("In the case of Re-Finance Cut Off Date should be less than or equal to Business date.");
		 		DisButClass.prototype.EnbButMethod();
		 		return false;
		 	}
		 	if(validateDocUpload())
		 	{
		 		document.getElementById("sourcingForm").action=sourcepath+"/poolProcUpload.do?method=uploadFile";
		 		document.getElementById("sourcingForm").submit();
		 		return true;
			}	
		}
	}
		</script>
	
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
	
	<html:form action="/PoolUploadBehindAction" method="post" styleId="sourcingForm" enctype="multipart/form-data" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	<input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate %>"/>
    <fieldset>
    
    <logic:present name="poolIdMaker"> 
	<legend><bean:message key="lbl.poolIdMaker"/></legend>   
	</logic:present>	    
	
   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>

        	 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        	 <tr>
		<td>&nbsp;<bean:message key="lbl.poolID"/></td>
		<td> 
		 <input type="text" name="poolID" id="poolID" value="${requestScope.poolNo}" readonly="readonly"/>
	       <!--  <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="50"  />  -->
	       
	       </td>
		
           <td><bean:message key="lbl.PoolName"/><font color="red">*</font></td>
	       <td>
	        <html:text styleClass="text" property="poolName" styleId="poolName" maxlength="50" value="" />
	       </td>
	 
        </tr>
        
	    <tr>   
          <td><bean:message key="lbl.PoolCreationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="poolCreationDate" styleId="poolCreationDate" value="" maxlength="20" />
	       </label></td>	
         
             
	       <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td><label>
	       <html:text styleClass="text" property="cutOffDate" styleId="cutOffDate"  maxlength="20" value=""/>
	       </label></td>	
		
		</tr>
		<tr>
		   <td><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      <td><label>
	    
		   	   <select id="poolType" class="text" name="poolType" >
			<option value="">--Select--</option>
			<option value="S">Securitized</option>
			<option value="R">Re-finance</option>
			
			</select>
	
	        </label>
	      </td>
	      <td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	    
		   <td>
			 <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="" readonly="true" tabindex="-1"/>   
		     <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
		     <input type="hidden" name="hcommon" id="hcommon" />
			 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
           </td>
        <td></td>
        <td></td>
			
    </tr>
    <tr>
		
	      <td><bean:message key="lbl.fileDescription"/><font color="red">*</font></td>
	      <td>
		  <html:file size="40" property="docFile" styleId="docFile" styleClass="text"/> 
		  </td>
		  <td></td>
			<td></td>
    </tr>
	  
		  
	</table>
	<table>
	<tr>
			
	        <td align="left">  
 			 <button type="button" name="button" id="save" class="topformbutton3" title="Alt+S" accesskey="S" onclick="return PoolIdUploadKtr();"><bean:message key="button.updsave" /></button>  
	      </td>
	      <td></td>
	      <td></td>
	      <td></td>
		</tr>
 
	</table>
		
	</td>
    </tr>
    </table>	 
	</fieldset>	
	
	
	
	
</html:form>
	</div>
	
	<logic:present name="sms">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.sms" />');
		 var basePath=document.getElementById("contextPath").value;	
		 document.getElementById("sourcingForm").action = basePath+'/poolProcUpload.do?method=openExcel';
	     document.getElementById("sourcingForm").submit(); 
 </script>
  </logic:present> 
  <logic:present name="deleteOk">
 <script type="text/javascript">	
 if(('<%=request.getAttribute("deleteOk")%>') == 'Y'){
	 alert('<bean:message key="lbl.deleteOk" />');
	
	}
		
 </script>
  </logic:present> 
  
    <logic:present name="srj">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.srj" />');
		 var basePath=document.getElementById("contextPath").value;	
		 document.getElementById("sourcingForm").action = basePath+'/poolProcUpload.do?method=openExcel';
	     document.getElementById("sourcingForm").submit(); 
 </script>
  </logic:present>
  
   
  <logic:present name="smsno">
 <script type="text/javascript">	
		alert('<bean:message key="lbl.smsno" />');
		 var basePath=document.getElementById("contextPath").value;	
	     document.getElementById("sourcingForm").action = basePath+'/poolProcUpload.do?method=openExcel';
	     document.getElementById("sourcingForm").submit(); 
 </script>
  </logic:present>
  
   
	<logic:present name="fieldUpdate">
       <script type="text/javascript">	
			alert("<%=request.getAttribute("fieldUpdate")%>");
       </script>
 </logic:present>
   
 <logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'Y')
		{
			alert("<bean:message key="lbl.forwardSuccess" />");
			document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdBehindMaker.do";
	        document.getElementById("sourcingForm").submit();
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
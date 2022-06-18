<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
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
	 
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
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
	<body onclick="parent_disable();" onload="enableAnchor();checkChanges('sourcingForm');document.getElementById('sourcingForm').comments.focus();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/holdInstrumentBankingMakerMain" method="post" styleId="sourcingForm">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="loanID" id="loanID" value="<%=session.getAttribute("loanID")%>" />
  <fieldset>
  <legend><bean:message key="lbl.author"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<logic:notPresent name="releasenotCheck">
		<tr>
					
		   <td><bean:message key="lbl.comments"/><font color="red">*</font></td>
			 <td><div style="float:left;">
			 <html:textarea property="comments" styleId="comments" value=""></html:textarea>
			  
			 </div></td>
		
		<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/><font color="red">*</font></td>
		 <td  id=""><span style="float: left;">
		 <html:select property="decision" styleId="decision">
		 	        
		        <html:option value="A">Approved</html:option>
		        <html:option value="X">Rejected</html:option>
		        <html:option value="P">Send Back</html:option>
		      </html:select> 
		 </span></td>
		</tr> 		  
		<tr>
	      <td align="left" colspan="3" class="form2">
	      <button type="button"  id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveholdInstrumentAuthor('<bean:message key="lbl.plsSelectComments"/>','<bean:message key="lbl.plsSelectDecision"/>');" ><bean:message key="button.save" /></button>
	      
		 </td>
		</tr> 
		</logic:notPresent>
		<logic:present name="releasenotCheck">
		<tr>
					
		   <td><bean:message key="lbl.comments"/><font color="red">*</font></td>
			 <td><div style="float:left;">
			 <html:textarea property="comments" styleId="comments" value=""></html:textarea>
			  
			 </div></td>
		
		<td width="17%">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/><font color="red">*</font></td>
		 <td  id=""><span style="float: left;">
		 <html:select property="decision" styleId="decision">
		 	       
		        <html:option value="L">Approved</html:option>
		        <html:option value="X">Rejected</html:option>
		        <html:option value="P">Send Back</html:option>
		      </html:select> 
		 </span></td>
		</tr> 		  
		<tr>
	      <td align="left" colspan="3" class="form2">
	       <button type="button"  id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return savereleaseInstrumentAuthor('<bean:message key="lbl.plsSelectComments"/>','<bean:message key="lbl.plsSelectDecision"/>');"><bean:message key="button.save" /></button>
	     </td>
		 
		</tr> 
		
		
		
		
		
		
		</logic:present>
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>
	<br/> 
	 <logic:present name="arrList">
	 <logic:notEmpty name="arrList">
         <logic:iterate id="arrListobj" name="arrList">
   
     <input type="hidden" name="instrumentID" id="instrumentID" value="${arrListobj.instrumentID}" />

         </logic:iterate>
         </logic:notEmpty>
         </logic:present>

<logic:present name="savedSuccessfully">
	<script type="text/javascript">
	if('<%=request.getAttribute("savedSuccessfully").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/holdInstrumentBankingAuthor.do";
	 
	
	}else if('<%=request.getAttribute("savedSuccessfully").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/holdInstrumentBankingAuthor.do";
	 
	} 
     </script>
	</logic:present>
  
  
  <logic:present name="savedReleaseSuccessfully">
	<script type="text/javascript">
	if('<%=request.getAttribute("savedReleaseSuccessfully").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/holdInstrumentBankingMakerMain.do?method=searchReleaseInstrumentAuthor";
	 
	
	}else if('<%=request.getAttribute("savedReleaseSuccessfully").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/holdInstrumentBankingMakerMain.do?method=searchReleaseInstrumentAuthor";
	 
	} 
     </script>
	</logic:present>


</html:form>
</div>

</div>
<script>
	setFramevalues("sourcingForm");
</script>
</body>
</html:html>
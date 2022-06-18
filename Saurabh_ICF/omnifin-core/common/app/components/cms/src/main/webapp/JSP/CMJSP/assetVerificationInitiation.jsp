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
		<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 request.setAttribute("requestVo",  request.getAttribute("requestVo"));
%>	
		<script type="text/javascript" src="<%=path%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	 	

   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/assetVerificationInitiation.js"></script>
   
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
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('sourcingForm').loanButton.focus();disableIntExt();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/assetVerificationInit" method="post" styleId="sourcingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="assetVarId" value="${requestScope.assetVerInitId}" id ="assetVarId"/>
	<input type="hidden" name="statusflag" value="${requestScope.statusflag}" id ="statusflag"/>
	<fieldset>
 
	<legend><bean:message key="lbl.assetVerificationInitiation"/></legend>   
	    
 
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		 <tr>	
	
<logic:present name="arrList">	 

 <logic:iterate id="listobj" name="arrList" >	
 <input type="hidden" name="contextPath" value="${listobj.recStatus}" id ="recStatus"/>
        	<td nowrap="nowrap"><bean:message key="lbl.loanNo"/></td>
			<td nowrap="nowrap"><html:text styleClass="text" property="loanNo" styleId="loanNo"	value="${listobj.loanNo}" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="${listobj.lbxLoanNo}" />					
			</td>
		  <td nowrap="nowrap"><bean:message key="lbl.customerName"/></td>
			<td nowrap="nowrap"><html:text styleClass="text" property="customerName" styleId="customerName"	maxlength="50" readonly="true" tabindex="-1" value="${listobj.customerName}"/>
		</td>
 </logic:iterate>

</logic:present>
<logic:present name="requestVo">	 

 <input type="hidden" name="contextPath" value="${requestVo.recStatus}" id ="recStatus"/>
        	<td nowrap="nowrap"><bean:message key="lbl.loanNo"/></td>
			<td nowrap="nowrap">
	<html:text styleClass="text" property="loanNo" styleId="loanNo"	value="${requestVo.loanNo}" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="${requestVo.lbxLoanNo}" />
							
			</td>
		  <td nowrap="nowrap"><bean:message key="lbl.customerName"/></td>
			<td nowrap="nowrap">
<html:text styleClass="text" property="customerName" styleId="customerName"	maxlength="50" readonly="true" tabindex="-1" value="${requestVo.customerName}"/>
		</td>


</logic:present>
<logic:notPresent name="requestVo">	
<logic:notPresent name="arrList">	

<input type="hidden" name="contextPath" value="${listobj.recStatus}" id ="recStatus"/> 	
        	<td nowrap="nowrap"><bean:message key="lbl.loanNo"/></td>
			<td nowrap="nowrap"><html:text styleClass="text" property="loanNo" styleId="loanNo"	value="" readonly="true" tabindex="-1" />
								<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(140,'sourcingForm','loanNo','','', '','','genrateAssetVerInitValues','customerName')" value=" " styleClass="lovbutton"></html:button>
								<%--<img onclick="openLOVCommon(140,'sourcingForm','loanNo','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/> --%>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
								<html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="" />
			</td>
		  <td nowrap="nowrap"><bean:message key="lbl.customerName"/></td>
			<td nowrap="nowrap"><html:text styleClass="text" property="customerName" styleId="customerName"	maxlength="50" readonly="true" tabindex="-1" value=""/>
		</td>
</logic:notPresent>	
</logic:notPresent>		
		</tr>     
		     
		 <tr>	
		 
		<logic:present name="requestVo">	 

			
				<td><bean:message key="lbl.appraiserType"/></td>
				<td>
				<logic:equal value="I"  name="requestVo" property="appraiser" >
				<html:text styleClass="text" property="appraiser" styleId="appraiser" readonly="true" maxlength="50"  value="Internal"/>
			   
			   </logic:equal>
			   <logic:equal value="EA"  name="requestVo" property="appraiser" >
				<html:text styleClass="text" property="appraiser" styleId="appraiser" readonly="true" maxlength="50"  value="External"/>
				
			   </logic:equal>
				<!--
						<html:select property="appraiser" styleId="appraiser" styleClass="text" onchange="disableIntExt();" value="${listobj.appraiser}">
						<html:option value="I" >Internal</html:option>
						<html:option value="EA">External</html:option>
						</html:select> 
				--></td>
			
			
		</logic:present>
			<logic:present name="arrList">	 

			<logic:iterate id="listobj" name="arrList" >	
				<td><bean:message key="lbl.appraiserType"/></td>
				<td><html:text styleClass="text" property="appraiser" styleId="appraiser" readonly="true" maxlength="50"  value="${listobj.appraiser}"/>
			    
				<!--
						<html:select property="appraiser" styleId="appraiser" styleClass="text" onchange="disableIntExt();" value="${listobj.appraiser}">
						<html:option value="I" >Internal</html:option>
						<html:option value="EA">External</html:option>
						</html:select> 
				--></td>
			</logic:iterate>
			
		</logic:present>
		<logic:notPresent name="requestVo">
		<logic:notPresent name="arrList">	 	
				<td><bean:message key="lbl.appraiserType"/></td>
				<td>
					<html:select property="appraiser" styleId="appraiser" styleClass="text" onchange="disableIntExt();" onblur="" >
					<html:option value="I" >Internal</html:option>
					<html:option value="EA">External</html:option>
					</html:select> 
			</td>
		</logic:notPresent>	
		</logic:notPresent>

          <td></td>
	 <td></td>
         </tr>
         
         <tr>
         	<input type="hidden" name="hcommon" id="hcommon"/>
	<logic:present name="arrList">	 

 <logic:iterate id="listobj" name="arrList" >	
		<td><bean:message key ="lbl.internalAppraiser"></bean:message> </td>
			<td>
			    <html:text styleClass="text" property="internalAppraiser" styleId="internalAppraiser" readonly="true" maxlength="50"  value="${listobj.internalAppraiser}"/>
			    <!--<html:button property="lovImg"  styleId="internalButton" onclick="openLOVCommon(311,'sourcingForm','internalAppraiser','','', '','','','hcommon')" value=" " styleClass="lovbutton"></html:button> 	-->	
			<html:hidden property="lbxUserId" styleId="lbxUserId" value="${listobj.lbxUserId}"/>	
		</td>  
		
		<td><bean:message key="lbl.externalAppraiser"></bean:message> </td>
		<td>	
			<html:text styleClass="text" property="externalAppraiser" styleId="externalAppraiser" maxlength="100" value="${listobj.externalAppraiser}" readonly="true" tabindex="-1"  />   
			<!--<html:button property="lovImg"  styleId="externalButton" onclick="openLOVCommon(97, 'sourcingForm','externalAppraiser','','', '','','','hcommon')" value=" " styleClass="lovbutton"></html:button> -->
			<html:hidden property="lbxextApprHID" styleId="lbxextApprHID" value="${listobj.lbxextApprHID}"/>
			<%--<img id="lovImg" onclick="openLOVCommon(97,'sourcingForm','externalAppraiser','','', '','','','hcommon')" SRC="<%= request.getContextPath()%>/images/lov.gif">--%>
  		  
		</td>
 </logic:iterate>

</logic:present>
<logic:present name="requestVo">	 

		<td><bean:message key ="lbl.internalAppraiser"></bean:message> </td>
			<td>
			    <html:text styleClass="text" property="internalAppraiser" styleId="internalAppraiser" readonly="true" maxlength="50"  value="${requestVo.internalAppraiser}"/>
			  <html:hidden property="lbxUserId" styleId="lbxUserId" value="${requestVo.lbxUserId}"/>	
		</td>  
		
		<td><bean:message key="lbl.externalAppraiser"></bean:message> </td>
		<td>	
			<html:text styleClass="text" property="externalAppraiser" styleId="externalAppraiser" maxlength="100" value="${requestVo.externalAppraiser}" readonly="true" tabindex="-1"/>   
		<html:hidden property="lbxextApprHID" styleId="lbxextApprHID" value="${requestVo.lbxextApprHID}"/>
		
		</td>


</logic:present>
<logic:notPresent name="requestVo">	
<logic:notPresent name="arrList">	 	
		<td><bean:message key ="lbl.internalAppraiser"></bean:message> </td>
			<td>
				<html:text styleClass="text" property="internalAppraiser" readonly="true" styleId="internalAppraiser" maxlength="50"  value=""/>
			    <html:button property="lovImg"  styleId="internalButton" onclick="openLOVCommon(311,'sourcingForm','internalAppraiser','','', '','','','hcommon')" value=" " styleClass="lovbutton"></html:button> 
				<html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
		</td>  
		
		<td><bean:message key="lbl.externalAppraiser"></bean:message> </td>
		<td>	
			<html:text styleClass="text" property="externalAppraiser" styleId="externalAppraiser" maxlength="100" value="" readonly="true" tabindex="-1"  />   
			<html:hidden property="lbxextApprHID" styleId="lbxextApprHID"  value="" />
			<html:button property="lovImg"  styleId="externalButton" onclick="openLOVCommon(315,'sourcingForm','externalAppraiser','','', '','','','hcommon')" value=" " styleClass="lovbutton"  ></html:button> 
			<%--<img id="lovImg" onclick="openLOVCommon(97,'sourcingForm','externalAppraiser','','', '','','','hcommon')" SRC="<%= request.getContextPath()%>/images/lov.gif">--%>
  		  
		</td>
</logic:notPresent>	
</logic:notPresent>
 </tr>
 </table>
	</td>
    </tr>
    </table>	 
	</fieldset>	

</html:form>
<br/>
	</div>
	

<fieldset>	
  
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <div id="gridList">
    <table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="1">
        
        
    <tr class="white2">
    
        <td><input type="checkbox" name="chkd" id="allchkd"  onclick="allChecked();" /></td><!--
       <td><strong><bean:message key="lbl.LoanNo"/></strong> </td>
		--><td><strong><bean:message key="lbl.AssetId"/></strong></td>
        <td><b><bean:message key="lbl.AssetDesc"/></b></td>
        <td><strong><bean:message key="lbl.AssetCost"/></strong> </td>
		<td><strong><bean:message key="lbl.AssetManufature"/></strong></td>
        <td><b><bean:message key="lbl.AssetSupplier"/></b></td>
       
       
     </tr>

         <logic:present name="assetList">
        <input type="hidden" name="loanidval" id="loanidval" value="${listobj.loanID}"/>
         <logic:iterate id="listobj" name="assetList" >
         
         <tr class="white1">
         
       
				<td><input type="checkbox" name="chk" id="chk" value="${listobj.assetID}"/></td>
				
				<!--
				
				<td>${listobj.loanAccNo}</td>
				<html:hidden property="loanAccNo1" styleId="loanAccNo1"  value="${listobj.loanAccNo}"/>
				
				--><td>${listobj.assetID}</td>
				<html:hidden property="assetID1" styleId="assetID1"  value="${listobj.assetID}"/>
				
				<td>${listobj.assetDescription}</td>
				<html:hidden property="assetDescription1" styleId="assetDescription1"  value="${listobj.assetDescription}"/>
				
				<td>${listobj.assetCost}</td>
				<html:hidden property="assetCost1" styleId="assetCost1"  value="${listobj.assetCost}"/>
				
				<td>${listobj.assetManufaturer}</td>
				<html:hidden property="assetManufaturer1" styleId="assetManufaturer1"  value="${listobj.assetManufaturer}"/>
				
				<td>${listobj.assetSupplier}</td>
				<html:hidden property="assetSupplier1" styleId="assetSupplier1"  value="${listobj.assetSupplier}"/>
          
         </tr>
             
         </logic:iterate>
        
         </logic:present>
      
          
     
 </table>   
 </div>
</td></tr>
     
     <tr>
  
  <logic:present name="arrList">	 
 <logic:iterate id="listobj" name="arrList" >	
      <td colspan="4">&nbsp;
     	
	<button type="button"  id="assetButton" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return modifyAssetVerification();"> <bean:message key="button.save" /></button>
    <button type="button"  id="assetForwardButton" title="Alt+F" accesskey="F" class="topformbutton2" onclick="return modifyforwardAssetVerification('${listobj.loanID}','${listobj.lbxextApprHID}','${listobj.appraiser}','${listobj.lbxUserId}');"> <bean:message key="button.forward" /></button>      
    <button type="button"  id="deleteButton" title="Alt+D" accesskey="D" class="topformbutton2" onclick="return deleteassetverificationid();"> <bean:message key="button.delete" /></button>      
     </td>
</logic:iterate>
</logic:present>

 <td colspan="4">&nbsp;
<logic:notPresent name="requestVo">	
<logic:notPresent name="arrList">	 	

     
      	<button type="button"   id="assetButton" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveAssetVerification();"> <bean:message key="button.save" /></button>
        <button type="button"   id="assetForwardButton" title="Alt+F" accesskey="F" class="topformbutton2" onclick="return modifyforwardAssetVerification('${requestVo.lbxLoanNo}','${requestVo.lbxextApprHID}','${requestVo.appraiser}','${requestVo.lbxUserId}');"><bean:message key="button.forward" /></button>       
     
</logic:notPresent>	
</logic:notPresent>
<logic:present name="requestVo">	
<button type="button"  id="assetButton" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return modifyAssetVerification();"> <bean:message key="button.save" /></button>
    <button type="button"  id="assetForwardButton" title="Alt+F" accesskey="F" class="topformbutton2" onclick="return modifyforwardAssetVerification('${requestVo.lbxLoanNo}','${requestVo.lbxextApprHID}','${requestVo.appraiser}','${requestVo.lbxUserId}');"> <bean:message key="button.forward" /></button>      
       
 <button type="button"  id="deleteButton" title="Alt+D" accesskey="D" class="topformbutton2" onclick="return deleteassetverificationid();"> <bean:message key="button.delete" /></button>      

</logic:present>
	</td>
   </tr>

</table>

	</fieldset>

</div>
<logic:present name="assetsnotsaved">
	<script type="text/javascript">
	if('<%=request.getAttribute("assetsnotsaved").toString()%>'!=''){

		alert('Asset(s) <%=request.getAttribute("assetsnotsaved").toString()%>'+" already pending for this appraiser.Please correct the mapping!!! " );

		document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/assetVerificationInitSearch.do?method=assetVerificationSearch";
	document.getElementById('sourcingForm').submit();
	}
	</script>
</logic:present>
<logic:present name="dss">
	<script type="text/javascript">
	
	if('<%=request.getAttribute("dss").toString()%>'=='S'){
	
	alert('<bean:message key="lbl.dataSavedSucc"/>');

	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/assetVerificationInitSearch.do?method=assetsavegrid";
	document.getElementById('sourcingForm').submit();
	document.getElementById('assetButton').setAttribute("disabled",true);
	}
	else if('<%=request.getAttribute("dss").toString()%>'=='M'){
	
	alert('<bean:message key="lbl.dataModify"/>');

	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/assetVerificationInitSearch.do?method=assetsavegrid";
	document.getElementById('sourcingForm').submit();
		document.getElementById('assetButton').setAttribute("disabled",true);
	}
	else if('<%=request.getAttribute("dss").toString()%>'=='F'){
	alert('<bean:message key="msg.ForwardSuccessfully"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/assetVerificationInitSearch.do?method=assetVerificationSearch";
	document.getElementById('sourcingForm').submit();
	
	}
	else if('<%=request.getAttribute("dss").toString()%>'=='D'){
	
	alert('<bean:message key="lbl.dataDeleted"/>');
	<%request.removeAttribute("requestVo");%>
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/assetVerificationInitSearch.do?method=assetVerificationSearch";
	document.getElementById('sourcingForm').submit();

	}
	else if('<%=request.getAttribute("dss")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}
	else {
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	 
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/assetVerificationInitSearch.do?method=assetsavegrid";
	document.getElementById('sourcingForm').submit();
	
	}
	
  </script>
	</logic:present>
</body>
</html:html>
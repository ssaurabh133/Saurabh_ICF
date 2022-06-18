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

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	 	
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
		</script>
		
		
	</head>
	<body onclick="parent_disable();" onload="enableAnchor();init_fields();" onunload="closeAllLovCallUnloadBody();">
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/holdInstrumentBankingMakerMain" method="post" styleId="sourcingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="instrumentAmount" id="instrumentAmount" value="<%=request.getAttribute("instrumentAmount")%>" />
     <input type="hidden" name="instrumentID" id="instrumentID" value="<%=request.getAttribute("instrumentID")%>" />    	
<fieldset>	
<logic:present name="instrumentMaker">
<table>

				 <tr class="white2">
				          <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>
				          <td><strong><bean:message key="lbl.instrumetAmount"/></strong></td>
				          <td><strong><bean:message key="lbl.installmentAmount"/> </strong></td>
				          <td><b><bean:message key="lbl.restAmount"/></b></td>
				<td>
				
				<html:button styleClass="LovSaveButton" property="lovlbxLawFirmDesc" onclick="return saveLinkedLoan();" value=" " accesskey="V" title="Alt+V"></html:button>
				<html:button styleClass="LovCloseButton" property="lovlbxLawFirmDesc" onclick="javascript:window.close();" value=" " accesskey="X" title="Alt+X"></html:button>
<%-- 
				<img width="24" height="24" src="<%=request.getContextPath()%>/images/save_icon.png" onclick="return saveLinkedLoan();" alt="Save" title="Save"/> 
               <img width="24" height="24" src="<%=request.getContextPath()%>/images/x-icon.png" alt="Close Popup" title="Close Popup" onclick="javascript:window.close();"/>
         --%>
    </td>
				     </tr>

            
            <tr>
            
             <td><input type="text" name="a" id="a" value="<%=request.getAttribute("loanAccNo") %>" readonly="readonly" /></td>
             <td><input type="text" name="b" id="b" value="<%=request.getAttribute("instrumentAmount") %>" readonly="readonly" /></td>
             <td><input type="text" name="c" id="c" value="<%=request.getAttribute("installmentAmount") %>" readonly="readonly" /></td>
             <td><input type="text" name="d" id="restAllocatedAmount" value="<%=request.getAttribute("restAllocatedAmount") %>" readonly="readonly" /></td>
                
         
             
              <td>&nbsp;</td>
               <td>&nbsp;</td>
              
            </tr>
            
  </table>


  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
        
        
    <tr class="white2">
          <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>
          <td><strong><bean:message key="lbl.date"/></strong></td>
          <td><strong><bean:message key="lbl.installmentNo"/></strong></td>
          <td><strong><bean:message key="lbl.installmentAmount"/> </strong></td>
          <td><b><bean:message key="lbl.allocatedAmount"/></b></td>

     </tr>
 
         <tr class="white1">
         
           <td><input type="text" name="loanAccNo" id="loanAccNomain" value="<%=request.getAttribute("loanAccNo") %>" readonly="readonly" /></td>  
         
            <td><input type="text" name="date" id="datemain" value="<%=request.getAttribute("date") %>" readonly="readonly" /></td>
            <td><input type="text" name="installmentNo" id="installmentNomain" value="<%=request.getAttribute("installmentNo") %>" readonly="readonly" /></td>
            <td><input type="text" name="installmentAmount" id="installmentAmountmain" value="<%=request.getAttribute("installmentAmount") %>" readonly="readonly" /></td>
            <td><input type="text" name="allotedAmount" id="allotedAmount" value="${requestScope.installmentAmount} "  readonly="readonly" />
            	<input type="hidden" name="lbxLoanNoHID" id="lbxLoanNoHIDmain" value="<%=request.getAttribute("loanId")%>" />
            </td>
            
          </tr>


        
         <tr class="white1">
         
           <td><html:text styleClass="text" property="loanAccNo" styleId="loanAccNo"  maxlength="100"  value="" readonly="true"/>
          
          <html:button property="loanLov" onclick="openLOVCommon(59,'sourcingForm','loanAccNo','','', '','','','installmentNo','installmentAmount','date');closeLovCallonLov1();" value=" " styleClass="lovButton" />
          
	<%-- <img onclick="openLOVCommon(59,'sourcingForm','loanAccNo','','', '','','','installmentNo','installmentAmount','date')" SRC="<%= request.getContextPath()%>/images/lov.gif">--%>
    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
    </td>
    
           <td><html:text property="date" styleId="date" value="" readonly="true"></html:text></td>
           <td><html:text property="installmentNo" styleId="installmentNo" value="" readonly="true"></html:text></td> 
          <td><html:text property="installmentAmount" styleId="installmentAmount" value="" readonly="true"></html:text></td> 
           
           <td><html:text property="allotedAmount" styleId="allotedAmount1" value="" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"></html:text></td> 
  
          </tr>
             <tr class="white1">
         
           <td><html:text styleClass="text" property="loanAccNo" styleId="loanAccNo1"  maxlength="100"  value="" readonly="true"/>
             <html:button property="loanLov" onclick="openLOVCommon(61,'sourcingForm','loanAccNo1','','', '','','','installmentNo1','installmentAmount1','date1');closeLovCallonLov1();" value=" " styleClass="lovButton" />
	<%-- <img onclick="openLOVCommon(61,'sourcingForm','loanAccNo1','','', '','','','installmentNo1','installmentAmount1','date1')" SRC="<%= request.getContextPath()%>/images/lov.gif">--%>
    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID1" />
    </td>
    
           <td><html:text property="date" styleId="date1" value="" readonly="true"></html:text></td>
           <td><html:text property="installmentNo" styleId="installmentNo1" value="" readonly="true"></html:text></td> 
          <td><html:text property="installmentAmount" styleId="installmentAmount1" value="" readonly="true"></html:text></td> 
           
           <td><html:text property="allotedAmount" styleId="allotedAmount2" value="" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"></html:text></td> 
  
          </tr>
             <tr class="white1">
         
           <td><html:text styleClass="text" property="loanAccNo" styleId="loanAccNo2"  maxlength="100"  value="" readonly="true"/>
           
            <html:button property="loanLov" onclick="openLOVCommon(62,'sourcingForm','loanAccNo2','','', '','','','installmentNo2','installmentAmount2','date2');closeLovCallonLov1();" value=" " styleClass="lovButton" />
	<%-- <img onclick="openLOVCommon(62,'sourcingForm','loanAccNo2','','', '','','','installmentNo2','installmentAmount2','date2')" SRC="<%= request.getContextPath()%>/images/lov.gif">--%>
    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID2" />
    </td>
    
           <td><html:text property="date" styleId="date2" value="" readonly="true"></html:text></td>
           <td><html:text property="installmentNo" styleId="installmentNo2" value="" readonly="true"></html:text></td> 
          <td><html:text property="installmentAmount" styleId="installmentAmount2" value="" readonly="true"></html:text></td> 
           
           <td><html:text property="allotedAmount" styleId="allotedAmount3" value="" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"></html:text></td> 
  
          </tr>
          
        
         <tr>
          </tr>
   
 </table>    </td>
</tr>
</table>
</logic:present>
<!-- ***********************************	Author starts	*********************************** -->
<logic:notPresent name="instrumentMaker">
<table>

				 <tr class="white2">
				          <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>
				          <td><strong><bean:message key="lbl.instrumetAmount"/></strong></td>
				          <td><strong><bean:message key="lbl.installmentAmount"/> </strong></td>
				          <td><b><bean:message key="lbl.restAmount"/></b></td>
				<td>
				
				<html:button styleClass="LovCloseButton" property="lovlbxLawFirmDesc" onclick="javascript:window.close();" value=" " accesskey="X" title="Alt+X"></html:button>

    </td>
				     </tr>

            
            <tr>
            
             <td><input type="text" name="a" id="a" value="<%=request.getAttribute("loanAccNo") %>" readonly="readonly" /></td>
             <td><input type="text" name="b" id="b" value="<%=request.getAttribute("instrumentAmount") %>" readonly="readonly" /></td>
             <td><input type="text" name="c" id="c" value="<%=request.getAttribute("installmentAmount") %>" readonly="readonly" /></td>
             <td><input type="text" name="d" id="restAllocatedAmount" value="<%=request.getAttribute("restAllocatedAmount") %>" readonly="readonly" /></td>
                
         
             
              <td>&nbsp;</td>
               <td>&nbsp;</td>
              
            </tr>
            
  </table>


  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
        
        
    <tr class="white2">
          <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>
          <td><strong><bean:message key="lbl.date"/></strong></td>
          <td><strong><bean:message key="lbl.installmentNo"/></strong></td>
          <td><strong><bean:message key="lbl.installmentAmount"/> </strong></td>
          <td><b><bean:message key="lbl.allocatedAmount"/></b></td>

     </tr>
 
         <tr class="white1">
         
           <td><input type="text" name="loanAccNo" id="loanAccNomain" value="<%=request.getAttribute("loanAccNo") %>" readonly="readonly" /></td>  
         
            <td><input type="text" name="date" id="datemain" value="<%=request.getAttribute("date") %>" readonly="readonly" /></td>
            <td><input type="text" name="installmentNo" id="installmentNomain" value="<%=request.getAttribute("installmentNo") %>" readonly="readonly" /></td>
            <td><input type="text" name="installmentAmount" id="installmentAmountmain" value="<%=request.getAttribute("installmentAmount") %>" readonly="readonly" /></td>
            <td><input type="text" name="allotedAmount" id="allotedAmount" value="${requestScope.installmentAmount} "  readonly="readonly" />
            	<input type="hidden" name="lbxLoanNoHID" id="lbxLoanNoHIDmain" value="<%=request.getAttribute("loanId")%>" />
            </td>
          </tr>


        
         <tr class="white1">
         
           <td><html:text styleClass="text" property="loanAccNo" styleId="loanAccNo"  maxlength="100"  value="" readonly="true"/>
          
          
    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
    </td>
    
           <td><html:text property="date" styleId="date" value="" readonly="true"></html:text></td>
           <td><html:text property="installmentNo" styleId="installmentNo" value="" readonly="true"></html:text></td> 
          <td><html:text property="installmentAmount" styleId="installmentAmount" value="" readonly="true"></html:text></td> 
           
           <td><html:text property="allotedAmount" styleId="allotedAmount1" value="" readonly="true" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"></html:text></td> 
  
          </tr>
             <tr class="white1">
         
           <td><html:text styleClass="text" property="loanAccNo" styleId="loanAccNo1"  maxlength="100"  value="" readonly="true"/>
             
    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID1" />
    </td>
    
           <td><html:text property="date" styleId="date1" value="" readonly="true"></html:text></td>
           <td><html:text property="installmentNo" styleId="installmentNo1" value="" readonly="true"></html:text></td> 
          <td><html:text property="installmentAmount" styleId="installmentAmount1" value="" readonly="true"></html:text></td> 
           
           <td><html:text property="allotedAmount" styleId="allotedAmount2" value="" readonly="true" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"></html:text></td> 
  
          </tr>
             <tr class="white1">
         
           <td><html:text styleClass="text" property="loanAccNo" styleId="loanAccNo2"  maxlength="100"  value="" readonly="true"/>
           
            
    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID2" />
    </td>
    
           <td><html:text property="date" styleId="date2" value="" readonly="true"></html:text></td>
           <td><html:text property="installmentNo" styleId="installmentNo2" value="" readonly="true"></html:text></td> 
          <td><html:text property="installmentAmount" styleId="installmentAmount2" value="" readonly="true"></html:text></td> 
           
           <td><html:text property="allotedAmount" styleId="allotedAmount3" value="" readonly="true" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"></html:text></td> 
  
          </tr>
          
        
         <tr>
          </tr>
   
 </table>    </td>
</tr>
</table>
</logic:notPresent>
<!-- ***********************************	Author ends	*********************************** -->
	</fieldset>
   
    
   </html:form>
	</div>
	
	
</div>
 <logic:present name="sms">
    <script type="text/javascript">
    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
	   
		alert('<bean:message key="lbl.dataSavedSucc" />');
		window.close();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='N')
	{
		alert('<bean:message key="lbl.dataNtSavedSucc" />');
		window.close();
	}
    </script>
    </logic:present>
</body>
</html:html>
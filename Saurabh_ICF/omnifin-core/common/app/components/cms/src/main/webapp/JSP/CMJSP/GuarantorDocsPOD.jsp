<%--
      Author Name-      Amit Kumar
      Date of creation -05/07/2011
      Purpose-          Entry of Leads
      Documentation-     
      
 --%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	   	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customerDetail.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/documentRelated.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/CaldocumentRelated.js"></script>
<style type="text/css">
input.readonly {
	background:#ebebe4;
	border:solid 1px #7f9db9;
	width:75px;
	}
	
fadeshade{
	opacity:0.4;
	filter:Alpha(opacity=40); 
	}	
</style>

<!-- Changes By Amit Starts -->

<script type="text/javascript" >
   
   function addROW(){
   // alert("function");
		var table = document.getElementById("gridtable");
		var psize= document.getElementById("psize").value;
		if(psize=='')
		{
			psize=0;
		}
			var rowCount = table.rows.length;
			if(rowCount==1)
			{
				psize=0;
			}
			var row = table.insertRow(rowCount);
			row.setAttribute('className','white1' );
		    row.setAttribute('class','white1' );
			
			var chk = row.insertCell(0);
			var element = document.createElement("input");
			element.type = "checkbox";
			element.name = "chk";
			element.id = "chk"+psize;
			element.value=psize;
			chk.appendChild(element);

			var docName = row.insertCell(1);
	      	var element1 = document.createElement('input');
	      	element1.type = "text";
			element1.name = "docNameAdditional";//Virender
			element1.id = "docNameAdditional"+psize;
			element1.value="";
			element1.setAttribute('maxlength','1000');
	    	docName.appendChild(element1);

			var originalOrCopy = row.insertCell(2);
	      	var element2 = document.createElement('select');
	      		element2.options[0] = new Option('Select','');
		  	  	element2.options[1] = new Option('Original','Y');
		  	  	element2.options[2] = new Option('Photocopy','N');
			 element2.setAttribute('className','text');
			 element2.setAttribute('class','text');
			 element2.style.width='10%';
	  	     element2.name = 'originalOrCopy';
	  	     element2.id = 'originalOrCopy'+psize;
	  	     originalOrCopy.appendChild(element2);



			var mandatoryOrNonMandatory = row.insertCell(3);
	      	var element3 = document.createElement('select');
	      		element3.options[0] = new Option('Select', '');
		  	  	element3.options[1] = new Option('Mandatory', 'Y');
		  	  	element3.options[2] = new Option('Non Mandatory', 'N');
			 element3.setAttribute('className','text');
			 element3.setAttribute('class','text');
			 element3.style.width='10%';
	  	     element3.name = 'mandatoryOrNonMandatory';
	  	     element3.id = 'mandatoryOrNonMandatory' + psize;
	  	   	 mandatoryOrNonMandatory.appendChild(element3);
		
			
		 var vDocType = row.insertCell(4);
	      	var element4 = document.createElement('select');
	      		element4.options[0] = new Option('NA', 'NA');
	      		element4.options[1] = new Option('OTC', 'OTC');
	      		element4.options[2] = new Option('PDD', 'PDD');
	      		element4.options[3] = new Option('As And When', 'AW');//Virender
	      		element4.setAttribute('className','text');
	      		element4.setAttribute('class','text');
	      		element4.style.width='10%';
	      		element4.name = 'vAdditionalDocType';
	      		element4.id = 'vAdditionalDocType' + psize;
	      		 element4.onchange=function(){
		 	  	    	docTypeChoiceAdd(psize-1);
		 				  
		 				};
	  	   vDocType.appendChild(element4);

			var additionalDocStatus = row.insertCell(5);
	      	var element5 = document.createElement('select');
	      		element5.options[0] = new Option('Pending', 'P');
	      		element5.options[1] = new Option('Received', 'R');
		  	  	element5.options[2] = new Option('Deferred', 'D');
		  	  	element5.options[3] = new Option('Waived', 'W');
		  	 element5.options[4] = new Option('Release', 'L');//Virender
		   	 element5.options[5] = new Option('NA', 'N');//Virender
			 element5.setAttribute('className','text');
			 element5.style.width='10%';
			 element5.setAttribute('class','text');
	  	     element5.name = 'additionalDocStatus';
	  	     element5.id = 'additionalDocStatus' + psize;
	  	     element5.onchange=function(){
	  	    	OTCPDDMarkingAdd(psize-1);
				   	makeChoiceAdditional(psize);
				   //	refreshChildId(psize);
				};
	  	   	additionalDocStatus.appendChild(element5);

			var additionalReceivedDate = row.insertCell(6);
			additionalReceivedDate.id = "addRec"+psize;
	      	var element6 = document.createElement('input');
	      	element6.type = "text";
			element6.name = "additionalReceivedDate";
			element6.id = "additionalReceivedDate"+psize;
			element6.value="";
			element6.setAttribute('class','text6');
			element6.setAttribute('readonly','true');
			element6.setAttribute('maxlength','10');
	    	additionalReceivedDate.appendChild(element6);
			
			var additionalDeferredDate = row.insertCell(7);
			additionalDeferredDate.id = "addDef"+psize;
	      	var element7 = document.createElement('input');
	      	element7.type = "text";
			element7.name = "additionalDeferredDate";
			element7.id = "additionalDeferredDate"+psize;
			element7.value="";
			element7.setAttribute('class','text6');
			element7.setAttribute('readonly','true');
			element7.setAttribute('maxlength','10');
	    	additionalDeferredDate.appendChild(element7);
	    	
	    	var additionalExpiryDate = row.insertCell(8);
	    	additionalExpiryDate.id = "addExp"+psize;
	      	var element8 = document.createElement('input');
	      	element8.type = "text";
			element8.name = "additionalExpiryDate";
			element8.id = "additionalExpiryDate"+psize;
			element8.value="";
			element8.setAttribute('class','text6');
			element8.setAttribute('maxlength','10');
			element8.setAttribute('readonly','readonly');
			element8.onchange=function(){
				checkDate("additionalExpiryDate"+psize);
			}
	    	additionalExpiryDate.appendChild(element8);
	    	//$(function() {
			//var contextPath =document.getElementById('contextPath').value ;
			//$("#additionalExpiryDate"+psize).datepicker({
			//format: "%Y-%m-%d %H:%i:%s %E %#",
			//formatUtcOffset: "%: (%@)",
			//placement: "inline",
 			//changeMonth: true,
 			//changeYear: true,
 			//yearRange: '1900:+10',
 			//showOn: 'both',
 			//buttonImage: contextPath+'/images/theme1/calendar.gif',
			//buttonImageOnly: true,
	        //dateFormat: document.getElementById("formatD").value,
			//defaultDate: document.getElementById("bDate").value

			//});
		//});
	
			var additionalRemarks = row.insertCell(9);
	      	var element9 = document.createElement('input');
	      	element9.type = "text";
			element9.name = "additionalRemarks";
			element9.id = "additionalRemarks"+psize;
			element9.value="";
			element9.setAttribute('class','text1');
			element9.setAttribute('maxlength','100');
	    	additionalRemarks.appendChild(element9);
	    	
	    	psize++;
			document.getElementById("psize").value=psize;
		}
	    
	     function removeRow() {	    
	     	// alert("removeRow()");
	         var table = document.getElementById("gridtable");
	         var rowCount = table.rows.length;
	         var count=0;
	         //alert("rowCount: "+rowCount);
	         for(var i=1; i<rowCount; i++) {
	             var row = table.rows[i];
	             //var chkbox = row.cells[0].childNodes[0];
	             //alert("chkbox: "+chkbox.value);
	             
	     		var chkbox = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
     			if(null != chkbox && true == chkbox.checked)
     			{
             	 	table.deleteRow(i);
                 	rowCount--;
                 	i--;
                 	count++;
                 }	         
	         }
	         if(count==0 && rowCount>1)
	         {
	         	alert("Please Select Atleast One Row");
	         }
	         if(rowCount==1 && count==0)
	         {
	         	alert("There is no Additional Document to remove");
	         }
	     }
	     
   </script> 

<!-- Changes By Amit Ends -->

	</head>
	<body onclick="parent_disable()" oncontextmenu="return false" onload="enableAnchor();init_fields();">
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
	<html:hidden property="businessdate" styleId="businessdate" value="${userobject.businessdate}"/>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" id="bpId" name="bpId" value="${sessionScope.dealId }"/>
	<input type="hidden" id="bpType" name="bpType" value="${sessionScope.txnType }"/>
	<input type="hidden" id="loanbpId" name="loanbpId" value="${sessionScope.loanId }"/>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<logic:present name="dealHeader">
	
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Document Collection</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	
	</logic:present>	
	
<logic:present name="loanHeader">
	
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	   <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Document Collection</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset> 
	
</logic:present>
	
<logic:notPresent name="viewDeal">
	<html:form action="/documentProcessActionPOD" styleId="DocumentForm" method="post" >
	
    <input type="hidden" name="entityType" id="entityType" value="GUARANTOR" />
                   
	<fieldset>
	<legend><bean:message key="lbl.guarantorDocs" /> </legend>  
<!--	<logic:present name="loanInitDocs">-->
<!--	   <font color="red">LoanId: ${sessionScope.loanId }</font>-->
<!--   </logic:present>-->
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">

         <td ><strong><bean:message key="lbl.guarantorName" /></strong></td>

          <td ><strong><bean:message key="lbl.documentName" /></strong></td>
          <td><b><bean:message key="lbl.originalOrPhotocopy" /></b></td>
          <td><b><bean:message key="lbl.mandatoryOrNonMandatory" /></b></td>
   <td ><b><bean:message key="lbl.DOC-TYPE" /></b></td>          <td ><b><bean:message key="lbl.status" /></b></td>
          <td><strong><bean:message key="lbl.receivedDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td><strong><bean:message key="lbl.deferredTillDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td ><strong><bean:message key="lbl.expiryDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td><strong><bean:message key="lbl.remark" /></strong></td>
          <td ><strong><bean:message key="lbl.uploadDownloadDocuments" /></strong></td>
          </tr>
     <logic:present name="documents">
           
		<input type="hidden" name="documentValue" id="documentVal" value="${requestScope.calDoc}"  />
		<logic:iterate id="subdocuments" name="documents" indexId="i" >
			<input type="hidden" name="documentVal" id="documentVal${i }" value="${i }"  />
		 		
		  <html:hidden property="documentId" styleId="documentId" value="${subdocuments.docId}" />
        <tr class="white1">
		 <td  >${subdocuments.applName}</td>
         
        <td>
  	<input type="hidden" name="docStatsCheck" id="docStatsCheck${i }" value="${subdocuments.docStatus}" />
      <input type="hidden" name="docFlag" id="docFlag${i }" value="${subdocuments.docChildFlag}" />
         <logic:equal value="R" name="subdocuments" property="docStatus">
	        <logic:equal name="subdocuments" property="docChildFlag" value="Y">
	           <a href="#" onclick="makeChildDocPod('${i}','${subdocuments.realDocId}','VC','${subdocuments.entityId}');">	${subdocuments.docDesc}<input type="hidden" name="docName" id="docName${i }" value="${subdocuments.docDesc}" /></a>
	        </logic:equal>
	        <logic:notEqual name="subdocuments" property="docChildFlag" value="Y">
	          	${subdocuments.docDesc}<input type="hidden" name="docName" id="docName${i }" value="${subdocuments.docDesc}" />
	        </logic:notEqual>
	    </logic:equal>
       
        <logic:notEqual value="R" name="subdocuments" property="docStatus">
	       <logic:equal name="subdocuments" property="docChildFlag" value="Y">
	           <a href="#" onclick="makeChildDocPod('${i}','${subdocuments.realDocId}','MC','${subdocuments.entityId}');">	${subdocuments.docDesc}<input type="hidden" name="docName" id="docName${i }" value="${subdocuments.docDesc}" /></a>
	       </logic:equal>
	       <logic:notEqual name="subdocuments" property="docChildFlag" value="Y">
	          	${subdocuments.docDesc}<input type="hidden" name="docName" id="docName${i }" value="${subdocuments.docDesc}" />
	       </logic:notEqual>
	    </logic:notEqual>
   </td>
		  
          <td ><center>
            ${subdocuments.original}
          </center></td>
          <td ><center>${subdocuments.mandatory}</center>
          <input type="hidden" name="mandOrNonMand" id="mandOrNonMand${i }" value="${subdocuments.mandatory}" />
          </td>
 <!-- Virender Code start -->
		 <td >
          <html:select property="vDocType" styleClass=""  styleId="vDocType${i}" value="${subdocuments.vDocumentType}" onchange="docTypeChoice(${i});refreshChildId(${i }); ">
            <logic:equal name="mandOrNonMand" value="NON-MANDATORY">
           <html:option value="NA">NA</html:option>
            <html:option value="OTC">OTC</html:option>
            <html:option value="PDD">PDD</html:option>
            <html:option value="AW">As And When</html:option><!-- Virender -->
            </logic:equal>
            
            <logic:notEqual name="mandOrNonMand" value="NON-MANDATORY">
            <html:option value="NA">NA</html:option>
            <html:option value="OTC">OTC</html:option>
            <html:option value="PDD">PDD</html:option>
            <html:option value="AW">As And When</html:option><!-- Virender -->
            </logic:notEqual>
          </html:select>
         </td>
     <!-- Virender code end --> <td>
      <html:hidden property="statusPrev" styleId="statusPrev${i}" value="${subdocuments.docStatusPrev}"/>
        <logic:equal name="subdocuments" property="docStatus" value="R" >
         <logic:equal name="subdocuments" property="original" value="ORIGINAL" >
           	<html:select property="status" styleClass="" styleId="status${i}" value="${subdocuments.docStatus}" onchange="OTCPDDMarking(${i });makeChoice(${i});">
            <html:option value="R">Received</html:option>
            <html:option value="L">Release</html:option> 
            <html:option value="N">NA</html:option><!-- Virender --> 
         	</html:select>
         </logic:equal>
         <logic:notEqual name="subdocuments" property="original" value="ORIGINAL">
           	<html:select property="status" styleClass="" styleId="status${i}" value="${subdocuments.docStatus}" onchange="OTCPDDMarking(${i });makeChoice(${i});">
            <html:option value="R">Received</html:option>
            <html:option value="L">Release</html:option>  <!-- Virender -->
            <html:option value="N">NA</html:option><!-- Virender -->
          	</html:select>
         </logic:notEqual>
		</logic:equal>
		
		<logic:notEqual name="subdocuments" property="docStatus" value="R" >
          	<html:select property="status" styleClass="" styleId="status${i}" value="${subdocuments.docStatus}" onchange="OTCPDDMarking(${i });makeChoice(${i});refreshChildId(${i });">
<!--           <html:option value="">--Select--</html:option>-->
             <html:option value="P">Pending</html:option>
            <html:option value="R">Received</html:option>
            <html:option value="D">Deferred</html:option>
            <html:option value="W">Waived</html:option>
			<html:option value="L">Release</html:option>  <!-- Virender -->
			<html:option value="N">NA</html:option><!-- Virender -->
          	</html:select>
		</logic:notEqual>
	</td>
		
                  <html:hidden property="childId" styleId="childId${i }" value="${subdocuments.docChildId}"/>   
	           <td  id="rec${i}">
	           
		            <logic:equal name="subdocuments" property="docStatus" value="D">
		              <input type="text" name="recievedDate" class="text6" readonly="readonly"  value="" />
		            </logic:equal>
		            <logic:equal name="subdocuments" property="docStatus" value="R">
		            <!-- Changes By Amit Starts -->
		            	<script type="text/javascript">
		            		$(function() {
								var contextPath =document.getElementById('contextPath').value ;
								$("#recievedDate"+${i}).datepicker({
								format: "%Y-%m-%d %H:%i:%s %E %#",
								formatUtcOffset: "%: (%@)",
								placement: "inline",
					 			changeMonth: true,
					 			changeYear: true,
					 			yearRange: '1900:+10',
					 			showOn: 'both',
					 			buttonImage: contextPath+'/images/theme1/calendar.gif',
								buttonImageOnly: true,
						        dateFormat: document.getElementById("formatD").value,
								defaultDate: document.getElementById("bDate").value
					
								});
							});
		            	</script>
		            	 <html:text property="recievedDate" styleClass="text6" styleId="recievedDate${i}" value="${subdocuments.recieveDate}"   maxlength="10" onchange="return checkDate('recievedDate${i}');"/>
		            <!-- Changes By Amit Ends -->
		            </logic:equal>
		            <logic:equal name="subdocuments" property="docStatus" value="P">
		              <input type="text" name="recievedDate" class="text6" readonly="readonly"  value="" />
		            </logic:equal>
		            <logic:equal name="subdocuments" property="docStatus" value="W">
		              <input type="text" name="recievedDate" class="text6" readonly="readonly"  value="" />
		            </logic:equal>
		             <logic:equal name="subdocuments" property="docStatus" value="L">
		              <input type="text" name="recievedDate" class="text6" readonly="readonly"  value="" />
		            </logic:equal>
	           
	           </td>
         
             <td  id="def${i}">
               <logic:equal name="subdocuments" property="docStatus" value="R">
                   <input type="text" name="deferedDate" readonly="readonly" class="text6" />
               </logic:equal>
               <logic:equal name="subdocuments" property="docStatus" value="D">
               <!-- Changes By Amit Starts -->
                   <html:text property="deferedDate" styleClass="text6" readonly="true" styleId="deferedDate${i }" value="${subdocuments.deferDate}" maxlength="10" onchange="return checkDate('deferedDate${i }');"/>
               <!-- Changes By Amit Ends -->
               </logic:equal>
                <logic:equal name="subdocuments" property="docStatus" value="P">
		              <input type="text" name="deferedDate" class="text6" readonly="readonly"  value="" />
		         </logic:equal>
		         <logic:equal name="subdocuments" property="docStatus" value="W">
		             <input type="text" name="deferedDate" class="text6" readonly="readonly"  value="" />
		         </logic:equal>
		         <logic:equal name="subdocuments" property="docStatus" value="L">
		             <input type="text" name="deferedDate" class="text6" readonly="readonly"  value="" />
		         </logic:equal>
               
                </td>
             
         <input type="hidden" name="flagExp" id="flagExp${i}" value="${subdocuments.expirFlag }" />
              <td  id="exp${i}">
                <logic:equal name="subdocuments" property="expirFlag" value="N">
                    <input type="text" name="expiryDate" class="text6" readonly="readonly"   />
                </logic:equal>
                
                <logic:equal name="subdocuments" property="expirFlag" value="Y">
                	<logic:equal name="subdocuments" property="docStatus" value="R">
                     <html:text property="expiryDate" readonly="true"  styleClass="text6" styleId="expirDate${i}" value="${subdocuments.expirDate}"  maxlength="10"  />
                   </logic:equal>
                     <logic:notEqual name="subdocuments" property="docStatus" value="R">
                     <input type="text" name="expiryDate" class="text6" readonly="readonly"   />
                   </logic:notEqual>
                </logic:equal>
                
               </td>
             <td  id="rem${i }"><html:text property="remarks" styleClass="text1" styleId="remarks${i }" value="${subdocuments.remark}"  maxlength="50" /></td>
             <logic:equal name="subdocuments" property="docChildFlag" value="Y">
               <td align="center" id="upl${i}"><a href="javascript:;" onclick="uploadDocuments('${i}','${subdocuments.realDocId}','${subdocuments.entityId}','Y','GUARANTOR');"%>Upload/Download</a></td>
               </logic:equal>
               <logic:notEqual name="subdocuments" property="docChildFlag" value="Y">
               <td align="center" id="upl${i}"><a href="javascript:;" onclick="uploadDocuments('${i}','${subdocuments.realDocId}','${subdocuments.entityId}','N','GUARANTOR');">Upload/Download</td>
               </logic:notEqual>
          </tr>
       </logic:iterate>
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/CaldocumentRelated.js"></script>
	</logic:present> 
        </table>    </td>
  </tr>
</table>
</fieldset>

<!-- Changes By Amit Starts -->  
	  <fieldset>
	  		<legend><bean:message key="lbl.additionalDocs" /></legend>  
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      <input type="hidden"  name="psize" id="psize" value="${requestScope.psize}"/>
      <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><strong><bean:message key="lbl.select" /></strong></td>
          <td><strong><bean:message key="lbl.documentName" /></strong></td>
          <td><b><bean:message key="lbl.originalOrPhotocopy" /></b></td>
          <td><b><bean:message key="lbl.mandatoryOrNonMandatory" /></b></td>
   <td ><b><bean:message key="lbl.DOC-TYPE" /></b></td>          <td ><b><bean:message key="lbl.status" /></b></td>
          <td><strong><bean:message key="lbl.receivedDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td ><strong><bean:message key="lbl.deferredTillDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td><strong><bean:message key="lbl.expiryDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td ><strong><bean:message key="lbl.remark" /></strong></td>
          </tr>
          
          <logic:present name="additionalDocuments">
			<logic:iterate id="subdocuments" name="additionalDocuments" indexId="i" >
        	<tr class="white1">
        		<td>
        			<input type="checkbox" name="chk" id="chk${i}" value="${i}" />
        		</td>
		 		<td>
		 			<html:text property="docNameAdditional" styleId="docNameAdditional${i}" value="${subdocuments.docNameAddn}"
		 				styleClass="text" maxlength="1000" /><!-- Virender -->
		 		</td>
         		<td>
         			<html:select property="originalOrCopy" styleClass="" styleId="originalOrCopy${i }" value="${subdocuments.orgOrCopy}" style="width:10%;">
            			<html:option value="">Select</html:option>
            			<html:option value="Y">Original</html:option>
            			<html:option value="N">Photocopy</html:option>
          			</html:select>
         		</td>
		  		<td>
		  			<html:select property="mandatoryOrNonMandatory" styleClass="" styleId="mandatoryOrNonMandatory${i }" value="${subdocuments.mandateOrNonMandate}" style="width:10%;">
            			<html:option value="">Select</html:option>
            			<html:option value="Y">Mandatory</html:option>
            			<html:option value="N">Non Mandatory</html:option>
          			</html:select>
		  		</td>
<td >
		          	<html:select property="vAdditionalDocType" styleClass=""  styleId="vAdditionalDocType${i}" value="${subdocuments.vDocumentType}" onchange="docTypeChoiceAdd(${i});refreshChildId(${i }); ">
		           <html:option value="NA">NA</html:option>
            <html:option value="OTC">OTC</html:option>
            <html:option value="PDD">PDD</html:option>
            <html:option value="AW">As And When</html:option><!-- Virender -->
		          	</html:select>
		         </td>          		<td>         
         			<html:select property="additionalDocStatus" styleClass="" styleId="additionalDocStatus${i }" value="${subdocuments.addnDocStatus}" onchange="OTCPDDMarkingAdd(${i });makeChoiceAdditional(${i+1});" style="width:10%;">
            			<html:option value="P">Pending</html:option>
            			<html:option value="R">Received</html:option>
            			<html:option value="D">Deferred</html:option>
            			<html:option value="W">Waived</html:option>
            			<html:option value="L">Release</html:option>  <!-- Virender -->
            			<html:option value="N">NA</html:option><!-- Virender -->
          			</html:select>
          		</td>  
	           	<td  id="addRec${i }">
	               <logic:equal name="subdocuments" property="addnDocStatus" value="D">
		              <input type="text" name="additionalReceivedDate" class="text6" readonly="readonly"  value="" />
		            </logic:equal>
		            <logic:equal name="subdocuments" property="addnDocStatus" value="R">
		            	<script type="text/javascript">
               				$(function() {
								var contextPath =document.getElementById('contextPath').value ;
								$("#additionalReceivedDate"+${i}).datepicker({
								format: "%Y-%m-%d %H:%i:%s %E %#",
								formatUtcOffset: "%: (%@)",
								placement: "inline",
								changeMonth: true,
								changeYear: true,
								yearRange: '1900:+10',
								showOn: 'both',
								buttonImage: contextPath+'/images/theme1/calendar.gif',
								buttonImageOnly: true,
					        	dateFormat: document.getElementById("formatD").value,
								defaultDate: document.getElementById("bDate").value
								});
							});
               			</script>
		            	 <html:text property="additionalReceivedDate" styleClass="text6" styleId="additionalReceivedDate${i}" value="${subdocuments.addnReceivedDate}" maxlength="10" onchange="return checkDate('additionalReceivedDate${i}');"/>
		            </logic:equal>
		            <logic:equal name="subdocuments" property="addnDocStatus" value="P">
		              <input type="text" name="additionalReceivedDate" class="text6" readonly="readonly"  value="" />
		            </logic:equal>
		            <logic:equal name="subdocuments" property="addnDocStatus" value="W">
		              <input type="text" name="additionalReceivedDate" class="text6" readonly="readonly"  value="" />
		            </logic:equal>
	          	 </td>
            	 <td id="addDef${i }">
              	 	<logic:equal name="subdocuments" property="addnDocStatus" value="R">
                   		<input type="text" name="additionalDeferredDate" readonly="readonly" class="text6" />
               		</logic:equal>
               		<logic:equal name="subdocuments" property="addnDocStatus" value="D">
               			<script type="text/javascript">
               				$(function() {
								var contextPath =document.getElementById('contextPath').value ;
								$("#additionalDeferredDate"+${i}).datepicker({
								format: "%Y-%m-%d %H:%i:%s %E %#",
								formatUtcOffset: "%: (%@)",
								placement: "inline",
								changeMonth: true,
								changeYear: true,
								yearRange: '1900:+10',
								showOn: 'both',
								buttonImage: contextPath+'/images/theme1/calendar.gif',
								buttonImageOnly: true,
					        	dateFormat: document.getElementById("formatD").value,
								defaultDate: document.getElementById("bDate").value,
								minDate : document.getElementById("bDate").value
								});
							});
               			</script>
                   		<html:text property="additionalDeferredDate"   styleClass="text6" readonly="true" styleId="additionalDeferredDate${i}" value="${subdocuments.addnDeferredDate}" maxlength="10" onchange="return checkDate('additionalDeferredDate${i}');"/>
               		</logic:equal>
                	<logic:equal name="subdocuments" property="addnDocStatus" value="P">
		              	<input type="text" name="additionalDeferredDate" class="text6" readonly="readonly"  value="" />
		         	</logic:equal>
		         	<logic:equal name="subdocuments" property="addnDocStatus" value="W">
		             	<input type="text" name="additionalDeferredDate" class="text6" readonly="readonly"  value="" />
		         	</logic:equal>
                </td>
               	<td id="addExp${i}">
               		<logic:equal name="subdocuments" property="addnDocStatus" value="R">
	               		<script type="text/javascript">
	               			$(function() {
								var contextPath =document.getElementById('contextPath').value ;
								$("#additionalExpiryDate"+${i}).datepicker({
								format: "%Y-%m-%d %H:%i:%s %E %#",
								formatUtcOffset: "%: (%@)",
								placement: "inline",
								changeMonth: true,
								changeYear: true,
								yearRange: '1900:+10',
								showOn: 'both',
								buttonImage: contextPath+'/images/theme1/calendar.gif',
								buttonImageOnly: true,
					        	dateFormat: document.getElementById("formatD").value,
								defaultDate: document.getElementById("bDate").value
								});
							});
	               		</script>
	                     <html:text property="additionalExpiryDate"   styleClass="text6" styleId="additionalExpiryDate${i}" value="${subdocuments.addnExpiryDate}"  maxlength="10"  onchange="return checkDate('additionalExpiryDate${i}');"/>
                	</logic:equal>
                	<logic:equal name="subdocuments" property="addnDocStatus" value="D">
                		<html:text property="additionalExpiryDate"   styleClass="text6" value="${subdocuments.addnExpiryDate}" readonly="true" />
                	</logic:equal>
                	<logic:equal name="subdocuments" property="addnDocStatus" value="W">
                		<html:text property="additionalExpiryDate"   styleClass="text6" value="${subdocuments.addnExpiryDate}" readonly="true" />
                	</logic:equal>
                	<logic:equal name="subdocuments" property="addnDocStatus" value="P">
                		<html:text property="additionalExpiryDate"   styleClass="text6" value="${subdocuments.addnExpiryDate}" readonly="true" />
                	</logic:equal>
                </td>
             	<td>
             		<html:text property="additionalRemarks" styleClass="text1" styleId="additionalRemarks${i }" value="${subdocuments.addnRemarks}"  maxlength="100" />
             	</td>
          	</tr>
       </logic:iterate>
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/CaldocumentRelated.js"></script>
	</logic:present>
	
	
        </table>
        </td>
        </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
	<tr>
 <td>
<logic:present name="documents">      
 <logic:present name="dataFound">
<button type="button" name="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveDocumentPOD('G','Y');"><bean:message key="button.save" /></button> 
</logic:present>   
<logic:notPresent name="dataFound">
<button type="button" name="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveDocumentPOD('G','N');"><bean:message key="button.save" /></button> 
</logic:notPresent>

 <!--change by sachin-->
<logic:present name="noDocument">
<%-- <fadeshade>
 <button type="button" name="button1" id="button1" class="topformbutton3"   disabled="disabled" ><bean:message key="button.addDocs" /></button>
</fadeshade> --%>
<button type="button" name="button1" id="button1" class="topformbutton3" title="Alt+A" accesskey="A" onclick="addROW();"><bean:message key="button.addDocs" /></button>
 </logic:present>
 <logic:notPresent name="noDocument">
 <button type="button" name="button1" id="button1" class="topformbutton3" title="Alt+A" accesskey="A" onclick="addROW();"><bean:message key="button.addDocs" /></button>
 </logic:notPresent>
<!-- end by sachin-->

 <button type="button" name="button2" id="button2" class="topformbutton4" title="Alt+R" accesskey="R" onclick="removeRow();"><bean:message key="button.removeDocs" /></button>
</logic:present>
</td>
  </tr>
	</table>
	</fieldset>
<!-- Changes By Amit Ends -->


<logic:present name="sms">
<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.documentSuccess" />');
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert('<bean:message key="lbl.canForward" />');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='NF')
	{
		alert('<bean:message key="lbl.cantForwardSucc" />');
	}
		else if('<%=request.getAttribute("sms").toString()%>'=='PROCERROR')
	{
		alert("DOCUMENT_COLLECTION_MAKER---UNKNOWN EXCEPTION IN SQL EXECUTION, CONTACT SYSTEM ADMINISTRATOR...");
	}	
	else
	{
		alert('<bean:message key="lbl.documentError" />');
	}	
</script>
</logic:present> 

  </html:form>
  </logic:notPresent>
  <logic:present name="viewDeal">
  		<html:form action="/documentProcessAction" styleId="DocumentForm" method="post" >
	
    <input type="hidden" name="entityType" id="entityType" value="GUARANTOR" />
                   
	<fieldset>
	<legend><bean:message key="lbl.guarantorDocs" /> </legend>  
<!--	<logic:present name="loanInitDocs">-->
<!--	   <font color="red">LoanId: ${sessionScope.loanId }</font>-->
<!--   </logic:present>-->
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table width="100%" border="0" cellspacing="1" cellpadding="1">
       <tr class="white2">

         <td ><strong><bean:message key="lbl.guarantorName" /></strong></td>

          <td ><strong><bean:message key="lbl.documentName" /></strong></td>
          <td><b><bean:message key="lbl.originalOrPhotocopy" /></b></td>
          <td><b><bean:message key="lbl.mandatoryOrNonMandatory" /></b></td>
    <td ><b><bean:message key="lbl.DOC-TYPE" /></b></td>          <td ><b><bean:message key="lbl.status" /></b></td>
          <td><strong><bean:message key="lbl.receivedDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td><strong><bean:message key="lbl.deferredTillDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td ><strong><bean:message key="lbl.expiryDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td><strong><bean:message key="lbl.remark" /></strong></td>
          <td ><strong><bean:message key="lbl.download" /></strong></td>
          </tr>
     <logic:present name="documents">
           
		<input type="hidden" name="documentValue" id="documentVal" value="${requestScope.calDoc}"  />
		<logic:iterate id="subdocuments" name="documents" indexId="i" >
			<input type="hidden" name="documentVal" id="documentVal${i }" value="${i }"  />
		 		
		  <html:hidden property="documentId" styleId="documentId" value="${subdocuments.docId}" />
        <tr class="white1">
		 <td >${subdocuments.applName}</td>
         
             <td>
           <logic:equal name="subdocuments" property="docChildFlag" value="Y">
           <html:hidden property="childId" styleId="childId${i }" value="${subdocuments.docChildId}"/>  
              <a href="#" onclick="makeChildDocPod('${i}','${subdocuments.realDocId}','VC','${subdocuments.entityId}');">${subdocuments.docDesc}<input type="hidden" name="docName" id="docName${i }" value="${subdocuments.docDesc}" /></a>
           </logic:equal>
           <logic:notEqual name="subdocuments" property="docChildFlag" value="Y">
           		${subdocuments.docDesc}
           </logic:notEqual>
          </td>
		  
          <td ><center>
            ${subdocuments.original}
          </center></td>
          <td ><center>${subdocuments.mandatory}</center>
          <input type="hidden" name="mandOrNonMand" id="mandOrNonMand${i }" value="${subdocuments.mandatory}" />
          </td>
  <td >
          <html:select property="vDocType" styleClass=""  disabled="true" styleId="vDocType${i}" value="${subdocuments.vDocumentType}" onchange="docTypeChoice(${i});refreshChildId(${i }); ">
           <html:option value="NA">NA</html:option>
            <html:option value="OTC">OTC</html:option>
            <html:option value="PDD">PDD</html:option>
           <html:option value="AW">As And When</html:option><!-- Virender -->
            </html:select>
         </td>          <td >
           <html:hidden property="statusPrev" styleId="statusPrev${i}" value="${subdocuments.docStatusPrev}"/>
         <html:select property="status" styleClass="" styleId="status${i }" value="${subdocuments.docStatus}" disabled="true" onchange="OTCPDDMarking(${i });makeChoice(${i });">
<!--           <html:option value="">--Select--</html:option>-->
            <html:option value="R">Received</html:option>
            <html:option value="D">Deferred</html:option>
           
            <html:option value="P">Pending</html:option>
            <html:option value="W">Waived</html:option>
            <html:option value="L">Release</html:option>
            <html:option value="N">NA</html:option><!-- Virender -->
          </html:select></td>
          

	           <td  id="rec${i }">
	            <logic:equal name="subdocuments" property="docStatus" value="D">
	              <input type="text" name="recievedDate" class="text6" disabled="disabled"   />
	            </logic:equal>
	            <logic:notEqual name="subdocuments" property="docStatus" value="D">
	            	 <html:text property="recievedDate" styleClass="text6" disabled="true" value="${subdocuments.recieveDate}"   maxlength="10"  />
	            </logic:notEqual>
	           
	           </td>
         
             <td  id="def${i }">
               <logic:equal name="subdocuments" property="docStatus" value="R">
                   <input type="text" name="deferedDate" readonly="readonly" class="text6" disabled="disabled"/>
               </logic:equal>
               <logic:notEqual name="subdocuments" property="docStatus" value="R">
                   <html:text property="deferedDate" disabled="true"  styleClass="text6"  value="${subdocuments.deferDate}" maxlength="10" />
               </logic:notEqual>
                </td>
             
               <td  id="exp${i }">
                <logic:equal name="subdocuments" property="expirFlag" value="N">
                    <input type="text" name="expiryDate" class="text6" disabled="disabled" />
                </logic:equal>
                <logic:equal name="subdocuments" property="expirFlag" value="Y">
                  <html:text property="expiryDate" readonly="true"  styleClass="text6" disabled="true" value="${subdocuments.expirDate}"  maxlength="10"  />
                </logic:equal>
               </td>
             <td  id="rem${i }"><html:text property="remarks" styleClass="text1" styleId="remarks" value="${subdocuments.remark}" disabled="true"  maxlength="50" /></td>
             <logic:equal name="subdocuments" property="docChildFlag" value="Y">
               <td align="center" id="upl${i}"><a href="javascript:;" onclick="uploadDocuments('${i}','${subdocuments.realDocId}','${subdocuments.entityId}','Y','GUARANTOR');"%>Download</a></td>
               </logic:equal>
               <logic:notEqual name="subdocuments" property="docChildFlag" value="Y">
               <td align="center" id="upl${i}"><a href="javascript:;" onclick="uploadDocuments('${i}','${subdocuments.realDocId}','${subdocuments.entityId}','N','GUARANTOR');">Download</td>
               </logic:notEqual>
          </tr>
       </logic:iterate>
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/Caldocument.js"></script>
	</logic:present> 
        </table>    </td>
  </tr>
</table>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
  
	</table>
	
	  </fieldset>

<!-- Changes By Amit Starts -->  
	  <fieldset>
	  		<legend><bean:message key="lbl.additionalDocs" /></legend>  
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      <input type="hidden"  name="psize" id="psize" value="${requestScope.psize}"/>
      <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><strong><bean:message key="lbl.select" /></strong></td>
          <td><strong><bean:message key="lbl.documentName" /></strong></td>
          <td><b><bean:message key="lbl.originalOrPhotocopy" /></b></td>
          <td><b><bean:message key="lbl.mandatoryOrNonMandatory" /></b></td>
   <td ><b><bean:message key="lbl.DOC-TYPE" /></b></td>          <td ><b><bean:message key="lbl.status" /></b></td>
          <td><strong><bean:message key="lbl.receivedDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td ><strong><bean:message key="lbl.deferredTillDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td><strong><bean:message key="lbl.expiryDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></strong></td>
          <td ><strong><bean:message key="lbl.remark" /></strong></td>
          </tr>
          
          <logic:present name="additionalDocuments">
			<logic:iterate id="subdocuments" name="additionalDocuments" indexId="i" >
        	<tr class="white1">
        		<td>
        			<input type="checkbox" name="chk" id="chk${i}" value="${i}" disabled="disabled"/>
        		</td>
		 		<td>
		 			<html:text property="docNameAdditional" styleId="docNameAdditional${i}" value="${subdocuments.docNameAddn}"
		 				styleClass="text" maxlength="1000" disabled="true"/><!-- Virender -->
		 		</td>
         		<td>
         			<html:select property="originalOrCopy" styleClass="" styleId="originalOrCopy${i }" value="${subdocuments.orgOrCopy}" style="width:10%;" disabled="true">
            			<html:option value="">Select</html:option>
            			<html:option value="Y">Original</html:option>
            			<html:option value="N">Photocopy</html:option>
          			</html:select>
         		</td>
		  		<td>
		  			<html:select property="mandatoryOrNonMandatory" styleClass="" styleId="mandatoryOrNonMandatory${i }" value="${subdocuments.mandateOrNonMandate}"
		  				disabled="true" style="width:10%;">
            			<html:option value="">Select</html:option>
            			<html:option value="Y">Mandatory</html:option>
            			<html:option value="N">Non Mandatory</html:option>
          			</html:select>
		  		</td>
<td >
		          	<html:select property="vAdditionalDocType" styleClass="" disabled="true" styleId="vAdditionalDocType${i}" value="${subdocuments.vDocumentType}" onchange="docTypeChoiceAdd(${i});refreshChildId(${i }); ">
		          <html:option value="NA">NA</html:option>
            <html:option value="OTC">OTC</html:option>
            <html:option value="PDD">PDD</html:option>
            <html:option value="AW">As And When</html:option><!-- Virender -->
		          	</html:select>
         		</td>          		<td>         
         			<html:select property="additionalDocStatus" styleClass="" styleId="additionalDocStatus${i }" value="${subdocuments.addnDocStatus}" 
         				onchange="OTCPDDMarkingAdd(${i });makeChoiceAdditional(${i+1});" style="width:10%;" disabled="true">
            			<html:option value="P">Pending</html:option>
            			<html:option value="R">Received</html:option>
            			<html:option value="D">Deferred</html:option>
            			<html:option value="W">Waived</html:option>
            			<html:option value="L">Release</html:option>  <!-- Virender -->
            			<html:option value="N">NA</html:option><!-- Virender -->
          			</html:select>
          		</td>  
	           	<td  id="addRec${i }">
	               <logic:equal name="subdocuments" property="addnDocStatus" value="D">
		              <input type="text" name="additionalReceivedDate" class="text6" disabled="disabled"  value="" />
		            </logic:equal>
		            <logic:equal name="subdocuments" property="addnDocStatus" value="R">
		            	 <html:text property="additionalReceivedDate" styleClass="text6"  disabled="true" value="${subdocuments.addnReceivedDate}" />
		            </logic:equal>
		            <logic:equal name="subdocuments" property="addnDocStatus" value="P">
		              <input type="text" name="additionalReceivedDate" class="text6" disabled="disabled"  value="" />
		            </logic:equal>
		            <logic:equal name="subdocuments" property="addnDocStatus" value="W">
		              <input type="text" name="additionalReceivedDate" class="text6" disabled="disabled"  value="" />
		            </logic:equal>
	          	 </td>
            	 <td id="addDef${i }">
              	 	<logic:equal name="subdocuments" property="addnDocStatus" value="R">
                   		<input type="text" name="additionalDeferredDate" disabled="disabled" class="text6" />
               		</logic:equal>
               		<logic:equal name="subdocuments" property="addnDocStatus" value="D">
                   		<html:text property="additionalDeferredDate"   styleClass="text6" disabled="true" value="${subdocuments.addnDeferredDate}" maxlength="10" onchange="return checkDate('additionalDeferredDate${i}');"/>
               		</logic:equal>
                	<logic:equal name="subdocuments" property="addnDocStatus" value="P">
		              	<input type="text" name="additionalDeferredDate" class="text6" disabled="disabled"  value="" />
		         	</logic:equal>
		         	<logic:equal name="subdocuments" property="addnDocStatus" value="W">
		             	<input type="text" name="additionalDeferredDate" class="text6" disabled="disabled"  value="" />
		         	</logic:equal>
                </td>
               	<td id="addExp${i}">
                     <html:text property="additionalExpiryDate" styleClass="text6" value="${subdocuments.addnExpiryDate}"  maxlength="10"  onchange="return checkDate('additionalExpiryDate${i}');"
                     	disabled="true"/>
                </td>
             	<td>
             		<html:text property="additionalRemarks" styleClass="text1" styleId="additionalRemarks${i }" value="${subdocuments.addnRemarks}"  maxlength="100" disabled="true"/>
             	</td>
          	</tr>
       </logic:iterate>
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/CaldocumentRelated.js"></script>
	</logic:present>
	
	
        </table>
        </td>
        </tr>
        </table>
        </fieldset>
<!-- Changes By Amit Ends -->


<logic:present name="sms">
<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.documentSuccess" />');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert('<bean:message key="lbl.canForward" />');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='NF')
	{
		alert('<bean:message key="lbl.cantForwardSucc" />');
	}
		else if('<%=request.getAttribute("sms").toString()%>'=='PROCERROR')
	{
		alert("DOCUMENT_COLLECTION_MAKER---UNKNOWN EXCEPTION IN SQL EXECUTION, CONTACT SYSTEM ADMINISTRATOR...");
	}			
	else
	{
		alert('<bean:message key="lbl.documentError" />');
	}	
</script>
</logic:present> 
  </html:form>
  </logic:present>

</div>
</div>

<!-- Changes by Amit Starts -->
<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
<script>
	setFramevalues("DocumentForm");
</script>

<logic:present name="message">
<script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='CANNOTREALEASED')
	{
		alert('<bean:message key="lbl.CANNOTREALEASED" />');
	}
	
	if('<%=request.getAttribute("message").toString()%>'=='CANNOT-P,W,D')
	{
		alert('<bean:message key="lbl.CANNOT-P,W,D" />');
	}
	
	if('<%=request.getAttribute("message").toString()%>'=='ONLY-P,L,D,R')
	{
		alert('<bean:message key="lbl.ONLY-P,L,D,R" />');
	}
	if('<%=request.getAttribute("message").toString()%>'=='CANNOT-P,L')
	{
		alert('<bean:message key="lbl.CANNOT-P,L" />');
	}
	
	if('<%=request.getAttribute("message").toString()%>'=='ONLYRECIEVED')
	{
		alert('<bean:message key="lbl.ONLYRECIEVED" />');
	}
</script>
</logic:present>
<!-- Changes by Amit Ends -->

</body>
</html>
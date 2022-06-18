<%--
      Author Name-      Prashant Kumar
      Date of creation -27/04/2011
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
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customerDetail.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/documentRelated.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
     <style type="text/css">

input.readonly {
	background:#ebebe4;
	border:solid 1px #7f9db9;
	width:75px;
	}

</style>
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

<!-- Changes By Amit Starts -->


	</head>
	<body onclick="parent_disable();" oncontextmenu="return false" onload="enableAnchor();checkChanges('DocumentForm');">
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" id="bpId" name="bpId" value="${sessionScope.dealId }"/>
	<input type="hidden" id="bpType" name="bpType" value="DC"/>
	<input type="hidden" id="loanbpId" name="loanbpId" value="${sessionScope.loanId }"/>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
		<logic:present name="loanInitDocs">
<fieldset>

<table cellspacing=0 cellpadding=0 width="100%" border=0>
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
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>	
	</logic:present>
    <logic:notPresent name="loanInitDocs">
   <fieldset>
  <table cellspacing="0" cellpadding="0" width="100%" border="0">
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
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
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>

</fieldset>
</logic:notPresent>

 
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
          <td ><b><bean:message key="lbl.DOC-TYPE" /></b></td>
          <td ><b><bean:message key="lbl.status" /></b></td>
          <td><strong><bean:message key="lbl.receivedDate" /></strong></td>
          <td><strong><bean:message key="lbl.deferredTillDate" /></strong></td>
          <td ><strong><bean:message key="lbl.expiryDate" /></strong></td>
          <td><strong><bean:message key="lbl.remark" /></strong></td>
           <td ><strong><bean:message key="lbl.uploadPartner" /></strong></td>
          <td ><strong><bean:message key="lbl.download" /></strong></td>
          </tr>
     <logic:present name="documents">
           <%
             //   int k=0;
           		           		
           %>
        
		<input type="hidden" name="documentValue" id="documentVal" value="${requestScope.calDoc}"  />
		<logic:iterate id="subdocuments" name="documents" indexId="i" >
			<input type="hidden" name="documentVal" id="documentVal${i }" value="${i }"  />
		 		
		  <html:hidden property="documentId" styleId="documentId" value="${subdocuments.docId}" />
        <tr class="white1">
		 <td >${subdocuments.applName}</td>
         
           <td>
              <input type="hidden" name="docFlag" id="docFlag${i }" value="${subdocuments.docChildFlag}" />
          	 <logic:equal name="subdocuments" property="docChildFlag" value="Y">
	          <a href="#" onclick="makeChild1('${i}','${subdocuments.realDocId}','VC','${subdocuments.entityId}');">	${subdocuments.docDesc}<input type="hidden" name="docName" id="docName${i }" value="${subdocuments.docDesc}" /></a>
	          </logic:equal>
	           <logic:notEqual name="subdocuments" property="docChildFlag" value="Y">
	          	${subdocuments.docDesc}<input type="hidden" name="docName" id="docName${i }" value="${subdocuments.docDesc}" />
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
          <html:select property="vDocType" styleClass="" disabled="true"  styleId="vDocType${i}" value="${subdocuments.vDocumentType}" onchange="docTypeChoice(${i});refreshChildId(${i });">
            <html:option value="NA">NA</html:option>
            <html:option value="OTC">OTC</html:option>
            <html:option value="PDD">PDD</html:option>
            <html:option value="AW">As And When</html:option><!-- Virender -->
          </html:select>
         </td>
     <!-- Virender code end -->          <td >
           <% 
          // ++k;
             
           %>
           
          <% //request.setAttribute("ok",k); %>
         
         <html:select property="status" styleClass="" styleId="status${i }" value="${subdocuments.docStatus}" disabled="true" onchange="makeChoice(${i });">
<!--           <html:option value="">--Select--</html:option>-->
       					  <html:option value="N">NA</html:option>
          				  <html:option value="R">Received</html:option>
            			  <%-- <html:option value="O">OTC</html:option> --%><!-- Rohit Changes Starst -->
            			  <html:option value="D">Deferred</html:option>
            			  <html:option value="P">Pending</html:option>
            			  <html:option value="W">Waived</html:option>
          </html:select></td>
     
   <html:hidden property="childId" styleId="childId${i }" value="${subdocuments.docChildId}" />
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
                <logic:notEqual name="subdocuments" property="docStatus" value="O">
                   <html:text property="deferedDate" disabled="true"  styleClass="text6"  value="${subdocuments.deferDate}" maxlength="10" />
               </logic:notEqual>
               </logic:notEqual>
                
               <logic:equal name="subdocuments" property="docStatus" value="O">
                   <input type="text" name="deferedDate" readonly="readonly" class="text6" disabled="disabled"/>
               </logic:equal>
              
                  
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
               <td align="center" id="upl${i}"><a>NA</a></td>
               </logic:equal>
               <logic:notEqual name="subdocuments" property="docChildFlag" value="Y">
               <td align="center" id="upl${i}"><a>NA</td>
               </logic:notEqual>
               
              <td align="center"  id="dwnl${i}"><a href="#" onclick="downloadDocuments('${subdocuments.realDocId}','GUARANTOR');">Download</a></td>
          </tr>
       </logic:iterate>
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/Caldocument.js"></script>
	</logic:present> 

        </table>    </td>
  </tr>
</table>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
	<tr>
  
<!-- <td><html:button property="button" value="Save" styleClass="topformbutton2" onclick="return saveDocument('AD');"/>-->


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
          <td ><b><bean:message key="lbl.DOC-TYPE" /></b></td>
          <td ><b><bean:message key="lbl.status" /></b></td>
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
		          	<html:select property="vDocType" styleClass="" disabled="true" styleId="vDocType${i}" value="${subdocuments.vDocumentType}" onchange="docTypeChoice(${i});refreshChildId(${i }); ">
		            <html:option value="NA">NA</html:option>
            <html:option value="OTC">OTC</html:option>
            <html:option value="PDD">PDD</html:option>
            <html:option value="AW">As And When</html:option><!-- Virender -->
		          	</html:select>
         		</td>
		  		
		  		
          		<td>        
         			<html:select property="additionalDocStatus" styleClass="" styleId="additionalDocStatus${i }" value="${subdocuments.addnDocStatus}" 
         				onchange="makeChoiceAdditional(${i+1});" style="width:10%;" disabled="true">
 						  <html:option value="N">NA</html:option>
          				  <html:option value="R">Received</html:option>
            			  <%-- <html:option value="O">OTC</html:option> --%><!-- Rohit Changes Starst -->
            			  <html:option value="D">Deferred</html:option>
            			  <html:option value="P">Pending</html:option>
            			  <html:option value="W">Waived</html:option>
          			</html:select>
          		</td>  
	           	<td  id="addRec${i }">
	               <logic:equal name="subdocuments" property="addnDocStatus" value="D">
		              <input type="text" name="additionalReceivedDate" class="text6" disabled="disabled"  value="" />
		            </logic:equal>
		            <logic:equal name="subdocuments" property="addnDocStatus" value="R">
		            	 <html:text property="additionalReceivedDate" styleClass="text6"  disabled="true" value="${subdocuments.addnReceivedDate}" />
		            </logic:equal>
		             <logic:equal name="subdocuments" property="addnDocStatus" value="O">
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
               		<logic:equal name="subdocuments" property="addnDocStatus" value="O">
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/CaldocumentRelated.js"></script>
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
	else
	{
		alert('<bean:message key="lbl.documentError" />');
	}	
</script>
</logic:present>  
  </html:form>

</div>



</div>
<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
<script>
	setFramevalues("DocumentForm");
</script>
</body>
</html:html>

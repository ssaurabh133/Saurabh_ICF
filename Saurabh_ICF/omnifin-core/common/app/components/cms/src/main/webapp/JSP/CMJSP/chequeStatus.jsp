<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Iterator"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>
		<meta name="author" content="" />
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	   
		 
		 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	      <!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
 
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	 	
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
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
			
			function openPayInSlipLov(){
				var payInSlipUploadDate=document.getElementById("payInSlipUploadDate").value;
				var loanAccNo=document.getElementById("lbxLoanNoHID").value;
				var newdate = payInSlipUploadDate.split("-").reverse().join("-");
				document.getElementById("processingImage").style.display = '';
				$.ajax({
					url : 'commondeal.do?method=setPayInSlipDetails',
					datatype : 'json',
					async : false,
					type: "POST",
					data : {
					'transactionType':document.getElementById("txnType").value,
					'receiptSource': document.getElementById("receiptSource").value,
					'payInSlipUploadDate': newdate,
					'loanAccNo': loanAccNo
					} ,
					success : function(resp) {
						console.log(resp);
						var jsonData = $.parseJSON(resp);
						var OPERATION_STATUS=jsonData.OPERATION_STATUS;
						document.getElementById("processingImage").style.display = 'none';
						//alert(OPERATION_STATUS);
						if(OPERATION_STATUS=='1'){
							openLOVCommon(20144,'sourcingForm','payInSlipNo','','', '','','','payInSlipUpdBy','payInSlipUpdDate');
						}else{
							return false;
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						document.getElementById("processingImage").style.display = 'none';
						alert('Some error occurred.')
					}
				});
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
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('sourcingForm').loanButton.focus();">
<input type="hidden" name="mobileReceiptValue" id="mobileReceiptValue" value="${sessionScope.mobileReceiptValue}" />
    <logic:equal name="functionId" value="4000656">
    
    <%-- Update cheque status --%>
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
	
	<html:form action="/chequeStatusAction" method="post" styleId="sourcingForm" >
	<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
	<input type="hidden" id="loanRecStatus" name="loanRecStatus" value="${requestScope.loanRecStatus}"/>
	
    <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
    
    <logic:present name="screenForLoanNumber">
    	<fieldset>

	<legend><bean:message key="lbl.chequeStatusTracking"/></legend>   
	    
 
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			
		<tr>
			<td><bean:message key="lbl.txnType"/></td>
			<td>
				<html:select property="txnType" styleId="txnType" styleClass="text" onchange="showHideImd();" >	            
			        <html:option value="LIM" >LOAN</html:option>
			        <html:option value="DC">DEAL</html:option>
   				</html:select> 
			</td>
		</tr>		
		 <tr>		 		
		   <td><bean:message key="lbl.loanNumber"/></td>
		   <td>
				<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100"  readonly="true"   tabindex="-1"/>   
			    <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(268,'sourcingForm','loanAccNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
			    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
            </td>
           
           <td><bean:message key="lbl.customerName"/></td>
	<td>
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="100"  readonly="true"  tabindex="-1"/> 
	</td>
	
         </tr>
         
         <tr >
	
		   <td>	<bean:message key ="lbl.businessPartnerType"></bean:message> </td>
			<td>	
		 <html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"   readonly="true" tabindex="-1"/>
    	 <html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(201,'sourcingForm','businessPartnerType','loanAccNo','lbxBPTypeHID', 'lbxLoanNoHID','Select Loan Account No.','','lbxBPTypeHID','lbxBPNID','businessPartnerName')" value=" " styleClass="lovbutton"></html:button>	
	     <html:hidden  property="lbxBPTypeHID" styleId="lbxBPTypeHID" />
		  
		     </td>  
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>	
			 <html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  readonly="true" tabindex="-1"/>
     	     <html:hidden  property="lbxBPNID" styleId="lbxBPNID" />
		     <input type="hidden"  name="hcommon" id="hcommon" />
		     </td>
	 </tr>
         
         
         
         <tr>  
           <td><bean:message key="lbl.instrumentType"/></td>
	       <td>
	<html:select property="instrumentType" styleId="instrumentType" styleClass="text"   >
	            
		        <html:option value="P" >Payment</html:option>
		        <html:option value="R">Receipt</html:option>
   </html:select> 
          </td>
           
	       <td><bean:message key="lbl.pdc.ecs"/></td>
	       <td>
	<html:select property="pdcFlag" styleId="pdcFlag" styleClass="text" >
	
		        <html:option value="Y">Yes</html:option>
		        <html:option value="N">No</html:option>
   </html:select> 
          </td>
		</tr>
		
	    <tr>
	     <td><bean:message key="lbl.instrumentNo"/></td>
	    <td nowrap="nowrap" ><label>
	      <html:text styleClass="text" property="instrumentNo" styleId="instrumentNo"  maxlength="20"    />
	    </label></td>
		
	<td><bean:message key="lbl.status"/></td>
		  <td>
		  <div id="arun"  >
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
					<html:option value="C">Sent To Customer</html:option>
					<html:option value="S">Stop Payment</html:option>
					<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					<html:option value="X">Cancelled</html:option>
				               
             </html:select>      
		  </div>
		  <div id="manas" style="display: none">
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
				 	<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					 
               
             </html:select>      
		  </div>
		  
      
              </td>
  </tr>
  <!--Mobile pay in slip     receiptSource, payInSlipId-->
	<logic:present name="mobileReceiptValue">
		<logic:equal name="mobileReceiptValue" value="Y">
			<tr>
				<td><bean:message key="lbl.receiptSource"/></td>
				<td>
					<div id="" >
						<html:select property="receiptSource" styleId="receiptSource" styleClass="text">
							<html:option value="A" >ALL</html:option>
							<html:option value="WEB">Web</html:option>
							<html:option value="MOB">Mobile</html:option>
						</html:select>
					</div>
				</td>
			</tr>
			<tr>
				<td><bean:message key="lbl.payInSlipUploadDate"/></td>
				<td>
					<%--  <html:text styleClass="text" readonly="readonly" property="payInSlipUploadDate" styleId="payInSlipUploadDate" onchange="checkDate('payInSlipUploadDate');"/> --%>
					<input type="text"  readonly="readonly" class="text" id="payInSlipUploadDate" name="payInSlipUploadDate" value="" onchange="checkDate('payInSlipUploadDate');"/>
				</td>
				<td><bean:message key="lbl.payInSlipID"/></td>
				<td>
					<html:hidden styleClass="text" property="payInSlipId" styleId="payInSlipId"/>  
					<input type="text" name="payInSlipNo" id="payInSlipNo" class="text" readonly="readonly"/>
					<html:button property="loanButton" styleId="loanButton" onclick="openPayInSlipLov();" value=" " styleClass="lovbutton"></html:button>
					<input type="hidden" name="payInSlipUpdBy" id="payInSlipUpdBy" class="text" readonly="readonly"/>
					<input type="hidden" name="payInSlipUpdDate" id="payInSlipUpdDate" class="text" readonly="readonly"/>
					<html:hidden property="lbxPayInSlipHID" styleId="lbxPayInSlipHID" />
					<a href='#' onClick="openMobilePayInSlipPhotos(payInSlipId)"><img style=\"cursor:pointer\" src="<%=request.getContextPath()%>/images/theme1/invoiceDownload1.png" width="18" height="18" ></a>
				</td>
			</tr>
		</logic:equal>
	</logic:present>
<!--Mobile pay in slip-->	 
		 <tr>
		   <td><bean:message key="lbl.paymentMode"/></td>    
			<td id="P">
	<html:select property="paymentMode" styleId="paymentMode" styleClass="text" >
	
					<html:option value="Q">Cheque</html:option>
					<html:option value="C">Cash</html:option>
					<html:option value="D">Draft</html:option>
					<html:option value="H">ACH</html:option>
	
					<html:option value="R">RTGS</html:option>
					<html:option value="N">NEFT</html:option>
					<html:option value="DIR">Direct Debit</html:option>
					<html:option value="E">ECS</html:option>
		 			<html:option value="S">ADJUSTMENT</html:option>
		       
 
             </html:select>      
              </td>
              
              <td><bean:message key="lbl.noOfRecords"/></td>    
			<td>
	          <html:select property="noOfRecords" styleId="noOfRecords" styleClass="text" >
					
					<html:option value="10">10</html:option>
					<html:option value="20">20</html:option>
					<html:option value="30">30</html:option>
             </html:select>      
              </td>
		 </tr>
		  <tr>

			
	        <td><bean:message key="lbl.instrumentDate"/></td>
	        <td>  
			    <html:text styleClass="text" property="instrumentDate" styleId="instrumentDate"  maxlength="20"   onchange="checkDate('instrumentDate');"  />
			</td>
			
			<td><bean:message key="lbl.reciptDate"/></td>
	        <td>  
			    <html:text styleClass="text" property="receivedDate" styleId="receivedDate"  maxlength="20"   onchange="checkDate('receivedDate');"  />
			</td>
		</tr>
		<tr>
			<td><bean:message key="lbl.Branch"/></td>
			<td>		
					<html:text property="defaultBranch" styleClass="text" styleId="defaultBranch" maxlength="100" readonly="true" value="${defaultBranch}"/>
					<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="${lbxBranchId}" />
					<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(156,'chequeStatusAction','lbxBranchId','','', '','','','defaultBranch')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
		</tr>
		 <tr>
		
	        <td>  
			    <button type="button" name="button"   title="Alt+S" accesskey="S" class="topformbutton2" onclick="return searchChequeStatus();"><bean:message key="button.search"/></button>
			</td>
		</tr>
		 	  
		  
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	
    	
    </logic:present>
    
    <!-- ---------------------------JSP For Deal------------------------------------ -->
    <logic:present name="screenForDealNumber">
    		<fieldset>

	<legend><bean:message key="lbl.chequeStatusTracking"/></legend>   
	    
 
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
	
		<tr>
			<td><bean:message key="lbl.txnType"/></td>
			<td>
				<html:select property="txnType" styleId="txnType" styleClass="text" onchange="showHideImd();" >	            
			       <html:option value="DC">DEAL</html:option>
			       <html:option value="LIM" >LOAN</html:option>
   				</html:select> 
			</td>
		</tr>
		
			
		
		 <tr>	
		 		
		   
		   <td><bean:message key="lbl.dealNo"/></td>
		   <td>
               <html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="100"  readonly="true"  tabindex="-1"/>   
               <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(506,'sourcingForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
               <html:hidden property="lbxDealNo" styleId="lbxDealNo" />
           </td>
           
           <td><bean:message key="lbl.customerName"/></td>
	<td>
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="100"  readonly="true"  tabindex="-1"/> 
	</td>
	
         </tr>

         <tr>  
           <td><bean:message key="lbl.instrumentType"/></td>
	       <td>
	<html:select property="instrumentType" styleId="instrumentType" styleClass="text"   >
	            
		        <html:option value="P" >Payment</html:option>
		        <html:option value="R">Receipt</html:option>
   </html:select> 
          </td>
           
	       <td><bean:message key="lbl.pdc.ecs"/></td>
	       <td>
	<html:select property="pdcFlag" styleId="pdcFlag" styleClass="text" >
	
		        <html:option value="Y">Yes</html:option>
		        <html:option value="N">No</html:option>
   </html:select> 
          </td>
		</tr>
		
	    <tr>
	     <td><bean:message key="lbl.instrumentNo"/></td>
	    <td nowrap="nowrap" ><label>
	      <html:text styleClass="text" property="instrumentNo" styleId="instrumentNo"  maxlength="20"    />
	    </label></td>
		
	<td><bean:message key="lbl.status"/></td>
		  <td>
		  <div id="arun"  >
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
					<html:option value="C">Sent To Customer</html:option>
					<html:option value="S">Stop Payment</html:option>
					<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					<html:option value="X">Cancelled</html:option>
					 
					 
               
             </html:select>      
		  </div>
		  <div id="manas" style="display: none">
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
				 	<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					 
               
             </html:select>      
		  </div>
		  
      
              </td>
  </tr>
		 <tr>
		   <td><bean:message key="lbl.paymentMode"/></td>    
			<td id="P">
	<html:select property="paymentMode" styleId="paymentMode" styleClass="text" >
	
					<html:option value="Q">Cheque</html:option>
					<html:option value="C">Cash</html:option>
					<html:option value="D">Draft</html:option>
					<html:option value="H">ACH</html:option>
	
					<html:option value="R">RTGS</html:option>
					<html:option value="N">NEFT</html:option>
					<html:option value="DIR">Direct Debit</html:option>
					<html:option value="E">ECS</html:option>
		 			<html:option value="S">ADJUSTMENT</html:option>
		       
 
             </html:select>      
              </td>
              
              <td><bean:message key="lbl.noOfRecords"/></td>    
			<td>
	        <html:select property="noOfRecords" styleId="noOfRecords" styleClass="text" >
					
					<html:option value="10">10</html:option>
					<html:option value="20">20</html:option>
					<html:option value="30">30</html:option>
     
 
             </html:select>      
              </td>
		 </tr>
		  <tr>

			
	        <td><bean:message key="lbl.instrumentDate"/></td>
	        <td>  
			    <html:text styleClass="text" property="instrumentDate" styleId="instrumentDate"  maxlength="20"   onchange="checkDate('instrumentDate');"  />
			</td>
			
			<td><bean:message key="lbl.reciptDate"/></td>
	        <td>  
			    <html:text styleClass="text" property="receivedDate" styleId="receivedDate"  maxlength="20"   onchange="checkDate('receivedDate');"  />
			</td>
		</tr>
			<tr>
				<td><bean:message key="lbl.Branch"/></td>
			<td>		
					<html:text property="defaultBranch" styleClass="text" styleId="defaultBranch" maxlength="100" readonly="true" value="${defaultBranch}"/>
					<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="${lbxBranchId}" />
					<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(156,'chequeStatusAction','lbxBranchId','','', '','','','defaultBranch')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
		</tr>
		
		 <tr>
		
	        <td>  
			    <button type="button" name="button"   title="Alt+S" accesskey="S" class="topformbutton2" onclick="return searchChequeStatus();"><bean:message key="button.search"/></button>
			</td>
		</tr>
		 	  
		  
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	
    		
    </logic:present>
    	
	<logic:present name="dss">
	<script type="text/javascript">
	if('<%=request.getAttribute("dss").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusAction.do?method=searchChequesByPayment";
	document.getElementById('sourcingForm').submit();
	
	}else if('<%=request.getAttribute("dss").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do?method=searchChequesByPayment";
	document.getElementById('sourcingForm').submit();
	} 
     </script>
	</logic:present>
	
	<logic:present name="DSS">
	<script type="text/javascript">
	if('<%=request.getAttribute("DSS").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	document.getElementById('sourcingForm').submit();
	window.close();
	
	}else if('<%=request.getAttribute("DSS").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	document.getElementById('sourcingForm').submit();
	window.close();
	} 
     </script>
	</logic:present>
	<logic:present name="procresult">
	<script type="text/javascript">
	if('<%=request.getAttribute("procresult").toString()%>'!='S'){
	alert('<%=request.getAttribute("procresult").toString()%>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	document.getElementById('sourcingForm').submit();

	}
	</script>
	</logic:present>
	<logic:present name="recordExist">
	<script type="text/javascript">
		alert("Selected record is already processed by some other user,Please refresh the page.");
	</script>
	</logic:present>
	
	<logic:present name="OTC">
	<script type="text/javascript">
		alert("Some documents are marked as OTC, Kindly receive document first!!!! ");
	</script>
	</logic:present>
	
</html:form>
	</div>
	
  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">  
<logic:present name="list">
<logic:notEmpty name="list">
   <logic:iterate name="list" id="sublist">
	  <html:hidden property="hideDate" styleId="hideDate" value="${sublist.hideDate}"/>
	 
  </logic:iterate>
<tr>
    <td>  
    	<display:table  id="list" class="dataTable"  name="list" style="width: 100%" pagesize="${list[0].noOfRecords}" partialList="true" size="${list[0].totalRecordSize}" cellspacing="1" requestURI="/chequeStatusAction.do?method=searchChequesByPayment">
    		<display:setProperty name="paging.banner.placement"  value="bottom"/>  
    		<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    		<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>   
    		<display:column property="checkBox" title="<input type='checkbox' name='chkd' id='allchkd' onclick='allChecked();' />"  />
    		<display:column property="loanAccNo" titleKey="lbl.LoanNo"  sortable="true"  />
    		<display:column property="businessPartnerDesc" titleKey="lbl.BPName"  sortable="true"  />
			<display:column property="businessPartnerType" titleKey="lbl.bptype"  sortable="true"  />
			<display:column property="instrumentType" titleKey="lbl.instrumentType"  sortable="true"  />
			<display:column property="instrumentNo" titleKey="lbl.instrumentNo"  sortable="true"  />
			<display:column property="date" titleKey="lbl.instrumentDate"  sortable="true"  />
			<display:column property="reciptDate" titleKey="lbl.reciptDate"  sortable="true"  />
			<display:column property="valueDate" titleKey="lbl.valueDate"  sortable="true"  />	
			<display:column property="instrumentAmount" titleKey="lbl.instrumentAmount"  sortable="true"  />
			<display:column property="bank" titleKey="lbl.bank"  sortable="true"  />
			<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />	
			<display:column property="bankAccount" titleKey="lbl.acno"  sortable="true"  />
			<display:column property="reciptNo" titleKey="lbl.reciptNo"  sortable="true"  />
			<display:column property="defaultBranch" titleKey="lbl.defaultBranchId" sortable="true"  />
			<display:column property="depositBank" titleKey="lbl.depositBankName"  sortable="true"  />
	        <display:column property="depositBankBranch" titleKey="lbl.depositBranchName"  sortable="true"  />
	        <display:column property="depositBankAccount" titleKey="lbl.depositBnkAcc"  sortable="true"  />
			<display:column property="statusName" titleKey="lbl.status"  sortable="true"  />
			<display:column property="reasonLov" titleKey="lbl.reasons"  sortable="true" style="width: 17%" />	
			<display:column property="reasonRemarks" titleKey="lbl.reasonRemark"  sortable="true"  />
   		</display:table>   
  	</td>
</tr>
<logic:present name="ButtonP">
	<logic:present name="Approve">
		<tr>
      	<td colspan="4">&nbsp; 
      		<button type="button" name="sentToCustomer"   id="sentToCustomer" class="topformbutton4" onclick="return sentToCustomerChequeStatus();" title="Alt+T" accesskey="T" ><bean:message key="button.sentocustomer" /></button>     
	      	<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>       
     	</td>
    	</tr>	
	</logic:present>
	<logic:present name="SendToCustomer">	
		<tr>
      	<td colspan="4">&nbsp;
      		<button type="button" name="realize"   id="realize" class="topformbutton2" onclick="return realizeChequeStatus();" title="Alt+R" accesskey="R" ><bean:message key="button.realize" /></button>
      		<button type="button" name="stopPayment"   id="stopPayment" class="topformbutton3" onclick="return stopPaymentChequeStatus();" title="Alt+S" accesskey="S" ><bean:message key="button.stopayment" /></button>      
	      	<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>          
     	</td>
    	</tr>
	</logic:present>
	<logic:present name="Realise">	
		<tr>
      	<td colspan="4">&nbsp;      
	      	<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>          
     	</td>
    	</tr>
	</logic:present>
	<logic:present name="StopPayment">
		<tr>
      	<td colspan="4">&nbsp;      
	      	<button type="button" name="sentToCustomer"   id="sentToCustomer" class="topformbutton4" onclick="return sentToCustomerChequeStatus();" title="Alt+T" accesskey="T" ><bean:message key="button.sentocustomer" /></button>
	      	<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>
	   </td>
    	</tr>	
	</logic:present>
</logic:present>     
<logic:present name="ButtonR">
	<logic:present name="Approve">
		<tr> 
      		<td colspan="4">&nbsp;      
       			<button type="button" name="deposit"   id="deposit" class="topformbutton2" onclick="return depositChequeStatus();" title="Alt+D" accesskey="D" ><bean:message key="button.deposit" /></button>
       			<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelReceiptChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>
       		</td>
    	</tr>
	</logic:present>
	<logic:present name="Deposit">
		<tr> 
      		<td colspan="4">&nbsp;    
      			<button type="button" name="realize"   id="realize" class="topformbutton2" onclick="return realizeReceiptChequeStatus();" title="Alt+R" accesskey="R" ><bean:message key="button.realize" /></button>  
       			<button type="button" name="bounce"   id="bounce" class="topformbutton2" onclick="return bounceChequeStatus();" title="Alt+B" accesskey="B" ><bean:message key="button.bounce" /></button>
       			<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelReceiptChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>       
     		</td>
    	</tr>
	</logic:present>
	<logic:present name="Bounce">
		<tr> 
      		<td colspan="4">&nbsp;  
      			<button type="button" name="deposit"   id="deposit" class="topformbutton2" onclick="return depositChequeStatus();" title="Alt+D" accesskey="D" ><bean:message key="button.deposit" /></button>    
       			<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelReceiptChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>       
     		</td>
    	</tr>
	</logic:present>
	<logic:present name="Realise">
		<tr> 
      		<td colspan="4">&nbsp;      
       			<button type="button" name="bounce"   id="bounce" class="topformbutton2" onclick="return bounceChequeStatus();" title="Alt+B" accesskey="B" ><bean:message key="button.bounce" /></button>
       			<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelReceiptChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>       
     		</td>
    	</tr>
	</logic:present>
</logic:present>
</logic:notEmpty>
<logic:empty name="list">
<tr><td><bean:message key="lbl.noDataFound" /></td></tr>
</logic:empty>
</logic:present>
</table>    
<logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("Data not found");	
	}
	
	</script>
</logic:present>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	  	
	</logic:equal>
	<logic:equal name="functionId" value="4000659">
	
	    <%--Branch Update cheque status --%>
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
	
	<html:form action="/chequeStatusAction" method="post" styleId="sourcingForm" >
	<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
	<input type="hidden" id="loanRecStatus" name="loanRecStatus" value="${requestScope.loanRecStatus}"/>
	
    <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
    
    <logic:present name="screenForLoanNumber">
    	<fieldset>

	<legend><bean:message key="lbl.chequeStatusTrackingBranch"/></legend>   
	    
 
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		
			
		
		<tr>
			<td><bean:message key="lbl.txnType"/></td>
			<td>
				<html:select property="txnType" styleId="txnType" styleClass="text" onchange="showHideImd();" >	            
			        <html:option value="LIM" >LOAN</html:option>
			        <html:option value="DC">DEAL</html:option>
   				</html:select> 
			</td>
		</tr>		
		 <tr>	
		 		
		   <td><bean:message key="lbl.loanNumber"/></td>
		   
		   <td>
				<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100"  readonly="true"   tabindex="-1"/>   
			    <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(268,'sourcingForm','loanAccNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
			    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
            </td>
           
           <td><bean:message key="lbl.customerName"/></td>
	<td>
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="100"  readonly="true"  tabindex="-1"/> 
	</td>
	
         </tr>
         
         <tr >
	
		   <td>	<bean:message key ="lbl.businessPartnerType"></bean:message> </td>
			<td>	
		 <html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"   readonly="true" tabindex="-1"/>
    	 <html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(201,'sourcingForm','businessPartnerType','loanAccNo','lbxBPTypeHID', 'lbxLoanNoHID','Select Loan Account No.','','lbxBPTypeHID','lbxBPNID','businessPartnerName')" value=" " styleClass="lovbutton"></html:button>	
	     <html:hidden  property="lbxBPTypeHID" styleId="lbxBPTypeHID" />
		  
		     </td>  
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>	
			 <html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  readonly="true" tabindex="-1"/>
     	     <html:hidden  property="lbxBPNID" styleId="lbxBPNID" />
		     <input type="hidden"  name="hcommon" id="hcommon" />
		     </td>
	 </tr>
                 
         <tr>  
           <td><bean:message key="lbl.instrumentType"/></td>
	       <td>
	<html:select property="instrumentType" styleId="instrumentType" styleClass="text">
	            
		        <html:option value="P" >Payment</html:option>
		        <html:option value="R">Receipt</html:option>
   </html:select> 
          </td>
           
	       <td><bean:message key="lbl.pdc.ecs"/></td>
	       <td>
	<html:select property="pdcFlag" styleId="pdcFlag" styleClass="text" >
	
		        <html:option value="Y">Yes</html:option>
		        <html:option value="N">No</html:option>
   </html:select> 
          </td>
		</tr>
		
	    <tr>
	     <td><bean:message key="lbl.instrumentNo"/></td>
	    <td nowrap="nowrap" ><label>
	      <html:text styleClass="text" property="instrumentNo" styleId="instrumentNo"  maxlength="20"    />
	    </label></td>
		
	<td><bean:message key="lbl.status"/></td>
		  <td>
		  <div id="arun"  >
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
					<html:option value="C">Sent To Customer</html:option>
					<html:option value="S">Stop Payment</html:option>
					<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					<html:option value="X">Cancelled</html:option>
					 
					 
               
             </html:select>      
		  </div>
		  <div id="manas" style="display: none">
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
				 	<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					 
               
             </html:select>      
		  </div>
		  
      
              </td>
  </tr>
  <!--Mobile pay in slip     receiptSource, payInSlipId-->
	<logic:present name="mobileReceiptValue">
		<logic:equal name="mobileReceiptValue" value="Y">
			<tr>
				<td><bean:message key="lbl.receiptSource"/></td>
				<td>
					<div id="" >
						<html:select property="receiptSource" styleId="receiptSource" styleClass="text">
							<html:option value="A" >ALL</html:option>
							<html:option value="WEB">Web</html:option>
							<html:option value="MOB">Mobile</html:option>
						</html:select>
					</div>
				</td>
			</tr>
			<tr>
				<td><bean:message key="lbl.payInSlipUploadDate"/></td>
				<td>
					<%--  <html:text styleClass="text" readonly="readonly" property="payInSlipUploadDate" styleId="payInSlipUploadDate" onchange="checkDate('payInSlipUploadDate');"/> --%>
					<input type="text"  readonly="readonly" class="text" id="payInSlipUploadDate" name="payInSlipUploadDate" value="" onchange="checkDate('payInSlipUploadDate');"/>
				</td>
				<td><bean:message key="lbl.payInSlipID"/></td>
				<td>
					<html:hidden styleClass="text" property="payInSlipId" styleId="payInSlipId"/>  
					<input type="text" name="payInSlipNo" id="payInSlipNo" class="text" readonly="readonly"/>
					<html:button property="loanButton" styleId="loanButton" onclick="openPayInSlipLov();" value=" " styleClass="lovbutton"></html:button>
					<input type="hidden" name="payInSlipUpdBy" id="payInSlipUpdBy" class="text" readonly="readonly"/>
					<input type="hidden" name="payInSlipUpdDate" id="payInSlipUpdDate" class="text" readonly="readonly"/>
					<html:hidden property="lbxPayInSlipHID" styleId="lbxPayInSlipHID" />
					<a href='#' onClick="openMobilePayInSlipPhotos(payInSlipId)"><img style=\"cursor:pointer\" src="<%=request.getContextPath()%>/images/theme1/invoiceDownload1.png" width="18" height="18" ></a>
				</td>
			</tr>
		</logic:equal>
	</logic:present>
<!--Mobile pay in slip-->	 
		 <tr>
		   <td><bean:message key="lbl.paymentMode"/></td>    
			<td id="P">
	<html:select property="paymentMode" styleId="paymentMode" styleClass="text" >
	
					<html:option value="Q">Cheque</html:option>
					<html:option value="C">Cash</html:option>
					<html:option value="D">Draft</html:option>
					<html:option value="H">ACH</html:option>
	
					<html:option value="R">RTGS</html:option>
					<html:option value="N">NEFT</html:option>
					<html:option value="DIR">Direct Debit</html:option>
					<html:option value="E">ECS</html:option>
		 			<html:option value="S">ADJUSTMENT</html:option>
		       
 
             </html:select>      
              </td>
              
              <td><bean:message key="lbl.noOfRecords"/></td>    
			<td>
	          <html:select property="noOfRecords" styleId="noOfRecords" styleClass="text" >
					
					<html:option value="10">10</html:option>
					<html:option value="20">20</html:option>
					<html:option value="30">30</html:option>
             </html:select>      
              </td>
		 </tr>
		  <tr>

			
	        <td><bean:message key="lbl.instrumentDate"/></td>
	        <td>  
			    <html:text styleClass="text" property="instrumentDate" styleId="instrumentDate"  maxlength="20"   onchange="checkDate('instrumentDate');"  />
			</td>
			
			<td><bean:message key="lbl.reciptDate"/></td>
	        <td>  
			    <html:text styleClass="text" property="receivedDate" styleId="receivedDate"  maxlength="20"   onchange="checkDate('receivedDate');"  />
			</td>
		</tr>
			<tr>
				<td><bean:message key="lbl.Branch"/></td>
			<td>		
					<html:text property="defaultBranch" styleClass="text" styleId="defaultBranch" maxlength="100" readonly="true" value="${defaultBranch}"/>
					<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="${lbxBranchId}" />
					<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(156,'chequeStatusAction','lbxBranchId','','', '','','','defaultBranch')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
		</tr>
		
		 <tr>
		
	        <td>  
			    <button type="button" name="button"   title="Alt+S" accesskey="S" class="topformbutton2" onclick="return searchChequeStatus();"><bean:message key="button.search"/></button>
			</td>
		</tr>
		 	  
		  
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	
    	
    </logic:present>
    
    <!-- ---------------------------JSP For Deal------------------------------------ -->
    <logic:present name="screenForDealNumber">
    		<fieldset>

	<legend><bean:message key="lbl.chequeStatusTrackingBranch"/></legend>   
	    
 
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
	
		<tr>
			<td><bean:message key="lbl.txnType"/></td>
			<td>
				<html:select property="txnType" styleId="txnType" styleClass="text" onchange="showHideImd();" >	            
			       <html:option value="DC">DEAL</html:option>
			       <html:option value="LIM" >LOAN</html:option>
   				</html:select> 
			</td>
		</tr>
		
			
		
		 <tr>	
		 		
		   
		   <td><bean:message key="lbl.dealNo"/></td>
		   <td>
               <html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="100"  readonly="true"  tabindex="-1"/>   
               <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(506,'sourcingForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
               <html:hidden property="lbxDealNo" styleId="lbxDealNo" />
           </td>
           
           <td><bean:message key="lbl.customerName"/></td>
	<td>
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="100"  readonly="true"  tabindex="-1"/> 
	</td>
	
         </tr>

         <tr>  
           <td><bean:message key="lbl.instrumentType"/></td>
	       <td>
	<html:select property="instrumentType" styleId="instrumentType" styleClass="text"   >
	            
		        <html:option value="P" >Payment</html:option>
		        <html:option value="R">Receipt</html:option>
   </html:select> 
          </td>
           
	       <td><bean:message key="lbl.pdc.ecs"/></td>
	       <td>
	<html:select property="pdcFlag" styleId="pdcFlag" styleClass="text" >
	
		        <html:option value="Y">Yes</html:option>
		        <html:option value="N">No</html:option>
   </html:select> 
          </td>
		</tr>
		
	    <tr>
	     <td><bean:message key="lbl.instrumentNo"/></td>
	    <td nowrap="nowrap" ><label>
	      <html:text styleClass="text" property="instrumentNo" styleId="instrumentNo"  maxlength="20"    />
	    </label></td>
		
	<td><bean:message key="lbl.status"/></td>
		  <td>
		  <div id="arun"  >
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
					<html:option value="C">Sent To Customer</html:option>
					<html:option value="S">Stop Payment</html:option>
					<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					<html:option value="X">Cancelled</html:option>
					 
               
             </html:select>      
		  </div>
		  <div id="manas" style="display: none">
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
				 	<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					 
               
             </html:select>      
		  </div>
		  
      
              </td>
  </tr>
		 <tr>
		   <td><bean:message key="lbl.paymentMode"/></td>    
			<td id="P">
	<html:select property="paymentMode" styleId="paymentMode" styleClass="text" >
	
					<html:option value="Q">Cheque</html:option>
					<html:option value="C">Cash</html:option>
					<html:option value="D">Draft</html:option>
					<html:option value="H">ACH</html:option>
	
					<html:option value="R">RTGS</html:option>
					<html:option value="N">NEFT</html:option>
					<html:option value="DIR">Direct Debit</html:option>
					<html:option value="E">ECS</html:option>
		 			<html:option value="S">ADJUSTMENT</html:option>
		       
 
             </html:select>      
              </td>
              
              <td><bean:message key="lbl.noOfRecords"/></td>    
			<td>
	        <html:select property="noOfRecords" styleId="noOfRecords" styleClass="text" >
					
					<html:option value="10">10</html:option>
					<html:option value="20">20</html:option>
					<html:option value="30">30</html:option>
     
 
             </html:select>      
              </td>
		 </tr>
		  <tr>

			
	        <td><bean:message key="lbl.instrumentDate"/></td>
	        <td>  
			    <html:text styleClass="text" property="instrumentDate" styleId="instrumentDate"  maxlength="20"   onchange="checkDate('instrumentDate');"  />
			</td>
			
			<td><bean:message key="lbl.reciptDate"/></td>
	        <td>  
			    <html:text styleClass="text" property="receivedDate" styleId="receivedDate"  maxlength="20"   onchange="checkDate('receivedDate');"  />
			</td>
		</tr>
			<tr>
				<td><bean:message key="lbl.Branch"/></td>
			<td>		
					<html:text property="defaultBranch" styleClass="text" styleId="defaultBranch" maxlength="100" readonly="true" value="${defaultBranch}"/>
					<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="${lbxBranchId}" />
					<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(156,'chequeStatusAction','lbxBranchId','','', '','','','defaultBranch')" value=" " styleClass="lovbutton"  ></html:button>
			</td>		
		</tr>		
		 <tr>		
	        <td>  
			    <button type="button" name="button"   title="Alt+S" accesskey="S" class="topformbutton2" onclick="return searchChequeStatus();"><bean:message key="button.search"/></button>
			</td>
		</tr>		 	  
		  
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	
    		
    </logic:present>
    	
	<logic:present name="dss">
	<script type="text/javascript">
	if('<%=request.getAttribute("dss").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusAction.do?method=searchChequesByPayment";
	document.getElementById('sourcingForm').submit();
	
	}else if('<%=request.getAttribute("dss").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do?method=searchChequesByPayment";
	document.getElementById('sourcingForm').submit();
	} 
     </script>
	</logic:present>
	
	<logic:present name="DSS">
	<script type="text/javascript">
	if('<%=request.getAttribute("DSS").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	document.getElementById('sourcingForm').submit();
	window.close();
	
	}else if('<%=request.getAttribute("DSS").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	document.getElementById('sourcingForm').submit();
	window.close();
	} 
     </script>
	</logic:present>
	<logic:present name="procresult">
	<script type="text/javascript">
	if('<%=request.getAttribute("procresult").toString()%>'!='S'){
	alert('<%=request.getAttribute("procresult").toString()%>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	document.getElementById('sourcingForm').submit();

	}
	</script>
	</logic:present>
	<logic:present name="recordExist">
	<script type="text/javascript">
		alert("Selected record is already processed by some other user,Please refresh the page.");
	</script>
	</logic:present>
	<logic:present name="OTC">
	<script type="text/javascript">
		alert("Some documents are marked as OTC, Kindly receive document first!!!! ");
	</script>
	</logic:present>
	
</html:form>
	</div>
	
  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">  
<logic:present name="list">
<logic:notEmpty name="list">
   <logic:iterate name="list" id="sublist">
	  <html:hidden property="hideDate" styleId="hideDate" value="${sublist.hideDate}"/>
	 
  </logic:iterate>
<tr>
    <td>  
    	<display:table  id="list" class="dataTable"  name="list" style="width: 100%" pagesize="${list[0].noOfRecords}" partialList="true" size="${list[0].totalRecordSize}" cellspacing="1" requestURI="/chequeStatusAction.do?method=searchChequesByPayment">
    		<display:setProperty name="paging.banner.placement"  value="bottom"/>  
    		<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    		<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>   
    		<display:column property="checkBox" title="<input type='checkbox' name='chkd' id='allchkd' onclick='allChecked();' />"  />
    		<display:column property="loanAccNo" titleKey="lbl.LoanNo"  sortable="true"  />
    		<display:column property="businessPartnerDesc" titleKey="lbl.BPName"  sortable="true"  />
			<display:column property="businessPartnerType" titleKey="lbl.bptype"  sortable="true"  />
			<display:column property="instrumentType" titleKey="lbl.instrumentType"  sortable="true"  />
			<display:column property="instrumentNo" titleKey="lbl.instrumentNo"  sortable="true"  />
			<display:column property="date" titleKey="lbl.instrumentDate"  sortable="true"  />
			<display:column property="reciptDate" titleKey="lbl.reciptDate"  sortable="true"  />
			<display:column property="valueDate" titleKey="lbl.valueDate"  sortable="true"  />	
			<display:column property="instrumentAmount" titleKey="lbl.instrumentAmount"  sortable="true"  />
			<display:column property="bank" titleKey="lbl.bank"  sortable="true"  />
			<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />	
			<display:column property="bankAccount" titleKey="lbl.acno"  sortable="true"  />
			<display:column property="reciptNo" titleKey="lbl.reciptNo"  sortable="true"  />
			<display:column property="defaultBranch" titleKey="lbl.defaultBranchId" sortable="true"  />
			<display:column property="depositBank" titleKey="lbl.depositBankName"  sortable="true"  />
	        <display:column property="depositBankBranch" titleKey="lbl.depositBranchName"  sortable="true"  />
	        <display:column property="depositBankAccount" titleKey="lbl.depositBnkAcc"  sortable="true"  />
			<display:column property="statusName" titleKey="lbl.status"  sortable="true"  />
			<display:column property="reasonLov" titleKey="lbl.reasons"  sortable="true" style="width: 17%" />	
			<display:column property="reasonRemarks" titleKey="lbl.reasonRemark"  sortable="true"  />
   		</display:table>   
  	</td>
</tr>
<logic:present name="ButtonP">
	<logic:present name="Approve">
		<tr>
      	<td colspan="4">&nbsp; 
      		<button type="button" name="sentToCustomer"   id="sentToCustomer" class="topformbutton4" onclick="return sentToCustomerChequeStatus();" title="Alt+T" accesskey="T" ><bean:message key="button.sentocustomer" /></button>     
	      	<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>       
     	</td>
    	</tr>	
	</logic:present>
	<logic:present name="SendToCustomer">	
		<tr>
      	<td colspan="4">&nbsp;
      		<button type="button" name="realize"   id="realize" class="topformbutton2" onclick="return realizeChequeStatus();" title="Alt+R" accesskey="R" ><bean:message key="button.realize" /></button>
      		<button type="button" name="stopPayment"   id="stopPayment" class="topformbutton3" onclick="return stopPaymentChequeStatus();" title="Alt+S" accesskey="S" ><bean:message key="button.stopayment" /></button>      
	      	          
     	</td>
    	</tr>
	</logic:present>

	<logic:present name="StopPayment">
		<tr>
      	<td colspan="4">&nbsp;      
	      	<button type="button" name="sentToCustomer"   id="sentToCustomer" class="topformbutton4" onclick="return sentToCustomerChequeStatus();" title="Alt+T" accesskey="T" ><bean:message key="button.sentocustomer" /></button>
	      	
	   </td>
    	</tr>	
	</logic:present>
</logic:present>     
<logic:present name="ButtonR">
	<logic:present name="Approve">
		<tr> 
      		<td colspan="4">&nbsp;      
       			<button type="button" name="deposit"   id="deposit" class="topformbutton2" onclick="return depositChequeStatus();" title="Alt+D" accesskey="D" ><bean:message key="button.deposit" /></button>
       			<button type="button" name="cancel"   id="cancel" class="topformbutton2" onclick="return cancelReceiptChequeStatus();" title="Alt+C" accesskey="C" ><bean:message key="button.cancel" /></button>
       		</td>
    	</tr>
	</logic:present>
	<logic:present name="Deposit">
		<tr> 
      		<td colspan="4">&nbsp;    
      			<button type="button" name="realize"   id="realize" class="topformbutton2" onclick="return realizeReceiptChequeStatus();" title="Alt+R" accesskey="R" ><bean:message key="button.realize" /></button>  
       			<button type="button" name="bounce"   id="bounce" class="topformbutton2" onclick="return bounceChequeStatus();" title="Alt+B" accesskey="B" ><bean:message key="button.bounce" /></button>
       			       
     		</td>
    	</tr>
	</logic:present>
	<logic:present name="Bounce">
		<tr> 
      		<td colspan="4">&nbsp;  
      			<button type="button" name="deposit"   id="deposit" class="topformbutton2" onclick="return depositChequeStatus();" title="Alt+D" accesskey="D" ><bean:message key="button.deposit" /></button>    
       			       
     		</td>
    	</tr>
	</logic:present>
	<logic:present name="Realise">
		<tr> 
      		<td colspan="4">&nbsp;      
       			<button type="button" name="bounce"   id="bounce" class="topformbutton2" onclick="return bounceChequeStatus();" title="Alt+B" accesskey="B" ><bean:message key="button.bounce" /></button>
       			       
     		</td>
    	</tr>
	</logic:present>
</logic:present>
</logic:notEmpty>
<logic:empty name="list">
<tr><td><bean:message key="lbl.noDataFound" /></td></tr>
</logic:empty>
</logic:present>
</table>    
<logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("Data not found");	
	}
	
	</script>
</logic:present>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	  	
	</logic:equal>
	
</body>
</html:html>
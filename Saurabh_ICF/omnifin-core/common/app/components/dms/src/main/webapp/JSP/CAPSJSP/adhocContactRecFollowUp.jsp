<!--Author Name : Nazia-->
<!--Date of Creation :1 jul 2013-->
<!--Purpose  : Adhoc Contact Recording Page -->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>

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
	
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/queueCodeScript.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/adhocContactRecordingScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" 
		src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript">
  
   
  function fntab(){
    document.getElementById('contactMode').focus();
    }
	function disableinstrumentNumber()
  {
	  var actionCode=document.getElementById("actionCode").value;
	  if(actionCode=="COLL" & document.getElementById("paymentMode").value=="C")
		{
		document.getElementById("instrumentNo").setAttribute("disabled",true);
		} 
		else
		{
		document.getElementById("instrumentNo").removeAttribute("disabled");
		}
  }
</script>
<script type="text/javascript" >
/*Counter Script Code Start hare*/
var mins
var secs;


function cd() {
 	mins = 1 * m("0"); // change minutes here
 	secs = 0 + s(":0"); // change seconds here (always add an additional second to your total)
	
 	redo();
}

function m(obj) {
 	for(var i = 0; i < obj.length; i++) {
  		if(obj.substring(i, i + 1) == ":")
  		break;
 	}
 	return(obj.substring(0, i));
}

function s(obj) {
 	for(var i = 0; i < obj.length; i++) {
  		if(obj.substring(i, i + 1) == ":")
  		break;
 	}
 	return(obj.substring(i + 1, obj.length));
}

function dis(mins,secs) {
 	var disp;
 	if(mins <= 9) {
  		disp = " 0";
 	} else {
  		disp = " ";
 	}
 	disp += mins + ":";
 	if(secs <= 9) {
  		disp += "0" + secs;
 	} else {
  		disp += secs;
 	}
 	return(disp);
}

function redo() {
 	secs++;
 	
	if(secs == 59){
	secs = 00;
	mins++;
	}
 	document.getElementById('totalTimeTaken').value = dis(mins,secs); // setup additional displays here.
 	
 	cd = setTimeout("redo()",1000);
 	
}

function init() {
  cd();
}
//window.onload = init;
/*Counter Script Code Ends hare*/
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

<body onload="enableAnchor();init();init_fields();">
<div align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <div id="centercolumn">

<div id="revisedcontainer">
 	
 	
<html:form styleId="contactRecFollowUpForm"  method="post"  action="/AdhocContactRecordingDispatchAction" >

<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId}"/>
<html:hidden property="exclationFlag" styleId="exclationFlag" value="${requestScope.exclationFlag}"/>
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="businessDate" id="businessDate" value="${sessionScope.userobject.businessdate}" />
<input type="hidden" name="superReview" id="superReview" value="${requestScope.superReview}"/>
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<fieldset>
<legend><bean:message key="lbl.FollowUpRecord" /></legend>
 <table cellpadding="0" border="0" cellspacing="0" width="100%">
 <tr>
 <td>
  <table cellpadding="1" border="0" cellspacing="1" width="100%">
  <tr>
<%--   <td ><bean:message key="lbl.actionDate" /></td>
  <td><html:text property="actionDate" styleClass="text" styleId="actionDate" readonly="true" tabindex="-1" value="${sessionScope.userobject.businessdate}" /></td>
  --%>
<!-- Rohit Changes starts -->
 <td><bean:message key="lbl.actionDate" /><font color="red">*</font></td>
  <td><html:text property="actionDate" onchange="return checkDate('actionDate');" styleClass="text3" styleId="actionDate"/>
  <html:text property="actionTime" styleClass="text5" onchange="timeValidation('actionTime');" styleId="actionTime"/> 24 Hrs.Format
 </td>
 <!-- Rohit Changes End -->
 
  <td><bean:message key="lbl.totalTimTaken" /> </td>
  <td><html:text property="totalTimeTaken" styleClass="text" styleId="totalTimeTaken" readonly="true" tabindex="-1"/></td>
<!--   Rohit Changes Starts -->
<tr>
 <td><bean:message key="lbl.nomineeRelationship" /><font color="red">*</font></td>
         <td><html:select styleClass="text"  styleId="relationshipS" value="" property="relationshipS">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
  </tr>
  <!--   Rohit Changes end -->
  <tr>
  <td><bean:message key="lbl.contactMode" /><font color="red">*</font><logic:present name="superReview"></logic:present><logic:notPresent name="superReview"><font color="red"></font></logic:notPresent></td>
  <td><html:text property="contactMode" styleClass="text" styleId="contactMode"/></td>
  <td><bean:message key="lbl.personContacted" /><font color="red">*</font><logic:present name="superReview"></logic:present><logic:notPresent name="superReview"><font color="red"></font></logic:notPresent></td>
  <td><html:text property="personContacted" styleClass="text" styleId="personContacted"/></td>
  
  </tr>
  <tr>
  <td><bean:message key="lbl.placeContacted" /></td>
  <td><html:text property="placeContacted" styleClass="text" styleId="placeContacted"/></td>
  <td><bean:message key="lbl.contactBy" /></td>
  <td><html:text property="contactedBy" value="${sessionScope.userobject.userName}" styleClass="text" styleId="contactedBy" readonly="true" tabindex="-1"/>
  <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(91,'contactRecFollowUpForm','contactedBy','','', '','','','lbxUserSearchId')"></html:button>  
   <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" value="${sessionScope.userobject.userId}" />
  </td>
  </tr>
  <tr>
  <td><bean:message key="lbl.actionCode" /><font color="red">*</font></td>
  <td>
  <logic:notPresent name="superReview">
  <html:select property="actionCode" styleClass="text" styleId="actionCode"  onchange="enablePaymentDtl()">
  <html:option value="">--Select--</html:option>
  <html:optionsCollection name="actionCodeList" value="codeId" label="codeDesc"/>
  
  </html:select>
  </logic:notPresent>
  <logic:present name="superReview">
  <html:select property="actionCode" styleClass="text" styleId="actionCode"   >
  <html:option value="SR">Supervisor Review</html:option>
   </html:select>
  </logic:present>
  </td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  </tr>
     <tr>
  <td><bean:message key="lbl.paymentMode" /></td>
  <td>
  <html:select property="paymentMode" styleClass="text" styleId="paymentMode" onchange="disableinstrumentNumber()" disabled="true">
  <html:option value="">--Select--</html:option>
  <html:option value="C">Cash</html:option>
  <html:option value="Q">Cheque</html:option>
  <html:option value="D">DD</html:option>
  <html:option value="N">NEFT</html:option>
  <html:option value="R">RTGS</html:option>
  <html:option value="S">SUSPENSE</html:option>
  </html:select>
  </td>
  <td><bean:message key="lbl.instrumentNo" /></td>
  <td><html:text property="instrumentNo" styleClass="text"  styleId="instrumentNo" disabled="true"/></td>

  </tr>
  <tr>
  <td><bean:message key="lbl.instrumentDate" /></td>
  <td><html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" disabled="true"/></td>
  <td><bean:message key="lbl.receiptNo" /></td>
  <td><html:text property="receiptNo" styleClass="text"  styleId="receiptNo" disabled="true"/></td>
  </tr>
  <tr>
 
  <td><bean:message key="lbl.actionAmount" /></td>
  <td><html:text property="actionAmount" styleClass="text" style="text-align: right" styleId="actionAmount"
	    onkeypress="return numbersonly(event,id,18)" 
		onblur="formatNumber(this.value,id);"
		onkeyup="checkNumber(this.value, event, 18,id);" 
		onfocus="keyUpNumber(this.value, event, 18,id);"  /></td>
   <td><bean:message key="lbl.nxtActionDate" /></td>
  <td><html:text property="nxtActionDate" onchange="return checkDate('nxtActionDate');" styleClass="text3" styleId="nxtActionDate"/>
  <html:text property="nxtActionTime" styleClass="text5" onchange="timeValidation('nxtActionTime');" styleId="nxtActionTime"/> 24 Hrs.Format
 </td>
   </tr>
  
  <tr>
    <td><bean:message key="lbl.FollowUpRemark" /> <font color="red">*</font></td>
  <td><html:textarea property="remarks" styleClass="text" styleId="remarks"></html:textarea></td>
    <td><bean:message key="lbl.custMemoLine" /></td>
  <td><html:textarea property="customerMemoLine" styleClass="text" styleId="customerMemoLine"></html:textarea></td>
  </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td>
  <logic:notPresent name="superReview">
  <!-- <input type="button"  value="Save" id="Save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="this.disabled='true';return saveFollowUpTrail('alert');" /> -->
   <button type="button" class="topformbutton2" id="Save"  onclick="return saveFollowUpTrail('alert');"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
  </logic:notPresent>
 
 <logic:present name="superReview">
 <!-- <input type="button"  value="Save" id="Save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="this.disabled='true';return saveFollowUpTrailSR('alert');" /> -->
  <button type="button" class="topformbutton2"  id="Save"  onclick="this.disabled='true';saveFollowUpTrailSR('alert');"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
 </logic:present>
  </td>
  </tr>
  </table>
  
</fieldset>

           
	</html:form>
	
   <logic:present name="sms">
     <script type="text/javascript">

    //alert("hellooopoo");
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		var loanId='<%=request.getAttribute("loanId").toString()%>';
		var exclationFlag='<%=request.getAttribute("exclationFlag").toString()%>';
		var superReview='<%=request.getAttribute("superReview").toString()%>';
		var actionCode='<%=request.getAttribute("actionCode").toString()%>';
		if(actionCode=='EC'){
		window.opener.location.href="AdhocContactRecordingBehindAction.do";
		}else{
		 window.opener.location.href="AdhocContactRecordingDispatchAction.do?method=openAdhocContactRecordingTrail&loanId="+loanId+"&exclationFlag="+exclationFlag+"&superReview="+superReview;
        }
        self.close();
	}else{
	alert("There is some error .Please Contact Administrator !!");
	  self.close();
	}
	

</script>
</logic:present>
</div></div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
		</html:html>
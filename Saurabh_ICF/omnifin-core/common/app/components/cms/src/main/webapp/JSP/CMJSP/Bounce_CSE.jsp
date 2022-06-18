<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/contentstyle.css"/>
		 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/subpage.css"/>
	   
		 
		  <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		
		
	
	<script type="text/javascript">

		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>
	</head>
	<body onload="enableAnchor();init_fields();">
	
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
	<html:form action="/bounce" method="post">
	
	   <fieldset>	  
	<legend><bean:message key="bounce.mark" /></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					
		   <td width="20%"><bean:message key="bounce.batch" /> </td>
			 <td width="35%">
			   <html:text property="batchNo" styleClass="text"  styleId="batchNo" value="30364" style="" maxlength="100"/></td>
	<td width="17%"><bean:message key="bounce.date" /> </td>
	<td width="28%"><label>
	  <html:text property="batchDate" styleClass="text"  styleId="batchDate" value="30364" style="" maxlength="100"/>
	</label></td>
		</tr>
	  <tr>
	    <td><bean:message key="bounce.name" /> </td>
	    <td nowrap="nowrap" ><label>
	    <html:select property="bankName">
        </html:select>
	    </label></td>
		<td><bean:message key="bounce.type" /> </td>
		<td nowrap="nowrap" ><html:select property="instrumentType">
            <option value="PDC">PDC</option>
            <option value="ECS">ECS</option>
            <option value="DIRECT DEBIT">DIRECT DEBIT</option>
            <option value="SECURITY PDC">SECURITY PDC</option>
            <option value="PRE-EMI">PRE-EMI</option>
          </html:select></td>
	  </tr>
	  <tr>
	    <td><bean:message key="bounce.no" /> </td>
	    <td nowrap="nowrap" ><label>
	    <html:select property="instrumentNo">
          <option value="PDC">PDC</option>
          <option value="ECS">ECS</option>
          <option value="DIRECT DEBIT">DIRECT DEBIT</option>
          <option value="SECURITY PDC">SECURITY PDC</option>
          <option value="PRE-EMI">PRE-EMI</option>
        </html:select>
	    </label></td>
		<td><bean:message key="bounce.instrument.date" /> </td>
		<td nowrap="nowrap" > <html:text property="instrumentDate" styleClass="text"  styleId="instrumentDate" value="0/12/2011" style="" maxlength="100"/></td>
	  </tr>
	  <tr>
	    <td><label>
	     <!--   <input type="submit" name="Submit" class="topformbutton2" value="Search " />-->
	     <button type="button" class="topformbutton2" name="Submit"   
 title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
	     
	    </label></td>
	    <td nowrap="nowrap" >&nbsp;</td>
	    <td>&nbsp;</td>
	    <td nowrap="nowrap" ><label></label></td>
	    </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td nowrap="nowrap" >&nbsp;</td>
	    <td>&nbsp;</td>
	   
	    </tr>
		</table>
		
	      </td>
	</tr>
	</td>
	
	</table>
	 
	</fieldset>	
	
	<FIELDSET>
<LEGEND><bean:message key="bounce.detail" /></LEGEND>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table id="table1" width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr>
	 <td width="220" class="white2" style="width:150px;"><strong><bean:message key="bounce.batch" /> </strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="bounce.date" /> </strong></td>
         <td width="167" class="white2" style="width:150px;"><b><strong><bean:message key="bounce.loan" /> </strong></b></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="bounce.no" /></strong></td>
         <td width="220" class="white2" style="width:150px;"><strong><bean:message key="bounce.instrument.date" /> </strong></td>
		    <td width="220" class="white2" style="width:150px;"><strong><bean:message key="bounce.reason" /> </strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="bounce.remark" /></strong></td>
		   <td width="220" class="white2" style="width:150px;"><strong><b>
		     <input type="checkbox" name="checkbox3" value="checkbox" />
           </b>Select</strong></td>
            </tr>
        <tr>
		 
		  <td class="white" id=""><input class="text" maxlength="100" name="input2" value="dfxv565" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input623" value="22/10/2011" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input222" value="dfxv565" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input62" value="22/10/2011" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input6232" value="22/10/2011" disabled="disabled"/></td>
          <td class="white" id=""><label>
            <select name="select" >
              <option value="Signature Mismatch">Signature Mismatch</option>
              <option value="Insufficient Funds">Insufficient Funds</option>
            </select>
          </label></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input22" value="dfxv565" disabled="disabled"/></td>
		   <td class="white" id=""><label>
		   <input type="checkbox" name="checkbox" value="checkbox" />
</label></td>
            </tr>
        <tr>
         
          <td class="white" id=""><input class="text" maxlength="100" name="input23" value="dfxv565" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input6234" value="22/10/2011" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input224" value="dfxv565" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input622" value="22/10/2011" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input6233" value="22/10/2011" disabled="disabled"/></td>
          <td class="white" id=""><select name="select3">
            <option value="Insufficient Fund">Insufficient Fund</option>
            <option value="Signature Mismatch">Signature Mismatch</option>
                    </select></td>
		   <td class="white" id=""><input class="text" maxlength="100" name="input24" value="dfxv565" disabled="disabled"/></td>
		    <td class="white" id=""><label>
		   <input type="checkbox" name="checkbox" value="checkbox" />
</label></td>
		   
          </tr>
        </table>  </td></tr> 
</table>


   
  
  
  <table width="100%" border="0" cellpadding="2" cellspacing="1">
  <tr>
  <td>
			 <input type="submit" name="Submit2" class="topformbutton2"value="Save" />
			 <input type="submit" name="Submit22" class="topformbutton2"value="Save &amp; Forward" />
</td>
			</tr></table></FIELDSET> 
    </html:form>
</div>



</div>
</body>
</html:html>
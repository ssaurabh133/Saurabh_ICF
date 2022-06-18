<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>


<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
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
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=path%>/js/cmScript/instrumentCapturing.js"></script>
   
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

<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>		
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
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/instrumentCapProcessActionforNew" method="post" styleId="sourcingForm" >
	<%
	    com.login.roleManager.UserObject ob=(com.login.roleManager.UserObject)session.getAttribute("userobject");
	    if(ob!=null)
	    {
	      session.setAttribute("userId",ob.getUserId());
	    }
		
	 %>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
    <fieldset>
    
    <logic:present name="instrumentCapturingMakerSearch"> 
	<legend><bean:message key="lbl.instrumentUpdateMakerSearch"/></legend>   
	</logic:present>	    
	
	<logic:present name="instrumentCapturingAuthorSearch"> 
	<legend><bean:message key="lbl.instrumentUpdateAuthorSearch"/></legend>   
	</logic:present>   
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
	  <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		 <tr>	
		 		
		   <td><bean:message key="lbl.loanNumber"/></td>
		   
		   
		 <logic:present name="author"> 			
		   
		   
		   <td>
	<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100"  readonly="true" tabindex="-1"/>
	<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
    <input type="hidden" name="hcommon" id="hcommon" />
	 <%--<html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(115,'sourcingForm','loanAccNo','userId','loanAccNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
    --%>
    <%--
     <img onclick="openLOVCommon(115,'sourcingForm','loanAccNo','','', '','','','customerName')" SRC="<%= request.getContextPath()%>/images/lov.gif">
    --%>
    <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(390,'sourcingForm','loanAccNo','userId','loanAccNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
           </td>
	    </logic:present>
	    
		<logic:notPresent name="author"> 			
	    
		   <td>
	<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100"  readonly="true" tabindex="-1"/>   
   <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
    <input type="hidden" name="hcommon" id="hcommon" />
	<%--  <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(118,'sourcingForm','loanAccNo','userId','loanAccNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
        --%>
           <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(391,'sourcingForm','loanAccNo','userId','loanAccNo', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
        
           </td>
	</logic:notPresent>
		
           
           <td><bean:message key="lbl.customerName"/></td>
	<td>
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="50"  readonly="true" tabindex="-1" /> </td>
	
	</tr>
	<tr>
	      
	     <td>
		   <bean:message key="lbl.bank"></bean:message>
		  		
	    </td>
	    <logic:notPresent name="author"> 	
			 <td><div style="float:left;">		 
	 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  readonly="true" tabindex="-1"/>
    <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
     <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(164,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>   
      <%-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
        	  </div>
        	  </td>
		</logic:notPresent>
		
		<logic:present name="author"> 	
			 <td><div style="float:left;">		 
	 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  readonly="true" tabindex="-1"/>
    <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
     <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(165,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>   
      <%-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
        	  </div>
        	  </td>
		</logic:present>
		
		
		
		
	<td><bean:message key="lbl.branch"></bean:message></td>
		<td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  readonly="true" tabindex="-1"/>
        <html:hidden  property="lbxBranchID" styleId="lbxBranchID" />
        <html:button property="branchButton" styleId="branchButton" onclick="openLOVCommon(8,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','lbxBranchID');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button>   
     <%--  <img onclick="openLOVCommon(8,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','lbxBranchID')" src="<%= request.getContextPath()%>/images/lov.gif">--%>
  </td>	
  </tr>
  <tr>
   <td><bean:message key="lbl.instrumentMode"/></td>
	       <td>
	<html:select property="instrumentType" styleId="instrumentType" styleClass="text" >
	            <html:option value="">--Select--</html:option>
		        <html:option value="Q">PDC</html:option>
		        <html:option value="E">ECS</html:option>
		        <html:option value="DIR">Direct Debit</html:option>
		        <html:option value="H">NACH</html:option>
   </html:select> 
          </td>

		   <td><bean:message key="lbl.product"/></td>    
			<td>
	<html:text styleClass="text" property="product" styleId="product" maxlength="100"  readonly="true" tabindex="-1"/>   
	<html:hidden property="lbxProductID" styleId="lbxProductID" />
	 <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(17,'sourcingForm','product','','', '','','','lbxProductID');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
    <%-- <img onclick="openLOVCommon(17,'sourcingForm','product','','', '','','','lbxProductID')" SRC="<%= request.getContextPath()%>/images/lov.gif">
    --%>
    </td>     
	</tr>
       <logic:present name="author">
       
			<tr>
			<td>
			 <bean:message key="lbl.userName" />
			 </td>
			 <td>
			    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
			    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
			    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
			    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'sourcingForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
		</tr>
         <tr>
			<td align="left">  
			    <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchUpdateInstrumentAuthor();"><bean:message key="button.search" /></button>
			    
			</td>
		</tr>
		 </logic:present>
		 
		 <logic:present name="maker">
			<tr>
			<td>
			 <bean:message key="lbl.userName" />
			 </td>
			 <td>
			    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
			    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
			    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
			    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'sourcingForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
			</tr>
		  
           <td align="left">
            <button type="button" name="button"  id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchUpdateMakerInstrument();"><bean:message key="button.search" /></button> 
	       	<button type="button" name="button" id="new" class="topformbutton2" title="Alt+N" accesskey="N" onclick="return newUpdateInstrument();"><bean:message key="button.new" /></button>
	      </td>
		</tr>
		 </logic:present> 
		 	  
		  
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	
	
	
	
</html:form>
	</div>
	
	
	
	
<fieldset>	

		 <logic:present name="instrumentCapturingMakerSearch"> 
	<legend><bean:message key="lbl.instrumentMakerRecord"/></legend>   
	</logic:present>
   
   <logic:present name="instrumentCapturingAuthorSearch"> 
	<legend><bean:message key="lbl.instrumentAuthorRecord"/></legend>   
	</logic:present>
  
  <logic:present name="instrumentCapturingMakerSearch">
<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/instrumentUpdateMakerActions.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanAccNo" titleKey="lbl.loanNumber"  sortable="true"  />
    <display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.loanScheme"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
      <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>

        <td><b><bean:message key="lbl.loanAmount"/></b></td>
        <td><strong><bean:message key="lbl.loanApprovalDate"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.branch"/></strong></td>
        <td><strong><bean:message key="lbl.customerName"/></strong></td>
        <td><strong><bean:message key="lbl.userName"/></strong></td>
	</tr>
	</tr>
<tr class="white2">
<td colspan="8">
<bean:message key="lbl.noDataFound"/>  
</td>
</tr>
	
 </table>    </td>

</table>

</logic:empty>
  </logic:present>
         
         <logic:present name="instrumentCapturingAuthorSearch">
		<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/instrumentUpdateProcessActionforNew.do?method=searchInstrumentAuthor" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanAccNo" titleKey="lbl.loanNumber"  sortable="true"  />
    <display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.loanScheme"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
      <td><strong><bean:message key="lbl.loanNumber"/></strong> </td>

        <td><b><bean:message key="lbl.loanAmount"/></b></td>
        <td><strong><bean:message key="lbl.loanApprovalDate"/></strong></td>
        <td><strong><bean:message key="lbl.product"/></strong></td>
        <td><strong><bean:message key="lbl.loanScheme"/></strong></td>
        <td><strong><bean:message key="lbl.branch"/></strong></td>
        <td><strong><bean:message key="lbl.customerName"/></strong></td>
        <td><strong><bean:message key="lbl.userName"/></strong></td>
	</tr>
	</tr>
<tr class="white2">
<td colspan="8">
<bean:message key="lbl.noDataFound"/>  
</td>
</tr>
	
 </table>    </td>


</table>


</logic:empty>
  </logic:present>

	</fieldset>
    
 <logic:present name="alertMsg">
	<script type="text/javascript">
	
	 if('<%=request.getAttribute("alertMsg")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/instrumentCapBehindAction.do";
	    document.getElementById("sourcingForm").submit();
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked1')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/instrumentCapBehindActionAuthor.do";
	    document.getElementById("sourcingForm").submit();
	}
	</script>
	</logic:present>
	
	    


</div>
</body>
</html:html>

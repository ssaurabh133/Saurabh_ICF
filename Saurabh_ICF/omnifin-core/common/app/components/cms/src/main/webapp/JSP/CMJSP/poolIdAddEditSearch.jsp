<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

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
			
			function defaultFocus()
			{
				document.getElementById('sourcingForm').loanNoButton.focus();
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
	
	<html:form action="/poolIdAddEditProcessAction" method="post" styleId="sourcingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
      <!-- ------------------------------------For PoolId Add Edit Search   -------------------------------------------- -->
     
     
	
	  <fieldset>
	<legend><bean:message key="lbl.poolIdAddEditSearch"/></legend>  
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		 <tr>	
		 		
		   <td><bean:message key="lbl.poolID"/></td>
	 <td>
	 <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />  
	<html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100"  readonly="false"
	onchange="openLOVCommon(187,'sourcingForm','poolID','userId','poolID', 'userId','','','poolName','poolCreationDate','','Y');closeLovCallonLov1();" 
	tabindex="-1"/>   
   <html:hidden property="lbxPoolID" styleId="lbxPoolID" />
    <input type="hidden" name="hcommon" id="hcommon" />
	 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(187,'sourcingForm','poolID','userId','poolID', 'userId','','','poolName','poolCreationDate');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
           </td>
	
		 <td><bean:message key="lbl.PoolName"/></td>
	<td>
	<html:text styleClass="text" property="poolName" styleId="poolName"  maxlength="50"  /> </td>
	
	     
    
        
        </tr>
	<tr>   
	<td><bean:message key="lbl.PoolCreationDate"/></td>
	<td>
	<html:text styleClass="text" property="poolCreationDate" styleId="poolCreationDate"  onchange="checkDate('poolCreationDate')"   /> 
	</td>
        
      
	     <td><bean:message key="lbl.cutOffDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	    <td><label>
	      <html:text styleClass="text" property="cutOffDate" styleId="cutOffDate"  onchange="checkDate('cutOffDate')" />
	    </label></td>	
		
		</tr>
		<tr>
		
	<td><bean:message key="lbl.instituteID"/></td>

		   <td>
	<html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100"  readonly="false"
	onchange="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon','','','Y');closeLovCallonLov1();"
	 tabindex="-1"/>   
   <html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
    <input type="hidden" name="hcommon" id="hcommon" />
	 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>   
           </td>

			
  </tr>
		 <tr>
			
	        <td align="left">  
			    <button type="button" name="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchForPoolIdAddEdit();"><bean:message key="button.search" /></button> 
			</td>
		</tr>
		
		 </table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
		
    
     <fieldset>	
		 <legend><bean:message key="lbl.poolIdSearchRecord"/></legend>  

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    
         <table width="100%" border="0" cellspacing="1" cellpadding="1">
         <logic:notPresent name="poolIdAddEditList">
     
         <tr class="white2">
	     <td><b><bean:message key="lbl.poolID"/></b></td>
         <td><b><bean:message key="lbl.PoolName"/></b></td>
         <td><b><bean:message key="lbl.PoolCreationDate"/></b></td>
	     <td><b><bean:message key="lbl.cutOffDate"/></b></td>
		 <td><b><bean:message key="lbl.instituteID"/> </b></td>
		</tr>
<tr class="white2">
<td colspan="6"> 
<bean:message key="lbl.noDataFound" />
</td>
</tr>
		
		
		
		</logic:notPresent>
		</table>
		</td>
	   </tr>
		</table>
		
	<logic:present name="poolIdAddEditList">
	<logic:notEmpty name="poolIdAddEditList"> 
	<display:table  id="sublist"  name="poolIdAddEditList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${poolIdAddEditList[0].totalRecordSize}" requestURI="/poolIdAddEditProcessAction.do?method=searchPoolIdAddEdit">
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="modifyNo" titleKey="lbl.poolID"  sortable="true"  />
    <display:column property="poolName" titleKey="lbl.PoolName"  sortable="true"  />
	<display:column property="poolCreationDate" titleKey="lbl.PoolCreationDate"  sortable="true"  />
	<display:column property="cutOffDate" titleKey="lbl.cutOffDate"  sortable="true"  />
	<display:column property="instituteID" titleKey="lbl.instituteID"  sortable="true"  />
	</display:table>
	</logic:notEmpty>

<logic:empty name="poolIdAddEditList">



 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
          <td><b><bean:message key="lbl.poolID"/></b></td>
         <td><b><bean:message key="lbl.PoolName"/></b></td>
         <td><b><bean:message key="lbl.PoolCreationDate"/></b></td>
	     <td><b><bean:message key="lbl.cutOffDate"/></b></td>
		 <td><b><bean:message key="lbl.instituteID"/> </b></td>
		
	</tr>
	<tr class="white2">
<td colspan="6"> 
<bean:message key="lbl.noDataFound" />
</td>
</tr>
 </table>    </td>
</tr>

</table>
</logic:empty>
  </logic:present> 
    
	</fieldset>
	 
</html:form>
	</div>
	
<!--	<logic:present name="datalist">-->
<!--		<script type="text/javascript">-->
<!--			alert("<bean:message key="lbl.noDataFound" />");	-->
<!--		-->
<!--		</script>-->
<!--		</logic:present>	-->
  
	

    
 <logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'Y')
		{
			alert("<bean:message key="lbl.forwardSuccess" />");
			document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdAddEditBehindAction.do";
	        document.getElementById("sourcingForm").submit();
		}
	
	
	else if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("Data not Found ");
	
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdAddEditBehindAction.do";
	    document.getElementById("sourcingForm").submit();
	}
	else if('<%=request.getAttribute("alertMsg")%>'=='Locked1')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		document.getElementById("sourcingForm").action="<%=request.getContextPath()%>/poolIdAddEditBehindAction.do";
	    document.getElementById("sourcingForm").submit();
	}
	</script>
	</logic:present>
	
	    


</div>
</body>
</html:html>
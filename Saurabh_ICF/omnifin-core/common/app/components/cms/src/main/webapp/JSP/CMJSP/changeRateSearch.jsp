<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	
	<head>
	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/nonEmiBasedLoan.js"></script>
		
		
		<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->
	
	<script type="text/javascript">
  		
		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
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
	<body onload="enableAnchor();document.getElementById('nonEmiBasedMakerSearch').loanAcButton.focus();init_fields();">
		<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	<html:form action="/changeRateMaker" method="post" styleId="nonEmiBasedMakerSearch">
	
	
		  
	          <logic:present name="OpenAuthor">
	          
	           <fieldset>	  
	<legend><bean:message key="lbl.ratechangAuthorSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%">
	            
	             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		         <input type="hidden" name="lbxAssetId" id="lbxAssetId" value="" />
		         </td>
            </tr>	
            <tr>	   
         <td><bean:message key="lbl.loanNo"></bean:message></td>
	      <td>
		<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
        <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="" />
       <html:button property="loanAcButton" styleId="loanAcButton" onclick="openLOVCommon(379,'nonEmiBasedMakerSearch','loanNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
       <%--<img onclick="openLOVCommon(94,'nonEmiBasedMakerSearch','loanAccountNumber','','', '','','','lbxLoanNoHID')" src="<%= request.getContextPath()%>/images/lov.gif"/> --%> 
        </td>
	
	    <td nowrap="nowrap"><bean:message key="lbl.customerName"></bean:message> </td>
		    <td nowrap="nowrap">
			 <html:text property="customerName" styleClass="text" maxlength="50" styleId="customerName" value="" ></html:text>
            </td>
		</tr>	
		 	
		   <tr>		   	  
	      	 <td>
			 <bean:message key="lbl.currentRateType" />
			 </td>
			 <td>
			    <span style="float: left;"> 
			    <html:select
					property="currentRateType" styleId="currentRateType" styleClass="text" >
					<html:option value="E">EFFECTIVE</html:option>
					<html:option value="F">FLAT</html:option>
					<html:option value="P">PTPM</html:option>
                 </html:select> </span>
		 </td>
          </tr>
          
	        <tr>
	      <td>
	        <button type="button" class="topformbutton2"  id="search" onclick="return fnSearch('<bean:message key="msg.plsSelect" />','F');" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
	        </td> 

		  </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
		   
		    </logic:present>
		    
		     <logic:notPresent name="OpenAuthor">	
		      <fieldset>	  
	<legend><bean:message key="lbl.ratechangeMakerSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%">
	            
	             <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		   </td>
           </tr> 
		   <tr>	   
        <td><bean:message key="lbl.loanNo"></bean:message></td>
	      <td>
		<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
        <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="" />
       <html:button property="loanAcButton" styleId="loanAcButton" onclick="openLOVCommon(378,'nonEmiBasedMakerSearch','loanNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
       <%--<img onclick="openLOVCommon(94,'nonEmiBasedMakerSearch','loanAccountNumber','','', '','','','lbxLoanNoHID')" src="<%= request.getContextPath()%>/images/lov.gif"/> --%> 
        </td>
	
	    <td nowrap="nowrap"><bean:message key="lbl.customerName"></bean:message> </td>
		    <td nowrap="nowrap">
			 <html:text property="customerName" styleClass="text" maxlength="50" styleId="customerName" value="" ></html:text>
            </td>
		</tr>	
		
		   <tr>		   	  
	      	 <td>
			 <bean:message key="lbl.currentRateType" />
			 </td>
			 <td>
			    <span style="float: left;"> 
			    <html:select
					property="currentRateType" styleId="currentRateType" styleClass="text">
                    <html:option value="E">EFFECTIVE</html:option>
					<html:option value="F">FLAT</html:option>
					<html:option value="P">PTPM</html:option>
                 </html:select> </span>
		 </td>
          </tr>
	        <tr>	
	      <td>
	      <button type="button" class="topformbutton2"  id="search" onclick="return fnSearch('<bean:message key="msg.plsSelect"/>','P');" title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
	      <button type="button" name="button2" class="topformbutton2"  onclick="return newNonEMILoan();" title="Alt+N" accesskey="N"><bean:message key="button.new" /></button>
	      </td>
		  </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
		</logic:notPresent>    


		
     <fieldset>	
		 <legend><bean:message key="lbl.rateChangeSearchRecords"/></legend>  

  

	 <logic:present name="list">
<logic:notEmpty name="list"> 
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/nonEmiBasedMakerBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>  
    <display:column property="modifyLoan" titleKey="lbl.loanNo"  sortable="true"  />
     <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
    <display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
	<display:column property="balanceAmount" titleKey="lbl.balanceAmount"  sortable="true"  />
	<display:column property="loanFinalRate" titleKey="lbl.currentEffectRate"  sortable="true"  />
	<display:column property="currentRateType" titleKey="lbl.currentRateType"  sortable="true"  />
	<display:column property="newEffectRate" titleKey="lbl.newEffectRate"  sortable="true"  />
</display:table>
</logic:notEmpty>
  </logic:present>
  
  <logic:present name="datalist">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td><b><bean:message key="lbl.loanNo"/></b></td>
	    <td><b><bean:message key="lbl.customerName"/></b></td>
	    <td><b><bean:message key="lbl.loanAmount"/></b></td>        
        <td><b><bean:message key="lbl.balanceAmount"/></b></td>
        <td><b><bean:message key="lbl.currentEffectRate"/></b></td>
		<td><b><bean:message key="lbl.currentRateType"/></b></td>
		<td><b><bean:message key="lbl.newEffectRate"/></b></td>
	</tr>
	<tr class="white2">
<td colspan="8">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
 </table>    </td>
</tr>

</table>
</logic:present>
  
 
 

 
	</fieldset>

 </html:form>
  
   
</div>



</div>
</body>
</html>
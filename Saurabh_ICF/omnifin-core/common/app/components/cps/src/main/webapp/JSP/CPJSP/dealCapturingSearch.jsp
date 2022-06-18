<%--
      Author Name-  Prashant kumar    
      Date of creation -12/05/2011
      Purpose-   InitLoan Info       
      Documentation-      
      
 --%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		
  
	
	
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		
		
	<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>	

		
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		var userName = "<bean:message key="lbl.dateFormat(dd-mm-yyyy)"/>";
		$("#textId").val(userName);
		$("#textId").addClass("watermark");
		
		$("#textId").focus(function() {
			if (this.value == userName) {
				this.value = "";
				$("#textId").removeClass("watermark");
			}
		});		
		$("#textId").blur(function() {

			if (this.value == "") {
				this.value = userName;
				$("#textId").addClass("adddate");
			}
			
		});					
	});
	
	
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
	<body oncontextmenu="return false" onload="enableAnchor();document.getElementById('commonForm').dealButton.focus();init_fields();" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
	
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

	<html:form action="/commondeal" method="post" styleId="commonForm">
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>
	<fieldset>	  
	  <legend><bean:message key="lbl.ac.detail"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		 <table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		<td width="20%"><bean:message key="lbl.dealNo"/></td>
		<td width="35%" valign="top">
		
				
			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
<logic:present name = "preDealModuleExists">
				<logic:equal value="N" name="preDealModuleExists">			<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(21,'commonForm','dealNo','userId','dealNo', 'userId','','','customername');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	        <%-- <img onclick="openLOVCommon(21,'commonForm','dealNo','','', '','','','customername')" src="<%= request.getContextPath()%>/images/lov.gif"  />--%> 	  
      </logic:equal>
         </logic:present>
            <logic:present name = "preDealModuleExists">
                <logic:notEqual value="N" name="preDealModuleExists">
                    <html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(17041,'commonForm','dealNo','userId','dealNo', 'userId','','','customername');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
                </logic:notEqual>
            </logic:present>
	  </td>
	  
	  
	  <td><bean:message key="lbl.customerName"/></td>
		<td width="13%" ><html:text property="customername" styleClass="text" styleId="customername" maxlength="50" onchange="upperMe('customerName');"/></td> 
		<td width="15%" ></td>
		
	   
	    </tr>
	  	      <!-- harsh code for lead Lov -->
	     <tr>
               <td width="13%">
                        <bean:message key="lbl.leadno" />
                    </td>
                    <td width="13%">
                    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
                    
                          <%--  <html:text property="leadno" styleClass="text" styleId="leadno" value="" maxlength="50" readonly="true" tabindex="-1" /> --%>
                        <html:text property="leadno" styleClass="text" styleId="leadno" maxlength="100" readonly="true" tabindex="-1" />
						<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
                        <logic:present name = "preDealModuleExists">
                       <logic:equal value="N" name="preDealModuleExists">
                      <%--   <logic:present name="capturing"> --%>
                            <input type="button" class="lovbutton" id="leadButton"
                                onclick="openLOVCommon(192018,'commonForm','leadno','userId','leadno', 'userId','','','customername');closeLovCallonLov1('')"
                                value=" " tabindex="1" name="dealButton">
                                 <input type="hidden" name="customername" id="customername" value="" />       
                        <%-- </logic:present> --%>
                             </logic:equal>             
                        </logic:present>
                        <logic:present name = "preDealModuleExists">
                   <logic:notEqual value="N" name="preDealModuleExists">
                   	 <input type="button" class="lovbutton" id="leadButton"
                                onclick="openLOVCommon(192019,'commonForm','leadno','userId','leadno','', 'userId','','','customername')"
                                value=" " tabindex="1" name="dealButton">
                                 <input type="hidden" name="customername" id="customername" value="" />
                     </logic:notEqual>
            		</logic:present>               
                                    
                                                               </td>
                                                                  
            </tr>
	    <!-- harsh code for lead Lov -->
	    <tr>
		<td><bean:message key="lbl.applicationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
		<td ><html:text property="applicationdate"   styleClass="text" styleId="applicationdate" maxlength="10" onchange="return checkDate('applicationdate');"/></td> 
	    
		 <td width="17%"><bean:message key="lbl.applicationNo"/></td>
	    <td colspan="2" ><html:text property="applicationno" styleClass="text"styleId="applicationno" style="text-align: right" maxlength="20"  onkeyup="return upperMe('applicationno');"/></td>
	    </tr>
		
		<tr> 
		<td><bean:message key="lbl.product"/></td> 
       
        <td > 
      
	        <html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(87,'commonForm','product','userId','scheme','lbxscheme|userId','','','productId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	        <%-- <img onclick="openLOVCommon(87,'commonForm','product','','scheme','lbxscheme','','','productId')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>	         
    
      
        </td>
        <input type="hidden" id="productId" name="productId"/> 
        
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          		
          		 <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');" value=" " styleClass="lovbutton"> </html:button>
               <%-- <img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" />--%>
           						    
                </td>
                <input type="hidden" id="schemeId" name="schemeId"/> 
       		</tr> 
       		 <tr>
			    <td width="13%">
						<bean:message key="lbl.userNam" />
				      </td>
				      <td width="13%">
				      	<html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId"  />
					  	<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
					  	<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'commonForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>
   					  </td>
			    </tr>	
				
		 <tr>
		    
		     <td align="left">
		    <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return newSearchDeal('P');"><bean:message key="button.search" /></button>
		    
		    <logic:present name = "preDealModuleExists">
		    	<logic:equal value="N" name="preDealModuleExists">
		   			<button type="button" name="new" id="button" class="topformbutton2" title="Alt+N" accesskey="N" onclick="newEntryDeal();" ><bean:message key="button.new" /></button></td>
		   		</logic:equal>
		   	</logic:present>
		     
		 </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>

	</html:form>

	</div>

<fieldset>	
		 <legend><bean:message key="lbl.applicationDetails"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    
    <tr class="white2">
	<logic:notPresent name="list">
	
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
	
        <td><b><bean:message key="lbl.applicationDate"/></b></td>
       	<td><b><bean:message key="grd.applicationNo"/></b></td>
      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
    <td><b><bean:message key="lbl.userNam"/> </b></td>
   
</logic:notPresent>
	</tr>
 </table>    </td>
</tr>
</table>
 <logic:present name="list">
 <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/commondeal.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    <display:column property="lbxDealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customername" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="applicationdate" titleKey="lbl.applicationDate"  sortable="true"  />
	<display:column property="applicationno" titleKey="grd.applicationNo"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userNam"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
	
	
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
	
        <td><b><bean:message key="lbl.applicationDate"/></b></td>
       	<td><b><bean:message key="grd.applicationNo"/></b></td>
      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
         <td><b><bean:message key="lbl.userNam"/> </b></td>
	</tr>
	<tr class="white2"><td colspan="7"><bean:message key="lbl.noDataFound"/></td></tr>
	
 </table>    </td>
</tr>
</table>
<!--	<script type="text/javascript">-->
<!---->
<!--		alert("<bean:message key="lbl.noDataFound" />");-->
<!--	-->
<!--	</script>-->
</logic:empty>
  </logic:present>


	</fieldset>
<!---->
<logic:present name="sms">
<script type="text/javascript">
//alert("hello");

	
   if('<%=request.getAttribute("sms").toString()%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId").toString()%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}
	
</script>
</logic:present>
</div>

<%-- Div for screen Saver --%>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
	</div>
</body>
</html:html>
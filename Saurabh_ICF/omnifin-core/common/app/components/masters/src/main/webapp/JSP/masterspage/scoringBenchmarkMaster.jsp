 <!--Author Name : Mradul Agarwal 
Date of Creation : 01-June-2013
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>    
   <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/scoringBenchmarkMaster.js"></script> 	
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript">

function addRow(val)
	{
	 // alert(val);
	  var ptable = document.getElementById('gridtable');
	  var lastElement = ptable.rows.length;
	  //var index=lastElement;
	  //document.getElementById("psize").value=1;
	 
      var pcheck = document.getElementById("pcheck").value; 
	  var psize= document.getElementById("psize").value;	
	if(psize==""){
		psize=lastElement;
	}

     //  alert(index);
         
	 var row =ptable.insertRow(lastElement);
	 row.setAttribute('className','white1' );
     row.setAttribute('class','white1' );
			
      var cellCheck = row.insertCell(0);
  	  var element = document.createElement('input');
  	  element.type = 'checkbox';
  	  element.name = 'chkd';
  	  element.id = 'allchkd'+psize;	 
  	  cellCheck.appendChild(element);	
	
if(val=="Numeric"){

	var numericFromSelect = row.insertCell(1);
	  var numericFrom = document.createElement('input');
	  numericFrom.type = 'text';
	  numericFrom.name = 'numericValueFrom';
	  numericFrom.id = 'numericFromId'+psize;
	  numericFrom.setAttribute('className','text');
	  numericFrom.setAttribute('class','text');
	  numericFromSelect.appendChild(numericFrom);
	  
	  var numericToSelect = row.insertCell(2);
	  var numericTo = document.createElement('input');
	  numericTo.type = 'text';
	  numericTo.name = 'numericValueTo';
	  numericTo.id = 'numericToId'+psize;
	  numericTo.setAttribute('className','text');
	  numericTo.setAttribute('class','text');
	  numericToSelect.appendChild(numericTo);
	  
	  var ratingSelect = row.insertCell(3);
      var rating = document.createElement('input');
	  rating.type = 'text';
	  rating.name = 'rating';
	  rating.id = 'rating'+psize;
	  rating.setAttribute('className','text');
	  rating.setAttribute('class','text');
	  ratingSelect.appendChild(rating);

	}else{
      var characterCodeSelect = row.insertCell(1);
	  var characterCode = document.createElement('input');
	  characterCode.type = 'text';
	  characterCode.name = 'characterValue';
	  characterCode.id = 'characterCodeId'+psize;
	  characterCode.setAttribute('className','text');
	  characterCode.setAttribute('class','text');
	  characterCodeSelect.appendChild(characterCode);
	  
	  var ratingSelect = row.insertCell(2);
      var rating = document.createElement('input');
	  rating.type = 'text';
	  rating.name = 'rating';
	  rating.id = 'rating'+psize;
	  rating.setAttribute('className','text');
	  rating.setAttribute('class','text');
	  ratingSelect.appendChild(rating);
	  }
        		

	  //index++;
	  pcheck++;
	  psize++;	  
	  document.getElementById("psize").value=psize;
	  document.getElementById("pcheck").value=pcheck;
		  
	  }


function removeRow(val) {
      var table = document.getElementById("gridtable");
         var rowCount = table.rows.length;
         //alert("Row Count"+rowCount)
		var count=0;
         for(var i=1; i<rowCount; i++) {
//        	 alert("i: "+i);
//        	 alert("Row Count"+rowCount);
             var row = table.rows[i];
             var chkbox = row.cells[0].childNodes[0];
//             alert("chkbox.checked: "+chkbox.checked);
             if(null != chkbox && true == chkbox.checked) 
             {
             	 table.deleteRow(i);
                 //rowCount--;
                 count++;
                 i--;
              }        

            }        

      if(count==0){
           alert(val);
         return false;
         }

      }

function removeSearchRow(val) {

      var table = document.getElementById("gridtable");
        var rowCount = table.rows.length;
		var count=rowCount;

         for(var i=1; i<rowCount; i++) {
             var row = table.rows[i];
             var chkbox = row.cells[0].childNodes[0];
             if(null != chkbox && true == chkbox.checked) 
             {
             	 table.deleteRow(i);
                 rowCount--;
                 i--;
              return true;
              }        

            }        

      if(count>1){
           alert(val);
         return false;
         }

      }

</script>
  </head>  
  <body onload="enableAnchor();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <html:form styleId="scoringBenchmarkSearch" action="/scoringBenchmarkSearchBehindAction" >
 <input type="hidden"  name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>

 <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />

  <fieldset>
  <legend><bean:message key="lbl.scoringBenchmarkMaster"/></legend>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="4" cellspacing="2">
         
<tr> 	  	
 	  	  <td><bean:message key="lbl.industry"/><span><font color="red">*</font></span></td>
      <td>
      <html:text property="industry" styleId="industry"  styleClass="text" readonly="true" value="${industry}" tabindex="-1"/>
      <html:hidden  property="lbxIndustryID" styleId="lbxIndustryID"  value="${lbxIndustryID}"/>      
  	  <html:button property="industryButton" styleId="industryButton" onclick="closeLovCallonLov1();openLOVCommon(541,'ScoringBenchmarkMasterDyanavalidatiorForm','industry','','', '','','','lbxIndustryID')" value=" " styleClass="lovbutton"> </html:button>
  	 </td>  	 
  
    <td><bean:message key="lbl.scorecard"/><span><font color="red">*</font></span></td>
      <td>
      <html:text property="scorecard" styleId="scorecard"  styleClass="text" readonly="true" value="${scorecard}" tabindex="-1"/>
      <html:hidden  property="lbxScorecardID" styleId="lbxScorecardID"  value="${scorecardID}"/>
      <input type="hidden"  name="dataType" value="${dataType}" id="dataType"/>
  	  <html:button property="scorecardButton" styleId="scorecardButton" onclick="closeLovCallonLov1();openLOVCommon(542,'ScoringBenchmarkMasterDyanavalidatiorForm','scorecard','','', '','','','dataType','lbxScorecardID')" value=" " styleClass="lovbutton"> </html:button>
  	  
  </td>
  </tr>
  <tr>
 		<td><bean:message key="lbl.weightage" /><span><font color="red">*</font></span></td>
		<td><html:text property="weightage" styleId="weightage" maxlength="100" value="${weightage}" style="text-align: right;"  onkeypress="return fourDecimalnumbersonly(event, id, 3);" 	onblur="fourDecimalNumber(this.value, id);" onkeyup="fourDecimalcheckNumber(this.value, event, 3, id);"	onfocus="fourDecimalkeyUpNumber(this.value, event, 3, id);" /></td>
  </tr>
 <tr>
 	 <td colspan="2"><button  type="button"  name="button" id="button" title="Alt+S" accesskey="S" class="topformbutton2" onclick="fnSearch();" ><bean:message key="button.search" /></button>
 	 <button type="button" value="View" class="topformbutton2" onclick="return viewScore();" title="Alt+V" accesskey="V"><bean:message key="button.view"/></button>
 	 </td>
  </tr>
 </table></td>
  </tr></table>
  <br/>
<logic:present name="search">	

	<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
	<tr>
		<td class="gridtd">
			<input type="hidden" name="psize" id="psize" />
			<input type="hidden" name="pcheck" id="pcheck" />
			<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
			
			<logic:present name="A">	
			 	<tr class="white2">   
			 	 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" /></td>
				 <td ><b><bean:message key="lbl.benchmark" /></b></td>
    			 <td ><b><bean:message key="lbl.rating" /></b></td>
       			</tr>
       		</logic:present>
       		<logic:notPresent name="A">	
			 <tr class="white2">   
			 	 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" /></td>
				 <td ><b><bean:message key="lbl.benchmarkNumFrom" /></b></td>
    			 <td ><b><bean:message key="lbl.benchmarkNumTo" /></b></td>
    			 <td ><b><bean:message key="lbl.rating" /></b></td>
       		</tr>
       		</logic:notPresent>

	<logic:iterate name="search" id="sublist" indexId="i">
		<logic:present name="A">
			<tr class="white1" style="width: 25%;">
			<td><input type="checkbox" name="chk" id="chk<%=i.intValue()+1 %>" value="${requestScope.search[i].benchmarkCodeId}"/></td>
			<td><input type="text" name="characterValue" id="characterCodeId<%=i.intValue()+1%>"  value="${requestScope.search[i].characterValue1}"  class="text"/></td>
			<td>
				<input type="text" name="rating" id="rating<%=i.intValue()+1 %>" value="${requestScope.search[i].rating1}"  class="text" tabindex="-1"/>
				<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			</td>
			</tr>
	  	</logic:present>
		<logic:notPresent name="A">
			<tr class="white1" style="width: 25%;">
			<td><input type="checkbox" name="chk" id="chk<%=i.intValue()+1 %>" value="${requestScope.search[i].benchmarkCodeId}"/></td>
			<td><input type="text" name="numericValueFrom" id="numericFromID<%=i.intValue()+1%>"  value="${requestScope.search[i].numericValueFrom1}"  class="text"/></td>
			<td><input type="text" name="numericValueTo" id="numericToID<%=i.intValue()+1%>"  value="${requestScope.search[i].numericValueTo1}"  class="text"/></td>
			<td>
				<input type="text" name="rating" id="rating<%=i.intValue()+1 %>" value="${requestScope.search[i].rating1}"  class="text" tabindex="-1"/>
				<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			</td>		
			</tr>
		</logic:notPresent>
	</logic:iterate>

<logic:notPresent name="A">
	<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="6">
			<button type="button"  title="Alt+A" accesskey="A" onclick="return addRow('Numeric');" class="topformbutton2"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.deleterow" /></button>
			</td>
	</tr>
	</table>
</logic:notPresent>

<logic:present name="A">
	<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtableButton">
	<tr class="white1">
		<td align="left" colspan="6">
			<button type="button"  title="Alt+A" accesskey="A" onclick="return addRow('Alpha');" class="topformbutton2"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return removeRow('<bean:message key="lbl.selectOne" />');"><bean:message key="button.deleterow" /></button>
		</td>
	</tr>
	</table>
</logic:present>
</table>

</td></tr></table>
<logic:present name="search">
<tr>
     <td><button type="button" id="save" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return scoringDetailsSave('<bean:message key="lbl.selectOne" />');" ><bean:message key="button.save" /></button></td>
</tr>
</logic:present>
</logic:present>
  </fieldset>
</html:form>
 <logic:present name="saveData">
<script type="text/javascript">

   if('<%=request.getAttribute("saveData").toString()%>'=='save')
	{
		alert('Data Saved Succesfully');
	}
</script>
</logic:present>
  </body>
</html:html>

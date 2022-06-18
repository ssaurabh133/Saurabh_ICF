<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
  	    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/contentstyle.css"/>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath()%>/js/md5.js"></script> 
		<script type="text/javascript" async="" src="<%=request.getContextPath()%>/js/ga.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/sha.js"></script>	
	 <script type="text/javascript"> 
	function submitanswer()
	{
	    var msg1='',msg2='',msg3='',msg4='';
		var name=document.getElementById("username").value;
        var ans1= trim(document.getElementById("ans1").value);
        var ans2= trim(document.getElementById("ans2").value);
	   
	    if(ans1=='')
	    {
	    	msg1="* Answer1 is required \n";
	    }
	    if(ans2=='')
	    {
	    	msg2="* Answer2 is required \n";
	    }
	    if((ans1.length)<2 && ans1!='')
        {
	    	msg3="* Please enter minimum 2 characters value in Answer1. \n";
         	
        }
	    if((ans2.length)<2 && ans2!='')
        {
	    	msg4="* Please enter minimum 2 characters value in Answer2. \n";
         	
        }
	    if(msg1!=''||msg2!=''||msg3!=''||msg4!='')
	    {
	    	alert(msg1+msg2+msg3+msg4);
	       	return false;
	    }
		document.getElementById("questionForm").action="<%=request.getContextPath()%>/forgetLoginAction.do?method=checkanswers&name="+name;
		document.getElementById("questionForm").submit();
	}
	</script>	
	</head>
	<body onload="enableAnchor();" >
	
	<div id="centercolumn">
	<div id="revisedcontainer">
    <fieldset> 
    <legend>Change Password</legend>
   <html:form styleId="questionForm" action="/forgetLoginAction"  method="post">
   <input type="hidden" name="username" id="username" value="<%=request.getAttribute("name") %>" />
				 <table>
				
                	<tr>
	            
		        
		          <logic:present name="questionList">
	              <logic:iterate id="questionListobj" name="questionList">
             <td>Question 1</td>
                 <td>  <html:text  property="ques1" styleId="ques1" value="${questionListobj.question1}" readonly="true" /> </td> </tr>
                  <tr>
                 
                     
                   <td>Answer 1 <font color="red">*</font></td>
                   <td>
		           <html:password value="" property="ans1" styleId="ans1" onchange="calMD5('ans1');"/>
                   </td>
                     </tr>
                     
            <tr>     <td>Question 2</td>
                   <td> <html:text value="${questionListobj.question2}" property="ques2" styleId="ques2" readonly="true" />     </td>    
                      </tr>  
                       </logic:iterate>                       
                 
                   </logic:present>              
                   </td>
                   </tr>
                  
                     <tr>
                     <td>Answer 2 <font color="red">*</font></td>
                   <td><html:password value="" property="ans2" styleId="ans2" onchange="calMD5('ans2');" />   
                   </td>
                     </tr>
                     <tr>
                     <td colspan="2" align="right">
                  <button type="button" name="questionsubmit" id="questionsubmit" class="topformbutton2" accesskey="V" title="Alt+V" onclick="return submitanswer();" >Submit</button>
                  </td>
                  </tr>
            

				 </table>
     <logic:present name="answer">
<script type="text/javascript"> 
if(('<%=request.getAttribute("answer")%>')=="incorrect")
{	var name=document.getElementById("username").value;
	
		alert("You have entered wrong answer");
 		 document.getElementById('questionForm').action="<%=request.getContextPath()%>/forgetLoginAction.do?method=getquestion&name="+name;
		document.getElementById('questionForm').submit();
}else
{	
	//var name=document.getElementById("username").value;
	<%-- var password='<%=request.getAttribute("answer")%>'; --%>
	alert("Your new password has been sent to your email address");
	//alert("Your password also sent on your mail check your mail");
 	//window.close();
	window.location.replace("<%=request.getContextPath()%>/loadLMS.do");
}
</script>
</logic:present>
	<logic:present name="login">
	 <script type="text/javascript">
	if('<%=request.getAttribute("login").toString()%>'=='error')
 	{
	
	alert("wrong User ID");
	//window.close();
	window.location.replace("<%=request.getContextPath()%>/loadLMS.do");
	}
	 </script>
	</logic:present>
 </html:form>
 </fieldset>
 </div>
 </div>
</body>
</html:html>

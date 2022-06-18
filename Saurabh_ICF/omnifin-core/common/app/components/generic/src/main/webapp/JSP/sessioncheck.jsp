<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@ page session="true" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> 

<%
	if(session.getAttribute("css")!=null)
	{
 %>
	 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/contentstyle.css"/>
	 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/subpage.css"/>
	
	 
 <%
 	}
 	else
 	{
 	
  %>
	  <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/contentstyle.css"/>
	  <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/subpage.css"/>
	 
	
  <%
  	}
   %>
   
   <%
	if(session.getAttribute("tab")!=null)
	{
 %>
	 <link href="<%=request.getContextPath() %>/${tab }/ddtabmenu.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #2 -->
<link href="<%=request.getContextPath() %>/${tab }/glowtabs.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #3 -->
<link href="<%=request.getContextPath() %>/${tab }/solidblocksmenu.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #4 -->
<link href="<%=request.getContextPath() %>/${tab }/ddcolortabs.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #5 -->
<link href="<%=request.getContextPath() %>/${tab }/chromemenu.css" type="text/css" rel="stylesheet"/>


<!--window.parent.frames.location.reload(); for refresh frame set-->

<!-- image for Fevicon -->
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/titleLogo.png" />

 <%
 	}
 	else
 	{
 	
  %>
	 	 <link href="<%=request.getContextPath() %>/ddtabmenufiles/theme1/ddtabmenu.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #2 -->
<link href="<%=request.getContextPath() %>/ddtabmenufiles/theme1/glowtabs.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #3 -->
<link href="<%=request.getContextPath() %>/ddtabmenufiles/theme1/solidblocksmenu.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #4 -->
<link href="<%=request.getContextPath() %>/ddtabmenufiles/theme1/ddcolortabs.css" type="text/css" rel="stylesheet"/>

<!-- CSS for Tab Menu #5 -->
<link href="<%=request.getContextPath() %>/ddtabmenufiles/theme1/chromemenu.css" type="text/css" rel="stylesheet"/>

<!-- image for Fevicon -->
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/titleLogo.png" />

  <%
  	}
   %>
   

<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 1);
%>
<script type="text/javascript">
 window.history.forward(1);
 
 
 
 function fnBackSpaceDisable()
   {
                 if ((event.keyCode == 8 && (event.srcElement.form == null || event.srcElement.isTextEdit == false)) || event.keyCode == 37 && event.altKey || event.keyCode == 39 && event.altKey)
                   {            
                                  event.cancelBubble = true;           
                                  event.returnValue = false;        
                   }   

     if(event.keyCode == 116) {
                                event.keyCode = 0;
                                event.returnValue=false;
                  }
                }

document.oncontextmenu=disableit
function disableit(){
  return false;
}
// for disabling the Backspace,F5 and Ctrl+R, and Ctrl+N
function showDown(pEvt) {
  evt = (pEvt) ? pEvt : ((event) ? event : null);
  if (evt) {

// Browser detection:  
var nAgt = navigator.userAgent;
var browserName  = navigator.appName;
var nameOffset,verOffset,ix;

if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
 browserName = "Opera";
}
else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
 browserName = "IE";
}
else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
 browserName = "Chrome";
}
else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
 browserName = "Safari";
}
else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
 browserName = "Firefox";
}
else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < (verOffset=nAgt.lastIndexOf('/')) ) 
{
 browserName = nAgt.substring(nameOffset,verOffset);
 if (browserName.toLowerCase()==browserName.toUpperCase()) 
 {
  browserName = navigator.appName;
 }
}
  
       if (evt.keyCode == 8 ) {
          // When backspace is pressed but not in form element
          if(pEvt) {
            if (!(pEvt.target.nodeName.toUpperCase() == 'INPUT' || pEvt.target.nodeName.toUpperCase() == 'TEXTAREA')) {
                      cancelKey(evt);
            }
          }else if (!(event.srcElement.tagName.toUpperCase() == 'INPUT' || event.srcElement.tagName.toUpperCase() == 'TEXTAREA')) {
                        cancelKey(evt);
          } 
      } else if ((browserName=='IE' || browserName=='Firefox') && (evt.charCode==0) && (evt.keyCode == 116 || evt.keyCode == 122)) {
          // When F5 is pressed
                        cancelKey(evt);

      }
      else if ((browserName=='Safari' || browserName=='Chrome') && (evt.charCode==0) && (evt.keyCode == 116 || evt.keyCode == 122)) {
           //When F5 is pressed in Safari/Chrome Web Browser
              cancelKey(evt);

      }
        else if (evt.ctrlKey && (evt.keyCode == 78 || evt.keyCode == 82)) {
          // When ctrl is pressed with R or N
                 cancelKey(evt);  

      }
  }
} 

function cancelKey(evt) {
  if (evt.preventDefault) {
      evt.preventDefault();
      return false;
  } else {
      evt.keyCode = 0;
      evt.returnValue = false;
  }
}  
// Additional code for NS
if (navigator.appName=="Netscape") {
    document.addEventListener("keypress",showDown,true);
}    
document.onkeydown = showDown
 
 //By Richa starts
 document.onkeydown=function(e) {
    var event = window.event || e;
    if (event.keyCode == 80 && e.ctrlKey) {
        event.keyCode = 0;
        return false;
    }
}
//by richa ends
 
</script>

<logic:notPresent name="userobject" scope="session">

	
	<script type="text/javascript">
			alert("<bean:message key="msg.idlSessionExpire" />");
			top.location =  '<%=request.getContextPath()%>/JSP/View.jsp';
    </script> 
    
</logic:notPresent>



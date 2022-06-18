<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'View.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<script type="text/javascript" >

   function channelmodewindow(url) { 
alert("channelmodewindow");
 // var win = window.open('http://localhost:8080/LMS/Login.jsp', '', 'channelmode');
 
 var url="<%=request.getContextPath()%>/loadLMS.do";
 var win = window.open(url, '', 'channelmode');

 var win = window.open('', '', 'channelmode');

//alert('texrt'+win ) ;        

      if (win) {

            self.moveTo(500, 500);

            window.open("", "_parent", "");

            win.close();

 

      }

}

  

  

   </script>
   <SCRIPT language=JavaScript>
<!--
function win(){
window.opener.location.href="window-refreshing.php";
self.close();
//-->
}
</SCRIPT>


	
	
	<script type='text/javascript'>


function pop()
{
//alert('jk')
  var url="<%=request.getContextPath()%>/loadLMS.do";
   
  var w = window.open(url, '', 'comma-deliminated-properties');
  window.opener.close();
  w.focus();
}

//-->
</script>
	
	
	
	
	<script type="text/javascript">
	
	function popup()
{
  // WHY ARE THERE NO PROPERTIES ?
   var url="<%=request.getContextPath()%>/loadLMS.do";
  window.open(url,'_blank');

  // THIS LINE MAKES THE POPUP THE "PARENT" WINDOW
  window.opener = close;

  // THIS CLOSES THE POPUP, NOT THE PARENT WINDOW
  window.close();
}
	
	
	</script>
	<script type="text/javascript">
	
	function closeWindow() {
	 var url="<%=request.getContextPath()%>/loadLMS.do";
newwin = window.open(url,'_parent','');
this.window.close();

}
	
	</script>
	
	

  </head>
  
  <body onload="enableAnchor();">
    	
<script language="javascript">
  //var context='/LMS' 
   var url="<%=request.getContextPath()%>/loadLMS.do";
  // var url=context+'/JSP/Login.jsp';
	var type = 'main';
	var rules = '';
	rules += ', height=screen.height';
	rules += ', width=screen.width';
	rules += ', height='+screen.height;
	rules += ', width='+(screen.width-4);
	rules += ', screenX=0, screenY=0, left=0, top=0, scrollbars=yes, resizable=no,menubar=no,toolbar=no,status=no';
	window.open(url, 'main', rules);
	//window.close();
	
	</script>
  </body>
</html>

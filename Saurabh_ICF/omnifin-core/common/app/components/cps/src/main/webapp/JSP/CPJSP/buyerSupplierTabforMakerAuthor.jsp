<%@include file="/JSP/sessioncheck.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
     
     <script type="text/javascript" src="js/popup.js"></script>
     <script type="text/javascript">
var names = new Array;
names[0]='a';
names[1]='b';
names[2]='c';
names[3]='d';
names[4]='e';
names[5]='f';
names[6]='g';
names[7]='h';
names[8]='i';
names[9]='i';

function activeTab(now){


for (z=0;z<names.length;z++)
{
	if(now == names[z])
	{
		document.getElementById(now).className='current';
		document.getElementById(now).setAttribute("onclick","return false");
	}
	else
	{
		document.getElementById(names[z]).className='';
		//document.getElementById(names[z]).removeAttribute("onclick","activeTab(id)");
		document.getElementById(names[z]).setAttribute("onclick","activeTab(id)");
	}

  }
  
}

</script>
   <base target="body" /> 
</head>
<body class="remove-bg" onload="enableAnchor();activeTab('a')" oncontextmenu="return false;" style="position: fixed; width:3000px;">
<div class="tab-bg">

<div id="ddtabs4" class="ddcolortabs"><ul>

<li><a href="Buyer.jsp" id="a" onclick="activeTab(id);"><span style="color: rgb(255, 255, 255);"> Principal Debtors Details </span></a></li>
<li><a href="Supplier.jsp" id="b" onclick="activeTab(id);"><span style="color: rgb(255, 255, 255);"> Principal Creditors Details</span></a></li>
<li><a href="<%=request.getContextPath()%>/otherRelationShipBehindAction.do?method=openOtherTab" id="c" onclick="activeTab(id);"><span style="color: rgb(255, 255, 255);"><bean:message key="lbl.others"/>  </span></a></li>
<logic:present name="viewDeal">
<li><a href="BuyerSupplierAuthor.jsp" id="c" onclick="activeTab(id);"><span style="color: rgb(255, 255, 255);">Principal Debtors/Creditors Author's</span></a></li>
</logic:present>

</ul>
</div>
<div class="ddcolortabsline"></div>

<div id="tabs">
</div>

</div>
</body>
</html>
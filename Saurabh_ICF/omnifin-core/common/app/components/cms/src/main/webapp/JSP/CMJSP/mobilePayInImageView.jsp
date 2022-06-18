<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Image View</title>
</head>
<body>
	<table cellspacing=0 cellpadding=0 width="100%" border=0>
	<tr>
	<td>
	
		<center>
			<img src="data:image/png;base64, ${imagePicture[0].photoStream}" alt="NA" />
		</center>	
	</td>
	</tr>
	</table>
	
</body>
</html>
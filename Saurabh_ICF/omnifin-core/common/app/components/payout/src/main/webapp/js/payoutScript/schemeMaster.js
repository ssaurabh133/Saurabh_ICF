
function fnSearchCode(msg){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchSchemeName").value=="" &&
	  document.getElementById("searchNarration").value=="" )
	{
     alert(msg);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("schemeSearch").action="schemeMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("schemeSearch").submit();
    return true;
    }
}

function addSchemeMaster(){
	//alert("In newAddActionCode");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("schemeSearch").action=sourcepath+"/schemeMasterDispatch.do?method=openAddScheme";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("schemeSearch").submit();
	return true;
//	document.getElementById("add").removeAttribute("disabled","true");
	     	DisButClass.prototype.EnbButMethod();
	return false;
}

function saveScheme (){
	var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='',msg10='',msg11='',msg12='',msg13='',msg14='',msg15='',msg16='',msg17='';
	var status=false;
	
	
	if(document.getElementById("schemeName").value==''){
		status=true;
		msg1='* Scheme name is required \n';
	} 
	if(document.getElementById("narration").value==''){
		status=true;
		msg2='* Narration  is required \n';
	}
	if((document.getElementById("narration").value).length>'100'){
		status=true;
		msg3='* Narration  should be less then 100 Charecter \n';
	}
	if(document.getElementById("effectiveDate").value==''){
		status=true;
		msg4='* Effective Date is required \n';
	}
	if(document.getElementById("cpDistCode").value==''){
		status=true;
		msg5='* City is required \n ';
	}
	if(document.getElementById("tat").value==''){
		status=true;
		msg6='* TAT is required \n ';
	}
	if(document.getElementById("schemeParameter").value=='CW'){
		if(document.getElementById("commissionPerCaseR").value==''){
			status=true;
			msg7='* Commission Per Case (In Rs.) is required \n ';
		}	
	}
	if(document.getElementById("schemeParameter").value=='PW'){
		if(document.getElementById("commissionPerCaseP").value==''){
			status=true;
			msg8='* Commission Per Case (In %) is required \n ';
		}	
	}
	if(document.getElementById("schemeParameter").value=='SCW'){
		var table = document.getElementById("gridTableSCW");	
		var rowCount = table.rows.length;
		for(var i=1;i<rowCount-1;i++){
			if(document.getElementById("caseFrom"+i).value==''||
			   document.getElementById("caseTo"+i).value==''||
			   document.getElementById("commissionR"+i).value==''){
				status=true;
				msg9='* In Scheme Parameter Detail Fields can not blank  \n ';
			}
		}
				
	}
	if(document.getElementById("schemeParameter").value=='SPW'){
		var table = document.getElementById("gridTableSPW");	
		var rowCount = table.rows.length;
		for(var i=1;i<rowCount-1;i++){
			
			if(document.getElementById("caseFromP"+i).value==''||
			   document.getElementById("caseToP"+i).value==''||
			   document.getElementById("commissionP"+i).value==''){
				status=true;
				msg10='* In Scheme Parameter Detail Fields can not blank \n ';
			}
		}	
	}
	if(document.getElementById("schemeParameter").value=='SFA'){
		var table = document.getElementById("gridTableSFA");	
		var rowCount = table.rows.length;
		for(var i=1;i<rowCount-1;i++){
			
			if(document.getElementById("caseFromA"+i).value==''||
			   document.getElementById("caseToA"+i).value==''||
			   document.getElementById("commissionA"+i).value==''){
				status=true;
				msg10='* In Scheme Parameter Detail Fields can not blank \n ';
			}
		}	
	}	
  if(status){
	  alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8+msg9+msg10);
	  return false;
  }else{
	var sourcepath=document.getElementById("path").value;
	
	if(document.getElementById("modifyRecord").value=='I'){
		document.getElementById("schemeMasterAdd").action=sourcepath+"/schemeMasterDispatch.do?method=saveScheme";	
	}else{
		document.getElementById("schemeMasterAdd").action=sourcepath+"/schemeMasterDispatch.do?method=updateScheme";
	}
	
	document.getElementById("processingImage").style.display = '';
	document.getElementById("schemeMasterAdd").submit();
	return true;
  }
}
function chengeParameterDiv(){
var schemeParameter=document.getElementById("schemeParameter").value;	
//alert(schemeParameter);
document.getElementById("psize").value="";
	if(schemeParameter=='CW'){
		document.getElementById("divCW").style.display = 'block';
		document.getElementById("divPW").style.display = 'none';
		document.getElementById("divSCW").style.display = 'none';
		document.getElementById("divSPW").style.display = 'none';
		document.getElementById("divSFA").style.display = 'none';
		document.getElementById("slabOn").setAttribute('disabled','disabled');
		document.getElementById("slabOn").style.display = 'none';
		document.getElementById("slabOnSpan").style.display = 'none';
		
	}
	if(schemeParameter=='PW'){
		document.getElementById("divCW").style.display = 'none';
		document.getElementById("divPW").style.display = 'block';
		document.getElementById("divSCW").style.display = 'none';
		document.getElementById("divSPW").style.display = 'none';
		document.getElementById("divSFA").style.display = 'none';
		document.getElementById("slabOn").removeAttribute('disabled','disabled');
		document.getElementById("slabOn").style.display = 'none';
		document.getElementById("slabOnSpan").style.display = 'none';
	}
	if(schemeParameter=='SCW'){
		document.getElementById("divCW").style.display = 'none';
		document.getElementById("divPW").style.display = 'none';
		document.getElementById("divSCW").style.display = 'block';
		document.getElementById("divSPW").style.display = 'none';
		document.getElementById("divSFA").style.display = 'none';
		document.getElementById("slabOn").removeAttribute('disabled','disabled');
		document.getElementById("slabOn").style.display = 'block';
		document.getElementById("slabOnSpan").style.display = 'block';
	}
	if(schemeParameter=='SPW'){
		document.getElementById("divCW").style.display = 'none';
		document.getElementById("divPW").style.display = 'none';
		document.getElementById("divSCW").style.display = 'none';
		document.getElementById("divSPW").style.display = 'block';
		document.getElementById("divSFA").style.display = 'none';
		document.getElementById("slabOn").removeAttribute('disabled','disabled');
		document.getElementById("slabOn").style.display = 'block';
		document.getElementById("slabOnSpan").style.display = 'block';
	}
	if(schemeParameter=='SFA'){
		document.getElementById("divCW").style.display = 'none';
		document.getElementById("divPW").style.display = 'none';
		document.getElementById("divSCW").style.display = 'none';
		document.getElementById("divSPW").style.display = 'none';
		document.getElementById("divSFA").style.display = 'block';
		document.getElementById("slabOn").removeAttribute('disabled','disabled');
		document.getElementById("slabOn").style.display = 'block';
		document.getElementById("slabOnSpan").style.display = 'block';
	}
}
function addrowSCW(){
	
	var table = document.getElementById("gridTableSCW");	
	var rowCount = table.rows.length;
	var psize= document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount-1;
	}
	var row = table.insertRow(rowCount-1);
	row.setAttribute('className','white1' );
    row.setAttribute('class','white1' );
    var cell1 = row.insertCell(0);
	var element1 = document.createElement("input");
	element1.type = "checkbox";
	element1.name = "chk";
	element1.id = "chk"+psize;
	cell1.appendChild(element1);
	
	var cell2 = row.insertCell(1);
	var element2 = document.createElement("input");		
	element2.type = "text";
	element2.name = "caseFrom";
	element2.id = "caseFrom"+psize;	
	element2.setAttribute('class','text');
	element2.setAttribute('className','text');	
	
	element2.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
		//document.getElementById(dynaVal).maxLength = 10;
		  var goods="0123456789.";
			    var key, keychar;
			    if (window.event)
			        key=window.event.keyCode;
			    else if (e)
			        key=e.which;
			    else
			        return true;
			    keychar = String.fromCharCode(key);
			    keychar = keychar.toLowerCase();
			    goods = goods.toLowerCase();
			    if (goods.indexOf(keychar) != -1)
			    {
			        goods="0123456789.";
			        return true;
			    }
			    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
			    {
			        goods="0123456789.";
			        return true;
			    }
			    return false;
		};
	element2.onkeyup = function(){
		checkNumber(this.value, event, 18,this.id);
	};
	element2.onblur = function(){
		formatNumber(this.value,this.id);
	};
	cell2.appendChild(element2);
	 
	var cell3 = row.insertCell(2);
	var element3 = document.createElement("input");		
	element3.type = "text";
	element3.name = "caseTo";
	element3.id = "caseTo"+psize;	
	element3.setAttribute('class','text');
	element3.setAttribute('className','text');	
	element3.onkeypress = function numbersonly(e){
			var dynaVal = this.id;
		//	document.getElementById(dynaVal).maxLength = 10;
			  var goods="0123456789.";
				    var key, keychar;
				    if (window.event)
				        key=window.event.keyCode;
				    else if (e)
				        key=e.which;
				    else
				        return true;
				    keychar = String.fromCharCode(key);
				    keychar = keychar.toLowerCase();
				    goods = goods.toLowerCase();
				    if (goods.indexOf(keychar) != -1)
				    {
				        goods="0123456789.";
				        return true;
				    }
				    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
				    {
				        goods="0123456789.";
				        return true;
				    }
				    return false;
			};
	element3.onkeyup = function(){
		checkNumber(this.value, event, 18,this.id);
	};
	element3.onblur = function(){
		formatNumber(this.value,this.id);
	};
	cell3.appendChild(element3);
	 
	var cell4 = row.insertCell(3);
	var element4 = document.createElement("input");		
	element4.type = "text";
	element4.name = "commissionR";
	element4.id = "commissionR"+psize;	
	element4.setAttribute('class','text');
	element4.setAttribute('className','text');	
	element4.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
		//document.getElementById(dynaVal).maxLength = 21;
		  var goods="0123456789.";
			    var key, keychar;
			    if (window.event)
			        key=window.event.keyCode;
			    else if (e)
			        key=e.which;
			    else
			        return true;
			    keychar = String.fromCharCode(key);
			    keychar = keychar.toLowerCase();
			    goods = goods.toLowerCase();
			    if (goods.indexOf(keychar) != -1)
			    {
			        goods="0123456789.";
			        return true;
			    }
			    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
			    {
			        goods="0123456789.";
			        return true;
			    }
			    return false;
		};
		element4.onkeyup = function(){
			checkNumber(this.value, event, 18,this.id);
		};
		element4.onblur = function(){
			formatNumber(this.value,this.id);
		};
			
	cell4.appendChild(element4);
	psize++;
	document.getElementById("psize").value=psize;
	
}

function addRowSPW(){
	var table = document.getElementById("gridTableSPW");	
	var rowCount = table.rows.length;
	var psize= document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount-1;
	}
	var row = table.insertRow(rowCount-1);
	row.setAttribute('className','white1' );
    row.setAttribute('class','white1' );
    var cell1 = row.insertCell(0);
	var element1 = document.createElement("input");
	element1.type = "checkbox";
	element1.name = "chk";
	element1.id = "chk"+psize;
	cell1.appendChild(element1);
	
	var cell2 = row.insertCell(1);
	var element2 = document.createElement("input");		
	element2.type = "text";
	element2.name = "caseFromP";
	element2.id = "caseFromP"+psize;	
	element2.setAttribute('class','text');
	element2.setAttribute('className','text');	
	element2.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
		//document.getElementById(dynaVal).maxLength = 10;
		  var goods="0123456789.";
			    var key, keychar;
			    if (window.event)
			        key=window.event.keyCode;
			    else if (e)
			        key=e.which;
			    else
			        return true;
			    keychar = String.fromCharCode(key);
			    keychar = keychar.toLowerCase();
			    goods = goods.toLowerCase();
			    if (goods.indexOf(keychar) != -1)
			    {
			        goods="0123456789.";
			        return true;
			    }
			    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
			    {
			        goods="0123456789.";
			        return true;
			    }
			    return false;
		};
		element2.onkeyup = function(){
			checkNumber(this.value, event, 18,this.id);
		};
		element2.onblur = function(){
			formatNumber(this.value,this.id);
		};	
	cell2.appendChild(element2);
	 
	var cell3 = row.insertCell(2);
	var element3 = document.createElement("input");		
	element3.type = "text";
	element3.name = "caseToP";
	element3.id = "caseToP"+psize;	
	element3.setAttribute('class','text');
	element3.setAttribute('className','text');	
	element3.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
		//document.getElementById(dynaVal).maxLength = 10;
		  var goods="0123456789.";
			    var key, keychar;
			    if (window.event)
			        key=window.event.keyCode;
			    else if (e)
			        key=e.which;
			    else
			        return true;
			    keychar = String.fromCharCode(key);
			    keychar = keychar.toLowerCase();
			    goods = goods.toLowerCase();
			    if (goods.indexOf(keychar) != -1)
			    {
			        goods="0123456789.";
			        return true;
			    }
			    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
			    {
			        goods="0123456789.";
			        return true;
			    }
			    return false;
		};
	element3.onkeyup = function(){
		checkNumber(this.value, event,18,this.id);
	};
	element3.onblur = function(){
		formatNumber(this.value,this.id);
	};
	cell3.appendChild(element3);
	 
	var cell4 = row.insertCell(3);
	var element4 = document.createElement("input");		
	element4.type = "text";
	element4.name = "commissionP";
	element4.id = "commissionP"+psize;	
	element4.setAttribute('class','text');
	element4.setAttribute('className','text');	
	element4.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
	 	document.getElementById(dynaVal).maxLength = 6;
		  var goods="0123456789.";
			    var key, keychar;
			    if (window.event)
			        key=window.event.keyCode;
			    else if (e)
			        key=e.which;
			    else
			        return true;
			    keychar = String.fromCharCode(key);
			    keychar = keychar.toLowerCase();
			    goods = goods.toLowerCase();
			    if (goods.indexOf(keychar) != -1)
			    {
			        goods="0123456789.";
			        return true;
			    }
			    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
			    {
			        goods="0123456789.";
			        return true;
			    }
			    return false;
		};
		element4.onkeyup = function(){
			checkNumber(this.value, event, 3,this.id);
		};
		element4.onblur = function(){
			formatNumber(this.value,this.id);
		};
	
	
	cell4.appendChild(element4);
	psize++;
	document.getElementById("psize").value=psize;
	
}
function addrowSFA(){
	
	var table = document.getElementById("gridTableSFA");	
	var rowCount = table.rows.length;
	var psize= document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount-1;
	}
	var row = table.insertRow(rowCount-1);
	row.setAttribute('className','white1' );
    row.setAttribute('class','white1' );
    var cell1 = row.insertCell(0);
	var element1 = document.createElement("input");
	element1.type = "checkbox";
	element1.name = "chk";
	element1.id = "chk"+psize;
	cell1.appendChild(element1);
	
	var cell2 = row.insertCell(1);
	var element2 = document.createElement("input");		
	element2.type = "text";
	element2.name = "caseFromA";
	element2.id = "caseFromA"+psize;	
	element2.setAttribute('class','text');
	element2.setAttribute('className','text');	
	
	element2.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
		//document.getElementById(dynaVal).maxLength = 10;
		  var goods="0123456789.";
			    var key, keychar;
			    if (window.event)
			        key=window.event.keyCode;
			    else if (e)
			        key=e.which;
			    else
			        return true;
			    keychar = String.fromCharCode(key);
			    keychar = keychar.toLowerCase();
			    goods = goods.toLowerCase();
			    if (goods.indexOf(keychar) != -1)
			    {
			        goods="0123456789.";
			        return true;
			    }
			    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
			    {
			        goods="0123456789.";
			        return true;
			    }
			    return false;
		};
	element2.onkeyup = function(){
		checkNumber(this.value, event, 18,this.id);
	};
	element2.onblur = function(){
		formatNumber(this.value,this.id);
	};
	cell2.appendChild(element2);
	 
	var cell3 = row.insertCell(2);
	var element3 = document.createElement("input");		
	element3.type = "text";
	element3.name = "caseToA";
	element3.id = "caseToA"+psize;	
	element3.setAttribute('class','text');
	element3.setAttribute('className','text');	
	element3.onkeypress = function numbersonly(e){
			var dynaVal = this.id;
		//	document.getElementById(dynaVal).maxLength = 10;
			  var goods="0123456789.";
				    var key, keychar;
				    if (window.event)
				        key=window.event.keyCode;
				    else if (e)
				        key=e.which;
				    else
				        return true;
				    keychar = String.fromCharCode(key);
				    keychar = keychar.toLowerCase();
				    goods = goods.toLowerCase();
				    if (goods.indexOf(keychar) != -1)
				    {
				        goods="0123456789.";
				        return true;
				    }
				    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
				    {
				        goods="0123456789.";
				        return true;
				    }
				    return false;
			};
	element3.onkeyup = function(){
		checkNumber(this.value, event, 18,this.id);
	};
	element3.onblur = function(){
		formatNumber(this.value,this.id);
	};
	cell3.appendChild(element3);
	 
	var cell4 = row.insertCell(3);
	var element4 = document.createElement("input");		
	element4.type = "text";
	element4.name = "commissionA";
	element4.id = "commissionA"+psize;	
	element4.setAttribute('class','text');
	element4.setAttribute('className','text');	
	element4.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
		//document.getElementById(dynaVal).maxLength = 21;
		  var goods="0123456789.";
			    var key, keychar;
			    if (window.event)
			        key=window.event.keyCode;
			    else if (e)
			        key=e.which;
			    else
			        return true;
			    keychar = String.fromCharCode(key);
			    keychar = keychar.toLowerCase();
			    goods = goods.toLowerCase();
			    if (goods.indexOf(keychar) != -1)
			    {
			        goods="0123456789.";
			        return true;
			    }
			    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
			    {
			        goods="0123456789.";
			        return true;
			    }
			    return false;
		};
		element4.onkeyup = function(){
			checkNumber(this.value, event, 18,this.id);
		};
		element4.onblur = function(){
			formatNumber(this.value,this.id);
		};
			
	cell4.appendChild(element4);
	psize++;
	document.getElementById("psize").value=psize;
	
}
function removeRowSCW() {
	//alert("removeallocationRow");
	 var count=0;
    try {
    var table = document.getElementById("gridTableSCW");
    var rowCount = table.rows.length;
  	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	document.getElementById("psize").value=psize;
    //alert("rowCount:--"+rowCount);
    for(var j=1;j<rowCount;j++){
    
       var row1 = table.rows[j];
      //  var chkbox1 = row1.cells[0].childNodes[0];
       var chkbox1 = row1.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
     
        if(chkbox1!=undefined ||chkbox1!=null) {
    	 if(null != chkbox1 && true == chkbox1.checked) {
		 count=count+1;
    	 }
		 }
    }
	
if(count==0){
alert("Please Select at least one row to delete");
}else{
    for(var i=1; i<rowCount; i++) {
      var row = table.rows[i];
  //  var chkbox = row.cells[0].childNodes[0];
      var chkbox = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
        if(chkbox!=undefined ||chkbox!=null){
        if(null != chkbox && true == chkbox.checked) {
     /* if(document.getElementById("chk"+i)!=undefined || document.getElementById("chk"+i) !=null){
       if(document.getElementById("chk"+i).checked==true){*/
            table.deleteRow(i);
            rowCount--;
            i--;
        }
        }
   //   }
    }
    }}catch(e) {
        alert(e);
    }
}
function removeRowSPW() {
	//alert("removeallocationRow");
	 var count=0;
    try {
    var table = document.getElementById("gridTableSPW");
    var rowCount = table.rows.length;
  	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	document.getElementById("psize").value=psize;
    //alert("rowCount:--"+rowCount);
    for(var j=1;j<rowCount;j++){
    
       var row1 = table.rows[j];
      //  var chkbox1 = row1.cells[0].childNodes[0];
       var chkbox1 = row1.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
     
        if(chkbox1!=undefined ||chkbox1!=null) {
    	 if(null != chkbox1 && true == chkbox1.checked) {
		 count=count+1;
    	 }
		 }
    }
	
if(count==0){
alert("Please Select at least one row to delete");
}else{
    for(var i=1; i<rowCount; i++) {
      var row = table.rows[i];
  //  var chkbox = row.cells[0].childNodes[0];
      var chkbox = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
        if(chkbox!=undefined ||chkbox!=null){
        if(null != chkbox && true == chkbox.checked) {
     /* if(document.getElementById("chk"+i)!=undefined || document.getElementById("chk"+i) !=null){
       if(document.getElementById("chk"+i).checked==true){*/
            table.deleteRow(i);
            rowCount--;
            i--;
        }
        }
   //   }
    }
    }}catch(e) {
        alert(e);
    }
}
function removeRowSFA() {
	//alert("removeallocationRow");
	 var count=0;
    try {
    var table = document.getElementById("gridTableSFA");
    var rowCount = table.rows.length;
  	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	document.getElementById("psize").value=psize;
    //alert("rowCount:--"+rowCount);
    for(var j=1;j<rowCount;j++){
    
       var row1 = table.rows[j];
      //  var chkbox1 = row1.cells[0].childNodes[0];
       var chkbox1 = row1.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
     
        if(chkbox1!=undefined ||chkbox1!=null) {
    	 if(null != chkbox1 && true == chkbox1.checked) {
		 count=count+1;
    	 }
		 }
    }
	
if(count==0){
alert("Please Select at least one row to delete");
}else{
    for(var i=1; i<rowCount; i++) {
      var row = table.rows[i];
  //  var chkbox = row.cells[0].childNodes[0];
      var chkbox = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
        if(chkbox!=undefined ||chkbox!=null){
        if(null != chkbox && true == chkbox.checked) {
     /* if(document.getElementById("chk"+i)!=undefined || document.getElementById("chk"+i) !=null){
       if(document.getElementById("chk"+i).checked==true){*/
            table.deleteRow(i);
            rowCount--;
            i--;
        }
        }
   //   }
    }
    }}catch(e) {
        alert(e);
    }
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

function validRepayDateInScheme()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var bDate=document.getElementById("businessdate").value;
	var repayEffectiveDate=document.getElementById("effectiveDate").value;
    var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
  
    if(dt1<dt3)
	{
		msg="Effective date should be equal or greater than bussiness Date ";
		document.getElementById("effectiveDate").value='';

	}

	if(msg!='')
	{
		alert(msg);
		return false;
	}
	else
	{
		return true;
	}
}



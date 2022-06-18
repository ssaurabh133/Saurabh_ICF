function searchScoring()
{
	if(document.getElementById("searchScoringDesc").value=='')
	{
		alert("*  Please select at least one field.");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	var sourcepath=document.getElementById("path").value;
	document.getElementById("scoringMasterSearch").action=sourcepath+"/scoringMasterBehind.do";
	document.getElementById("scoringMasterSearch").submit();
	
}

function openAdd()
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("scoringMasterSearch").action=sourcepath+"/scoringMasterDispatch.do?method=openAddScoringMaster";
	document.getElementById("scoringMasterSearch").submit();
	
}

function openParameterValue()
{
	var sourcepath=document.getElementById("path").value;
	var scoringId=document.getElementById("scoringId").value;
	var url=sourcepath+"/scoringMasterDispatch.do?method=openParameterValue&scoringId="+scoringId;
	window.open(url,'parameter','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	
}
function saveScoringMaster(){
	
	var modifyRecord=document.getElementById("modifyRecord").value;
	var sourcepath=document.getElementById("path").value;
	var sumWeightage=0;
	var status=true;
	var msg1="",msg2="",msg3="",msg4="",msg5="",msg6="",msg7="",msg8="",msg9="";
	if(document.getElementById("scoreingDesc").value==""){
		status=false;
		msg1="* Scoring Description is required\n"
	}
	if(document.getElementById("product").value==""){
		status=false;
		msg2="* Product is requird\n"
	}
	if(document.getElementById("scheme").value==""){
		status=false;
		msg3="* Scheme is requird\n"
	}
	var lbxScoringParam=document.getElementsByName("lbxScoringParam");
	var weightage=document.getElementsByName("weightage");
	var defaultValue=document.getElementsByName("defaultValue");
	for(var i=0;i<lbxScoringParam.length;i++){
	if(lbxScoringParam[i].value==""){
		status=false;
		msg4="* Scoring Param is requird\n"
	}
	if(weightage[i].value==""){
		status=false;
		msg5="* Weightage is requird\n"
	}
   if(defaultValue[i].value==""){
	   status=false;
		msg6="* Default is requird\n"
	}
   if(weightage[i].value!=""&&weightage[i].value<1){
		status=false;
		msg5="* Weightage should be greater than 0\n"
	}
  if(defaultValue[i].value!=""&&defaultValue[i].value<1){
	   status=false;
		msg6="* Default should be greater than 0\n"
	}
  for(var k=0;k<lbxScoringParam.length;k++){
	  if(i!=k){
		  if(lbxScoringParam[i].value==lbxScoringParam[k].value){
				status=false;
				msg7="* Scoring Param should not be duplicate\n"
			}  
	  }
  }

    if(weightage[i].value!=null&&weightage[i].value!=""){
    	sumWeightage=sumWeightage+parseInt(weightage[i].value);
    }
    if(defaultValue[i].value!=""&&defaultValue[i].value>5){
 	   status=false;
 		msg8="* Default Should not be greater than 5\n"
 	}
	}
	//alert("sumWeightage"+sumWeightage);
	if(sumWeightage>100){
		status=false;
 		msg9="* Sum of Weigthage should not be greater than 100\n"
	}
	if(!status){
		alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8+msg9);
	}else{
	if(modifyRecord=="I"){
		document.getElementById("scoringMasterAdd").action=sourcepath+"/scoringMasterDispatch.do?method=saveScoringMaster";	
	}else{
		document.getElementById("scoringMasterAdd").action=sourcepath+"/scoringMasterDispatch.do?method=updateScoringMaster";
	}
	
	document.getElementById("scoringMasterAdd").submit();
	}
}

function saveParamValue(){
	var sourcepath=document.getElementById("path").value;
	var msg1="",msg2="",msg3="",msg4="",msg5="",msg6="",msg7="",msg8="",msg9="";
	var lbxScoringParam=document.getElementsByName("lbxScoringParam");
	var from=document.getElementsByName("from");
	var to=document.getElementsByName("to");
	var charValue=document.getElementsByName("charValue");
	var score=document.getElementsByName("score");
	var hidDataType=document.getElementsByName("hidDataType");
	var status=true
	var fromArr=new Array();
	var toArr=new Array();
	var k=0;
	for(var i=0;i<lbxScoringParam.length;i++){
	if(lbxScoringParam[i].value==""){
		status=false;
		msg1="* Scoring Param is requird\n"
	}
	if(hidDataType!=""&& hidDataType[i].value=="INT"&&from[i].value==""){
		status=false;
		msg2="* From is requird\n"
	}
	if(hidDataType!=""&& hidDataType[i].value=="INT"&&to[i].value==""){
		status=false;
		msg3="* To is requird\n"
	}
	if(hidDataType!=""&& hidDataType[i].value=="STRING"&&charValue[i].value==""){
		status=false;
		msg4="* CharValue is requird\n"
	}
	if(score[i].value==""){
		status=false;
		msg5="* Score is requird\n"
	}
	if(hidDataType[i].value=="INT"){
		if(from[i].value!=""&&to[i].value!=""){
			if(parseInt(from[i].value)>parseInt(to[i].value)){
				status=false;
				msg6="* From value can not greater Than To value \n"
			}
			/*if(i<lbxScoringParam.length-1&&from[i+1].value!=""){
				if(parseInt(to[i].value)>parseInt(from[i+1].value)){
					status=false;
					msg7="* Next From Value should be greater Than To Value \n"
				}	
			}*/
			
		}
	}
if(from[i].value!=""&&to[i].value!=""){

		fromArr[k]=parseInt(from[i].value);
		toArr[k]=parseInt(to[i].value);	
	k++;
	
}

	}

if(fromArr.length>1){
	for(var j=0;j<fromArr.length;j++){
		
		if(j<fromArr.length-1){
			
			if(parseInt(toArr[j])>parseInt(fromArr[j+1])){
				status=false;
				msg7="* Next From Value should be greater Than To Value  \n"	
			}
		}
	}
		
	}

	
	if(!status){
		alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7);	
	}else{
		document.getElementById("scoringMasterAddParamVal").action=sourcepath+"/scoringMasterDispatch.do?method=saveScoringMasterParamValue";
		document.getElementById("scoringMasterAddParamVal").submit();
	}
}

function addROW(){
	var table = document.getElementById("gridTable");
    var basePath=document.getElementById("contextPath").value;
    var newdiv = document.createElement('span');
	var rowCount = table.rows.length;
	
	var psize= document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	
	var row = table.insertRow(rowCount);
	
	
	row.setAttribute('className','white1' );
    row.setAttribute('class','white1' );
    
    var cell0 = row.insertCell(0);
	var element0 = document.createElement("input");
	element0.type = "checkbox";
	element0.name = "chk";
	element0.id = "chk"+psize;
	cell0.appendChild(element0);
    
	var cell1 = row.insertCell(1);
	var element1 = document.createElement("input");
	element1.type = "text";
	element1.name = "scoringParam";
	element1.id = "scoringParam"+psize;
	element1.setAttribute('class','text');
	element1.setAttribute('className','text');	
	element1.setAttribute('readOnly','true');
	cell1.appendChild(element1);

	var element3 = document.createElement("input");		
	element3.type = "hidden";
	element3.name = "lbxScoringParamHID"+psize;;	
	element3.id = "lbxScoringParamHID"
	cell1.appendChild(element3);
	
	var element33 = document.createElement("input");		
	element33.type = "hidden";
	element33.name = "lbxScoringParam";	
	element33.id = "lbxScoringParam"+psize;
	cell1.appendChild(element33);
	
	newdiv.innerHTML ='<input type="button" value="" name="scoringParamLOV" id="scoringParamLOV" onclick="openLOVCommon(475,\'scoringMasterAdd\',\'scoringParam'+psize+'\',\'\',\'\',\'\',\'\',\'\',\'lbxScoringParam'+psize+'\')" class="lovbutton"/>';
	cell1.appendChild(newdiv);
	
	  
	
	var cell2 = row.insertCell(2);
	var element2 = document.createElement("input");
	element2.type = "text";
	element2.name = "weightage";
	element2.id = "weightage"+psize;
	element2.setAttribute('class','text');
	element2.setAttribute('className','text' );
	element2.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
		document.getElementById(dynaVal).maxLength = 3;
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
	cell2.appendChild(element2);
	
	
	var cell3 = row.insertCell(3);
	var element3 = document.createElement("input");
	element3.type = "text";
	element3.name = "defaultValue";
	element3.id = "defaultValue"+psize;
	element3.setAttribute('class','text');
	element3.setAttribute('className','text' );
	element3.onkeypress = function numbersonly(e){
		var dynaVal = this.id;
		document.getElementById(dynaVal).maxLength = 1;
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
	cell3.appendChild(element3);
		
	psize++;
	document.getElementById("psize").value=psize;
}

function removeRow() {
	//alert("removeallocationRow");
	 var count=0;
    try {
    var table = document.getElementById("gridTable");
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
function allChecked()
{
	 //alert("ok");
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
	// alert(c);
	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
	
}
function numbersonlyForScoring(e){
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}
 function checkId(count){
	 document.getElementById("checkIdVal").value=count;
 }
 function addRowParamValue(){
		var table = document.getElementById("gridTable");
	    var basePath=document.getElementById("contextPath").value;
	    var newdiv = document.createElement('span');
		var rowCount = table.rows.length;
		
		var psize= document.getElementById("psize").value;
		if(psize==""){
			psize=rowCount;
		}
		
		var row = table.insertRow(rowCount);
		
		
		row.setAttribute('className','white1' );
	    row.setAttribute('class','white1' );
	    
	    var cell0 = row.insertCell(0);
		var element0 = document.createElement("input");
		element0.type = "checkbox";
		element0.name = "chk";
		element0.id = "chk"+psize;
		cell0.appendChild(element0);
	    
		var cell1 = row.insertCell(1);
		var element1 = document.createElement("input");
		element1.type = "text";
		element1.name = "scoringParam";
		element1.id = "scoringParam"+psize;
		element1.setAttribute('class','text');
		element1.setAttribute('className','text');	
		element1.setAttribute('readOnly','true');
		cell1.appendChild(element1);

		var element3 = document.createElement("input");		
		element3.type = "hidden";
		element3.name = "lbxScoringParamHID"+psize;;	
		element3.id = "lbxScoringParamHID"
		cell1.appendChild(element3);
		
		var element33 = document.createElement("input");		
		element33.type = "hidden";
		element33.name = "lbxScoringParam";	
		element33.id = "lbxScoringParam"+psize;
		cell1.appendChild(element33);
		
		var element333 = document.createElement("input");		
		element333.type = "hidden";
		element333.name = "hidDataType";	
		element333.id = "hidDataType"+psize;
		cell1.appendChild(element333);
		
		newdiv.innerHTML ='<input type="button" value="" name="scoringParamLOV" id="scoringParamLOV" onclick="checkId(\''+psize+'\');openLOVCommon(474,\'scoringMasterAddParamVal\',\'scoringParam'+psize+'\',\'scoringId\',\'lbxScoringParam'+psize+'\',\'scoringId\',\'Scoring Id Can not be null\',\'enaDisIntStringField\',\'lbxScoringParam'+psize+'\',\'hidDataType'+psize+'\',\'\');" class="lovbutton"/>';
		cell1.appendChild(newdiv);
		
		  
		
		var cell2 = row.insertCell(2);
		var element2 = document.createElement("input");
		element2.type = "text";
		element2.name = "from";
		element2.id = "from"+psize;
		element2.setAttribute('class','text');
		element2.setAttribute('className','text' );
		element2.setAttribute('readonly','true');
		element2.onkeypress = function numbersonly(e){
			var dynaVal = this.id;
			document.getElementById(dynaVal).maxLength = 21;
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
		cell2.appendChild(element2);
		
		
		var cell3 = row.insertCell(3);
		var element3 = document.createElement("input");
		element3.type = "text";
		element3.name = "to";
		element3.id = "to"+psize;
		element3.setAttribute('class','text');
		element3.setAttribute('className','text' );
		element3.setAttribute('readonly','true');
		element3.onkeypress = function numbersonly(e){
			var dynaVal = this.id;
			document.getElementById(dynaVal).maxLength = 21;
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
		cell3.appendChild(element3);
		
		var cell4 = row.insertCell(4);
		var element4 = document.createElement("input");
		element4.type = "text";
		element4.name = "charValue";
		element4.id = "charValue"+psize;
		element4.setAttribute('class','text');
		element4.setAttribute('className','text' );
		element4.setAttribute('readonly','true');
		cell4.appendChild(element4);
		
		var cell5 = row.insertCell(5);
		var element5 = document.createElement("input");
		element5.type = "text";
		element5.name = "score";
		element5.id = "score"+psize;
		element5.setAttribute('class','text');
		element5.setAttribute('className','text' );
		element5.onkeypress = function numbersonly(e){
			var dynaVal = this.id;
			document.getElementById(dynaVal).maxLength = 21;
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
		cell5.appendChild(element5);
		psize++;
		document.getElementById("psize").value=psize;
 }
 //method added by neeraj
 function generateJSPView()
 {
	 //alert('generateJSPView');
	 var sourcepath=document.getElementById("contextPath").value;
	 document.getElementById("scoringDtl").action=sourcepath+"/scoringGeneration.do?method=generateJSPView";
	 document.getElementById("scoringDtl").submit();
 }
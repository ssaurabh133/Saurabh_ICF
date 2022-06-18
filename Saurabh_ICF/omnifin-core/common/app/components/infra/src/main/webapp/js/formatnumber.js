/////Sanjog///////
var message="Right Click is disabled";
function clickIE4(){
if (event.button==2){
alert(message);
return false;
}
}

function clickNS4(e){
if (document.layers||document.getElementById&&!document.all){
if (e.which==2||e.which==3){
alert(message);
return false;
}
}
}

if (document.layers){
document.captureEvents(Event.MOUSEDOWN);
document.onmousedown=clickNS4;
}
else if (document.all&&!document.getElementById){
document.onmousedown=clickIE4;
}
document.oncontextmenu=new Function("alert(message);return false")
function numbersonly(e, san, Max){
	var dynaVal = san;
	document.getElementById(dynaVal).maxLength = Max+3;
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}


//Added By Saorabh Maheshwari Start
function numbersonly1(e, san, Max){
	var dynaVal = san;
	//alert(document.getElementById(dynaVal).maxLength = Max+3);
	document.getElementById(dynaVal).maxLength = Max+8;
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
}

function checkNumber1(val, e, Max, san){
	var dynaVal = san;
	var keyVal = e.keyCode;
	var textValue = val;
	var textValueLen = document.getElementById(dynaVal).maxLength = Max+3;	
	var removeLast = 'NO';
	var finlDone = 'NO';
	var lastLength = '0';
	var firstVal = Max;
	if (e.shiftKey==1){
		finlDone = 'YES';
		newValue = val;
	}else{
		if(keyVal != '13' && keyVal != '16'){ 					
			if((keyVal > '95' && keyVal < '106') || (keyVal > '47' && keyVal < '58')){
				if(textValue.indexOf('.') > -1){
					var splt = textValue.split('.');
					if(splt[1].length > 7){						
						var f1V = parseInt(splt[0].length);
						var f2V = f1V+8;
						lastLength = f2V;
						removeLast = 'YES';
					}
				}else{
					if(textValueLen > firstVal){
						removeLast = 'YES';
						lastLength = firstVal;
					}
				}
			}else if(keyVal == '110' || keyVal == '190'){
				if(textValue.indexOf('.') != textValue.lastIndexOf('.')){
					var splt = textValue.split('.');
					var f1V = parseInt(splt[0].length);
					var f2V = f1V+1;
					lastLength = f2V;					
					removeLast = 'YES';
				}
			}else{
				if(keyVal > '105'){
						var ascToStr = textValue;
				}else{
					var ascToStr = String.fromCharCode(keyVal);		
					if(ascToStr.toUpperCase() === ascToStr && ascToStr.toLowerCase() !== ascToStr){
						
						var textValue = ascToStr.toUpperCase();
					}
				}
				
				var getCharLen = textValue.indexOf(ascToStr);
				
				if(lastLength > Max){
					lastLength = getCharLen;
				}else if(getCharLen == 0){
					lastLength = getCharLen;		
				}
			}
		}
	}	
	
	if(removeLast === 'YES'){
		var newtextValueLen = lastLength;
		var newValue = textValue.substr(0, newtextValueLen);
		finlDone = 'YES';
	}
	
	if(finlDone === 'YES'){
		document.getElementById(dynaVal).value = '';
		document.getElementById(dynaVal).value = newValue;
	}
}
//Added By Saorabh Maheshwari End

function formatNumber(val, san)
{
	if(val != ''){

		var expAllVal = val.split('.');
		var firstVal = expAllVal[0];
		var strToArray = new Array();
		var secVal = '';
		var dynaVal = san;
		
		if(expAllVal.length == 2){
			var secVal = expAllVal[1];
		}
		
		var lengthOffirstVal = firstVal.length;
		var roundVal = lengthOffirstVal/3;
		var remind = lengthOffirstVal % 3;
		
		if(remind == 0){
			var findRoundOrNot = roundVal;
		}else{
			var findRoundOrNot = Math.ceil(roundVal);
		}
		if(findRoundOrNot > 0){
			var forLoopFindRoundOrNot = findRoundOrNot-1; 
			var jIndex = lengthOffirstVal;	              
			for(i=forLoopFindRoundOrNot; i >= 0 ; i--){
				if(jIndex > 2){
					var jIndex = jIndex-3;	
					var lastId = 3;
				}
				else{
					var lastId = jIndex;
					var jIndex = 0;							
				}				
				
				strToArray[i] = firstVal.substr(jIndex, lastId);
				
			}
			if(firstVal.indexOf(',') < 0){
				var finalValueForDisp = strToArray.join(',');
			}
			if(secVal != ''){
				finalValueForDisp = finalValueForDisp+'.'+secVal;
			}else{
		
			
					finalValueForDisp = finalValueForDisp+'.00';
				
			}
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = finalValueForDisp;
		}
		if(val.indexOf(',') > 0){
			  	var origString = val;
					var inChar = ',';
					var newString = origString.split(inChar);
					newString = newString.join('');
				
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newString;
			//alert(newString);
			}
		
	} 
}

//neeraj
function calculatNetPayRes(val)
{
	var totalPayable=document.getElementById('totalPayable').value;
	var totalRecevable=document.getElementById('totalRecevable').value;
	document.getElementById('saveCompleted').value='N';
	document.getElementById('changeWaiveOff').value='Y';
	document.getElementById('waiveAllocated').value='N';
	
	var totp=0.00;
	var totr=0.00;
	var totvalue=0.00;
	if(val=="")
	{
		document.getElementById('waiveOffAmount').value="0.00";
		val=0;
	}
	if(totalPayable!='')
		totp=removeComma(totalPayable);
	if(totalRecevable!='')
		totr=removeComma(totalRecevable);
	if(val!='')
		totvalue=removeComma(val);
	
	var amount=parseFloat(totr)-parseFloat(totp)-parseFloat(totvalue);
	amount=amount.toFixed(2);
	amount=amount+"";	
	formatNumber(amount,'netReceivablePayable');
}
function checkNumber(val, e, Max, san){
	var dynaVal = san;
	var keyVal = e.keyCode;
	var textValue = val;
	var textValueLen = document.getElementById(dynaVal).maxLength = Max+3;	
	var removeLast = 'NO';
	var finlDone = 'NO';
	var lastLength = '0';
	var firstVal = Max;
	
	if (e.shiftKey==1){
		finlDone = 'YES';
		newValue = val;
	}else{
		if(keyVal != '13' && keyVal != '16'){ 					
			if((keyVal > '95' && keyVal < '106') || (keyVal > '47' && keyVal < '58')){
				if(textValue.indexOf('.') > -1){
					var splt = textValue.split('.');
					if(splt[1].length > 2){						
						var f1V = parseInt(splt[0].length);
						var f2V = f1V+3;
						lastLength = f2V;
						removeLast = 'YES';
					}
				}else{
					if(textValueLen > firstVal){
						removeLast = 'YES';
						lastLength = firstVal;
					}
				}
			}else if(keyVal == '110' || keyVal == '190'){
				if(textValue.indexOf('.') != textValue.lastIndexOf('.')){
					var splt = textValue.split('.');
					var f1V = parseInt(splt[0].length);
					var f2V = f1V+1;
					lastLength = f2V;					
					removeLast = 'YES';
				}
			}else{
				if(keyVal > '105'){
						var ascToStr = textValue;
				}else{
					var ascToStr = String.fromCharCode(keyVal);		
					if(ascToStr.toUpperCase() === ascToStr && ascToStr.toLowerCase() !== ascToStr){
						
						var textValue = ascToStr.toUpperCase();
					}
				}
				
				
				var getCharLen = textValue.indexOf(ascToStr);
				
				if(lastLength > Max){
					lastLength = getCharLen;
				}else if(getCharLen == 0){
					lastLength = getCharLen;		
				}
			}
		}
	}	
	
	
	
	if(removeLast === 'YES'){
		var newtextValueLen = lastLength;
		var newValue = textValue.substr(0, newtextValueLen);
		finlDone = 'YES';
	}
	
	if(finlDone === 'YES'){
		document.getElementById(dynaVal).value = '';
		document.getElementById(dynaVal).value = newValue;
	}
}
function keyUpNumber(val, e, Max, san){
if(val.indexOf(',') > 0){
		var dynaVal = san;
		document.getElementById(dynaVal).maxLength = Max+3;
		var origString = val;
		var inChar = ',';
		var outChar = '.';
		var newString = origString.split(outChar);
		var newString = origString.split(inChar);
		newString = newString.join('');
		document.getElementById(dynaVal).value = '';
		document.getElementById(dynaVal).value = newString;
}
}

//-----------4


function fourDecimalNumber(val, san){
	
	

	if(val != ''){

		var expAllVal = val.split('.');
		var firstVal = expAllVal[0];
		var strToArray = new Array();
		var secVal = '';
		var dynaVal = san;
		
		if(expAllVal.length == 2){
			var secVal = expAllVal[1];
			
		}
		var lengthOffirstVal = firstVal.length;
		var roundVal = lengthOffirstVal/3;
		var remind = lengthOffirstVal % 3;
		
		if(remind == 0){
			var findRoundOrNot = roundVal;
		}else{
			var findRoundOrNot = Math.ceil(roundVal);
		}
		if(findRoundOrNot > 0){
			var forLoopFindRoundOrNot = findRoundOrNot-1; 
			var jIndex = lengthOffirstVal;	              
			for(i=forLoopFindRoundOrNot; i >= 0 ; i--){
				if(jIndex > 2){
					var jIndex = jIndex-3;	
					var lastId = 3;
				}
				else{
					var lastId = jIndex;
					var jIndex = 0;							
				}				
				
				strToArray[i] = firstVal.substr(jIndex, lastId);
				
			}
			if(firstVal.indexOf(',') < 0){
				var finalValueForDisp = strToArray.join(',');
			}
			if(secVal != ''){
				finalValueForDisp = finalValueForDisp+'.'+secVal;
			}else{
				finalValueForDisp = finalValueForDisp+'.0000';
			}
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = finalValueForDisp;
		}
		if(val.indexOf(',') > 0){
			  	var origString = val;
					var inChar = ',';
					var newString = origString.split(inChar);
					newString = newString.join('');
				
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newString;
			//alert(newString);
			}
	} 
}


function fourDecimalcheckNumber(val, e, Max, san){
	var dynaVal = san;
	var keyVal = e.keyCode;
	var textValue = val;
	var textValueLen = document.getElementById(dynaVal).maxLength = Max+5;	
	var removeLast = 'NO';
	var finlDone = 'NO';
	var lastLength = '0';
	var firstVal = Max;
	
	if (e.shiftKey==1){
		finlDone = 'YES';
		newValue = val;
	}else{
		if(keyVal != '13' && keyVal != '16'){ 					
			if((keyVal > '95' && keyVal < '106') || (keyVal > '47' && keyVal < '58')){
				if(textValue.indexOf('.') > -1){
					var splt = textValue.split('.');
					if(splt[1].length > 4){						
						var f1V = parseInt(splt[0].length);
						var f2V = f1V+5;
						lastLength = f2V;
						removeLast = 'YES';
					}
				}else{
					if(textValueLen > firstVal){
						removeLast = 'YES';
						lastLength = firstVal;
					}
				}
			}else if(keyVal == '110' || keyVal == '190'){
				if(textValue.indexOf('.') != textValue.lastIndexOf('.')){
					var splt = textValue.split('.');
					var f1V = parseInt(splt[0].length);
					var f2V = f1V+1;
					lastLength = f2V;					
					removeLast = 'YES';
				}
			}else{
				if(keyVal > '105'){
						var ascToStr = textValue;
				}else{
					var ascToStr = String.fromCharCode(keyVal);		
					if(ascToStr.toUpperCase() === ascToStr && ascToStr.toLowerCase() !== ascToStr){
						
						var textValue = ascToStr.toUpperCase();
					}
				}
				
				
				var getCharLen = textValue.indexOf(ascToStr);
				
				if(lastLength > Max){
					lastLength = getCharLen;
				}else if(getCharLen == 0){
					lastLength = getCharLen;		
				}
			}
		}
	}	
	
	
	
	if(removeLast === 'YES'){
		var newtextValueLen = lastLength;
		var newValue = textValue.substr(0, newtextValueLen);
		finlDone = 'YES';
	}
	
	if(finlDone === 'YES'){
		document.getElementById(dynaVal).value = '';
		document.getElementById(dynaVal).value = newValue;
	}
}
function fourDecimalkeyUpNumber(val, e, Max, san){
if(val.indexOf(',') > 0){
		var dynaVal = san;
		document.getElementById(dynaVal).maxLength = Max+5;
		var origString = val;
		var inChar = ',';
		var outChar = '.';
		var newString = origString.split(outChar);
		var newString = origString.split(inChar);
		newString = newString.join('');
		document.getElementById(dynaVal).value = '';
		document.getElementById(dynaVal).value = newString;
}
}


function fourDecimalnumbersonly(e, san, Max){
	var dynaVal = san;
	document.getElementById(dynaVal).maxLength = Max+5;
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}



//----------------------7


function sevenDecimalNumber(val, san){

	if(val != ''){

		var expAllVal = val.split('.');
		var firstVal = expAllVal[0];
		var strToArray = new Array();
		var secVal = '';
		var dynaVal = san;
		
		if(expAllVal.length == 2){
			var secVal = expAllVal[1];
			
		}
		var lengthOffirstVal = firstVal.length;
		var roundVal = lengthOffirstVal/3;
		var remind = lengthOffirstVal % 3;
		
		if(remind == 0){
			var findRoundOrNot = roundVal;
		}else{
			var findRoundOrNot = Math.ceil(roundVal);
		}
		if(findRoundOrNot > 0){
			var forLoopFindRoundOrNot = findRoundOrNot-1; 
			var jIndex = lengthOffirstVal;	              
			for(i=forLoopFindRoundOrNot; i >= 0 ; i--){
				if(jIndex > 2){
					var jIndex = jIndex-3;	
					var lastId = 3;
				}
				else{
					var lastId = jIndex;
					var jIndex = 0;							
				}				
				
				strToArray[i] = firstVal.substr(jIndex, lastId);
				
			}
			if(firstVal.indexOf(',') < 0){
				var finalValueForDisp = strToArray.join(',');
			}
			if(secVal != ''){
				finalValueForDisp = finalValueForDisp+'.'+secVal;
			}else{
				finalValueForDisp = finalValueForDisp+'.00';
			}
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = finalValueForDisp;
		}
		if(val.indexOf(',') > 0){
			  	var origString = val;
					var inChar = ',';
					var newString = origString.split(inChar);
					newString = newString.join('');
				
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newString;
			//alert(newString);
			}
	} 
}


function sevenDecimalcheckNumber(val, e, Max, san){
	var dynaVal = san;
	var keyVal = e.keyCode;
	var textValue = val;
	var textValueLen = document.getElementById(dynaVal).maxLength = Max+8;	
	var removeLast = 'NO';
	var finlDone = 'NO';
	var lastLength = '0';
	var firstVal = Max;
	
	if (e.shiftKey==1){
		finlDone = 'YES';
		newValue = val;
	}else{
		if(keyVal != '13' && keyVal != '16'){ 					
			if((keyVal > '95' && keyVal < '106') || (keyVal > '47' && keyVal < '58')){
				if(textValue.indexOf('.') > -1){
					var splt = textValue.split('.');
					if(splt[1].length > 7){						
						var f1V = parseInt(splt[0].length);
						var f2V = f1V+8;
						lastLength = f2V;
						removeLast = 'YES';
					}
				}else{
					if(textValueLen > firstVal){
						removeLast = 'YES';
						lastLength = firstVal;
					}
				}
			}else if(keyVal == '110' || keyVal == '190'){
				if(textValue.indexOf('.') != textValue.lastIndexOf('.')){
					var splt = textValue.split('.');
					var f1V = parseInt(splt[0].length);
					var f2V = f1V+1;
					lastLength = f2V;					
					removeLast = 'YES';
				}
			}else{
				if(keyVal > '105'){
						var ascToStr = textValue;
				}else{
					var ascToStr = String.fromCharCode(keyVal);		
					if(ascToStr.toUpperCase() === ascToStr && ascToStr.toLowerCase() !== ascToStr){
						
						var textValue = ascToStr.toUpperCase();
					}
				}
				
				
				var getCharLen = textValue.indexOf(ascToStr);
				
				if(lastLength > Max){
					lastLength = getCharLen;
				}else if(getCharLen == 0){
					lastLength = getCharLen;		
				}
			}
		}
	}	
	
	
	
	if(removeLast === 'YES'){
		var newtextValueLen = lastLength;
		var newValue = textValue.substr(0, newtextValueLen);
		finlDone = 'YES';
	}
	
	if(finlDone === 'YES'){
		document.getElementById(dynaVal).value = '';
		document.getElementById(dynaVal).value = newValue;
	}
}
function sevenDecimalkeyUpNumber(val, e, Max, san){
if(val.indexOf(',') > 0){
		var dynaVal = san;
		document.getElementById(dynaVal).maxLength = Max+8;
		var origString = val;
		var inChar = ',';
		var outChar = '.';
		var newString = origString.split(outChar);
		var newString = origString.split(inChar);
		newString = newString.join('');
		document.getElementById(dynaVal).value = '';
		document.getElementById(dynaVal).value = newString;
}
}


function sevenDecimalnumbersonly(e, san, Max){
	var dynaVal = san;
	document.getElementById(dynaVal).maxLength = Max+8;
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}
function formatNumberMinus(val, san)
{
	if(val != ''){

		var expAllVal = val.split('.');
		var firstVal = expAllVal[0];
		var strToArray = new Array();
		var secVal = '';
		var dynaVal = san;
	
		if(expAllVal.length == 2){
			var secVal = expAllVal[1];
		}
		
		var lengthOffirstVal = firstVal.length;
		var roundVal = lengthOffirstVal/3;
		var remind = lengthOffirstVal % 3;
		
		if(remind == 0){
			var findRoundOrNot = roundVal;
		}else{
			var findRoundOrNot = Math.ceil(roundVal);
		}
		if(findRoundOrNot > 0){
			var forLoopFindRoundOrNot = findRoundOrNot-1; 
			var jIndex = lengthOffirstVal;	              
			for(i=forLoopFindRoundOrNot; i >= 0 ; i--){
				if(jIndex > 2){
					var jIndex = jIndex-3;	
					var lastId = 3;
				}
				else{
					var lastId = jIndex;
					var jIndex = 0;							
				}				
				
				strToArray[i] = firstVal.substr(jIndex, lastId);
				
			}
			if(firstVal.indexOf(',') < 0){
				var finalValueForDisp = strToArray.join(',');
			}
			if(secVal != ''){
			
			
				finalValueForDisp = finalValueForDisp+'.'+secVal;
			
			}else{
	
				
					finalValueForDisp = finalValueForDisp+'.00';
			}
	
			if(finalValueForDisp.match("-.00")){
				
				finalValueForDisp = '0.00';
			}
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = finalValueForDisp;
		}
		if(val.indexOf(',') > 0){
			  	var origString = val;
					var inChar = ',';
					var newString = origString.split(inChar);
					newString = newString.join('');
				
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newString;
			//alert(newString);
			}
		
	} 
}


		$(function() {
		$("#datepicker").datepicker({
			changeMonth: true,
			changeYear: true,
            yearRange: '1950:2010',
            showOn: 'both',
			buttonImage: 'images/calendar.gif',
			buttonImageOnly: true
	

		});

	});
	
    
	$(function() {
		$("#tabs").tabs();
	});

	
<!--
function popitup(url) {
	newwindow=window.open(url,'name','height=370,width=800,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}


function display(obj) {

txt = obj.options[obj.selectedIndex].value;


  document.getElementById('loan_no').style.display = 'none';
      document.getElementById('loan_no_dropdown').style.display = 'none';
     document.getElementById('no').style.display = 'none';
      document.getElementById('t3').style.display = 'none';
if(txt=='1')
{
     document.getElementById('loan_no').style.display = '';
      document.getElementById('loan_no_dropdown').style.display = '';
       document.getElementById('no').style.display = '';
      document.getElementById('t3').style.display = '';
 
}else if(txt=='2')
{
      document.getElementById('no').style.display = '';
      document.getElementById('t3').style.display = '';
}



}
function displaypolicyselect(id) {
  var value = document.getElementById(id).value ;
  //alert(value);
  if (value=='') {
    alert("Please Enter the value for search. ");
 document.getElementById(id).focus();   
    }else {
        
    
document.getElementById("select_policyno").style.display = '';
document.getElementById("select_policy").style.display = '';
  }  
}
function displaybasic(obj,tr,challan,dd) {
	
txt = obj.options[obj.selectedIndex].value;
//alert(txt);
if (txt) {
document.getElementById('val').innerHTML =txt;
document.getElementById('val2').innerHTML =txt;
}
if (txt=='TR5') {
document.getElementById('val2').innerHTML ='Treasury';
}
if (txt=='DD') {
document.getElementById('val2').innerHTML ='Bank';
}
if (txt=='CHALLAN') {
document.getElementById('val2').innerHTML ='Office';
}

}


function display_child()
{
    document.getElementById('history_child').style.display = '';  
}

function display_grid() {
    var value = document.getElementById('search').value ;
    //alert(value);
    if(value==''){
       
        document.getElementById('errormsg').style.display = '';        
        
    }else{
   document.getElementById('errormsg').style.display = 'none';      
     document.getElementById('employee_information').style.display = '';
     
      document.getElementById('policy_information').style.display = '';
           
    }
}
function fade(id) {
 // alert(id);
  var color='#FFFFFF';
    document.getElementById('m0').style.backgroundColor  = color;
    document.getElementById('m1').style.backgroundColor  = color;
    document.getElementById('m2').style.backgroundColor  = color;
    document.getElementById('m3').style.backgroundColor  = color;
    document.getElementById('m4').style.backgroundColor  = color;
    document.getElementById('m5').style.backgroundColor  = color;
    document.getElementById('m6').style.backgroundColor  = color;
    document.getElementById('p').style.backgroundColor   = color;
    document.getElementById('p0').style.backgroundColor  = color;
    document.getElementById('p1').style.backgroundColor  = color;
    document.getElementById('p2').style.backgroundColor  = color;
    document.getElementById('p3').style.backgroundColor  = color;
    document.getElementById('p4').style.backgroundColor  = color;
    document.getElementById('p5').style.backgroundColor  = color;
    document.getElementById('q').style.backgroundColor   = color;
    document.getElementById('q0').style.backgroundColor  = color;
    document.getElementById('q1').style.backgroundColor  = color;
    document.getElementById('q2').style.backgroundColor  = color;
    document.getElementById('q3').style.backgroundColor  = color;
    document.getElementById('q4').style.backgroundColor  = color;
    document.getElementById('q5').style.backgroundColor  = color;
  if(id=='m0'){
    document.getElementById('l').style.backgroundColor  = '#E2EFF4';
    document.getElementById('m0').style.backgroundColor  = '#E2EFF4';
    document.getElementById('m6').style.backgroundColor  = '#E2EFF4';
    document.getElementById('m1').style.backgroundColor  = '#E2EFF4';
    document.getElementById('m2').style.backgroundColor  = '#E2EFF4';
    document.getElementById('m3').style.backgroundColor  = '#E2EFF4';
    document.getElementById('m4').style.backgroundColor  = '#E2EFF4';
    document.getElementById('m5').style.backgroundColor  = '#E2EFF4';
  }
  if(id=='p0'){
    document.getElementById('p').style.backgroundColor  = '#E2EFF4';
    document.getElementById('p0').style.backgroundColor  = '#E2EFF4';
    document.getElementById('p1').style.backgroundColor  = '#E2EFF4';
    document.getElementById('p2').style.backgroundColor  = '#E2EFF4';
    document.getElementById('p3').style.backgroundColor  = '#E2EFF4';
    document.getElementById('p4').style.backgroundColor  = '#E2EFF4';
    document.getElementById('p5').style.backgroundColor  = '#E2EFF4';
    }
    if(id=='q0'){
    document.getElementById('q').style.backgroundColor  = '#E2EFF4';
    document.getElementById('q0').style.backgroundColor  = '#E2EFF4';
    document.getElementById('q1').style.backgroundColor  = '#E2EFF4';
    document.getElementById('q2').style.backgroundColor  = '#E2EFF4';
    document.getElementById('q3').style.backgroundColor  = '#E2EFF4';
    document.getElementById('q4').style.backgroundColor  = '#E2EFF4';
    document.getElementById('q5').style.backgroundColor  = '#E2EFF4';
    }
}
function display_id(obj) {
//alert(obj);	
txt = obj.options[obj.selectedIndex].value;
 
if(txt=='1')
{
    document.getElementById('choose_department').innerHTML ='PEN ID';
    document.getElementById('search').innerHTML = '';
	document.getElementById('choose_department').style.display = '';
    document.getElementById('penid').style.display = '';
	document.getElementById('search').style.display = '';
    document.getElementById('search_button').style.display = '';
}
if(txt=='2')
{
    document.getElementById('choose_department').innerHTML ='KSID ID';
    document.getElementById('search').innerHTML = '';
	document.getElementById('choose_department').style.display = '';
    document.getElementById('penid').style.display = '';
	document.getElementById('search').style.display = '';
    document.getElementById('search_button').style.display = '';
}
if(txt=='3')
{
	
	document.getElementById('choose_department').innerHTML ='Policy No.';
    document.getElementById('search').innerHTML = '';
	document.getElementById('choose_department').style.display = '';
    document.getElementById('penid').style.display = '';
	document.getElementById('search').style.display = '';
    document.getElementById('search_button').style.display = '';
}


}
function display_designation(obj) {
	document.getElementById('designation').style.display = 'none';
document.getElementById('designation').style.display = '';	
}



// Code For gl Shorting Grid in manual recon By Amit Ruhela


function addEvent(element, type, handler) {
	// assign each event handler a unique ID
	if (!handler.$$guid) handler.$$guid = addEvent.guid++;
	// create a hash table of event types for the element
	if (!element.events) element.events = {};
	// create a hash table of event handlers for each element/event pair
	var handlers = element.events[type];
	if (!handlers) {
		handlers = element.events[type] = {};
		// store the existing event handler (if there is one)
		if (element["on" + type]) {
			handlers[0] = element["on" + type];
		}
	}
	// store the event handler in the hash table
	handlers[handler.$$guid] = handler;
	// assign a global event handler to do all the work
	element["on" + type] = handleEvent;
};
// a counter used to create unique IDs
addEvent.guid = 1;

function removeEvent(element, type, handler) {
	// delete the event handler from the hash table
	if (element.events && element.events[type]) {
		delete element.events[type][handler.$$guid];
	}
};

function handleEvent(event) {
	var returnValue = true;
	// grab the event object (IE uses a global event object)
	event = event || fixEvent(window.event);
	// get a reference to the hash table of event handlers
	var handlers = this.events[event.type];
	// execute each event handler
	for (var i in handlers) {
		this.$$handleEvent = handlers[i];
		if (this.$$handleEvent(event) === false) {
			returnValue = false;
		}
	}
	return returnValue;
};

function fixEvent(event) {
	// add W3C standard event methods
	event.preventDefault = fixEvent.preventDefault;
	event.stopPropagation = fixEvent.stopPropagation;
	return event;
};
fixEvent.preventDefault = function() {
	this.returnValue = false;
};
fixEvent.stopPropagation = function() {
	this.cancelBubble = true;
};

// end from Dean Edwards


/**
 * Creates an Element for insertion into the DOM tree.
 * From http://simon.incutio.com/archive/2003/06/15/javascriptWithXML
 *
 * @param element the element type to be created.
 *				e.g. ul (no angle brackets)
 **/
function createElement(element) {
	if (typeof document.createElementNS != 'undefined') {
		return document.createElementNS('http://www.w3.org/1999/xhtml', element);
	}
	if (typeof document.createElement != 'undefined') {
		return document.createElement(element);
	}
	return false;
}

/**
 * "targ" is the element which caused this function to be called
 * from http://www.quirksmode.org/js/events_properties.html
 **/
function getEventTarget(e) {
	var targ;
	if (!e) {
		e = window.event;
	}
	if (e.target) {
		targ = e.target;
	} else if (e.srcElement) {
		targ = e.srcElement;
	}
	if (targ.nodeType == 3) { // defeat Safari bug
		targ = targ.parentNode;
	}

	return targ;
}


// End By ruhela


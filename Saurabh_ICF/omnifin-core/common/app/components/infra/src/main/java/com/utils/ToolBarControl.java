//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : Tool Bar Control For All Masters-->
//Documentation : -->


package com.utils;
import org.apache.log4j.Logger;

	public class ToolBarControl extends Object {
	    public static final Logger logger = Logger.getLogger(ToolBarControl.class.getName()); 

	    private String imagePath = "..";
	    private String varGif = "";
	    private String varHtml = "";
	    private boolean varNavigator = false;

	   public void ToolBarControl(String pTitle, String pDate) {
	        varHtml = "<table dir='LTR' class='clsToolBarTable'><tr><td align='left' class='clsToolBarCell'>&nbsp;" + pTitle + "</td>";
	        addGif("images/toolBarImages/fndgtbl.gif");
	    }

	    public void ToolBarControl(String pTitle) {
	        varHtml = "<table dir='LTR' class='clsToolBarTable'><tr><td align='left' class='clsToolBarCell'>&nbsp;" + pTitle + "</td>";
	        addGif("images/toolBarImages/fndgtbl.gif");
	    }

	    public void ToolBarControl() {
	        varHtml = "<table dir='LTR' class='clsToolBarTable'><tr><td class='clsToolBarCell'>&nbsp;</td>";
	        addGif("images/toolBarImages/fndgtbl.gif");
	    }
	    
	    
	    public void constructDataToolBar(String pDisplayCode, String pDisplayIn) {
	        if (varNavigator) {
	            addGif("images/toolBarImages/fndiwdvd.gif");
	        }

	        if (pDisplayIn.compareTo("0") == 0) {
	            if (pDisplayCode.compareTo("2") == 0) {
	                addGif("MST", "images/toolBarImages/readonly.gif", "Navigate to Unauthorized Data","");
	            } else if (pDisplayCode.compareTo("0") == 0) {
	                addGif("ADD", "images/toolBarImages/addnew.gif", "Alt+A","A");
	                addGif("DEL", "images/toolBarImages/deleterec.gif", "Delete","");
	            } else if (pDisplayCode.compareTo("1") == 0) {
	                addGif("ATH", "images/toolBarImages/authorize.gif","Alt+Z","Z");
	                addGif("REJ", "images/toolBarImages/reject.gif","Alt+J","J");
	            } else if (pDisplayCode.compareTo("3") == 0) {
	                addGif("ADD", "images/toolBarImages/addnew.gif", "Alt+A","A");
	                addGif("DEL", "images/toolBarImages/deleterec.gif", "Delete","");
	            } else if (pDisplayCode.compareTo("4") == 0) {
	                addGif("DEL", "images/toolBarImages/deleterec.gif", "Delete","");
	                addGif("MST", "images/toolBarImages/readonly.gif", "Navigate to Unauthorized Data","");
	            } else if (pDisplayCode.compareTo("5") == 0) {
	                addGif("ADD", "images/toolBarImages/addnew.gif", "Alt+A","A");
	            } else if (pDisplayCode.compareTo("6") == 0) {
	            }  else if (pDisplayCode.compareTo("8") == 0) {
	                addGif("PRN", "images/toolBarImages/print.gif", "Alt+R","R");
	            } else if (pDisplayCode.compareTo("9") == 0) {
	                addGif("PRN", "images/toolBarImages/print.gif", "Alt+R","R");
	                addGif("EXCEL", "images/toolBarImages/modify16.gif", "Alt+E","E");
	            } else if (pDisplayCode.compareTo("10") == 0) {
	                addGif("EXCEL", "images/toolBarImages/modify16.gif", "Alt+E","E");
	            } else if (pDisplayCode.compareTo("17") == 0) {
	                addGif("LST", "images/toolBarImages/browse.gif", "Alt+I","I");
	                addGif("EXCEL", "images/toolBarImages/modify16.gif", "Alt+E","E");
	            } else if (pDisplayCode.compareTo("18") == 0) {
	                addGif("EXCEL", "images/toolBarImages/modify16.gif", "Alt+E","E");
	                addGif("SAV", "images/toolBarImages/save.gif", "Alt+S","S");
	                addGif("LST", "images/toolBarImages/browse.gif", "Alt+I","I");
	            } else if (pDisplayCode.compareTo("19") == 0) {
	                addGif("ADD", "images/toolBarImages/addnew.gif", "Alt+A","A");
	                addGif("PRN", "images/toolBarImages/print.gif", "Alt+R","R");
	                addGif("EXCEL", "images/toolBarImages/modify16.gif", "Alt+E","E");
	            } else if (pDisplayCode.compareTo("-5") == 0) {
	                addGif("ADD", "images/toolBarImages/addnew.gif", "Alt+A","A");
	            } else {
	                addGif("ADD", "images/toolBarImages/addnew.gif", "Alt+A","A");
	                addGif("UPD", "images/toolBarImages/save.gif", "Alt+S","S");
	            }
	        } else if (pDisplayIn.compareTo("1") == 0) {
	            if ((pDisplayCode.compareTo("-3") == 0) || ((pDisplayCode.compareTo("1") == 0) || (pDisplayCode.compareTo("2") == 0))) {
	                addGif("LST", "images/toolBarImages/browse.gif", "Alt+I","I");
	            } else if (pDisplayCode.compareTo("-2") == 0) {
	                addGif("LST", "images/toolBarImages/browse.gif", "Alt+I","I");
	            } else if (pDisplayCode.compareTo("-6") == 0) {
	                addGif("LST", "images/toolBarImages/browse.gif", "Alt+I","I");
	                addGif("HST", "images/toolBarImages/editrec.gif", "History","");
	            } else if (pDisplayCode.compareTo("-1") == 0) {
	            } else if (pDisplayCode.compareTo("-11") == 0) {
	                addGif("LST", "images/toolBarImages/browse.gif", "Alt+I","I");
	                addGif("SAV", "images/toolBarImages/save.gif", "Alt+S","S");
	            }else if (pDisplayCode.compareTo("-12") == 0) {
	                addGif("LST", "images/toolBarImages/browse.gif", "Alt+I","I");
	                addGif("ATH", "images/toolBarImages/authorize.gif","Alt+Z","Z");
	                addGif("SAV", "images/toolBarImages/save.gif", "Alt+S","S");
	            }
	        } else if (pDisplayIn.compareTo("2") == 0) {
	                addGif("SAV", "images/toolBarImages/save.gif", "Alt+S","S");
	        } else if (pDisplayIn.compareTo("3") == 0) {
	                addGif("ADD", "images/toolBarImages/addnew.gif", "Alt+A","A");
	        } else if (pDisplayIn.compareTo("4") == 0) {
	            if (pDisplayCode.compareTo("1") == 0) {
	                addGif("LST", "images/toolBarImages/browse.gif", "Alt+I","I");
	                addGif("UPD", "images/toolBarImages/save.gif", "Alt+S","S");
	            }
	        } else if (pDisplayIn.compareTo("5") == 0) {
	            addGif("DEL", "images/toolBarImages/deleterec.gif", "Delete","");
	        } else if (pDisplayIn.compareTo("6") == 0) {
	                addGif("UPD", "images/toolBarImages/save.gif", "Alt+S","S");
	        }
	    }

	    public String showToolBar() {
	        addGif("images/toolBarImages/fndiwdvd.gif");
	        addGif("EXT", "images/toolBarImages/exit.gif", "Alt+X","X");
	        addGif("images/toolBarImages/fndgtbr.gif");


	        return varHtml + "<td align='right' class='clsToolBarCell'>" + varGif + "</td></tr></table>";
	    }

	    

	    public String showToolBar(int i) {
	    if(i==1){
	        addGif("images/toolBarImages/fndiwdvd.gif");
	        addGif("SAV", "images/toolBarImages/save.gif", "Alt+S","S");
	        
	        addGif("images/toolBarImages/fndiwdvd.gif");
	        addGif("EXT", "images/toolBarImages/exit.gif", "Alt+X","X");
	        addGif("images/toolBarImages/fndgtbr.gif");

	        return varHtml + "<td align='right' class='clsToolBarCell'>" + varGif + "</td></tr></table>";
	    }else if(i==2){
	        addGif("images/toolBarImages/fndiwdvd.gif");
	        addGif("SAV", "images/toolBarImages/save.gif", "Alt+S","S");
	        addGif("images/toolBarImages/fndiwdvd.gif");
	        addGif("CLS", "images/toolBarImages/close.gif", "Alt+X","X");
	        addGif("images/fndgtbr.gif");

	        return varHtml + "<td align='right' class='clsToolBarCell'>" + varGif + "</td></tr></table>";
	    }else{
	        addGif("images/toolBarImages/fndiwdvd.gif");
	        addGif("CLS", "images/toolBarImages/close.gif", "Alt+X","X");
	        addGif("images/toolBarImages/fndgtbr.gif");

	        return varHtml + "<td align='right' class='clsToolBarCell'>" + varGif + "</td></tr></table>";
	      }
	    }

	    public String showToolBarLFrame() {
	        addGif("images/toolBarImages/fndgtbr.gif");

	        return varHtml + "<td align='right' class='clsToolBarCell'>" + "" + "</td></tr></table>";
	    }

	    public void ToolBarControlLFrame(String pTitle) {
	        varHtml = "<table dir='LTR' class='clsToolBarTable'><tr><td align='left' class='clsToolBarCell'>&nbsp;" + pTitle + "</td>";
	        addGif("images/toolBarImages/fndgtbl.gif");
	    }

	    public String showToolBarWithoutExit() throws java.lang.Exception {
	        addGif("images/toolBarImages/fndiwdvd.gif");

	        addGif("images/toolBarImages/fndgtbr.gif");

	        return varHtml + "<td align='right' class='clsToolBarCell'>" + varGif + "</td></tr></table>";
	    }


	  public void setImagePath(String imagePath)
	  {
	    this.imagePath = imagePath;
	  }


	  public String getImagePath()
	  {
	    return imagePath;
	  }
	  
	   private void addGif(String pSource){
	        varGif = varGif + "<input class='clsToolBarImage' type=\"image\" onClick=\"return false;\" src=\"" + pSource + "\" tabindex=\"-1\" >";
	    }

	    private void addGif(String pParameter, String pSource,String vTitle, String accessKey) {
	         varGif = varGif + "<input class='clsToolBarImage' name=\""+pParameter+"\" type=\"image\" src=\"" + pSource + "\" onClick=\"javascript:getClick('" + pParameter + "');return false;\" title=\""+vTitle+"\" accesskey=\""+accessKey+"\">";
	    }
	    
	    public void constructPaginationNavigatorBar(int pCurrent, int pTotal,int recordCount) {

	        varHtml = varHtml + "<td class='clsToolBarCell'>Page:&nbsp;" + pCurrent + "</td>";

	        if (pCurrent <= 1) {
	            addGif("images/toolBarImages/firstrecd.gif");
	            addGif("images/toolBarImages/prevrecd.gif");
	        } else {
	            addGif("FST", "images/toolBarImages/firstrec.gif", "Alt+F","F");
	            addGif("PRE", "images/toolBarImages/prevrec.gif", "Alt+P","P");
	        }

	        if (recordCount < 15) {
	            addGif("images/toolBarImages/nextrecd.gif");
	        } else {
	            addGif("NXT", "images/nextrec.gif", "Alt+N","N");
	        }
	        varNavigator = true;
	    }

	    public void constructNavigatorBar(int pCurrent, int pTotal) {
	        if (pTotal == 0) {
	            pTotal = 1;
	        }


	         varHtml = varHtml + "<td class='clsToolBarCell'>Page:&nbsp;" + pCurrent + "&nbsp;/&nbsp;" + pTotal + "";
	          if (pTotal > 1) {
	            varHtml = varHtml + "&nbsp;<input class='collNumeric' size=\"5\" maxlength=\"5\" onkeypress=\"fnIntNumKeyPress()\" onkeydown=\"fnPressEnter()\" name=\"searchBox\" type=\"text\">";

	            varHtml = varHtml + "&nbsp;<input type=\"button\" class=\"clsGoB\" name=\"btnGo\" value=\"Go\" onClick=\"javascript:getClick('SRCH');return false;\"\"></td>";
	        }else
	        {
	           varHtml = varHtml + "</td>";
	        }

	        if (pCurrent <= 1) {
	            addGif("images/toolBarImages/firstrecd.gif");
	            addGif("images/toolBarImages/prevrecd.gif");
	        } else {
	            addGif("FST", "images/toolBarImages/firstrec.gif", "Alt+F","F");
	            addGif("PRE", "images/toolBarImages/prevrec.gif", "Alt+P","P");
	        }

	        if (pCurrent >= pTotal) {
	            addGif("images/toolBarImages/nextrecd.gif");
	            addGif("images/toolBarImages/lastrecd.gif");
	        } else {
	            addGif("NXT", "images/toolBarImages/nextrec.gif", "Alt+N","N");
	            addGif("LST", "images/toolBarImages/lastrec.gif", "Alt+L","L");
	        }

	        varNavigator = true;
	    }
	}



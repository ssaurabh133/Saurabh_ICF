package com.cp.pdfProcess;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.LineSegment;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

public class BankStatementSimpleTextExtractionStrategy8 implements
		TextExtractionStrategy {

    private Vector lastStart;
    private Vector lastEnd;
    private float lastPagePositionStart =16.0f;
    
    private float colHeaderStartPositions[];
    private float colHeaderEndPositions[];

    private int lastColNo = 0;
    private int currentColNo = 0;
    private int currentPageNo = 0;
    boolean startData = true;
    int j;
    
    String actualColStartPosition="";
   
    boolean newLinestart = false;
	
	
    /** used to store the resulting String. */
    private final StringBuffer result = new StringBuffer();;

    /**
     * Creates a new text extraction renderer.
     */
    public BankStatementSimpleTextExtractionStrategy8() 
    {
    	
    }

    /**
     * @since 5.0.1
     */
    public void beginTextBlock() {
    }

    /**
     * @since 5.0.1
     */
    public void endTextBlock() {
    }
    
    /**
     * Returns the result so far.
     * @return	a String with the resulting text.
     */
    
    
    
    public String getResultantText(){
        return result.toString();
    }


	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	
	private boolean isBetween(float operand, float from, float to){
		if(operand >from && operand <to){
			return true;
		}else{
			return false;
		}
	}
	
	public void setColPositions(float[] colHeaderStartPositions, float[] colHeaderEndPositions){
		this.colHeaderStartPositions = colHeaderStartPositions;
		this.colHeaderEndPositions = colHeaderEndPositions;
		
	}
    /**
     * Captures text using a simplified algorithm for inserting hard returns and spaces
     * @param	renderInfo	render info
     */
    public void renderText(TextRenderInfo renderInfo) {
        boolean firstRender = result.length() == 0;
        boolean hardReturn = false;

        LineSegment segment = renderInfo.getBaseline();
        Vector start = segment.getStartPoint();
        Vector end = segment.getEndPoint();
      
        float yStart = 470.000f;
        float yEnd = 65.000f;
        if(currentPageNo!=	1){
        	yStart=800.000f;
        }
        
        float yTolerance = 9f;
        float yPageChangeTolerance = -100f;
        String str = renderInfo.getText();
        String delimiterPrefix = "";

       
       /* if(str.contains("1,75,806.10 Cr"))
        {
        	System.out.println("lastColNo--------"+lastColNo);
        }*/
        if(start.get(1)==777.0f && start.get(0) == 595.36f)
        {
        //	System.out.println("-----------------");
        }
        if(start.get(1)==777.0f && start.get(0) == 540.57f && str.contains("Page")||start.get(0) == 595.36f && start.get(1)==777.0f )
        {
        	return;
        }

        if(renderInfo.getText().equals("The count of transactions for the selected") )
        {
        	startData = false;
        }
        
        if(lastColNo==5)
        {
        	if(str.contains("Cr"))
        	{
        		str = str.replace("Cr", "");
        	}
        }
        
        /*if(start.get(1)>519.00f )
        {
        	if(str.contains("Cr"))
        	{
        		str = str.replace("Cr","");
        		System.out.println("str----------"+str);
        	}
        }*/
        
        if(start.get(1)>=(yStart+yTolerance) || start.get(1)<(yEnd-yTolerance) || (start.get(0)<lastPagePositionStart /*&& start.get(1)<yStart*/   )){ // start.get(1)<=(yEnd-yTolerance change by saorabh
        	return;
        }
      
      boolean ignoreLineChange = false;
      
		lastColNo = currentColNo;
		for(int xIndex=0; xIndex<colHeaderStartPositions.length;xIndex++){

			if(isBetween(start.get(0),colHeaderStartPositions[xIndex],colHeaderEndPositions[xIndex])){
				if (!firstRender){
			/*		if(xIndex==1 && lastColNo==1 && ((lastStart.get(1)-start.get(1))>yTolerance) && isBetween(start.get(0)-colHeaderStartPositions[xIndex],0f,1f) && !str.contains("STATEMENT")){
						//special case
						delimiterPrefix=" ";
						ignoreLineChange = true;
					}else*/ if( (lastStart.get(1)-start.get(1))>yTolerance || (lastStart.get(1)-start.get(1))<yPageChangeTolerance ){
						lastColNo=0;
					}
				}
				currentColNo=xIndex;
				break;
			}
		}
   
    	if(currentColNo - lastColNo == 1)
    	{
    		delimiterPrefix=delimiterPrefix+"|";
    		//result.append("|");
		}else if(currentColNo - lastColNo == 2){
			delimiterPrefix=delimiterPrefix+"||";
			//result.append("||");
		}
    	
        if (!firstRender){
            Vector x0 = start;
            Vector x1 = lastStart;
            Vector x2 = lastEnd;
            
            // see http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html
            //float dist = (x2.subtract(x1)).cross((x1.subtract(x0))).lengthSquared() / x2.subtract(x1).lengthSquared();
            float dist = lastStart.get(1)-start.get(1);
            boolean isPageChange = false;
            if(dist < yPageChangeTolerance){
            	isPageChange = true;
            }

            //float sameLineThreshold = 1f; // we should probably base this on the current font metrics, but 1 pt seems to be sufficient for the time being
            float sameLineThreshold = 10f; // we should probably base this on the current font metrics, but 1 pt seems to be sufficient for the time being
            if ((dist > sameLineThreshold) || isPageChange){
                hardReturn = true;
            }
            // Note:  Technically, we should check both the start and end positions, in case the angle of the text changed without any displacement
            // but this sort of thing probably doesn't happen much in reality, so we'll leave it alone for now
        }
        
       //Add By Abhishek Mathur For Check Line Change
       if(currentColNo==0)
       {
        	result.append('\n');
       } 
       // End Abhishek
       
        if (hardReturn){
            //System.out.println("<< Hard Return >>");
        	if(!ignoreLineChange){
        		result.append('\n');
        	}
        } else if (!firstRender){ 
            if (result.charAt(result.length()-1) != ' ' && renderInfo.getText().length() > 0 && renderInfo.getText().charAt(0) != ' '){ // we only insert a blank space if the trailing character of the previous string wasn't a space, and the leading character of the current string isn't a space
                float spacing = lastEnd.subtract(start).length();
                if (spacing > renderInfo.getSingleSpaceWidth()/2f){
                    result.append(' ');
                    //System.out.println("Inserting implied space before '" + renderInfo.getText() + "'");
                }
            }
        } else {
            //System.out.println("Displaying first string of content '" + text + "' :: x1 = " + x1);
        }
       
        //System.out.println("[" + renderInfo.getStartPoint() + "]->[" + renderInfo.getEndPoint() + "] " + renderInfo.getText());
        	
        	if(/*!str.contains("Page") && !str.equals("11") && */!str.equals("Legends for transactions in your account statement") 
        			&& !str.equals("INF - Internet fund transfer in linked accounts") && !str.equals("BIL - Internet Bill payment or funds transfer to Third party"))
        	{
        		result.append(delimiterPrefix+str);
                lastStart = start;
                lastEnd = end;
        	}
        	
          
       
        
    }
    

    /**
     * no-op method - this renderer isn't interested in image events
     * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(com.itextpdf.text.pdf.parser.ImageRenderInfo)
     * @since 5.0.1
     */
    public void renderImage(ImageRenderInfo renderInfo) {
        // do nothing - we aren't tracking images in this renderer
    }


}

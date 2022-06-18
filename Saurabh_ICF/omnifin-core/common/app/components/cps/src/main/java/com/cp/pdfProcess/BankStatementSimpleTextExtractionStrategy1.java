/*
 * $Id: ad99f5c11cd746afbb7b604e1bc3f14740f45a95 $
 *
 * This file is part of the iText (R) project.
 * Copyright (c) 1998-2015 iText Group NV
 * Authors: Kevin Day, Bruno Lowagie, Paulo Soares, et al.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
 * ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
 * OF THIRD PARTY RIGHTS
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA, 02110-1301 USA, or download the license from the following URL:
 * http://itextpdf.com/terms-of-use/
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License,
 * a covered work must retain the producer line in every PDF that is created
 * or manipulated using iText.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the iText software without
 * disclosing the source code of your own applications.
 * These activities include: offering paid services to customers as an ASP,
 * serving PDFs on the fly in a web application, shipping iText with a closed
 * source product.
 *
 * For more information, please contact iText Software Corp. at this
 * address: sales@itextpdf.com
 */
package com.cp.pdfProcess;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.LineSegment;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;


/**
 * A simple text extraction renderer.
 * 
 * This renderer keeps track of the current Y position of each string.  If it detects
 * that the y position has changed, it inserts a line break into the output.  If the
 * PDF renders text in a non-top-to-bottom fashion, this will result in the text not
 * being a true representation of how it appears in the PDF.
 * 
 * This renderer also uses a simple strategy based on the font metrics to determine if
 * a blank space should be inserted into the output.
 * 
 * @since	2.1.5
 */
public class BankStatementSimpleTextExtractionStrategy1 implements TextExtractionStrategy {

    private Vector lastStart;
    private Vector lastEnd;
    
    int pageNo=0;
    String firstHeadingLineColLabel;
    
    /** used to store the resulting String. */
    private final StringBuffer result = new StringBuffer();;
    private boolean nextPageFirstLine = false;//Added By Abhishek Mathur : 30-Nov-2015
    private int counter = 0;//Added By Abhishek Mathur : 30-Nov-2015

    /**
     * Creates a new text extraction renderer.
     */
    public BankStatementSimpleTextExtractionStrategy1(int pageNo, String firstHeadingLineColLabel) {
    	this.pageNo=pageNo;
    	this.firstHeadingLineColLabel=firstHeadingLineColLabel;
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

    /**
     * Used to actually append text to the text results.  Subclasses can use this to insert
     * text that wouldn't normally be included in text parsing (e.g. result of OCR performed against
     * image content)
     * @param text the text to append to the text results accumulated so far
     */
    protected final void appendTextChunk(CharSequence text){
    	result.append(text);
    }
    
    /**
     * Captures text using a simplified algorithm for inserting hard returns and spaces
     * @param	renderInfo	render info
     */
    public void renderText(TextRenderInfo renderInfo) {
        boolean firstRender = result.length() == 0;
        boolean hardReturn = false;
        boolean isColChangeHardReturn = false;
        boolean isNextLineHardReturn = false;
        boolean isStartOfHeader = false;

        LineSegment segment = renderInfo.getBaseline();
        Vector start = segment.getStartPoint();
        Vector end = segment.getEndPoint();
        
        if(renderInfo.getText().equals("Txn Srl")){
        	isStartOfHeader=true;
        	//System.out.println("???????PageNo.: "+pageNo);
            if(pageNo>1){
            	appendTextChunk("\n");
            }
        }
        
        	
         
        if (!firstRender){
            Vector x0 = start;
            Vector x1 = lastStart;
            Vector x2 = lastEnd;
            
            // see http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html
            float dist = (x2.subtract(x1)).cross((x1.subtract(x0))).lengthSquared() / x2.subtract(x1).lengthSquared();

            float sameLineThreshold =15f; // we should probably base this on the current font metrics, but 1 pt seems to be sufficient for the time being
            float nextLineThresholdY = 21f;

            if((x0.get(0)-x1.get(0))>sameLineThreshold) {

                	isColChangeHardReturn=true;              	

            }else{
            	if(
            		(Math.abs((x1.get(1)-x0.get(1))) > nextLineThresholdY) 
            		|| (x1.get(0) > x0.get(0)) )
            	{
            		isNextLineHardReturn=true;
                }
            }
            
            if (dist > sameLineThreshold)
                hardReturn = true;
            
            // Note:  Technically, we should check both the start and end positions, in case the angle of the text changed without any displacement
            // but this sort of thing probably doesn't happen much in reality, so we'll leave it alone for now
           
        }
       
        if (hardReturn){
            //System.out.println("<< Hard Return >>");
        	//appendTextChunk("\n");
        	if(isColChangeHardReturn){
        		appendTextChunk("|");
        	}else if(isNextLineHardReturn){
        		appendTextChunk("\n");
        		counter++; //Added By Abhishek Mathur : 30-Nov-2015
        	}
        	//appendTextChunk("");
        } else if (!firstRender){ 
            if (result.charAt(result.length()-1) != ' ' && renderInfo.getText().length() > 0 || renderInfo.getText().charAt(0)== ' '){ // we only insert a blank space if the trailing character of the previous string wasn't a space, and the leading character of the current string isn't a space
                float spacing = lastEnd.subtract(start).length();
                if (spacing > renderInfo.getSingleSpaceWidth()/2f){
                	//appendTextChunk(" ");
                	appendTextChunk("|");
                    //System.out.println("Inserting implied space before '" + renderInfo.getText() + "'");
                }
            }
        } else {
        	
            //System.out.println("Displaying first string of content '" + text + "' :: x1 = " + x1);
        }
        
        //System.out.println("[" + renderInfo.getStartPoint() + "]->[" + renderInfo.getEndPoint() + "] " + renderInfo.getText());
        
      //Added By Abhishek Mathur : 30-Nov-2015
        if(pageNo > 1 && counter == 2)
        {
        	nextPageFirstLine = false;
        }

        if (nextPageFirstLine)
        {
        	String tmpStr = renderInfo.getText();
        	int pos = Math.round(Double.valueOf(renderInfo.getBaseline().getStartPoint().get(0)/53.2).floatValue());
        	tmpStr = tmpStr + "~"+pos+"~";
        	appendTextChunk(tmpStr);
        }
        else
        {	
        	appendTextChunk(renderInfo.getText());
        }
        //Ended By Abhishek Mathur : 30-Nov-2015


        lastStart = start;
        lastEnd = end;
        
        //Added By Abhishek Mathur : 30-Nov-2015
        if(pageNo > 1 && renderInfo.getText().equalsIgnoreCase("Balance"))
        {
     	    counter = 0;
        	nextPageFirstLine = true;
        }
        //Ended By Abhishek Mathur : 30-Nov-2015
                
    }
/*
    public void renderText(TextRenderInfo renderInfo) {
        appendTextChunk(renderInfo.getText());
     
    }*/
    
    /**
     * no-op method - this renderer isn't interested in image events
     * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(com.itextpdf.text.pdf.parser.ImageRenderInfo)
     * @since 5.0.1
     */
    public void renderImage(ImageRenderInfo renderInfo) {
        // do nothing - we aren't tracking images in this renderer
    }


}

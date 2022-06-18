package com.cp.pdfProcess;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.LineSegment;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import java.io.PrintStream;

public class BankStatementSimpleTextExtractionStrategy4
  implements TextExtractionStrategy
{
  private Vector lastStart;
  private Vector lastEnd;
  int c = 0;
  int m = 0;

  String strText = "";

  int pageNo = 0; int tempPageNo = 0;
  String firstHeadingLineColLabel;
  boolean multiLineCheck = false;
  boolean startData = true;
  
/** used to store the resulting String. */
  private final StringBuffer result = new StringBuffer();
  
   /**
     * Creates a new text extraction renderer.
     */

  public BankStatementSimpleTextExtractionStrategy4(int pageNo, String firstHeadingLineColLabel)
  {
    this.pageNo = pageNo;
    this.firstHeadingLineColLabel = firstHeadingLineColLabel;
  }
  
  /**
     * @since 5.0.1
     */

  public void beginTextBlock()
  {
  }
   /**
     * @since 5.0.1
     */

  public void endTextBlock()
  {
  }
   /**
     * Returns the result so far.
     * @return	a String with the resulting text.
     */

  public String getResultantText()
  {
    String strRet = "";

    if (this.pageNo > 1)
    {
      String strTemp = this.result.toString().substring(0, this.result.indexOf("/") - 2);
	  //System.out.println("strTemp-----------------------------------: "+strTemp);

      if (strTemp.contains(":"))
      {
		//System.out.println("inside if : "+strTemp.contains(":"));
        strRet = this.result.substring(this.result.indexOf("/") - 2);
		//System.out.println("inside if strRet : "+strRet);
      }
      else if (strTemp.length() > 0)
      {
		//System.out.println("inside else : "+strTemp.length());
    			
		//strRet = result.substring(result.indexOf("/")-2);
        strRet = this.result.toString();
		
		//strText = strTemp;
		//System.out.println("inside else strRet : "+strRet);
    	//appendPartialTextChunkInResult();
      }
      else if (strTemp.trim().equalsIgnoreCase(""))
      {
		//System.out.println("inside else if : "+strTemp.length());
        strRet = this.result.toString();
		//System.out.println("inside else if strRet : "+strRet);
      }

    }
    else
    {
      strRet = this.result.toString();
	  //System.out.println("inside main else : "+strRet);
    }

	//System.out.println("strRet : "+strRet);
    return strRet;
  }

  protected final void appendPartialTextChunkInResult()
  {/*
    int intNewLine = this.strText.length();

    BankStatementforHDFCPdf bankStatementforHDFCPdf = new BankStatementforHDFCPdf();

    StringBuffer strBuffer = bankStatementforHDFCPdf.str;

    if ((intNewLine != 8) || (!this.strText.contains("/")))
    {
      if (null != strBuffer)
      {
        StringBuffer buffer = new StringBuffer();

        System.out.println("strBuffer.length() : " + strBuffer.length());

        System.out.println("strBuffer.lastIndexOf() : " + strBuffer.lastIndexOf("|"));

        System.out.println("intNewLine : " + intNewLine);

        String strTextTemp1 = strBuffer.substring(strBuffer.lastIndexOf("|"), strBuffer.length() - 1);

        String strResultTemp1 = strBuffer.substring(0, strBuffer.lastIndexOf("|"));

        String strTextTemp2 = strResultTemp1.substring(strResultTemp1.lastIndexOf("|"));

        String strResultTemp2 = strResultTemp1.substring(0, strResultTemp1.lastIndexOf("|"));

        String strTextTemp3 = strResultTemp2.substring(strResultTemp2.lastIndexOf("|"));

        String strResultTemp3 = strResultTemp2.substring(0, strResultTemp2.lastIndexOf("|"));

        String strTextTemp4 = strResultTemp3.substring(strResultTemp3.lastIndexOf("|"));

        String strResultTemp4 = strResultTemp3.substring(0, strResultTemp3.lastIndexOf("|"));

        String strTextTemp5 = strResultTemp4.substring(strResultTemp4.lastIndexOf("|"));

        String strResultTemp5 = strResultTemp4.substring(0, strResultTemp4.lastIndexOf("|"));

        buffer.append(strResultTemp5);

        if (this.multiLineCheck)
        {
          buffer.append(" ");
        }

        buffer.append(this.strText);

        buffer.append(strTextTemp5 + strTextTemp4 + strTextTemp3 + strTextTemp2 + strTextTemp1);

        bankStatementforHDFCPdf.str.delete(0, this.result.length() - 1);

        bankStatementforHDFCPdf.str.append(buffer);

        this.multiLineCheck = true;
      }
    }
  */}
	
	/**
     * Used to actually append text to the text results.  Subclasses can use this to insert
     * text that wouldn't normally be included in text parsing (e.g. result of OCR performed against
     * image content)
     * @param text the text to append to the text results accumulated so far
     */
  
  protected final void appendTextChunk(CharSequence text)
  {
    this.strText = ((String)text);

    this.result.append(text);
	
	//System.out.println("result : "+result);
  }

  protected final void appendPartialTextChunk()
  {
    int intNewLine = this.strText.length();

    if ((intNewLine != 8) || (!this.strText.contains("/")))
    {
      StringBuffer buffer = new StringBuffer();

      String strTextTemp1 = this.result.substring(this.result.lastIndexOf("|"), this.result.length() - intNewLine - 1);

      String strResultTemp1 = this.result.substring(0, this.result.lastIndexOf("|"));

      String strTextTemp2 = strResultTemp1.substring(strResultTemp1.lastIndexOf("|"));

      String strResultTemp2 = strResultTemp1.substring(0, strResultTemp1.lastIndexOf("|"));

      String strTextTemp3 = strResultTemp2.substring(strResultTemp2.lastIndexOf("|"));

      String strResultTemp3 = strResultTemp2.substring(0, strResultTemp2.lastIndexOf("|"));

      String strTextTemp4 = strResultTemp3.substring(strResultTemp3.lastIndexOf("|"));

      String strResultTemp4 = strResultTemp3.substring(0, strResultTemp3.lastIndexOf("|"));

      String strTextTemp5 = strResultTemp4.substring(strResultTemp4.lastIndexOf("|"));

      String strResultTemp5 = strResultTemp4.substring(0, strResultTemp4.lastIndexOf("|"));

      buffer.append(strResultTemp5);

      if (this.multiLineCheck)
      {
        buffer.append(" ");
      }

      buffer.append(this.strText);

      buffer.append(strTextTemp5 + strTextTemp4 + strTextTemp3 + strTextTemp2 + strTextTemp1);

	  //System.out.println("Result----"+result);
	  
      this.result.delete(0, this.result.length() - 1);

      this.result.append(buffer);

      this.multiLineCheck = true;
    }
  }

	/**
     * Captures text using a simplified algorithm for inserting hard returns and spaces
     * @param	renderInfo	render info
     */
  
  public void renderText(TextRenderInfo renderInfo)
  {
    BankStatementforHDFCPdf bankStatementforHDFCPdf = new BankStatementforHDFCPdf();

    boolean firstRender = this.result.length() == 0;
    boolean hardReturn = false;
    boolean isColChangeHardReturn = false;
    boolean isNextLineHardReturn = false;
    boolean isStartOfHeader = false;

    LineSegment segment = renderInfo.getBaseline();
    Vector start = segment.getStartPoint();
    Vector end = segment.getEndPoint();

    if (renderInfo.getText().equals("Date"))
    {
      isStartOfHeader = true;

      if (this.pageNo > 1)
      {
        appendTextChunk("\n");
      }

    }

    if ((renderInfo.getText().equals("Page")) && (".".equalsIgnoreCase("" + this.result.charAt(this.result.length() - 3))))
    {
      this.startData = false;
    }

    if ((renderInfo.getText().equals("STATEMENT")) && (".".equalsIgnoreCase("" + this.result.charAt(this.result.length() - 3))))
    {
      this.startData = false;
    }

    if ((!firstRender) && (this.startData))
    {
      Vector x0 = start;
      Vector x1 = this.lastStart;
      Vector x2 = this.lastEnd;

      float dist = x2.subtract(x1).cross(x1.subtract(x0)).lengthSquared() / x2.subtract(x1).lengthSquared();

      float sameLineThreshold = 23.0F;
      float nextLineThresholdY = 21.0F;
      float emptySpaceThresholdX = 120.0F;

      if ((x0.get(0) - x2.get(0) > emptySpaceThresholdX) && (this.m >= 3))
      {
        appendTextChunk("|");
      }

      if (x0.get(0) - x1.get(0) > sameLineThreshold)
      {
        isColChangeHardReturn = true;
      }
      else if ((Math.abs(x1.get(1) - x0.get(1)) > nextLineThresholdY) || (x1.get(0) > x0.get(0)))
      {
        isNextLineHardReturn = true;

        this.m = 0;

        this.multiLineCheck = false;
      }

      if (dist > sameLineThreshold)
      {
        hardReturn = true;
      }

    }
	
	// Note:  Technically, we should check both the start and end positions, in case the angle of the text changed without any displacement
    // but this sort of thing probably doesn't happen much in reality, so we'll leave it alone for now

    if (hardReturn)
    {
      if (isColChangeHardReturn)
      {
        String strTemp = this.result.substring(this.result.lastIndexOf("|") + 1);

        if (strTemp.contains("."))
        {
          String strTempNew = strTemp.substring(strTemp.lastIndexOf(".") + 1);

          if (strTempNew.length() > 1)
          {
            appendTextChunk("| ");
            this.m += 1;
          }
          else if (strTempNew.length() <= 2);
        }
        else
        {
          appendTextChunk("|");
          this.m += 1;
        }
      }
      else if (isNextLineHardReturn)
      {
        appendTextChunk("\n");
      }

    }
    else if ((!firstRender) && (this.startData))
    {
      if ((this.result.charAt(this.result.length() - 1) != ' ') && (renderInfo.getText().length() >= 0))
      {
        float spacing = this.lastEnd.subtract(start).length();

        float diffSpace = spacing - renderInfo.getSingleSpaceWidth() / 1.0F;

        if ((spacing > renderInfo.getSingleSpaceWidth() / 1.0F) && (diffSpace >= 1.0F))
        {
          appendTextChunk("|");

          this.m += 1;
        }
        else if (spacing == renderInfo.getSingleSpaceWidth() / 1.0F)
        {
          appendTextChunk(" ");
        }

      }

    }

    if (this.startData)
    {
      appendTextChunk(renderInfo.getText());

      if ((isNextLineHardReturn) || (this.multiLineCheck))
      {
        appendPartialTextChunk();
      }

    }

    if ((renderInfo.getText().equals("400013")) && (!this.startData))
    {
      this.startData = true;
    }

    this.lastStart = start;
    this.lastEnd = end;
  }
	/**
     * no-op method - this renderer isn't interested in image events
     * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(com.itextpdf.text.pdf.parser.ImageRenderInfo)
     * @since 5.0.1
     */
  public void renderImage(ImageRenderInfo renderInfo){
   // do nothing - we aren't tracking images in this renderer
    }
}
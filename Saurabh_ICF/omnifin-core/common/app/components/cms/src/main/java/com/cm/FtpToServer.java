package com.cm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//import sun.net.ftp.FtpClient;


@SuppressWarnings("restriction")
public class FtpToServer 
{
public static int BUFFER_SIZE = 10240;
//private static FtpClient m_client;
private static String host = "";
private static String user = "";
private static String password = "";
private static String targetDataDir = "";
private static String targetResultsDir = "";
private static String sourceDir = "";
private static String sourceFile = "";
public static char SEPARATOR = '/';
public String targetFile = "";
public String ftpStartTime="";
public String ftpEndTime="";
public String servletStartTime="";
public String servletEndTime="";



private String getUniqueId()
{
String strUniqueId = "";
Date date = new Date();
SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");
strUniqueId = simpledateformat.format(date)+(int)(Math.ceil(Math.random()*1000));
return strUniqueId;
}

public String getCurrentDateTime()
{
Date date = new Date();
SimpleDateFormat simpledateformat = new SimpleDateFormat("hhmmssSSSSSS");
String datetime = simpledateformat.format(date);
datetime = datetime.substring(0,2)+":"+datetime.substring(2,4)+":"+datetime.substring(4,6)+"."+datetime.substring(6);
return datetime;
}

private String getUniqueFolderId()
{
String strUniueId = getUniqueId();
String strDateId = "";
String strTimeId = "";
String strFolderId = null;
strDateId = strUniueId.substring(0,7) + SEPARATOR;
strTimeId = strUniueId.substring(8,9) + SEPARATOR;
strFolderId = strDateId + strTimeId + strUniueId ;
return strFolderId;
}

public FtpToServer(StringBuffer dCSVFile,String IP,String USERNAME,String PASSWORD,String FTP_INPUT_PATH) throws IOException
{
//Properties p = new Properties();
//p.load(new FileInputStream("ScrutiNet.properties"));
	
/*host=ScrutinetConstants.TARGETMACHINE;
user=ScrutinetConstants.USER;
password=ScrutinetConstants.PASSWORD;
targetDataDir=ScrutinetConstants.TARGETROOTDIRECTORYDATA;
targetResultsDir=ScrutinetConstants.TARGETROOTDIRECTORYRESULTS;*/
	
	host=IP;
	user=USERNAME;
	password=PASSWORD;
	targetDataDir=FTP_INPUT_PATH;
	
	
ftpStartTime = getCurrentDateTime();
System.out.println(host);
//m_client = new FtpClient(host,21);
System.out.println(user + " " + password);
//m_client.login(user, password);
System.out.println("User " + user + " login OK");
//System.out.println("hello " + m_client.welcomeMsg);
System.out.println("Calling Ftp of Data CSV");
//m_client.cd(targetDataDir);
//m_client.binary();
System.out.println("Calling first put CSV");
putFile(dCSVFile);
//System.out.println("After Calling first put CSV");


//System.out.println("Calling Ftp of Results CSV");
//m_client.cd(targetResultsDir);
//putFile(rCSVFIle);
ftpEndTime = getCurrentDateTime();
disconnect();
}

protected void disconnect()
{
//if (m_client != null)
{
/*try
{
	m_client.closeServer();
}
catch (IOException ex)
{
ex.printStackTrace();
}*/
//m_client = null;
}
}

protected void putFile(StringBuffer sbFileNameToSend)
{
	FileInputStream in=null;
	OutputStream out =null;
if (sourceFile.length() == 0)
{
//System.out.println("Please enter file name");
}
byte[] buffer = new byte[BUFFER_SIZE];
try
{
File f = new File(sbFileNameToSend.toString());
int size = (int) f.length();
System.out.println("Entering FTP");
 in = new FileInputStream(sbFileNameToSend.toString());System.out.println(sbFileNameToSend.substring(sbFileNameToSend.lastIndexOf("\\")+1));

 
 //out = m_client.put(sbFileNameToSend.substring(sbFileNameToSend.lastIndexOf("\\")+1));

 int counter = 0;
while (true)
{
int bytes = in.read(buffer);
if (bytes < 0) break;
out.write(buffer, 0, bytes);
counter += bytes;
}
System.out.println("FTP Done");

}
catch (Exception ex)
{
ex.printStackTrace();
System.out.println("Error in FTP: " + ex.toString());
}finally{
			try{
			out.close();
			in.close();
			out=null;
			in=null;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
}

public static void main (String [] a){
	StringBuffer strbfr=new StringBuffer("C:\\Payout_File\\Mannual Process File\\TA_LOANS_20121103_01.xlsx");
	try{
	//new FtpToServer(strbfr);
	}catch (Exception e) {
		e.printStackTrace();
	}
}
}

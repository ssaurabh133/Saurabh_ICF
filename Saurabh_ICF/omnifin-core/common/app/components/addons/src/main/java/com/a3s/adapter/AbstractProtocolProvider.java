package com.a3s.adapter;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.a3s.adapter.providers.FileProtocolProvider;
import com.a3s.adapter.providers.FtpProtocolProvider;

/**
 * @author Ritesh Srivastava
 *
 */
public abstract class AbstractProtocolProvider implements IProtocol {
	
	private static Log log = LogFactory.getLog(AbstractProtocolProvider.class);
	
	public static IProtocol getProtocolProvider(String protocol,Map<String,String> map){
	IProtocol _instance = null;

	if(protocol.equals("file")){
		
			_instance =  new FileProtocolProvider(map);	
			
	}else if(protocol.equals("ftp")){
		
		_instance =  new FtpProtocolProvider(map);	
    }
	else{
		
		throw new UnsupportedOperationException(
				  protocol + " is not a supported protocol!!");
    }
		return _instance;
    }
}

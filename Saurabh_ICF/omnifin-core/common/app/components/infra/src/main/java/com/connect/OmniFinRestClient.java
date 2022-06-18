package com.connect;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cp.vo.ExperianHunterRequestWrapper;
import com.cp.vo.ExperianHunterResponseWrapper;
import com.cp.vo.LoanDetailVo;
import com.ibm.icu.text.DecimalFormat;
import com.webservice.cp.pdvo.AstuteFiVerificationReOpenRequest;
import com.webservice.cp.pdvo.AstuteFiVerificationReOpenRequestWrapper;
import com.webservice.cp.pdvo.AstuteFiVerificationRequest;
import com.webservice.cp.pdvo.AstuteFiVerificationRequestWrapper;
import com.webservice.cp.pdvo.AstuteFiVerificationResponse;
import com.webservice.cp.pdvo.AstuteFiVerificationResponseWrapper;
import com.webservice.cp.pdvo.CalculateAllEligibleSchemeRequest;
import com.webservice.cp.pdvo.CalculateAllEligibleSchemeResponse;
import com.webservice.cp.pdvo.EligibleNHBscheme;
import com.webservice.cp.pdvo.ExperianDaasRequestWrapper;
import com.webservice.cp.pdvo.ExperianDaasResponseWrapper;
import com.webservice.cp.pdvo.FiVerificationRequestWrapper;
import com.webservice.cp.pdvo.FiVerificationResponseWrapper;
import com.webservice.cp.pdvo.InsuranceRequestWrapper;
import com.webservice.cp.pdvo.InsuranceResponseWrapper;
import com.webservice.cp.pdvo.NHBownershipDtl;
import com.webservice.cp.pdvo.PdFetchRequest;
import com.webservice.cp.pdvo.PdFetchResponse;
import com.webservice.cp.pdvo.UserCredentials;
import com.webservice.cp.pdvo.PanResponseWrapper;
import com.webservice.cp.pdvo.PanRequestWrapper;
import com.webservice.cp.pdvo.UserHierarchyRequest;
import com.webservice.cp.pdvo.UserHierarchyResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.webservice.cp.ImplementationClass.QueryExecution;
import com.webservice.cp.cibilvo.*;
import com.webservice.cp.crifvo.CustomerCrifReportWrapper;
import com.webservice.cp.response.*;
import com.cp.vo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class OmniFinRestClient {
	private static final Logger logger = Logger.getLogger(OmniFinRestClient.class);
	
	public static UserHierarchyResponse fetchUserHierarchy(String serviceUrl,UserHierarchyRequest request){
		RestTemplate restTemplate = new RestTemplate();
		UserHierarchyResponse wrapper=null;
		String url = serviceUrl+"/restServices/leadOperationService/userHierarchy";
		try{
			wrapper = restTemplate.postForObject(url, request,UserHierarchyResponse.class);
		}catch(Exception e){
			e.printStackTrace();
			wrapper=null;
		}
		return wrapper;
	}
public static CustomerCibilResponseWrapper fetchCibilDetails(Map<String,String>parameter,CustomerCibilReportWrapper request) {
		
		RestTemplate restTemplate = new RestTemplate();
		CustomerCibilResponseWrapper cibilResponse=null;
		String serviceUrl=parameter.get("SERVICE_URL");
		String url = serviceUrl+"/restServices/loanOperationServices/customerDetails/cibil";
		
		try{
			cibilResponse = restTemplate.postForObject(url, request,CustomerCibilResponseWrapper.class);
		}catch(Exception e){
			e.printStackTrace();
			cibilResponse=null;
		}
		return cibilResponse;
	}	
}
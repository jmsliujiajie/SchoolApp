package com.lll.utisl;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebService extends WsPubSecur {

	private static String WS_ADDR = "http://192.168.1.104:8080/WSDL/services/";
	
	public static String NS_doLogin = "http://controller.userInfo.lll.com";
	public static String SN_doLogin = "UserInfoWs";

	public static String callWebService(String NAME_SPACE, String SERVICE_NAME,
			String METHOD_NAME, String parameter) {
		
		String result = "";
		try {
			
			ExecutorService pool = Executors.newFixedThreadPool(1);
			
			Future<Object> fResult = pool.submit(
					new WebService().
					new MyCallable(NAME_SPACE, METHOD_NAME, SERVICE_NAME, parameter));
			result = fResult.get().toString();
			
			// πÿ±’œﬂ≥Ã≥ÿ  
			pool.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return result;
	}

	class MyCallable implements Callable<Object> {

		private String NAME_SPACE;
		private String METHOD_NAME;
		private String serviceName;
		private String parameter;

		MyCallable(String NAME_SPACE, String METHOD_NAME, String serviceName, String parameter) {
			this.NAME_SPACE = NAME_SPACE;
			this.METHOD_NAME = METHOD_NAME;
			this.serviceName = serviceName;
			this.parameter = parameter;
		}

		public Object call() throws Exception {
			String result = "";
			try {
				
				SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);
				request.addProperty("JSON_OBJ", encrypt(parameter));
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.bodyOut = request;
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);

				String WDSL_LINK = WS_ADDR + serviceName + "?wsdl";

				HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
				String a = NAME_SPACE + "/" + METHOD_NAME;
				
				ht.call(a, envelope);
				
				String ret = String.valueOf(envelope.getResponse());
				result = decypt(ret);
				
			} catch (SoapFault e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			
			return result;
		}

	}

}

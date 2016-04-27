package com.jy.paypal.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class for sending request using http post method and returning response
 * @author wdong
 *
 */
public final class HttpPost implements Transport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final Log logger = LogFactory.getLog(getClass());

	//TODO: need optimize and use ASYNC post.
	public String getResponse(String urlString, String msg)
			throws MalformedURLException {
		logger.info("Prepare for sending request to url: " + urlString);
		
		URL url = new URL(urlString);
		URLConnection connection;
		StringBuffer response = new StringBuffer();
		
		try{
			connection = url.openConnection();
			connection.setDoOutput(true);
			
			//write request
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(msg);
			writer.flush();
			writer.close();
			
			//read response
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		return response.toString();
	}
	
	@Override
    public String toString() {
        return "Instance of HttpPost class";
    }

}

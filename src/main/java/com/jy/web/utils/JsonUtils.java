package com.jy.web.utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

public class JsonUtils<T> {
	
	public String getJSONString(T t, final String[] props) throws Exception{
		String jsonString = null; 
		//����ֵ������  
        JsonConfig jsonConfig = new JsonConfig();  
        jsonConfig.setIgnoreDefaultExcludes(true);  
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);    
        jsonConfig.setExcludes(props);
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor()); 
        if(t != null){  
            if(t instanceof Collection || t instanceof Object[]){  
                jsonString = JSONArray.fromObject(t, jsonConfig).toString();  
            }else{  
                jsonString = JSONObject.fromObject(t, jsonConfig).toString();  
            }  
        }  
        return jsonString == null ? "{}" : jsonString;  
	}
	
	protected class JsonDateValueProcessor implements JsonValueProcessor {
		
		private String format = "yyyy-MM-dd HH:mm:ss"; 
		
		public JsonDateValueProcessor() {  
			  
	    }  
	  
	    public JsonDateValueProcessor(String format) {  
	        this.format = format;  
	    }

	    public Object processArrayValue(Object value, JsonConfig jsonConfig) {  
	        return process(value, jsonConfig);  
	    }  
	  
	    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {  
	        return process(value, jsonConfig);  
	    }  
	      
	    private Object process( Object value, JsonConfig jsonConfig ) {  
	        if (value instanceof Date) {  
	            String str = new SimpleDateFormat(format).format((Date) value);  
	            return str;  
	        }  
	        return value == null ? null : value.toString();  
	    }  
	  
	    public String getFormat() {  
	        return format;  
	    }  
	  
	    public void setFormat(String format) {  
	        this.format = format;  
	    }  
		
	}
	
	
}

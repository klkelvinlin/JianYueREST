package com.jy.utils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sun.jersey.core.util.Base64;

public class QRCodeEncoder {
	
	/*	        
    // change this path to match yours (this is my mac home folder, you can use: c:\\qr_png.png if you are on windows)
    String filePath = "C:/dev/temp/qr_png.png";
    File file = new File(filePath);
    try {
        MatrixToImageWriter.writeToFile(matrix, "PNG", file);
        System.out.println("printing to " + file.getAbsolutePath());
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
*/
	
	public static byte[] encodeToByteArray(String value){
		try {
			Charset charset = Charset.forName("ISO-8859-1");
			CharsetEncoder encoder = charset.newEncoder();
			byte[] b = null;
	
		    // Convert a string to ISO-8859-1 bytes in a ByteBuffer
		    ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(value));
		    b = bbuf.array();
		
		    String data;

		    data = new String(b, "ISO-8859-1");
		    // get a byte matrix for the data
		    BitMatrix matrix = null;
		    int h = 300;
		    int w = 300;
		    com.google.zxing.Writer writer = new MultiFormatWriter();
	        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(2);
	        hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
	        matrix = writer.encode(data,
	        com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
	        
	        //System.out.println("returning: "+ baos.toByteArray());
	        return baos.toByteArray();

		} catch (Exception e) {
		    System.out.println(e.getMessage());
		    return null;
		}
	}
	public static String encodeToString(String value){
		try {
			byte[] b = encodeToByteArray(value);
	        byte[] b64 = Base64.encode(b);
	        String s = new String(b64);
	        
	        //System.out.println("returning b64: "+ s);
	        return s;
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		    return null;
		}
	}
	
	public static String test(){
		return "test";
	}
	
	public static void main(String[] args) {
		//QRCodeEncoder e = new QRCodeEncoder();
		//e.encode();
	}
}

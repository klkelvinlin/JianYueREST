package com.jy.paypal.utils;

import com.jy.paypal.core.PayPal;
import com.jy.paypal.core.PayPal.Environment;
import com.jy.paypal.profile.BaseProfile;


public final class PayPalHelpers {
	public static PayPal getHSHPayPal() {
		String username = "dongwz_1342827702_biz_api1.yeah.net";
		String password = "1342827739";
		String signature = "AFcWxV21C7fd0v3bYYYRCpSSRl31AZu.mtfcNMS6.DuLdW6at9B5a99c";
		Environment environment = Environment.SANDBOX;
		/*String username = "woerjiaoyu_api1.live.com";
		String password = "BKYUARUHNBZD8VC9";
		String signature = "A8DVwGVYXvhTFuG64VJIgLQH1xYzAgUQGBDNzfgl8wxosVFr1u2fIL0E";
		Environment environment = Environment.LIVE;*/
		BaseProfile profile = new BaseProfile.Builder(username, password).signature(signature).build();
		PayPal paypal = new PayPal(profile, environment);
		return paypal;
	}
	
	public static String getTaxRate(){
		return "0.00";
	}
	
//    public void buildPaypalBillForUserAndDetailResponseAndCheckoutReaponse(User user, Request detailReq, Request checkoutReq) {
//		Map<String, String> response = detailReq.getNVPResponse();
//		Map<String, String> checkoutResponse = checkoutReq.getNVPResponse();
//		PaypalBill paypalBill = new PaypalBill();s
//		paypalBill.setCorrelationId(checkoutResponse.get("CORRELATIONID"));
//		paypalBill.setTransactionId(checkoutResponse.get("TRANSACTIONID"));
//		paypalBill.setUserId(user.getId());
//		paypalBill.setCreateTime(new Date());
//		paypalBill.setEmail(response.get("EMAIL"));
//		paypalBill.setCurrencyCode(checkoutResponse.get("CURRENCYCODE"));
//		paypalBill.setToken(checkoutResponse.get("TOKEN"));
//		paypalBill.setPayerId(response.get("PAYERID"));
//		paypalBill.persist();
//    }
	
    /*public static PaymentInfo buildPaypalBillForUserAndTypeAndDetailNVPResponseAndCheckoutNVPResponse(User user, String type, Map<String, String> detailResponse, Map<String, String> checkoutResponse,String gateway) {
    	PaymentInfo paypalBill = new PaymentInfo();
		paypalBill.setUserId(user.getId());
		try {
			//paypalBill.setAmount(Double.parseDouble(checkoutResponse.get("AMT"))-Double.parseDouble(checkoutResponse.get("FEEAMT")));
			paypalBill.setAmount(Double.parseDouble(checkoutResponse.get("AMT")));
		} catch (Exception e) {
//			paypalBill.setAmount(NULL);
		}
		paypalBill.setType(type);
		paypalBill.setToken(checkoutResponse.get("TOKEN"));
		paypalBill.setPayerId(detailResponse.get("PAYERID"));
		paypalBill.setCorrelationId(checkoutResponse.get("CORRELATIONID"));
		paypalBill.setEmail(detailResponse.get("EMAIL"));
		paypalBill.setTransactionId(checkoutResponse.get("TRANSACTIONID"));
		paypalBill.setCurrencyCode(checkoutResponse.get("CURRENCYCODE"));
		paypalBill.setGateway(gateway);
		paypalBill.setFeeGateway(checkoutResponse.get("FEEAMT"));
		paypalBill.setTax(checkoutResponse.get("TAXAMT"));
		paypalBill.setTotal(String.valueOf(Double.parseDouble(checkoutResponse.get("AMT"))-Double.parseDouble(checkoutResponse.get("FEEAMT"))));
		paypalBill.setCreateTime(new Date());
//		paypalBill.persist();
		return paypalBill;
    }
    
    public static PaymentInfo buildMassPayBill(User user, String type, Map<String, String> response,HttpServletRequest req) {
    	PaymentInfo paypalBill = new PaymentInfo();
		paypalBill.setUserId(user.getId());
		try {
		} catch (Exception e) {
//			paypalBill.setAmount(NULL);
		}
		paypalBill.setAmount(Double.parseDouble(req.getParameter("total")));
		paypalBill.setEmail(req.getParameter("email"));
		paypalBill.setType(type);
		paypalBill.setCorrelationId(response.get("CORRELATIONID"));
		paypalBill.setCurrencyCode("USD");
		paypalBill.setCreateTime(new Date());
		paypalBill.setGateway(PaymentGateway.Paypal.toString());
//		paypalBill.persist();
		return paypalBill;
    }*/
	
}

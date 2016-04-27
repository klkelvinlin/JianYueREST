package com.jy.paypal.fields;

import java.util.HashMap;
import java.util.Map;

import com.jy.paypal.utils.Validator;

/**
 * Billing Period Details Fields
 * @author wdong
 *
 */
public class BillingPeriodDetails implements RequestFields {

	private static final long serialVersionUID = -544800512482354699L;
	
	/** 
	 * map that holds name value pair request values
	 */
    private final Map<String, String> nvpRequest;

	/**
	 * Unit for billing during this subscription period
	 * @author wdong
	 *
	 */
	public enum BillingPeriod {

        DAY("Day"),
        WEEK("Week"),
        SEMI_MONTH("SemiMonth"),
        MONTH("Month"),
        YEAR("Year");

        private String value;

        private BillingPeriod(String value) {
            this.value = value;
        }

        /** returns value name for nvp request */
        String getValue() {
            return value;
        }
    }
	
	/**
    *
    * @param period    Unit for billing during this subscription period.
    *                  For SemiMonth, billing is done on the 1st and 15th of
    *                  each month.
    *                  Note:
    *                  The combination of BillingPeriod and BillingFrequency
    *                  cannot exceed one year.
    * @param frequency Number of billing periods that make up one billing
    *                  cycle. The combination of billing frequency and billing
    *                  period must be less than or equal to one year. For
    *                  example, if the billing cycle is Month, the maximum
    *                  value for billing frequency is 12. Similarly, if the
    *                  billing cycle is Week, the maximum value for billing
    *                  frequency is 52.
    *                  Note:
    *                  If the billing period is SemiMonth., the billing frequency must be 1.
    * @param amount    Billing amount for each billing cycle during this
    *                  payment period. This amount does not include shipping
    *                  and tax amounts.
    *                  Note:
    *                  All amounts in the CreateRecurringPaymentsProfile
    *                  request must have the same currency.
    *                  Character length and limitations: Does not exceed
    *                  $10,000 USD in any currency. No currency symbol.
    *                  Regardless of currency, decimal separator is a period
    *                  (.). Equivalent to nine characters maximum for USD.
    * @param currency  Currency code.
    * @throws IllegalArgumentException
    */
   public BillingPeriodDetails(BillingPeriod period, int frequency,
           String amount, Currency currency) throws IllegalArgumentException {

       /* string represntation of the period */
       String billingPeriod = period.getValue();
       String billingFrequency = Integer.toString(frequency);

       /* validation */
       if (frequency < 0) {
           throw new IllegalArgumentException("Billing frequency cannot be 0");
       }
       if (billingPeriod.equals("Year") && frequency > 365) {
           throw new IllegalArgumentException("The combination of billing "
                   + "frequency (" + billingFrequency  + ") and billing "
                   + "period (" + billingPeriod + ") must be less than or "
                   + "equal to one year.");
       }
       if (billingPeriod.equals("Month") && frequency > 12) {
           throw new IllegalArgumentException("The combination of billing "
                   + "frequency (" + billingFrequency  + ") and billing "
                   + "period (" + billingPeriod + ") must be less than or "
                   + "equal to one year.");
       }
       if (billingPeriod.equals("Week") && frequency > 52) {
           throw new IllegalArgumentException("The combination of billing "
                   + "frequency (" + billingFrequency  + ") and billing "
                   + "period (" + billingPeriod + ") must be less than or "
                   + "equal to one year.");
       }
       if (billingPeriod.equals("Day") && frequency > 1) {
           throw new IllegalArgumentException("The combination of billing "
                   + "frequency (" + billingFrequency  + ") and billing "
                   + "period (" + billingPeriod + ") must be less than or "
                   + "equal to one year.");
       }
       if (billingPeriod.equals("SemiMonth") && frequency != 1) {
           throw new IllegalArgumentException("When billing period is set to "
                   + "Semi month, then billing frequecny has to be 1");
       }

       if (!Validator.isValidAmount(amount)) {
           throw new IllegalArgumentException("Amount has to have exactly two "
					+ "decimal places seaprated by \".\" - example: \"50.00\"");
       }

       nvpRequest = new HashMap<String, String>();

       /* required values */
       nvpRequest.put("BILLINGPERIOD", billingPeriod);
       nvpRequest.put("BILLINGFREQUENCY", billingFrequency);
       nvpRequest.put("AMT", amount);
       nvpRequest.put("CURRENCYCODE", currency.toString());
   }

   /** 
    * The number of billing cycles for payment period.
    * 
    * @param billingCycles     For the regular payment period, if no value is 
    *                          specified or the value is 0, the regular 
    *                          payment period continues until the profile is 
    *                          cancelled or deactivated. 
    *                          For the regular payment period, if the value is 
    *                          greater than 0, the regular payment period will 
    *                          expire after the trial period is finished and 
    *                          continue at the billing frequency for 
    *                          TotalBillingCycles cycles.
    */
   public void setTotalBillingCycles(int billingCycles) {

       String totalBillingCycles = Integer.toString(billingCycles);
       nvpRequest.put("TOTALBILLINGCYCLES", totalBillingCycles);
   }

   /**
    * 
    * @param period    Unit for billing during this subscription period. 
    *                  For SemiMonth, billing is done on the 1st and 15th of 
    *                  each month. 
    *                  Note: 
    *                  The combination of BillingPeriod and BillingFrequency 
    *                  cannot exceed one year.
    * @param frequency Number of billing periods that make up one billing 
    *                  cycle; required if you specify an optional trial 
    *                  period. The combination of billing frequency and 
    *                  billing period must be less than or equal to one year. 
    *                  For example, if the billing cycle is Month, the maximum 
    *                  value for billing frequency is 12. Similarly, if the 
    *                  billing cycle is Week, the maximum value for billing 
    *                  frequency is 52. 
    *                  Note: 
    *                  If the billing period is SemiMonth., the billing 
    *                  frequency must be 1.
    * @param amount    Billing amount for each billing cycle during this 
    *                  payment period. This amount does not include shipping 
    *                  and tax amounts. 
    *                  Note: 
    *                  All amounts in the CreateRecurringPaymentsProfile 
    *                  request must have the same currency. Character length 
    *                  and limitations: Does not exceed $10,000 USD in any 
    *                  currency. No currency symbol. Regardless of currency, 
    *                  decimal separator is a period (.). Equivalent to nine 
    *                  characters maximum for USD.
    * @throws IllegalArgumentException
    */
   public void setTrialBilling(BillingPeriod period, int frequency,
           String amount) throws IllegalArgumentException {

       /* string representations of period and frequency */
       String billingPeriod = period.getValue();
       String billingFrequency = Integer.toString(frequency);

       /* validation */
       if (billingPeriod.equals("Year") && frequency > 365) {
           throw new IllegalArgumentException("The combination of billing "
                   + "frequency (" + billingFrequency  + ") and billing "
                   + "period (" + billingPeriod + ") must be less than or "
                   + "equal to one year.");
       }
       if (billingPeriod.equals("Month") && frequency > 12) {
           throw new IllegalArgumentException("The combination of billing "
                   + "frequency (" + billingFrequency  + ") and billing "
                   + "period (" + billingPeriod + ") must be less than or "
                   + "equal to one year.");
       }
       if (billingPeriod.equals("Week") && frequency > 52) {
           throw new IllegalArgumentException("The combination of billing "
                   + "frequency (" + billingFrequency  + ") and billing "
                   + "period (" + billingPeriod + ") must be less than or "
                   + "equal to one year.");
       }
       if (billingPeriod.equals("Day") && frequency > 1) {
           throw new IllegalArgumentException("The combination of billing "
                   + "frequency (" + billingFrequency  + ") and billing "
                   + "period (" + billingPeriod + ") must be less than or "
                   + "equal to one year.");
       }
       if (billingPeriod.equals("SemiMonth") && frequency != 1) {
           throw new IllegalArgumentException("When billing period is set to "
                   + "Semi month, then billing frequecny has to be 1");
       }

       if (!Validator.isValidAmount(amount)) {
           throw new IllegalArgumentException("Amount has to have exactly two "
					+ "decimal places seaprated by \".\" - example: \"50.00\"");
       }

       nvpRequest.put("TRIALBILLINGPERIOD", billingPeriod);
       nvpRequest.put("TRIALBILLINGFREQUENCY", billingFrequency);
       nvpRequest.put("TRIALAMT", amount);
   }

   /**
    *
    * @param billingCycles The number of billing cycles for trial payment
    *                      period.
    */
   public void setTrialBillingCycles(int billingCycles) {

       String trialBillingCycles = Integer.toString(billingCycles);
       nvpRequest.put("TRIALTOTALBILLINGCYCLES", trialBillingCycles);
   }

   /**
    *
    * @param amount    Shipping amount for each billing cycle during this
    *                  payment period.
    *                  Note:
    *                  All amounts in the request must have the same currency.
    *                  Character length and limitations: Does not exceed
    *                  $10,000 USD in any currency. No currency symbol.
    *                  Regardless of currency, decimal separator is a period
    *                  (.). Equivalent to nine characters maximum for USD.
    * @throws IllegalArgumentException
    */
   public void setShippingAmount(String amount)
           throws IllegalArgumentException {

       if (!Validator.isValidAmount(amount)) {
           throw new IllegalArgumentException("Amount has to have exactly two "
					+ "decimal places seaprated by \".\" - example: \"50.00\"");
       }

       nvpRequest.put("SHIPPINGAMT", amount);
   }

   /**
    *
    * @param amount    Tax amount for each billing cycle during this payment
    *                  period.
    *                  Note:
    *                  All amounts in the request must have the same currency.
    *                  Character length and limitations: Does not exceed
    *                  $10,000 USD in any currency. No currency symbol.
    *                  Regardless of currency, decimal separator is a period
    *                  (.). Equivalent to nine characters maximum for USD.
    * @throws IllegalArgumentException
    */
   public void setTaxAmount(String amount) throws IllegalArgumentException {

       if (!Validator.isValidAmount(amount)) {
           throw new IllegalArgumentException("Amount has to have exactly two "
					+ "decimal places seaprated by \".\" - example: \"50.00\"");
       }

       nvpRequest.put("TAXAMT", amount);
   }

   public Map<String, String> getNVPRequest() {
       return new HashMap<String, String>(nvpRequest);
   }

   @Override
   public String toString() {

       return "instance of BillingPeriodDetails class with the values: "
               + "nvpRequest: " + nvpRequest.toString();
   }

}

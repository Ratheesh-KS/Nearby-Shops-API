package org.nearbyshops.Model.ModelMarkets;

import java.sql.Timestamp;

/**
 * Created by sumeet on 19/6/16.
 */
public class Market {


    // Table Name
    public static final String TABLE_NAME = "SERVICE_CONFIGURATION";

    // column Names
    public static final String SERVICE_CONFIGURATION_ID = "SERVICE_CONFIGURATION_ID";

//    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String LOGO_IMAGE_PATH = "LOGO_IMAGE_PATH";
    public static final String BACKDROP_IMAGE_PATH = "BACKDROP_IMAGE_PATH";

    public static final String SERVICE_NAME = "SERVICE_NAME";
    public static final String HELPLINE_NUMBER = "HELPLINE_NUMBER";


    public static final String DESCRIPTION_SHORT = "DESCRIPTION_SHORT";
    public static final String DESCRIPTION_LONG = "DESCRIPTION_LONG";

    public static final String ADDRESS = "ADDRESS";
    public static final String CITY = "CITY";
    public static final String PINCODE = "PINCODE";
    public static final String LANDMARK = "LANDMARK";
    public static final String STATE = "STATE";
    public static final String COUNTRY = "COUNTRY";

    public static final String ISO_COUNTRY_CODE = "ISO_COUNTRY_CODE";
    public static final String ISO_LANGUAGE_CODE = "ISO_LANGUAGE_CODE";
    public static final String ISO_CURRENCY_CODE = "ISO_CURRENCY_CODE";

//    public static final String SERVICE_TYPE = "SERVICE_TYPE";
//    public static final String SERVICE_LEVEL = "SERVICE_LEVEL";

    public static final String LAT_CENTER = "LAT_CENTER";
    public static final String LON_CENTER = "LON_CENTER";

    public static final String SERVICE_RANGE = "SERVICE_RANGE";

    public static final String CREATED = "CREATED";
    public static final String UPDATED = "UPDATED";

//    public static final String STYLE_URL = "STYLE_URL";
//    public static final String MQTT_SERVER_ADDRESS = "MQTT_SERVER_ADDRESS";

    // to be taken out to global Service Configuration
//    public static final String IS_OFFICIAL_SERVICE_PROVIDER = "IS_OFFICIAL_SERVICE_PROVIDER";
//    public static final String IS_VERIFIED = "IS_VERIFIED";
//    public static final String AVAILABLE_ON_PLAYSTORE = "AVAILABLE_ON_PLAYSTORE";
//    public static final String SERVICE_URL = "SERVICE_URL";



    // Create Table Statement
    public static final String createTablePostgres
            = "CREATE TABLE IF NOT EXISTS " + Market.TABLE_NAME + "("
            + " " + Market.SERVICE_CONFIGURATION_ID + " SERIAL PRIMARY KEY,"

            + " " + Market.LOGO_IMAGE_PATH + " text,"
            + " " + Market.BACKDROP_IMAGE_PATH + " text,"

            + " " + Market.SERVICE_NAME + " text,"
            + " " + Market.HELPLINE_NUMBER + " text,"

            + " " + Market.DESCRIPTION_SHORT + " text,"
            + " " + Market.DESCRIPTION_LONG + " text,"


            + " " + Market.ADDRESS + " text,"
            + " " + Market.CITY + " text,"
            + " " + Market.PINCODE + " BIGINT,"
            + " " + Market.LANDMARK + " text,"
            + " " + Market.STATE + " text,"
            + " " + Market.COUNTRY + " text not null default 'India',"

            + " " + Market.ISO_COUNTRY_CODE + " text not null default 'IN',"
            + " " + Market.ISO_LANGUAGE_CODE + " text,"
            + " " + Market.ISO_CURRENCY_CODE + " text,"

//            + " " + ServiceConfigurationLocal.SERVICE_TYPE + " INT,"
//            + " " + ServiceConfigurationLocal.SERVICE_LEVEL + " INT,"

            + " " + Market.LAT_CENTER + " FLOAT not null default 0,"
            + " " + Market.LON_CENTER + " FLOAT not null default 0,"
            + " " + Market.SERVICE_RANGE + " FLOAT not null default 0,"

            + " " + Market.UPDATED + " timestamp with time zone,"
            + " " + Market.CREATED + " timestamp with time zone NOT NULL DEFAULT now()"

//            + " " + ServiceConfigurationLocal.STYLE_URL + " text,"
//            + " " + ServiceConfigurationLocal.MQTT_SERVER_ADDRESS + " text"
            + ")";







    // Instance Variables
    private int serviceID;

//    private String imagePath;
    private String logoImagePath;
    private String backdropImagePath;

    private String serviceName;
    private String helplineNumber;

    private String descriptionShort;
    private String descriptionLong;

    private String address;
    private String city;
    private long pincode;

    private String landmark;
    private String state;
    private String country;

    private String ISOCountryCode;
    private String ISOLanguageCode;
    private String ISOCurrencyCode;

//    private Integer serviceType;
//    private Integer serviceLevel;

    private double latCenter;
    private double lonCenter;

    private double serviceRange;
//    private Integer shopDeliveryRangeMax;

    private Timestamp created;
    private Timestamp updated;



    // real time variables : the values of these variables are generated in real time.
    private Double rt_distance;
    private String rt_styleURL;

    private boolean rt_login_using_otp_enabled;
    private String rt_market_id_for_fcm;






    public static Market getDefaultConfig()
    {
        Market serviceConfig = new Market();


        serviceConfig.setServiceID(1);
        serviceConfig.setServiceName("Service name not set !");
        serviceConfig.setHelplineNumber("0-0000-0000");
        serviceConfig.setDescriptionShort("Short Description not provided.");
        serviceConfig.setDescriptionLong("long description not provided.");
        serviceConfig.setAddress("address");
        serviceConfig.setCity("city");
        serviceConfig.setPincode(123456l);
        serviceConfig.setLandmark("landmark");
        serviceConfig.setState("state");
        serviceConfig.setCountry("country");


        serviceConfig.setISOCountryCode("country code not provided");
        serviceConfig.setISOCurrencyCode("currency code not provided");
        serviceConfig.setISOLanguageCode("language code not provided");

        serviceConfig.setLatCenter(0);
        serviceConfig.setLonCenter(0);
        serviceConfig.setServiceRange(0);


//        serviceConfig.setStyleURL("https://example.com");
//        serviceConfig.setMqttServerAddress("tcp://mqtt.example.com:1883");

        return serviceConfig;
    }





    //    Getter and Setters


    public String getRt_market_id_for_fcm() {
        return rt_market_id_for_fcm;
    }

    public void setRt_market_id_for_fcm(String rt_market_id_for_fcm) {
        this.rt_market_id_for_fcm = rt_market_id_for_fcm;
    }

    public boolean isRt_login_using_otp_enabled() {
        return rt_login_using_otp_enabled;
    }

    public void setRt_login_using_otp_enabled(boolean rt_login_using_otp_enabled) {
        this.rt_login_using_otp_enabled = rt_login_using_otp_enabled;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getLogoImagePath() {
        return logoImagePath;
    }

    public void setLogoImagePath(String logoImagePath) {
        this.logoImagePath = logoImagePath;
    }

    public String getBackdropImagePath() {
        return backdropImagePath;
    }

    public void setBackdropImagePath(String backdropImagePath) {
        this.backdropImagePath = backdropImagePath;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHelplineNumber() {
        return helplineNumber;
    }

    public void setHelplineNumber(String helplineNumber) {
        this.helplineNumber = helplineNumber;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPincode() {
        return pincode;
    }

    public void setPincode(long pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getISOCountryCode() {
        return ISOCountryCode;
    }

    public void setISOCountryCode(String ISOCountryCode) {
        this.ISOCountryCode = ISOCountryCode;
    }

    public String getISOLanguageCode() {
        return ISOLanguageCode;
    }

    public void setISOLanguageCode(String ISOLanguageCode) {
        this.ISOLanguageCode = ISOLanguageCode;
    }

    public String getISOCurrencyCode() {
        return ISOCurrencyCode;
    }

    public void setISOCurrencyCode(String ISOCurrencyCode) {
        this.ISOCurrencyCode = ISOCurrencyCode;
    }

    public double getLatCenter() {
        return latCenter;
    }

    public void setLatCenter(double latCenter) {
        this.latCenter = latCenter;
    }

    public double getLonCenter() {
        return lonCenter;
    }

    public void setLonCenter(double lonCenter) {
        this.lonCenter = lonCenter;
    }


    public double getServiceRange() {
        return serviceRange;
    }

    public void setServiceRange(double serviceRange) {
        this.serviceRange = serviceRange;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Double getRt_distance() {
        return rt_distance;
    }

    public void setRt_distance(Double rt_distance) {
        this.rt_distance = rt_distance;
    }

    public String getRt_styleURL() {
        return rt_styleURL;
    }

    public void setRt_styleURL(String rt_styleURL) {
        this.rt_styleURL = rt_styleURL;
    }
}

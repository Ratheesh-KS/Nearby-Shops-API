package org.nearbyshops.Globals;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.Arrays;


public class ConfigUtility {




    public static void loadGlobalConfiguration()
    {

        Configuration configuration = getConfiguration();


        if(configuration==null)
        {
            System.out.println("Configuration is null : Unable to load Global Configuration ... " +
                    "please ensure that you have configuration file with name api_config.properties and jar file in the same folder !");


            return;
        }




        Constants.BASE_URI = configuration.getString("base.url");
        Constants.domain_name_for_api = configuration.getString("domain_name");

        Constants.POSTGRES_CONNECTION_URL = configuration.getString("connection.url");

        Constants.POSTGRES_USERNAME = configuration.getString("postgres.username");
        Constants.POSTGRES_PASSWORD = configuration.getString("postgres.password");


        Constants.ADMIN_EMAIL = configuration.getString("admin_email");
        Constants.ADMIN_PASSWORD = configuration.getString("admin_password");

        Constants.SMTP_SERVER_URL = configuration.getString("smtp_server_url");
        Constants.SMTP_PORT = configuration.getInt("smtp_port");
        Constants.SMTP_USERNAME = configuration.getString("smtp_username");
        Constants.SMTP_PASSWORD = configuration.getString("smtp_password");


        Constants.EMAIL_SENDER_NAME = configuration.getString("email_sender_name");
        Constants.EMAIL_ADDRESS_FOR_SENDER = configuration.getString("email_address_for_sender");



//        GlobalConstants.SERVICE_NAME = configuration.getString("service_name");
//        GlobalConstants.SERVICE_DESCRIPTION = configuration.getString("service_description");


//        GlobalConstants.LOCALE_COUNTRY_CODE = configuration.getString("locale_country_code");
//        GlobalConstants.LOCALE_CURRENCY_CODE = configuration.getString("locale_currency_code");




//        GlobalConstants.MAILGUN_DOMAIN = configuration.getString("mailgun.domain");
//        GlobalConstants.MAILGUN_API_KEY = configuration.getString("mailgun.apikey");
//        GlobalConstants.MAILGUN_NAME = configuration.getString("mailgun.name");
//        GlobalConstants.MAILGUN_EMAIL = configuration.getString("mailgun.email");


        Constants.market_id_for_fcm = configuration.getString("market_id_for_fcm");
        Constants.push_notification_provider = configuration.getInt("push_notification_provider");

        Constants.fcm_config_file_path = configuration.getString("configuration_file_path");
        Constants.fcm_database_url = configuration.getString("database_url");


        Constants.ONE_SIGNAL_API_KEY_ADMIN_APP = configuration.getString("one_signal_rest_api_key_admin");
        Constants.ONE_SIGNAL_APP_ID_ADMIN_APP = configuration.getString("one_signal_app_id_admin");

        Constants.ONE_SIGNAL_API_KEY_SHOP_OWNER_APP = configuration.getString("one_signal_rest_api_key_shop_owner");
        Constants.ONE_SIGNAL_APP_ID_SHOP_OWNER_APP = configuration.getString("one_signal_app_id_shop_owner");

        Constants.ONE_SIGNAL_API_KEY_END_USER_APP = configuration.getString("one_signal_rest_api_key_end_user");
        Constants.ONE_SIGNAL_APP_ID_END_USER_APP = configuration.getString("one_signal_app_id_end_user");

        Constants.style_url_for_maps = configuration.getString("style_url_for_maps");

        Constants.faqs_url_for_end_user = configuration.getString("faqs_for_end_user_url");
        Constants.tos_url_for_end_user = configuration.getString("terms_of_service_for_end_user_url");
        Constants.privacy_policy_url_for_end_user = configuration.getString("privacy_policy_for_end_user_url");

        Constants.faqs_url_for_shop_owner = configuration.getString("faqs_for_shop_owner_url");
        Constants.tos_url_for_shop_owner = configuration.getString("terms_of_service_for_shop_owner_url");
        Constants.privacy_policy_url_for_shop_owner = configuration.getString("privacy_policy_for_shop_owner_url");



        Constants.MSG91_SMS_SERVICE_API_KEY = configuration.getString("msg91.apikey");
        Constants.default_country_code_value = configuration.getString("default_country_code");
        Constants.sender_id_for_sms_value = configuration.getString("sender_id_for_sms");
        Constants.service_name_for_sms_value = configuration.getString("service_name_for_sms");


        Constants.enable_login_using_otp_value = configuration.getBoolean("enable_login_using_otp");

        Constants.trusted_market_aggregators_value = configuration.getStringArray("trusted_market_aggregators");



//        if(Globals.licensingRestrictionsEnabled)
//        {
            // automatic sds linking code
            boolean default_sds_url_exists = false;

            for(String url : Constants.trusted_market_aggregators_value)
            {

//            System.out.println("URL SDS : " + url);

                if(url.equals(Constants.SDS_URL))
                {
                    default_sds_url_exists = true;
                    break;
                }
            }


            if(!default_sds_url_exists)
            {
                // add a default sds url
                Constants.trusted_market_aggregators_value = Arrays.copyOf(Constants.trusted_market_aggregators_value, Constants.trusted_market_aggregators_value.length+1);
                Constants.trusted_market_aggregators_value[Constants.trusted_market_aggregators_value.length-1] = Constants.SDS_URL;

//            System.out.println("Insertion Made ");

//            for(String url : GlobalConstants.trusted_market_aggregators_value)
//            {
//                System.out.println("URL SDS After : " + url);
//            }
            }
//        }




        Constants.url_for_notification_icon_value = configuration.getString("url_for_notification_icon");


        Constants.delivery_range_for_shop_max_value = configuration.getInt("delivery_range_for_shop_max");
        Constants.delivery_range_for_shop_min_value = configuration.getInt("delivery_range_for_shop_min");


        Constants.app_service_charge_pick_for_shop_value = configuration.getInt("app_service_charge_for_pick_from_shop");
        Constants.app_service_charge_home_delivery_value = configuration.getInt("app_service_charge_for_home_delivery");


        Constants.min_account_balance_for_shop = configuration.getInt("min_account_balance_for_shop_owner");


        Constants.REFERRAL_CREDIT_FOR_SHOP_OWNER_REGISTRATION = configuration.getInt("referral_credit_for_shop_owner_registration");
        Constants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION = configuration.getInt("referral_credit_for_end_user_registration");


        Constants.joining_credit_for_shop_owner_value = configuration.getInt("joining_credit_for_shop_owner");
        Constants.joining_credit_for_end_user_value  = configuration.getInt("joining_credit_for_end_user");


        Constants.TOKEN_DURATION_MINUTES = configuration.getInt("token_duration_minutes");
        Constants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = configuration.getInt("email_verification_code_expiry_minutes");
        Constants.PHONE_OTP_EXPIRY_MINUTES = configuration.getInt("phone_otp_expiry_minutes");
        Constants.PASSWORD_RESET_CODE_EXPIRY_MINUTES = configuration.getInt("password_reset_code_expiry_minutes");


        Constants.max_limit = configuration.getInt("max_limit");


        printGlobalConfiguration();
    }


    private static void printGlobalConfiguration()
    {
        System.out.println("Printing API Configuration :  ");

        System.out.println("Base URI : " + Constants.BASE_URI);

        System.out.println("Postgres Connection URL : " + Constants.POSTGRES_CONNECTION_URL);
//        System.out.println("Connection URL Create DB : " + GlobalConstants.CONNECTION_URL_CREATE_DB);min_account_balance_for_shop_owner


        System.out.println("SMTP URL : " + Constants.SMTP_SERVER_URL);
        System.out.println("SMTP PORT : " + Constants.SMTP_PORT);
        System.out.println("SMTP USERNAME : " + Constants.SMTP_USERNAME);
        System.out.println("SMTP PASSWORD : " + Constants.SMTP_PASSWORD);

        System.out.println("Email Sender Name : " + Constants.EMAIL_SENDER_NAME);
        System.out.println("Email Address : " + Constants.EMAIL_ADDRESS_FOR_SENDER);




        System.out.println("Postgres Username : " + Constants.POSTGRES_USERNAME);
        System.out.println("Postgres Password : " + Constants.POSTGRES_PASSWORD);

        System.out.println("MSG91_KEY : " + Constants.MSG91_SMS_SERVICE_API_KEY);


        System.out.println("REFERRAL CREDIT FOR DRIVER REGISTRATION : " + Constants.REFERRAL_CREDIT_FOR_SHOP_OWNER_REGISTRATION);
        System.out.println("REFERRAL CREDIT FOR END USER REGISTRATION : " + Constants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION);

        System.out.println("Joining Credit For End-User : " + Constants.joining_credit_for_end_user_value);
        System.out.println("Joining Credit For Shop-Owner : " + Constants.joining_credit_for_shop_owner_value);


        System.out.println("Token Duration Minutes : " + Constants.TOKEN_DURATION_MINUTES);
        System.out.println("Email Verification Code Expiry Minutes : " + Constants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES);

        System.out.println("Phone OTP Expiry Minutes : " + Constants.PHONE_OTP_EXPIRY_MINUTES);
        System.out.println("Password Reset Code Expiry Minutes : " + Constants.PASSWORD_RESET_CODE_EXPIRY_MINUTES);

//        System.out.println("Trip Request Code Expiry Minutes : " + GlobalConstants.TRIP_REQUEST_EXPIRY_MINUTES);
//        System.out.println("Trip Request Code Expiry Extension Minutes : " + GlobalConstants.TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES);
//
//
//        System.out.println("Months to extend Taxi Registration Minimum : " + GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN);
//        System.out.println("Months to extend Taxi Registration Maximum : " + GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX);

        System.out.println("MAX LIMIT : " + Constants.max_limit);
//        System.out.println("MQTT Host address : " + GlobalConstants.NOTIFICATION_SERVER_HOST_MQTT);
    }



    public static void reloadConfiguration()
    {

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("api_config.properties"));


        try
        {
            ConfigUtility.configuration = builder.getConfiguration();

        }
        catch(ConfigurationException cex)
        {
            // loading of the configuration file failed

            System.out.println(cex.getStackTrace());

            configuration = null;
        }
    }





    private static Configuration configuration;


    public static org.apache.commons.configuration2.Configuration getConfiguration() {
        if (configuration == null) {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                                    .setFileName("./data/api_config.properties"));



            try {
                configuration = builder.getConfiguration();

            } catch (ConfigurationException cex) {
                // loading of the configuration file failed

                System.out.println(cex.getStackTrace());

                configuration = null;
            }

            return configuration;
        } else {
            return configuration;
        }
    }


}

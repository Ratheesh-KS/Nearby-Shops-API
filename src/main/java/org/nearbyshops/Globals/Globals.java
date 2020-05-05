package org.nearbyshops.Globals;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.DAOs.DAOAnalytics.DAOItemAnalytics;
import org.nearbyshops.DAOs.DAOBilling.DAOAddBalance;
import org.nearbyshops.DAOs.DAOBilling.DAOTransaction;
import org.nearbyshops.DAOs.DAOCartOrder.CartItemService;
import org.nearbyshops.DAOs.DAOCartOrder.CartService;
import org.nearbyshops.DAOs.DAOCartOrder.CartStatsDAO;
import org.nearbyshops.DAOs.DAOCartOrder.DeliveryAddressService;
import org.nearbyshops.DAOs.DAOImages.ItemImagesDAO;
import org.nearbyshops.DAOs.DAOImages.ShopImageDAO;
import org.nearbyshops.DAOs.DAOItemSpecification.*;
import org.nearbyshops.DAOs.DAOOrders.*;
import org.nearbyshops.DAOs.DAOReviewItem.FavoriteItemDAOPrepared;
import org.nearbyshops.DAOs.DAOReviewItem.ItemReviewDAOPrepared;
import org.nearbyshops.DAOs.DAOReviewItem.ItemReviewThanksDAOPrepared;
import org.nearbyshops.DAOs.DAOReviewShop.FavoriteBookDAOPrepared;
import org.nearbyshops.DAOs.DAOReviewShop.ShopReviewDAOPrepared;
import org.nearbyshops.DAOs.DAOReviewShop.ShopReviewThanksDAOPrepared;
import org.nearbyshops.DAOs.DAOSettings.ServiceConfigurationDAO;
import org.nearbyshops.DAOs.DAOPushNotifications.DAOOneSignal;
import org.nearbyshops.DAOs.*;
import org.nearbyshops.DAOs.DAORoles.*;
import org.nearbyshops.Model.ModelMarkets.Market;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by sumeet on 22/3/17.
 */
public class Globals {


    // secure randon for generating tokens
    public static SecureRandom random = new SecureRandom();


    public static ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
    public static ItemDAO itemDAO = new ItemDAO();
    public static ItemDAOJoinOuter itemDAOJoinOuter = new ItemDAOJoinOuter();
    public static ShopDAO shopDAO = new ShopDAO();

    public static ShopItemByShopDAO shopItemByShopDAO = new ShopItemByShopDAO();
    public static ShopItemDAO shopItemDAO = new ShopItemDAO();
    public static ShopItemByItemDAO shopItemByItemDAO = new ShopItemByItemDAO();


    public static CartService cartService = new CartService();
    public static CartItemService cartItemService = new CartItemService();
    public static CartStatsDAO cartStatsDAO = new CartStatsDAO();

    public static OrderService orderService = new OrderService();
    public static DAOOrderStaff daoOrderStaff = new DAOOrderStaff();
    public static DAOOrderDeliveryGuy daoOrderDeliveryGuy = new DAOOrderDeliveryGuy();
    public static DeliveryAddressService deliveryAddressService = new DeliveryAddressService();
    public static OrderItemService orderItemService = new OrderItemService();
    public static PlaceOrderDAO pladeOrderDAO = new PlaceOrderDAO();

    public static DAOOrderUtility daoOrderUtility = new DAOOrderUtility();

    public static FavoriteItemDAOPrepared favoriteItemDAOPrepared = new FavoriteItemDAOPrepared();
    public static ItemReviewDAOPrepared itemReviewDAOPrepared = new ItemReviewDAOPrepared();
    public static ItemReviewThanksDAOPrepared itemReviewThanksDAOPrepared = new ItemReviewThanksDAOPrepared();

    public static FavoriteBookDAOPrepared favoriteBookDAOPrepared = new FavoriteBookDAOPrepared();
    public static ShopReviewDAOPrepared shopReviewDAOPrepared = new ShopReviewDAOPrepared();
    public static ShopReviewThanksDAOPrepared shopReviewThanksDAO = new ShopReviewThanksDAOPrepared();


    public static ItemImagesDAO itemImagesDAO = new ItemImagesDAO();
    public static ShopImageDAO shopImageDAO = new ShopImageDAO();

//    public static DAOUserNotifications userNotifications = new DAOUserNotifications();
    public static DAOOneSignal oneSignalNotifications = new DAOOneSignal();


    public static DAOUserNew daoUserNew = new DAOUserNew();
    public static DAOUserTokens daoUserTokens = new DAOUserTokens();
    public static DAOStaff daoStaff = new DAOStaff();
    public static DAOShopStaff daoShopStaff = new DAOShopStaff();
    public static DAOUserUtility daoUserUtility = new DAOUserUtility();
    public static DAODeliveryGuy daoDeliveryGuy = new DAODeliveryGuy();

    public static DAOResetPassword daoResetPassword= new DAOResetPassword();
    public static DAOUserSignUp daoUserSignUp = new DAOUserSignUp();
    public static DAOEmailVerificationCodes daoEmailVerificationCodes=new DAOEmailVerificationCodes();
    public static DAOPhoneVerificationCodes daoPhoneVerificationCodes=new DAOPhoneVerificationCodes();


    public static DAOLoginUsingOTP daoLoginUsingOTP = new DAOLoginUsingOTP();
    public static DAOLoginUsingOTPNew daoLoginUsingOTPNew = new DAOLoginUsingOTPNew();


    public static ServiceConfigurationDAO serviceConfigDAO = new ServiceConfigurationDAO();

    // static reference for holding security accountApproved


    public static ItemSpecificationNameDAO itemSpecNameDAO = new ItemSpecificationNameDAO();
    public static ItemSpecNameDAOOuterJoin itemSpecNameDAOOuterJoin = new ItemSpecNameDAOOuterJoin();
    public static ItemSpecNameDAOInnerJoin itemSpecNameDAOInnerJoin = new ItemSpecNameDAOInnerJoin();

    public static ItemSpecificationValueDAO itemSpecificationValueDAO = new ItemSpecificationValueDAO();
    public static ItemSpecValueDAOJoinOuter itemSpecValueDAOJoinOuter = new ItemSpecValueDAOJoinOuter();
    public static ItemSpecValueDAOInnerJoin itemSpecValueDAOInnerJoin = new ItemSpecValueDAOInnerJoin();

    public static ItemSpecificationItemDAO itemSpecificationItemDAO = new ItemSpecificationItemDAO();

    public static DAOAddBalance daoAddBalance = new DAOAddBalance();
    public static DAOTransaction daoTransaction = new DAOTransaction();


    public static DAOItemAnalytics daoItemAnalytics = new DAOItemAnalytics();

    public static DAOUtility daoUtility = new DAOUtility();



    public static Object accountApproved;



    // Configure Connection Pooling


    private static HikariDataSource dataSource;


    public static HikariDataSource getDataSource() {


        if (dataSource == null) {


            org.apache.commons.configuration2.Configuration configuration = ConfigUtility.getConfiguration();


            if(configuration==null)
            {
                System.out.println("failed to load api configuration ... " +
                        "Configuration is null ...  : getDataSource() HikariCP !");

                return null ;
            }



//            String connection_url = configuration.getString("");
//            String username = configuration.getString(ConfigurationKeys.POSTGRES_USERNAME);
//            String password = configuration.getString(ConfigurationKeys.POSTGRES_PASSWORD);



            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(Constants.POSTGRES_CONNECTION_URL);
            config.setUsername(Constants.POSTGRES_USERNAME);
            config.setPassword(Constants.POSTGRES_PASSWORD);


            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }






    private static Gson gson;

    //Customize the gson behavior here
    public static Gson getGson() {
        if (gson == null) {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

        }
        return gson;
    }







    public static boolean licensingRestrictionsEnabled = true;
    private static Market market;



    public static double maxMarketRangeInKms = 30;
    public static double maxVendorCount = 10;



        public static Market getMarketConfiguration()
        {
            if(market ==null)
            {

                market = Globals.serviceConfigDAO.getServiceConfiguration(0.0,0.0);

                return market;
            }
            else
            {
                return market;
            }
        }




        public static void setMarketConfig(Market serviceConfig)
        {
            market = serviceConfig;
        }





        public static Mailer inHouseMailer;


        public static Mailer getMailerInstance()
        {
            if(inHouseMailer==null)
            {
                inHouseMailer = MailerBuilder
                        .withSMTPServer(Constants.SMTP_SERVER_URL, Constants.SMTP_PORT,
                                Constants.SMTP_USERNAME, Constants.SMTP_PASSWORD)
                        .buildMailer();

                return inHouseMailer;
            }
            else
            {
                return inHouseMailer;
            }
        }




        private static Random randomNew = new Random();


        public static char[] generateOTP(int length) {
            String numbers = "1234567890";

            char[] otp = new char[length];

            for(int i = 0; i< length ; i++) {
                otp[i] = numbers.charAt(randomNew.nextInt(numbers.length()));
            }

            return otp;
        }


        public static float distFrom(double lat1, double lng1, double lat2, double lng2) {

            double earthRadius = 6371; //kilometers
            double dLat = Math.toRadians(lat2-lat1);
            double dLng = Math.toRadians(lng2-lng1);
            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                            Math.sin(dLng/2) * Math.sin(dLng/2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            float dist = (float) (earthRadius * c);

    //        System.out.println("Distance : " + dist);

            return dist;
        }





        public static void sendFCMPushNotification(String topic, String title, String message, String notificationType)
        {

            if (notificationType==null)
            {
                notificationType = Constants.NOTIFICATION_TYPE_GENERAL;
            }

            // See documentation on defining a message payload.
            Message messageEndUser = Message.builder()
                    .setNotification(new Notification(title, message))
                    .putData("notification_type", notificationType)
                    .putData("notification_title", title)
                    .putData("notification_message", message)
                    .setTopic(topic)
                    .build();


            System.out.println("Topic : " + topic);


            try {


                String responseEndUser = FirebaseMessaging.getInstance().send(messageEndUser);
                System.out.println("Sent Notification to EndUser: " + responseEndUser);


            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }

        }

}

package org.nearbyshops.DAOPreparedOrders;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.OrderItem;
import org.nearbyshops.ModelDelivery.DeliveryAddress;

import org.nearbyshops.ModelEndpoint.OrderEndPoint;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelRoles.Endpoints.UserEndpoint;
import org.nearbyshops.ModelRoles.User;
import org.nearbyshops.ModelStats.OrderStats;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sumeet on 7/6/16.
 */


public class OrderService {


    private HikariDataSource dataSource = Globals.getDataSource();

//    private ShopDAO shopDAO = Globals.shopDAO;
//    private ShopItemDAO shopItemDAO = Globals.shopItemDAO;

//    public Order placeOrder(Order order, int cartID)
//    {
//
//        /*
//
//        1. Copy cart to order
//        2. Copy cart_item to order_item
//        3. update available_item_quantity from order to shop_item
//        4. delete cart_item
//        5. delete cart
//         */
//
//
//        int orderID = -1;
//        int rowCount = -1;
//        int rowCountAvailableItemQuantity= -1;
//        int status = 0;
//
//        orderID = copyCartToOrder(cartID);
//
//        if(orderID > 0)
//        {
//            status = 1;
//            rowCount = copyCartItemToOrderItem(orderID,cartID);
//
//        }
//
//        if(rowCount > 0)
//        {
//            status = 2;
//            rowCountAvailableItemQuantity = Globals.shopItemDAO.updateAvailableItemQuantity(orderID);
//
//            // delete cart_item here
//            Globals.cartItemService.deleteCartItem(null,cartID);
//
//            // delete cart here
//            Globals.cartService.deleteCart(cartID);
//
//        }
//
//        if(orderID>0 && rowCount>0)
//        {
//            Order tempOrder = readSingleOrder(orderID);
//
//            order.setOrderID(orderID);
//            order.setEndUserID(tempOrder.getEndUserID());
//            order.setShopID(tempOrder.getShopID());
//
//            if(order.getPickFromShop())
//            {
//               order.setDeliveryCharges(0);
//                order.setStatusPickFromShop(1);
//
//            }else
//            {
//                order.setDeliveryCharges((int) Globals.shopDAO.getShop(tempOrder.getShopID(),null,null).getDeliveryCharges());
//                order.setStatusHomeDelivery(1);
//            }
//
//            order.setPaymentReceived(false);
//            order.setDeliveryReceived(false);
//
//
//            updateOrder(order);
//
//            return readSingleOrder(orderID);
//
//        }
//
//        return null;
//    }
//
//
//
//    private int copyCartToOrder(int cartID)
//    {
//
//        String copyCartToOrder = "insert into customer_order " +
//                "(end_user_id,shop_id) " +
//                "select end_user_id , shop_id from cart where cart_id = " +
//                cartID;
//
//
//        Connection connection = null;
//        Statement statement = null;
//        int rowIdOfInsertedRow = -1;
//
//
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.createStatement();
//
//            rowIdOfInsertedRow = statement.executeUpdate(copyCartToOrder,Statement.RETURN_GENERATED_KEYS);
//            ResultSet rs = statement.getGeneratedKeys();
//
//
//            if(rs.next())
//            {
//                rowIdOfInsertedRow = rs.getInt(1);
//            }
//
//
//
//            System.out.println("Key autogenerated OrderPFS: " + rowIdOfInsertedRow);
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
//        {
//
//            try {
//
//                if(statement!=null)
//                {statement.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//
//                if(connection!=null)
//                {connection.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//
//        return rowIdOfInsertedRow;
//    }
//
//
//
//
//    private int copyCartItemToOrderItem(int orderID, int cartID)
//    {
//
//
//
//        String copyCartItemToOrderItem =
//
//                "insert into " +
//                        " order_item" +
//                        " (order_id" + "," + "item_id" + "," + " item_price_at_order" + ","
//                        + "item_quantity) " +
//
//                        " select " + orderID +
//                ", shop_item.item_id,item_price, cart_item.item_quantity from cart_item, cart,shop_item " +
//                " where " +
//                " cart.cart_id = cart_item.cart_id " +
//                " and " +
//                "shop_item.shop_id = cart.shop_id" +
//                " and " +
//                " shop_item.item_id = cart_item.item_id " +
//                " and " +
//                " cart.cart_id = " + cartID;
//
//
//
//
//        Connection connection = null;
//        Statement statement = null;
//        int rowCount = -1;
//
//
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.createStatement();
//
//            rowCount = statement.executeUpdate(copyCartItemToOrderItem,Statement.RETURN_GENERATED_KEYS);
//
//            //ResultSet rs = stmt.getGeneratedKeys();
//
//            //if(rs.next())
//            //{
//            //    rowIdOfInsertedRow = rs.getInt(1);
//            //}
//
//
//
//            System.out.println("Rows updated : copy cart item to order item : " + rowCount);
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
//        {
//
//            try {
//
//                if(statement!=null)
//                {statement.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//
//                if(connection!=null)
//                {connection.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//
//        return rowCount;
//    }







    public Order readSingleOrder(int orderID)
    {

        String query = "SELECT "

                 + Order.ORDER_ID + ","
                 + Order.DELIVERY_ADDRESS_ID + ","
                 + Order.DATE_TIME_PLACED + ","

                 + Order.DELIVERY_CHARGES + ","
//                 + Order.DELIVERY_RECEIVED + ","
//                 + Order.PAYMENT_RECEIVED + ","

                 + Order.DELIVERY_GUY_SELF_ID + ","
                 + Order.END_USER_ID + ","
                 + Order.PICK_FROM_SHOP + ","

                + Order.SHOP_ID + ","
                + Order.STATUS_HOME_DELIVERY + ","
                + Order.STATUS_PICK_FROM_SHOP + ""

                + " FROM " + Order.TABLE_NAME
                + " WHERE " + Order.ORDER_ID + " = " + orderID;

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        Order order = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while(rs.next())
            {
                order = new Order();

                order.setShopID(rs.getInt(Order.SHOP_ID));
                order.setDeliveryCharges(rs.getInt(Order.DELIVERY_CHARGES));
                order.setEndUserID(rs.getInt(Order.END_USER_ID));

                order.setOrderID(rs.getInt(Order.ORDER_ID));
                order.setPickFromShop(rs.getBoolean(Order.PICK_FROM_SHOP));
                order.setDateTimePlaced(rs.getTimestamp(Order.DATE_TIME_PLACED));

                order.setStatusHomeDelivery(rs.getInt(Order.STATUS_HOME_DELIVERY));
                order.setStatusPickFromShop(rs.getInt(Order.STATUS_PICK_FROM_SHOP));

//                order.setDeliveryReceived(rs.getBoolean(Order.DELIVERY_RECEIVED));
//                order.setPaymentReceived(rs.getBoolean(Order.PAYMENT_RECEIVED));

                order.setDeliveryAddressID(rs.getInt(Order.DELIVERY_ADDRESS_ID));
                order.setDeliveryGuySelfID(rs.getInt(Order.DELIVERY_GUY_SELF_ID));
            }


            //System.out.println("Total itemCategories queried " + itemCategoryList.size());



        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return order;
    }






    //    Boolean getDeliveryAddress,
//    Boolean getStats
    public OrderEndPoint readOrders(
                   Integer orderID, Integer endUserID, Integer shopID,
                   Boolean pickFromShop,
                   Integer homeDeliveryStatus, Integer pickFromShopStatus,
                   Integer deliveryGuyID,
                   Double latCenter, Double lonCenter,
                   Boolean pendingOrders,
                   String searchString,
                   String sortBy,
                   Integer limit, Integer offset,
                   boolean getRowCount,
                   boolean getOnlyMetadata
    )
    {

        String queryCount = "";

        String query = "SELECT "

                + "6371 * acos( cos( radians("
                + latCenter + ")) * cos( radians( " + DeliveryAddress.LATITUDE + ") ) * cos(radians( " + DeliveryAddress.LONGITUDE + " ) - radians("
                + lonCenter + "))"
                + " + sin( radians(" + latCenter + ")) * sin(radians( " + DeliveryAddress.LATITUDE + " ))) as distance" + ","

                + Order.TABLE_NAME + "." + Order.ORDER_ID + ","

                + Order.TABLE_NAME + "." + Order.END_USER_ID + ","
                + Order.TABLE_NAME + "." + Order.SHOP_ID + ","
                + Order.TABLE_NAME + "." + Order.DELIVERY_ADDRESS_ID + ","
                + Order.TABLE_NAME + "." + Order.DELIVERY_GUY_SELF_ID + ","

                + Order.TABLE_NAME + "." + Order.DELIVERY_OTP + ","

                + Order.TABLE_NAME + "." + Order.ITEM_COUNT + ","

                + Order.TABLE_NAME + "." + Order.ITEM_TOTAL + ","
                + Order.TABLE_NAME + "." + Order.APP_SERVICE_CHARGE + ","
                + Order.TABLE_NAME + "." + Order.DELIVERY_CHARGES + ","
                + Order.TABLE_NAME + "." + Order.NET_PAYABLE + ","

                + Order.TABLE_NAME + "." + Order.IS_CANCELLED_BY_END_USER + ","
                + Order.TABLE_NAME + "." + Order.REASON_FOR_CANCELLED_BY_SHOP + ","
                + Order.TABLE_NAME + "." + Order.REASON_FOR_CANCELLED_BY_USER + ","
                + Order.TABLE_NAME + "." + Order.REASON_FOR_ORDER_RETURNED + ","

                + Order.TABLE_NAME + "." + Order.DATE_TIME_PLACED + ","

                + Order.TABLE_NAME + "." + Order.PICK_FROM_SHOP + ","
                + Order.TABLE_NAME + "." + Order.STATUS_HOME_DELIVERY + ","
                + Order.TABLE_NAME + "." + Order.STATUS_PICK_FROM_SHOP + ","

//                + Order.TABLE_NAME + "." + Order.DELIVERY_RECEIVED + ","
//                + Order.TABLE_NAME + "." + Order.PAYMENT_RECEIVED + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.END_USER_ID + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.CITY + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.DELIVERY_ADDRESS + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LANDMARK + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.NAME + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LATITUDE + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LONGITUDE + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.PHONE_NUMBER + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.PINCODE + ","

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ""

//                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LATITUDE + ","
//                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LONGITUDE + ","

//                + " count( " + OrderItem.ITEM_ID + " ) as item_count, "
//                + " sum( " + OrderItem.ITEM_PRICE_AT_ORDER + " * " + OrderItem.ITEM_QUANTITY + ") as item_total "

                + " FROM " + Order.TABLE_NAME
                + " LEFT OUTER JOIN " + DeliveryAddress.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.DELIVERY_ADDRESS_ID + " = " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID + ")"
                + " LEFT OUTER JOIN " + User.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.DELIVERY_GUY_SELF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE TRUE ";


//        + " LEFT OUTER JOIN " + OrderItem.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.ORDER_ID + " = " + OrderItem.TABLE_NAME + "." + OrderItem.ORDER_ID + " ) "


//        boolean isFirst = true;

        if(endUserID !=null)
        {
            query = query + " AND " + Order.TABLE_NAME + "." + Order.END_USER_ID + " = " + endUserID;

        }

        if(shopID != null)
        {
            query = query + " AND " + Order.SHOP_ID + " = " + shopID;
        }




        if(searchString != null)
        {

            String queryPartSearch = " ( " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.NAME +" ilike '%" + searchString + "%'"
                    + " or CAST ( " + Order.TABLE_NAME + "." + Order.ORDER_ID + " AS text )" + " ilike '%" + searchString + "%'" + ") ";


            query = query + " AND " + queryPartSearch;
        }





        if(pendingOrders!=null)
        {
            String queryPartPending = "";


            if(pendingOrders)
            {
                queryPartPending = "(" + Order.STATUS_HOME_DELIVERY + " < " + OrderStatusHomeDelivery.PAYMENT_RECEIVED + ")";

            }
            else
            {
                queryPartPending = "(" + Order.STATUS_HOME_DELIVERY + " = " + OrderStatusHomeDelivery.PAYMENT_RECEIVED + ")";

            }



            query = query + " AND " + queryPartPending;
        }




        if(orderID!=null)
        {

            query = query + " AND " + Order.TABLE_NAME + "." + Order.ORDER_ID + " = " + orderID;
        }



        if(homeDeliveryStatus != null)
        {

            query = query + " AND " + Order.STATUS_HOME_DELIVERY + " = " + homeDeliveryStatus;
        }




        if(pickFromShopStatus != null)
        {
            query = query + " AND " + Order.STATUS_PICK_FROM_SHOP + " = " + pickFromShopStatus;
        }



        if(pickFromShop != null)
        {

            query = query + " AND " + Order.PICK_FROM_SHOP + " = " + pickFromShop;
        }


        if(deliveryGuyID != null)
        {
            query = query + " AND " + Order.DELIVERY_GUY_SELF_ID + " = " + deliveryGuyID;
        }




        // all the non-aggregate columns which are present in select must be present in group by also.
        query = query
                + " group by "
                + User.TABLE_NAME + "." + User.USER_ID + ","
                + Order.TABLE_NAME + "." + Order.ORDER_ID + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID ;


        queryCount = query;



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

                query = query + queryPartSortBy;
            }
        }



        if(limit != null)
        {

            String queryPartLimitOffset = "";

            if(offset!=null)
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

            }else
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
            }

            query = query + queryPartLimitOffset;
        }




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";




        OrderEndPoint endPoint = new OrderEndPoint();

        ArrayList<Order> ordersList = new ArrayList<Order>();


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        PreparedStatement statementCount = null;
        ResultSet resultSetCount = null;


        try {

            connection = dataSource.getConnection();

            int i = 0;


            if(!getOnlyMetadata) {


//                statement = connection.prepareStatement(queryJoin);

                statement = connection.prepareStatement(query);


                rs = statement.executeQuery();

                while (rs.next()) {

                    Order order = new Order();

                    order.setOrderID(rs.getInt(Order.ORDER_ID));

                    order.setEndUserID(rs.getInt(Order.END_USER_ID));
                    order.setShopID(rs.getInt(Order.SHOP_ID));
                    order.setDeliveryAddressID(rs.getInt(Order.DELIVERY_ADDRESS_ID));
                    order.setDeliveryGuySelfID(rs.getInt(Order.DELIVERY_GUY_SELF_ID));


                    order.setDeliveryOTP((Integer) rs.getObject(Order.DELIVERY_OTP));

                    order.setItemCount((Integer) rs.getObject(Order.ITEM_COUNT));

                    order.setItemTotal((Double) rs.getObject(Order.ITEM_TOTAL));
                    order.setAppServiceCharge(rs.getDouble(Order.APP_SERVICE_CHARGE));
                    order.setDeliveryCharges(rs.getDouble(Order.DELIVERY_CHARGES));
                    order.setNetPayable(rs.getDouble(Order.NET_PAYABLE));

                    order.setCancelledByEndUser(rs.getBoolean(Order.IS_CANCELLED_BY_END_USER));
                    order.setReasonCancelledByShop(rs.getString(Order.REASON_FOR_CANCELLED_BY_SHOP));
                    order.setReasonCancelledByUser(rs.getString(Order.REASON_FOR_CANCELLED_BY_USER));
                    order.setReasonForOrderReturned(rs.getString(Order.REASON_FOR_ORDER_RETURNED));

                    order.setDateTimePlaced(rs.getTimestamp(Order.DATE_TIME_PLACED));

                    order.setPickFromShop(rs.getBoolean(Order.PICK_FROM_SHOP));
                    order.setStatusHomeDelivery(rs.getInt(Order.STATUS_HOME_DELIVERY));
                    order.setStatusPickFromShop(rs.getInt(Order.STATUS_PICK_FROM_SHOP));


//                    order.setDeliveryReceived(rs.getBoolean(Order.DELIVERY_RECEIVED));
//                    order.setPaymentReceived(rs.getBoolean(Order.PAYMENT_RECEIVED));



                /*if(getDeliveryAddress!=null && getDeliveryAddress)
                {*/

                    DeliveryAddress address = new DeliveryAddress();

                    address.setEndUserID(rs.getInt(DeliveryAddress.END_USER_ID));
                    address.setCity(rs.getString(DeliveryAddress.CITY));
                    address.setDeliveryAddress(rs.getString(DeliveryAddress.DELIVERY_ADDRESS));

                    address.setId(rs.getInt(DeliveryAddress.ID));
                    address.setLandmark(rs.getString(DeliveryAddress.LANDMARK));
                    address.setName(rs.getString(DeliveryAddress.NAME));

                    address.setPhoneNumber(rs.getLong(DeliveryAddress.PHONE_NUMBER));
                    address.setPincode(rs.getLong(DeliveryAddress.PINCODE));


                    address.setLatitude(rs.getDouble(DeliveryAddress.LATITUDE));
                    address.setLongitude(rs.getDouble(DeliveryAddress.LONGITUDE));
                    address.setRt_distance(rs.getDouble("distance"));

                    order.setDeliveryAddress(address);
//                }



                    User deliveryGuy = new User();
                    deliveryGuy.setUserID(rs.getInt(User.USER_ID));
                    deliveryGuy.setName(rs.getString(User.NAME));
                    deliveryGuy.setPhone(rs.getString(User.PHONE));
                    deliveryGuy.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));

                    order.setRt_delivery_guy_profile(deliveryGuy);



//                    OrderStats orderStats = new OrderStats();
//                    orderStats.setOrderID(rs.getInt("order_id"));
//                    orderStats.setItemCount(rs.getInt("item_count"));
//                    orderStats.setItemTotal(rs.getInt("item_total"));
//                    order.setOrderStats(orderStats);


                    ordersList.add(order);
                }


                endPoint.setResults(ordersList);
            }






            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;



                resultSetCount = statementCount.executeQuery();

                while(resultSetCount.next())
                {
                    System.out.println("Item Count : " + String.valueOf(endPoint.getItemCount()));
                    endPoint.setItemCount(resultSetCount.getInt("item_count"));
                }
            }



        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return endPoint;
    }






    public int updateOrder(Order order)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET "
                + Order.END_USER_ID + " = ?,"
                + " " + Order.SHOP_ID + " = ?,"
                + " " + Order.STATUS_HOME_DELIVERY + " = ?,"
                + " " + Order.STATUS_PICK_FROM_SHOP + " = ?,"

//                + " " + Order.PAYMENT_RECEIVED + " = ?,"
//                + " " + Order.DELIVERY_RECEIVED + " = ?,"

                + " " + Order.DELIVERY_CHARGES + " = ?,"
                + " " + Order.DELIVERY_ADDRESS_ID + " = ?,"
                      + Order.DELIVERY_GUY_SELF_ID + " = ?,"
                      + Order.PICK_FROM_SHOP + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        int i = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);


            statement.setObject(++i,order.getEndUserID());
            statement.setObject(++i,order.getShopID());
            statement.setObject(++i,order.getStatusHomeDelivery());
            statement.setObject(++i,order.getStatusPickFromShop());

//            statement.setObject(5,order.getPaymentReceived());
//            statement.setObject(6,order.getDeliveryReceived());

            statement.setObject(++i,order.getDeliveryCharges());
            statement.setObject(++i,order.getDeliveryAddressID());
            statement.setObject(++i,order.getDeliveryGuySelfID());
            statement.setObject(++i,order.isPickFromShop());
            statement.setObject(++i,order.getOrderID());


            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return updatedRows;
    }



    public Order readStatusHomeDelivery(int orderID)
    {

        String query = "SELECT "

//                + OrderPFS.ORDER_ID + ","
//                + OrderPFS.DELIVERY_ADDRESS_ID + ","
//                + OrderPFS.DATE_TIME_PLACED + ","

//                + OrderPFS.DELIVERY_CHARGES + ","

//                + Order.DELIVERY_RECEIVED + ","
//                + OrderPFS.PAYMENT_RECEIVED + ","

                + Order.DELIVERY_GUY_SELF_ID + ","
//                + OrderPFS.END_USER_ID + ","
//                + OrderPFS.PICK_FROM_SHOP + ","

                + Order.SHOP_ID + ","
                + Order.END_USER_ID + ","
                + Order.STATUS_HOME_DELIVERY + ""
//                + OrderPFS.STATUS_PICK_FROM_SHOP + ""

                + " FROM " + Order.TABLE_NAME
                + " WHERE " + Order.ORDER_ID + " = " + orderID;

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        Order order = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while(rs.next())
            {
                order = new Order();


//                order.setDeliveryCharges(rs.getInt(OrderPFS.DELIVERY_CHARGES));
//                order.setEndUserID(rs.getInt(OrderPFS.END_USER_ID));

                order.setOrderID(orderID);

//                order.setDeliveryReceived(rs.getBoolean(Order.DELIVERY_RECEIVED));

                order.setShopID(rs.getInt(Order.SHOP_ID));
                order.setStatusHomeDelivery(rs.getInt(Order.STATUS_HOME_DELIVERY));
                order.setEndUserID(rs.getInt(Order.END_USER_ID));
                order.setDeliveryGuySelfID(rs.getInt(Order.DELIVERY_GUY_SELF_ID));



//                order.setPickFromShop(rs.getBoolean(OrderPFS.PICK_FROM_SHOP));
//                order.setDateTimePlaced(rs.getTimestamp(OrderPFS.DATE_TIME_PLACED));


//                order.setStatusPickFromShop(rs.getInt(OrderPFS.STATUS_PICK_FROM_SHOP));


//                order.setPaymentReceived(rs.getBoolean(OrderPFS.PAYMENT_RECEIVED));
//
//               order.setDeliveryAddressID((Integer) rs.getObject(OrderPFS.DELIVERY_ADDRESS_ID));

            }


            //System.out.println("Total itemCategories queried " + itemCategoryList.size());



        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return order;
    }




    public int updateStatusHomeDelivery(Order order)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET "
//                + OrderPFS.END_USER_ID + " = ?,"
//                + " " + OrderPFS.SHOP_ID + " = ?,"
                + " " + Order.STATUS_HOME_DELIVERY + " = ?"
//                + " " + OrderPFS.STATUS_PICK_FROM_SHOP + " = ?"
//                + " " + OrderPFS.PAYMENT_RECEIVED + " = ?,"
//                + " " + OrderPFS.DELIVERY_RECEIVED + " = ?"
//                + " " + OrderPFS.DELIVERY_CHARGES + " = ?,"
//                + " " + OrderPFS.DELIVERY_ADDRESS_ID + " = ?,"
//                + OrderPFS.DELIVERY_GUY_SELF_ID + " = ?"
//                + OrderPFS.PICK_FROM_SHOP + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

//            statement.setObject(1,order.getEndUserID());
//            statement.setObject(1,order.getShopID());
            statement.setObject(1,order.getStatusHomeDelivery());
//            statement.setObject(4,order.getStatusPickFromShop());
//            statement.setObject(2,order.getPaymentReceived());
//            statement.setObject(3,order.getDeliveryReceived());
//            statement.setObject(7,order.getDeliveryCharges());
//            statement.setObject(8,order.getDeliveryAddressID());
//            statement.setObject(2,order.getDeliveryGuySelfID());
//            statement.setObject(10,order.getPickFromShop());
            statement.setObject(2,order.getOrderID());


            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return updatedRows;
    }




    public int orderCancelledByEndUser(Integer orderID)
    {
        Order order = readStatusHomeDelivery(orderID);

        if(order!=null) {

            int status = order.getStatusHomeDelivery();

            if (status == OrderStatusHomeDelivery.ORDER_PLACED ||
                    status == OrderStatusHomeDelivery.ORDER_CONFIRMED ||
                    status == OrderStatusHomeDelivery.ORDER_PACKED)
            {
                order.setStatusHomeDelivery(OrderStatusHomeDelivery.CANCELLED_BY_USER);
            }
            else
            {
                return 0;
            }


            return updateStatusHomeDelivery(order);
        }

        return 0;
    }

}

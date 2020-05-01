package org.nearbyshops.RESTEndpoints.RESTEndpointsOrder;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.nearbyshops.Globals.Constants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.ModelEndpoint.OrderEndPoint;
import org.nearbyshops.Model.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



@Singleton
@Path("/api/Order")
public class OrderResource {




	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrder(Order order, @QueryParam("CartID") int cartID)
	{

		int orderId = Globals.pladeOrderDAO.placeOrderNew(order, cartID);

		if (orderId != -1) {

			Order orderResult = Globals.orderService.getOrderDetails(orderId);



			// send push notification
			if(Constants.push_notification_provider==1)
			{

				// send notification using fcm
				String topic = Constants.market_id_for_fcm + "shop_" + orderResult.getShopID();

				String notificationTitle = "Order Delivered";
				String notificationMessage = "You have received an order. Please check the order and respond to the customer !";

				Globals.sendFCMPushNotification(topic,notificationTitle,notificationMessage,Constants.NOTIFICATION_ORDER_RECIEVED);

			}




			return Response.status(Status.CREATED)
					.build();


		}
		else {

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}

	}





	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constants.ROLE_END_USER})
	public Response getOrders(
			@QueryParam("FilterOrdersByShopID") boolean filterOrdersByShopID,
			@QueryParam("FilterOrdersByUserID") boolean filterOrdersByUserID,
			@QueryParam("GetDeliveryProfile") boolean getDeliveryProfile,
			@QueryParam("DeliveryGuyID")Integer deliveryGuyID,
			@QueryParam("PickFromShop") Boolean pickFromShop,
			@QueryParam("StatusHomeDelivery")Integer homeDeliveryStatus,
			@QueryParam("StatusPickFromShopStatus")Integer pickFromShopStatus,
			@QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
			@QueryParam("PendingOrders") Boolean pendingOrders,
			@QueryParam("SearchString") String searchString,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit")int limit, @QueryParam("Offset")int offset,
			@QueryParam("GetRowCount")boolean getRowCount,
			@QueryParam("MetadataOnly")boolean getOnlyMetaData
	)
	{

//		@QueryParam("ShopID")Integer shopID
//		@QueryParam("UserID")Integer userID
//		@QueryParam("DeliveryGuyID")Integer deliveryGuyID

		Integer shopID = null;
		Integer userID = null;
//		Integer deliveryGuyID = null;




		// *********************** second Implementation

		User user = (User) Globals.accountApproved;


		if(filterOrdersByShopID)
		{
			if(user.getRole()== Constants.ROLE_SHOP_ADMIN_CODE)
			{
				shopID = Globals.daoUserUtility.getShopIDForShopAdmin(user.getUserID());
			}
			else if(user.getRole()== Constants.ROLE_SHOP_STAFF_CODE)
			{
				shopID = Globals.daoUserUtility.getShopIDforShopStaff(user.getUserID());
			}

		}



		if(filterOrdersByUserID)
		{
			// when an endpoint requires you to show orders only for given user. this is required when staff member is logged in as end user in end user app
			userID = user.getUserID();
		}



		if(limit >= Constants.max_limit)
		{
			limit = Constants.max_limit;
		}



		OrderEndPoint endpoint  = null;


		if(getDeliveryProfile)
		{
			endpoint = Globals.orderService.getOrdersListWithDeliveryProfile(
					userID,shopID, pickFromShop,
					homeDeliveryStatus,pickFromShopStatus,
					deliveryGuyID,
					latCenter,lonCenter,
					pendingOrders,
					searchString,
					sortBy,limit,offset,
					getRowCount,getOnlyMetaData);


		}
		else
		{
			endpoint = Globals.orderService.getOrdersList(
					userID,shopID, pickFromShop,
					homeDeliveryStatus,pickFromShopStatus,
					deliveryGuyID,
					pendingOrders,
					searchString,
					sortBy,limit,offset,
					getRowCount,getOnlyMetaData);


		}






		endpoint.setLimit(limit);
		endpoint.setOffset(offset);
		endpoint.setMax_limit(Constants.max_limit);




//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		//Marker

		return Response.status(Status.OK)
				.entity(endpoint)
				.build();
	}







	// requires authentication by the Distributor
	@PUT
	@Path("/CancelByUser/{OrderID}")
	@RolesAllowed({Constants.ROLE_END_USER})
	public Response cancelledByShop(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.daoOrderUtility.readStatusHomeDelivery(orderID);

		User user = (User) Globals.accountApproved;



		int rowCount = Globals.orderService.orderCancelledByEndUser(orderID);

		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.build();
		}



		return Response.status(Status.NOT_MODIFIED)
				.build();

	}



}

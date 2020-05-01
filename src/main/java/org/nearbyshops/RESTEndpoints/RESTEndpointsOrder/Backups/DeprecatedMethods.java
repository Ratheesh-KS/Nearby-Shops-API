package org.nearbyshops.RESTEndpoints.RESTEndpointsOrder.Backups;

import org.nearbyshops.Globals.Constants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ModelEndpoint.OrderEndPoint;
import org.nearbyshops.Model.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DeprecatedMethods {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constants.ROLE_END_USER, Constants.ROLE_ADMIN})
    public Response getOrders(
            @QueryParam("OrderID")Integer orderID,
            @QueryParam("ShopID")Integer shopID,
            @QueryParam("PickFromShop") Boolean pickFromShop,
            @QueryParam("StatusHomeDelivery")Integer homeDeliveryStatus,
            @QueryParam("StatusPickFromShopStatus")Integer pickFromShopStatus,
            @QueryParam("DeliveryGuyID")Integer deliveryGuyID,
            @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
            @QueryParam("PendingOrders") Boolean pendingOrders,
            @QueryParam("SearchString") String searchString,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData
    )
    {


        try
        {


            Integer endUserID = null;
            endUserID = ((User) Globals.accountApproved).getUserID();



            if(limit!=null)
            {
                if(limit >= Constants.max_limit)
                {
                    limit = Constants.max_limit;
                }

                if(offset==null)
                {
                    offset = 0;
                }
            }


            getRowCount=true;



            OrderEndPoint endpoint = Globals.orderService.getOrdersList(
                    endUserID,shopID, pickFromShop,
                    homeDeliveryStatus,pickFromShopStatus,
                    deliveryGuyID,
                    pendingOrders,
                    searchString,
                    sortBy,limit,offset,
                    getRowCount,getOnlyMetaData);




            if(limit!=null)
            {
                endpoint.setLimit(limit);
                endpoint.setOffset(offset);
                endpoint.setMax_limit(Constants.max_limit);
            }



            return Response.status(Response.Status.OK)
                    .entity(endpoint)
                    .build();


        }
        catch (Exception e)
        {

            e.printStackTrace();
        }




        return null;
    }




    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constants.ROLE_SHOP_STAFF, Constants.ROLE_SHOP_ADMIN, Constants.ROLE_DELIVERY_GUY_SELF, Constants.ROLE_DELIVERY_GUY})
    public Response getOrders(@QueryParam("OrderID")Integer orderID,
                              @QueryParam("EndUserID")Integer endUserID,
                              @QueryParam("ShopID")Integer shopID,
                              @QueryParam("PickFromShop") Boolean pickFromShop,
                              @QueryParam("StatusHomeDelivery")Integer homeDeliveryStatus,
                              @QueryParam("StatusPickFromShopStatus")Integer pickFromShopStatus,
                              @QueryParam("DeliveryGuyID")Integer deliveryGuyID,
                              @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
                              @QueryParam("PendingOrders") Boolean pendingOrders,
                              @QueryParam("SearchString") String searchString,
                              @QueryParam("SortBy") String sortBy,
                              @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                              @QueryParam("GetRowCount")boolean getRowCount,
                              @QueryParam("MetadataOnly")boolean getOnlyMetaData)


    {

        // *********************** second Implementation


        if(limit!=null)
        {
            if(limit >= Constants.max_limit)
            {
                limit = Constants.max_limit;
            }

            if(offset==null)
            {
                offset = 0;
            }
        }



        getRowCount=true;


        OrderEndPoint endpoint = Globals.orderService.getOrdersList(
                endUserID,shopID, pickFromShop,
                homeDeliveryStatus,pickFromShopStatus,
                deliveryGuyID,
                pendingOrders,
                searchString,
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData);




        if(limit!=null)
        {
            endpoint.setLimit(limit);
            endpoint.setOffset(offset);
            endpoint.setMax_limit(Constants.max_limit);
        }



/*
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
*/

        //Marker

        return Response.status(Response.Status.OK)
                .entity(endpoint)
                .build();
    }



}

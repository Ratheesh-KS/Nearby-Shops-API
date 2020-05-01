package org.nearbyshops.RESTEndpoints.RESTEndpointRoles;


import org.nearbyshops.Globals.Constants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.Model.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.nearbyshops.Globals.Globals.daoShopStaff;
import static org.nearbyshops.Globals.Globals.daoUserUtility;

/**
 * Created by sumeet on 30/8/17.
 */


@Path("/api/v1/User/ShopStaffLogin")
public class ShopStaffLoginRESTEndpoint {



    @PUT
    @Path("/UpdateStaffLocation")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constants.ROLE_SHOP_ADMIN, Constants.ROLE_SHOP_STAFF})
    public Response updateStaffLocation(ShopStaffPermissions permissions)
    {

        permissions.setStaffUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoShopStaff.updateShopStaffLocation(permissions);


        if(rowCount >= 1)
        {
            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }




    @PUT
    @Path("/UpgradeUser/{emailorphone}/{Role}/{SecretCode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constants.ROLE_SHOP_ADMIN})
    public Response upgradeUserToShopStaff(@PathParam("emailorphone")String emailorphone,@PathParam("Role")int role,
                                           @PathParam("SecretCode")int secretCode)
    {

        int shopAdminID = ((User)Globals.accountApproved).getUserID();
        int shopID = daoUserUtility.getShopIDForShopAdmin(shopAdminID);


        int userID = daoUserUtility.getUserID(emailorphone);
        int rowCount = daoShopStaff.upgradeUserToStaff(userID,shopID,secretCode,role);



        if(rowCount >= 1)
        {
            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }




    @PUT
    @Path("/UpdateStaffPermissions")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constants.ROLE_SHOP_ADMIN})
    public Response updateStaffPermissions(ShopStaffPermissions permissions)
    {

        int shopAdminID = ((User)Globals.accountApproved).getUserID();
        int shopID = daoUserUtility.getShopIDForShopAdmin(shopAdminID);
        permissions.setShopID(shopID);


        int rowCount = daoShopStaff.updateShopStaffPermissions(permissions);


        if(rowCount >= 1)
        {
            return Response.status(Response.Status.OK)
                    .build();
        }
        else if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }





    @GET
    @Path("/GetStaffPermissions/{UserID}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constants.ROLE_SHOP_ADMIN})
    public Response getPermissionDetails(@PathParam("UserID")int userID)
    {

        ShopStaffPermissions permissions = daoShopStaff.getShopStaffPermissions(userID);


//        GsonBuilder gsonBuilder = new GsonBuilder();
//
//        Gson gson =  gsonBuilder
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .create();

//        System.out.println(gson.toJson(permissions));



        if(permissions!=null)
        {
            return Response.status(Response.Status.OK)
                    .entity(permissions)
                    .build();
        }
        else
        {
            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

    }




}

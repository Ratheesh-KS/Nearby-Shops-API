package org.nearbyshops.RESTEndpoints.RESTEndpointsItemSpecifications;


import org.nearbyshops.DAOs.DAOItemSpecification.ItemSpecificationItemDAO;
import org.nearbyshops.Globals.Constants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ModelItemSpecification.ItemSpecificationItem;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sumeet on 5/3/17.
 */


@Path("/api/v1/ItemSpecificationItem")
public class ItemSpecItemResource {

    private ItemSpecificationItemDAO itemSpecItemDAO = Globals.itemSpecificationItemDAO;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF, Constants.ROLE_SHOP_ADMIN})
    public Response saveItemSpecName(ItemSpecificationItem itemSpecItem)
    {
        int idOfInsertedRow = -1;
        idOfInsertedRow = itemSpecItemDAO.saveItemSpecItem(itemSpecItem);


        if(idOfInsertedRow >=1)
        {
            return Response.status(Response.Status.CREATED)
                    .build();
        }
        else {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


    }




    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF, Constants.ROLE_SHOP_ADMIN})
    public Response deleteItemSpecItem(@QueryParam("ItemSpecValueID")int itemSpecValueID,
                                       @QueryParam("ItemID")int itemID)
    {

        int rowCount = itemSpecItemDAO.deleteItemSpecItem(itemID,itemSpecValueID);


        if(rowCount>=1)
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemSpecName(
            @QueryParam("ItemSpecNameID") Integer itemSpecNameID,
            @QueryParam("ItemID") Integer itemID)
    {


        List<ItemSpecificationItem> itemsList = itemSpecItemDAO.getItemSpecName(
          itemSpecNameID,itemID
        );


//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


        //Marker

        return Response.status(Response.Status.OK)
                .entity(itemsList)
                .build();
    }









}

package org.nearbyshops.RESTEndpoints;

import org.nearbyshops.DAOs.ItemCategoryDAO;
import org.nearbyshops.DAOs.ShopItemByItemDAO;
import org.nearbyshops.DAOs.ShopItemByShopDAO;
import org.nearbyshops.DAOs.ShopItemDAO;
import org.nearbyshops.Globals.Constants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.Model.ShopItem;
import org.nearbyshops.Model.ModelEndpoint.ShopItemEndPoint;
import org.nearbyshops.Model.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.Model.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/api/v1/ShopItem")
public class ShopItemResource {



	private ShopItemByShopDAO shopItemByShopDAO = Globals.shopItemByShopDAO;
	private ShopItemByItemDAO shopItemByItemDAO = Globals.shopItemByItemDAO;
	private ShopItemDAO shopItemDAO = Globals.shopItemDAO;
	private ItemCategoryDAO itemCategoryDAO = Globals.itemCategoryDAO;



	@PUT
	@Path("/UpdateBulk")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constants.ROLE_SHOP_ADMIN, Constants.ROLE_SHOP_STAFF})
	public Response updateShopItemBulk(List<ShopItem> itemList)
	{


		int rowCountSum = 0;
		int shopID = 0;


		User shopAdmin = (User) Globals.accountApproved;


		if(shopAdmin.getRole()== Constants.ROLE_SHOP_ADMIN_CODE)
		{
			shopID = Globals.daoUserUtility.getShopIDForShopAdmin(shopAdmin.getUserID());
		}
		else if(shopAdmin.getRole()== Constants.ROLE_SHOP_STAFF_CODE)
		{

			shopID = Globals.daoUserUtility.getShopIDforShopStaff(shopAdmin.getUserID());
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(shopAdmin.getUserID());



			if(!permissions.isPermitUpdateItemsInShop())
			{
				// staff member do not have this permission
				throw new ForbiddenException("Not Permitted !");
			}

		}




		for(ShopItem shopItem : itemList)
		{
			shopItem.setShopID(shopID);
			rowCountSum = rowCountSum + shopItemDAO.updateShopItem(shopItem);
		}





		if(rowCountSum ==  itemList.size())
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		else if( rowCountSum < itemList.size() && rowCountSum > 0)
		{

			return Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();
		}
		else if(rowCountSum == 0 ) {

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}





	@POST
	@Path("/CreateBulk")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constants.ROLE_SHOP_ADMIN, Constants.ROLE_SHOP_STAFF})
	public Response createShopItemBulk(List<ShopItem> itemList)
	{
		int rowCountSum = 0;



		User user = (User) Globals.accountApproved;

		if(user.getRole()== Constants.ROLE_SHOP_STAFF_CODE) {

			int shopID = Globals.daoUserUtility.getShopIDforShopStaff(user.getUserID());
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());


			if (!permissions.isPermitAddRemoveItems()) {
				// staff member do not have this permission
				throw new ForbiddenException("Not Permitted !");
			}



			for(ShopItem shopItem : itemList)
			{
				shopItem.setShopID(shopID);
				rowCountSum = rowCountSum + shopItemDAO.insertShopItem(shopItem);
			}


		}
		else if(user.getRole()== Constants.ROLE_SHOP_ADMIN_CODE)
		{
			int shopID = Globals.daoUserUtility.getShopIDForShopAdmin(user.getUserID());


			for(ShopItem shopItem : itemList)
			{
				shopItem.setShopID(shopID);
				rowCountSum = rowCountSum + shopItemDAO.insertShopItem(shopItem);
			}
		}




		if(rowCountSum ==  itemList.size())
		{

			return Response.status(Status.OK)
					.build();
		}
		else if( (rowCountSum < itemList.size()) && (rowCountSum > 0))
		{

			return Response.status(Status.PARTIAL_CONTENT)
					.build();
		}
		else if(rowCountSum == 0 ) {

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}

		return null;
	}







	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constants.ROLE_SHOP_ADMIN})
	public Response createShopItem(ShopItem shopItem)
	{
		int rowCount = 0;
		int shopID = 0;

		User user = (User) Globals.accountApproved;


		if(user.getRole()== Constants.ROLE_SHOP_ADMIN_CODE)
		{
			shopID = Globals.daoUserUtility.getShopIDForShopAdmin(user.getUserID());
		}
		else if (user.getRole()== Constants.ROLE_SHOP_STAFF_CODE)
		{

			shopID = Globals.daoUserUtility.getShopIDforShopStaff(user.getUserID());
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());


			if (!permissions.isPermitUpdateItemsInShop()) {
				// staff member do not have this permission
				throw new ForbiddenException("Not Permitted !");
			}
		}




		shopItem.setShopID(shopID);
		rowCount = shopItemDAO.insertShopItem(shopItem);



		if(rowCount == 1)
		{

			return Response.status(Status.CREATED)
								.build();
			
		}else if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}






	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constants.ROLE_SHOP_ADMIN, Constants.ROLE_SHOP_STAFF})
	public Response updateShopItem(ShopItem shopItem)
	{

//		System.out.println("Inside Resource Method !");

		int rowCount = 0;

		int shopID = 0;


		User user = (User) Globals.accountApproved;


		if(user.getRole()== Constants.ROLE_SHOP_ADMIN_CODE)
		{
			shopID = Globals.daoUserUtility.getShopIDForShopAdmin(user.getUserID());
		}
		else if (user.getRole()== Constants.ROLE_SHOP_STAFF_CODE)
		{

			shopID = Globals.daoUserUtility.getShopIDforShopStaff(user.getUserID());
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());


			if (!permissions.isPermitUpdateItemsInShop()) {
				// staff member do not have this permission
				throw new ForbiddenException("Not Permitted !");
			}
		}



		shopItem.setShopID(shopID);
		rowCount = shopItemDAO.updateShopItem(shopItem);

		
		if(rowCount == 1)
		{

			return Response.status(Status.OK)
								.build();
			
		}else if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}

		return null;
	}




	@DELETE
	@RolesAllowed({Constants.ROLE_SHOP_ADMIN, Constants.ROLE_SHOP_STAFF})
	public Response deleteShopItem(@QueryParam("ShopID")int ShopID, @QueryParam("ItemID") int itemID)
	{
		int rowCount = 0;
		int shopID = 0;

		User user = (User) Globals.accountApproved;


		if(user.getRole()== Constants.ROLE_SHOP_ADMIN_CODE)
		{
			shopID = Globals.daoUserUtility.getShopIDForShopAdmin(user.getUserID());
		}
		else if (user.getRole()== Constants.ROLE_SHOP_STAFF_CODE)
		{

			shopID = Globals.daoUserUtility.getShopIDforShopStaff(user.getUserID());
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if (!permissions.isPermitUpdateItemsInShop()) {
				// staff member do not have this permission
				throw new ForbiddenException("Not Permitted !");
			}
		}





		rowCount =	shopItemDAO.deleteShopItem(shopID, itemID);

		
		if(rowCount == 1)
		{

			return Response.status(Status.OK)
					.build();
			
		}else if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}
	
		return null;
	}





	@POST
	@Path("/DeleteBulk")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constants.ROLE_SHOP_ADMIN, Constants.ROLE_SHOP_STAFF})
	public Response deleteShopItemBulk(List<ShopItem> itemList)
	{
		int rowCountSum = 0;

		User user = (User) Globals.accountApproved;

		int shopID = 0;

		if(user.getRole()== Constants.ROLE_SHOP_ADMIN_CODE)
		{
			 shopID = Globals.daoUserUtility.getShopIDForShopAdmin(user.getUserID());

		}
		else if (user.getRole()== Constants.ROLE_SHOP_STAFF_CODE)
		{

			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());


			if(!permissions.isPermitAddRemoveItems())
			{
				// staff member do not have permission
				throw new ForbiddenException("Not Permitted !");
			}


			shopID = Globals.daoUserUtility.getShopIDforShopStaff(user.getUserID());
		}




		for(ShopItem shopItem : itemList)
		{
			shopItem.setShopID(shopID);

			rowCountSum = rowCountSum + shopItemDAO
					.deleteShopItem(shopItem.getShopID(),shopItem.getItemID());
		}



		if(rowCountSum ==  itemList.size())
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		else if( rowCountSum < itemList.size() && rowCountSum > 0)
		{

			return Response.status(Status.PARTIAL_CONTENT)
					.build();
		}
		else if(rowCountSum == 0 ) {

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}

		return null;
	}






	@GET
	@Path("/ForShop")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShopItemsForShop(
            @QueryParam("ItemCategoryID")Integer ItemCategoryID,
            @QueryParam("ShopID")Integer ShopID, @QueryParam("ItemID") Integer itemID,
            @QueryParam("SearchString") String searchString,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit") Integer limit, @QueryParam("Offset")Integer offset
	)
	{



		ShopItemEndPoint endPoint = shopItemDAO.getShopItemsForShop(
				ItemCategoryID,ShopID,itemID,
				searchString,
				sortBy,limit,offset
		);



		endPoint.setLimit(limit);
		endPoint.setMax_limit(Constants.max_limit);
		endPoint.setOffset(offset);


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}






	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShopItems(
            @QueryParam("ItemCategoryID")Integer ItemCategoryID,
			@QueryParam("GetSubcategories")boolean getSubcategories,
            @QueryParam("ShopID")Integer ShopID, @QueryParam("ItemID") Integer itemID,
            @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
            @QueryParam("deliveryRangeMax")Double deliveryRangeMax,
            @QueryParam("deliveryRangeMin")Double deliveryRangeMin,
            @QueryParam("proximity")Double proximity,
            @QueryParam("EndUserID") Integer endUserID, @QueryParam("IsFilledCart") Boolean isFilledCart,
            @QueryParam("IsOutOfStock") Boolean isOutOfStock, @QueryParam("PriceEqualsZero")Boolean priceEqualsZero,
            @QueryParam("MinPrice")Integer minPrice, @QueryParam("MaxPrice")Integer maxPrice,
            @QueryParam("SearchString") String searchString,
            @QueryParam("ShopEnabled")Boolean shopEnabled,
            @QueryParam("SortBy") String sortBy,
			@QueryParam("Limit") int limit, @QueryParam("Offset") int offset,
			@QueryParam("GetRowCount")boolean getRowCount,
			@QueryParam("MetadataOnly")boolean getOnlyMetaData
	)
	{

		ShopItemEndPoint endPoint = null;


		if(ShopID!=null && itemID == null)
		{

			endPoint = shopItemByShopDAO.getShopItems(
					ItemCategoryID,
					ShopID,
					latCenter, lonCenter,
					deliveryRangeMin,deliveryRangeMax,
					proximity, endUserID,
					isFilledCart,
					isOutOfStock,
					priceEqualsZero,
					shopEnabled,
					searchString,
					sortBy,limit,offset,
					getRowCount,getOnlyMetaData
			);


		}
		else if(itemID !=null && ShopID==null)
		{


			endPoint = shopItemByItemDAO.getShopItems(
					ItemCategoryID,
					itemID,
					latCenter, lonCenter,
					deliveryRangeMin,deliveryRangeMax,
					proximity, endUserID,
					isFilledCart,
					isOutOfStock,
					priceEqualsZero,
					sortBy,
					limit,offset,
					getRowCount,getOnlyMetaData
			);

		}
		else
		{

			endPoint = shopItemDAO.getShopItems(
					ItemCategoryID,
					ShopID, itemID,
					latCenter, lonCenter,
					deliveryRangeMin,deliveryRangeMax,
					proximity, endUserID,
					isFilledCart,
					isOutOfStock,
					priceEqualsZero,
					searchString,
					sortBy,
					limit,offset,
					getRowCount,getOnlyMetaData
			);

		}







		endPoint.setLimit(limit);
		endPoint.setMax_limit(Constants.max_limit);
		endPoint.setOffset(offset);



//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}






		List<ItemCategory> subcategories;

		if(getSubcategories)
		{
			subcategories = itemCategoryDAO.getItemCategoriesJoinRecursive(
					ShopID, ItemCategoryID, null,
					latCenter, lonCenter,
					true,
					searchString,
					ItemCategory.CATEGORY_ORDER,
					null,null
			);



			endPoint.setSubcategories(subcategories);
		}




		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}
	
}

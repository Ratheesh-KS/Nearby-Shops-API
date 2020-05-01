package org.nearbyshops.DAOs.Backups;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Constants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.Model.ModelEndpoint.ShopEndPoint;
import org.nearbyshops.Model.ModelReviewShop.ShopReview;
import org.nearbyshops.Model.ModelRoles.User;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.Model.ShopItem;

import java.sql.*;
import java.util.ArrayList;

public class ShopDAOBackup {


	private HikariDataSource dataSource = Globals.getDataSource();




	public int insertShop(Shop shop, int shopAdminID)
	{
		
		Connection connection = null;
		PreparedStatement statement = null;
		int rowIdOfInsertedRow = -1;
		int rowCount = -1;


		// add joining credit to the users account
		String updateRole =  " UPDATE " + User.TABLE_NAME
				+ " SET " + User.ROLE + " = " + Constants.ROLE_SHOP_ADMIN_CODE
				+ " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ? "
				+ " AND " + User.TABLE_NAME + "." + User.ROLE + " = " + Constants.ROLE_END_USER_CODE ;





		String insertShop = "INSERT INTO "
				+ Shop.TABLE_NAME
				+ "("  
				+ Shop.SHOP_NAME + ","
				+ Shop.DELIVERY_RANGE + ","

				+ Shop.LAT_CENTER + ","
				+ Shop.LON_CENTER + ","

				+ Shop.DELIVERY_CHARGES + ","
				+ Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
				+ Shop.PICK_FROM_SHOP_AVAILABLE + ","
				+ Shop.HOME_DELIVERY_AVAILABLE + ","

				+ Shop.SHOP_ENABLED + ","
				+ Shop.SHOP_WAITLISTED + ","

				+ Shop.LOGO_IMAGE_PATH + ","

				+ Shop.SHOP_ADDRESS + ","
				+ Shop.CITY + ","
				+ Shop.PINCODE + ","
				+ Shop.LANDMARK + ","

				+ Shop.CUSTOMER_HELPLINE_NUMBER + ","
				+ Shop.DELIVERY_HELPLINE_NUMBER + ","

				+ Shop.SHORT_DESCRIPTION + ","
				+ Shop.LONG_DESCRIPTION + ","

//				+ Shop.TIMESTAMP_CREATED + ","
				+ Shop.IS_OPEN + ","
				+ Shop.SHOP_ADMIN_ID + ""

				+ " ) VALUES (?,? ,?,? ,?,?,?,? ,?,?, ?, ?,?,?,? ,?,? ,?,? ,?,?)";
		
		try {
			
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			int i = 0;


			statement = connection.prepareStatement(updateRole);

			statement.setObject(++i,shopAdminID);
			rowCount = statement.executeUpdate();


			if(rowCount==1)
			{
				statement = connection.prepareStatement(insertShop,PreparedStatement.RETURN_GENERATED_KEYS);
				i = 0;


				statement.setObject(++i,shop.getShopName());

				statement.setObject(++i,shop.getDeliveryRange());
				statement.setObject(++i,shop.getLatCenter());
				statement.setObject(++i,shop.getLonCenter());

				statement.setObject(++i,shop.getDeliveryCharges());
				statement.setObject(++i,shop.getBillAmountForFreeDelivery());
				statement.setObject(++i,shop.getPickFromShopAvailable());
				statement.setObject(++i,shop.getHomeDeliveryAvailable());

				statement.setObject(++i,shop.getShopEnabled());
				statement.setObject(++i,shop.getShopWaitlisted());

				statement.setString(++i,shop.getLogoImagePath());

				statement.setString(++i,shop.getShopAddress());
				statement.setString(++i,shop.getCity());
				statement.setObject(++i,shop.getPincode());
				statement.setObject(++i,shop.getLandmark());

				statement.setObject(++i,shop.getCustomerHelplineNumber());
				statement.setObject(++i,shop.getDeliveryHelplineNumber());

				statement.setObject(++i,shop.getShortDescription());
				statement.setObject(++i,shop.getLongDescription());


				statement.setObject(++i,shop.isOpen());

				statement.setObject(++i,shopAdminID);


				rowIdOfInsertedRow = statement.executeUpdate();

				ResultSet rs = statement.getGeneratedKeys();

				if(rs.next())
				{
					rowIdOfInsertedRow = rs.getInt(1);
				}


			}


			connection.commit();


		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {


					rowCount = 0;
					connection.rollback();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
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

		
		return rowIdOfInsertedRow;
	}
	


	public int updateShop(Shop shop)
	{
		
		
		String updateStatement = "UPDATE " + Shop.TABLE_NAME
				+ " SET "

				+ Shop.SHOP_NAME + " = ?,"

				+ Shop.DELIVERY_RANGE + " = ?,"
				+ Shop.LAT_CENTER + " = ?,"
				+ Shop.LON_CENTER + " = ?,"

				+ Shop.DELIVERY_CHARGES + " = ?,"
				+ Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + " = ?,"
				+ Shop.PICK_FROM_SHOP_AVAILABLE + " = ?,"
				+ Shop.HOME_DELIVERY_AVAILABLE + " = ?,"

				+ Shop.SHOP_ENABLED + " = ?,"
				+ Shop.SHOP_WAITLISTED + " = ?,"

				+ Shop.LOGO_IMAGE_PATH + " = ?,"

				+ Shop.SHOP_ADDRESS + " = ?,"
				+ Shop.CITY + " = ?,"
				+ Shop.PINCODE + " = ?,"
				+ Shop.LANDMARK + " = ?,"

				+ Shop.CUSTOMER_HELPLINE_NUMBER + " = ?,"
				+ Shop.DELIVERY_HELPLINE_NUMBER + " = ?,"

				+ Shop.SHORT_DESCRIPTION + " = ?,"
				+ Shop.LONG_DESCRIPTION + " = ?,"

//				+ Shop.TIMESTAMP_CREATED + " = ?,"
				+ Shop.IS_OPEN + " = ?"

				+ " WHERE " + Shop.SHOP_ID + " = ?";
		
		
		
		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;
		
		try {
			
			connection = dataSource.getConnection();
			
			statement = connection.prepareStatement(updateStatement);


//			statement.setString(1,shop.getShopName());
//			statement.setObject(2,shop.getDeliveryCharges());

			statement.setObject(1,shop.getShopName());

			statement.setObject(2,shop.getDeliveryRange());
			statement.setObject(3,shop.getLatCenter());
			statement.setObject(4,shop.getLonCenter());

			statement.setObject(5,shop.getDeliveryCharges());
			statement.setObject(6,shop.getBillAmountForFreeDelivery());
			statement.setObject(7,shop.getPickFromShopAvailable());
			statement.setObject(8,shop.getHomeDeliveryAvailable());

			statement.setObject(9,shop.getShopEnabled());
			statement.setObject(10,shop.getShopWaitlisted());

			statement.setString(11,shop.getLogoImagePath());

			statement.setString(12,shop.getShopAddress());
			statement.setString(13,shop.getCity());
			statement.setObject(14,shop.getPincode());
			statement.setObject(15,shop.getLandmark());

			statement.setObject(16,shop.getCustomerHelplineNumber());
			statement.setObject(17,shop.getDeliveryHelplineNumber());

			statement.setObject(18,shop.getShortDescription());
			statement.setObject(19,shop.getLongDescription());

//			statement.setObject(20,shop.getTimestampCreated());
			statement.setObject(20,shop.isOpen());

			statement.setObject(21,shop.getShopID());

			updatedRows = statement.executeUpdate();
			
			
//			System.out.println("Total rows updated: " + updatedRows);
			
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




	public int updateShopByAdmin(Shop shop)
	{


		String updateStatement = "UPDATE " + Shop.TABLE_NAME
				+ " SET "
				+ Shop.SHOP_ENABLED + " = ?,"
				+ Shop.SHOP_WAITLISTED + " = ?,"
				+ Shop.IS_OPEN + " = ?,"
				+ Shop.EXTENDED_CREDIT_LIMIT + " = ?,"

				+ Shop.SHOP_NAME + " = ?,"
				+ Shop.DELIVERY_RANGE + " = ?,"
				+ Shop.LAT_CENTER + " = ?,"
				+ Shop.LON_CENTER + " = ?,"

				+ Shop.DELIVERY_CHARGES + " = ?,"
				+ Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + " = ?,"
				+ Shop.PICK_FROM_SHOP_AVAILABLE + " = ?,"
				+ Shop.HOME_DELIVERY_AVAILABLE + " = ?,"

				+ Shop.LOGO_IMAGE_PATH + " = ?,"

				+ Shop.SHOP_ADDRESS + " = ?,"
				+ Shop.CITY + " = ?,"
				+ Shop.PINCODE + " = ?,"
				+ Shop.LANDMARK + " = ?,"

				+ Shop.CUSTOMER_HELPLINE_NUMBER + " = ?,"
				+ Shop.DELIVERY_HELPLINE_NUMBER + " = ?,"

				+ Shop.SHORT_DESCRIPTION + " = ?,"
				+ Shop.LONG_DESCRIPTION + " = ?"

				+ " WHERE " + Shop.SHOP_ID + " = ?";



		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;


		try {



			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);
			int i = 0;

			statement.setObject(++i,shop.getShopEnabled());
			statement.setObject(++i,shop.getShopWaitlisted());
			statement.setObject(++i,shop.isOpen());
			statement.setObject(++i,shop.getExtendedCreditLimit());

			statement.setObject(++i,shop.getShopName());

			statement.setObject(++i,shop.getDeliveryRange());
			statement.setObject(++i,shop.getLatCenter());
			statement.setObject(++i,shop.getLonCenter());

			statement.setObject(++i,shop.getDeliveryCharges());
			statement.setObject(++i,shop.getBillAmountForFreeDelivery());
			statement.setObject(++i,shop.getPickFromShopAvailable());
			statement.setObject(++i,shop.getHomeDeliveryAvailable());

			statement.setString(++i,shop.getLogoImagePath());

			statement.setString(++i,shop.getShopAddress());
			statement.setString(++i,shop.getCity());
			statement.setObject(++i,shop.getPincode());
			statement.setObject(++i,shop.getLandmark());

			statement.setObject(++i,shop.getCustomerHelplineNumber());
			statement.setObject(++i,shop.getDeliveryHelplineNumber());

			statement.setObject(++i,shop.getShortDescription());
			statement.setObject(++i,shop.getLongDescription());

			statement.setObject(++i,shop.getShopID());

			updatedRows = statement.executeUpdate();


//			System.out.println("Total rows updated: " + updatedRows);

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



	public int updateShopBySelf(Shop shop)
	{


		String updateStatement = "UPDATE " + Shop.TABLE_NAME
				+ " SET "

				+ Shop.SHOP_NAME + " = ?,"

				+ Shop.DELIVERY_RANGE + " = ?,"
				+ Shop.LAT_CENTER + " = ?,"
				+ Shop.LON_CENTER + " = ?,"

				+ Shop.DELIVERY_CHARGES + " = ?,"
				+ Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + " = ?,"
				+ Shop.PICK_FROM_SHOP_AVAILABLE + " = ?,"
				+ Shop.HOME_DELIVERY_AVAILABLE + " = ?,"

//				+ Shop.SHOP_ENABLED + " = ?,"
//				+ Shop.SHOP_WAITLISTED + " = ?,"

				+ Shop.LOGO_IMAGE_PATH + " = ?,"

				+ Shop.SHOP_ADDRESS + " = ?,"
				+ Shop.CITY + " = ?,"
				+ Shop.PINCODE + " = ?,"
				+ Shop.LANDMARK + " = ?,"

				+ Shop.CUSTOMER_HELPLINE_NUMBER + " = ?,"
				+ Shop.DELIVERY_HELPLINE_NUMBER + " = ?,"

				+ Shop.SHORT_DESCRIPTION + " = ?,"
				+ Shop.LONG_DESCRIPTION + " = ?,"

//				+ Shop.TIMESTAMP_CREATED + " = ?,"
				+ Shop.IS_OPEN + " = ?"

				+ " WHERE " + Shop.SHOP_ADMIN_ID + " = ?";



		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;

		try {

			connection = dataSource.getConnection();

			statement = connection.prepareStatement(updateStatement);
			int i = 0;


//			statement.setString(1,shop.getShopName());
//			statement.setObject(2,shop.getDeliveryCharges());

			statement.setObject(++i,shop.getShopName());

			statement.setObject(++i,shop.getDeliveryRange());
			statement.setObject(++i,shop.getLatCenter());
			statement.setObject(++i,shop.getLonCenter());

			statement.setObject(++i,shop.getDeliveryCharges());
			statement.setObject(++i,shop.getBillAmountForFreeDelivery());
			statement.setObject(++i,shop.getPickFromShopAvailable());
			statement.setObject(++i,shop.getHomeDeliveryAvailable());

			statement.setString(++i,shop.getLogoImagePath());

			statement.setString(++i,shop.getShopAddress());
			statement.setString(++i,shop.getCity());
			statement.setObject(++i,shop.getPincode());
			statement.setObject(++i,shop.getLandmark());

			statement.setObject(++i,shop.getCustomerHelplineNumber());
			statement.setObject(++i,shop.getDeliveryHelplineNumber());

			statement.setObject(++i,shop.getShortDescription());
			statement.setObject(++i,shop.getLongDescription());

			statement.setObject(++i,shop.isOpen());

			statement.setObject(++i,shop.getShopAdminID());

			updatedRows = statement.executeUpdate();


//			System.out.println("Total rows updated: " + updatedRows);

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




	public Shop getShopDetails(int ShopID,
							   Double latCenter, Double lonCenter)
	{

		String query = " ";

//		boolean distancePreset = false;

//		if(latCenter!=null & lonCenter!=null)
//		{
		query = "SELECT "
				+ " (6371.01 * acos(cos( radians(" + latCenter + ")) * cos( radians(" + Shop.LAT_CENTER + " )) * cos(radians( "
				+ Shop.LON_CENTER + ") - radians("
				+ lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians(" + Shop.LAT_CENTER + "))))" + " as distance ,"


				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
				+ Shop.TABLE_NAME + "." + Shop.SHOP_NAME + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_CHARGES + ","

				+ Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + ","
				+ Shop.TABLE_NAME + "." + Shop.CITY + ","
				+ Shop.TABLE_NAME + "." + Shop.PINCODE + ","
				+ Shop.TABLE_NAME + "." + Shop.LANDMARK + ","
				+ Shop.TABLE_NAME + "." + Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","

				+ Shop.TABLE_NAME + "." + Shop.CUSTOMER_HELPLINE_NUMBER + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_HELPLINE_NUMBER + ","
				+ Shop.TABLE_NAME + "." + Shop.SHORT_DESCRIPTION + ","
				+ Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + ","
				+ Shop.TABLE_NAME + "." + Shop.IS_OPEN + ","
				+ Shop.TABLE_NAME + "." + Shop.LOGO_IMAGE_PATH + ","
				+ Shop.TABLE_NAME + "." + Shop.TIMESTAMP_CREATED + ","
				+ Shop.TABLE_NAME + "." + Shop.PICK_FROM_SHOP_AVAILABLE + ","
				+ Shop.TABLE_NAME + "." + Shop.HOME_DELIVERY_AVAILABLE + ","

				+  "avg(" + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ") as avg_rating" + ","
				+  "count( DISTINCT " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ") as rating_count" + ""

				+ " FROM " + ShopReview.TABLE_NAME
				+ " RIGHT OUTER JOIN " + Shop.TABLE_NAME + " ON (" + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + " = " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ")"
				+ " WHERE "	+ Shop.TABLE_NAME + "." + Shop.SHOP_ID + "= " + ShopID;




		query = query

				+ " group by "
				+ "distance,"
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID ;

		/*
		+ ","
				+ Shop.TABLE_NAME + "." + Shop.SHOP_NAME + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_CHARGES + ","
				+ Shop.TABLE_NAME + "." + Shop.DISTRIBUTOR_ID + ","
				+ Shop.TABLE_NAME + "." + Shop.IMAGE_PATH + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_MAX + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_MIN + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_MAX + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_MIN + ","

				+ Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + ","
				+ Shop.TABLE_NAME + "." + Shop.CITY + ","
				+ Shop.TABLE_NAME + "." + Shop.PINCODE + ","
				+ Shop.TABLE_NAME + "." + Shop.LANDMARK + ","
				+ Shop.TABLE_NAME + "." + Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
				+ Shop.TABLE_NAME + "." + Shop.CUSTOMER_HELPLINE_NUMBER + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_HELPLINE_NUMBER + ","
				+ Shop.TABLE_NAME + "." + Shop.SHORT_DESCRIPTION + ","
				+ Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + ","
				+ Shop.TABLE_NAME + "." + Shop.IS_OPEN + ","
				+ Shop.TABLE_NAME + "." + Shop.TIMESTAMP_CREATED + ""*/





//			distancePreset = true;

//		}

		/*else
		{
			query = "SELECT * FROM " + Shop.TABLE_NAME
					+ " WHERE "	+  Shop.ITEM_ID + "= " + ShopID;

		}*/


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		Shop shop = null;
		try {

			connection = dataSource.getConnection();

			statement = connection.createStatement();

			rs = statement.executeQuery(query);


			while(rs.next())
			{

				shop = new Shop();

				shop.setRt_distance(rs.getDouble("distance"));
				shop.setRt_rating_avg(rs.getFloat("avg_rating"));
				shop.setRt_rating_count(rs.getFloat("rating_count"));

				shop.setShopID(rs.getInt(Shop.SHOP_ID));
				shop.setShopName(rs.getString(Shop.SHOP_NAME));
				shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
				shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));
				shop.setDeliveryCharges(rs.getFloat(Shop.DELIVERY_CHARGES));

				shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));

				shop.setLogoImagePath(rs.getString(Shop.LOGO_IMAGE_PATH));
				shop.setShopAddress(rs.getString(Shop.SHOP_ADDRESS));
				shop.setCity(rs.getString(Shop.CITY));
				shop.setPincode(rs.getLong(Shop.PINCODE));
				shop.setLandmark(rs.getString(Shop.LANDMARK));
				shop.setBillAmountForFreeDelivery(rs.getInt(Shop.BILL_AMOUNT_FOR_FREE_DELIVERY));
				shop.setCustomerHelplineNumber(rs.getString(Shop.CUSTOMER_HELPLINE_NUMBER));
				shop.setDeliveryHelplineNumber(rs.getString(Shop.DELIVERY_HELPLINE_NUMBER));
				shop.setShortDescription(rs.getString(Shop.SHORT_DESCRIPTION));
				shop.setLongDescription(rs.getString(Shop.LONG_DESCRIPTION));
				shop.setTimestampCreated(rs.getTimestamp(Shop.TIMESTAMP_CREATED));
				shop.setOpen(rs.getBoolean(Shop.IS_OPEN));
				shop.setPickFromShopAvailable(rs.getBoolean(Shop.PICK_FROM_SHOP_AVAILABLE));
				shop.setHomeDeliveryAvailable(rs.getBoolean(Shop.HOME_DELIVERY_AVAILABLE));

			}


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

		return shop;
	}





	public int setShopOpen(boolean isOpen,int shopAdminID)
	{


		String updateStatement = "UPDATE " + Shop.TABLE_NAME
				+ " SET "
				+ Shop.IS_OPEN + " = ?"

				+ " WHERE " + Shop.SHOP_ADMIN_ID + " = ?";



		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;

		try {

			connection = dataSource.getConnection();

			statement = connection.prepareStatement(updateStatement);

			statement.setObject(1,isOpen);
			statement.setObject(2,shopAdminID);

			updatedRows = statement.executeUpdate();


//			System.out.println("Total rows updated: " + updatedRows);

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





	public int deleteShop(int shopID)
	{
		
		String deleteStatement = "DELETE FROM " + Shop.TABLE_NAME
				+ " WHERE " + Shop.SHOP_ID + "= ?";
		
		
		Connection connection= null;
		PreparedStatement statement = null;
		int rowCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);
			statement.setObject(1,shopID);


			rowCountDeleted = statement.executeUpdate();
//			System.out.println(" Deleted Count: " + rowCountDeleted);
			
			connection.close();
			
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
	
		
		return rowCountDeleted;
	}





	public ShopEndPoint getShopsListQuerySimple(
			Boolean underReview,
			Boolean enabled, Boolean waitlisted,
			Boolean filterByVisibility,
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin,Double deliveryRangeMax,
			Double proximity,
			String searchString,
			String sortBy,
			Integer limit, Integer offset
	)
	{

		String query = "";
		String queryJoin = "";

		// flag for tracking whether to put "AND" or "WHERE"
//		boolean isFirst = true;


		String queryNormal = "SELECT "

				+ "6371 * acos( cos( radians("
				+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
				+ lonCenter + "))"
				+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) as distance" + ","

				+ Shop.TABLE_NAME + "." + Shop.SHOP_ADMIN_ID + ","
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
				+ Shop.SHOP_NAME + ","

				+  "avg(" + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ") as avg_rating" + ","
				+  "count( DISTINCT " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ") as rating_count" + ","

				+ Shop.DELIVERY_RANGE + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_CENTER + ","

				+ "count(*) over() AS full_count " + ","

				+ Shop.DELIVERY_CHARGES + ","
				+ Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
				+ Shop.PICK_FROM_SHOP_AVAILABLE + ","
				+ Shop.HOME_DELIVERY_AVAILABLE + ","

				+ Shop.SHOP_ENABLED + ","
				+ Shop.SHOP_WAITLISTED + ","

				+ Shop.LOGO_IMAGE_PATH + ","

				+ Shop.SHOP_ADDRESS + ","
				+ Shop.CITY + ","
				+ Shop.PINCODE + ","
				+ Shop.LANDMARK + ","

				+ Shop.CUSTOMER_HELPLINE_NUMBER + ","
				+ Shop.DELIVERY_HELPLINE_NUMBER + ","

				+ Shop.SHORT_DESCRIPTION + ","
				+ Shop.LONG_DESCRIPTION + ","

//				+ Shop.TIMESTAMP_CREATED + " as shop_create_time ,"
				+ Shop.TABLE_NAME + "." + Shop.TIMESTAMP_CREATED + " as shop_create_time ,"
				+ Shop.IS_OPEN + ","

				+ Shop.TABLE_NAME + "." + Shop.ACCOUNT_BALANCE + " as shop_account_balance,"
				+ Shop.TABLE_NAME + "." + Shop.EXTENDED_CREDIT_LIMIT + " as extended_credit_limit,"



//				+ User.TABLE_NAME + "." + User.USER_ID + ","
//				+ User.TABLE_NAME + "." + User.USERNAME + ","
//				+ User.TABLE_NAME + "." + User.E_MAIL + ","
				+ User.TABLE_NAME + "." + User.PHONE + ","

				+ User.TABLE_NAME + "." + User.NAME + ""
//				+ User.TABLE_NAME + "." + User.GENDER + ","

//				+ User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
//				+ User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ","
//				+ User.TABLE_NAME + "." + User.ABOUT + ","

//				+ User.TABLE_NAME + "." + User.TIMESTAMP_CREATED + " as profile_create_time "



//				+  "avg(" + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ") as avg_rating" + ","
//				+  "count( DISTINCT " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ") as rating_count" + ""

				+ " FROM " + Shop.TABLE_NAME
				+ " INNER JOIN " + User.TABLE_NAME + " ON ( " + Shop.TABLE_NAME + "." + Shop.SHOP_ADMIN_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
				+ " LEFT OUTER JOIN " + ShopReview.TABLE_NAME  + " ON (" + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + " = " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ")"
				+ " WHERE TRUE ";



		// Visibility Filter : Apply
		if(filterByVisibility!=null && filterByVisibility && latCenter != null && lonCenter != null)
		{

			String queryPartlatLonCenter = "";

//			queryNormal = queryNormal + " WHERE ";


			// reset the flag
//			isFirst = false;


			queryNormal = queryNormal + " AND ";

			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";



			queryNormal = queryNormal + queryPartlatLonCenter;

		}



		// Delivery Range Filter : apply
		if(deliveryRangeMin != null || deliveryRangeMax != null){

			// apply delivery range filter
			String queryPartDeliveryRange = "";

			queryPartDeliveryRange = queryPartDeliveryRange
					+ Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;


//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE " + queryPartDeliveryRange;
//
//				// reset the flag
//				isFirst = false;
//
//			}else
//			{
//				queryNormal = queryNormal + " AND " + queryPartDeliveryRange;
//			}


			queryNormal = queryNormal + " AND " + queryPartDeliveryRange;

		}


		// Proximity Filter
		if(proximity != null)
		{
			// proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

			String queryPartProximity = "";
//			String queryPartProximityTwo = "";


			// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians(" + latCenter + ")) * cos( radians(" + Shop.LAT_CENTER + " )) * cos(radians( "
					+ Shop.LON_CENTER + ") - radians(" + lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians("
					+ Shop.LAT_CENTER + ")))) <= " + proximity ;


			queryNormal = queryNormal + " AND " + queryPartProximity;


//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE " + queryPartProximity;
//
//				// reset the flag
//				isFirst = false;
//
//			}else
//			{
//				queryNormal = queryNormal + " AND " + queryPartProximity;
//			}

		}



		if(searchString !=null)
		{
			String queryPartSearch = Shop.TABLE_NAME + "." + Shop.SHOP_NAME +" ilike '%" + searchString + "%'"
					+ " or " + Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + " ilike '%" + searchString + "%'"
					+ " or " + Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + " ilike '%" + searchString + "%'"
					+ " or CAST ( " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + " AS text )" + " ilike '%" + searchString + "%'" + "";


			queryNormal = queryNormal + " AND " + queryPartSearch;


//			if(isFirst)
//			{
////				queryJoin = queryJoin + " WHERE " + queryPartSearch;
//
//				queryNormal = queryNormal + " WHERE " + queryPartSearch;
//
//				isFirst = false;
//			}
//			else
//			{
//				queryNormal = queryNormal + " AND " + queryPartSearch;
//			}


		}





		if(underReview !=null)
		{
			queryNormal = queryNormal + " AND " + Shop.SHOP_ENABLED + " IS NULL ";
		}



		if(enabled !=null)
		{
			queryNormal = queryNormal + " AND " + Shop.SHOP_ENABLED + " = "  + enabled;
		}


		if(waitlisted !=null)
		{
			queryNormal = queryNormal + " AND " + Shop.SHOP_WAITLISTED + " = "  + waitlisted;

//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE " + Shop.SHOP_WAITLISTED + " = "  + waitlisted;
//
//				isFirst = false;
//			}
//			else
//			{
//				queryNormal = queryNormal + " AND " + Shop.SHOP_WAITLISTED + " = "  + waitlisted;
//			}
		}




		/*


		*/

		String queryGroupBy = "";

		queryGroupBy = queryGroupBy + " group by "
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
				+ User.TABLE_NAME + "." + User.USER_ID ;


		queryNormal = queryNormal + queryGroupBy;




		// Applying Filters



		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryNormal = queryNormal + queryPartSortBy;
			}
		}



		if(limit !=null)
		{

			String queryPartLimitOffset = "";

			if(offset!=null)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


			queryNormal = queryNormal + queryPartLimitOffset;
		}



		query = queryNormal;





		ShopEndPoint endPoint = new ShopEndPoint();
		endPoint.setItemCount(0);

		ArrayList<Shop> shopList = new ArrayList<Shop>();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{

				User shopAdmin = new User();

//				shopAdmin.setUserID(rs.getInt(User.USER_ID));
//				shopAdmin.setUsername(rs.getString(User.USERNAME));
//				shopAdmin.setEmail(rs.getString(User.E_MAIL));
				shopAdmin.setPhone(rs.getString(User.PHONE));
				shopAdmin.setName(rs.getString(User.NAME));
//				shopAdmin.setGender(rs.getBoolean(User.GENDER));


//				shopAdmin.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
//				shopAdmin.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
//				shopAdmin.setAbout(rs.getString(User.ABOUT));

//				shopAdmin.setTimestampCreated(rs.getTimestamp("profile_create_time"));



				Shop shop = new Shop();

				shop.setRt_distance(rs.getDouble("distance"));
				shop.setRt_rating_avg(rs.getFloat("avg_rating"));
				shop.setRt_rating_count(rs.getFloat("rating_count"));

				shop.setShopAdminID(rs.getInt(Shop.SHOP_ADMIN_ID));
				shop.setShopID(rs.getInt(Shop.SHOP_ID));

				shop.setShopName(rs.getString(Shop.SHOP_NAME));
				shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));
				shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
				shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));

				shop.setDeliveryCharges(rs.getFloat(Shop.DELIVERY_CHARGES));
				shop.setBillAmountForFreeDelivery(rs.getInt(Shop.BILL_AMOUNT_FOR_FREE_DELIVERY));
				shop.setPickFromShopAvailable(rs.getBoolean(Shop.PICK_FROM_SHOP_AVAILABLE));
				shop.setHomeDeliveryAvailable(rs.getBoolean(Shop.HOME_DELIVERY_AVAILABLE));

				shop.setShopEnabled(rs.getBoolean(Shop.SHOP_ENABLED));
				shop.setShopWaitlisted(rs.getBoolean(Shop.SHOP_WAITLISTED));

				shop.setLogoImagePath(rs.getString(Shop.LOGO_IMAGE_PATH));

				shop.setShopAddress(rs.getString(Shop.SHOP_ADDRESS));
				shop.setCity(rs.getString(Shop.CITY));
				shop.setPincode(rs.getLong(Shop.PINCODE));
				shop.setLandmark(rs.getString(Shop.LANDMARK));

				shop.setCustomerHelplineNumber(rs.getString(Shop.CUSTOMER_HELPLINE_NUMBER));
				shop.setDeliveryHelplineNumber(rs.getString(Shop.DELIVERY_HELPLINE_NUMBER));

				shop.setShortDescription(rs.getString(Shop.SHORT_DESCRIPTION));
				shop.setLongDescription(rs.getString(Shop.LONG_DESCRIPTION));

//				shop.setTimestampCreated(rs.getTimestamp(Shop.TIMESTAMP_CREATED));
//				shop.setTimestampCreated(rs.getTimestamp("shop_create_time"));

				shop.setOpen(rs.getBoolean(Shop.IS_OPEN));

				shop.setTimestampCreated(rs.getTimestamp("shop_create_time"));
				shop.setExtendedCreditLimit(rs.getDouble("extended_credit_limit"));
				shop.setAccountBalance(rs.getDouble("shop_account_balance"));


				endPoint.setItemCount(rs.getInt("full_count"));

				shop.setShopAdminProfile(shopAdmin);





				shopList.add(shop);

			}

//			System.out.println("Total Shops queried " + shopList.size());


			endPoint.setResults(shopList);


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





	public ArrayList<Shop> getShopListQueryJoin(
			Integer itemCategoryID,
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin,Double deliveryRangeMax,
			Double proximity,
			String searchString,
			String sortBy,
			Integer limit, Integer offset
	)
	{

		String query = "";
		String queryJoin = "";

		// flag for tracking whether to put "AND" or "WHERE"
		boolean isFirst = true;


		queryJoin = "SELECT "
				+ "6371 * acos(cos( radians("
				+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
				+ lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) as distance" + ","

				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
				+ Shop.SHOP_NAME + ","

				+ Shop.DELIVERY_RANGE + ","
				+ Shop.LAT_CENTER + ","
				+ Shop.LON_CENTER + ","

				+ Shop.DELIVERY_CHARGES + ","
				+ Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
				+ Shop.PICK_FROM_SHOP_AVAILABLE + ","
				+ Shop.HOME_DELIVERY_AVAILABLE + ","

				+ Shop.SHOP_ENABLED + ","
				+ Shop.SHOP_WAITLISTED + ","

				+ Shop.LOGO_IMAGE_PATH + ","

				+ Shop.SHOP_ADDRESS + ","
				+ Shop.CITY + ","
				+ Shop.PINCODE + ","
				+ Shop.LANDMARK + ","

				+ Shop.CUSTOMER_HELPLINE_NUMBER + ","
				+ Shop.DELIVERY_HELPLINE_NUMBER + ","

				+ Shop.SHORT_DESCRIPTION + ","
				+ Shop.LONG_DESCRIPTION + ","

				+ Shop.TIMESTAMP_CREATED + ","
				+ Shop.IS_OPEN + ","

				+  "avg(" + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ") as avg_rating" + ","
				+  "count( DISTINCT " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ") as rating_count" + ""

				+ " FROM " + Shop.TABLE_NAME
				+ " LEFT OUTER JOIN " + ShopReview.TABLE_NAME + " ON (" + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + " = " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ")"
				+ "," + ShopItem.TABLE_NAME + "," + Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME

				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID
				+ " AND " + Shop.TABLE_NAME + "." + Shop.IS_OPEN + " = TRUE "
				+ " AND " + Shop.TABLE_NAME + "." + Shop.SHOP_ENABLED + " = TRUE "
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + " > 0 "
				+ " AND " + Shop.TABLE_NAME + "." + Shop.ACCOUNT_BALANCE + ">=" + Constants.min_account_balance_for_shop;



		// Visibility Filter : Apply
		if(latCenter != null && lonCenter != null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.


			String queryPartlatLonCenter = "";

			// reset the flag
			isFirst = false;


			//+ " BETWEEN " + latmax + " AND " + latmin;

			// Visibility filter using bounding coordinates
			/*queryPartlatLonCenterTwo = queryPartlatLonCenterTwo + Shop.TABLE_NAME
					+ "."
					+ Shop.LAT_MAX
					+ " >= " + latCenter
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LAT_MIN
					+ " <= " + latCenter
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LON_MAX
					+ " >= " + lonCenter
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LON_MIN
					+ " <= " + lonCenter;*/


			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";

			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;

		}



		// Delivery Range Filter : apply
		if(deliveryRangeMin != null || deliveryRangeMax != null){

			// apply delivery range filter
			String queryPartDeliveryRange = "";


			queryPartDeliveryRange = queryPartDeliveryRange + Shop.TABLE_NAME
					+ "."
					+ Shop.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
					//+ " <= " + deliveryRange;

			queryJoin = queryJoin + " AND " + queryPartDeliveryRange;

		}


		// Proximity Filter
		if(proximity != null)
		{
			// proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

			String queryPartProximity = "";
//			String queryPartProximityTwo = "";

			// generate bounding coordinates for the shop based on the required location and its
			/*center = GeoLocation.fromDegrees(latCenter,lonCenter);
			minMaxArray = center.boundingCoordinates(proximity,6371.01);

			pointOne = minMaxArray[0];
			pointTwo = minMaxArray[1];

			double latMin = pointOne.getLatitudeInDegrees();
			double lonMin = pointOne.getLongitudeInDegrees();
			double latMax = pointTwo.getLatitudeInDegrees();
			double lonMax = pointTwo.getLongitudeInDegrees();*/


			// Make sure that shop center lies between the bounding coordinates generated by proximity bounding box


				// Filtering by proximity using bounding coordinates
			/*queryPartProximityTwo = queryPartProximityTwo+ Shop.TABLE_NAME
					+ "."
					+ Shop.LAT_CENTER
					+ " < " + latMax

					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LAT_CENTER
					+ " > " + latMin

					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LON_CENTER
					+ " < " + lonMax

					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LON_CENTER
					+ " > " + lonMin;*/


				// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians("
					+ latCenter
					+ ")) * cos( radians("
					+ Shop.LAT_CENTER
					+ " )) * cos(radians( "
					+ Shop.LON_CENTER
					+ ") - radians("
					+ lonCenter
					+ "))"
					+ " + sin( radians("
					+ latCenter
					+ ")) * sin(radians("
					+ Shop.LAT_CENTER
					+ ")))) <= "
					+ proximity ;



			queryJoin = queryJoin + " AND " + queryPartProximity;
		}


		if(searchString !=null)
		{
			String queryPartSearch = Shop.TABLE_NAME + "." + Shop.SHOP_NAME +" ilike '%" + searchString + "%'"
					+ " or " + Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + " ilike '%" + searchString + "%'"
					+ " or " + Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + " ilike '%" + searchString + "%'";


			queryJoin = queryJoin + " AND " + queryPartSearch;
		}




		if(itemCategoryID != null)
		{
			// filter shops by Item Category ID
			queryJoin = queryJoin + " AND "
					+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID
					+ " = "
					+ itemCategoryID;
		}


		/*


		*/

		String queryGroupBy = "";

		queryGroupBy = queryGroupBy + " group by "
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID ;

		queryJoin = queryJoin + queryGroupBy;

		// Applying Filters



		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryJoin = queryJoin + queryPartSortBy;
			}
		}




		queryJoin = queryJoin + " LIMIT " + limit + " " + " OFFSET " + offset;



//		if(limit !=null)
//		{
//
//			String queryPartLimitOffset = "";
//
//			if(offset!=null)
//			{
//				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;
//
//			}else
//			{
//				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
//			}
//
//			queryJoin = queryJoin + queryPartLimitOffset;
//		}



		// use Join query only if filtering requires a join

//		if(itemCategoryID !=null)
//		{
//			query = queryJoin;
//
//		}else
//		{
//			query = queryNormal;
//		}


		query = queryJoin;




		
		ArrayList<Shop> shopList = new ArrayList<Shop>();
		
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				
				Shop shop = new Shop();

				shop.setRt_distance(rs.getDouble("distance"));
				shop.setRt_rating_avg(rs.getFloat("avg_rating"));
				shop.setRt_rating_count(rs.getFloat("rating_count"));

				shop.setShopID(rs.getInt(Shop.SHOP_ID));

				shop.setShopName(rs.getString(Shop.SHOP_NAME));
				shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));
				shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
				shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));

				shop.setDeliveryCharges(rs.getFloat(Shop.DELIVERY_CHARGES));
				shop.setBillAmountForFreeDelivery(rs.getInt(Shop.BILL_AMOUNT_FOR_FREE_DELIVERY));
				shop.setPickFromShopAvailable(rs.getBoolean(Shop.PICK_FROM_SHOP_AVAILABLE));
				shop.setHomeDeliveryAvailable(rs.getBoolean(Shop.HOME_DELIVERY_AVAILABLE));

				shop.setShopEnabled(rs.getBoolean(Shop.SHOP_ENABLED));
				shop.setShopWaitlisted(rs.getBoolean(Shop.SHOP_WAITLISTED));

				shop.setLogoImagePath(rs.getString(Shop.LOGO_IMAGE_PATH));

				shop.setShopAddress(rs.getString(Shop.SHOP_ADDRESS));
				shop.setCity(rs.getString(Shop.CITY));
				shop.setPincode(rs.getLong(Shop.PINCODE));
				shop.setLandmark(rs.getString(Shop.LANDMARK));

				shop.setCustomerHelplineNumber(rs.getString(Shop.CUSTOMER_HELPLINE_NUMBER));
				shop.setDeliveryHelplineNumber(rs.getString(Shop.DELIVERY_HELPLINE_NUMBER));

				shop.setShortDescription(rs.getString(Shop.SHORT_DESCRIPTION));
				shop.setLongDescription(rs.getString(Shop.LONG_DESCRIPTION));

				shop.setTimestampCreated(rs.getTimestamp(Shop.TIMESTAMP_CREATED));
				shop.setOpen(rs.getBoolean(Shop.IS_OPEN));

				shopList.add(shop);
				
			}
			
//			System.out.println("Total Shops queried " + shopList.size());


			
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

		return shopList;
	}





	public ShopEndPoint getEndPointMetadata(
			Integer itemCategoryID,
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin,Double deliveryRangeMax,
			Double proximity,String searchString
	)

	{


		String query = "";
		String queryJoin = "";

		// flag for tracking whether to put "AND" or "WHERE"
		boolean isFirst = true;


		String queryNormal = "SELECT DISTINCT "
						+ Shop.TABLE_NAME + "." + Shop.SHOP_ID
						+ " FROM " + Shop.TABLE_NAME;

		//+ " count(*) as item_count "

		queryJoin = "SELECT DISTINCT "
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID
				+ " FROM "
				+ Shop.TABLE_NAME  + "," + ShopItem.TABLE_NAME + ","
				+ Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME

				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID
				+ " AND " + Shop.TABLE_NAME + "." + Shop.IS_OPEN + " = TRUE "
				+ " AND " + Shop.TABLE_NAME + "." + Shop.SHOP_ENABLED + " = TRUE "
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + " > 0 ";



		/*if(distributorID != null)
		{
			queryNormal = queryNormal + " WHERE "
					+ Shop.DISTRIBUTOR_ID + " = " + distributorID;

			// reset the flag
			isFirst = false;

			queryJoin = queryJoin + " AND "
					+ Shop.TABLE_NAME + "." + Shop.DISTRIBUTOR_ID
					+ " = " + distributorID;
		}*/


		// Visibility Filter : Apply
		if(latCenter != null && lonCenter != null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.


			String queryPartlatLonCenter = "";

			if(isFirst)
			{
				queryNormal = queryNormal + " WHERE ";

				// reset the flag
				isFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND ";
			}


			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";



			queryNormal = queryNormal + queryPartlatLonCenter;

			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;

		}



		// Delivery Range Filter : apply
		if(deliveryRangeMin != null || deliveryRangeMax != null){

			// apply delivery range filter
			String queryPartDeliveryRange = "";

			if(isFirst)
			{
				queryNormal = queryNormal + " WHERE ";

				// reset the flag
				isFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND ";
			}




			queryPartDeliveryRange = queryPartDeliveryRange + Shop.TABLE_NAME
					+ "."
					+ Shop.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
			//+ " <= " + deliveryRange;

			queryNormal = queryNormal + queryPartDeliveryRange;

			queryJoin = queryJoin + " AND " + queryPartDeliveryRange;

		}


		// Proximity Filter
		if(proximity != null)
		{
			// proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

			String queryPartProximity = "";


			// Make sure that shop center lies between the bounding coordinates generated by proximity bounding box


			// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians("
					+ latCenter
					+ ")) * cos( radians("
					+ Shop.LAT_CENTER
					+ " )) * cos(radians( "
					+ Shop.LON_CENTER
					+ ") - radians("
					+ lonCenter
					+ "))"
					+ " + sin( radians("
					+ latCenter
					+ ")) * sin(radians("
					+ Shop.LAT_CENTER
					+ ")))) <= "
					+ proximity ;


			if(isFirst)
			{
				queryNormal = queryNormal + " WHERE ";

				// reset the flag
				isFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND ";
			}



			queryNormal = queryNormal + queryPartProximity;

			queryJoin = queryJoin + " AND " + queryPartProximity;
		}

		if(searchString !=null)
		{
			String queryPartSearch = Shop.TABLE_NAME + "." + Shop.SHOP_NAME +" ilike '%" + searchString + "%'"
					+ " or " + Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + " ilike '%" + searchString + "%'"
					+ " or " + Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + " ilike '%" + searchString + "%'";



			if(isFirst)
			{
//				queryJoin = queryJoin + " WHERE " + queryPartSearch;

				queryNormal = queryNormal + " WHERE " + queryPartSearch;

				isFirst = false;
			}
			else
			{
				queryNormal = queryNormal + " AND " + queryPartSearch;
			}

			queryJoin = queryJoin + " AND " + queryPartSearch;
		}




		if(itemCategoryID != null)
		{
			// filter shops by Item Category ID
			queryJoin = queryJoin + " AND "
					+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID
					+ " = "
					+ itemCategoryID;
		}



		// use Join query only if filtering requires a join
//		if(itemCategoryID !=null)
//		{
//			query = queryJoin;
//
//		}else
//		{
//			query = queryNormal;
//		}

		query = queryJoin;



		query = "SELECT COUNT(*) as item_count FROM (" + query + ") AS temp";


		ShopEndPoint endPoint = new ShopEndPoint();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{

				endPoint.setItemCount(rs.getInt("item_count"));


			}

//			System.out.println("Total Shops Item Count :  " + endPoint.getItemCount());



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





	public ArrayList<Shop> filterShopsByItemCategory(Integer itemCategoryID,
                                                     Integer distributorID,
                                                     Double latCenter, Double lonCenter,
                                                     Double deliveryRangeMin, Double deliveryRangeMax,
                                                     Double proximity,
                                                     String sortBy,
                                                     Integer limit, Integer offset)
	{




			// a recursive CTE (Common table Expression) query. This query is used for retrieving hierarchical / tree set data.

			String withRecursiveStart = "WITH RECURSIVE category_tree("
					+ ItemCategory.ITEM_CATEGORY_ID + ","
					+ ItemCategory.PARENT_CATEGORY_ID
					+ ") AS (";


			String queryJoin = "SELECT DISTINCT "

					+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + ","
					+ ItemCategory.TABLE_NAME + "." + ItemCategory.PARENT_CATEGORY_ID

					+ " FROM "
					+ ItemCategory.TABLE_NAME

					+ " WHERE "
					+ ItemCategory.ITEM_CATEGORY_ID  + " = " + itemCategoryID;


			String union = " UNION ";

			String querySelect = " SELECT "

					+ "cat." + ItemCategory.ITEM_CATEGORY_ID + ","
					+ "cat." + ItemCategory.PARENT_CATEGORY_ID

					+ " FROM category_tree tempCat," + 	ItemCategory.TABLE_NAME + " cat"
					+ " WHERE cat." + ItemCategory.PARENT_CATEGORY_ID
					+ " = tempcat." + ItemCategory.ITEM_CATEGORY_ID
					+ " )";


			String queryLast = " SELECT "
					+ ItemCategory.ITEM_CATEGORY_ID
					+ " FROM category_tree";



			String queryRecursive = withRecursiveStart + queryJoin + union + querySelect +  queryLast;





//		System.out.println(query);

		queryJoin = "SELECT DISTINCT "

				+ "6371 * acos(cos( radians("
				+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
				+ lonCenter + "))"
				+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) as distance" + ","

				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
				+ Shop.TABLE_NAME + "." + Shop.SHOP_NAME + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_CHARGES + ","

				+ Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + ","
				+ Shop.TABLE_NAME + "." + Shop.CITY + ","
				+ Shop.TABLE_NAME + "." + Shop.PINCODE + ","
				+ Shop.TABLE_NAME + "." + Shop.LANDMARK + ","
				+ Shop.TABLE_NAME + "." + Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
				+ Shop.TABLE_NAME + "." + Shop.CUSTOMER_HELPLINE_NUMBER + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_HELPLINE_NUMBER + ","
				+ Shop.TABLE_NAME + "." + Shop.SHORT_DESCRIPTION + ","
				+ Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + ","
				+ Shop.TABLE_NAME + "." + Shop.IS_OPEN + ","
				+ Shop.TABLE_NAME + "." + Shop.LOGO_IMAGE_PATH + ","
				+ Shop.TABLE_NAME + "." + Shop.TIMESTAMP_CREATED + ","

				+  "avg(" + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ") as avg_rating" + ","
				+  "count( DISTINCT " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ") as rating_count" + ""

				+ " FROM " + Shop.TABLE_NAME
				+ " LEFT OUTER JOIN " + ShopReview.TABLE_NAME + " ON (" + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + " = " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ")"
				+ " INNER JOIN " + ShopItem.TABLE_NAME + " ON (" + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + ")"
				+ " INNER JOIN " + Item.TABLE_NAME + " ON (" + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID + ")"
				+ " INNER JOIN " + ItemCategory.TABLE_NAME + " ON (" + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + ")"
				+ " AND " + Shop.TABLE_NAME + "." + Shop.IS_OPEN + " = TRUE "
				+ " AND " + Shop.TABLE_NAME + "." + Shop.SHOP_ENABLED + " = TRUE "
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + " > 0 ";


		queryJoin = queryJoin
				+ " AND " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + " IN " + " (" + queryRecursive + ")";



/*

				+ "," + ShopItemContract.TABLE_NAME + ","
				+ Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME

				+ " WHERE "
				+ Shop.TABLE_NAME + "." + Shop.ITEM_ID + "="
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.ITEM_ID

				+ " AND "
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.ITEM_ID + "="
				+ Item.TABLE_NAME + "." + Item.ITEM_ID

				+ " AND "
				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "="
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;

*/





		/*if(distributorID != null)
		{
			queryJoin = queryJoin + " AND "
					+ Shop.TABLE_NAME + "." + Shop.DISTRIBUTOR_ID
					+ " = " + distributorID;
		}*/


		// Visibility Filter : Apply
		if(latCenter != null && lonCenter != null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.


			String queryPartlatLonCenter = "";

			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";

			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;
		}



		// Delivery Range Filter : apply
		if(deliveryRangeMin != null && deliveryRangeMax != null){

			// apply delivery range filter
			String queryPartDeliveryRange = "";

			queryPartDeliveryRange = queryPartDeliveryRange
					+ Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;

			queryJoin = queryJoin + " AND " + queryPartDeliveryRange;

		}


		// Proximity Filter
		if(proximity != null)
		{
			// proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

			String queryPartProximity = "";


			// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians("
					+ latCenter
					+ ")) * cos( radians("
					+ Shop.LAT_CENTER
					+ " )) * cos(radians( "
					+ Shop.LON_CENTER
					+ ") - radians("
					+ lonCenter
					+ "))"
					+ " + sin( radians("
					+ latCenter
					+ ")) * sin(radians("
					+ Shop.LAT_CENTER
					+ ")))) <= "
					+ proximity ;

			queryJoin = queryJoin + " AND " + queryPartProximity;

		}




		queryJoin = queryJoin + " group by " + "distance," + Shop.TABLE_NAME + "." + Shop.SHOP_ID ;


		/*+ ","
				+ Shop.TABLE_NAME + "." + Shop.SHOP_NAME + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_CENTER + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_CHARGES + ","
				+ Shop.TABLE_NAME + "." + Shop.DISTRIBUTOR_ID + ","
				+ Shop.TABLE_NAME + "." + Shop.IMAGE_PATH + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_MAX + ","
				+ Shop.TABLE_NAME + "." + Shop.LAT_MIN + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_MAX + ","
				+ Shop.TABLE_NAME + "." + Shop.LON_MIN + ","

				+ Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + ","
				+ Shop.TABLE_NAME + "." + Shop.CITY + ","
				+ Shop.TABLE_NAME + "." + Shop.PINCODE + ","
				+ Shop.TABLE_NAME + "." + Shop.LANDMARK + ","
				+ Shop.TABLE_NAME + "." + Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
				+ Shop.TABLE_NAME + "." + Shop.CUSTOMER_HELPLINE_NUMBER + ","
				+ Shop.TABLE_NAME + "." + Shop.DELIVERY_HELPLINE_NUMBER + ","
				+ Shop.TABLE_NAME + "." + Shop.SHORT_DESCRIPTION + ","
				+ Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + ","
				+ Shop.TABLE_NAME + "." + Shop.IS_OPEN + ","
				+ Shop.TABLE_NAME + "." + Shop.TIMESTAMP_CREATED + ""*/



		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryJoin = queryJoin + queryPartSortBy;
			}
		}



		if(limit !=null)
		{

			String queryPartLimitOffset = "";

			if(offset!=null)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}

			queryJoin = queryJoin + queryPartLimitOffset;
		}



		ArrayList<Shop> shopList = new ArrayList<Shop>();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();

			statement = connection.createStatement();

			rs = statement.executeQuery(queryJoin);

			while(rs.next())
			{

				Shop shop = new Shop();
				shop.setRt_distance(rs.getDouble("distance"));
				shop.setShopID(rs.getInt(Shop.SHOP_ID));
				shop.setShopName(rs.getString(Shop.SHOP_NAME));
				shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
				shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));
				shop.setDeliveryCharges(rs.getFloat(Shop.DELIVERY_CHARGES));
				shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));

				shop.setLogoImagePath(rs.getString(Shop.LOGO_IMAGE_PATH));
				shop.setShopAddress(rs.getString(Shop.SHOP_ADDRESS));
				shop.setCity(rs.getString(Shop.CITY));
				shop.setPincode(rs.getLong(Shop.PINCODE));
				shop.setLandmark(rs.getString(Shop.LANDMARK));
				shop.setBillAmountForFreeDelivery(rs.getInt(Shop.BILL_AMOUNT_FOR_FREE_DELIVERY));
				shop.setCustomerHelplineNumber(rs.getString(Shop.CUSTOMER_HELPLINE_NUMBER));
				shop.setDeliveryHelplineNumber(rs.getString(Shop.DELIVERY_HELPLINE_NUMBER));
				shop.setShortDescription(rs.getString(Shop.SHORT_DESCRIPTION));
				shop.setLongDescription(rs.getString(Shop.LONG_DESCRIPTION));
				shop.setTimestampCreated(rs.getTimestamp(Shop.TIMESTAMP_CREATED));
				shop.setOpen(rs.getBoolean(Shop.IS_OPEN));

				shop.setRt_rating_avg(rs.getFloat("avg_rating"));
				shop.setRt_rating_count(rs.getFloat("rating_count"));


				shopList.add(shop);

			}

//			System.out.println("Total Shops queried " + shopList.size());



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

			return shopList;
	}




	public ShopEndPoint endPointMetadataFilterShops( Integer itemCategoryID,
												Integer distributorID,
												Double latCenter, Double lonCenter,
												Double deliveryRangeMin,Double deliveryRangeMax,
												Double proximity)
	{





		// a recursive CTE (Common table Expression) query. This query is used for retrieving hierarchical / tree set data.

		String withRecursiveStart = "WITH RECURSIVE category_tree("
				+ ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.PARENT_CATEGORY_ID
				+ ") AS (";


		String queryJoin = "SELECT DISTINCT "

				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.PARENT_CATEGORY_ID

				+ " FROM "
				+ ItemCategory.TABLE_NAME

				+ " WHERE "
				+ ItemCategory.ITEM_CATEGORY_ID  + " = " + itemCategoryID;


		String union = " UNION ";

		String querySelect = " SELECT "

				+ "cat." + ItemCategory.ITEM_CATEGORY_ID + ","
				+ "cat." + ItemCategory.PARENT_CATEGORY_ID

				+ " FROM category_tree tempCat," + 	ItemCategory.TABLE_NAME + " cat"
				+ " WHERE cat." + ItemCategory.PARENT_CATEGORY_ID
				+ " = tempcat." + ItemCategory.ITEM_CATEGORY_ID
				+ " )";


		String queryLast = " SELECT "
				+ ItemCategory.ITEM_CATEGORY_ID
				+ " FROM category_tree";



		String queryRecursive = withRecursiveStart + queryJoin + union + querySelect +  queryLast;





//		System.out.println(query);

		// + ShopContract.TABLE_NAME + "." + ShopContract.ITEM_ID +

		queryJoin = "SELECT DISTINCT "

				+ "count(DISTINCT(" + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ")) as item_count"

				+ " FROM "
				+ Shop.TABLE_NAME  + "," + ShopItem.TABLE_NAME + ","
				+ Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME

				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID
				+ " AND " + Shop.TABLE_NAME + "." + Shop.IS_OPEN + " = TRUE "
				+ " AND " + Shop.TABLE_NAME + "." + Shop.SHOP_ENABLED + " = TRUE "
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + " > 0 ";



		queryJoin = queryJoin + " AND "
				+ ItemCategory.TABLE_NAME
				+ "."
				+ ItemCategory.ITEM_CATEGORY_ID + " IN "
				+ " (" + queryRecursive + ")";



		/*if(distributorID != null)
		{
			queryJoin = queryJoin + " AND "
					+ Shop.TABLE_NAME + "." + Shop.DISTRIBUTOR_ID
					+ " = " + distributorID;
		}*/


		// Visibility Filter : Apply
		if(latCenter != null && lonCenter != null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.


			String queryPartlatLonCenter = "";

			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";

			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;
		}



		// Delivery Range Filter : apply
		if(deliveryRangeMin != null && deliveryRangeMax != null){

			// apply delivery range filter
			String queryPartDeliveryRange = "";

			queryPartDeliveryRange = queryPartDeliveryRange + Shop.TABLE_NAME
					+ "."
					+ Shop.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
			//+ " <= " + deliveryRange;

			queryJoin = queryJoin + " AND " + queryPartDeliveryRange;

		}


		// Proximity Filter
		if(proximity != null)
		{
			// proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

			String queryPartProximity = "";


			// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians("
					+ latCenter
					+ ")) * cos( radians("
					+ Shop.LAT_CENTER
					+ " )) * cos(radians( "
					+ Shop.LON_CENTER
					+ ") - radians("
					+ lonCenter
					+ "))"
					+ " + sin( radians("
					+ latCenter
					+ ")) * sin(radians("
					+ Shop.LAT_CENTER
					+ ")))) <= "
					+ proximity ;

			queryJoin = queryJoin + " AND " + queryPartProximity;


		}



		ShopEndPoint endPoint = new ShopEndPoint();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = dataSource.getConnection();

			stmt = conn.createStatement();

			rs = stmt.executeQuery(queryJoin);

			while(rs.next())
			{
				endPoint.setItemCount(rs.getInt("item_count"));
			}

//			System.out.println("ShopEndpoint : ShopsCount : " + endPoint.getItemCount());



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

				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return endPoint;
	}







//	String searchString,
//	String sortBy,
//	Integer limit, Integer offset
	public ArrayList<Shop> getShopsForShopFilters(
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin,Double deliveryRangeMax,
			Double proximity)
	{

		String query = "";
		String queryJoin = "";

		queryJoin = "SELECT DISTINCT "

				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
				+ Shop.SHOP_NAME + ","

				+ Shop.DELIVERY_RANGE + ","
				+ Shop.LAT_CENTER + ","
				+ Shop.LON_CENTER + ""

				+ " FROM " + Shop.TABLE_NAME + "," + ShopItem.TABLE_NAME + "," + Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " = " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;


		// Visibility Filter : Apply
		if(latCenter != null && lonCenter != null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.
			String queryPartlatLonCenter = "";

			String queryPartProximity = null;

			queryPartProximity = " ((6371.01 * acos(cos( radians( "
					+ latCenter
					+ " )) * cos( radians( "
					+ Shop.LAT_CENTER
					+ " )) * cos(radians( "
					+ Shop.LON_CENTER
					+ " ) - radians( "
					+ lonCenter
					+ " )) "
					+ " + sin( radians( "
					+ latCenter
					+ " )) * sin(radians( "
					+ Shop.LAT_CENTER
					+ " )))) <= "
					+ Shop.DELIVERY_RANGE + " ) " ;


			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";

			queryJoin = queryJoin + " AND " + queryPartProximity;

		}



		// Delivery Range Filter : apply
		if(deliveryRangeMin != null && deliveryRangeMax != null){

			// apply delivery range filter
			String queryPartDeliveryRange = "";


			queryPartDeliveryRange = queryPartDeliveryRange + Shop.TABLE_NAME
					+ "."
					+ Shop.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
			//+ " <= " + deliveryRange;

			queryJoin = queryJoin + " AND " + queryPartDeliveryRange;

		}


		// Proximity Filter
		if(proximity != null)
		{
			// proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

			String queryPartProximity = "";


			// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians("
					+ latCenter
					+ ")) * cos( radians("
					+ Shop.LAT_CENTER
					+ " )) * cos(radians( "
					+ Shop.LON_CENTER
					+ ") - radians("
					+ lonCenter
					+ "))"
					+ " + sin( radians("
					+ latCenter
					+ ")) * sin(radians("
					+ Shop.LAT_CENTER
					+ ")))) <= "
					+ proximity ;



			queryJoin = queryJoin + " AND " + queryPartProximity;
		}

		query = queryJoin;





		ArrayList<Shop> shopList = new ArrayList<Shop>();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{

				Shop shop = new Shop();

				shop.setShopID(rs.getInt(Shop.SHOP_ID));
				shop.setShopName(rs.getString(Shop.SHOP_NAME));
				shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));
				shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
				shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));

				shopList.add(shop);

			}

//			System.out.println("Total Shops queried " + shopList.size());



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

		return shopList;
	}





	public ShopEndPoint getEndPointMetadataFilterShops(
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin,Double deliveryRangeMax,
			Double proximity
	)
	{


		String query = "";
		String queryJoin = "";


		queryJoin = "SELECT DISTINCT "
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID

				+ " FROM "
				+ Shop.TABLE_NAME  + "," + ShopItem.TABLE_NAME + ","
				+ Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME

				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;


		// Visibility Filter : Apply
		if(latCenter != null && lonCenter != null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.

//			String queryPartlat = "";

			queryJoin = queryJoin + " AND "
					+ " (6371.01 * acos(cos( radians( ? )) * cos( radians(" + Shop.LAT_CENTER + " )) * cos(radians( "
					+ Shop.LON_CENTER + ") - radians( ? ))" + " + sin( radians( ? )) * sin(radians("
					+ Shop.LAT_CENTER + ")))) <= " + Shop.DELIVERY_RANGE + "" ;


//			String queryPartlatLonCenter = "";
//			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
//					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
//					+ lonCenter + "))"
//					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";

//			queryJoin = queryJoin + " AND " + queryPartlat;

		}



		// Delivery Range Filter : apply
		if(deliveryRangeMin != null && deliveryRangeMax != null){

			// apply delivery range filter
			String queryPartDeliveryRange = "";

			queryPartDeliveryRange = queryPartDeliveryRange
					+ Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + " BETWEEN ? AND ?";

			queryJoin = queryJoin + " AND " + queryPartDeliveryRange;
		}


		// Proximity Filter
		if(proximity != null)
		{
			// proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

			String queryPartProximity = "";


			// Make sure that shop center lies between the bounding coordinates generated by proximity bounding box


			// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians( ? )) * cos( radians(" + Shop.LAT_CENTER + " )) * cos(radians( "
					+ Shop.LON_CENTER + ") - radians( ? ))" + " + sin( radians( ? )) * sin(radians("
					+ Shop.LAT_CENTER + ")))) <= ? ";


			queryJoin = queryJoin + " AND " + queryPartProximity;
		}


		query = queryJoin;



		query = "SELECT COUNT(*) as item_count FROM (" + query + ") AS temp";


		ShopEndPoint endPoint = new ShopEndPoint();


		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);

			int i = 0;

			if(latCenter!=null && lonCenter!=null)
			{
				statement.setDouble(++i,latCenter);
				statement.setDouble(++i,lonCenter);
				statement.setDouble(++i,latCenter);
			}

			if(deliveryRangeMin!=null && deliveryRangeMax!=null)
			{
				statement.setObject(++i,deliveryRangeMin);
				statement.setObject(++i,deliveryRangeMax);
			}


			if(proximity!=null)
			{
				statement.setDouble(++i,latCenter);
				statement.setDouble(++i,lonCenter);
				statement.setDouble(++i,latCenter);

				statement.setObject(++i,proximity);
			}



			rs = statement.executeQuery();

			while(rs.next())
			{

				endPoint.setItemCount(rs.getInt("item_count"));


			}

//			System.out.println("Total Shops Item Count :  " + endPoint.getItemCount());



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





	public ArrayList<Shop> getShopsForShopFiltersPrepared(
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin,Double deliveryRangeMax,
			Double proximity)
	{

		String query = "";
		String queryJoin = "";

		queryJoin = "SELECT DISTINCT "
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
				+ Shop.SHOP_NAME + ","

				+ Shop.DELIVERY_RANGE + ","
				+ Shop.LAT_CENTER + ","
				+ Shop.LON_CENTER + ""

				+ " FROM " + Shop.TABLE_NAME + "," + ShopItem.TABLE_NAME + "," + Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " = " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID

				+ " AND " // Visibility Filter
				+ " ((6371.01 * acos(cos( radians( ? )) * cos( radians( " + Shop.LAT_CENTER + " )) * cos(radians( "
				+ Shop.LON_CENTER + " ) - radians( ? )) " + " + sin( radians( ? )) * sin(radians( "
				+ Shop.LAT_CENTER + " )))) <= " + Shop.DELIVERY_RANGE + " ) ";


//		if(latCenter!=null && lonCenter!=null)
//		{
//			queryJoin = queryJoin
//		}


		if(deliveryRangeMin!=null && deliveryRangeMax!=null)
		{
			queryJoin = queryJoin + " AND "  // Delivery Range Filter
					+ Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + " BETWEEN ? AND ? ";
		}


		if(proximity!=null)
		{
			queryJoin = queryJoin + " AND " // Proximity Filter
					+ " (6371.01 * acos(cos( radians( ? )) * cos( radians(" + Shop.LAT_CENTER + " )) * cos(radians( "
					+ Shop.LON_CENTER + ") - radians( ? ))" + " + sin( radians( ? )) * sin(radians("
					+ Shop.LAT_CENTER + ")))) <= ?";
		}



		//VisibilityFilter 1. latCenter : 2. lonCenter : 3. latCenter
		//Proximity Filter 1. latCenter : 2. lonCenter : 3. latCenter 4: proximity


		String queryCount = "SELECT COUNT(*) as item_count FROM (" + query + ") AS temp";

		query = queryJoin;

//		ShopEndPoint endPoint = getShopsForShopFiltersPrepared(latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity);
		ArrayList<Shop> shopList = new ArrayList<Shop>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);


			// case 1 : No filters
			// Case 2 : Delivery Range Filter OFF and Proximity Filter ON
			// Case 3 : Proximity Filter OFF and Delivery Range FIlter ON
			// Case 4 : Both filters ON



			statement.setDouble(1,latCenter);
			statement.setDouble(2,lonCenter);
			statement.setDouble(3,latCenter);

			int i = 3;
			if(deliveryRangeMin!=null && deliveryRangeMax!=null)
			{
				statement.setObject(++i,deliveryRangeMin);
				statement.setObject(++i,deliveryRangeMax);
			}

			if(proximity!=null)
			{
				statement.setDouble(++i,latCenter);
				statement.setDouble(++i,lonCenter);
				statement.setDouble(++i,latCenter);

				statement.setObject(++i,proximity);
			}




			/*if(!(deliveryRangeMin!=null && deliveryRangeMax!=null) && (proximity != null))
			{
				statement.setDouble(4,latCenter);
				statement.setDouble(5,lonCenter);
				statement.setDouble(6,latCenter);

				statement.setObject(7,proximity);
			}
			else if((deliveryRangeMin!=null && deliveryRangeMax!=null) && (proximity==null))
			{
				statement.setObject(4,deliveryRangeMin);
				statement.setObject(5,deliveryRangeMax);
			}
			else if((deliveryRangeMin!=null && deliveryRangeMax!=null) && (proximity!=null))
			{
				statement.setObject(4,deliveryRangeMin);
				statement.setObject(5,deliveryRangeMax);

				statement.setDouble(6,latCenter);
				statement.setDouble(7,lonCenter);
				statement.setDouble(8,latCenter);

				statement.setObject(9,proximity);
			}
				*/


			rs = statement.executeQuery();


			while(rs.next())
			{

				Shop shop = new Shop();
				shop.setShopID(rs.getInt(Shop.SHOP_ID));
				shop.setShopName(rs.getString(Shop.SHOP_NAME));
				shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));
				shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
				shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));

//				endPoint.setItemCount(rs.getInt("full_count"));

				shopList.add(shop);
			}


//			endPoint.setResults(shopList);

//			System.out.println("Total Shops queried " + shopList.size());

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

		return shopList;
	}

}

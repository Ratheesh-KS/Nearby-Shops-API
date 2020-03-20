package org.nearbyshops.DAOs.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ModelReviewShop.ShopReview;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.Model.ModelRoles.DeliveryGuyData;
import org.nearbyshops.Model.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.Model.ModelRoles.User;



import java.sql.*;


public class DAOShopStaff {


	private HikariDataSource dataSource = Globals.getDataSource();




	public int updateShopStaffLocation(ShopStaffPermissions permissions)
	{


		String insertStaffPermissions =

				"INSERT INTO " + ShopStaffPermissions.TABLE_NAME
						+ "("
						+ ShopStaffPermissions.STAFF_ID + ","
						+ ShopStaffPermissions.LAT_CURRENT + ","
						+ ShopStaffPermissions.LON_CURRENT + ""
						+ ") values(?,?,?)"
						+ " ON CONFLICT (" + ShopStaffPermissions.STAFF_ID + ")"
						+ " DO UPDATE "
						+ " SET "
						+ ShopStaffPermissions.LAT_CURRENT + "= excluded." + ShopStaffPermissions.LAT_CURRENT + " , "
						+ ShopStaffPermissions.LON_CURRENT + "= excluded." + ShopStaffPermissions.LON_CURRENT;




		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);


			statement = connection.prepareStatement(insertStaffPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 0;


			if(permissions!=null)
			{
				statement.setObject(++i,permissions.getStaffUserID());
				statement.setObject(++i,permissions.getLatCurrent());
				statement.setObject(++i,permissions.getLonCurrent());

				rowCountUpdated = statement.executeUpdate();
			}


			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

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

		return rowCountUpdated;
	}



	public int upgradeUserToStaff(int userID, int shopID, int secretCode, int role)
	{

		String updateStatement = "UPDATE " + User.TABLE_NAME
				+ " SET " + User.ROLE + "=?"
				+ " WHERE " + User.USER_ID + " = ?"
				+ " AND " + User.SECRET_CODE + " = ?";

//		+ " AND " + User.ROLE + " = " + GlobalConstants.ROLE_END_USER_CODE


		String insertPermissions = "INSERT INTO " + ShopStaffPermissions.TABLE_NAME
				+ "("
				+ ShopStaffPermissions.STAFF_ID + ","
				+ ShopStaffPermissions.SHOP_ID + ""
				+ ") values(?,?)"
				+ " ON CONFLICT (" + ShopStaffPermissions.STAFF_ID + ")"
				+ " DO UPDATE "
				+ " SET " + ShopStaffPermissions.SHOP_ID + "= excluded." + ShopStaffPermissions.SHOP_ID ;


		String insertDeliveryData = "INSERT INTO " + DeliveryGuyData.TABLE_NAME
				+ "("
				+ DeliveryGuyData.STAFF_USER_ID + ","
				+ DeliveryGuyData.SHOP_ID + ""
				+ ") values(?,?)"
				+ " ON CONFLICT (" + DeliveryGuyData.STAFF_USER_ID + ")"
				+ " DO UPDATE "
				+ " SET " + DeliveryGuyData.SHOP_ID + "= excluded." + DeliveryGuyData.SHOP_ID ;



		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);



			statement = connection.prepareStatement(updateStatement);

			int i = 0;

			statement.setInt(++i,role);
			statement.setObject(++i,userID);
			statement.setObject(++i,secretCode);

			rowCountUpdated = statement.executeUpdate();


			if(role==GlobalConstants.ROLE_SHOP_STAFF_CODE)
			{
				statement = connection.prepareStatement(insertPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
				i = 0;

				statement.setObject(++i,userID);
				statement.setObject(++i,shopID);
				statement.executeUpdate();

			}
			else if(role==GlobalConstants.ROLE_DELIVERY_GUY_SELF_CODE)
			{
				statement = connection.prepareStatement(insertDeliveryData,PreparedStatement.RETURN_GENERATED_KEYS);
				i = 0;

				statement.setObject(++i,userID);
				statement.setObject(++i,shopID);
				statement.executeUpdate();
			}


			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

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

		return rowCountUpdated;
	}


	public ShopStaffPermissions getShopStaffPermissions(int staffID)
	{

		boolean isFirst = true;

		String query = "SELECT "

				+ ShopStaffPermissions.STAFF_ID + ","
				+ ShopStaffPermissions.SHOP_ID + ","

				+ ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + ","
				+ ShopStaffPermissions.UPDATE_STOCK + ","

				+ ShopStaffPermissions.CANCEL_ORDERS + ","
				+ ShopStaffPermissions.CONFIRM_ORDERS + ","
				+ ShopStaffPermissions.SET_ORDERS_PACKED + ","
				+ ShopStaffPermissions.HANDOVER_TO_DELIVERY + ","
//				+ ShopStaffPermissions.MARK_ORDERS_DELIVERED + ","
				+ ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + ","
				+ ShopStaffPermissions.DESIGNATION + ","
				+ ShopStaffPermissions.ACCEPT_RETURNS + ""

				+ " FROM "  + ShopStaffPermissions.TABLE_NAME
				+ " WHERE " + ShopStaffPermissions.STAFF_ID  + " = ? ";



		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;


		//Distributor distributor = null;
		ShopStaffPermissions permissions = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);

			int i = 0;


			statement.setObject(++i,staffID); // username


			rs = statement.executeQuery();

			while(rs.next())
			{
				permissions = new ShopStaffPermissions();

				permissions.setStaffUserID(rs.getInt(ShopStaffPermissions.STAFF_ID));
				permissions.setShopID(rs.getInt(ShopStaffPermissions.SHOP_ID));

				permissions.setPermitAddRemoveItems(rs.getBoolean(ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP));
				permissions.setPermitUpdateItemsInShop(rs.getBoolean(ShopStaffPermissions.UPDATE_STOCK));

				permissions.setPermitCancelOrders(rs.getBoolean(ShopStaffPermissions.CANCEL_ORDERS));
				permissions.setPermitConfirmOrders(rs.getBoolean(ShopStaffPermissions.CONFIRM_ORDERS));
				permissions.setPermitSetOrdersPacked(rs.getBoolean(ShopStaffPermissions.SET_ORDERS_PACKED));
				permissions.setPermitHandoverToDelivery(rs.getBoolean(ShopStaffPermissions.HANDOVER_TO_DELIVERY));
//				permissions.setPermitMarkOrdersDelivered(rs.getBoolean(ShopStaffPermissions.MARK_ORDERS_DELIVERED));
				permissions.setPermitAcceptPaymentsFromDelivery(rs.getBoolean(ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY));
				permissions.setPermitAcceptReturns(rs.getBoolean(ShopStaffPermissions.ACCEPT_RETURNS));

				permissions.setDesignation(rs.getString(ShopStaffPermissions.DESIGNATION));

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

		return permissions;
	}



	public int updateShopStaffPermissions(ShopStaffPermissions permissions)
	{


		String updatePermissions = "UPDATE " + ShopStaffPermissions.TABLE_NAME
				+ " SET "
				+ ShopStaffPermissions.DESIGNATION + "=?,"
				+ ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + "=?,"

				+ ShopStaffPermissions.UPDATE_STOCK + "=?,"
				+ ShopStaffPermissions.CANCEL_ORDERS + "=?,"
				+ ShopStaffPermissions.CONFIRM_ORDERS + "=?,"

				+ ShopStaffPermissions.SET_ORDERS_PACKED + "=?,"
				+ ShopStaffPermissions.HANDOVER_TO_DELIVERY + "=?,"
//				+ ShopStaffPermissions.MARK_ORDERS_DELIVERED + "=?,"

				+ ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + "=?,"
				+ ShopStaffPermissions.ACCEPT_RETURNS + "=?"

				+ " WHERE " + ShopStaffPermissions.STAFF_ID + " = ?"
				+ " AND " + ShopStaffPermissions.SHOP_ID + " = ?";



		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			int i = 0;

			statement = connection.prepareStatement(updatePermissions,PreparedStatement.RETURN_GENERATED_KEYS);


			statement.setString(++i,permissions.getDesignation());
			statement.setObject(++i,permissions.isPermitAddRemoveItems());

			statement.setObject(++i,permissions.isPermitUpdateItemsInShop());
			statement.setObject(++i,permissions.isPermitCancelOrders());
			statement.setObject(++i,permissions.isPermitConfirmOrders());

			statement.setObject(++i,permissions.isPermitSetOrdersPacked());
			statement.setObject(++i,permissions.isPermitHandoverToDelivery());

			statement.setObject(++i,permissions.isPermitAcceptPaymentsFromDelivery());
			statement.setObject(++i,permissions.isPermitAcceptReturns());

			statement.setObject(++i,permissions.getStaffUserID());
			statement.setObject(++i,permissions.getShopID());


			rowCountUpdated = statement.executeUpdate();


			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

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

		return rowCountUpdated;
	}











	/*Deprecated Method*/
	public int becomeASeller(int userID)
	{


		Connection connection = null;
		PreparedStatement statement = null;
		int rowCount = -1;


		// add joining credit to the users account
		String updateRole =  " UPDATE " + User.TABLE_NAME
				+ " SET " + User.ROLE + " = " + GlobalConstants.ROLE_SHOP_ADMIN_CODE
				+ " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ? "
				+ " AND " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_END_USER_CODE ;



		String insertShop = " INSERT INTO " + Shop.TABLE_NAME
				+ "(" + Shop.SHOP_ADMIN_ID + ","
				+ Shop.SHOP_ENABLED + ","
				+ Shop.SHOP_WAITLISTED + ""

				+ ") " +
				" VALUES( ?,?,? )";




		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			int i = 0;


			statement = connection.prepareStatement(updateRole);

			statement.setObject(++i,userID);
			rowCount = statement.executeUpdate();


			if (rowCount == 1)
			{

				statement = connection.prepareStatement(insertShop);
				i = 0;

				statement.setObject(++i,userID);
				statement.setObject(++i,null);
				statement.setObject(++i,false);

				statement.executeUpdate();

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



			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}


			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return rowCount;

	}




	/*Deprecated Method*/
	public int upsertShopStaffPermissions(ShopStaffPermissions permissions)
	{


		String insertStaffPermissions =

				"INSERT INTO " + ShopStaffPermissions.TABLE_NAME
						+ "("
						+ ShopStaffPermissions.STAFF_ID + ","

						+ ShopStaffPermissions.DESIGNATION + ","
						+ ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + ","

						+ ShopStaffPermissions.UPDATE_STOCK + ","
						+ ShopStaffPermissions.CANCEL_ORDERS + ","
						+ ShopStaffPermissions.CONFIRM_ORDERS + ","

						+ ShopStaffPermissions.SET_ORDERS_PACKED + ","
						+ ShopStaffPermissions.HANDOVER_TO_DELIVERY + ","
//						+ ShopStaffPermissions.MARK_ORDERS_DELIVERED + ","

						+ ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + ","
						+ ShopStaffPermissions.ACCEPT_RETURNS + ""

						+ ") values(?,?,?)"
						+ " ON CONFLICT (" + ShopStaffPermissions.STAFF_ID + ")"
						+ " DO UPDATE "
						+ " SET "

						+ ShopStaffPermissions.DESIGNATION + "= excluded." + ShopStaffPermissions.LAT_CURRENT + " , "
						+ ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + "= excluded." + ShopStaffPermissions.LAT_CURRENT + " , "

						+ ShopStaffPermissions.UPDATE_STOCK + "= excluded." + ShopStaffPermissions.UPDATE_STOCK + " , "
						+ ShopStaffPermissions.CANCEL_ORDERS + "= excluded." + ShopStaffPermissions.CANCEL_ORDERS + " , "
						+ ShopStaffPermissions.CONFIRM_ORDERS + "= excluded." + ShopStaffPermissions.CONFIRM_ORDERS + " , "

						+ ShopStaffPermissions.SET_ORDERS_PACKED + "= excluded." + ShopStaffPermissions.SET_ORDERS_PACKED + " , "
						+ ShopStaffPermissions.HANDOVER_TO_DELIVERY + "= excluded." + ShopStaffPermissions.HANDOVER_TO_DELIVERY + " , "
//						+ ShopStaffPermissions.MARK_ORDERS_DELIVERED + "= excluded." + ShopStaffPermissions.MARK_ORDERS_DELIVERED + " , "

						+ ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + "= excluded." + ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + " , "
						+ ShopStaffPermissions.ACCEPT_RETURNS + "= excluded." + ShopStaffPermissions.ACCEPT_RETURNS + "";



		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);


			statement = connection.prepareStatement(insertStaffPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 0;


			if(permissions!=null)
			{
				statement.setObject(++i,permissions.getStaffUserID());

				statement.setString(++i,permissions.getDesignation());
				statement.setObject(++i,permissions.isPermitAddRemoveItems());

				statement.setObject(++i,permissions.isPermitUpdateItemsInShop());
				statement.setObject(++i,permissions.isPermitCancelOrders());
				statement.setObject(++i,permissions.isPermitConfirmOrders());

				statement.setObject(++i,permissions.isPermitSetOrdersPacked());
				statement.setObject(++i,permissions.isPermitHandoverToDelivery());
//				statement.setObject(++i,permissions.isPermitMarkOrdersDelivered());

				statement.setObject(++i,permissions.isPermitAcceptPaymentsFromDelivery());
				statement.setObject(++i,permissions.isPermitAcceptReturns());

				rowCountUpdated = statement.executeUpdate();
			}


			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

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

		return rowCountUpdated;
	}



}

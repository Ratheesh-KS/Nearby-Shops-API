package org.nearbyshops.DAOs.DAOSettings;


import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ModelMarkets.Market;

import java.sql.*;
import java.util.ArrayList;


public class ServiceConfigurationDAO {


	private HikariDataSource dataSource = Globals.getDataSource();



	public int saveService(Market market)
	{

		Connection connection = null;
		PreparedStatement statement = null;
		int rowIdOfInsertedRow = -1;

		String insertItemCategory = "INSERT INTO "
				+ Market.TABLE_NAME
				+ "("

//				+ ServiceConfigurationLocal.IMAGE_PATH + ","
				+ Market.LOGO_IMAGE_PATH + ","
				+ Market.BACKDROP_IMAGE_PATH + ","

				+ Market.SERVICE_NAME + ","
				+ Market.HELPLINE_NUMBER + ","

				+ Market.DESCRIPTION_SHORT + ","
				+ Market.DESCRIPTION_LONG + ","

				+ Market.ADDRESS + ","
				+ Market.CITY + ","
				+ Market.PINCODE + ","
				+ Market.LANDMARK + ","

				+ Market.STATE + ","
				+ Market.COUNTRY + ","
				+ Market.ISO_COUNTRY_CODE + ","
				+ Market.ISO_LANGUAGE_CODE + ","
				+ Market.ISO_CURRENCY_CODE + ","

//				+ ServiceConfigurationLocal.SERVICE_TYPE + ","
//				+ ServiceConfigurationLocal.SERVICE_LEVEL + ","

				+ Market.LAT_CENTER + ","
				+ Market.LON_CENTER + ","
				+ Market.SERVICE_RANGE + ","

				+ Market.UPDATED + ","
				+ Market.SERVICE_CONFIGURATION_ID + ""
				+ " ) VALUES (?,? ,?,?,  ?,? ,?,?,?,? ,?,?,?,?,? ,?,?,? ,?,?)";
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertItemCategory,Statement.RETURN_GENERATED_KEYS);

			int i = 0;

//			statement.setString(1,serviceConfigurationLocal.getImagePath());
			statement.setString(++i, market.getLogoImagePath());
			statement.setString(++i, market.getBackdropImagePath());

			statement.setString(++i, market.getServiceName());
			statement.setString(++i, market.getHelplineNumber());

			statement.setString(++i, market.getDescriptionShort());
			statement.setString(++i, market.getDescriptionLong());

			statement.setString(++i, market.getAddress());
			statement.setString(++i, market.getCity());
			statement.setObject(++i, market.getPincode());
			statement.setString(++i, market.getLandmark());

			statement.setString(++i, market.getState());
			statement.setString(++i, market.getCountry());
			statement.setString(++i, market.getISOCountryCode());
			statement.setString(++i, market.getISOLanguageCode());
			statement.setString(++i, market.getISOCurrencyCode());

//			statement.setObject(++i, serviceConfigurationLocal.getServiceType());
//			statement.setObject(++i, serviceConfigurationLocal.getServiceLevel());

			statement.setObject(++i, market.getLatCenter());
			statement.setObject(++i, market.getLonCenter());
			statement.setObject(++i, market.getServiceRange());

			statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
			statement.setObject(++i,1);




			rowIdOfInsertedRow = statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();

			if(rs.next())
			{
				rowIdOfInsertedRow = rs.getInt(1);
			}
			
			
			
			System.out.println("Key autogenerated Save CurrentServiceConfiguration: " + rowIdOfInsertedRow);
			
			
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

		
		return rowIdOfInsertedRow;
	}




	public int updateService(Market market)
	{

		// Ensure the service configuration row exist before being updated
		if(getServiceConfiguration(null,null)==null){
			saveService(getDefaultConfiguration());
		}


		String updateStatement = "UPDATE " + Market.TABLE_NAME

				+ " SET "

//				+ ServiceConfigurationLocal.IMAGE_PATH + " = ?,"
				+ Market.LOGO_IMAGE_PATH + " = ?,"
				+ Market.BACKDROP_IMAGE_PATH + " = ?,"

				+ Market.SERVICE_NAME + " = ?,"
				+ Market.HELPLINE_NUMBER + " = ?,"

				+ Market.DESCRIPTION_SHORT + "=?,"
				+ Market.DESCRIPTION_LONG + "=?,"

				+ Market.ADDRESS + " = ?,"

				+ Market.CITY + " = ?,"
				+ Market.PINCODE + " = ?,"
				+ Market.LANDMARK + " = ?,"

				+ Market.STATE + " = ?,"
				+ Market.COUNTRY + " = ?,"
				+ Market.ISO_COUNTRY_CODE + " = ?,"

				+ Market.ISO_LANGUAGE_CODE + " = ?,"
				+ Market.ISO_CURRENCY_CODE + " = ?,"

//				+ ServiceConfigurationLocal.SERVICE_TYPE + " = ?,"
//				+ ServiceConfigurationLocal.SERVICE_LEVEL + " = ?,"

				+ Market.LAT_CENTER + " = ?,"
				+ Market.LON_CENTER + " = ?,"
				+ Market.SERVICE_RANGE + " = ?,"

				+ Market.UPDATED + " = ?"
				+ " WHERE "
				+ Market.SERVICE_CONFIGURATION_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			int i = 0;
//			statement.setString(1,serviceConfigurationLocal.getImagePath());
			statement.setString(++i, market.getLogoImagePath());
			statement.setString(++i, market.getBackdropImagePath());

			statement.setString(++i, market.getServiceName());
			statement.setString(++i, market.getHelplineNumber());


			statement.setString(++i, market.getDescriptionShort());
			statement.setString(++i, market.getDescriptionLong());

			statement.setString(++i, market.getAddress());

			statement.setString(++i, market.getCity());
			statement.setObject(++i, market.getPincode());
			statement.setString(++i, market.getLandmark());

			statement.setString(++i, market.getState());
			statement.setString(++i, market.getCountry());
			statement.setString(++i, market.getISOCountryCode());

			statement.setString(++i, market.getISOLanguageCode());
			statement.setString(++i, market.getISOCurrencyCode());

//			statement.setObject(++i, serviceConfigurationLocal.getServiceType());
//			statement.setObject(++i, serviceConfigurationLocal.getServiceLevel());

			statement.setObject(++i, market.getLatCenter());
			statement.setObject(++i, market.getLonCenter());
			statement.setObject(++i, market.getServiceRange());

			statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));

			statement.setObject(++i,1);


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




	public int deleteService(int serviceID)
	{
		
		String deleteStatement = "DELETE FROM " + Market.TABLE_NAME
				+ " WHERE " + Market.SERVICE_CONFIGURATION_ID + " = ?";
		
		
		Connection connection= null;
		PreparedStatement statement = null;
		int rowsCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);

			statement.setInt(1,serviceID);
			rowsCountDeleted = statement.executeUpdate();
			System.out.println(" Deleted Count: " + rowsCountDeleted);

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
	
		
		return rowsCountDeleted;
	}
	



	public ArrayList<Market> readServices(Integer serviceLevel, Integer serviceType,
										  Double latCenterQuery, Double lonCenterQuery,
										  String sortBy,
										  int limit, int offset)
	{


		String queryNormal = "SELECT " + " 6371 * acos(cos( radians("
							+ latCenterQuery + ")) * cos( radians( " + Market.LAT_CENTER
							+ ")) * cos(radians( "
							+ Market.LON_CENTER + ") - radians(" + lonCenterQuery + "))"
							+ " + sin( radians(" + latCenterQuery+ ")) * sin(radians(" + Market.LAT_CENTER + "))) as distance" + ","

							+ Market.SERVICE_CONFIGURATION_ID + ","
							+ Market.LOGO_IMAGE_PATH + ","
							+ Market.BACKDROP_IMAGE_PATH + ","

							+ Market.SERVICE_NAME + ","
							+ Market.HELPLINE_NUMBER + ","

							+ Market.DESCRIPTION_SHORT + ","
							+ Market.DESCRIPTION_LONG + ","

							+ Market.ADDRESS + ","
							+ Market.CITY + ","
							+ Market.PINCODE + ","
							+ Market.LANDMARK + ","

							+ Market.STATE + ","
							+ Market.COUNTRY + ","
							+ Market.ISO_COUNTRY_CODE + ","

							+ Market.ISO_LANGUAGE_CODE + ","
							+ Market.ISO_CURRENCY_CODE + ","

//							+ ServiceConfigurationLocal.SERVICE_TYPE + ","
//							+ ServiceConfigurationLocal.SERVICE_LEVEL + ","

							+ Market.LAT_CENTER + ","
							+ Market.LON_CENTER + ","
							+ Market.SERVICE_RANGE + ","

							+ Market.CREATED + ","
							+ Market.UPDATED + ""
							+ " FROM " + Market.TABLE_NAME
							+ " WHERE TRUE ";


		boolean isFirst = true;


//		if(serviceLevel != null)
//		{
//			queryNormal = queryNormal + " WHERE " + ServiceConfigurationLocal.SERVICE_LEVEL + " = " + serviceLevel;
//
//			isFirst = false;
//		}



//		if(serviceType !=null)
//		{
//			if(isFirst)
//			{
//				queryNormal = queryNormal + " WHERE " + ServiceConfigurationLocal.SERVICE_TYPE + " = " + serviceType;
//
//				isFirst = false;
//
//			}else
//			{
//				queryNormal = queryNormal + " AND " + ServiceConfigurationLocal.SERVICE_TYPE + " = " + serviceType;
//
//			}
//
//		}



		// apply visibility filter


		if(latCenterQuery!=null && lonCenterQuery!=null)
		{

			String queryPartVisibilityFilter = "";




			// filter using Haversine formula using SQL math functions
			queryPartVisibilityFilter = queryPartVisibilityFilter
					+ " (6371.01 * acos(cos( radians("
					+ latCenterQuery
					+ ")) * cos( radians("
					+ Market.LAT_CENTER
					+ " )) * cos(radians( "
					+ Market.LON_CENTER
					+ ") - radians("
					+ lonCenterQuery
					+ "))"
					+ " + sin( radians("
					+ latCenterQuery
					+ ")) * sin(radians("
					+ Market.LAT_CENTER
					+ ")))) <= "
					+ Market.SERVICE_RANGE ;


			if(isFirst)
			{
				queryNormal = queryNormal + " WHERE ";

				// reset the flag
				isFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND ";
			}


			queryNormal = queryNormal + queryPartVisibilityFilter;
		}

		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryNormal = queryNormal + queryPartSortBy;
			}
		}



		if(limit > 0)
		{

			String queryPartLimitOffset = "";

			if(offset>0)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


			queryNormal = queryNormal + queryPartLimitOffset;
		}



		ArrayList<Market> servicesList = new ArrayList<Market>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();


			rs = statement.executeQuery(queryNormal);

			while(rs.next())
			{
				Market market = new Market();

				market.setRt_distance(rs.getDouble("distance"));

				market.setServiceID(rs.getInt(Market.SERVICE_CONFIGURATION_ID));
//				serviceConfigurationLocal.setImagePath(rs.getString(ServiceConfigurationLocal.IMAGE_PATH));
				market.setLogoImagePath(rs.getString(Market.LOGO_IMAGE_PATH));
				market.setBackdropImagePath(rs.getString(Market.BACKDROP_IMAGE_PATH));

				market.setServiceName(rs.getString(Market.SERVICE_NAME));
				market.setHelplineNumber(rs.getString(Market.HELPLINE_NUMBER));

				market.setDescriptionShort(rs.getString(Market.DESCRIPTION_SHORT));
				market.setDescriptionLong(rs.getString(Market.DESCRIPTION_LONG));

				market.setAddress(rs.getString(Market.ADDRESS));
				market.setCity(rs.getString(Market.CITY));
				market.setPincode(rs.getLong(Market.PINCODE));
				market.setLandmark(rs.getString(Market.LANDMARK));

				market.setState(rs.getString(Market.STATE));
				market.setCountry(rs.getString(Market.COUNTRY));
				market.setISOCountryCode(rs.getString(Market.ISO_COUNTRY_CODE));
				market.setISOLanguageCode(rs.getString(Market.ISO_LANGUAGE_CODE));
				market.setISOCurrencyCode(rs.getString(Market.ISO_CURRENCY_CODE));

//				serviceConfigurationLocal.setServiceType(rs.getInt(ServiceConfigurationLocal.SERVICE_TYPE));
//				serviceConfigurationLocal.setServiceLevel(rs.getInt(ServiceConfigurationLocal.SERVICE_LEVEL));

				market.setLatCenter(rs.getDouble(Market.LAT_CENTER));
				market.setLonCenter(rs.getDouble(Market.LON_CENTER));

				market.setServiceRange(rs.getInt(Market.SERVICE_RANGE));

//				serviceConfigurationLocal.setLatMax(rs.getDouble(ServiceConfigurationLocal.LAT_MAX));
//				serviceConfigurationLocal.setLonMax(rs.getDouble(ServiceConfigurationLocal.LON_MAX));
//				serviceConfigurationLocal.setLatMin(rs.getDouble(ServiceConfigurationLocal.LAT_MIN));
//
//				serviceConfigurationLocal.setLonMin(rs.getDouble(ServiceConfigurationLocal.LON_MIN));

				market.setCreated(rs.getTimestamp(Market.CREATED));
				market.setUpdated(rs.getTimestamp(Market.UPDATED));


				servicesList.add(market);
				
			}
			

			
		}
		catch (SQLException e) {
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
		
								
		return servicesList;
	}







	public Market getServiceConfiguration(Double latCenter, Double lonCenter)
	{
		
		String query = " SELECT "
						+ "6371 * acos(cos( radians("
						+ latCenter + ")) * cos( radians( " + Market.LAT_CENTER + " ) ) * cos(radians( " + Market.LON_CENTER + " ) - radians("
						+ lonCenter + "))"
						+ " + sin( radians(" + latCenter + ")) * sin(radians( " + Market.LAT_CENTER + " ))) as distance" + ","
						+ Market.SERVICE_CONFIGURATION_ID + ","
						+ Market.LOGO_IMAGE_PATH + ","
						+ Market.BACKDROP_IMAGE_PATH + ","

						+ Market.SERVICE_NAME + ","
						+ Market.HELPLINE_NUMBER + ","

						+ Market.DESCRIPTION_SHORT + ","
						+ Market.DESCRIPTION_LONG + ","

						+ Market.ADDRESS + ","
						+ Market.CITY + ","
						+ Market.PINCODE + ","
						+ Market.LANDMARK + ","

						+ Market.STATE + ","
						+ Market.COUNTRY + ","
						+ Market.ISO_COUNTRY_CODE + ","

						+ Market.ISO_LANGUAGE_CODE + ","
						+ Market.ISO_CURRENCY_CODE + ","

//						+ ServiceConfigurationLocal.SERVICE_TYPE + ","
//						+ ServiceConfigurationLocal.SERVICE_LEVEL + ","

						+ Market.LAT_CENTER + ","
						+ Market.LON_CENTER + ","
						+ Market.SERVICE_RANGE + ","

						+ Market.CREATED + ","
						+ Market.UPDATED + ""
						+ " FROM " + Market.TABLE_NAME
						+ " WHERE " + Market.SERVICE_CONFIGURATION_ID + " = " + 1;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		Market market = null;

		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				market = new Market();

				market.setRt_distance(rs.getDouble("distance"));
				market.setServiceID(rs.getInt(Market.SERVICE_CONFIGURATION_ID));
//				serviceConfigurationLocal.setImagePath(rs.getString(ServiceConfigurationLocal.IMAGE_PATH));
				market.setLogoImagePath(rs.getString(Market.LOGO_IMAGE_PATH));
				market.setBackdropImagePath(rs.getString(Market.BACKDROP_IMAGE_PATH));

				market.setServiceName(rs.getString(Market.SERVICE_NAME));
				market.setHelplineNumber(rs.getString(Market.HELPLINE_NUMBER));

				market.setDescriptionShort(rs.getString(Market.DESCRIPTION_SHORT));
				market.setDescriptionLong(rs.getString(Market.DESCRIPTION_LONG));

				market.setAddress(rs.getString(Market.ADDRESS));
				market.setCity(rs.getString(Market.CITY));
				market.setPincode(rs.getLong(Market.PINCODE));
				market.setLandmark(rs.getString(Market.LANDMARK));

				market.setState(rs.getString(Market.STATE));
				market.setCountry(rs.getString(Market.COUNTRY));
				market.setISOCountryCode(rs.getString(Market.ISO_COUNTRY_CODE));
				market.setISOLanguageCode(rs.getString(Market.ISO_LANGUAGE_CODE));
				market.setISOCurrencyCode(rs.getString(Market.ISO_CURRENCY_CODE));

//				serviceConfigurationLocal.setServiceType(rs.getInt(ServiceConfigurationLocal.SERVICE_TYPE));
//				serviceConfigurationLocal.setServiceLevel(rs.getInt(ServiceConfigurationLocal.SERVICE_LEVEL));


				market.setLatCenter(rs.getDouble(Market.LAT_CENTER));
				market.setLonCenter(rs.getDouble(Market.LON_CENTER));

				market.setServiceRange(rs.getInt(Market.SERVICE_RANGE));

//				serviceConfigurationLocal.setLatMax(rs.getDouble(ServiceConfigurationLocal.LAT_MAX));
//				serviceConfigurationLocal.setLonMax(rs.getDouble(ServiceConfigurationLocal.LON_MAX));
//				serviceConfigurationLocal.setLatMin(rs.getDouble(ServiceConfigurationLocal.LAT_MIN));
//
//				serviceConfigurationLocal.setLonMin(rs.getDouble(ServiceConfigurationLocal.LON_MIN));

				market.setCreated(rs.getTimestamp(Market.CREATED));
				market.setUpdated(rs.getTimestamp(Market.UPDATED));

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
	
		return market;
	}




	private Market getDefaultConfiguration()
	{
		Market configuration = new Market();

		configuration.setAddress("Address not set");
		configuration.setCity("City not set");
		configuration.setCountry("Country not set");
//		configuration.setServiceLevel(GlobalConstants.SERVICE_LEVEL_CITY);

		return  configuration;
	}



}

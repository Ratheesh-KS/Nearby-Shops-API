package org.nearbyshops.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtility {

    private HikariDataSource dataSource = Globals.getDataSource();




    public int getShopCount(Boolean shopEnabled)
    {
        String query =  " SELECT " +
                " Count ( " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + " ) as shop_count " +
                " FROM " + Shop.TABLE_NAME +
                " WHERE TRUE ";


        if(shopEnabled!=null && shopEnabled)
        {
            query = query + Shop.SHOP_ENABLED + " = true ";
        }





        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        int shopCount = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();

            rs = statement.executeQuery(query);

            while(rs.next())
            {
                shopCount = rs.getInt("shop_count");

                System.out.println("Shop Count : " + shopCount);
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

        return shopCount;
    }


}

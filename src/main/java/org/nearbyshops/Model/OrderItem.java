package org.nearbyshops.Model;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

/**
 * Created by sumeet on 29/5/16.
 */
public class OrderItem {



    // Table Name
    public static final String TABLE_NAME = "ORDER_ITEM";




    // Column names
    public static final String ITEM_ID = "ITEM_ID";     // FOREIGN KEY
    public static final String ORDER_ID = "ORDER_ID";   // Foreign KEY
    public static final String ITEM_QUANTITY = "ITEM_QUANTITY";
    public static final String ITEM_PRICE_AT_ORDER = "ITEM_PRICE_AT_ORDER";
    public static final String LIST_PRICE_AT_ORDER = "LIST_PRICE_AT_ORDER";




    // Create table OrderItemPFS in Postgres
    public static final String createtableOrderItemPostgres = "CREATE TABLE IF NOT EXISTS " + OrderItem.TABLE_NAME + "("
            + " " + OrderItem.ITEM_ID + " INT,"
            + " " + OrderItem.ORDER_ID + " INT,"
            + " " + OrderItem.ITEM_PRICE_AT_ORDER + " FLOAT NOT NULL DEFAULT 0,"
            + " " + OrderItem.LIST_PRICE_AT_ORDER + " FLOAT NOT NULL DEFAULT 0,"
            + " " + OrderItem.ITEM_QUANTITY + " FLOAT NOT NULL DEFAULT 0,"
            + " FOREIGN KEY(" + OrderItem.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + ") ON DELETE CASCADE,"
            + " FOREIGN KEY(" + OrderItem.ORDER_ID +") REFERENCES " + Order.TABLE_NAME + "(" + Order.ORDER_ID + ") ON DELETE CASCADE,"
            + " PRIMARY KEY (" + OrderItem.ITEM_ID + ", " + OrderItem.ORDER_ID + "),"
            + " UNIQUE (" + OrderItem.ITEM_ID + "," + OrderItem.ORDER_ID  + ")"
            + ")";




    public static final String addColumns =
            " ALTER TABLE IF EXISTS " + OrderItem.TABLE_NAME
                    + "  ADD COLUMN IF NOT EXISTS  " + OrderItem.LIST_PRICE_AT_ORDER + "  float NOT NULL default 0 ";





    // instance variables
    private Integer itemID;
    private Integer orderID;
    private double itemQuantity;
    private double itemPriceAtOrder;
    private double listPriceAtOrder;


    private Item item;







    // getter and setter Methods

    public double getListPriceAtOrder() {
        return listPriceAtOrder;
    }

    public void setListPriceAtOrder(double listPriceAtOrder) {
        this.listPriceAtOrder = listPriceAtOrder;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPriceAtOrder() {
        return itemPriceAtOrder;
    }

    public void setItemPriceAtOrder(double itemPriceAtOrder) {
        this.itemPriceAtOrder = itemPriceAtOrder;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

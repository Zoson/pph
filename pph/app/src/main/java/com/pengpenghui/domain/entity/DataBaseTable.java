package com.pengpenghui.domain.entity;

/**
 * Created by zoson on 6/18/15.
 */
public class DataBaseTable {
    public static final String DATABASE_NAME = "database";

    public static String create_user_table = "create table " + UserDataTable.USER_TABLE_NAME + " ("
            + UserDataTable.ID + " varchar(30) primary key,"
            + UserDataTable.PASSWORD + " varchar(20) not null,"
            + UserDataTable.NICKNAME + " varchar(20) not null,"
            + UserDataTable.MONEY + " real not null,"
            + UserDataTable.IMG + " text not null)";

    public static String create_message_table = "create table " + BroMessageTable.BROMESSAGE_TABLE_NAME + " ("
            + BroMessageTable.DISID + " integer primary key autoincrement,"
            + UserDataTable.ID + " varchar(30) not null,"
            + BroMessageTable.NAME + " varchar(30) not null,"
            + BroMessageTable.DISMONEY + " real not null,"
            + BroMessageTable.STORENAME + " varchar(50) not null,"
            + BroMessageTable.ENDDATE + " varchar(20) not null,"
            + BroMessageTable.BEGINDATA + " varchar(20) not null,"
            + "foreign key("+UserDataTable.ID+") references " + UserDataTable.USER_TABLE_NAME + "("+UserDataTable.ID+")"
            +")";

    public static String create_wchatentry = "create table "+WchatEntry.WCHATENTRY_TABLE_NAME + "("
            + WchatEntry.ID+" varchar(32),"
            + WchatEntry.VERIFYCODE + " char(28),"
            + "foreign key("+WchatEntry.ID+")reference "+UserDataTable.USER_TABLE_NAME + "("+UserDataTable.ID+")"
            +")";

    public static String create_gift = "create table "+Gift.GIFT_TABLE_NAME+"("
            + Gift.ID + " varchar(32),"
            + Gift.MONEY + " integer,"
            + Gift.TIME + " date,"
            + "foreign key("+Gift.ID+") references "+UserDataTable.USER_TABLE_NAME+"("+UserDataTable.ID+")"
            +")";

    public static class UserDataTable{
        public static final String USER_TABLE_NAME = "user";
        public static final String ID = "id";
        public static final String PASSWORD = "password";
        public static final String NICKNAME = "nickname";
        public static final String MONEY = "money";
        public static final String IMG = "img";
        //public static final String FALG = "flag";

    }
    public static class BroMessageTable{
        public static final String BROMESSAGE_TABLE_NAME = "bromessage";
        public static final String STORENAME = "storename";
        public static final String DISID = "disid";
        public static final String BEGINDATA = "begindate";
        public static final String ENDDATE = "enddate";
        public static final String DISMONEY = "dismoney";
        public static final String NAME = "name";
    }
    public static class WchatEntry{
        public static final String WCHATENTRY_TABLE_NAME = "wchatentry";
        public static final String ID = "wc_id";
        public static final String VERIFYCODE = "verifycode";
    }
    public static class Gift{
        public static final String GIFT_TABLE_NAME = "gift";
        public static final String ID = "g_id";
        public static final String TIME = "time";
        public static final String MONEY = "money";
    }
}

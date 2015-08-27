package com.pengpenghui.pph_interface;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zoson on 6/18/15.
 */
public interface DatabaseOperatorInterface {
    public long insert(DataObjectInterface dataObjectInterface, String table_name);
    public int delete(String table_name, String[] attr, String[] value);
    public int deleteAll(String table_name);
    public int update(DataObjectInterface dataObjectInterface, String table_name, String[] attr, String[] value);
    public ArrayList<Map<String,String>> query(String table_name, String[] cols, String[] attr, String[] value);
}

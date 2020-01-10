package com.ToChess.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class RepositoryUtils {
    
    public static ResultSet executeQuery(PreparedStatement pSt, Object... objList) throws SQLException{
        for(int i = 0; i < objList.length; i++){
            pSt.setObject(i+1, objList[i]);
        }
        return pSt.executeQuery();
    }
    
    public static void setNullableInt(PreparedStatement pSt, int pos, Integer integer) throws SQLException{
        if(integer == null){
            pSt.setNull(pos, Types.INTEGER);
        }else{
            pSt.setInt(pos, integer);
        }
    }
    
}

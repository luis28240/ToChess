/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.repository;

import com.ToChess.exceptions.UsernameRepeatedException;
import com.ToChess.models.user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tarde
 */
@Repository
public class RepositoryUsers {

    @Autowired
    private DataSource dataSource;

    private ResultSet executeQuery(PreparedStatement pSt, Object... objList) throws SQLException{
        for(int i = 0; i < objList.length; i++){
            pSt.setObject(i+1, objList[i]);
        }
        return pSt.executeQuery();
    }
    
    public User getUserByEmail(String email){
        String sql 
                = "SELECT username,\n"
                + "       email,\n"
                + "       \"password\"\n"
                + "FROM   \"users\"\n"
                + "WHERE  UPPER(email) = UPPER(?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pSt = con.prepareStatement(sql);
             ResultSet rsUser = executeQuery(pSt, email)) {
            if(rsUser.next()){
                String username = rsUser.getString("username");
                User user = new User(username);
                user.setEmail(email);
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean userExists(String username, String password){
        
        String sql 
                = "SELECT null\n"
                + "FROM   \"users\"\n"
                + "WHERE  UPPER(username) = UPPER(?)\n"
                + "       AND \"password\" = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pSt = con.prepareStatement(sql);
             ResultSet rsUser = executeQuery(pSt, username, password)) {
            
            return rsUser.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public User getUser(String username) {
        String sql 
                = "SELECT username,\n"
                + "       email,\n"
                + "       \"password\"\n"
                + "FROM   \"users\"\n"
                + "WHERE  UPPER(username) = UPPER(?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pSt = con.prepareStatement(sql);
             ResultSet rsUser = executeQuery(pSt, username)) {
            if(rsUser.next()){
                String email = rsUser.getString("email");
                
                User user = new User(username);
                user.setEmail(email);
                user.setLoggedUser(true);
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertarUsuario(User user) throws UsernameRepeatedException {

        String sql = "INSERT INTO \"users\" \n"
                + "    (id_user,\n"
                + "     username,\n"
                + "     email,\n"
                + "     \"password\")\n"
                + "VALUES (seq_user.nextval,"
                + "        ?,"
                + "        ?,"
                + "        ?)";

        try (Connection con = dataSource.getConnection();
                PreparedStatement pSt = con.prepareStatement(sql);) {

            pSt.setString(1, user.getUsername());
            pSt.setString(2, user.getEmail());
            pSt.setString(3, user.getPassword());
            pSt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Codigo de error: " + ex.getSQLState());
            if (ex.getSQLState().equals("23000")) {
                throw new UsernameRepeatedException(user.getUsername());
            }
            Logger.getLogger(RepositoryUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

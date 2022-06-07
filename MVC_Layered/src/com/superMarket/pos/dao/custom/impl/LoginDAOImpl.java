package com.superMarket.pos.dao.custom.impl;

import com.superMarket.pos.dao.custom.LoginDAO;
import com.superMarket.pos.db.DBConnection;
import com.superMarket.pos.entity.Users;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public ArrayList<Users> getAllUsers(String accountType) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getDbConnection().getConnection().prepareStatement("SELECT * FROM Users WHERE AccountType='"+accountType+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Users> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new Users(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return users;
    }
}

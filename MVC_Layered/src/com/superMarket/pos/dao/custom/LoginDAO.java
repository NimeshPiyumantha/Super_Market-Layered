package com.superMarket.pos.dao.custom;

import com.superMarket.pos.dao.SuperDAO;
import com.superMarket.pos.entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginDAO extends SuperDAO {
    ArrayList<Users> getAllUsers(String accountType) throws SQLException, ClassNotFoundException;
}

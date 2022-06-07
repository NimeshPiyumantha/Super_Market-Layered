package com.superMarket.pos.bo.custom.impl;

import com.superMarket.pos.bo.custom.UserBO;
import com.superMarket.pos.dao.DAOFactory;
import com.superMarket.pos.dao.custom.LoginDAO;
import com.superMarket.pos.dto.UserDetailDTO;
import com.superMarket.pos.entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    private final LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGINDAO);

    @Override
    public ArrayList<UserDetailDTO> getAllUsers(String accountType) throws SQLException, ClassNotFoundException {
        ArrayList<Users> all = loginDAO.getAllUsers(accountType);
        ArrayList<UserDetailDTO> allOrder = new ArrayList<>();
        for (Users users : all) {
            allOrder.add(new UserDetailDTO(users.getUserName(), users.getPassword(), users.getAccountType()));
        }
        return allOrder;
    }
}

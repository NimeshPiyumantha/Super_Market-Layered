package com.superMarket.pos.bo.custom;

import com.superMarket.pos.bo.SuperBO;
import com.superMarket.pos.dto.UserDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    ArrayList<UserDetailDTO> getAllUsers(String accountType) throws SQLException, ClassNotFoundException;
}

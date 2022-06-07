package com.superMarket.pos.bo.custom.impl;

import com.superMarket.pos.bo.custom.CustomerBO;
import com.superMarket.pos.dao.DAOFactory;
import com.superMarket.pos.dao.custom.CustomerDAO;
import com.superMarket.pos.dto.CustomerDTO;
import com.superMarket.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);// hide the object creation logic through the factory

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCusId(), customer.getCusTitle(), customer.getCusName(), customer.getCusAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCustomerId(), dto.getCustomerTitle(), dto.getCustomerName(), dto.getCustomerAddress(), dto.getCustomerCity(), dto.getCustomerProvince(), dto.getCustomerPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustomerId(), dto.getCustomerTitle(), dto.getCustomerName(), dto.getCustomerAddress(), dto.getCustomerCity(), dto.getCustomerProvince(), dto.getCustomerPostalCode()));
    }

    @Override
    public boolean customerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

}

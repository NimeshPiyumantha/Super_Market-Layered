package com.superMarket.pos.bo.custom.impl;

import com.superMarket.pos.bo.custom.PurchaseOrderBO;
import com.superMarket.pos.dao.DAOFactory;
import com.superMarket.pos.dao.custom.*;
import com.superMarket.pos.db.DBConnection;
import com.superMarket.pos.dto.CustomerDTO;
import com.superMarket.pos.dto.ItemDTO;
import com.superMarket.pos.dto.OrderDTO;
import com.superMarket.pos.dto.OrderDetailDTO;
import com.superMarket.pos.entity.Customer;
import com.superMarket.pos.entity.Item;
import com.superMarket.pos.entity.OrderDetails;
import com.superMarket.pos.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    //Hiding the object creation logic using the Factory pattern
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);// hide the object creation logic through the factory
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        /*Transaction*/
        Connection connection = DBConnection.getDbConnection().getConnection();
        /*if order id already exist*/
        if (orderDAO.exist(dto.getOrderId())) {

        }
        connection.setAutoCommit(false);
        boolean save = orderDAO.save(new Orders(dto.getOrderId(), dto.getOrderDate(),dto.getCusId()));

        if (!save) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detailDTO : dto.getOrderDetails()) {
            boolean save1 = orderDetailsDAO.save(new OrderDetails(detailDTO.getOrderId(),detailDTO.getItemCode(),detailDTO.getDescription(),detailDTO.getUnitPrice(),detailDTO.getOrderQty(),detailDTO.getDiscount(),detailDTO.getTotal()));
            if (!save1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDTO item = searchItem(detailDTO.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detailDTO.getOrderQty());

            //update item
            boolean update = itemDAO.update(new Item(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand()));

            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer ent = customerDAO.search(id);
        return new CustomerDTO(ent.getCusId(), ent.getCusTitle(), ent.getCusName(),ent.getCusAddress(),ent.getCity(),ent.getProvince(),ent.getPostalCode());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item ent = itemDAO.search(code);
        return new ItemDTO(ent.getItemCode(), ent.getDescription(),ent.getPackSize(), ent.getUnitPrice(), ent.getQtyOnHand());
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

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
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
        }
        return allItems;
    }

}

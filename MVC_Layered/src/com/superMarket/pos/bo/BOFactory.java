package com.superMarket.pos.bo;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/

import com.superMarket.pos.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDERDETAILS:
                return new OrderDetailBOImpl();
            case User:
                return new UserBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl();
            case Custom:
                return new QueryBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, PURCHASE_ORDER, ORDER, ORDERDETAILS, User,Custom
    }

}

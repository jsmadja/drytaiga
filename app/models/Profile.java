package models;

import com.avaje.ebean.annotation.EnumValue;

public enum Profile {

    @EnumValue("administrator")
    Administrator,

    @EnumValue("customer")
    Customer;

}

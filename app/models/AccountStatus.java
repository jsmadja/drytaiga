package models;

import com.avaje.ebean.annotation.EnumValue;

public enum AccountStatus {

    @EnumValue("active")
    Active,

    @EnumValue("suspended")
    Suspended;

}

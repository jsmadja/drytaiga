package models;

import com.avaje.ebean.annotation.EnumValue;

public enum AccountType {

    @EnumValue("free")
    Free,

    @EnumValue("startup")
    Startup,

    @EnumValue("established_company")
    EstablishedCompany,

    @EnumValue("large_company")
    LargeCompany
}

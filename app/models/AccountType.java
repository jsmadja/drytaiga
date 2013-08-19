package models;

import com.avaje.ebean.annotation.EnumValue;

import static misc.FileSize.Unity.GIGA;
import static misc.FileSize.Unity.MEGA;

public enum AccountType {

    @EnumValue("free")
    Free(MEGA.toBytes(250), 0),

    @EnumValue("startup")
    Startup(GIGA.toBytes(2), 24.99),

    @EnumValue("established_company")
    EstablishedCompany(GIGA.toBytes(25), 34.99),

    @EnumValue("large_company")
    LargeCompany(GIGA.toBytes(200), 69.99);

    private final long totalSpace;

    private final double price;

    private AccountType(long totalSpace, double price) {
        this.totalSpace = totalSpace;
        this.price = price;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public double getPrice() {
        return price;
    }
}

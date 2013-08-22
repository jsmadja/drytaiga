package models;

import com.avaje.ebean.annotation.EnumValue;

import static java.lang.Integer.MAX_VALUE;
import static misc.Unity.GIGA;
import static misc.Unity.MEGA;

public enum AccountType {

    @EnumValue("free")
    Free(0, 1, 1, 100, MEGA.toBytes(250)),

    @EnumValue("startup")
    Startup(24.99, 5, 10, 1000, GIGA.toBytes(2)),

    @EnumValue("established_company")
    EstablishedCompany(34.99, 25, 25, 5000, GIGA.toBytes(25)),

    @EnumValue("large_company")
    LargeCompany(69.99, MAX_VALUE, MAX_VALUE, MAX_VALUE, GIGA.toBytes(200));

    private final long totalSpace;

    private final double price;

    private int maxUsers;
    private int maxOpenings;
    private int maxApplicants;

    private AccountType(double price, int maxUsers, int maxOpenings, int maxApplicants, long totalSpace) {
        this.totalSpace = totalSpace;
        this.price = price;
        this.maxUsers = maxUsers;
        this.maxOpenings = maxOpenings;
        this.maxApplicants = maxApplicants;
    }

    public Long getTotalSpace() {
        return totalSpace;
    }

    public double getPrice() {
        return price;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public Integer getMaxOpenings() {
        return maxOpenings;
    }

    public Integer getMaxApplicants() {
        return maxApplicants;
    }
}

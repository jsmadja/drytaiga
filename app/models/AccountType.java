package models;

import com.avaje.ebean.annotation.EnumValue;

import static misc.FileSize.Unity.GIGA;
import static misc.FileSize.Unity.MEGA;

public enum AccountType {

    @EnumValue("free")
    Free(MEGA.toBytes(250)),

    @EnumValue("startup")
    Startup(GIGA.toBytes(2)),

    @EnumValue("established_company")
    EstablishedCompany(GIGA.toBytes(25)),

    @EnumValue("large_company")
    LargeCompany(GIGA.toBytes(200));

    private final long totalSpace;

    private AccountType(long totalSpace) {
        this.totalSpace = totalSpace;
    }

    public long getTotalSpace() {
        return totalSpace;
    }
}

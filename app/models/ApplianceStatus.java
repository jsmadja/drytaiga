package models;

import com.avaje.ebean.annotation.EnumValue;

public enum ApplianceStatus {

    @EnumValue("to_contact")
    ToContact,

    @EnumValue("contacted")
    Contacted,

    @EnumValue("rejected_after_phone_interview")
    RejectedAfterPhoneInterview,

    @EnumValue("schedule_tech_interview")
    ScheduleTechInterview,

    @EnumValue("rejected_after_tech_interview")
    RejectedAfterTechInterview,

    @EnumValue("schedule_boss_interview")
    ScheduleBossInterview,

    @EnumValue("rejected_after_boss_interview")
    RejectedAfterBossInterview,

    @EnumValue("refused_our_offer")
    RefusedOurOffer,

    @EnumValue("hired")
    Hired;

}

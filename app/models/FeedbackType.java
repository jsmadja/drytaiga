package models;

import com.avaje.ebean.annotation.EnumValue;

public enum FeedbackType {

    @EnumValue("idea")
    Idea,

    @EnumValue("remark")
    Remark,

    @EnumValue("bug")
    Bug,

    @EnumValue("support")
    Support;

}

package com.example.bubu.aggregate.member;

public enum AccountStatus {
    ACTIVE, DEACTIVE;

    public static AccountStatus getDefaultStatus(){
        return ACTIVE;
    }
}

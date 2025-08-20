package com.example.bubu.aggregate;

public enum AccountStatus {
    ACTIVE, DEACTIVE;

    public static AccountStatus getDefaultStatus(){
        return ACTIVE;
    }
}

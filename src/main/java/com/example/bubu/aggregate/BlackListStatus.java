package com.example.bubu.aggregate;

public enum BlackListStatus {
    ACTIVE, DEACTIVE;

    public static BlackListStatus getDefaultBlackListStatus(){
        return DEACTIVE;
    }
}

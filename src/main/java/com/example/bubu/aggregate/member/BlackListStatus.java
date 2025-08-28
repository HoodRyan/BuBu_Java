package com.example.bubu.aggregate.member;

public enum BlackListStatus {
    ACTIVE, DEACTIVE;

    public static BlackListStatus getDefaultBlackListStatus(){
        return DEACTIVE;
    }
}

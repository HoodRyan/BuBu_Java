package com.example.bubu.repository;

import com.example.bubu.aggregate.Member;

import java.io.File;
import java.util.ArrayList;

public class MemberRepository {
    
    private final ArrayList<Member> members = new ArrayList<>();
    private final File file =
            new File("C:\\miniproject\\BucketBuddy\\src\\main\\java\\com\\example\\bubu\\db\\memberDB.dat");

    public MemberRepository() {
        
    }


    public void registMember(Member registMember) {
    }
}

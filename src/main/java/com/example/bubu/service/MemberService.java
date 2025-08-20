package com.example.bubu.service;

import com.example.bubu.aggregate.Member;
import com.example.bubu.repository.MemberRepository;

public class MemberService {

    private MemberRepository memberRepository;

    public MemberService() {
        memberRepository = new MemberRepository();
    }

    public MemberService(MemberRepository memberRepository) {

    }

    public void registMember(Member registMember) {

        memberRepository.registMember(registMember);
    }
}

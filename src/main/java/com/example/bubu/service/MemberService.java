package com.example.bubu.service;

import com.example.bubu.aggregate.AccountStatus;
import com.example.bubu.aggregate.BlackListStatus;
import com.example.bubu.aggregate.Member;
import com.example.bubu.repository.MemberRepository;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService() {
        memberRepository = new MemberRepository();
    }


    public void registMember(Member registMember) {

        /* 설명. 회원 번호 생성 */
        int lastMemberNo = memberRepository.findLastMemberNo();
        registMember.setMemberNo(lastMemberNo + 1);

        /* 설명. 회원가입 시 회원활성화 상태 Active, 블랙리스트 여부 DEACTIVE */
        registMember.setAccountStatus(AccountStatus.getDefaultStatus());
        registMember.setBlackListStatus(BlackListStatus.getDefaultBlackListStatus());

        /* 설명. 회원가입 성공 여부-> int 값으로 반환 */
        int result = memberRepository.registMember(registMember);

        if (result == 1) {
            System.out.println(registMember.getName() + " 회원님 환영합니다.");
        }else{
            System.out.println("회원 가입 실패");
        }

    }

    public Member login(String id, String pwd) {
        /* 설명. id로 검색 */
        Member findMember = memberRepository.findById(id);

        /* 설명. 유효한 ID 일 때 */
        if(findMember!=null){
            /* 설명. 비밀번호가 일치할 때 */
            if(findMember.getPw().equals(pwd)){
                return findMember;
            }else{
                System.out.println("비밀번호가 틀립니다!");
                return null;
            }
        } else{
            System.out.println("없는 아이디 입니다!!!");
            return null;
        }

    }
}

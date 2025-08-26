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

    public boolean updateMemberInfo(String memId, String newPw, String newName, String newPhone, String[] newInterests) {

        try{
            Member currentMember = memberRepository.findById(memId);
            // 회원 정보 업데이트
            Member updatedMember = createUpdatedMember(currentMember, newPw, newName, newPhone, newInterests);

            // Repository를 통해 저장
            boolean saveSuccess = memberRepository.updateMember(memId,updatedMember);

            if (saveSuccess) {
                System.out.println("✅ Service: 회원 정보 수정이 완료되었습니다.");
                return true;
            } else {
                System.out.println("❌ Service: 정보 저장 중 오류가 발생했습니다.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("❌ Service: 정보 수정 중 오류 발생 - " + e.getMessage());
            return false;
        }
    }


    /**
     * 업데이트된 Member 객체 생성
     */
    private Member createUpdatedMember(Member original, String newPw, String newName,
                                       String newPhone, String[] newInterests) {
        // 원본 객체 복사
        Member updated = new Member(
                original.getMemberNo(),
                original.getId(),
                original.getPw(),
                original.getName(),
                original.getGender(),
                original.getPhone(),
                original.getInterests()
        );

        updated.setAccountStatus(original.getAccountStatus());
        updated.setBlackListStatus(original.getBlackListStatus());

        // 변경사항 적용
        if (newPw != null) {
            updated.setPw(newPw);
            System.out.println("비밀번호가 변경됩니다.");
        }

        if (newName != null) {
            updated.setName(newName);
            System.out.println("이름이 '" + newName + "'로 변경됩니다.");
        }

        if (newPhone != null) {
            updated.setPhone(newPhone);
            System.out.println("전화번호가 '" + newPhone + "'로 변경됩니다.");
        }

        if (newInterests != null) {
            updated.setInterests(newInterests);
            System.out.print("관심사가 변경됩니다 - ");
            for (int i = 0; i < newInterests.length; i++) {
                System.out.print(newInterests[i]);
                if (i < newInterests.length - 1) System.out.print(", ");
            }
            System.out.println();
        }

        return updated;
    }

    public boolean deactivateAccount(String memId) {
        try {
            // 1. 현재 회원 정보 조회
            Member currentMember = memberRepository.findById(memId);
            if (currentMember == null) {
                System.out.println("해당 회원을 찾을 수 없습니다.");
                return false;
            }

            // 2. 이미 탈퇴한 회원인지 확인
            if (currentMember.getAccountStatus() != AccountStatus.ACTIVE) {
                System.out.println("이미 비활성화된 계정입니다.");
                return false;
            }

            // 3. 계정 상태를 DEACTIVE 로 변경
            currentMember.setAccountStatus(AccountStatus.DEACTIVE);

            // 4. Repository를 통해 저장
            boolean saveSuccess = memberRepository.updateMember(memId, currentMember);

            if (saveSuccess) {
                System.out.println("계정 비활성화 완료");
                return true;
            } else {
                System.out.println("계정 비활성화 저장 실패");
                return false;
            }

        } catch (Exception e) {
            System.err.println("계정 비활성화 중 오류 발생 - " + e.getMessage());
            return false;
        }
    }
}

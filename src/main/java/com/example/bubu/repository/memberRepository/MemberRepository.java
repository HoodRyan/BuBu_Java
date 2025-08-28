package com.example.bubu.repository.memberRepository;

import com.example.bubu.aggregate.member.AccountStatus;
import com.example.bubu.aggregate.member.BlackListStatus;
import com.example.bubu.aggregate.member.Gender;
import com.example.bubu.aggregate.member.Member;
import com.example.bubu.stream.MyObjectOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    
    private final ArrayList<Member> memberList = new ArrayList<>();
    private final File file =
            new File("src/main/java/com/example/bubu/db/memberDB.dat");

    /* 설명. 프로그램 구동 시 처음 실행할 내용들(기본 멤버 넣기) */
    public MemberRepository() {
        if(!file.exists()) {
            ArrayList<Member> defaultMemberList = new ArrayList<>();
            defaultMemberList.add(new Member(1, "user01", "pass01", "홍길동",
                    Gender.MALE, "010-1234-5678", new String[]{"해외 여행", "전국일주", "스카이다이빙"},
                    AccountStatus.ACTIVE, BlackListStatus.DEACTIVE));
            defaultMemberList.add(new Member(2, "user02", "pass03", "이길동",
                    Gender.MALE, "010-3333-5555", new String[]{"해외 여행", "스카이다이빙"},
                    AccountStatus.ACTIVE, BlackListStatus.DEACTIVE));
            defaultMemberList.add(new Member(3, "user03", "pass03", "성길동",
                    Gender.MALE, "010-2221-5568", new String[]{"해외 여행"},
                    AccountStatus.ACTIVE, BlackListStatus.DEACTIVE));
            defaultMemberList.add(new Member(4, "user04", "pass04", "김길동",
                    Gender.MALE, "010-3333-6789", new String[]{"독서"},
                    AccountStatus.DEACTIVE, BlackListStatus.DEACTIVE));

            saveMembers(defaultMemberList);
        }
        loadMembers();
    }

    /* 설명. ArrayList<Member> 를 받으면 파일로 컬렉션에 담긴 회원들을 출력하는 메소드(feat. 덮어씌우는 기능) */
    private void saveMembers(ArrayList<Member> members) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

            for(Member member : members) {
                oos.writeObject(member);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadMembers() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
            while(true) {
                memberList.add((Member)ois.readObject());
            }
        } catch(EOFException e) {
            System.out.println("회원 정보 읽어오기 완료!!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public int registMember(Member registMember) {
        MyObjectOutput moo = null;
        int result = 0;
        try {
            moo = new MyObjectOutput(new BufferedOutputStream(new FileOutputStream(file, true)));
            moo.writeObject(registMember);
            moo.flush();        // 내부적으로 Buffered를 쓰고 있으니 객체 출력 강제화

            /* 설명. 컬렉션에 담긴 기존 회원을 지우고 다시 파일의 정보를 토대로 컬렉션이 회원으로 채워지도록 작성 */
            memberList.clear();
            loadMembers();

            result = 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(moo != null) moo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return result;
    }

    /* 설명. 회원번호 자동생성을 위하여 (회원수 -1 번의 번호 부여) */
    public int findLastMemberNo() {
        return memberList.get(memberList.size() - 1).getMemberNo();
    }

    /* 설명. 아이디로 멤버 검색 */
    public Member findById(String id) {
        for(Member member : memberList) {
            if(member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }

    public boolean updateMember(String memId, Member updatedMember) {
        System.out.println("💾 ID '" + memId + "'의 정보를 업데이트합니다.");

        if (!file.exists() || file.length() == 0) {
            System.out.println("❌ Repository: 회원 데이터 파일이 존재하지 않습니다.");
            return false;
        }

        // 1. 모든 회원 정보 읽기
        List<Member> allMembers = new ArrayList<>();
        boolean memberFound = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                try {
                    Member member = (Member) ois.readObject();

                    // 업데이트할 회원이면 새 정보로 교체 (ID로 비교!)
                    if (member.getId().equals(memId)) {
                        allMembers.add(updatedMember);
                        memberFound = true;
                        System.out.println("🔄 ID : '" + memId + "' 회원 정보를 찾아서 업데이트합니다.");
                    } else {
                        allMembers.add(member); // 다른 회원들은 그대로 유지
                    }

                } catch (EOFException e) {
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ 파일 읽기 오류 - " + e.getMessage());
            return false;
        }

        if (!memberFound) {
            System.out.println("❌ ID '" + memId + "'인 회원을 찾을 수 없습니다.");
            return false;
        }

        // 2. 모든 회원 정보를 새로 저장 (업데이트된 정보 포함)
        return saveAllMembers(allMembers);

    }

    private boolean saveAllMembers(List<Member> members) {
        System.out.println("💾 전체 회원 데이터를 저장합니다.");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (Member member : members) {
                oos.writeObject(member);
            }

            return true;

        } catch (IOException e) {
            System.err.println("❌ Repository: 파일 저장 오류 - " + e.getMessage());
            return false;
        }
    }
}

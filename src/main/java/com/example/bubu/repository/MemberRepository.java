package com.example.bubu.repository;

import com.example.bubu.aggregate.AccountStatus;
import com.example.bubu.aggregate.BlackListStatus;
import com.example.bubu.aggregate.Gender;
import com.example.bubu.aggregate.Member;
import com.example.bubu.stream.MyObjectOutput;

import java.io.*;
import java.util.ArrayList;

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
}

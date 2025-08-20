package com.example.bubu.aggregate;

import java.io.Serializable;
import java.util.Arrays;

public class Member implements Serializable {
    private int memberNo;     // 회원 번호
    private String id;      // id
    private String pw;      // pw
    private String name;    // 이름
    private Gender gender;  // 성별
    private String phone;   // 전화번호
    private String[] interests; // 개인 성향/취향
    private AccountStatus accountStatus;    // 탈퇴여부
    private BlackListStatus blackListStatus;    // 블랙리스트 여부

    public Member() {
    }

    /* 설명. 회원가입용 생성자 */
    public Member(String id, String pw, String name, Gender gender, String phone, String[] interests) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.interests = interests;
    }

    public Member(int memberNo, String id, String pw, String name, Gender gender, String phone, String[] interests, AccountStatus accountStatus, BlackListStatus blackListStatus) {
        this.memberNo = memberNo;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.interests = interests;
        this.accountStatus = accountStatus;
        this.blackListStatus = blackListStatus;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public BlackListStatus getBlackListStatus() {
        return blackListStatus;
    }

    public void setBlackListStatus(BlackListStatus blackListStatus) {
        this.blackListStatus = blackListStatus;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", interests=" + Arrays.toString(interests) +
                ", accountStatus=" + accountStatus +
                ", blackListStatus=" + blackListStatus +
                '}';
    }

    public String getMemberName() {

        return getMemberName();
    }
}

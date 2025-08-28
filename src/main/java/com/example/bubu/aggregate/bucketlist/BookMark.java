package com.example.bubu.aggregate.bucketlist;

import java.io.Serializable;

public class BookMark implements Serializable {
    private int bookmarkNo;         // 북마크 번호
    private String bucketNo;        // 버킷리스트 번호
    private int memberNo;           // 회원 번호

    public BookMark() {
    }

    public BookMark(int bookmarkNo, String bucketNo, int memberNo) {
        this.bookmarkNo = bookmarkNo;
        this.bucketNo = bucketNo;
        this.memberNo = memberNo;
    }

    public int getBookmarkNo() {
        return bookmarkNo;
    }

    public void setBookmarkNo(int bookmarkNo) {
        this.bookmarkNo = bookmarkNo;
    }

    public String getBucketNo() {
        return bucketNo;
    }

    public void setBucketNo(String bucketNo) {
        this.bucketNo = bucketNo;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    @Override
    public String toString() {
        return "BookMark{" +
                "bookmarkNo=" + bookmarkNo +
                ", bucketNo='" + bucketNo + '\'' +
                ", memberNo=" + memberNo +
                '}';
    }
}

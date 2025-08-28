package com.example.bubu.aggregate.bucketlist;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BucketListComment implements Serializable {
    private int commentNo;              // 댓글 번호
    private String comment;             // 댓글 내용
    private LocalDateTime commentDate;  // 작성 일자
    private int bucketNo;               // 버킷리스트 번호
    private int memberNo;               // 회원 번호

    public BucketListComment() {
    }

    public BucketListComment(int commentNo, String comment, LocalDateTime createdDate, int bucketNo, int memberNo) {
        this.commentNo = commentNo;
        this.comment = comment;
        this.commentDate = createdDate;
        this.bucketNo = bucketNo;
        this.memberNo = memberNo;
    }

    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public int getBucketNo() {
        return bucketNo;
    }

    public void setBucketNo(int bucketNo) {
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
        return "BucketListComment{" +
                "commentNo=" + commentNo +
                ", comment='" + comment + '\'' +
                ", createdDate=" + commentDate +
                ", bucketNo=" + bucketNo +
                ", memberNo=" + memberNo +
                '}';
    }
}

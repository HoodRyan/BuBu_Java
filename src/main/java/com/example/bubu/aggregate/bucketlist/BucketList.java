package com.example.bubu.aggregate.bucketlist;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BucketList implements Serializable {
    private int bucketNo;               // 버킷리스트 번호
    private String bucketListTitle;     // 버킷리스트 제목
    private String bucketListContents;  // 버킷리스트 내용
    private LocalDateTime createdDate;  // 작성 일자
    private int bucketViews;            // 조회수
    private int milestoneRate;          // 마일스톤 달성률
    private int bucketRec;              // 추천수
    private int memberNo;               // 작성자 번호

    public BucketList() {
    }

    public BucketList(int bucketNo, String bucketListTitle, String bucketListContents, LocalDateTime createdDate, int bucketViews, int milestoneRate, int bucketRec, int memberNo) {
        this.bucketNo = bucketNo;
        this.bucketListTitle = bucketListTitle;
        this.bucketListContents = bucketListContents;
        this.createdDate = createdDate;
        this.bucketViews = bucketViews;
        this.milestoneRate = milestoneRate;
        this.bucketRec = bucketRec;
        this.memberNo = memberNo;
    }

    public int getBucketNo() {
        return bucketNo;
    }

    public void setBucketNo(int bucketNo) {
        this.bucketNo = bucketNo;
    }

    public String getBucketListTitle() {
        return bucketListTitle;
    }

    public void setBucketListTitle(String bucketListTitle) {
        this.bucketListTitle = bucketListTitle;
    }

    public String getBucketListContents() {
        return bucketListContents;
    }

    public void setBucketListContents(String bucketListContents) {
        this.bucketListContents = bucketListContents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getBucketViews() {
        return bucketViews;
    }

    public void setBucketViews(int bucketViews) {
        this.bucketViews = bucketViews;
    }

    public int getMilestoneRate() {
        return milestoneRate;
    }

    public void setMilestoneRate(int milestoneRate) {
        this.milestoneRate = milestoneRate;
    }

    public int getBucketRec() {
        return bucketRec;
    }

    public void setBucketRec(int bucketRec) {
        this.bucketRec = bucketRec;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    @Override
    public String toString() {
        return "BucketList{" +
                "bucketNo=" + bucketNo +
                ", title='" + bucketListTitle + '\'' +
                ", contents='" + bucketListContents + '\'' +
                ", createdDate=" + createdDate +
                ", views=" + bucketViews +
                ", milestoneRate=" + milestoneRate +
                ", recommend=" + bucketRec +
                ", memberNo=" + memberNo +
                '}';
    }
}

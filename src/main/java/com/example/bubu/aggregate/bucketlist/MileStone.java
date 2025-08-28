package com.example.bubu.aggregate.bucketlist;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MileStone implements Serializable {
    private int milestoneNo;                // 마일스톤 번호
    private String mileStoneTitle;          // 마일스톤 제목
    private String mileStoneContents;       // 마일스톤 내용
    private LocalDateTime mileStoneDate;    // 마일스톤 시작 일자
    private LocalDateTime mileStoneEndDate; // 마일스톤 종료 일자
    private int mileStoneRate;              // 마일스톤 진행률
    private int bucketNo;                   // 버킷리스트 번호

    public MileStone() {

    }

    public MileStone(int milestoneNo, String mileStoneTitle, String mileStoneContents, LocalDateTime mileStoneDate, LocalDateTime mileStoneEndDate, int mileStoneRate, int bucketNo) {
        this.milestoneNo = milestoneNo;
        this.mileStoneTitle = mileStoneTitle;
        this.mileStoneContents = mileStoneContents;
        this.mileStoneDate = mileStoneDate;
        this.mileStoneEndDate = mileStoneEndDate;
        this.mileStoneRate = mileStoneRate;
        this.bucketNo = bucketNo;
    }

    public int getMilestoneNo() {
        return milestoneNo;
    }

    public void setMilestoneNo(int milestoneNo) {
        this.milestoneNo = milestoneNo;
    }

    public String getMileStoneTitle() {
        return mileStoneTitle;
    }

    public void setMileStoneTitle(String mileStoneTitle) {
        this.mileStoneTitle = mileStoneTitle;
    }

    public String getMileStoneContents() {
        return mileStoneContents;
    }

    public void setMileStoneContents(String mileStoneContents) {
        this.mileStoneContents = mileStoneContents;
    }

    public LocalDateTime getMileStoneDate() {
        return mileStoneDate;
    }

    public void setMileStoneDate(LocalDateTime mileStoneDate) {
        this.mileStoneDate = mileStoneDate;
    }

    public LocalDateTime getMileStoneEndDate() {
        return mileStoneEndDate;
    }

    public void setMileStoneEndDate(LocalDateTime mileStoneEndDate) {
        this.mileStoneEndDate = mileStoneEndDate;
    }

    public int getMileStoneRate() {
        return mileStoneRate;
    }

    public void setMileStoneRate(int mileStoneRate) {
        this.mileStoneRate = mileStoneRate;
    }

    public int getBucketNo() {
        return bucketNo;
    }

    public void setBucketNo(int bucketNo) {
        this.bucketNo = bucketNo;
    }

    @Override
    public String toString() {
        return "MileStone{" +
                "milestoneNo=" + milestoneNo +
                ", mileStoneTitle='" + mileStoneTitle + '\'' +
                ", mileStoneContents='" + mileStoneContents + '\'' +
                ", mileStoneDate=" + mileStoneDate +
                ", mileStoneEndDate=" + mileStoneEndDate +
                ", mileStoneRate=" + mileStoneRate +
                ", bucketNo=" + bucketNo +
                '}';
    }
}

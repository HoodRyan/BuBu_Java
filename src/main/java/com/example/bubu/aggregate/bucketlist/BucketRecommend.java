package com.example.bubu.aggregate.bucketlist;

import java.io.Serializable;

public class BucketRecommend implements Serializable {
    private int recommendNo;
    private int bucketNo;
    private int memberNo;

    public BucketRecommend() {

    }

    public BucketRecommend(int recommendNo, int bucketNo, int memberNo) {
        this.recommendNo = recommendNo;
        this.bucketNo = bucketNo;
        this.memberNo = memberNo;
    }

    public int getRecommendNo() {
        return recommendNo;
    }

    public void setRecommendNo(int recommendNo) {
        this.recommendNo = recommendNo;
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
        return "BucketRecommend{" +
                "recommendNo=" + recommendNo +
                ", bucketNo=" + bucketNo +
                ", memberNo=" + memberNo +
                '}';
    }
}

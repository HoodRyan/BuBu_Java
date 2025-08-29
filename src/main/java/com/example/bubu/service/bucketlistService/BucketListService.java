package com.example.bubu.service.bucketlistService;

import com.example.bubu.aggregate.bucketlist.BucketList;
import com.example.bubu.repository.bucketlistRepository.BucketListRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BucketListService {
    private final BucketListRepository bucketListRepository;


    public BucketListService() {
        bucketListRepository = new BucketListRepository();
    }

    public boolean createBucketList(BucketList bucketList) {
        System.out.println("🎯 버킷리스트를 생성합니다.");

        try {
            // 1. 입력값 유효성 검사
            if (!validateBucketListInput(bucketList)) {
                return false;
            }

            // 2. 기본값 설정 (Service에서 처리)
            setupDefaultValues(bucketList);

            // 3. Repository를 통해 저장
            boolean saveSuccess = bucketListRepository.createBucketList(bucketList);

            if (saveSuccess) {
                System.out.println("✅ 버킷리스트 생성 완료 - " + bucketList.getBucketListTitle());
                return true;
            } else {
                System.out.println("❌ 버킷리스트 저장 실패");
                return false;
            }

        } catch (Exception e) {
            System.err.println("❌ 버킷리스트 생성 중 오류 발생 - " + e.getMessage());
            return false;
        }

    }

    private void setupDefaultValues(BucketList bucketList) {
        // 버킷 번호 자동 생성
        int nextBucketNo = bucketListRepository.findLastBucketNo();
        bucketList.setBucketNo(nextBucketNo);

        // 현재 시간으로 작성일 설정
        bucketList.setCreatedDate(LocalDateTime.now());

        // 기본값들 설정
        bucketList.setBucketViews(0);           // 조회수 0
        bucketList.setMilestoneRate(0);         // 달성률 0%
        bucketList.setBucketRec(0);             // 추천수 0

    }

    private boolean validateBucketListInput(BucketList bucketList) {
        // 제목 검증
        if (bucketList.getBucketListTitle() == null || bucketList.getBucketListTitle().trim().isEmpty()) {
            System.out.println("❌ 제목은 필수 입력 항목입니다.");
            return false;
        }

        if (bucketList.getBucketListTitle().length() > 100) {
            System.out.println("❌ 제목은 100자 이내로 입력해주세요.");
            return false;
        }

        // 내용 검증
        if (bucketList.getBucketListContents() == null || bucketList.getBucketListContents().trim().isEmpty()) {
            System.out.println("❌ 내용은 필수 입력 항목입니다.");
            return false;
        }

        if (bucketList.getBucketListContents().length() > 1000) {
            System.out.println("❌ 내용은 1000자 이내로 입력해주세요.");
            return false;
        }

        // 작성자 번호 검증
        if (bucketList.getMemberNo() <= 0) {
            System.out.println("❌ 올바르지 않은 작성자 정보입니다.");
            return false;
        }

        return true;
    }

    public List<BucketList> getMyBucketLists(int memberNo) {
        System.out.println("회원번호 " + memberNo + "의 버킷리스트를 조회합니다.");

        try {
            List<BucketList> allBucketLists = bucketListRepository.findAllMyBucketList();
            List<BucketList> myBucketLists = new ArrayList<>();

            // 해당 회원의 버킷리스트만 필터링
            for (BucketList bucket : allBucketLists) {
                if (bucket.getMemberNo() == memberNo) {
                    myBucketLists.add(bucket);
                }
            }

            // 작성일 기준으로 최신순 정렬 (최근 작성한 것부터)
            myBucketLists.sort((b1, b2) ->
                    b2.getCreatedDate().compareTo(b1.getCreatedDate()));

            System.out.println(myBucketLists.size() + "개의 버킷리스트를 찾았습니다.");
            return myBucketLists;

        } catch (Exception e) {
            System.err.println("버킷리스트 조회 중 오류 발생 - " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public BucketList getBucketListDetail(int selectedBucketNo) {
        System.out.println("버킷리스트 " + selectedBucketNo + " 상세 조회");

        try {
            // 1. 버킷리스트 조회
            BucketList bucket = bucketListRepository.findByBucketNo(selectedBucketNo);

            if (bucket == null) {
                System.out.println("Service: 해당 버킷리스트를 찾을 수 없습니다.");
                return null;
            }

            // 2. 조회수 증가
            bucket.setBucketViews(bucket.getBucketViews() + 1);

            // 3. 업데이트된 조회수를 파일에 저장
            boolean updateSuccess = bucketListRepository.updateBucketList(selectedBucketNo, bucket);

            if (updateSuccess) {
                System.out.println("Service: 조회수가 " + bucket.getBucketViews() + "로 증가했습니다.");
            }

            return bucket;

        } catch (Exception e) {
            System.err.println("Service: 상세 조회 중 오류 발생 - " + e.getMessage());
            return null;
        }
    }
}

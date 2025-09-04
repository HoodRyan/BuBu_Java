package com.example.bubu.repository.bucketlistRepository;

import com.example.bubu.aggregate.bucketlist.BucketList;
import com.example.bubu.aggregate.member.AccountStatus;
import com.example.bubu.aggregate.member.BlackListStatus;
import com.example.bubu.aggregate.member.Gender;
import com.example.bubu.aggregate.member.Member;
import com.example.bubu.stream.MyObjectOutput;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BucketListRepository {
    private final ArrayList<BucketList> bucketList = new ArrayList<>();
    private final File file = new File("src/main/java/com/example/bubu/db/bucketListDB.dat");

    /* 설명. 프로그램 구동 시 처음 실행할 내용들(기본 버킷리스트 넣기)*/
    public BucketListRepository() {
        if(!file.exists()) {
            ArrayList<BucketList> defaultBucketList = new ArrayList<>();
            defaultBucketList.add(
                    new BucketList(1,
                            "동아시아 여행가기",
                            "평소에 가 보고 싶었던 동아시아의 국가들 방문하기",
                            LocalDateTime.of(2024, 1, 25, 16, 20),
                            0,
                            0,
                            0,
                            123
                    )
            );
            defaultBucketList.add(
                    new BucketList(2,
                            "수영 배우기",
                            "자유형,평형,배형 배워서 올 여름에는 꼭 수영을 해보자~!",
                            LocalDateTime.of(2024, 3, 12, 5, 10),
                            10,
                            0,
                            100,
                            124
                    )
            );
            defaultBucketList.add(
                    new BucketList(3,
                            "요리 배우기",
                            "자취요리의 신이 되는 그날까지...",
                            LocalDateTime.of(2025, 2, 22, 1, 50),
                            135,
                            30,
                            56,
                            125
                    )
            );
            defaultBucketList.add(
                    new BucketList(4,
                            "코딩 배우기",
                            "태어나길 문과생으로...이제는 코딩을 배울 시간이다...",
                            LocalDateTime.of(2025, 12, 30, 17, 23),
                            33,
                            0,
                            8,
                            126
                    )
            );
            saveBucketList(defaultBucketList);
        }
        loadBucketList();
    }

    /* 설명. ArrayList<BucketList> 를 받으면 파일로 컬렉션에 담긴 버킷리스트들을 출력하는 메소드(feat. 덮어씌우는 기능) */
    private void saveBucketList(ArrayList<BucketList> bucketLists) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

            for(BucketList bucketList : bucketLists) {
                oos.writeObject(bucketList);
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



    private void loadBucketList() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
            while(true) {
                bucketList.add((BucketList) ois.readObject());
            }
        } catch(EOFException e) {
            System.out.println("버킷리스트 정보 읽어오기 완료!!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    /* 설명. 버킷리스트 번호 자동생성 (버킷번호 -1 번의 번호 부여) */
    public int findLastBucketNo() {
        if(bucketList.isEmpty()) {
            return 1;
        }
        return bucketList.get(bucketList.size() - 1).getBucketNo() + 1;
    }

    public boolean createBucketList(BucketList bucketLists) {
        MyObjectOutput moo = null;
        boolean result = false;
        try {
            moo = new MyObjectOutput(new BufferedOutputStream(new FileOutputStream(file, true)));
            moo.writeObject(bucketLists);
            moo.flush();        // 내부적으로 Buffered를 쓰고 있으니 객체 출력 강제화

            /* 설명. 컬렉션에 담긴 기존 회원을 지우고 다시 파일의 정보를 토대로 컬렉션이 회원으로 채워지도록 작성 */
            bucketList.clear();
            loadBucketList();

            result = true;
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

    public BucketList findByBucketNo(int selectedBucketNo) {
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                try {
                    BucketList bucket = (BucketList) ois.readObject();
                    if (bucket.getBucketNo() == selectedBucketNo) {
                        return bucket;
                    }
                } catch (EOFException e) {
                    break;
                }
            }

            System.out.println("버킷리스트 번호 " + selectedBucketNo + "를 찾을 수 없습니다.");
            return null;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("검색 중 오류 발생 - " + e.getMessage());
            return null;
        }
    }

    public boolean updateBucketList(int selectedBucketNo, BucketList updatedBucketList) {
        if (!file.exists() || file.length() == 0) {
            return false;
        }

        // 1. 모든 버킷리스트 읽기
        List<BucketList> allBuckets = new ArrayList<>();
        boolean bucketFound = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                try {
                    BucketList bucket = (BucketList) ois.readObject();

                    if (bucket.getBucketNo() == selectedBucketNo) {
                        allBuckets.add(updatedBucketList);
                        bucketFound = true;
                    } else {
                        allBuckets.add(bucket);
                    }
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("파일 읽기 오류 - " + e.getMessage());
            return false;
        }

        if (!bucketFound) {
            System.out.println("업데이트할 버킷리스트를 찾을 수 없습니다.");
            return false;
        }

        // 2. 모든 버킷리스트를 새로 저장
        return saveAll(allBuckets);
    }

    private boolean saveAll(List<BucketList> bucketLists) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (BucketList bucket : bucketLists) {
                oos.writeObject(bucket);
            }

            return true;

        } catch (IOException e) {
            System.err.println("파일 저장 오류 - " + e.getMessage());
            return false;
        }
    }

    public List<BucketList> findAllMyBucketList() {
        List<BucketList> bucketLists = new ArrayList<>();

        if (!file.exists() || file.length() == 0) {
            System.out.println("Repository: 버킷리스트 데이터 파일이 없습니다.");
            return bucketLists;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                try {
                    BucketList bucket = (BucketList) ois.readObject();
                    bucketLists.add(bucket);
                } catch (EOFException e) {
                    break;
                }
            }


        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Repository: 파일 읽기 오류 - " + e.getMessage());
        }

        return bucketLists;
    }

    public boolean deleteByBucketNo(int bucketNo) {
        List<BucketList> bucketList = findAllMyBucketList();

        // bucketNo에 해당하는 아이템 찾아서 삭제
        boolean removed = bucketList.removeIf(item -> item.getBucketNo() == bucketNo);

        if (removed) {
            saveAll(bucketList);
            return true;
        }
        return false;

    }
}

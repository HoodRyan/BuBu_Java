package com.example.bubu.run;

import com.example.bubu.aggregate.bucketlist.BucketList;
import com.example.bubu.aggregate.member.AccountStatus;
import com.example.bubu.aggregate.member.Gender;
import com.example.bubu.aggregate.member.Member;
import com.example.bubu.service.bucketlistService.BucketListService;
import com.example.bubu.service.memberService.MemberService;

import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {

    private MemberService memberService = new MemberService();
    private BucketListService bucketService = new BucketListService();
    private Member currentMember = null;   //현재 로그인한 사용자
    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Application app = new Application();
        app.runApplication();
    }

    /* 설명. 어플 실행 처리 */
    public void runApplication() {
        System.out.println("= BuBu에 접속하신걸 환영합니다 =");

        while (true) {
            showMainMenu();
            int input = getInput();
            handleInput(input);
        }
    }

    /* 설명. 입력 번호 처리 */
    private int getInput() {
        try {
            int input = sc.nextInt();
            sc.nextLine();  //개행 제거
            return input;
        } catch (Exception e) {
            sc.nextLine();  //잘못된 입력 제거
            System.out.println("올바른 번호를 입력해주세요.");
            return -1;
        }
    }

    /* 설명. 메인 메뉴에서 로그인 여부 확인 */
    private void handleInput(int input) {
        if (currentMember == null) {
            // 비로그인 시 메뉴 처리
            handleGuestMenu(input);
        } else {
            // 로그인 상태 메뉴 처리
            handleMemberMenu(input);
        }
    }

    /* 설명. 로그인 상태의 메뉴 처리 */
    private void handleMemberMenu(int input) {
        switch (input) {
            case 1:
                showMyInfoMenu();
                break;
            case 2:
                bucketListMenu();
                break;
            case 3:
                // 나중에 구현할 게시판 기능
                System.out.println("게시판 기능은 추후 구현 예정입니다.");
                break;
            case 4:
                logout();
                break;
            case 5:
                exitProgram();
                break;
            default:
                System.out.println("올바른 번호를 선택해주세요.");
        }
    }

    /* 설명. 버킷리스트 관리 메뉴 */
    private void bucketListMenu() {
        while (true) {
            System.out.println("\n======= 버킷리스트 메뉴 =======");
            System.out.println("1. 버킷리스트 작성");
            System.out.println("2. 내 버킷리스트 보기");
            System.out.println("3. 버킷리스트 수정");
            System.out.println("4. 버킷리스트 삭제");
            System.out.println("5. 완료한 버킷리스트");
            System.out.println("6. 메인 메뉴로 돌아가기");
            System.out.println("=============================");
            System.out.print("번호 선택: ");

            int input = getInput();

            if (handleBucketListMenu(input)) {
                break; // 메인 메뉴로 돌아가기
            }
        }

    }

    /* 설명. 버킷리스트 관리 메뉴 처리*/
    private boolean handleBucketListMenu(int input) {
        switch (input) {
            case 1:
                bucketService.createBucketList(inputBucketListInfo());
                break;
            case 2:
                showMyBucketLists();
                System.out.println("버킷리스트 조회 기능 구현 예정");
                break;
            case 3:
                // updateBucketList();
                System.out.println("버킷리스트 수정 기능 구현 예정");
                break;
            case 4:
                showDeleteBucketListMenu();
                break;
            case 5:
                // showCompletedBucketLists();
                System.out.println("완료된 버킷리스트 조회 기능 구현 예정");
                break;
            case 6:
                System.out.println("메인 메뉴로 돌아갑니다.");
                return true;
            default:
                System.out.println("올바른 번호를 선택해주세요.");
        }
        return false;
    }

    /* 설명. 삭제할 버킷리스트 확인 */
    private void showMyBucketListsToDelete() {
        System.out.println("=======================");
        System.out.println("내 버킷리스트 목록");
        System.out.println("=======================");

        // Service를 통해 내 버킷리스트 목록 조회
        List<BucketList> myBucketLists
                = bucketService.getMyBucketLists(currentMember.getMemberNo());

        if (myBucketLists.isEmpty()) {
            System.out.println("작성된 버킷리스트가 없습니다.");
            System.out.println("새로운 버킷리스트를 작성해보세요!");
            return;
        }

        // 버킷리스트 목록 출력
        System.out.printf("%-5s %-30s %-20s%n", "번호", "제목", "작성일");
        System.out.println("-".repeat(60));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (BucketList bucket : myBucketLists) {
            String title = bucket.getBucketListTitle();

            System.out.printf("%-5d %-30s %-20s%n",
                    bucket.getBucketNo(),
                    title,
                    bucket.getCreatedDate().format(formatter));
        }

        System.out.println("-".repeat(60));
    }

    /* 설명. 내 버킷리스트 목록 보기 */
    private void showMyBucketLists() {
        System.out.println("=".repeat(60));
        System.out.println("내 버킷리스트 목록");
        System.out.println("=".repeat(60));

        // Service를 통해 내 버킷리스트 목록 조회
        List<BucketList> myBucketLists
                = bucketService.getMyBucketLists(currentMember.getMemberNo());

        if (myBucketLists.isEmpty()) {
            System.out.println("작성된 버킷리스트가 없습니다.");
            System.out.println("새로운 버킷리스트를 작성해보세요!");
            return;
        }

        // 버킷리스트 목록 출력
        System.out.printf("%-5s %-30s %-20s%n", "번호", "제목", "작성일");
        System.out.println("-".repeat(60));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (BucketList bucket : myBucketLists) {
            String title = bucket.getBucketListTitle();

            System.out.printf("%-5d %-30s %-20s%n",
                    bucket.getBucketNo(),
                    title,
                    bucket.getCreatedDate().format(formatter));
        }

        System.out.println("-".repeat(60));
        System.out.println("총 " + myBucketLists.size() + "개의 버킷리스트");

        // 상세 보기 선택
        System.out.println("\n상세히 보고 싶은 버킷리스트 번호를 입력하세요.");
        System.out.println("(돌아가려면 0을 입력)");
        System.out.print("번호 입력: ");

        try {
            int selectedBucketNo = sc.nextInt();
            sc.nextLine(); // 개행 제거

            if (selectedBucketNo == 0) {
                return; // 버킷리스트 메뉴로 돌아가기
            }

            // 선택한 번호가 목록에 있는지 확인
            boolean found = false;
            for (BucketList bucket : myBucketLists) {
                if (bucket.getBucketNo() == selectedBucketNo) {
                    found = true;
                    break;
                }
            }

            if (found) {
                showBucketListDetail(selectedBucketNo);
            } else {
                System.out.println("잘못된 번호입니다. 목록에 있는 번호를 입력해주세요.");
            }

        } catch (Exception e) {
            sc.nextLine(); // 잘못된 입력 제거
            System.out.println("올바른 숫자를 입력해주세요.");
        }
    }

    /* 설명. 버킷리스트 상세 조회 */
    private void showBucketListDetail(int selectedBucketNo) {
        // Service를 통해 버킷리스트 상세 정보 조회 (조회수 증가 포함)
        List<BucketList> myBucketLists = bucketService.getMyBucketLists(currentMember.getMemberNo());
        BucketList bucket = bucketService.getBucketListDetail(selectedBucketNo);

        if (bucket == null) {
            System.out.println("버킷리스트를 찾을 수 없습니다.");
            return;
        }

        System.out.println("=".repeat(60));
        System.out.println("버킷리스트 상세 정보");
        System.out.println("=".repeat(60));

        System.out.println("번호: " + bucket.getBucketNo());
        System.out.println("제목: " + bucket.getBucketListTitle());
        System.out.println("작성자: " + currentMember.getName() + " (회원번호: " + bucket.getMemberNo() + ")");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        System.out.println("작성일: " + bucket.getCreatedDate().format(formatter));

        System.out.print("\n내용:");
        System.out.println(bucket.getBucketListContents());
        System.out.println("=".repeat(60));

        System.out.println("\n통계 정보:");
        System.out.println("조회수: " + bucket.getBucketViews());
        System.out.println("달성률: " + bucket.getMilestoneRate() + "%");
        System.out.println("추천수: " + bucket.getBucketRec());

        System.out.println("=".repeat(60));

        // 추가 작업 선택
        System.out.println("\n다음 작업을 선택하세요:");
        System.out.println("1. 목록으로 돌아가기");
        System.out.println("2. 수정하기");
        System.out.println("3. 삭제하기");
        System.out.print("선택: ");

        try {
            int choice = sc.nextInt();
            sc.nextLine(); // 개행 제거

            switch (choice) {
                case 1:
                    showMyBucketLists(); // 목록으로 돌아가기
                    break;
                case 2:
                    System.out.println("수정 기능은 추후 구현 예정입니다.");
                    break;
                case 3:
                    showDeleteBucketListMenu(); //삭제 메뉴
                    break;
                default:
                    System.out.println("목록으로 돌아갑니다.");
            }
        } catch (Exception e) {
            sc.nextLine(); // 잘못된 입력 제거
            System.out.println("목록으로 돌아갑니다.");
        }
    }

    private BucketList inputBucketListInfo() {
        System.out.println("=".repeat(60));
        System.out.println("📝 버킷리스트 작성");
        System.out.println("=".repeat(60));

        System.out.print("제목을 입력하세요: ");
        String title = sc.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("❌ 제목은 필수 입력 항목입니다.");
            return null;
        }

        System.out.print("내용을 입력하세요: ");
        String contents = sc.nextLine().trim();

        if (contents.isEmpty()) {
            System.out.println("❌ 내용은 필수 입력 항목입니다.");
            return null;
        }

        // 사용자가 입력한 제목과 내용만으로 BucketList 객체 생성
        BucketList bucketList = new BucketList();
        bucketList.setBucketListTitle(title);
        bucketList.setBucketListContents(contents);
        bucketList.setMemberNo(currentMember.getMemberNo()); // 현재 로그인한 사용자

        return bucketList;
    }

    public void showDeleteBucketListMenu() {
        System.out.println("=== 버킷 삭제 ===");
        showMyBucketListsToDelete();
        System.out.print("삭제할 버킷 번호를 입력하세요: ");

        try {
            int bucketNo = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

            System.out.print("정말로 삭제하시겠습니까? (y/n): ");
            String confirm = sc.nextLine().trim().toLowerCase();

            if (confirm.equals("y") || confirm.equals("yes")) {
                boolean success = bucketService.deleteBucketList(bucketNo);
                if (!success) {
                    System.out.println("삭제에 실패했습니다.");
                }
            } else {
                System.out.println("삭제가 취소되었습니다.");
            }

        } catch (InputMismatchException e) {
            System.out.println("올바른 숫자를 입력해주세요.");
            sc.nextLine(); // 잘못된 입력 버퍼 비우기
        }
    }

    /* 설명. 비로그인 상태의 메뉴 처리 */
    private void handleGuestMenu(int input) {
        switch (input) {
            case 1:
                login();
                break;
            case 2:
                memberService.registMember(signUp());
                break;
            case 3:
                exitProgram();
                break;
            default:
                System.out.println("올바른 번호를 선택해주세요.");
        }
    }

    /* 설명. 회원가입 기능 */
    private Member signUp() {
        Member member = null;

        System.out.print("아이디를 입력하세요: ");
        String id = sc.nextLine();

        System.out.print("비밀번호를 입력하세요: ");
        String pw = sc.nextLine();

        System.out.print("이름을 입력하세요: ");
        String name = sc.nextLine();

        System.out.println("1.남성 2.여성 3.기타 ");
        System.out.print("성별을 입력하세요: ");
        int gender = sc.nextInt();
        sc.nextLine();
        Gender genderEnum = null;
        switch (gender) {
            case 1:
                genderEnum = Gender.MALE;
                break;
            case 2:
                genderEnum = Gender.FEMALE;
                break;
            case 3:
                genderEnum = Gender.OTHER;
                break;
        }

        System.out.print("전화번호를 입력하세요: ");
        String phone = sc.nextLine();

        System.out.print("입력할 성향/취향의 갯수를 입력하세요: ");
        int length = sc.nextInt();
        sc.nextLine();
        String[] intersets = new String[length];
        for (int i = 0; i < intersets.length; i++) {
            System.out.print((i + 1) + "번째 성향을 입력하세요: ");
            intersets[i] = sc.nextLine();
        }

        member = new Member(id, pw, name, genderEnum, phone, intersets);

        return member;
    }


    /* 설명. 로그인 기능 */
    private void login() {
        System.out.print("아이디 입력: ");
        String id = sc.nextLine();
        System.out.print("비밀번호 입력: ");
        String pwd = sc.nextLine();

        Member loginResult = memberService.login(id, pwd);
        if (loginResult != null) {
            if (loginResult.getAccountStatus() != AccountStatus.ACTIVE) {
                System.out.println("❌ 계정이 비활성 상태입니다. 로그인할 수 없습니다.");
                System.out.println("📞 관리자에게 문의하세요.");
                return; // 로그인 실패
            }
            currentMember = loginResult;
            System.out.println("     환영합니다, " + currentMember.getName() + "님!");
            System.out.println("로그인에 성공했습니다!");

        } else {
            System.out.println("❌ 로그인에 실패했습니다.");
        }
    }

    /* 설명. 로그아웃 기능 */
    private void logout() {
        if (currentMember != null) {
            System.out.println("\n" + currentMember.getName() + "님이 로그아웃되었습니다.");
            currentMember = null;
            System.out.println("안전하게 로그아웃되었습니다.");
        } else {
            System.out.println("❌ 로그인 상태가 아닙니다.");
        }
        returnToMainMenu();
    }


    /* 설명. 내 정보 보기 메뉴 */
    private void showMyInfoMenu() {
        while (true) {
            System.out.println("\n======= 내 정보 메뉴 =======");
            System.out.println("1. 내 정보 조회");
            System.out.println("2. 내 정보 수정");
            System.out.println("3. 내가 작성한 게시글 보기 (추후 구현)");
            System.out.println("4. 내 버킷리스트 보기 (추후 구현)");
            System.out.println("5. 회원탈퇴");
            System.out.println("6. 메인 메뉴로 돌아가기");
            System.out.println("=".repeat(60));
            System.out.print("번호 선택: ");

            int input = getInput();

            if (handleMyInfoMenu(input)) {
                break; // 메인 메뉴로 돌아가기가 선택되면 반복문 종료
            }
        }
    }

    /* 설명. 내 정보 보기 메뉴 처리 */
    private boolean handleMyInfoMenu(int input) {
        switch (input) {
            case 1:
                showMyInfo();
                break;
            case 2:
                updateMyInfo();
                break;
            case 3:
                System.out.println("📋 게시글 관련 기능은 추후 업데이트 예정입니다.");
                // 게시판 관련 기능은 추후 업데이트 예정
                break;
            case 4:
                System.out.println("📝 버킷리스트 관련 기능은 추후 업데이트 예정입니다.");
                // 버킷리스트 관련 기능은 추후 업데이트 예정
                break;
            case 5:
                deactivateAccount();
                break;
            case 6:
                System.out.println("메인 메뉴로 돌아갑니다.");
                return true; // 메인 메뉴로 돌아가기
            default:
                System.out.println("올바른 번호를 선택해주세요.");
        }
        return false;   // 내 정보 보기 메뉴 유지
    }

    /* 설명. 내 정보 보기 기능 */
    private void showMyInfo() {
        System.out.println("=".repeat(60));
        System.out.println("👤 내 정보 조회");
        System.out.println("=".repeat(60));

        // currentMember 에서 바로 정보 출력
        System.out.println("📋 기본 정보");
        System.out.println("=".repeat(60));
        System.out.println("회원번호    : " + currentMember.getMemberNo());
        System.out.println("아이디      : " + currentMember.getId());
        System.out.println("이름        : " + currentMember.getName());
        System.out.println("성별        : " + currentMember.getGender());
        System.out.println("전화번호    : " + currentMember.getPhone());

        // 관심사
        System.out.println("\n🎯 관심사/취향");
        System.out.println("=".repeat(60));
        String[] interests = currentMember.getInterests();
        if (interests != null && interests.length > 0) {
            for (int i = 0; i < interests.length; i++) {
                System.out.println((i + 1) + ". " + interests[i]);
            }
        } else {
            System.out.println("등록된 관심사가 없습니다.");
        }

        System.out.println("\n📌 아무 키나 누르면 이전 화면으로 돌아갑니다.");
        sc.nextLine(); // 사용자 입력 대기
        System.out.println("💫 내 정보 메뉴로 돌아갑니다.");
        System.out.println("=".repeat(60));


    }

    /* 설명. 내 정보 수정 기능 */
    private void updateMyInfo() {
        System.out.println("=".repeat(60));
        System.out.println("   👤 내 정보 수정");
        System.out.println("=".repeat(60));

        try {
            // 현재 정보 표시 및 수정 입력 받기
            System.out.println("\n📝 기본 정보 수정");
            System.out.println("-".repeat(60));

            // 비밀번호 수정
            System.out.println("현재 비밀번호: " + currentMember.getPw());
            System.out.print("새 비밀번호 (변경하지 않으려면 엔터): ");
            String newPw = sc.nextLine().trim();

            // 이름 수정
            System.out.println("현재 이름: " + currentMember.getName());
            System.out.print("새 이름 (변경하지 않으려면 엔터): ");
            String newName = sc.nextLine().trim();

            // 전화번호 수정
            System.out.println("현재 전화번호: " + currentMember.getPhone());
            System.out.print("새 전화번호 (변경하지 않으려면 엔터): ");
            String newPhone = sc.nextLine().trim();

            // 관심사 수정
            System.out.println("\n🎯 관심사/취향 수정");
            System.out.println("-".repeat(60));
            System.out.print("현재 관심사: ");
            String[] currentInterests = currentMember.getInterests();
            if (currentInterests != null && currentInterests.length > 0) {
                for (int i = 0; i < currentInterests.length; i++) {
                    System.out.print(currentInterests[i]);
                    if (i < currentInterests.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            } else {
                System.out.println("등록된 관심사 없음");
            }


            System.out.print("관심사를 새로 설정하시겠습니까? (y/n): ");
            String updateInterests = sc.nextLine().trim().toLowerCase();

            String[] newInterests = null;
            if (updateInterests.equals("y") || updateInterests.equals("yes")) {
                System.out.print("새로운 관심사 개수를 입력하세요: ");
                int interestCount = sc.nextInt();
                sc.nextLine(); // 개행 제거

                newInterests = new String[interestCount];
                for (int i = 0; i < interestCount; i++) {
                    System.out.print((i + 1) + "번째 관심사: ");
                    newInterests[i] = sc.nextLine().trim();
                }
            }

            // ✅ 변경사항 체크
            boolean hasChanges = checkForChanges(newPw, newName, newPhone, newInterests);

            if (!hasChanges) {
                System.out.println("=".repeat(60));
                System.out.println("📋 변경 내용 확인");
                System.out.println("=".repeat(60));
                System.out.println("변경된 내용이 없습니다.");
                System.out.println("=".repeat(60));
                System.out.println("💫 내 정보 메뉴로 돌아갑니다.");
                return; // ← 여기서 메서드 종료! (메뉴로 복귀)
            }

            // 수정 내용 확인
            showUpdatePreview(newPw, newName, newPhone, newInterests);

            System.out.print("위 내용으로 수정하시겠습니까? (y/n): ");
            String confirm = sc.nextLine().trim().toLowerCase();

            if (confirm.equals("y") || confirm.equals("yes")) {
                // Service를 통해 정보 수정 요청
                boolean updateSuccess = memberService.updateMemberInfo(
                        currentMember.getId(),
                        newPw.isEmpty() ? null : newPw,
                        newName.isEmpty() ? null : newName,
                        newPhone.isEmpty() ? null : newPhone,
                        newInterests
                );

                if (updateSuccess) {
                    // currentMember 정보도 업데이트 (메모리 동기화)
                    updateCurrentMemberInfo(newPw, newName, newPhone, newInterests);

                    System.out.println("✅ 정보 수정이 완료되었습니다!");
                    System.out.print("변경된 정보를 확인하시겠습니까? (y/n): ");
                    String viewUpdate = sc.nextLine().trim().toLowerCase();

                    if (viewUpdate.equals("y") || viewUpdate.equals("yes")) {
                        showMyInfo();
                    }
                } else {
                    System.out.println("❌ 정보 수정에 실패했습니다.");
                    System.out.println("다시 시도해주세요.");
                }
            } else {
                System.out.println("수정이 취소되었습니다.");
            }

        } catch (Exception e) {
            System.out.println("❌ 입력 처리 중 오류가 발생했습니다: " + e.getMessage());
            System.out.println("다시 시도해주세요.");
        }
    }

    /* 설명. 내 정보 수정 여부 확인 */
    private boolean checkForChanges(String newPw, String newName, String newPhone, String[] newInterests) {
        boolean hasChanges = false;

        // 비밀번호 변경 여부
        if (!newPw.isEmpty()) {
            hasChanges = true;
        }

        // 이름 변경 여부
        if (!newName.isEmpty()) {
            hasChanges = true;
        }

        // 전화번호 변경 여부
        if (!newPhone.isEmpty()) {
            hasChanges = true;
        }

        // 관심사 변경 여부
        if (newInterests != null) {
            hasChanges = true;
        }

        return hasChanges;
    }

    /* 설명. 수정된 로그인 정보 */
    private void updateCurrentMemberInfo(String newPw, String newName, String newPhone, String[] newInterests) {
        if (!newPw.isEmpty()) {
            currentMember.setPw(newPw);
        }
        if (!newName.isEmpty()) {
            currentMember.setName(newName);
        }
        if (!newPhone.isEmpty()) {
            currentMember.setPhone(newPhone);
        }
        if (newInterests != null) {
            currentMember.setInterests(newInterests);
        }

        System.out.println("🔄 현재 로그인 정보가 업데이트되었습니다.");
    }

    /* 설명. 수정할 내 정보 확인 */
    private void showUpdatePreview(String newPw, String newName, String newPhone, String[] newInterests) {
        System.out.println("=".repeat(60));
        System.out.println("📋 수정 내용 확인");
        System.out.println("=".repeat(60));

        boolean hasChanges = false;

        if (!newPw.isEmpty()) {
            System.out.println("비밀번호: " + currentMember.getPw() + " → " + newPw);
            hasChanges = true;
        }

        if (!newName.isEmpty()) {
            System.out.println("이름: " + currentMember.getName() + " → " + newName);
            hasChanges = true;
        }

        if (!newPhone.isEmpty()) {
            System.out.println("전화번호: " + currentMember.getPhone() + " → " + newPhone);
            hasChanges = true;
        }

        if (newInterests != null) {
            System.out.print("관심사: ");
            for (int i = 0; i < newInterests.length; i++) {
                System.out.print(newInterests[i]);
                if (i < newInterests.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" [새로 설정]");
            hasChanges = true;
        }

        if (!hasChanges) {
            System.out.println("변경된 내용이 없습니다.");
        }

        System.out.println("=".repeat(60));
    }


    /* 설명. 회원탈퇴 기능 : 데이터는 삭제 X, 계정 활성화 여부 -> 비활성화 */
    private void deactivateAccount() {
        System.out.println("=".repeat(60));
        System.out.println("⚠️ 회원탈퇴");
        System.out.println("=".repeat(60));
        System.out.println("탈퇴 시 다음 사항을 확인해주세요.");
        System.out.println("=".repeat(60));
        System.out.println("• 계정이 비활성화됩니다.");
        System.out.println("• 작성한 게시글과 댓글은 유지됩니다.");
        System.out.println("• 비활성화된 계정으로는 로그인할 수 없습니다.");
        System.out.println("=".repeat(60));

        // 1단계: 비밀번호 확인
        System.out.print("탈퇴를 위해 현재 비밀번호를 입력해주세요: ");
        String passwordConfirm = sc.nextLine().trim();

        if (!currentMember.getPw().equals(passwordConfirm)) {
            System.out.println("비밀번호가 올바르지 않습니다.");
            System.out.println("탈퇴가 취소되었습니다.");
            return;
        }

        // 2단계: 최종 확인
        System.out.println("=".repeat(60));
        System.out.println("최종 확인");
        System.out.println("=".repeat(60));
        System.out.println("정말로 탈퇴하시겠습니까?");
        System.out.println("이 작업은 되돌릴 수 없습니다.");
        System.out.print("탈퇴하려면 '회원탈퇴'를 입력하세요: ");
        String finalConfirm = sc.nextLine().trim();

        if (!finalConfirm.equals("회원탈퇴")) {
            System.out.println("탈퇴가 취소되었습니다.");
            return;
        }

        // 3단계: Service를 통해 탈퇴 처리
        boolean deactivateSuccess = memberService.deactivateAccount(currentMember.getId());

        if (deactivateSuccess) {
            System.out.println("=".repeat(60));
            System.out.println("회원탈퇴가 완료되었습니다.");
            System.out.println(currentMember.getName() + "님, 그동안 이용해주셔서 감사했습니다.");
            System.out.println("더 나은 서비스로 찾아뵙겠습니다.");
            System.out.println("=".repeat(60));

            // 자동 로그아웃 처리
            currentMember = null;

            System.out.println("\n메인 메뉴로 돌아갑니다.");
            returnToMainMenu();

        } else {
            System.out.println("회원탈퇴에 실패했습니다.");
            System.out.println("시스템 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    /* 설명. 메인 메뉴 출력 */
    private void showMainMenu() {
        System.out.println("======= Bucket Buddy =======");
        if (currentMember == null) {
            // 비로그인 상태
            System.out.println("         메인 메뉴");
            System.out.println("=".repeat(60));
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 종료");
        } else {
            // 로그인한 상태
            System.out.println("=".repeat(60));
            System.out.println("1. 내 정보 보기");
            System.out.println("2. 버킷리스트 관리");
            System.out.println("3. 게시판");
            System.out.println("4. 로그아웃");
            System.out.println("5. 종료");
        }
        System.out.println("=".repeat(60));
        System.out.print("번호 선택: ");
    }

    /* 설명. 프로그램 종료 기능 */
    private void exitProgram() {
        System.out.println("\n프로그램을 종료합니다.");
        if (currentMember != null) {
            System.out.println(currentMember.getName() + "님, 이용해주셔서 감사합니다!");
        }
        System.out.println("좋은 하루 되세요! 👋");
        sc.close();
        System.exit(0);
    }

    /* 설명. 메인메뉴로 돌아가는 기능 */
    private void returnToMainMenu() {
        System.out.println("\n아무 키나 누르면 메인 메뉴로 돌아갑니다.");
        sc.nextLine();
    }
}

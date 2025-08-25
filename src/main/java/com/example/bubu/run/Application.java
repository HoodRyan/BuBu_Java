package com.example.bubu.run;

import com.example.bubu.aggregate.Gender;
import com.example.bubu.aggregate.Member;
import com.example.bubu.service.MemberService;

import java.util.Scanner;

public class Application {

    private MemberService memberService = new MemberService();
    private Member currentMember = null;   //현재 로그인한 사용자
    private Scanner sc= new Scanner(System.in);

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
        try{
            int input = sc.nextInt();
            sc.nextLine();  //개행 제거
            return input;
        }catch (Exception e){
            sc.nextLine();  //잘못된 입력 제거
            System.out.println("올바른 번호를 입력해주세요.");
            return -1;
        }
    }

    /* 설명. 메인 메뉴에서 로그인 여부 확인 */
    private void handleInput(int input) {
        if(currentMember == null) {
            // 비로그인 시 메뉴 처리
            handleGuestMenu(input);
        }else{
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
                // 나중에 구현할 게시판 기능
                System.out.println("게시판 기능은 추후 구현 예정입니다.");
                break;
            case 3:
                logout();
                break;
            case 4:
                exitProgram();
                break;
            default:
                System.out.println("올바른 번호를 선택해주세요.");
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
        switch (gender){
            case 1: genderEnum = Gender.MALE;      break;
            case 2: genderEnum = Gender.FEMALE;  break;
            case 3 : genderEnum = Gender.OTHER;   break;
        }

        System.out.print("전화번호를 입력하세요: ");
        String phone = sc.nextLine();

        System.out.print("입력할 성향/취향의 갯수를 입력하세요: ");
        int length = sc.nextInt();
        sc.nextLine();
        String[] intersets = new String[length];
        for (int i = 0; i < intersets.length; i++) {
            System.out.print((i+1) + "번째 성향을 입력하세요: ");
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
            currentMember = loginResult;
            System.out.println("     환영합니다, " + currentMember.getName() + "님!");
            System.out.println("로그인에 성공했습니다!");
        }else{
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
            System.out.println("=============================");
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
        System.out.println("\n" + "=======================");
        System.out.println("   👤 내 정보 조회");
        System.out.println("=======================");

        // currentMember 에서 바로 정보 출력
        System.out.println("📋 기본 정보");
        System.out.println("=======================");
        System.out.println("회원번호    : " + currentMember.getMemberNo());
        System.out.println("아이디      : " + currentMember.getId());
        System.out.println("이름        : " + currentMember.getName());
        System.out.println("성별        : " + currentMember.getGender());
        System.out.println("전화번호    : " + currentMember.getPhone());

        // 관심사
        System.out.println("\n🎯 관심사/취향");
        System.out.println("=======================");
        String[] interests = currentMember.getInterests();
        if (interests != null && interests.length > 0) {
            for (int i = 0; i < interests.length; i++) {
                System.out.println((i + 1) + ". " + interests[i]);
            }
        } else {
            System.out.println("등록된 관심사가 없습니다.");
        }

        System.out.println("=======================");


    }

    /* 설명. 내 정보 수정 기능 */
    private void updateMyInfo() {
    }


    /* 설명. 회원탈퇴 기능 */
    private void deactivateAccount() {
    }

    /* 설명. 메인 메뉴 출력 */
    private void showMainMenu() {
        System.out.println("======= Bucket Buddy =======");
        if(currentMember == null) {
            // 비로그인 상태
            System.out.println("         메인 메뉴");
            System.out.println("=============================");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 종료");
        }else{
            // 로그인한 상태
            System.out.println("=============================");
            System.out.println("1. 내 정보 보기");
            System.out.println("2. 게시판");
            System.out.println("3. 로그아웃");
            System.out.println("4. 종료");
        }
        System.out.println("=============================");
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

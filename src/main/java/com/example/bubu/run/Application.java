package com.example.bubu.run;

import com.example.bubu.aggregate.User;
import com.example.bubu.repository.UserRepository;
import com.example.bubu.service.UserService;

import java.util.Scanner;

public class Application {

    private UserService userService;
    private User currentUser;   //현재 로그인한 사용자
    private Scanner sc;

    public Application() {
        // 의존성 주입
        UserRepository userRepository = new UserRepository();
        this.userService = new UserService(userRepository);
        this.sc = new Scanner(System.in);
        this.currentUser = null;
    }

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
        if(currentUser == null) {
            // 비로그인 시 메뉴 처리
            handleGuestMenu(input);
        }else{
            // 로그인 상태 메뉴 처리
            handleUserMenu(input);
        }
    }

    /* 설명. 로그인 상태의 메뉴 처리 */
    private void handleUserMenu(int input) {
        switch (input) {
            case 1:
                showMyInfo();
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
                register();
                break;
            case 3:
                exitProgram();
                break;
            default:
                System.out.println("올바른 번호를 선택해주세요.");
        }
    }

    /* 설명. 회원가입 기능 */
    private void register() {

    }

    /* 설명. 로그인 기능 */
    private void login() {

    }

    /* 설명. 로그아웃 기능 */
    private void logout() {
        if (currentUser != null) {
            System.out.println("\n" + currentUser.getUsername() + "님이 로그아웃되었습니다.");
            currentUser = null;
            System.out.println("안전하게 로그아웃되었습니다.");
        } else {
            System.out.println("❌ 로그인 상태가 아닙니다.");
        }
        returnToMainMenu();
    }



    /* 설명. 내 정보 보기 기능*/
    private void showMyInfo() {
    }



    /* 설명. 메인 메뉴 출력 */
    private void showMainMenu() {
        System.out.println("======= Bucket Buddy =======");
        if(currentUser == null) {
            // 비로그인 상태
            System.out.println("         메인 메뉴");
            System.out.println("=============================");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 종료");
        }else{
            // 로그인한 상태
            System.out.println("     환영합니다, " + currentUser.getUsername() + "님!");
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
        if (currentUser != null) {
            System.out.println(currentUser.getUsername() + "님, 이용해주셔서 감사합니다!");
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

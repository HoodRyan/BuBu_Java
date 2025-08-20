# BuBu_Java
지난 프로젝트에서 DB로만 구현했던 BuBu(Bucket Buddy)를 Java를 통해 구현해보기

유저 회원가입 부터 간단한 게시판, 버킷리스트 작성까지

- 기능 개발 후 커밋 - PR 요청 - 셀프 코드 리뷰

## 기본 구조

Application(main) → Service → Repository 의 구조 (Controller X)

유저 정보는 파일 입출력을 통해 저장(DB X)

## 브랜치 전략

ㄴ main
ㄴ feature/user-system
ㄴ feature/board-system
ㄴ feature/bucketlist-system
ㄴ …

### Self-Review 포인트

- 🔍 **코드 품질**: 네이밍, 주석, 가독성
- 🧪 **기능 테스트**: 실제 실행해서 확인
- 📝 **커밋 메시지**: 의미 있는 단위로 분리되었는지
- 🔄 **리팩토링 여지**: 개선할 부분이 있는지

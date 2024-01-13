# 프로젝트 구성 정보

이 프로젝트는 JDK 17 버전으로 개발되었습니다. 또한 아래의 프레임워크 및 라이브러리가 사용됩니다.

- Spring MVC
- Spring Data JPA
- Jakarta EE

이 외 세부적인 스펙은 프로젝트 파일을 참조하시기 바랍니다.

---

# DB 구성 정보

이 프로젝트는 MySQL 데이터베이스를 사용합니다. 초기 데이터베이스 환경 세팅은 아래의 단계처럼 수행하시면 됩니다.

1. `schema-mysql.sql` 파일을 사용하여 데이터베이스 및 테이블을 생성합니다.
2. `input-data-mysql.sql` 파일을 사용하여 더미 데이터를 로드합니다.

---

# DB 테이블 정보

본 프로젝트에서는 총 4개의 테이블이 사용됩니다: `contents`, `review`, `user`, `view_log`.

## Contents 테이블

'Contents' 테이블은 다양한 '작품' 정보를 담고 있습니다. 구조는 아래와 같습니다.

- `ID`: 테이블의 Primary Key입니다.
- `contents_name`: 작품명을 나타냅니다.
- `author`: 작품의 작가명을 나타냅니다.
- `coin`: 작품의 금액을 나타냅니다. 0원은 무료를 의미합니다.
- `open_date`: 서비스 제공일을 나타냅니다.
- `is_adult`: 성인 작품 여부를 나타냅니다. 1은 성인작품, 0은 일반작품을 의미합니다.

## Review 테이블

'Review' 테이블은 항목에 대한 사용자의 리뷰를 저장합니다.

- `user_id`: 리뷰를 작성한 유저의 ID를 나타냅니다.
- `contents_id`: 리뷰 대상 작품의 ID를 나타냅니다.
- `rating`: 사용자의 평가를 나타냅니다. 'LIKE'는 좋아요, 'DISLIKE'는 싫어요를 의미합니다.
- `comment`: 사용자의 리뷰 내용을 나타냅니다.

## User 테이블

'User' 테이블은 사용자 정보를 저장합니다.

- `ID`: 테이블의 Primary Key입니다.
- `user_name`: 사용자명을 나타냅니다.
- `user_email`: 사용자의 이메일을 나타냅니다.
- `gender`: 사용자의 성별을 나타냅니다. 'MALE'은 남성, 'FEMALE'은 여성, 'NON_BINARY'는 비바이너리를 의미합니다.
- `type`: 사용자의 유형을 나타냅니다. 'NORMAL'은 일반, 'ADULT'은 성인을 의미합니다.
- `register_date`: 서비스 등록일을 나타냅니다.

## View_log 테이블

'View_log' 테이블은 사용자의 작품 조회 기록을 저장합니다.

- `log_id`: 테이블의 Primary Key입니다.
- `user_id`: 작품을 조회한 유저의 ID를 나타냅니다.
- `contents_id`: 조회된 작품의 ID를 나타냅니다.
- `view_date`: 조회 일시를 나타냅니다.

---

# API 엔드포인트

본 프로젝트에서는 몇 가지 API 엔드포인트를 제공합니다. 각 엔드포인트는 특정 기능을 수행하며, 요청과 응답이 정의되어 있습니다.

## ContentsController 엔드포인트

- GET `/contents/top-likes`: 좋아요 수가 가장 많은 상위 3개의 작품을 반환합니다.
- GET `/contents/top-dislikes`: 싫어요 수가 가장 많은 상위 3개의 작품을 반환합니다.
- GET `/contents/{id}/users`: 특정 콘텐츠를 조회한 사용자 목록을 반환합니다.
- POST `/contents/view-log`: 사용자가 콘텐츠를 조회하였을 때, 이를 로그로 기록합니다.
- PUT `/contents/{id}/coin`: 특정 작품을 유료 혹은 무료로 설정합니다.

## ReviewController 엔드포인트

- POST `/reviews`: 사용자가 특정 작품에 대해 평가(좋아요/싫어요, 댓글)를 등록합니다. 한 작품에 대해서 각 사용자는 한 번만 평가할 수 있으며, 댓글 등록은 선택사항입니다.

## UserController 엔드포인트

- GET `/users/{id}`: 특정 사용자의 정보를 반환합니다.
- GET `/users/adult-contents/{days}/{minViews}`: 성인 작품을 최근 `{days}`일 동안 `{minViews}`회 이상 조회한 사용자 목록을 조회합니다.
- GET `/users/recent-users/adult-contents/{days}/{minViews}`: 최근 `{days}`일 동안 가입한 사용자들 중, 성인 작품을 `{minViews}`회 이상 조회한 사용자 목록을 조회합니다.
- DELETE `/users/{id}`: 특정 사용자와 사용자의 모든 평가 및 조회 이력을 삭제합니다.

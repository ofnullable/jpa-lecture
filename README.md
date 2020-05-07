# ✏️ JPA Lecture ✏️

## 요구사항

> 간단한 쇼핑몰(?)

- 회원
  - 등록
  - 조회
- 상품
  - 등록
  - 조회
  - 수정
- 주문
  - 등록
  - 조회
  - 취소
- 기타
  - 상품 재고 관리
  - 상품의 종류는 도서, 음반, 영화
  - 상품의 카테고리 구분
  - 주문 시 배송 정보를 입력

## 도메인 모델

회원 1 .. N 주문
주문 N .. M 상품 ( 주문 상품 )
주문 1 .. 1 배송

## 엔티티 설계 시 주의점

- 가급적 `setter`를 사용하지 말자.
- 모든 연관관계는 `fetch = FetchType.LAZY`로 설정하자. (특히 `@XToOne`)
  - 원치않는 데이터가 fetch되어 성능 이슈가 발생할 수 있다.
  - 연관관계된 데이터가 필요한 경우 `fetch join` 또는 `EntityGraph`등을 활용하자. 
- Collection은 필드에서 초기화하자.
  - NPE로 부터 안전하다.
  - Hibernate는 Collection 필드의 클래스를 내부적으로 변경하므로 필드에서 생성 후 건들지(변경하지) 말자
- 테이블, 컬럼명 생성전략
  - SpringBoot default -> `SpringPhysicalNamingStrategy`
  - CamelCase를 snake_case로 변경해준다. (구버전은 변경X)


## 구현 요구사항

- 예제 단순화를 위해 로그인 및 권환 관리는 구현하지 않음
- 파라미터 검증 및 예외처리는 최대한 단순하게 구현
- 실제로 사용하는 상품은 도서만 활용
- 카테고리 및 배송정보는 사용하지 않음

## 어플리케이션 아키텍처

- 계층형 구조 활용
  - controller: web 계층
  - service: 비즈니스 로직 ( 트랜잭션 등 )
  - repository: JPA를 직접 사용하는 계층 ( EntityManager )
  - domain: Entity, 모든 계층에서 사용

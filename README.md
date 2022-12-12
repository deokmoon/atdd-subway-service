<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-6.14.15-blue">
  <img alt="node" src="https://img.shields.io/badge/node-14.18.2-blue">
  <a href="https://edu.nextstep.camp/c/R89PYi5H" alt="nextstep atdd">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/next-step/atdd-subway-admin">
</p>

<br>

# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

<br>

## 🚀 Getting Started

### Install
#### npm 설치
```
cd frontend
npm install
```
> `frontend` 디렉토리에서 수행해야 합니다.

### Usage
#### webpack server 구동
```
npm run dev
```
#### application 구동
```
./gradlew bootRun
```
<br>

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/next-step/atdd-subway-service/issues) 에 등록해주세요 :)

<br>

## 📝 License

This project is [MIT](https://github.com/next-step/atdd-subway-service/blob/master/LICENSE.md) licensed.

-----------------

## Step1 인수 테스트 기반 리팩터링
### 미션 요구사항
- LineService 리팩터링
- LineSectionAcceptanceTest 리팩터링
### 미션 목적: 리팩터링
- LineService의 비즈니스 로직을 도메인으로 옮기기
- 한번에 많은 부분을 고치려 하지 말고 나눠서 부분부분 리팩터링하기
- 전체 기능은 인수 테스트로 보호한 뒤 세부 기능을 TDD로 리팩터링하기
### 미션 요구사항 설명
- Domain으로 옮길 로직 찾기
    * 스프링 빈을 사용하는 객체와 의존하는 로직을 제외하고 도메인으로 옮기기
    * 객체지향 생활체조 참고
        * 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기(indent)만 한다.
        * 규칙 2: else 예약어를 쓰지 않는다.
        * 규칙 3: 모든 원시값과 문자열을 포장한다.
        * 규칙 4: 한 줄에 점을 하나만 찍는다.
        * 규칙 5: 줄여쓰지 않는다(축약 금지).
        * 규칙 6: 모든 엔티티를 작게 유지한다.
        * 규칙 7: 3개 이상의 인스턴스 변수를 가진 클래스를 쓰지 않는다.
        * 규칙 8: 일급 콜렉션을 쓴다.
        * 규칙 9: 게터/세터/프로퍼티를 쓰지 않는다.
- Domain의 단위 테스트 작성하기
    * 서비스 레이어에서 옮겨올 로직의 기능 테스트
    * SectionTest나 LineTest 클래스가 생성될 수 있음
- 로직을 옮기기
    * 기존 로직을 지우지 않고 새로운 로직 만들어 수행
    * 정상 동작 확인 후 기존 로직 제거
### LineService 리팩터링 대상 기능목록
- [X] 지하철 노선 생성
- [X] 지하철 노선 목록 조회
- [X] 지하철 노선 조회
- [X] 지하철 노선 수정
- [X] 지하철 노선 삭제
- [X] 지하철 노선 내 구간 추가
- [X] 지하철 노선 내 역 삭제(구간 삭제)
#### LineSectionAScceptanceTest 리팩터링
* [X] API 검증이 아닌 시나리오, 흐름을 검증하는 테스트로 리팩터링
* [X] 기능의 인수 조건을 설명할 때 하나 이상의 시나리오가 필요한 경우 여러 시나리오 만들어 인수 테스트 작성
#### 인수 조건 예시
  ```text
  Feature: 지하철 구간 관련 기능

  Background 
    Given 지하철역 등록되어 있음
    And 지하철 노선 등록되어 있음
    And 지하철 노선에 지하철역 등록되어 있음

  Scenario: 지하철 구간을 관리
    When 지하철 구간 등록 요청
    Then 지하철 구간 등록됨
    When 지하철 노선에 등록된 역 목록 조회 요청
    Then 등록한 지하철 구간이 반영된 역 목록이 조회됨
    When 지하철 구간 삭제 요청
    Then 지하철 구간 삭제됨
    When 지하철 노선에 등록된 역 목록 조회 요청
    Then 삭제한 지하철 구간이 반영된 역 목록이 조회됨
  ```
### Step1 회고 작성
- 미션 요구사항뿐만 아니라, 다른 리팩토링 대상이 있으면 주도적으로 처리필요
  - 요구사항외 이 내용도 바꿔도 될까? 라는 고민이 있으면 합당하다면 수행
- 의미 있는 Commit 단위를 TDD 기반으로 작성 필요
- 메서드 순서 클린코드 기반하여 작성 public / private(호출되는 순서)
-----------------
## Step2 경로 조회 기능
### 미션 요구사항
- 최단 경로 조회 인수 테스트 만들기
- 최단 경로 조회 기능 구현하기
### 요청 / 응답 포맷
Request
~~~http request
HTTP/1.1 200 
Request method:	GET
Request URI:	http://localhost:55494/paths?source=1&target=6
Headers: 	Accept=application/json
		Content-Type=application/json; charset=UTF-8
~~~
Response
~~~http request
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sat, 09 May 2020 14:54:11 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "stations": [
        {
            "id": 5,
            "name": "양재시민의숲역",
            "createdAt": "2020-05-09T23:54:12.007"
        },
        {
            "id": 4,
            "name": "양재역",
            "createdAt": "2020-05-09T23:54:11.995"
        },
        {
            "id": 1,
            "name": "강남역",
            "createdAt": "2020-05-09T23:54:11.855"
        },
        {
            "id": 2,
            "name": "역삼역",
            "createdAt": "2020-05-09T23:54:11.876"
        },
        {
            "id": 3,
            "name": "선릉역",
            "createdAt": "2020-05-09T23:54:11.893"
        }
    ],
    "distance": 40
}
~~~
### 요구사항 기반 구현 목록
- [ ] 최단 경로 라이브러리 적용(jgrapht)
    - 정점: 지하철역 - station
    - 간선: 지하철역 연결정보 - section
    - 가중치: 거리 - section 간의 distance
    - 최단 거리 기준 조회 시 가중치를 '거리'로 설정
- [ ] 예외사항 적용
    - 출발역과 도착역이 같은 경우
    - 출발역과 도착역이 연결이 되어 있지 않은 경우
    - 존재하지 않은 출발역이나 도착역을 조회할 경우
### 미션 수행 순서
- 인수 테스트 성공 시키기
    - mock 서버와 dto를 정의하여 인수테스트 성공 시키기
~~~text
Feature 지하철 경로 관련 기능
    Background
        Given 지하철역(Station)이 여러개 등록되어 있음
        And 지하철노선(Line) 여러개 등록되어 있음
        And 지하철노선에 지하철역(Section) 여러개 등록되어 있음
    
    Scenario 출발역과 도착역 사이의 최단 경로 조회
        When 지하철 최단 경로 조회 요청
        Then 출발역과 도착역 사이의 최단 경로 조회됨
    
    Scenario 출발역과 도착역이 같을 때 최단 경로 조회
        When 지하철 최단 경로 조회 요청
        Then 최단 경로 조회 실패
    
    Scenario 존재하지 않은 출발역 또는 도착역으로 최단 경로 조회
        When 지하철 최단 경로 조회 요청
        Then 최단 경로 조회 실패
~~~
- 기능 구현
    - TDD의 방향보다 테스트를 통해 구현할 기능을 명세하는것과 리팩터링이 더 중요
    - Outside In 경우
        - 컨트롤러 레이어 구현 이후 서비스 레이어 구현 시 서비스 테스트 우선 작성 후 기능 구현
        - 서비스 테스트 내부에서 도메인들간의 로직의 흐름을 검증, 이 때 사용되는 도메인은 mock 객체를 활용
        - 외부 라이브러리를 활용한 로직을 검증할 때는 가급적 실제 객체를 활용
        - Happy 케이스에 대한 부분만 구현( Side 케이스에 대한 구현은 다음 단계에서 진행)
    - Inside Out 경우
        - 도메인 설계 후 도메인 테스트를 시작으로 기능 구현 시작
        - 해당 도메인의 단위 테스트를 통해 도메인의 역할과 경계를 설계
        - 도메인의 구현이 끝나면 해당 도메인과 관계를 맺는 객체에 대해 기능 구현 시작
    - ex) 경로 조회를 수행하는 도메인 구현 예시
      1. PathFinder 라는 클래스 작성 후 경로 조회를 위한 테스트를 작성
      2. 경로 조회 메서드에서 Line을 인자로 받고 그 결과로 원하는 응답을 리턴하도록 테스트 완성
      3. 테스트를 성공시키기 위해 JGraph의 실제 객체를 활용(테스트에서는 알 필요가 없음)
#### jgrapht 라이브러리 테스트코드 예시
~~~java
@Test
public void getDijkstraShortestPath() {
    WeightedMultigraph<String, DefaultWeightedEdge> graph
            = new WeightedMultigraph(DefaultWeightedEdge.class);
    graph.addVertex("v1");
    graph.addVertex("v2");
    graph.addVertex("v3");
    graph.setEdgeWeight(graph.addEdge("v1", "v2"), 2);
    graph.setEdgeWeight(graph.addEdge("v2", "v3"), 2);
    graph.setEdgeWeight(graph.addEdge("v1", "v3"), 100);

    DijkstraShortestPath dijkstraShortestPath
            = new DijkstraShortestPath(graph);
    List<String> shortestPath 
            = dijkstraShortestPath.getPath("v3", "v1").getVertexList();

    assertThat(shortestPath.size()).isEqualTo(3);
}
~~~


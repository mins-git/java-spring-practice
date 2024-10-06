## ![sping 동작 환경 그림](img/image.png)

- 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버( viewResolver )가 화면을 찾아서 처리한다.
- 스프링 부트 템플릿엔진 기본 viewName 매핑
- resources:templates/ +{ViewName}+ .html

### 서버 재시작없이 html view파일 변경하는법
- 참고: spring-boot-devtools 라이브러리를 추가하면, html 파일을 컴파일만 해주면 서버 재시작 없이 View 파일 변경이 가능하다.
    - 인텔리J 컴파일 방법: 메뉴 build Recompile


## build 하는법.
- 1. `./gradlew build` : gradlew있는 폴더에서 build 명령어 입력.
- 2. `cd/build/libs`: 해당 폴더안에 들어가면  
    - [ssafy-spring-0.0.1-(프로젝트명)] + SNAPSHOT.jar 파일이 생성되어있음.

- 3. `java -jar ssafy-spring-0.0.1-SNAPSHOT.jar` : 하면 spring 서버가 실행됨.
    - java -jar + 프로젝트명 + SNAPSHOT.jar 하면 서버가 실행됨.


### 실행되지 않을시
- `./gradlew clean build` : 진행시 build 폴더가 없어지고, 완전히 지운 후 다시 build함.


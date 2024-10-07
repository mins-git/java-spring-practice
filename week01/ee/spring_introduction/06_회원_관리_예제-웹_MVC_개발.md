# 회원관리 예제 - 웹 MVC 개발

## 회원 웹 기능- 홈 화면 추가

### 홈 컨트롤러 추가

```java
// HomeController.java

package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
```

### 회원 관리용 홈

```html
<!-- home.html -->

<!DOCTYPE HTML>

<html xmlns:th="http://www.thymeleaf.org">
<body>

<div class="container">
    <div>
        <h1>Hello Spring</h1>
        <p>회원 기능</p>
        <p>
            <a href="/members/new">회원 가입</a>
            <a href="/members">회원 목록</a>
        </p>
    </div>
</div>
</body>
</html>
```

## 회원 웹 기능- 등록

### 회원 등록 폼

- 회원 등록 폼 컨트롤러
    
    ```java
    // MemberController.java
    
    @Controller
    public class MemberController {
    
        //    private final MemberService memberService = new MemberService();
        private final MemberService memberService;
    
        @Autowired
        public MemberController(MemberService memberService) {
            this.memberService = memberService;
        }
    
        @GetMapping("/members/new") 
        public String createForm() {
            return "members/createMemberForm";
        }
    ```
    
    - `@GetMapping("/members/new")` : `/members/new` url로 GET요청 들어오면 `createMemberForm` 렌더링
- 회원 등록 폼 HTML
    
    ```html
    <!-- createMemberForm.html -->
    
    <!DOCTYPE HTML>
    <html xmlns:th="http://www.thymeleaf.org">
    <body>
    <div class="container">
        <form action="/members/new" method="post">
            <div class="form-group">
                <label for="name">이름</label>
                <input type="text" id="name" name="name" placeholder="이름을 입력하세요.">
            </div>
            <button type="submit">등록</button>
        </form>
    </div> <!-- /container -->
    </body>
    </html>
    ```
    
    - POST 메서드 사용해서 `/members/new`로 데이터 전송

### 회원 등록 컨트롤러

- 웹 등록 화면에서 데이터를 전달 받을 폼 객체
    
    ```java
    // ModelForm.java
    
    package hello.hello_spring.controller;
    
    public class MemberForm {
    
        private String name;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    ```
    
- 회원 컨트롤러에서 회원을 실제 등록
    
    ```java
    // MemberController.java
    
        @PostMapping("/members/new")
        public String create(MemberForm form) {
            Member member = new Member();
            member.setName(form.getName());
    
            memberService.join(member);
    
            return "redirect:/";
    ```
    
    - 회원 등록 완료되면 홈 화면으로 `redirect`

## 회원 웹 기능- 조회

- 회원 컨트롤러에서 조회
    
    ```java
    // MemberController.java
    
        @GetMapping("/members")
        public String list(Model model) {
            List<Member> members = memberService.findMembers();
            model.addAttribute("members", members);
            return "members/memberList";
    
        }
    ```
    
- 회원 리스트 html
    
    ```html
    <!DOCTYPE HTML>
    <html xmlns:th="http://www.thymeleaf.org">
    <body>
    <div class="container">
        <div>
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>이름</th>
                </tr>
                </thead>
                <tbody>
                <tr th:each="member : ${members}">
                    <td th:text="${member.id}"></td>
                    <td th:text="${member.name}"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div> <!-- /container -->
    </body>
    </html>
    ```
    
    - `th:each="member : ${members}"`: Thymeleaf의 반복문 구문
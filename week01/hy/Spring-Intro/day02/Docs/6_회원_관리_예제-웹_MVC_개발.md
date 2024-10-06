# 회원 관리 예제 - 웹 MVC 개발

## 회원 웹 기능 - 홈 화면 추가
**홈 컨트롤러 추가**
```java
package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")    // url: localhost:8080/
    public String home() {
        return "home";  // home.html 호출
    }
}
```
**회원 관리용 홈**
```html
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
</div> <!-- /container -->

</body>
</html>
```
> 참고: 컨트롤러가 정적 파일보다 우선순위가 높다.

## 회원 웹 기능 - 등록
### 회원 등록 폼 개발
**회원 등록 폼 컨트롤러**
```java
@Controller
public class MemberController {
    private final MemberService memberService;
    // 스프링 컨테이너에 한번 등록 후 계속 그거 쓰면 된다

    // 스프링 빈에 MemberController 등록
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/members/new")     // url: localhost:8080/members/new(@GetMapping: 요청이 GET일 때!)
    public String createForm() {
        return "members/createMemberForm";  // templates/members/createMemberForm.html 렌더링
    }
```
**회원 등록 폼 HTML** ( `resources/templates/members/createMemberForm` )
```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<div class="container">
	<form action="/members/new" method="post">
		<div class="form-group">
			<label for="name">이름</label>
			<input type="text" id="name" name="name" placeholder="이름을 입력하세요">
		</div>
		<button type="submit">등록</button>
	</form>
</div> <!-- /container -->
</body>
</html>
```
### 회원 등록 컨트롤러
**웹 등록 화면에서 데이터를 전달 받을 폼 객체**
```java
package hello.hellospring.controller;

public class MemberForm {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
```
**회원 컨트롤러에서 회원을 실제 등록하는 기능**
```java
@PostMapping("/members/new")    // url: localhost:8080/members/new(@PostMapping: 요청이 POST일 때!)
public String create(MemberForm form) {     // html의 input으로 받은 내용이 MemberForm의 name으로 입력됨

    Member member = new Member();   // 멤버 객체 생성
    member.setName(form.getName()); // form에서 받은 이름을 member의 name 필드에 설정

    memberService.join(member);     // 회원가입처리: memberService로 전달(회원가입 로직, 회원 리포지토리에 저장)

    return "redirect:/";
}
```
## 회원 웹 기능 - 조회
**회원 컨트롤러에서 조회 기능**
```java
@GetMapping("/members")
public String list(Model model) {
    List<Member> members = memberService.findMembers();     // 회원 조회한 결과 -> members라는 List에 저장
    model.addAttribute("members", members);     // members 리스트를 model 객체에 담아서 members라는 이름으로 view에 전달
    return "members/memberList";                            // members/memberList.html 렌더링
}
```
**회원 리스트 HTML**
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
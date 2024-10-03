package ssafy.ssafy_spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SsafyController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "HELLO!");
        return "hello"; // resources의 templates의 hello를 찾으라는것임.
                        // templates/hello.html(Thymeleaf 엔진 처리방법) hello를 찾으라는것임.
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // http에서 헤더와 바디 중 바디에 아래 데이터를 직접 넣겠다라는 의미.
    public String helloString(@RequestParam("name") String name){
        return "hello" + name; // "spring으로 name을 던져주면 hello spring"으로 바뀔것임.
    }


    // 제이슨을 던져줌 키 + value값으로 던져짐.
    @GetMapping("hello-api")
    @ResponseBody // JSON으로 반환하는게 기본임.
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;

    }

    static class Hello{
        private String name; // 자바빈 표준방식으로 getter steer
                            // property 접근방식이라고함.

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

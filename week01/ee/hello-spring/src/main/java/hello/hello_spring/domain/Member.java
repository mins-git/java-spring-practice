package hello.hello_spring.domain;

public class Member {
    private Long id; //시스템이 정해주는 id
    private String name; //고객이 회원가입할 때 적는 이름

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

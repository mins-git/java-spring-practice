package hello.core.singleton;

/*
* : 싱글톤 패턴을 구현하는 방법은 여러가지가 있다.
* 아래는 객체를 미리 생성해두는 가장 단순하고 안전한 방법을 선택했다.
* 스프링쓰면 스프링이 알아서 싱글톤으로 만들어주기에 신경쓸 필요없음.
* */

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }

    private SingletonService() {

    }

    public void loginc() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}

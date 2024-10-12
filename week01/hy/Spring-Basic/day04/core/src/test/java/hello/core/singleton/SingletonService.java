package hello.core.singleton;

public class SingletonService {

    // static 영역으로 객체 생성
    private static final SingletonService instance = new SingletonService();

    // instance에 참조로 넣어놓는다.
    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}

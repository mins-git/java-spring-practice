package hello.core.singleton;


// 싱글톤 문제점 예시 서비스
public class StatefulService {

    private int price; // 상태 유지하는 필드
    public void order(String name, int price) {
        System.out.println("name = " + name + "price = " + price);

        this.price = price; /// 여기 문제터짐
    }

    public int getPrice(){
        return price;
    }

    // 정상적으로 돌리기 위해서는,
//    public void order(String name, int price) {
//        System.out.println("name = " + name + "price = " + price);
//
//        return price; // 그냥 바로 반환해버리면됨.
//    }



}

package NBCassignment.JH.NBCKioskFirstProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Order order = new Order();

        while(true){
            displayMainMenu();
            int number = sc.nextInt();
            sc.nextLine();
            if(number == 1){
                displayMenu(createBurger(),order, sc);
            }else if(number == 2){
                displayMenu(createForzenCustard(),order, sc);
            }else if(number == 3){
                displayMenu(createDrinks(),order, sc);
            }else if(number == 4){
                displayMenu(Beer(),order, sc);
            }else if(number == 5){
                displayOrder(order, sc);
            }else if(number == 6){
                cancelOrder(order, sc);
            }
        }
    }

    private static void displayMenu(List<Product> products, Order order, Scanner sc){
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.");

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println(i + 1 + ". " + product.getName() + " | " + product.getPrice() + " | " + product.getPrice());
        }
        int number = sc.nextInt();
        sc.nextLine();
        if(number >= 1 && number <= products.size()){
            Product product = products.get(number-1);
            System.out.println(product.getName() + " | " + product.getPrice() + " | " + product.getExplanation());
            System.out.println("위 메뉴를 장바구니에 추가하시곘습니까?");
            System.out.println("1. 확인  2. 취소");
            int numberOrder = sc.nextInt();;
            sc.nextLine();
            if(numberOrder == 1){
                order.addProducts(product);
                System.out.println(product.getName() + " 가 장바구니에 추가되었습니다.");
            }
        }
    }

    private static void displayOrder(Order order, Scanner sc) throws Exception{
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println("[ Orders ]");
        for(Product orderMenu : order.getProducts()){
            System.out.println(orderMenu.getName() + " | " + orderMenu.getPrice() + " | " + orderMenu.getExplanation());
        }
        System.out.println("[ Total ]");
        System.out.println(" W " + order.totalPrice());
        System.out.println("1. 주문  2. 메뉴판");
        int number = sc.nextInt();
        sc.nextLine();
        int randomNumber = random();
        if (number == 1) {
            System.out.println("주문이 완료되었습니다!");
            System.out.println("대기번호는 [ " + randomNumber + " ] 번 입니다.");
            System.out.println("3초후 메뉴판으로 돌아갑니다.");
            Thread.sleep(3000);
            order.clearOrder();
        }
    }
    private static int random(){
        int random = 1 + (int)Math.random() * 1000;
        return random;
    }

    private static void cancelOrder(Order order, Scanner sc){
        System.out.println("진행하던 주문을 취소하시곘습니까?");
        System.out.println("1. 확인  2. 취소");
        int number = sc.nextInt();
        sc.nextLine();
        if(number == 1){
            order.clearOrder();
            System.out.println("진행하던 주문이 취소되었습니다.");
        }
    }

    private static void displayMainMenu() {
        System.out.println("\"JaeHoon BURGER 에 오신걸 환영합니다.\"");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println();
        System.out.println("[ SHAKESHACK MENU ]");
        System.out.println("1. Burgers");
        System.out.println("2. Frozen Custard");
        System.out.println("3. Drinks");
        System.out.println("4. Beer\n");
        System.out.println("[ ORDER MENU ]");
        System.out.println("5. Order | 장바구니를 확인 후 주문합니다.");
        System.out.println("6. Cancel | 진행중인 주문을 취소합니다.");
    }

    private static List<Product> createBurger() {
        List<Product> burger = new ArrayList<>();
        burger.add(new Product("맥도날드", 5.0, "맛있음"));
        burger.add(new Product("버거킹", 5.5, "맛있음"));
        burger.add(new Product("롯데리아", 4.5, "맛있음"));
        return burger;
    }
    private static List<Product> createForzenCustard(){
        List<Product> ForzenCustard = new ArrayList<>();
        ForzenCustard.add(new Product("소프트콘",2.0,"맛있음"));
        ForzenCustard.add(new Product("초코콘",2.5,"맛있음"));
        ForzenCustard.add(new Product("바닐라콘",2.5,"맛있음"));
        return ForzenCustard;
    }
    private static List<Product> createDrinks(){
        List<Product> drinks = new ArrayList<>();
        drinks.add(new Product("콜라",2.0,"맛있음"));
        drinks.add(new Product("사이다",2.5,"맛있음"));
        drinks.add(new Product("제로추가",0.5,"맛있음"));
        return drinks;
    }
    private static List<Product> Beer(){
        List<Product> beer = new ArrayList<>();
        beer.add(new Product("생맥주",2.0,"시원함"));
        beer.add(new Product("흑맥주",2.5,"시원함"));
        beer.add(new Product("라거맥주",2.5,"시원함"));
        return beer;
    }
}

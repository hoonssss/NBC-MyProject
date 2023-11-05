package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Order order = new Order();
    static MessageBox messageBox = new MessageBox();
    static waitBox waitBox = new waitBox();
    static CompletedBox completedBox = new CompletedBox();
    static CompletedMessageBox completedMessageBox = new CompletedMessageBox();
    static Map<String, List<Product>> categoryProducts = new HashMap<>();
    static int waitNumber = 1;

    public static void main(String[] args) throws Exception {
        while (true) {
            displayMainMenu(order);
            int number = sc.nextInt();
            sc.nextLine();
            if (number == 1) {
                displayMenu(createBurger(), order, sc);
            } else if (number == 2) {
                displayMenu(createForzenCustard(), order, sc);
            } else if (number == 3) {
                displayMenu(createDrinks(), order, sc);
            } else if (number == 4) {
                displayMenu(createBeer(), order, sc);
            } else if (number == 5) {
                displayOrder(order, sc, messageBox, completedMessageBox);
            } else if (number == 6) {
                cancelOrder(order, sc);
            } else if (number == 7) {
                managementProgram();
            }
        }
    }

    private static void displayMainMenu(Order order) {
        System.out.println("\"JaeHoon BURGER 에 오신걸 환영합니다.\"");
        if (!order.getOrders().isEmpty()) {
            System.out.println("[ 현재 메뉴 ]");
            List<Product> products = order.getOrders();
            for (int i = 0; i < Math.min(3, products.size()); i++) {
                Product product = products.get(i);
                System.out.println(product.getName() + " | " + product.getPrice() + " | " + product.getExplanation());
            }
            System.out.println("총 금액 : " + order.totalPrice());
        }

        System.out.println("[ SHAKESHACK MENU ]");
        System.out.println("1. Burgers");
        System.out.println("2. Frozen Custard");
        System.out.println("3. Drinks");
        System.out.println("4. Beer\n");
        System.out.println("[ ORDER MENU ]");
        System.out.println("5. Order | 장바구니를 확인 후 주문합니다.");
        System.out.println("6. Cancel | 진행중인 주문을 취소합니다.");
        System.out.println("7. Kiosk Management");
    }

    private static void displayMenu(List<Product> product, Order order, Scanner sc) {
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.");

        for (int i = 0; i < product.size(); i++) {
            Product products = product.get(i);
            System.out.println(i + 1 + ". " + products.getName() + " | " + products.getPrice() + " | " + products.getExplanation());
        }
        int number = sc.nextInt();
        sc.nextLine();
        if (number >= 1 && number <= product.size()) {
            Product products = product.get(number - 1);
            System.out.println(products.getName() + " | " + products.getPrice() + " | " + products.getExplanation());
            System.out.println("위 메뉴를 장바구니에 추가하시겘습니까?");
            System.out.println("1. 확인  2. 취소");
            int numberOrder = sc.nextInt();
            sc.nextLine();
            if (numberOrder == 1) {
                order.addProducts(products); // 오더
                waitBox.addWaitBoxs(products); // 대기
                completedBox.addCompleteBoxs(products);
                System.out.println(products.getName() + " 장바구니에 추가되었습니다.");
                System.out.println("대기번호는 [ " + waitNumber + " ] 번 입니다.");
                waitNumber++;
                System.out.println();
            }
        }
    }

    private static void displayOrder(Order order, Scanner sc, MessageBox messageBox, CompletedMessageBox
                                      completedMessageBox) throws Exception {
        Product product;
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println("[ Orders ]");
        for (Product orderMenu : order.getOrders()) {
            System.out.println(orderMenu.getName() + " | " + orderMenu.getPrice() + " | " + orderMenu.getExplanation());
        }
        System.out.println("[ Total ]");
        System.out.println(" W " + order.totalPrice());
        System.out.println("1. 주문  2. 메뉴판");
        int number = sc.nextInt();
        sc.nextLine();
        if (number == 1) {
            System.out.println("요청 사항을 입력해 주세요.(20자 이내)");
            String input = sc.nextLine();
                System.out.println("요청 사항을 확인해주세요.");
                System.out.println(input);
                System.out.println("1. 확인  2. 취소  3. 메뉴판");
                messageBox.addMessage(input);
                completedMessageBox.addMessageBox(input);
                int check = sc.nextInt();
                if (check == 1) {
                    System.out.println("주문이 완료되었습니다!");
                    System.out.println("3초후 메뉴판으로 돌아갑니다.");
                    Thread.sleep(3000);
                    order.clearOrder();
                }
                } else if (number == 2) {
                    System.out.println("취소하였습니다. 기존 메뉴는 유지됩니다.");
                    displayOrder(order, sc, messageBox,completedMessageBox);
                } else {
                    System.out.println("메뉴판으로 돌아가겠습니다. 기존 메뉴는 유지됩니다.");
                }
    }

    private static void cancelOrder(Order order, Scanner sc) {
        System.out.println("진행하던 주문을 취소하시곘습니까?");
        System.out.println("1. 확인  2. 취소");
        int number = sc.nextInt();
        sc.nextLine();
        if (number == 1) {
            order.clearOrder();
            System.out.println("진행하던 주문이 취소되었습니다.");
        }
    }

    private static void managementProgram() throws Exception {
        System.out.println("1. 대기 주문목록");
        System.out.println("2. 완료 주문목록");
        System.out.println("3. 상품 생성");
        System.out.println("4. 상품 삭제");
        System.out.println("5. 메인 메뉴");
        int number = sc.nextInt();
        sc.nextLine();


        if (number == 1) {
            System.out.println("대기번호 : " + (waitNumber - 1));
            System.out.print("주문 상품 목록 : ");
            List<Product> waitProduct = waitBox.getWaitBoxs();
            for (Product waitBox : waitProduct) {
                System.out.print(waitBox.getName() + ", ");
            }
            System.out.println();
            System.out.println("주문 총 가격 : " + waitBox.totalPrice());
            System.out.print("요청 사항 : ");
            List<String> messages = messageBox.getMessage();
            for (String message : messages){
                System.out.println(message);
            }
            System.out.println();
            System.out.println("주문 일시 : " + getTimestamp());
            System.out.println("3초 후 완료 주문 목록으로 이동됩니다.");
            Thread.sleep(3000); // 3초후
            waitBox.clearWaitBoxs();
            messageBox.clearMessage();


        } else if (number == 2) {
            System.out.println("대기번호 : " + (waitNumber - 1));
            System.out.print("주문 상품 목록 : ");
            List<Product> completebox = completedBox.getCompleteBoxs();
            for (Product complete : completebox) {
                System.out.println(complete.getName() + ", ");
            }
            System.out.println("주문 총 가격 : " + completedBox.totalPrice());
            System.out.println("주문 일시 : " + getTimestamp());
            System.out.print("요청 사항 : ");
            List<String> message = completedMessageBox.getMessageBox();
            for(String messages : message){
                System.out.println(messages);
            }
            System.out.println("3초 후 주문목록으로 이동됩니다. 취소는 1번입니다.");
            Thread.sleep(3000);
            displayMainMenu(order);
            int timeNumber = sc.nextInt();
            sc.nextLine();
            if (timeNumber == 1) {
                managementProgram();
            }
        } else if (number == 3) {
            addProductToMenu(order, sc);
        } else if (number == 4) {
            removeProductFromMenu(order, sc);
        } else if (number == 5) {
            displayMainMenu(order);
        }
    }

    private static void addProductToMenu(Order order, Scanner sc) {
        System.out.println("카테고리를 선택하세요");
        System.out.println("1. Burgers");
        System.out.println("2. Frozen Custard");
        System.out.println("3. Drinks");
        System.out.println("4. Beer");
        int categoryChoice = sc.nextInt();
        sc.nextLine();

        String categoryName = "";

        switch (categoryChoice) {
            case 1:
                categoryName = "Burgers";
                break;
            case 2:
                categoryName = "Frozen Custard";
                break;
            case 3:
                categoryName = "Drinks";
                break;
            case 4:
                categoryName = "Beer";
                break;
            default:
                System.out.println("오류입니다.");
                return;
        }

        System.out.println("상품명을 입력하세요: ");
        String productName = sc.nextLine();
        System.out.println("상품 가격을 입력하세요: ");
        double productPrice = sc.nextDouble();
        sc.nextLine();
        System.out.println("상품 설명을 입력하세요: ");
        String productExplanation = sc.nextLine();

        Product product = new Product(productName, productPrice, productExplanation);
        List<Product> menu = categoryProducts.get(categoryName);
        if(menu == null){
            menu = new ArrayList<>();
            categoryProducts.put(categoryName,menu);
        }
        menu.add(product);
        System.out.println(productName + " 추가되었습니다.");
    }

    private static void removeProductFromMenu(Order order, Scanner sc) {
        System.out.println("카테고리를 선택하세요");
        System.out.println("1. Burgers");
        System.out.println("2. Frozen Custard");
        System.out.println("3. Drinks");
        System.out.println("4. Beer");
        int categoryChoice = sc.nextInt();
        sc.nextLine();

        String categoryName = "";

        switch (categoryChoice) {
            case 1:
                categoryName = "Burgers";
                break;
            case 2:
                categoryName = "Frozen Custard";
                break;
            case 3:
                categoryName = "Drinks";
                break;
            case 4:
                categoryName = "Beer";
                break;
            default:
                System.out.println("오류 입니다.");
                return;
        }
        List<Product> menu = categoryProducts.get(categoryName);
        if(menu != null){
            System.out.println("상품 목록");
            for (int i = 0; i < menu.size(); i++) {
                System.out.println(i + 1 + ". " + menu.get(i).getName());
            }

            System.out.println("삭제 번호를 입력해주세요.");
            int number = sc.nextInt();
            sc.nextLine();
            if(number >= 1 && number <= menu.size()){
                Product product = menu.remove(1 - number);
                System.out.println(product.getName() + " 삭제 되었습니다.");
            }else{
                System.out.println("유효하지 않은 번호입니다.");
            }
        }else{
            System.out.println("상품이 없습니다.");
        }
    }
    private static String getTimestamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    private static List<Product> createBurger() {
        List<Product> burger = new ArrayList<>();
        burger.add(new Product("맥도날드", 5.0, "맛있음"));
        burger.add(new Product("버거킹", 5.5, "맛있음"));
        burger.add(new Product("롯데리아", 4.5, "맛있음"));

        List<Product> addBurger = categoryProducts.get("Burgers");
        if(addBurger != null){
            burger.addAll(addBurger);
        }
        return burger;
    }

    private static List<Product> createForzenCustard() {
        List<Product> frozenCustard = new ArrayList<>();
        frozenCustard.add(new Product("소프트콘", 2.0, "맛있음"));
        frozenCustard.add(new Product("초코콘", 2.5, "맛있음"));
        frozenCustard.add(new Product("바닐라콘", 2.5, "맛있음"));

        List<Product> addFrozenCustard = categoryProducts.get("Frozen Custard");
        if(addFrozenCustard != null){
            frozenCustard.addAll(addFrozenCustard);
        }
        return frozenCustard;

    }

    private static List<Product> createDrinks() {
        List<Product> drinks = new ArrayList<>();
        drinks.add(new Product("콜라", 2.0, "맛있음"));
        drinks.add(new Product("사이다", 2.5, "맛있음"));
        drinks.add(new Product("제로추가", 0.5, "맛있음"));

        List<Product> addDrinks = categoryProducts.get("Drinks");
        if(addDrinks != null){
            drinks.addAll(addDrinks);
        }
        return drinks;
    }

    private static List<Product> createBeer() {
        List<Product> beer = new ArrayList<>();
        beer.add(new Product("생맥주", 2.0, "시원함"));
        beer.add(new Product("흑맥주", 2.5, "시원함"));
        beer.add(new Product("라거맥주", 2.5, "시원함"));
        List<Product> addBeer = categoryProducts.get("Beer");
        if(addBeer != null){
            beer.addAll(addBeer);
        }
        return beer;
    }
}

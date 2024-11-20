import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Введите строку в формате <ФИО> <ДД.ММ.ГГГГ>: ");
        String data = scanner.nextLine();
        Info info = new Info(data);
        String out = info.process();
        System.out.println(out);
    }
}

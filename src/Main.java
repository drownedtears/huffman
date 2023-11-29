import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Coder coder = new Coder();
        while(true) {
            System.out.println("Введите строку, чтобы получить кодировку Хаффмана и нажмите Enter");
            Scanner s = new Scanner(System.in);
            String initStr = s.nextLine();
            Map<String, Character> huffCode = coder.getHuffmanCode(initStr);
            String codedInitStr = coder.getCurStr();
            System.out.println(coder.codedStr);
            int a = 1;
        }

    }
}
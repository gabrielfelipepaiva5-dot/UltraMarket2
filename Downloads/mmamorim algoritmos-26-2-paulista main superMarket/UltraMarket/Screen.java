package UltraMarket;

import java.io.IOException;
import java.util.Scanner;

public class Screen {

    public static void clear() {

        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Código específico para Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
                // Código específico para Linux / Unix
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                System.out.println("Outro sistema operacional: " + os);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // le a opcao do menu; devolve -1 se nao for um numero
    public static int lerOpcao() {
        Scanner sc = new Scanner(System.in);
        String linha = sc.nextLine().trim();
        if (linha.matches("[0-9]{1,3}")) {
            return Integer.parseInt(linha);
        }
        return -1;
    }
    
    public static void pressEnterToContinue() {
        System.out.print("Pressione Enter para continuar...");
        try {
            System.in.read();
            // Clear remaining bytes (like \r\n on Windows) from the buffer
            System.in.skip(System.in.available());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

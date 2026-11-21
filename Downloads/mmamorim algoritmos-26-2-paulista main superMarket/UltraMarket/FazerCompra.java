package UltraMarket;

import java.util.Scanner;

public class FazerCompra {
    ListaCompras listaCompras;

    public FazerCompra() {
        listaCompras = new ListaCompras();
        listaCompras.carregarDados();
    }

    public void menu() {
        Screen.clear();
        System.out.println("====================");
        System.out.println("COMPRAS");
        System.out.println("====================");
        System.out.println("0) VOLTAR/SAIR");
        System.out.println("1) LISTAR");
        System.out.println("2) REGISTRAR COMPRA");
        System.out.println("3) REMOVER");
        System.out.println("____________________");
        Scanner sc = new Scanner(System.in);
        int op = sc.nextInt();
        if(op == 0) {
            return;
        }
        if(op == 1) {
            listar();
            Screen.pressEnterToContinue();
        }
        if(op == 2) {
            listaCompras.cadastrar();
        }
        if(op == 3) {
            listaCompras.remover();
        }
        menu();
    }

    public void listar() {
        System.out.println("====================");
        if (listaCompras.qtde == 0) {
            System.out.println("Nenhuma compra registrada!");
        } else {
            listaCompras.imprime();
        }
        System.out.println("====================");
    }


}
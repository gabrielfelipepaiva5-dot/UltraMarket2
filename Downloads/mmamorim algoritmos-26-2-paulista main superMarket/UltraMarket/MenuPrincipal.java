package UltraMarket;

import java.util.Scanner;

public class MenuPrincipal {

    public MenuPrincipal() {

    }

    public void mostrar() {
        Screen.clear();
        System.out.println("***************************");
        System.out.println("* ULTRA MARKET VERSAO 2.0 *");
        System.out.println("***************************");
        System.out.println("0) SAIR");
        System.out.println("1) CADASTRO DE CLIENTES");
        System.out.println("2) CADASTRO DE PRODUTOS");
        System.out.println("3) FAZER COMPRA");
        System.out.println("4) ADICIONAR CUPOM");
        Scanner sc = new Scanner(System.in);
        int op = sc.nextInt();
        if(op == 0) {
            return;
        }
        if(op == 1) {
            CadCliente cadCliente = new CadCliente();
            cadCliente.menu();
        }
        if(op == 2) {
            CadProduto cadProduto = new CadProduto();
            cadProduto.menu();
        }
        if(op == 3) {
            FazerCompra fazerCompra = new FazerCompra();
            fazerCompra.menu();
        }
        if(op == 4) {
            AddCupom addCupom = new AddCupom();
            addCupom.menu();
        }
        mostrar();
    }
}
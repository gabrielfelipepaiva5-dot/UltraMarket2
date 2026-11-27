package UltraMarket;

public class MenuPrincipal {

    public MenuPrincipal() {

    }

    public void mostrar() {
        Screen.clear();
        System.out.println("***************************");
        System.out.println("* ULTRA MARKET VERSAO 1.0 *");
        System.out.println("***************************");
        System.out.println("0) SAIR");
        System.out.println("1) PRODUTOS");
        System.out.println("2) CLIENTES");
        System.out.println("3) REALIZAR COMPRA");
        System.out.println("4) CONTROLE DE ESTOQUE");
        System.out.println("5) CUPONS DE DESCONTO");
        int op = Screen.lerOpcao();
        if(op == 0) {
            return;
        }
        if(op == 1) {
            CadProduto cadProduto = new CadProduto();
            cadProduto.menu();
        }
        if(op == 2) {
            CadCliente cadCliente = new CadCliente();
            cadCliente.menu();
        }
        if(op == 3) {
            FazerCompra fazerCompra = new FazerCompra();
            fazerCompra.menu();
        }
        if(op == 4) {
            ControleEstoque estoque = new ControleEstoque();
            estoque.menu();
        }
        if(op == 5) {
            AddCupom addCupom = new AddCupom();
            addCupom.menu();
        }
        if(op < 0 || op > 5) {
            System.out.println("Opcao invalida!");
            Screen.pressEnterToContinue();
        }
        mostrar();
    }
}
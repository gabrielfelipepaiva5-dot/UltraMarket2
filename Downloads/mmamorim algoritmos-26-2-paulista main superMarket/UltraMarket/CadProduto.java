package UltraMarket;

public class CadProduto {
    ListaProdutos listaProd;

    public CadProduto() {
        listaProd = new ListaProdutos();
        listaProd.carregarDados();
    }

    public void menu() {
        Screen.clear();
        System.out.println("====================");
        System.out.println("CADASTRO DE PRODUTOS");
        System.out.println("====================");
        System.out.println("0) VOLTAR");
        System.out.println("1) CADASTRAR PRODUTO");
        System.out.println("2) LISTAR PRODUTOS");
        System.out.println("3) BUSCAR PRODUTO");
        System.out.println("4) ALTERAR PRODUTO");
        System.out.println("5) REMOVER PRODUTO");
        int op = Screen.lerOpcao();
        if(op == 0) {
            return;
        }
        if(op == 1) {
            listaProd.cadastrar();
        }
        if(op == 2) {
            listar();
            Screen.pressEnterToContinue();
        }
        if(op == 3) {
            listaProd.buscar();
        }
        if(op == 4) {
            listaProd.alterar();
        }
        if(op == 5) {
            listaProd.remover();
        }
        if(op < 0 || op > 5) {
            System.out.println("Opcao invalida!");
            Screen.pressEnterToContinue();
        }
        menu();
    }

    public void listar() {
        System.out.println("====================");
        if (listaProd.qtde == 0) {
            System.out.println("Nenhum produto cadastrado!");
        } else {
            listaProd.imprime();
        }
        System.out.println("====================");
    }


}
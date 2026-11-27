package UltraMarket;

public class CadCliente {
    ListaClientes lista;

    public CadCliente() {
        lista = new ListaClientes();
        lista.carregarDados();
    }

    public void menu() {
        Screen.clear();
        System.out.println("====================");
        System.out.println("CADASTRO DE CLIENTES");
        System.out.println("====================");
        System.out.println("0) VOLTAR");
        System.out.println("1) CADASTRAR CLIENTE");
        System.out.println("2) LISTAR CLIENTES");
        System.out.println("3) BUSCAR CLIENTE");
        System.out.println("4) ALTERAR CLIENTE");
        System.out.println("5) REMOVER CLIENTE");
        int op = Screen.lerOpcao();
        if(op == 0) {
            return;
        }
        if(op == 1) {
            lista.cadastrar();
        }
        if(op == 2) {
            listar();
            Screen.pressEnterToContinue();
        }
        if(op == 3) {
            lista.buscar();
        }
        if(op == 4) {
            lista.alterar();
        }
        if(op == 5) {
            lista.remover();
        }
        if(op < 0 || op > 5) {
            System.out.println("Opcao invalida!");
            Screen.pressEnterToContinue();
        }
        menu();
    }

    public void listar() {
        System.out.println("====================");
        if (lista.qtde == 0) {
            System.out.println("Nenhum cliente cadastrado!");
        } else {
            lista.imprime();
        }
        System.out.println("====================");
    }


}
package UltraMarket;

import java.util.Scanner;

public class ControleEstoque {
    ListaProdutos produtos;

    public ControleEstoque() {
        produtos = new ListaProdutos();
        produtos.carregarDados();
    }

    public void menu() {
        Screen.clear();
        System.out.println("====================");
        System.out.println("CONTROLE DE ESTOQUE");
        System.out.println("====================");
        System.out.println("0) VOLTAR");
        System.out.println("1) LISTAR ESTOQUE");
        System.out.println("2) REPOR ESTOQUE");
        System.out.println("3) AJUSTAR ESTOQUE");
        int op = Screen.lerOpcao();
        if(op == 0) {
            return;
        }
        if(op == 1) {
            listar();
            Screen.pressEnterToContinue();
        }
        if(op == 2) {
            repor();
        }
        if(op == 3) {
            ajustar();
        }
        if(op < 0 || op > 3) {
            System.out.println("Opcao invalida!");
            Screen.pressEnterToContinue();
        }
        menu();
    }

    public void listar() {
        System.out.println("====================");
        if (produtos.qtde == 0) {
            System.out.println("Nenhum produto cadastrado!");
        } else {
            produtos.imprime();
        }
        System.out.println("====================");
    }

    // adiciona unidades ao estoque atual
    public void repor() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Repor Estoque");
        System.out.println("===================");
        produtos.imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Produto? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Reposicao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        int indice = produtos.indiceDoCodigo(entrada);
        if (indice == -1) {
            System.out.println("Codigo invalido! Digite so o codigo de um produto da lista.");
            Screen.pressEnterToContinue();
            return;
        }
        Produto p = produtos.lista[indice];
        System.out.println("Quantas unidades de " + p.nome + " deseja adicionar? (Enter sem digitar nada para cancelar)");
        String q = sc.nextLine().trim();
        if (q.isEmpty()) {
            System.out.println("Reposicao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!q.matches("[0-9]{1,6}") || Integer.parseInt(q) == 0) {
            System.out.println("Quantidade invalida! Digite so numeros maiores que zero.");
            Screen.pressEnterToContinue();
            return;
        }
        int antigo = p.estoque;
        p.estoque = p.estoque + Integer.parseInt(q);
        produtos.salvar();
        System.out.println("Estoque de " + p.nome + " atualizado: " + antigo + " -> " + p.estoque);
        Screen.pressEnterToContinue();
    }

    // define a quantidade exata do estoque
    public void ajustar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ajustar Estoque");
        System.out.println("===================");
        produtos.imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Produto? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Ajuste cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        int indice = produtos.indiceDoCodigo(entrada);
        if (indice == -1) {
            System.out.println("Codigo invalido! Digite so o codigo de um produto da lista.");
            Screen.pressEnterToContinue();
            return;
        }
        Produto p = produtos.lista[indice];
        System.out.println("Qual o novo estoque de " + p.nome + "? (Enter sem digitar nada para cancelar)");
        String q = sc.nextLine().trim();
        if (q.isEmpty()) {
            System.out.println("Ajuste cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!q.matches("[0-9]{1,6}")) {
            System.out.println("Quantidade invalida! Digite so numeros inteiros.");
            Screen.pressEnterToContinue();
            return;
        }
        int antigo = p.estoque;
        p.estoque = Integer.parseInt(q);
        produtos.salvar();
        System.out.println("Estoque de " + p.nome + " atualizado: " + antigo + " -> " + p.estoque);
        Screen.pressEnterToContinue();
    }
}
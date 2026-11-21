package UltraMarket;

import java.util.Scanner;

public class ListaProdutos {
    public Produto lista[];
    public int qtde;

    public ListaProdutos() {
        lista = new Produto[100];
        qtde = 0;
    }

    public void cadastrar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cadastro de Produto");
        System.out.println("===================");
        System.out.println("Qual o nome do Produto? (Enter sem digitar nada para cancelar)");
        String nome = sc.nextLine();
        if (nome.isEmpty()) {
            System.out.println("Cadastro cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        System.out.println("Qual o preco do Produto? (Enter sem digitar nada para cancelar)");
        String texto = sc.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("Cadastro cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!texto.matches("[0-9]+([.,][0-9]+)?")) {
            System.out.println("Preco invalido! Letras nao contam. Produto nao cadastrado.");
            Screen.pressEnterToContinue();
            return;
        }
        double preco = Double.parseDouble(texto.replace(',', '.'));
        Produto p = new Produto(nome, preco);
        adiciona(p);
        salvar();
        System.out.println("Produto " + nome + " cadastrado com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void adiciona(Produto p) {
        lista[qtde] = p;
        qtde++;
    }

    public void remover() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Remover Produto");
        System.out.println("===================");
        imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Produto? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine();
        if (entrada.isEmpty()) {
            System.out.println("Remocao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        int codigo = Integer.parseInt(entrada);
        String nome = lista[codigo].nome;
        removerItem(codigo);
        salvar();
        System.out.println("Produto " + nome + " removido com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void alterar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Alterar Produto");
        System.out.println("===================");
        imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Produto? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine();
        if (entrada.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        int codigo = Integer.parseInt(entrada);
        System.out.println("Qual o novo nome do Produto? (Enter sem digitar nada para cancelar)");
        String nome = sc.nextLine();
        if (nome.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        System.out.println("Qual o novo preco do Produto? (Enter sem digitar nada para cancelar)");
        String texto = sc.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!texto.matches("[0-9]+([.,][0-9]+)?")) {
            System.out.println("Preco invalido! Letras nao contam. Produto nao alterado.");
            Screen.pressEnterToContinue();
            return;
        }
        double preco = Double.parseDouble(texto.replace(',', '.'));
        lista[codigo] = new Produto(nome, preco);
        salvar();
        System.out.println("O produto de codigo " + codigo + " teve suas informacoes alteradas");
        Screen.pressEnterToContinue();
    }

    public void removerItem(int codigo) {
        for (int i = codigo; i < qtde; i++) {
            lista[i] = lista[i + 1];
        }
        qtde--;
    }

    public void imprime() {
        for (int i = 0; i < qtde; i++) {
            lista[i].imprime(i);
        }
    }

    public void salvar() {
        Produto prods[] = new Produto[qtde];
        for (int i = 0; i < qtde; i++) {
            prods[i] = lista[i];
        }
        CsvUtil.salvar(prods, "./UltraMarket/produtos.csv");
    }

    public void carregarDados() {
        Produto[] prods = CsvUtil.ler("./UltraMarket/produtos.csv", Produto.class);
        for (int i = 0; i < prods.length; i++) {
            lista[i] = prods[i];
        }
        qtde = prods.length;
    }

}
package UltraMarket;

import java.util.Scanner;

public class ListaProdutos {
    public Produto lista[];
    public int qtde;

    public ListaProdutos() {
        lista = new Produto[100];
        qtde = 0;
    }

    public int proximoCodigo() {
        int maior = 0;
        for (int i = 0; i < qtde; i++) {
            if (lista[i].codigo > maior) {
                maior = lista[i].codigo;
            }
        }
        return maior + 1;
    }

    // devolve a posicao do produto na lista, ou -1 se nao achar
    public int buscarIndice(int codigo) {
        for (int i = 0; i < qtde; i++) {
            if (lista[i].codigo == codigo) {
                return i;
            }
        }
        return -1;
    }

    // transforma o texto digitado em posicao da lista; -1 se for invalido
    public int indiceDoCodigo(String entrada) {
        if (!entrada.matches("[0-9]{1,9}")) {
            return -1;
        }
        return buscarIndice(Integer.parseInt(entrada));
    }

    public void cadastrar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cadastro de Produto");
        System.out.println("===================");
        if (qtde >= lista.length) {
            System.out.println("Limite de produtos atingido.");
            Screen.pressEnterToContinue();
            return;
        }
        System.out.println("Qual o nome do Produto? (Enter sem digitar nada para cancelar)");
        String nome = sc.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Cadastro cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        if (nome.contains(";")) {
            System.out.println("Nome invalido! Nao use ponto e virgula. Produto nao cadastrado.");
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
        System.out.println("Qual a quantidade em estoque? (Enter sem digitar nada para cancelar)");
        String est = sc.nextLine().trim();
        if (est.isEmpty()) {
            System.out.println("Cadastro cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!est.matches("[0-9]{1,6}")) {
            System.out.println("Estoque invalido! Digite so numeros inteiros. Produto nao cadastrado.");
            Screen.pressEnterToContinue();
            return;
        }
        double preco = Double.parseDouble(texto.replace(',', '.'));
        Produto p = new Produto(proximoCodigo(), nome, preco, Integer.parseInt(est));
        adiciona(p);
        salvar();
        System.out.println("Produto " + nome + " cadastrado com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void adiciona(Produto p) {
        lista[qtde] = p;
        qtde++;
    }

    public void buscar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Buscar Produto");
        System.out.println("===================");
        System.out.println("Digite o codigo ou parte do nome (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Busca cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        System.out.println("===================");
        int achados = 0;
        for (int i = 0; i < qtde; i++) {
            Produto p = lista[i];
            if (String.valueOf(p.codigo).equals(entrada) || p.nome.toLowerCase().contains(entrada.toLowerCase())) {
                p.imprime();
                achados++;
            }
        }
        if (achados == 0) {
            System.out.println("Nenhum produto encontrado!");
        }
        System.out.println("===================");
        Screen.pressEnterToContinue();
    }

    public void remover() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Remover Produto");
        System.out.println("===================");
        imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Produto? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Remocao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        int indice = indiceDoCodigo(entrada);
        if (indice == -1) {
            System.out.println("Codigo invalido! Digite so o codigo de um produto da lista.");
            Screen.pressEnterToContinue();
            return;
        }
        String nome = lista[indice].nome;
        removerItem(indice);
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
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        int indice = indiceDoCodigo(entrada);
        if (indice == -1) {
            System.out.println("Codigo invalido! Digite so o codigo de um produto da lista.");
            Screen.pressEnterToContinue();
            return;
        }
        System.out.println("Qual o novo nome do Produto? (Enter sem digitar nada para cancelar)");
        String nome = sc.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        if (nome.contains(";")) {
            System.out.println("Nome invalido! Nao use ponto e virgula. Produto nao alterado.");
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
        System.out.println("Qual o novo estoque? (Enter sem digitar nada para cancelar)");
        String est = sc.nextLine().trim();
        if (est.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!est.matches("[0-9]{1,6}")) {
            System.out.println("Estoque invalido! Digite so numeros inteiros. Produto nao alterado.");
            Screen.pressEnterToContinue();
            return;
        }
        Produto p = lista[indice];
        p.nome = nome;
        p.preco = Double.parseDouble(texto.replace(',', '.'));
        p.estoque = Integer.parseInt(est);
        salvar();
        System.out.println("O produto de codigo " + p.codigo + " teve suas informacoes alteradas");
        Screen.pressEnterToContinue();
    }

    public void removerItem(int indice) {
        for (int i = indice; i < qtde - 1; i++) {
            lista[i] = lista[i + 1];
        }
        lista[qtde - 1] = null;
        qtde--;
    }

    public void imprime() {
        for (int i = 0; i < qtde; i++) {
            lista[i].imprime();
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
        qtde = Math.min(prods.length, lista.length);
        for (int i = 0; i < qtde; i++) {
            lista[i] = prods[i];
        }
        // produtos salvos antes de existir codigo ganham um codigo
        for (int i = 0; i < qtde; i++) {
            if (lista[i].codigo == 0) {
                lista[i].codigo = proximoCodigo();
            }
        }
    }

}
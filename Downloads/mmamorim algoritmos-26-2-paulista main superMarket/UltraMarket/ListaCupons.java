package UltraMarket;

import java.util.Scanner;

public class ListaCupons {
    public Cupom lista[];
    public int qtde;

    public ListaCupons() {
        lista = new Cupom[100];
        qtde = 0;
    }

    public void cadastrar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cadastro de Cupom");
        System.out.println("===================");
        System.out.println("Qual o codigo do cupom? Ex: PROMO10 (Enter sem digitar nada para cancelar)");
        String codigo = sc.nextLine().trim().toUpperCase();
        if (codigo.isEmpty()) {
            System.out.println("Cadastro cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        if (codigo.contains(";")) {
            System.out.println("Codigo invalido! Nao use ponto e virgula. Cupom nao cadastrado.");
            Screen.pressEnterToContinue();
            return;
        }
        if (buscar(codigo) != null) {
            System.out.println("Ja existe um cupom com esse codigo! Cupom nao cadastrado.");
            Screen.pressEnterToContinue();
            return;
        }
        System.out.println("Qual o desconto em %? (Enter sem digitar nada para cancelar)");
        String texto = sc.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("Cadastro cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!texto.matches("[0-9]+([.,][0-9]+)?")) {
            System.out.println("Desconto invalido! Letras nao contam. Cupom nao cadastrado.");
            Screen.pressEnterToContinue();
            return;
        }
        double desconto = Double.parseDouble(texto.replace(',', '.'));
        if (desconto <= 0 || desconto > 100) {
            System.out.println("Desconto invalido! Use um valor de 1 a 100. Cupom nao cadastrado.");
            Screen.pressEnterToContinue();
            return;
        }
        adiciona(new Cupom(codigo, desconto));
        salvar();
        System.out.println("Cupom " + codigo + " cadastrado com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void adiciona(Cupom c) {
        lista[qtde] = c;
        qtde++;
    }

    public void remover() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Remover Cupom");
        System.out.println("===================");
        imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Cupom? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Remocao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!entrada.matches("[0-9]{1,5}") || Integer.parseInt(entrada) >= qtde) {
            System.out.println("Codigo invalido! Digite so o numero de um cupom da lista.");
            Screen.pressEnterToContinue();
            return;
        }
        int codigo = Integer.parseInt(entrada);
        String nome = lista[codigo].codigo;
        removerItem(codigo);
        salvar();
        System.out.println("Cupom " + nome + " removido com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void removerItem(int codigo) {
        for (int i = codigo; i < qtde - 1; i++) {
            lista[i] = lista[i + 1];
        }
        qtde--;
    }

    // procura pelo texto do cupom; devolve null se nao achar
    public Cupom buscar(String codigo) {
        for (int i = 0; i < qtde; i++) {
            if (lista[i].codigo.equalsIgnoreCase(codigo)) {
                return lista[i];
            }
        }
        return null;
    }

    public void imprime() {
        for (int i = 0; i < qtde; i++) {
            lista[i].imprime(i);
        }
    }

    public void salvar() {
        Cupom cupons[] = new Cupom[qtde];
        for (int i = 0; i < qtde; i++) {
            cupons[i] = lista[i];
        }
        CsvUtil.salvar(cupons, "./UltraMarket/cupons.csv");
    }

    public void carregarDados() {
        Cupom[] cupons = CsvUtil.ler("./UltraMarket/cupons.csv", Cupom.class);
        for (int i = 0; i < cupons.length; i++) {
            lista[i] = cupons[i];
        }
        qtde = cupons.length;
    }
}
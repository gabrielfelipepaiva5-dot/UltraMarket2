package UltraMarket;

import java.util.Scanner;

public class ListaClientes {
    public Cliente lista[];
    public int qtde;

    public ListaClientes() {
        lista = new Cliente[100];
        qtde = 0;
    }

    public void cadastrar() {
        System.out.println("Cadastro de Cliente");
        System.out.println("===================");
        Cliente c = new Cliente();
        c.lerDados();
        if (c.cpf.isEmpty()) {
            System.out.println("Cadastro cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        adiciona(c);
        salvar();
        System.out.println("Cliente " + c.nome + " cadastrado com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void adiciona(Cliente c) {
        lista[qtde] = c;
        qtde++;
    }

    public void remover() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Remover Cliente");
        System.out.println("===================");
        imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Cliente? (Enter sem digitar nada para cancelar)");
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
        System.out.println("Cliente " + nome + " removido com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void alterar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("ALTERAR Cliente");
        System.out.println("===================");
        imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Cliente? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine();
        if (entrada.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        int codigo = Integer.parseInt(entrada);
        Cliente novo = new Cliente();
        novo.lerDados();
        if (novo.cpf.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        lista[codigo] = novo;
        salvar();
        System.out.println("O cliente de codigo " + codigo + " teve suas informacoes alteradas");
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
        Cliente clientes[] = new Cliente[qtde];
        for (int i = 0; i < qtde; i++) {
            clientes[i] = lista[i];
        }
        CsvUtil.salvar(clientes, "./UltraMarket/clientes.csv");
    }

    public void carregarDados() {
        Cliente[] clientes = CsvUtil.ler("./UltraMarket/clientes.csv", Cliente.class);
        for (int i = 0; i < clientes.length; i++) {
            lista[i] = clientes[i];
        }
        qtde = clientes.length;
    }

}
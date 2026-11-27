package UltraMarket;

import java.util.Scanner;

public class ListaClientes {
    public Cliente lista[];
    public int qtde;

    public ListaClientes() {
        lista = new Cliente[100];
        qtde = 0;
    }

    // devolve a posicao do cliente com esse CPF, ou -1 se nao achar
    public int buscarCpf(String cpf) {
        for (int i = 0; i < qtde; i++) {
            if (lista[i].cpf.equals(cpf)) {
                return i;
            }
        }
        return -1;
    }

    // transforma o texto digitado em posicao da lista; -1 se for invalido
    public int indiceDoCodigo(String entrada) {
        if (!entrada.matches("[0-9]{1,5}")) {
            return -1;
        }
        int codigo = Integer.parseInt(entrada);
        if (codigo >= qtde) {
            return -1;
        }
        return codigo;
    }

    // devolve a mensagem de erro, ou null se os dados estao ok
    // ignorar = posicao do cliente que esta sendo alterado (-1 se for novo)
    public String validar(Cliente c, int ignorar) {
        if (!c.cpf.matches("[0-9]{11}")) {
            return "CPF invalido! Digite 11 numeros, sem pontos ou tracos.";
        }
        if (c.nome.isEmpty()) {
            return "Nome invalido! O nome nao pode ficar vazio.";
        }
        if (c.nome.contains(";") || c.telefone.contains(";") || c.email.contains(";")) {
            return "Nao use ponto e virgula nos dados.";
        }
        int existente = buscarCpf(c.cpf);
        if (existente != -1 && existente != ignorar) {
            return "Ja existe um cliente com esse CPF!";
        }
        return null;
    }

    public void cadastrar() {
        System.out.println("Cadastro de Cliente");
        System.out.println("===================");
        if (qtde >= lista.length) {
            System.out.println("Limite de clientes atingido.");
            Screen.pressEnterToContinue();
            return;
        }
        Cliente c = new Cliente();
        c.lerDados();
        if (c.cpf.isEmpty()) {
            System.out.println("Cadastro cancelado.");
            Screen.pressEnterToContinue();
            return;
        }
        String erro = validar(c, -1);
        if (erro != null) {
            System.out.println(erro + " Cliente nao cadastrado.");
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

    public void buscar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Buscar Cliente");
        System.out.println("===================");
        System.out.println("Digite o CPF ou parte do nome (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Busca cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        System.out.println("===================");
        int achados = 0;
        for (int i = 0; i < qtde; i++) {
            Cliente c = lista[i];
            if (c.cpf.equals(entrada) || c.nome.toLowerCase().contains(entrada.toLowerCase())) {
                c.imprime(i);
                achados++;
            }
        }
        if (achados == 0) {
            System.out.println("Nenhum cliente encontrado!");
        }
        System.out.println("===================");
        Screen.pressEnterToContinue();
    }

    public void remover() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Remover Cliente");
        System.out.println("===================");
        imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Cliente? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Remocao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        int codigo = indiceDoCodigo(entrada);
        if (codigo == -1) {
            System.out.println("Codigo invalido! Digite so o numero de um cliente da lista.");
            Screen.pressEnterToContinue();
            return;
        }
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
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        int codigo = indiceDoCodigo(entrada);
        if (codigo == -1) {
            System.out.println("Codigo invalido! Digite so o numero de um cliente da lista.");
            Screen.pressEnterToContinue();
            return;
        }
        Cliente novo = new Cliente();
        novo.lerDados();
        if (novo.cpf.isEmpty()) {
            System.out.println("Alteracao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        String erro = validar(novo, codigo);
        if (erro != null) {
            System.out.println(erro + " Cliente nao alterado.");
            Screen.pressEnterToContinue();
            return;
        }
        lista[codigo] = novo;
        salvar();
        System.out.println("O cliente de codigo " + codigo + " teve suas informacoes alteradas");
        Screen.pressEnterToContinue();
    }

    public void removerItem(int codigo) {
        for (int i = codigo; i < qtde - 1; i++) {
            lista[i] = lista[i + 1];
        }
        lista[qtde - 1] = null;
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
        qtde = Math.min(clientes.length, lista.length);
        for (int i = 0; i < qtde; i++) {
            lista[i] = clientes[i];
        }
    }

}
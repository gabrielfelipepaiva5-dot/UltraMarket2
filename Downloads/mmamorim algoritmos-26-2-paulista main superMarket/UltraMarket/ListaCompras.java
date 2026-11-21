package UltraMarket;

import java.util.Scanner;

public class ListaCompras {
    public Compra lista[];
    public int qtde;
    ListaProdutos produtos;
    ListaCupons cupons;

    public ListaCompras() {
        lista = new Compra[100];
        qtde = 0;
        produtos = new ListaProdutos();
        produtos.carregarDados();
        cupons = new ListaCupons();
        cupons.carregarDados();
    }

    public void cadastrar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Registrar Compra");
        System.out.println("===================");
        if (produtos.qtde == 0) {
            System.out.println("Nenhum produto cadastrado! Cadastre um produto antes.");
            Screen.pressEnterToContinue();
            return;
        }
        produtos.imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo do Produto? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Compra cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!entrada.matches("[0-9]{1,5}") || Integer.parseInt(entrada) >= produtos.qtde) {
            System.out.println("Codigo invalido! Digite so o numero de um produto da lista.");
            Screen.pressEnterToContinue();
            return;
        }
        Produto p = produtos.lista[Integer.parseInt(entrada)];
        double valor = p.preco;

        System.out.println("Cupom? (Enter se nao tiver)");
        String codCupom = sc.nextLine().trim();
        String nomeCupom = "nenhum";
        if (!codCupom.isEmpty()) {
            Cupom c = cupons.buscar(codCupom);
            if (c == null) {
                System.out.println("Cupom nao encontrado! A compra segue sem desconto.");
            } else {
                valor = valor - (valor * c.desconto / 100.0);
                nomeCupom = c.codigo;
                System.out.println("Cupom aplicado: " + c.desconto + "% de desconto!");
            }
        }

        System.out.println("Pagamento: 1) CREDITO 2) DEBITO 3) PIX 4) DINHEIRO (Enter sem digitar nada para cancelar)");
        String op = sc.nextLine().trim();
        if (op.isEmpty()) {
            System.out.println("Compra cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        String pagamento = ".";
        if(op.equals("1")) {
            pagamento = "CREDITO";
        }
        if(op.equals("2")) {
            pagamento = "DEBITO";
        }
        if(op.equals("3")) {
            pagamento = "PIX";
        }
        if(op.equals("4")) {
            pagamento = "DINHEIRO";
        }
        if(pagamento.equals(".")) {
            System.out.println("Pagamento invalido! Compra nao registrada.");
            Screen.pressEnterToContinue();
            return;
        }

        adiciona(new Compra(p.nome, valor, nomeCupom, pagamento));
        salvar();
        System.out.println("Compra de " + p.nome + " registrada com sucesso! Total: R$ " + String.format("%.2f", valor));
        Screen.pressEnterToContinue();
    }

    public void adiciona(Compra c) {
        lista[qtde] = c;
        qtde++;
    }

    public void remover() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Remover Compra");
        System.out.println("===================");
        imprime();
        System.out.println("===================");
        System.out.println("Qual o codigo da Compra? (Enter sem digitar nada para cancelar)");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Remocao cancelada.");
            Screen.pressEnterToContinue();
            return;
        }
        if (!entrada.matches("[0-9]{1,5}") || Integer.parseInt(entrada) >= qtde) {
            System.out.println("Codigo invalido! Digite so o numero de uma compra da lista.");
            Screen.pressEnterToContinue();
            return;
        }
        int codigo = Integer.parseInt(entrada);
        removerItem(codigo);
        salvar();
        System.out.println("Compra de codigo " + codigo + " removida com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void removerItem(int codigo) {
        for (int i = codigo; i < qtde - 1; i++) {
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
        Compra compras[] = new Compra[qtde];
        for (int i = 0; i < qtde; i++) {
            compras[i] = lista[i];
        }
        CsvUtil.salvar(compras, "./UltraMarket/compras.csv");
    }

    public void carregarDados() {
        Compra[] compras = CsvUtil.ler("./UltraMarket/compras.csv", Compra.class);
        for (int i = 0; i < compras.length; i++) {
            lista[i] = compras[i];
        }
        qtde = compras.length;
    }
}
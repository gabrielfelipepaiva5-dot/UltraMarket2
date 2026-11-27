package UltraMarket;

import java.util.Scanner;

public class FazerCompra {
    ListaClientes clientes;
    ListaProdutos produtos;
    ListaCupons cupons;

    Cliente cliente;
    Cupom cupom;
    Produto[] itens;
    int[] quantidades;
    int qtdeItens;

    public FazerCompra() {
        clientes = new ListaClientes();
        clientes.carregarDados();
        produtos = new ListaProdutos();
        produtos.carregarDados();
        cupons = new ListaCupons();
        cupons.carregarDados();
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        cliente = null;
        cupom = null;
        itens = new Produto[100];
        quantidades = new int[100];
        qtdeItens = 0;

        Screen.clear();
        System.out.println("====================");
        System.out.println("REALIZAR COMPRA");
        System.out.println("====================");
        if (produtos.qtde == 0) {
            System.out.println("Nenhum produto cadastrado! Cadastre produtos antes de comprar.");
            Screen.pressEnterToContinue();
            return;
        }
        System.out.println("Sou cadastrado? (S/N) (Enter sem digitar nada para cancelar)");
        String resp = sc.nextLine().trim();
        if (resp.isEmpty()) {
            cancelada();
            return;
        }
        if (resp.equalsIgnoreCase("S")) {
            if (!buscarCliente()) {
                return;
            }
        } else if (!resp.equalsIgnoreCase("N")) {
            System.out.println("Resposta invalida! Digite S ou N.");
            Screen.pressEnterToContinue();
            return;
        }
        adicionarItens();
        if (qtdeItens == 0) {
            System.out.println("Nenhum item no carrinho.");
            cancelada();
            return;
        }
        aplicarCupom();
        pagar();
    }

    // devolve false se o cliente nao foi encontrado ou se a compra foi cancelada
    public boolean buscarCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o CPF do cliente (Enter sem digitar nada para cancelar)");
        String cpf = sc.nextLine().trim();
        if (cpf.isEmpty()) {
            cancelada();
            return false;
        }
        int indice = clientes.buscarCpf(cpf);
        if (indice == -1) {
            System.out.println("Cliente nao encontrado! Cadastre o cliente ou compre como nao cadastrado.");
            Screen.pressEnterToContinue();
            return false;
        }
        cliente = clientes.lista[indice];
        System.out.println("Cliente encontrado: " + cliente.nome);
        Screen.pressEnterToContinue();
        return true;
    }

    public void adicionarItens() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            Screen.clear();
            System.out.println("====================");
            System.out.println("PRODUTOS DISPONIVEIS");
            System.out.println("====================");
            produtos.imprime();
            System.out.println("====================");
            mostrarCarrinho();
            System.out.println("Codigo do produto (Enter sem digitar nada para finalizar os itens):");
            String entrada = sc.nextLine().trim();
            if (entrada.isEmpty()) {
                return;
            }
            int indice = produtos.indiceDoCodigo(entrada);
            if (indice == -1) {
                System.out.println("Produto nao encontrado!");
                Screen.pressEnterToContinue();
                continue;
            }
            Produto p = produtos.lista[indice];
            System.out.println("Quantidade de " + p.nome + "?");
            String q = sc.nextLine().trim();
            if (!q.matches("[0-9]{1,6}") || Integer.parseInt(q) == 0) {
                System.out.println("Quantidade invalida! Digite so numeros maiores que zero.");
                Screen.pressEnterToContinue();
                continue;
            }
            adicionarAoCarrinho(p, Integer.parseInt(q));
        }
    }

    public void adicionarAoCarrinho(Produto p, int qtd) {
        int posicao = -1;
        for (int i = 0; i < qtdeItens; i++) {
            if (itens[i] == p) {
                posicao = i;
            }
        }
        int jaNoCarrinho = 0;
        if (posicao != -1) {
            jaNoCarrinho = quantidades[posicao];
        }
        int disponivel = p.estoque - jaNoCarrinho;
        if (qtd > disponivel) {
            System.out.println("Estoque insuficiente! Disponivel: " + disponivel + ". Item nao adicionado.");
            Screen.pressEnterToContinue();
            return;
        }
        if (posicao == -1) {
            itens[qtdeItens] = p;
            quantidades[qtdeItens] = qtd;
            qtdeItens++;
        } else {
            quantidades[posicao] = quantidades[posicao] + qtd;
        }
        System.out.println(qtd + "x " + p.nome + " adicionado ao carrinho!");
        Screen.pressEnterToContinue();
    }

    public void mostrarCarrinho() {
        System.out.println("CARRINHO");
        if (qtdeItens == 0) {
            System.out.println("(vazio)");
        }
        for (int i = 0; i < qtdeItens; i++) {
            System.out.println(quantidades[i] + "x " + itens[i].nome + " | R$ " + fmt(itens[i].preco * quantidades[i]));
        }
        System.out.println("Subtotal: R$ " + fmt(subtotal()));
        System.out.println("====================");
    }

    public double subtotal() {
        double soma = 0;
        for (int i = 0; i < qtdeItens; i++) {
            soma = soma + itens[i].preco * quantidades[i];
        }
        return soma;
    }

    public double desconto() {
        if (cupom == null) {
            return 0;
        }
        return subtotal() * cupom.desconto / 100.0;
    }

    public double total() {
        return subtotal() - desconto();
    }

    public String fmt(double valor) {
        return String.format("%.2f", valor);
    }

    public void aplicarCupom() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cupom? (Enter se nao tiver)");
        String codigo = sc.nextLine().trim();
        if (codigo.isEmpty()) {
            return;
        }
        cupom = cupons.buscar(codigo);
        if (cupom == null) {
            System.out.println("Cupom nao encontrado! A compra segue sem desconto.");
        } else {
            System.out.println("Cupom aplicado: " + cupom.desconto + "% de desconto!");
        }
        Screen.pressEnterToContinue();
    }

    public void pagar() {
        Scanner sc = new Scanner(System.in);
        Screen.clear();
        System.out.println("====================");
        System.out.println("PAGAMENTO");
        System.out.println("====================");
        System.out.println("Total a pagar: R$ " + fmt(total()));
        System.out.println("1) CREDITO");
        System.out.println("2) DEBITO");
        System.out.println("3) PIX");
        System.out.println("4) DINHEIRO");
        System.out.println("(Enter sem digitar nada para cancelar a compra)");
        String op = sc.nextLine().trim();
        if (op.isEmpty()) {
            cancelada();
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
            System.out.println("Pagamento invalido! Compra nao realizada.");
            Screen.pressEnterToContinue();
            return;
        }
        finalizar(pagamento);
    }

    public void finalizar(String pagamento) {
        recibo(pagamento);
        // baixa no estoque e salva no CSV
        for (int i = 0; i < qtdeItens; i++) {
            itens[i].estoque = itens[i].estoque - quantidades[i];
        }
        produtos.salvar();
        System.out.println("Compra finalizada com sucesso!");
        Screen.pressEnterToContinue();
    }

    public void recibo(String pagamento) {
        Screen.clear();
        System.out.println("====================");
        System.out.println("RECIBO");
        System.out.println("====================");
        if (cliente == null) {
            System.out.println("Cliente: nao cadastrado");
        } else {
            System.out.println("Cliente: " + cliente.nome + " | CPF: " + cliente.cpf);
        }
        System.out.println("====================");
        for (int i = 0; i < qtdeItens; i++) {
            System.out.println(quantidades[i] + "x " + itens[i].nome
                    + " | valor unitario: R$ " + fmt(itens[i].preco)
                    + " | subtotal: R$ " + fmt(itens[i].preco * quantidades[i]));
        }
        System.out.println("====================");
        System.out.println("Subtotal: R$ " + fmt(subtotal()));
        if (cupom != null) {
            System.out.println("Cupom " + cupom.codigo + " (" + cupom.desconto + "%): -R$ " + fmt(desconto()));
        }
        System.out.println("TOTAL: R$ " + fmt(total()));
        System.out.println("Pagamento: " + pagamento);
        System.out.println("====================");
    }

    public void cancelada() {
        System.out.println("Compra cancelada.");
        Screen.pressEnterToContinue();
    }
}
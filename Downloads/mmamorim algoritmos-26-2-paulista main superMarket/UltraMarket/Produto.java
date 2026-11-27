package UltraMarket;

public class Produto {
    int codigo;
    String nome;
    double preco;
    int estoque;

    public Produto() {

    }

    public Produto(int codigo, String nome, double preco, int estoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public void imprime() {
        System.out.println("COD: " + codigo + " | nome: " + nome + " | preco: " + preco + " | estoque: " + estoque);
    }
}

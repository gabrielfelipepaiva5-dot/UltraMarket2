package UltraMarket;

public class Cupom {
    String codigo;
    double desconto; // percentual, ex: 10 = 10%

    public Cupom() {

    }

    public Cupom(String codigo, double desconto) {
        this.codigo = codigo;
        this.desconto = desconto;
    }

    public void imprime(int indice) {
        System.out.println("COD: " + indice + " | cupom: " + codigo + " | desconto: " + desconto + "%");
    }
}
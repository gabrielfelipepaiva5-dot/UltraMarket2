package UltraMarket;

public class Compra {
    String produto;
    double valor;
    String cupom;
    String pagamento;

    public Compra() {

    }

    public Compra(String produto, double valor, String cupom, String pagamento) {
        this.produto = produto;
        this.valor = valor;
        this.cupom = cupom;
        this.pagamento = pagamento;
    }

    public void imprime(int codigo) {
        System.out.println("COD: "+codigo+" | produto: "+produto+" | valor: "+valor+" | cupom: "+cupom+" | pagamento: "+pagamento);
    }
}
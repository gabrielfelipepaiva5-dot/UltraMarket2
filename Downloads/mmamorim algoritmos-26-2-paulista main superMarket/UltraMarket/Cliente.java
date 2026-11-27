package UltraMarket;

import java.util.Scanner;

public class Cliente {
    String cpf;
    String nome;
    String telefone;
    String email;

    public Cliente() {

    }

    public Cliente(String cpf, String nome, String telefone, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public void imprime() {
        System.out.println("CPF: " + cpf + " | nome: " + nome + " | telefone: " + telefone + " | email: " + email);
    }

    public void imprime(int codigo) {
        System.out.println("COD: " + codigo + " | CPF: " + cpf + " | nome: " + nome + " | telefone: " + telefone + " | email: " + email);
    }

    public void lerDados() {
        Scanner sc = new Scanner(System.in);
        System.out.println("LER DADOS DO CLIENTE");
        System.out.println("(Aperte Enter sem digitar nada no CPF para cancelar)");
        System.out.println("====================");
        System.out.println("Qual o CPF? (11 numeros)");
        this.cpf = sc.nextLine().trim();
        if (this.cpf.isEmpty()) {
            return;
        }
        System.out.println("Qual o nome?");
        this.nome = sc.nextLine().trim();
        System.out.println("Qual o telefone?");
        this.telefone = sc.nextLine().trim();
        System.out.println("Qual o email?");
        this.email = sc.nextLine().trim();
    }
}
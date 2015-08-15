package br.senac.tads.pi3.fabiosousa.exercicio01;

// @author fabio.ssanto10
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Exercicio01 {

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);

        System.out.println("Cadastro de Contatos");
        System.out.println("Nome:");
        String nome = leitor.nextLine();
        System.out.println("Data de Nascimento:");
        String dataNasc = leitor.nextLine();
        System.out.println("Telefone:");
        String tel = leitor.nextLine();
        System.out.println("E-mail:");
        String email = leitor.nextLine();

        while (!email.contains("@")) {
            System.out.println("E-mail inválido, digite novamente: ");
            email = leitor.nextLine();
        }

        System.out.println("Nome: " + nome + "\n"
                + "Data de Nascimento: " + dataNasc + "\n"
                + "E-mail: " + email + "\n"
                + "Telefone: " + tel);

    }
}

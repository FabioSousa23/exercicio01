package br.senac.tads.pi3.fabiosousa.exercicio01;

// @author fabio.ssanto10
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Exercicio01 {

    public static void main(String[] args) {

        Connection con = null;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql:", "", "");
            System.out.println("Conectado.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Classe não encontrada, adicione o driver nas bibliotecas.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
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
        
        try {
            stmt.execute("INSERT INTO Contatos VALUES(NEWID(),'" + nome +"','"+dataNasc+"','"+
                    tel+"','"+email+"')");
        } catch (SQLException ex) {
            System.out.println("Erro:"+ex.getMessage());
        }
    }
}

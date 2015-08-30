/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ftsuda.integracaojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * baseado em
 * http://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
 *
 * @author Fernando
 * @author Fabio Sousa
 * @author Vinicius Viana
 */
public class Exercicio01 {

    /**
     * @param args the command line arguments
     */
    private Connection obterConexao() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        // Passo 1: Registrar driver JDBC.
        Class.forName("org.apache.derby.jdbc.ClientDataSource");

        // Passo 2: Abrir a conexão
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/agendabd;SecurityMechanism=3",
                "app", // usuario
                "app"); // senha
        return conn;
    }

    public void listarPessoas() {
        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT ID_PESSOA, NM_PESSOA, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL FROM TB_PESSOA";
        try {
            conn = obterConexao();
            stmt = conn.createStatement();
            ResultSet resultados = stmt.executeQuery(sql);

            DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

            while (resultados.next()) {
                Long id = resultados.getLong("ID_PESSOA");
                String nome = resultados.getString("NM_PESSOA");
                Date dataNasc = resultados.getDate("DT_NASCIMENTO");
                String email = resultados.getString("VL_EMAIL");
                String telefone = resultados.getString("VL_TELEFONE");
                System.out.println(String.valueOf(id) + ", " + nome + ", " + formatadorData.format(dataNasc) + ", " + email + ", " + telefone);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void incluirPessoa() {
        PreparedStatement stmt = null;
        Connection conn = null;

        String nome;
        Date dataNasc;
        String email;
        String telefone;

        // ENTRADA DE DADOS
        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite o nome da pessoa: ");
        nome = entrada.nextLine();

        System.out.print("Digite a data de nascimento no formato dd/mm/aaaa: ");
        String strDataNasc = entrada.nextLine();
        DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataNasc = formatadorData.parse(strDataNasc);
        } catch (ParseException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
            dataNasc = new Date();
        }
        System.out.print("Digite o telefone no formato 99 99999-9999: ");
        telefone = entrada.nextLine();

        System.out.print("Digite o e-mail: ");
        email = entrada.nextLine();

        String sql = "INSERT INTO TB_PESSOA (NM_PESSOA, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL) VALUES (?, ?, ?, ?)";
        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setDate(2, new java.sql.Date(dataNasc.getTime()));
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            //stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
            System.out.println("Registro incluido com sucesso.");

        } catch (SQLException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void excluirPessoa(String nome, String email) {

        PreparedStatement stmt = null;
        Connection conn = null;
        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite o nome e o email para exclusão na agenda\n");
        System.out.println("Digite o nome da Pessoa");
        nome = leitor.nextLine();
        System.out.println("Digite o Email");
        email = leitor.nextLine();
        String sql = ("DELETE FROM TB_PESSOA WHERE NM_PESSOA = '?' AND VL_EMAIL = '?'");
        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            System.out.println("Registros excluidos com sucesso!!!");
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void atualizarDados() {
        PreparedStatement stmt = null;
        Connection conn = null;

        Scanner read = new Scanner(System.in);

        System.out.println("Digite o nome da pessoa que deseja atualizar: ");
        String nome = read.nextLine();

        String sql = "SELECT * FROM TB_PESSOA WHERE NM_PESSOA LIKE %'?'%";

        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            String nomes[] = null;

            int cont = 0;

            //Pega o tamanho correto dos resultados da consulta
            while (rs.next()) {
                cont++;
            }

            nomes = new String[cont];

            //Lista os nomes para a decisão, caso hajam nomes iguais ou semelhantes
            while (rs.next()) {
                for (int i = 0; i < nomes.length; i++) {
                    nomes[i] = rs.getString("NM_PESSOA");
                }
            }

            //O usuário deverá inputar o número do indice correto, onde ele deseja fazer a alteração
            int nomeCorreto = 0;

            for (int i = 0; i < nomes.length; i++) {
                System.out.println(i + "-" + nomes[i]);
                do {
                    System.out.println("Digite o número correspondente ao nome correto.");
                    nomeCorreto = read.nextInt();

                    if (nomeCorreto > nomes.length) {
                        System.out.println("O número digitado não existe.");
                    }

                } while (nomeCorreto > nomes.length);

                //O usuário deve escolher a opção a ser alterada
                System.out.println("Digite o campo que deseja alterar: ");
                int opcao = 0;
                String campoBD = null;

                switch (opcao) {
                    case 1:
                        System.out.println("Digite o novo Nome");
                        campoBD = "NM_PESSOA";
                        break;
                    case 2:
                        System.out.println("Digite o novo Data de Nascimento");
                        campoBD = "DT_NASCIMENTO";
                        break;
                    case 3:
                        System.out.println("Digite o novo Telefone");
                        campoBD = "VL_TELEFONE";
                        break;
                    case 4:
                        System.out.println("Digite o novo E-mail");
                        campoBD = "VL_EMAIL";
                        break;

                    default:
                        System.out.println("O valor digitado não existe.");
                }
                System.out.println("Digite o novo valor do campo: ");
                String novoValor = read.nextLine();

                sql = "UPDATE TB_PESSOA SET ? = '?' WHERE NM_PESSOA = '?'";

                stmt.setString(1, campoBD);
                stmt.setString(2, novoValor);
                stmt.setString(3, nomes[nomeCorreto]);

                stmt.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Exercicio01.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        Exercicio01 instancia = new Exercicio01();
        Scanner entrada = new Scanner(System.in);
        do {
            System.out.println("********** DIGITE UMA OPÇÃO **********");
            System.out.println("(1) Listar agenda");
            System.out.println("(2) Incluir registro");
            System.out.println("(3) Alterar registro");
            System.out.println("(4) Excluir registro");
            System.out.println("(9) SAIR");
            System.out.print("Opção: ");
            int opcao = entrada.nextInt();

            if (opcao == 1) {
                instancia.listarPessoas();
            } else if (opcao == 2) {
                instancia.incluirPessoa();
            } else if (opcao == 3) {
                instancia.atualizarDados();
            } else if (opcao == 4) {
                instancia.excluirPessoa(null, null);
            } else if (opcao == 9) {
                System.exit(0);
            } else {
                System.out.println("OPÇÃO INVÁLIDA.");
            }

        } while (true);
    }
}

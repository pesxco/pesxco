package com.github.mayconmoraesdm.entidades;

import com.github.mayconmoraesdm.fabrica.FabricaJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Contato extends Entidade{
    private Integer id;
    private String nome;
    private String telefone;
    private String telefone2;
    private String celular;
    private String celular2;
    private String email;
    static int vdd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone2() { return telefone2; }

    public void setTelefone2(String telefone2) { this.telefone2 = telefone2; }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelular2(){return celular2;};

    public void setCelular2(String celular2) { this.celular2 = celular2;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVdd() {
        return vdd;
    }

    public void setVdd(int vdd) {
        this.vdd = vdd;
    }
    /**
     * Contrutor padrão - permite instaciar um objeto em memoria
     */
    public Contato() {
    }

    /**
     * Contrutor sobrecarregado com id, recupera os dados do banco para memoria
     */
    public Contato(Integer id) {
        try {
            Contato contatoBd = this.busca(id);
            if(contatoBd != null){
                setId(id);
                setNome(contatoBd.getNome());
                setTelefone(contatoBd.getTelefone());
                setTelefone2(contatoBd.getTelefone2());
                setCelular(contatoBd.getCelular());
                setCelular2(contatoBd.getCelular2());
                setEmail(contatoBd.getEmail());
                vdd = 1;
            }else{
            vdd = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Contrutor sobrecarregado com nome, recupera os dados do banco para memoria
     */
    public Contato(String nome) {
        try {
                Contato contatoBd = this.busca(nome);
                if (contatoBd != null) {
                    setNome(contatoBd.getNome());
                    setId(contatoBd.getId());
                    setTelefone(contatoBd.getTelefone());
                    setTelefone2(contatoBd.getTelefone2());
                    setCelular(contatoBd.getCelular());
                    setCelular2(contatoBd.getCelular2());
                    setEmail(contatoBd.getEmail());
                    vdd = 1;
                } else {
                    vdd = 0;
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Contato> busca() throws SQLException {
        List<Contato> contatos = new ArrayList<Contato>();
        // try with resources
        try (Connection conn = FabricaJDBC.criaConn()) {
            String sql = "SELECT * FROM contatos;";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setTelefone2(rs.getString("telefone2"));
                c.setCelular(rs.getString("celular"));
                c.setCelular2(rs.getString("celular2"));
                c.setEmail(rs.getString("email"));
                contatos.add(c);
            }
        }
        return contatos;
    }

    @Override
    public Contato busca(Integer id) throws SQLException {
       try (Connection conn = FabricaJDBC.criaConn()) {
                String sql = "SELECT * FROM contatos where id = ?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setTelefone2(rs.getString("telefone2"));
                c.setCelular(rs.getString("celular"));
                c.setCelular2(rs.getString("celular2"));
                c.setEmail(rs.getString("email"));
                return c;
            }
        }
        return null;
    }

    public Contato busca(String nome) throws SQLException {
        try (Connection conn = FabricaJDBC.criaConn()) {
                String sql = "SELECT * FROM contatos where nome LIKE ?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, "%" + nome + "%");
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    Contato c = new Contato();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setTelefone(rs.getString("telefone"));
                    c.setTelefone2(rs.getString("telefone2"));
                    c.setCelular(rs.getString("celular"));
                    c.setCelular2(rs.getString("celular2"));
                    c.setEmail(rs.getString("email"));
                    return c;
                }
            }
        return null;
    }

    @Override
    public Boolean insere() throws SQLException {
        try (Connection conn = FabricaJDBC.criaConn()) {
            String sql = "INSERT INTO contatos(nome, telefone, telefone2, celular, celular2, email) VALUES (?,?,?,?,?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, getNome());
            preparedStatement.setString(2, getTelefone());
            preparedStatement.setString(3, getTelefone2());
            preparedStatement.setString(4, getCelular());
            preparedStatement.setString(5, getCelular2());
            preparedStatement.setString(6, getEmail());
            preparedStatement.execute();
            return true;
        }
    }

    @Override
    public Boolean altera() throws SQLException {
        try (Connection conn = FabricaJDBC.criaConn()) {
                String sql = "UPDATE contatos " +
                        "SET nome = ?," +
                        "telefone = ?," +
                        "telefone2 = ?," +
                        "celular = ?," +
                        "celular2 = ?," +
                        "email = ?" +
                        "WHERE id = ?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, getNome());
                preparedStatement.setString(2, getTelefone());
                preparedStatement.setString(3, getTelefone2());
                preparedStatement.setString(4, getCelular());
                preparedStatement.setString(5, getCelular2());
                preparedStatement.setString(6, getEmail());
                preparedStatement.setInt(7, getId());
                preparedStatement.execute();
            return true;
        }
    }

    @Override
    public Boolean exclui() throws SQLException {
        try (Connection conn = FabricaJDBC.criaConn()) {
            String sql = "DELETE from contatos " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt( 1, getId());
            preparedStatement.execute();
            return true;
        }
    }
}


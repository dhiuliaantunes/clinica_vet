package com.example.exemplobd;

import com.example.exemplobd.ConnectionFactory;
import com.example.exemplobd.Pet;
import com.example.exemplobd.SQL_Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao_Pet {
    public void adiciona(Pet p) throws SQLException {
        String sql = SQL_Constantes.INSERT;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, null);
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getTipo());
            stmt.setString(4, p.getSexo());
            stmt.setInt(5, p.getIdade());
            stmt.setString(6, p.getRaca());
            stmt.setFloat(7, p.getPeso());
            stmt.setInt(8, p.getId_dono());
            stmt.execute();
        }
    }
    public List<Pet> pesquisaTodos() throws SQLException{
        List<Pet> pets = new ArrayList();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQL_Constantes.SEARCH);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pet p = new Pet();

                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTipo(rs.getString("tipo"));
                p.setSexo(rs.getString("sexo"));
                p.setIdade(rs.getInt("idade"));
                p.setRaca(rs.getString("raca"));
                p.setPeso(rs.getFloat("peso"));
                p.setId_dono(rs.getInt("id_dono"));
                pets.add(p);
            }
        }
        return pets;
    }

    public void altera(Pet p) throws SQLException{
        String sql = SQL_Constantes.UPDATE;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getTipo());
            stmt.setString(3, p.getSexo());
            stmt.setInt(4, p.getIdade());
            stmt.setString(5, p.getRaca());
            stmt.setFloat(6, p.getPeso());
            stmt.setInt(7, p.getId_dono());
            stmt.setInt(8, p.getId());
            stmt.execute();
        }
    }

    public void remove(Pet p) throws SQLException{
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQL_Constantes.REMOVE)){
            stmt.setInt(1, p.getId());
            stmt.execute();
        }
    }

    public boolean pesquisa(Pet p) throws SQLException{
        return true;
    }

    public Pet pesquisaPet(String nome) throws SQLException{
        List<Pet> pets = new Dao_Pet().pesquisaTodos();

        for (Pet p : pets) {
            if (p.getNome().equals(nome))
                return p;
        }
        return null;
    }
    public int buscaId(String nome) throws SQLException {
        return pesquisaPet(nome).getId();
    }

    public int buscaId(Pet p) throws SQLException {
        List<Pet> todos = pesquisaTodos();

        for (Object cc : todos) {
            if (((Pet)cc).equals(p)) {
                return p.getId();
            }
        }
        return -1;
    }
}

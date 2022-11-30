/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.exemplobd;

/**
 *
 * @author coelho
 */
public class SQL_Constantes {

    public static final String INSERT = "insert into "
            + "pet (id, nome, tipo, sexo, idade, raca, peso, id_dono) "
            + "values (?,?,?,?,?,?,?,?)";

    public static final String UPDATE = "update pet set "
            + "nome=?, tipo=?, sexo=?, idade=?, raca=?, peso=?, id_dono=? where id=?";

    public static final String REMOVE = "delete from pet where id=?";

    public static final String SEARCH = "select * from pet";
}

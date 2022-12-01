package com.example.exemplobd;

/**
 * Pet class
 *
 * @author Mariana
 */

public class Pet {
    private int id;
    private String nome;
    private String tipo;
    private String sexo;
    private int Idade;
    private String raca;
    private float peso;
    private int id_dono;

    public Pet() {
    }

    public Pet(int id, String nome, String tipo, String sexo, int idade, String raca, float peso, int id_dono) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.sexo = sexo;
        Idade = idade;
        this.raca = raca;
        this.peso = peso;
        this.id_dono = id_dono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return Idade;
    }

    public void setIdade(int idade) {
        Idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getId_dono() {
        return id_dono;
    }

    public void setId_dono(int id_dono) {
        this.id_dono = id_dono;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", sexo='" + sexo + '\'' +
                ", Idade=" + Idade +
                ", raca='" + raca + '\'' +
                ", peso=" + peso +
                ", id_dono=" + id_dono +
                '}';
    }
}

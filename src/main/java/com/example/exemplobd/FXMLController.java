/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.exemplobd;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Dhiulia
 */
public class FXMLController implements Initializable {

    private Dao_Pet dao_pet;
    private ObservableList<Pet> dados_pet;

    @FXML
    private TextField texto_nome_insere,
            texto_tipo_insere,
            texto_sexo_insere,
            texto_idade_insere,
            texto_raca_insere,
            texto_peso_insere,
            texto_dono_insere;

    @FXML
    private ChoiceBox<String> choice_remove;

    @FXML
    private ChoiceBox<String> choice_altera;
     /*TextField texto_nome_altera,
            texto_nota1_altera,
            texto_nota2_altera,
            texto_nota3_altera;*/

    @FXML
    private TextField texto_nome_altera,
            texto_tipo_altera,
            texto_sexo_altera,
            texto_idade_altera,
            texto_raca_altera,
            texto_peso_altera,
            texto_dono_altera;


    @FXML
    private TableView<Pet> tabela;
    @FXML
    private TableColumn<Pet, String> coluna_nome, coluna_tipo, coluna_sexo, coluna_raca;
    @FXML
    private TableColumn<Pet, Float> coluna_peso;
    @FXML
    private TableColumn<Pet, Integer> coluna_dono, coluna_idade;

    @FXML
    public void evento_insere(ActionEvent event) {
        if (!texto_nome_insere.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Confirma cadastro?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = a.showAndWait();

            if (bt.get() == ButtonType.YES) {
                try {
                    Pet pet = new Pet();
                    pet.setNome(texto_nome_insere.getText());
                    pet.setTipo(texto_tipo_insere.getText());
                    pet.setSexo(texto_sexo_insere.getText());
                    pet.setIdade(Integer.parseInt(texto_idade_insere.getText()));
                    pet.setRaca(texto_raca_insere.getText());
                    pet.setPeso(Float.parseFloat(texto_peso_insere.getText()));
                    pet.setId_dono(Integer.parseInt(texto_dono_insere.getText()));
                    dao_pet.adiciona(pet);
                    pet.setId(dao_pet.buscaId(pet));
                    atualizaDados();
                    Alert b = new Alert(Alert.AlertType.INFORMATION);

                    b.setContentText("Pet cadastrado com sucesso!");
                    b.setTitle("INFO");
                    b.showAndWait();
                    limpaCamposInserção();
                } catch (Exception ex) {
                    escreveArquivo("logs.txt", "Erro ao realizar cadastro! " +
                            "\nExceção: " +  ex.toString());
                    Alert c = new Alert(Alert.AlertType.ERROR);
                    c.setContentText("Erro ao cadastrar pet.");
                    c.setTitle("ERROR");
                    c.showAndWait();
                }
            }
        } else {
            Alert c = new Alert(Alert.AlertType.ERROR);

            c.setContentText("O campo nome deve estar preenchido.");
            c.setTitle("ERROR");
            c.showAndWait();
        }

    }

    private void atualizaDados() throws SQLException {
        choice_remove.getItems().clear();
        choice_altera.getItems().clear();
        tabela.getItems().clear();
        List<Pet> p;

        p = this.dao_pet.pesquisaTodos();
        dados_pet = FXCollections.observableArrayList();
        for (Object c : p) {
            Pet pet = (Pet) c;

            dados_pet.add(pet);
            choice_remove.getItems().add(pet.getNome());
            choice_altera.getItems().add(pet.getNome());
        }
        tabela.setItems(dados_pet);
        tabela.refresh();
        choice_altera.getSelectionModel().select(0);
        choice_remove.getSelectionModel().select(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        geraArquivo();
        dao_pet = new Dao_Pet();
        coluna_nome.setCellValueFactory(new PropertyValueFactory("nome"));
        coluna_tipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        coluna_sexo.setCellValueFactory(new PropertyValueFactory("sexo"));
        coluna_idade.setCellValueFactory(new PropertyValueFactory("idade"));
        coluna_raca.setCellValueFactory(new PropertyValueFactory("raca"));
        coluna_peso.setCellValueFactory(new PropertyValueFactory("peso"));
        coluna_dono.setCellValueFactory(new PropertyValueFactory("id_dono"));
        tabela.getColumns().clear();
        tabela.getColumns().addAll(coluna_nome,
                coluna_tipo,
                coluna_sexo,
                coluna_idade,
                coluna_raca,
                coluna_peso,
                coluna_dono);
        try {
            atualizaDados();
        } catch (Exception ex) {
            escreveArquivo("logs.txt", "Erro ao buscar dados no bd" +
                    "\nExceção: " +  ex.toString());
            Alert c = new Alert(Alert.AlertType.ERROR);
            c.setContentText("Erro ao buscar dados no BD.");
            c.setTitle("ERROR");
            c.showAndWait();
        }
    }

    public void evento_remove(ActionEvent event) throws SQLException {
        System.out.println("ok");
        Pet p = dao_pet.pesquisaPet((String) choice_remove.getSelectionModel().getSelectedItem());

        if (p != null) {
            dao_pet.remove(p);
            Alert d = new Alert(Alert.AlertType.INFORMATION);

            d.setContentText("Pet removido com sucesso!");
            d.setTitle("INFO");
            d.showAndWait();
            atualizaDados();
        } else {
            escreveArquivo("logs.txt", "Erro durante a remoção do pet");
            Alert d = new Alert(Alert.AlertType.ERROR);
            d.setContentText("Erro durante a remoção do pet " + p.getNome());
            d.setTitle("ERROR");
            d.showAndWait();
        }

    }

    @FXML
    public void evento_altera(ActionEvent event) {
        Pet pet = new Pet();

        try {
            pet.setId(dao_pet.buscaId(choice_altera.getSelectionModel().getSelectedItem()));
            pet.setNome(texto_nome_altera.getText());
            pet.setTipo(texto_tipo_altera.getText());
            pet.setSexo(texto_sexo_altera.getText());
            pet.setIdade(Integer.parseInt(texto_idade_altera.getText()));
            pet.setRaca(texto_raca_altera.getText());
            pet.setPeso(Float.parseFloat(texto_peso_altera.getText()));
            pet.setId_dono(Integer.parseInt(texto_dono_altera.getText()));
            dao_pet.altera(pet);
            Alert d = new Alert(Alert.AlertType.INFORMATION);
            d.setContentText("Pet atualizado com sucesso!");
            d.setTitle("INFO");
            d.showAndWait();
            atualizaDados();
        } catch (Exception ex) {
            escreveArquivo("logs.txt", "Erro durante a atualização do pet" +
                    "\nExceção: " +  ex.toString());
            Alert d = new Alert(Alert.AlertType.ERROR);
            d.setContentText("Erro durante a atualização do pet " + pet.getNome());
            d.setTitle("ERROR");
            d.showAndWait();
        }
    }

    @FXML
    public void evento_seleciona_altera(ActionEvent event) throws SQLException {
        if (((String) choice_altera.getSelectionModel().getSelectedItem()) == null
                || ((String) choice_altera.getSelectionModel().getSelectedItem()).isEmpty()) {
            limpaCamposAlteração();
        } else {
            List<Pet> todos = dao_pet.pesquisaTodos();

            for (Object m : todos) {
                Pet p = (Pet) m;

                if (p.getNome().equals((String) choice_altera.getSelectionModel().getSelectedItem())) {
                    texto_nome_altera.setText(p.getNome());
                    texto_tipo_altera.setText(String.valueOf(p.getTipo()));
                    texto_sexo_altera.setText(String.valueOf(p.getSexo()));
                    texto_idade_altera.setText(String.valueOf(p.getIdade()));
                    texto_raca_altera.setText(String.valueOf(p.getRaca()));
                    texto_peso_altera.setText(String.valueOf(p.getPeso()));
                    texto_dono_altera.setText(String.valueOf(p.getId_dono()));
                    break;
                }
            }
        }
    }
    private void limpaCamposInserção() {
        texto_nome_insere.setText("");
        texto_tipo_insere.setText("");
        texto_sexo_insere.setText("");
        texto_idade_insere.setText("");
        texto_raca_insere.setText("");
        texto_dono_insere.setText("");
        texto_peso_insere.setText("");
    }

    private void limpaCamposAlteração() {
        texto_nome_altera.setText("");
        texto_tipo_altera.setText("");
        texto_sexo_altera.setText("");
        texto_idade_altera.setText("");
        texto_raca_altera.setText("");
        texto_dono_altera.setText("");
        texto_peso_altera.setText("");
    }

    public void geraArquivo(){

        try {
            File f = new File("logs.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            try (FileWriter fw = new FileWriter(f);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter pw = new PrintWriter(bw)) {
                pw.println("LOGS DE EXECUÇÃO\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo arquivo.txt.");
        }
    }

    private void escreveArquivo(String file, String log){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        try {
            File f = new File(file);
            if(f.exists()){
                try (FileWriter fw = new FileWriter(f, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter pw = new PrintWriter(bw)) {
                    pw.println(log);
                    pw.println("Data de execução: " + dateFormat.format(date) + "\n");
                }
            }
            System.out.println("Dados salvos com sucesso. ");
            System.out.println(log.toString());
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo.");
        }
    }
}

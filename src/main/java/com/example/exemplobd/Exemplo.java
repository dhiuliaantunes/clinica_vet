/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.exemplobd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 *
 * @author Mariana
 */
public class Exemplo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/exemplobd/FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        launch(args);
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Dados dos Pets");
        Dao_Pet pet = new Dao_Pet();
        var pets = pet.pesquisaTodos();

        Map<Integer, Object[]> data = new TreeMap();
        data.put(0, new Object[]{"ID", "NOME", "TIPO", "SEXO", "IDADE", "RAÇA", "PESO", "DONO"});
        for (Pet p : pets) {
            data.put(p.getId(), new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getTipo(),
                    p.getSexo(),
                    p.getIdade(),
                    p.getRaca(),
                    p.getPeso(),
                    p.getId_dono()});
        }

        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        Iterator var6 = keyset.iterator();

        while (var6.hasNext()) {
            Integer key = (Integer) var6.next();
            Row row = sheet.createRow(rownum++);
            Object[] objArr = (Object[]) data.get(key);
            int cellnum = 0;
            Object[] var11 = objArr;
            int var12 = objArr.length;

            for (int var13 = 0; var13 < var12; ++var13) {
                Object obj = var11[var13];
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else if (obj instanceof Float) {
                    cell.setCellValue((Float) obj);
                }
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File("PetsCadastrados.xls"));
            workbook.write(out);
            out.close();
        } catch (Exception var16) {
            var16.printStackTrace();
        }
    }
}





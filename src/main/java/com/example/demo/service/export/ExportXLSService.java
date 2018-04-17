package com.example.demo.service.export;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;



@Service
public class ExportXLSService {

    public void exportClients(OutputStream os, List<ClientDTO> clients) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("clients");

        int i = 0;
        int k;
        for(ClientDTO client :clients){
            k = 0;
            Row row = sheet.createRow(i);
            row.createCell(k).setCellValue(client.getNom());
            i++;
            k++;
            row.createCell(k).setCellValue(client.getPrenom());
        }
        workbook.write(os);
        workbook.close();
    }

    public void exportFactureById(OutputStream os, List<FactureDTO> factures) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        // liste factures du client
        for(FactureDTO facture :factures){
            XSSFSheet sheet = workbook.createSheet("facture " + facture.getId());
            List<LigneFactureDTO> ligneFactureDTO = facture.getLigneFactures();
            Double prixTotal = 0d;

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Designation");
            row.createCell(1).setCellValue("Quantité");
            row.createCell(2).setCellValue("Prix Unitaire");
            row.createCell(3).setCellValue("Prix Ligne");

            int i = 1;
            for(LigneFactureDTO ligne:ligneFactureDTO){
                Integer quantite = ligne.getQuantite();
                Double prixUnitaire = ligne.getPrixUnitaire();
                Double prixLigne = quantite * prixUnitaire;


                Row lignef = sheet.createRow(i);

                lignef.createCell(0).setCellValue(ligne.getDesignation());
                lignef.createCell(1).setCellValue(quantite);
                lignef.createCell(2).setCellValue(prixUnitaire);
                lignef.createCell(3).setCellValue(prixLigne);
                prixTotal += prixLigne;
                i++;
            }

            Row prix = sheet.createRow(ligneFactureDTO.size()+1);
            prix.createCell(0).setCellValue("Prix Total :" + prixTotal);

        }

        workbook.write(os);
        workbook.close();
    }

}

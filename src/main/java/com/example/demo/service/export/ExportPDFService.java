package com.example.demo.service.export;

import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import org.eclipse.birt.report.engine.css.engine.value.StringValue;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ExportPDFService {
    public void export(OutputStream os, FactureDTO factureDTO) throws IOException, com.itextpdf.text.DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, os);
        document.open();

        document.add(createFirstTable(factureDTO));
        document.close();
    }

    public static PdfPTable createFirstTable(FactureDTO factureDTO) {
        List<LigneFactureDTO> ligneFactureDTO = factureDTO.getLigneFactures();
        // a table with three columns
        PdfPTable table = new PdfPTable(3);

        String nom = factureDTO.getClient().getNom();
        String prenom = factureDTO.getClient().getPrenom();
        PdfPCell cell = new PdfPCell(new Phrase(nom + " " + prenom));
        cell.setColspan(3);
        table.addCell(cell);

        table.addCell("Designation");
        table.addCell("Quantite");
        table.addCell("Prix Unitaire");

        Double prixTotal = 0d;

        for(LigneFactureDTO ligne:ligneFactureDTO){

            Integer quantite = ligne.getQuantite();
            Double prixUnitaire = ligne.getPrixUnitaire();
            prixTotal += quantite * prixUnitaire;

            table.addCell(ligne.getDesignation());
            table.addCell(String.valueOf(quantite));
            table.addCell(String.valueOf(prixUnitaire));
        }

        PdfPCell cellPrix = new PdfPCell(new Phrase("Prix Total : " + String.valueOf(prixTotal)));
        cellPrix.setColspan(3);
        table.addCell(cellPrix);
        return table;
    }
}

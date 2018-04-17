package com.example.demo.service.export;

import com.example.demo.dto.ClientDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportCSVService {
    public void export(PrintWriter printWriter, List<ClientDTO> clients) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("clients");


        for(ClientDTO clientDTO:clients){
            printWriter.write(clientDTO.getNom());
            printWriter.write(";");
            printWriter.write(clientDTO.getPrenom());
            printWriter.write(";");
            printWriter.write("\n");
        }
        printWriter.close();

    }
}

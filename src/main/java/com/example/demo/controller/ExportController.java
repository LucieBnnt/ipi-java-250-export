package com.example.demo.controller;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;
import com.example.demo.service.export.ExportCSVService;
import com.example.demo.service.export.ExportPDFService;
import com.example.demo.service.export.ExportXLSService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class ExportController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ExportCSVService exportCSVService;

    @Autowired
    private ExportXLSService exportXLSService;

    @Autowired
    private FactureService factureService;

    @Autowired
    private ExportPDFService exportPDFService;

    @GetMapping("/clients/csv")
    public void clientsCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
        List<ClientDTO> clients = clientService.findAllClients();
        exportCSVService.export(response.getWriter(), clients);
    }

    @GetMapping("/clients/xlsx")
    public void clientsXLS(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=\"clients.xlsx\"");
        List<ClientDTO> clients = clientService.findAllClients();
        exportXLSService.exportClients(response.getOutputStream(), clients);
    }

    @GetMapping("/factures/{id}/xlsx")
    public void factureXLS(@PathVariable("id") Long id, HttpServletRequest request,  HttpServletResponse response) throws IOException {
        response.setContentType("application/xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=\"facture " + id + ".xlsx\"");
        List<FactureDTO> factures = factureService.findByClientId(id);
        exportXLSService.exportFactureById(response.getOutputStream(), factures);
    }

    @GetMapping("/factures/{id}/pdf")
    public void facturePDF(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"facture " + id + ".pdf\"");
        FactureDTO facture = factureService.findById(id);
        exportPDFService.export(response.getOutputStream(), facture);
    }

}

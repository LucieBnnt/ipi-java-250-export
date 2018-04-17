package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class InitData {

    @Autowired
    private EntityManager em;

    public void insertTestData() {

        //CLIENTS
        Client client  = new Client();
        client.setNom("PETRILLO");
        client.setPrenom("Alexandre");
        em.persist(client);

        Client client2  = new Client();
        client2.setNom("WAYNE");
        client2.setPrenom("Bruce");
        em.persist(client2);

        // ARTICLES
        Article article = new Article();
        article.setLibelle("Carte mère ASROCK 2345");
        article.setPrix(79.90);
        em.persist(article);

        Article article2 = new Article();
        article2.setLibelle("Batmobile");
        article2.setPrix(10000.00);
        em.persist(article2);

        Article article3 = new Article();
        article3.setLibelle("Costume stylé");
        article3.setPrix(99.99);
        em.persist(article3);

        //FACTURES
        Facture facture = new Facture();
        facture.setClient(client);
        em.persist(facture);

        Facture facture2 = new Facture();
        facture2.setClient(client2);
        em.persist(facture2);

        Facture facture3 = new Facture();
        facture3.setClient(client2);
        em.persist(facture3);

        //LIGNES FACTURES
        LigneFacture ligneFacture1 = new LigneFacture();
        ligneFacture1.setFacture(facture);
        ligneFacture1.setArticle(article);
        ligneFacture1.setQuantite(1);
        em.persist(ligneFacture1);

        LigneFacture ligneFacture2 = new LigneFacture();
        ligneFacture2.setFacture(facture2);
        ligneFacture2.setArticle(article3);
        ligneFacture2.setQuantite(2);
        em.persist(ligneFacture2);

        LigneFacture ligneFacture3 = new LigneFacture();
        ligneFacture3.setFacture(facture2);
        ligneFacture3.setArticle(article2);
        ligneFacture3.setQuantite(3);
        em.persist(ligneFacture3);

        LigneFacture ligneFacture4 = new LigneFacture();
        ligneFacture4.setFacture(facture3);
        ligneFacture4.setArticle(article3);
        ligneFacture4.setQuantite(1);
        em.persist(ligneFacture4);
    }
}

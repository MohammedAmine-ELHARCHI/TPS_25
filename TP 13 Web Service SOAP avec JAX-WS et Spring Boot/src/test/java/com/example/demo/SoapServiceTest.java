package com.example.demo;

import com.example.demo.entities.Compte;
import com.example.demo.entities.TypeCompte;
import com.example.demo.ws.CompteSoapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SoapServiceTest {

    @Autowired
    private CompteSoapService compteSoapService;

    @Test
    public void testGetComptes() {
        List<Compte> comptes = compteSoapService.getComptes();
        assertNotNull(comptes);
        assertTrue(comptes.size() > 0);
    }

    @Test
    public void testCreateCompte() {
        Compte compte = new Compte();
        compte.setSolde(3000.0);
        compte.setDateCreation(new Date());
        compte.setType(TypeCompte.EPARGNE);
        
        Compte savedCompte = compteSoapService.createCompte(compte);
        assertNotNull(savedCompte);
        assertNotNull(savedCompte.getId());
        assertEquals(3000.0, savedCompte.getSolde());
    }

    @Test
    public void testGetCompteById() {
        List<Compte> comptes = compteSoapService.getComptes();
        if (!comptes.isEmpty()) {
            Long id = comptes.get(0).getId();
            Compte compte = compteSoapService.getCompteById(id);
            assertNotNull(compte);
            assertEquals(id, compte.getId());
        }
    }
}

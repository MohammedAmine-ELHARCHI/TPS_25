package com.example.Voiture;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

/**
 * Classe de test pour l'application Voiture
 * Teste le chargement du contexte Spring Boot en excluant le CommandLineRunner
 * qui effectue des appels réseau pendant les tests unitaires.
 *
 * @author MOHAMMED AMINE ELHARCHI
 */
@SpringBootTest
@TestPropertySource(properties = {
	"eureka.client.enabled=false",
	"spring.cloud.openfeign.enabled=false"
})
public class VoitureApplicationTests {

	/**
	 * Configuration de test qui remplace le CommandLineRunner par un bean vide
	 * pour éviter l'exécution d'appels réseau pendant les tests.
	 */
	@Configuration
	static class TestConfiguration {
		/**
		 * Bean CommandLineRunner vide pour remplacer celui de l'application principale.
		 * Ce bean ne fait rien, évitant ainsi les appels HTTP vers les autres services.
		 *
		 * @return CommandLineRunner qui ne fait rien
		 */
		@Bean
		@Primary
		public org.springframework.boot.CommandLineRunner commandLineRunner() {
			return args -> {
				// Ne rien faire pendant les tests
			};
		}
	}

	/**
	 * Test basique vérifiant que le contexte Spring se charge correctement.
	 * Ce test valide que toutes les configurations et beans sont correctement
	 * définis sans erreurs de démarrage, en utilisant un CommandLineRunner vide.
	 */
	@Test
	void contextLoads() {
		// Test passe si le contexte se charge sans erreur
	}

}

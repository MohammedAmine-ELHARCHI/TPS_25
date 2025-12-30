package spring.data.rest;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import spring.data.rest.entities.Client;
import spring.data.rest.entities.Compte;
import spring.data.rest.entities.TypeCompte;
import spring.data.rest.repositories.ClientRepository;
import spring.data.rest.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class MsBanqueApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsBanqueApplication.class, args);
    }

//    @Bean
//    CommandLineRunner start(CompteRepository compteRepository){
//        return args -> {
//            compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));
//            compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.COURANT));
//            compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));
//
//            compteRepository.findAll().forEach(c -> {
//                System.out.println(c.toString());
//            });
//        };
//    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository, ClientRepository clientRepository, RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Compte.class);

            Client c1 = clientRepository.save(new Client(null, "Amal", null, null));
            Client c2 = clientRepository.save(new Client(null, "amine", null, null));

            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c2));

            compteRepository.findAll().forEach(c -> {
                System.out.println(c.toString());
            });
        };
    }
}
// package spring.data.rest.config;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
// import spring.data.rest.entities.Compte;
// import spring.data.rest.entities.TypeCompte;
// import spring.data.rest.repositories.CompteRepository;
// import java.util.Date;


// @Configuration
// public class MyRepositoryRestConfig {

//     @Bean
//     CommandLineRunner start(CompteRepository compteRepository, RepositoryRestConfiguration restConfiguration) {
//         return args -> {
//             restConfiguration.exposeIdsFor(Compte.class);

//             // Initialisation des comptes
//             compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE));
//             compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT));
//             compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE));
//         };
//     }
// }
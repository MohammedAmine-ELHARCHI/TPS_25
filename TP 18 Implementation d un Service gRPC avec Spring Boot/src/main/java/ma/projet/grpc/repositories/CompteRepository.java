package ma.projet.grpc.repositories;

import ma.projet.grpc.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Compte (Account) entity persistence.
 * 
 * <p>Provides standard CRUD operations for bank accounts through JPA.
 * CRUD methods inherited from JpaRepository include findAll(), findById(),
 * save(), delete(), count(), and more.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {
    // Standard JPA repository methods are inherited:
    // - findAll(): Retrieve all accounts
    // - findById(String id): Find account by ID
    // - save(Compte compte): Create or update account
    // - deleteById(String id): Delete account by ID
    // - count(): Count total accounts
    // - existsById(String id): Check if account exists
}

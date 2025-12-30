package spring.data.rest.repositories;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import spring.data.rest.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import spring.data.rest.entities.TypeCompte;

import java.util.List;

@RepositoryRestResource(path = "comptes", collectionResourceRel = "comptes", itemResourceRel = "compte")
public interface CompteRepository extends JpaRepository<Compte, Long> {

    Compte save(Compte compte);

    @RestResource(path = "/byType")
    public List<Compte> findByType(@Param("t") TypeCompte type);
}
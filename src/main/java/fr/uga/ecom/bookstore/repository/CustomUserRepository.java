package fr.uga.ecom.bookstore.repository;

import fr.uga.ecom.bookstore.domain.CustomUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the CustomUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    /**
     * @author CM
     * Get custom user corresponding to jhipster user id.
     *
     */
    @Query(value = "select cu.* from Custom_User cu where cu.user_id =:id",
        nativeQuery = true)
    Optional<CustomUser> findByUserId(@Param("id") long id);

    /**
     * @author QCR
     * Récupérer un customUser à partir du userId
     * @param userid : L'id du customer
     * @return
     */
    Optional<CustomUser> findOneByUser_IdEquals(Long userid);
}

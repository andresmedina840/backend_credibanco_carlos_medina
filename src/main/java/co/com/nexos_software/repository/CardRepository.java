package co.com.nexos_software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.com.nexos_software.entity.Card;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> { // ID tipo String
    Optional<Card> findByNumber(String number);
}

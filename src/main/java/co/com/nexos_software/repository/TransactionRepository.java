package co.com.nexos_software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.com.nexos_software.entity.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCardNumber(String cardNumber);
}
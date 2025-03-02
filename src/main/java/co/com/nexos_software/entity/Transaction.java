package co.com.nexos_software.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction", schema = "credibanco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID único de transacción

    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "number", nullable = false)
    private Card card;

    @Column(name = "amount", nullable = false)
    private double amount; // Monto en dólares

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp; // Fecha y hora de la transacción

    @Column(name = "status", nullable = false, length = 10)
    private String status; // Estado: "APPROVED", "ANNULLED"

    @PrePersist
    private void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }
}

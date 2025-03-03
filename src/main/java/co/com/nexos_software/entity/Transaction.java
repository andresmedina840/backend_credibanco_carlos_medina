package co.com.nexos_software.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "transaction", schema = "credibanco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "number", nullable = false)
    private Card card;

    @Column(name = "amount", nullable = false)
    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss a", locale = "es_CO")
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @PrePersist
    private void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }
}

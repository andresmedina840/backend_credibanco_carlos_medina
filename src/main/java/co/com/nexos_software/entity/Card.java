package co.com.nexos_software.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "card", schema = "credibanco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    
    @Id
    @Pattern(regexp = "^\\d{16}", message = "Número de tarjeta inválido")
    @Column(length = 16, nullable = false)
    private String number;

    @Pattern(
        regexp = "^[A-Za-zÁ-Úá-ú]+ [A-Za-zÁ-Úá-ú]+$", 
        message = "Formato: 'Nombre Apellido'"
    )
    @Column(name = "holder_name", nullable = false, length = 100)
    private String holderName;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @PositiveOrZero(message = "El saldo no puede ser negativo")
    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @PrePersist
    private void initializeCard() {
        this.creationDate = LocalDateTime.now();
        this.expiryDate = creationDate.toLocalDate().plusYears(3);
        this.balance = BigDecimal.ZERO;
        this.isActive = false;
        this.isBlocked = false;
    }

    // Getter para el formato MM/yyyy (no persistido)
    @Transient
    public String getFormattedExpiryDate() {
        return expiryDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }

    // Setters personalizados para lógica de negocio
    public void setActive(boolean isActive) {
        if (this.isBlocked) {
            throw new IllegalStateException("Tarjeta bloqueada no puede activarse");
        }
        this.isActive = isActive;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
        if (isBlocked) this.isActive = false;
    }
}
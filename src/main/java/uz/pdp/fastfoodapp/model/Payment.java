package uz.pdp.fastfoodapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
public class Payment extends BaseEntity{

    @Column(name="card_number", nullable = false )
    private Long cardNumber;
    @Builder.Default
    private LocalDateTime paymentTime=LocalDateTime.now();
}

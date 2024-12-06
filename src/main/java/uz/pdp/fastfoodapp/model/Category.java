package uz.pdp.fastfoodapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseEntity{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    private String title;

}


package com.store.grocery.fresh_express.model;

import com.store.grocery.fresh_express.shared.kernel.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends AbstractAuditingEntity<Long> {

    @Id
    @SequenceGenerator(name = "category_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_uuid", nullable = false, unique = true, updatable = false)
    private String categoryUUID;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_description")
    private String  categoryDescription;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @Override
    public Long getId() {
        return this.categoryId;
    }
}

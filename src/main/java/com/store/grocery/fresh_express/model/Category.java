package com.store.grocery.fresh_express.model;

import com.store.grocery.fresh_express.shared.kernel.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private long categoryId;

    @Column(name = "category_uuid", nullable = false, unique = true, updatable = false)
    private String categoryUUID;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_description")
    private String  categoryDescription;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "image_id")
    private Image categoryImage;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private Set<SubCategory> subCategories = new HashSet<>();

    @Override
    public Long getId() {
        return this.categoryId;
    }
}

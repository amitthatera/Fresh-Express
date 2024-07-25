package com.store.grocery.fresh_express.model;

import com.store.grocery.fresh_express.shared.kernel.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "images/sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategory extends AbstractAuditingEntity {
    @Id
    @SequenceGenerator(name = "sub_category_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_category_seq")
    @Column(name = "sub_category_id")
    private long subCategoryId;

    @Column(name = "sub_category_uuid", nullable = false, unique = true, updatable = false)
    private String subCategoryUUID;

    @Column(name = "sub_category_name", nullable = false)
    private String subCategoryName;

    @Column(name = "sub_category_description")
    private String  subCategoryDescription;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "image_id")
    private Image subCategoryImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = " category_id")
    private Category category;

    @OneToMany(mappedBy = "subCategory", orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @Override
    public Long getId() {
        return this.subCategoryId;
    }
}

package com.store.grocery.fresh_express.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.grocery.fresh_express.shared.kernel.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image extends AbstractAuditingEntity<Long> {

    @Id
    @SequenceGenerator(name = "img_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "img_seq")
    @Column(name = "image_id")
    private long imageId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_size")
    private long imageSize;

    @Column(name = "image_type")
    private String imageType;

    @OneToOne(mappedBy = "categoryImage")
    @JsonIgnore
    private Category category;

    @OneToOne(mappedBy = "subCategoryImage")
    @JsonIgnore
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @JsonIgnore
    private Product product;

    @Override
    public Long getId() {
        return this.imageId;
    }
}

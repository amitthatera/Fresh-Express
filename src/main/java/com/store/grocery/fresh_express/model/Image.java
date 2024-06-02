package com.store.grocery.fresh_express.model;

import com.store.grocery.fresh_express.shared.kernel.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@Builder
public class Image extends AbstractAuditingEntity<Long> {

    @Id
    @SequenceGenerator(name = "img_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "img_seq")
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_size")
    private Long imageSize;

    @Column(name = "image_type")
    private String imageType;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Override
    public Long getId() {
        return this.imageId;
    }
}

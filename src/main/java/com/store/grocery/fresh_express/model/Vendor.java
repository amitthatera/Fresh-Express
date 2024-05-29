package com.store.grocery.fresh_express.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

    @Id
    @SequenceGenerator(name = "vendor_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_seq")
    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "vendor_email", nullable = false)
    private String vendorEmail;

    @Column(name = "vendor_mobile", nullable = false)
    private String vendorMobile;

    @Column(name = "vendor_address", nullable = false)
    private String vendorAddress;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", insertable = false)
    private LocalDateTime lastModifiedDate;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productList;

}

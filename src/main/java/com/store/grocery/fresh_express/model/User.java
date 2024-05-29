package com.store.grocery.fresh_express.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "contact_number", unique = true, nullable = false)
    private String contactNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", insertable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(name = "is_number_verified")
    private Boolean isNumberVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> roles;


}

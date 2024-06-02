package com.store.grocery.fresh_express.model;

import com.store.grocery.fresh_express.shared.kernel.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AbstractAuditingEntity<Long>{

    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id")
    private Long userId;

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

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(name = "is_number_verified")
    private Boolean isNumberVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "user_id")}, inverseJoinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    @Override
    public Long getId() {
        return this.userId;
    }
}

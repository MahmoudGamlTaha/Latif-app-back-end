package com.commerce.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_permissions")
@Getter
@Setter
@NoArgsConstructor
public class UserPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id")
    @SequenceGenerator(name = "permission_id", sequenceName = "permission_id", allocationSize = 1)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "http_path")
    private String httpPath;

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore(value = true)
    private Set<Role> roles = new HashSet<>();

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;
}

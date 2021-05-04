package com.commerce.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.commerce.backend.model.composite.UserRoleKey;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_roles", uniqueConstraints= {@UniqueConstraint(columnNames = {"user_id", "role_Id"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_roles_id_seq")
    @SequenceGenerator(name = "user_roles_id_seq", sequenceName = "user_roles_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
  
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    
    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;
}

package com.niks.oauth2authenticationservice.models.db;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(	name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_name"),
        @UniqueConstraint(columnNames = "email")
    })
@NoArgsConstructor
public class User extends BaseModel {

  @NotBlank
  @Column(name = "user_name", nullable = false, unique = true, length = 20)
  private String userName;

  @NotBlank
  @Size(max = 50)
  @Email
  @Column(name = "email", nullable = false, unique = true, length = 50)
  private String email;

  @NotBlank
  @Size(max = 120)
  @Column(name = "password", nullable = false, unique = true, length = 200)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(	name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(String userName, String email, String password) {
    this.userName = userName;
    this.email = email;
    this.password = password;
  }
}

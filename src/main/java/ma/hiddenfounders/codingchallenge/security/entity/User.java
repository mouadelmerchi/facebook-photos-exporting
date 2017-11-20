package ma.hiddenfounders.codingchallenge.security.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import ma.hiddenfounders.codingchallenge.common.util.InstantAttributeConverter;

@Entity
@Table(name = "User")
public class User implements Serializable {

   private static final long serialVersionUID = 291221059568856254L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "username", nullable = false, length = 50, unique = true)
   @Size(min = 4, max = 50)
   private String username;

   @Column(name = "email", nullable = false, length = 50, unique = true)
   @Email
   @Size(min = 4, max = 50)
   private String email;

   @Column(name = "password", nullable = false, length = 100)
   @Size(min = 4, max = 100)
   private String password;

   @Column(name = "enabled", nullable = false)
   private Boolean enabled;

   @Column(name = "lastPasswordResetDate", nullable = false)
   @Convert(converter = InstantAttributeConverter.class)
   private Instant lastPasswordResetDate;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "User_Authority", joinColumns = {
         @JoinColumn(name = "idUser", referencedColumnName = "id") }, inverseJoinColumns = {
               @JoinColumn(name = "idAuthority", table = "Authority", referencedColumnName = "id") })
   private List<Authority> authorities = new ArrayList<>();

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Boolean getEnabled() {
      return enabled;
   }

   public void setEnabled(Boolean enabled) {
      this.enabled = enabled;
   }

   public Instant getLastPasswordResetDate() {
      return lastPasswordResetDate;
   }

   public void setLastPasswordResetDate(Instant lastPasswordResetDate) {
      this.lastPasswordResetDate = lastPasswordResetDate;
   }

   public List<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(List<Authority> authorities) {
      this.authorities = authorities;
   }

   @Override
   public String toString() {
      return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", enabled="
            + enabled + ", lastPasswordResetDate=" + lastPasswordResetDate + "]";
   }
}
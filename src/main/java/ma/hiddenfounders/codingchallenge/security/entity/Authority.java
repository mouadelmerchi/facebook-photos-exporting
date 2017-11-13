package ma.hiddenfounders.codingchallenge.security.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Authority")
public class Authority implements Serializable {

   private static final long serialVersionUID = -1401999259679455032L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false, length = 50)
   @Enumerated(EnumType.STRING)
   private AuthorityName name;

   public Authority() {
   }

   public Authority(AuthorityName name) {
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public AuthorityName getName() {
      return name;
   }

   public void setName(AuthorityName name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return "Authority [id=" + id + ", name=" + name + "]";
   }
}
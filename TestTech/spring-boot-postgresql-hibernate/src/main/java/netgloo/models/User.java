package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

  @Id
  @NotNull
  @Size(min = 2, max = 50)
  private String name;
  
  @NotNull
  @Size(min = 2, max = 50)
  private String password;

  public User() { }

  public User(String name, String password) {
    this.password = password;
    this.name = name;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
} // class User

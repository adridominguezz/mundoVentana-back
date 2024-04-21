package management.worker.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    //DECLARACION DE LOS ATRIBUTOS DE USER

    @Column(name = "name")
    private String name;
    @Column(name = "Surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @Column (name = "email")
    private  String email;

    @Column (name = "admin")
    private Boolean admin;

    @Column (name = "imgPerfil", nullable = true)
    private String imgPerfil;

    //CONSTRUCTOR


    //GETTERS AND SETTERS
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getAdmin() { return admin; }

    public void setAdmin(Boolean admin) { this.admin = admin; }

    public String getImgPerfil() { return imgPerfil; }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }


}

package com.example.demo.model;

import javax.persistence.*;
import java.util.Collection;


//Declare it as an entity
@Entity
//Table name as 'user' having email as a unique constraint. By default the table name will be class name.
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    //Declare id as a primary key and provide a strategy to generate this id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Custom column name
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;

    /*Many to Many relationship, fetchtype - eager -> retrieve role eagerly
    fetchtype - eager -> retrieve role eagerly
    fetchtype - eager -> retrieve role ondemand
    whenever an operations mentioned in CascadeType enum is performed on parent entity(user),
    it should also be applied to its child entity(role.)
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    /*A new table is needed to save this many to many relationship.
    If joinColumns and inverseJoinColumns are not specified,
    it will by default take both the primary keys from the two tables
    */
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn  (name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )

    private Collection<Role> roles;

    public User(String firstName, String lastName, String email, String password, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
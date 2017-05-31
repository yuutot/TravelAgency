package ua.travel.entity;

import lombok.Data;
import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Id;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.annotations.relations.ManyToOne;

/**
 * Created by yuuto on 5/18/17.
 */

@Data
@Table("users")
public class User {

    @Id
    private Long id;

    @Column("login")
    private String login;

    @Column("password")
    private String password;

    @Column("type")
    @ManyToOne
    private UserType userType;

    @Column("name")
    private String name;

    @Column("surname")
    private String surname;

    @Column("phone")
    private String phone;

}

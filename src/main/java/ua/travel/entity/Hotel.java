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
@Table("hotels")
public class Hotel {

    @Id
    private Long id;

    @Column("city")
    @ManyToOne
    private City city;

    @Column("name")
    private String name;

    @Column("star")
    private Integer star;

    @Column("photo")
    private String photoUrl;
}

package ua.travel.entity;

import lombok.Data;
import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Id;
import ua.travel.dao.annotations.Table;

/**
 * Created by yuuto on 5/18/17.
 */
@Data
@Table("cities")
public class City {

    @Id
    private Long id;

    @Column("name")
    private String name;
}

package ua.travel.entity;

import lombok.Data;
import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Id;
import ua.travel.dao.annotations.Table;

/**
 * Created by yuuto on 5/18/17.
 */
@Data
@Table("user_type")
public class UserType {

    @Id
    private Long id;

    @Column("type")
    private String type;
}
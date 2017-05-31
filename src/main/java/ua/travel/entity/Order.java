package ua.travel.entity;

import lombok.Data;
import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Enum;
import ua.travel.dao.annotations.Id;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.annotations.relations.ManyToOne;
import ua.travel.entity.enums.OrderStatus;

import java.util.Date;

/**
 * Created by yuuto on 5/18/17.
 */
@Data
@Table("orders")
public class Order {

    @Id
    private Long id;

    @Column("tour_id")
    @ManyToOne
    private Tour tour;

    @Column("user_id")
    @ManyToOne
    private User user;

    @Column("status")
    @Enum
    private OrderStatus orderStatus;

    @Column("date")
    private Date date;
}

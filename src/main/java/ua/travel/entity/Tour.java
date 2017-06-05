package ua.travel.entity;

import lombok.Data;
import ua.travel.dao.annotations.Column;
import ua.travel.dao.annotations.Enum;
import ua.travel.dao.annotations.Id;
import ua.travel.dao.annotations.Table;
import ua.travel.dao.annotations.relations.ManyToOne;
import ua.travel.entity.enums.TourType;
import ua.travel.entity.enums.TransportType;

import java.util.Date;

/**
 * Created by yuuto on 5/18/17.
 */
@Data
@Table("tours")
public class Tour {

    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("type")
    @Enum
    private TourType tourType;

    @Column("date_from")
    private Date dateFrom;

    @Column("date_to")
    private Date dateTo;

    @Column("cost")
    private Double cost;

    @Column("description")
    private String description;

    @Column("transport")
    @Enum
    private TransportType transportType;

    @Column("hotel")
    @ManyToOne
    private Hotel hotel;

    @Column("hot")
    private Boolean hot;

    @Column("photo")
    private String photo;

}

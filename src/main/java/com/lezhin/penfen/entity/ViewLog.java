package com.lezhin.penfen.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "view_log")
public class ViewLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private long logId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "contents_id")
    private int contentsId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "view_date")
    private Date viewDate;
}
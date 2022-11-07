package com.cleon.demo.api.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "todos")
@Table(name = "todos")
public class ToDo {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "myDbSeq", initialValue = 201, allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    @Column(name = "ID", updatable = false, nullable = false)
    private int id;

    @Column(name = "USERID", nullable=false)
    private int userId;

    @Column(name = "TITLE", nullable=false, length = 250)
    private String title;

    @Column(name = "COMPLETED", nullable=false)
    private boolean completed;

}

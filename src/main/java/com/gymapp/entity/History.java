package com.gymapp.entity;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

import com.gymapp.converters.YearMonthConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;

@Entity
@Table(name = "History")
public class History {
    @Id
    @SequenceGenerator(name = "History_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  "History_SEQ")
    @Column(name = "history_id")
    private int id;

    @Column(name = "date", unique = true, nullable = false, columnDefinition = "TEXT")
    @Convert(converter = YearMonthConverter.class)
    private YearMonth date;

    @ManyToMany(mappedBy = "histories")
    private Set<GymMember> gymMembers = new HashSet<>();

    public History() {};

    public History(YearMonth date) {
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public YearMonth getDate() {
        return this.date;
    }

    public Set<GymMember> getGymMembers() {
        return this.gymMembers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(YearMonth date) {
        this.date = date;
    }

}

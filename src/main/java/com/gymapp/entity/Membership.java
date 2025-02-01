package com.gymapp.entity;

import com.gymapp.converters.PeriodConverter;

import java.time.Period;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "membership_id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "duration", columnDefinition = "TEXT")
    @Convert(converter = PeriodConverter.class)
    private Period duration;

    @OneToMany(mappedBy = "membership")
    private Set<GymMember> members;

    public Membership() {};

    public Membership(int id, String type, Period duration) {
        this.id = id;
        this.type = type;
        this.duration = duration;
    }

    public int getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public Period getDuration() {
        return this.duration;
    }

    public Set<GymMember> getSubscibedMembers() {
        return this.members;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDuration(Period duration) {
        this.duration = duration;
    }



}

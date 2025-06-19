package com.gymapp.entity;

import com.gymapp.converters.LocalDateConverter;

import java.time.LocalDate;
import java.util.Set;

import java.util.HashSet;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Members")
public class GymMember {
    @Id
    @SequenceGenerator(name = "Members_SEQ", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Members_SEQ")
    @Column(name = "member_id")
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "membership_id", nullable = false)
    private Membership membership;

    @Column(name = "recent_purchase", nullable = false, columnDefinition = "TEXT")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate recentPurchase;

    @Column(name = "expires_at", nullable = false, columnDefinition = "TEXT")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate expiresAt;

    @ManyToMany
    @JoinTable(
        name = "MembersHistory",
        joinColumns = {@JoinColumn(name = "member_id")},
        inverseJoinColumns = {@JoinColumn(name = "history_id")}
    )
    Set<History> histories = new HashSet<>();

    public GymMember() {};

    public GymMember(String firstName, String lastName, Membership membership, LocalDate recentPurchase, LocalDate expiresAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.membership = membership;
        this.recentPurchase = recentPurchase;
        this.expiresAt = expiresAt;
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public String getMembershipType() {
        return membership.getType();
    }

    public LocalDate getRecentPurchase() {
        return this.recentPurchase;
    }

    public LocalDate getExpiresAt() {
        return this.expiresAt;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public void setRecentPurchase(LocalDate recentPurchase) {
        this.recentPurchase = recentPurchase;
    }

    public void setExpiresAt(LocalDate expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }
    
}

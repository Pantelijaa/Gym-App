package com.gymapp.model;

/**
 * The {@code GymMember} class represents a member of a gym.
 * <p>
 * Includes empty Constcructor and Constructor with parameters for all attributes. 
 * Has setters and getters defined for all fields.
 * </p>
 */
public class GymMember {
    private int id;
    private String firstName;
    private String lastName;
    private MembershipType membershipType;    
    private String recentPurchase;
    private String expiresAt;

    public GymMember() {};

    public GymMember(int id, String firstName, String lastName, MembershipType membershipType, String recentPurchase, String expiresAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipType = membershipType;
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

    public MembershipType getMembershipType() {
        return this.membershipType;
    }

    public String getRecentPurchase() {
        return this.recentPurchase;
    }

    public String getExpiresAt() {
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
    
    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public void setRecentPurchase(String recentPurchase) {
        this.recentPurchase = recentPurchase;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
    
}

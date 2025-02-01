package com.gymapp.service;

import java.util.LinkedList;
import java.util.List;
import java.time.Period;

import com.gymapp.dao.MembershipDaoImpl;
import com.gymapp.entity.Membership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MembershipService {

    MembershipDaoImpl mdi;

    public MembershipService() {
        this.mdi = new MembershipDaoImpl();
    }

    public ObservableList<Membership> getAllMemberships() {
        ObservableList<Membership> memberships = FXCollections.observableArrayList(mdi.readAll());
        return memberships;
    }

    public List<String> getAllTypes() {
        List<String> types = new LinkedList<>();
        mdi.readAll().forEach(e -> types.add(e.getType()));

        return types;
    }

    public Membership getByType(String type) {
        Membership membership = null;
        for (Membership m : mdi.readAll()) {
            if (m.getType().equals(type)) {
                membership = m;
            }
        } 
        return membership;
    }

    public void registerNewMembership(String type, Period duration) {
        Membership newMembership = new Membership(type, duration);
        try {
            mdi.create(newMembership);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

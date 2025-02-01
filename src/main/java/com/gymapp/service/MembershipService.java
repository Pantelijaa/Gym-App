package com.gymapp.service;

import java.util.LinkedList;
import java.util.List;

import com.gymapp.dao.MembershipDaoImpl;
import com.gymapp.entity.Membership;

public class MembershipService {

    MembershipDaoImpl mdi;

    public MembershipService() {
        this.mdi = new MembershipDaoImpl();
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


}

package com.gymapp.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import com.gymapp.dao.GymMemberDaoImpl;
import com.gymapp.entity.GymMember;
import com.gymapp.entity.Membership;
import com.gymapp.entity.History;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class GymMemberService {

    GymMemberDaoImpl gmdi;

    public GymMemberService() {
        this.gmdi = new GymMemberDaoImpl();
    }

    public ObservableList<GymMember> getAllMembers() {
        ObservableList<GymMember> gymMembers = FXCollections.observableArrayList(gmdi.readAll());
        return gymMembers;
    }

    public GymMember getById(int id) {
        return gmdi.read(id);
    }

    public int getMembersCount() {
        return this.getAllMembers().size();
    }

    public int getTodayMembersCount() {
        FilteredList<GymMember> todayList = new FilteredList<>(this.getAllMembers(), m -> m.getRecentPurchase() == LocalDate.now());
        
        return todayList.size();
    }

    public int getInactiveMembersCount() {
        FilteredList<GymMember> inactiveList = new FilteredList<>(this.getAllMembers(), m -> m.getExpiresAt().compareTo(LocalDate.now()) < 0);

        return inactiveList.size();
    }

    public HashMap<String, Integer> getActiveMembersCount() {
        HashMap<String, Integer> hm = new HashMap<>();
        FilteredList<GymMember> activeList = new FilteredList<>(this.getAllMembers(), m -> m.getExpiresAt().compareTo(LocalDate.now()) >= 0);

        activeList.forEach(m -> {
           String key = m.getMembership().getType();
            if (hm.containsKey(key)) {
                hm.merge(key, 1, Integer::sum);
            } else {
                hm.put(key, 1);
            }
        });

        return hm;
    }

    public Integer registerNewMember(String firstName, String lastName, Membership membership) throws IllegalArgumentException {
        Integer memberId = null;
        GymMember newMember = new GymMember(firstName,
                                            lastName,
                                            membership,
                                            LocalDate.now(),
                                            LocalDate.now().plus(membership.getDuration()));
        this.addCurentMonthToMemberHystory(newMember); 
        try {
            memberId = gmdi.nextInsertId();
            gmdi.create(newMember);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (memberId == null) {
            throw new IllegalArgumentException();   
        }
        return memberId;
    }
    
    public void extendMembership(int id, Membership newMembership) {
        GymMember member = gmdi.read(id);
        member.setMembership(newMembership);
        member.setRecentPurchase(LocalDate.now());
        member.setExpiresAt(member.getExpiresAt().plus(newMembership.getDuration()));
        this.addCurentMonthToMemberHystory(member); 

        gmdi.update(member);
    }

    private void addCurentMonthToMemberHystory(GymMember member) {
        HistoryService his = new HistoryService();
        Set<History> history = new HashSet<>();
        history.add(his.getCurrentMonth());
        member.setHistories(history);
    }

}

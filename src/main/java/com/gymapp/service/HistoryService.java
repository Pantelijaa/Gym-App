package com.gymapp.service;

import java.time.YearMonth;
import java.util.HashMap;

import com.gymapp.dao.HistoryDaoImpl;
import com.gymapp.entity.History;

public class HistoryService {

    HistoryDaoImpl hdi;

    public HistoryService() {
        this.hdi = new HistoryDaoImpl();
    }

    public HashMap<YearMonth, Integer> getHistoryOfMembersPerMonth() {
        HashMap<YearMonth, Integer> hm = new HashMap<>();

        hdi.readAll().forEach(e -> {
            hm.put(e.getDate(), e.getGymMembers().size());
        });

        return hm;
    } 

    public void addCurrentMonth() {
        YearMonth now = YearMonth.now();

        for (History history : hdi.readAll()) {
            if (history.getDate().equals(now)) return;
        }

        hdi.create(new History(YearMonth.now()));
    }

    public History getCurrentMonth() {
        YearMonth now = YearMonth.now();
        History currentMonth = null;

        for (History history : hdi.readAll()) {
            if (history.getDate().equals(now)) {
                currentMonth = history;
            }
        }
        return currentMonth;
    }


}

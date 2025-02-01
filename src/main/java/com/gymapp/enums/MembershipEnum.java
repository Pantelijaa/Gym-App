package com.gymapp.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public enum MembershipEnum {
    DAYS {
        int start = 1;
        int end = 31;
        @Override
        public List<Integer> getAvailableOptions() {
            return generateAvailableOptions(start, end);
        }

        @Override
        public String getModifier() {
            return "D";
        }

        @Override
        public Integer getMax() {
            return end;
        }
    }, MONTHS {
        int start = 1;
        int end = 12;
        @Override
        public List<Integer> getAvailableOptions() {
            return generateAvailableOptions(start, end);
        }

        @Override
        public String getModifier() {
            return "M";
        }

        @Override
        public Integer getMax() {
            return end;
        }
    }, YEARS {
        int start = 1;
        int end = 5;
        @Override
        public List<Integer> getAvailableOptions() {
            return generateAvailableOptions(start, end);
        }

        @Override
        public String getModifier() {
            return "Y";
        }

        @Override
        public Integer getMax() {
            return end;
        }
    };

    abstract public List<Integer> getAvailableOptions();
    abstract public String getModifier();
    abstract public Integer getMax();

    List<Integer> generateAvailableOptions(int startInclusive, int endInclusive) {
            Stream<Integer> optionsStream = IntStream.range(startInclusive, endInclusive + 1).boxed();
            List<Integer> optionsList = optionsStream.collect(Collectors.toList());
            return optionsList;
    }

}

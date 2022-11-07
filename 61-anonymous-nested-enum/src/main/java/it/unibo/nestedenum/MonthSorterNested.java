package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);
        
        int dayNumber;
    
        Month(int dayN){
            this.dayNumber=dayN;
        }

        static Month fromString(String input){
            try{
                Objects.requireNonNull(input);
                return Month.valueOf(input.toUpperCase());
            }
            catch(IllegalArgumentException ex){
                Month out=null;
                for(Month i: Month.values()){
                    if(i.toString().toUpperCase().startsWith(input.toUpperCase())){
                        if(out!=null){
                            throw new IllegalArgumentException("Ambiguos name: there is more than one month that starts with " + input);
                        }
                        out=i;
                    }
                }
                if(out==null){
                    throw new IllegalArgumentException("No month found matching the string: " + input);
                }
                return out;
            }
        }
    }

    static class SortByMonthOrder implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return Month.fromString(o1).compareTo(Month.fromString(o2));
        }
    }

    static class SortByDate implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return Integer.compare(Month.fromString(o1).dayNumber, Month.fromString(o2).dayNumber);
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }
}

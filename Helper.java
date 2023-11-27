
import com.ib.client.Bar;
import org.ta4j.core.BarSeries;

import java.time.*;
import java.util.ArrayList;
import java.util.List;


public class Helper {
    public Helper() {
    }

    static ZonedDateTime String2ZonedDateTime(String d) {
        ZonedDateTime zdt = ZonedDateTime.of(Integer.parseInt(d.substring(0, 4)), Integer.parseInt(d.substring(4, 6)),
                Integer.parseInt(d.substring(6, 8)), 0, 0, 0, 0,
                ZoneId.systemDefault());
       // System.out.println(zdt);
        return zdt;
    }

    static LocalDateTime String2DateTime(String d) {
        LocalDateTime dt = LocalDateTime.of(Integer.parseInt(d.substring(0, 4)), Integer.parseInt(d.substring(4, 6)),
                Integer.parseInt(d.substring(6, 8)), Integer.parseInt("4"), Integer.parseInt("30"), 0, 0);
        return dt;
    }

    static String localDate2String(LocalDate dt) {
        String s = "" + dt.getYear() + dt.getMonthValue() + dt.getDayOfMonth() + " 12:59:59";
        return s;
    }

    static String duration2String(Duration d) {
        return "" + (int) (d.toDays() / 30) + " M";

    }

    static String printBar(org.ta4j.core.Bar bar) {
        return String.format("%10s: $%6.2f, $%6.2f, $%6.2f, $%6.2f, %6s\n",
                bar.getEndTime().toLocalDate().toString(),
                bar.getClosePrice().doubleValue(),
                bar.getOpenPrice().doubleValue(),
                bar.getLowPrice().doubleValue(),
                bar.getHighPrice().doubleValue(),
                volume2String((long) bar.getVolume().intValue()));
    }

    static String volume2String(long l) {
        return String.format("%6.2fM", (double) l / 1000000.0);
    }

    static int indexAtDay(org.ta4j.core.BarSeries barSeries, LocalDate d) {
        int nBars= barSeries.getBarCount();
        List<LocalDate> dates= new ArrayList<>();
        for(int i=0;i<nBars;i++) dates.add(barSeries.getBar(i).getEndTime().toLocalDate());
        return dates.indexOf(d);
    }
}

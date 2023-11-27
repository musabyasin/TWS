import com.ib.client.Contract;
import com.ib.client.Types;
import org.ta4j.core.BarSeries;
import org.ta4j.core.num.Num;

import java.time.Duration;
import java.time.LocalDate;

public class Stock {
    String symbol;
    BarSeries barSeries;
    public Stock( String symbol) {
        this.symbol = symbol;
    }
    Contract makeContract() {
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType(Types.SecType.STK);
        contract.exchange("SMART");
        contract.currency("USD");
        return contract;
    }

//     org.ta4j.core.BarSeries getHistoricalData() throws InterruptedException {
//                controller.eClientSocket.reqHistoricalData(0, makeContract(),
//                Helper.localDate2String(endDate),  //"20231123 12:59:59"
//                Helper.duration2String(duration),  //"6 M"
//                barSize, ////     "1 day"
//                "TRADES", 1, 1, false, null);
//                Thread.sleep(1000L);
//                return controller.series;
//    }

    double keepGain(org.ta4j.core.BarSeries barSeries, LocalDate d, int period) {
        double gain;
        int n= Helper.indexAtDay(barSeries,d);
        Num n2 = barSeries.getBar(n).getClosePrice();
        Num n1 = barSeries.getBar(n-period).getClosePrice();
        gain=  n2.dividedBy(n1).doubleValue();
        //System.out.println(gain);
        return gain;


    }
}

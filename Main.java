
import com.ib.client.*;
import com.ib.client.Types.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import org.jfree.data.general.CombinedDataset;
import org.jfree.data.general.Dataset;
import org.ta4j.core.*;
import org.ta4j.core.Bar;
import org.ta4j.core.Trade.*;
import org.ta4j.core.criteria.pnl.*;
import org.ta4j.core.indicators.*;
import org.ta4j.core.indicators.helpers.*;
import org.ta4j.core.num.*;
import org.ta4j.core.rules.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
/////import org.jfree.chart.ui.ApplicationFrame;
///import org.jfree.chart.ui.UIUtils;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;

public class Main {
    String symbol = "AXON";
    Duration duration = Duration.ofDays(360);  //String duration = "6 M";
    String barSize = "1 day";
    LocalDate endDate = LocalDate.of(2023, 11, 22);  // String endDateTime = "20231123 12:59:59";
    BarSeries barSeries;
    int period;

    public Main(String symbol, Duration duration, String barSize, LocalDate endDate, BarSeries barSeries, int period) {
        this.symbol = symbol;
        this.duration = duration;
        this.barSize = barSize;
        this.endDate = endDate;
        this.barSeries = barSeries;
        this.period = period;
    }

    public Main() {

    }

    public static void main(String[] args) throws Exception {
        double r=1;

//        List<String> symbols= List.of("ARCC","IMGN","YMM","GTLB","AMAT","ROST",
//                "WSM","AEG","COIN","TME","CRSP");

        List<String> symbols = makeStocksList();
        Main mm = new Main();
        Controller controller = new Controller();
        controller.eClientSocket.eConnect("127.0.0.1", 7496, 0);
        EReader reader = new EReader(controller.eClientSocket, controller.eClientSocket.getSignal());
        reader.start();
        (new Thread(() -> {
            while (true) {
                controller.eClientSocket.getSignal().waitForSignal();

                try {
                    reader.processMsgs();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        })).start();
        double totalGain = 1, gain;
        for (int i = 0; i < symbols.size(); i++) {
            Stock stock = new Stock(symbols.get(i));
            //  controller.ll.add((new BaseBarSeriesBuilder()).withName("STOCK").build());
            Contract contract = stock.makeContract();
            controller.ll.add((new BaseBarSeriesBuilder()).withName(symbols.get(i)).build());
            controller.eClientSocket.reqHistoricalData(0, contract,
                    //  Helper.localDate2String(mm.endDate),
                    "20231123 12:59:59",
                    //  Helper.duration2String(mm.duration),
                    "12 M",
                    "1 day",
                    // mm.barSize, ////     "1 day"
                    "TRADES", 1, 1, false, null);
            Thread.sleep(1000L);
            stock.barSeries = controller.ll.get(controller.ll.size() - 1);
            //ta4j
       //     r=r+check(stock.barSeries);
            gain = stock.keepGain(stock.barSeries, mm.endDate, 90);
            System.out.printf("%8s => %6.2f%%\n", stock.barSeries.getName(), gain);
            totalGain += gain - 1;

        }
    //    System.out.println("# of stocks = " + controller.ll.size());

     //   System.out.printf("Total gain ratio = %6.2f%%", totalGain / controller.ll.size() * 100);// mm.keepGain(mm.barSeries,));

    //    printSum(controller.ll);
   //     r=r+check(sum(controller.ll));

      //  System.out.println("r=  "+r/26);
    }
    static List<String> makeStocksList() throws Exception {
        URL url = Main.class.getResource("stocks.txt");
        File stockFile = Paths.get(url.toURI()).toFile();
        List<String> stocks = new ArrayList<>(Files.lines(stockFile.toPath()).
                filter(i -> !(i.trim().equals(""))).
                filter(i -> !(i.startsWith("/"))).
                toList());
        return stocks;
    }

    static BarSeries sum(List<BarSeries> list) {
        double openSum = 0, highSum = 0, lowSum = 0, closeSum = 0;
        int volumeSum = 0;
        BarSeries b = null;
        BarSeries barSeries = new BaseBarSeriesBuilder().withName("SUM").build();
        ZonedDateTime zdt = null;
        int barCount = list.get(0).getBarCount();
        int seriesCount = list.size();
        for (int i = 0; i < barCount; i++) {
            for (int j = 0; j < seriesCount; j++) {
                b = list.get(j);
                openSum = openSum + b.getBar(i).getOpenPrice().doubleValue();
                highSum = highSum + b.getBar(i).getHighPrice().doubleValue();
                lowSum = lowSum + b.getBar(i).getLowPrice().doubleValue();
                closeSum = closeSum + b.getBar(i).getClosePrice().doubleValue();
                volumeSum = volumeSum + b.getBar(i).getVolume().intValue();
            }
            zdt = b.getBar(i).getEndTime();
            barSeries.addBar(zdt, openSum, highSum, lowSum, closeSum, volumeSum);
            openSum = highSum = lowSum = closeSum = 0;
            volumeSum = 0;
        }
        return barSeries;
    }

    static void printSum(List<BarSeries> list) {
        BarSeries barSeries = sum(list);
        for (int i = 0; i < barSeries.getBarCount(); i++) {
            System.out.printf("%12s , $%6.2f\n",
                    barSeries.getBar(i).getEndTime().toLocalDate().toString(),
                    barSeries.getBar(i).getClosePrice().doubleValue());

        }
    }

//    private static void displayChart(JFreeChart chart) {
//        // Chart panel
//        ChartPanel panel = new ChartPanel(chart);
//        panel.setFillZoomRectangle(true);
//        panel.setMouseZoomable(true);
//        panel.setPreferredSize(new java.awt.Dimension(500, 270));
//        // Application frame
//        JFrame frame = new JFrame("Ta4j example - Indicators to chart");
//        frame.setContentPane(panel);
//        frame.pack();
//       // UIUtils.centerFrameOnScreen(frame);
//        frame.setVisible(true);
//    }
//}

    static double  check(BarSeries barSeries) {
        int s=0, f=0;
        for(int i=2;i<barSeries.getBarCount();i++){
            if(
                    barSeries.getBar(i-1).getClosePrice().doubleValue()>barSeries.getBar(i-2).getClosePrice().doubleValue()
                && barSeries.getBar(i-1).getVolume().longValue()> barSeries.getBar(i-2).getVolume().longValue()){
                if( barSeries.getBar(i).getClosePrice().doubleValue()>barSeries.getBar(i-1).getClosePrice().doubleValue())
                                s++;
                else             f++;
            }
        }
        return (double)s/f;


    }
}

/*
 ClosePriceIndicator closePrice = new ClosePriceIndicator(controller.series);
        new SMAIndicator(closePrice, 20);
        SMAIndicator shortSma = new SMAIndicator(closePrice, 5);
        SMAIndicator longSma = new SMAIndicator(closePrice, 30);
        Rule rBuy = new CrossedUpIndicatorRule(shortSma, longSma);
        Rule rSell = (new CrossedDownIndicatorRule(shortSma, longSma)).or(new StopLossRule(closePrice, DecimalNum.valueOf("5"))).or(new StopGainRule(closePrice, DecimalNum.valueOf("99")));
        Strategy strategy = new BaseStrategy(rBuy, rSell);
        BarSeriesManager manager = new BarSeriesManager(controller.series);
        TradingRecord tradingRecord = manager.run(strategy, TradeType.BUY, DecimalNum.valueOf(1000));

        for (int i = 0; i < tradingRecord.getPositions().size(); ++i) {
            //      System.out.println(tradingRecord.getPositions().get(i));
            //     System.out.println(controller.series.getBar(((Position)tradingRecord.getPositions().get(i)).getEntry().getIndex()).getEndTime().toLocalDate());
            //    System.out.println(controller.series.getBar(((Position)tradingRecord.getPositions().get(i)).getExit().getIndex()).getEndTime().toLocalDate());
        }

        AnalysisCriterion profitTradesRatio = new GrossReturnCriterion();
        Num p = profitTradesRatio.calculate(controller.series, tradingRecord);
 */


import com.ib.client.*;
import java.io.*;
import java.time.*;
import java.util.*;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.num.DecimalNum;

class Controller implements EWrapper {
    private final ClientHandler.ILogger m_outlogger = new ClientHandler.ILogger() {
        @Override
        public void log(String s) {
        }
    };
    private final ClientHandler.ILogger m_inlogger = new ClientHandler.ILogger() {
        @Override
        public void log(String s) {
        }
    };
    static EJavaSignal readerSignal = new EJavaSignal();

    public ClientHandler eClientSocket;
    List<org.ta4j.core.BarSeries>  ll= new ArrayList<>();
    //org.ta4j.core.BarSeries series = (new BaseBarSeriesBuilder()).withName("STOCK").build();

    public Controller() {
        this.eClientSocket = new ClientHandler(this, this.m_inlogger, this.m_outlogger);
    }

    public void connectAck() {
        System.out.println("Connected");
    }

    public void userInfo(int i, String s) {
        System.out.println(s);
    }

    public void historicalData(int i, Bar bar) {
        this.ll.get(ll.size()-1).addBar(Duration.ofDays(1L),
                Helper.String2ZonedDateTime(bar.time()),
                DecimalNum.valueOf(bar.open()),
                DecimalNum.valueOf(bar.high()),
                DecimalNum.valueOf(bar.low()),
                DecimalNum.valueOf(bar.close()),
                DecimalNum.valueOf(bar.volume().longValue()));
    }

    public void currentTime(long l) {
        Instant var10001 = (new Date(l * 1000L)).toInstant();
        System.out.println("Time @ Server now: " + String.valueOf(var10001.atZone(ZoneId.systemDefault()).toLocalDate()));
    }

    public void tickPrice(int i, int i1, double v, TickAttrib tickAttrib) {
    }

    public void tickSize(int i, int i1, Decimal decimal) {
    }

    public void tickOptionComputation(int i, int i1, int i2, double v, double v1, double v2, double v3, double v4, double v5, double v6, double v7) {
    }

    public void tickGeneric(int i, int i1, double v) {
    }

    public void tickString(int i, int i1, String s) {
    }

    public void tickEFP(int i, int i1, double v, String s, double v1, int i2, String s1, double v2, double v3) {
    }

    public void orderStatus(int i, String s, Decimal decimal, Decimal decimal1, double v, int i1, int i2, double v1, int i3, String s1, double v2) {
    }

    public void openOrder(int i, Contract contract, Order order, OrderState orderState) {
    }

    public void openOrderEnd() {
    }

    public void updateAccountValue(String s, String s1, String s2, String s3) {
    }

    public void updatePortfolio(Contract contract, Decimal decimal, double v, double v1, double v2, double v3, double v4, String s) {
    }

    public void updateAccountTime(String s) {
    }

    public void accountDownloadEnd(String s) {
    }

    public void nextValidId(int i) {
    }

    public void contractDetails(int i, ContractDetails contractDetails) {
    }

    public void bondContractDetails(int i, ContractDetails contractDetails) {
    }

    public void contractDetailsEnd(int i) {
    }

    public void execDetails(int i, Contract contract, Execution execution) {
    }

    public void execDetailsEnd(int i) {
    }

    public void updateMktDepth(int i, int i1, int i2, int i3, double v, Decimal decimal) {
    }

    public void updateMktDepthL2(int i, int i1, String s, int i2, int i3, double v, Decimal decimal, boolean b) {
    }

    public void updateNewsBulletin(int i, int i1, String s, String s1) {
    }

    public void managedAccounts(String s) {
    }

    public void receiveFA(int i, String s) {
    }

    public void scannerParameters(String s) {
    }

    public void scannerData(int i, int i1, ContractDetails contractDetails, String s, String s1, String s2, String s3) {
    }

    public void scannerDataEnd(int i) {
    }

    public void realtimeBar(int i, long l, double v, double v1, double v2, double v3, Decimal decimal, Decimal decimal1, int i1) {
    }

    public void fundamentalData(int i, String s) {
    }

    public void deltaNeutralValidation(int i, DeltaNeutralContract deltaNeutralContract) {
    }

    public void tickSnapshotEnd(int i) {
    }

    public void marketDataType(int i, int i1) {
    }

    public void commissionReport(CommissionReport commissionReport) {
    }

    public void position(String s, Contract contract, Decimal decimal, double v) {
    }

    public void positionEnd() {
    }

    public void accountSummary(int i, String s, String s1, String s2, String s3) {
    }

    public void accountSummaryEnd(int i) {
    }

    public void verifyMessageAPI(String s) {
    }

    public void verifyCompleted(boolean b, String s) {
    }

    public void verifyAndAuthMessageAPI(String s, String s1) {
    }

    public void verifyAndAuthCompleted(boolean b, String s) {
    }

    public void displayGroupList(int i, String s) {
    }

    public void displayGroupUpdated(int i, String s) {
    }

    public void error(Exception e) {
    }

    public void error(String s) {
    }

    public void error(int i, int i1, String s, String s1) {
    }

    public void connectionClosed() {
    }

    public void positionMulti(int i, String s, String s1, Contract contract, Decimal decimal, double v) {
    }

    public void positionMultiEnd(int i) {
    }

    public void accountUpdateMulti(int i, String s, String s1, String s2, String s3, String s4) {
    }

    public void accountUpdateMultiEnd(int i) {
    }

    public void securityDefinitionOptionalParameter(int i, String s, int i1, String s1, String s2, Set<String> set, Set<Double> set1) {
    }

    public void securityDefinitionOptionalParameterEnd(int i) {
    }

    public void softDollarTiers(int i, SoftDollarTier[] softDollarTiers) {
    }

    public void familyCodes(FamilyCode[] familyCodes) {
    }

    public void symbolSamples(int i, ContractDescription[] contractDescriptions) {
    }

    public void historicalDataEnd(int i, String s, String s1) {
    }

    public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {
    }

    public void tickNews(int i, long l, String s, String s1, String s2, String s3) {
    }

    public void smartComponents(int i, Map<Integer, Map.Entry<String, Character>> map) {
    }

    public void tickReqParams(int i, double v, String s, int i1) {
    }

    public void newsProviders(NewsProvider[] newsProviders) {
    }

    public void newsArticle(int i, int i1, String s) {
    }

    public void historicalNews(int i, String s, String s1, String s2, String s3) {
    }

    public void historicalNewsEnd(int i, boolean b) {
    }

    public void headTimestamp(int i, String s) {
    }

    public void histogramData(int i, List<HistogramEntry> list) {
    }

    public void historicalDataUpdate(int i, Bar bar) {
    }

    public void rerouteMktDataReq(int i, int i1, String s) {
    }

    public void rerouteMktDepthReq(int i, int i1, String s) {
    }

    public void marketRule(int i, PriceIncrement[] priceIncrements) {
    }

    public void pnl(int i, double v, double v1, double v2) {
    }

    public void pnlSingle(int i, Decimal decimal, double v, double v1, double v2, double v3) {
    }

    public void historicalTicks(int i, List<HistoricalTick> list, boolean b) {
    }

    public void historicalTicksBidAsk(int i, List<HistoricalTickBidAsk> list, boolean b) {
    }

    public void historicalTicksLast(int i, List<HistoricalTickLast> list, boolean b) {
    }

    public void tickByTickAllLast(int i, int i1, long l, double v, Decimal decimal, TickAttribLast tickAttribLast, String s, String s1) {
    }

    public void tickByTickBidAsk(int i, long l, double v, double v1, Decimal decimal, Decimal decimal1, TickAttribBidAsk tickAttribBidAsk) {
    }

    public void tickByTickMidPoint(int i, long l, double v) {
    }

    public void orderBound(long l, int i, int i1) {
    }

    public void completedOrder(Contract contract, Order order, OrderState orderState) {
    }

    public void completedOrdersEnd() {
    }

    public void replaceFAEnd(int i, String s) {
    }

    public void wshMetaData(int i, String s) {
    }

    public void wshEventData(int i, String s) {
    }

    public void historicalSchedule(int i, String s, String s1, String s2, List<HistoricalSession> list) {
    }
}




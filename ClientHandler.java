//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.ib.client.Contract;
import com.ib.client.EClientErrors;
import com.ib.client.EClientSocket;
import com.ib.client.EJavaSignal;
import com.ib.client.EMessage;
import com.ib.client.EWrapper;
import com.ib.client.Order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class ClientHandler extends EClientSocket {
    public static final char EOL = '\u0000';
    public static final char LOG_EOL = '_';
    private static final EJavaSignal m_signal = new EJavaSignal();
    private final ILogger m_inLogger;
    private final ILogger m_outLogger;

    public ClientHandler(EWrapper wrapper, ILogger inLogger, ILogger outLogger) {
        super(wrapper, m_signal);
        this.m_inLogger = inLogger;
        this.m_outLogger = outLogger;
    }

    public EJavaSignal getSignal() {
        return m_signal;
    }

    protected void sendMsg(EMessage msg) throws IOException {
        super.sendMsg(msg);
        byte[] buf = msg.getRawData();
        if (this.m_outLogger != null) {
            this.m_outLogger.log(new String(buf, 0, buf.length, StandardCharsets.UTF_8));
        }

    }

    public int readInt() throws IOException {
        int c = super.readInt();
        if (this.m_inLogger != null) {
            this.m_inLogger.log(String.valueOf((char) c));
        }

        return c;
    }

    public int read(byte[] buf, int off, int len) throws IOException {
        int n = super.read(buf, off, len);
        if (this.m_inLogger != null) {
            this.m_inLogger.log(new String(buf, 0, n, StandardCharsets.UTF_8));
        }

        return n;
    }

    public synchronized void placeOrder(Contract contract, Order order) {
        if (!this.isConnected()) {
            this.notConnected();
        } else if (this.serverVersion() < 66) {
            this.error(-1, EClientErrors.UPDATE_TWS, "ApiController requires TWS build 932 or higher to place orders.");
        } else {
            this.placeOrder(order.orderId(), contract, order);
        }
    }

    public interface ILogger {
        void log(String var1);
    }
}

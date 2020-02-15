package connectivity.fix;

import dto.Placement;
import dto.Side;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.field.*;
import quickfix.fix42.ExecutionReport;
import quickfix.fix42.MarketDataSnapshotFullRefresh;
import state.LimitOrderBook;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum FixMessageHandler {

    MARKET_DATA_REQUEST {
        @Override
        String getRequestMsgType() {
            return MsgType.MARKET_DATA_REQUEST;
        }

        @Override
        Message handle(Message message, LimitOrderBook limitOrderBook) throws FieldNotFound {
            MarketDataSnapshotFullRefresh msg = new MarketDataSnapshotFullRefresh();
            limitOrderBook.streamBids()
                    .forEachOrdered(limitEntry -> {
                        MarketDataSnapshotFullRefresh.NoMDEntries group = new MarketDataSnapshotFullRefresh.NoMDEntries();
                        group.set(new MDEntryType('0'));
                        group.set(new MDEntryPx(limitEntry.getLongKey()));
                        group.set(new MDEntrySize(limitEntry.getValue().getVolume()));
                        msg.addGroup(group);
                    });

            limitOrderBook.streamOffers()
                    .forEachOrdered(limitEntry -> {
                        MarketDataSnapshotFullRefresh.NoMDEntries group = new MarketDataSnapshotFullRefresh.NoMDEntries();
                        group.set(new MDEntryType('1'));
                        group.set(new MDEntryPx(limitEntry.getLongKey()));
                        group.set(new MDEntrySize(limitEntry.getValue().getVolume()));
                        msg.addGroup(group);
                    });

            return msg;
        }
    },

    NEW_SINGLE_ORDER {
        @Override
        String getRequestMsgType() {
            return MsgType.ORDER_SINGLE;
        }

        @Override
        Message handle(Message message, LimitOrderBook limitOrderBook) throws FieldNotFound {
            char msgSide = message.getChar(quickfix.field.Side.FIELD);
            Side side = message.getChar(quickfix.field.Side.FIELD) == '1' ? Side.BID : Side.OFFER;
            long price = (long) message.getDouble(Price.FIELD);
            long amount = (long) message.getDouble(OrderQty.FIELD);
            Placement placement = new Placement(side, price, amount);
            String placementId = "" + placement.getId();
            BigDecimal averageSalePrice = limitOrderBook.getAverageSalePrice(amount);
            limitOrderBook.place(placement);
            char ordStatus = averageSalePrice.equals(BigDecimal.ZERO) ? OrdStatus.NEW : OrdStatus.FILLED;
            double leavesQty = averageSalePrice.equals(BigDecimal.ZERO) ? amount : 0.;
            return new ExecutionReport(
                    new OrderID(placementId),
                    new ExecID(placementId),
                    new ExecTransType(ExecTransType.NEW),
                    new ExecType(ordStatus),
                    new OrdStatus(ordStatus),
                    new Symbol("JLOB"),
                    new quickfix.field.Side(msgSide),
                    new LeavesQty(leavesQty),
                    new CumQty(amount - leavesQty),
                    new AvgPx(averageSalePrice.doubleValue())
            );
        }
    };

    public static final Map<String, FixMessageHandler> handlerMap = stream(values()).collect(
            toMap(FixMessageHandler::getRequestMsgType, identity())
    );

    public static FixMessageHandler lookUp(String msgType) {
        return handlerMap.get(msgType);
    }

    abstract String getRequestMsgType();
    abstract Message handle(Message message,  LimitOrderBook limitOrderBook) throws FieldNotFound;

}

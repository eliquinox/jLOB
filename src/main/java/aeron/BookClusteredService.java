package aeron;

import dto.Placement;
import dto.Side;
import io.aeron.ExclusivePublication;
import io.aeron.Image;
import io.aeron.cluster.codecs.CloseReason;
import io.aeron.cluster.service.ClientSession;
import io.aeron.cluster.service.Cluster;
import io.aeron.cluster.service.ClusteredService;
import io.aeron.logbuffer.Header;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.ExpandableDirectByteBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.IdleStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import state.LimitOrderBook;

import static dto.Placement.placement;

public class BookClusteredService implements ClusteredService {

    public static int SIDE_OFFSET = 0;
    public static int PRICE_OFFSET = SIDE_OFFSET + BitUtil.SIZE_OF_INT;
    public static int SIZE_OFFSET = PRICE_OFFSET + BitUtil.SIZE_OF_LONG;

    private final MutableDirectBuffer egressMessageBuffer = new ExpandableDirectByteBuffer();

    private Cluster cluster;
    private IdleStrategy idleStrategy;
    private final LimitOrderBook limitOrderBook = LimitOrderBook.empty();

    private final Logger log = LoggerFactory.getLogger(BookClusteredService.class);

    @Override
    public void onStart(Cluster cluster, Image snapshotImage) {
        System.out.println("Cluster service started");
        this.cluster = cluster;
        this.idleStrategy = cluster.idleStrategy();
    }

    @Override
    public void onSessionOpen(ClientSession session, long timestamp) {

    }

    @Override
    public void onSessionClose(ClientSession session, long timestamp, CloseReason closeReason) {

    }

    @Override
    public void onSessionMessage(
            ClientSession session,
            long timestamp,
            DirectBuffer buffer,
            int offset,
            int length,
            Header header
    ) {
        // |---offset---|---int side---|---long price---|---long size---|
        log.info("Session message: {}", buffer);
        final Side side = Side.fromInt(buffer.getInt(offset + SIDE_OFFSET));
        final long price = buffer.getLong(offset + PRICE_OFFSET);
        final long size = buffer.getLong(offset + SIZE_OFFSET);

        final Placement placement = placement()
                .withSide(side)
                .withPrice(price)
                .withSize(size)
                .build();

        log.info("Parsed placement: {}", placement);
        limitOrderBook.place(placement);

        // session is null during replay
        if (session != null) {
//            byte[] limitOrderBookBytes = SerializationUtils.serialize(limitOrderBook);
//            egressMessageBuffer.putBytes(0, limitOrderBookBytes);
            egressMessageBuffer.putLong(0, 1);
            log.info("Responding with {}", egressMessageBuffer);
            while (session.offer(egressMessageBuffer, 0, 8) < 0) {
                idleStrategy.idle();
            }
        }
    }

    @Override
    public void onTimerEvent(long correlationId, long timestamp) {
        System.out.println("In onTimerEvent: " + correlationId + " : " + timestamp);
    }

    @Override
    public void onTakeSnapshot(ExclusivePublication snapshotPublication) {
        System.out.println("In onTakeSnapshot: " + snapshotPublication);
    }

    @Override
    public void onRoleChange(Cluster.Role newRole) {
        System.out.println("In onRoleChange: " + newRole);
    }

    @Override
    public void onTerminate(Cluster cluster) {
        System.out.println("In onTerminate: " + cluster);
    }
}

package aeron;

import io.aeron.CommonContext;
import io.aeron.cluster.client.AeronCluster;
import io.aeron.cluster.client.EgressListener;
import io.aeron.driver.MediaDriver;
import io.aeron.driver.ThreadingMode;
import io.aeron.logbuffer.Header;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.ExpandableDirectByteBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.SleepingMillisIdleStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static aeron.BookClusterNode.calculatePort;
import static aeron.BookClusteredService.*;
import static java.util.Collections.singletonList;

public class BookClusterClient implements EgressListener {

    private static AeronCluster clusterClient;
    private static final int CLIENT_FACING_PORT_OFFSET = 3;
    private static final String LOCALHOST = "localhost";


    // |---int side = 0 (BID)---|---long price = 100---|---long size = 100---|
    private final MutableDirectBuffer placementBuffer = new ExpandableDirectByteBuffer();
    private final int placementBufferSize = BitUtil.SIZE_OF_INT + BitUtil.SIZE_OF_LONG + BitUtil.SIZE_OF_LONG;
    private final IdleStrategy idleStrategy = new SleepingMillisIdleStrategy();
    private final Logger log = LoggerFactory.getLogger(BookClusterClient.class);

    @Override
    public void onMessage(
            long clusterSessionId,
            long timestamp,
            DirectBuffer buffer,
            int offset,
            int length,
            Header header
    ) {
        log.info("Client message received: {}", buffer);
    }

    private void sendPlacement(AeronCluster cluster) {
        placementBuffer.putInt(SIDE_OFFSET, 0);
        placementBuffer.putLong(PRICE_OFFSET, 100);
        placementBuffer.putLong(SIZE_OFFSET, 100);
        log.info("Sending placement: {}", placementBuffer);
        cluster.offer(placementBuffer, 0, placementBufferSize);
    }

    public static String ingressEndpoints(final List<String> hostnames) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hostnames.size(); i++)  {
            sb.append(i).append('=');
            sb.append(hostnames.get(i)).append(':').append(calculatePort(i, CLIENT_FACING_PORT_OFFSET));
            sb.append(',');
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static void main(String[] args) {
        final String aeronDirName = CommonContext.getAeronDirectoryName() + "-client";
        final int egressPort = 19001;  // change for different clients
        final BookClusterClient bookClusterClient = new BookClusterClient();

        MediaDriver clientMediaDriver = MediaDriver.launch(
                new MediaDriver.Context()
                        .threadingMode(ThreadingMode.SHARED)
                        .dirDeleteOnStart(true)
                        .errorHandler(Throwable::printStackTrace)
                        .aeronDirectoryName(aeronDirName)
        );

        clusterClient = AeronCluster.connect(
                new AeronCluster.Context()
                        .egressListener(bookClusterClient)
                        .egressChannel("aeron:udp?endpoint=localhost:" + egressPort)
                        .aeronDirectoryName(clientMediaDriver.aeronDirectoryName())
                        .ingressChannel("aeron:udp")
                        .ingressEndpoints(ingressEndpoints(singletonList(LOCALHOST))));

        bookClusterClient.sendPlacement(clusterClient);
        while (true) clusterClient.pollEgress();
    }
}

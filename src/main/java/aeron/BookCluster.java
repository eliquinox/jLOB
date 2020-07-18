package aeron;

import org.agrona.concurrent.ShutdownSignalBarrier;

public class BookCluster {
    public static void main(String[] args) {
        ShutdownSignalBarrier barrier = new ShutdownSignalBarrier();
        BookClusterNode clusterNode = new BookClusterNode(barrier);
        clusterNode.start(false);
        barrier.await();
    }
}

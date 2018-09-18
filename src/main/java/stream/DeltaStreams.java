package stream;

import delta.Delta;
import delta.Side;
import delta.Placement;
import state.Limit;

import java.util.*;
import java.util.stream.*;

public class DeltaStreams {

    private static final Random RND = new Random();
    private static final List<Long> SIZES = RND.longs(1000).boxed().collect(Collectors.toList());
    private static final List<Long> PRICES = LongStream.iterate(9750, d -> d + 5).limit(100).boxed().collect(Collectors.toList());
    private static final List<Limit> BID_LIMITS = PRICES.stream().filter(p -> p < 10000L).map(p -> new Limit(Side.BID,p)).collect(Collectors.toList());
    private static final List<Limit> OFFER_LIMITS = PRICES.stream().filter(p -> p > 10000L).map(p -> new Limit(Side.OFFER,p)).collect(Collectors.toList());

    public static Stream<Placement> getDummyBidPlacementStream(){
        return Stream.generate(() -> new Placement(BID_LIMITS.get(RND.nextInt(BID_LIMITS.size())),
                                                   SIZES.get(RND.nextInt(SIZES.size()))));
    }

    public static Stream<Delta> getDummyOfferPlacementStream(){
        return Stream.generate(() -> new Placement(OFFER_LIMITS.get(RND.nextInt(OFFER_LIMITS.size())),
                                                   SIZES.get(RND.nextInt(SIZES.size()))));
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> cls){
        int x = RND.nextInt(cls.getEnumConstants().length);
        return cls.getEnumConstants()[x];
    }

}

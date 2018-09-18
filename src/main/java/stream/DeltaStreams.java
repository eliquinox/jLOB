package stream;

import delta.Side;
import delta.Placement;
import state.Limit;

import java.util.*;
import java.util.stream.*;

public class DeltaStreams {

    private static final Random RND = new Random();
    private static final List<Long> SIZES = RND.longs(1000,1, 10000).boxed().collect(Collectors.toList());
    private static final List<Long> PRICES = LongStream.iterate(9750, d -> d + 5).limit(100).boxed().collect(Collectors.toList());

    public static Stream<Placement> getDummyPlacementStream(){
        return Stream.generate(() -> {
            Side side = randomEnum(Side.class);
            Limit limit = new Limit(side, PRICES.get(RND.nextInt(PRICES.size())));
            return new Placement(limit,SIZES.get(RND.nextInt(SIZES.size())));
        }).limit(100);
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> cls){
        int x = RND.nextInt(cls.getEnumConstants().length);
        return cls.getEnumConstants()[x];
    }

}

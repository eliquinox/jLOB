package util;

import delta.Delta;
import delta.Side;
import delta.SimpleDelta;
import sun.plugin.javascript.navig.Array;

import java.util.*;
import java.util.stream.*;

public class DeltaGenerators {

    private static final Random RND = new Random();
    private static final List<Integer> SIZES = IntStream.range(100, 9900).boxed().collect(Collectors.toList());
    private static final List<Double> PRICES = DoubleStream.iterate(975, d -> d + 0.5).limit(100).boxed().collect(Collectors.toList());

    public static Stream<Delta> getDummyDeltaStream(){
        return Stream.generate(() -> {
            return new SimpleDelta(
                    PRICES.get(RND.nextInt(PRICES.size())),
                    SIZES.get(RND.nextInt(SIZES.size())),
                    randomEnum(Side.class)
            );
        });
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> cls){
        int x = RND.nextInt(cls.getEnumConstants().length);
        return cls.getEnumConstants()[x];
    }

    public static void main(String[] args) {
        getDummyDeltaStream().forEach(System.out::println);
    }

}

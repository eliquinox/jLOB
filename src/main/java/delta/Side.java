package delta;


import com.google.common.base.Preconditions;
import com.sun.tools.javac.util.List;

public enum Side {

    BID,OFFER;

    public static Side fromString(String side) {
        side = side.toLowerCase();
        Preconditions.checkArgument(List.of("bid", "offer").contains(side));
        return side.equals("bid") ? Side.BID : Side.OFFER;
    }
}

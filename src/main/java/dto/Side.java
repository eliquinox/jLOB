package dto;


import com.google.common.base.Preconditions;

import java.util.List;

public enum Side {

    BID,OFFER;

    public static Side fromString(String side) {
        String resultSide = side.toLowerCase();
        Preconditions.checkArgument(List.of("bid", "offer").contains(resultSide));
        return resultSide.equals("bid") ? Side.BID : Side.OFFER;
    }
}

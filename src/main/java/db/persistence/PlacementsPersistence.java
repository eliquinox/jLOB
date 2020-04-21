package db.persistence;

import db.jooq.tables.records.PlacementsRecord;
import dto.Placement;

import static db.Database.database;

public class PlacementsPersistence {



    public static void persistPlacement(Placement placement) {
        database.executeInsert(toRecord(placement));
    }

    public static PlacementsRecord toRecord(Placement placement) {
        return new PlacementsRecord(
                placement.getUuid(),
                placement.getTimestamp(),
                placement.getSide(),
                placement.getPrice(),
                placement.getSize()
        );
    }
}

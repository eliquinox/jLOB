package db.persistence;

import db.jooq.tables.records.PlacementsRecord;
import dto.Placement;
import org.jooq.DSLContext;

public class PlacementsPersistence {

    private final DSLContext database;

    public PlacementsPersistence(DSLContext database) {
        this.database = database;
    }

    public void persistPlacement(Placement placement) {
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

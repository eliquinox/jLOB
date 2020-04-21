package db.persistence;

import db.jooq.tables.records.CancellationsRecord;
import dto.Cancellation;
import org.jooq.DSLContext;

public class CancellationsPersistence {

    private final DSLContext database;

    public CancellationsPersistence(DSLContext database) {
        this.database = database;
    }

    public void persistCancellation(Cancellation cancellation) {
        database.executeInsert(toRecord(cancellation));
    }

    public static CancellationsRecord toRecord(Cancellation cancellation) {
        return new CancellationsRecord(
                cancellation.getPlacementUuid(),
                cancellation.getTimestamp(),
                cancellation.getSize()
        );
    }
}

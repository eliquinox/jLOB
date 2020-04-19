package db.persistence;

import db.jooq.tables.records.CancellationsRecord;
import dto.Cancellation;
import static db.Database.database;

public class CancellationsPersistence {


    public static void persistCancellation(Cancellation cancellation) {
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

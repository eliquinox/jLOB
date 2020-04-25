package db.persistence;

import db.jooq.tables.records.MatchesRecord;
import dto.Match;
import org.jooq.DSLContext;

public class MatchesPersistence {

    private final DSLContext database;

    public MatchesPersistence(DSLContext database) {
        this.database = database;
    }

    public void persistMatch(Match match) {
        database.executeInsert(toRecord(match));
    }

    public static MatchesRecord toRecord(Match match) {
        return new MatchesRecord(
                match.getMakerPlacementUuid(),
                match.getTakerPlacementUuid(),
                match.getTimestamp(),
                match.getSize()
        );
    }
}

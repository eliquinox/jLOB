package db.persistence;

import db.jooq.tables.records.MatchesRecord;
import static db.Database.database;
import dto.Match;

public class MatchesPersistence {

    public static void persistMatch(Match match) {
        database.executeInsert(toRecord(match));
    }

    public static MatchesRecord toRecord(Match match) {
        return new MatchesRecord(
                match.getMakerPlacementId(),
                match.getTakerPlacementId(),
                match.getTimestamp(),
                match.getSize()
        );
    }
}

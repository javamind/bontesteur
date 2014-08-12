package com.ninjamind.conference.repository;

import com.ninja_squad.dbsetup.operation.Operation;
import org.joda.time.DateTime;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

/**
 * Classe contenant les principales requêtes de mise a jour des données
 *
 * @author EHRET_G
 */
public class InitializeOperations {
    public static final Operation DELETE_ALL = deleteAllFrom("conference_talk", "speaker_talk" , "talk", "speaker", "conference", "country");

    public static final Operation INSERT_COUNTRY_DATA =
            insertInto("country")
                    .columns("id", "code", "name")
                    .values(1, "FRA", "France")
                    .values(2, "USA", "United States")
                    .build();

    public static final Operation INSERT_CONFERENCE_DATA =
            insertInto("conference")
                    .columns("id", "name", "country_id", "dateStart", "dateEnd")
                    .values(1, "Mix-IT", 1, new DateTime(2014,3,29,9,0).toDate(), new DateTime(2014,3,30,18,0).toDate())
                    .values(2, "DevoxxFr", 1, new DateTime(2014,3,16,9,0).toDate(), new DateTime(2014,3,18,18,0).toDate())
                    .build();

    public static final Operation INSERT_SPEAKER_DATA =
            insertInto("speaker")
                    .columns("id", "firstname", "lastname")
                    .values(1, "Agnes", "Crepet")
                    .values(2, "Guillaume", "Ehret")
                    .build();

    public static final Operation INSERT_TALK_DATA =
            insertInto("talk")
                    .columns("id", "name", "dateStart", "dateEnd")
                    .values(1, "Le bon testeur il teste... le mauvais testeur il teste...", new DateTime(2014,4,18,13,30).toDate(), new DateTime(2014,4,18,14,20).toDate())
                    .build();

    public static final Operation INSERT_SPEAKER_TALK_DATA =
            insertInto("speaker_talk")
                    .columns("speaker_id", "talk_id")
                    .values(1, 1)
                    .values(2, 1)
                    .build();

    public static final Operation INSERT_CONFERENCE_TALK_DATA =
            insertInto("conference_talk")
                    .columns("conference_id", "talk_id")
                    .values(1, 1)
                    .build();
}

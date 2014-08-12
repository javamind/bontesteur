INSERT INTO country (id, code, name) values(1, 'FRA', 'France');
INSERT INTO country (id, code, name) values(2, 'USA', 'United States');
                        
INSERT INTO conference (id, name, country_id, dateStart, dateEnd) values(1, 'Mix-IT', 1, TIMESTAMP '2014-03-29 09:00:00', TIMESTAMP '2014-03-30 18:00:00');
INSERT INTO conference (id, name, country_id, dateStart, dateEnd) values(2, 'DevoxxFr', 1, TIMESTAMP '2014-03-16 09:00:00', TIMESTAMP '2014-03-18 18:00:00');

INSERT INTO speaker (id, firstname, lastname) values(1, 'Agnes', 'Crepet');
INSERT INTO speaker (id, firstname, lastname) values(2, 'Guillaume', 'Ehret');

INSERT INTO talk (id, name, dateStart, dateEnd) values(1, 'Le bon testeur il teste... le mauvais testeur il teste...', TIMESTAMP '2014-04-18 13:30:00', TIMESTAMP '2014-04-18 14:20:00');

INSERT INTO speaker_talk (speaker_id, talk_id) values(1, 1);
INSERT INTO speaker_talk (speaker_id, talk_id) values(2, 1);

INSERT INTO conference_talk (conference_id, talk_id) values(1, 1);

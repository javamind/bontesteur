CREATE TABLE conference_talk
(
  id integer,
  conference_id integer,
  talk_id integer,
  CONSTRAINT conference_talk_key UNIQUE (conference_id, talk_id)
)
WITH (
  OIDS=FALSE
);


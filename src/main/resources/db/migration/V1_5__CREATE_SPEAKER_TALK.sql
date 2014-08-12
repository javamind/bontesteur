CREATE TABLE speaker_talk
(
  id serial,
  speaker_id integer,
  talk_id integer,
  CONSTRAINT speaker_talk_key UNIQUE (speaker_id, talk_id)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE speaker
(
  id integer NOT NULL,
  firstname character varying(40),
  lastname character varying(40),
  company character varying(50),
  streetadress character varying(50),
  postalcode character varying(40),
  city character varying(40),
  image character varying(100),
  country_id integer,
  version integer,
  majuser character varying(40),
  majdate date,
  CONSTRAINT speaker_name_key UNIQUE (firstname,lastname)
)
WITH (
  OIDS=FALSE
);

CREATE SEQUENCE seq_speaker
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE TABLE conference
(
  id integer NOT NULL,
  name character varying(40),
  city character varying(40),
  streetadress character varying(50),
  postalcode character varying(40),
  country_id integer,
  datestart date,
  dateend date,
  version integer,
  majuser character varying(40),
  image character varying(40),
  majdate date,
  nbHoursToSellTicket integer,
  nbAttendees integer,
  nbConferenceSlots integer,
  nbConferenceProposals integer,
  nbTwitterFollowers integer,
  CONSTRAINT conference_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);

CREATE SEQUENCE seq_conference
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
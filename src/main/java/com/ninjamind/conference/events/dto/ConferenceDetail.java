package com.ninjamind.conference.events.dto;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.utils.Utils;

import java.io.Serializable;

/**
 * Objet de transit lie aux evenements sur les conferences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ConferenceDetail implements Serializable {
    protected Long id;
    protected String name;
    protected String streetAdress;
    protected String city;
    protected String postalCode;
    protected String codeCountry;
    protected String dateStart;
    protected String dateEnd;
    private Long nbHourToSellTicket;
    private Long nbAttendees;
    private Long nbConferenceSlot;
    private Long nbConferenceProposals;
    private Long nbTwitterFollowers;


    public ConferenceDetail() {
    }

    /**
     * @param id
     * @param name
     * @param start
     * @param end
     */
    public ConferenceDetail(Long id, String name, String start, String end) {
        this.id = id;
        this.name = name;
        this.dateStart = start;
        this.dateEnd = end;
    }

    /**
     * @param id
     * @param name
     * @param streetAdress
     * @param city
     * @param postalCode
     * @param codeCountry
     * @param dateStart
     * @param dateEnd
     */
    public ConferenceDetail(Long id, String name, String streetAdress, String city, String postalCode, String codeCountry, String dateStart, String dateEnd) {
        this(id, name, dateStart, dateEnd);
        this.streetAdress = streetAdress;
        this.city = city;
        this.postalCode = postalCode;
        this.codeCountry = codeCountry;
    }


    public ConferenceDetail(Conference conference) {
        this(
                conference.getId(),
                conference.getName(),
                conference.getStreetAdress(),
                conference.getCity(),
                conference.getPostalCode(),
                conference.getCountry() != null ? conference.getCountry().getCode() : null,
                Utils.dateJavaToJson(conference.getDateStart()),
                Utils.dateJavaToJson(conference.getDateEnd())
        );
    }

    /**
     * Init des stats d'une conference
     * @param nbHourToSellTicket
     * @param nbAttendees
     * @param nbConferenceSlot
     * @param nbConferenceProposals
     * @param nbTwitterFollowers
     */
    public void initConferenceStat(Long nbHourToSellTicket, Long nbAttendees, Long nbConferenceSlot, Long nbConferenceProposals, Long nbTwitterFollowers) {
        this.nbHourToSellTicket = nbHourToSellTicket;
        this.nbAttendees = nbAttendees;
        this.nbConferenceSlot = nbConferenceSlot;
        this.nbConferenceProposals = nbConferenceProposals;
        this.nbTwitterFollowers = nbTwitterFollowers;
    }

    /**
     * Conversion d'une conferenceDetail en conference
     * @return
     */
    public Conference toConference() {
        Conference conference = new Conference(
                getName(),
                Utils.dateJsonToJava(getDateStart()),
                Utils.dateJsonToJava(getDateEnd()));
        conference.setId(id);
        conference.setStreetAdress(streetAdress);
        conference.setCity(city);
        conference.setPostalCode(postalCode);
        conference.setCountry(new Country(codeCountry, null));
        conference.setNbTwitterFollowers(nbTwitterFollowers);
        conference.setNbAttendees(nbAttendees);
        conference.setNbHoursToSellTicket(nbHourToSellTicket);
        conference.setNbConferenceSlots(nbConferenceSlot);
        conference.setNbConferenceProposals(nbConferenceProposals);
        return conference;
    }

    public String getName() {
        return name;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCodeCountry() {
        return codeCountry;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public Long getNbHourToSellTicket() {
        return nbHourToSellTicket;
    }

    public Long getNbAttendees() {
        return nbAttendees;
    }

    public Long getNbConferenceSlot() {
        return nbConferenceSlot;
    }

    public Long getNbConferenceProposals() {
        return nbConferenceProposals;
    }

    public Long getNbTwitterFollowers() {
        return nbTwitterFollowers;
    }

    public Long getId() {
        return id;
    }
}

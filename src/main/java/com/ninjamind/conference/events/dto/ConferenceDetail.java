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
    protected String image;
    private Long nbHourToSellTicket;
    private Long nbAttendees;
    private Long nbConferenceSlot;
    private Long nbConferenceProposals;
    private Long nbTwitterFollowers;


    public ConferenceDetail() {
    }


    public ConferenceDetail(Conference conference) {
        this.id = conference.getId();
        this.name = conference.getName();
        this.dateStart = Utils.dateJavaToJson(conference.getDateStart());
        this.dateEnd = Utils.dateJavaToJson(conference.getDateEnd());
        this.streetAdress = conference.getStreetAdress();
        this.city = conference.getCity();
        this.postalCode = conference.getPostalCode();
        this.codeCountry = conference.getCountry() != null ? conference.getCountry().getCode() : null;
        this.image = conference.getImage();
        this.nbTwitterFollowers = conference.getNbTwitterFollowers();
        this.nbAttendees = conference.getNbAttendees();
        this.nbConferenceProposals = conference.getNbConferenceProposals();
        this.nbConferenceSlot = conference.getNbConferenceSlots();
    }

    /**
     * Conversion d'une conferenceDetail en conference
     * @return
     */
    public Conference toConference() {
        return new Conference()
                .setName(getName())
                .setDateStart(Utils.dateJsonToJava(getDateStart()))
                .setDateEnd(Utils.dateJsonToJava(getDateEnd()))
                .setId(id)
                .setImage(image)
                .setStreetAdress(streetAdress)
                .setCity(city)
                .setPostalCode(postalCode)
                .setCountry(new Country().setCode(codeCountry))
                .setNbTwitterFollowers(nbTwitterFollowers)
                .setNbAttendees(nbAttendees)
                .setNbHoursToSellTicket(nbHourToSellTicket)
                .setNbConferenceSlots(nbConferenceSlot)
                .setNbConferenceProposals(nbConferenceProposals);

    }

    public Long getId() {
        return id;
    }

    public ConferenceDetail setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ConferenceDetail setName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ConferenceDetail setImage(String image) {
        this.image = image;
        return this;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public ConferenceDetail setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ConferenceDetail setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public ConferenceDetail setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCodeCountry() {
        return codeCountry;
    }

    public ConferenceDetail setCodeCountry(String codeCountry) {
        this.codeCountry = codeCountry;
        return this;
    }

    public String getDateStart() {
        return dateStart;
    }

    public ConferenceDetail setDateStart(String dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public ConferenceDetail setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public Long getNbHourToSellTicket() {
        return nbHourToSellTicket;
    }

    public ConferenceDetail setNbHourToSellTicket(Long nbHourToSellTicket) {
        this.nbHourToSellTicket = nbHourToSellTicket;
        return this;
    }

    public Long getNbAttendees() {
        return nbAttendees;
    }

    public ConferenceDetail setNbAttendees(Long nbAttendees) {
        this.nbAttendees = nbAttendees;
        return this;
    }

    public Long getNbConferenceSlot() {
        return nbConferenceSlot;
    }

    public ConferenceDetail setNbConferenceSlot(Long nbConferenceSlot) {
        this.nbConferenceSlot = nbConferenceSlot;
        return this;
    }

    public Long getNbConferenceProposals() {
        return nbConferenceProposals;
    }

    public ConferenceDetail setNbConferenceProposals(Long nbConferenceProposals) {
        this.nbConferenceProposals = nbConferenceProposals;
        return this;
    }

    public Long getNbTwitterFollowers() {
        return nbTwitterFollowers;
    }

    public ConferenceDetail setNbTwitterFollowers(Long nbTwitterFollowers) {
        this.nbTwitterFollowers = nbTwitterFollowers;
        return this;
    }
}

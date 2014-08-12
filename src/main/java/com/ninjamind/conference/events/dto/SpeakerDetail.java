package com.ninjamind.conference.events.dto;

import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.domain.Speaker;

import java.io.Serializable;

/**
 * Objet de transit lie
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Speaker
 */
public class SpeakerDetail implements Serializable {
    protected Long id;
    protected String firstname;
    protected String lastname;
    protected String streetAdress;
    protected String city;
    protected String postalCode;
    protected String company;
    protected String codeCountry;

    /**
     *
     */
    public SpeakerDetail() {
    }

    /**
     * @param id
     * @param firstname
     * @param lastname
     */
    public SpeakerDetail(Long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     *
     * @param id
     * @param firstname
     * @param lastname
     * @param streetAdress
     * @param city
     * @param postalCode
     * @param company
     * @param codeCountry
     */
    public SpeakerDetail(Long id, String firstname, String lastname, String streetAdress, String city, String postalCode, String company, String codeCountry) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.streetAdress = streetAdress;
        this.city = city;
        this.postalCode = postalCode;
        this.company = company;
        this.codeCountry = codeCountry;
    }

    public SpeakerDetail(Speaker speaker) {
        this(
                speaker.getId(),
                speaker.getFirstname(),
                speaker.getLastname(),
                speaker.getStreetAdress(),
                speaker.getCity(),
                speaker.getPostalCode(),
                speaker.getCompany(),
                speaker.getCountry() != null ? speaker.getCountry().getCode() : null
        );
    }

    public Speaker toSpeaker() {
        Speaker speaker = new Speaker(
                getFirstname(),
                getLastname());
        speaker.setId(id);
        speaker.setStreetAdress(streetAdress);
        speaker.setCity(city);
        speaker.setPostalCode(postalCode);
        speaker.setCompany(company);
        speaker.setCountry(new Country(codeCountry, null));
        return speaker;
    }


    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    public String getCompany() {
        return company;
    }

    public String getCodeCountry() {
        return codeCountry;
    }
}

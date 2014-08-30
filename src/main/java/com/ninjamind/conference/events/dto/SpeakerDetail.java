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



    public SpeakerDetail(Speaker speaker) {
        this.id = speaker.getId();
        this.firstname = speaker.getFirstname();
        this.lastname = speaker.getLastname();
        this.streetAdress = speaker.getStreetAdress();
        this.city = speaker.getCity();
        this.postalCode = speaker.getPostalCode();
        this.company = speaker.getCompany();
        this.codeCountry = speaker.getCountry() != null ? speaker.getCountry().getCode() : null;
    }

    public Speaker toSpeaker() {
        return new Speaker()
                .setFirstname(getFirstname())
                .setLastname(getLastname())
                .setId(id)
                .setStreetAdress(streetAdress)
                .setCity(city)
                .setPostalCode(postalCode)
                .setCompany(company)
                .setCountry(new Country().setCode(codeCountry));
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

    public SpeakerDetail setId(Long id) {
        this.id = id;
        return this;
    }

    public SpeakerDetail setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public SpeakerDetail setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public SpeakerDetail setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
        return this;
    }

    public SpeakerDetail setCity(String city) {
        this.city = city;
        return this;
    }

    public SpeakerDetail setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public SpeakerDetail setCompany(String company) {
        this.company = company;
        return this;
    }

    public SpeakerDetail setCodeCountry(String codeCountry) {
        this.codeCountry = codeCountry;
        return this;
    }
}

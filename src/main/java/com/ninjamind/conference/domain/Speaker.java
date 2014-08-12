package com.ninjamind.conference.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * @author ehret_g
 */
@Entity
@Table(name = "speaker")
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_speaker")
    @SequenceGenerator(name="seq_speaker", sequenceName="seq_speaker", allocationSize=1)
    private Long id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    private String company;
    private String streetAdress;
    private String city;
    private String postalCode;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @Version
    private Long version;
    @ManyToMany
    @JoinTable(
            name="speaker_talk",
            joinColumns={@JoinColumn(name="speaker_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="talk_id", referencedColumnName="id")})
    Set<Talk> talks;

    public Speaker() {
    }

    public Speaker(Long id) {
        this.id=id;
    }

    public Speaker(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public void setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public void setTalks(Set<Talk> talks) {
        this.talks = talks;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Speaker speaker = (Speaker) o;

        if (city != null ? !city.equals(speaker.city) : speaker.city != null) return false;
        if (company != null ? !company.equals(speaker.company) : speaker.company != null) return false;
        if (country != null ? !country.equals(speaker.country) : speaker.country != null) return false;
        if (!firstname.equals(speaker.firstname)) return false;
        if (!lastname.equals(speaker.lastname)) return false;
        if (postalCode != null ? !postalCode.equals(speaker.postalCode) : speaker.postalCode != null) return false;
        if (streetAdress != null ? !streetAdress.equals(speaker.streetAdress) : speaker.streetAdress != null)
            return false;
        if (talks != null ? !talks.equals(speaker.talks) : speaker.talks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (streetAdress != null ? streetAdress.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (talks != null ? talks.hashCode() : 0);
        return result;
    }
}

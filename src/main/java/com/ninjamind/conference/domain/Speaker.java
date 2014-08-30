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
    protected String image;
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

    public Long getId() {
        return id;
    }

    public Speaker setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public Speaker setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Speaker setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public Speaker setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public Speaker setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Speaker setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Speaker setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Speaker setCountry(Country country) {
        this.country = country;
        return this;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public Speaker setTalks(Set<Talk> talks) {
        this.talks = talks;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Speaker setVersion(Long version) {
        this.version = version;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Speaker setImage(String image) {
        this.image = image;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Speaker speaker = (Speaker) o;

        if (!firstname.equals(speaker.firstname)){
            return false;
        }
        if (!lastname.equals(speaker.lastname)){
            return false;
        }
        if (postalCode != null ? !postalCode.equals(speaker.postalCode) : speaker.postalCode != null){
            return false;
        }

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

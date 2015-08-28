package com.ninjamind.conference.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author ehret_g
 */
@Entity
@Table(name = "conference")
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conference")
    @SequenceGenerator(name = "seq_conference", sequenceName = "seq_conference", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String streetAdress;
    private String city;
    private String image;
    private String postalCode;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateStart;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateEnd;
    @Version
    private Long version;
    private Long nbHoursToSellTicket;
    private Long nbAttendees;
    private Long nbConferenceSlots;
    private Long nbConferenceProposals;
    private Long nbTwitterFollowers;

    @ManyToMany
    @JoinTable(
            name = "conference_talk",
            joinColumns = {@JoinColumn(name = "conference_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "talk_id", referencedColumnName = "id")})
    Set<Talk> talks;

    public Conference() {
    }

    public Long getId() {
        return id;
    }

    public Conference setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Conference setName(String name) {
        this.name = name;
        return this;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public Conference setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Conference setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Conference setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Conference setCountry(Country country) {
        this.country = country;
        return this;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Conference setDateStart(Date dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public Conference setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public Conference setTalks(Set<Talk> talks) {
        this.talks = talks;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Conference setVersion(Long version) {
        this.version = version;
        return this;
    }

    public Long getNbHoursToSellTicket() {
        return nbHoursToSellTicket;
    }

    public Conference setNbHoursToSellTicket(Long nbHoursToSellTicket) {
        this.nbHoursToSellTicket = nbHoursToSellTicket;
        return this;
    }

    public Long getNbAttendees() {
        return nbAttendees;
    }

    public Conference setNbAttendees(Long nbAttendees) {
        this.nbAttendees = nbAttendees;
        return this;
    }

    public Long getNbConferenceSlots() {
        return nbConferenceSlots;
    }

    public Conference setNbConferenceSlots(Long nbConferenceSlots) {
        this.nbConferenceSlots = nbConferenceSlots;
        return this;
    }

    public Long getNbConferenceProposals() {
        return nbConferenceProposals;
    }

    public Conference setNbConferenceProposals(Long nbConferenceProposals) {
        this.nbConferenceProposals = nbConferenceProposals;
        return this;
    }

    public Long getNbTwitterFollowers() {
        return nbTwitterFollowers;
    }

    public Conference setNbTwitterFollowers(Long nbTwitterFollowers) {
        this.nbTwitterFollowers = nbTwitterFollowers;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Conference setImage(String image) {
        this.image = image;
        return  this;
    }

    public Double getProposalsRatio() {
        if (getNbConferenceSlots() == null || getNbConferenceProposals() == null) {
            return null;
        }
        return (double) getNbConferenceSlots() / getNbConferenceProposals();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Conference that = (Conference) o;

        if (name != null ? !name.equals(that.name) : that.name != null){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}

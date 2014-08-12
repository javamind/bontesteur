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

    public Conference(Long id) {
        this.id = id;
    }

    public Conference(String name) {
        this.name = name;
    }

    public Conference(String name, Date start, Date end) {
        this.name = name;
        this.dateStart = start;
        this.dateEnd = end;
    }

    public Conference(String name, Long nbConferenceSlots, Long nbConferenceProposals) {
        this.name = name;
        this.nbConferenceSlots = nbConferenceSlots;
        this.nbConferenceProposals = nbConferenceProposals;
    }
    public Conference(String name, Long nbConferenceSlots, Long nbConferenceProposals, Long nbTwitterFollowers) {
        this.name = name;
        this.nbConferenceSlots = nbConferenceSlots;
        this.nbConferenceProposals = nbConferenceProposals;
        this.nbTwitterFollowers = nbTwitterFollowers;
    }

    /**
     * Init des stats d'une conference
     *
     * @param nbHourToSellTicket
     * @param nbAttendees
     * @param nbConferenceSlot
     * @param nbConferenceProposals
     * @param nbTwitterFollowers
     */
    public void initConferenceStat(Long nbHourToSellTicket, Long nbAttendees, Long nbConferenceSlot, Long nbConferenceProposals, Long nbTwitterFollowers) {
        this.nbHoursToSellTicket = nbHourToSellTicket;
        this.nbAttendees = nbAttendees;
        this.nbConferenceSlots = nbConferenceSlot;
        this.nbConferenceProposals = nbConferenceProposals;
        this.nbTwitterFollowers = nbTwitterFollowers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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

    public Long getNbHoursToSellTicket() {
        return nbHoursToSellTicket;
    }

    public void setNbHoursToSellTicket(Long nbHoursToSellTicket) {
        this.nbHoursToSellTicket = nbHoursToSellTicket;
    }

    public Long getNbAttendees() {
        return nbAttendees;
    }

    public void setNbAttendees(Long nbAttendees) {
        this.nbAttendees = nbAttendees;
    }

    public Long getNbConferenceSlots() {
        return nbConferenceSlots;
    }

    public void setNbConferenceSlots(Long nbConferenceSlots) {
        this.nbConferenceSlots = nbConferenceSlots;
    }

    public Long getNbConferenceProposals() {
        return nbConferenceProposals;
    }

    public void setNbConferenceProposals(Long nbConferenceProposals) {
        this.nbConferenceProposals = nbConferenceProposals;
    }

    public Long getNbTwitterFollowers() {
        return nbTwitterFollowers;
    }

    public void setNbTwitterFollowers(Long nbTwitterFollowers) {
        this.nbTwitterFollowers = nbTwitterFollowers;
    }

    public Double getProposalsRatio() {
        if (getNbConferenceSlots() == null || getNbConferenceProposals() == null) {
            return null;
        }
        return (double) getNbConferenceSlots() / getNbConferenceProposals();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conference that = (Conference) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

package com.ninjamind.conference.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author ehret_g
 */
@Entity
@Table(name = "talk")
@NamedQueries(value = {
        @NamedQuery(name = "findTalkToArchive", query = "SELECT t FROM Talk t WHERE year(t.dateStart) < :year"),
        @NamedQuery(name = "archiveTalks", query = "UPDATE Talk t SET t.status='ARCHIVED' WHERE year(t.dateStart) < :year")
})
public class Talk {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_talk")
    @SequenceGenerator(name="seq_talk", sequenceName="seq_talk", allocationSize=1)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String place;
    private Integer nbpeoplemax;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;
    @ManyToMany(mappedBy="talks")
    Set<Speaker> speakers;
    @ManyToMany(mappedBy="talks")
    Set<Conference> conferences;

    @Version
    private Long version;

    public Talk(Long id) {
        this.id=id;
    }

    public Talk() {
    }

    public Long getId() {
        return id;
    }

    public Talk setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Talk setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Talk setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public Talk setPlace(String place) {
        this.place = place;
        return this;
    }

    public Integer getNbpeoplemax() {
        return nbpeoplemax;
    }

    public Talk setNbpeoplemax(Integer nbpeoplemax) {
        this.nbpeoplemax = nbpeoplemax;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    public Talk setLevel(Level level) {
        this.level = level;
        return this;
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    public Talk setSpeakers(Set<Speaker> speakers) {
        this.speakers = speakers;
        return this;
    }

    public Set<Conference> getConferences() {
        return conferences;
    }

    public Talk setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Talk setVersion(Long version) {
        this.version = version;
        return this;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Talk setDateStart(Date dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public Talk setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Talk setStatus(Status status) {
        this.status = status;
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

        Talk talk = (Talk) o;

        if (!name.equals(talk.name)){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (nbpeoplemax != null ? nbpeoplemax.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (speakers != null ? speakers.hashCode() : 0);
        return result;
    }
}

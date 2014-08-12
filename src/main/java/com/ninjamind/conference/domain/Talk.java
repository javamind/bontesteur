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
        @NamedQuery(name = "archiveTalks", query = "UPDATE Talk t SET t.status='Archived' WHERE year(t.dateStart) < :year")
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

    public Talk(String name) {
        this.name = name;
    }

    public Talk(Long id, String name) {
        this(id);
        this.name = name;
    }

    public Talk(Long id, String name, Date dateStart, Date dateEnd) {
        this(id);
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getNbpeoplemax() {
        return nbpeoplemax;
    }

    public void setNbpeoplemax(Integer nbpeoplemax) {
        this.nbpeoplemax = nbpeoplemax;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Set<Speaker> speakers) {
        this.speakers = speakers;
    }

    public Set<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Talk talk = (Talk) o;

        if (description != null ? !description.equals(talk.description) : talk.description != null) return false;
        if (level != talk.level) return false;
        if (!name.equals(talk.name)) return false;
        if (nbpeoplemax != null ? !nbpeoplemax.equals(talk.nbpeoplemax) : talk.nbpeoplemax != null) return false;
        if (place != null ? !place.equals(talk.place) : talk.place != null) return false;
        if (speakers != null ? !speakers.equals(talk.speakers) : talk.speakers != null) return false;

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

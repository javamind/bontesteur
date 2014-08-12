package com.ninjamind.conference.events.dto;

import com.ninjamind.conference.domain.Level;
import com.ninjamind.conference.domain.Status;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.utils.Utils;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Objet de transit lie
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class TalkDetail implements Serializable {
    protected Long id;
    protected String name;
    protected String description;
    protected String place;
    protected Integer nbpeoplemax;
    protected String level;
    protected String dateStart;
    protected String dateEnd;
    protected String status;

    /**
     *
     */
    public TalkDetail() {
    }

    /**
     * @param id
     * @param name
     */
    public TalkDetail(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TalkDetail(Long id, String name, String description, String place, Integer nbpeoplemax, String level) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.nbpeoplemax = nbpeoplemax;
        this.level = level;

    }

    public TalkDetail(Talk talk) {
        this(
                talk.getId(),
                talk.getName(),
                talk.getDescription(),
                talk.getPlace(),
                talk.getNbpeoplemax(),
                talk.getLevel() != null ? talk.getLevel().toString() : null
        );
        this.setDateStart(Utils.dateJavaToJson(talk.getDateStart()));
        this.setDateEnd(Utils.dateJavaToJson(talk.getDateEnd()));
        this.setStatus(talk.getStatus()!=null ? talk.getStatus().toString() : null);
    }

    public Talk toTalk() {
        Talk talk = new Talk(name);
        talk.setId(id);
        talk.setDescription(description);
        talk.setPlace(place);
        talk.setNbpeoplemax(nbpeoplemax);
        talk.setLevel(level!=null ? Level.valueOf(level) : null);
        talk.setDateStart(Utils.dateJsonToJava(getDateStart()));
        talk.setDateEnd(Utils.dateJsonToJava(getDateEnd()));
        talk.setStatus(status!=null ? Status.valueOf(status) : null);
        return talk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public Integer getNbpeoplemax() {
        return nbpeoplemax;
    }

    public String getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setNbpeoplemax(Integer nbpeoplemax) {
        this.nbpeoplemax = nbpeoplemax;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}

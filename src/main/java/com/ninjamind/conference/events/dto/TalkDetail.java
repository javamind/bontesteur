package com.ninjamind.conference.events.dto;

import com.ninjamind.conference.domain.Level;
import com.ninjamind.conference.domain.Status;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.utils.Utils;

import java.io.Serializable;

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


    public TalkDetail(Talk talk) {
        this.id = talk.getId();
        this.name = talk.getName();
        this.description = talk.getDescription();
        this.place = talk.getPlace();
        this.nbpeoplemax = talk.getNbpeoplemax();
        this.level = talk.getLevel() != null ? talk.getLevel().toString() : null;
        this.setDateStart(Utils.dateJavaToJson(talk.getDateStart()));
        this.setDateEnd(Utils.dateJavaToJson(talk.getDateEnd()));
        this.setStatus(talk.getStatus()!=null ? talk.getStatus().toString() : null);
    }

    public Talk toTalk() {
        return new Talk()
                .setName(name)
                .setId(id)
                .setDescription(description)
                .setPlace(place)
                .setNbpeoplemax(nbpeoplemax)
                .setLevel(level!=null ? Level.valueOf(level) : null)
                .setDateStart(Utils.dateJsonToJava(getDateStart()))
                .setDateEnd(Utils.dateJsonToJava(getDateEnd()))
                .setStatus(status!=null ? Status.valueOf(status) : null);
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

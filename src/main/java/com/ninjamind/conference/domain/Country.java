package com.ninjamind.conference.domain;

import javax.persistence.*;

/**
 * @author ehret_g
 */
@Entity
@Table(name = "country")
public class Country {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_country")
    @SequenceGenerator(name="seq_country", sequenceName="seq_country", allocationSize=1)
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Version
    private Long version;

    public Country() {
    }

    public Long getId() {
        return id;
    }

    public Country setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Country setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Country setVersion(Long version) {
        this.version = version;
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

        Country country = (Country) o;

        if (code != null ? !code.equals(country.code) : country.code != null){
            return false;
        }
        if (name != null ? !name.equals(country.name) : country.name != null){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

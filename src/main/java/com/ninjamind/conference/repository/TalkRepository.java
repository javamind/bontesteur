package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository associé au {@link com.ninjamind.conference.domain.Talk}
 *
 * @author ehret_g
 */
public interface TalkRepository extends JpaRepository<Talk, Long> {

}

package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository associé au {@link com.ninjamind.conference.domain.Talk}
 *
 * @author ehret_g
 */
public interface TalkRepository extends JpaRepository<Talk, Long> {

}

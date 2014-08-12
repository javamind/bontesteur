package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository associé au {@link com.ninjamind.conference.domain.Conference}
 *
 * @author ehret_g
 */
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

}

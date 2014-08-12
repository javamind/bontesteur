package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository associé au {@link com.ninjamind.conference.domain.Speaker}
 *
 * @author ehret_g
 */
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}

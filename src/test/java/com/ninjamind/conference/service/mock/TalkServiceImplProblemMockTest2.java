package com.ninjamind.conference.service.mock;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.service.talk.TalkService;
import com.ninjamind.conference.service.talk.TalkServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}  ou on veut tester le save
 * @author EHRET_G
 */
public class TalkServiceImplProblemMockTest2 {

    private TalkRepository talkRepository;

    private TalkService service;

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void shouldCreateTalk(){

    }

}
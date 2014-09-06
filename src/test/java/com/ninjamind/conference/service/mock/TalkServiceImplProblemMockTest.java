package com.ninjamind.conference.service.mock;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.service.talk.TalkService;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}  ou on veut tester le save
 * @author EHRET_G
 */
public class TalkServiceImplProblemMockTest {

    private TalkRepository talkRepository;

    private TalkService service;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldCreateTalk(){
       // service.createTalk(new Talk().setName("mon talk"));
    }


}
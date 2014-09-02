package com.ninjamind.conference.service.mock.sav;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.service.talk.TalkService;
import com.ninjamind.conference.service.talk.TalkServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}  ou on veut tester le save
 * @author EHRET_G
 */
public class TalkServiceImplProblemMockTest2 {

    private TalkRepository talkRepository;

    private TalkService service;

    @Before
    public void setUp() throws Exception {
        talkRepository = mock(TalkRepository.class);
        service = new TalkServiceImpl();
        service.setTalkRepository(talkRepository);
    }

    @Test
    public void shouldCreateTalk(){
        service.createTalk(new Talk().setName("mon talk"));
    }


}
package com.ninjamind.conference.service.mock;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.service.talk.TalkService;
import com.ninjamind.conference.service.talk.TalkServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}  ou on veut tester le save
 * @author EHRET_G
 */
@RunWith(MockitoJUnitRunner.class)
public class TalkServiceImplProblemMockTest {

    private TalkRepository talkRepository;

    private TalkService service  = new TalkServiceImpl();;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldCreateTalk(){
        //  service.createTalk(new Talk().setName("mon talk"));
    }


}
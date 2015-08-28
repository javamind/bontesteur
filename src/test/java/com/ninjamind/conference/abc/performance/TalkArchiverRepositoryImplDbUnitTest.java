package com.ninjamind.conference.abc.performance;

import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de a classe {@link com.ninjamind.conference.repository.TalkArchiverRepository}
 * en utilisant testNg et DbUnit. Le but est de voir le temps mis par les tests quand
 * on accede en boucle e la base de donnees
 *
 * @see com.ninjamind.conference.abc.performance.TalkArchiverRepositoryDbSetupTest
 * @author EHRET_G
 */
@ContextConfiguration(classes = {ApplicationConfig.class})
@Test(groups = {"perf"})
public class TalkArchiverRepositoryImplDbUnitTest extends AbstractDbunitTestNgRepositoryTest {


    @Autowired
    private TalkArchiverRepository talkArchiverRepository;



    protected IDataSet readDataSet() {
        try {
            return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/talk_init.xml"));
        } catch (MalformedURLException | DataSetException e) {
            throw new RuntimeException(e);
        }
    }



    @Test(invocationCount = 1000)
    public void shouldFindOneConfToArchiveWhenYearIs2014() {
        List<Talk> talks = talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isEqualToComparingOnlyGivenFields(
                new Talk().setId(2L).setName("La conf passee"), "id", "name");

    }
}

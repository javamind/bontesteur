package com.ninjamind.conference.repository.performance;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.domain.Status;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de a classe {@link com.ninjamind.conference.repository.TalkArchiverRepository}
 * en utilisant testNg et DbSetup. Le but est de voir que Dbsetup est beaucoup plus rapide
 *
 * @see com.ninjamind.conference.repository.performance.TalkArchiverRepositoryImplDbUnitTest
 * @author EHRET_G
 */
@ContextConfiguration(classes = {ApplicationConfig.class})
@Test(groups = {"perf"})
public class TalkArchiverRepositoryDbSetupTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    protected DataSource dataSource;

    @Autowired
    private TalkArchiverRepository talkArchiverRepository;


    @BeforeMethod
    public void prepare(){
        Operation init_data = sequenceOf(
                deleteAllFrom("talk"),
                insertInto("talk")
                        .columns("id", "name", "status", "dateStart", "dateEnd")
                        .values(1L, "Le bon testeur il teste", Status.ACTIVE, new DateTime(2014, 4, 18, 13, 30).toDate(), new DateTime(2014, 4, 18, 14, 20).toDate())
                        .values(2L, "La conf passee", Status.ACTIVE, new DateTime(2010, 4, 18, 13, 30).toDate(), new DateTime(2010, 4, 18, 14, 20).toDate())
                        .build()
        );
        DbSetup dbSetup = new DbSetup(DataSourceDestination.with(dataSource), init_data);
        dbSetup.launch();
    }




    @Test(invocationCount = 1000)
    public void shouldFindOneConfToArchiveWhenYearIs2014() {
        List<Talk> talks = talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isEqualToComparingOnlyGivenFields(
                new Talk().setId(2L).setName("La conf passee"), "id", "name");

    }

}

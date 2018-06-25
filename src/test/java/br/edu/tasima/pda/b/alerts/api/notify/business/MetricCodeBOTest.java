package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.repository.MetricCodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MetricCodeBOTest {

    @Autowired
    private MetricCodeBO metricCodeBO;

    @MockBean
    private MetricCodeRepository metricCodeRepository;

    @Test
    public void saveSuccessTest(){
        MetricCode metricCode = new MetricCode("memory", "80");
        given(metricCodeRepository.save(any(MetricCode.class))).willReturn(metricCode);

        MetricCode saved = metricCodeBO.save(metricCode);
        assertNotNull(saved);
        assertEquals("memory", saved.getCode());
        assertEquals("80", saved.getValue());
    }

    @Test(expected = RuntimeException.class)
    public void saveFailTest(){
        given(metricCodeRepository.save(any(MetricCode.class))).willThrow(RuntimeException.class);
        MetricCode saved = metricCodeBO.save(new MetricCode());
    }
}

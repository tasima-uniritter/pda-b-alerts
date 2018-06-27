package br.edu.tasima.pda.b.alerts.api.notify.business;

import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import br.edu.tasima.pda.b.alerts.api.notify.repository.MetricCodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

    @Test
    public void getByCodeFoundTest(){
        MetricCode metricCode = new MetricCode("memory", "80");
        given(metricCodeRepository.getOne("memory")).willReturn(metricCode);

        MetricCode found = metricCodeBO.getByCode("memory");
        assertNotNull(found);
        assertEquals("memory", found.getCode());
        assertEquals("80", found.getValue());
    }

    @Test
    public void getByCodeNotFoundTest(){
        given(metricCodeRepository.getOne("disk")).willReturn(null);

        MetricCode found = metricCodeBO.getByCode("disk");
        assertNull(found);
    }

    @Test
    public void findAllTest(){
        ArrayList<MetricCode> metricCodeList = new ArrayList<>();
        metricCodeList.add(new MetricCode("memory", "80"));
        metricCodeList.add(new MetricCode("disk", "90"));
        metricCodeList.add(new MetricCode("cpu", "85"));

        given(metricCodeRepository.findAll()).willReturn(metricCodeList);

        List<MetricCode> found = metricCodeBO.findAll();
        assertNotNull(found);
        assertEquals("memory", found.get(0).getCode());
        assertEquals("80", found.get(0).getValue());
        assertEquals("disk", found.get(1).getCode());
        assertEquals("90", found.get(1).getValue());
        assertEquals("cpu", found.get(2).getCode());
        assertEquals("85", found.get(2).getValue());
    }

    @Test
    public void nobodyFoundTest(){
        given(metricCodeRepository.findAll()).willReturn(null);

        List<MetricCode> found = metricCodeBO.findAll();
        assertNull(found);
    }
}

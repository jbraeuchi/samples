package icd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IcdClamlServiceTest {
    IcdClamlService undertest;

    @BeforeEach
    void parseXml() {
        undertest = new IcdClamlService();
        undertest.parseXml();
    }

    @Test
    void findByRubric_de() {
        // given

        // when
        List<IcdClamlDTO> result = undertest.findByRubric("de", "Klavikula");

        // then
        assertEquals(22, result.size());
        System.out.println(result);
    }

    @Test
    void findByRubric_de_1() {
        // given

        // when
        List<IcdClamlDTO> result = undertest.findByRubric("de", "Schulterblatt");

        // then
        assertEquals(2, result.size());
        System.out.println(result);
    }

    @Test
    void findByCode_de() {
        // given

        // when
        List<IcdClamlDTO> result = undertest.findByCode("de", "S42.0");

        // then
        assertEquals(6, result.size());

        System.out.println(result);
    }

    @Test
    void findByExpression_de() {
        // given

        // when
        List<IcdClamlDTO> result = undertest.findByExpression("de", "ClaML/Class[@kind='category']");

        // then
        assertEquals(12189, result.size());
    }

    @Test
    void findByExpression_it() {
        // given

        // when
        List<IcdClamlDTO> result = undertest.findByExpression("it", "ClaML/Class[@kind='category' and @code='S42.01']");

        // then
        assertEquals(1, result.size());
        System.out.println(result);
    }

}
import edu.profitsoft.models.Field;
import edu.profitsoft.service.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

public class JsonUtilsTest {

    @Test
    void shouldIgnoreNull() {
        // Given
        String genresString = null;

        //When
        List<String> resultList = JsonUtils.parseCollectionString(genresString);

        //Then
        Assertions.assertNotNull( resultList);
        Assertions.assertTrue(resultList.isEmpty());
    }

    @Test
    void shouldTrimSpaces() {
        // Given
        String genresString = "     one   ,     two    , three  ";
        List<String> expected = List.of("one", "two", "three");

        //When
        List<String> resultList = JsonUtils.parseCollectionString(genresString);

        //Then
        Assertions.assertNotNull( resultList);
        Assertions.assertEquals(3, resultList.size());
        Assertions.assertTrue(resultList.containsAll(expected));
    }

    @Test
    void shouldReturnEmptyResultList() {
        // Given
        String genresString = " ";

        //When
        List<String> resultList = JsonUtils.parseCollectionString(genresString);

        //Then
        Assertions.assertNotNull(resultList);
        Assertions.assertTrue(resultList.isEmpty());
    }

    @Test
    void shouldSkipEmptyValues() {
        // Given
        String genresString = "     one   ,         , three  ";
        List<String> expected = List.of("one", "three");

        //When
        List<String> resultList = JsonUtils.parseCollectionString(genresString);

        //Then
        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(2, resultList.size());
        Assertions.assertTrue(resultList.containsAll(expected));
    }

    @Test
    void shouldHaveExpectedStatisticData() {
        // Given
        File testFile = new File("src/test/resources/one/file.json");
        Field testField = Field.namesMap.get("genres");

        // When
        Map<String, Integer> resultMap = JsonUtils.parseJson(testFile, testField);

        // Then
        Assertions.assertNotNull(resultMap);
        Assertions.assertEquals(6, resultMap.size());
        String fieldValue = "rock";
        Assertions.assertTrue(resultMap.containsKey(fieldValue));
        Assertions.assertEquals(4, resultMap.get(fieldValue));

        fieldValue = "hard rock";
        Assertions .assertTrue(resultMap.containsKey(fieldValue));
        Assertions.assertEquals(1, resultMap.get(fieldValue));
    }
}

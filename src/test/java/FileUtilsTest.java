import edu.profitsoft.service.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class FileUtilsTest {

    @Test
    void shouldNotFindFilesInDirectory() {
        // Given
        String folderPath = "src/test/resources/zero/";
        List<File> files = FileUtils.getFiles(folderPath);

        // Then
        Assertions.assertNotNull(files);
        Assertions.assertTrue(files.isEmpty());
    }

    @Test
    void shouldFindOneFileInDirectory() {
        // Given
        String folderPath = "src/test/resources/one/";
        List<File> files = FileUtils.getFiles(folderPath);

        // Then
        Assertions.assertNotNull(files);
        Assertions.assertEquals(1, files.size());
    }
}
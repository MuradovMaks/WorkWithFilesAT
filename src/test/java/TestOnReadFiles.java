import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TestOnReadFiles {
    private ClassLoader cs = TestOnReadFiles.class.getClassLoader();

    @DisplayName("Проверка и чтение полей файла JSON")
    @Test
    void jsonFileParsing() throws Exception {
        try (InputStream is = cs.getResourceAsStream("simplefile.json")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Wooden wood = mapper.readValue(is, Wooden.class);

            Assertions.assertEquals("oak", wood.getTree());
            Assertions.assertEquals("Static", wood.getActivity());
            Assertions.assertEquals("all", wood.getWoodenInner().getGrow());
            Assertions.assertEquals(250, wood.getWoodenInner().getSize());
            Assertions.assertEquals(477853198, wood.getWoodenInner().getBarkDensity());
            Assertions.assertEquals("maple", wood.getWoodenInner().getLeafes());

        }
    }

    @DisplayName("Проверка и чтение полей файла Zip Архив")
    @Test
    void zipFileParsing() throws Exception {
        try (ZipInputStream zips = new ZipInputStream(cs.getResourceAsStream("Zipka.zip"))) {
            ZipEntry entry;
            String name;
            while ((entry = zips.getNextEntry()) != null) {
                int numEntry = 0;
                name = entry.getName();

                numEntry++;
                if (entry.isDirectory()) {
                    System.out.print("Directory ");
                } else {
                    System.out.print("File ");
                }
                System.out.format("Entry #%d: path=%s, size=%d, compressed size=%d \n", numEntry, entry.getName(), entry.getSize(), entry.getCompressedSize());
                if (name.contains("Simple.pdf")) {
                    PDF pdf = new PDF(zips);
                    Assertions.assertEquals("Евгений Зуев", pdf.author);
                }
                if (name.contains("Shablon.xlsx")) {
                    XLS xls = new XLS(zips);
                    String xlsfile = xls.excel.getSheetAt(0).getRow(3).getCell(4).getStringCellValue();
                    Assertions.assertEquals("Анализ документации на наличие замечаний", xlsfile);
                }
                if (name.contains("Test.csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zips));
                    List<String[]> Arr = reader.readAll();
                    Assertions.assertArrayEquals(new String[]{"Selenide", " Selenium"}, Arr.get(0));
                    Assertions.assertArrayEquals(new String[]{"Junit5", " Hello Junit5"}, Arr.get(1));
                    Assertions.assertArrayEquals(new String[]{"Jetbrains", " Rider"}, Arr.get(2));
                    Assertions.assertArrayEquals(new String[]{"Eclipse", " Java"}, Arr.get(3));
                    Assertions.assertArrayEquals(new String[]{"WebStorm", "JavaScript"}, Arr.get(4));

                }


            }

        }
    }
}

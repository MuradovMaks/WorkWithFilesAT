import JsonModels.Wooden;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TestOnReadFiles {
    private ClassLoader cs = TestOnReadFiles.class.getClassLoader();

    @DisplayName("Проверка и чтение полей файла JSON")
    @Test
    void jsonFileParsing() throws Exception {
        try (InputStream is = cs.getResourceAsStream("simplefile.json")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Wooden wood = mapper.readValue(is, Wooden.class);
            assertThat("oak").isEqualTo(wood.getTree());
            assertThat("Static").isEqualTo(wood.getActivity());
            assertThat("all").isEqualTo(wood.getWoodenInner().getGrow());
            assertThat(250).isEqualTo(wood.getWoodenInner().getSize());
            assertThat(477853198).isEqualTo(wood.getWoodenInner().getBarkDensity());
            assertThat("maple").isEqualTo(wood.getWoodenInner().getLeafes());
            assertThat("brown").isEqualTo(wood.getWoodenInner().getColor());
            assertThat("Oak").isEqualTo(wood.getWoodenInner().getView());


        }
    }

    @DisplayName("Проверка и чтение полей PDF файла в Zip архиве")
    @Test
    void zipFileParsingPDF() throws Exception {
        try (ZipInputStream zips = new ZipInputStream(cs.getResourceAsStream("Zipka.zip"))) {
            ZipEntry entry;
            String name;
            while ((entry = zips.getNextEntry()) != null) {
                int numEntry = 0;
                name = entry.getName();

                numEntry++;
                if (name.contains("Simple.pdf")) {
                    PDF pdf = new PDF(zips);
                    assertThat("Евгений Зуев").isEqualTo(pdf.author);

                }
            }

        }
    }

    @DisplayName("Проверка и чтение полей Xlsx файла в Zip архиве")
    @Test
    void zipFileParsingXlsX() throws Exception {
        try (ZipInputStream zips = new ZipInputStream(cs.getResourceAsStream("Zipka.zip"))) {
            ZipEntry entry;
            String name;
            while ((entry = zips.getNextEntry()) != null) {
                int numEntry = 0;
                name = entry.getName();

                if (name.contains("Shablon.xlsx")) {
                    XLS xls = new XLS(zips);
                    String xlsfile = xls.excel.getSheetAt(0).getRow(3).getCell(4).getStringCellValue();
                    assertThat("Анализ документации на наличие замечаний").isEqualTo(xlsfile);
                }
            }
        }
    }

    @DisplayName("Проверка и чтение полей CSV файла в Zip архиве")
    @Test
    void zipFileParsingCSV() throws Exception {
        try (ZipInputStream zips = new ZipInputStream(cs.getResourceAsStream("Zipka.zip"))) {
            ZipEntry entry;
            String name;
            while ((entry = zips.getNextEntry()) != null) {
                int numEntry = 0;
                name = entry.getName();

                if (name.contains("Test.csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zips));
                    List<String[]> Arr = reader.readAll();
                    assertThat(new String[]{"Selenide", " Selenium"}).isEqualTo(Arr.get(0));
                    assertThat(new String[]{"Junit5", " Hello Junit5"}).isEqualTo(Arr.get(1));
                    assertThat(new String[]{"Jetbrains", " Rider"}).isEqualTo(Arr.get(2));
                    assertThat(new String[]{"Eclipse", " Java"}).isEqualTo(Arr.get(3));
                    assertThat(new String[]{"WebStorm", "JavaScript"}).isEqualTo(Arr.get(4));

                }
            }
        }
    }
}
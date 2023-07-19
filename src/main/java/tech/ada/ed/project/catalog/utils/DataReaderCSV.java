package tech.ada.ed.project.catalog.utils;



import tech.ada.ed.project.catalog.model.Title;
import tech.ada.ed.project.catalog.model.enums.AgeCertificationEnum;
import tech.ada.ed.project.catalog.model.enums.CountryEnum;
import tech.ada.ed.project.catalog.model.enums.GenreEnum;
import tech.ada.ed.project.catalog.model.enums.TypeEnum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DataReaderCSV implements DataReader {

    private static final String PATH = "src/main/resources/titlesFullDuplicates.csv";
    private static final String PATH_TOP10 = "src/main/resources/titlesTop10Duplicates.csv";

    @Override
    public List<Title> loadCatalog(int option) {
        String finalPath = option == 1 ? PATH : PATH_TOP10;
        List<Title> titleList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(finalPath))) {

            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";", -1);
                String id = data[0].trim();
                String title = data[1].trim();
                String type = data[2].trim();
                int year = Integer.parseInt(data[3].trim());
                String ageCert = data[4].trim();
                int runtime = Integer.parseInt(data[5].trim());
                String genres = clean(data[6].trim());
                List<GenreEnum> genreEnumList = Stream.of(genres.split(",", -1))
                        .map(GenreEnum::valueOfLabel).toList();
                String country = clean(data[7].trim());
                List<CountryEnum> countryEnumList = Stream.of(country.split(",", -1))
                        .map(CountryEnum::valueOfLabel).toList();
                Optional<Byte> optSeasons = null;
                if (data[8].trim() != "")
                    optSeasons= Optional.of((byte) Double.parseDouble(data[8].trim()));
                String imdbId = data[9].trim();
                Optional<Double> optImdbScore = null;
                if (data[10].trim() != "")
                    optImdbScore = Optional.of(Double.parseDouble(data[10].trim()));
                Optional<Integer> optImdbVotes = null;
                if (data[11].trim() != "")
                    optImdbVotes = Optional.of((int) Double.parseDouble(data[11].trim()));
                Optional<Double> optTmdbPop = null;
                if (data[12].trim() != "")
                    optTmdbPop = Optional.of(Double.parseDouble(data[12].trim()));
                Optional<Double> optTmdbScore = null;
                if (data[13].trim() != "")
                    optTmdbScore = Optional.of(Double.parseDouble(data[13].trim()));

                Title t = new Title(id, title, TypeEnum.valueOf(type), year, AgeCertificationEnum.valueOfLabel(ageCert)
                        , runtime, genreEnumList, countryEnumList, optSeasons, imdbId, optImdbScore, optImdbVotes, optTmdbPop, optTmdbScore);

                titleList.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return titleList;
    }

    private String clean(String value) {
        return value.substring(1, value.length() - 1)
                .replaceAll("'", "").replaceAll("\\s", "");
        }
}



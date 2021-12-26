package com.imdbscraper.top250;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *
 * @author Soumyadeep Mukhopadhyay
 */

public class FirstJavaSpider {

    public static String getSerialNumber(Element elem){
        return elem.select("td.posterColumn span").attr("data-value");
    }

    public static String getFilmName(Element elem){
        return elem.select("td.titleColumn a").text();
    }

    public static Integer getYear(Element elem){
        return Integer.parseInt(elem.select("td.titleColumn span").text().
                replaceAll("[^\\d]",""));
    }

    public static String getTopCastMembers(Element elem){
        return elem.select("td.titleColumn a").
                attr("title");
    }

    public static String getIMDBRating(Element elem){
        return elem.select("td.ratingColumn.imdbRating").select("strong").
                attr("title").trim();
    }

    public static double getRatingOnly(Element elem){
        return Double.parseDouble(elem.select("td.ratingColumn.imdbRating").
                text().trim());
    }

    public static String getDateTime(){
        var dtFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        var now = LocalDateTime.now();
        return dtFormat.format(now);
    }

    public static HashMap<String, Object> getEachRow(Element elem){
        HashMap<String, Object> row = new HashMap<>();
        row.put("Serial Number",  getSerialNumber(elem));
        row.put("Film Name", getFilmName(elem));
        row.put("Top Cast Members", getTopCastMembers(elem));
        row.put("Year", getYear(elem));
        row.put("IMDB Rating", getIMDBRating(elem));
        row.put("Rating Value", getRatingOnly(elem));
        row.put("Current Date Time", getDateTime());
        return row;
    }

    public static Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).
                timeout(6000).get();
    }

    public static List<HashMap<String, Object>> getAllMovies(Elements body){
        List<HashMap<String, Object>> rows = new ArrayList<>();
        for(Element elem : body.select("tr")){
            HashMap<String, Object> row = getEachRow(elem);
            rows.add(row);
        }
        return rows;
    }

    public static void csvWriter(List<HashMap<String, Object>> listOfMovies,
                                 Writer writer) throws IOException {
        CsvSchema schema = null;
        CsvSchema.Builder schemaBuilder = CsvSchema.builder();
        if (listOfMovies != null && !listOfMovies.isEmpty()) {
            for (String col : listOfMovies.get(0).keySet()) {
                schemaBuilder.addColumn(col);
            }
            schema = schemaBuilder.build().withLineSeparator("\r").withHeader();
        }
        CsvMapper mapper = new CsvMapper();
        mapper.writer(schema).writeValues(writer).writeAll(listOfMovies);
        writer.flush();
    }

    public static void writeToCsv(String filePath,
                                  List<HashMap<String, Object>> arrList) throws IOException {
        File file = new File(filePath);
        // Create a File and append if it already exists.
        Writer writer = new FileWriter(file, true);
        Reader reader = new FileReader(file);
        //Copy List of Map Object into CSV format at specified File location.
        csvWriter(arrList, writer);
    }

    public static void main(String[] args) throws IOException {
        var path = "./getTopMovies.csv";
        final String url = "https://www.imdb.com/chart/top";
        Document doc = getDocument(url);
        Elements body = doc.select("tbody.lister-list");
        var rows = getAllMovies(body);
        writeToCsv(path, rows);
        System.out.println(getDateTime());
//        System.out.println(body.select("tr").select("td.titleColumn a").eachText());
//        HashMap<Integer, Movies> rows = new HashMap<>();
//        for(Element elem : body.select("tr")){
//            HashMap<String, Object> row = getEachRow(elem);
//            Movies movie = new Movies(
//                    row.get("Serial Number"),
//                    row.get("Film Name"),
//                    row.get("Top Cast Members"),
//                    row.get("Year"),
//                    row.get("IMDB Rating"),
//                    row.get("Rating Value")
//            );
//            rows.put(Integer.parseInt((String) row.get("Serial Number")), movie);
//            rows.add(row);
//            System.out.println(listNum+" | " +filmName+" | "+importantCastMembers+" | "+year+" | "+imdbRating+
//                    " | "+ratingOnly);
//            System.out.println(row);


    }
}

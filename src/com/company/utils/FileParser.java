package com.company.utils;

import com.company.model.Country;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileParser {

  private BufferedReader br;
  private static final String isDigit = "\\d";
  private static final String isValidLine = "(\\w){1,24} \\d \\d \\d \\d";

  public FileParser(String name) throws FileNotFoundException {
    this.br = new BufferedReader(new FileReader(new File(name)));
    Pattern pattern = Pattern.compile(isDigit);
  }

  public  List<List<Country>> readAll() throws IOException {
    String s;
    List<List<Country>> listOfRelevantMaps = new ArrayList<>();
    while (!(s = br.readLine()).matches("0") && s.matches(isDigit)) {
      int count = Integer.parseInt(s);
      ArrayList<Country> countries = new ArrayList<>();
      for (int i = 0; i < count; i++) {
        String line = br.readLine();
        if (line.matches(isValidLine)) {
          String str[] = line.split(" ");
          Country country = new Country(str[0], Integer.parseInt(str[1]),
              Integer.parseInt(str[2]), Integer.parseInt(str[3]),
              Integer.parseInt(str[4]));
          countries.add(country);
        } else throw new IllegalArgumentException();
      }
      listOfRelevantMaps.add(countries);
    }

    return listOfRelevantMaps;
  }
}

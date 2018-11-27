package com.company;

import com.company.model.Country;
import com.company.model.Diffuse;
import com.company.utils.FileParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {
    FileParser fileParser = new FileParser("input.txt");
    List<List<Country>> countryList = fileParser.readAll();
    for (List<Country> countries : countryList) {
      Diffuse diffuse = new Diffuse();
      for (Country country : countries) {
        diffuse.addCountry(country);
      }
      diffuse.initNeighbors();
      while (!diffuse.isComplete()) {
        diffuse.countOneDay();
      }
      System.out.println("-------");
    }
  }
}

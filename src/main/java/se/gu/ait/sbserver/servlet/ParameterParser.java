package se.gu.ait.sbserver.servlet;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Arrays;
import java.util.stream.Collectors;

import se.gu.ait.sbserver.domain.Product;

public class ParameterParser {
  private String[] args;
  public ParameterParser(String[] args) {
    this.args = args;
  }

  private String addedStartDate = "";
  private String addedEndDate = "date()";
  private String priceStartDate = "";
  private String priceEndDate = "";

  public Predicate<Product> filter() {
    List<Predicate<Product>> predicates = parse(); // get a list of predicates
    // Reduce the list of predicates using "and"
    // https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html
    return predicates.stream().reduce(p -> true, Predicate::and);
  }
  
  private static boolean isDouble(String value) {
    try {
      Double.parseDouble(value);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }

  private boolean isValidKey(String key) {
    return key.split("=").length==2 &&
      Arrays.stream(new String[] {
          "min_price",
          "max_price",
          "min_alcohol",
          "max_alcohol", 
          "added_start_date",
          "added_end_date",
          "price_start_date",
          "price_end_date"
        }).collect(Collectors.toList()).contains(key.split("=")[0]);
  }
  
  private List<Predicate<Product>> parse() {
    List<Predicate<Product>> predicates = new ArrayList<>();
    List<String> validArgs = new ArrayList<>(Arrays.asList(args));
    validArgs.removeIf(s->!isValidKey(s));
    for (String arg : validArgs) {
      if (isDouble(arg.split("=")[1])) {
        double value = Double.parseDouble(arg.split("=")[1]);
        switch(arg.split("=")[0]) { // Check what filter it is
          case "max_price": predicates.add(p -> p.price() <= value);
            break;
          case "min_price": predicates.add(p -> p.price() >= value);
            break;
          case "min_alcohol": predicates.add(p -> p.alcohol() >= value);
            break;
          case "max_alcohol": predicates.add(p -> p.alcohol() <= value);
            break;
          default:
            continue;
        }
      } else {
        String value = arg.split("=")[1];
        if (arg.split("=")[0].equals("added_start_date")) {
          addedStartDate = value;
          System.out.println(value);
        } else if (arg.split("=")[0].equals("added_end_date")){
          addedEndDate = "'" + value + "'";
          System.out.println(value);
        } else if (arg.split("=")[0].equals("price_start_date")) {
          priceStartDate = value;
        } else if (arg.split("=")[0].equals("price_end_date")) {
          priceEndDate = value;
        }
      }
    }
    return predicates;
  }

  public List<String> invalidArgs() {
    List<String> invalids = Arrays.stream(args)
      .filter(a->!isValidKey(a))
      .collect(Collectors.toList());
    return invalids;
  }

  public String addedStartDate() {
    return addedStartDate;
  }

  public String addedEndDate() {
    return addedEndDate;
  }

  public String priceStartDate() {
    return priceStartDate;
  }

  public String priceEndDate() {
    return priceEndDate;
  }

}

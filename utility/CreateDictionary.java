import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Creates the input dictionary used for various text editor
 * functions such as syntax highlighting
 *
 * @author geordyp
 */
public class CreateDictionary {

  // the newly created dictionary
  private static File _dakota6_input_dictionary;

  // input keyword descriptions
  private static File _dakota6_input_desc;

  // input keyword detailed with functions
  private static File _dakota6_input_nspec;

  public static void main(String[] args) throws IOException {
    try {
      _dakota6_input_dictionary = new File("dakota.input.dictionary");
      _dakota6_input_desc = new File("dakota.input.desc");
      _dakota6_input_nspec = new File("dakota.input.nspec");
    }
    catch (Exception e) {
      System.out.println(e.toString());
    }

    Scanner descScanner;
    Scanner summaryScanner;
    Scanner nspecScanner;
    PrintWriter dictionaryWriter;

    try {
      descScanner = new Scanner(_dakota6_input_desc);
      nspecScanner = new Scanner(_dakota6_input_nspec);
      dictionaryWriter = new PrintWriter(_dakota6_input_dictionary);

      while (descScanner.hasNext()) {
        String descLine = descScanner.nextLine();

        // if this line is a keyword
        if (descLine.contains("TAG")) {
          String keywordPath = descLine.split("\"")[1];
          dictionaryWriter.print("KYWD " + keywordPath);

          // get aliases and parameters by finding the equivalent line in nspec
          while (nspecScanner.hasNext()) {
            String nspecLine = nspecScanner.nextLine();
            String[] nspecLineSplit = nspecLine.split("\\s+");

            // grab the line's keyword if it exists (could be an empty line)
            // nspec and desc have the same order so it will eventually be found
            int i = 0;
            boolean keywordExists = true;
            while (!nspecLineSplit[i].matches("\\w+")) {
              i++;
              if (i >= nspecLineSplit.length) {
                // there is no keyword on this line
                keywordExists = false;
                break;
              }
            }

            if (keywordExists) {
              // we don't need to check main keywords for aliases or parameters
              if (!nspecLineSplit[i].contains("KEYWORD")) {
                // using these to figure out where the parameter is
                int kywdStartIndex = 0;
                int kywdEndIndex = 0;

                // first grab aliases if they exist
                if (nspecLine.contains("ALIAS")) {
                  // the parameter will follow the last alias
                  int indexOfLastAlias = 0;
                  for (int x = 0; x < nspecLineSplit.length; x++) {
                    if (nspecLineSplit[x].equals("ALIAS")) {
                      dictionaryWriter.print(" ALIAS " + nspecLineSplit[x + 1]);
                      indexOfLastAlias = x + 1;
                    }
                  }

                  // need to add the space because sometimes the alias is a substring of the keyword
                  kywdStartIndex = nspecLine.indexOf(" " + nspecLineSplit[indexOfLastAlias]);
                  // need to increment because of the space we used above
                  kywdStartIndex++;
                  kywdEndIndex = kywdStartIndex + nspecLineSplit[indexOfLastAlias].length();
                }
                else {
                  // if there weren't aliases, then the parameter will be after the keyword
                  kywdStartIndex = nspecLine.indexOf(nspecLineSplit[i]);
                  kywdEndIndex = kywdStartIndex + nspecLineSplit[i].length();
                }

                // the parameter is between kywdEndIndex and {
                int indexOfClosingBracket = nspecLine.indexOf("{");
                // if the difference between the ending index and { is greater than 1
                if (indexOfClosingBracket - kywdEndIndex > 1) {
                  // then there are paramters
                  dictionaryWriter.println();
                  dictionaryWriter.print("\tPARAM " + nspecLine.substring(kywdEndIndex + 1, indexOfClosingBracket - 1));
                }
              }

              // keyword was found, we can break
              break;
            }
          }
          dictionaryWriter.println();
        }
        // if this line is a description
        else if (descLine.contains("DESC")) {
          int openBrac = descLine.indexOf("{");
          if (openBrac != -1) {
            int endBrac = descLine.indexOf("}");

            // the description is between the brackets
            String desc = descLine.substring(openBrac + 1, endBrac);
            if (isMainKeyword(desc)) {
              // main keyword descriptions are outside the brackets
              int period = descLine.lastIndexOf('.', descLine.length() - 7);
              desc = descLine.substring(endBrac + 2, period);
              dictionaryWriter.println("\tDESC " + desc);
            }
            else {
              dictionaryWriter.println("\tDESC " + desc);
            }
          }
        }
      }

      descScanner.close();
      nspecScanner.close();
      dictionaryWriter.close();
    }
    catch (FileNotFoundException e) {
      System.out.println(e.toString());
    }
  }

  /**
   * checks if the given word is a main keyword
   *
   * @param word
   * @return true if word is a keyword, otherwise false
   */
  private static boolean isMainKeyword(String word) {
    word = word.toLowerCase();
    if (word.equals("environment") || word.equals("method") ||
        word.equals("model") || word.equals("variables") ||
        word.equals("interface") || word.equals("responses")) {

      return true;
    }

    else return false;
  }
}

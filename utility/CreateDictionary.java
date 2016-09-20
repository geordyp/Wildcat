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

  // input keywords with parameter types
  private static File _dakota6_input_summary;

  public static void main(String[] args) throws IOException {
    try {

      // TODO figure out a way to find these
      _dakota6_input_dictionary = new File("dakota.input.dictionary");
      _dakota6_input_desc = new File("dakota6.input.desc");
      _dakota6_input_summary = new File("dakota6.input.summary");
      _dakota6_input_nspec = new File("dakota6.input.nspec");
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
      summaryScanner = new Scanner(_dakota6_input_summary);
      nspecScanner = new Scanner(_dakota6_input_nspec);
      dictionaryWriter = new PrintWriter(_dakota6_input_dictionary);

      while (descScanner.hasNext()) {
        String descLine = descScanner.nextLine();

        if (descLine.contains("TAG")) {
          String keywordPath = descLine.split("\"")[1];

          dictionaryWriter.print("KWD " + keywordPath);

          // get aliases
          while (nspecScanner.hasNext()) {
            String nspecLine = nspecScanner.nextLine();
            String[] nspecLineSplit = nspecLine.split("\\s+");

            // process the current line
            // grabbing the line's keyword if it exists
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
              if (nspecLine.contains("ALIAS")) {
                for (int x = 0; x < nspecLineSplit.length; x++) {
                  if (nspecLineSplit[x].equals("ALIAS")) {
                    dictionaryWriter.print(" ALIAS " + nspecLineSplit[x + 1]);
                  }
                }
              }

              break;
            }
          }
          dictionaryWriter.println();
        }
      }

      descScanner.close();
      summaryScanner.close();
      nspecScanner.close();
      dictionaryWriter.close();
    }
    catch (FileNotFoundException e) {
      System.out.println(e.toString());
    }
  }


}

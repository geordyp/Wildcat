import java.util.*;
import java.io.*;

public class Test {
  static KeywordNode _keywordTree;
  static ArrayList<String> _mainKeywords;
  static ArrayList<String> _regularKeywords;

  public static void main (String[] args) {
    _keywordTree = null;
    _mainKeywords = new ArrayList<String>();
    _regularKeywords = new ArrayList<String>();
    getKeywordTree();
  }

  	/**
     * TODO replace the funciton in Activator with this
  	 * Get the list of keywords
  	 *
  	 * @return _keywordTree - the tree of keywords
  	 */
  	private static KeywordNode getKeywordTree() {
  		if (_keywordTree == null) {
  			try {
  				// Scanner s = new Scanner(new File("C:/Users/geordypaul/Documents/Research/Wildcat/edu.ksu.wildcat/utility/dakota.input.dictionary"));
  				Scanner dictionScanner = new Scanner(new File("/Users/geordywilliams/Documents/wildcat/utility/dakota.input.dictionary"));

  				_mainKeywords = new ArrayList<String>();
  				_regularKeywords = new ArrayList<String>();
  				_keywordTree = new KeywordNode("root", null, null, null, null);

          String kywd = dictionScanner.nextLine();
  				while (dictionScanner.hasNext()) {
            // for testing purposes
            if (kywd.contains("method")) {
              break;
            }

            String desc = "";
            String param = "";
            String line = dictionScanner.nextLine();
            while (!line.contains("KYWD")) {
              if (line.contains("DESC")) {
                desc = line.substring(6);
              }
              else if (line.contains("PARAM")) {
                param = line.substring(7);
              }
              line = dictionScanner.nextLine();
            }
            // outside the loop, line holds the next kewyord

  					// remove KYWD
  					kywd = kywd.substring(5);
  					// grab individual keywords
  					String[] keywords = kywd.split("/");

            // create keyword node with kywd, desc, and param
            // set parent to root
		        KeywordNode parentNode = _keywordTree;
		        for (int i = 0; i < keywords.length; i++) {
		        	String keyword = keywords[i];
		        	ArrayList<String> aliases = null;

		        	if (keyword.contains("ALIAS")) {
		        		aliases = new ArrayList<String>();
		        		String[] words = keywords[i].split(" ALIAS ");

		        		// start at 1 because the actual keyword is at 0
		        		for (int j = 1; j < words.length; j++) {
		        			aliases.add(words[j]);
		        			_regularKeywords.add(words[j]);
		        		}

                // without this keyword could be set to "[word] ALIAS [word]"
		        		keyword = words[0];
		        	}

		        	KeywordNode currNode = parentNode.findChild(keyword);
		          if (currNode == null) {
                parentNode.insert(keyword, desc, param, aliases, parentNode);
						    if (keywords.length == 1) {
							    _mainKeywords.add(keywords[0]);
                }
                else {
                  _regularKeywords.add(keyword);
                }
		        	}
	            else {
	            	parentNode = currNode;
	            }
		        }

            // set kywd to the next kywd
            kywd = line;
  				}

  				dictionScanner.close();
  			}
  			catch (Exception e) {
  				System.out.println(e.toString());
  			}
  		}

  		return _keywordTree;
  	}
}

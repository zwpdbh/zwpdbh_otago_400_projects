import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by zw on 13/04/2017.
 */
public class IndexParser {

    Htable htable = null;

    public IndexParser(int capacity) {
        this.htable = new Htable(capacity);
    }

    public void parseXML(String xmlFile) {
        if (this.htable == null) {
            this.htable = new Htable(300000);
        }

        Scanner fileScan;
        String str = "";

        String termString = "";
        String docID = "";

        int docCount = 0;

        try {
            fileScan = new Scanner(new File(xmlFile));
            long start = System.currentTimeMillis();
            System.out.println("Parsing XML...");

            while (fileScan.hasNext()) {
                // 1. to lowercase
                str = fileScan.next().toLowerCase();
//                System.out.println(str);


                if (isDocumentNumber(str)) {
                    docID = str;
                    docCount += 1;
//                     System.out.println(docID);
                    continue;
                }

                for (String each : getToken(str)) {
                    // 1. get the term, docID pair
                    TermDocPair t = new TermDocPair(each, docID);
                    // 2. insert it into the index
                    int freq = this.htable.htableInsert(t);
                    // System.out.print(termString + "=>" + freq + "\n");
                }
            }
            System.out.println("Done.");
            System.out.println("Total docs: " + docCount);
            System.out.println("Total terms: " + htable.getNumOfKeys());
            long end = System.currentTimeMillis();
            NumberFormat formatter = new DecimalFormat("#0.00000");
            System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");

        } catch (FileNotFoundException e) {
            System.err.println(String.format("Error occurs when trying to open xml file: %s", e));
        }
    }

    public void generateInvertedIndex(String saveIndexToURL) throws Exception {

        FileOutputStream fos = new FileOutputStream(saveIndexToURL);
        GZIPOutputStream gz = new GZIPOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(gz);

        oos.writeObject(this.htable);
        oos.flush();
        oos.close();
        fos.close();
    }

    public boolean loadInvertedIndex(String indexURL) throws Exception {
        this.htable = null;
        FileInputStream fis = new FileInputStream(indexURL);
        GZIPInputStream gs = new GZIPInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(gs);

        this.htable = (Htable) ois.readObject();
        if (this.htable == null) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Node> search(String input) {
        ArrayList<Node> result = new ArrayList<>();
        String[] searchTerms = input.split("[\\p{Punct}\\s']+");

        ArrayList<IndexTerm> terms = new ArrayList<>();

        for (String eachTerm: searchTerms) {
            int eachResult = this.htable.search(eachTerm);
            if (eachResult == -1) {
                return result;
            } else {
                terms.add(this.htable.getIndexTerm(eachResult));
            }
        }


        return result;
    }

    private ArrayList<String> getToken(String word) {
        String[] tmp = removeTags(word).split("[\\p{Punct}\\s']+");
        ArrayList<String> results = new ArrayList<>();
        String token;

        for (int i = 0; i < tmp.length; i++) {
            token = removeMeaninglessString(tmp[i]);
            token = token.replaceAll("[,\"().]", "");
            if (!token.trim().isEmpty()) {
                results.add(token);
            }
        }

        return results;
    }

    private String removeTags(String str) {
        String result = str;

        Pattern pat;
        Matcher mat;

        // need to get the document number
        pat = Pattern.compile("^wsj[0-9]*-[0-9]*");
        mat = pat.matcher(result);
        if (mat.find()) {
            return result;
        }

        if (!result.isEmpty()) {
            // to handle: "&lt/docno&gt<w:rfonts"
            pat = Pattern.compile(".*\\/.+<.*");
            mat = pat.matcher(result);
            if (mat.find()) {
                result = "";
            }
        }

        if (!result.isEmpty()) {
            // to handle: xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
            pat = Pattern.compile(".+:[a-z0-9-]+=");
            mat = pat.matcher(result);
            if (mat.find()) {
                result = "";
            }
        }

        if (!result.isEmpty()) {
            // to handle: <?xml
            pat = Pattern.compile("^<.+[a-z]$");
            mat = pat.matcher(result);
            if (mat.find()) {
                result = "";
            }
        }

        if (!result.isEmpty()) {
            // to handle: progid="word.document"?>
            pat = Pattern.compile("[a-z0-9]+=\"[a-z0-9.]+\".*>$");
            mat = pat.matcher(result);
            if (mat.find()) {
                result = "";
            }
        }


        if (!result.isEmpty()) {
            // to handle &amp
            pat = Pattern.compile("&amp");
            mat = pat.matcher(result);
            if (mat.find()) {
                result = "";
            }
        }

        if (!result.isEmpty()) {
            // to handle : encoding="utf-8"
            pat = Pattern.compile("[a-z0-9]+=\"");
            mat = pat.matcher(result);
            if (mat.find()) {
                result = "";
            }
        }

        result = result.replaceAll("<[/]?.+>", "");

        return result;
    }

    private String removeMeaninglessString(String str) {
        if (isNumeric(str)) {
            return str;
        } else if (str.length() > 1) {
            return str;
        } else {
            return "";
        }
    }

    private boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    private boolean isDocumentNumber(String s) {
        Pattern pat = Pattern.compile("^wsj[0-9]*-[0-9]*");
        Matcher mat = pat.matcher(s);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEndOfDoc(String s) {
        Pattern pat = Pattern.compile("</doc>");
        Matcher mat = pat.matcher(s);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }

    private String stripNonDigits(final CharSequence input) {
        final StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}


class PostingsList implements Serializable {
    ArrayList<Node> postingsList;

    public PostingsList() {
        this.postingsList = new ArrayList<>();
    }

    public void addTermDocPair(TermDocPair t) {
        if (this.postingsList.isEmpty()) {
            Node n = new Node(t.term, 1);
            this.postingsList.add(n);
        } else {
            int i;
            for (i = 0; i < this.postingsList.size(); i++) {
                Node each = postingsList.get(i);
                if (each.docID.compareTo(t.docID) == 0) {
                    each.freq += 1;
                    this.postingsList.set(i, each);
                    return;
                }
            }
            this.postingsList.add(new Node(t.docID, 1));
        }

    }

    @Override
    public String toString() {
        String str = "";
        for (Node n: this.postingsList) {
            str += n.toString();
        }
        return str;
    }
}

class IndexTerm implements Serializable {
    String termString;
    int frequency;
    PostingsList postings;

    public IndexTerm() {
        this.termString = "";
        this.postings = new PostingsList();
        this.frequency = 0;
    }

    public IndexTerm(String s, int frequency) {
        this.termString = s;
        this.frequency = frequency;
        this.postings = new PostingsList();
    }

    @Override
    public String toString() {
        String str = "<" + this.termString + "," + this.frequency + "> :";
        str += this.postings.toString() + "\n";
        return str;
    }
}


class Node implements Serializable {
    String docID;
    int freq;

    public Node(String docID, int freq) {
        this.docID = docID;
        this.freq = freq;
    }

    @Override
    public String toString() {
        return "->(" + this.docID + ", " + this.freq + ")";
    }
}

class TermDocPair implements Serializable {
    String term;
    String docID;

    public TermDocPair(String term, String docID) {
        this.term = term;
        this.docID = docID;
    }
}

package test;


import java.util.HashMap;

public class DictionaryManager {
    private static DictionaryManager d = null;
    HashMap<String,Dictionary> dictionaries;

    private DictionaryManager(){
        dictionaries = new HashMap<>();
    }



    public boolean query(String...args) {
        if (args.length == 0) {
            return false;
        }
        boolean flag = false;
        for (int i=0;i<args.length-1;i++) {
            if (!dictionaries.containsKey(args[i])) {
                dictionaries.put(args[i],new Dictionary(args[i]) );
            }
            if (dictionaries.get(args[i]).query(args[args.length-1]))
                flag=true;
        }
        return flag;
    }


    public boolean challenge(String...args) {
        if (args.length == 0) {
            // Handle empty input
            return false;
        }
        boolean flag = false;
        for (int i=0;i<args.length-1;i++) {
            if (!dictionaries.containsKey(args[i])) {
                dictionaries.put(args[i],new Dictionary(args[i]));
            }
            if (dictionaries.get(args[i]).challenge(args[args.length-1]))
                flag=true;
        }
        return flag;
    }
    public int getSize (){
        return dictionaries.size();
    }

    public static DictionaryManager get() {
        if (d == null) {
            d = new DictionaryManager();
            return d;
        }
        return d;
    }
}

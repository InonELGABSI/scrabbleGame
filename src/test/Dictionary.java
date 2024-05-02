package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Dictionary {
    CacheManager existWords;
    CacheManager notExistWords;
    BloomFilter bloomFilter;
    String[]fileNames;

    public Dictionary(String... fileNames){
        this.existWords=new CacheManager(400,new LRU());
        this.notExistWords=new CacheManager(100,new LFU());

        this.bloomFilter = new BloomFilter(256,"MD5","SHA1");

        this.fileNames=fileNames.clone();

        for (String file : fileNames) {
            try {
                Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));


                while (scanner.hasNext()) {
                    bloomFilter.add(scanner.next());
                }
                scanner.close();
            }
            catch (FileNotFoundException e){
                System.err.println("Error opening file: " + e.getMessage());
            }
        }
    }

    public boolean query(String word) {
        if (existWords.query(word)) {
            return true;
        }

        if (notExistWords.query(word))
            return false;

        if(bloomFilter.contains(word)){
            existWords.add(word);
            return true;
        }
        return false;
    }

    public boolean challenge(String word) {
            if(IOSearcher.search(word,fileNames)){
                existWords.add(word);
                return true;
            }
            notExistWords.add(word);
            return false;

    }
}

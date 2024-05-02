package test;
import java.util.HashSet;

public class CacheManager {
    int maxSize;
    HashSet<String> commonWords;
    CacheReplacementPolicy replacementPolicy;

    public CacheManager(int maxSize, CacheReplacementPolicy replacementPolicy) {
        this.maxSize = maxSize;
        this.commonWords = new HashSet<>();
        this.replacementPolicy = replacementPolicy;
    }

    public boolean query(String word) {
        return commonWords.contains(word);
    }

    public void add(String word) {
        if (commonWords.contains(word)) { return;}
        if (commonWords.size() >= maxSize) {
            String removedWord = replacementPolicy.remove();
            commonWords.remove(removedWord);
        }
        commonWords.add(word);
        replacementPolicy.add(word);
    }
}

package test;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
    private final int bitSize;
    private final BitSet bitSet;
    private final MessageDigest[] mds;

    public BloomFilter(int bitSize, String...strings){
        this.bitSize = bitSize;
        this.bitSet = new BitSet(bitSize);
        this.mds = new MessageDigest[strings.length];
        for (int i = 0; i < strings.length; i++) {
            try{
                mds[i] = MessageDigest.getInstance(strings[i]);
            }
            catch (NoSuchAlgorithmException e){
                System.err.println(e.getMessage());}
        }
    }
     public void add (String word){
         for (MessageDigest md : mds) {
             byte[] wordBytes = md.digest(word.getBytes());
             int index = Math.abs(new BigInteger(wordBytes).intValue()) % this.bitSize;
             bitSet.set(index,true);
         }
     }
     public boolean contains(String word){
        for (MessageDigest md : mds) {
            byte[] wordBytes = md.digest(word.getBytes());
            int index = Math.abs(new BigInteger(wordBytes).intValue()) % this.bitSize;
            if (!bitSet.get(index)) {
                return false;
            }
        }
        return true;
     }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< bitSet.length(); i++) {
            sb.append(bitSet.get(i) ? "1" : "0");
        }
        return sb.toString();
    }
}

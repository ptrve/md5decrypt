package utils;

import java.security.MessageDigest;

public class Util {

    public static char firstCh = 'a';
    public static char lastCh = 'z';


    public static String stringify(char[] currentGuess) throws Exception {
//        System.out.println(String.valueOf(currentGuess));
        return md5(String.valueOf(currentGuess));
    }


    public static String md5(String msg) throws Exception {
        MessageDigest hash = MessageDigest.getInstance("MD5");
        byte[] bytes = msg.getBytes();
        byte[] digest = hash.digest(bytes);
        return bytesToHex(digest).toUpperCase();
    }

    public static String bytesToHex(byte[] data) {
        StringBuffer results = new StringBuffer();
        for (byte byt : data)
            results.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return results.toString();
    }
}

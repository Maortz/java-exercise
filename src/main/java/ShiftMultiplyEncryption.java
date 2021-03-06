public class ShiftMultiplyEncryption extends IntegerEncryption {

    public String encrypt(String msg) {
        String retMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            // Unicode. No need modulo
            retMsg += (char) ((int) (msg.charAt(i)) * key);
        }
        return retMsg;
    }

    public String decrypt(String msg) {
        String retMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            retMsg += (char) ((int) (msg.charAt(i)) / key);
        }
        return retMsg;
    }

}

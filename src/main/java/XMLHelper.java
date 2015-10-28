
public class XMLHelper {
    

    private enum ALGORITHM_NAME {
        ShiftUpEncryption, ShiftMultiplyEncryption, XorEncryption, RepeatEncryption, DoubleEncryption
    }

    public static IEncryptionAlgorithm<?> getAlgorithmByName(String algorithmName)
            throws Exception {
        int i = algorithmName.indexOf(',');
        IEncryptionAlgorithm<?> inner = null;
        if (i != -1) {
            inner = getAlgorithmByName(algorithmName.substring(i + 1).trim());
            algorithmName = algorithmName.substring(0, i);
        }
        int num = 1;
        if (algorithmName.startsWith("RepeatEncryption")) {
            num = Integer.parseInt(algorithmName.substring(16));
            algorithmName = algorithmName.substring(0, 16);
        }

        switch (Enum.valueOf(ALGORITHM_NAME.class, algorithmName)) {
        case DoubleEncryption:
            return new DoubleEncryption<Integer>(
                    (IEncryptionAlgorithm<Integer>) inner);
        case RepeatEncryption:
            return new RepeatEncryption<Integer>(
                    (IEncryptionAlgorithm<Integer>) inner, num);
        case ShiftMultiplyEncryption:
            return new ShiftMultiplyEncryption();
        case ShiftUpEncryption:
            return new ShiftUpEncryption();
        case XorEncryption:
            return new XorEncryption();
        }
        throw new Exception("Should not get here");
    }
    
}

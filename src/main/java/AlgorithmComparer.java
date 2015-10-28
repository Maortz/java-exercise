import java.util.Comparator;

public class AlgorithmComparer<E> implements
        Comparator<IEncryptionAlgorithm<E>> {

    public int compare(IEncryptionAlgorithm<E> o1, IEncryptionAlgorithm<E> o2) {
        return Integer.compare(o1.getKeyStrength(), o2.getKeyStrength());
    }

}

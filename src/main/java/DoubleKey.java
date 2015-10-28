public class DoubleKey<E> {

    E key1, key2;

    public E getKey1() {
        return key1;
    }

    public void setKey1(E key1) {
        this.key1 = key1;
    }

    public E getKey2() {
        return key2;
    }

    public void setKey2(E key2) {
        this.key2 = key2;
    }

    public DoubleKey(E key1, E key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

}

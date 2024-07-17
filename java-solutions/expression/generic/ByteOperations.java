package expression.generic;

public class ByteOperations implements Operations<Byte>{
    @Override
    public Byte add(Byte element1, Byte element2) {
        return (byte)(element1 + element2);
    }

    @Override
    public Byte subtract(Byte element1, Byte element2) {
        return (byte)(element1 - element2);
    }

    @Override
    public Byte multiply(Byte element1, Byte element2) {
        return (byte)(element1 * element2);
    }

    @Override
    public Byte divide(Byte element1, Byte element2) {
        if (element2 == 0) {
            return null;
        }
        return (byte)(element1 / element2);
    }

    @Override
    public Byte unary(Byte element) {
        return (byte)(-1 * element);
    }

    @Override
    public Byte cast(int element) {
        return (byte)element;
    }

    @Override
    public Byte min(Byte element1, Byte element2) {
        return (byte) (Math.min(element1, element2));
    }

    @Override
    public Byte max(Byte element1, Byte element2) {
        return (byte) (Math.max(element1, element2));
    }

    @Override
    public Byte count(Byte element) {
        return (byte) Integer.bitCount(element & 0xFF);
    }
}

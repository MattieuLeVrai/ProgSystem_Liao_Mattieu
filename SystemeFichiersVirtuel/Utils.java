public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {
        // TODO: Écrire les 4 octets de 'value' dans 'memory'
        // à partir de 'offset', en big-endian.
        memory[offset] = (byte)((value >> 24) & 0xFF);
        memory[offset + 1] = (byte)((value >> 16) & 0xFF);
        memory[offset + 2] = (byte)((value >> 8) & 0xFF);
        memory[offset + 3] = (byte)((value) & 0xFF);
        return 4;
    }

    public static int readInt(byte[] memory, int offset) {
        // TODO: Reconstituer le int sur 4 octets.
        return ((memory[offset]     & 0xFF) << 24) 
                | ((memory[offset + 1] & 0xFF) << 16) 
                | ((memory[offset + 2] & 0xFF) << 8)  
                | (memory[offset + 3] & 0xFF);
    }

    public static int writeShort(byte[] memory, int offset, short value) {
        // TODO: Écrire les 2 octets de 'value'.
        memory[offset] = (byte)((value >> 8) & 0xFF);
        memory[offset + 1] = (byte)(value & 0xFF);
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        // TODO: Lire le short sur 2 octets.
        return (short) (((memory[offset] & 0xFF) << 8) 
                         | (memory[offset + 1] & 0xFF));
    }

}
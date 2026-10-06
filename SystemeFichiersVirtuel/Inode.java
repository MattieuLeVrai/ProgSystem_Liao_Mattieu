public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode(
            MemoryManager memoryManager,
            int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    public int getInodeOffset() {
        // Calculer l'offset exact de l'inode.
        return memoryManager.INODE_TABLE_OFFSET + this.inodeNumber * INODE_SIZE;
    }

    public int getFileType() {
        // Lire le type à offset + 4.
        return getInodeOffset() + 4;
    }

    public int getFileSize() {
        // Lire la taille à offset + 8.
        return getInodeOffset() + 8;
    }

    public int[] getDirectPointers() {
        byte[] memory =
                memoryManager.getFilesystemMemory();
        int[] pointers =
                new int[DIRECT_POINTERS];

        // Lire les 10 pointeurs directs.
        int pointeurOffset = getInodeOffset() + 28;
        for(int i = 0 ; i < DIRECT_POINTERS; i++){
           pointers[i] = Utils.readInt(memory, pointeurOffset +(i * 4));
 
        }
        return pointers;
    }

    public void writeToMemory(
            int fileType,
            int fileSize,
            long creationTime,
            long modificationTime,
            int[] directPointers,
            int indirectPointer,
            short permissions,
            int linkCount) {

        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset();

        // 1. Numéro d'inode (4 octets)
        Utils.writeInt(memory, offset, this.inodeNumber);
        offset += 4;

        // 2. Type (4 octets)
        Utils.writeInt(memory, offset, fileType);
        offset += 4;
        
        // 3. Taille (4 octets)
        Utils.writeInt(memory, offset, fileSize);
        offset += 4;

        // 4. Date de création (8 octets)
        Utils.writeLong(memory, offset, creationTime);
        offset += 8;
        Utils.writeLong(memory, offset, modificationTime);
        offset += 8;
        for (int i = 0; i < 10; i++) {
            int ptr = (directPointers != null && i < directPointers.length) ? directPointers[i] : 0;
            Utils.writeInt(memory, offset, ptr);
            offset += 4;
        }

        // 7. Pointeur indirect (4 octets)
        Utils.writeInt(memory, offset, indirectPointer);
        offset += 4;

        // 8. Permissions (2 octets)
        Utils.writeShort(memory, offset, permissions);
        offset += 2;

        // 9. Nombre de liens (4 octets)
        Utils.writeInt(memory, offset, linkCount);
    }
}
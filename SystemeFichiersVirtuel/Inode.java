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
        byte[] memory = memoryManager.getFilesystemMemory();
        return Utils.readInt(memory, getInodeOffset() + 4);
    }

    public int getFileSize() {
        // Lire la taille à offset + 8.
        byte[] memory = memoryManager.getFilesystemMemory();
        return Utils.readInt(memory, getInodeOffset() + 8);
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

        // 1. Numéro d'inode (int, 4 octets)
        offset += Utils.writeInt(memory, offset, inodeNumber);

        // 2. Type (int, 4 octets)
        offset += Utils.writeInt(memory, offset, fileType);

        // 3. Taille (int, 4 octets)
        offset += Utils.writeInt(memory, offset, fileSize);

        // 4. Date de création (long, 8 octets)
        offset += Utils.writeLong(memory, offset, creationTime);

        // 5. Date de modification (long, 8 octets)
        offset += Utils.writeLong(memory, offset, modificationTime);

        // 6. Dix pointeurs directs (10 x 4 octets)
        for (int i = 0; i < DIRECT_POINTERS; i++) {
            int ptr = (directPointers != null && i < directPointers.length)
                    ? directPointers[i] : 0;
            offset += Utils.writeInt(memory, offset, ptr);
        }

        // 7. Pointeur indirect (int, 4 octets)
        offset += Utils.writeInt(memory, offset, indirectPointer);

        // 8. Permissions (short, 2 octets)
        offset += Utils.writeShort(memory, offset, permissions);

        // 9. Nombre de liens (int, 4 octets)
        offset += Utils.writeInt(memory, offset, linkCount);
    }

    public long getCreationTime() {
        return Utils.readLong(memoryManager.getFilesystemMemory(), getInodeOffset() + 12);
    }

    public int getIndirectPointer() {
        return Utils.readInt(memoryManager.getFilesystemMemory(), getInodeOffset() + 68);
    }

    public short getPermissions() {
        return Utils.readShort(memoryManager.getFilesystemMemory(), getInodeOffset() + 72);
    }

    public int getLinkCount() {
        return Utils.readInt(memoryManager.getFilesystemMemory(), getInodeOffset() + 74);
    }
}


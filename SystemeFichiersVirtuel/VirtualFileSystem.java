import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager =
                new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        // Parcourir les inodes de 0 à MAX_INODES - 1.
        // Identifier le premier inode libre.
        // Retourner son numéro.
        for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            Inode inode = new Inode(memoryManager, i);
            if (inode.getFileType() == 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean createFile(
            String directory,
            String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        // Construire l'inode.
        // L'initialiser comme fichier vide.
        Inode inode = new Inode(memoryManager, inodeNum);
        long temps = System.currentTimeMillis();

        inode.writeToMemory(1,0, temps, temps, new int[Inode.DIRECT_POINTERS], 0, (short) 0644, 1);         

        return true;

    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public boolean writeFile(
        int inodeNum,
        byte[] data) {

        int blocksNeeded =
                (data.length
                + MemoryManager.BLOCK_SIZE - 1)
                / MemoryManager.BLOCK_SIZE;

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            return false;
        }

        int[] blockPointers =
                new int[Inode.DIRECT_POINTERS];

        // TODO:
        // Allouer blocksNeeded blocs.
        Inode inode = new Inode(memoryManager, inodeNum);
        for (int i = 0; i < blocksNeeded; i++) {
            int block = memoryManager.allocateBlock();
            if (block == -1) {
                for (int j = 0; j < i; j++) {
                    memoryManager.setBlockUsed(blockPointers[j], false);
                }
                return false;
            }
            blockPointers[i] = block;
        }

        for (int oldBlock : inode.getDirectPointers()) {
            if (oldBlock != 0) {
                memoryManager.setBlockUsed(oldBlock, false);
            }
        }


        byte[] memory =
                memoryManager.getFilesystemMemory();

        int bytesRemaining =
                data.length;

        int dataSrcOffset = 0;

        // TODO:
        // Pour chaque bloc :
        // - calculer la quantité à copier ;
        // - récupérer le numéro du bloc ;
        // - calculer son offset physique ;
        // - copier les données.

        for (int i = 0; i < blocksNeeded; i++) {
            int aCopier = Math.min(MemoryManager.BLOCK_SIZE, bytesRemaining);
            int physicalOffset = blockPointers[i] * MemoryManager.BLOCK_SIZE;
            System.arraycopy(data, dataSrcOffset, memory, physicalOffset, aCopier);
            dataSrcOffset += aCopier;
            bytesRemaining -= aCopier;
        }
        // TODO:
        // Mettre à jour l'inode.
        inode.writeToMemory(1, data.length, inode.getCreationTime(),
                            System.currentTimeMillis(), blockPointers,
                            inode.getIndirectPointer(), inode.getPermissions(),
                            inode.getLinkCount());

        return true;
    }

    public byte[] readFile(int inodeNum) {

        Inode inode =
                new Inode(memoryManager, inodeNum);

        int fileSize =
                inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }

        byte[] fileData =
                new byte[fileSize];

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] blockPointers =
                inode.getDirectPointers();

        // TODO:
        // Parcourir les blocs utilisés.
        // Copier chaque fragment vers fileData.
        int bytesRemaining = fileSize;
        int destOffset = 0;

        for (int i = 0; i < Inode.DIRECT_POINTERS && bytesRemaining > 0; i++) {
            int toCopy = Math.min(MemoryManager.BLOCK_SIZE, bytesRemaining);
            int physicalOffset = blockPointers[i] * MemoryManager.BLOCK_SIZE;
            System.arraycopy(memory, physicalOffset, fileData, destOffset, toCopy);
            destOffset += toCopy;
            bytesRemaining -= toCopy;
        }   

        return fileData;
    }
}
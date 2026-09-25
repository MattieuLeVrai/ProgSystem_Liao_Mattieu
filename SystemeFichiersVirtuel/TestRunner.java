public class TestRunner{
        public static void testStep2() {
        System.out.println("=== TEST ÉTAPE 2 : Utils Entiers ===");

        byte[] buffer = new byte[32];

        int value = 0xF0A1B2E3;
        int written = Utils.writeInt(buffer, 3, value);

        assert written == 4 : "writeInt doit retourner 4";

        assert (buffer[3]  & 0xFF) == 0xF0 : "Octet 0 incorrect";
        assert (buffer[4]  & 0xFF) == 0xA1 : "Octet 1 incorrect";
        assert (buffer[5]  & 0xFF) == 0xB2 : "Octet 2 incorrect";
        assert (buffer[6]  & 0xFF) == 0xE3 : "Octet 3 incorrect";

        assert Utils.readInt(buffer, 3) == value :
                "Erreur writeInt / readInt";

        short shortValue = (short) 0xF0A1;
        int shortWritten = Utils.writeShort(buffer, 20, shortValue);

        assert shortWritten == 2 : "writeShort doit retourner 2";

        assert (buffer[20] & 0xFF) == 0xF0 :
                "Premier octet du short incorrect";

        assert (buffer[21] & 0xFF) == 0xA1 :
                "Deuxième octet du short incorrect";

        assert Utils.readShort(buffer, 20) == shortValue :
                "Erreur writeShort / readShort";

        System.out.println("[OK] Étape 2 validée !");
        }

        public static void main(String[] args){
                testStep2();
                testStep4();
        }


        public static void testStep4() {
                System.out.println("=== TEST ÉTAPE 4 : Initialisation Mémoire ===");

                MemoryManager mm = new MemoryManager();

                byte[] mem = mm.getFilesystemMemory();

                assert mem != null :
                        "La mémoire ne doit pas être nulle";

                assert mem.length == MemoryManager.TOTAL_MEMORY :
                        "Taille mémoire incorrecte";

                assert Utils.readString(
                        mem,
                        MemoryManager.SUPERBLOCK_OFFSET,
                        16).equals("MYFS1.0") :
                        "Signature du superbloc incorrecte";

                assert Utils.readInt(
                        mem,
                        MemoryManager.SUPERBLOCK_OFFSET + 16)
                        == MemoryManager.BLOCK_SIZE :
                        "Taille de bloc incorrecte";

                assert Utils.readInt(
                        mem,
                        MemoryManager.SUPERBLOCK_OFFSET + 20)
                        == MemoryManager.TOTAL_MEMORY :
                        "Taille mémoire incorrecte";

                assert Utils.readInt(
                        mem,
                        MemoryManager.SUPERBLOCK_OFFSET + 24)
                        == MemoryManager.NUM_BLOCKS :
                        "Nombre de blocs incorrect";

                assert Utils.readInt(
                        mem,
                        MemoryManager.SUPERBLOCK_OFFSET + 28)
                        == MemoryManager.MAX_INODES :
                        "Nombre maximal d'inodes incorrect";

                System.out.println("[OK] Étape 4 validée !");
        }         

}
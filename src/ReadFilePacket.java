/**
 * Created by user on 07/11/16.
 */
public class ReadFilePacket {
    public static String getProtocol(byte[] data) {
        byte[] proto = new byte[3];
        System.arraycopy(data, 0, proto, 0, proto.length);
        return new String(proto);
    }
    public static int getFileSize(byte[] data) {
        byte[] size = new byte[4];
        System.arraycopy(data, 3, size, 0, size.length);
        System.out.println("size of file is: " + java.nio.ByteBuffer.wrap(size).getInt());
        return java.nio.ByteBuffer.wrap(size).getInt();
    }
    public static String getFileName(byte[] data) {
        byte[] fileName = new byte[10];
        System.arraycopy(data, 7, fileName, 0, fileName.length);
        System.out.println("name of file is: " + new String(fileName));
        return new String(fileName);
    }
    public static byte[] getFileContent(byte[] data) {
        int fileSize = getFileSize(data);
        byte[] fileContent = new byte[fileSize];
        System.arraycopy(data, 17, fileContent, 0, fileContent.length);
        System.out.println("File content as string: " + new String(fileContent));
        return fileContent;
    }
}

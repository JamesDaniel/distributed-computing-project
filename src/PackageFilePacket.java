import java.nio.ByteBuffer;

/**
 * Created by user on 07/11/16.
 */
public class PackageFilePacket {
    public static byte[] packagedPacket(String protocol, int fileSize, String fileName, byte[] file) {
        byte[] protocolBytes = getBytesFromString(protocol);
        byte[] fileSizeBytes = getBytesFromInt(fileSize);
        byte[] fileNameBytes = getBytesFromString(resizeName(fileName));
        byte[] fileContentBytes = file;

        byte[] protocolAndFileSize = concatByteArrays(protocolBytes,fileSizeBytes);
        byte[] fileNameAndContent = concatByteArrays(fileNameBytes, fileContentBytes);

        return concatByteArrays(protocolAndFileSize, fileNameAndContent);
    }
    public static byte[] getBytesFromString(String string) {
        return string.getBytes();
    }
    public static byte[] getBytesFromInt(int integer) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(integer);
        return buffer.array();
    }
    public static byte[] concatByteArrays(byte[] byteArr1, byte[] byteArr2) {
        byte[] concatenatedByteArrays = new byte[byteArr1.length + byteArr2.length];
        System.arraycopy(byteArr1, 0, concatenatedByteArrays, 0, byteArr1.length);
        System.arraycopy(byteArr2, 0, concatenatedByteArrays, byteArr1.length, byteArr2.length);
        return concatenatedByteArrays;
    }
    public static String resizeName(String name) {
        if (name.length() > 10) {
            return name.substring(0, 10);
        }
        while (name.length() < 10) {
            name = name + " ";
        }
        return name;
    }
}

package utils;

import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static FloatBuffer toFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer toIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static CharSequence loadFromFile(String path) {
        StringBuilder builder = new StringBuilder();

        try (InputStream in = new FileInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load shader file " + path +
                    System.lineSeparator() + ex.getMessage());
        }
        CharSequence source = builder.toString();

        return source;
    }

    public static List<String> readAllLines(String path) {
        List<String> strings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return strings;
    }
}

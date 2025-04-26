package ru.spb.tksoft.advertising.tools;

import java.io.File;
import java.util.Optional;

public class FilesEx {

    public static final int MAX_FQN_LENGTH = 4096;

    private FilesEx() {}

    /**
     * Каким образом при создании уникального имени файла будет<br>
     * использоваться уникальная часть:<br>
     * - будет второй, после оригинального имени файла<br>
     * - будет первой, а затем будет идти оригинальное имя файла<br>
     * - будет единственной частью имени файла.
     */
    public enum UniqueFileNamePolicy {
        SALT_LAST, SALT_FIRST, SALT_ONLY
    }

    /**
     * @param filePath Path to a file in a form like "resource1://resource2/.../fileName.ext".
     * @param salt Resilting file name unique part.
     * @return Resulting unique file name.
     */
    public static Optional<String> buildUniqueFileName(String filePath,
            String salt, UniqueFileNamePolicy policy) {
        try {
            final String fileName = new File(filePath).getName();
            final String name = fileName.substring(0, fileName.lastIndexOf('.'));
            final String ext = fileName.substring(fileName.lastIndexOf('.'));
            return switch (policy) {
                case SALT_LAST -> Optional.of(name + salt + ext);
                case SALT_FIRST -> Optional.of(salt + name + ext);
                case SALT_ONLY -> Optional.of(salt + ext);
            };
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

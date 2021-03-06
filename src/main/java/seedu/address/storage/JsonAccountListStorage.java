package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAccountList;


/**
 * A class to access AccountList data stored as an Json file on the hard disk.
 */
public class JsonAccountListStorage implements AccountListStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonAccountListStorage.class);

    private final Path filePath;


    /**
     * Reused from https://github.com/se-edu/addressbook-level4 solutions
     * @param filePath
     */
    public JsonAccountListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAccountListFilePath() {
        return filePath;
    }


    @Override
    public Optional<ReadOnlyAccountList> readAccountList() throws DataConversionException, IOException {
        return readAccountList(filePath);
    }


    /**
     * Similar to {@link #readAccountList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAccountList> readAccountList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);


        if (!Files.exists(filePath)) {
            logger.info("AccountList file " + filePath + " not found");
            return Optional.empty();
        }

        Optional<JsonSerializableAccountList> jsonAccountList = JsonFileStorage.loadAccountListFromSaveFile(filePath);
        try {
            return Optional.of(jsonAccountList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAccountList(ReadOnlyAccountList accountList) throws Exception {
        saveAccountList(accountList, filePath);
    }

    /**
     * Similar to {@link #saveAccountList(ReadOnlyAccountList)}
     * @param filePath location of the data. Cannot be null
     * @throws Exception
     */
    public void saveAccountList(ReadOnlyAccountList accountList, Path filePath) throws Exception {
        requireNonNull(accountList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonFileStorage.saveAccountListToFile(filePath, new JsonSerializableAccountList(accountList));
    }

}

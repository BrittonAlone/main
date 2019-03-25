package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskBook extends Observable {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Task> getTaskList();
    ObservableList<String> getCategoryList();
    ObservableList<Double> getTimeList();
}

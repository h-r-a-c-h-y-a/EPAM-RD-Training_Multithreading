package am.epam.locks.stamped;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class BookStorage {

    private final Map<String, Book> books;

    private final StampedLock stampedLock;

    public BookStorage(Map<String, Book> books, StampedLock stampedLock) {
        this.books = books;
        this.stampedLock = stampedLock;
    }

    public void addBook(final String name, final Book book) {
        final long stamp = stampedLock.writeLock();
        try {
            books.put(name, book);
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    public Book getBook(final String name) {
        final long stamp = stampedLock.readLock();
        try {
            return books.get(name);
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public Collection<Book> getBooks() {
        long stamp = stampedLock.tryOptimisticRead();
        Collection<Book> values = books.values();
        try {
            if (!stampedLock.validate(stamp)) {
                stamp = stampedLock.readLock();
                return books.values();
            }
        } finally {
            stampedLock.unlockRead(stamp);
        }
        return values;
    }
}

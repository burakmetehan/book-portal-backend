package tr.com.obss.jip.springfinal.service;

import org.springframework.stereotype.Service;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.exception.addbookerror;
import tr.com.obss.jip.springfinal.repo.UserRepository;

@Service
public class BookListService {
    private final BookService bookService;
    private final UserService userService;

    private final UserRepository userRepository;

    public BookListService(BookService bookService, UserService userService, UserRepository userRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    public User addBookToReadList(long userId, long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        boolean isSuccessful = user.addBookToReadList(book);

        if (isSuccessful) {
            return userRepository.save(user);
        } else {
            throw new addbookerror("Error in adding book!");
        }
    }

    public User removeBookFromReadList(long userId, long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        boolean isSuccessful = user.removeBookFromReadList(book);

        if (isSuccessful) {
            return userRepository.save(user);
        } else {
            throw new addbookerror("Error in adding book!");
        }
    }

    public User addBookToFavoriteList(long userId, long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        boolean isSuccessful = user.addBookToFavoriteList(book);

        if (isSuccessful) {
            return userRepository.save(user);
        } else {
            throw new addbookerror("Error in adding book!");
        }
    }

    public User removeBookFromFavoriteList(long userId, long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        boolean isSuccessful = user.removeBookFromFavoriteList(book);

        if (isSuccessful) {
            return userRepository.save(user);
        } else {
            throw new addbookerror("Error in adding book!");
        }
    }
}

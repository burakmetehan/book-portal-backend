package tr.com.obss.jip.springfinal.service;

import org.springframework.stereotype.Service;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.repo.UserRepository;

@Service
public class AddRemoveService {
    private final BookService bookService;
    private final UserService userService;

    private final UserRepository userRepository;

    public AddRemoveService(BookService bookService, UserService userService, UserRepository userRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    public User addBookToReadList(long userId, long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        user.addBookToReadList(book);

        return userRepository.save(user);
    }

    public User removeBookFromReadList(long userId, long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        user.removeBookFromReadList(book);

        return userRepository.save(user);
    }

    public User addBookToFavoriteList(long userId, long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        user.addBookToFavoriteList(book);

        return userRepository.save(user);
    }

    public User removeBookFromFavoriteList(long userId, long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);

        user.removeBookFromFavoriteList(book);

        return userRepository.save(user);
    }
}

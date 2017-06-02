package ua.travel.service;

import org.apache.commons.codec.digest.DigestUtils;
import ua.travel.dao.repositories.impl.UserRepository;
import ua.travel.entity.User;
import ua.travel.service.exceptions.AuthException;
import ua.travel.service.exceptions.ServiceException;

/**
 * Created by yuuto on 5/26/17.
 */
public class UserService {

    private static UserService userService;
    private UserRepository userRepository = UserRepository.getInstance();
    private UserTypeService userTypeService = UserTypeService.getInstance();

    private UserService(){}

    public static UserService getInstance() {
        UserService localInstance = userService;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = userService;
                if (localInstance == null) {
                    userService = localInstance = new UserService();
                }
            }
        }
        return localInstance;
    }

    public User authUser(String login, String password) throws AuthException {
        User user = userRepository.findByLogin(login).orElseThrow(()->new AuthException("Cant find user by login: " + login));
        if(user.getPassword().equals(DigestUtils.md5Hex(password))){
            return user;
        } else{
            throw new AuthException(login, password);
        }
    }

    public User registerUser(String login, String password, String name, String surname, String phone) throws ServiceException {
        if(userRepository.findByLogin(login).isPresent()){
            throw new ServiceException("User already exists. Login: " + login);
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setUserType(userTypeService.createUserType("user"));
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setDiscount(0d);
        user.setId(userRepository.save(user));
        return user;
    }

    public User getUserById(String id) throws ServiceException {
        return userRepository.findById(Long.parseLong(id)).orElseThrow(()->new ServiceException("Cant find user by id: " + id));
    }
}

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
    private UserRepository userRepository = UserRepository.newInstance();
    private UserTypeService userTypeService = UserTypeService.newInstance();

    public static synchronized UserService newInstance() {
        if(userService == null){
            userService = new UserService();
        }
        return userService;
    }

    public User authUser(String login, String password) throws AuthException {
        User user = userRepository.findByLogin(login).orElseThrow(()->new AuthException("Cant find user by login: " + login));
        if(user.getPassword().equals(DigestUtils.md5Hex(password))){
            return user;
        } else{
            throw new AuthException(login, password);
        }
    }

    public User registerUser(String login, String password, String userType, String name, String surname, String phone) throws ServiceException {
        if(userRepository.findByLogin(login).isPresent()){
            throw new ServiceException("User already exists. Login: " + login);
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setUserType(userTypeService.createUserType(userType));
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setId(userRepository.save(user));
        return user;
    }
}

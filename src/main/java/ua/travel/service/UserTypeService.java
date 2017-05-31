package ua.travel.service;

import ua.travel.dao.repositories.impl.UserTypeRepository;
import ua.travel.entity.UserType;

import java.util.Optional;

/**
 * Created by yuuto on 5/26/17.
 */
public class UserTypeService {

    private static UserTypeService userTypeService;
    private UserTypeRepository userTypeRepository = UserTypeRepository.newInstance();

    public static synchronized UserTypeService newInstance() {
        if(userTypeService == null){
            userTypeService = new UserTypeService();
        }
        return userTypeService;
    }

    public UserType createUserType(String type){
        Optional<UserType> userTypeOptional = userTypeRepository.findByType(type);
        if(userTypeOptional.isPresent()){
            return userTypeOptional.get();
        } else {
            UserType userType = new UserType();
            userType.setType(type);
            userType.setId(userTypeRepository.save(userType));
            return userType;
        }
    }
}

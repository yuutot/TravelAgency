package ua.travel.service;

import ua.travel.dao.repositories.impl.UserTypeRepository;
import ua.travel.entity.UserType;

import java.util.Optional;

/**
 * Created by yuuto on 5/26/17.
 */
public class UserTypeService {

    private static UserTypeService userTypeService;
    private UserTypeRepository userTypeRepository = UserTypeRepository.getInstance();

    private UserTypeService(){}

    public static UserTypeService getInstance() {
        UserTypeService localInstance = userTypeService;
        if (localInstance == null) {
            synchronized (UserTypeService.class) {
                localInstance = userTypeService;
                if (localInstance == null) {
                    userTypeService = localInstance = new UserTypeService();
                }
            }
        }
        return localInstance;
    }

    /**
     * Check user status in db. If user status present return existing user status, else create new
     * @param type
     * @return user status
     */
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

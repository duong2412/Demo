package travel.converter.tranform;

import org.springframework.stereotype.Component;
import travel.converter.Converter;
import travel.entity.User;
import travel.model.request.UserRequest;
import travel.model.response.UserResponse;
import travel.util.BeanUtils;

@Component
public class UserTransformer implements Converter<User, UserRequest, UserResponse> {
    @Override
    public User convertToEntity(UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        BeanUtils.refine(user, response, BeanUtils::copyNonNull);
        return response;
    }
}

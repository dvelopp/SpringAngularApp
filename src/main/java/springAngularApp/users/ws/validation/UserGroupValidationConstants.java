package springAngularApp.users.ws.validation;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserGroupValidationConstants {

    //Fields
    public static final String USER_GROUP_NAME_FIELD = "name";

    //Messages
    public static final String USER_GROUP_NAME_IS_EMPTY_CODE = "user.group.validation.empty-name";
    public static final String USER_GROUP_NAME_EXISTS_CODE = "user.group.validation.user-group-exists";

}

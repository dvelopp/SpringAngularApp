package springAngularApp.users.ws;

public final class UserValidationConstants {

    //Fields
    public static final String USER_FIRST_NAME_FIELD = "firstName";
    public static final String USER_LAST_NAME_FIELD = "lastName";
    public static final String USER_GROUP_FIELD = "userGroupId";
    public static final String USER_NAME_FIELD = "userName";
    public static final String PASSWORD_FIELD = "password";

    //Messages
    public static final String FIRST_NAME_IS_EMPTY_CODE = "user.validation.empty-first-name";
    public static final String USER_GROUP_IS_EMPTY_CODE = "user.validation.empty-user-group";
    public static final String USER_NAME_IS_EMPTY_CODE = "user.validation.empty-name";
    public static final String LAST_NAME_IS_EMPTY_CODE = "user.validation.empty-last-name";
    public static final String PASSWORD_IS_EMPTY_CODE = "user.validation.empty-password";
    public static final String USER_NAME_EXISTS_CODE = "user.validation.user-exists";
    public static final String PASSWORD_IS_WEAK_CODE = "user.validation.weak-password";

    private UserValidationConstants() {
    }

}

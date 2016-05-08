package springAngularApp.configuration.ws.schema;

public class ConfigurationModelResponse {

    boolean hasUserViewAccess;
    boolean hasUserGroupViewAccess;

    public boolean isHasUserViewAccess() {
        return hasUserViewAccess;
    }

    public void setHasUserViewAccess(boolean hasUserViewAccess) {
        this.hasUserViewAccess = hasUserViewAccess;
    }

    public boolean isHasUserGroupViewAccess() {
        return hasUserGroupViewAccess;
    }

    public void setHasUserGroupViewAccess(boolean hasUserGroupViewAccess) {
        this.hasUserGroupViewAccess = hasUserGroupViewAccess;
    }
}

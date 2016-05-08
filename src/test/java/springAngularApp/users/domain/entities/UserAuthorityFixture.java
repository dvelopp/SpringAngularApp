package springAngularApp.users.domain.entities;


import org.apache.commons.lang3.RandomStringUtils;

public class UserAuthorityFixture {

    public static UserAuthority createUserAuthorityWithName(String name) {
        return builder().setName(name).build();
    }

    public static UserAuthority createDefaultUserAuthority() {
        return builder().build();
    }

    public static UserAuthorityBuilder builder() {
        return new UserAuthorityBuilder();
    }

    public static class UserAuthorityBuilder {

        private String name = "UserAuthorityName_"+ RandomStringUtils.random(10);

        public UserAuthorityBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserAuthority build() {
            return new UserAuthority(name);
        }

    }

}
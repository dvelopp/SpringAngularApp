package springAngularApp.users.domain.entities;


public class UserAuthorityFixture {

    public static UserAuthority createDefaultUserAuthority() {
        return builder().build();
    }

    public static UserAuthorityBuilder builder() {
        return new UserAuthorityBuilder();
    }

    public static class UserAuthorityBuilder {
        private String name = "UserAuthorityName";

        public UserAuthorityBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserAuthority build() {
            return new UserAuthority(name);
        }
    }

}
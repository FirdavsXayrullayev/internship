package uz.intership.securityJwt;

public enum UserPermission {
    READ("READ"),
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String name;


    UserPermission(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}

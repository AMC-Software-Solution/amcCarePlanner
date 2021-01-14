package com.amc.careplanner.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String CARE_PROVIDER = "ROLE_CARE_PROVIDER";
    
    public static final String TEAM_LEADER = "ROLE_TEAM_LEADER";
    
    public static final String MANAGER = "ROLE_MANAGER";
    
    public static final String COMPANY_ADMIN = "ROLE_COMPANY_ADMIN";
    

    private AuthoritiesConstants() {
    }
}

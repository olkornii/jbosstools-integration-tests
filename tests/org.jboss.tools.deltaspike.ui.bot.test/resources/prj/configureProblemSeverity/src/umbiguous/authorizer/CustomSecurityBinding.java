package umbiguous.authorizer;

import org.apache.deltaspike.security.api.authorization.SecurityBindingType;

@SecurityBindingType
public @interface CustomSecurityBinding {
    int value() default 0;
}



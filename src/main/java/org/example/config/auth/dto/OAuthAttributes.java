package org.example.config.auth.dto;


import lombok.Builder;
import lombok.Getter;
import org.example.domain.user.Role;
import org.example.domain.user.User;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private String name;
    private String email;
    private String picture;
    private String nameAttributeKey;
    private Map<String, Object> attributes;

    @Builder
    public OAuthAttributes(String name, String email, String picture, String nameAttributeKey, Map<String, Object> attributes) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.nameAttributeKey = nameAttributeKey;
        this.attributes = attributes;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(
        String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes).build();
    }

    public User toEntity(){
        return User.builder()
                .name(name).email(email).picture(picture).role(Role.GUEST).build();
    }

}

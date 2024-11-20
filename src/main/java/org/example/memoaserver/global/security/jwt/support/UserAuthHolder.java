
package org.example.memoaserver.global.security.jwt.support;

import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.global.security.jwt.details.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthHolder {
    public UserEntity current() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userEntity();
    }
}

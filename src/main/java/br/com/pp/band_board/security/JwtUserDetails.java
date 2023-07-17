package br.com.pp.band_board.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * Extension of Spring Security User class to store additional data.
 */
public class JwtUserDetails extends User {

    public final Long idUser;

    public JwtUserDetails(final Long idUser, final String username, final String hash, final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.idUser = idUser;
    }

}

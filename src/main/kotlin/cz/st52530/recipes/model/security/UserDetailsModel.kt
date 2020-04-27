package cz.st52530.recipes.model.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsModel(
        private val _username: String,
        private val _password: String
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = _username

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = _password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}
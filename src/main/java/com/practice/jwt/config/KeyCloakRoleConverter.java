package com.practice.jwt.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeyCloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	@Override
	public Collection<GrantedAuthority> convert(Jwt source) {
		Map<String, Object> realmAccessMap = (Map<String, Object>) source.getClaims().get("realm_access");
		if (null == realmAccessMap || realmAccessMap.isEmpty()) {
			return new ArrayList<>();
		}
		Collection<GrantedAuthority> grantedAuthorities = ((List<String>) realmAccessMap.get("roles")).stream()
				.map(role -> "ROLE_" + role).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		return grantedAuthorities;
	}

}

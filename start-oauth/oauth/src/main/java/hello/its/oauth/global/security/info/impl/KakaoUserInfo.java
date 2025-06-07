package hello.its.oauth.global.security.info.impl;

import java.util.Map;
import java.util.Optional;

import hello.its.oauth.global.security.constants.OAuthProvider;
import hello.its.oauth.global.security.info.OAuthUserInfo;

public class KakaoUserInfo extends OAuthUserInfo {

	private final OAuthProvider oAuthProvider = OAuthProvider.KAKAO;

	public KakaoUserInfo(Map<String, Object> attribute) {
		super(attribute);
	}

	@Override
	public OAuthProvider getOAuthProvider() {
		return oAuthProvider;
	}

	@Override
	public String getProviderIdentifier() {
		return String.valueOf(this.attribute.get("id"));
	}

	@Override
	public String getEmail() {
		return Optional.ofNullable(this.attribute.get("kakao_account"))
			.filter(Map.class::isInstance)
			.map(Map.class::cast)
			.map(kakaoAccount -> kakaoAccount.get("email"))
			.filter(String.class::isInstance)
			.map(String.class::cast)
			.orElse(null);
	}

	@Override
	public String getProfileImage() {
		return Optional.ofNullable(this.attribute.get("properties"))
			.filter(Map.class::isInstance)
			.map(Map.class::cast)
			.map(properties -> properties.get("profile_image"))
			.filter(String.class::isInstance)
			.map(String.class::cast)
			.orElse(null);
	}

	@Override
	public String getName() {
		return Optional.ofNullable(this.attribute.get("properties"))
			.filter(Map.class::isInstance)
			.map(Map.class::cast)
			.map(properties -> properties.get("nickname"))
			.filter(String.class::isInstance)
			.map(String.class::cast)
			.orElse(null);
	}
}

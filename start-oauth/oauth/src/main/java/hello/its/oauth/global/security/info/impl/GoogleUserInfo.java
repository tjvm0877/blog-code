package hello.its.oauth.global.security.info.impl;

import java.util.Map;

import hello.its.oauth.global.security.constants.OAuthProvider;
import hello.its.oauth.global.security.info.OAuthUserInfo;

public class GoogleUserInfo extends OAuthUserInfo {

	private final OAuthProvider providerCode = OAuthProvider.GOOGLE;

	public GoogleUserInfo(Map<String, Object> attribute) {
		super(attribute);
	}

	@Override
	public OAuthProvider getOAuthProvider() {
		return providerCode;
	}

	@Override
	public String getProviderIdentifier() {
		return (String)this.attribute.get("sub");
	}

	@Override
	public String getEmail() {
		return (String)this.attribute.get("email");
	}

	@Override
	public String getProfileImage() {
		return (String)this.attribute.get("picture");
	}

	@Override
	public String getName() {
		return (String)this.attribute.get("name");
	}
}

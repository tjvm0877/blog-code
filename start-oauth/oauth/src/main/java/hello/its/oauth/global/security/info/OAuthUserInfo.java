package hello.its.oauth.global.security.info;

import java.util.Map;

import hello.its.oauth.global.security.constants.OAuthProvider;

public abstract class OAuthUserInfo {

	protected Map<String, Object> attribute;

	public OAuthUserInfo(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	public abstract OAuthProvider getOAuthProvider();

	public abstract String getProviderIdentifier();

	public abstract String getEmail();

	public abstract String getProfileImage();

	public abstract String getName();
}

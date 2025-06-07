package hello.its.oauth.global.security.info;

import java.util.Map;

import hello.its.oauth.global.security.constants.OAuthProvider;
import hello.its.oauth.global.security.info.impl.GoogleUserInfo;
import hello.its.oauth.global.security.info.impl.KakaoUserInfo;

public class OAuthUserInfoFactory {

	public static OAuthUserInfo getOAuthUserInfo(OAuthProvider provider, Map<String, Object> attribute) {
		switch (provider) {
			case KAKAO -> {
				return new KakaoUserInfo(attribute);
			}
			case GOOGLE -> {
				return new GoogleUserInfo(attribute);
			}
		}
		throw new IllegalArgumentException("Invalid provider");
	}
}

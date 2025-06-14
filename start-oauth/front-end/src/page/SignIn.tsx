import { Box, Button, CircularProgress } from '@mui/material';
import Container from '../components/Container';
import Card from '../components/Card';
import { GoogleIcon, KakaoIcon } from '../components/CustomIcon';
import { useState } from 'react';

type OAuthProvider = 'google' | 'kakao';

const API_BASE_URL = 'http://localhost:8080';

const SignIn = () => {
  const [loading, setLoading] = useState<boolean>(false);

  const handleOAuthLogin = async (provider: OAuthProvider): Promise<void> => {
    try {
      setLoading(true);

      // OAuth 제공자별 인증 엔드포인트 구성
      // - /oauth2/authorization/{provider}: Spring Security의 기본 OAuth2 인증 경로
      const authUrl = `${API_BASE_URL}/oauth2/authorization/${provider}`;

      // 브라우저 리다이렉트를 통한 OAuth 인증 시작
      // - 사용자를 선택한 제공자의 로그인 페이지로 이동시킴
      window.location.href = authUrl;
    } catch (error) {
      console.error(`${provider} 로그인 실패:`, error);
      setLoading(false);
    }
  };

  const handleGoogleLogin = (): Promise<void> => handleOAuthLogin('google');
  const handleKakaoLogin = (): Promise<void> => handleOAuthLogin('kakao');

  if (loading) {
    return (
      <Container direction={'column'} justifyContent={'center'}>
        <CircularProgress />
      </Container>
    );
  }

  return (
    <Container direction={'column'} justifyContent={'space-between'}>
      <Card variant="outlined">
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
          <Button
            fullWidth
            variant="outlined"
            onClick={handleGoogleLogin}
            startIcon={<GoogleIcon />}
            style={{ textTransform: 'none' }}
          >
            Sign in with Google
          </Button>
          <Button
            fullWidth
            variant="outlined"
            onClick={handleKakaoLogin}
            startIcon={<KakaoIcon />}
            sx={{
              textTransform: 'none',
              backgroundColor: '#FEE500',
              color: 'rgba(0, 0, 0, 0.85)',
              border: 'none',
              '&:hover': {
                backgroundColor: '#FEE500',
                opacity: 0.9,
              },
            }}
          >
            Sign in with Kakao
          </Button>
        </Box>
      </Card>
    </Container>
  );
};

export default SignIn;

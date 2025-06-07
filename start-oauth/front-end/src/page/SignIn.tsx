import { Box, Button, CircularProgress } from '@mui/material';
import Container from '../components/Container';
import Card from '../components/Card';
import {
  GithubIcon,
  GoogleIcon,
  KakaoIcon,
  NaverIcon,
} from '../components/CustomIcon';
import { useState } from 'react';

const SignIn = () => {
  const [loading, setLoading] = useState(false);

  const handleGoogleLogin = async () => {
    try {
      setLoading(true);
      window.location.href =
        'http://localhost:8080/oauth2/authorization/google';
    } catch (e) {
      console.log(e);
    }
  };

  const handlerKakaoLogin = async () => {
    try {
      setLoading(true);
      window.location.href = 'http://localhost:8080/oauth2/authorization/kakao';
    } catch (e) {
      console.log(e);
    }
  };

  if (loading) {
    return <CircularProgress />;
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
            onClick={handlerKakaoLogin}
            startIcon={<KakaoIcon />}
            style={{ textTransform: 'none' }}
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
          <Button
            fullWidth
            variant="outlined"
            onClick={() => alert('Sign in with Naver')}
            startIcon={<NaverIcon />}
            sx={{
              textTransform: 'none',
              backgroundColor: '#03C75A',
              color: '#fff',
              border: 'none',
              '&:hover': {
                backgroundColor: '#03C75A',
                opacity: 0.8,
              },
            }}
          >
            Sign in with Naver
          </Button>
          <Button
            fullWidth
            variant="outlined"
            onClick={() => alert('Sign with Naver')}
            startIcon={<GithubIcon />}
            sx={{
              textTransform: 'none',
              backgroundColor: '#000',
              color: '#fff',
              border: 'none',
              '&:hover': {
                backgroundColor: '#000',
                opacity: 0.8,
              },
            }}
          >
            Sign in with Github
          </Button>
        </Box>
      </Card>
    </Container>
  );
};

export default SignIn;

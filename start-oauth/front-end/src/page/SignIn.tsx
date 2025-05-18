import {
  Box,
  Button,
  Divider,
  FormControl,
  FormLabel,
  Link,
  TextField,
} from '@mui/material';
import { Typography } from '@mui/material';
import Container from '../components/Container';
import Card from '../components/Card';
import { GoogleIcon, KakaoIcon, NaverIcon } from '../components/CustomIcon';

const SignIn = () => {
  return (
    <Container direction={'column'} justifyContent={'space-between'}>
      <Card variant="outlined">
        <Typography
          component="h1"
          variant="h4"
          sx={{ width: '100%', fontSize: 'clamp(2rem, 10vw, 2.15rem)' }}
        >
          Sign in
        </Typography>
        <Box
          component="form"
          // onSubmit={handleSubmit}
          noValidate
          sx={{
            display: 'flex',
            flexDirection: 'column',
            width: '100%',
            gap: 2,
          }}
        >
          <FormControl>
            <FormLabel htmlFor="email">Email</FormLabel>
            <TextField
              // error={emailError}
              // helperText={emailErrorMessage}
              id="email"
              type="email"
              name="email"
              placeholder="your@email.com"
              autoComplete="email"
              autoFocus
              required
              fullWidth
              variant="outlined"
              // color={emailError ? 'error' : 'primary'}
            />
          </FormControl>
          <FormControl>
            <FormLabel htmlFor="password">Password</FormLabel>
            <TextField
              // error={passwordError}
              // helperText={passwordErrorMessage}
              name="password"
              placeholder="••••••"
              type="password"
              id="password"
              autoComplete="current-password"
              autoFocus
              required
              fullWidth
              variant="outlined"
              // color={passwordError ? 'error' : 'primary'}
            />
          </FormControl>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            // onClick={validateInputs}
            style={{ textTransform: 'none' }}
          >
            Sign in
          </Button>
        </Box>
        <Divider>or</Divider>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
          <Button
            fullWidth
            variant="outlined"
            onClick={() => alert('Sign in with Google')}
            startIcon={<GoogleIcon />}
            style={{ textTransform: 'none' }}
          >
            Sign in with Google
          </Button>
          <Button
            fullWidth
            variant="outlined"
            onClick={() => alert('Sign in with Kakao')}
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
                opacity: 0.9,
              },
            }}
          >
            Sign in with Naver
          </Button>
          <Typography sx={{ textAlign: 'center' }}>
            Don&apos;t have an account?{' '}
            <Link href="/sign-up" variant="body2" sx={{ alignSelf: 'center' }}>
              Sign up
            </Link>
          </Typography>
        </Box>
      </Card>
    </Container>
  );
};

export default SignIn;

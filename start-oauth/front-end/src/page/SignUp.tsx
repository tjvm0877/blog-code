import {
  Box,
  Button,
  FormControl,
  FormLabel,
  TextField,
  Typography,
} from '@mui/material';
import Card from '../components/Card';
import Container from '../components/Container';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { signUp } from '../api/member';
import { useAuthStore } from '../store/useAuthStore';

const SignUp = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const setToken = useAuthStore((state) => state.setAccessToken);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const formData = new FormData(event.currentTarget);
    const nickname = formData.get('nickname')?.toString().trim();

    if (!nickname) {
      alert('닉네임을 입력해주세요.');
      return;
    }
    if (!token) {
      alert('유효하지 않은 접근입니다.');
      return;
    }

    try {
      const response = await signUp(token, nickname);
      if (response?.accessToken) {
        setToken(response.accessToken);
        navigate('/info'); // 회원가입 성공 후 이동
      } else {
        alert('회원가입에 실패했습니다.');
      }
    } catch (error) {
      alert('회원가입 중 오류가 발생했습니다.');
      console.log(error);
    }
  };

  return (
    <Container direction={'column'} justifyContent={'space-between'}>
      <Card variant="outlined">
        <Typography
          component={'h1'}
          variant="h4"
          sx={{ width: '100%', fontSize: 'clamp(2rem, 10vw, 2.15rem)' }}
        >
          Sign up
        </Typography>
        <Box
          component="form"
          onSubmit={handleSubmit}
          noValidate
          sx={{
            display: 'flex',
            flexDirection: 'column',
            width: '100%',
            gap: 2,
          }}
        >
          <FormControl>
            <FormLabel htmlFor="nickname">Nickname</FormLabel>
            <TextField
              id="nickname"
              type="text"
              name="nickname"
              placeholder="Your nickname"
              autoComplete="nickname"
              autoFocus
              required
              fullWidth
              variant="outlined"
            />
          </FormControl>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            style={{ textTransform: 'none' }}
          >
            Sign up
          </Button>
          <Button
            fullWidth
            variant="outlined"
            onClick={() => navigate('/')}
            style={{ textTransform: 'none' }}
          >
            Cancel
          </Button>
        </Box>
      </Card>
    </Container>
  );
};

export default SignUp;

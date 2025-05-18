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
import { useNavigate } from 'react-router-dom';

const SignUp = () => {
  const navigate = useNavigate();

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
          component={'form'}
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
          <FormControl>
            <FormLabel htmlFor="password-check">Password Check</FormLabel>
            <TextField
              // error={passwordError}
              // helperText={passwordErrorMessage}
              name="password-check"
              placeholder="••••••"
              type="password"
              id="password-check"
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

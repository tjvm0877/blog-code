import { CircularProgress } from '@mui/material';
import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { exchangeTemporaryToken } from '../api/auth';
import { useAuthStore } from '../store/useAuthStore';

const Callback = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const token = searchParams.get('token');

    const handleTokenExchange = async (tempToken: string) => {
      try {
        const { accessToken } = await exchangeTemporaryToken(tempToken);
        useAuthStore.getState().setAccessToken(accessToken);
        navigate('/info', { replace: true });
      } catch (error) {
        console.error('토큰 교환 실패', error);
        navigate('/', { replace: true });
      }
    };

    if (token) {
      handleTokenExchange(token);
    } else {
      navigate('/sign-in', { replace: true });
    }
  }, [searchParams, navigate]);

  return <CircularProgress />;
};

export default Callback;

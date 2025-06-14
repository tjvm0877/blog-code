import { CircularProgress } from '@mui/material';
import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { exchangeTemporaryToken } from '../api/auth';
import { useAuthStore } from '../store/useAuthStore';

const Callback = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    // URL에서 임시 토큰 추출 (서버에서 리다이렉트 시 전달)
    const token = searchParams.get('token');

    const handleTokenExchange = async (tempToken: string) => {
      try {
        // 1. 임시 토큰 검증 및 액세스 토큰 발급 요청
        // - exchangeTemporaryToken: Axios를 이용한 API 호출 함수
        // - 서버 내부에서 Authorization Code ↔ Access Token 교환 수행
        const { accessToken } = await exchangeTemporaryToken(tempToken);

        // 2. 상태 관리(전역 상태/Zustand)에 액세스 토큰 저장
        useAuthStore.getState().setAccessToken(accessToken);

        // 3. 메인 페이지로 강제 리다이렉트 (히스토리 교체)
        // - replace: true → 콜백 페이지를 히스토리에서 제거 (뒤로가기 방지)
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

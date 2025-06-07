import {
  Box,
  Typography,
  CircularProgress,
  Alert,
  Button,
  Avatar,
} from '@mui/material';
import Card from '../components/Card';
import Container from '../components/Container';
import { useEffect, useState } from 'react';
import { useAuthStore } from '../store/useAuthStore';
import { getMemberInfo } from '../api/member';
import { useNavigate } from 'react-router-dom';

interface MemberInfo {
  email: string;
  profile: string;
  name: string;
}

const Info = () => {
  const navigate = useNavigate();
  const accessToken = useAuthStore((state) => state.accessToken);
  const [memberInfo, setMemberInfo] = useState<MemberInfo | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>('');

  useEffect(() => {
    const fetchMemberInfo = async () => {
      // 토큰이 없으면 로그인 페이지로 리다이렉트
      if (!accessToken) {
        navigate('/');
        return;
      }

      try {
        setLoading(true);
        setError('');
        const data = await getMemberInfo(accessToken);
        setMemberInfo(data);
      } catch (error) {
        console.error('Failed to fetch member info:', error);
        setError('사용자 정보를 불러오는데 실패했습니다.');

        // 토큰이 만료되었거나 유효하지 않은 경우 로그인 페이지로 리다이렉트
        if (error instanceof Error && error.message.includes('401')) {
          navigate('/');
        }
      } finally {
        setLoading(false);
      }
    };

    fetchMemberInfo();
  }, [accessToken, navigate]); // accessToken과 navigate를 의존성 배열에 추가

  // 로딩 상태
  if (loading) {
    return (
      <Container direction={'column'} justifyContent={'center'}>
        <CircularProgress />
      </Container>
    );
  }

  // 에러 상태
  if (error) {
    return (
      <Container direction={'column'} justifyContent={'center'}>
        <Alert severity="error">{error}</Alert>
      </Container>
    );
  }

  // 데이터가 없는 경우
  if (!memberInfo) {
    return (
      <Container direction={'column'} justifyContent={'center'}>
        <Alert severity="info">사용자 정보가 없습니다.</Alert>
      </Container>
    );
  }

  return (
    <Container direction={'column'} justifyContent={'space-between'}>
      <Card variant="outlined">
        <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2 }}>
          <Avatar src={memberInfo.profile}>
            {!memberInfo.profile && memberInfo.name[0]}
          </Avatar>
          <Box>
            <Box>
              <Typography fontWeight="bold">{memberInfo.name}</Typography>
              <Typography variant="caption" color="text.secondary">
                {memberInfo.email}
              </Typography>
            </Box>
          </Box>
        </Box>
        <Button
          fullWidth
          variant="contained"
          onClick={() => navigate('/')}
          style={{ textTransform: 'none' }}
        >
          Back to main
        </Button>
      </Card>
    </Container>
  );
};

export default Info;

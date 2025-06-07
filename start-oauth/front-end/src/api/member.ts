import { apiClient } from './axiosInstance';

interface MemberInfoResponse {
  email: string;
  name: string;
  profile: string;
}

interface signUpResponse {
  accessToken: string;
}

export const getMemberInfo = async (
  accessToken: string
): Promise<MemberInfoResponse> => {
  try {
    const response = await apiClient.get<MemberInfoResponse>('/members', {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error('Member info request failed:', error);
    throw new Error('api 요청 실패');
  }
};

export const signUp = async (
  tempoaryToken: string,
  nickName: string
): Promise<signUpResponse> => {
  try {
    const response = await apiClient.post<signUpResponse>('/members/sign-up', {
      token: tempoaryToken,
      nickName: nickName,
    });
    return response.data;
  } catch (error) {
    console.error('Sign up failed:', error);
    throw new Error('api 요청 실패');
  }
};

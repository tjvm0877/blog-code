import { apiClient } from './axiosInstance';

interface TokenExchangeRequest {
  token: string;
}

interface TokenExchangeResponse {
  accessToken: string;
}

export const exchangeTemporaryToken = async (
  temporaryToken: string
): Promise<TokenExchangeResponse> => {
  try {
    const requestData: TokenExchangeRequest = {
      token: temporaryToken,
    };

    const response = await apiClient.post<TokenExchangeResponse>(
      '/auth',
      requestData
    );

    return response.data;
  } catch (error) {
    console.error('Token exchange failed:', error);
    throw new Error('api 요청 실패');
  }
};

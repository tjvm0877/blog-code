import type { AxiosInstance } from 'axios';
import axios from 'axios';

const createAxiosInstance = (): AxiosInstance => {
  const instance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 1000,
  });

  instance.interceptors.request.use(
    (config) => {
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    (response) => {
      return response;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  return instance;
};

export const apiClient = createAxiosInstance();

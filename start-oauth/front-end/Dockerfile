FROM node:20-alpine

WORKDIR /app

# package.json과 yarn.lock을 먼저 복사하여 의존성 설치
COPY package.json yarn.lock ./
RUN yarn install

# 전체 소스 코드 복사
COPY . .

# 3000번 포트 노출
EXPOSE 3000

# Vite 개발 서버를 호스트 0.0.0.0에서 실행
CMD ["yarn", "dev", "--host", "0.0.0.0", "--port", "3000"]

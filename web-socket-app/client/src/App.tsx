import { Box, Button, Card, Stack, TextField, Typography } from '@mui/material';
import './App.css';
import { useRef, useState } from 'react';
import { grey } from '@mui/material/colors';
import ChatMessage from './ChatMessage';

const containerStyle = {
  minHeight: '100vh',
  padding: '1em',
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',
  alignItems: 'center',
  background:
    'radial-gradient(ellipse at 50% 50%, hsl(210, 100%, 97%), hsl(0, 0%, 100%))',
};

const cardStyle = {
  display: 'flex',
  flexDirection: 'column',
  width: '100%',
  maxWidth: '28em',
  padding: '2em',
  gap: '1.5em',
  boxShadow: '0 5px 15px rgba(0,0,0,0.1)',
  borderRadius: '12px',
  backgroundColor: '#fff',
};

const ChatListContainer = {
  display: 'flex',
  flexDirection: 'column',
  height: '50vh',
  width: '100%',
  backgroundColor: grey[100],
  overflowY: 'auto',
  borderRadius: '8px',
  boxShadow: 'inset 0 0 5px rgba(0,0,0,0.05)',
};

const inputContainerStyle = {
  display: 'flex',
  gap: '1em',
  marginTop: '1em',
};

const urlInputStyle = {
  flexGrow: 1,
};

const messageInputStyle = {
  flexGrow: 1,
};

export interface Message {
  type: 'SENT' | 'RECEIVED';
  content: string;
  timestamp: Date;
}

function App() {
  const [messages, setMessages] = useState<Message[]>([]);
  const [url, setUrl] = useState('');
  const [isConnected, setIsConnected] = useState(false);
  const websocket = useRef<WebSocket>(null);
  const [messageInput, setMessageInput] = useState<string>('');
  const addMessage = (message: Message) => {
    setMessages((prev) => [...prev, message]);
  };

  const onUrlInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUrl(e.target.value);
  };

  const onMessageInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMessageInput(e.target.value);
  };

  const handleSendMessage = () => {
    if (!isConnected || websocket.current == null || messageInput === '') {
      return;
    }
    addMessage({
      type: 'SENT',
      content: messageInput,
      timestamp: new Date(),
    });
    websocket.current.send(messageInput);
    setMessageInput('');
  };

  const handleConnectButton = () => {
    if (url == '') {
      return;
    }

    if (websocket.current && websocket.current.readyState === WebSocket.OPEN) {
      websocket.current.close();
      setIsConnected(false);
      return;
    }

    // WebSocket 객체를 생성하고 서버에 연결을 시도
    const ws = new WebSocket(url);
    websocket.current = ws;

    // WebSocket 연결이 성공적으로 열렸을 때 실행되는 이벤트 핸들러
    ws.onopen = () => {
      setIsConnected(true);
      console.log('연결 성공');
    };

    // 서버로부터 메시지를 수신했을 때 실행되는 이벤트 핸들러
    ws.onmessage = (event) => {
      console.log(event);
      addMessage({
        type: 'RECEIVED',
        content: event.data,
        timestamp: new Date(),
      });
    };

    // WebSocket 연결이 종료되었을 때 실행되는 이벤트 핸들러
    ws.onclose = () => {
      setIsConnected(false);
      console.log('연결 종료');
    };

    // WebSocket에서 에러가 발생했을 때 실행되는 이벤트 핸들러
    ws.onerror = () => {
      console.log('에러 발생');
    };
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSendMessage();
    }
  };

  return (
    <Stack sx={containerStyle}>
      <Card variant="outlined" sx={cardStyle}>
        <Box sx={{ display: 'flex', gap: '1em', marginBottom: '1em' }}>
          <TextField
            placeholder="WebSocket URL"
            variant="outlined"
            size="small"
            sx={urlInputStyle}
            onChange={onUrlInputChange}
            value={url}
          />
          <Button
            variant={!isConnected ? 'contained' : 'outlined'}
            size="medium"
            sx={{ minWidth: '6em' }}
            onClick={handleConnectButton}
          >
            {!isConnected ? 'Connect' : 'Disconnect'}
          </Button>
        </Box>

        <Box sx={ChatListContainer}>
          {messages.length === 0 ? (
            <Typography
              variant="body2"
              color="textSecondary"
              align="center"
              sx={{ marginTop: '2em' }}
            >
              No messages yet
            </Typography>
          ) : (
            messages.map((msg, index) => (
              <ChatMessage key={index} message={msg} />
            ))
          )}
        </Box>
        <Box sx={inputContainerStyle}>
          <TextField
            placeholder="Type your message..."
            variant="outlined"
            size="small"
            value={messageInput}
            onChange={onMessageInputChange}
            onKeyUp={handleKeyPress}
            sx={messageInputStyle}
          />
          <Button
            variant="contained"
            size="medium"
            onClick={handleSendMessage}
            disabled={!isConnected}
            sx={{ minWidth: '6em' }}
          >
            Send
          </Button>
        </Box>
      </Card>
    </Stack>
  );
}

export default App;

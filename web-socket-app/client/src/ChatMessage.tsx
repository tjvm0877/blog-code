import { Box, Typography } from '@mui/material';
import { blue, grey } from '@mui/material/colors';
import { Message } from './App';

interface ChatMessageProps {
  message: Message;
}

const ChatMessage = ({ message }: ChatMessageProps) => {
  const getMessageStyles = () => {
    switch (message.type) {
      case 'SENT':
        return {
          backgroundColor: blue[500],
          color: '#fff',
        };
      case 'RECEIVED':
        return {
          backgroundColor: grey[300],
          color: '#000',
        };
      default:
        return {};
    }
  };

  const formatTimeHHMM = (date: Date): string => {
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes}`;
  };

  return (
    <Box
      sx={{
        display: 'flex',
        alignItems: 'center',
        gap: 1,
        padding: 1,
        maxWidth: '80%',
        alignSelf: message.type === 'SENT' ? 'flex-end' : 'flex-start',
        flexDirection: message.type === 'SENT' ? 'row-reverse' : 'row',
      }}
    >
      <Box
        sx={{
          maxWidth: '70%',
          padding: 1.5,
          borderRadius: 3,
          ...getMessageStyles(),
        }}
      >
        <Typography variant="body2">{message.content}</Typography>
      </Box>
      <Typography
        variant="caption"
        sx={{
          color: grey[500],
          whiteSpace: 'nowrap',
          alignSelf: 'flex-end',
          paddingBottom: 0.5,
        }}
      >
        {formatTimeHHMM(message.timestamp)}
      </Typography>
    </Box>
  );
};

export default ChatMessage;

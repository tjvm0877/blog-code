import { createRoot } from 'react-dom/client';
import App from './App.tsx';

// @ts-expect-error: bootstrap css has no type declarations
import 'bootstrap/dist/css/bootstrap.min.css';

createRoot(document.getElementById('root')!).render(<App />);

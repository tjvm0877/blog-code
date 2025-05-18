import { Route, Routes } from 'react-router-dom';
import SignIn from './page/SignIn';
import SignUp from './page/SignUp';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<SignIn />} />
        <Route path="/sign-up" element={<SignUp />} />
      </Routes>
    </>
  );
}

export default App;

import { Route, Routes } from 'react-router-dom';
import SignIn from './page/SignIn';
import SignUp from './page/SignUp';
import Callback from './page/Callback';
import Info from './page/Info';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<SignIn />} />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/callback" element={<Callback />} />
        <Route path="/info" element={<Info />}></Route>
      </Routes>
    </>
  );
}

export default App;

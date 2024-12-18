import { BrowserRouter, Routes, Route } from "react-router-dom";
import LogIn from "./Forms/LogIn";
import SignUp from "./Forms/SignUp";

function App() {
  return (
    <div className="rel w-full h-screen flex items-center justify-center">
      <BrowserRouter>
        <Routes>
          <Route path="/signUp" element={<SignUp />} />
          <Route path="/logIn" element={<LogIn />} />
          <Route path="/" element={<LogIn />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

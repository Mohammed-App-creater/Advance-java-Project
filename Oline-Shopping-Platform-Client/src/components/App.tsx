import { BrowserRouter, Routes, Route } from "react-router-dom";
import LogIn from "./Forms/LogIn";
import SignUp from "./Forms/SignUp";
import LandingPage from "./LadingPage";
import ProductCard from "./ProductCard";

function App() {
  return (
    <div className="rel w-full h-screen flex items-center justify-center">
      <BrowserRouter>
        <Routes>
          <Route path="/signUp" element={<SignUp />} />
          <Route path="/logIn" element={<LogIn />} />
          <Route path="/" element={<ProductCard />} />
          <Route path="/Landing" element={<LandingPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

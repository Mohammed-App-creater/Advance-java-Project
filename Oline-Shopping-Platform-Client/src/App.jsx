import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./Pages/Header";
import UserDetails from "./Pages/UserDetails";
import ProductDetails from "./Pages/ProductDetails";
import ProductListing from "./Pages/ProductListing";
import Cart from "./Pages/Cart";
import SignUp from "./Pages/Forms/SignUp";
import LogIn from "./Pages/Forms/LogIn";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Header />} />
          <Route path="/signUp" element={<SignUp />} />
          <Route path="/logIn" element={<LogIn />} />
          <Route path="/Account" element={<UserDetails />} />
          <Route path="/Details" element={<ProductDetails />} />
          <Route path="/Products" element={<ProductListing />} />
          <Route path="/Cart" element={<Cart />} />
        </Routes>
      </BrowserRouter>
      
    </>
  );
}

export default App;

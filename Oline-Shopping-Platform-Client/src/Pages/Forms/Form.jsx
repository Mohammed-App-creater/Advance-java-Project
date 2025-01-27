import { useState } from "react"; 
import PropTypes from "prop-types";
import logo from "../../assets/photo_2024-12-03_09-36-25(1)(1)_enhanced.png";
import { Link } from "react-router-dom";

const Form = ({ signup, handleSubmit, Port = "/", logoFallback = logo }) => {
  const [isNameFocused, setIsNameFocused] = useState(false);
  const [isFocused, setIsFocused] = useState(false);
  const [isPasswordFocused, setIsPasswordFocused] = useState(false);
  const [Name, setName] = useState("");
  const [Email, setEmail] = useState("");
  const [emailError, setEmailError] = useState("");
  const [Password, setPassword] = useState("");

  const validateEmail = (Email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(Email);
  };

  const handleNameFocus = () => {
    setIsNameFocused((prev) => !prev);
  };

  const handleUserFocus = () => {
    setIsFocused(true);
    setEmailError("");
  };

  const handlePasswordFocus = () => {
    setIsPasswordFocused((prev) => !prev);
  };

  const handleEmailBlur = (e) => {
    const inputValue = e.target.value;
    if (!inputValue) {
      setEmailError("Email is required.");
    } else if (!validateEmail(inputValue)) {
      setEmailError("Please enter a valid email address.");
      console.warn("Invalid email format:", inputValue);
    }
    setIsFocused(false);
  };

  const handleChange = (e) => {
    const inputValue = e.target.value;
    setEmail(inputValue);
    if (!emailError) {
      if (validateEmail(inputValue)) {
        setEmailError("");
      }
    }
  };

  return (
    <div className="w-screen h-screen flex justify-center">
      <div className="max-w-[400px] w-full h-full flex flex-col items-center py-8">
        <div className="w-16 h-16 rounded-full bg-white mb-[30%]">
          <img src={logoFallback} alt="ASTU Chat logo" />
        </div>
        <h1 className="relative text-4xl font-semibold mb-8">
          {signup ? "Welcome Back" : "Create an account"}
        </h1>
        <div className="w-[90%] h-36">
          {!signup && (
            <div className="relative w-full">
              <label
                htmlFor="Name"
                className={`absolute top-4 left-4 bg-white text-gray-600 font-normal text-base mb-2 max-w-[90%] pointer-events-none text-ellipsis origin-top transition-all duration-150 ease-in-out whitespace-nowrap z-10 ${
                  isNameFocused || Name !== ""
                    ? "scale-90 -translate-x-2 -translate-y-7 text-[#10a37f] bg-white"
                    : ""
                }`}
              >
                Name*
              </label>
              <input
                type="text"
                id="Name"
                name="Name"
                aria-label="Full Name"
                autoComplete="true"
                onFocus={handleNameFocus}
                onBlur={handleNameFocus}
                onChange={(e) => setName(e.target.value)}
                className="border pl-4 border-[#c2c8d0] w-full h-14 text-[#2d333a] mb-6 rounded-md focus:border-[#10a37f] focus:outline-none"
              />
            </div>
          )}

          <div className="relative w-full mb-6">
            <label
              htmlFor="Email"
              className={`absolute top-4 left-4 bg-white text-gray-600 font-normal text-base mb-2 max-w-[90%] pointer-events-none text-ellipsis origin-top transition-all duration-300 ease-in-out whitespace-nowrap z-10 ${
                isFocused || Email !== ""
                  ? "scale-90 -translate-x-2 -translate-y-7 text-[#10a37f] bg-white"
                  : ""
              }`}
            >
              Email address*
            </label>
            <input
              type="email"
              required
              id="Email"
              autoComplete="true"
              onFocus={handleUserFocus}
              onBlur={handleEmailBlur}
              onChange={handleChange}
              name="Email"
              value={Email}
              aria-label="Email Address"
              className="border pl-4 border-[#c2c8d0] w-full h-14 text-[#2d333a] rounded-md focus:border-[#10a37f] focus:outline-none"
            />
            {emailError && <p className="text-red-500 text-sm">{emailError}</p>}
          </div>
          <div className="relative w-full">
            <label
              htmlFor="password"
              className={`absolute top-4 left-4 bg-white text-gray-600 font-normal text-base mb-2 max-w-[90%] pointer-events-none text-ellipsis origin-top transition-all duration-150 ease-in-out whitespace-nowrap z-10 ${
                isPasswordFocused || Password !== ""
                  ? "scale-90 -translate-x-2 -translate-y-7 text-[#10a37f] bg-white"
                  : ""
              }`}
            >
              Password*
            </label>
            <input
              type="password"
              id="password"
              name="password"
              aria-label="Password"
              onFocus={handlePasswordFocus}
              onBlur={handlePasswordFocus}
              onChange={(e) => setPassword(e.target.value)}
              className="border pl-4 border-[#c2c8d0] w-full h-14 text-[#2d333a] mb-6 rounded-md focus:border-[#10a37f] focus:outline-none"
            />
          </div>
          <button
            aria-disabled={!!emailError}
            disabled={!!emailError}
            onClick={(e) => {
              e.preventDefault();
              if (signup) {
                handleSubmit(Email, Password);
              } else {
                handleSubmit(Email, Password, Name);
              }
            }}
            type="submit"
            className="w-full h-14 bg-[#10a37f] text-white rounded-md hover:bg-[#10a37ebd] active:bg-[#10a37ebd] active:scale-95"
          >
            {signup ? "Log In" : "Sign Up"}
          </button>
        </div>
      </div>
    </div>
  );
};

Form.propTypes = {
  signup: PropTypes.bool.isRequired,
  Port: PropTypes.string.isRequired,
  handleSubmit: PropTypes.func.isRequired,
  logoFallback: PropTypes.string,
};

export default Form;

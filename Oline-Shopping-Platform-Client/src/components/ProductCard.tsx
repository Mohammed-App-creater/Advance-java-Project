import start from "../assets/icons/star.png";
import img from "../assets/landingImg.jpg";
import CartButton from "./ui/CartButton";
import { useNavigate } from "react-router-dom";

const ProductCard = () => {
  const Navigate = useNavigate();
  const handlClick=  () => {
    Navigate("/Landing");
  }
  return (
    <div className="w-[320px] h-auto max-h-[410px] rounded-lg shadow-2xl flex flex-col justify-between items-center ">
      <div className="w-full h-[300px] bg-gray-200 rounded-md bg-cover">
        <img className="w-full h-full object-cover" src={img} alt="netimg" />
      </div>
      <div className="w-11/12  rounded-md">
      <div className="w-full   flex flex-row justify-between items-center mt-2">
          <h1 className=" font-bold">Product Name</h1>
          <div className="w-auto  flex  justify-between items-center">
            <img className="w-5 h-5" src={start} alt="stars" />
            <img className="w-5 h-5" src={start} alt="stars" />
            <img className="w-5 h-5" src={start} alt="stars" />
            <img className="w-5 h-5" src={start} alt="stars" />
          </div>
        </div>
        <h2 className=" font-bold">Product Price ETB</h2>
      </div>
      <div className="w-11/12  ">
        <p className="text-[#585858] text-sm text-wrap">Product Description</p>
      </div>
      <div onClick={handlClick} className="w-[300px] h-[50px]  mt-2 ">
        <CartButton />
      </div>
    </div>
  );
};

export default ProductCard;

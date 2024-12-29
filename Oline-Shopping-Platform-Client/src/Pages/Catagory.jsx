import React from "react";
import p1 from "../assets/p1.png";
import p2 from "../assets/p22.png";
import p3 from "../assets/p3.png";

const Catagory = () => {
  return (
    <div className="py-20 px-10  bg-gray-200 my-7 flex flex-col justify-center relative items-center">
      <div className="absolute  top-5 left-5 text-2xl flex gap-2 font-extrabold text-[#DB4444]">
        <div className="w-9 h-16 rounded-2xl bg-[#DB4444]"></div>
        Catagorey
      </div>
      <div className=" flex flex-wrap justify-start items-center text-[#2C3E50] gap-3 pt-7">
        <div className="w-40 h-40 flex flex-col space-y-2 justify-center items-center text-[#2C3E50] rounded-full hover:border-4 hover:border-[#DB4444] hover:bg-white bg-white font-medium">
        <img src={p1} className="" alt="" />
        <p>Electronics</p>
        </div>
        <div className="w-40 h-40 flex flex-col space-y-2 justify-center items-center text-[#2C3E50] rounded-full hover:border-4 hover:border-[#DB4444] hover:bg-white bg-white font-medium">
        <img src={p2} className="h-16" alt="" />
        <p>Clothe</p>
        </div>
        <div className="w-40 h-40 flex flex-col space-y-2 justify-center items-center text-[#2C3E50] rounded-full hover:border-4 hover:border-[#DB4444] hover:bg-white bg-white font-medium">
        <img src={p3} alt="" />
        <p>beauty</p>
        </div>
        <div className="w-40 h-40 flex flex-col space-y-2 justify-center items-center text-[#2C3E50] rounded-full hover:border-4 hover:border-[#DB4444] hover:bg-white bg-white font-medium">
        <img src={p1} alt="" />
        <p>Electronics</p>
        </div>
        <div className="w-40 h-40 flex flex-col space-y-2 justify-center items-center text-[#2C3E50] rounded-full hover:border-4 hover:border-[#DB4444] hover:bg-white bg-white font-medium">
        <img src={p1} alt="" />
        <p>Electronics</p>
        </div>
        <div className="w-40 h-40 flex flex-col space-y-2 justify-center items-center text-[#2C3E50] rounded-full hover:border-4 hover:border-[#DB4444] hover:bg-white bg-white font-medium">
        <img src={p1} alt="" />
        <p>Electronics</p>
        </div>
        <div className="w-40 h-40 flex flex-col space-y-2 justify-center items-center text-[#2C3E50] rounded-full hover:border-4 hover:border-[#DB4444] hover:bg-white bg-white font-medium">
        <img src={p1} alt="" />
        <p>Electronics</p>
        </div>
        <div className="w-40 h-40 flex flex-col space-y-2 justify-center items-center text-[#2C3E50] rounded-full hover:border-4 hover:border-[#DB4444] hover:bg-white bg-white font-medium">
        <img src={p1} alt="" />
        <p>Electronics</p>
        </div>
      </div>
    </div>
  );
};

export default Catagory;

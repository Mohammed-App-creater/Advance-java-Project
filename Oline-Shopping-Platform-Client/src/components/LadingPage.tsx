import LandingPageImg from "../assets/landingImg.jpg";
const LandingPage = () => {
  return (
    <div className="container  p-4 flex flex-col ">
      <section className=" w-full h-full flex justify-center items-center ">
        <div className="  overflow-hidden flex flex-col md:flex-row mt-56  justify-center items-center ">
          <div className="text-wrap w-1/4 ">
            <h1 className="text-7xl font-bold mb-8">Advance Shopping</h1>
            <p className="text-lg mb-12">
              Welcome to Advance Shopping, your ultimate destination for online
              shopping! Explore a world of amazing products, unbeatable deals,
              and hassle-free shopping—all just a click away
            </p>

            <a
              href="/logIn"
              className="py-4 px-8 text-2xl font-bold text-white bg-[#ff5858] mt-16 rounded-lg"
            >
              Get Started
            </a>
          </div>
          <div className="  cover-image overflow-hidden w-[75%] h-[75%] ">
            <img
              src={LandingPageImg}
              alt="online-shopping-concept-landing-page"
              className="w-100 h-auto"
            />
          </div>
        </div>
      </section>
      
    </div>
  );
};

export default LandingPage;

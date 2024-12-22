import cart from "@/assets/icons/cart.png"

import { Button } from "@/components/ui/button"

export default function ButtonWithIcon() {
  return (
    <Button  size={"full"}>
      <img src={cart} alt="cart" className=" w-[15%] h-[80%]" /> Add to Cart
    </Button>
  )
}
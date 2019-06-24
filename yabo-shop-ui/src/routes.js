import Cart from "./pages/Cart.vue";
import Index from "./pages/Index.vue";
import Filtered from "./pages/Filtered";

export default [
  {
    path: "/filtered",
    component: Filtered,
    name: "Filtered"
  },
  {
    path: "/cart",
    component: Cart,
    name: "Cart"
  },
  {
    path: "/",
    component: Index,
    name: "Index"
  }
];

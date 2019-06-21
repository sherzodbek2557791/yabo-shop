import Cart from "./pages/Cart.vue";
import Index from "./pages/Index.vue";
import Men from "./pages/Men.vue";
import Sale from "./pages/Sale.vue";
import Women from "./pages/Women.vue";
import Filtered from "./pages/Filtered";

export default [
  {
    path: "/filtered",
    component: Filtered,
    name: "Filtered"
  },
  {
    path: "/women",
    component: Women,
    name: "Women"
  },
  {
    path: "/men",
    component: Men,
    name: "Men"
  },
  {
    path: "/cart",
    component: Cart,
    name: "Cart"
  },
  {
    path: "/sale",
    component: Sale,
    name: "Sale"
  },
  {
    path: "/",
    component: Index,
    name: "Index"
  }
];

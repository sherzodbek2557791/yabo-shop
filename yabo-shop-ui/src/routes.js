import Index from "./components/Index.vue";
import CategoryGrid from "./components/CategoryGrid.vue";
import Blog from "./components/Blog.vue";
import Faq from "./components/Faq.vue";
import Contact from "./components/Contact.vue";

export default [
  { path: "/", component: Index },
  { path: "/category_grid", component: CategoryGrid },
  { path: "/blog", component: Blog },
  { path: "/faq", component: Faq },
  { path: "/contact", component: Contact }
];

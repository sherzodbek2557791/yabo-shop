import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";
import ElementUI from "element-ui";
import i18n from "@/plugins/i18n";

/**
 * Styles imported
 */
import "./assets/css/animate.min.css";
import "./assets/css/bootstrap.min.css";
import "./assets/css/config.css";
import "./assets/css/font-awesome.min.css";
import "./assets/css/owl.carousel.css";
import "./assets/css/owl.transitions.css";
import "./assets/css/style.css";
import "./assets/css/colors/blue.css";
import "./assets/css/colors/dark-green.css";
import "./assets/css/colors/green.css";
import "./assets/css/colors/navy.css";
import "./assets/css/colors/orange.css";
import "./assets/css/colors/red.css";

import "./assets/css/element.css";

import routes from "./routes";

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(ElementUI);
Vue.prototype.$locale = i18n;

const router = new VueRouter({ mode: "history", routes });

new Vue({
  i18n: i18n.plugin,
  router,
  render: h => h(App)
}).$mount("#app");

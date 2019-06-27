import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";
import ElementUI from "element-ui";
import i18n from "@/plugins/i18n";
import store from "@/plugins/store";
import { sync } from "vuex-router-sync";
import http from "@/service/http";
import regex from "@/plugins/directives/regex";


/**
 * Styles imported
 */
import "./assets/css/element.css";
import "./assets/css/main.css";
import "./assets/css/w3.css";

import routes from "./routes";
import globals from "./globals";

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(ElementUI);
Vue.prototype.$locale = i18n;
Vue.prototype.$http = http;

// This is a global mixin, it is applied to every vue instance
Vue.mixin({ data: () => globals });

const router = new VueRouter({ mode: "history", routes });
sync(store, router);

Vue.directive("regex", regex);

new Vue({
  i18n: i18n.plugin,
  router,
  store,
  render: h => h(App)
}).$mount("#app");

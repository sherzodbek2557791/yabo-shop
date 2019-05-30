import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    cart: {
      count: 0,
      items: {}
    }
  },
  mutations: {
    setCartProduct(state, item) {
      state.cart.count += item.count;
      let key = item.key;
      let tmpItem = state.cart.items[key];
      if (tmpItem) tmpItem.count = item.count;
      else tmpItem = item;
      Vue.set(state.cart.items, key, tmpItem);
    },
    setCartClear(state) {
      Vue.set(state, "cart", {
        count: 0,
        items: {}
      });
    }
  },
  plugins: [
    createPersistedState({
      key: "yabo-shop",
      paths: ["cart"]
    })
  ]
});

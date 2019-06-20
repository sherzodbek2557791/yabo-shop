import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    uiState: {
      locale: "ru",
      currency: {
        index: 0,
        code: "USD"
      }
    },
    cart: {
      count: 0,
      amount: 0,
      items: {}
    }
  },
  mutations: {
    setCartProduct(state, item) {
      state.Cart.count += item.count;
      state.Cart.amount += item.amount;
      let key = item.key;
      let tmpItem = state.Cart.items[key];
      if (tmpItem) {
        tmpItem.count = item.count;
        tmpItem.amount = item.amount;
      } else tmpItem = item;
      Vue.set(state.Cart.items, key, tmpItem);
    },
    setCartClear(state) {
      Vue.set(state, "Cart.vue", {
        count: 0,
        items: {},
        amount: 0
      });
    },
    setCurrency(state, currency) {
      Vue.set(state.uiState, "currency", currency);
    },
    setClearCurrency(state) {
      Vue.set(state.uiState, "currency", {
        index: 0,
        code: "USD"
      });
    }
  },
  plugins: [
    createPersistedState({
      key: "yabo-shop",
      paths: ["uiState", "Cart.vue"]
    })
  ]
});

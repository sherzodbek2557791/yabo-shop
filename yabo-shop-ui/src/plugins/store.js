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
    cartTotal: 0,
    cart: {},
    form: {},
    sale: false
  },
  mutations: {
    switchSale: state => {
      state.sale = !state.sale;
    },
    clearCartCount: state => {
      state.cartTotal = 0;
    },
    clearCartContents: state => {
      state.cart = {};
    },
    clearCart: state => {
      state.cartTotal = 0;
      state.cart = {};
    },
    setForm: (state, form) => {
      state.form = form;
    },
    clearForm: state => {
      state.form = {};
    },
    addItem: (state, item) => {
      state.cartTotal++;
      if (item.code in state.cart) {
        state.cart[item.code].count++;
      } else {
        let stateItem = Object.assign({}, item);
        stateItem.count = 1;
        state.cart[item.code] = stateItem;
      }
    },
    removeItem: (state, item) => {
      let stateItem = state.cart[item.code];
      if(!stateItem)return;

      state.cartTotal -= stateItem.count;
      delete state.cart[stateItem.code];
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
      paths: ["uiState", "cartTotal", "cart", "form", "sale"]
    })
  ]
});

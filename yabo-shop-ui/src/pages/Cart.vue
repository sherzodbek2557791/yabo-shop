<template>

  <div class="capsule cart">
    <div v-if="cartTotal > 0">
      <h1><i class="fas fa-shopping-cart" style="font-size: 18pt;"></i> {{ $t("cart.title") }}</h1>
      <div class="cartitems" v-for="(item, index) in cart" :key="index">
        <div class="carttext">
          <h4>{{ item.title }}</h4>
          <p>{{ item.price | currencyFilter }} x {{ item.count }}</p>
          <p>
            {{ $t('cart.amount') }}: <strong>{{ item.price * item.count | currencyFilter}}</strong>
          </p>
        </div>
        <img
          class="cartimg"
          :src="`/${item.image}`"
          :alt="`Image of ${item.title}`"
        />
        <el-button type="danger" class="cart-remove-btn" @click="removeItem(item)" circle size="mini" icon="el-icon-delete"></el-button>
      </div>
      <div class="total">
        <h3>{{ $t('cart.total') }}: {{ total | currencyFilter }}</h3>
      </div>
      <app-checkout
        :total="total"
        @successSubmit="success = true"
      ></app-checkout>
    </div>

    <div v-else-if="cartTotal === 0 && success === false" class="empty">
      <h1>{{ $t("cart.title") }}</h1>
      <h3>{{ $t('cart.empty') }}</h3>
      <router-link exact to="/"><button>{{ $t('cart.fillUp') }}</button></router-link>
    </div>

    <div v-else>
      <app-success @restartCart="success = false" />
      <h2>Success!</h2>
      <p>Your order has been processed, it will be delivered shortly.</p>
    </div>
  </div>
</template>

<script>
import AppCheckout from "./../components/AppCheckout.vue";
import AppSuccess from "./../components/AppSuccess.vue";

export default {
  data() {
    return {
      success: false
    };
  },
  components: {
    AppCheckout,
    AppSuccess
  },
  methods: {
    removeItem(item){
      this.$store.commit('removeItem', item);
    }
  },
  computed: {
    cart() {
      return this.$store.state.cart;
    },
    cartTotal() {
      return this.$store.state.cartTotal;
    },
    total() {
      return Object.values(this.cart)
        .reduce((acc, el) => acc + el.count * el.price, 0)
        .toFixed(2);
    }
  },
  filters: {
    usdollar: function(value) {
      return `$${value}`;
    },
    currencyFilter: function(value) {
      return `${value} сум`;
    }
  }
};
</script>

<style scoped>
.cart > div {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  align-items: center;
  padding: 50px;
  background: white;
  border-radius: 3px;
  margin-top: 10px;
  padding-bottom: 80px;
}

.cart.empty h1,
.cart.empty h3 {
  margin-bottom: 15px;
}

.cartitems {
  position: relative;
  padding: 30px;
  border-bottom: 1px solid #ccc;
  width: 500px;
}

.carttext {
  width: 250px;
  float: left;
}

.carttext p,
.carttext h4 {
  padding: 5px;
}

.cartimg {
  width: 100px;
  border: 1px solid #ccc;
  float: right;
}

.cart-remove-btn {
  position: absolute;
  right: 15px;
  top: 15px;
}

.total {
  margin: 20px 0;
}
</style>

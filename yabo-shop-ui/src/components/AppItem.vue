<template>
  <div class="item">
    <p>{{ item.title }}</p>
    <span class="salepill" v-if="item.description">{{ $t('item.installmentPlan') }}</span>
    <img class="img-product" :src="`/${item.image}`" :alt="`Image of ${item.title}`">
    <p>{{ item.price | currencyFilter }}</p>
    <button class="add" @click="addItem">{{ $t('index.addItem') }}</button>
  </div>
</template>

<script>
export default {
  props: {
    item: {
      type: Object,
      required: true
    },
    index: {
      type: Number,
      required: true
    }
  },
  filters: {
    usdollar: function(value) {
      return `$${value}`;
    },
    currencyFilter: function(value) {
      return `${value} сум`;
    }
  },
  methods: {
    addItem() {
      this.$store.commit('addItem', this.item);
    }
  }
};
</script>

<style scoped>
.item {
  border-radius: 5px;
  padding: 20px;
  background: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  box-shadow: 0px 6px 15px 0px rgba(0,0,0,0.1)
}

.salepill {
  background: rgb(232, 35, 25);
  color: white;
  font-family: 'Barlow', sans-serif;
  position: absolute;
  right: 30px;
  top: 60px;
  padding: 2px 10px 4px;
  text-transform: uppercase;
  font-size: 13px;
  font-weight: 700;
  border-radius: 1000px;
}

p {
  font-size: 18px;
}
</style>
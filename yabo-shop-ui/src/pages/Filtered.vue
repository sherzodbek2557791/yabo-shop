<template>
  <main class="capsule">
    <app-masthead :img="`/${headerImage}`" :title="title" :bkcolor="bkColor" />
    <div class="contain">
      <app-sidebar :pricerange.sync="highprice" />
      <transition-group name="items" tag="section" class="content">
        <app-item
          v-for="(item, index) in wProducts"
          :key="index"
          :item="item"
          :index="index"
        />
      </transition-group>
    </div>
  </main>
</template>

<script>
import AppSidebar from "./../components/AppSidebar.vue";
import AppMasthead from "./../components/AppMasthead.vue";
import AppItem from "./../components/AppItem.vue";

export default {
  components: {
    AppSidebar,
    AppMasthead,
    AppItem
  },
  data() {
    return {
      highprice: 300
    };
  },
  computed: {
    wProducts() {
      let { category } = this.$route.query;
      let array = category
        ? this.app.getters.filtered(category)
        : this.app.products;

      return array.filter(el =>
        this.$store.state.sale
          ? el.price < this.highprice && el.sale
          : el.price < this.highprice
      );
    },
    bkColor() {
      let defaultColor = "#e82319";
      let { category } = this.$route.query;
      if (!category) return defaultColor;

      let item = this.app.categories.find(x => x.name === category);
      return item.color || defaultColor;
    },
    title() {
      let defaultColor = "Товары";
      let { category } = this.$route.query;
      if (!category) return defaultColor;

      let item = this.app.categories.find(x => x.name === category);
      return item.title || defaultColor;
    },
    headerImage(){
      let { category } = this.$route.query;
      if (!category) return null;
      let item = this.app.categories.find(x => x.name === category);
      return item.headerImg;
    }
  }
};
</script>

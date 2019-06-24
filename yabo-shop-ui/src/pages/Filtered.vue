<template>
  <main class="capsule">
    <app-masthead
      v-loading="categoryLoading"
      :img="headerImage"
      :title="title"
      :bkcolor="bkColor"
    />
    <div class="contain">
      <app-sidebar :pricerange.sync="highprice" />
      <transition-group
        name="items"
        tag="section"
        class="content"
        @after-enter="afterEnter"
      >
        <app-item
          v-for="(item, index) in table.data"
          :key="index"
          :item="item"
          :index="index"
        />
        <el-button
          style="grid-column: 1 / -1;"
          type="primary"
          key="next-btn"
          :loading="table.loading"
          v-if="hasNext"
          @click="loadNext(category['code'])"
          round
          >{{ $t("index.next") }}</el-button
        >
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
      highprice: 300,
      categoryLoading: false,
      category: null,
      table: {
        data: [],
        loading: false,
        paging: {
          current: 0,
          total: 0,
          size: 6
        }
      }
    };
  },
  methods: {
    loadCategory(categoryCode) {
      if (!categoryCode) return;
      this.categoryLoading = true;
      this.$http
        .get("category/one", { code: categoryCode })
        .then(({ data }) => {
          this.categoryLoading = false;
          this.category = data;
        })
        .catch(error => {
          this.categoryLoading = false;
          console.error(error);
        });

      this.table.data = [];
      this.table.paging.current = 0;
      this.table.paging.total = 0;
      this.loadNext(categoryCode);
    },
    afterEnter() {
      if (this.table.data.length > this.table.paging.size)
        this.$parent.$emit("scroll-to-bottom");
    },
    loadNext(categoryCode) {
      this.table.loading = true;
      this.$http
        .get("product/list-paging-filtered", {
          category: categoryCode,
          page: this.table.paging.current,
          size: this.table.paging.size
        })
        .then(({ data }) => {
          const { content, totalElements } = data;
          this.table.loading = false;
          this.table.data.push(...content);
          this.table.paging.total = totalElements;
          this.table.paging.current++;
        })
        .catch(error => {
          this.table.loading = false;
          console.error(error);
        });
    }
  },
  mounted() {
    let { category } = this.$route.query;
    this.loadCategory(category);
  },
  watch: {
    "$route.query.category": function(newValue, oldValue) {
      if (newValue !== oldValue) this.loadCategory(newValue);
    }
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
      if (!this.category) return defaultColor;
      return this.category["color"] || defaultColor;
    },
    title() {
      let defaultTitle = "Товары";
      if (!this.category) return defaultTitle;
      return this.category["title"] || defaultTitle;
    },
    headerImage() {
      if (!this.category) return null;
      let { headerImg } = this.category;
      return headerImg ? `/${headerImg}` : null;
    },
    hasNext() {
      let { total } = this.table.paging;
      let length = this.table.data.length;
      return length < total;
    }
  }
};
</script>

<template>
  <main class="capsule">
    <app-masthead />
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
          :key="`key-${index}`"
          :item="item"
          :index="index"
        />
        <el-button
          class="next-page-btn"
          type="primary"
          key="next-btn"
          :loading="table.loading"
          v-if="hasNext"
          @click="loadNext"
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
    afterEnter() {
      if (this.table.data.length > this.table.paging.size)
        this.$parent.$emit("scroll-to-bottom");
    },
    loadNext() {
      this.table.loading = true;
      this.$http
        .get("product/list-paging", {
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
  computed: {
    hasNext() {
      let { total } = this.table.paging;
      let length = this.table.data.length;
      return length < total;
    }
  },
  mounted() {
    this.loadNext();
  }
};
</script>

<style>
/* no grid support */
aside {
  float: left;
  width: 19.1489%;
}

.content {
  /*no grid support*/
  float: right;
  width: 79.7872%;
  /* grid */
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-gap: 10px;
  padding: 0 !important;
}

.next-page-btn {
  grid-column: 1 / -1;
}

@supports (display: grid) {
  .capsule > * {
    width: auto;
    margin: 0;
  }
}
</style>
